let bid = getUrlParam("bid");
let replyCid;
let editId;

$.getJSON("/api/blog/info/"+bid,function (res) {
    $("title").text(res.title + " - DOJ");
    $("#blogTitle").html(HTML.b(res.title));
    if (res.is_author === 1) {
        $("#blogTitle").append(HTML.frontSR(HTML.a("javascript:delBlog()",HTML.bS("color: red;","[删除博文]"))));
        $("#Blog table tbody td.replyRight").append("<div class=\"replyLink\" style='float: right;'><a href=\"editblog.jsp?bid="+bid+"\">编辑</a></div>");
    }

    let st = res.username.split('|');
    let newU = st[1]+'|'+st[3];

    if (res.image != null) $("#blogInfo div img").attr("src",res.image);
    $("#blogUsername").html(HTML.a("/userinfo.jsp?uid="+res.uid,getStyleName(newU)));

    $("#blogText").html(res.text);

    let blogFooter = $("#blogFooter");
    blogFooter.append(HTML.frontSL(getVote(0,res.up_vote,res.down_vote,res.my_vote)));
    blogFooter.append(HTML.frontSR("Written by "+getStyleName(res.username)+" at "+res.date + ", " + res.num + " replies."));
    blogFooter.append("<br />");

    if (res.iscollect) {
        $("#blogCollect").text('已收藏')
    }

    loadComments();
})

function hLight() {
    // $("pre").addClass("hljs");
    $('pre code').each(function(i, block) {
        hljs.highlightBlock(block);
    });
    $("code").each(function(){
        $(this).html("<ol><li>" + $(this).html().replace(/\n/g,"\n</li><li>") +"\n</li></ol>");
    });

    window.MathJax.Hub.Queue(["Typeset", MathJax.Hub]);
}

function loadComments() {
    $.getJSON("/api/blog/comments/"+bid, function (res) {
        let comments = $("#Commtents");
        comments.empty();
        let fl = 0;
        for (let i = 0; i < res.length; i++) {
            if (res[i].precid === -1) {
                fl++;
                comments.append(newComment(fl,res[i]));
            } else {
                $("#"+res[i].precid+" div.reply").append(newReply(res[i])+"<hr />");
            }
        }
        hLight();
    })
}

function newComment(fl,data) {

    let del = "";
    let edit ="";

    if (data.is_author) {
        del = HTML.a("javascript:delComment("+data.cid+")",HTML.bS("color: red","[删除]"));
        edit = "<div class=\"replyLink\" style='float: right;'><a href=\"#blogCEdit\" data-toggle=\"modal\" onclick=\"setEdit("+data.cid+")\">编辑</a></div>";
    }

    let st = data.username.split('|');
    let newU = st[1]+'|'+st[3];

    let res = "<div class=\"panel panel-default\" style=\"word-break: break-all;\" id=\""+data.cid+"\">" +
                "<div class=\"panel-heading\">" +
                    "<a href=\"#"+data.cid+"\">#"+fl+"&nbsp;</a>" +
                    "reply by " + getStyleName(data.username) + " at " + data.date + "&nbsp;&nbsp;" +
                    del +
                    "<front style=\"float: right\">"+ getVote(data.cid,data.up_vote,data.down_vote,data.my_vote) +"</front>" +
                "</div>" +
                "<table style=\"width: 100%\">" +
                    "<tbody>" +
                        "<tr>" +
                            "<td class=\"replyInfo\">" +
                                "<div>" +
                                    "<img src=\""+data.image+"\" onerror=\"this.src='/media/user/default.jpg'\">" +
                                "</div>" +
                                "<div style=\"text-align: center;\">" +
                                    getStyleName(newU) +
                                "</div>" +
                            "</td>" +
                            "<td class=\"replyRight\">" +
                                "<div class=\"replyText\">" +
                                    data.text +
                                "</div>" +
                                "<div class=\"reply\">" +
                                "</div>" +
                                "<div class=\"replyLink\"><a href=\"#blogReply\" data-toggle=\"modal\" onclick=\"setReply("+data.cid+")\">回复</a></div>" +
                                edit +
                            "</td>" +
                        "</tr>" +
                    "</tbody>" +
                "</table>" +
            "</div>";
    return res;
}

function newReply(data) {
    let res = "<div class=\"replyreply\" id=\""+data.cid+"\">" +
                "<front style=\"float: left\">" +
                    "<img src=\""+data.image+"\" onerror=\"this.src='/media/user/default.jpg'\" />" +
                "</front>" + 
                "<div>" +
                    getStyleName(data.username) + "：" +
                    "<p>"+
                        data.text +
                    "</p>" +
                "</div>" +
                "<div>" +
                    data.date +
                    // "<a href=\"javascript:reply("+data.precid+")\">回复</a>"+
                "</div>"+
            "</div>";
    return res;
}

function blogCollect() {
    $.ajax({
        url: "/api/blog/collect/"+bid,
        type: "PUT",
        contentType: "application/json",
        dataType: "json",
        success: function (res) {
            if (res.state === 0) {
                narn("success",'收藏成功')
            } else if (res.state === 1) {
                narn('log','你还没有登陆')
            } else if (res.state === 2) {
                narn('log','取消收藏成功')
            }
        }
    })
}

function setEdit(id) {
    if (!id || id=='') id = '';
    editId = id;
    tinyMCE.editors["editor1"].setContent($("#"+id+" .replyText:first").html());
}

function setReply(id) {
    if (!id || id == '') id = '';
    replyCid = id;
}

function reply() {
    let cid = replyCid;
    $.ajax({
        url: "/api/blog/reply?bid="+bid+"&cid="+cid,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "text": tinyMCE.editors["editor"].getContent()
        }),
        success: function (res) {
            if (res.state == 0) {
                narn("success","成功");
                loadComments();
            } else {
                narn("log","回复失败");
            }
        }
    })
}

function addStar(id) {
    if (id == 0) {
        $.ajax({
            url: "/api/blog/upvote?bid="+bid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state == 0) {
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
                if (res.state == 0) {
                    narn("success","点赞成功！");
                    loadComments();
                } else {
                    narn("log","您现在不能投票！");
                }
            }
        })
    }
}

function subStar(id) {
    if (id == 0) {
        $.ajax({
            url: "/api/blog/downvote?bid="+bid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state == 0) {
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
                if (res.state == 0) {
                    narn("success","点踩成功");
                    loadComments();
                } else {
                    narn("log","您现在不能投票");
                }
            }
        })
    }
}

function delComment(id) {
    let flag = confirm("确定删除？本操作不可撤销！");
    if (flag) {
        $.ajax({
            url: "/api/blog/delete?bid="+bid+"&cid="+id,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state == 0) {
                    narn("log","删除成功");
                    loadComments();
                } else {
                    narn("log","删除失败");
                }
            }
        })
    }
}

function delBlog() {
    let flag = confirm("确定删除本篇？本操作不可撤销！");
    if (flag) {
        $.ajax({
            url: "/api/blog/delete?bid="+bid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state == 0) {
                    //narn("log","删除成功");
                    location.href =  "blogs.jsp#sdelete";
                } else {
                    narn("log","删除失败");
                }
            }
        })
    }
}

function CEdit() {
    $.ajax({
        url: "/api/blog/comment/edit?cid="+editId,
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "text": tinyMCE.editors["editor1"].getContent()
        }),
        success: function (res) {
            if (res.state == 0) {
                narn("success","修改成功");
                loadComments();
            } else {
                narn("log","修改失败");
            }
        }
    })
}

const BlogDirectory = {
    /*
        获取元素位置，距浏览器左边界的距离（left）和距浏览器上边界的距离（top）
    */
    getElementPosition: function (ele) {
        let topPosition = 0;
        let leftPosition = 0;
        while (ele) {
            topPosition += ele.offsetTop;
            leftPosition += ele.offsetLeft;
            ele = ele.offsetParent;
        }
        return {top: topPosition, left: leftPosition};
    },

    /*
    获取滚动条当前位置
    */
    getScrollBarPosition: function () {
        return document.body.scrollTop || document.documentElement.scrollTop;
    },

    /*
    移动滚动条，finalPos 为目的位置，internal 为移动速度
    */
    moveScrollBar: function (finalpos, interval) {

        //若不支持此方法，则退出
        if (!window.scrollTo) {
            return false;
        }

        //窗体滚动时，禁用鼠标滚轮
        window.onmousewheel = function () {
            return false;
        };

        //清除计时
        if (document.body.movement) {
            clearTimeout(document.body.movement);
        }

        let currentpos = BlogDirectory.getScrollBarPosition();//获取滚动条当前位置

        let dist = 0;
        if (currentpos === finalpos) {//到达预定位置，则解禁鼠标滚轮，并退出
            window.onmousewheel = function () {
                return true;
            }
            return true;
        }
        if (currentpos < finalpos) {//未到达，则计算下一步所要移动的距离
            dist = Math.ceil((finalpos - currentpos) / 10);
            currentpos += dist;
        }
        if (currentpos > finalpos) {
            dist = Math.ceil((currentpos - finalpos) / 10);
            currentpos -= dist;
        }

        const scrTop = BlogDirectory.getScrollBarPosition();//获取滚动条当前位置
        window.scrollTo(0, currentpos);//移动窗口
        if (BlogDirectory.getScrollBarPosition() === scrTop)//若已到底部，则解禁鼠标滚轮，并退出
        {
            window.onmousewheel = function () {
                return true;
            }
            return true;
        }

        //进行下一步移动
        const repeat = "BlogDirectory.moveScrollBar(" + finalpos + "," + interval + ")";
        document.body.movement = setTimeout(repeat, interval);
    },

    htmlDecode: function (text) {
        let temp = document.createElement("div");
        temp.innerHTML = text;
        const output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    },

    /*
    创建博客目录，
    id表示包含博文正文的 div 容器的 id，
    mt 和 st 分别表示主标题和次级标题的标签名称（如 H2、H3，大写或小写都可以！），
    interval 表示移动的速度
    */
    createBlogDirectory: function (id, mt, st, interval) {
        //获取博文正文div容器
        const elem = document.getElementById(id);
        if (!elem) return false;
        //获取div中所有元素结点
        const nodes = elem.getElementsByTagName("*");
        //创建博客目录的div容器
        const divSideBar = document.createElement('DIV');
        divSideBar.className = 'uprightsideBar';
        divSideBar.setAttribute('id', 'uprightsideBar');
        const divSideBarTab = document.createElement('DIV');
        divSideBarTab.setAttribute('id', 'sideBarTab');
        divSideBar.appendChild(divSideBarTab);
        const h2 = document.createElement('H2');
        divSideBarTab.appendChild(h2);
        const txt = document.createTextNode('目录导航');
        h2.appendChild(txt);
        const divSideBarContents = document.createElement('DIV');
        divSideBarContents.style.display = 'none';
        divSideBarContents.setAttribute('id', 'sideBarContents');
        divSideBar.appendChild(divSideBarContents);
        //创建自定义列表
        const dlist = document.createElement("dl");
        divSideBarContents.appendChild(dlist);
        let num = 0;//统计找到的mt和st
        mt = mt.toUpperCase();//转化成大写
        st = st.toUpperCase();//转化成大写
        //遍历所有元素结点
        for (var i = 0; i < nodes.length; i++) {
            if (nodes[i].nodeName == mt || nodes[i].nodeName == st) {
                //获取标题文本
                let nodetext = nodes[i].innerHTML.replace(/<\/?[^>]+>/g, "");//innerHTML里面的内容可能有HTML标签，所以用正则表达式去除HTML的标签
                nodetext = nodetext.replace(/ /ig, "");//替换掉所有的
                nodetext = BlogDirectory.htmlDecode(nodetext);
                //插入锚
                nodes[i].setAttribute("id", "blogTitle" + num);
                let item;
                switch (nodes[i].nodeName) {
                    case mt:    //若为主标题
                        item = document.createElement("dt");
                        break;
                    case st:    //若为子标题
                        item = document.createElement("dd");
                        break;
                }

                //创建锚链接
                const itemtext = document.createTextNode(nodetext);
                item.appendChild(itemtext);
                item.setAttribute("name", num);
                item.onclick = function () {        //添加鼠标点击触发函数
                    const pos = BlogDirectory.getElementPosition(document.getElementById("blogTitle" + this.getAttribute("name")));
                    if (!BlogDirectory.moveScrollBar(pos.top, interval)) return false;
                };

                //将自定义表项加入自定义列表中
                dlist.appendChild(item);
                num++;
            }
        }

        if (num === 0) return false;
        /*鼠标进入时的事件处理*/
        divSideBarTab.onmouseenter = function () {
            divSideBarContents.style.display = 'block';
        }
        /*鼠标离开时的事件处理*/
        divSideBar.onmouseleave = function () {
            divSideBarContents.style.display = 'none';
        }

        document.body.appendChild(divSideBar);
    }

};

window.onload=function(){
    /*页面加载完成之后生成博客目录*/
    BlogDirectory.createBlogDirectory("blogText","h1","h2",10);
}