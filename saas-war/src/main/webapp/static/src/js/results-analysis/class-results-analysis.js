var tnId = Common.cookie.getCookie('tnId');
function ClassResultsAnalysis() {
    this.init();
    this.count = '';
    this.grade = '';
    this.className = '';
    this.bacthLine = '';
    this.stepStart = '';
    this.stepEnd = '';
    this.rankStepStart = '';
    this.rankStepEnd = '';
    this.batchV = '';
    this.progressStart = '10';
    this.progressLength = '20';
    this.gradeRankStart = '1';
    this.gradeRankLength = '99';
}
ClassResultsAnalysis.prototype = {
    constructor: ClassResultsAnalysis,
    init: function () {
        // 获取年级
        this.getGrade();
        // 班级情况统计
        this.undergraduateLine();
    },
    // 获取年级
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#grade-template").html());
                $('#grade-body').html(myTemplate(res));
                // 默认拉取上次选中年级和上次选中班级
                that.getExamProperties(); // 同步获取
                that.getClassesNameByGrade(that.grade);
                $('input[name="results-radio"][value="'+ that.grade +'"]').attr('checked','checked');
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    getExamProperties: function () {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getExamProperties', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $.each(res.bizData, function (i,v) {
                    switch (v.name){
                        case 'defaultClassGrade':
                            that.grade = v.value;
                            break;
                        case 'defaultClass':
                            that.className = v.value;
                            break;
                        case 'line':
                            that.bacthLine = v.value;
                            break;
                        default:
                            break;
                    }
                });
                $('#set-line').val(that.bacthLine);
                that.grade3ShowView(that.grade,that.className,that.bacthLine);
                that.getStuNumberScoreChangeForClass(that.grade,that.className);
                // 进步较大学生下拉
                that.getMostAdvancedDetailAdvancedStepList(that.grade,that.className,that.progressStart,that.progressLength);
                // 年级排名下拉列表
                that.getMostAdvancedDetailGradeRankStepList(that.grade,that.className,that.gradeRankStart,that.gradeRankLength);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 更新年级后台
    updateExamProperties:function(name,value){
        Common.ajaxFun('/scoreAnalyse/updateExamProperties', 'GET', {
            'tnId': tnId,
            'name': name,
            'value': value
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
    grade3ShowView:function(gradeV,className,line){
        var that = this;
        if (gradeV.indexOf('高三') >= 0 || gradeV.indexOf('高3') >= 0) {
            $('.grade-type').text('本科线成绩分析');
            $('.grade1-2-line,.grade-student-num').hide();
            $('.batch-info,.grade3-main,.grade3-student-batch,#batch-sel').show();
            that.getOverLineNumberByDate(gradeV,className);
            that.selSortOnline(gradeV,className);
        } else {
            $('.grade-type').text('重点线成绩分析');
            $('.grade1-2-line,.grade-student-num').show();
            $('.batch-info,.grade3-main,.grade3-student-batch,#batch-sel').hide();
            that.getOverLineDetailForClassTwo(gradeV,className,line);
            that.getMostAttendDetailForClassTwo(gradeV,className,line);
        }
    },
    // 根据年级获取班级
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
                    $('.title-2 .class-sel').append(classOption).find('option[value="'+ that.className +'"]').attr('selected','selected');
                    // 默认拉取班级排名趋势
                    that.getAvgScoresForClass(that.grade,that.className);
                    that.chartTab();
                    //// 人数分布变化
                    //that.getStuNumberScoreChangeForClass(that.grade,that.className);
                    $('.sel-class-txt,.class-name').text(that.className);
                    // 进步较大学生列表默认拉取
                    that.getMostAdvancedDetailForClass(that.grade,that.className,ClassAnalysisIns.stepStart,ClassAnalysisIns.stepEnd,ClassAnalysisIns.rankStepStart,ClassAnalysisIns.rankStepEnd);
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
    },
    undergraduateLine: function () {  // 各班情况统计
        Common.ajaxFun('/scoreAnalyse/getEnrollingNumberInfo', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('.batchOne').text(res.bizData.batchOne);
                $('.batchTwo').text(res.bizData.batchTwo);
                $('.batchThr').text(res.bizData.batchThr);
            } else {
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 拉取趋势数据
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
                    $('.chart-main1').show();
                    $('.class-rank-tips-txt').hide().text('班级趋势暂无数据');
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
                }else{
                    $('.chart-main1').hide();
                    $('.class-rank-tips-txt').show().text('班级趋势暂无数据');
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            //layer.msg(res.msg);
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
                formatter: '{b} <br/>{a} : {c}'
            },
            legend: {
                left: 'left'
            },
            xAxis: {
                type: 'category',
                name: '考试时间',
                position:'bottom',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                //data: ["2016-02-02", "2016-03-03", "2016-04-07", "2016-05-10", "2016-06-14"]
                data: dateData
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                scale:false,
                type: 'value',
                // min:Array.min(totalScoreData),
                // max:Array.max(totalScoreData),
                // minInterval:1
            },
            series: [
                {
                    name: '班级平均分排名',
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
            //tooltip: {
            //    trigger: 'item',
            //    formatter: '{a} <br/>{b} : {c}'
            //},
            tooltip: {
                trigger: 'axis'
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
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            yAxis: {
                scale:false,
                type: 'value',
                min:Array.min(arrMaxMin),
                max:Array.max(arrMaxMin)
            },
            series:datas
        };
        subjectsChart.setOption(subjectsChartOption);
    },
    // 高三浙江批次线图表
    getOverLineNumberByDate: function (grade,className) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getOverLineNumberByDate', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'lineScore': '439', //浙江理科二本
            'className': className
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var dateData = [];
                var popData = [];
                $.each(res.bizData, function (i, v) {
                    dateData.push(v.examTime);
                    popData.push(v.total);
                });
                that.lineNumberByDateChart(dateData, popData);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    lineNumberByDateChart: function (dateData, popData) {
        var lineNumberByDateChart = echarts.init(document.getElementById('lineNumberByDate-chart'));
        var lineNumberByDateOption = {
            title: {
                text: '16年浙江本科批次线:439分'
            },
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '10%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    name: '考试时间',
                    boundaryGap: false,
                    //data : ['周一','周二','周三','周四','周五','周六','周日']
                    data: dateData
                }
            ],
            yAxis: [
                {
                    name: '上线人数',

                    scale:false,
                    type: 'value',
                    // min:Array.min(totalScoreData),
                    // max:Array.max(totalScoreData),
                    // minInterval:1
                }
            ],
            series: [
                {
                    name: '上线人数',
                    type: 'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    //data:[120, 132, 101, 134, 90, 230, 210]
                    data: popData
                }
            ]
        };
        lineNumberByDateChart.setOption(lineNumberByDateOption);
    },
    // 人数分布变化
    getStuNumberScoreChangeForClass:function(grade,className){
        $('#student-change-tbody').html('');
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
            } else {
                layer.msg('暂无数据');
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
                // area: ['362px', '350px'],
                area: ['362px', 'auto'],
                content: studentChangeTable
            });
        });
    },
    // 班级上线人数统计（高一，高二）
    getOverLineDetailForClassTwo:function(grade,className,line){
        $('#overLineDetail-tbody').html('');
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getOverLineDetailForClassTwo', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'line':line
        }, function (res) {
            if (res.rtnCode == "0000000") {
                // 提交位次线记录
                //that.updateExamProperties('line',ClassAnalysisIns.bacthLine);
                that.saveExamLineProperties(line);
                $('.student-num').text(line);
                var myTemplate = Handlebars.compile($("#overLineDetail-template").html());
                $('#overLineDetail-tbody').html(myTemplate(res));
                that.student12Layer(grade,className);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    student12Layer:function(grade,className){
        var that = this;
        $('body').on('click','.student-btn',function(){
            var studentName = $(this).text();
            var studentChart = ''
                +'<div class="chart-main2">'
                +'<div class="chart-tab student-chart-tab">'
                +'<button class="btn btn-return cur">查看总分趋势</button>'
                +'<button class="btn btn-return">查看各科情况</button>'
                +'</div>'
                +'<div class="student-chart-box">'
                +'<div id="studentTotalScore-chart" style="width: 100%;height: 400px;"></div>'
                +'</div>'
                +'<div class="student-chart-box hds">'
                +'<div id="studentSubjects-chart" style="width: 100%;height: 400px;"></div>'
                +'</div>'
                +'</div>';
            layer.open({
                type: 1,
                title: '个人成绩变化趋势',
                offset: 'auto',
                area: ['800px','auto'],
                // area: ['800px', '400'],
                content: studentChart,
                success:function(){
                    that.studentChartTab();
                    that.getAvgScoresForClassStudent(grade,className,studentName);
                }
            });
        });
    },
    // 班级前多少名人数统计  高三
    getOverLineDetailForClass:function(grade,className,batch){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getOverLineDetailForClass', 'GET', {
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'batch':batch
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#overLineDetail-template").html());
                $('#overLineDetail-tbody').html(myTemplate(res));
                that.student12Layer();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    //
    // 个人成绩变化趋势
    getAvgScoresForClassStudent:function(grade,className,studentName){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getAvgScoresForClassStudent', 'GET', { //个人成绩变化趋势
            'tnId':tnId,
            'grade':grade,
            'className':className,
            'studentName':studentName
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
                        delete dataJson[i].studentName;
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
                            delete dataJson[g].studentName;
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
                    that.studentTotalScoreChart(dateData,totalScoreData);
                    that.studentSubjectsChart(subjectData,dateData,datas,arrMaxMin);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // chartTab
    studentChartTab:function(){
        var that = this;
        $('.student-chart-tab button').on('click',function(){
            var index = $(this).index();
            $(this).addClass('cur').siblings().removeClass('cur');
            $('.student-chart-box').addClass('hds').eq(index).removeClass('hds');
        });
        $('.student-chart-tab button:eq(0)').click();
    },
    // 查看总分趋势
    studentTotalScoreChart:function(dateData,totalScoreData){
        var studentTotalScoreChart = echarts.init(document.getElementById('studentTotalScore-chart'));
        var studentTtotalScoreChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b} <br/>{a} : {c}'
            },
            legend: {
                left: 'left'
            },
            xAxis: {
                type: 'category',
                name: '考试时间',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                // data: ["2016-02-02", "2016-03-03", "2016-04-07", "2016-05-10", "2016-06-14"]
                data: dateData
            },
            grid: {
                left: '3%',
                right: '10%',
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
                    name: '班级平均分排名',
                    type: 'line',
                    //data: [1, 3, 9, 27, 81, 247, 741, 2223, 6669]
                    data: totalScoreData
                }
            ]
        };
        studentTotalScoreChart.setOption(studentTtotalScoreChartOption);
    },
    studentSubjectsChart:function(subjectData,dateData,datas,arrMaxMin){
        var studentSubjectsChart = echarts.init(document.getElementById('studentSubjects-chart'));
        var studentSubjectsChartOption = {
            title: {
                text: '班级平均分排名',
                left: 'left',
                textStyle:{
                    fontSize:12
                }
            },
            tooltip: {
                trigger: 'item',
                formatter: '{b} <br/>{a} : {c}'
            },
            legend: {
                left: 'center',
                //data: ['2的指数', '3的指数']
                data: subjectData
            },
            xAxis: {
                type: 'category',
                name: '考试时间',
                splitLine: {show: false},
                //data: ['一', '二', '三', '四', '五', '六', '七', '八', '九']
                data: dateData
            },
            grid: {
                left: '3%',
                right: '10%',
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
        studentSubjectsChart.setOption(studentSubjectsChartOption);
    },
    selSortOnline: function (grade,className) {
        var that = this;
        $('input[name="sort-radio"]').change(function () {
            var orderBy = $(this).val();
            that.getOverLineDetailForClass(grade,className,orderBy);
        });
        $('input[name="sort-radio"]:first').attr('checked','checked');
        var orderByFirst = $('input[name="sort-radio"]:first').val();
        that.getOverLineDetailForClass(grade,className,orderByFirst);
    },
    // 重点关注学生
    getMostAttentionPage: function (grade, batchName, className, courseName, offset, rows) {
        $('body #details-main-tbody').html('');
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAttentionPage', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'batchName': batchName,
            'className': className,
            'courseName': courseName,
            'offset': offset,
            'rows': rows
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.count = parseInt(Math.ceil(res.bizData.total / rows));
                var myTemplate = Handlebars.compile($("body #details-main-template").html());
                $('body #details-main-tbody').html(myTemplate(res));
            } else {
                layer.msg(res.msg);
            }
        }, function (res){
            layer.msg(res.msg);
        }, true);
    },
    // 班级进步较大学生进步名次下拉列表
    getMostAdvancedDetailAdvancedStepList: function (grade,className,stepStart, stepLength) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAdvancedDetailAdvancedStepList', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'className':className,
            'stepStart': stepStart,
            'stepLength': stepLength
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('#ranking-sel').show().html('');
                var rankingSel = [];
                rankingSel.push('<option stepStart="" stepEnd="">选择进步名次</option>');
                $.each(res.bizData,function(i,v){
                    rankingSel.push('<option stepStart="'+ v.stepStart +'" stepEnd="'+ v.stepEnd +'" value="">'+ v.stepStart + "-" + v.stepEnd +'</option>');
                });
                $('#ranking-sel').append(rankingSel);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 进步较大学生页面列表
    getMostAdvancedDetailForClass: function (grade,className,stepStart, stepEnd,rankStepStart,rankStepEnd) {
        $('#progress-tbody').html('');
        Common.ajaxFun('/scoreAnalyse/getMostAdvancedDetailForClass', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'className': className,
            'stepStart': stepStart,
            'stepEnd': stepEnd,
            'rankStepStart':rankStepStart,
            'rankStepEnd':rankStepEnd
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#progress-template").html());
                $('#progress-tbody').html(myTemplate(res));
                //var sortTheadTemplate = Handlebars.compile($("#progress-thead-template").html());
                //$('#progress-thead').html(sortTheadTemplate(res));
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 年级排名下拉列表
    getMostAdvancedDetailGradeRankStepList: function (grade,className,stepStart, stepLength) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAdvancedDetailGradeRankStepList', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'className': className,
            'stepStart': stepStart,
            'stepLength': stepLength
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('#gradeTop-sel').html('');
                var gradeTopSel = [];
                gradeTopSel.push('<option stepStart="" stepEnd="">选择年级排名</option>');
                $.each(res.bizData,function(i,v){
                    gradeTopSel.push('<option stepStart="'+ v.stepStart +'" stepEnd="'+ v.stepEnd +'" value="">'+ v.stepStart + "-" + v.stepEnd +'</option>');
                });
                $('#gradeTop-sel').append(gradeTopSel);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 保存关注位次线
    saveExamLineProperties:function(value){
        Common.ajaxFun('/scoreAnalyse/saveExamLineProperties', 'GET', {
            'tnId': tnId,
            'value': value
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData=='true'){
                    console.log('保存关注位次线成功!');
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 高一高二重点关注学生
    getMostAttendDetailForClassTwo:function(grade,className,line){
        Common.ajaxFun('/scoreAnalyse/getMostAttendDetailForClassTwo', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'className': className,
            'line': line
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("body #details-main-template").html());
                $('body #details-main-tbody').html(myTemplate(res));
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
};

var ClassAnalysisIns = new ClassResultsAnalysis();

$(function () {

    // 选择年级
    $('body').on('change','input[name="results-radio"]',function () {
        ClassAnalysisIns.grade = $(this).val();
        // 通过年级获取班级
        ClassAnalysisIns.getClassesNameByGrade(ClassAnalysisIns.grade);
        // 提交选中年级记录
        ClassAnalysisIns.updateExamProperties('defaultClassGrade',ClassAnalysisIns.grade);
        // 人数分布变化
        ClassAnalysisIns.getStuNumberScoreChangeForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className);
        // 选择高三年级
        ClassAnalysisIns.grade3ShowView(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.bacthLine);
        // 重点关注学生年级选择
        ClassAnalysisIns.getMostAttentionPage(ClassAnalysisIns.grade,ClassAnalysisIns.batchV,ClassAnalysisIns.className,'',0,3);
        // 进步较大学生下拉
        ClassAnalysisIns.getMostAdvancedDetailAdvancedStepList(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.progressStart,ClassAnalysisIns.progressLength);
        // 年级排名下拉列表
        ClassAnalysisIns.getMostAdvancedDetailGradeRankStepList(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.gradeRankStart,ClassAnalysisIns.gradeRankLength);
        // 进步较大学生列表
        ClassAnalysisIns.getMostAdvancedDetailForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.stepStart,ClassAnalysisIns.stepEnd,ClassAnalysisIns.rankStepStart,ClassAnalysisIns.rankStepEnd);
    });
    // 选择班级
    $('body').on('change','#select-class',function(){
        ClassAnalysisIns.className = $(this).val();
        $('.sel-class-txt,.class-name').text(ClassAnalysisIns.className);
        // 提交选中年级记录
        ClassAnalysisIns.updateExamProperties('defaultClass',ClassAnalysisIns.className);
        // 默认拉取班级排名趋势
        ClassAnalysisIns.getAvgScoresForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className);
        // 人数分布变化
        ClassAnalysisIns.getStuNumberScoreChangeForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className);
        // 选择高三年级
        ClassAnalysisIns.grade3ShowView(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.bacthLine);
        // 重点关注学生班级选择
        ClassAnalysisIns.getMostAttentionPage(ClassAnalysisIns.grade,ClassAnalysisIns.batchV,ClassAnalysisIns.className,'',0,3);
        // 进步较大学生下拉
        ClassAnalysisIns.getMostAdvancedDetailAdvancedStepList(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.progressStart,ClassAnalysisIns.progressLength);
        // 年级排名下拉列表
        ClassAnalysisIns.getMostAdvancedDetailGradeRankStepList(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.gradeRankStart,ClassAnalysisIns.gradeRankLength);
        // 进步较大学生列表
        ClassAnalysisIns.getMostAdvancedDetailForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.stepStart,ClassAnalysisIns.stepEnd,ClassAnalysisIns.rankStepStart,ClassAnalysisIns.rankStepEnd);
    });


    // 设置关注位次线  高一高二
    $('body').on('click','#set-line-btn',function(){
        ClassAnalysisIns.bacthLine = $.trim($('#set-line').val());
        if(ClassAnalysisIns.bacthLine==''){
            layer.msg('请输入位次线!');
            return false;
        }
        ClassAnalysisIns.getOverLineDetailForClassTwo(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.bacthLine);
        ClassAnalysisIns.getMostAttendDetailForClassTwo(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.bacthLine);
    });

    // 重点关注学生批次选择
    $('body').on('change','#batch-sel',function () {
        $(this).children('option[value=""]').remove();
        ClassAnalysisIns.batchV = $(this).children('option:checked').val();
        if(ClassAnalysisIns.batchV!==""){
            ClassAnalysisIns.getMostAttentionPage(ClassAnalysisIns.grade,ClassAnalysisIns.batchV,ClassAnalysisIns.className,'',0,3);
            $(".tcdPageCode").createPage({
                pageCount: ClassAnalysisIns.count,
                current: 1,
                backFn: function (p) {
                    ClassAnalysisIns.getMostAttentionPage(ClassAnalysisIns.grade,ClassAnalysisIns.batchV,ClassAnalysisIns.className,'', (p - 1) * 3, 3);
                }
            });
        }
    });


    // 选择进步较大
    $('#ranking-sel').change(function(){
        ClassAnalysisIns.stepStart = $(this).children('option:checked').attr('stepStart');
        ClassAnalysisIns.stepEnd = $(this).children('option:checked').attr('stepEnd');
        ClassAnalysisIns.getMostAdvancedDetailForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.stepStart,ClassAnalysisIns.stepEnd,ClassAnalysisIns.rankStepStart,ClassAnalysisIns.rankStepEnd);
    });


    // 选择年级排行
    $('#gradeTop-sel').change(function(){
        ClassAnalysisIns.rankStepStart = $(this).children('option:checked').attr('stepStart');
        ClassAnalysisIns.rankStepEnd = $(this).children('option:checked').attr('stepEnd');
        ClassAnalysisIns.getMostAdvancedDetailForClass(ClassAnalysisIns.grade,ClassAnalysisIns.className,ClassAnalysisIns.stepStart,ClassAnalysisIns.stepEnd,ClassAnalysisIns.rankStepStart,ClassAnalysisIns.rankStepEnd);
    });


});


Array.max = function (array) {
    return Math.max.apply(Math, array);
};

Array.min = function (array) {
    return Math.min.apply(Math, array);
};

