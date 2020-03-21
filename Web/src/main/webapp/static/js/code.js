let sid = getUrlParam("sid");

function submitInfo(res) {
    return "/** \n" +
            " * @Author: " + res.username + "\n" +
            " * @Date: " + res.date + "\n" +
            " * @PID: " + res.pid + "\n" +
            " * @Result: " + getProRes(res.result,res.score,res.pos) + "\n" +
            " * Powered By DOJ" + "\n" +
            "*/\n";
}

function getProRes(res,score,pos) {
    let flag;
    if (score == null || score === '') {
        flag = '';
    } else {
        flag = score;
    }
    switch(res) {
        case 0:
            return "Accepted";
        case 1:
            return "PE on test "+pos;
        case 2:
            return "TLE on test "+pos;
        case 3:
            return "MLE on test "+pos;
        case 4:
            return "WA on test "+pos;
        case 5:
            return "RE on test "+pos;
        case 6:
            return "OLE on test "+pos;
        case 7:
            return "Waiting";
        case 8:
            return "System Error";
        case 9:
            return "Compilation Error";
    }
}

$.getJSON("/api/submit/getcode?sid="+sid,function (res) {
    if (res.state !== 0) {
        narn("log","您现在不能查看别人的代码");
    } else {
        let table = $("div table tbody");
        table.append(HTML.tr(HTML.td(sid)+HTML.td(HTML.a("problem.jsp?pid="+res.pid,res.pid + " - " + res.ptitle))+HTML.td(getStyleName(res.username+"|"+res.rating))+HTML.td(getProRes(res.result,res.score,res.pos))+HTML.td(res.time_used==null?"-":(res.time_used+"ms"))+HTML.td(res.memory_used==null?"-":(res.memory_used+"KB"))+HTML.td(res.lang)+HTML.td(res.size)+HTML.td(res.date)));

        $("#newhack input[name='sid']").val(sid)
        if (res.result === 0) $("#upHackData").show()
        if (res.collect) {
            $("#submitCollection").html("<font color=\"green\"><span class=\"glyphicon glyphicon-star\"></span>已收藏</font>");
        }
        $(".hljs code").html(hljs.highlightAuto(submitInfo(res)+res.code).value)
        $("code").each(function(){
            $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>");
        });
    }
})

function collect() {
    $.ajax({
        url: "/api/submit/add/collection?sid=" + sid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                if ($("#submitCollection").find("font").length === 0) {
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