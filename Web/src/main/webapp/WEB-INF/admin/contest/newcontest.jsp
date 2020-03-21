<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/static/css/jquery-ui/jquery-ui.min.css">
<style>
    .ui-timepicker-div .ui-widget-header { margin-bottom: 8px; }
    .ui-timepicker-div dl { text-align: left; }
    .ui-timepicker-div dl dt { height: 25px; margin-bottom: -25px; }
    .ui-timepicker-div dl dd { margin: 0 10px 10px 65px; }
    .ui-timepicker-div td { font-size: 90%; }
    .ui-tpicker-grid-label { background: none; border: none; margin: 0; padding: 0; }
    .ui_tpicker_hour_label,.ui_tpicker_minute_label,.ui_tpicker_second_label,
    .ui_tpicker_millisec_label,.ui_tpicker_time_label{padding-left:20px}
</style>
<div class="panel panel-info">
    <div class="panel panel-info">
        <div class="panel-heading">
            新增比赛
        </div>
        <div class="panel-body" id="newContest">
            <h1 class="text-center" id="contestTitle">Title</h1>
            <div class="form-group">
                <div class="form-group row">
                    <label for="startTime" class="col-xs-2 control-label">开始时间：</label>
                    <div class="col-xs-10">
                        <input type="text" name="starttime" class="form-control" id="startTime" />
                    </div>
                </div>
                <div class="form-group row">
                    <label for="endsTime" class="col-xs-2 control-label">开始时间：</label>
                    <div class="col-xs-10">
                        <input type="text" name="endstime" class="form-control" id="endsTime" />
                    </div>
                </div>
                <div class="form-group row">
                    <label for="contestDesc" class="col-xs-2 control-label">比赛描述：</label>
                    <div class="col-xs-10">
                        <a id="hidden-a" href="#editConDesc" data-toggle="modal"></a>
                        <div id="contestDesc">点击编辑比赛描述</div>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-xs-2 control-label">比赛权限：</label>
                    <div class="col-xs-10">
                        <input type="radio" name="contestLevel" class="form-select-button" checked value="0" onclick="choosePublic()"/>公开
                        <input type="radio" name="contestLevel" class="form-select-button" value="1" onclick="choosePasswd()"/>密码
                    </div>
                </div>
                <div class="form-group row" id="contestPasswd" style="display: none">
                    <label class="col-xs-2 control-label">密码：</label>
                    <div class="col-xs-10 ">
                        <input type="text" class="form-control" placeholder="请输入比赛密码"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label for="contestType" class="col-xs-2 control-label">比赛类型：</label>
                    <div class="col-xs-10" id="contestType">
                        <select class="form-control">
                            <option value="0" selected>
                                ICPC赛制
                            </option>
                            <option value="1">
                                OI赛制
                            </option>
                            <option value="2">
                                IOI赛制
                            </option>
                            <option value="3">
                                ShortCode赛制
                            </option>
                            <option value="4">
                                MinTime赛制
                            </option>
                        </select>
                    </div>
                </div>
            </div>
            <br />
            <a href="#addConPro" data-toggle="modal" class="btn">添加新题</a>
            <table class="table table-striped table-hover" id="contestProblem">
                <thead>
                <tr>
                    <th>PID</th>
                    <th>Title</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="form-group">
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-default" onclick="newConSubmit()">Submit</button>
                </div>
            </div>
        </div>
        <div class="modal fade" id="editConDesc"  aria-labelledby="myModalLabel" aria-hidden="true">
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
                        <button type="button" class="btn btn-primary" onclick="editContestDesc()" data-dismiss="modal">提交</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="addConPro"  aria-labelledby="myModalLabel1" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content" style="min-width: 700px;">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title" id="myModalLabel1">
                            添加题目
                        </h4>
                    </div>
                    <div class="col-sm-4">
                        <label class="col-sm-1 control-label">PID</label>
                        <input type="text" name="pid" class="form-control" value=""/>
                    </div>
                    <div class="col-sm-4">
                        <label class="cole-sm-2 control-label">标题</label>
                        <input type="text" name="ptitle" readonly class="form-control" value="" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                        <button type="button" class="btn btn-primary" onclick="addContestPro()" data-dismiss="modal">提交</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $("#contestDesc").on("click",function () {
        $("#hidden-a").click()
    })
    $("#contestTitle").on("click",function () {
        let s = prompt("输入比赛标题",$("#contestTitle").text());
        if (s != null) {
            $("#contestTitle").text(s)
        }
    })
    $("#addConPro input[name='pid']").bind('input propertychange',function () {
        let pid = $(this).val();
        $.getJSON("/api/problem?pid="+pid,function (res) {
            if (res.pid == pid) {
                $("#addConPro input[name='ptitle']").val(res.title)
            } else {
                $("#addConPro input[name='ptitle']").val("没有该题")
            }
        })
    })

    function newConSubmit() {
        if ($("#startTime").val()==="" || $("#endsTime").val()==="") {
            narn("log","请填写比赛开始和结束时间");
            return;
        }
        let list = [];
        let table = $("#newContest table tbody tr");
        for (let i = 0; i < table.length; i++) {
            list.push(parseInt(table[i].attributes["id"].value));
        }
        Submit(list)
    }

    function Submit(list) {
        let type = parseInt($("#contestType option:selected").val());
        let level = parseInt($("input[name='contestLevel']:checked").val());
        let passwd = $("#contestPasswd input").val();
        if (type == null || type < 0 || type > 4) {
            narn('log','请选择正确的赛制')
        } else if (level == null || (level !== 0 && level !== 1)) {
            narn('log','请选择正确的权限')
        } else if (level === 1 && (passwd == null || passwd.length === 0)) {
            narn('log','请输入比赛密码')
        } else {
            $.ajax({
                url: "/api/contest/new",
                type: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify({
                    "title": $("#contestTitle").text(),
                    "startTime": new Date($("#startTime").val()),
                    "endsTime": new Date($("#endsTime").val()),
                    "desc": $("#contestDesc").html(),
                    "problems": list,
                    "level": level,
                    "password": passwd,
                    "type": type
                }),
                success: function (res) {
                    if (res.state === 0) {
                        narn("success", "添加比赛成功");
                    } else {
                        narn("log", "添加失败");
                    }
                }
            })
        }
    }

    function editContestDesc() {
         $("#contestDesc").html(tinyMCE.editors["editor"].getContent())
    }
    function addContestPro() {
        let table = $("#newContest table tbody");
        let pid = $("#addConPro input[name='pid']").val();
        if ($("#newContest table tbody tr#"+pid).length!==0) {
            narn("log","已经添加过了");
            return ;
        }
        table.append(HTML.trId(pid,HTML.td(pid)+HTML.td($("#addConPro input[name='ptitle']").val())+HTML.td(HTML.a("javascript:delContestPro("+pid+")","删除"))));
    }
    function delContestPro(pid) {
        let tr = $("table tbody tr#"+pid);
        tr.remove()
    }
</script>

<script src="/static/js/jquery-ui/jquery-ui.min.js"></script>
<script src="/static/js/jquery-ui/jquery-ui-timepicker-addon.min.js"></script>
<script>
    $("#startTime,#endsTime").datetimepicker({
        showSecond: false,
        showMillisec: false,
    })
</script>