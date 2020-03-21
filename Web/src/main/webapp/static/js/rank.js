let rnums;
let page = getUrlParam("page");
let name = '';
if (!page || page <= 0) page = 1;

function loadMain(_info,_page) {
    name = _info;
    if (!_page || _page <= 0) page = 1;
    else page = _page;
    $.getJSON("/api/rank/set?page="+page + "&name=" + name,function (res) {
        rnums = res.pages;

        let rankPage = $("#rankPage");
        pageList(rankPage,page,rnums);

        let rankList = $("#rankList");
        rankList.empty();
        for (let i = 0; i < res["rankset"].length; i++) {
            let tp = res["rankset"][i];
            rankList.append(HTML.tr(HTML.td(50*page+i-49)+HTML.td(getStyleName(tp.username))+HTML.tdC((tp.note==null||tp.note.length===0)?"-":removeHtml(tp.note))+HTML.tdR(tp.ac_cnt)+HTML.tdR(tp.rating===0?'-':tp.rating)+HTML.tdR(tp.num)));
        }
    });
}

function toPage(_page) {
    loadMain("",_page);
}

function searchUser() {
    let _info = $("#searchInfo").val();
    loadMain(_info,1);
}

loadMain("",1);