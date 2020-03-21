<%@page contentType="text/html; charset=UTF-8" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        所有商品
    </div>
    <div class="panel-body" id="goodsInfo">
        <table class="table table-striped table-hover table-bordered table-condensed">
            <thead>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Price</th>
                <th>Count</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
<script>
    function loadGoodsInfo() {
        $.getJSON("/api/mall/adminall", function (res) {
            let goodsInfo = $("#goodsInfo table tbody");
            goodsInfo.empty()
            for (let i = 0; i < res.length; i++) {
                goodsInfo.append(
                    HTML.tr(HTML.td(res[i].gid) +
                    HTML.td(HTML.a('javascript:loadEditGoods('+res[i].gid+')',res[i].title)) +
                    HTML.td(res[i].price) +
                    HTML.td(res[i].cnt) +
                    HTML.td(HTML.a('javascript:changeGoodsStatus('+res[i].gid+')',res[i].status===0?"在售":"停售"))
                ))
            }
        })
    }

    function changeGoodsStatus(gid) {
        $.ajax({
            url: "/app/mall/changestatus/"+gid,
            type: "PUT",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn('success','更变状态成功')
                    loadGoodsInfo()
                } else {
                    narn('log','更变状态失败')
                }
            }
        })
    }

    loadGoodsInfo()
</script>