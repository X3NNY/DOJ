<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>Hack信息 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>

<div class="container-fluid" style="margin-top: 5%">
    <div>
        <table class="table table-bordered table-text-center">
            <thead>
            <tr>
                <th>ID</th>
                <th>SID</th>
                <th>Problem</th>
                <th>Hacker</th>
                <th>Committer</th>
                <th>Result</th>
                <th>Date</th>
            </tr>
            </thead>
            <tbody></tbody>
        </table>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">
            Code
        </div>
        <div class="panel-body">
            <pre class="hljs">
                <code>
                </code>
            </pre>
        </div>
    </div>
    <div class="panel panel-info">
        <table id="input">
            <tbody>
            <tr style="width: 160px;vertical-align: center;min-height: 400px">
                <td style="min-width: 90px;padding: 10px"><b>标准输入：</b></td>
                <td style="padding: 10px;width: 100%;">
                    <pre></pre>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="panel panel-info">
        <table id="output">
            <tbody>
            <tr style="width: 160px;vertical-align: center;min-height: 400px">
                <td style="min-width: 90px;padding: 10px"><b>标准输出：</b></td>
                <td style="padding: 10px;width: 100%;">
                    <pre></pre>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="panel panel-info">
        <table id="answer">
            <tbody>
                <tr style="width: 160px;vertical-align: center;min-height: 400px">
                    <td style="min-width: 90px;padding: 10px"><b>用户输出：</b></td>
                    <td style="padding: 10px;width: 100%;">
                        <pre></pre>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <input style="display:none;" value="<%=session.getId()%>" id="token">
</div>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/hlight/an-old-hope.css">
<script src="${pageContext.request.contextPath}/static/js/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/static/js/hack.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/hack.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/hack.js"></script>
<% } %>
</body>
</html>