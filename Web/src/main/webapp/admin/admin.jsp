<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("/userlogin.jsp");
    }
%>

<head>
    <%@include file="../header.jspf" %>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/static/css/admin/style.css'>
    <title>管理界面 - DOJ</title>
</head>
<body>
<input type="text" id="username" style="display: none" value="<%=user==null?"":user.getUsername() %>" />
<input id="token" value="<%=session.getId()%>" style="display: none" />
<div id="wrapper" class="toggled">
    <div class="overlay" style="display: block;"></div>
    <nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper"
         role="navigation">
        <ul class="nav sidebar-nav">
            <li class="sidebar-brand"> <a href="/"> Dreamer OJ </a> </li>
            <li> <a href="javascript:loadAdminMain()"> 信息统计 </a> </li>
            <li> <a href="javascript:loadSettingMain()"> 主页设置 </a> </li>
            <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 题目管理 <span class="caret"> </span></a>
                <ul class="dropdown-menu">
                    <li> <a href="javascript:loadMyProblem();">我的题目</a></li>
                    <li> <a href="javascript:loadAllProblem();">所有题目</a></li>
                    <li> <a href="javascript:loadNewProblem();">新增题目</a></li>
                    <li> <a href="javascript:loadBatchAddProblem();">批量添加</a></li>
                </ul>
            </li>
            <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 比赛管理 <span class="caret"> </span></a>
                <ul class="dropdown-menu">
                    <li> <a href="javascript:loadMyContest()">我的比赛</a></li>
                    <li> <a href="javascript:loadAllContest()">正式比赛</a></li>
                    <li> <a href="javascript:loadAllCloneContest()">克隆比赛</a> </li>
                    <li> <a href="javascript:loadNewContest()">新增比赛</a></li>
                </ul>
            </li>
            <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 用户管理 <span class="caret"> </span> </a>
                <ul class="dropdown-menu">
                    <li> <a href="javascript:loadAllUser()"> 所有用户 </a> </li>
                    <li> <a href="javascript:loadAddUser()"> 新增用户 </a> </li>
                    <li> <a href="javascript:loadAddInviteCode()"> 添加邀请码 </a> </li>
                </ul>
            </li>
            <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Wiki管理 <span class="caret"> </span></a>
                <ul class="dropdown-menu">
                    <li><a href="#">模块管理</a></li>
                </ul>
            </li>
            <li class="dropdown"> <a href="#" class="dropdown-toggle" data-toggle="dropdown"> 商品管理 <span class="caret"> </span></a>
                <ul class="dropdown-menu">
                    <li><a href="javascript:loadAllGoods()">全部商品</a></li>
                    <li><a href="javascript:loadAddGoods()">添加商品</a></li>
                    <li><a href="javascript:loadAllOrder()">订单管理</a></li>
                </ul>
            </li>
            <li> <a href="javascript:loadReJudge()">重判</a></li>
            <li style="margin-top: 100%;"><a href="/">返回DOJ</a></li>
        </ul>
    </nav>

    <div id="page-content-wrapper">
        <button type="button" class="hamburger animated fadeInLeft is-open" data-toggle="offcanvas">
            <span class="hamb-top"></span>
            <span class="hamb-middle"></span>
            <span class="hamb-bottom"></span>
        </button>
        <div class="container-fluid" style="min-width: 100%;">

        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {
        let trigger = $('.hamburger'),
            overlay = $('.overlay'),
            isClosed = true;
        trigger.click(function() {
            hamburger_cross()
        });
        function hamburger_cross() {
            if (isClosed === true) {
                overlay.hide();
                trigger.removeClass('is-open')
                trigger.addClass('is-closed')
                isClosed = false;
            } else {
                overlay.show()
                trigger.removeClass('is-closed')
                trigger.addClass('is-open')
                isClosed = true;
            }
        }
        $('[data-toggle="offcanvas"]').click(function() {
            $('#wrapper').toggleClass('toggled')
        })

        $('[data-toggle="offcanvas"]').click()
    });
</script>
<script src="${pageContext.request.contextPath}/static/js/admin/admin.js"></script>
</body>
