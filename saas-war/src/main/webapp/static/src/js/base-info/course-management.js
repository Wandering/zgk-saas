var tnId = Common.cookie.getCookie('tnId');


function CourseManagement() {
    this.init();
}

CourseManagement.prototype = {
    constructor: CourseManagement,
    init: function () {
        this.queryCourseList();
        this.getCourse();
        this.addCourse();
        this.updateCourse();
        this.delCourse();
        this.getGrade();
    },
    // 语文、数学、英语、物理、化学、生物、政治、历史、地理  默认课程不可编辑\不可修改\不可删除
    // 通用技术 不可编辑\不可修改\不可删除
    // 查询课程基础信息
    getDefaultInfo:function(data){
        //var courseArr = ['语文','数学','英语','物理','化学','生物','地理','历史','生物'];
        //var courseTemplate = Handlebars.compile($("#course-tbody-template").html());
        //Handlebars.registerHelper("addOne", function (index, options) {
        //    return parseInt(index) + 1;
        //});
        //$('#course-tbody').html(courseTemplate(courseArr));

        console.log(data);

        var dataObj = data.bizData.courses;
        var courseHtml = '';
        for(var i=0;i<dataObj.length;i++){
            if(
                dataObj[i].courseName == '语文' ||
                dataObj[i].courseName == '数学' ||
                dataObj[i].courseName == '英语' ||
                dataObj[i].courseName == '物理' ||
                dataObj[i].courseName == '化学' ||
                dataObj[i].courseName == '生物' ||
                dataObj[i].courseName == '地理' ||
                dataObj[i].courseName == '历史' ||
                dataObj[i].courseName == '生物'

            ){
                courseHtml  += '<tr>'
                    +'<th class="center">'
                    +'<label>'
                    +'<input type="checkbox" dataId="'+ dataObj[i].id +'" courseName="'+ dataObj[i].courseName +'" class="ace" />'
                    +'<span class="lbl"></span>'
                    +'</label>'
                    +'</th>'
                    +'<td class="center">'+ (i+1) +'</td>'
                    +'<td class="center">'+ dataObj[i].courseName +'</td>'
                    +'<td class="center"></td>'
                    +'<td class="center"></td>'
                    +'<td class="center"></td>'
                    +'<td class="center"></td>'
                    +'</tr>';
            }
        }
        $('#course-tbody').append(courseHtml);
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
        var that = this;
        var courseArr = [];
        courseArr.push('<option value="00">请选择课程名称</option>');
        Common.ajaxFun('/course/get/baseInfo.do', 'GET', {}, function (res) {
            that.getDefaultInfo(res);
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
    addCourseLayer:function(title,id,courseName){
        var that = this;
        var addCourseContentHtml = [];
        addCourseContentHtml.push('<div class="add-course-box">');
        addCourseContentHtml.push('<div class="course-box">');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>课程名称：</span>');
        if(courseName){
            addCourseContentHtml.push('<select id="course-name-list" disabled="disabled"><option value="">'+ courseName +'</option></select>');
        }else{
            addCourseContentHtml.push('<select id="course-name-list">'+ that.queryCourseList() +'</select>');
        }
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>开课年级：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne" data-id="1" type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高一年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo" data-id="2"  type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高二年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree" data-id="3"   type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高三年级</span></label>');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row box-row-1 hides">');
        addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li"  type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row box-row-2 hides">');
        addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row box-row-3 hides">');
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
    },
    // 修改课程管理信息
    updateCourse:function(){

    },
    // 删除课程管理信息
    delCourse:function(id){
        var that = this;
        Common.ajaxFun('/course/del/manager/' + id, 'POST', {
            'courseId':id
        }, function (res) {
            console.log()
            if (res.rtnCode == "0000000") {

            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    //查询年级信息
    getGrade:function(){
        var that = this;
        Common.ajaxFun('/grade/getGrade.do', 'GET', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                that.isSubjectType(res);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    isSubjectType:function(data){
        var that = this;
        var dataObj = data.bizData;
        // 选择开课年级
        $('body').on('click','.form-input-checkbox',function(){
            var id = $(this).attr('data-id');
            for(var l=0;l<data.bizData.length;l++){
                if(data.bizData[l].gradeCode==id){
                    var classType = data.bizData[l].classType;
                    if ($(this).is(':checked')) {
                        $(this).prop('checked', true);
                        if(classType==3){
                            $('.box-row-'+classType).removeClass('hides');
                        }
                    } else {
                        $(this).prop('checked', false);
                        if(classType==3){
                            $('.box-row-'+classType).addClass('hides').find('input[type="checkbox"]').prop('checked', false);
                        }
                    }
                }
            }

        });
    }
};

var CourseManagementIns = new CourseManagement();

$(function () {

    // 新增
    $('#addCourse-btn').on('click',function(){
        CourseManagementIns.addCourseLayer('添加课程');
    });
    // 修改
    $('#updateCourse-btn').on('click',function(){
        var id = $('#course-tbody input:checked').attr('dataid'),
            courseName = $('#course-tbody input:checked').attr('courseName'),
            checkboxLen = $('#course-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }

        CourseManagementIns.addCourseLayer('修改课程',id,courseName);
    });




    // 删除
    $('#deleteCourse-btn').on('click', function () {
        var id = $('#course-tbody input:checked').attr('dataid');
        var checkboxLen = $('#course-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }

        var courseName = $('#course-tbody input:checked').attr('courseName');


        if(
            courseName == '语文' ||
            courseName == '数学' ||
            courseName == '英语' ||
            courseName == '物理' ||
            courseName == '化学' ||
            courseName == '生物' ||
            courseName == '地理' ||
            courseName == '历史' ||
            courseName == '生物'
        ){
            layer.tips('不可删除'+ courseName +'课程', $('#course-tbody input:checked'));
            return false;
        }






        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {

            CourseManagementIns.delCourse(id);
        }, function () {
            layer.closeAll();
        });
    });





});



