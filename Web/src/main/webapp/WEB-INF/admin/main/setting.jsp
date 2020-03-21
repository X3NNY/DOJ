<%@page contentType="text/html; charset=UTF-8" %>

<div class="panel panel-info">
    <div class="row clearfix">
        <div class="col-md-10 column">
            <form class="form-horizontal" id="userRegister" onsubmit="return false">
                <h1 class="text-center">主页设置</h1>
                <div class="form-group">
                    <label for="blogid" class="col-sm-2 control-label">主页BlogID</label>
                    <div class="col-sm-8">
                        <input type="text" id="blogid" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="thing" class="col-sm-2 control-label">主页简言</label>
                    <div class="col-sm-8">
                        <input type="text" id="thing" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="notice" class="col-sm-2 control-label">主页通知</label>
                    <div class="col-sm-8">
                        <input type="text" id="notice" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="editSubmit()">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function loadInfo_() {
        $.getJSON("/api/index/blog", function (res) {
            $("#blogid").val(res.bid);
        })
        $.getJSON("/api/about/notify", function (res) {
            $("#notice").val(res.msg)
        })
        $.getJSON("/api/about/index_thing", function (res) {
            $("#thing").val(res.msg)
        })
    }
    loadInfo_()
    function editSubmit() {
        let blogid = $("#blogid").val();
        if (blogid.match(/^[0-9]+$/)==null) {
            narn('log','BlogID必须为纯数字')
        } else {
            $.ajax({
                url: "/api/about/edit",
                type: "PUT",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    blogid: parseInt(blogid),
                    thing: $("#thing").val(),
                    notice: $("#notice").val()
                }),
                success: function (res) {
                    if (res.state === 0) {
                        narn("success", "修改成功")
                    }
                    loadInfo_()
                }
            })
        }
    }
</script>