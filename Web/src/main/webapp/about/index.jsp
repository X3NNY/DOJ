<%@page pageEncoding="UTF-8" %>
<%@page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <%@include file="/header.jspf" %>
    <script src="${pageContext.request.contextPath}/static/js/about/index.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/marked/marked.min.js"></script>
</head>
<body>
<%@include file="/navbar.jspf" %>
<style>
    /*---------------------------------------------------------------------------------------------
     *  Copyright (c) Microsoft Corporation. All rights reserved.
     *  Licensed under the MIT License. See License.txt in the project root for license information.
     *--------------------------------------------------------------------------------------------*/

    body {
        font-family: "Segoe WPC", "Segoe UI", "SFUIText-Light", "HelveticaNeue-Light", sans-serif, "Droid Sans Fallback";
        font-size: 14px;
        padding: 0 12px;
        line-height: 22px;
        word-wrap: break-word;
    }

    #code-csp-warning {
        position: fixed;
        top: 0;
        right: 0;
        color: white;
        margin: 16px;
        text-align: center;
        font-size: 12px;
        font-family: sans-serif;
        background-color:#444444;
        cursor: pointer;
        padding: 6px;
        box-shadow: 1px 1px 1px rgba(0,0,0,.25);
    }

    #code-csp-warning:hover {
        text-decoration: none;
        background-color:#007acc;
        box-shadow: 2px 2px 2px rgba(0,0,0,.25);
    }


    body.scrollBeyondLastLine {
        margin-bottom: calc(100vh - 22px);
    }

    body.showEditorSelection .code-line {
        position: relative;
    }

    body.showEditorSelection .code-active-line:before,
    body.showEditorSelection .code-line:hover:before {
        content: "";
        display: block;
        position: absolute;
        top: 0;
        left: -12px;
        height: 100%;
    }

    body.showEditorSelection li.code-active-line:before,
    body.showEditorSelection li.code-line:hover:before {
        left: -30px;
    }

    .vscode-light.showEditorSelection .code-active-line:before {
        border-left: 3px solid rgba(0, 0, 0, 0.15);
    }

    .vscode-light.showEditorSelection .code-line:hover:before {
        border-left: 3px solid rgba(0, 0, 0, 0.40);
    }

    .vscode-dark.showEditorSelection .code-active-line:before {
        border-left: 3px solid rgba(255, 255, 255, 0.4);
    }

    .vscode-dark.showEditorSelection .code-line:hover:before {
        border-left: 3px solid rgba(255, 255, 255, 0.60);
    }

    .vscode-high-contrast.showEditorSelection .code-active-line:before {
        border-left: 3px solid rgba(255, 160, 0, 0.7);
    }

    .vscode-high-contrast.showEditorSelection .code-line:hover:before {
        border-left: 3px solid rgba(255, 160, 0, 1);
    }

    img {
        max-width: 100%;
        max-height: 100%;
    }

    a {
        color: #4080D0;
        text-decoration: none;
    }

    a:focus,
    input:focus,
    select:focus,
    textarea:focus {
        outline: 1px solid -webkit-focus-ring-color;
        outline-offset: -1px;
    }

    hr {
        border: 0;
        height: 2px;
        border-bottom: 2px solid;
    }

    h1 {
        padding-bottom: 0.3em;
        line-height: 1.2;
        border-bottom-width: 1px;
        border-bottom-style: solid;
    }

    h1, h2, h3 {
        font-weight: normal;
    }

    h1 code,
    h2 code,
    h3 code,
    h4 code,
    h5 code,
    h6 code {
        font-size: inherit;
        line-height: normal;
    }

    a:hover {
        color: #4080D0;
        text-decoration: underline;
    }

    table {
        border-collapse: collapse;
    }

    table > thead > tr > th {
        text-align: left;
        border-bottom: 1px solid;
    }

    table > thead > tr > th,
    table > thead > tr > td,
    table > tbody > tr > th,
    table > tbody > tr > td {
        padding: 5px 10px;
    }

    table > tbody > tr + tr > td {
        border-top: 1px solid;
    }

    blockquote {
        margin: 0 7px 0 5px;
        padding: 0 16px 0 10px;
        border-left: 5px solid;
    }

    code {
        font-family: Menlo, Monaco, Consolas, "Droid Sans Mono", "Courier New", monospace, "Droid Sans Fallback";
        font-size: 14px;
        line-height: 19px;
    }

    body.wordWrap pre {
        white-space: pre-wrap;
    }

    .mac code {
        font-size: 12px;
        line-height: 18px;
    }

    pre:not(.hljs),
    pre.hljs code > div {
        padding: 16px;
        border-radius: 3px;
        overflow: auto;
    }

    /** Theming */

    .vscode-light,
    .vscode-light pre code {
        color: rgb(30, 30, 30);
    }

    .vscode-dark,
    .vscode-dark pre code {
        color: #DDD;
    }

    .vscode-high-contrast,
    .vscode-high-contrast pre code {
        color: white;
    }

    .vscode-light code {
        color: #A31515;
    }

    .vscode-dark code {
        color: #D7BA7D;
    }

    .vscode-light pre:not(.hljs),
    .vscode-light code > div {
        background-color: rgba(220, 220, 220, 0.4);
    }

    .vscode-dark pre:not(.hljs),
    .vscode-dark code > div {
        background-color: rgba(10, 10, 10, 0.4);
    }

    .vscode-high-contrast pre:not(.hljs),
    .vscode-high-contrast code > div {
        background-color: rgb(0, 0, 0);
    }

    .vscode-high-contrast h1 {
        border-color: rgb(0, 0, 0);
    }

    .vscode-light table > thead > tr > th {
        border-color: rgba(0, 0, 0, 0.69);
    }

    .vscode-dark table > thead > tr > th {
        border-color: rgba(255, 255, 255, 0.69);
    }

    .vscode-light h1,
    .vscode-light hr,
    .vscode-light table > tbody > tr + tr > td {
        border-color: rgba(0, 0, 0, 0.18);
    }

    .vscode-dark h1,
    .vscode-dark hr,
    .vscode-dark table > tbody > tr + tr > td {
        border-color: rgba(255, 255, 255, 0.18);
    }

    .vscode-light blockquote,
    .vscode-dark blockquote {
        background: rgba(127, 127, 127, 0.1);
        border-color: rgba(0, 122, 204, 0.5);
    }

    .vscode-high-contrast blockquote {
        background: transparent;
        border-color: #fff;
    }
</style>
<div class="container-fluid" style="margin-top: 5%">
    <div class="row clearfix">
        <div class="col-md-1 column"></div>
        <div class="col-md-10 column">
            <div><h1>关于</h1>
                <h2>序言</h2>
                <ul><li><p>Dreamer Online Judge(追梦在线评测系统)以下简称DOJ是一个致力于为所有算法爱好者提供一个良好的学习环境。</p></li><li><p>第一代DOJ由Xenny &amp; Pandora共同完成，Xenny在大学期间参加ACM竞赛，接触了很多OJ，但每种OJ都有它的优点，亦有其缺点，总是有些不尽人意的地方值得改进，所以Xenny想自己开发一个OJ，最初的设想是集合各大OJ的优点，以及自身学习算法路上的一些经验。去构造一个完美的OJ。这就是DOJ的出发点，同样也是其目标。</p></li><li><p>Xenny的个人开发之路因为技术问题受到了阻碍，因为没有开发经验，DOJ项目一直被搁置下来，直到一次偶然的机会，结交了同专业的伙伴Pandora，他不仅熟练掌握各大Java框架，同样也还是一名算法爱好者。仅仅简短的交谈便确定了开发DOJ。</p></li><li><p>在beta版本上线之前，其实经历了几次失败的开发，经过不断的磨合，最终确定了开发思路以及流程。也造就了DOJ的beta版本。</p></li><li><p>至此，DOJ终于上线了，而且我们相信，DOJ最终能够达到最初的目标，成为算法爱好者心目中完美的OJ。</p></li></ul>
                <hr/>
                <h2>介绍</h2>
                <ul><li><p>对于刚接触在线评测系统即OJ的用户可以先阅读本篇内容<a href="http://120.79.31.49/blog.jsp?bid=14">什么是OJ</a></p></li><li><p>DOJ我将其解释为追梦者OJ，我希望以这个名字警戒不要忘记最初的目标，但同时也要脚踏实地，不然追梦者将变为白日梦。同样，我也希望每一个使用DOJ的人都能在这里找到他所追寻的“梦想”。</p></li><li><p>以下是DOJ各语言的编译参数以及编译器版本。(对于Py，会先将其转为字节码.pyo以提升速度)</p>
                    <table>
                        <thead>
                        <tr>
                            <th style="text-align:center">语言</th>
                            <th style="text-align:center">编译器</th>
                            <th style="text-align:center">编译参数</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td style="text-align:center">C</td>
                            <td style="text-align:center">GCC 7.4</td>
                            <td style="text-align:center">gcc main.c -o main -O2 -lm -std=c11 -DDOJ</td>
                        </tr>
                        <tr>
                            <td style="text-align:center">C++</td>
                            <td style="text-align:center">GCC 7.4</td>
                            <td style="text-align:center">g++ main.cpp -o main -O2 -lm -std=c++11 -DDOJ</td>
                        </tr>
                        <tr>
                            <td style="text-align:center">Java</td>
                            <td style="text-align:center">openjdk 11</td>
                            <td style="text-align:center">javac Main.java</td>
                        </tr>
                        <tr>
                            <td style="text-align:center">Python</td>
                            <td style="text-align:center">Python 3.6.9</td>
                            <td style="text-align:center">python3 -O -m py_compile main.py</td>
                        </tr>
                        </tbody>
                    </table></li><li><p>DOJ所有题目均为原创，不设立爬虫爬取其他OJ，同时DOJ会开放其部分接口文档以供大家编写插件或DIY使用，详情请转至<a href="http://acmer.vip/about/api/">DOJ的API</a></p></li><li><p>DOJ允许用户自行设计其主题，允许用户上传自己的CSS和JS文件，允许不破坏其他用户正常使用体验的机器爬虫，我们将尽可能的提供一个更加开放的环境，同时我们也提供了已经编写好的主题和插件，供用户自行选择使用。</p></li><li><p>DOJ也是一个开源OJ，欢迎用户在遵守开源协议的基础上构造自己的DOJ，也欢迎给参与DOJ的维护，开源地址<a href="https://github.com/X3NNY/DOJ">DOJ</a></p></li><li><p>DOJ比赛设置了CF、ICPC、OI和IOI四种赛制，题目设置了传统题、交互题、接口编写题以及提交答案题四种题型，支持特判程序。比赛期间禁止提交题库中的题目，非比赛期间提交题目若结果不正确将返回其错误数据以及正确数据供比较。同时用户可以下载每一道题目的测试数据，以供对比，当数据较弱时，支持用户上传hack数据进入总数据中。</p></li><li><p>DOJ欢迎用户申请比赛，正式比赛将计算Rating，同时我们提供了DIY比赛的功能，用户可自行使用题库内的题创建DIY比赛以供训练。</p></li><li><p>以上是关于DOJ的一个简短介绍，关于DOJ各部分的详细使用说明请转至<a href="http://120.79.31.49/blog.jsp?bid=15">DOJ使用说明</a></p></li></ul>
                <hr/>
                <h2>使用准则</h2>
                <ul><li><p>DOJ秉承着开放、自由、共享的精神，欢迎每一个算法爱好者使用，但我们希望用户能保持这种精神使用DOJ，例如如果您要上传题目，您的所有测试数据以及std程序等将会被公开，又例如如果您要DIY一个有密码的比赛，比赛结束后也将公开这场DIY比赛。</p></li><li><p>DOJ需要每一个使用者的维护与热爱，如果您在使用DOJ中有什么不满之处，欢迎在<a href="http://120.79.31.49/blog.jsp?bid=2">DOJ使用反馈</a>中将需求详细阐述给我们，我们将尽可能的进行版本更新，同时如若在使用过程中遇到了BUG，请在<a href="http://120.79.31.49/blog.jsp?bid=2">BUG提交</a>中将bug提交给我们，我们将尽快修复并作出补偿。</p></li><li><p>DOJ采用邀请码注册方式，邀请码可通过购买或系统发放的形式获得，采用这种注册方式的原因是希望每一个使用DOJ的用户都能够维护和热爱DOJ，对于以下几种使用行为一经发现后永久封禁账号，对于违法行为将依法追究其法律责任。</p><ol><li>恶意利用DOJ提供的资源妨碍他人使用，例如编写bot短时间内提交大量代码妨正常评测。</li><li>恶意抄袭他人成果，例如使用其他用户公开的代码刷AC数量。</li><li>恶意利用DOJ存在的漏洞进行入侵造成数据泄露，如果您发现了DOJ存在的漏洞，请联系我们，我们将尽快修复并作出奖励，但如果将漏洞私用进行非法获取或篡改数据，我们将依法追究其法律责任。</li></ol></li></ul>
                <hr/>
                <h2>Ends</h2>
                <ul><li>DOJ第一代的开发工作和现在的维护工作全由Xenny &amp; Pandora完成，希望用户能够帮助DOJ的成长，您的每一点付出，都能够帮助DOJ更快的接近目标，同时能够给您和其他用户提供更加完美的服务。</li></ul>
                <hr/>
                <p>编写于2020-01-17 04:16 Xenny.</p></div>
        </div>
    </div>
</div>
<%@include file="/footer.jspf" %>
</body>
</html>
