<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/setting.jsp"%>
<div class="panel panel-primary">
    <div class="panel-heading">
        批量添加
    </div>
    <div class="panel-body">
        <p>
            您可以在这里批量上传题目，但是请保证上传的文件符合格式要求。
        </p>
        <form style="margin:0px" class="form-horizontal" id="newForm" enctype="multipart/form-data" onsubmit="return false;">
            <div class="form-group row">
                <label class="col-sm-2 control-label">上传文件</label>
                <div class="col-xs-10">
                    <input type="file" class="form-control" name="datafile">
                </div>
            </div>
            <div class="form-group"><div class="col-xs-10 col-xs-offset-2">
                <input class="submit btn btn-default" type="submit" value="submit" onclick="submitData()">
            </div>
            </div>
        </form>
    </div>
</div>
<script>
    function submitData() {
        let formData = new FormData($("#newForm")[0]);
        $.ajax({
            url: "<%=judgeUrl%>/api/problem/batch/add?token=<%=session.getId()%>",
            type: "POST",
            data: formData,
            contentType: false,
            processData: false,
            async : false,
            success: function () {
                narn("success",'上传成功')
            }
        })
    }
</script>