var tnId = Common.cookie.getCookie('tnId');
function ClassResultsAnalysis() {
    this.init();
    this.deGrade='';
}
ClassResultsAnalysis.prototype = {
    constructor: ClassResultsAnalysis,
    init: function () {
        var that = this;
        that.getGrade();
        that.selGrade();
        that.getExamProperties('grade');
    },
    getExamProperties: function (type) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getExamProperties', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $.each(res.bizData, function (i,v) {
                    if(type=='grade'){
                        if(v.name=='defaultClassGrade'){
                            $('#grade-body input[name="results-radio"][value="'+ v.value +'"]').attr('checked','checked');
                            that.getClassesNameByGrade(v.value);
                        }
                    }else if(type=='class'){
                        if(v.name=='defaultClassGrade'){
                            that.deGrade = v.value;
                        }
                        if(v.name=='defaultClass'){
                            $('#select option[value="'+ v.value +'"]').prop('selected','selected');
                            $('.sel-class-txt').text(v.value);
                            that.getAvgScoresForClass(that.deGrade,v.value);
                            that.getStuNumberScoreChangeForClass(that.deGrade,v.value);
                            that.setLine(that.deGrade,v.value);
                            if (that.deGrade.indexOf('高三') >= 0 || that.deGrade.indexOf('高3') >= 0) {
                                that.getOverLineDetailForClass(that.deGrade,v.value,'batchAll');
                            } else {

                            }
                        }
                    }
                });

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 更新年级后台
    updateExamProperties:function(defaultClass,grade){
        Common.ajaxFun('/scoreAnalyse/updateExamProperties', 'GET', {
            'tnId': tnId,
            'name': defaultClass,
            'value': grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData==true){
                    // 提交成功
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
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
            that.updateExamProperties('defaultClassGrade',radioV);
            if (radioV.indexOf('高三') >= 0 || radioV.indexOf('高3') >= 0) {
                $('.grade-type').text('本科线成绩分析');
            } else {
                $('.grade-type').text('重点线成绩分析');
            }
        });
    },
    // 选择班级
    selClass: function (grade) {
        var that = this;
        // 选择高一
        $('#select').on('change',function () {
            var selV =  $(this).find("option:selected").val();
            $('.sel-class-txt').text(selV);
            that.getAvgScoresForClass(grade,selV);
            that.getStuNumberScoreChangeForClass(grade,selV);
            that.updateExamProperties('defaultClass',selV);
            that.setLine(grade,selV);
            if (grade.indexOf('高三') >= 0 || grade.indexOf('高3') >= 0) {
                that.getOverLineDetailForClass(grade,selV,'batchAll');
            } else {

            }
        });


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
                    $.each(res.bizData,function(i,v){
                        classOption.push('<option class="opt-item" value="'+ v +'">'+ v +'</option>');
                    });
                    $('.title-2 .class-sel').append(classOption);
                    that.selClass(grade);
                    that.getExamProperties('class');
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
            $('.chart-box').addClass('hds').eq(index).removeClass('hds');
        });
        $('.chart-tab button:eq(0)').click();
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
                var dataJson = res.bizData;
                if(dataJson.length>0){
                    var dateData = [];
                    var totalScoreData = [];
                    var subjectData = [];
                    var datas = [];
                    var arrMaxMin = [];
                    for(var i in dataJson) {
                        dateData.push(dataJson[i].examTime);
                        totalScoreData.push(parseInt(dataJson[i]['总分']));
                        delete dataJson[i].examTime;
                        delete dataJson[i].className;
                        delete dataJson[i]['总分'];
                        for(var l in dataJson[i]){
                            arrMaxMin.push(dataJson[i][l]);
                        }
                    }
                    for(var k in dataJson[0]){
                        switch (k){
                            case 'className':
                            case 'examTime':
                            case '总分':
                                break;
                            default:
                                subjectData.push(k);
                                break;
                        }
                    }
                    for(var j in subjectData){
                        var subArr = [];
                        for(var g in dataJson) {
                            delete dataJson[g].examTime;
                            delete dataJson[g].className;
                            delete dataJson[g]['总分'];
                            subArr.push(dataJson[g][subjectData[j]])
                        }
                        var seriesObj = {
                            name:subjectData[j],
                            type : 'line',
                            data:subArr
                        };
                        datas.push(seriesObj);
                    }
                    that.totalScoreChart(dateData,totalScoreData);
                    that.subjectsChart(subjectData,dateData,datas,arrMaxMin);
                }
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
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                left: 'left'
            },
            xAxis: {
                type: 'category',
                name: 'x',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                data: ["2016-02-02", "2016-03-03", "2016-04-07", "2016-05-10", "2016-06-14"]
                //data: dateData
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'value',
                min:Array.min(totalScoreData),
                max:Array.max(totalScoreData)
            },
            series: [
                {
                    name: '3的指数',
                    type: 'line',
                    //data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
                    data: totalScoreData
                }
            ]
        };
        totalScoreChart.setOption(totalScoreChartOption);
    },
    subjectsChart:function(subjectData,dateData,datas,arrMaxMin){
        var subjectsChart = echarts.init(document.getElementById('subjectsChart-chart'));
        var subjectsChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c}'
            },
            legend: {
                left: 'center',
                //data: ['2的指数', '3的指数']
                data: subjectData
            },
            xAxis: {
                type: 'category',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                data: dateData
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                type: 'value',
                min:Array.min(arrMaxMin),
                max:Array.max(arrMaxMin)
            },
            series:datas
        };
        subjectsChart.setOption(subjectsChartOption);
    },
    // 查看各科情况
    // 人数分布变化
    getStuNumberScoreChangeForClass:function(grade,className){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getStuNumberScoreChangeForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#student-change-template").html());
                Handlebars.registerHelper('agree_button', function() {
                    return new Handlebars.SafeString(
                       '<tr><td>' + this.学生姓名 + '</td><td>' + this.变化趋势 + '</td></tr>'
                    );
                });
                $('#student-change-tbody').html(myTemplate(res));
                that.changeStudent();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    changeStudent:function(){
        $('.change-student-btn').on('click',function(){
            var data = $(this).attr('data');
            var studentChangeTable = '<table class="student-change-table table table-hover"><thead><tr><th class="center">学生姓名</th><th class="center">变化趋势</th></tr></thead><tbody>'+ data +'</tbody></table>'
            layer.open({
                type: 1,
                title: '变化人数详情',
                offset: 'auto',
                area: ['362px', '350px'],
                content: studentChangeTable
            });
        });
    },
    // 变化人数弹层

    // 个人成绩变化趋势
    getAvgScoresForClassStudent:function(){
        Common.ajaxFun('/scoreAnalyse/getStuNumberScoreChangeForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'batch':batch
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#online-template").html());
                $('#online-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 设置关注位次线
    setLine:function(grade,className){
        var that = this;
        $('#set-line-btn').on('click',function(){
            var setLine = $.trim($('#set-line').val());
            if(setLine==''){
                layer.msg('请输入位次线!');
                return false;
            }
            that.getOverLineDetailForClassTwo(grade,className,setLine)
        });
    },
    // 班级前多少名人数统计  高三
    getOverLineDetailForClass:function(grade,className,batch){
        Common.ajaxFun('/scoreAnalyse/getOverLineDetailForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'batch':batch
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#online-template").html());
                $('#online-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 班级上线人数统计（高一，高二）
    getOverLineDetailForClassTwo:function(grade,className,line){
        Common.ajaxFun('/scoreAnalyse/getOverLineDetailForClassTwo', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'line':line
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#overLineDetail-template").html());
                $('#overLineDetail-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }

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

