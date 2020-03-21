let Gid;
loadMall()
function loadMall() {
    $.getJSON("/api/mall/all", function (res) {
        if (res.state !== 0) {
            narn("log","获取商品失败")
            return;
        }
        let list = $("#Mall");
        list.empty()
        for (let i = 0; i < res.goods.length; i++) {
            list.append(newGoods(res.goods[i]))
        }
    })
}

function buy(gid) {
    $.getJSON("/api/mall/info/"+gid, function (res) {
        if (res.state !== 0) {
            narn("log","获取商品详细信息失败")
            return;
        }
        $("#hidden_a").click()
        let info = $("#goodsInfo div.modal-body");
        info.find("h3").text(res.title)
        info.find("div").html(res.text)
        Gid = gid;
    })
}

function pay() {
    $.getJSON("/api/mall/pay/"+Gid, function (res) {
        if (res.state === 0) { //success
            narn("success","购买成功")
        } else if (res.state === 1) { //dc 不足
            narn("log","购买失败，DC 不足")
        } else if (res.state === 2) {
            narn("log","请先登陆")
        } else if (res.state === 3) {
            narn("log","商品不存在或者已售罄")
        }
    })
}

function newGoods(data) {
    return "<div class=\"panel panel-info\"  style=\"width: 300px;display:inline-block;float: left;margin-right:10px;\">" +
        "                <div class=\"panel-body\">" +
        "                    <table>" +
        "                        <tbody>" +
        "                            <tr>" +
        "                                <td>" +
        "                                    <img src=\""+data.image+"\" onerror=\"this.src='media/user/default.jpg'\"  style=\"width: 100px\" alt=\"商品图片\">" +
        "                                </td>" +
        "                                <td style=\"vertical-align: top\">" +
        "                                    <p><b>商品名称：</b>"+data.title+"</p>" +
        "                                    <p><b>商品价格：</b>"+data.price+" DC</p>" +
        "                                    <p><b>商品库存：</b>"+(data.cnt===0?"<span style='color: red'>已售罄</span>":data.cnt)+"</p>" +
        "                                    <span  style=\"text-align: center;display: inline-block;width: 170px;margin: .2rem auto 0;\"><button type=\"button\" class=\"btn-sm btn-default btn-primary\" onclick=\"buy("+data.gid+")\">Buy!</button></span>" +
        "                                </td>" +
        "                            </tr>" +
        "                        </tbody>" +
        "                    </table>" +
        "                </div>" +
        "            </div>";
}