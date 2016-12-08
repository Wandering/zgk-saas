var taskId = Common.cookie.getCookie('taskId');


//教师信息构造函数及其原型
function TeacherInfo() {
    this.init();
    this.teacherArr = [];
}
TeacherInfo.prototype = {
    constructor: TeacherInfo,
    init: function () {
        this.queryTeacherByTaskId(taskId);
    },
    // 根据任务ID获取教师信息
    queryTeacherByTaskId: function (taskId) {
        Common.ajaxFun('/scheduleTask/queryTeacherByTaskId.do', 'GET', {
                'taskId': taskId
            },
            function (res) {
                var res = {
                    "bizData": [
                        {
                            "teacherId": "教师ID",
                            "teacherName": "教师姓名",
                            "courseName": "课程名",
                            "classNum": "最大带班数",
                            "classInfo": [
                                {
                                    "classId": "所带班级ID",
                                    "className": "所带班级名称"
                                }
                            ]
                        },
                        {
                            "teacherId": "教师ID",
                            "teacherName": "教师姓名",
                            "courseName": "课程名",
                            "classNum": "最大带班数",
                            "classInfo": [
                                {
                                    "classId": "所带班级ID",
                                    "className": "所带班级名称"
                                }
                            ]
                        }
                    ],
                    "rtnCode": "0000000",
                    "ts": 1480990693884
                }
                if (res.rtnCode == "0000000") {
                    $('#teacher-list').html('');
                    var myTemplate = Handlebars.compile($("#teacher-template").html());
                    Handlebars.registerHelper("addOne", function (index, options) {
                        return parseInt(index) + 1;
                    });
                    $('#teacher-list').html(myTemplate(res));
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    },
    addOrUpdateTeacher: function (title) {
        var that = this;
        var addTeacherContentHtml = [];
        addTeacherContentHtml.push('<div class="add-teacher-box">');
        addTeacherContentHtml.push('<div class="teacher-box">');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title"><i>*</i>教师姓名：</span><input type="text" id="teacher-keywords" />所授课程：<span class="subject-name"></span>');
        addTeacherContentHtml.push('<ul class="like-teacher-list">');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾静静</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾玲</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾斌</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾宝玉</a></li>');
        addTeacherContentHtml.push('<li><a href="javascript: void(0);">贾珍</a></li>');
        addTeacherContentHtml.push('</ul>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title"><i>*</i>最大带班数：</span>');
        addTeacherContentHtml.push('<select id="max-class-count">');
        addTeacherContentHtml.push('<option value="00">选择最大带班数</option>');
        addTeacherContentHtml.push('<option value="1">1</option>');
        addTeacherContentHtml.push('<option value="2">2</option>');
        addTeacherContentHtml.push('<option value="3">3</option>');
        addTeacherContentHtml.push('<option value="4">4</option>');
        addTeacherContentHtml.push('<option value="5">5</option>');
        addTeacherContentHtml.push('</select>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title">所带班级：</span>');
        addTeacherContentHtml.push('<ul class="teaching-class-list">');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item1" /><label for="item1">通用技术1班</label></li>');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item2" /><label for="item2">通用技术2班</label></li>');
        addTeacherContentHtml.push('<li><input type="checkbox" id="item3" /><label for="item3">通用技术3班</label></li>');
        addTeacherContentHtml.push('</ul>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="tips">若需要设置某老师必须带某班时，则设置“所带班级”项</span>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<button type="button" id="save-course-btn">保存</button>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['471px', '303px'],
            content: addTeacherContentHtml.join(''),
            success:function(){


                console.log(that.teacherArr)


                $( "#teacher-keywords" ).autocomplete({
                    source: that.teacherArr
                });

                //that.queryTeacherByKeyWord(taskId,keyWord);
            }
        });
    },
    // 删除教师配置信息
    deleteTeacher: function (taskId, teacherId) {
        Common.ajaxFun('/scheduleTask/deleteTeacher.do', 'GET', {
                'taskId': taskId,
                'teacherId': teacherId
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    layer.msg('删除成功!');
                    $('input[type="checkbox"][teacherid="' + teacherId + '"]').parents('tr').remove();
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    },
    // 自动补全教师姓名
    queryTeacherByKeyWord: function (taskId, keyWord) {
        var that = this;
        Common.ajaxFun('/scheduleTask/queryTeacherByKeyWord.do', 'GET', {
                'taskId': taskId,
                'keyWord': keyWord
            },
            function (res) {
                var res = {
                    "bizData": [
                        {
                            "teacherId": "教师ID1",
                            "teacherName": "教师姓名1",
                            "courseName": "课程名1",
                            "classInfo": [
                                {
                                    "classId": "班级ID1",
                                    "className": "班级名称1"
                                },
                                {
                                    "classId": "班级ID2",
                                    "className": "班级名称2"
                                },
                                {
                                    "classId": "班级ID3",
                                    "className": "班级名称3"
                                }
                            ]
                        },
                        {
                            "teacherId": "教师ID2",
                            "teacherName": "教师姓名2",
                            "courseName": "课程名2",
                            "classInfo": [
                                {
                                    "classId": "班级ID1",
                                    "className": "班级名称1"
                                },
                                {
                                    "classId": "班级ID2",
                                    "className": "班级名称2"
                                },
                                {
                                    "classId": "班级ID3",
                                    "className": "班级名称3"
                                }
                            ]
                        }
                    ],
                    "rtnCode": "0000000",
                    "ts": 1480990693884
                }
                if (res.rtnCode == "0000000") {


                    //$.each(res.bizData,function(j,k){
                    //    that.teacherArr.push(k.teacherName);
                    //});
                    //
                    //console.log(11)
                    //
                    //
                    //$('.teaching-class-list').html('');
                    //$('.subject-name').text(res.bizData.courseName);
                    //var classItem = [];
                    //$.each(res.bizData.classInfo,function (i,v) {
                    //    classItem.push('<li><input type="checkbox" classId="'+ v.classId +'" /><label for="item1">'+ v.className +'</label></li>');
                    //});
                    //$('.teaching-class-list').append(classItem.join(''));

                }
            }, function (res) {
                layer.msg(res.msg);
            },true);
    },
    // 所授课程
    teachingCourses:function(data){

    },
    // 最大带班数
    maxClassNum:function(data){

    },
    // 所带班级
    classItem:function(data){

    }
};


$(function () {

    var TeacherInfoIns = new TeacherInfo();
    // 添加
    $(document).on('click', '#add-teacher-btn', function () {
        TeacherInfoIns.addOrUpdateTeacher('添加教师');
    });

    // 模糊搜索
    $(document).on('mouseover', '#teacher-keywords', function () {
        $('.like-teacher-list').show();
    });

    $(document).on('click', function (e) {
        $('.like-teacher-list').hide();
        e.stopPropagation();
    });


    // 修改
    $(document).on('click', '#modify-teacher-btn', function () {
        var checkboxLen = $('#teacher-list input:checked').length;
        var teacherV = $('#teacher-list input:checked')
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        TeacherInfoIns.addOrUpdateTeacher('修改教师');
        $('#teacher-keywords').val(teacherV.attr('teachername'));
        $('.subject-name').text(teacherV.attr('coursename'));
    });

    // 删除
    // 删除详情列表
    $('body').on('click', '#delete-teacher-btn', function () {
        var checkboxLen = $('#teacher-list input[type="checkbox"]:checked').length;
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
            var teacherid = $('#teacher-list input[type="checkbox"]:checked').attr('teacherid');
            console.log(teacherid)
            TeacherInfoIns.deleteTeacher(taskId, teacherid);
        }, function () {
        });
    });

});