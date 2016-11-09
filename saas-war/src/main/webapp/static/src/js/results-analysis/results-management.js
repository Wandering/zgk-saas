var tnId = Common.cookie.getCookie('tnId');

function ResultsManagementFun() {
    this.init();
}

ResultsManagementFun.prototype = {
    constructor: ResultsManagementFun,
    init: function () {
    },
    getResultsList: function (grade) {
        Common.ajaxFun('/scoreAnalyse/listExam.do', 'GET', {
            'grade' : grade
        }, function (res) {
            console.log(res)
            //var myTemplate = Handlebars.compile($("#role-template").html());
            //Handlebars.registerHelper('FormatTime', function (num) {
            //    return Common.getFormatTime(num);
            //});
            //$('#role-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    }
};

var ResultsManagementIns = new ResultsManagementFun();

// 选择年级
$('input[name="results-radio"]').change(function(){
    var radioV = $('input[name="results-radio"]:checked').val();
    ResultsManagementIns.getResultsList(radioV);
});