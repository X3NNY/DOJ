<%@page contentType="text/html; charset=UTF-8" %>
<div class="panel panel-info">
    <div class="panel-heading">
        添加用户
    </div>
    <div class="panel-body">
        <h1>批量添加</h1>
        <form action="/api/user/adduser" id="addUsers" method="post" class="form-horizontal">
            <div class="form-group">
                <label for="pre" class="col-sm-2 control-label">前缀</label>
                <div class="col-sm-8">
                    <input type="text" id="pre" name="pre" class="form-control" placeholder="请输入账号前缀，默认为user"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">后缀</label>
                <span class="col-sm-8">
                    <input type="radio" name="suf" value="0"/>数字序号
                    <input type="radio" name="suf" value="1"/>随机6位数字
                    <input type="radio" name="suf" value="2"/>随机6位字母
                    <input type="radio" name="suf" value="3"/>随机6位混合
                </span>
            </div>
            <div class="form-group">
                <label for="num" class="col-sm-2 control-label">数量</label>
                <div class="col-sm-8">
                    <input type="text" id="num" name="num" class="form-control" placeholder="请输入数量"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="button" class="btn btn-default" onclick="addUser()">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    function addUser() {
        let num = parseInt($("#num").val())
        let opt = parseInt($("input[name='suf']:checked").val())
        if (!num || isNaN(num) || num <= 0 || num > 200) {
            narn('log', '数量不合法，请输入1-200之间的数字')
        } else if (!opt || isNaN(opt) || opt < 0 || opt >=4) {
            narn('log','请选择正确的后缀')
        } else {
            $("#addUsers").submit()
        }
    }
</script>