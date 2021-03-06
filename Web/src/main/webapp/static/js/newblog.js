function blogSubmit() {
    let s = tinyMCE.editors["editor"].getContent();
    if ($("#blogTitle").val().length === 0) {
        narn("log","标题不能为空，请重新输入");
    } else {
        $.ajax({
            url: "/api/blog/new",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "title": $("#blogTitle").val(),
                "text": s
            }),
            success: function (res) {
                if (res.state === 0) {
                    tinyMCE.editors["editor"].setContent('');
                    narn("success", "发表成功")
                } else {
                    narn("log", "发布失败！");
                }
            }
        })
    }
}