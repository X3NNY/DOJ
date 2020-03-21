$().ready(function () {
    $('#checkcodeImg').click(function() {
        let timestamp = new Date().getTime();
        $(this).attr('src',$(this).attr('src')+'?'+timestamp);
    });

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
                maxlength: "面慢长度最大为18"
            }
        }
    });
});

function loginSubmit() {
    $.ajax({
        url: "/api/user/login",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            "username": $("#username").val(),
            "password": $("#password").val(),
            "checkcode": $("#checkcode").val()
        }),
        success: function (res) {
            if (res.state == 0) {
                location.href = "/index.jsp#slogin";
            } else if (res.state == 1) {
                narn("error","用户名或密码错误");
            } else if (res.state == 2) {
                narn("warn","验证码错误或已失效");
            } else if (res.state == 3) {
                narn("error","检测到非法字符！");
            }
        },
        error: function (e) {
            console.log(e);
        }
    })
}