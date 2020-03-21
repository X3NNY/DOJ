<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body p {
        color: rgba(0, 0, 0, 1);
    }
</style>
<div class="panel panel-info">
    <div class="panel-heading">
        添加题目
    </div>
    <div class="panel-body" style="background-color: #d6caf9">
        <div class="tabbable">
            <h1 style='text-align:center' id="problemTitle">
                Title
            </h1>
            <div class='row'>
                <div style='text-align:center'>
                    TimeLimit: <b id="problemTimeLimit">1000</b>ms&nbsp;&nbsp;MemoryLimit:<b id="problemMemoryLimit">32</b>MB
                </div>
                <div style='text-align:center'>
                    Type:
                    <select id="problemType">
                        <option value="0">
                            传统题
                        </option>
                        <option value="1">
                            交互题
                        </option>
                        <option value="2">
                            接口实现题
                        </option>
                        <option value="3">
                            提交答案题
                        </option>
                    </select>
                    &nbsp;&nbsp;Special:
                    <select id="problemSpecial">
                        <option value="0">
                            No
                        </option>
                        <option value="1">
                            Yes
                        </option>
                    </select>
                </div>
            </div>
            <br />
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    Problem Description
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('desc');">编辑</a>
                    </front>
                </div>
                <div class='panel-body' id="problemDesc">
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    Input
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('input');">编辑</a>
                    </front>
                </div>
                <div class='panel-body' id="problemInput">
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    Output
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('output');">编辑</a>
                    </front>
                </div>
                <div class='panel-body' id="problemOutput">
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    SampleInput
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('sinput');">编辑</a>
                    </front>
                </div>
                <div class='panel-body'><pre class='sample' id="problemSampleInput"></pre>
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    SampleOutput
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('soutput');">编辑</a>
                    </front>
                </div>
                <div class='panel-body'><pre class='sample' id="problemSampleOutput"></pre>
                </div>
            </div>
            <div class='panel panel-default'>
                <div class='panel-heading'>
                    Hint
                    <front style="float:right;">
                        <a href="#edit" data-toggle="modal" onclick="setPl('hint');">编辑</a>
                    </front>
                </div>
                <div class='panel-body' id="problemHint">
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
    if (typeof setP == "undefined") {
        var setP;
    }
    function setPl(s) {
        setP = s;
        let st;
        if (s === 'desc') {
            st = $("#problemDesc");
        } else if (s === 'input') {
            st = $("#problemInput");
        } else if (s === 'output') {
            st = $("#problemOutput");
        } else if (s === 'sinput') {
            st = $("#problemSampleInput");
        } else if (s === 'soutput') {
            st = $("#problemSampleOutput");
        } else if (s === 'hint') {
            st = $("#problemHint");
        }
        tinyMCE.editors["editor"].setContent(st.html());
    }

    function Edit() {
        let s = setP;
        let st;
        if (s === 'desc') {
            st = $("#problemDesc");
        } else if (s === 'input') {
            st = $("#problemInput");
        } else if (s === 'output') {
            st = $("#problemOutput");
        } else if (s === 'sinput') {
            st = $("#problemSampleInput");
        } else if (s === 'soutput') {
            st = $("#problemSampleOutput");
        } else if (s === 'hint') {
            st = $("#problemHint");
        }
        st.html(tinyMCE.editors["editor"].getContent());
        tinyMCE.editors["editor"].setContent("");
    }

    function editSubmit() {
        $.ajax({
            url: "/api/problem/new",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "title": $("#problemTitle").text(),
                "description": $("#problemDesc").html(),
                "input": $("#problemInput").html(),
                "output": $("#problemOutput").html(),
                "sampleinput": $("#problemSampleInput").html(),
                "sampleoutput": $("#problemSampleOutput").html(),
                "hint": $("#problemHint").html(),
                "timelimit": $("#problemTimeLimit").text(),
                "memorylimit": $("#problemMemoryLimit").text(),
                "type": $("#problemType option:selected").val(),
                "special": $("#problemSpecial option:selected").val() === 1
            }),
            success: function (res) {
                if (res.state === 0) {
                    narn("success","添加成功");
                    loadMyProblem();
                } else {
                    narn("log","添加失败");
                }
            }
        })
    }
    $("#problemTitle").on("click",function () {
        let a = prompt("输入新标题");
        if (a != null) {
            $("#problemTitle").text(a);
        }
    })
    $("#problemTimeLimit").on("click",function () {
        let a = prompt("输入新时限，单位:ms");
        if (a != null && a.match(/^\d+$/) != null) {
            $("#problemTimeLimit").text(a);
        }
    })
    $("#problemMemoryLimit").on("click",function () {
        let a = prompt("输入新空限，单位:MB");
        if (a != null && a.match(/^\d+$/) != null) {
            $("#problemMemoryLimit").text(a);
        }
    })
</script>