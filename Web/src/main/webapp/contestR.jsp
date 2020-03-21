<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>比赛报名 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-info">
        <div class="panel-heading">
            报名列表
            <front style="float:right;"><a href="#R" data-toggle="modal"></a></front>
        </div>
        <div class="modal fade" id="R"  aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            报名
                        </h4>
                    </div>
                    <p>您确定报名本场比赛吗？</p>
                    <p>报名后在比赛开始前可取消报名，比赛开始后任意时刻进入比赛界面即进入排名！</p>
                    <p>请不要在比赛途中有作弊行为</p>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="submitR()" data-dismiss="modal">提交</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <div id="Rpage"></div>
            <table class="table table-striped table-hover table-bordered table-condensed">
                <thead>
                    <tr>
                        <th>用户名</th>
                        <th style="width: 100px;">Rating</th>
                        <th style="width: 100px;">状态</th>
                        <th style="width: 200px;">时间</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/contestR.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/contestR.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/contestR.js"></script>
<% } %>
</body>
</html>