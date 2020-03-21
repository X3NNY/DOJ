let username = getUrlParam("username");
let uid = getUrlParam("uid");
let d = [], d2 = [];

let bpage=1, bnums;
let flag = true;

let s = {
    colors: ['#000000', '#000000', '#50B432', '#DDDF00', '#24CBE5', '#64E572', '#FF9655', '#FFF263', '#6AF9C4'],
    chart: {
        backgroundColor: {
            linearGradient: {x1: 0, y1: 0, x2: 0, y2: 0},
            stops: [
                [0, 'rgb(255, 255, 255)'],
                [1, 'rgb(240, 240, 255)']
            ]
        },
        borderWidth: 0,
        plotBackgroundColor: 'rgba(255, 255, 255, .9)',
        plotShadow: true,
        plotBorderWidth: 1
    },
    title: {
        text: ''
    },
    plotOptions: {
        series: {
            marker: {
                enabled: true,
                fillColor: '#ffffff',
                lineWidth: 2,
                radius: 2,
                lineColor: null // inherit from series
            }
        }
    },
    xAxis: {
        gridLineWidth: 0,
        lineColor: '#000',
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, sans-serif'

            }
        },
        type: 'datetime',
        dateTimeLabelFormats: {
            millisecond: '%Y-%m-%e',
            second: '%Y-%m-%e',
            minute: '%Y-%m-%e',
            hour: '%Y-%m-%e',
            day: '%Y年%m月',
            week: '%Y年%m月',
            month: '%Y年%m月',
            year: '%Y年%m月'
        }
    },
    yAxis: {
        allowDecimals: false,
        gridLineWidth: 0,
        lineColor: '#000',
        lineWidth: 1,
        tickWidth: 1,
        tickColor: '#000',
        labels: {
            style: {
                color: '#000',
                font: '11px Trebuchet MS, Verdana, sans-serif'
            }
        },
        title: {
            text: '',
            style: {
                color: '#333',
                fontWeight: 'bold',
                fontSize: '12px',
                fontFamily: 'Trebuchet MS, Verdana, sans-serif'
            }
        },
        plotBands: [
            {
                from: -10000,
                to: 1000,
                color: 'rgb(165,165,165)'
            }, {
                from: 1000,
                to: 1200,
                color: 'rgba(119,255,119,1)'
            }, {
                from: 1200,
                to: 1400,
                color: 'rgb(143,255,219)'
            }, {
                from: 1400,
                to: 1700,
                color: 'rgb(83,95,255)',
            }, {
                from: 1700,
                to: 2000,
                color: 'rgba(255,136,255,1)',
            }, {
                from: 2000,
                to: 2200,
                color: 'rgba(255,204,136,1)',
            }, {
                from: 2200,
                to: 2500,
                color: 'rgb(255,0,0)',
            },{
                from: 2500,
                to: 10000,
                color: 'rgb(255,0,0)',
            }]
    },
    tooltip: {
        formatter: function () {
            return '<b>' + this.series.name + "</b><br/>" +
                Highcharts.dateFormat('%Y-%m-%e', this.x) + " Rating:" + this.y + this.point.text;
        }
    },
    series: [{
        name: '',
        data: d
    }]
};
let data = [];
uid = uid==null?'':uid;
username = username==null?'':username;

function loadMain() {
    $.getJSON("/api/user/info?uid=" + uid + "&username=" + username, function (res) {
        username = res.username;
        uid = res.uid;

        $("#blog div.panel-heading").text(username + " 的博客")
        $("#index div.panel-primary:first").css("background", "url('/media/" + res.uid + "/bg-pic.png')")
        HTML.loadCss("/media/" + res.uid + "/css/user.css")
        HTML.loadJs("/media/" + res.uid + "/js/user.js")

        $("title").text(res.username + " - DOJ")
        $("#usernameS").html(getStyleName(res.username + "|" + res.rating))
        $("#uid").text(res.uid)
        s.series[0].name = res.username;
        if (res.image != null && res.image.length > 0)
            $("#userImage").attr("src", res.image)
        scoreBar(res.level)
        $("#userRating").html(res.rating == 0 ? "-" : getStyleRating(res.rating))
        $("#userMaxRating").html(res.maxscore == 0 ? "-" : getStyleRating(res.rating))
        $("#registerDate").text(res.registerdate)
        $("#lastVisitDate").text(res.lastvisit)
        $("#userCntInfo").text("迄今为止一共AC了" + res.ac_cnt + "次，一共提交过" + res.all_cnt + "次。")
        $("#userSchool").text(res.school)
        $("#userNote").text((res.note == null || res.note == "") ? "这个人什么都没写..." : res.note)

        for (let i = 0; i < res["contest"].length; i++) {
            let tp = res["contest"][i];
            data.push({
                "time": new Date(tp.starttime).getTime(),
                "rating": tp.score,
                "text": '(' + (tp.addscore >= 0 ? ('+' + tp.addscore) : tp.addscore) + ')<br />' + tp.title + " Rank: " + tp.rank + "/" + tp.sum
            });
        }
        let m = 10000;
        for (let i = 0; i < data.length; i++) {
            d[i] = {x: data[i].time, y: data[i].rating, text: data[i].text};
            m = (m < d[i].y ? m : d[i].y);
        }

        s.series[0].data = d;
        $.getJSON("/api/user/curuser", function (res) {
            if (res.state === 1) {
                flag = false;
                $("#navigation ul li:last").hide()
            }
            if (res.state === 0 && res.username !== username) {
                flag = false;
                $("#navigation ul li:last").hide()

                loadSeries2(res.uid, m)
            } else {
                s.yAxis.min = m - 100;
                $('#userContestInfo').highcharts(s);
            }

            if (flag) {
                $("#userImage").on("click", function () {
                    changeImage()
                })
            }
        })
    })
}

function changeImage() {
    $("#hidden-a").click()
}

function loadSeries2(ruid,m) {
    $.getJSON("/api/visual/user/rating?uid="+ruid,function (res) {
        for (let i = 0; i < res["contest"].length; i++) {
            let tp = res["contest"][i];
            d2[i] = {
                x: new Date(tp.starttime).getTime(),
                y: tp.score,
                text: '('+(tp.addscore>=0?('+'+tp.addscore):tp.addscore)+')<br />'+tp.title + " Rank: "+tp.rank+"/"+tp.sum
            }
            m = Math.min(m,d2[i].y);
        }
        s.yAxis.min = m - 100;
        s.series[1] = {
            name: res.username,
            data: d2
        };
        $('#userContestInfo').highcharts(s)
        $('#userContestInfo').highcharts().series[1].hide()
    })
}

function loadStatistics() {
    loadPicMessage1()
    loadPicMessage2()
}

function loadBlog() {
    $.getJSON("/api/blog/set?page="+bpage+"&name="+username, function (res) {
        bnums = res.pages;
        let blogList = $("#blog table tbody");

        let blogPage = $("#blogPage");
        pageList(blogPage,bpage,bnums)

        blogList.empty()
        for (let i = 0; i < res["blogset"].length; i++) {
            let tp = res["blogset"][i];
            if (tp.author.split('|')[1] !== username) continue;
            blogList.append(HTML.tr(HTML.td(tp.bid)+HTML.tdC(HTML.a("/blog.jsp?bid="+tp.bid,tp.title))+HTML.tdC(getStyleName(tp.author))+HTML.tdC(tp.date)+getVote(tp.bid,tp.up_vote,tp.down_vote,tp.my_vote)));
        }
    })
}

function loadTeam() {
    
}

function loadCollet() {
    loadProCollect(1)
}

function loadSetting() {
    
}

function loadProCollect(_page) {
    $("#collectInfo h3").text('题目收藏')
    $.getJSON("/api/problem/getcollect?uid="+uid, function (res) {
        let tbody = $("#collectInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            tbody.append(HTML.tr(HTML.td(res[i].id)+HTML.td(HTML.a('problem.jsp?pid='+res[i].pid,res[i].pid+' - '+res[i].title))+HTML.td(res[i].date)))
        }
    })
}

function loadConCollect(_page) {
    $("#collectInfo h3").text('比赛收藏')
    $.getJSON("/api/contest/getcollect?uid="+uid, function (res) {
        let tbody = $("#collectInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            tbody.append(HTML.tr(HTML.td(res[i].id)+HTML.td(HTML.a('contest.jsp?cid='+res[i].cid,res[i].title))+HTML.td(res[i].date)))
        }
    })
}

function loadStaCollect(_page) {
    $("#collectInfo h3").text('评测收藏')
    $.getJSON("/api/submit/getcollect?uid="+uid, function (res) {
        let tbody = $("#collectInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            tbody.append(HTML.tr(HTML.td(res[i].id)+HTML.td(HTML.a('code.jsp?sid='+res[i].sid,"#"+res[i].sid))+HTML.td(res[i].date)))
        }
    })
}

function loadBloCollect(_page) {
    $("#collectInfo h3").text('博客收藏')
    $.getJSON("/api/blog/getcollect?uid="+uid, function (res) {
        let tbody = $("#collectInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            tbody.append(HTML.tr(HTML.td(res[i].id)+HTML.td(HTML.a('blog.jsp?bid='+res[i].bid,res[i].bid+' - '+res[i].title))+HTML.td(res[i].date)))
        }
    })
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

function loadPicMessage1() {
    let chart = null;
    $.getJSON('/api/visual/user/submit?uid='+uid+'&username='+username, function (data) {
        chart = Highcharts.chart('picMessage1', {
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

function loadPicMessage2() {
    var chart = null;
    $.getJSON('/api/visual/user/allpass?uid='+uid+'&username='+username, function (data) {
        chart = Highcharts.chart('picMessage2', {
            chart: {
                zoomType: 'x'
            },
            title: {
                text: 'AC总数统计'
            },
            subtitle: {
                text: document.ontouchstart === undefined ?
                    '选择一块区域可放大' : '手势操作进行缩放'
            },
            xAxis: {
                type: 'datetime',
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                }
            },
            tooltip: {
                dateTimeLabelFormats: {
                    millisecond: '%H:%M:%S.%L',
                    second: '%H:%M:%S',
                    minute: '%H:%M',
                    hour: '%H:%M',
                    day: '%Y-%m-%d',
                    week: '%m-%d',
                    month: '%Y-%m',
                    year: '%Y'
                },
                formatter: function() {
                    return '<b>时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e',this.x)+'<br>'+
                        '<b>总AC数:</b>' + this.y;
                }
            },
            yAxis: {
                title: {
                    text: 'AC数'
                }
            },
            legend: {
                enabled: false
            },
            plotOptions: {
                area: {
                    fillColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, Highcharts.getOptions().colors[0]],
                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                        ]
                    },
                    marker: {
                        radius: 2
                    },
                    lineWidth: 1,
                    states: {
                        hover: {
                            lineWidth: 1
                        }
                    },
                    threshold: null
                }
            },
            series: [{
                type: 'area',
                name: 'AC总数',
                data: (function (data) {
                    let res = [];
                    data = quickOrder(data);
                    for (let i = 0; i < data.length; i++) {
                        res.push([data[i]*1000,i+1])
                    }
                    return res;
                })(data)
            }]
        });
    });
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
                searchBlog();
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
                searchBlog();
            } else {
                narn("log","您现在不能投票");
            }
        }
    })
}

function toPage(_page) {
    bpage = _page;
    loadBlog()
}

const level = [0,1000,4000,10000,20000,50000];
function scoreRake(score){
    let len = level.length;
    let i = getUserLevel(score);
    let min,max,rake;
    if(i === 0)
        return 0;
    if(i >= len){
        return 100;
    }
    min = level[i-1];
    max = level[i];
    if(score>min && score<=max)
        return (score-min)*100/(max-min);
    return 0;
}

function getUserLevel(score){
    let i = 0;
    for(;i<level.length;i++){
        if(score <= level[i])break;
    }
    return i;
}
function scoreBar(score){
    let rake=scoreRake(score);
    $("#scoreBar").css('width',rake+'%')
    let l=getUserLevel(score);
    let ns; // next level's need score
    if(l>=6){
        l=5;
        ns ="/-";
    } else {
        ns = "/"+level[l];
    }
    $("#scoreDisplay").html('('+score+ns+') '+HTML.textC('user_level_'+l,'Lv.'+l));
}

function loadAchi() {
    $.getJSON("/api/user/getachi", function (res) {
        $("#chooseAchi div.modal-body span").html(getStyleName(res.username))
        let mtable = $("#chooseAchi div.modal-body table tbody");
        mtable.empty()
        for (let i = 0; i < res.achis.length; i++) {
            let t = res.achis[i];
            mtable.append(HTML.tr(HTML.td(t.aid) +
                HTML.td(t.title) +
                HTML.td(t.desc) +
                HTML.td(t.type?"前缀":"后缀") +
                HTML.td(t.date) +
                HTML.td(t.vaildday) +
                HTML.td(HTML.a('javascript:chooseAchi('+t.aid+')','使用'))
            ))
        }
    })
}

function chooseAchi(aid) {
    $.ajax({
        url: "/api/user/chooseachi?aid="+aid,
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn('success','更换展示成就成功')
                loadAchi()
            } else {
                narn('log','更换失败！您未登录或是未拥有本成就')
            }
        }
    })
}

function uploadImage() {
    let formData = new FormData($("#form1")[0]);
    $.ajax({
        url: "/api/user/upload/image",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        async : false,
        success: function () {
            narn("success",'上传成功')
            loadMain()
        }
    })
}
function uploadImage2() {
    let formData = new FormData($("#form2")[0]);
    $.ajax({
        url: "/api/user/upload/bgimage",
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        async : false,
        success: function () {
            narn("success",'上传成功')
            loadMain()
        }
    })
}

loadMain()