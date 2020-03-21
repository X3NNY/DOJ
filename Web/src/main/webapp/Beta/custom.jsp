<%@ page import="pers.dreamer.bean.User" %>
<%@page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/header.jspf" %>
    <title>Custom Test</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/codemirror/lib/codemirror.css">
    <script src="${pageContext.request.contextPath}/static/codemirror/lib/codemirror.js"></script>
    <script src="${pageContext.request.contextPath}/static/codemirror/mode/clike/clike.js"></script>
    <script src="${pageContext.request.contextPath}/static/codemirror/mode/python/python.js"></script>

    <style>
        .CodeMirror {
            border-top: 1px solid #eee;
            border-bottom: 1px solid #eee;
        }

        .cm-tab {
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADAAAAAMCAYAAAAkuj5RAAAAAXNSR0IArs4c6QAAAGFJREFUSMft1LsRQFAQheHPowAKoACx3IgEKtaEHujDjORSgWTH/ZOdnZOcM/sgk/kFFWY0qV8foQwS4MKBCS3qR6ixBJvElOobYAtivseIE120FaowJPN75GMu8j/LfMwNjh4HUpwg4LUAAAAASUVORK5CYII=);
            background-position: right;
            background-repeat: no-repeat;
        }
    </style>
</head>
<body>
<%@include file="/navbar.jspf" %>

<div class="container-fluid" style="margin-top: 5%">
    <div class="panel panel-primary">
        <div class="panel-heading">
            Custom Test
        </div>
        <div class="panel-body">
            <div class="col-md-7 column">
                <article>
                    代码：
                    <form>
                        <textarea id="code" name="code" style="display: none;"></textarea>
                    </form>
                    <script>
                        var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
                            lineNumbers: true,
                            tabSize: 4,
                            indentUnit: 4,
                            indentWithTabs: true,
                            mode: "text/x-c++src"
                        });
                    </script>
                </article>
                    <button type="button" class="btn btn-sm btn-primary" style="margin-left: 50%;margin-top: 20px;" onclick="run()">Run!</button>

            </div>
            <div class="col-md-5 column">
                语言: <select style="min-width: 200px;">
                <option value="GCC">GCC</option>
                <option value="G++">G++</option>
                <option value="Python">Python</option>
                <option value="JAVA">JAVA</option>
            </select>
                <br />
                输入：
                <textarea name="input" class="form-control" cols="10" rows="6"></textarea>
                输出：
                <textarea name="output" class="form-control" cols="10" rows="6"></textarea>
                * 只会显示前255个字符
            </div>
        </div>
    </div>
</div>
<script>
    function run() {
        narn("log","未配置Judge端")
        const code = editor.getValue();
        const lang = $("div select option:selected").val();
        const input = $("div textarea");
        $.ajax({
            url: "/beta/custon",
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({
                "code": code,
                "lang": lang,
                "input": input,
            }),
            success: function (res) {
                waitingOutput()
            },
        })
    }
    function waitingOutput() {

    }
</script>
<%@include file="/footer.jspf" %>
</body>
</html>