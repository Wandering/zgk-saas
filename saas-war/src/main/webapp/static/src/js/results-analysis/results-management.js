var tnId = Common.cookie.getCookie('tnId');

function ResultsManagementFun() {
    this.init();
}

ResultsManagementFun.prototype = {
    constructor: ResultsManagementFun,
    init: function () {
    },
    getResultsList: function (grade) {
        Common.ajaxFun('/scoreAnalyse/listExam', 'GET', {
            'grade': grade
        }, function (res) {
            console.log(res)
            var myTemplate = Handlebars.compile($("#results-template").html());
            Handlebars.registerHelper('excel', function (url) {
                return Common.getPageName(url);
            });
            $('#results-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    },
    uploadResults: function () {
        layer.open({
            type: 1,
            title: '上传成绩',
            offset: 'auto',
            area: ['362px', '420px'],
            content: $('#uploadLayer').html()
        });
    }
};

var ResultsManagementIns = new ResultsManagementFun();

$(function () {
    // 选择年级
    $('input[name="results-radio"]').change(function () {
        var radioV = $('input[name="results-radio"]:checked').val();
        ResultsManagementIns.getResultsList(radioV);
    });

    // 默认高一年级
    $('input[name="results-radio"][value="1"]').attr('checked', true);
    ResultsManagementIns.getResultsList(1);

    //上传成绩
    $('body').on('click', '#uploadResultsBtn', function () {
        ResultsManagementIns.uploadResults();
        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    })


});



