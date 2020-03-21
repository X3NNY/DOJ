let path = "/wiki";

function loadWiki() {
    while ($("#Path li:last a").text() != "Wiki") {
        $("#Path li:last").remove()
    }
    $("#greetingDiv").hide()
    $("#Wiki").show()
    $.getJSON("/api/wiki", function (res) {
        let p = 0;
        $("#Wiki div").empty()
        for (let i = 0; i < res.length; i++) {
            $("#Wiki div.column:nth-of-type("+(p+1)+")").append(newPanel(res[i]))
            p = (p+1)%4;
        }
    })
}

function newPanel(res) {
    let s = "<a href=\"javascript:loadMode('"+res.name+"')\"><div class=\"panel panel-info\" style='height: 180px;' id=\""+ res.name +"\">" +
                "<div class='panel-heading'>" +
                    res.title +
                "</div>" +
                "<div class='panel-body'>" +
                    res.desc +
                "</div>" +
            "</div></a>";
    return s;
}

function newModePanel(res) {
    let s = "<a href=\"javascript:loadMode2('"+res.name+"')\"><div class=\"panel panel-info\" style='height: 180px;' id=\""+ res.name +"\">" +
        "<div class='panel-heading'>" +
        res.title +
        "</div>" +
        "<div class='panel-body'>" +
        res.desc +
        "</div>" +
        "</div></a>";
    return s;
}

function loadMode(name) {
    $("#Path li.active").remove()
    if ($("#Path li:last a").text() != name) {
        $("#Path").append(HTML.liC("active", HTML.a("javascript:loadMode('" + name + "')", name)))
    } else {
        $("#Path li:last").addClass("active")
    }
    $.getJSON("/api/wiki/"+name, function (res) {
        let p = 0;
        $("#Wiki div").empty()
        for (let i = 0; i < res.length; i++) {
            $("#Wiki div.column:nth-of-type("+(p+1)+")").append(newModePanel(res[i]))
            p = (p+1)%4;
        }
    })
}

function loadMode2(name) {
    $("#Path li.active").removeClass("active")
    $("#Path").append(HTML.liC("active",HTML.a("javascript:loadMode2('"+name+"')",name)))
    $.getJSON("/api/wiki/"+name,function (res) {
        let p = 0;
        $("#Wiki div").empty()
        for (let i = 0; i < res.length; i++) {
            $("#Wiki div.column:nth-of-type("+(p+1)+")").append(newLink(res[i]))
            p = (p+1)%4;
        }
    })
}

function newLink(res) {
    let s = "<div class=\"panel panel-info\" style='height: 180px;'>" +
        "<div class='panel-heading'>" +
            HTML.a("blog.jsp?bid="+res.bid,res.title) +
            "<front style='float: right'>" +
                getVote(res.bid,res.up_vote,res.down_vote,res.state) +
            "</front>" +
        "</div>" +
        "<div class='panel-body'>" +
        res.desc +
        "</div>" +
        "</div>";
    return s;
}