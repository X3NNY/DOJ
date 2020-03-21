<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>代码信息 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>

<div class="container-fluid" style="margin-top: 5%">
    <div>
        <table class="table table-bordered table-text-center">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>题目</th>
                    <th>用户</th>
                    <th>结果</th>
                    <th>耗时</th>
                    <th>内存</th>
                    <th>语言</th>
                    <th>大小</th>
                    <th>时间</th>
                </tr>
            </thead>
            <tbody></tbody>
        </table>
        <div id="upHackData" style="display: none">
            <span><p style="text-align: center">这份代码我觉得有问题！<button class="btn-sm btn-danger" onclick="$('#newhack').toggle('fast');">Hack</button></p></span>
            <iframe id="hidden-if" style="display: none"></iframe>
            <div id="newhack" style="display: none;" class="bot-buffer-md">
                <div class="panel panel-info">
                    <div class="panel-body">
                        <form style="margin:0px" class="form-horizontal" enctype="multipart/form-data" action="<%=judgeUrl%>/api/hack/new/file" method="post" target="hidden-if">
                            <input type="hidden" value="" name="sid" />
                            <input type="hidden" value="<%=session.getId()%>" name="token" />
                            <div class="form-group row">
                                <label class="col-sm-2 control-label">上传数据</label>
                                <div class="col-xs-10">
                                    <input type="file" class="form-control" multiple="multiple" name="datafile">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-xs-10 col-xs-offset-2">
                                    <input class="submit btn btn-default" type="submit" value="submit" onclick="narn('success','上传数据成功')">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-primary">
        <div class="panel-heading">
            Code
            <front style="float: right;">
                <a style="color: blue;" id="submitCollection" href="javascript:collect()"><span
                        class='glyphicon glyphicon-star-empty'></span>收藏</a>
            </front>
        </div>
        <div class="panel-body">
            <pre class="hljs">
                <code>
                </code>
            </pre>
        </div>
    </div>
</div>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/hlight/an-old-hope.css">
<script src="${pageContext.request.contextPath}/static/js/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/static/js/code.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/code.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/code.js"></script>
<% } %>
</body>
</html>