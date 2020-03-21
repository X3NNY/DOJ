<%@page contentType="text/html; charset=UTF-8" %>
<div class="panel panel-info">
    <div class="panel-heading">
        添加商品
    </div>
    <div class="panel-body">
        <h1 class="text-center">添加商品</h1>
        <form class="form-horizontal" onsubmit="return false;">
            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">名称</label>
                <div class="col-sm-8">
                    <input type="text" id="name" class="form-control" placeholder="请输入商品名"/>
                </div>
            </div>
            <div class="form-group">
                <label for="desc" class="col-sm-2 control-label">描述</label>
                <div class="col-sm-8">
                    <textarea  id="desc" class="form-control" placeholder="请输入商品描述"></textarea>
                </div>
            </div>
            <div class="form-group">
                <label for="num" class="col-sm-2 control-label">库存</label>
                <div class="col-sm-8">
                    <input type="text" id="num" class="form-control" placeholder="请输入商品库存"/>
                </div>
            </div>
            <div class="form-group">
                <label for="price" class="col-sm-2 control-label">价格</label>
                <div class="col-sm-8">
                    <input type="text" id="price" class="form-control" placeholder="请输入商品价格"/>
                </div>
            </div>
            <div class="form-group">
                <label for="img" class="col-sm-2 control-label">描述图</label>
                <div class="col-sm-8">
                    <input type="text" id="img" class="form-control" placeholder="请输入图片URL"/>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" onclick="addGoods()" class="btn btn-default">Submit</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/tinymce/tinymce.min.js"></script>
<script>
    tinymce.init({
        selector: "#desc",
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
        autosave_ask_before_unload: false,
        images_upload_url: '/api/upload/img',
        images_upload_base_path: '/media/img/',
        language: 'zh_CN',
    })

    function addGoods() {
        let price = parseInt($("#price").val());
        let num = parseInt($("#num").val());
        if (!price || isNaN(price) || price<0 || price>10000) {
            narn('log',"商品的价格只能为1-10000")
        } else if (!num || isNaN(num) || num<0 || num>100) {
            narn('log','商品的库存只能为1-100')
        } else {
            $.ajax({
                url: "/api/mall/addgoods",
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    "name": $("#name").val(),
                    "count": num,
                    "price": price,
                    "desciption": tinyMCE.editors['desc'].getContent(),
                    "image": $("#img").val()
                }),
                success: function (res) {
                    if (res.state === 0) {
                        narn("success",'添加商品成功')
                    } else {
                        narn('log','添加失败')
                    }
                }
            })
        }
    }
</script>