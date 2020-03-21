<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>题库</title>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-default">
        <div class="panel-heading">
            题库
        </div>
        <div class="panel-body" style="padding: 5px">
                <front style="float: left">
                    <div class="btn-toolbar" >
                        <div class="btn-group"  id="problemPage">
                        </div>
                    </div>
                </front>
                <front style="float: right">
                    <form style="margin:0px" class="form-inline" action="javascript:searchProblem();">
                        <div class="form-group">
                            <div class="checkbox"><label><input type="checkbox" id="myStar" name="myStar">我的收藏</label></div>
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    标题/ID/作者
                                </span>
                                <input type="text" class="form-control" id="searchInfo" style="width:120px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    标签
                                </span>
                                <select class="form-control" id="tagSelect">
                                    <option value="" selected="selected">
                                        All
                                    </option>
                                </select>
                            </div>
                        </div>
                        <a class="btn btn-default btn-sm" href="javascript:searchProblem();">
                            搜索
                        </a>
                    </form>
                </front>
            </div>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>ID</th>
                <th style="text-align: center;">Title</th>
                <th style="text-align: center;">Ratio</th>
                <th style="text-align: center;">Author</th>
                <th style="text-align: right;">#&nbsp;&nbsp;&nbsp;&nbsp;</th>
            </tr>
            </thead>
            <tbody id="problemList">
            </tbody>
        </table>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/problems.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/problems.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/problems.js"></script>
<% } %>
</body>
</html>