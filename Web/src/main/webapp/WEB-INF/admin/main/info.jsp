<%@page contentType="text/html; charset=UTF-8" %>
<h1 class="text-center" style="font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif"><b style="color: #00ff80">DOJ 信息统计</b></h1>
<div class="panel panel-info">
    <div class="message" id="picMessage1" style="min-width:400px;height:400px">

    </div>
</div>

<div class="panel panel-info">
    <div class="message" id="picMessage2" style="min-width:400px;height:400px">

    </div>
</div>

<div class="panel panel-info">
    <div class="message" id="picMessage3" style="min-width:400px;height:400px">

    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/highcharts/highcharts.js"></script>
<%--<script src="https://code.highcharts.com.cn/highcharts/modules/exporting.js"></script>--%>
<script>
    loadPicMessage1()
    loadPicMessage2()
    //loadPicMessage3()

    function loadInfo(s,title,ytitle,format,se) {
        Highcharts.chart(s, {
            title: {
                text: title
            },
            xAxis: {
                type: 'datetime',
                tickmarkPlacement: 'on',
                dateTimeLabelFormats:{
                    day: '%m-%e',
                },
                title: {
                    enabled: false
                }
            },
            yAxis: [{
                allowDecimals:false,
                title: {
                    text: ytitle
                },
                min:0,
                labels: {
                    formatter: function() {
                        return this.value;
                    }
                }
            }],
            legend: {
                align: 'left',
                verticalAlign: 'top',
                y: 20,
                floating: true,
                borderWidth: 0
            },
            tooltip: {
                shared: true,
                valueSuffix: '',
                formatter: format
            },
            series: se
        });
    }

    function loadPicMessage1() {
        $.getJSON('/api/visual/index/submit', function (data) {
            let se = [{
                name: "submit",
                data: data.submit
            },{
                name: "ac",
                data: data.ac
            }];

            loadInfo('picMessage1','30天内提交统计','提交数',function () {
                return '<b>开始时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x-86400000)+'<br>'+
                    '<b>结束时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x)+'<br>'+
                    '<b>总提交数:</b>' + this.points[0].y+"<br>"+
                    '<b>总通过数:</b>' + this.points[1].y;
            },se)
        });
    }
    function loadPicMessage2() { //30天注册人数
        $.getJSON('/api/visual/admin/register', function (data) {
            let se = [{
                name: "users",
                data: data
            }];

            loadInfo('picMessage2','30天内注册统计','注册数',function () {
                return '<b>开始时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x-86400000)+'<br>'+
                    '<b>结束时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x)+'<br>'+
                    '<b>注册人数:</b>' + this.points[0].y+"<br>";
            },se)
        });
    }
    // function loadPicMessage3() { //30天内平均活跃度及最高活跃度
    //     $.getJSON('/api/visual/admin/active', function (data) {
    //         let se = [{
    //             name: "average",
    //             data: data.average
    //         },{
    //             name: "max",
    //             data: data.max
    //         }];
    //
    //         loadInfo('picMessage3','30天内活跃度统计','活跃度',function () {
    //             return '<b>开始时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x-86400000)+'<br>'+
    //                 '<b>结束时间：</b>'+ Highcharts.dateFormat('%Y-%b-%e %H:%M:%S',this.x)+'<br>'+
    //                 '<b>平均活跃:</b>' + this.points[0].y+"<br>"+
    //                 '<b>最高活跃:</b>' + this.points[1].y+"<br>";
    //         },se)
    //     });
    // }
</script>