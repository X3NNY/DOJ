<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-info">
    <div class="panel-heading" >
        所有比赛
    </div>
    <front style="float: left;">
        <div class="btn-toolbar">
            <div class="btn-group" id="contestPage">
            </div>
        </div>
    </front>
    <div class="panel-body" id="allContest">
        <table class="table table-striped table-hover table-bordered table-condensed">
            <thead>
            <tr>
                <th>ID</th>
                <th style="text-align: center;">Title</th>
                <th style="text-align: center;">Writers</th>
                <th style="text-align: center;">Start</th>
                <th style="text-align: center;">Ends</th>
                <th style="text-align: center;">Level</th>
                <th style="text-align: center;">Register</th>
                <th style="text-align: center">Status</th>
                <th style="text-align: right;">#&nbsp;&nbsp;</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>

<script>
    if (typeof page == "undefined") {
        var page = 1;
    }

    function loadAllContest_() {
        $.getJSON("/api/contest/all?page="+page, function (res) {
            let tbody = $("#allContest table tbody");
            let contestPage = $("#contestPage");
            pageList(contestPage,page,res.pages);

            tbody.empty();

            for (let i = 0; i < res["contests"].length; i++) {
                let tp = res["contests"][i];
                let _writers = '';
                for (let j = 0; j < tp["builders"].length; j++) {
                    _writers += getStyleName(tp["builders"][j]);
                    if (j < tp["builders"].length-1) {
                        _writers += "<br />";
                    }
                }

                let rt;
                if (tp.status === 0) {
                    rt = "未计算Rating";
                } else {
                    rt = "已计算Rating";
                }

                tbody.append(HTML.tr(
                    HTML.td(tp.cid) + HTML.tdC(HTML.a("javascript:loadEditContest("+tp.cid+")",tp.title)) +
                    HTML.tdC(_writers) + HTML.tdC(tp.starttime) +
                    HTML.tdC(getGapMin(tp.endstime,tp.starttime)) +
                    HTML.tdC(tp.level?"密码":"公开") +
                    HTML.tdC(HTML.b("已报名"+tp.num+"人")+"<br />"+HTML.a("contestR.jsp?cid="+tp.cid,"[报名]")) +
                    HTML.tdC(HTML.a("javascript:calcRating("+tp.cid+")",rt)) +
                    getVote(tp.cid,tp.up_vote,tp.down_vote,tp.my_vote)
                ));
            }
        })
    }

    function calcRating(_cid) {
        $.ajax({
            url: "/api/contest/calc/"+_cid,
            type: "PUT",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success",'已更新参数人员Rating')
                } else if (res.state === 1) {
                    narn("log","已经计算过或您没有权限")
                }
            }
        })
    }

    function toPage(_page) {
        page = _page;
        loadAllContest_()
    }

    function getGapMin(e,s) {
        let _tp = Math.floor(((new Date(e)).getTime() - (new Date(s)).getTime())/(1000));
        let _h = (Math.floor(_tp/3600)).toString();
        _h = _h.length<2?"0"+_h:_h;
        let _m = ((_tp/60).toFixed(0)%60).toString();
        _m = _m.length<2?"0"+_m:_m;
        let _s = (_tp%60).toString();
        _s = _s.length<2?"0"+_s:_s;
        return _h + ":" + _m+":"+_s;
    }

    toPage(1)
</script>