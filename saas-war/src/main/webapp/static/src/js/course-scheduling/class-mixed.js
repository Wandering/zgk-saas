/**
 * 全局常量
 * @wiki: http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44458344
 * @type {{tnId: *, taskId: *, grade: GLOBAL_CONSTANT.grade}}
 * @by:pdeng
 */
$(document).ready(function () {
    var GLOBAL_CONSTANT = {
        tnId: Common.cookie.getCookie('tnId'),
        taskId: Common.cookie.getCookie('taskId'),
        grade: function () {
            var gradeId = Common.cookie.getCookie('gradeName');
            switch (gradeId) {
                case '高一年级':
                    gradeId = '1';
                    break;
                case '高二年级':
                    gradeId = '2';
                    break;
                case '高三年级':
                    gradeId = '2';
                    break;
            }
            return gradeId;
        },
    }

    /**
     * 排课任务 -> 排课规则设置 ->合班
     * @type {{init: MixedClass.init, fetchAndRenderSubject: MixedClass.fetchAndRenderSubject, fetchBelongClass: MixedClass.fetchBelongClass, addEvent: MixedClass.addEvent, submitMixedClass: MixedClass.submitMixedClass, fetchResult: MixedClass.fetchResult, renderResult: MixedClass.renderResult, delMixedClass: MixedClass.delMixedClass}}
     */
    var MixedClass = {
        init: function () {
            this.fetchAndRenderSubject();
            this.fetchBelongClass(MixedClass.subjectData[0].courseName, MixedClass.subjectData[0].courseId);
            this.fetchResult();
            this.addEvent();
            // var lock = 0;
            // $(document).on('click','input[name="merge-class"]',function () {
            //     // if (!lock) {
            //     //     lock = 1;
            //     //     $(this).attr('isRight', 1);
            //     // } else {
            //     //     lock = 0;
            //     //     $(this).attr('isRight', 0);
            //     // }
            // })
        },
        fetchAndRenderSubject: function () {
            Common.ajaxFun('/scheduleTask/queryCourseInfoByTaskId.do', 'get', {
                'taskId': GLOBAL_CONSTANT.taskId
            }, function (res) {
                if (res.rtnCode == '0000000') {
                    MixedClass.subjectData = res.bizData;
                    var tpl = Handlebars.compile($('#course-select-tpl').html());
                    $('#course-select').html(tpl(res.bizData));
                } else {
                    layer.msg(res.msg);
                }
            }, function () {
            }, 'true');
        },
        fetchBelongClass: function (courseName, courseId) {
            var paramsData = {
                'tnId': GLOBAL_CONSTANT.tnId,
                'taskId': GLOBAL_CONSTANT.taskId,
                'grade': GLOBAL_CONSTANT.grade,
                'courseName': courseName,
                'courseId': courseId,
            }
            Common.ajaxFun('/mergeClassController/getClassDtoByCourse.do', 'GET', paramsData, function (res) {
                if (res.rtnCode == "0000000") {
                    // isMerge 1:已合过，置灰
                    if (res.bizData.classBaseDtoList.length === 0) {
                        $('#choose-class-list').text('暂无匹配班级');
                        return false;
                    }
                    var tpl = Handlebars.compile($('#choose-class-list-tpl').html());
                    $('#choose-class-list').html(tpl(res.bizData.classBaseDtoList));
                }
            });
        },
        addEvent: function () {
            var that = this;
            $('.mixed-class-btn').click(function () {
                that.submitMixedClass();
            });
            $(document).on('change', '#course-select', function () {
                var cName = $('#course-select option:selected').text(),
                    cId = $('#course-select option:selected').val();
                that.fetchBelongClass(cName, cId);
            });
            $(document).on('click', '#mixed-list .del-class', function () {
                var cId = $(this).attr('classIds');
                that.delMixedClass(cId);
            });
        },
        submitMixedClass: function () {
            var that = this, classIds = '', $target = $('input[name="merge-class"]');
            $target.each(function (i, v) {
                if ($target.eq(i).attr('isRight') == 1) {
                    console.info($target.eq(i).val())
                }
            })
            var checkArr = [],disCheckArr = [];
            $('input[name="merge-class"]:checked').each(function(){
                    checkArr.push($(this).val());
            });
            $('input[name="merge-class"]:disabled').each(function(){
                    disCheckArr.push($(this).val());
            });
            var removeByValue =function(arr, val) {
                for(var i=0; i<arr.length; i++) {
                    if(arr[i] == val) {
                        arr.splice(i, 1);
                        break;
                    }
                }
            }
            $.each(disCheckArr,function(i,v){
                removeByValue(checkArr, v);
            })
            if (checkArr.length < 2) {
                layer.msg('请至少选择两个班')
                return false;
            }
            $.each(checkArr,function(i,v){
                classIds += v+',';
            })
            Common.ajaxFun('/mergeClassController/addMergeInfo.do', 'get', {
                'tnId': GLOBAL_CONSTANT.tnId,
                'taskId': GLOBAL_CONSTANT.taskId,
                'courseId': $('#course-select').val(),
                'classIds': classIds.substr(0, classIds.length - 1),//班级Id，多个班级有英文逗号分隔
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    that.fetchResult();
                    var cName = $('#course-select option:selected').text(),
                        cId = $('#course-select option:selected').val();
                    that.fetchBelongClass(cName, cId);
                    layer.msg('合班成功')
                } else {
                    layer.msg(res.massge);
                }
            });
        },
        fetchResult: function () {
            var that = this;
            Common.ajaxFun('/mergeClassController/getMergeInfo.do', 'get', {
                'tnId': GLOBAL_CONSTANT.tnId,
                'taskId': GLOBAL_CONSTANT.taskId,
                'grade': GLOBAL_CONSTANT.grade,
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    that.renderResult(res.bizData)
                } else {
                    layer.msg(res.msg);
                }
            });
        },
        renderResult: function (dataJson) {
            var tpl = Handlebars.compile($('#mixed-list-tpl').html());
            if (dataJson.mergeClassInfoList.length == 0) {
                $('.mixed-class-tips').addClass('dh');
                return false;
            }
            $('#mixed-list').html(tpl(dataJson.mergeClassInfoList));
            $('.mixed-class-tips').removeClass('dh');
        },
        delMixedClass: function (cId) {
            var that = this;
            Common.ajaxFun('/mergeClassController/deleteMergeInfo.do', 'get', {
                'id': cId
            }, function (res) {
                if (res.rtnCode === '0000000') {
                    layer.msg('删除成功');
                    that.fetchResult();
                    var cName = $('#course-select option:selected').text(),
                        cId = $('#course-select option:selected').val();
                    that.fetchBelongClass(cName, cId);
                } else {
                    layer.msg(res.msg);
                }
            });
        },
    }
    MixedClass.init();
});