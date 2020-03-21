let hash;
let bid;
$.getJSON("/api/index/blog",function (res) {
    bid = res.bid;
    loadNotice();
})
function hashInfo() {
    hash = location.hash;

    switch (hash) {
        case "#slogout":
            narn("log","注销成功！");break;
        case "#slogin":
            getGreetingMsg();break;
        case "#sregister":
            narn("success","激活成功");break;
    }
}

function getGreetingMsg() {
    $.getJSON("/api/user/curuser",function (res) {
        if (res.state !== 0) return "";
        let username = res.username;
        let info = "";
        let time = new Date().getHours();
        if (time < 6) {
          info = "凌晨好，"+username+"，新的一天开始了。";
        } else if (time < 11) {
            info = "上午好，"+username;
        } else if (time < 14) {
            info = "中午好，"+username;
        } else if (time < 19) {
            info = "下午好，"+username;
        } else if (time < 24) {
            info = "晚上好，"+username;
        }
        narn("success",info);
    })
}

function loadNotify() {
    $.getJSON("/api/about/notify", function (res) {
        if (res.msg && res.msg.length !== 0) {
            $("#Notify").show()
            $("#Notify span").html(res.msg)
        }
    })
}

function loadNotice() {
    $.getJSON("/api/blog/info/"+bid,function (res) {
        $("#Notice .panel-heading font").text(res.title);
        $("#Notice .panel-body").html(res.text);
        $("#Notice .panel-heading front").append(HTML.frontSR("Written by "+getStyleName(res.username)+" at "+res.date + " " + HTML.a("blog.jsp?bid="+bid,"<span class=\"glyphicon glyphicon-arrow-right\" style=\"color: #fff;\"></span>")));

        let footer = $("#Notice .panel-footer");
        footer.append(HTML.frontSL(getVote(bid,res.up_vote,res.down_vote,res.my_vote)));
        footer.append(HTML.frontSR( res.num + " replies."));
        footer.append("<br />");

        hLight();
    })
}

function loadContests() {
    $.getJSON("/api/contest?page=1",function (res) {
        let Contests = $("#Contests table tbody");
        for (let i = 0; i < Math.min(5,res.contests.length); i++) {
            let tp = res["contests"][i];
            let c="";
            if (new Date().getTime() < new Date(tp.starttime).getTime()) {
                c = HTML.a("contestR.jsp?cid="+tp.cid,"[报名]");
            } else if (new Date().getTime() < new Date(tp.endstime).getTime()) {
                c = "[进行中]";
            } else {
                c = "[已结束]";
            }
            c += "<span class=\"glyphicon glyphicon-user\" style=\"color: rgb(42, 39, 156);\"></span>x"+tp.num;
            Contests.append(HTML.tr(HTML.td(tp.cid)+HTML.td(tp.title)+HTML.td(tp.starttime)+HTML.td(c)));
        }
    })
}

function loadProblems() {
    $.getJSON("/api/problem/toindex",function (res) {
        let Problems = $("#Problems table tbody");
        for (let i = 0; i < res.length; i++) {
            let tp = res[i];
            let ra = ((tp.ac_cnt / tp.all_cnt)*100).toFixed(2);
            if (tp.all_cnt === 0) {
                ra = "0.00%(0/0)";
            } else {
                ra = ra + "%(" + tp.ac_cnt + "/" + tp.all_cnt + ")";
            }

            let ts = HTML.td(tp.pid) + HTML.tdC(HTML.a("/problem.jsp?pid=" + tp.pid, tp.title)) + HTML.tdC(ra) + HTML.tdC(getStyleName(tp.author));

            if (tp.state === 0) {
                Problems.append(HTML.tr(ts));
            } else if (tp.state === 1) {
                Problems.append(HTML.trClass("success",ts));
            } else {
                Problems.append(HTML.trClass("danger",ts));
            }
        }
    })
}

function loadTop10() {
    $.getJSON("/api/rank/toindex",function (res) {
        let Top = $("#Top10 table tbody");
        for (let i = 0; i < res.rankset.length; i++) {
            let tp = res["rankset"][i];
            Top.append(HTML.tr(HTML.td(i+1)+HTML.td(getStyleName(tp.username)+HTML.tdR(tp.rating))));
        }
    })
}

function loadBlogs() {
    $.getJSON("/api/blog/toindex",function (res) {
        let Blogs = $("#Blogs table tbody");
        let tot = 0;
        for (let i = 0; i < res["blogset"].length; i++) {
            let tp = res["blogset"][i];
            Blogs.append(HTML.tr(HTML.td(tp.bid) + HTML.tdC(HTML.a("/blog.jsp?bid=" + tp.bid, tp.title)) + HTML.tdR(getStyleName(tp.author))));
            tot++;
        }
    })
}
function loadThing() {
    $.getJSON("/api/user/edit",function (res) {
        if (res.note != null){
            $("#Thing div.panel-body").text(removeHtml(res.note))
        } else {
            $.getJSON("/api/about/index_thing", function (res) {
                $("#Thing div.panel-body").text(res.msg)
            })
        }
    })
}

function search() {
    let name = $("#Search input").val();
    $.ajax({
        url: "/api/user/search?name="+name,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 1) {
                location.href = "userinfo.jsp?uid="+res.uid;
            } else {
                narn("log","该用户不存在");
            }
        }
    })
}

function addStar(bid) {
    $.ajax({
        url: "/api/blog/upvote?bid="+bid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("success","点赞成功");
                loadNotice();
            } else {
                narn("log","您现在不能投票");
            }
        }
    })
}

function subStar(bid) {
    $.ajax({
        url: "/api/blog/downvote?bid="+bid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("success","点踩成功");
                loadNotice();
            } else {
                narn("log","您现在不能投票");
            }
        }
    })
}

function hLight() {
    // $("pre").addClass("hljs");
    $('pre code').each(function(i, block) {
        hljs.highlightBlock(block);
    });
    $("code").each(function(){
        $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>");
    });
}

function processData(data) {
    let time = new Date(new Date().getTime() - 30*86400000).getTime();
    time = time - time%(86400000);
    let res = [];
    let j = 0;
    for (let i = 0; i < 30; i++) {
        if (typeof data[j] == "undefined" || data[j][0] - data[j][0]%(86400000) !== time) {
            res.push([time,0])
        } else {
            res.push([time,data[j][1]])
            j++;
        }
        time += 86400000;
    }
    return res;
}

function loadPicMessage() {
    let chart = null;
    $.getJSON('/api/visual/index/submit', function (data) {
        chart = Highcharts.chart('picMessage', {
            title: {
                text: '30天内提交记录统计'
            },
            xAxis: {
                type: 'datetime',
                tickmarkPlacement: 'on',
                dateTimeLabelFormats:{
                    day: '%m-%e',
                },
                title: {
                    enabled: false
                }
            },
            yAxis: [{ // 第一个 Y 轴，放置在左边（默认在坐标）
                allowDecimals:false,
                title: {
                    text: '提交数'
                },
                min:0,
                labels: {
                    formatter: function() {
                        return this.value;
                    }
                }
            }],
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                shared: true,
                valueSuffix: '',
                formatter: function() {
                    return '<b>开始时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x)+'<br>'+
                        '<b>结束时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x+86400000)+'<br>'+
                        '<b>总提交数:</b>' + this.points[0].y+"<br>"+
                        '<b>总通过数:</b>' + this.points[1].y;
                }
            },
            series: [{
                name: 'submit',
                data: processData(data.submit),
            },{
                name: 'ac',
                data: processData(data.ac),
            }]
        });
    });
}

loadNotify()
loadBlogs();
loadContests();
loadProblems();
loadTop10();
loadThing();
hashInfo();

loadPicMessage();