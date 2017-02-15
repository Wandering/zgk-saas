var tnId = Common.cookie.getCookie('tnId');


function CourseManagement() {
    this.init();
}

CourseManagement.prototype = {
    constructor: CourseManagement,
    init: function () {
        this.getDefaultInfo();
        this.getCourse();
        this.addCourse();
        this.updateCourse();
        this.delCourse();
    },
    // 语文、数学、英语、物理、化学、生物、政治、历史、地理  默认课程不可编辑\不可修改\不可删除
    // 通用技术 不可编辑\不可修改\不可删除
    // 查询课程基础信息
    getDefaultInfo:function(){
        var courseArr = ['语文','数学','英语','物理','化学','生物','地理','历史','生物'];
        var courseTemplate = Handlebars.compile($("#course-tbody-template").html());
        Handlebars.registerHelper("addOne", function (index, options) {
            return parseInt(index) + 1;
        });
        $('#course-tbody').html(courseTemplate(courseArr));
    },
    // 获取租户下的课程设置信息
    getCourse:function(){
        Common.ajaxFun('/course/get/manager/'+ tnId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                console.log(res)

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 新增课程管理信息
    addCourse:function(){

    },
    queryCourseList: function () {
        var courseArr = [];
        courseArr.push('<option value="00">请选择课程名称</option>');
        Common.ajaxFun('/course/get/baseInfo.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var resData = res.bizData.courses;
                $.each(resData, function (i, v) {
                    if(i>8){
                        courseArr.push('<option courseV="' + v['id'] + '" statusV="' + v['status'] + '" value="' + v['courseName'] + '">' + v['courseName'] + '</option>');
                    }
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        return courseArr.join('');
    },
    addCourseLayer:function(title,isUpdate){
        var that = this;
        var addCourseContentHtml = [];
        addCourseContentHtml.push('<div class="add-course-box">');
        addCourseContentHtml.push('<div class="course-box">');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>课程名称：</span>');
        addCourseContentHtml.push('<select id="course-name-list">'+ that.queryCourseList() +'</select>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>开课年级：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne" type="checkbox" class="ace" /><span class="lbl">高一年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo"  type="checkbox" class="ace" /><span class="lbl">高二年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree"  type="checkbox" class="ace" /><span class="lbl">高三年级</span></label>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-wen" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-li"  type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<button type="button" class="save-course-btn" id="save-course-btn">保存</button>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['550px', '350px'],
            content: addCourseContentHtml.join(''),
            success: function () {

            }
        });
        if (isUpdate) {

        }
    },
    // 修改课程管理信息
    updateCourse:function(){

    },
    // 删除课程管理信息
    delCourse:function(){

    }
};

var CourseManagementIns = new CourseManagement();

$(function () {

    // 新增
    $('#addCourse-btn').on('click',function(){
        CourseManagementIns.addCourseLayer('添加课程', false);
    });
    // 修改
    $('#updateCourse-btn').on('click',function(){
        CourseManagementIns.addCourseLayer('修改课程', true);
    });

    // 删除
    $('#deleteCourse-btn').on('click', function () {
        var checkboxLen = $('#course-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            var id = $('#course-tbody input:checked').attr('dataid');
            CourseManagementIns.delCourse(id);
        }, function () {
            layer.closeAll();
        });
    });





});



