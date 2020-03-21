<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title></title>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-primary">
        <div class="panel-heading">

        </div>
        <div class="panel-body">
            <p></p>
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th>
                            输入数据
                        </th>
                        <th>
                            输出数据
                        </th>
                        <th>
                            用户输出
                        </th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/statusInfo.min.js"></script>
<%@include file="footer.jspf" %>
</body>
</html>