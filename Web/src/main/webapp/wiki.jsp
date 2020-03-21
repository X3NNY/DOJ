<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Wiki - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <ul class="breadcrumb" id="Path">
                <li>
                    <a href="javascript:loadWiki()">Wiki</a>
                </li>
            </ul>
            <div class="jumbotron" id="greetingDiv">
                <h1>
                    Hello, Dreamer
                </h1>
                <p>
                    您可以在这里查看有关算法和我们的介绍，同时也期待您能够参与到建设它的过程。
                </p>
                <p>
                    <a class="btn btn-primary btn-large" href="javascript:loadWiki()">Learn more</a>
                </p>
            </div>
            <div class="row clearfix" id="Wiki" style="display: none;">
                <div class="col-md-3 column">
                </div>
                <div class="col-md-3 column">
                </div>
                <div class="col-md-3 column">
                </div>
                <div class="col-md-3 column">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/wiki.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/wiki.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/wiki.js"></script>
<% } %>
</body>
</html>
