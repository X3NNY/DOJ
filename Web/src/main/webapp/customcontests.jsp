<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>DIY比赛 - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<style>
    #contestList tbody tr td {
        vertical-align: middle;
    }
    .panel-body {
        padding: 0px;
    }
    .table {
        margin-bottom: 0px;
    }
</style>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="panel panel-default">
                <div class="panel-heading">
                    DIY比赛列表
                    <%
                        if (user != null) {
                    %>
                    <front style="float: right;"><a href="newcustoncontest.jsp">创建比赛</a></front>
                    <%
                        }
                    %>
                </div>
                <front style="float: left;">
                    <div class="btn-toolbar">
                        <div class="btn-group" id="contestPage">

                        </div>
                    </div>
                </front>
                <front style="float: right;">
                    <form style="margin:0px" class="form-inline" action="javascript:searchContest();">
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                        <span class="input-group-addon">
                            名称
                        </span>
                                <input type="text" class="form-control" id="searchInfo" style="width:100px" value="">
                            </div>
                        </div>
                        <a class="btn btn-default btn-sm" href="javascript:searchContest();">
                            搜索
                        </a>
                    </form>
                </front>
                <div class="panel-body">
                    <table class="table table-bordered table-striped table-hover table-condensed" id="contestList">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th style="text-align: center;">Title</th>
                                <th style="text-align: center;">Writers</th>
                                <th style="text-align: center;">StartTime</th>
                                <th style="text-align: center;">Length</th>
                                <th style="text-align: center;">Level</th>
                                <th style="text-align: center;">Register</th>
                                <th style="text-align: right;">#&nbsp;&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/customcontests.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/customcontests.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/customcontests.js"></script>
<% } %>
</body>
</html>
