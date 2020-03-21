<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/contest.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/hlight/an-old-hope.css">
</head>
<body>
<%@include file="navbar.jspf" %>
<%
    user = (User) session.getAttribute("user");
    if (user==null) {
        response.sendRedirect("/userlogin.jsp");
    }
%>
<style>
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
        min-height: 60px;
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
<div class="container-fluid" id="Contest" style="margin-top: 5%;">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<h3 class="text-center" id="contestTitle">
			</h3>

			<div class="progress progress-striped active">
				<div class="progress-bar progress-success" style="width: 0%;">
				</div>
            </div>
            <div style="text-align:center"><text id="contestTime"></text>
            </div>
           <a id="modal-601910" href="#modal-container-601910"  class="btn" data-toggle="modal">提问</a>
            <div style="float:right;display: none;" id="editContestInfo">[<a href="javascript:editContestInfo()">修改比赛</a>] </div>
           <div class="modal fade" id="modal-container-601910"  aria-labelledby="myModalLabel" aria-hidden="true">
               <div class="modal-dialog">
                   <div class="modal-content">
                       <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                           <h4 class="modal-title" id="myModalLabel">
                               提问
                           </h4>
                       </div>
                       <div class="modal-body">
                            Problem：<select class="form-control" id="questionSelect">
                                <option value="-1" selected="selected">For this contest</option>
                            </select>
                            <br />
                            Question：<textarea id="questionText" class="form-control" cols="80" rows="15" id="codeinput"
                                placeholder="Put your code here"></textarea>
                       </div>
                       <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="askQuestion()">提交</button>
                       </div>
                   </div>
               </div>
           </div>
			<div class="tabbable" id="tabs-425272">
				<ul class="nav nav-tabs">
					<li class="active">
						 <a href="#Home" onclick="javascript:onChange('#Home')" data-toggle="tab">主页</a>
					</li>
					<li>
						 <a href="#P" onclick="javascript:onChange('#P0')" data-toggle="tab">题目</a>
                    </li>
                    <li>
                        <a href="#Submit" onclick="javascript:onChange('#Submit')" data-toggle="tab">提交</a>
                    </li>
                    <li>
                        <a href="#Status" onclick="javascript:onChange('#Status')" data-toggle="tab">评测</a>
                    </li>
                    <li>
                        <a href="#Rank" onclick="javascript:onChange('#Rank')" data-toggle="tab">排名</a>
                    </li>
                    <li>
                        <a href="#Discuss" onclick="javascript:onChange('#Discuss')" data-toggle="tab">问答</a>
                    </li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="Home">
                        <br />
                        <br />
                        <div class="row clearfix">
                            <div class="col-md-1 column"></div>
                            <div class="col-md-10 column">
                                <br />
                                <p style="font-size: large;">
                                     <em>比赛时间：&nbsp;</em> <b style="font-size: large;" id="startTime">2020-01-01 00:00:00</b> ～ <b id="endsTime">2020-02-01 00:00:00</b>
                                </p>
                                <br />
                                <p style="font-size: large;float: left;">
                                     <em>比赛描述：&nbsp;</em> <div id="contestDesc" style="font-size: large;">欢迎参赛，有问题联系<strong>Admin</strong></div>
                                </p>
                                <br />
                                <table class="table table-striped table-hover" id="problemList">
                                    <thead>
                                        <tr>
                                            <th>
                                                ID
                                            </th>
                                            <th>
                                                Title
                                            </th>
                                            <th>
                                                Radio
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
					</div>
					<div class="tab-pane" id="P">
						<div class="row clearfix">
                            <div class="col-md-1 column"></div>
                            <div class="col-md-10 column">
                                <br />
                                <ul class="nav nav-pills" id="problems">
                                </ul>
                                <br />
                                <button id="toProButton" style="display: none;" class="btn btn-default" type="button"><em class="glyphicon glyphicon-align-justify"></em>转到题库</button>
                                <br />
                                <div class="row clearfix">
                                    <div class='col-md-12'>
                                        <h1 id="problemTitle" style='text-align:center'>

                                        </h1>
                                        <div class='row'>
                                            <div style='text-align:center'>
                                                TimeLimit: <b id="problemTimeLimit"></b>ms&nbsp;&nbsp;MemoryLimit:<b id="problemMemoryLimit"></b>MB
                                            </div>
                                            <div style='text-align:center' Id="problem">
                                                Type: <b id="problemType"></b>&nbsp;&nbsp;Special:<b id="problemSpecial"></b>
                                            </div>
                                        </div>
                                        <div class='row'>
                                            <div id="problemState" class='col-xs-12 col-xs-offset-0 '
                                                 style='padding-top:8px;padding-bottom:8px'>
                                                <font color='black'><span
                                                        class='glyphicon glyphicon-minus'></span>未提交</font>
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
                                        <div class='panel panel-default' id="problemHint">
                                            <div class='panel-heading'>
                                                Hint
                                            </div>
                                            <div class='panel-body'>
                                            </div>
                                        </div>
                                    </div>
                                </div>
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
                                    Problem：<select class="form-control" id="problemSelect">
                                    </select>
                                    <br />
                                    Language：<select class="form-control" id="problemLang">
                    
                                        <option value="GCC">GCC</option>
                                        <option value="G++">G++</option>
                                        <option value="JAVA">JAVA</option>
                                        <option value="Python">Python</option>
                                    </select>
                                    <br />
                                    Code：<textarea id="problemCode" class="form-control" cols="80" rows="15" placeholder="Put your code here"></textarea>
                                    <br />
                                    <input type="button" class="form-control" value="Submit" onclick="javascript:submit()">
                                    <br />
                                    <br />
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="Status">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                                <br />
                                <br />
                                <div class="panel-body" style="padding:5px">
                                    <front style="float: left">
                                        <div class="btn-toolbar" >
                                            <a class="btn btn-default btn-sm" href="javascript:toPage(1)">刷新</a>
                                            <div class="btn-group"  id="statusPage">
                                            </div>
                                        </div>
                                    </front>
                                    <front style="float: right">
                                        <form style="margin:0px" class="form-inline" action="javascript:statusSearch();">
                                            <div class="form-group">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon">
                                                        用户
                                                    </span>
                                                    <input type="text" class="form-control " id="statusUser" style="width:100px" value="">
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon">
                                                        pid
                                                    </span>
                                                    <select class="form-control" id="statusProblemSelect">
                                                        <option value="-1" selected="selected">
                                                            All
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon">
                                                        结果
                                                    </span>
                                                    <select class="form-control" id="statusResultSelect">
                                                        <option value="-1" selected="selected">
                                                            All
                                                        </option>
                                                        <option value="0">
                                                            AC
                                                        </option>
                                                        <option value="1">
                                                            PE
                                                        </option>
                                                        <option value="2">
                                                            TLE
                                                        </option>
                                                        <option value="3">
                                                            MLE
                                                        </option>
                                                        <option value="4">
                                                            WA
                                                        </option>
                                                        <option value="5">
                                                            RE
                                                        </option>
                                                        <option value="6">
                                                            OLE
                                                        </option>
                                                        <option value="8">
                                                            SE
                                                        </option>
                                                        <option value="9">
                                                            CE
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <div class="input-group input-group-sm">
                                                    <span class="input-group-addon">
                                                        语言
                                                    </span>
                                                    <select class="form-control" id="statusLangSelect">
                                                        <option value="-1" selected="selected">
                                                            All
                                                        </option>
                                                        <option value="GCC">
                                                            GCC
                                                        </option>
                                                        <option value="G++">
                                                            G++
                                                        </option>
                                                        <option value="Python">
                                                            Python
                                                        </option>
                                                        <option value="JAVA">
                                                            JAVA
                                                        </option>
                                                    </select>
                                                </div>
                                            </div>
                                            <a class="btn btn-default btn-sm" href="javascript:statusSearch();">
                                                筛选
                                            </a>
                                        </form>
                                    </front>
                                </div>
                                <br />
                                <table class="table table-striped table-hover table-condensed" id="statusList">
                                    <thead>
                                        <tr>
                                            <th>
                                                ID
                                            </th>
                                            <th>
                                                Name
                                            </th>
                                            <th>
                                                Problem
                                            </th>
                                            <th>
                                                Result
                                            </th>
                                            <th>
                                                Lang
                                            </th>
                                            <th>
                                                TimeUsed
                                            </th>
                                            <th>
                                                MemoryUsed
                                            </th>
                                            <th>
                                                Size
                                            </th>
                                            <th>
                                                Date
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                                <a id="showCodeBtn" href="#showCode" data-toggle="modal" style="display: none;"></a>
                                <div class="modal fade" id="showCode"  aria-labelledby="myModalLabel1" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content" style="min-width: 700px;">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title" id="myModalLabel1">
                                                    查看代码
                                                </h4>
                                            </div>
                                            <pre class="hljs">
                                                    <code>
                                                    </code>
                                                </pre>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="Rank">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                                <br />
                                <br />
                                <table class="table table-bordered table-hover table-condensed" id="rankList">
                                    <thead>
                                        <tr>
                                            <th>
                                                Rank
                                            </th>
                                            <th>
                                                Name
                                            </th>
                                            <th>
                                                Sum
                                            </th>
                                            <th>
                                                *
                                            </th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="tab-pane" id="Discuss">
                        <div class="row clearfix">
                            <div class="col-md-12 column">
                                <br />
                                <br />
                                <div id="discussList">

                                </div>
                                <a href="#replyDis" data-toggle="modal" style="display: none" id="hidden-a-rp"></a>
                                <div class="modal fade" id="replyDis"  aria-labelledby="myModalLabel2" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content" style="min-width: 700px;">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                                <h4 class="modal-title" id="myModalLabel2">
                                                    回答
                                                </h4>
                                            </div>
                                            <textarea placeholder="输入回复内容... " class="form-control" cols="10" rows="6"></textarea>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                                <button type="button" class="btn btn-primary" onclick="replyDis()" data-dismiss="modal">回复</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="${pageContext.request.contextPath}/static/js/highlight.pack.js"></script>
<script src="${pageContext.request.contextPath}/static/js/contest.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/contest.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/contest.js"></script>
<% } %>
</body>
</html>
