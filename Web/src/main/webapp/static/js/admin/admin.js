loadAdminMain()

function loadAdminMain() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/main/info")
}

function loadSettingMain() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/main/setting")
}

function loadMyProblem() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/my")
}

function loadNewProblem() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/new")
}

function loadEditProblem(pid) {
    location.hash = pid;
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/edit?pid="+pid)
}

function loadEditProblemData(pid) {
    location.hash = pid;
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/data?pid="+pid)
}

function loadAllProblem() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/all")
}

function loadBatchAddProblem() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/problem/batch")
}

function loadMyContest() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/contest/my")
}

function loadAllContest() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/contest/all")
}

function loadAllCloneContest() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/contest/allclone")
}

function loadNewContest() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/contest/new")
}

function loadEditContest(cid) {
    location.hash = cid;
    $("#page-content-wrapper div.container-fluid").load("/module/admin/contest/edit?cid="+cid)
}

function loadAllUser() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/user/all")
}

function loadAddUser() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/user/add")
}

function loadAddInviteCode() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/user/addinvite")
}

function loadAllGoods() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/mall/allgoods")
}

function loadAddGoods() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/mall/addgoods")
}

function loadAllOrder() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/mall/allorder")
}

function loadEditGoods(gid) {
    location.hash = gid;
    $("#page-content-wrapper div.container-fluid").load("/module/admin/mall/editgoods?gid="+gid)
}

function loadReJudge() {
    $("#page-content-wrapper div.container-fluid").load("/module/admin/rejudge")
}