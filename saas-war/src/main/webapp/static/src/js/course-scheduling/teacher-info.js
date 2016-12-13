var taskId = Common.cookie.getCookie('taskId');


//教师信息构造函数及其原型
function TeacherInfo() {
    this.init();
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
                if (res.rtnCode == "0000000") {
                    $('#teacher-list').html('');
                    var myTemplate = Handlebars.compile($("#teacher-template").html());
                    Handlebars.registerHelper("classInfoData", function (v) {
                        var data = [];
                        $.each(v,function(i,k){
                            console.log(k);
                            data.push(k.classId);
                        });
                        return data;
                    });
                    Handlebars.registerHelper("addOne", function (index, options) {
                        return parseInt(index) + 1;
                    });
                    $('#teacher-list').html(myTemplate(res));
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    },
    addOrUpdateTeacher: function (title,teachername) {
        var that = this;
        var addTeacherContentHtml = [];
        addTeacherContentHtml.push('<div class="add-teacher-box">');
        addTeacherContentHtml.push('<div class="teacher-box">');
        addTeacherContentHtml.push('<div class="box-row">');
        if(teachername){
            addTeacherContentHtml.push('<span class="title"><i>*</i>教师姓名：</span><input type="text" disabled="disabled" id="teacher-keywords" /><span class="teach-subject">所授课程：<span class="subject-name"></span></span>');
        }else{
            addTeacherContentHtml.push('<span class="title"><i>*</i>教师姓名：</span><input type="text" id="teacher-keywords" /><span class="teach-subject">所授课程：<span class="subject-name"></span></span>');
        }
        addTeacherContentHtml.push('<ul class="like-teacher-list" id="search-list">');
        addTeacherContentHtml.push('</ul>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="title"><i>*</i>最大带班数：</span>');
        addTeacherContentHtml.push('<select id="max-class-count">');
        addTeacherContentHtml.push('<option value="00">选择最大带班数</option>');
        addTeacherContentHtml.push('</select>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row class-box">');
        addTeacherContentHtml.push('<span class="title class-title">所带班级：</span>');
        addTeacherContentHtml.push('<ul class="teaching-class-list">');
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
            area: ['471px', '350px'],
            content: addTeacherContentHtml.join(''),
            success: function () {
                if(teachername){
                    that.queryTeacherByKeyWord(taskId,teachername);
                }else{
                    that.queryTeacherByKeyWord(taskId,"");
                    //that.keywordsPropertychange();

                    $('#teacher-keywords').bind('input propertychange', function() {
                        console.log($(this).val().length)
                        if($(this).val().length=='0'){
                            $('.teach-subject,.class-box').hide();
                            $('.subject-name').text('');
                            $('#max-class-count option:gt(0)').remove();
                            $('#teaching-class-list').html('');
                            $('#save-course-btn').removeAttr('teacherid');
                            $('#save-course-btn').removeAttr('coursename');
                            $('#save-course-btn').removeAttr('classinfodata');
                        }
                    });

                }
            }
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
                if (res.rtnCode == "0000000") {
                    if(keyWord==""){
                        that.getWordList(res.bizData);
                    }else{
                        that.setWordList(res.bizData);
                    }
                }
            }, function (res) {
                layer.msg(res.msg);
            }, true);
    },
    getWordList:function(wordData){
        var wordArr = [];
        $.each(wordData,function(i,v){
            var wordsObj = {};
            wordsObj['title']= v['teacherName'];
            wordsObj['teacherId']= v['teacherId'];
            wordsObj['courseName']= v['courseName'];
            wordsObj['classNum']= v['classInfo'].length;
            wordsObj['classInfo']= v['classInfo'];
            wordArr.push(wordsObj);
        });
        $("#teacher-keywords").bigAutocomplete({
            width:159,
            data: wordArr,
            callback:function(data){
                $('.teaching-class-list').html('');
                $('#max-class-count option:gt(0)').remove();
                $('.teach-subject').show().find('.subject-name').text(data.courseName);
                $('.class-box').show();
                var dataClassInfo = data.classInfo;
                var teachingClassList = [];
                var maxClassCount = [];
                for (var i = 0; i < data.classInfo.length; i++) {
                    maxClassCount.push('<option value="' + (i + 1) + '">' + (i + 1) + '</option>');
                    teachingClassList.push('<li><input type="checkbox" classId="' + dataClassInfo[i].classId + '" className="' + dataClassInfo[i].className + '" id="classInfo' + i + '" /><label for="classInfo' + i + '">' + dataClassInfo[i].className + '</label></li>');
                }
                $('.teaching-class-list').append(teachingClassList);
                $('#max-class-count').append(maxClassCount);
                $('#save-course-btn').attr(
                    {
                        'teacherId': data.teacherId,
                        'courseName': data.courseName,
                        'classInfoData': JSON.stringify(dataClassInfo)
                    }
                );


            }
        });
    },
    setWordList:function(result){
        var data = result[0];
        $('#teacher-keywords').val(data.teacherName);
        $('.teach-subject').show().find('.subject-name').text(data.courseName);
        $('.class-box').show();
        var dataClassInfo = data.classInfo;
        var teachingClassList = [];
        var maxClassCount = [];
        for (var i = 0; i < data.classInfo.length; i++) {
            maxClassCount.push('<option value="' + (i + 1) + '">' + (i + 1) + '</option>');
            teachingClassList.push('<li><input type="checkbox" classId="' + dataClassInfo[i].classId + '" className="' + dataClassInfo[i].className + '" id="classInfo' + i + '" /><label for="classInfo' + i + '">' + dataClassInfo[i].className + '</label></li>');
        }
        $('.teaching-class-list').append(teachingClassList);
        $('#max-class-count').append(maxClassCount);
        $('#save-course-btn').attr(
            {
                'teacherId': data.teacherId,
                'courseName': data.courseName,
                'classInfoData': JSON.stringify(dataClassInfo)
            }
        );
    },
    // 模糊匹配
    keywordsPropertychange: function () {
        var that = this;
        $('#teacher-keywords').unbind('input propertychange').bind('input propertychange', function () {
            var keyWord = $.trim($(this).val());
            if (keyWord == "") {
                $('.teach-subject,.class-box').hide();
            }
            that.queryTeacherByKeyWord(taskId, keyWord);
        });
    },
    // 模糊下拉点击
    //listClick: function () {
    //    $(document).on('click', '#search-list li', function () {
    //        $('.teaching-class-list').html('');
    //        $('#max-class-count option:gt(0)').remove();
    //        $('#teacher-keywords').val($(this).text());
    //        $('.teach-subject,.class-box').show();
    //        $('.subject-name').text($(this).attr('courseName'));
    //        $('#search-list').html('').hide();
    //        var maxClassNum = $(this).attr('maxClass');
    //        var maxClassCount = [];
    //        for (var i = 0; i < maxClassNum; i++) {
    //            maxClassCount.push('<option value="' + (i + 1) + '">' + (i + 1) + '</option>');
    //        }
    //        $('#max-class-count').append(maxClassCount);
    //        var teachingClassList = [];
    //        var classInfo = $(this).attr('classInfo');
    //        var classInfoData = JSON.parse(classInfo);
    //        console.log(classInfo)
    //        $('#save-course-btn').attr(
    //            {
    //                'teacherId': $(this).attr('teacherId'),
    //                'courseName': $(this).attr('courseName'),
    //                'classInfoData': classInfo
    //            }
    //        );
    //        for (var j = 0; j < classInfoData.length; j++) {
    //            teachingClassList.push('<li><input type="checkbox" classId="' + classInfoData[j].classId + '" className="' + classInfoData[j].className + '" id="classInfo' + j + '" /><label for="classInfo' + j + '">' + classInfoData[j].className + '</label></li>');
    //        }
    //        $('.teaching-class-list').append(teachingClassList);
    //    })
    //},
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
    // 模糊匹配教师
    renderSearchList: function (data) {
        var dataJson = data;
        $(document).on('click', function (e) {
            $('#search-list').html('').hide();
            e.stopPropagation();
        });
        if (dataJson.length == 0) {
            $('#search-list').html('<span>暂无数据</span>')
            return false;
        }
        var tpl = '';
        $.each(dataJson, function (i, v) {
            tpl += "<li teacherId='" + v.teacherId + "' courseName='" + v.courseName + "' maxClass='" + v.classInfo.length + "' classInfo='" + JSON.stringify(v.classInfo) + "' ><a href='javascript:;'>" + v.teacherName + "</a></li>"
        });
        $('#search-list').html(tpl).show();
    },

    // 所授课程
    teachingCourses: function (data) {

    },
    // 最大带班数
    maxClassNum: function (data) {

    },
    // 所带班级
    classItem: function (data) {

    },
    // 保存
    saveOrUpdateTeacher: function (taskId, teacherId, classNum, course, classId) {
        var that = this;
        Common.ajaxFun('/scheduleTask/saveOrUpdateTeacher.do', 'POST', {
                'taskId': taskId,
                'teacherId': teacherId,
                'classNum': classNum,
                'course': course,
                'classId': classId
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    layer.msg("添加成功!");
                    that.queryTeacherByTaskId(taskId);
                    layer.closeAll();
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    }
};


$(function () {

    var TeacherInfoIns = new TeacherInfo();
    // 添加
    $(document).on('click', '#add-teacher-btn', function () {
        TeacherInfoIns.addOrUpdateTeacher('添加教师');
    });

    // 模糊搜索
    //$(document).on('mouseover', '#teacher-keywords', function () {
    //    $('.like-teacher-list').show();
    //});
    //
    //$(document).on('click', function (e) {
    //    $('.like-teacher-list').hide();
    //    e.stopPropagation();
    //});


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
        var teachername = teacherV.attr('teachername');
        var classnum = teacherV.attr('classnum');
        var classinfo = teacherV.attr('classinfo');
        TeacherInfoIns.addOrUpdateTeacher('修改教师',teachername);
        $('#max-class-count option[value="'+ classnum +'"]').attr('selected','selected');

        console.log(classinfo.split(","))

        $.each(classinfo.split(","),function(i,v){
            console.log(v);
            $('.teaching-class-list input[classid="'+ v +'"]').attr('checked','checked');
        });


        //for (var j = 0; j < classInfoData.length; j++) {
        //    teachingClassList.push('<li><input type="checkbox" classId="' + classInfoData[j].classId + '" className="' + classInfoData[j].className + '" id="classInfo' + j + '" /><label for="classInfo' + j + '">' + classInfoData[j].className + '</label></li>');
        //}
        //$('.teaching-class-list').append(teachingClassList);

        //var maxClassCount = [];
        //for (var i = 0; i < data.classNum; i++) {
        //    maxClassCount.push('<option value="' + (i + 1) + '">' + (i + 1) + '</option>');
        //}
        //$('#max-class-count').append(maxClassCount);
    });

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
    // 保存
    $('body').on('click', '#save-course-btn', function () {
        var keyWord = $.trim($('#teacher-keywords').val());
        if (keyWord == "") {
            layer.tips('请输入教师姓名', $('#teacher-keywords'));
            return false;
        }
        var teacherid = $(this).attr('teacherid');
        if (!teacherid) {
            layer.tips('请至教师管理添加教师信息！', $('#teacher-keywords'));
            return false;
        }


        var maxClassCount = $('#max-class-count').val();
        if (maxClassCount == '00') {
            layer.tips('请选择最大带班数', $('#max-class-count'));
            return false;
        }
        var teachingClassLen = $('.teaching-class-list input:checked').length;
        console.log(teachingClassLen);
        if (maxClassCount < teachingClassLen) {
            layer.tips('最大带班数不能小于选中所带班级数', $('#max-class-count'));
            return false;
        }
        var courseName = $(this).attr('courseName');
        var teachingClassArr = [];
        $('.teaching-class-list input:checked').each(function(i,v){
            teachingClassArr.push($(this).attr('classid'));
        });
        TeacherInfoIns.saveOrUpdateTeacher(taskId, teacherid, maxClassCount, courseName, teachingClassArr.join(","));


    });







});