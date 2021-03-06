$().ready(function () {
    $("#userRegister").validate({
        rules: {
            email: {
                required: true,
                maxlength: 64
            },
            invitation: {
                required: true,
                minlength: 6,
                maxlength: 6
            }
        },
        messages: {
            email: {
                required: "邮箱不能为空",
                maxlength: "邮箱长度不能超过64"
            },
            invitation: {
                required: "邀请码不能为空",
                minlength: "邀请码长度应为6",
                maxlength: "邀请码长度应为6"
            }
        }
    });
});

function registSubmit(step) {
    if (step == 0) {
        $.ajax({
            url: "/api/user/register",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "step": 0,
                "invitecode": $("input[name='invitation']").val(),
                "email": $("input[name='email']").val(),
                "checkcode": $("input[name='checkcode']").val()
            }),
            success: function (res) {
                if (res.state === 0) {
                    narn("success","注册成功，已发送激活邮件至邮箱");
                } else if (res.state === 1){
                    narn("error","邀请码错误或失效，请重新填写");
                } else if (res.state === 2) {
                    narn("error","验证码错误，请重新填写！");
                } else if (res.state === 3) {
                    narn("error","检测到非法字符！注册失败！");
                } else if (res.state === 4) {
                    narn("warn","该邮箱已被注册，请重新填写！");
                }
            },
            error: function (e) {
                console.log(e);
            }
        })
    }
}