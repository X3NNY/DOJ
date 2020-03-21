let sid = getUrlParam("sid");

$.getJSON("/api/submit/info/"+sid,function (res) {
    if (res.state == 0) {
        narn("log","获取评测信息失败");
    } if (res.state == 1) { //CE info
        $("div.panel-heading").text("编译信息");
        $("div.panel-body").html(res.msg);
    } else if (res.state == 2) {
        $("div.panel-heading").text("系统错误");
        $("div.panel-body").text("可能是系统遇到了故障，请稍后重试或联系管理员");
    } else if (res.state == 3) { // other
        $("div.panel-heading").text("错误信息");
        $("div.panel-body p").text("在第"+res.pos+"组数据处发送错误");
        $("div.panel-body table tbody").append( HTML.trNum(3,Array(HTML.td(res.input),HTML.td(res.output),HTML.td("此功能暂未实现，请下载数据自行测试"))));
    }
})