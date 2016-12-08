/**
 * Created by machengcheng on 16/12/6.
 */
var taskId = Common.cookie.getCookie('taskId');

function CourseNoProceed () {

}
CourseNoProceed.prototype = {
    constructor: CourseNoProceed,
    init: function () {

    },
    //获取不连堂节次
    getCourseNoProceed: function () {
        Common.ajaxFun('/disSelectRule/getGapRule/' + taskId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                $.each(data, function (i, k) {
                    var tempChk = '';
                    var tempId = i.substr(0, 1);
                    if (parseInt(k) == 1) {
                        tempChk = '<li>' +
                            '<input type="checkbox" checked="checked" id="proceed' + tempId + '" />' +
                            '<label for="proceed' + tempId + '">' + i + '</label>' +
                            '</li>';
                    } else {
                        tempChk = '<li>' +
                            '<input type="checkbox" id="proceed' + tempId + '" />' +
                            '<label for="proceed' + tempId + '">' + i + '</label>' +
                            '</li>';
                    }
                    $('.course-no-proceed-list').append(tempChk);
                });
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    },
    //获取已设连堂科目
    getLinkList: function () {
        Common.ajaxFun('/disSelectRule/getLinkList/' + taskId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    }
};

$(function () {

    var courseNoProceed = new CourseNoProceed();
    courseNoProceed.getCourseNoProceed();
    courseNoProceed.getLinkList();

});