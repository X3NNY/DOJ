<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/header.jspf" %>
    <title>Paste</title>
</head>
<body>
<%@include file="/navbar.jspf" %>

<div class="container-fluid" style="margin-top: 5%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            Code Paste
            <front style="float:right;">
                <a href="javascript:copyUrl()"><b style="color: #5bff01">复制链接</b></a>
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
<script>
    let id = getUrlParam("id");

    function submitInfo(res) {
        return "/** \n" +
            " * @Author: " + res.username + "\n" +
            " * @Date: " + res.date + "\n" +
            " * Powered By DOJ" + "\n" +
            "*/\n";
    }

    $.getJSON("/beta/paste/getcode?id="+id,function (res) {
        if (res.state != 0) {
            narn("log","该链接错误或已失效");
        } else {
            $(".hljs code").text(submitInfo(res)+res.code);
            hljs.initHighlighting();
            $("code").each(function(){
                $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>");
            });
        }
    })

    function copyUrl() {
        if (window.clipboardData == undefined) {
            narn("log","暂不支持您的游览器，请手动复制地址栏的链接")
        } else {
            window.clipboardData.setData("text", location.href);
            narn("succes", "已经将链接复制到剪切板")
        }
    }
</script>
<%@include file="/footer.jspf" %>
</body>
</html>