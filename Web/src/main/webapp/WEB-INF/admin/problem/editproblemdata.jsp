<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/setting.jsp"%>
<div class="panel panel-info">
    <div class="panel-heading">
        编辑数据&nbsp;&nbsp;<font></font>
        <front style="float: right;">
            <span class="glyphicon glyphicon-arrow-left"><a href="javascript:loadMyProblem();">返回</a></span>
        </front>
    </div>
    <div class="panel-body">
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>文件名</th>
                    <th>大小</th>
                    <th>操作</th>
                    <th>操作</th>
                    <th>操作</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        <br />
        <br />
        <form style="margin:0px" id="newForm" class="form-horizontal" enctype="multipart/form-data" onsubmit="return false;">
            <input type="hidden" value="" name="pid">
            <div class="form-group row">
                <label class="col-sm-2 control-label">上传数据</label>
                <div class="col-xs-10">
                    <input type="file" class="form-control" multiple="multiple" name="datafile">
                </div>
            </div>
            <div class="form-group">
                <div class="col-xs-10 col-xs-offset-2">
                    <input class="submit btn btn-default" type="submit" onclick="submitData()" value="Submit">
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    if (typeof pid == "undefined") {
        var pid;
    }
    pid = location.hash.substring(1,5);

    $.getJSON("/api/problem?pid="+pid,function (res) {
        $("input[name='pid']").val(pid);
        $("div.panel-heading font").text(res.pid + " - "+ res.title);
    })

    function submitData() {
        let formData = new FormData($("#newForm")[0]);
        $.ajax({
            url: "<%=judgeUrl%>/api/problem/add/data?token=<%=session.getId()%>",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            async : false,
            success: function () {
                narn("success",'上传成功')
                getProData()
            }
        })
    }

    function getProData() {
        $.getJSON("<%=judgeUrl%>/api/problem/down/"+pid+"?token="+$("#token").val(), function (res) {
            if (res.state === 1) {
                narn("log", "获取数据失败");
            } else {
                let table = $("table tbody");
                table.empty();
                for (let i = 0; i < res.data.length; i++) {
                    let tp = res["data"][i];

                    let size;
                    if (tp.size < 1024) {
                        size = tp.size + "B";
                    } else if (tp.size < 1024*1024) {
                        size = (tp.size/1024).toFixed(0) + "KB";
                    } else if (tp.size < 1024*1024*1024) {
                        size = ((tp.size/1024)/1024).toFixed(0) + "MB";
                    }

                    if (tp.name.endsWith(".zip")) {
                        table.append(HTML.tr(HTML.td(tp.name) + HTML.td(size) + HTML.td(HTML.a(tp.link+"&token="+$("#token").val(), "下载")) + HTML.td(HTML.a("javascript:delProData(" + pid + ",'" + tp.name + "')", "删除")) + HTML.td(HTML.a("javascript:unzipProData(" + pid + ",'" + tp.name + "')", "解压")) + HTML.td("")));
                    } else {
                        table.append(HTML.tr(HTML.td(tp.name) + HTML.td(size) + HTML.td(HTML.a(tp.link+"&token="+$("#token").val(), "下载")) + HTML.td(HTML.a("javascript:delProData(" + pid + ",'" + tp.name + "')", "删除")) + HTML.td("") + HTML.td("")));
                    }
                }
            }
        })
    }
    function delProData(pid,filename) {
        $.ajax({
            url: "<%=judgeUrl%>/api/problem/del/data?pid="+pid+"&filename="+filename+"&token="+$("#token").val(),
            type: "DELETE",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("log","删除成功");
                    getProData(0);
                } else if (res.state === 1) {
                    narn("log","文件不存在");
                } else if (res.state === 2) {
                    narn("los","无权限");
                }
            }
        })
    }
    function unzipProData(pid,filename) {
        $.ajax({
            url: "<%=judgeUrl%>/api/problem/unzip/data?pid="+pid+"&filename="+filename+"&token="+$("#token").val(),
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success","解压成功")
                    getProData(0);
                }
            }
        })
    }
    getProData();
</script>