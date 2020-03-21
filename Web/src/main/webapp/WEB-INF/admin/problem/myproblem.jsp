<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="panel panel-primary">
    <div class="panel-heading">
        我的题目
    </div>
    <div class="panel-body" style="padding: 5px">
        <front style="float: left">
            <div class="btn-toolbar" >
                <div class="btn-group"  id="problemPage">
                </div>
            </div>
        </front>
    </div>
    <table class="table table-striped table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th style="text-align: center;">Title</th>
            <th style="text-align: center;">Ratio</th>
            <th style="text-align: center;">Status</th>
            <th style="text-align: center;">Data</th>
            <th style="text-align: right;"># &nbsp;</th>
        </tr>
        </thead>
        <tbody id="problemList">
        </tbody>
    </table>
</div>
<script>
    if (typeof pages == "undefined") {
        var pages;
    }
    if (typeof page == "undefined") {
        var page = 1;
    }
    function loadMain(_page) {
        page=_page;
        $.getJSON("/api/problem/set?page="+_page +"&name="+$("#username").val(),function (res) {
            let problemList = $("#problemList");
            problemList.empty();

            let problemPage = $("#problemPage");
            pageList(problemPage,_page,pages);

            let username = $("#username").val().trim();

            for (let i = 0; i < res.length; i++) {
                let tp = res[i];

                let name = tp.author.split('|')[1].trim();
                if (name !== username) {
                    continue;
                }

                let ra = (tp.ac_cnt / tp.all_cnt).toFixed(2)*100;
                ra = ra + "%(" + tp.ac_cnt + "/" + tp.all_cnt + ")";

                let ts;
                if (tp.status===0) {
                    ts = HTML.tdC(HTML.a("javascript:changeProStatus("+tp.pid+","+1+")",HTML.bS("color: blue;",'公开')));
                } else {
                    ts = HTML.tdC(HTML.a("javascript:changeProStatus("+tp.pid+","+0+")",HTML.bS("color: red;","未公开")));
                }

                problemList.append(HTML.tr(HTML.td(tp.pid) + HTML.tdC(HTML.a("javascript:loadEditProblem("+tp.pid+");", HTML.bS("color: blue;",tp.title))) + HTML.tdC(ra) + ts + HTML.tdC(HTML.a("javascript:loadEditProblemData("+tp.pid+")","编辑数据"))+ getVote(tp.pid, tp.up_vote, tp.down_vote, tp.my_vote)));
            }
        });
    }

    function getPages() {
        $.getJSON("/api/problem/getpages?name="+$("#username").val(),function (res) {
            pages = res.pages;

            let problemPage = $("#problemPage");
            pageList(problemPage,1,pages);
        })
    }

    function toPage(_page) {
        loadMain(_page);
    }
    getPages();
    loadMain(1);

    function changeProStatus(pid,s) {
        $.ajax({
            url: "/api/problem/changestatus?pid="+pid+"&status="+s,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) { // success
                    narn("success","更变题目状态成功");
                    loadMain(page);
                } else if (res.state === 1) { // forbid
                    narn("log","您没有这个权限");
                }
            }
        })
    }
</script>