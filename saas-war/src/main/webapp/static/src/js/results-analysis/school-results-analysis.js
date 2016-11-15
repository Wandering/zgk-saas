var tnId = Common.cookie.getCookie('tnId');

function SchoolResultsAnalysis() {
    this.init();
}

SchoolResultsAnalysis.prototype = {
    constructor: SchoolResultsAnalysis,
    init: function () {
        this.selGrade();
    },
    selGrade: function () {
        var that = this;
        // 选择高一
        $('input[name="results-radio"]').change(function () {
            var radioV = $('input[name="results-radio"]:checked').val();
            that.selSortOnline(radioV);
            that.coreStudent(radioV);
        });
        $('input[name="results-radio"]:first').trigger('click');


        // 默认高一年级

        // 上线排序


    },
    undergraduateLine: function () {
        Common.ajaxFun('/scoreAnalyse/getEnrollingNumberInfo', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('.batchOne').text(res.bizData.batchOne);
                $('.batchTwo').text(res.bizData.batchTwo);
                $('.batchThr').text(res.bizData.batchThr);
                $('.batchOneRadio').val(res.bizData.batchOne);
                $('.batchTwoRadio').val(res.bizData.batchTwo);
                $('.batchThrRadio').val(res.bizData.batchThr);
                $('.batchAllRadio').val(parseInt(res.bizData.batchOne) + parseInt(res.bizData.batchTwo) + parseInt(res.bizData.batchThr));
            }
        }, function (res) {
            alert("出错了");
        });
    },
    selSortOnline: function (grade) {
        var that = this;
        $('input[name="sort-radio"]').change(function () {
            var sortV = $('input[name="sort-radio"]:checked').val();
            that.overLineNumber(grade, sortV);
        });
    },
    overLineNumber: function (grade, orderBy) {
        Common.ajaxFun('/scoreAnalyse/getOverLineNumberDetail', 'GET', {
            'tnId': tnId,
            'grade': grade,
            'orderBy': orderBy
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            alert("出错了");
        });
    },
    coreStudent: function (grade) {
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
        Common.ajaxFun('/scoreAnalyse/getMostAttentionNumber', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#progress-template").html());
                $('#progress-tbody').html(myTemplate(res));
            }
        }, function (res) {
            alert("出错了");
        });
    }

};

var SchoolResultsAnalysisIns = new SchoolResultsAnalysis();
SchoolResultsAnalysisIns.undergraduateLine();


$(function () {


});



