<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Hack - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<style>
    span a{
        color: white;
    }
</style>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="panel-body" style="padding:5px">
                <front style="float: left">
                    <div class="btn-toolbar" >
                        <a class="btn btn-default btn-sm" href="javascript:toPage(1)"><span class="glyphicon glyphicon-refresh"></span>刷新</a>
                        <div class="btn-group"  id="hackPage">

                        </div>
                    </div>
                </front>
                <front style="float: right">
                    <form style="margin:0px" class="form-inline" action="javascript:hackSearch();">
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    Hacker
                                </span>
                                <input type="text" class="form-control " id="hackUser" style="width:100px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    提交者
                                </span>
                                <input type="text" class="form-control " id="commitUser" style="width:100px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    pid
                                </span>
                                <input type="text" class="form-control " id="hackProblemSelect" style="width:50px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    结果
                                </span>
                                <select class="form-control" id="hackResultSelect">
                                    <option value="-1" selected="selected">
                                        All
                                    </option>
                                    <option value="2">
                                        Success
                                    </option>
                                    <option value="1">
                                        Failed
                                    </option>
                                </select>
                            </div>
                        </div>
                        <a class="btn btn-default btn-sm" href="javascript:hackSearch();">
                            <span class="glyphicon glyphicon-filter"></span>筛选
                        </a>
                    </form>
                </front>
            </div>
            <br />
            <table class="table table-striped table-hover table-condensed" id="hackList">
                <thead>
                <tr>
                    <th>
                        ID
                    </th>
                    <th>
                        SID
                    </th>
                    <th>
                        Problem
                    </th>
                    <th>
                        Hacker
                    </th>
                    <th>
                        Committer
                    </th>
                    <th>
                        Result
                    </th>
                    <th>
                        Date
                    </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/hacks.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/hacks.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/hacks.js"></script>
<% } %>
</body>
</html>