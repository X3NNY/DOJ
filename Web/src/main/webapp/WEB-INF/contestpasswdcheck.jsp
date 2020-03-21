<%@page contentType="text/html; charset=UTF-8" %>

<div class="row clearfix">
    <div class="col-md-4 column"></div>
    <div class="col-md-4 column">
        <div class="panel panel-default">
            <div class="panel-heading">
                验证密码
            </div>
            <br />
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="passwd" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-8">
                        <input type="text" id="passwd" class="form-control" placeholder="请输入比赛密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="checkPasswd()">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function checkPasswd() {
        $.ajax({
            url: "/api/contest/checkpasswd",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "cid": parseInt(getUrlParam("cid")),
                "password": $("#passwd").val()
            }),
            success: function (res) {
                if (res.state === 0) {
                    location.href = location.href
                } else {
                    narn('log','密码不正确！')
                }
            }
        })
    }
</script>