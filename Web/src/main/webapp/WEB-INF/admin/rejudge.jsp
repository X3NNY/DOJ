<%@page contentType="text/html; charset=UTF-8" %>

<div class="panel panel-info">
    <div class="row clearfix">
        <div class="col-md-10 column">
            <h1 class="text-center">重判</h1>
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="rejudgesub" class="col-sm-2 control-label">重判单个评测</label>
                    <div class="col-sm-8">
                        <input type="text" id="rejudgesub" class="form-control" placeholder="请输入SID"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="reJudgeSid()">Submit</button>
                    </div>
                </div>
            </form>
            <br />
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="rejudgepro" class="col-sm-2 control-label">重判题目</label>
                    <div class="col-sm-8">
                        <input type="text" id="rejudgepro" class="form-control" placeholder="请输入PID"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="reJudgePro()">Submit</button>
                    </div>
                </div>
            </form>
            <br />
            <form class="form-horizontal" onsubmit="return false">
                <div class="form-group">
                    <label for="rejudgecon" class="col-sm-2 control-label">重判比赛</label>
                    <div class="col-sm-8">
                        <input type="text" id="rejudgecon" class="form-control" placeholder="请输入CID"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" onclick="reJudgeCid()">Submit</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function reJudgeSid() {
        let sid = $("#rejudgesub").val();
        $.ajax({
            url: "/api/submit/rejudge/sid/" + sid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "已加入重判队列")
                }
            }
        })
    }

    function reJudgePro() {
        let pid = $("#rejudgepro").val();
        $.ajax({
            url: "/api/submit/rejudge/pid/" + pid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "已加入重判队列")
                }
            }
        })
    }

    function reJudgeCid() {
        let cid = $("#rejudgecon").val();
        $.ajax({
            url: "/api/submit/rejudge/cid/" + cid,
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            success: function (res) {
                if (res.state === 0) {
                    narn("success", "已加入重判队列")
                }
            }
        })
    }
</script>