<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>商店 - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
<%--            <div class="panel panel-info"  style="width: 300px;display:inline-block;float: left;margin-right:10px;">--%>
<%--                <div class="panel-body">--%>
<%--                    <table>--%>
<%--                        <tbody>--%>
<%--                        <tr>--%>
<%--                            <td>--%>
<%--                                <img src="media/user/default.jpg"  style="width: 100px">--%>
<%--                            </td>--%>
<%--                            <td style="vertical-align: top">--%>
<%--                                <p><b>商品名称：</b>Test</p>--%>
<%--                                <p><b>商品价格：</b>50 DC</p>--%>
<%--                                <p><b>商品库存：</b>20</p>--%>
<%--                                <span  style="text-align: center;display: inline-block;width: 170px;margin: .2rem auto 0;"><button type="button" class="btn-sm btn-default btn-primary">Buy!</button></span>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
<%--                        </tbody>--%>
<%--                    </table>--%>
<%--                </div>--%>
<%--            </div>--%>
            <div id="Mall">
            </div>
        </div>
    </div>
    <a id="hidden_a" href="#goodsInfo" data-toggle="modal" style="display: none"></a>
    <div class="modal fade" id="goodsInfo"  aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content" style="min-width: 700px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        商品详情
                    </h4>
                </div>
                <div class="modal-body">
                    <h3></h3>
                    <hr />
                    <div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" class="btn btn-primary" onclick="pay()" data-dismiss="modal">确定购买</button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/mall.min.js"></script>
<%@include file="footer.jspf" %>
<% if (user != null) { %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/min.css">
<script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/min.js"></script>
<% } %>
</body>
</html>
