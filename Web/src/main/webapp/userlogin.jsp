<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登陆 - DOJ</title>
    <%@include file="header.jspf" %>
</head>
<body>
<%@include file="navbar.jspf" %>
<style>
    .form-control {
        display: inline;
        width: 90%;
        height: 34px;
        padding: 6px 12px;
        font-size: 14px;
        line-height: 1.42857143;
        color: #555;
        background-color: #fff;
        background-image: none;
        border: 1px solid #ccc;
        border-radius: 4px;
        -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
        box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
        -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
        -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
        transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    }
</style>
<style>
    .rightValidate { width: 280px; margin: 0px auto; position: relative; line-height: 33px; height: 33px; text-align: center; z-index: 99; }
    .v_rightBtn { position: absolute; left: 0; top: 0; height: 33px; width: 40px; background: #ddd; cursor: pointer; }
    .imgBtn{ width:44px; height: 171px; position: absolute; left: 0;  }
    .imgBtn img{ width:100%;z-index:99; }
    .imgBg{ position:absolute;bottom:35px;width: 280px; height: 171px; box-shadow: 0px 4px 8px #3C5476; display:none;z-index:9;}
    .hkinnerWrap{ border: 1px solid #eee; }
    .green{ border-color:#34C6C2 !important; }
    .green .v_rightBtn{ background: #34C6C2; color: #fff; }
    .red{ border-color:red !important; }
    .red .v_rightBtn{ background: red; color: #fff; }
    .refresh{ position: absolute; width: 30px; height: 30px; right: 4px; top: 4px; font-size: 12px; color: #fff; text-shadow: 0px 0px 9px #333; cursor: pointer; display: none; }
    .notSel{ user-select: none; -webkit-user-select: none; -moz-user-select: none; -ms-user-select: none; -webkit-touch-callout: none; }
</style>
<div class="container-fluid" style="margin-top: 10%;">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form class="form-horizontal" id="userRegister" onsubmit="return false">
                <div class="form-group">
                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-8">
                        <input type="text" id="username" name="username" class="form-control"/>
                        <em style="color: red;">*</em>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-8">
                        <input type="password" id="password" name="password" class="form-control"/>
                        <em style="color: red;">*</em>
                    </div>
                </div>
                <br />
                <br />
                <div class="form-group">
                    <div class="comImageValidate rightValidate">
                        <div class="imgBg">
                            <div class="imgBtn">
                                <img />
                            </div>
                            <span class="refresh" >
                                <img alt="" src="${pageContext.request.contextPath}/static/img/targets/refresh.jpg">
                            </span>
                        </div>
                        <div class="hkinnerWrap" style="height: 33px;position: relative">
                            <span  class="v_rightBtn "><em class="notSel">→</em></span>
                            <span class="huakuai"  style="font-size: 12px;line-height: 33px;color: #A9A9A9;">向右滑动滑块填充拼图完成验证</span>
                            <input type = "hidden" name="validX"/>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" disabled="disabled" onclick="loginSubmit()">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/userlogin.min.js"></script>
<script>
    var tokenId = "";
    var y = "";
    var x = "";
    $(".comImageValidate").ready(function () {
        validateImageInit();
        $(".refresh").click(function () {
            validateImageInit();
        })
        $(".hkinnerWrap").mouseover(function(){
            $(".imgBg").css("display","block");
            $(".refresh").css("display","block");
        }).mouseleave(function () {
            $(".imgBg").css("display","none");
            $(".refresh").css("display","none");
        });

        $(".imgBg").mouseover(function(){
            $(".imgBg").css("display","block");
            $(".refresh").css("display","block");
        }).mouseleave(function () {
            $(".imgBg").css("display","none");
            $(".refresh").css("display","none");
        });


        $('.v_rightBtn').on({
            mousedown: function(e) {
                $(".huakuai").html("");
                $(".hkinnerWrap").removeClass("red green")
                var el = $(this);
                var os = el.offset();
                dx = e.pageX - os.left;
                //$(document)
                $(this).parents(".hkinnerWrap").off('mousemove');
                $(this).parents(".hkinnerWrap").on('mousemove', function(e) {
                    var newLeft=e.pageX - dx;
                    el.offset({
                        left: newLeft
                    });
                    var newL=parseInt($(".v_rightBtn").css("left"));
                    if(newL<=0){
                        newL=0;
                    }else if (newL>=298){
                        newL=306;
                    }
                    $(".v_rightBtn").css("left",newL+"px");
                    $(".imgBtn").offset({
                        left: newLeft
                    });
                    $(".imgBtn").css("left",newL+"px")
                }).on('mouseup', function(e) {
                    //$(document)
                    $(this).off('mousemove');
                })
            }
        }).on("mouseup",function () {
            $(this).parents(".hkinnerWrap").off('mousemove');
            var l=$(this).css("left");
            if(l.indexOf("px")!=-1){
                l=l.substring(0,l.length-2);
            }
            x = l;


            submitDate(l,y,tokenId)
        })

    });
    /*图形验证*/
    function submitDate(x,y,tokenId) {
        console.log(x);
        console.log(y);
        console.log(tokenId);


        $.ajax({
            url:"/api/util/img/check?X="+x+"&Y="+y,
            dataType:'json',
            type: "POST",
            success:function (data) {
                if(data==true){
                    $(".hkinnerWrap").addClass("green").removeClass("red");
                    $(".hkinnerWrap input[name='validX']").val(x);
                    $("#X").val(x);
                    $("#Y").val(y);
                    // layer.msg("验证成功", {time:1000,icon:1})
                    $("div.form-group div button").removeAttr("disabled");
                } else {
                    $(".hkinnerWrap").addClass("red").removeClass("green");
                    setTimeout(function(){
                        $(".hkinnerWrap").removeClass("red green");
                        $(".v_rightBtn").css("left",0);
                        $(".imgBtn").css("left",0);
                    },280)
                    //validateImageInit();
                }
            }
        })
    }

    /*初始化图形验证码*/
    function validateImageInit() {
        $.ajax({
            url:"/api/util/img/get_img_verify",
            dataType:'json',
            cache:false,
            type: "get",
            success:function (data) {
                $(".huakuai").html("向右滑动滑块填充拼图");
                $(".imgBg").css("background",'#fff url("data:image/jpg;base64,'+data.oriCopyImage+'")');
                $(".imgBtn").find("img").css('margin-top',data.Y+'px');
                $(".imgBtn").find("img").attr("src","data:image/png;base64,"+data.newImage)
//                 tokenId=data.token;
                y=data.Y;
                $(".hkinnerWrap").removeClass("red green");
                $(".v_rightBtn").css("left",0);
                $(".imgBtn").css("left",0);
            },error:function(err){
                validateImageInit();
            }
        })
    }
</script>
</body>
</html>