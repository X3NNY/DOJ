<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Beta - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<style>
    /*css reset */
    body,p,div,ol,ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,form,input,iframe,nav {
        margin: 0 0;
        padding: 0;
    }
    html,body {
        width: 100%;
        height: 100%;
    }
    body {
        font: 14px Microsoft YaHei;
        -webkit-text-size-adjust:100%;
        -moz-user-select: none;
        -webkit-user-select: none;
        user-select: none;
        position: relative;
        background: #000;
    }
    #canvas {
        width: 100%;
        height: 100%;
        display: block;
        z-index: -1;
        position: fixed;
        opacity: .8;
    }
</style>
<%@include file="navbar.jspf" %>
<canvas id="canvas"></canvas>
    <div class="container-fluid" style="margin-top: 5%;">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="alert alert-success alert-dismissable">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                    <h4>
                        注意!
                    </h4>
                    这里是我们为未来开放的一些测试功能，它们不一定会在未来的版本中保留，您可以在这里测试使用它们。
                </div>
                <a href="${pageContext.request.contextPath}/Beta/paste.jsp"><button type="button" class="btn btn-default btn-info">Code Paste</button></a>
                <a href="${pageContext.request.contextPath}/Beta/custom.jsp"><button type="button" class="btn btn-default btn-info">Custom Test</button></a>
            </div>
        </div>
    </div>
<%--<script src="${pageContext.request.contextPath}/static/js/Beta.js"></script>--%>
<%@include file="footer.jspf" %>
</body>
</html>
