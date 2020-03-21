<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改资料 - DOJ</title>
    <%@include file="header.jspf" %>

</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-default">
        <div class="panel-heading">
            修改资料
        </div>
        <div class="panel-body">
            <form class="form-horizontal" id="userEdit" onsubmit="return false">
                <div class="form-group">
                    <label for="oldPassword" class="col-sm-2 control-label">原密码</label>
                    <div class="col-sm-8">
                        <input type="text" id="oldPassword" name="oldPassword" class="form-control" placeholder="必须填写"/>
                        <em style="color: red;">*</em>
                    </div>
                </div>
                <div class="form-group">
                    <label for="newPassword" class="col-sm-2 control-label">新密码</label>
                    <div class="col-sm-8">
                        <input type="text" id="newPassword" name="newPassword" class="form-control" placeholder="需要修改则填写" />
                    </div>
                </div>
                <div class="form-group">
                    <label for="rPassword" class="col-sm-2 control-label">重复新密码</label>
                    <div class="col-sm-8">
                        <input type="text" id="rPassword" name="rPassword" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="school" class="col-sm-2 control-label">学校/单位</label>
                    <div class="col-sm-8">
                        <input type="text" id="school" name="school" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="note" class="col-sm-2 control-label">个人说明</label>
                    <div class="col-sm-8">
                        <input type="text" id="note" name="note" class="form-control" placeholder="写点什么吧"/>
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
<script src="${pageContext.request.contextPath}/static/js/useredit.min.js"></script>
</body>
</html>