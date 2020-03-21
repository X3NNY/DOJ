<%@page contentType="text/html; charset=UTF-8" %>
<div class="panel panel-info">
<div class="panel-body" style="padding:5px">
    <front style="float: left">
        <div class="btn-toolbar" >
            <div class="btn-group"  id="userPage">

            </div>
        </div>
    </front>
    <front style="float: right">
        <form style="margin:0px" class="form-inline" action="javascript:userSearch();">
            <div class="form-group">
                <div class="input-group input-group-sm">
                                <span class="input-group-addon">
                                    用户
                                </span>
                    <input type="text" class="form-control " id="userUser" placeholder="Username/UID" style="width:150px" value="">
                </div>
            </div>
            <a class="btn btn-default btn-sm" href="javascript:userSearch();">
                <span class="glyphicon glyphicon-filter"></span>筛选
            </a>
        </form>
    </front>
    <table class="table table-striped table-hover table-condensed" id="userList">
        <thead>
        <tr>
            <th>
                UID
            </th>
            <th>
                Name
            </th>
            <th>
                Rating
            </th>
            <th>
                Role
            </th>
            <th>
                Level
            </th>
            <th>
                Status
            </th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
</div>
<script>
    if (typeof unums == "undefined") {
        var unums;
    }
    if (typeof page == "undefined") {
        var page;
    }
    function getPage() {
        let name = $("#userUser").val();
        $.getJSON("/api/user/getpages?name="+name, function (res) {
            unums = res.pages;
            let c = $("#userPage");
            pageList(c,page,unums);
        })
    }

    function loadUserList() {
        let name = $("#userUser").val();
        $.getJSON("/api/user/getlist?page="+page+"&name="+name, function (res) {
            let list = $("#userList tbody");
            list.empty()
            for (let i = 0; i < res.length; i++) {
                let t = res[i];
                list.append(HTML.tr(HTML.td(t.uid)+HTML.td(t.username)+HTML.td(t.rating)+HTML.td(t.role)+HTML.td(t.level)+HTML.td(t.status)))
            }
        })
    }

    function toPage(_page) {
        page = _page;
        let c = $("#userPage");
        pageList(c,page,unums);
        loadUserList()
    }

    function userSearch() {
        page = 1
        getPage()
        loadUserList()
    }

    getPage()
    toPage(1)
    document.onkeydown = function (e) {
        if ((e.keyCode || e.which) == 13) {
            userSearch()
        }
    }
</script>