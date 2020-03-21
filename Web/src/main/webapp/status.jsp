<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>提交 - DOJ</title>
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
            <div class="panel panel-default">
                <div class="panel-heading">
                    评测
                </div>
                <div class="panel-body" style="padding:5px">
                <front style="float: left">
                    <div class="btn-toolbar" >
<%--                        <a class="btn btn-default btn-sm" href="javascript:toPage(1)"><span class="glyphicon glyphicon-refresh"></span>刷新</a>--%>
                        <div class="btn-group"  id="statusPage">

                        </div>
                    </div>
                </front>
                <front style="float: right">
                    <form style="margin:0px" class="form-inline" action="javascript:statusSearch();">
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    用户
                                </span>
                                <input type="text" class="form-control " id="statusUser" style="width:100px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    pid
                                </span>
                                <input type="text" class="form-control " id="statusProblemSelect" style="width:50px" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    结果
                                </span>
                                <select class="form-control" id="statusResultSelect">
                                    <option value="-1" selected="selected">
                                        All
                                    </option>
                                    <option value="0">
                                        AC
                                    </option>
                                    <option value="1">
                                        PE
                                    </option>
                                    <option value="2">
                                        TLE
                                    </option>
                                    <option value="3">
                                        MLE
                                    </option>
                                    <option value="4">
                                        WA
                                    </option>
                                    <option value="5">
                                        RE
                                    </option>
                                    <option value="6">
                                        OLE
                                    </option>
                                    <option value="7">
                                        PJ
                                    </option>
                                    <option value="8">
                                        SE
                                    </option>
                                    <option value="9">
                                        CE
                                    </option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    语言
                                </span>
                                <select class="form-control" id="statusLangSelect">
                                    <option value="" selected="selected">
                                        All
                                    </option>
                                    <option value="GCC">
                                        GCC
                                    </option>
                                    <option value="G++">
                                        G++
                                    </option>
                                    <option value="JAVA">
                                        JAVA
                                    </option>
                                    <option value="Python">
                                        Python
                                    </option>
                                </select>
                            </div>
                        </div>
                        <a class="btn btn-default btn-sm" href="javascript:statusSearch();">
                            <span class="glyphicon glyphicon-filter"></span>筛选
                        </a>
                    </form>
                </front>
            </div>
                <table class="table table-striped table-hover table-condensed" id="statusList">
                <thead>
                    <tr>
                        <th>
                            ID
                        </th>
                        <th style="text-align: center">
                            Name
                        </th>
                        <th style="text-align: center">
                            Problem
                        </th>
                        <th>
                            Result
                        </th>
                        <th style="text-align: center">
                            Lang
                        </th>
                        <th>
                            TimeUsed
                        </th>
                        <th>
                            MemoryUsed
                        </th>
                        <th style="text-align: center">
                            Date
                        </th>
                        <th>
                            Size
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/status.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/status.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/status.js"></script>
<% } %>
</body>
</html>