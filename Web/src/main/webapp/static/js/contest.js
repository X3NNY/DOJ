let pnum = 0;
let cid = getUrlParam("cid");
let contestInfo;
let contestStartTime;
let contestEndsTime;
let href = location.hash;
let contesttime;
let spnum;
let nid;
let mp1 = [];
let isEnds = false;
if (href==null || href==='') {
    href = "#Home";
}
$("#Notice").hide()

$.getJSON("/api/contest/check?cid="+cid, function (res) {
    if (res.state === 0) {
        loadMain()
    } else if (res.state === 1) {
        $("#Contest").load("/api/contest/module/passwdcheck")
    } else if (res.state === 2) {
        narn('log','本场比赛不存在！')
    }
})

function loadMain() {
    $.getJSON("/api/contest/info/" + cid, function (data) {
        if (data.state === 1) {
            narn("log", "比赛还未开始呢")
        } else if (data.state === 2) {
            narn("log", "您还没有报名噢")
        }

        if (data.is_author) $("#editContestInfo").show()

        contestInfo = data;
        pnum = contestInfo.problemset.length;

        if (new Date(contestInfo.endstime) <= new Date().getTime()) {
            $("div.progress-bar.progress-success").css("width", "100%")
            $("#toProButton").show()
            isEnds = true;
        } else {
            go()
            setInterval("go()", 5000)
        }

        if (isEnds) {
            $("#contestTime").hide()
        } else {
            $("#contestTime").text(getGapMin(contestInfo.endstime, new Date()));
            setInterval("go2()", 1000);
        }
        init()
    })
}


window.onhashchange= function() {
    href=location.hash;
    run()
};

function init() {
    loadHome()
    if (href !== "#Home") {
        run();
    }
}

function run() {
    if (href === "#P") {
        location.hash = "#P0";
    } else if (href.charAt(1) === 'P') {
        loadProblem(parseInt(href.substr(2,5)));
        $(".tab-pane.active").removeClass("active")
        $("#P").addClass("active")
        $("#tabs-425272 ul li.active").removeClass("active")
        $("#tabs-425272 ul li a[href='#P']").parent().addClass("active")
        $("#problems li[class='active']").removeClass()
        $("#problems a[href='"+href+"']").parent().addClass("active")
    } else if (href === "#Status") {
        loadStatusPages();
        $(".tab-pane.active").removeClass("active");
        $("#Status").addClass("active");
        $("#tabs-425272 ul li.active").removeClass("active")
        $("#tabs-425272 ul li a[href='#Status']").parent().addClass("active");
    } else if (href === "#Rank") {
        loadRank()
        $(".tab-pane.active").removeClass("active")
        $("#Rank").addClass("active")
        $("#tabs-425272 ul li.active").removeClass("active")
        $("#tabs-425272 ul li a[href='#Rank']").parent().addClass("active")
    } else if (href === "#Rating") {
        loadRating()
        $(".tab-pane.active").removeClass("active")
        $("#Rating").addClass("active")
        $("#tabs-425272 ul li.active").removeClass("active")
        $("#tabs-425272 ul li a[href='#Rating']").parent().addClass("active")
    } else if (href === "#Discuss") {
        loadDiscuss()
        $(".tab-pane.active").removeClass("active")
        $("#Discuss").addClass("active")
        $("#tabs-425272 ul li.active").removeClass("active")
        $("#tabs-425272 ul li a[href='#Discuss']").parent().addClass("active")
    } else if (href === "#Home") {
        $.getJSON("/api/contest/info/" + cid, function (data) {
                contestInfo = data;
                pnum = contestInfo.problemset.length;
                loadHome();
            }
        );
    }
}

function go() {
    let _time = new Date().getTime();
    let ra = (_time - contestStartTime) / contesttime;
    ra *= 100;
    if (ra > 100) ra = 100;
    $("div.progress-bar.progress-success").css("width",ra+"%");
}

function loadProblem(pos) {
    $("#problemHint").hide();
    if (pos==null || pos >= pnum || pos < 0) return ;
    $.ajax({
        type: "GET",
        url: "/api/contest/problem/"+cid+"/"+pos,
        dataType: "json",
        success: function (res) {
            $("#problemTitle").text(res.title);
            $("#problemInput").html(res.input);
            $("#problemOutput").html(res.output);
            $("#problemSampleInput").html(res.sampleinput);
            $("#problemSampleOutput").html(res.sampleoutput);
            $("#problemDesc").html(res.description);
            $("#problemTimeLimit").text(res.timelimit);
            $("#problemMemoryLimit").text(res.memorylimit);
            switch(res.type) {
                case 0:
                    $("#problemType").text("传统题");break;
                case 1:
                    $("#problemType").text("交互题");break;
                case 2:
                    $("#problemType").text("接口实现题");break;
                case 3:
                    $("#problemType").text("提交答案题");break;
            }
            $("#problemSpecial").text(res.special?"Yes":"No");
            if (res.hint != null && res.hint !== "") {
                $("#problemHint").show();
                $("#problemHint div.panel-body").html(res.hint);
            }
            if (res.state === 1) {
                $("#problemState").html("<font color=\"green\"><span class=\"glyphicon glyphicon-ok\"></span>已AC</font>");
            } else if (res.state === 2) {
                $("#problemState").html("<font color=\"red\"><span class=\"glyphicon glyphicon-remove\"></span>未AC</font>");
            } else {
                $("#problemState").html("<font color='black'><span class='glyphicon glyphicon-minus'></span>未提交</font>")
            }
        },
        error: function(e) {
            console.log(e);
        }
    });
}

function loadStatusPages(user,num,res,lang,page) {
    if (!user) user = "";
    if (!num || num === '-1') num = -1;
    if (!res || res === '-1') res = -1;
    if (!lang || lang === '-1') lang = "";
    page = 1;
    if (true) {
        $.getJSON("/api/contest/"+cid+"/getpages?name="+user+"&num="+num+"&res="+res+"&lang="+lang, function (res) {
            spnum = res;
            let statusPage = $("#statusPage");
            pageList(statusPage, 1, spnum);
        })
        loadStatus(user,num,res,lang,page)
    }
}

function loadStatus(user,num,res,lang,page) {
    if (!user) user = "";
    if (!num || num === '-1') num = -1;
    if (!res || res === '-1') res = -1;
    if (!lang || lang === '-1') lang = "";
    if (!page || page <= 0) page = 1;
    if (true) {
        $.ajax({
            url: "/api/contest/status/" + cid + "/" + page,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "username": user,
                "num": parseInt(num),
                "res": parseInt(res),
                "lang": lang
            }),
            success: function (res) {
                let sList = $("#statusList tbody");
                sList.empty();
                for (let i = 0; i < res["status"].length; i++) {
                    let c = "default";
                    let timeUsed = '-';
                    let memoryUsed = '-';
                    let cSize = res["status"][i].size;
                    if (res["status"][i].state === 1) {
                        c = 'info';
                        timeUsed = res["status"][i].time_used!=null?res["status"][i].time_used + 'ms' : '-';
                        memoryUsed = res["status"][i].memory_used!=null?res["status"][i].memory_used + 'KB' : '-';
                        cSize = HTML.a("javascript:showCode(" + res["status"][i].sid + ")", res["status"][i].size);
                    }
                    sList.append(HTML.trClass(c,HTML.td(res["status"][i].sid) +
                        HTML.td(getStyleName(res["status"][i].username)) +
                        HTML.td(HTML.a("#P" + mp1[res["status"][i].pid], getProChar(mp1[res["status"][i].pid]))) +
                        HTML.td(getProRes(res["status"][i].result, null, res['status'][i].score)) +
                        HTML.td(res["status"][i].lang) +
                        HTML.td(timeUsed) +
                        HTML.td(memoryUsed) +
                        HTML.td(cSize) +
                        HTML.td(res["status"][i].date)
                    ));
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    }
}

function loadRank() {
    $.getJSON("/api/contest/rank/" + cid,function (res) {
        switch (contestInfo.type) {
            case 0:
                loadRankICPC(res);break;
            case 1:
                loadRankOI(res);break;
            case 2:
                loadRankIOI(res);break;
            case 3:
                loadRankShortCode(res);break;
            case 4:
                loadRankMinTime(res);break;
        }
    });
}

function loadRankICPC(res) {
    let num = res["set"].length;
    res['set'].sort(function (a,b) {
        if (a.cnt === b.cnt) {
            return a.penalty > b.penalty;
        }
        return a.cnt < b.cnt;
    })
    let rankListBody = $("#rankList tbody");
    rankListBody.empty()
    let gold = Math.ceil(0.1*num);
    let silver = Math.ceil(0.3*num);
    let bronze = Math.ceil(0.6*num);
    let tot = 0;
    let skipped = [];
    for (let i = 0; i < num; i++) {
        let _rank,_name,_sum;
        let _proset = new Array(pnum).fill(HTML.td(""));

        if (res["set"][i].state === 2) {
            _rank = HTML.tdClass("rank_skipped","*");
            _name = HTML.tdClass("rank_skipped",getStyleName(res["set"][i].name));//mp2[res["set"][i].uid]
            _sum = HTML.tdClass("rank_skipped","*");
            _proset = new Array(pnum).fill(HTML.tdClass("rank_skipped","*"));
            skipped.push(HTML.tr(_rank + _name + _sum + HTML.tdClass("rank_skipped","*") +_proset.toString()));
            continue;
        }

        if (res["set"][i].state === 1) {
            _rank = HTML.tdClass("rank_star","*");
        } else if (tot < gold) {
            _rank = HTML.tdClass("rank_gold",++tot);
        } else if (tot < silver) {
            _rank = HTML.tdClass("rank_silver",++tot);
        } else if (tot < bronze) {
            _rank = HTML.tdClass("rank_bronze",++tot);
        } else {
            _rank = HTML.td(++tot);
        }

        _name = HTML.td(getStyleName(res["set"][i].name));
        _sum = HTML.td(Math.round(res["set"][i].penalty/60));

        let uac_cnt = 0;
        for (let j = 0; j < res["set"][i].pid.length; j++) {
            let cnt = res["set"][i].pid[j];
            if (cnt === 0) continue;
            if (res["set"][i].date[j] === 0) {
                _proset[j]=HTML.tdClass("danger",-cnt);
            } else {
                uac_cnt++;
                let c = "success";
                if (res["fb"][j] === res["set"][i].uid) {
                    c = "first_blood";
                }
                if (cnt === 1) {
                    _proset[j]=HTML.tdClass(c,Math.round(res["set"][i].date[j]/60))
                } else {
                    _proset[j]=HTML.tdClass(c,Math.round(res["set"][i].date[j]/60)+"(-"+(cnt-1)+")")
                }
            }
        }
        rankListBody.append(HTML.tr(_rank+_name+_sum+HTML.td(uac_cnt)+_proset.toString()));
    }

    for (let i = 0; i < skipped.length; i++) {
        rankListBody.append(skipped[i]);
    }
}

function loadRankOI(res) {
    let num = res.set.length;
    res['set'].sort(function (a,b) {
        if (a.cnt === b.cnt) {
            return a.penalty > b.penalty;
        }
        return a.cnt < b.cnt;
    })
    let rankListBody = $("#rankList tbody");
    rankListBody.empty()
    let gold = Math.ceil(0.1*num);
    let silver = Math.ceil(0.3*num);
    let bronze = Math.ceil(0.6*num);
    let tot = 0;
    let skipped = [];
    for (let i = 0; i < num; i++) {
        let _rank,_name,_sum;
        let _proset = new Array(pnum).fill(HTML.td(""));

        if (res['set'][i].state === 2) {
            _rank = HTML.tdClass("rank_skipped","*");
            _name = HTML.tdClass("rank_skipped",getStyleName(res['set'][i].username));
            _sum = HTML.tdClass("rank_skipped","*");
            _proset = new Array(pnum).fill(HTML.tdClass("rank_skipped","*"));
            skipped.push(HTML.tr(_rank + _name + _sum + HTML.tdClass("rank_skipped","*") +_proset.toString()));
            continue;
        }

        if (res['set'][i].state === 1) {
            _rank = HTML.tdClass("rank_star","*");
        } else if (tot < gold) {
            _rank = HTML.tdClass("rank_gold",++tot);
        } else if (tot < silver) {
            _rank = HTML.tdClass("rank_silver",++tot);
        } else if (tot < bronze) {
            _rank = HTML.tdClass("rank_bronze",++tot);
        } else {
            _rank = HTML.td(++tot);
        }

        _name = HTML.td(getStyleName(res['set'][i].name));
        _sum = HTML.td((res['set'][i].cnt/100).toFixed(2));

        let uac_cnt = 0;
        for (let j = 0; j < res['set'][i].pid.length; j++) {
            if (res['set'][i].pid[j] === 0) continue;
            if (100 - res['set'][i].score[j] <= 0.001) {
                _proset[j]=HTML.tdClass("success","100");
                uac_cnt++;
            } else {
                _proset[j]=HTML.tdClass("danger",res['set'][i].score[j]);
            }
        }
        rankListBody.append(HTML.tr(_rank+_name+_sum+HTML.td(uac_cnt)+_proset.toString()));
    }

    for (let i = 0; i < skipped.length; i++) {
        rankListBody.append(skipped[i]);
    }
}

function loadRankIOI(res) {
    loadRankOI(res);
}

function loadRankShortCode(res) {
    let num = res.length;
    res['set'].sort(function (a,b) {
        if (a.cnt === b.cnt) {
            return a.penalty > b.penalty;
        }
        return a.cnt < b.cnt;
    })
    let rankListBody = $("#rankList tbody");
    rankListBody.empty()
    let gold = Math.ceil(0.1*num);
    let silver = Math.ceil(0.3*num);
    let bronze = Math.ceil(0.6*num);
    let tot = 0;
    let skipped = [];
    for (let i = 0; i < num; i++) {
        let _rank,_name,_sum;
        let _proset = new Array(pnum).fill(HTML.td(""));

        if (res["set"][i].state === 2) {
            _rank = HTML.tdClass("rank_skipped","*");
            _name = HTML.tdClass("rank_skipped",getStyleName(res["set"][i].name));//mp2[res["set"][i].uid]
            _sum = HTML.tdClass("rank_skipped","*");
            _proset = new Array(pnum).fill(HTML.tdClass("rank_skipped","*"));
            skipped.push(HTML.tr(_rank + _name + _sum + HTML.tdClass("rank_skipped","*") +_proset.toString()));
            continue;
        }

        if (res["set"][i].state === 1) {
            _rank = HTML.tdClass("rank_star","*");
        } else if (tot < gold) {
            _rank = HTML.tdClass("rank_gold",++tot);
        } else if (tot < silver) {
            _rank = HTML.tdClass("rank_silver",++tot);
        } else if (tot < bronze) {
            _rank = HTML.tdClass("rank_bronze",++tot);
        } else {
            _rank = HTML.td(++tot);
        }

        _name = HTML.td(getStyleName(res["set"][i].name));
        _sum = HTML.td(Math.round(res["set"][i].penalty));

        let uac_cnt = 0;
        for (let j = 0; j < res["set"][i].pid.length; j++) {
            let cnt = res["set"][i].pid[j];
            if (res['set'][i].date[j] === 0) continue;
            if (cnt === -1) {
                _proset[j]=HTML.tdClass("danger",-1);
            } else {
                uac_cnt++;
                let c = "success";
                if (res["fb"][j] === res["set"][i].uid) {
                    c = "first_blood";
                }
                _proset[j]=HTML.tdClass(c,Math.round(res["set"][i].pid[j]))
            }
        }
        rankListBody.append(HTML.tr(_rank+_name+_sum+HTML.td(uac_cnt)+_proset.toString()));
    }
    for (let i = 0; i < skipped.length; i++) {
        rankListBody.append(skipped[i]);
    }
}

function loadRankMinTime(res) {
    loadRankShortCode(res);
}

function loadRating() {
    //留坑
}

function newDiscuss(tp) {
    let reply = "";
    if (tp.answer != null && tp.answer.length !== 0) {
        reply = "<div class=\"replyreply\">" +
                "<div>" +
                    HTML.b("管理员回复") + "：" +
                "<p>"+
                    tp.answer +
                "</p>" +
            "</div>" +
                "<div>" +
                    tp.adate +
                "</div>"+
            "</div>";
    }

    let rp = "";
    if (contestInfo.is_admin) {
        rp = "<front style='float:right;'><a href=\"javascript:replyDiscuss("+tp.nid+")\">[回复]</a></front>";
    }

    let res = "<div class=\"panel panel-default\" style=\"word-break: break-all;\" id=\""+tp.nid+"\">" +
        "<div class=\"panel-heading\">" +
            "<a href=\"#"+tp.nid+"\">#"+tp.nid+"&nbsp;</a>" +
                getStyleName(tp.username) + " at " + tp.qdate + "&nbsp;&nbsp;" +
            rp +
        "</div>" +
        "<table style=\"width: 100%\">" +
            "<tbody>" +
                "<tr>" +
                    "<td class=\"replyRight\">" +
                        "<div class=\"replyText\">" +
                            (tp.num!=null?contestInfo["problemset"][tp.num].title:"For This Contest") + " : " + tp.question +
                        "</div>" +
                        "<div class=\"reply\">" +
                            reply +
                        "</div>" +
                    "</td>" +
                "</tr>" +
            "</tbody>" +
        "</table>" +
        "</div>";
    return res;
}

function replyDiscuss(_nid) {
    nid = _nid;
    $("#hidden-a-rp").click()
}

function replyDis() {
    let r = $("#replyDis textarea").val();
    if (r == null || r.length === 0) {
        narn('log','不能回复空文本')
    } else {
        $.ajax({
            url: "/api/contest/notice/reply/" + nid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "answer": r,
            }),
            success: function (res) {
                if (res.state === 0) {
                    narn('success', '回复成功')
                    loadDiscuss()
                    $("#replyDis textarea").val('')
                } else {
                    narn('log', '回复失败')
                }
            }
        })
    }
}

function loadDiscuss() {
    $.getJSON("/api/contest/notice/"+cid, function (res) {
        let discussList = $("#discussList");
        discussList.empty()
        for (let i = 0; i < res.length; i++) {
            discussList.append(newDiscuss(res[i]))
        }
    })
}

function getGapMin(e,s) {
    let _tp = Math.floor(((new Date(e)).getTime() - (new Date(s)).getTime())/(1000));
    let _h = (Math.floor(_tp/3600)).toString();
    _h = _h.length<2?"0"+_h:_h;
    let _m = ((_tp/60).toFixed(0)%60).toString();
    _m = _m.length<2?"0"+_m:_m;
    let _s = (_tp%60).toString();
    _s = _s.length<2?"0"+_s:_s;
    return _h + ":" + _m+":"+_s;
}

function go2() {
    let time = $("#contestTime").text().split(':');
    if (time[2] > 0) {
        time[2] = parseInt(time[2]) - 1;
    } else if (time[1]>0) {
        time[1] = parseInt(time[1]) - 1;
        time[2] = 59;
    } else {
        time[0] = parseInt(time[0]) - 1;
        time[1] = time[2] = 59;
    }
    time[0] = time[0].length<2?"0"+time[0]:time[0];
    time[1] = time[1].length<2?"0"+time[1]:time[1];
    time[2] = time[2].length<2?"0"+time[2]:time[2];
    $("#contestTime").text(time[0]+":"+time[1]+":"+time[2])
}

function loadHome() {
    contestStartTime = new Date(contestInfo.starttime).getTime()
    contestEndsTime = new Date(contestInfo.endstime).getTime()

    if (!isEnds) {
        $("#toProButton").hide()
    } else {
        $("#toProButton").on("click",function() {
            location.href = "problem.jsp?pid="+contestInfo["problemset"][parseInt(location.hash.substr(2,5))].pid;
        });
    }

    contesttime = contestEndsTime - contestStartTime;

    $("title").text(contestInfo.title + " - DOJ")
    $("#contestTitle").text(contestInfo.title)
    $("#startTime").text(contestInfo.starttime)
    $("#endsTime").text(contestInfo.endstime)
    $("#contestDesc").html(contestInfo.desc)

    let problems = $("#problems");
    problems.empty()
    for (let i = 0; i < pnum; i++) {
        problems.append(HTML.li(HTML.a("#P"+i,getProChar(i))))
    }
    problems.find("li").first().addClass("active")


    let problemListBody = $("#problemList tbody");
    problemListBody.empty()
    for (let i = 0; i < pnum; i++) {
        let ac_cnt = contestInfo.problemset[i].ac_cnt;
        let all_cnt = contestInfo.problemset[i].all_cnt;
        let ra = ((ac_cnt / all_cnt)*100).toFixed(2);
        if (all_cnt === 0) {
            ra = "0.00%(0/0)";
        } else {
            ra = ra+"%(" + ac_cnt + "/" + all_cnt + ")";
        }
        let c = "default";
        if (contestInfo.problemset[i].state === 1) {
            c = "success";
        } else if (contestInfo.problemset[i].state === 2) {
            c = "danger";
        }
        mp1[contestInfo.problemset[i].pid] = i;
        problemListBody.append(HTML.trCNum(c,3,Array(getProChar(i),HTML.a("#P"+i,contestInfo.problemset[i].title),ra)));
    }

    let rankListHead = $("#rankList thead tr");
    let statusProblemSelect = $("#statusProblemSelect");
    let questionSelect = $("#questionSelect");
    let problemSelect = $("#problemSelect");
    rankListHead.html(HTML.th("#")+HTML.th("Name")+HTML.th("P")+HTML.th("C"))
    questionSelect.empty()
    problemSelect.empty()
    statusProblemSelect.html("<option value=\"-1\" selected=\"selected\">All</option>")
    questionSelect.html("<option value=\"-1\" selected=\"selected\">For this contest</option>")
    for (let i = 0; i < pnum; i++) {
        rankListHead.append(HTML.th(HTML.a("#P"+i,getProChar(i))));
        statusProblemSelect.append(HTML.optionValue(i,getProChar(i) + " - " + contestInfo["problemset"][i].title));
        questionSelect.append(HTML.optionValue(i,getProChar(i) + " - " + contestInfo["problemset"][i].title));
        problemSelect.append(HTML.optionValue(i,getProChar(i) + " - " + contestInfo["problemset"][i].title));
    }
}

function toPage(page) {
    let statusPage = $("#statusPage");
    if (!page || page <= 0) page = 1;
    pageList(statusPage, page, spnum);
    loadStatus(null,null,null,null,page);
}

function statusSearch() {
    let _pid = $("#statusProblemSelect").val();
    let _username = $("#statusUser").val();
    let _lang = $("#statusLangSelect").val();
    let _res = $("#statusResultSelect").val();
    loadStatusPages(_username,_pid,_res,_lang,1)
}

function submit() {
    let _pid = contestInfo["problemset"][parseInt($("#problemSelect").val())].pid;
    let _lang = $("#problemLang option:selected").val();
    let _code = $("#problemCode").val();
    if (_code.length >= 20000) {
        alert("代码长度不能超过20000");
        return;
    }
    if (contestEndsTime <= new Date().getTime()) {
        narn("log", "比赛已结束，请跳转至题库提交！");
    } else {
        $.ajax({
            url: "/api/contest/submit/" + cid + "/" + _pid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "lang": _lang,
                "code": addSlashes(_code),
            }),
            success: function (res) {
                if (res.state === 0) {
                    //成功提交
                    narn("success", "代码成功提交");
                } else {
                    //未报名
                    narn("error", "您没有报名本场比赛");
                }
                location.hash = "#Status";
            },
            error: function (e) {
                console.log(e);
            }
        })
    }
}

function askQuestion() {
    if (isEnds) {
        narn('log','比赛已结束，不能再提问了')
    } else {
        let _pid = $("#questionSelect").val();
        let _text = $("#questionText").val();
        $.ajax({
            url: "/api/contest/notice/" + cid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "num": _pid,
                "question": _text,
                "date": new Date().Format("yyyy-MM-dd hh:mm:ss"),
            }),
            success: function (res) {
                if (res.state === 0) {
                    $("#questionText").val("");
                    narn("success", "问题成功提交");
                } else {
                    narn("warn", "您不是本场比赛人员");
                }
            },
            error: function (e) {
                console.log(e);
                narn("error", "提问失败");
            }
        })
    }
}

function getProRes1(res) {
    switch(res) {
        case 0:
            return "Accepted";
        case 1:
            return "Presentation Error";
        case 2:
            return "Time Limit Exceeded";
        case 3:
            return "Memory Limit Exceeded";
        case 4:
            return "Wrong Answer";
        case 5:
            return "Runtime Error";
        case 6:
            return "Output Limit Exceeded";
        case 7:
            return "Waiting";
        case 8:
            return "System Error";
        case 9:
            return "Compilation Error";
    }
}

function submitInfo(res) {
    return "/** \n" +
        " * @Author: " + res.username + "\n" +
        " * @Date: " + res.date + "\n" +
        " * @PID: " + res.pid + "\n" +
        " * @Result: " + getProRes1(res.result) + "\n" +
        " * Powered By DOJ" + "\n" +
        "*/\n";
}

function showCode(_sid) {
    $.getJSON("/api/contest/getcode?sid="+_sid,function (res) {
        if (res.state !== 0) {
            narn("log","您现在不能查看别人的代码");
        } else {
            $("#showCodeBtn").click()
            $(".hljs code").html(hljs.highlightAuto(submitInfo(res)+res.code).value)
            $("code").each(function(){
                $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>")
            })
        }
    })
}

function getProChar(num) {
    if (num >= 26) {
        return String.fromCharCode(parseInt(num/26) + 65)+String.fromCharCode(num%26+65);
    } else {
        return String.fromCharCode(num+65);
    }
}

function getProRes(res,sid,score) {
    let c = "";
    if (typeof sid=="undefined" || sid === "" || sid == null) {
        c = "Compilation Error";
    } else {
        c = HTML.a("statusInfo.jsp?sid="+sid,"Compilation Error")
    }
    let s;
    switch(res) {
        case 0:
            s = "<span class=\"label label-success\">Accepted</span>";break;
        case 1:
            s =  "<span class=\"label label-info\">Presentation Error</span>";break;
        case 2:
            s = "<span class=\"label label-warning\">Time Limit Exceeded</span>";break;
        case 3:
            s = "<span class=\"label label-warning\">Memory Limit Exceeded</span>";break;
        case 4:
            s = "<span class=\"label label-danger\">Wrong Answer</span>";break;
        case 5:
            s = "<span class=\"label label-warning\">Runtime Error</span>";break;
        case 6:
            s = "<span class=\"label label-warning\">Output Limit Exceeded</span>";break;
        case 7:
            s =  "<span class=\"label label-default\">Waiting</span>";break;
        case 8:
            s = "<span class=\"label label-primary\">System Error</span>";break;
        case 9:
            s = "<span class=\"label label-info\">"+c+"</span>";break;
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

function editContestInfo() {
    location.href = 'editcontest.jsp?cid='+contestInfo.cid;
}

function onChange(s) {
    if (s === '' || s == null) {
        return ;
    }
    location.hash = s;
    href = s;
    //run();
}

function addSlashes(str) {
    str = str.replace(/\\/g,"\\\\");
    str = str.replace(/\"/g,"\\\"");
    return str;
}