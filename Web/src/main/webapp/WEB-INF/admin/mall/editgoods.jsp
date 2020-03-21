<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body p {
        color: rgba(0, 0, 0, 1);
    }
</style>
<div class="panel panel-info">
    <div class="panel-heading">
        商品编辑
        <front style="float: right;">
            <span class="glyphicon glyphicon-arrow-left"><a href="javascript:loadAllGoods();">返回</a></span>
        </front>
    </div>
    <div class="panel-body" style="background-color: #d6caf9">
        <div class="tabbable">
            <h1 style='text-align:center' id="goodsTitle">
                Title
            </h1>
            <br />
            <div class="form-group row" id="goodsImage">
                <label class="col-xs-2 control-label">封面图url：</label>
                <div class="col-xs-10 ">
                    <input type="text" class="form-control" placeholder="请输入封面图url"/>
                </div>
            </div>
            <div class="form-group row" id="goodsPrice">
                <label class="col-xs-2 control-label">价格：</label>
                <div class="col-xs-10 ">
                    <input type="text" class="form-control" placeholder="请输入商品价格"/>
                </div>
            </div>
            <div class="form-group row" id="goodsCnt">
                <label class="col-xs-2 control-label">库存：</label>
                <div class="col-xs-10 ">
                    <input type="text" class="form-control" placeholder="请输入商品库存"/>
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    Goods Description
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="tinyMCE.editors['editor'].setContent($('#goodsDesc').html())">编辑</a>
                    </front>
                </div>
                <div class='panel-body' id="goodsDesc">
                </div>
            </div>

            <input type="button" class="form-control" value="提交" onclick="editSubmit()">
            <br />
        </div>
        <div class="modal fade" id="edit"  aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" style="min-width: 700px;">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel">
                            编辑
                        </h4>
                    </div>
                    <textarea id="editor" placeholder="在此输入内容..."></textarea>
                    <script src="${pageContext.request.contextPath}/static/js/tinymce/tinymce.min.js"></script>
                    <script>
                        tinymce.init({
                            selector: "#editor",
                            plugins: ['code','advlist','autolink','link','lists','preview','autoresize','autosave','charmap','codesample','emoticons','fullscreen','help','hr','image','insertdatetime','link','media','print','searchreplace','table','textpattern','visualblocks','wordcount'],
                            toolbar: [
                                'code preview | undo redo restoredraft | formatselect fontselect fontsizeselect | forecolor backcolor bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | indent outdent | searchreplace copy cut paste selectall removeformat | bullist numlist table | charmap emoticons codesample hr image media insertdatetime link blockquote subscript superscript | visualblocks wordcount help'

                            ],
                            custom_undo_redo_levels: 10,
                            menubar: false,
                            min_height: 500,
                            fontsize_formats: '10px 12px 14px 16px 18px 24px 36px 48px 56px 72px',
                            font_formats: '微软雅黑=Microsoft YaHei,Helvetica Neue,PingFang SC,sans-serif;苹果苹方=PingFang SC,Microsoft YaHei,sans-serif;宋体=simsun,serif;仿宋体=FangSong,serif;黑体=SimHei,sans-serif;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;',
                            image_caption: true,
                            textpattern_patterns: [
                                {start: '*', end: '*', format: 'italic'},
                                {start: '**', end: '**', format: 'bold'},
                                {start: '#', format: 'h1'},
                                {start: '##', format: 'h2'},
                                {start: '###', format: 'h3'},
                                {start: '####', format: 'h4'},
                                {start: '#####', format: 'h5'},
                                {start: '######', format: 'h6'},
                                {start: '1. ', cmd: 'InsertOrderedList'},
                                {start: '* ', cmd: 'InsertUnorderedList'},
                                {start: '- ', cmd: 'InsertUnorderedList' }
                            ],
                            autosave_ask_before_unload: false,
                            images_upload_url: '/api/upload/img',
                            images_upload_base_path: '/media/img/',
                            language: 'zh_CN',
                        })
                    </script>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="Edit()" data-dismiss="modal">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>

    if (typeof gid == "undefined") {
        var gid;
    }
    gid = location.hash.substring(1,5);

    function Edit() {
        $("#goodsDesc").html(tinyMCE.editors["editor"].getContent());
    }

    function editSubmit() {
        let cnt = parseInt($("#goodsCnt input").val());
        let price = parseInt($("#goodsPrice input").val());
        let image = $("#goodsImage").val()
        if (price == null || isNaN(price) || price < 0) {
            narn('log','请输入正确的商品价格')
        } else if (cnt == null || isNaN(cnt) || cnt < 0) {
            narn('log','请输入正确的商品数量')
        } else {
            $.ajax({
                url: "/api/mall/edit/" + gid,
                type: "PUT",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    "title": $("#goodsTitle").text(),
                    "desciption": $("#goodsDesc").html(),
                    "price": price,
                    "count": cnt,
                    "image": image
                }),
                success: function (res) {
                    if (res.state === 0) {
                        narn("success", "更新成功");
                    } else {
                        narn("log", "更新失败");
                    }
                }
            })
        }
    }

    $.getJSON("/api/mall/info/"+gid, function (res) {
        $("#goodsTitle").text(res.title)
        $("#goodsPrice input").val(res.price)
        $("#goodsCnt input").val(res.cnt)
        $("#goodsDesc").html(res.text)
        $("#goodsImage input").val(res.image)
    })

    $("#goodsTitle").on("click",function () {
        let a = prompt("输入新标题",$("#goodsTitle").text());
        if (a != null) {
            $("#goodsTitle").text(a);
        }
    })
</script>