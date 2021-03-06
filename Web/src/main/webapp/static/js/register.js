$().ready(function () {
    $("#userRegister").validate({
        rules: {
            username: {
                required: true,
                maxlength: 64
            },
            password: {
                required: true,
                minlength: 6,
                maxlength: 18
            },
            rPassword: {
                required: true,
                minlength: 6,
                maxlength: 18
            },
            school: {
                required: true,
            }
        },
        messages: {
            username: {
                required: "用户名不能为空",
                maxlength: "用户名长度不能超过32",
            },
            password: {
                required: "密码不能为空",
                minlength: "密码长度最小为6",
                maxlength: "密码长度最大为18"
            },
            rPassword: {
                required: "密码不能为空",
                minlength: "密码长度最小为6",
                maxlength: "密码长度最大为18"
            },
            school: {
                required: "学校/单位不能为空"
            }
        }
    });
});

function registSubmit() {
    let password = $("#password").val();
    let rPassword = $("#rPassword").val();
    let username = $("#username").val();
    if (password !== rPassword) {
        narn("warn","两次密码不一致，请检查");
    } else if (username.length === 0) {
        narn('warn','用户名不能为空')
    } else {
        $.ajax({
            url: "/api/user/register",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "email": getUrlParam("email"),
                "invitecode": getUrlParam("invite"),
                "key": getUrlParam("key"),
                "username": username,
                "password": $("#password").val(),
                "school": $("#school").val()
            }),
            success: function (res) {
                if (res.state === 0) {
                    location.href = "/index.jsp#sregister"
                } else if (res.state === 1) {
                    narn('log', '链接失效或错误，请检查！')
                } else if (res.state === 2) {
                    narn("error", "含有非法字符，请重新注册")
                } else if (res.state === 3) {
                    narn('log', '名称已被占用')
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    }
}
