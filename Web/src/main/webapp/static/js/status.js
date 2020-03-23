let spnum;
let num;
let user="",pid=-1,res=-1,lang="";
let page=getUrlParam("page");

function loadStatus(user,pid,res,lang,_page) {
    if (!user) user = "";
    if (!pid) pid = -1;
    if (!res) res = -1;
    if (!lang) lang = "";
    if (!_page || _page <= 0) page = 1;
    let statusPage = $("#statusPage");
    pageList(statusPage,page,spnum);
    $.ajax({
        url: "/api/submit/list?page=" + page,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "pid": pid,
            "username": user,
            "res": res,
            "lang": lang
        }),
        dataType: "json",
        success: function (res) {
            let sList = $("#statusList tbody");
            sList.empty();
            for (let i = 0; i < res.length; i++) {
                let tp = res[i];
                let c = "default";
                if (tp.state) c = "info";
                let size = tp.size;
                if (tp.viewable || tp.state) {
                    size = HTML.a("code.jsp?sid=" + tp.sid, tp.size);
                }
                sList.append(HTML.trClass(c,
                    HTML.td(tp.sid) +
                    HTML.tdC(getStyleName(tp.username)) +
                    HTML.tdC(HTML.a("/problem.jsp?pid=" + tp.pid, tp.pid)) +
                    HTML.td(getProRes(tp.result, tp.score, tp.pos, tp.sid)) +
                    HTML.tdC(tp.lang) +
                    HTML.td(tp.time_used == null ? "-" : tp.time_used + "ms") +
                    HTML.td(tp.memory_used == null ? "-" : tp.memory_used + "KB") +
                    HTML.tdC(tp.date) +
                    HTML.td(size)
                ))
            }

        },
        error: function (e) {
            console.log(e);
        }
    });

}

function getPages(user,pid,res,lang) {
    if (!user) user = "";
    if (!pid) pid = -1;
    if (!res) res = -1;
    if (!lang) lang = "";
    $.ajax({
        url: "/api/submit/getpages",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            "pid": pid,
            "username": user,
            "res": res,
            "lang": lang
        }),
        dataType: "json",
        success: function (res) {
            spnum = res.pages;
            num = res.num;

            let statusPage = $("#statusPage");
            pageList(statusPage,1,spnum);
        }
    })
}

function toPage(_page) {
    page = _page;
    let _pid = $("#statusProblemSelect").val();
    let _username = $("#statusUser").val();
    let _lang = $("#statusLangSelect  option:selected").val();
    let _res = $("#statusResultSelect  option:selected").val();
    _lang = _lang.indexOf("ALL") === -1?_lang:"";
    loadStatus(_username,_pid,_res,_lang,page);
}

function getProRes(res,score,pos,sid) {
    let s;
    switch(res) {
        case 0:
            s = "<span class=\"label label-success\">Accepted</span>";break;
        case 1:
            s = "<span class=\"label label-info\">PE on test "+pos+"</span>";break;
        case 2:
            s = "<span class=\"label label-warning\">TLE on test "+pos+"</span>";break;
        case 3:
            s = "<span class=\"label label-warning\">MLE on test "+pos+"</span>";break;
        case 4:
            s = "<span class=\"label label-danger\">WA on test "+pos+"</span>";break;
        case 5:
            s = "<span class=\"label label-warning\">RE on test "+pos+"</span>";break;
        case 6:
            s = "<span class=\"label label-warning\">OLE on test "+pos+"</span>";break;
        case 7:
            s = "<span class=\"label label-default\">Waiting</span><img src='/media/img/status/wating1.gif' />";break;
        case 8:
            s = "<span class=\"label label-primary\">System Error</span>";break;
        case 9:
            s = "<span class=\"label label-info\"><a href=\"/statusInfo.jsp?sid="+sid+"\">Compilation Error</a>";break;
    }
    if (score != null && score !== -1 && res < 7) {
        if (res === 0) {
            return s + HTML.bS("color: green;", " "+score);
        } else {
            return s + HTML.bS("color: red;"," "+score);
        }
    }
    return s;
}

function statusSearch() {
    let _pid = $("#statusProblemSelect").val();
    let _username = $("#statusUser").val();
    let _lang = $("#statusLangSelect  option:selected").val();
    let _res = $("#statusResultSelect  option:selected").val();
    _lang = _lang.indexOf("ALL") === -1?_lang:"";
    page = 1;
    getPages(_username,_pid,_res,_lang);
    loadStatus(_username,_pid,_res,_lang,1);
}
getPages(null,null,null,null);
toPage(1);

document.onkeydown = function (e) {
    if ((e.keyCode || e.which) == 13) {
        statusSearch()
    }
}

setInterval(function () {
    toPage(page)
},5000)