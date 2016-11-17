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
        that.chartTab();
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
                    classOption.push('<option class="opt-item" value="高一1班">高一1班</option>');
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
            var index = $(this).index();
            $(this).addClass('cur').siblings().removeClass('cur');
            $('.chart-box').hide().eq(index).show();
        });
        $('.chart-tab button:first').click();
        that.totalScoreChart();
    },
    // 拉取班级趋势数据
    getAvgScoresForClass:function(grade,className){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getAvgScoresForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var dateData = [];
                var totalScoreData = [];
                $.each(res.bizData,function(i,v){
                    console.log(v)
                    dateData.push(v.examTime);
                    totalScoreData.push(parseInt(v['总分']));
                });

                that.totalScoreChart(dateData,totalScoreData);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查看总分趋势
    totalScoreChart:function(dateData,totalScoreData){
        var totalScoreChart = echarts.init(document.getElementById('totalScoreChart-chart'));
        var totalScoreChartOption = {
            title: {
                text: '堆叠区域图'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['邮件营销']
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    //data : ['周一','周二','周三','周四','周五','周六','周日']
                    data : dateData
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'邮件营销',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    //data:[120, 132, 101, 134, 90, 230, 210]
                    data:totalScoreData
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


Array.max = function (array) {
    return Math.max.apply(Math, array);
};

Array.min = function (array) {
    return Math.min.apply(Math, array);
};

