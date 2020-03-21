let cpnum;
let page=getUrlParam('page');
let name='';

function loadMain(_name,_page) {
    name = _name;
    page = _page;
    if (!name) name='';
    if (!page || page==='' || page<=0) page = 1;
    $.getJSON("/api/contest/diy?page="+page+"&name="+name, function (res) {
        cpnum = res.pages;

        let contestPage = $("#contestPage");
        pageList(contestPage,page,cpnum);

        let contestListBody = $("#contestList tbody");
        contestListBody.empty();
        for (let i = 0; i < res["contests"].length; i++) {
            let tp = res["contests"][i];
            let _now = new Date().getTime();
            let _writers =getStyleName(tp["builders"][0]);
            let level;
            if (tp.level === 0 || tp.level === 2) level = '公开';
            else level = '密码';
            tp.title = removeHtml(tp.title);
            if (new Date(tp.starttime).getTime() >= _now) {
                //未开始
                contestListBody.append(HTML.trClass(tp.state?"info":"default",
                    HTML.td(tp.cid) + HTML.tdC(HTML.a("/contest.jsp?cid="+tp.cid,tp.title)) +
                    HTML.tdC(_writers) + HTML.tdC(tp.starttime) +
                    HTML.tdC(getGapMin(tp.endstime,tp.starttime)) +
                    HTML.tdC(level) +
                    HTML.tdC(HTML.b("已报名"+tp.num+"人")+"<br />"+HTML.a("contestR.jsp?cid="+tp.cid,"[报名]")) +
                    getVote(tp.cid,tp.up_vote,tp.down_vote,tp.my_vote)
                ));
            } else {
                if (new Date(tp.endstime).getTime() > _now) {
                    //进行中
                    contestListBody.append(HTML.trClass(tp.state?"info":"default",
                        HTML.td(tp.cid) + HTML.tdC(HTML.a("/contest.jsp?cid="+tp.cid,tp.title)) +
                        HTML.tdC(_writers) + HTML.tdC(tp.starttime) +
                        HTML.tdC(getGapMin(tp.endstime,tp.starttime)) +
                        HTML.tdC(level) +
                        HTML.tdC(HTML.a("contestR.jsp?cid="+tp.cid,HTML.b("总报名"+tp.num+"人"))+"<br />"+HTML.b("[进行中]")) +
                        getVote(tp.cid,tp.up_vote,tp.down_vote,tp.my_vote)
                    ));
                } else {
                    //已结束
                    contestListBody.append(HTML.trClass(tp.state?(tp.state===1?"info":"success"):"default",
                        HTML.td(tp.cid) + HTML.tdC(HTML.a("/contest.jsp?cid="+tp.cid,tp.title)) +
                        HTML.tdC(_writers) + HTML.tdC(tp.starttime) +
                        HTML.tdC(getGapMin(tp.endstime,tp.starttime)) +
                        HTML.tdC(tp.level) +
                        HTML.tdC(HTML.a("contestR.jsp?cid="+tp.cid,HTML.b("总报名"+tp.num+"人"))+"<br />"+HTML.b("[已结束]")) +
                        getVote(tp.cid,tp.up_vote,tp.down_vote,tp.my_vote)
                    ));
                }
            }
        }
    });
}

function toPage(_page) {
    loadMain("",_page);
}

function searchContest() {
    let _info = $("#searchInfo").val();
    loadMain(_info,1);
}

function addStar(_cid) {
    $.ajax({
        url: "/api/contest/upvote/" + _cid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("log", "点赞成功！");
            } else {
                narn("log", "您现在不能投票！");
            }
        }
    });
}

function subStar(_cid) {
    $.ajax({
        url: "/api/contest/downvote/" + _cid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("log", "点踩成功！");
            } else {
                narn("log", "您现在不能投票！");
            }
        }
    });
}
function getGapMin(e,s) {
    let _tp = Math.floor(((new Date(e)).getTime() - (new Date(s)).getTime())/(1000*60));
    let _h = (Math.floor(_tp/60)).toString();
    _h = _h.length<2?"0"+_h:_h;
    let _m = (_tp%60).toString();
    _m = _m.length<2?"0"+_m:_m;
    return _h + ":" + _m;
}

toPage(1);