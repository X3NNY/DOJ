let pid=getUrlParam("pid");
let problemInfo;
let langList = ["GCC","G++","JAVA","Python"];
let replyCid;
let bid = -1;
if (pid==='' || pid==null) {pid=1;}

$.getJSON("/api/problem?pid=" + pid, function (res) {
        problemInfo = res;
        init();
        loadComment();

        if ($("#tab3 table").length !== 0) {
            loadTab3();
        }
    }
);

function init() {
    $("title").text(problemInfo.title + " - DOJ");
    $("#problemTitle").text(pid + " - " + problemInfo.title);
    $("#problemInput").html(problemInfo.input);
    $("#problemOutput").html(problemInfo.output);
    $("#problemSampleInput").html(problemInfo.sampleinput);
    $("#problemSampleOutput").html(problemInfo.sampleoutput);
    $("#problemDesc").html(problemInfo.desc);
    $("#problemTimeLimit").text(problemInfo.timelimit);
    $("#problemMemoryLimit").text(problemInfo.memorylimit);
    if (problemInfo.state === 1) {
        $("#problemIsSubmit").html("<font color=\"green\"><span class=\"glyphicon glyphicon-ok\"></span>已AC</font>");
    } else if (problemInfo.state === 2) {
        $("#problemIsSubmit").html("<font color=\"red\"><span class=\"glyphicon glyphicon-remove\"></span>未AC</font>");
    }

    if (problemInfo.collect) {
        $("#problemIsCollection").html("<font color=\"green\"><span class=\"glyphicon glyphicon-star\"></span>已收藏</font>");
    }

    if (problemInfo.hint == null || problemInfo.hint === '') {
        $("#problemHint").parent().hide();
    } else {
        $("#problemHint").html(problemInfo.hint);
    }

    switch(problemInfo.type) {
        case 0:
            $("#problemType").text("传统题");break;
        case 1:
            $("#problemType").text("交互题");break;
        case 2:
            $("#problemType").text("接口实现题");break;
        case 3:
            $("#problemType").text("提交答案题");break;
    }

    $("#problemSpecial").text(problemInfo.special?"Yes":"No");
    $("#problemAuthor").html(getStyleName(problemInfo.username));

    $.getJSON("/api/submit?pid="+pid, function(res) {
        $("#problemSelect").text(problemInfo.title);

        let problemLang = $("#problemLang");
        problemLang.empty();
        let blacklist = [];
        for (let i = 0; i < res.length; i++) {
            blacklist.append(res[i]);
        }
        for (let i = 0; i < langList.length; i++) {
            if (blacklist.indexOf(langList[i]) === -1) {
                problemLang.append(HTML.option(langList[i]));
            }
        }
    });

    $.getJSON("/api/problem/info/" + pid, function (res) {
        if (res.tag != null && res.tag.length !== 0) {
            let tagBody = $("#problemTag tbody");
            tagBody.empty();
            res.tag.sort(function (a,b) {
                return a.cnt < b.cnt;
            })
            for (let i = 0; i < res.tag.length; i++) {
                let tag = res["tag"][i];
                if (tag["state"] === 1) {
                    tagBody.append(HTML.trClass("success", HTML.td(tag.tag) + HTML.td(tag.tag)));
                } else {
                    tagBody.append(HTML.tr(HTML.td(tag.tag) + HTML.td(tag.tag)));
                }
            }
        }
        loadAcPic(res)
    });
}

function loadAcPic(res) {
    let all_cnt = res.ac_cnt + res.wa_cnt + res.tle_cnt + res.mle_cnt + res.re_cnt + res.se_cnt + res.ce_cnt + res.pe_cnt + res.ole_cnt;
    let colors = Highcharts.getOptions().colors,
        browserData = [{
            name: "AC",
            y: res.all_cnt===0?0:(res.ac_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[2],
        },{
            name: "Other",
            y: res.all_cnt===0?0:((1-res.ac_cnt/all_cnt)*100).toFixed(2)*1,
            color: colors[4]
        }],
        versionsData = [{
            name: "AC",
            y: res.all_cnt===0?0:(res.ac_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[2],
        },{
            name: "RE",
            y: res.all_cnt===0?0:(res.re_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[8],
        },{
            name: "MLE",
            y: res.all_cnt===0?0:(res.mle_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[4]
        },{
            name: "WA",
            y: res.all_cnt===0?0:(res.wa_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[8],
        },{
            name: "TLE",
            y: res.all_cnt===0?0:(res.tle_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[3],
        },{
            name: "OLE",
            y: res.all_cnt===0?0:(res.ole_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[6],
        },{
            name: "CE",
            y: res.all_cnt===0?0:(res.ce_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[7],
        },{
            name: "SE",
            y: res.all_cnt===0?0:(res.se_cnt/all_cnt*100).toFixed(2)*1,
            color: colors[8],
        }];
// Create the chart
    Highcharts.chart('picMessage', {
        chart: {
            type: 'pie'
        },
        title: {
            text: null
        },
        subtitle: {
            text: ''
        },
        yAxis: {
            title: {
                text: ''
            }
        },
        plotOptions: {
            pie: {
                shadow: false,
                center: ['50%', '50%']
            }
        },
        tooltip: {
            valueSuffix: '%'
        },
        series: [{
            name: '提交占比',
            data: browserData,
            size: '60%',
            dataLabels: {
                formatter: function () {
                    return this.y > 5 ? this.point.name : null;
                },
                color: '#ffffff',
                distance: -30
            }
        }, {
            name: '提交占比',
            data: versionsData,
            size: '100%',
            innerSize: '60%',
            dataLabels: {
                formatter: function () {
                    return this.y > 2 ? '<b>' + this.point.name + '</b> ': null;
                },
                distance: -20
            },
        }],
    });
}

function proSubmit() {
    let code = addSlashes($("#problemCode").val());
    if (code.length >= 15000) {
        narn("log","代码长度不能大于15000")
    } else {
        $.ajax({
            url: "/api/submit?pid=" + pid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "lang": $("#problemLang option:selected").text(),
                "code": code
            }),
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "提交成功");
                    location.href = "/status.jsp";
                } else if (res.state === 1) {
                    location.href = "/userlogin.jsp";
                } else if (res.state === 2) {
                    narn("error", "提交失败");
                }
            }
        })
    }
}

function loadComment() {
    $("#problemDisBlog").hide();
    $.getJSON("/api/problem/discuss/"+pid,function (res) {
        let problemDiscuss = $("#problemDiscuss tbody");
        for (let i = 0; i < res.length; i++) {
            let tp = res[i];
            problemDiscuss.append(HTML.tr(HTML.td(tp.bid)+HTML.tdC(HTML.a("javascript:showDiscuss("+tp.bid+")",tp.title))+HTML.tdC(getStyleName(tp.username))+HTML.tdC(tp.date)+getVote(tp.bid,tp.up_vote,tp.down_vote,tp.my_vote)));
        }
    })
}

function showDiscuss(id) {
    $("#problemDiscuss").hide();
    $("#problemDisBlog").show();
    $("#problemDiscussTitle").html("评论"+"<front style=\"float: right\"><a href=\"javascript:returnDis()\">返回</a></front>")
    bid = id;
    $.getJSON("/api/blog/info/"+bid, function (res) {
        $("#blogTitle").html(HTML.b(res.title))
        $("#blogTitle").append("<front style=\"float: right;\"><a href=\"blog.jsp?bid="+bid+"\"><span class=\"glyphicon glyphicon-arrow-right\" style=\"color: #fff;\"></span></a></front>")
        if (res.image != null) $("#blogInfo div img").attr("src",res.image);
        $("#blogUsername").html(HTML.a("/userinfo.jsp?uid="+res.uid,getStyleName(res.username)));

        $("#blogText").html(res.text);

        let blogFooter = $("#blogFooter");
        blogFooter.empty();
        blogFooter.append(HTML.frontSL(getVote(0,res.up_vote,res.down_vote,res.my_vote)));
        blogFooter.append(HTML.frontSR("Written by "+getStyleName(res.username)+" at "+res.date + ", " + res.num + " replies."));
        blogFooter.append("<br />");

        loadBlogComments();
    })
}

function returnDis() {
    $("#problemDisBlog").hide();
    $("#problemDiscuss").show();
    $("#problemDiscussTitle").text("所有评论");
    bid = -1;
}

function loadBlogComments() {
    $.getJSON("/api/blog/comments/"+bid, function (res) {
        let comments = $("#Commtents");
        comments.empty();
        let fl = 0;
        for (let i = 0; i < res.length; i++) {
            if (res[i].precid === -1) {
                fl++;
                comments.append(newComment(fl,res[i]));
            } else {
                $("#"+res[i].precid+" div.reply").append(newReply(res[i]));
            }
        }
    })
}

function newComment(fl,data) {
    return "<div class=\"panel panel-default\" style=\"word-break: break-all;\" id=\"" + data.cid + "\">" +
        "<div class=\"panel-heading\">" +
        "<a href=\"#" + data.cid + "\">#" + fl + "&nbsp;</a>" +
        "reply by " + getStyleName(data.username) + " at " + data.date +
        "<front style=\"float: right\">" + getVote(data.cid, data.up_vote, data.down_vote, data.my_vote) + "</front>" +
        "</div>" +
        "<table style=\"width: 100%\">" +
        "<tbody>" +
        "<tr>" +
        "<td class=\"replyInfo\">" +
        "<div>" +
        "<img src=\"" + data.image + "\" onerror=\"this.src='/media/user/default.jpg'\">" +
        "</div>" +
        "<div style=\"text-align: center;\">" +
        getStyleName(data.username) +
        "</div>" +
        "</td>" +
        "<td class=\"replyRight\">" +
        "<div class=\"replyText\">" +
        data.text +
        "</div>" +
        "<div class=\"reply\">" +
        "</div>" +
        "<div class=\"replyLink\"><a href=\"#blogReply\" data-toggle=\"modal\" onclick=\"setReply(" + data.cid + ")\">回复</a></div>" +
        "</td>" +
        "</tr>" +
        "</tbody>" +
        "</table>" +
        "</div>";
}

function newReply(data) {
    return "<div class=\"replyreply\" id=\"" + data.cid + "\">" +
        "<front style=\"float: left\">" +
        "<img src=\"" + data.image + "\" onerror=\"this.src='/media/user/default.jpg'\" />" +
        "</front>" +
        "<div>" +
        getStyleName(data.username) + "：" +
        "<p>" +
        data.text +
        "</p>" +
        "</div>" +
        "<div>" +
        data.date +
        // "<a href=\"javascript:reply("+data.precid+")\">回复</a>"+
        "</div>" +
        "</div>";
}

function setReply(id) {
    if (!id || id === '') id = '';
    replyCid = id;
}

function reply() {
    $.ajax({
        url: "/api/blog/reply?bid="+bid+"&cid="+replyCid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "text": tinyMCE.editors["editor"].getContent()
        }),
        success: function (res) {
            if (res.state === 0) {
                narn("success","成功");
                loadComments();
            } else {
                narn("log","回复失败");
            }
        }
    })
}

function addStar(id) {
    if (id === 0 || bid===-1) {
        let t = bid===-1?id:bid;
        $.ajax({
            url: "/api/blog/upvote?bid="+t,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success","点赞成功！");
                } else {
                    narn("log","您现在不能投票！");
                }
            }
        })
    } else {
        $.ajax({
            url: "/api/blog/comments/upvote?bid="+bid+"&id="+id,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success","点赞成功！");
                } else {
                    narn("log","您现在不能投票！");
                }
            }
        })
    }
}

function subStar(id) {
    if (id === 0 || bid === -1) {
        let t = bid===-1?id:bid;
        $.ajax({
            url: "/api/blog/downvote?bid="+t,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success","点踩成功");
                } else {
                    narn("log","您现在不能投票");
                }
            }
        })
    } else {
        $.ajax({
            url: "/api/blog/comments/downvote?bid="+bid+"&id="+id,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success","点踩成功");
                } else {
                    narn("log","您现在不能投票");
                }
            }
        })
    }
}

// function replyComment(cid) {
//     console.log(1);
// }

function collect() {
    $.ajax({
        url: "/api/problem/collect?pid=" + pid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                if ($("#problemIsCollection").find("font").length === 0) {
                    $("#problemIsCollection").html("<font color=\"green\"><span class=\"glyphicon glyphicon-star\"></span>已收藏</font>");
                    narn("success", "成功收藏");
                } else {
                    $("#problemIsCollection").html("<span class='glyphicon glyphicon-star-empty'></span>收藏");
                    narn("log", "成功取消收藏");
                }
            } else {
                narn("log", "未知错误");
            }
        }
    })
}

let isLoadTags = false;

function loadTags() {
    if (!isLoadTags) {
        isLoadTags = true;
        $.getJSON("/api/problem/tags", function (res) {
            let t = $("#tagSelect");
            for (let i = 0; i < res.length; i++) {
                t.append(HTML.optionValue(res[i].id,res[i].name))
            }
        })
    }
}

function addTag() {
    let tag = $("#tagSelect option:selected").val();
    if (tag === '-1') {
        narn("log","请选择一个标签进行添加！");
    } else {
        $.ajax({
            url: "/api/problem/add/tag?pid=" + pid+"&tid="+tag,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "标签添加成功");
                } else if (res.state === 1) {
                    narn("log", "您还没有登陆！");
                } else if (res.state === 2) {
                    narn("log", "您还没有AC本题，无法添加标签");
                } else if (res.state === 3) {
                    narn("log", "您已经添加过这个标签了");
                }
            }
        })
    }
}

function loadTab3() {
    let table = $("#tab3 table tbody");
    $.getJSON($("#judgeUrl").val()+"/api/problem/down/"+pid+"?token="+$("#token").val(),function (res) {
        if (res.state !== 0) {
            narn("log","题目数据获取失败！");
            return ;
        }
        for (let i = 0; i < res.data.length; i++) {
            table.append(HTML.tr(HTML.td(res.data[i].name)+HTML.td(HTML.a(res.data[i].link+"&token="+$("#token").val(),"点击下载"))));
        }
    })
}

function addSlashes(str) {
    str = str.replace(/\\/g,"\\\\");
    str = str.replace(/\"/g,"\\\"");
    return str;
}