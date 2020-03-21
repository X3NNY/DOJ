let page = getUrlParam("page");
let hnums;
if (!page || page <= 0) page = 1;

function getPages() {
    $.getJSON("/api/hack/getpages", function (res) {
        hnums = res.pages;
    })
}

function loadMain(pid,hacker,committer,result) {
    pid = (pid==null)?-1:pid;
    hacker = (hacker==null||hacker.length === 0)?"":hacker;
    committer = (committer==null||committer.length===0)?"":committer;
    result = (result==null)?-1:result;
    $.getJSON("/api/hack/list?page="+page+"&pid="+pid+"&hacker="+hacker+"&committer="+committer+"&result="+result, function (res) {
        let hackPages = $("#hackPage");
        pageList(hackPages,page,hnums)

        let list = $("#hackList tbody");
        list.empty()
        for (let i = 0; i < res.length; i++) {
            let t = res[i];
            let r = "<span class=\"label label-default\">Waiting</span>";
            if (t.result === 2) {
                r = "<span class=\"label label-success\">Success</span>";
            } else if (t.result === 1) {
                r = "<span class=\"label label-danger\">Failed</span>";
            }
            list.append(HTML.tr(HTML.td(HTML.a("hack.jsp?id="+t.id,t.id))+HTML.td(HTML.a("code.jsp?sid="+t.sid,t.sid))+HTML.td(HTML.a("problem.jsp?pid="+t.pid,t.pid+" - "+t.title))+HTML.td(getStyleName(t.hacker))+HTML.td(getStyleName(t.committer))+HTML.td(HTML.a("hack.jsp?id="+t.id,r))+HTML.td(t.date)))
        }
    })
}

function toPage(_page) {
    page = _page;
    let pid = $("#hackProblemSelect").val();
    let hacker = $("#hackUser").val();
    let committer = $("#commitUser").val();
    let res = $("#hackResultSelect  option:selected").val();
    loadMain(pid,hacker,committer,res)
}

function hackSearch() {
    page = 1;
    getPages()
    let pid = $("#hackProblemSelect").val();
    let hacker = $("#hackUser").val();
    let committer = $("#commitUser").val();
    let res = $("#hackResultSelect  option:selected").val();
    loadMain(pid,hacker,committer,res)
}

getPages()
toPage(page)

document.onkeydown = function (e) {
    if ((e.keyCode || e.which) == 13) {
        hackSearch()
    }
}