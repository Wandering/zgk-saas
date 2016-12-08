/**
 * Created by machengcheng on 16/12/6.
 */
var taskId = Common.cookie.getCookie('taskId');

function ArrangeCourse () {

}
ArrangeCourse.prototype = {
    constructor: ArrangeCourse,
    init: function () {

    },
    //获取不排课信息
    getNoArrangeCourseInfo: function () {
        Common.ajaxFun('/disSelectRule/getRule/' + taskId + '/' + type + '/' + id + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    }
};

$(function () {

    $(document).on('change', '#no-course-time', function () {
        var itemVal = $(this).val().trim();
        switch(itemVal) {
            case 'class':
                $('#class-no-array').show();
                $('#teacher-no-array').hide();
                $('#course-no-array').hide();
                break;
            case 'teacher':
                $('#class-no-array').hide();
                $('#teacher-no-array').show();
                $('#course-no-array').hide();
                break;
            case 'course':
                $('#class-no-array').hide();
                $('#teacher-no-array').hide();
                $('#course-no-array').show();
                break;
            default:
                break;
        }
    });

    $('.class-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.class-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $('.teacher-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.teacher-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $('.course-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.course-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $(document).on('click', '.no-assign-table td', function () {
        var curText = $(this).text().trim();
        var rowIndex = $(this).index();
        var columnIndex = $(this).parent().index();
        alert('rowIndex: ' + rowIndex + ', columnIndex: ' + rowIndex);
        if (curText == '') {
            $(this).text('不排课');
        } else {
            $(this).text('');
        }
    });

});