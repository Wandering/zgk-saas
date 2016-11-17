var tnId = Common.cookie.getCookie('tnId');



function SchoolResultsAnalysis() {
    this.init();
    this.classSel='';
    this.subjectSel='';
    this.count='';
}

SchoolResultsAnalysis.prototype = {
    constructor: SchoolResultsAnalysis,
    init: function () {
        this.getGrade();
        this.selGrade();
    },
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
    selGrade: function () {
        var that = this;
        // 选择高一
        $('input[name="results-radio"]').click(function () {
            var radioV = $('input[name="results-radio"]:checked').val();
            that.selSortOnline(radioV);
            that.coreStudent(radioV);
            that.getStepList(radioV, 10, 10);
            that.getMostAdvancedNumbers(radioV, 10, 20);
            if (radioV.indexOf('高三') >= 0 || radioV.indexOf('高3') >= 0) {
                $('.grade3-main').show();
                that.getOverLineNumberByDate(radioV);
            } else {
                $('.grade3-main').hide();
            }
        });


        // 默认高一年级

        // 上线排序


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
    selSortOnline: function (grade) {
        var that = this;
        $('input[name="sort-radio"]').click(function () {
            var orderBy = $('input[name="sort-radio"]:checked').val();
            Common.ajaxFun('/scoreAnalyse/getOverLineNumberDetail', 'GET', {  // 各班上线人数统计
                'tnId': tnId,
                'grade': grade,
                'orderBy': orderBy
            }, function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    var myTemplate = Handlebars.compile($("#sort-template").html());
                    $('#sort-tbody').html(myTemplate(res));
                    var sortTheadTemplate = Handlebars.compile($("#sort-thead-template").html());
                    $('#sort-thead').html(sortTheadTemplate(res));
                } else {
                    layer.msg(res.msg)
                }
            }, function (res) {
                layer.msg(res.msg)
            });
        });
    },
    coreStudent: function (grade) { // 重点关注学生
        $('#core-thead,#core-tbody').html('');
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumber', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var headArr = '<tr>';
                for (var i in data[0]) {
                    switch (i) {
                        case "batchName":
                            headArr += '<th class="center">本科</th>';
                            break;
                        case "batchTotal":
                            headArr += '<th class="center">总人数</th>';
                            break;
                        default:
                            headArr += '<th class="center">' + i + '</th>';
                            break;
                    }
                }
                headArr += '</tr>';
                $('#core-thead').append(headArr);
                var tbodyArr = '';
                $.each(data, function (i, v) {
                    tbodyArr += '<tr>';
                    $.each(v, function (n, k) {
                        switch (k) {
                            case "batchOne":
                                tbodyArr += '<td class="center"><a href="javascript:;" class="batch-btn" data="batchOne">一本</a></td>';
                                break;
                            case "batchTwo":
                                tbodyArr += '<td class="center"><a href="javascript:;" class="batch-btn" data="batchTwo">二本</a></td>';
                                break;
                            case "batchThr":
                                tbodyArr += '<td class="center"><a href="javascript:;" class="batch-btn" data="batchThr">三本</a></td>';
                                break;
                            default:
                                tbodyArr += '<td class="center">' + k + '</td>';
                                break;
                        }
                    });
                    tbodyArr += '</tr>';
                });
                $('#core-tbody').append(tbodyArr);
                // 重点关注学生图表
                $('body a.batch-btn').on('click', function () {
                    var batchData = $(this).attr('data');
                    var batchMainLayer = [];
                    batchMainLayer.push('<div class="row" id="batch-main-layer">');
                    batchMainLayer.push('<div class="col-xs-12">');
                    batchMainLayer.push('<div class="class-chart">');
                    batchMainLayer.push('<div id="class-chart" style="width: 100%;height: 250px;"></div>');
                    batchMainLayer.push('</div>');
                    batchMainLayer.push('<div class="subject-chart">');
                    batchMainLayer.push('<div id="subject-chart" class="" style="width: 100%;height: 250px;"></div>');
                    batchMainLayer.push('</div>');
                    batchMainLayer.push('<div class="sel-main">');
                    batchMainLayer.push('<select class="grade-sel"></select>');
                    batchMainLayer.push('<select class="subject-sel"></select>');
                    batchMainLayer.push('</div>');
                    batchMainLayer.push('<div class="table-details-main">');
                    batchMainLayer.push('<table class="table table-hover"><thead><tr><th class="center">班级</th><th class="center">学生名称</th><th class="center">年级排名</th><th class="center">弱势学科一</th><th class="center">弱势学科二</th></tr></thead><tbody id="details-main-tbody"></tbody></table>');
                    batchMainLayer.push('<div class="tcdPageCode"></div>');
                    batchMainLayer.push('</div>');
                    batchMainLayer.push('</div>');
                    batchMainLayer.push('</div>');
                    layer.open({
                        type: 1,
                        title: '学生明细',
                        offset: 'auto',
                        area: ['900px', '600px'],
                        content: batchMainLayer.join(''),
                        success: function (layero, index) {
                            that.getMostAdvancedNumbersChart(grade,batchData);
                            that.selClassFun(grade,batchData);
                            that.selSubjectFun(grade,batchData);
                            that.getMostAttentionPage(grade,batchData,that.classSel,that.subjectSel,0,3);
                            $(".tcdPageCode").createPage({
                                pageCount: that.count,
                                current: 1,
                                backFn: function (p) {
                                    that.getMostAttentionPage(grade,batchData,that.classSel,that.subjectSel,(p - 1) * 3,3);
                                }
                            });

                        }
                    });
                });
            } else {
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 进步较大学生
    getMostAdvancedNumbersChart: function (grade, batchName) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumberChart', 'GET', {
            'tnId': tnId,
            'batchName': batchName,
            'grade': grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('.grade-sel,.subject-sel').html('');
                // 学生所在班级统计
                var itemData = [];
                var classData = [];
                var gradeSel = [];
                gradeSel.push('<option value="">请选择班级</option>');
                $.each(res.bizData.classChartData, function (i, v) {
                    itemData.push(v.className);
                    var classDataObj = {};
                    classDataObj['value'] = v.total;
                    classDataObj['name'] = v.className;
                    classData.push(classDataObj);
                    gradeSel.push('<option value="' + v.className + '">' + v.className + '</option>');
                });
                $('.grade-sel').append(gradeSel);
                that.classChart(itemData, classData);
                // 弱势学科统计
                var subjectItemData = [];
                var subjectClassData = [];
                var subjectSel = [];
                subjectSel.push('<option value="">请选择科目</option>')
                $.each(res.bizData.courseChartData, function (i, v) {
                    subjectItemData.push(v.courseName);
                    var subjectClassDataObj = {};
                    subjectClassDataObj['value'] = v.total;
                    subjectClassDataObj['name'] = v.courseName;
                    subjectClassData.push(subjectClassDataObj);
                    subjectSel.push('<option value="' + v.courseName + '">' + v.courseName + '</option>')
                });
                $('.subject-sel').append(subjectSel);
                that.subjectChart(subjectItemData, subjectClassData);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    classChart: function (itemData, classData) {
        var classChart = echarts.init(document.getElementById('class-chart'));
        var classOption = {
            title: {
                text: '学生所在班级统计',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: '20',
                top: '20',
                data: itemData
            },
            series: [
                {
                    name: '学生所在班级统计',
                    type: 'pie',
                    radius: '40%',
                    center: ['65%', '60%'],
                    data: classData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        classChart.setOption(classOption);
    },
    subjectChart: function (itemData, classData) {
        var subjectChart = echarts.init(document.getElementById('subject-chart'));
        var subjectOption = {
            title: {
                text: '弱势学科统计',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: '20',
                top: '20',
                data: itemData
            },
            series: [
                {
                    name: '弱势学科统计',
                    type: 'pie',
                    radius: '40%',
                    center: ['65%', '60%'],
                    data: classData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        subjectChart.setOption(subjectOption);
    },
    // 进步较大学生页面列表
    getMostAdvancedNumbers: function (grade, stepStart, stepEnd) {
        Common.ajaxFun('/scoreAnalyse/getMostAdvancedNumbers', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'stepStart': stepStart,
            'stepEnd': stepEnd
        }, function (res) {
            if (res.rtnCode == "0000000") {

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 进步名次下拉列表
    getStepList: function (grade, stepStart, stepLength) {
        Common.ajaxFun('/scoreAnalyse/getStepList', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'stepStart': stepStart,
            'stepLength': stepLength
        }, function (res) {
            if (res.rtnCode == "0000000") {

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 高三浙江批次线图表
    getOverLineNumberByDate: function (grade) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getOverLineNumberByDate', 'GET', {
            'grade': grade,
            'lineScore': '439'  //浙江理科二本
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
                right: '4%',
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
                    type: 'value',
                    name: '上线人数'
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
    getMostAttentionPage:function(grade,batchName,className,courseName,offset,rows){
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
        }, function (res) {
            layer.msg(res.msg);
        },true);
    },
    // 弹层班级选择
    selClassFun:function(grade,batchData){
        var that = this;
        $('.grade-sel').change(function(){
            var selClassV = $(this).children('option:selected').val();
            that.classSel=selClassV;
            that.getMostAttentionPage(grade,batchData,selClassV,that.subjectSel,0,3);
            $(".tcdPageCode").createPage({
                pageCount: that.count,
                current: 1,
                backFn: function (p) {
                    that.getMostAttentionPage(grade,batchData,selClassV,that.subjectSel,(p - 1) * 3,3);
                }
            });
        })
    },
    // 弹层科目选择
    selSubjectFun:function(grade,batchData){
        var that = this;
        $('.subject-sel').change(function(){
            var selSubjectV = $(this).children('option:selected').val();
            that.subjectSel=selSubjectV;
            that.getMostAttentionPage(grade,batchData,that.classSel,selSubjectV,0,3);
            $(".tcdPageCode").createPage({
                pageCount: that.count,
                current: 1,
                backFn: function (p) {
                    that.getMostAttentionPage(grade,batchData,that.classSel,selSubjectV,(p - 1) * 3,3);
                }
            });
        })
    },
    progressStudent: function () {
        //Common.ajaxFun('/scoreAnalyse/getMostAttentionNumber', 'GET', {
        //    'tnId': tnId,
        //    'grade': grade
        //}, function (res) {
        //    console.log(res)
        //    if (res.rtnCode == "0000000") {
        //        var myTemplate = Handlebars.compile($("#progress-template").html());
        //        $('#progress-tbody').html(myTemplate(res));
        //    }
        //}, function (res) {
        //    layer.msg(res.msg);
        //});
    }

};

var SchoolResultsAnalysisIns = new SchoolResultsAnalysis();
SchoolResultsAnalysisIns.undergraduateLine();
//SchoolResultsAnalysisIns.getMostAdvancedNumbers('高一年级1','batchThr');

$(function () {


});



