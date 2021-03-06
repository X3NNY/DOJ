$().ready(function () {
    $("#userRegister").validate({
        rules: {
            oldPassword: {
                required: true,
            },
            newPassword: {
                minlength: 6,
                maxlength: 18
            },
            rPassword: {
                minlength: 6,
                maxlength: 18
            },
            school: {
                required: true,
            },
            note: {
                maxlength: 128
            }
        },
        messages: {
            oldPassword: {
                required: "旧密码不能为空",
            },
            newPassword: {
                minlength: "密码长度最小为6",
                maxlength: "密码长度最大为18"
            },
            rPassword: {
                minlength: "密码长度最小为6",
                maxlength: "密码长度最大为18"
            },
            school: {
                required: "学校/单位不能为空"
            },
            note: {
                maxlength: "个人说明长度不能超过128"
            }
        }
    });

    $.getJSON("/api/user/edit",function (res) {
        $("#school").val(res.school);
        $("#note").val(res.note);
    })
});

function editSubmit() {
    let oldPassword = $("#oldPassword").val();
    let newPassword = $("#newPassword").val();
    let rPassword = $("#rPassword").val();
    if (newPassword != rPassword) {
        narn("warn","两次密码不一致，请检查");
        return ;
    }
    $.ajax({
        url: "/api/user/edit",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "password": oldPassword,
            "newpassword": newPassword,
            "school": $("#school").val(),
            "note": $("#note").val()
        }),
        success: function (res) {
            if (res.state == 0) {
                narn("success","个人资料修改成功");
            } else if (res.state == 1) {
                narn("log","个人资料修改失败");
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}
