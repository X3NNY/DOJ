<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Beta - DOJ</title>
    <%@include file="/header.jspf" %>
</head>
<body>
<%@include file="/navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-info">
        <div class="panel-heading">
            Code Paste
        </div>
        <textarea class="form-control" placeholder="把您的代码粘贴到这里"  cols="10" rows="15"></textarea>
        <button type="button" class="btn btn-sm btn-success" onclick="paste()">Paste!</button>
    </div>
</div>
<script>
    function paste() {
        let code = $("textarea").val();
        $.ajax({
            url: "/beta/paste",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "code": code
            }),
            success: function (res) {
                if (res.state == 0) {// yes
                    location.href = "pastecode.jsp?id="+res.id
                } else if (res.state == 1) {
                    narn("log","请先登录");
                }
            }
        })
    }
</script>
<%--<script src="${pageContext.request.contextPath}/static/js/Beta.js"></script>--%>
<%@include file="/footer.jspf" %>
</body>
</html>
