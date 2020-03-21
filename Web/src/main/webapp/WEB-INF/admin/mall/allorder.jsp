<%@page contentType="text/html; charset=UTF-8" %>
<div class="panel panel-info">
    <div class="panel-body">
        <h3 style="text-align: center;padding: 0 0;">所有订单</h3>
        <hr />
        <table id="orderList" class="table table-hover table-condensed table-striped">
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
        <div id="pageList" style="float:right;"></div>
        <br />
        <a id="showMsgBtn" href="#showMsg" data-toggle="modal" style="display: none;"></a>
        <div class="modal fade" id="showMsg"  aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" style="min-width: 700px;">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            详细信息
                        </h4>
                    </div>
                    <div id="msgContent" class="modal-body">

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="changeStatus()" data-dismiss="modal" id="replyBtn">标记为已处理</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    if (typeof page == "undefined") {
        var page;
    }
    if (typeof mid == "undefined") {
        var mid;
    }
    function getPages(_page) {
        let pageList = $("#pageList");
        pageList.empty()
        let s = "";
        if (_page !== 1) {
            s += HTML.a("javascript:toPage("+(_page-1)+")","<span class=\"glyphicon glyphicon-chevron-left\"></span>上一页")
        }
        s += "&nbsp;&nbsp;&nbsp;&nbsp;";
        s += HTML.a("javascript:toPage("+(_page+1)+")","下一页<span class=\"glyphicon glyphicon-chevron-right\"></span>")
        pageList.append(HTML.span(s))
    }

    function toPage(_page) {
        page = _page;
        loadOrderMsg()
    }

    function loadOrderMsg() {
        getPages(page)
        $.getJSON("/api/message/order/"+page, function (res) {
            let list  =$("#orderList tbody");
            list.empty()
            for (let i = 0; i < res.length; i++) {
                let t = res[i];
                let state = "";
                if (t.status === 0) {
                    state = HTML.bS("color:red","未处理");
                } else {
                    state = HTML.bS("color: blue;", "已处理");
                }
                list.append(HTML.tr(HTML.td(res[i].mid)+HTML.td(HTML.a("javascript:showMessage("+t.mid+")",t.title))+HTML.td("管理员")+HTML.td(t.date)+HTML.td(state)))
            }
        })
    }

    function showMessage(_mid) {
        mid = _mid;
        $.getJSON("/api/message/content/"+_mid, function (res) {
            $("#showMsgBtn").click()
            $("#msgContent").html(res.content)
        })
    }

    function changeStatus() {
        $.ajax({
            url: "/api/message/status/"+mid,
            type: "PUT",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                loadOrderMsg()
            }
        })
    }

    toPage(1)
</script>