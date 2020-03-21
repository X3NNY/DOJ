<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>排行榜 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-default">
        <div class="panel-heading">排行榜</div>
        <div class="panel-body" style="padding: 5px">
            <front style="float: left">
                <div class="btn-toolbar" >
                    <div class="btn-group"  id="rankPage">
                    </div>
                </div>
            </front>
            <front style="float: right">
                <form style="margin:0px" class="form-inline" action="javascript:searchUser();">
                    <div class="form-group">
                        <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    Username
                                </span>
                            <input type="text" class="form-control" id="searchInfo" style="width:120px" value="">
                        </div>
                    </div>
                    <a class="btn btn-default btn-sm" href="javascript:searchUser();">
                        搜索
                    </a>
                </form>
            </front>
        </div>
        <style>
            tr td:nth-last-of-type(1) {
                width: 1%;
            }
            tr td:nth-last-of-type(2) {
                width: 5%;
            }
            tr td:nth-of-type(2) {
                width: 10%;
            }
            tr td:nth-of-type(3) {
                width: 50%;
            }
        </style>
        <table class="table table-striped table-hover table-condensed">
            <thead>
            <tr>
                <th>Rank</th>
                <th style="width: 15%;">Username</th>
                <th style="text-align:center;width: 50%;">Note</th>
                <th style="text-align: right;">AC</th>
                <th style="text-align: right;width: 5%">Rating</th>
                <th style="text-align: right;width: 1%">#</th>
            </tr>
            </thead>
            <tbody id="rankList">
            </tbody>
        </table>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/rank.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/rank.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/rank.js"></script>
<% } %>
</body>
</html>