<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="header.jspf" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/hlight/an-old-hope.css">
        <script src="${pageContext.request.contextPath}/static/js/highlight.pack.js"></script>
        <title></title>
    </head>
    <style>
        #uprightsideBar {
            font-size: 14px;
            fontamily: Arial, Helvetica, sans-serif;
            text-align: left;
            position: fixed;
            top: 300px;
            right: 107px;
            width: auto;
            height: auto;
        }
        #sideBarTab {
            float: left;
            width: 30px;
            border: 1px solid #e5e5e5;
            border-right: none;
            text-align: center;
            background: rgb(237, 148, 161);
            background-color: rgb(237, 148, 161);
            background-image: none;
            background-repeat: repeat;
            background-attachment: scroll;
            background-clip: border-box;
            background-origin: padding-box;
            background-position-x: 0%;
            background-position-y: 0%;
            background-size: auto auto;
            color: white;
            border-radius: 10px;
            font-family: PingFangSC-Regular, Verdana, Arial, '微软雅黑','宋体',FangSong,Tahoma,KaiTi,STXingkai,NSimSun;
        }

        #sideBarContents {
            float: left;
            overflow: auto;
            overflow-x: hidden;
            width: 200px;
            min-height: 108px;
            max-height: 460px;
            border: 1px solid #e5e5e5;
            border-right: none;
            background: #fff;
        }

        #sideBarTab>h2 {
            font-size:14px;
        }

    #blogInfo {
	/* float: left; */
	vertical-align: top;
	padding: 10px;
	height: 100%;
	/* margin-left: 15px; */
	/* margin-right: 15px; */
	text-align: center;
	width: 100px;
}
#blogText {
	vertical-align: top;
	padding: 15px;
	word-break: break-all;
}
    #blogInfo,.replyInfo{
        background-color: #bfc8ef;
        width: 100px;
        height: 100%;
        padding: 5px;
    }
        #blogInfo img:first-child,.replyInfo img:first-child{
            width: 100px;
            height: 100px;
            border-radius: 50%;
        }
        .reply {
            background-color: #f7f8fa;
            padding: 10px 20px;
        }
        .replyRight img {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            padding-right: 5px;
            padding-top: 5px;
        }
        .replyText,#blogText {
            min-height: 100px;
            margin-right: 0px;
        }
        .replyLink {
            float: left;
            margin: 10px 20px;
            padding: 5px;
            border: #ccc 1px solid;
            background-color: white;
        }
    .replyRight {
        vertical-align: top;
        padding: 15px;
        word-break: break-all;
    }
    /* MathJax v2.7.5 from 'cdnjs.cloudflare.com' */
    .mjx-chtml {
        outline: 0;
    }
    .MJXc-display {
        overflow-x: auto;
        overflow-y: hidden;
    }
    </style>
    <body>
        <%@include file="navbar.jspf" %>
        <div class="container-fluid" id="Blog" style="margin-top: 5%;">
            <div class="panel panel-primary" style="word-break: break-all;">
                <div class="panel-heading" id="blogTitle">
                </div>
                <table style="width: 100%">
                    <tbody>
                        <tr>
                            <td id="blogInfo">
                                <div>
                                    <img src="" onerror="this.src='${pageContext.request.contextPath}/static/img/default.jpg'">
                                </div>
                                <div style="text-align: center;" id="blogUsername">
                                    
                                </div>
                            </td>
                            <td class="replyRight">
                                <div id="blogText">
                                </div>
                                <div class="replyLink"><a href="#blogReply" data-toggle="modal" onclick="setReply()">回复</a></div>
                                <div class="replyLink"><a id="blogCollect" onclick="blogCollect()">收藏</a></div>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <div class="panel-footer" id="blogFooter">

                </div>
            </div>
            <div id="Commtents"></div>
            <div class="modal fade" id="blogReply"  aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="min-width: 700px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                回复
                            </h4>
                        </div>
                        <textarea id="editor" placeholder="在此输入内容..."></textarea>
                        <script src="${pageContext.request.contextPath}/static/js/tinymce/tinymce.min.js"></script>
<%--                        <script src="${pageContext.request.contextPath}/static/js/mathjax/config.js" type="text/javascript" charset="utf-8"></script>--%>
                        <script>
                            tinymce.init({
                                selector: "#editor",
                                plugins: ['code','advlist','autolink','link','lists','preview','autoresize','autosave','charmap','codesample','emoticons','fullscreen','help','hr','image','insertdatetime','link','media','print','searchreplace','table','textpattern','visualblocks','wordcount'],
                                toolbar: [
                                    'code preview | undo redo restoredraft | formatselect fontselect fontsizeselect | forecolor backcolor bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | indent outdent | searchreplace copy cut paste selectall removeformat | bullist numlist table | charmap emoticons codesample mathjax hr image media insertdatetime link blockquote subscript superscript | visualblocks wordcount help'

                                ],
                                external_plugins: {'mathjax': "/static/js/mathjax/plugin.min.js"},
                                mathjax: {
                                    lib: "/static/js/mathjax/tex-mml-chtml.js",
                                },
                                custom_undo_redo_levels: 10,
                                menubar: false,
                                min_height: 500,
                                fontsize_formats: '10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                                font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                                image_caption: true,
                                autosave_ask_before_unload: false,
                                images_upload_url: '/api/upload/img',
                                images_upload_base_path: '/media/img/',
                                language: 'zh_CN',
                            })
                        </script>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="reply()" data-dismiss="modal">提交</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="blogCEdit"  aria-labelledby="myModalLabel1" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content" style="min-width: 700px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel1">
                                回复
                            </h4>
                        </div>
                        <textarea id="editor1" placeholder="在此输入内容..."></textarea>
                        <script>
                            tinymce.init({
                                selector: "#editor1",
                                plugins: ['code','advlist','autolink','link','lists','preview','autoresize','autosave','charmap','codesample','emoticons','fullscreen','help','hr','image','insertdatetime','link','media','print','searchreplace','table','textpattern','visualblocks','wordcount'],
                                toolbar: [
                                    'code preview | undo redo restoredraft | formatselect fontselect fontsizeselect | forecolor backcolor bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | indent outdent | searchreplace copy cut paste selectall removeformat | bullist numlist table | charmap emoticons codesample mathjax hr image media insertdatetime link blockquote subscript superscript | visualblocks wordcount help'

                                ],
                                external_plugins: {'mathjax': "/static/js/mathjax/plugin.min.js"},
                                mathjax: {
                                    lib: "/static/js/mathjax/tex-mml-chtml.js",
                                },
                                custom_undo_redo_levels: 10,
                                menubar: false,
                                min_height: 500,
                                fontsize_formats: '10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                                font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                                image_caption: true,
                                autosave_ask_before_unload: false,
                                images_upload_url: '/api/upload/img',
                                images_upload_base_path: '/media/img/',
                                language: 'zh_CN',
                            })
                        </script>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="CEdit()" data-dismiss="modal">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/static/js/blog.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-MML-AM_CHTML"></script>
        <script type="text/javascript">
             let isMathjaxConfig = false; // 防止重复调用Config，造成性能损耗

            const initMathjaxConfig = () => {
              if (!window.MathJax) {
                return;
              }
              window.MathJax.Hub.Config({
                showProcessingMessages: false, //关闭js加载过程信息
                messageStyle: "none", //不显示信息
                jax: ["input/TeX", "output/HTML-CSS"],
                tex2jax: {
                  inlineMath: [["$", "$"], ["\\(", "\\)"]], //行内公式选择符
                  displayMath: [["$$", "$$"], ["\\[", "\\]"]], //段内公式选择符
                  skipTags: ["script", "noscript", "style", "textarea", "pre", "code", "a"] //避开某些标签
                },
                "HTML-CSS": {
                  availableFonts: ["STIX", "TeX"], //可选字体
                  showMathMenu: false //关闭右击菜单显示
                }
              });
              isMathjaxConfig = true; //
            };
             if (isMathjaxConfig === false) { // 如果：没有配置MathJax
                 initMathjaxConfig();
             }
         </script>
        <%@include file="footer.jspf" %>
        <% if (user != null) { %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/media/<%=user.getUid()%>/css/blog.css">
        <script src="${pageContext.request.contextPath}/media/<%=user.getUid()%>/js/blog.js"></script>
        <% } %>
    </body>
</html>