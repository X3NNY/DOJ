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
<style>
    #blogInfo {
        vertical-align: top;
        padding: 10px;
        height: 100%;
        text-align: center;
        width: 100px;
    }
    #blogText {
        vertical-align: top;
        padding: 15px;
        word-break: break-all;
    }
    #blogInfo,.replyInfo{
        background-color: #bfc8ef;
        width: 100px;
        height: 100%;
        padding: 5px;
    }
    #blogInfo img,.replyInfo img{
        width: 100px;
        height: 100px;
        border-radius: 50%;
    }
    .reply {
        background-color: #f7f8fa;
        padding: 10px 20px;
    }
    .replyRight img {
        width: 60px;
        height: 60px;
        border-radius: 50%;
    }
    .replyText,#blogText {
        min-height: 100px;
        margin-right: 0px;
    }
    .replyLink {
        float: left;
        margin: 10px 20px;
        padding: 5px;
        border: #ccc 1px solid;
        background-color: white;
    }
    .replyRight {
        vertical-align: top;
        padding: 15px;
        word-break: break-all;
    }
</style>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="col-xs-9">
        <div class="tabbable" id="tabs-474718">
            <ul class="nav nav-tabs">
                <li class="nav-item active">
                    <a class="nav-link" href="#tab1" data-toggle="tab">题目</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active show" href="#Submit" data-toggle="tab">提交</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active show" href="#tab2" data-toggle="tab">讨论区</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active show" href="#tab3" data-toggle="tab">数据</a>
                </li>
            </ul>
            <div class="tab-content">
                <div class="tab-pane active" id="tab1">
                    <div class='row'>
                        <div class='col-xs-12'>
                        <h1 style='text-align:center' id="problemTitle">
                            title
                        </h1>
                            <div class='row'>
                                <div style='text-align:center'>
                                    TimeLimit: <b id="problemTimeLimit"></b>ms&nbsp;&nbsp;MemoryLimit:<b id="problemMemoryLimit"></b>MB
                                </div>
                                <div style='text-align:center'>
                                    Type: <b id="problemType"></b>&nbsp;&nbsp;Special:<b id="problemSpecial"></b>
                                </div>
                            </div>
                            <div class='row'>
                                <div class='col-xs-12 col-xs-offset-0 '
                                     style='padding-top:8px;padding-bottom:8px'>
                                    <a style="color: black;" id="problemIsSubmit"><span
                                            class='glyphicon glyphicon-minus'></span>未提交</a> |
                                    <a style="color: blue;" id="problemIsCollection" href="javascript:collect()"><span
                                            class='glyphicon glyphicon-star-empty'></span>收藏</a>
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>Problem Description</div>
                                <div class='panel-body' id="problemDesc">
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>
                                    Input
                                </div>
                                <div class='panel-body' id="problemInput">
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>
                                    Output
                                </div>
                                <div class='panel-body' id="problemOutput">
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>
                                    SampleInput
                                </div>
                                <div class='panel-body'><pre class='sample' id="problemSampleInput"></pre>
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>
                                    SampleOutput
                                </div>
                                <div class='panel-body'><pre class='sample' id="problemSampleOutput"></pre>
                                </div>
                            </div>
                            <div class='panel panel-default'>
                                <div class='panel-heading'>
                                    Hint
                                </div>
                                <div class='panel-body' id="problemHint">
                                </div>
                            </div>
                            <br />
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="Submit">
                    <div class="row clearfix">
                        <div class="col-md-1 column"></div>
                        <div class="col-md-10 column">
                            <div style="text-align: left;">
                                <br />
                                <br />
                                Problem：<b id="problemSelect">name</b>
                                <br />
                                Language：<select class="form-control" id="problemLang">
                                    <option>G++</option>
                                    <option>GCC</option>
                                    <option>JAVA</option>
                                </select>
                                <br />
                                Code：<textarea id="problemCode" class="form-control" cols="80" rows="15"
                                    placeholder="Put your code here"></textarea>
                                <br />
                                <input type="button" class="form-control" value="Submit" onclick="proSubmit()">
                                <br />
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="tab2">
                    <div class="row">
                        <div class="col-xs-12" style="padding-top:10px;padding-bottom:10px">
                            <p class="lead text-success">
                                <i><a href="#newProblemDis" data-toggle="modal">点击</a></i>
                                这里发表一篇新评论
                                <div class="modal fade" id="newProblemDis"  aria-labelledby="myModalLabel2" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content" style="min-width: 700px;">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title" id="myModalLabel2">
                                                    提示
                                                </h4>
                                            </div>
                                            <p>您将跳转到发表博客界面，请将博文名置为#P{本题PID}|{博文名}，例如 #P1001|我的题解，即可自动添加至当前题目讨论区。</p>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                <button type="button" class="btn btn-primary" onclick="location.href='/newblog.jsp';" data-dismiss="modal">确定</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </p>
                            <div class="panel panel-info">
                                <div class="panel-heading" id="problemDiscussTitle">
                                    所有评论
                                </div>
                                <div class="panel-body">
                                    <table class="table table-striped table-hover" id="problemDiscuss">
                                        <thead>
                                            <tr>
                                                <td>ID</td>
                                                <td style="text-align: center;">标题</td>
                                                <td style="text-align: center;">作者</td>
                                                <td style="text-align: center;">发表日期</td>
                                                <td style="text-align: right;">#&nbsp;&nbsp;</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                    <div id="problemDisBlog">
                                        <div class="panel panel-primary" style="word-break: break-all;">
                                            <div class="panel-heading" id="blogTitle">
                                            </div>
                                            <table style="width: 100%">
                                                <tbody>
                                                <tr>
                                                    <td id="blogInfo">
                                                        <div>
                                                            <img src="" onerror="this.src='${pageContext.request.contextPath}/static/img/default.jpg'">
                                                        </div>
                                                        <div style="text-align: center;" id="blogUsername">

                                                        </div>
                                                    </td>
                                                    <td class="replyRight">
                                                        <div id="blogText">
                                                        </div>
                                                        <div class="replyLink"><a href="#blogReply" data-toggle="modal" onclick="setReply()">回复</a></div>
                                                    </td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <div class="panel-footer" id="blogFooter">

                                            </div>
                                        </div>
                                        <div id="Commtents"></div>
                                        <div class="modal fade" id="blogReply"  aria-labelledby="myModalLabel1" aria-hidden="true">
                                            <div class="modal-dialog">
                                                <div class="modal-content" style="min-width: 700px;">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                        <h4 class="modal-title" id="myModalLabel1">
                                                            回复
                                                        </h4>
                                                    </div>
                                                    <textarea id="editor" placeholder="在此输入内容..."></textarea>
                                                    <script src="static/js/tinymce/tinymce.min.js"></script>
                                                    <script>
                                                        tinymce.init({
                                                            selector: "#editor",
                                                            plugins: ['code','advlist','autolink','link','lists','preview','autoresize','autosave','charmap','codesample','emoticons','fullscreen','help','hr','image','insertdatetime','link','media','print','searchreplace','table','textpattern','visualblocks','wordcount','formatpainter'],
                                                            toolbar: [
                                                                'code preview | undo redo restoredraft | formatselect fontselect fontsizeselect | forecolor backcolor bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | indent outdent | searchreplace copy cut paste selectall removeformat | bullist numlist table | charmap emoticons codesample hr image media insertdatetime link blockquote subscript superscript | visualblocks wordcount help'

                                                            ],
                                                            custom_undo_redo_levels: 10,
                                                            menubar: false,
                                                            min_height: 500,
                                                            fontsize_formats: '10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                                                            font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                                                            image_caption: true,
                                                            textpattern_patterns: [
                                                                {start: '*', end: '*', format: 'italic'},
                                                                {start: '**', end: '**', format: 'bold'},
                                                                {start: '#', format: 'h1'},
                                                                {start: '##', format: 'h2'},
                                                                {start: '###', format: 'h3'},
                                                                {start: '####', format: 'h4'},
                                                                {start: '#####', format: 'h5'},
                                                                {start: '######', format: 'h6'},
                                                                {start: '1. ', cmd: 'InsertOrderedList'},
                                                                {start: '* ', cmd: 'InsertUnorderedList'},
                                                                {start: '- ', cmd: 'InsertUnorderedList' }
                                                            ],
                                                            images_upload_url: '/api/upload/img',
                                                            images_upload_base_path: '/media/img/',
                                                            language: 'zh_CN',
                                                        })
                                                    </script>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                        <button type="button" class="btn btn-primary" onclick="reply()" data-dismiss="modal">提交</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12" id="comment">

                        </div>
                    </div>
                </div>
                <div class="tab-pane" id="tab3">
                    <div class="alert alert-dismissable alert-info">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                        <h4>
                            注意！
                        </h4> 我们提供题目数据以及特判、交互和STD程序等文件，但请合理利用，感谢支持。
                    </div>
                    <div class="panel panel-info" id="problemDown">
                        <div class="panel-heading">
                            数据文件
                        </div>
                        <div class="panel-body">
                        <%
                            if (user == null) {
                        %>
                        <p class="lead text-warning">
                            请登录后查看！
                        </p>
                        <% } else { %>
                            <table class="table table-striped table-hover">
                                <thead>
                                    <tr>
                                        <td>名称</td>
                                        <td>#</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                    </tr>
                                </tbody>
                            </table>
                         <% } %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class='col-xs-3'>
        <div id="picMessage" style="min-width:300px;height:300px"></div>
        <div class='panel panel-info'>
            <div class='panel-heading'>
                作者
            </div>
            <table class='table'>
                <tbody>
                <tr>
                    <td id="problemAuthor">

                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div class='panel panel-info'>
            <div class='panel-heading'>
                标签
                <front style='float: right'><a href="#modal-container-601910" data-toggle="modal" onclick="loadTags()">
                    添加
                </a></front>
            </div>
            <table class='table' id="problemTag">
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="modal fade" id="modal-container-601910"  aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            添加标签
                        </h4>
                    </div>
                    <div class="modal-body">
                        Tags：
                        <select class="form-control" id="tagSelect">
                            <option value="-1" selected="selected">选择一个标签</option>
                        </select>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="addTag()">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <input style="display:none;" value="<%=session.getId()%>" id="token">
</div>
<script src="${pageContext.request.contextPath}/static/js/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/static/js/problem.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/problem.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/problem.js"></script>
<% } %>
</body>
</html>
