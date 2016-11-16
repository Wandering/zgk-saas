var tnId = Common.cookie.getCookie('tnId');

function SchoolResultsAnalysis() {
    this.init();
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
            }
        }, function (res) {
            alert("出错了");
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
                var myTemplate = Handlebars.compile($("#sort-template").html());
                $('#sort-tbody').html(myTemplate(res));
                var sortTheadTemplate = Handlebars.compile($("#sort-thead-template").html());
                $('#sort-thead').html(sortTheadTemplate(res));
            }, function (res) {
                alert("出错了");
            });
        });
    },
    coreStudent: function (grade) { // 重点关注学生
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumber', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var headArr='<tr>';
                for(var i in data[0]){
                    switch (i){
                        case "batchName":
                            headArr+='<th class="center">本科</th>';
                            break;
                        case "batchTotal":
                            headArr+='<th class="center">总人数</th>';
                            break;
                        default:
                            headArr+='<th class="center">'+ i +'</th>';
                            break;
                    }
                }
                headArr+='</tr>';
                $('#core-thead').append(headArr);
                var tbodyArr = '';
                $.each(data,function(i,v){
                    tbodyArr+='<tr>';
                    $.each(v,function(n,k){
                        switch (k){
                            case "batchOne":
                                tbodyArr+='<td class="center"><a href="javascript:;" class="batch-btn" data="batchOne">一本</a></td>';
                                break;
                            case "batchTwo":
                                tbodyArr+='<td class="center"><a href="javascript:;" class="batch-btn" data="batchTwo">二本</a></td>';
                                break;
                            case "batchThr":
                                tbodyArr+='<td class="center"><a href="javascript:;" class="batch-btn" data="batchThr">三本</a></td>';
                                break;
                            default:
                                tbodyArr+='<td class="center">'+ k +'</td>';
                                break;
                        }
                    });
                    tbodyArr+='</tr>';
                });
                $('#core-tbody').append(tbodyArr);
                // 重点关注学生图表
                $('body').on('click','a.batch-btn',function(){
                    var batchData = $(this).attr('data');
                    layer.open({
                        type: 1,
                        title: '学生明细',
                        offset: 'auto',
                        area: ['900px', '450px'],
                        content: $('#batch-main-layer').html(),
                        success: function (layero, index) {
                            $('#batch-main-layer').remove();
                            that.getMostAdvancedNumbers(grade,batchData);
                        }
                    });

                });
            }
        }, function (res) {
            alert("出错了");
        });
    },
    // 进步较大学生
    getMostAdvancedNumbers:function(grade,batchName){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumberChart', 'GET', {
            'batchName': batchName,
            'grade': grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                // 学生所在班级统计
                var itemData = [];
                var classData = [];
                $.each(res.bizData.classChartData,function(i,v){
                    itemData.push(v.className);
                    var classDataObj={};
                    classDataObj['value']= v.total;
                    classDataObj['name']= v.className;
                    classData.push(classDataObj);
                });
                that.classChart(itemData,classData);
                // 弱势学科统计
                var subjectItemData = [];
                var subjectClassData = [];
                $.each(res.bizData.courseChartData,function(i,v){
                    subjectItemData.push(v.courseName);
                    var subjectClassDataObj={};
                    subjectClassDataObj['value']= v.total;
                    subjectClassDataObj['name']= v.courseName;
                    subjectClassData.push(subjectClassDataObj);
                });
                that.subjectChart(subjectItemData,subjectClassData);
            }
        }, function (res) {
            alert("出错了");
        });
    },
    classChart:function(itemData,classData){
        var classChart = echarts.init(document.getElementById('class-chart'));
        var classOption = {
            title : {
                text: '学生所在班级统计',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: '20',
                top: '20',
                data: itemData
            },
            series : [
                {
                    name: '学生所在班级统计',
                    type: 'pie',
                    radius : '40%',
                    center: ['65%', '60%'],
                    data:classData,
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
    subjectChart:function(itemData,classData){
        var subjectChart = echarts.init(document.getElementById('subject-chart'));
        var subjectOption = {
            title : {
                text: '弱势学科统计',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: '20',
                top: '20',
                data: itemData
            },
            series : [
                {
                    name: '弱势学科统计',
                    type: 'pie',
                    radius : '40%',
                    center: ['65%', '60%'],
                    data:classData,
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
    progressStudent:function(){
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
        //    alert("出错了");
        //});
    }

};

var SchoolResultsAnalysisIns = new SchoolResultsAnalysis();
SchoolResultsAnalysisIns.undergraduateLine();
//SchoolResultsAnalysisIns.getMostAdvancedNumbers('高一年级1','batchThr');

$(function () {


});



