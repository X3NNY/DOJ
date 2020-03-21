<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <%@include file="header.jspf" %>
    <title>发布博文 - DOJ</title>
</head>
<body>
<%@include file="navbar.jspf" %>
<%
    user = (User) session.getAttribute("user");
    if (user==null) {
        response.sendRedirect("/userlogin.jsp");
    }
%>
<div class="container-fluid" style="margin-top: 5%;">
    <div class="panel panel-default">
        <div class="panel-heading">新的博客</div>
        <div class="panel-body" style="padding: 5px">
            <textarea id="editor" placeholder="在此输入内容..."></textarea>
            <script src="${pageContext.request.contextPath}/static/js/tinymce/tinymce.min.js"></script>
            <script src="${pageContext.request.contextPath}/static/js/mathjax/config.js" type="text/javascript" charset="utf-8"></script>
            <script src="${pageContext.request.contextPath}/static/js/mathjax/tex-mml-chtml.js" type="text/javascript" charset="utf-8"></script>
            <script>
                tinymce.init({
                    selector: "#editor",
                    plugins: ['code','advlist','autolink','link','lists','preview','autoresize','autosave','charmap','codesample','emoticons','fullscreen','help','hr','image','insertdatetime','link','media','print','searchreplace','table','textpattern','visualblocks','wordcount'],
                    toolbar: [
                        'code preview | undo redo restoredraft | formatselect fontselect fontsizeselect | forecolor backcolor bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | indent outdent',
                        'searchreplace copy cut paste selectall removeformat | bullist numlist table | charmap emoticons codesample mathjax hr image media insertdatetime link blockquote subscript superscript | fullscreen print visualblocks wordcount help'

                    ],
                    external_plugins: {'mathjax': "/static/js/mathjax/plugin.min.js"},
                    mathjax: {
                        lib: "/static/js/mathjax/tex-mml-chtml.js",
                        className: "math-tex",
                    },
                    custom_undo_redo_levels: 10,
                    menubar: false,
                    min_height: 500,
                    fontsize_formats: '10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                    font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                    image_caption: true,
                    images_upload_url: '/api/upload/img',
                    images_upload_base_path: '/media/img/',
                    language: 'zh_CN',
                })
            </script>
        </div>
        <div class="panel-footer">
            <a href="#setTitle" data-toggle="modal">确定</a>
        </div>
        <div class="modal fade" id="setTitle"  aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            标题
                        </h4>
                    </div>
                    <input placeholder="输入博文标题" id="blogTitle" type="text" class="form-control" style="width: 100%;" />
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="blogSubmit()" data-dismiss="modal">发布</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/newblog.min.js"></script>
<%@include file="footer.jspf" %>
</body>
</html>