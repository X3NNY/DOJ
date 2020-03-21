<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>消息 - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<%
    if (user == null) {
        response.sendRedirect("/userlogin.jsp");
    }
%>
<style>
    #messageInfo td, #messageInfo th {
        text-align: center;
    }
    tbody>tr>td>ul>li:active a{
        color: #23527c;
    }

    tbody>tr>td>ul>li a{
        color: black;
    }
</style>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="panel panel-info" style="min-height: 500px;">
                <div class="panel-heading">
                    消息通知
                </div>
                <a href="javascript:changeAllState()" style="float:right;">一键全读</a>
                <table style="width: 100%;">
                    <tbody>
                        <tr>
                            <td style="width: 160px;border-right: 1px solid #e8e8e8;min-height: 100%;vertical-align: top;" >
                                <ul style="font-size: large;padding-top: 30px;">
                                    <li class="active" style="padding: 0.5em 0;">
                                        <a href="javascript:loadSystemMessage()">系统消息</a>
                                    </li>
                                    <li style="padding: 0.5em 0;">
                                        <a href="javascript:loadAboutMessage()">提到我的</a>
                                    </li>
                                    <li style="padding: 0.5em 0;">
                                        <a href="javascript:loadFriendMessage()">好友留言</a>
                                    </li>
                                    <li style="padding: 0.5em 0;">
                                        <a href="javascript:loadSendMessage()">我发送的</a>
                                    </li>
                                    <li style="padding: 0.5em 0;">
                                        <a href="#newMsg" data-toggle="modal">新建留言</a>
                                    </li>
                                </ul>
                            </td>
                            <td id="messageInfo">
                                <h3 style="text-align: center;padding: 0 0;">系统消息</h3>
                                <hr />
                                <table class="table table-hover table-condensed table-striped">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Title</th>
                                            <th>User</th>
                                            <th>Date</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div id="pageList" style="float:right;"></div>
                <br />
                <a id="showMsgBtn" href="#showMsg" data-toggle="modal" style="display: none;"></a>
                <div class="modal fade" id="showMsg"  aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="min-width: 700px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 class="modal-title" id="myModalLabel">
                                    内容
                                </h4>
                            </div>
                            <div class="modal-body">
                                <div id="msgContent"></div>
                                <hr />
                                <textarea id="editor" placeholder="输入回复内容... " class="form-control" cols="10" rows="6"></textarea>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary" onclick="replyMsg()" data-dismiss="modal" id="replyBtn">回复</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal fade" id="newMsg"  aria-labelledby="myModalLabel1" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content" style="min-width: 700px;">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 class="modal-title" id="myModalLabel1">
                                    内容
                                </h4>
                            </div>
                            <input type="text" name="name" class="form-control" placeholder="输入用户名或UID" />
                            <hr />
                            <input type="text" name="title" class="form-control" placeholder="输入标题" />
                            <textarea placeholder="输入回复内容... " class="form-control" cols="10" rows="6"></textarea>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary" onclick="newMsg()" data-dismiss="modal">回复</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/usermessage.min.js"></script>
</body>
</html>