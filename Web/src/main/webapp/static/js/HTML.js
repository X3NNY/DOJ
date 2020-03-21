let HTML = {
    a: function (href, s) {
        return "<a href=\"" + href + "\">" + s + "</a>";
    },
    aClassHref: function (c, h, s) {
        return "<a class=\"" + c + "\" href=\"" + h + "\">" + s + "</a>";
    },
    aClassHrefDis: function (c, h, s) {
        return "<a class=\"" + c + "\" href=\"" + h + "\" disabled=\"disabled\">" + s + "</a>";
    },
    li: function (s) {
        return "<li>" + s + "</li>";
    },
    liC: function (c,s) {
        return "<li class=\""+ c +"\">" + s + "</li>";
    },
    tr: function (s) {
        return "<tr>" + s + "</tr>";
    },
    trClass: function (c, s) {
        return "<tr class=\"" + c + "\">" + s + "</tr>";
    },
    trId: function(i, s) {
        return "<tr id=\""+i+"\">" + s + "</tr>";
    },
    trNum: function (num, a) {
        let s = "<tr>"
        for (let i = 0; i < num; i++) {
            s += "<td>" + a[i] + "</td>";
        }
        s += "</tr>";
        return s;
    },
    trCNum: function (c,num, a) {
        let s = "<tr class=\""+ c +"\">"
        for (let i = 0; i < num; i++) {
            s += "<td>" + a[i] + "</td>";
        }
        s += "</tr>";
        return s;
    },
    th: function (s) {
        return "<th>" + s + "</th>";
    },
    td: function (s) {
        return "<td>" + s + "</td>";
    },
    tdC: function (s) {
        return "<td style=\"text-align: center;\">" + s + "</td>";
    },
    tdClass: function (c, s) {
        return "<td class=\"" + c + "\">" + s + "</td>";
    },
    tdR: function (s) {
        return "<td style=\"text-align: right;\">" + s + "</td>";
    },
    font: function (s) {
        return "<font>" + s + "</font>";
    },
    bClass: function (c, s) {
        return "<b class=\"" + c + "\">" + s + "</b>";
    },
    em: function (c, s) {
        return "<em class=\"" + c + "\">" + s + "</em>";
    },
    option: function (s) {
        return "<option>" + s + "</option>";
    },
    optionValue: function (v, s) {
        return "<option value=\"" + v + "\">" + s + "</option>";
    },
    b: function (s) {
        return "<b>" + s + "</b>";
    },
    bS: function (c, s) {
        return "<b style=\""+ c +"\">"+ s +"</b>";
    },
    frontSR: function (s) {
        return "<front style=\"float: right;\">" + s + "</front>";
    },
    frontSL: function (s) {
        return "<front style=\"float: left;\">" + s + "</front>";
    },
    span: function (s) {
        return "<span>"+ s +"</span>";
    },
    textC: function (c,s) {
        return "<text class=\""+ c +"\">" + s + "</text>";
    },
    loadCss: function (path) {
        if (!path || path.length === 0) {
            throw new Error('path error');
        }
        let head = document.getElementsByTagName('head')[0];
        let link = document.createElement('link');
        link.href = path;
        link.rel = 'stylesheet';
        link.type = 'text/css';
        head.appendChild(link);
    },
    loadJs: function (path) {
        if (!path || path.length === 0) {
            throw new Error('path error');
        }
        let head = document.getElementsByTagName('head')[0];
        let script = document.createElement('script');
        script.src = path;
        script.type = 'text/javascript';
        head.appendChild(script);
    }
};

function removeHtml(s) {
    return s.toString().replace(/</g,'&lt;').replace(/>/g,'&gt;').replace(/"/g, "&quot;").replace(/'/g, "&#039;");
}

function getUrlParam(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    let s = window.location.search.substr(1);
    while (s.indexOf("%26") !== -1) s = s.replace("%26","&");
    let r = s.match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}

function getRatingClass(rating) {
    let c = "username_onshore";
    if (rating == null || rating <= 0) {

    } else if (rating < 1000) {
        c = "username_cadet";
    } else if (rating < 1200) {
        c = "username_ord_seaman";
    } else if (rating < 1400) {
        c = "username_able_bodied";
    } else if (rating < 1700) {
        c = "username_bosun";
    } else if (rating < 2000) {
        c = "username_3rd_officer";
    } else if (rating < 2200) {
        c = "username_2nd_officer";
    } else if (rating < 2500) {
        c = "username_chief_officer";
    } else {
        c = "username_captain";
    }
    return c;
}

function getStyleRating(r) {
    let c = getRatingClass(r);
    return HTML.bClass(c,r);
}

function getStyleName(s) {
    s = s.split("|");
    let name = "";
    let c = "";
    if (s.length === 4) {
        s[1] = removeHtml(s[1]);
        if (s[0] !== "" && s[0] !== "null") {
            name += HTML.font("[" + s[0] + "]");
        }
        c = getRatingClass(parseInt(s[3]));
        name += HTML.a("/userinfo.jsp?username="+ s[1],HTML.bClass(c,s[1]));
        if (s[2] !== "") {
            name += HTML.em(s[2], "");
        }
    } else if (s.length === 2) {
        s[0] = removeHtml(s[0]);
        c = getRatingClass(parseInt(s[1]));
        name += HTML.a("/userinfo.jsp?username="+ s[0],HTML.bClass(c,s[0]));
    }

    return name;
}
function narn (type,msg) {
    naranja(msg)[type]({
        title: '通知',
        text: msg,
        timeout: 5000,
        buttons: [{
            text: '取消',
            click: function (e) {
                e.closeNotification()
            }
        }]
    })
}

function narnHref (type,msg,href) {
    naranja(msg)[type]({
        title: '通知',
        text: msg,
        timeout: 5000,
        buttons: [{
            text: '确定',
            click: function (e) {
                e.closeNotification();
                location.href = href;
            }
            },{
            text: '取消',
            click: function (e) {
                e.closeNotification();
            }
        }]
    })
}

function getUsername (s) {
    s = s.split('|');
    return s[1];
}

Date.prototype.Format = function(fmt) {
  let o = {
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(let k in o) {
      if (new RegExp("(" + k + ")").test(fmt)) {
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      }
  }
  return fmt;   
}

function pageList(c,p,ps) {
    c.empty();
    let t = "btn btn-default btn-sm";
    c.append(HTML.aClassHref(t,"javascript:toPage(1);","&lt;&lt;"));
    if (ps <= 5) {
        for (let i = 1; i <= ps; i++) {
            if (i == p) {
                c.append(HTML.aClassHref("btn btn-primary btn-sm", "javascript:toPage(" + i + ");", i));
            } else {
                c.append(HTML.aClassHref(t, "javascript:toPage(" + i + ");", i));
            }
        }
    } else {
        if (p == 1) {
            c.append(HTML.aClassHrefDis(t, "", "＜"));
        } else {
            c.append(HTML.aClassHref(t, "javascript:toPage(" + (p - 1) + ");", "＜"));
        }
        if (p > 3) {
            c.append(HTML.aClassHrefDis(t, "", "..."));
        }
        for (let i = Math.max(1,p-2); i <= Math.min(p+2, ps); i++) {
            if (i == p) {
                c.append(HTML.aClassHref("btn btn-primary btn-sm", "javascript:toPage(" + i + ");", i));
            } else {
                c.append(HTML.aClassHref(t, "javascript:toPage(" + i + ");", i));
            }
        }
        if (p+2 < ps) {
            c.append(HTML.aClassHrefDis(t, "", "..."));
        }
        if (p == ps) {
            c.append(HTML.aClassHrefDis(t, "", "＞"));
        } else {
            c.append(HTML.aClassHref(t, "javascript:toPage("+(p+1)+");", "＞"));
        }
    }
    c.append(HTML.aClassHref(t,"javascript:toPage("+ps+");","&gt;&gt;"));
}

function logout() {
    $.getJSON("/api/user/logout",function (res) {
        if (res.state == 0) {
            if (location.href.indexOf("index.jsp")) {
                location.href = "/index.jsp?logout#slogout";
            } else {
                location.href = "/index.jsp?logout#slogout";
            }
        } else {
            narn("warn","未知错误！");
        }
    })
}

function getVote(id,up_vote,down_vote,state) {
    let upSrc = "voteup-no";
    let downSrc = "votedown-no";
    let color = "black";
    let flag = up_vote>down_vote?'+':'';
    if (up_vote > down_vote) {
        color = "green";
    } else if (up_vote < down_vote){
        color = "red";
    }
    if (state == 1) {
        upSrc = "voteup";
    } else if (state == -1) {
        downSrc = "votedown";
    }
    return "<td style='text-align: right;'><a href='#' onclick='addStar(" + id + ")'>" +
        "<img style='position:relative;top:3px;opacity:0.35;' src='/static/img/"+ upSrc +".png' alt='点赞' title='点赞'></a>" +
        "<b style='color:"+color+";'>" +
        flag + (up_vote - down_vote) +
        "</b><a href='#' onclick='subStar(" + id + ")'><img style='position:relative;top:2px;opacity:0.35;' src='/static/img/"+ downSrc +".png' alt='点踩' title='点踩'></a>" +
        "</td>";
}

function sleepUt(ms){
    return new Promise((resolve)=>setTimeout(resolve,ms));
}
async function sleep(ms,func,c) {
    let temple = await sleepUt(ms);
    if (typeof c != "undefined") {
        func(c);
    }
    else {
        func();
    }
    return temple;
}


function quickOrder(arr){
    let left = [];
    let right = [];
    if(arr.length<=1){
        return arr;
    }
    let first = arr.splice(0,1);
    for(let i=0;i<arr.length;i++){
        if(first>=arr[i]){
            left.push(arr[i]);
        }else{
            right.push(arr[i]);
        }
    }
    return quickOrder(left).concat(first,quickOrder(right));
}