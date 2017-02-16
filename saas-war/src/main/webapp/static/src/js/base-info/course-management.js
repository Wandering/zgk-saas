var tnId = Common.cookie.getCookie('tnId');


function CourseManagement() {
    this.init();
}

CourseManagement.prototype = {
    constructor: CourseManagement,
    init: function () {
        this.getCourse();
        this.updateCourse();
        this.getGrade();
    },
    // 语文、数学、英语、物理、化学、生物、政治、历史、地理  默认课程不可编辑\不可修改\不可删除
    // 通用技术 不可编辑\不可修改\不可删除
    // 获取租户下的课程设置信息
    getCourse: function () {
        Common.ajaxFun('/course/get/manager/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                $('#course-tbody').html('');
                var dataObj = res.bizData.courses;
                var courseHtml = '';
                for (var i = 0; i < dataObj.length; i++) {
                    courseHtml += '<tr>'
                        + '<th class="center">'
                        + '<label>'
                        + "<input type='checkbox' courseMapList='"+ JSON.stringify(dataObj[i].courseMapList) +"' courseBaseId = '"+ dataObj[i].courseBaseId +"' courseName='"+ dataObj[i].courseName +"' class='ace' />"
                        + '<span class="lbl"></span>'
                        + '</label>'
                        + '</th>'
                        + '<td class="center">' + (i + 1) + '</td>'
                    courseHtml+= '<td class="center">' + dataObj[i].courseName + '</td>';
                    var gradeName = [];
                    var grade1CourseTypeName = '-';
                    var grade2CourseTypeName = '-';
                    var grade3CourseTypeName = '-';
                    for(var k=0;k<dataObj[i].courseMapList.length;k++){
                        var gradeId = dataObj[i].courseMapList[k].gradeId;
                        var courseType = dataObj[i].courseMapList[k].courseType;
                        switch (gradeId){
                            case 1:
                                gradeName.push('高一年级');
                                switch (courseType){
                                    case "0":
                                        grade1CourseTypeName = '-';
                                        break;
                                    case "1":
                                        grade1CourseTypeName = '文科班开设课程';
                                        break;
                                    case "2":
                                        grade1CourseTypeName = '理科班开设课程';
                                        break;
                                    case "3":
                                        grade1CourseTypeName = '文科班开设课程,理科班开设课程';
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 2:
                                gradeName.push('高二年级');
                                switch (courseType){
                                    case "0":
                                        grade2CourseTypeName = '-';
                                        break;
                                    case "1":
                                        grade2CourseTypeName = '文科班开设课程';
                                        break;
                                    case "2":
                                        grade2CourseTypeName = '理科班开设课程';
                                        break;
                                    case "3":
                                        grade2CourseTypeName = '文科班开设课程,理科班开设课程';
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 3:
                                gradeName.push('高三年级');
                                switch (courseType){
                                    case "0":
                                        grade3CourseTypeName = '-';
                                        break;
                                    case "1":
                                        grade3CourseTypeName = '文科班开设课程';
                                        break;
                                    case "2":
                                        grade3CourseTypeName = '理科班开设课程';
                                        break;
                                    case "3":
                                        grade3CourseTypeName = '文科班开设课程,理科班开设课程';
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    courseHtml+= '<td class="center grades-td">'+ gradeName.join(',') +'</td>'
                        + '<td class="center grade1Type">'+ grade1CourseTypeName +'</td>'
                        + '<td class="center grade2Type">'+ grade2CourseTypeName +'</td>'
                        + '<td class="center grade3Type">'+ grade3CourseTypeName +'</td>'
                        + '</tr>';
                }
                $('#course-tbody').append(courseHtml);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    // 新增课程管理信息
    addCourse: function (ids,courseBaseId,updateStatus) {
        var that = this;
        console.log(courseBaseId)
        var datas = {
            "clientInfo": {},
            "style": "",
            "data": {
                "courseManage": {
                    "tnId": tnId,
                    "courseBaseId": courseBaseId,
                    "gradeId": "",
                    "courseType": "",
                    "createTime": ""
                }
            }
        };
        console.log(JSON.stringify(datas))
        var url = '';
        var msgTxt = '';
        if(updateStatus=='true'){
            url = '/course/upd/manager.do?ids='+ids;
            msgTxt = '课程修改成功';
        }else{
            url = '/course/add/manager.do?ids='+ids;
            msgTxt = '课程添加成功';
        }
        Common.ajaxFun(url, 'POST', JSON.stringify(datas), function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.result==true){
                    layer.msg(msgTxt);
                    layer.closeAll();
                    that.getCourse();
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, null, true);





    },
    queryCourseList: function () {
        var that = this;
        var courseArr = [];
        courseArr.push('<option value="00">请选择课程名称</option>');
        Common.ajaxFun('/course/get/baseInfo.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var resData = res.bizData.courses;
                $.each(resData, function (i, v) {
                    if (i > 7) {
                        courseArr.push('<option courseV="' + v['id'] + '" statusV="' + v['status'] + '" value="' + v['id'] + '">' + v['courseName'] + '</option>');
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
    addCourseLayer: function (title, id, courseName,courseMapList) {
        var that = this;
        var addCourseContentHtml = [];
        addCourseContentHtml.push('<div class="add-course-box">');
        addCourseContentHtml.push('<div class="course-box">');
        addCourseContentHtml.push('<div class="box-row">');
        addCourseContentHtml.push('<span><i>*</i>课程名称：</span>');
        if (courseName) {
            addCourseContentHtml.push('<select id="course-name-list" disabled="disabled"><option value="">' + courseName + '</option></select>');
        } else {
            addCourseContentHtml.push('<select id="course-name-list">' + that.queryCourseList() + '</select>');
        }
        addCourseContentHtml.push('</div>');
        addCourseContentHtml.push('<div class="box-row box-start-course">');
        addCourseContentHtml.push('<span><i>*</i>开课年级：</span>');

        var grade1Sel = '';
        var grade2Sel = '';
        var grade3Sel = '';
        var grade1CourseType = '';
        var grade2CourseType = '';
        var grade3CourseType = '';
        if(courseMapList){
            var courseMapListObj = JSON.parse(courseMapList)
            for(var k=0;k<courseMapListObj.length;k++){
                switch (courseMapListObj[k].gradeId){
                    case 1:
                        grade1Sel = "高一年级";
                        grade1CourseType = courseMapListObj[k].courseType;
                        break;
                    case 2:
                        grade2Sel = "高二年级";
                        grade2CourseType = courseMapListObj[k].courseType;
                        break;
                    case 3:
                        grade3Sel = "高三年级";
                        grade3CourseType = courseMapListObj[k].courseType;
                        break;
                    default:
                        break;
                }
            }
        }


        if(grade1Sel != ""){
            addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked" id="gradeOne" data-id="1"  type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高一年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        }else{
            addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne" data-id="1"  type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高一年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        }
        if(grade2Sel != ""){
            addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked" id="gradeTwo" data-id="2"  type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高二年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        }else{
            addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo" data-id="2"  type="checkbox" class="ace form-input-checkbox" /><span class="lbl">高二年级</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
        }
        if(grade3Sel != ""){
            addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked" id="gradeThree" data-id="3"   type="checkbox" class="ace form-input-checkbox" /><span class="lbl grade-sel-box">高三年级</span></label>');
        }else{
            addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree" data-id="3"   type="checkbox" class="ace form-input-checkbox" /><span class="lbl grade-sel-box">高三年级</span></label>');
        }

        addCourseContentHtml.push('</div>');



        switch (grade1CourseType){
            case "0":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-1 hides" grade="高一年级">');
                addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "1":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-1" grade="高一年级">');
                addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen" checked="checked" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "2":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-1" grade="高一年级">');
                addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen"  dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li" checked="checked"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "3":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-1" grade="高一年级">');
                addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen" checked="checked"   dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li" checked="checked"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            default:
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-1 hides" grade="高一年级">');
                addCourseContentHtml.push('<span><i>*</i>高一年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeOne-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
        }


        switch (grade2CourseType){
            case "0":
                addCourseContentHtml.push('<div class="box-row box-row-course-type  box-row-2 hides" grade="高二年级">');
                addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "1":
                addCourseContentHtml.push('<div class="box-row box-row-course-type  box-row-2" grade="高二年级">');
                addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked" id="gradeTwo-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "2":
                addCourseContentHtml.push('<div class="box-row box-row-course-type  box-row-2" grade="高二年级">');
                addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox"  checked="checked" id="gradeTwo-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "3":
                addCourseContentHtml.push('<div class="box-row box-row-course-type  box-row-2" grade="高二年级">');
                addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox"  checked="checked" id="gradeTwo-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox"  checked="checked" id="gradeTwo-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            default:
                addCourseContentHtml.push('<div class="box-row box-row-course-type  box-row-2 hides" grade="高二年级">');
                addCourseContentHtml.push('<span><i>*</i>高二年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeTwo-li"  dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
        }


        switch (grade3CourseType){
            case "0":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-3 hides" grade="高三年级">');
                addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-li" dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "1":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-3" grade="高三年级">');
                addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked" id="gradeThree-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-li" dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "2":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-3" grade="高三年级">');
                addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked"  id="gradeThree-li" dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            case "3":
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-3" grade="高三年级">');
                addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked"  id="gradeThree-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" checked="checked"  id="gradeThree-li" dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
            default:
                addCourseContentHtml.push('<div class="box-row box-row-course-type box-row-3 hides" grade="高三年级">');
                addCourseContentHtml.push('<span><i>*</i>高三年级课程类型：</span>');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-wen" dataId = "1" type="checkbox" class="ace" /><span class="lbl">文科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('<label><input name="form-field-checkbox" id="gradeThree-li" dataId = "2" type="checkbox" class="ace" /><span class="lbl">理科班开设课程</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                addCourseContentHtml.push('</div>');
                break;
        }


        addCourseContentHtml.push('<div class="box-row">');
        if(id){
            addCourseContentHtml.push('<button type="button" class="save-course-btn" courseId="'+ id +'" updateStatus="true" id="save-course-btn">保存</button>');
        }else{
            addCourseContentHtml.push('<button type="button" class="save-course-btn" id="save-course-btn">保存</button>');
        }

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
    updateCourse: function () {

    },
    // 删除课程管理信息
    delCourse: function (courseId) {
        var that = this;
        Common.ajaxFun('/course/del/manager/'+ tnId +'/'+courseId, 'POST', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                layer.msg('删除成功');
                layer.closeAll();
                that.getCourse();
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    //查询年级信息
    getGrade: function () {
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
    isSubjectType: function (data) {
        var that = this;
        var dataObj = data.bizData;
        // 选择开课年级
        $('body').on('click', '.form-input-checkbox', function () {
            var id = $(this).attr('data-id');
            for (var l = 0; l < dataObj.length; l++) {
                if (dataObj[l].gradeCode == id) {
                    var classType = dataObj[l].classType;
                    $('.form-input-checkbox[type="checkbox"]').each(function (i, v) {
                        var _this = $(this);
                        if (_this.is(':checked')) {
                            _this.prop('checked', true);
                            if (classType == 3) {
                                $('.box-row-' + classType).removeClass('hides');
                            }
                        } else {
                            _this.prop('checked', false);
                            if (classType == 3) {
                                $('.box-row-' + classType).addClass('hides').find('input[type="checkbox"]').prop('checked', false);
                            }
                        }
                    });
                }
            }
        });
    }
};

var CourseManagementIns = new CourseManagement();

$(function () {

    // 新增
    $('#addCourse-btn').on('click', function () {
        CourseManagementIns.addCourseLayer('添加课程');
    });
    // 修改
    $('#updateCourse-btn').on('click', function () {
        var id = $('#course-tbody input:checked').attr('coursebaseid'),
            courseName = $('#course-tbody input:checked').attr('courseName'),
            checkboxLen = $('#course-tbody input:checked').length,
            courseMapList = $('#course-tbody input:checked').attr('courseMapList');
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        console.log(id)
        CourseManagementIns.addCourseLayer('修改课程',id, courseName ,courseMapList);
    });


    // 删除
    $('#deleteCourse-btn').on('click', function () {
        var courseBaseId = $('#course-tbody input:checked').attr('courseBaseId');
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


        if (
            courseName == '语文' ||
            courseName == '数学' ||
            courseName == '英语' ||
            courseName == '物理' ||
            courseName == '化学' ||
            courseName == '生物' ||
            courseName == '地理' ||
            courseName == '历史' ||
            courseName == '生物' ||
            courseName == '通用技术'
        ) {
            layer.tips('不可删除' + courseName + '课程', $('#course-tbody input:checked'));
            return false;
        }


        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            CourseManagementIns.delCourse(courseBaseId);
        }, function () {
            layer.closeAll();
        });
    });

    // 保存添加
    $('body').on('click', '#save-course-btn', function () {
        var updateStatus = $(this).attr('updateStatus');
        var courseNameVal = $('#course-name-list').val();
        var courseNameText = $('#course-name-list').children('option:selected').text();
        if(courseNameVal=='00'){
            layer.tips('请选择课程', $('#course-name-list'));
            return false;
        }

        if(updateStatus!="true"){
            for(var i=0;i<$('#course-tbody input[type="checkbox"]').length;i++){
                var coursename = $('#course-tbody input[type="checkbox"]').eq(i).attr('coursename');
                if(coursename == courseNameText){
                    layer.tips('不能重复添加同一门课程', $('#course-name-list'));
                    return false;
                }
            }
        }

        if($('.form-input-checkbox[type="checkbox"]:checked').length==0){
            layer.tips('请选择开课年级', $('.grade-sel-box'));
            return false;
        }
        var courseId = $(this).attr('courseid');
        var datasArr = [];

        for(var i=0;i<$('.form-input-checkbox[type="checkbox"]').length;i++){
            var _this = $('.form-input-checkbox[type="checkbox"]').eq(i);
            var gradeId = _this.attr('data-id');

            if (_this.is(':checked')) {
                _this.prop('checked', true);
                if ($('.box-row-' + gradeId).is(":visible")) {
                    var gradeSubType = '';
                    var checkedLen = $('.box-row-' + gradeId).find('input[type="checkbox"]:checked').length;
                    switch (checkedLen) {
                        case 0:
                            layer.tips('请选择高' + gradeId + '年级课程类型', $('.box-row-' + gradeId));
                            return false;
                        case 2:
                            gradeSubType = 3;
                            break;
                        default:
                            gradeSubType = $('.box-row-' + gradeId).find('input[type="checkbox"]:checked').attr('dataid');
                            break;
                    }

                    datasArr.push('-' + gradeId + ':' + gradeSubType);
                } else {
                    datasArr.push('-' + gradeId + ':' + 0);
                }
            } else {
                _this.prop('checked', false);
            }
        }
        datasArr = datasArr.join('');
        datasArr = datasArr.substring(1, datasArr.length);
        console.log(datasArr);
        CourseManagementIns.addCourse(datasArr,courseId,updateStatus);
    });

    //
    $('body').on('change', '#course-name-list', function () {
        var selVal = $(this).children('option:selected').val(),
            selText = $(this).children('option:selected').text();
        $('#save-course-btn').attr({
            'courseId': selVal,
            'courseName': selText
        });
    });




});



