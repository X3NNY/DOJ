let cid = getUrlParam("cid");

$("#contestDesc").on("click",function () {
    $("#hidden-a").click()
})
$("#contestTitle").on("click",function () {
    let s = prompt("输入比赛标题",$("#contestTitle").text());
    if (s != null) {
        $("#contestTitle").text(s)
    }
})
$("#addConPro input[name='pid']").bind('input propertychange',function () {
    let pid = parseInt($(this).val());
    $.getJSON("/api/problem?pid="+pid,function (res) {
        if (res.pid === pid) {
            $("#addConPro input[name='ptitle']").val(res.title)
        } else {
            $("#addConPro input[name='ptitle']").val("没有该题")
        }
    })
})

function newConSubmit() {
    if ($("#contestTitle").text().length === 0) {
        narn("log","标题不能为空")
        return;
    }
    if ($("#startTime").val()==="" || $("#endsTime").val()==="") {
        narn("log","请填写比赛开始和结束时间");
        return;
    }
    let list = [];
    let table = $("#newContest table tbody tr");
    for (let i = 0; i < table.length; i++) {
        list.push(parseInt(table[i].attributes["id"].value));
    }
    Submit(list)
}

function Submit(list) {
    let type = parseInt($("#contestType option:selected").val());
    let level = parseInt($("input[name='contestLevel']:checked").val());
    let passwd = $("#contestPasswd input").val();
    if (type == null || type < 0 || type > 4) {
        narn('log','请选择正确的赛制')
    } else if (level == null || (level !== 0 && level !== 1)) {
        narn('log','请选择正确的权限')
    } else if (level === 1 && (passwd == null || passwd.length === 0)) {
        narn('log','请输入比赛密码')
    } else {
        $.ajax({
            url: "/api/contest/edit/"+cid,
            type: "PUT",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "title": $("#contestTitle").text(),
                "startTime": new Date($("#startTime").val()),
                "endsTime": new Date($("#endsTime").val()),
                "desc": $("#contestDesc").html(),
                "problems": list,
                "level": level,
                "password": passwd,
                "type": type
            }),
            success: function (res) {
                if (res.state === 0) {
                    narn("success","编辑成功");
                } else {
                    narn("log","编辑失败");
                }
            }
        })
    }
}

function editContestDesc() {
    $("#contestDesc").html(tinyMCE.editors["editor"].getContent())
}
function addContestPro() {
    let table = $("#newContest table tbody");
    let pid = $("#addConPro input[name='pid']").val();
    if ($("#newContest table tbody tr#"+pid).length!==0) {
        narn("log","已经添加过了");
        return ;
    }
    table.append(HTML.trId(pid,HTML.td(pid)+HTML.td($("#addConPro input[name='ptitle']").val())+HTML.td(HTML.a("javascript:delContestPro("+pid+")","删除"))));
}
function delContestPro(pid) {
    let tr = $("table tbody tr#"+pid);
    tr.remove()
}

function loadContestInfo() {
    $.getJSON("/api/contest/sinfo/"+cid, function (res) {
        if (!res.is_author) {
            narn('warn', '您没有权限操作！')
        } else {
            $("#contestTitle").text(res.title)
            $("#startTime").val(res.starttime)
            $("#endsTime").val(res.endstime)
            if (res.desc.length !== 0) {
                $("#contestDesc").html(res.desc)
                tinyMCE.editors["editor"].setContent(res.desc)
            }
            if (res.level === 1 || res.level === 3) {
                $("input[name='contestLevel']:last").click()
                $("#contestPasswd input").val(res.password)
            }
            $("#contestType select").val(res.type)
            let table = $("#newContest table tbody");
            table.empty()
            for (let i in res.problemset) {
                let p = res.problemset[i];
                table.append(HTML.trId(p.pid, HTML.td(p.pid) + HTML.td(p.title) + HTML.td(HTML.a("javascript:delContestPro(" + p.pid + ")", "删除"))))
            }
        }
    })
}

function choosePublic() {
    $("#contestPasswd").hide()
}

function choosePasswd() {
    $("#contestPasswd").show()
}

loadContestInfo()