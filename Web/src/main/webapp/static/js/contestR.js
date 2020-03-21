let cid = getUrlParam("cid");
let page = 1;

function init() {
    $.getJSON("/api/contest/register/info/"+cid,function (res) {
        if (res.my_state === 1) {
            $("div.panel-heading front").html(HTML.a("javascript:submitR()","[已报名]"))
        } else {
            $("div.panel-heading front a").text("[报名]")
        }

        let Rpage = $("#Rpage");
        pageList(Rpage,page,res.pages);

        let table = $("table tbody");
        table.empty();
        for (let i = 0; i < res.info.length; i++) {
            let tp = res["info"][i];
            let state,c;
            if (tp.state === 0) {
                c = "color:green;";
                state = "报名成功";
            } else if (tp.state === 1) {
                c = "color:blue;";
                state = "已参赛";
            } else if (tp.state === 2) {
                c = "color:#808080;";
                state = "*";
            } else if (tp.state === 3) {
                c = "color:red;";
                state="skipped";
            }
            table.append(HTML.tr(HTML.td(getStyleName(tp.username))+HTML.tdR(tp.rating===0?"-":tp.rating)+HTML.tdR(HTML.bS(c,state))+HTML.tdR(tp.date)));
        }
    })

}

function toPage(_page) {
    page = _page;
    init()
}

function submitR() {
    $.ajax({
        url: "/api/contest/register/"+cid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("log","请先登录")
            } else if (res.state === 1) {
                narn("success","报名成功")
            } else if (res.state === 2) {
                narn("log","成功取消报名")
            } else {
                narn("error","现在不能报名")
            }
            init()
        }
    })
}

init()