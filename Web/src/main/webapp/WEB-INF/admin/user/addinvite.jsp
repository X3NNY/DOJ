<%@page contentType="text/html; charset=UTF-8" %>

<div class="panel panel-info">
    <div class="row clearfix">
        <div class="col-md-10 column">
            <h1 class="text-center">添加邀请码</h1>
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="invitecode" class="col-sm-2 control-label">单个添加</label>
                    <div class="col-sm-8">
                        <input type="text" id="invitecode" class="form-control" placeholder="请输入六位邀请码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="addOne()">Submit</button>
                    </div>
                </div>
            </form>
            <br />
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="addnum" class="col-sm-2 control-label">批量添加</label>
                    <div class="col-sm-8">
                        <input type="text" id="addnum" class="form-control" placeholder="请输入要添加的个数"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="addNum()">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function addOne() {
        let code = $("#invitecode").val();
        if (code.length !== 6 || code.match(/^[0-9a-zA-Z]{6}$/) == null) {
            narn('log','请输入六位只含数字和大小写字母的邀请码')
        } else {
            $.ajax({
                url: "/api/user/invite/newone?invitecode=" + code,
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                success: function (res) {
                    if (res.state === 0) {
                        narn("success", "添加成功")
                    }
                }
            })
        }
    }

    function addNum() {
        let num = $("#addnum").val();
        if (num.match(/^[0-9]+$/) == null) {
            narn("log","必须为正整数")
        } else {
            $.ajax({
                url: "/api/user/invite/newnum?num=" + num,
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                success: function (res) {
                    if (res.state === 0) {
                        narn("success", "成功添加了" + num + "个邀请码")
                    }
                }
            })
        }
    }
</script>