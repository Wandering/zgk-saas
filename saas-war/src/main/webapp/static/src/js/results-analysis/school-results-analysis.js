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
            alert(radioV)
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
            //that.overLineNumber(grade, orderBy);
            Common.ajaxFun('/scoreAnalyse/getOverLineNumberDetail', 'GET', {
                'tnId': tnId,
                'grade': grade,
                'orderBy': orderBy
            }, function (res) {
                console.log(res)

            }, function (res) {
                alert("出错了");
            });
        });
    },
    coreStudent: function (grade) { // 重点关注学生
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumber', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#core-template").html());
                Handlebars.registerHelper('batch', function (v) {
                    var batchNmae = '';
                    switch (v) {
                        case 'batchOne':
                            batchNmae = '一本';
                            break;
                        case 'batchTwo':
                            batchNmae = '二本';
                            break;
                        case 'batchThr':
                            batchNmae = '三本';
                            break;
                        default:
                            break;
                    }
                    return batchNmae;

                });
                $('#core-tbody').html(myTemplate(res));
            }
        }, function (res) {
            alert("出错了");
        });
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


$(function () {


});



