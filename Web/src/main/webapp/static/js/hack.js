let id=getUrlParam("id");
if (id==null) {
    narn("error","未找到Hack信息")
} else {
    loadSubmitInfo()
}

function loadSubmitInfo() {
    $.getJSON("/api/hack/info/"+id, function (res) {
        if (res.state !== 0) {
            narn("log","您现在不能查看Hack信息")
        } else {
            let info = $("div table:first tbody")
            let r = "<span class=\"label label-default\">Waiting</span>";
            if (res.result === 2) {
                r = "<span class=\"label label-success\">Success</span>";
            } else if (res.result === 1) {
                r = "<span class=\"label label-danger\">Failed</span>";
            }
            info.append(HTML.tr(HTML.td(HTML.a("/hack.jsp?id="+res.id,res.id))+HTML.td(HTML.a("/code.jsp?sid="+res.sid,res.sid))+HTML.td(HTML.a("/problem.jsp?pid="+res.pid,res.pid+" - "+res.ptitle))+HTML.td(getStyleName(res.hacker))+HTML.td(getStyleName(res.committer))+HTML.td(r)+HTML.td(res.date)))

            $("pre.hljs code").text(submitInfo(res)+res.code)
            hljs.initHighlighting();
            $("code").each(function(){
                $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>");
            });
            $("#answer tbody pre").text(res.answer)

            let token = $("#token").val();

            $.getJSON("<%=judgeUrl%>/api/problem/sdata/"+res.pid+"?name="+res.filename+"&token="+token, function (res) {
                $("#input tbody pre").text(res.data)
            })
            $.getJSON("<%=judgeUrl%>/api/problem/sdata/"+res.pid+"?name="+res.filename.replace(".in",".out")+"&token="+token, function (res) {
                $("#output tbody pre").text(res.data)
            })
        }
    })
}

function submitInfo(res) {
    return "/** \n" +
        " * @Author: " + res.committer.split('|')[1] + "\n" +
        " * @Date: " + res.sdate + "\n" +
        " * @PID: " + res.pid + "\n" +
        " * @Result: Accepted\n" +
        " * Powered By DOJ" + "\n" +
        "*/\n";
}
