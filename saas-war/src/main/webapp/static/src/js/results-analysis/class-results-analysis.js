var tnId = Common.cookie.getCookie('tnId');
function ClassResultsAnalysis() {
    this.init();
}
ClassResultsAnalysis.prototype = {
    constructor: ClassResultsAnalysis,
    init: function () {
        var that = this;
        that.defaultGrade();
    },
    // 默认年级赋值
    defaultGrade:function(){
        var that = this;
        that.getGrade();
        that.selGrade();
    },
    // 获取年级
    getGrade: function () {
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#grade-template").html());
                $('#grade-body').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 选择年级
    selGrade: function () {
        var that = this;
        // 选择高一
        $('input[name="results-radio"]').change(function () {
            $(this).attr('checked','checked');
            var radioV = $(this).val();
            that.getClassesNameByGrade(radioV);
            if (radioV.indexOf('高三') >= 0 || radioV.indexOf('高3') >= 0) {

            } else {

            }
            that.chartTab();
        });
        $('.grade-item:first').change();
    },
    // 选择班级
    selClass: function (grade) {
        var that = this;
        // 选择高一
        $('#select').on('change',function () {
            var selV =  $(this).find("option:selected").val();
            $('.sel-class-txt').text(selV);
            that.getAvgScoresForClass(grade,selV);
        });
        $('#select').find('option:eq(0)').attr('selected','selected');
        $('.sel-class-txt').text($('#select').find('option:eq(0)').val());
        that.getAvgScoresForClass(grade,$('#select').find('option:eq(0)').val());
    },
    // 通过年级获取班级
    getClassesNameByGrade:function(grade){
        var that = this;
        $('.title-2 .class-sel').html('');
        Common.ajaxFun('/scoreAnalyse/getClassesNameByGrade', 'GET', {
            'tnId':tnId,
            'grade':grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.length==0){
                    layer.msg('您还没有设置班级');
                }else{
                    var classOption=[];
                    $.each(res.bizData,function(i,v){
                        classOption.push('<option class="opt-item" value="'+ v +'">'+ v +'</option>');
                    });
                    $('.title-2 .class-sel').append(classOption);
                    that.selClass(grade);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // chartTab
    chartTab:function(){
        var that = this;
        $('.chart-tab button').on('click',function(){
            var that = $(this);
            var index = that.index();
            that.addClass('cur').siblings().removeClass('cur');
            $('.chart-box').hide().eq(index).show();
        });
        $('.chart-tab button:first').click();
        that.totalScoreChart();
    },
    // 拉取班级趋势数据
    getAvgScoresForClass:function(grade,className){
        Common.ajaxFun('/scoreAnalyse/getAvgScoresForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.length==0){
                    layer.msg('您还没有设置班级');
                }else{
                    var classOption=[];
                    $.each(res.bizData,function(i,v){
                        classOption.push('<option class="opt-item" value="'+ v +'">'+ v +'</option>');
                    });
                    $('.title-2 .class-sel').append(classOption);
                    that.selClass();
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查看总分趋势
    totalScoreChart:function(){
        var totalScoreChart = echarts.init(document.getElementById('totalScoreChart-chart'));
        var totalScoreChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:'12'
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                left: 'center',
                data: ['2的指数', '3的指数']
            },
            xAxis: {
                type: 'category',
                name: 'x',
                splitLine: {show: false},
                data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'log',
                name: 'y'
            },
            series: [
                {
                    name: '3的指数',
                    type: 'line',
                    data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
                },
                {
                    name: '2的指数',
                    type: 'line',
                    data: [1, 2, 4, 8, 16, 32, 64, 128, 256]
                },
                {
                    name: '1/2的指数',
                    type: 'line',
                    data: [1/2, 1/4, 1/8, 1/16, 1/32, 1/64, 1/128, 1/256, 1/512]
                }
            ]
        };
        totalScoreChart.setOption(totalScoreChartOption);
    },
    subjectsChart:function(){

    }
    // 查看各科情况
    // 人数分布变化
    // 变化人数弹层
    // 个人成绩变化趋势
    // 设置关注位次线
    // 班级前多少名人数统计
    // 重点关注学生
    // 进步较大学生
    // 选择进步名次
    // 选择年级排名

};

var ClassResultsAnalysisIns = new ClassResultsAnalysis();

$(function () {



});



