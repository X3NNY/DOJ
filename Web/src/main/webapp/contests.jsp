<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>比赛列表 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>
<style>
    #contestList tbody tr td {
        vertical-align: middle;
    }
    #contestHisList tbody tr td {
        vertical-align: middle;
    }
    .panel-body {
	    padding: 0px;
    }
    .table {
	    margin-bottom: 0px;
    }
</style>

<div class="container-fluid" style="margin-top: 5%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">比赛</h3>
        </div>
        <!-- Table -->
        <div class="panel-body">
            <table class="table table-striped table-hover table-bordered table-condensed" id="contestList">
            <thead>
                <tr>
                    <th>ID</th>
                    <th style="text-align: center;">Title</th>
                    <th style="text-align: center;">Writers</th>
                    <th style="text-align: center;">Start</th>
                    <th style="text-align: center;">Length</th>
                    <th style="text-align: center;">Level</th>
                    <th style="text-align: center;">Register</th>
                    <th style="text-align: right;">#&nbsp;&nbsp;</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
            </table>
        </div>
    </div>
</div>
<br />
<div class="container-fluid">
    <div class="panel panel-info">
        <div class="panel-heading" >
            <h3 class="panel-title">历史</h3>
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
            
            <table class="table table-striped table-hover table-bordered table-condensed" id="contestHisList">
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
                <tbody>
                </tbody>
                </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/contests.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/contests.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/contests.js"></script>
<% } %>
</body>
</html>