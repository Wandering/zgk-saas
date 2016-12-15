var tnId = Common.cookie.getCookie('tnId');

Common.flowSteps();


function SetingProcess2() {
    this.init();
}
SetingProcess2.prototype = {
    constructor: SetingProcess2,
    init: function () {
        this.getGrade();
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    renderList: function (data) {
        console.log(data);
        if (data.rtnCode == "0000000") {
            var gradeArr = [];
            $.each(data.bizData.grades, function (i, v) {
                gradeArr.push('<div class="classroom-item" gradeId="' + v.id + '">');
                gradeArr.push('<div class="form-group">');
                gradeArr.push('<label class="col-sm-4 control-label no-padding-right">' + v.grade + '教室数量 </label>');
                gradeArr.push('<div class="col-sm-6">');
                gradeArr.push('</div>');
                gradeArr.push('</div>');
                gradeArr.push('<div class="form-group">');
                gradeArr.push('<label for="classroom-num" class="col-sm-4 control-label no-padding-right">行政教室数量:</label>');
                gradeArr.push('<div class="col-sm-6">');
                gradeArr.push('<input type="text" class="form-control grade-item classroom-administrative"  placeholder="0--100以内">');
                gradeArr.push('</div>');
                gradeArr.push('</div>');
                gradeArr.push('<div class="form-group">');
                gradeArr.push('<label for="" class="col-sm-4 control-label no-padding-right">走班教室数量:</label>');
                gradeArr.push('<div class="col-sm-6">');
                gradeArr.push('<input type="text" class="form-control grade-item classroom-goclass" id="goclass-grade' + (i + 1) + '"  placeholder="0--100以内">');
                gradeArr.push('</div>');
                gradeArr.push('</div>');
                gradeArr.push('</div>');
            });
            $('#grade-group').append(gradeArr.join(''));
        } else {
            layer.msg(res.msg);
        }
    },
    eventClick: function () {
        var nums = [];
        $('body').find('.classroom-item').each(function (i, v) {
            var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
            var gradeId = $(this).attr('gradeId');
            var classroomAdministrativeNum = $.trim($(this).find('.classroom-administrative').val()) ? $.trim($(this).find('.classroom-administrative').val()) : 0;
            var classroomGoclassNum = $.trim($(this).find('.classroom-goclass').val()) ? $.trim($(this).find('.classroom-goclass').val()) : 0;
            if (classroomAdministrativeNum == '0') {
                layer.tips("教室数量必须填写", $(this).find('.classroom-administrative'));
                return false;
            }
            if (!re.test(classroomAdministrativeNum) || classroomAdministrativeNum > 100) {
                layer.tips('请输入正确的数字!', $(this).find('.classroom-administrative'));
                return false;
            }
            if (classroomGoclassNum == '0') {
                layer.tips("教室数量必须填写", $(this).find('.classroom-goclass'));
                return false;
            }
            if (!re.test(classroomGoclassNum) || classroomGoclassNum > 100) {
                layer.tips('请输入正确的数字!', $(this).find('.classroom-goclass'));
                return false;
            }
            nums.push("-" + gradeId + ":" + classroomAdministrativeNum + "|" + classroomGoclassNum);

        });
        nums = nums.join('');
        nums = nums.substring(1, nums.length);
        if (nums.split('|').length != 4) {
            return false;
        }
        Common.ajaxFun('/config/classRoom/setting/' + tnId + '/' + nums + '.do', 'POST', {
            'tnId': tnId,
            'nums': nums
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == "SUCCESS") {
                    window.location.href = "/seting-process3";
                } else if (res.bizData.result == "FAIL") {
                    console.log("失败");
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
};
var SetingProcess2Obj = new SetingProcess2();
$(function () {
    $('#seting-process2-btn').on('click', function () {
        SetingProcess2Obj.eventClick();
    });
});

