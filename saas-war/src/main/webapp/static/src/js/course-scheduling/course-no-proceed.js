/**
 * Created by machengcheng on 16/12/6.
 */
var taskId = Common.cookie.getCookie('taskId');
function CourseNoProceed () {
    this.params = '';
}
CourseNoProceed.prototype = {
    constructor: CourseNoProceed,
    init: function () {
        this.getCourseNoProceed();
        this.getLinkList();
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
        Common.ajaxFun('/disSelectRule/getLinkList/' + taskId, 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length != 0) {
                    $('#link-subjects').show();
                    var linkListHtml = [];
                    $.each(data, function (i, k) {
                        linkListHtml.push('<tr>');
                        linkListHtml.push('<td>' + k.courseName + '</td>');
                        linkListHtml.push('<td>' + k.hour + '</td>');
                        linkListHtml.push('<td>' + k.linkCount + '</td>');
                        linkListHtml.push('<td>' + k.singleCount + '</td>');
                        linkListHtml.push('</tr>');
                    });
                    $('#link-list-data').html(linkListHtml.join(''));
                }
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    },
    //更新不连堂信息
    updateLinkList: function () {
        Common.ajaxFun('/disSelectRule/addOrUpdateGapRule/' + taskId, 'POST', {
            '1-2节': '0',
            '2-3节': '1'
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;

            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, false);
    }
};

$(function () {

    var courseNoProceed = new CourseNoProceed();

    $(document).on('click', '#btn-save-link', function () {
        var params = '';
        var nodeCount = $('.course-no-proceed-list li').length;
        $('.course-no-proceed-list li').each(function (i) {
            var tempChk = $(this).find('input[type="checkbox"]');
            if (i != (nodeCount - 1)) {
                if (tempChk.prop('checked')) {
                    params += tempChk.next().text() + ', ' + 1 + ','
                } else {
                    params += tempChk.next().text() + ', ' + 0 + ','
                }
            } else {
                if (tempChk.prop('checked')) {
                    params += tempChk.next().text() + ', ' + 1
                } else {
                    params += tempChk.next().text() + ', ' + 0
                }
            }
        });
        console.info(courseNoProceed.params);
        //courseNoProceed.updateLinkList();
    });

});