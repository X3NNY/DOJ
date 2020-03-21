let bnums;
let page = getUrlParam("page");
let name = '';
if (!page || page <= 0) page = 1;

let hash = location.hash;
if (hash == "#sdelete") {
    narn("log","删除成功");
}

function addStar(bid) {
    $.ajax({
        url: "/api/blog/upvote?bid="+bid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state == 0) {
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
            if (res.state == 0) {
                narn("success","点踩成功");
                searchBlog();
            } else {
                narn("log","您现在不能投票");
            }
        }
    })
}
function loadMain(_info,_page,_star) {
    name = _info;
    if (!_page || _page <= 0) page = 1;
    else page = _page;
    $.getJSON("/api/blog/set?page="+page + "&name=" + name + "&star=" + _star,function (res) {
        bnums = res.pages;
        let blogList = $("#blogList");

        let blogPage = $("#blogPage");
        pageList(blogPage,page,bnums);

        blogList.empty();
        for (let i = 0; i < res["blogset"].length; i++) {
            let tp = res["blogset"][i];
            let id;
            if (tp.state == 0) {
                id = tp.bid;
            } else {
                id = tp.bid+"<span class=\"glyphicon glyphicon-pushpin\" style=\"color: rgb(255, 0, 0);\"></span>";
                tp.title = "<em style='color: red;'>[置顶]</em>"+tp.title;
            }
            blogList.append(HTML.tr(HTML.td(id)+HTML.tdC(HTML.a("/blog.jsp?bid="+tp.bid,tp.title))+HTML.tdC(getStyleName(tp.author))+HTML.tdC(tp.date)+getVote(tp.bid,tp.up_vote,tp.down_vote,tp.my_vote)));
        }
    });
}

function toPage(_page) {
    loadMain("",_page,0);
}

function searchBlog() {
    let _info = $("#searchInfo").val();
    let _star = $("#myStar").is(':checked')?1:0;
    loadMain(_info,1,_star);
}

loadMain("",1,0);