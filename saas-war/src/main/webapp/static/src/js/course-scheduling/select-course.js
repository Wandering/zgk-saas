var tnId = Common.cookie.getCookie('tnId');
function SelectCourse() {
    this.init();
    this.settingState='';
}

SelectCourse.prototype = {
    constructor: SelectCourse,
    init: function () {
        this.querySelectCourseTask();
    },
    // 任务列表
    querySelectCourseTask: function () {
        var that = this;
        Common.ajaxFun('/saas/selectCourse/getSelectCourseTasks.do', 'GET', {
            "tnId": tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('#select-course-list').html('');
                var myTemplate = Handlebars.compile($("#task-template").html());
                Handlebars.registerHelper("addOne", function (index, options) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper('FormatTime', function (num) {
                    return Common.getFormatTime(num);
                });
                Handlebars.registerHelper('reStatus', function (v) {

                    //console.log(v)
                    // 0：选课未设置 1：选课未开始 2：学生选课中 3：选课结果待使用 4：选课结果已使用"
                    var result = '';
                    switch (v) {
                        case 0: // 选课未设置
                            result = '<a href="javascript:;" class="look-course">选课未设置</a>';
                            break;
                        case 1: // 选课未开始
                            result = '<a href="javascript:;" class="look-course">选课未开始</a>';
                            break;
                        case 2: // 学生选课中
                            result = '<a href="javascript:;" class="look-course">学生选课中</a>';
                            that.settingState = 2;
                            break;
                        case 3: // 选课结果待使用
                            result = '<a href="javascript:;" class="look-course">选课结果待使用</a>';
                            break;
                        case 4: // 选课结果已使用
                            result = '<a href="javascript:;" class="look-course">选课结果已使用</a>';
                            break;
                        default:
                            break;
                    }

                    return result;
                });
                $('#select-course-list').html(myTemplate(res));

                $('.look-course').on('click', function () {
                    var id = $(this).parent().attr('dataid');
                    var gradeName = $(this).parent().attr('gradeName');
                    Common.cookie.setCookie('gradeName', gradeName);
                    Common.cookie.setCookie('taskId', id);
                    window.location.href='/select-course-settings';
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 查询年级信息
    queryGradeInfo: function () {
        var grade = [];
        grade.push('<option value="00">请选择年级</option>');
        Common.ajaxFun('/scheduleTask/queryGradeInfo.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var resData = res.bizData;
                $.each(resData, function (i, v) {
                    grade.push('<option gradeV="' + v['value'] + '" value="' + v['key'] + '">' + v['value'] + '</option>');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        return grade.join('');
    },
    // 弹层
    addOrUpdateSelectCourse: function (title, isUpdate) {
        var that = this;
        var addSelectCourseContentHtml = [];
        addSelectCourseContentHtml.push('<div class="add-select-course-box">');
        addSelectCourseContentHtml.push('<div class="select-course-box">');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>名称：</span><input id="task-name" placeholder="最多20个字" type="text" />');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>年级：</span><select id="grade-list">' + that.queryGradeInfo() + '</select>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课开始时间：</span><input  class="date-picker" id="LAY_demorange_s" type="text" placeholder="设置选课开始时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<div class="box-row">');
        addSelectCourseContentHtml.push('<span class="labels"><i>*</i>设置选课结束时间：</span><input class="date-picker" id="LAY_demorange_e" type="text"  placeholder="设置选课结束时间"/>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('<br><div class="box-row">');
        addSelectCourseContentHtml.push('<button type="button" id="save-select-course-btn">保存</button>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('</div>');
        addSelectCourseContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['500px', '350px'],
            content: addSelectCourseContentHtml.join(''),
            success: function () {
                that.getYears();
            }
        });
        layui.use('laydate', function(){
            var laydate = layui.laydate;

            var start = {
                min: laydate.now()
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    end.min = datas; //开始日选好后，重置结束日的最小日期
                    end.start = datas //将结束日的初始值设定为开始日
                },
                elem: this,
                istime: true,
                format: 'YYYY-MM-DD hh:mm:ss'
            };

            var end = {
                min: laydate.now()
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    start.max = datas; //结束日选好后，重置开始日的最大日期
                },
                elem: this,
                istime: true,
                format: 'YYYY-MM-DD hh:mm:ss'
            };

            document.getElementById('LAY_demorange_s').onclick = function(){
                start.elem = this;
                laydate(start);
            }
            document.getElementById('LAY_demorange_e').onclick = function(){
                end.elem = this
                laydate(end);
            }

        });
    },
    // 学期年份  当前年往前推5年
    getYears: function () {
        var years = [];
        var date = new Date();
        var year = date.getFullYear();
        for (var i = (year - 2); i < (year + 1); i++) {
            years.push('<option value="' + i + '">' + i + '</option>');
        }
        $('#term-year').append(years.join(''));
    },
    // 保存 更新
    addSelectCourseTask: function (id, selectCourseName, grade, startTime, endTime) {
        var that = this;
        var datas = {
            "data": {
                "tnId": tnId,
                "name": selectCourseName,
                "grade": grade,
                "startTime": startTime,
                "endTime": endTime
            }
        };
        if (id) {
            datas.data.id = id;
            Common.ajaxFun('/saas/selectCourse/updateSelectCourseTask.do', 'POST', JSON.stringify(datas), function (res) {
                if (res.rtnCode == "0000000") {
                    layer.closeAll();
                    layer.msg("修改成功");
                    that.querySelectCourseTask();
                } else {
                    layer.msg(res.msg);
                }
            }, function (res) {
                layer.msg(res.msg);
            },null,true);
        } else {
            console.log(JSON.stringify(datas))
            Common.ajaxFun('/saas/selectCourse/addSelectCourseTask.do', 'POST', JSON.stringify(datas), function (res) {
                if (res.rtnCode == "0000000") {
                    layer.closeAll();
                    layer.msg("保存成功");
                    that.querySelectCourseTask();
                } else {
                    layer.msg(res.msg);
                }
            }, function (res) {
                layer.msg(res.msg);
            },null,true);
        }
    },
    // 删除任务
    deleteSelectCourseTask: function (id) {
        var that = this;
        console.log(id);
        var datas = {
            "data":{
                "ids":id
            }
        };
        Common.ajaxFun('/saas/selectCourse/deleteSelectCourseTask.do', 'POST',JSON.stringify(datas), function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                that.querySelectCourseTask();
                $('#checkAll').attr('checked',false);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        },null,true);
    }


};

var SelectCourseIns = new SelectCourse();

$(function () {

    // 新建选课任务
    $('#addTask-btn').on('click', function () {
        SelectCourseIns.addOrUpdateSelectCourse('新建选课任务');
    });

    // 新建任务保存
    $('body').on('click', '#save-select-course-btn', function () {
        var id = $(this).attr('dataid');
        var taskName = $.trim($('#task-name').val());
        var gradeV = $('#grade-list').val();
        var startDate = $.trim($('#LAY_demorange_s').val());
        var endDate = $.trim($('#LAY_demorange_e').val());
        if (taskName == '') {
            layer.tips('请输入任务名称!', '#task-name');
            return false;
        }
        if (taskName.length > 20) {
            layer.tips('任务名称不能超过20个字!', '#task-name');
            return false;
        }
        if (gradeV == "00") {
            layer.tips('请选择年级!', '#grade-list');
            return false;
        }

        if (startDate == "") {
            layer.tips('请设置选课开始时间!', '#LAY_demorange_s');
            return false;
        }
        if (endDate == "") {
            layer.tips('请设置选课结束时间!', '#LAY_demorange_e');
            return false;
        }
        SelectCourseIns.addSelectCourseTask(id, taskName, gradeV, startDate, endDate);
    });

    // 修改选课任务
    $('#updateTask-btn').on('click', function () {
        var checkboxLen = $('#select-course-list input:checked').length;
        var selectCourseV = $('#select-course-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        var id = selectCourseV.attr('dataid');
        var schedulename = selectCourseV.attr('selectcoursename');
        var gradename = selectCourseV.attr('gradename');
        var starttime = selectCourseV.attr('starttime');
        var endtime = selectCourseV.attr('endtime');
        var termname = selectCourseV.attr('termname');
        var state = selectCourseV.attr('state');
        $('#save-schedule-btn').attr('dataId', id);
        SelectCourseIns.addOrUpdateSelectCourse('更新选课任务');
        $('#task-name').val(schedulename);
        $('#grade-list').attr('disabled','disabled').children('option[value="' + gradename + '"]').attr('selected', 'selected');
        $('#LAY_demorange_s').val(starttime);
        $('#LAY_demorange_e').val(endtime);
        if(state =='2'){
            $('#task-name').attr('disabled','disabled');
            $('#LAY_demorange_s').attr('disabled','disabled');
        }else{
            $('#task-name,#LAY_demorange_s').removeAttr('disabled');
        }

        $('#save-select-course-btn').attr('dataId', id);

    });
    // 删除选课任务
    $('#deleteTask-btn').on('click', function () {
        var checkboxLen = $('#select-course-list input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            var deleteIdArr = [];
            $('#select-course-list input:checked').each(function(){
                var id = $(this).attr('dataid');
                deleteIdArr.push(id);
            });
            SelectCourseIns.deleteSelectCourseTask(deleteIdArr.join(','));
        }, function () {
            layer.closeAll();
        });
    });


});