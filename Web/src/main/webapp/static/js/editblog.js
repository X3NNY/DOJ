let bid = getUrlParam("bid");
if (bid == null || bid <= 0) {
    narn("log","获取博客原文失败");
} else {
    $.getJSON("/api/blog/info/"+bid, function (res)  {
        tinyMCE.editors["editor"].setContent(res.text);
        $("#blogTitle").val(res.title);
    })
}

function blogSubmit() {
    let s = tinyMCE.editors["editor"].getContent();
    if ($("#blogTitle").val().length == 0) {
        narn("log","标题不能为空，请重新输入");
        return ;
    } else {
        $.ajax({
            url: "/api/blog/edit/" + bid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "title": $("#blogTitle").val(),
                "text": s
            }),
            success: function (res) {
                if (res.state == 0) {
                    tinyMCE.editors["editor"].setContent('');
                    narn("success", "发布成功")
                } else {
                    narn("log", "发布失败！");
                }
            }
        })
    }
}