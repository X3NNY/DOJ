<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<%@include file="header.jspf" %>
	<title>Dreamer Online Judge</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/hlight/an-old-hope.css">
    <script src="${pageContext.request.contextPath}/static/js/highlight.pack.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/highcharts/highcharts.js"></script>
<%--    <script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>--%>
<%--    <script src="https://code.highcharts.com.cn/highcharts/modules/data.js"></script>--%>
<%--    <script src="https://code.highcharts.com.cn/highcharts/modules/series-label.js"></script>--%>
<%--    <script src="https://code.highcharts.com.cn/highcharts/modules/oldie.js"></script>--%>
    <script src="${pageContext.request.contextPath}/static/js/highcharts/themes/dark-unica.js"></script>
</head>
<body>
<%@include file="navbar.jspf" %>
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
    front,text {
        color: #ffffff;
    }
</style>
<canvas id="canvas"></canvas>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix" id="Notify" style="display: none;">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <strong>系统公告：</strong><span>感谢大家使用DOJ。</span>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-9 column">
            <div class="panel panel-primary" id="Notice">
                <div class="panel-heading">
                    <font>公告</font>
                    <front></front>
                </div>
                <div class="panel-body" style="min-height: 100px;">
                </div>
                <div class="panel-footer">

                </div>
            </div>
            <div class="panel panel-info" id="Contests">
                <div class="panel-heading">
                    近期比赛
                    <front style="float:right;"><a href="contests.jsp"><span class="glyphicon glyphicon-arrow-right" style="color: #fff;"></span></a></front>
                </div>
                <table class="table table-striped table-hover table-condensed">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>标题</th>
                            <th>开始时间</th>
                            <th>状态</th>
                        </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
            <div class="panel panel-info" id="Problems">
                <div class="panel-heading">
                    最新题目
                    <front style="float:right;"><a href="problems.jsp"><span class="glyphicon glyphicon-arrow-right" style="color: #fff;"></span></a></front>
                </div>
                    <table class="table table-striped table-hover table-condensed">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th style="text-align: center">标题</th>
                                <th style="text-align: center">AC率</th>
                                <th style="text-align: center">作者</th>
                            </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
            </div>
            <div class="panel panel-info">
                <div class="message" id="picMessage" style="min-width:400px;height:400px">

                </div>
            </div>
        </div>
        <div class="col-md-3 column">
            <div class="panel panel-default" id="Thing">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        DOJ
                    </h3>
                </div>
                <div class="panel-body">

                </div>
            </div>
            <div class="panel panel-primary" id="Top10">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        Top 10
                        <front style="float:right;"><a href="rank.jsp"><span class="glyphicon glyphicon-arrow-right" style="color: #fff;"></span></a></front>
                    </h3>
                </div>
                    <table class="table table-striped table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>用户名</th>
                            <th style="text-align: right;">Rating</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
            </div>
            <div class="panel panel-info" id="Search">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        查找用户
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="input-group input-group-sm">
                        <input type="text" class="form-control" style="" value="">
                        <span class="input-group-addon" onclick="search()">
                            <a>搜索</a>
                        </span>
                    </div>
                </div>
            </div>
            <div class="panel panel-info" id="Blogs">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        最新博客
                        <front style="float:right;"><a href="blogs.jsp"><span class="glyphicon glyphicon-arrow-right" style="color: #fff;"></span></a></front>
                    </h3>
                </div>
                    <table class="table table-striped table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th style="text-align: center">名称</th>
                            <th style="text-align: right;">作者</th>
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/index.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/index.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/index.js"></script>
<% } %>
</body>
</html>