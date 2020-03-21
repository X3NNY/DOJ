let cpnum;
let page=getUrlParam('page');
let name='';

function loadMain(_name,_page) {
    name = _name;
    page = _page;
    if (!name) name='';
    if (!page || page==='' || page<=0) page = 1;
    $.getJSON("/api/contest?page="+page+"&name="+name, function (res) {
        cpnum = res.pages;

        let contestPage = $("#contestPage");
        pageList(contestPage,page,cpnum);

        let contestListBody = $("#contestList tbody");
        let contestHisListBody = $("#contestHisList tbody");
        contestHisListBody.empty();
        for (let i = 0; i < res["contests"].length; i++) {
            let tp = res["contests"][i];
            let _now = new Date().getTime();
            let _writers = '';
            for (let j = 0; j < tp["builders"].length; j++) {
                let _user = tp["builders"][j].split("|");
                _writers += HTML.a("/userinfo.jsp?username="+_user[0],getRatingName(parseInt(_user[1]),_user[0]));
                if (j < tp["builders"].length-1) {
                    _writers += "<br />";
                }
            }
            let level;
            if (tp.level === 0 || tp.level === 2) level = '公开';
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
                    contestHisListBody.append(HTML.trClass(tp.state?(tp.state===1?"info":"success"):"default",
                        HTML.td(tp.cid) + HTML.tdC(HTML.a("/contest.jsp?cid="+tp.cid,tp.title)+"<br />"+HTML.a("javascript:cloneContest("+tp.cid+")","<i style='font-size: small;color: #00cc66'>Clone</i>")) +
                        HTML.tdC(_writers) + HTML.tdC(tp.starttime) +
                        HTML.tdC(getGapMin(tp.endstime,tp.starttime)) +
                        HTML.tdC(tp.level?"密码":"公开") +
                        HTML.tdC(HTML.a("contestR.jsp?cid="+tp.cid,HTML.b("总报名"+tp.num+"人"))+"<br />"+HTML.b("[已结束]")) +
                        getVote(tp.cid,tp.up_vote,tp.down_vote,tp.my_vote)
                    ));
                }
            }
        }
    });
}

function cloneContest(_cid) {
    $.ajax({
        url: "/api/contest/clone?cid="+_cid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narnHref("success","已添加至DIY比赛，点击确认跳转","/contest.jsp?cid="+res.cid)
            } else if (res.state === 1) {
                narn("warn","您不能克隆本场比赛")
            }
        }
    })
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

function getRatingName(rating,name) {
    return getStyleName(name+"|"+rating);
}

$(function () {
    $("#contestList tbody").empty();
    loadMain('',1);
})