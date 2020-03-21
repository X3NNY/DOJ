let spage = 1;
let apage = 1;
let fpage = 1;
let sendPage = 1;
let MID;
let now = 0;

function getMPages(flag,_page) {
    let pageList = $("#pageList");
    pageList.empty()
    let s = "";
    if (_page !== 1) {
        s += HTML.a("javascript:toPage("+flag+","+(_page-1)+")","<span class=\"glyphicon glyphicon-chevron-left\"></span>上一页")
    }
    s += "&nbsp;&nbsp;&nbsp;&nbsp;";
    s += HTML.a("javascript:toPage("+flag+","+(_page+1)+")","下一页<span class=\"glyphicon glyphicon-chevron-right\"></span>")
    pageList.append(HTML.span(s))
}

function toPage(flag,_page) {
    if (flag === 0) {
        spage = _page;
        loadSystemMessage()
    } else if (flag === 1) {
        apage = _page;
        loadAboutMessage()
    } else if (flag === 2) {
        fpage = _page;
        loadFriendMessage()
    } else if (flag === 3) {
        sendPage = _page;
        loadSendMessage()
    }
}

function loadSystemMessage() {
    now = 0;
    $("#messageInfo h3").text("系统消息")
    $.getJSON("/api/message/system/"+spage, function (res) {
        let tbody = $("#messageInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            let state = "";
            if (res[i].status === 0) {
                state = HTML.bS("color: red;","未读");
            } else {
                state = HTML.bS("color: blue;", "已读");
            }
            tbody.append(HTML.tr(HTML.td(res[i].mid)+HTML.td(HTML.a("javascript:showMessage("+res[i].mid+",0)",res[i].title))+HTML.td("管理员")+HTML.td(res[i].date)+HTML.td(state)))
        }
        getMPages(0,spage)
    })
}

function loadAboutMessage() {
    now = 1;
    $("#messageInfo h3").text("提到我的")
    $.getJSON("/api/message/about/"+apage, function (res) {
        let tbody = $("#messageInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            let state = "";
            if (res[i].status === 0) {
                state = HTML.bS("color: red;","未读");
            } else {
                state = HTML.bS("color: blue;", "已读");
            }
            tbody.append(HTML.tr(HTML.td(res[i].mid)+HTML.td(HTML.a("javascript:showMessage("+res[i].mid+",1)",res[i].title))+HTML.td("-")+HTML.td(res[i].date)+HTML.td(state)))
        }
        getMPages(1,apage)
    })
}

function loadFriendMessage() {
    now = 2;
    $("#messageInfo h3").text("好友留言")
    $.getJSON("/api/message/friend/"+fpage, function (res) {
        let tbody = $("#messageInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            let state = "";
            if (res[i].status === 0) {
                state = HTML.bS("color: red;","未读");
            } else {
                state = HTML.bS("color: blue;", "已读");
            }
            tbody.append(HTML.tr(HTML.td(res[i].mid)+HTML.td(HTML.a("javascript:showMessage("+res[i].mid+",2)",res[i].title))+HTML.td(getStyleName(res[i].username))+HTML.td(res[i].date)+HTML.td(state)))
        }
        getMPages(2,fpage)
    })
}

function loadSendMessage() {
    $("#messageInfo h3").text("我发送的")
    $.getJSON("/api/message/send/"+sendPage, function (res) {
        let tbody = $("#messageInfo table tbody");
        tbody.empty()
        for (let i = 0; i < res.length; i++) {
            let state = "";
            if (res[i].status === 0) {
                state = HTML.bS("color: red;","未读");
            } else {
                state = HTML.bS("color: blue;", "已读");
            }
            tbody.append(HTML.tr(HTML.td(res[i].mid)+HTML.td(HTML.a("javascript:showMessage("+res[i].mid+",3)",res[i].title))+HTML.td(getStyleName(res[i].username))+HTML.td(res[i].date)+HTML.td(state)))
        }
        getMPages(3,sendPage)
    })
}

function newMsg() {
    $.ajax({
        url: "/api/message/send",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "name": $("#newMsg input[name='name']").val(),
            "title": $("#newMsg input[name='title']").val(),
            "text": $("#newMsg textarea").val()
        }),
        success: function (res) {
            if (res.state === 0) {
                narn("success","发送成功")
                $("#newMsg input[name='name']").val("")
                $("#newMsg input[name='title']").val("")
                $("#newMsg textarea").val("")
            } else {
                narn("log","没有这个用户")
            }
        }
    })
}

function showMessage(mid,flag) {
    if (flag === 0) {
        $("#editor").hide()
        $("#replyBtn").hide()
    } else if (flag === 1) {
        $("#editor").hide()
        $("#replyBtn").hide()
    } else if (flag === 2) {
        $("#replyBtn").show()
        $("#editor").show()
        MID = mid;
    } else if (flag === 3) {
        $("#editor").hide()
        $("#replyBtn").hide()
    }
    $.getJSON("/api/message/content/" + mid, function (res) {
        $("#showMsgBtn").click()
        $("#msgContent").html(res.content);
    })
    if (flag !== 3) {
        $.ajax({
            url: "/api/message/status/" + mid,
            type: "PUT",
            dataType: "json",
            contentType: "application/json",
            success: function (res) {
                if (flag === 0) {
                    loadSystemMessage()
                } else if (flag === 1) {
                    loadAboutMessage()
                } else if (flag === 2) {
                    loadFriendMessage()
                }
            }
        })
    }
}

function replyMsg() {
    $.ajax({
        url: "/api/message/reply/"+MID,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "content": $("#editor").val()
        }),
        success: function (res) {
            if (res.state === 0) {
                narn("success","回复成功")
                $("#editor").val("")
            } else {
                narn("log","回复失败")
            }
        }
    })
}

function changeAllState() {
    if (now <= 2) {
        $.ajax({
            url: "/api/message/status/all/" + now,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "全部设为已读成功");
                    if (now === 0) {
                        loadSystemMessage()
                    } else if (now === 1) {
                        loadAboutMessage()
                    } else if (now === 2) {
                        loadFriendMessage()
                    }
                } else {
                    narn("log", "失败");
                }
            }
        })
    }
}

loadSystemMessage()