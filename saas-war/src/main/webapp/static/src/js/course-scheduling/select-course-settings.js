var tnId = Common.cookie.getCookie('tnId');
var taskId = Common.cookie.getCookie('taskId');
var selCourseLoging = Common.cookie.getCookie('selCourseLoging');
function SelectCourseSettings() {
    this.init();
}

SelectCourseSettings.prototype = {
    constructor: SelectCourseSettings,
    init: function () {
        this.getSelectCourses();
        this.selCourseMax();
        $('.select-course-num').find('option[value="'+ this.selectCount +'"]').attr('selected','selected');

        if(selCourseLoging=='true'){
            $('.fgk-course-list').find(':checkbox').remove();
            $('#btn-save-base').remove();
            $('.selCourseNum').text(this.selectCount);
        }



    },
    // 拉取课程集合  /saas/selectCourse/getSelectCourses.do
    getSelectCourses: function () {
        var that = this;
        Common.ajaxFun('/saas/selectCourse/getSelectCourses.do', 'GET', {
            "taskId": taskId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var datas = res.bizData;
                $.each(datas, function (i, v) {
                    var courses = datas[i].courses;
                    // 高考课程
                    var coursesObj = JSON.parse(courses);
                    if (datas[i].type == '0') {
                        var gkCourse = [];
                        $.each(coursesObj, function (j, k) {
                            gkCourse.push('<span class="item" data-id="'+ coursesObj[j].id +'">' + coursesObj[j].name + '</span>');
                        });
                        $('.gk-course-list').html(gkCourse.join(''));
                        $('.selectCount').text(coursesObj[i].selectCount);
                    } else if (datas[i].type == '1') {  // 非高考
                        var gkCourse = [];
                        $.each(coursesObj, function (j, k) {
                            if(coursesObj[j].isSelect==true){
                                gkCourse.push('<label class="item"> <input type="checkbox" checked="checked" data-id="'+ coursesObj[j].id +'" data-name="'+ coursesObj[j].name +'" />' + coursesObj[j].name + '</label>');
                                that.selectCount = datas[i].selectCount;
                            }else{
                                gkCourse.push('<label class="item"> <input type="checkbox" data-id="'+ coursesObj[j].id +'" data-name="'+ coursesObj[j].name +'" />' + coursesObj[j].name + '</label>');
                            }
                        });
                        $('.fgk-course-list').html(gkCourse.join(''));

                    }
                })
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    // 提交选课信息  /saas/selectCourse/saveSelectCourse.do
    saveSelectCourse: function (datas) {
        Common.ajaxFun('/saas/selectCourse/saveOrUpdateSelectCourse.do', 'POST',JSON.stringify(datas) , function (res) {
            if (res.rtnCode == "0000000") {
                layer.msg('提交成功!');
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        },null,true);
    },
    // 费高考 最大选中科目
    selCourseMax:function(){
        var chk = 0;
        $(".fgk-course-list").find(':checkbox').each(function () {
            if ($(this).prop("checked") == true) {
                chk++;
            }
        });
        if (chk == 0) {
            $('.select-course-max').hide();
        } else {
            $('.select-course-max').show();
            var selectCourseMaxArr = [];
            for (var i = 0; i < chk; i++) {
                selectCourseMaxArr.push('<option value="'+ (i + 1) +'">' + (i + 1) + '</option>');
            }
            $('.select-course-num').append(selectCourseMaxArr.join(''));
        }
    }


};

var SelectCourseSettingsIns = new SelectCourseSettings();

$(function () {
    // 选择非高考课程
    $('.fgk-course-list').on('click', ':checkbox', function () {
        $('.select-course-num').html('');
        SelectCourseSettingsIns.selCourseMax();
        $('.select-course-num').find('option[value="'+ SelectCourseSettingsIns.selectCount +'"]').attr('selected','selected');
    });

    // 设置提交
    $('#btn-save-base').on('click', function () {
        var courses1=[];
        var courses2=[];
        $('.gk-course-list .item').each(function(i,v){
            courses1.push({"id":$(v).attr('data-id'),"name":$(v).text()});
        });
        $('.fgk-course-list :checkbox:checked').each(function(i,v){
            courses2.push({"id":$(v).attr('data-id'),"name":$(v).attr('data-name')});
        });
        var checkedLen = $('.select-course-num').children('option:selected').val();

        if(checkedLen==undefined){
            layer.tips('请设置非高考课程!', '.fgk-course-list');
            return false;
        }

        var datas = {
            "data": {
                "settings": [
                    {
                        "courses": courses1,
                        "selectCount": "3",
                        "type": "0",
                        "taskId": taskId
                    }, {
                        "courses": courses2,
                        "selectCount": checkedLen,
                        "type": "1",
                        "taskId": taskId
                    }
                ]
            }
        };
        console.log(datas)
        SelectCourseSettingsIns.saveSelectCourse(datas);
    });



});