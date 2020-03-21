<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<title>注册 - DOJ</title>
	<%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<style>
	.form-control {
		display: inline;
		width: 90%;
		height: 34px;
		padding: 6px 12px;
		font-size: 14px;
		line-height: 1.42857143;
		color: #555;
		background-color: #fff;
		background-image: none;
		border: 1px solid #ccc;
		border-radius: 4px;
		-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
		-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
		-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
		transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
	}
</style>
<div class="container-fluid" style="margin-top: 5%;">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form class="form-horizontal" id="userRegister" onsubmit="return false">
				<div class="form-group">
					<label for="email" class="col-sm-2 control-label">邮箱</label>
					<div class="col-sm-8">
						<input type="email" id="email" name="email" class="form-control"/>
						<em style="color: red;">*</em>
					</div>
				</div>
				<div class="form-group">
					<label for="invitation" class="col-sm-2 control-label">邀请码</label>
					<div class="col-sm-8">
						<input type="text" id="invitation" name="invitation" class="form-control"/>
						<em style="color: red;">*</em>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-default" onclick="registSubmit(0)">Submit</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script src="${pageContext.request.contextPath}/static/js/userregister.min.js"></script>
</body>
</html>