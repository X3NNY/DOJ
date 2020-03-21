<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/clear.css" type="text/css" charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/style_003.css" type="text/css" charset="utf-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/roundbox.css" type="text/css" charset="utf-8">
    <script src="${pageContext.request.contextPath}/static/js/highcharts/highcharts.js"></script>
</head>

<body>
<%@include file="navbar.jspf" %>
<style>
    .bar {
        width:250px;
        height:7px;
        border:1px solid #53d242;
    }
    #scoreBar {
        background:#6c0;
        height:100%;
        width:0;
        font-size:0px;
    }
    #index ul {
        margin: 0;
        padding: 0;
    }

    .userbox .info li {
        margin: 0.5em 0 1em 0;
    }

    #navigation ul{
        list-style: none;
        padding: 0;
        margin: 0;
        font-size: 15px;
        position: relative;
        height: 40px;
    }
    #navigation ul>li{
        float: left;
    }
    #navigation ul>li>a {
        margin-top: 20px;
    }
    #navigation ul>li a span {
        line-height: 40px;
        padding: 0 20px;
        display: block;
    }
    #collet tbody>tr>td>ul>li:active a{
        color: #23527c;
    }

    #collet tbody>tr>td>ul>li{
        color: black;
        list-style: disclosure-closed;
        font-size: large;
        padding: 0.5em 0;
    }

    #collectInfo thead th {
        text-align: center;
    }
</style>
<div class="container-fluid" style="margin-top: 5%;">

    <div class="panel panel-default" id="navigation">
        <ul>
            <li>
                <a href="#index" data-toggle="tab"><span>主页</span></a>
            </li>
            <li>
                <a href="#statistics" data-toggle="tab" onclick="loadStatistics()"><span>统计</span></a>
            </li>
            <li>
                <a href="#blog" data-toggle="tab" onclick="loadBlog()"><span>博客</span></a>
            </li>
            <li>
                <a href="#team" data-toggle="tab" onclick="loadTeam()"><span>团队</span></a>
            </li>
            <li>
                <a href="#collet" data-toggle="tab" onclick="loadCollet()"><span>收藏</span></a>
            </li>
            <li>
                <a href="#setting" data-toggle="tab" onclick="loadSetting()"><span>设置</span></a>
            </li>
        </ul>
    </div>
    <div class="tab-content">
        <div class="tab-pane active" id="index">
            <div class="panel panel-primary" style="background-size: 100%;">
            <div class="roundbox " style="padding:1em 1em 0 1em;">
                <div class="userbox">
                    <div class="title-photo">
                        <div style="float:right;border:1px solid #B9B9B9;min-height:150px;position: relative;margin-bottom: 2em;">
                            <div>
                                <div style="min-height:200px;">
                                    <img style="margin:auto;vertical-align:middle;display:inline;width: 250px;height: 250px;" id="userImage" src=""  onerror="this.src='${pageContext.request.contextPath}/static/img/default.jpg'"/>

                                    <a href="#modal-container-601910" data-toggle="modal" style="display: none;" id="hidden-a"></a>
                                    <div class="modal fade" id="modal-container-601910"  aria-labelledby="myModalLabel" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                    <h4 class="modal-title" id="myModalLabel">
                                                        更换头像
                                                    </h4>
                                                </div>
                                                <form style="margin:0px" class="form-horizontal" id="form1" enctype="multipart/form-data" onsubmit="return false;">
                                                    <div class="form-group row">
                                                        <label class="col-sm-2 control-label">上传头像</label>
                                                        <div class="col-sm-10">
                                                            <input type="file" class="form-control" name="datafile">
                                                        </div>
                                                    </div>
                                                </form>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                    <button type="button" class="btn btn-primary" onclick="uploadImage()" data-dismiss="modal">提交</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <div class="text-center">
                                <div class="bar">
                                    <div id="scoreBar">
                                    </div>
                                </div>
                                <span id="scoreDisplay"></span>
                            </div>
                        </div>
                    </div>

                    <div class="info">
                        <h1 id="usernameS">
                        </h1>
                        <ul>
                            <li>
                                <b>UID:</b>
                                <span id="uid"></span>
                            </li>
                            <li>
                                <b>Rating:

                                <span style="font-weight:bold;" id="userRating"></span> <span class="smaller">
                                        (max. <span style="font-weight:bold;" id="userMaxRating"></span>)</span></b></li>
                            <li>
                                <b>个人说明:</b> <font id="userNote"></font>
                            </li>
                            <li>
                                <b>学校:</b> <font id="userSchool"></font>
                            </li>
                            <li>
                                <b>注册时间: </b> <font id="registerDate"></font>
                            </li>
                            <li>
                                <b id="userCntInfo"></b>
                            </li>
                            <li>
                                <b>上次登陆:</b>
                                <span class="format-humantime" id="lastVisitDate"></span>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            </div>
            <div id="rating">
                <div class="panel panel-primary">
                    <div class="panel-heading">Rating</div>
                <div id="userContestInfo" style="min-width:700px;height:400px" data-highcharts-chart="1">
                </div>
                </div>
            </div>
        </div>
        <div class="tab-pane" id="statistics">
            <div class="panel panel-info">
                <div class="message" id="picMessage1" style="min-width:400px;height:300px">
                </div>
            </div>
            <div class="panel panel-info">
                <div class="message" id="picMessage2" style="min-width:400px;height:300px">
                </div>
            </div>
        </div>
        <div class="tab-pane" id="blog">
            <div class="panel panel-primary">
                <div class="panel-heading">

                </div>
                <div class="panel-body" style="padding: 5px">
                    <front style="float: left">
                        <div class="btn-toolbar" >
                            <div class="btn-group"  id="blogPage">
                            </div>
                        </div>
                    </front>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th style="text-align: center;">Title</th>
                        <th style="text-align: center;">Author</th>
                        <th style="text-align: center;">Date</th>
                        <th style="text-align: right;">#&nbsp;&nbsp;&nbsp;&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody id="blogList">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="tab-pane" id="team">

        </div>
        <div class="tab-pane" id="collet">
            <div class="panel panel-primary" style="height: 500px;">
                <div class="panel-heading">
                    收藏
                </div>
                    <table style="width: 100%;">
                        <tbody>
                            <tr>
                                <td style="width: 160px;border-right: 1px solid #e8e8e8;min-height: 100%;vertical-align: top;">
                                    <ul style="padding-top: 30px;">
                                        <li>
                                            <a href="javascript:loadProCollect(1)">题目收藏</a>
                                        </li>
                                        <li>
                                            <a href="javascript:loadConCollect(1)">比赛收藏</a>
                                        </li>
                                        <li>
                                            <a href="javascript:loadStaCollect(1)">评测收藏</a>
                                        </li>
                                        <li>
                                            <a href="javascript:loadBloCollect(1)">博客收藏</a>
                                        </li>
                                    </ul>
                                </td>
                                <td id="collectInfo">
                                    <h3 style="text-align: center;padding: 0 0;">题目收藏</h3>
                                    <br />
                                    <table class="table table-hover table-condensed table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>C</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody style="text-align: center">
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </tbody>
                    </table>
            </div>
        </div>
        <div class="tab-pane" id="setting">
            <a href="#submitBGP" data-toggle="modal"><button type="button" class="btn btn-default btn-info">封面上传</button></a>
            <a href="#submitDIY" data-toggle="modal"><button type="button" class="btn btn-default btn-info">DIY</button></a>
            <a href="#chooseAchi" data-toggle="modal" onclick="loadAchi()"><button type="button" class="btn btn-default btn-info">选择展示成就</button></a>
            <div class="modal fade" id="submitBGP" aria-labelledby="myModalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel1">
                                更换封面
                            </h4>
                        </div>
                        <form style="margin:0px" id="form2" class="form-horizontal" enctype="multipart/form-data" onsubmit="return false;">
                            <div class="form-group row">
                                <p class="col-md-offset-2">建议图片尺寸至少为1800px*600px。</p>
                                <label class="col-sm-2 control-label">上传封面</label>
                                <div class="col-sm-10">
                                    <input type="file" class="form-control" name="datafile">
                                </div>
                            </div>
                        </form>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="uploadImage2()" data-dismiss="modal">提交</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="submitDIY" aria-labelledby="myModalLabel2" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel2">
                                上传自定义CSS，JS文件
                            </h4>
                        </div>
                        <form style="margin:0px" class="form-horizontal" enctype="multipart/form-data" action="/api/user/upload/staticfile" method="post" target="hidden-if">
                            <div class="form-group row">
                                <p class="col-md-offset-2">单个文件最大为50kb。文件名请参考DOJ使用手册中用户DIY一项</p>
                                <label class="col-sm-2 control-label">上传文件</label>
                                <div class="col-sm-10">
                                    <input type="file" class="form-control" name="datafile">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-2 ">
                                    <input class="submit btn btn-default" type="submit" value="submit" onclick="sleep(500,function(){location.href=location.href;});">
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div class="modal fade" id="chooseAchi" aria-labelledby="myModalLabel3" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel3">
                                选择要展示的成就
                            </h4>
                        </div>
                        <div class="modal-body">
                            当前显示效果：<span></span>
                            <table class="table table-hover table-condensed table-striped">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>显示效果</th>
                                    <th>描述</th>
                                    <th>种类</th>
                                    <th>获取时间</th>
                                    <th>有效期</th>
                                    <th>#</th>
                                </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/userinfo.min.js"></script>
<%@include file="footer.jspf" %>
</body>
</html>