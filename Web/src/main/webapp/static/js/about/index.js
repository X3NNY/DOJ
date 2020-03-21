$.getJSON("/api/about/index",function (res) {
    $("#content").html(res.content);
})