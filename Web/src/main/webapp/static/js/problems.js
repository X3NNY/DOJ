let pnums;
let page = getUrlParam("page");
let name = '';
let num;
let tag = '';
if (!page || page <= 0) page = 1;

function addStar(pid) {
    $.ajax({
        url: "/api/problem/upvote?pid=" + pid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state == 0) {
                narn("log", "点赞成功！");
            } else {
                narn("log", "您现在不能投票！");
            }
        }
    });
}

function subStar(pid) {
    $.ajax({
        url: "/api/problem/downvote?pid=" + pid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state == 0) {
                narn("log", "点踩成功！");
            } else {
                narn("log", "您现在不能投票！");
            }
        }
    });
}

function loadMain(_info,_tag,_page,_star) {
    name = _info;
    tag = _tag;
    if (!_page || _page <= 0) page = 1;
    else page = _page;
    $.getJSON("/api/problem/set?page="+page + "&name=" + name + "&tag=" + tag + "&star=" + _star,function (res) {
        let problemList = $("#problemList");
        problemList.empty();

        let problemPage = $("#problemPage");
        pageList(problemPage,page,pnums);

        for (let i = 0; i < res.length; i++) {
            let tp = res[i];
            let ra = ((tp.ac_cnt / tp.all_cnt)*100).toFixed(2);
            if (tp.all_cnt === 0) {
                ra = "0.00%(0/0)";
            } else {
                ra = ra + "%(" + tp.ac_cnt + "/" + tp.all_cnt + ")";
            }

            let ts = HTML.td(tp.pid) + HTML.tdC(HTML.a("/problem.jsp?pid=" + tp.pid, tp.title)) + HTML.tdC(ra) + HTML.tdC(getStyleName(tp.author)) + getVote(tp.pid, tp.up_vote, tp.down_vote, tp.my_vote);

            if (tp.state == 0) {
                problemList.append(HTML.tr(ts));
            } else if (tp.state == 1) {
                problemList.append(HTML.trClass("success",ts));
            } else {
                problemList.append(HTML.trClass("danger",ts));
            }
        }
    });
}

function getPages(_info,_tag,_star) {
    name = _info;
    tag = _tag;
    $.getJSON("/api/problem/getpages?name=" + name + "&tag=" + tag + "&star=" + _star,function (res) {
        pnums = res.pages;
        num = res.num;

        let problemPage = $("#problemPage");
        pageList(problemPage,1,pnums);
    })
}

function toPage(_page) {
    loadMain("","",_page,0);
}

function searchProblem() {
    let _info = $("#searchInfo").val();
    let _tag = $("#tagSelect").val();
    let _star = $("#myStar").is(':checked')?1:0;
    getPages(_info,_tag,_star);
    loadMain(_info,_tag,1,_star);
}


getPages("","",0);
loadMain("","",1,0);

document.onkeydown = function (e) {
    if ((e.keyCode || e.which) == 13) {
        searchProblem()
    }
}