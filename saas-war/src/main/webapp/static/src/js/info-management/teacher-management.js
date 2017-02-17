var tnId = Common.cookie.getCookie('tnId');


function TeacherManagement() {
    this.init();

}

TeacherManagement.prototype = {
    constructor: TeacherManagement,
    init: function () {
        this.getThead();
        this.getTeacherData();
        this.gradeCode = '';
        this.subjectV  = '';

    },
    // 拉取表格thead
    getThead:function(){
        var that = this;
        Common.ajaxFun('/config/get/teacher/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                if (data.length != 0) {
                    var columnHtml = [];
                    columnHtml.push('<tr>');
                    columnHtml.push('<th class="center"><label><input type="checkbox" id="checkAll" class="ace" /><span class="lbl"></span></label></th>');
                    columnHtml.push('<th class="center">序号</th>');
                    $.each(data, function (i, k) {
                        columnHtml.push('<th class="center"  taecherNamekey ="'+ k.enName +'"  >' + k.name + '</th>');
                    });
                    columnHtml.push('</tr>');
                    $("#teacher-manage-table thead").html(columnHtml.join(''));
                }
            }
        }, function (res) {
            layer.msg("出错了");
        });
    },
    // 默认拉取列表
    getTeacherData: function () {
        var that = this;
        Common.ajaxFun('/manage/teacher/' + tnId + '/getTenantCustomData.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var teacherDataHtml = [];
                var data = res.bizData.result;
                $.each(data, function (m, n) {
                    teacherDataHtml.push('<tr rowid="' + n.id + '">');
                    teacherDataHtml.push('<td class="center"><label><input type="checkbox" teacher_class="'+ n.teacher_class +'" teacher_max_take_class="'+ n.teacher_max_take_class +'" teacher_grade="'+ n.teacher_grade +'" teacher_major_type="'+ n.teacher_major_type +'" teacher_name="'+ n.teacher_name +'" cid="' + n.id + '" class="ace" /><span class="lbl"></span></label></td>');
                    teacherDataHtml.push('<td class="center">' + (m + 1) + '</td>');
                    teacherDataHtml.push('<td class="center">' + n.teacher_name + '</td>');
                    teacherDataHtml.push('<td class="center">' + n.teacher_major_type + '</td>');
                    teacherDataHtml.push('<td class="center">' + n.teacher_grade + '</td>');
                    teacherDataHtml.push('<td class="center">' + n.teacher_max_take_class + '</td>');
                    teacherDataHtml.push('<td class="center">' + n.teacher_class + '</td>');
                    teacherDataHtml.push('</tr>');
                });
                $("#teacher-manage-table tbody").html(teacherDataHtml.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    // 提交保存
    addSubmit:function(datas,url){
        var that = this;
        Common.ajaxFun(url, 'POST', JSON.stringify(datas), function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.result=='SUCCESS'){
                    layer.closeAll();
                    layer.msg("教师添加成功");
                    that.getTeacherData();
                    $('#checkAll').prop('checked',false);
                }else{
                    layer.msg(res.bizData.result);
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, null,true);
    },
    // 所教科目
    querySubject:function(teacher_major_type){
        var that = this;
        var courseArr = [];
        courseArr.push('<option value="00">请选择科目</option>');
        Common.ajaxFun('/teacher/querySubject.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                $.each(res.bizData.subject, function (i, v) {
                    courseArr.push('<option value="' + v + '">' + v + '</option>');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        return courseArr.join('');
    },
    // 所教年级
    queryGradeBySubject:function(subjectV){
        var that = this;
        var GradeArr = [];
        $('#grade-list').html('');
        GradeArr.push('<option value="00">请选择所带年级</option>');
        Common.ajaxFun('/teacher/queryGradeBySubject.do', 'GET', {
            "subject":subjectV
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $.each(res.bizData.grade, function (i, v) {
                    GradeArr.push('<option value="' + v['gradeCode'] + '">' + v['grade'] + '</option>');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        //return GradeArr.join('');
        $('#grade-list').append(GradeArr.join(''));

    },
    getGrade:function(){

    },
    // 最大带班数
    queryMaxClassByGradeAndSubject:function(gradeCode,subject){
        var that = this;
        var classMaxArr = [];
        $('#classMax-list').html('');
        classMaxArr.push('<option value="00">请选择最大带班数</option>');
        Common.ajaxFun('/teacher/queryMaxClassByGradeAndSubject.do', 'GET', {
            "gradeCode":gradeCode,
            "subject":subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var maxClass = res.bizData.maxClass;
                if(maxClass!=0){
                    for(var i = 1;i<=maxClass;i++){
                        classMaxArr.push('<option value="' + i + '">' + i + '</option>');
                    }
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        $('#classMax-list').append(classMaxArr.join(''));
    },
    // 所带班级list
    queryClassByGradeCodeAndSubject:function(gradeCode,subject){
        var that = this;
        var classItemArr = [];
        $('#class-item').html('');
        Common.ajaxFun('/teacher/queryClassByGradeCodeAndSubject.do', 'GET', {
            "gradeCode":gradeCode,
            "subject":subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $('#box-row-classes').removeClass('hides');
                $.each(res.bizData.class, function (i, v) {
                    classItemArr.push('<label><input name="form-field-checkbox" type="checkbox" className = "'+ v.className +'" class="ace form-input-checkbox" /><span class="lbl">'+ v.className +'</span></label>&nbsp;&nbsp;&nbsp;&nbsp;');
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        $('#class-item').append(classItemArr.join(''));
    },

    // 添加教师
    addTeacherLayer:function(title,cid ,teacher_name,teacher_major_type,teacher_grade,teacher_max_take_class,teacher_class){
        var that = this;
        var addTeacherContentHtml = [];
        addTeacherContentHtml.push('<div class="add-course-box">');
        addTeacherContentHtml.push('<div class="course-box">');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="class-label"><i>*</i>教师名称：</span>');
        if(teacher_name){
            addTeacherContentHtml.push('<input type="text" id="teacher-name" teacherKay="teacher_name" value="'+ teacher_name +'" placeholder="请输入教师名称" class=""/>');
        }else{
            addTeacherContentHtml.push('<input type="text" id="teacher-name" teacherKay="teacher_name" value="" placeholder="请输入教师名称" class=""/>');
        }
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="class-label"><i>*</i>所教科目：</span>');

        addTeacherContentHtml.push('<select id="course-name-list" teacherKay="teacher_major_type">'+ that.querySubject() +'</select>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="class-label"><i>*</i>所带年级：</span>');
        if(teacher_grade){
            addTeacherContentHtml.push('<select id="grade-list" teacher_major_type="'+ teacher_major_type +'" teacher_grade="'+ teacher_grade +'" teacherKay="teacher_grade"></select>');
        }else{
            addTeacherContentHtml.push('<select id="grade-list" teacherKay="teacher_grade"></select>');
        }

        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        addTeacherContentHtml.push('<span class="class-label"><i>*</i>最大带班数：</span>');
        addTeacherContentHtml.push('<select id="classMax-list" teacherKay="teacher_max_take_class"></select>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row" id="box-row-classes">');
        addTeacherContentHtml.push('<span class="class-label class-item-label"><i>*</i>所带班级：</span>');
        addTeacherContentHtml.push('<span class="class-item" id="class-item" teacherKay="teacher_class"></span>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('<div class="box-row">');
        if(cid){
            addTeacherContentHtml.push('<button type="button" class="save-btn" cid="'+ cid +'" id="save-btn">保存</button>');
        }else{
            addTeacherContentHtml.push('<button type="button" class="save-btn" id="save-btn">保存</button>');
        }
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        addTeacherContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span class="layer-title">' + title + "</span>",
            offset: 'auto',
            area: ['550px', '400px'],
            content: addTeacherContentHtml.join(''),
            success: function () {
                $('#grade-list').html('<option value="00">请选择所带年级</option>');
                $('#classMax-list').html('<option value="00">请选择最大带班数</option>');
                $('#box-row-classes').addClass('hides');

                if(teacher_major_type){
                    that.subjectV = teacher_major_type;
                    that.queryGradeBySubject(teacher_major_type);
                    $('#course-name-list option[value="'+ teacher_major_type +'"]').prop('selected','selected');
                }
                console.log(teacher_grade)
                if(teacher_grade){
                    that.gradeCode = teacher_grade;
                    that.queryMaxClassByGradeAndSubject(that.gradeCode,that.subjectV);
                    that.queryClassByGradeCodeAndSubject(that.gradeCode,that.subjectV);
                    $('#grade-list option[value="'+ teacher_grade +'"]').prop('selected','selected');
                }

                if(teacher_max_take_class){
                    $('#classMax-list option[value="'+ teacher_max_take_class +'"]').prop('selected','selected');
                }

                if(teacher_class){
                    console.log(teacher_class.split('、'));
                    teacher_class = teacher_class.split('、');
                    for(var k=0;k<teacher_class.length;k++){
                        console.log(teacher_class[k])
                        $('#class-item').find('.form-input-checkbox[classname="'+ teacher_class[k] +'"]').prop('checked','checked');
                    }

                }

                // 选择科目
                $('#course-name-list').on('change', function () {
                    that.subjectV = $(this).children('option:selected').val();
                    if(that.subjectV!=='00'){
                        that.queryGradeBySubject(that.subjectV);
                    }else{
                        $('#grade-list').html('<option value="00">请选择所带年级</option>');
                    }
                    $('#classMax-list').html('<option value="00">请选择最大带班数</option>');
                    $('#box-row-classes').addClass('hides');
                });
                // 选择所带班级
                $('#grade-list').on('change', function () {
                    that.gradeCode = $(this).children('option:selected').val();
                    if(that.gradeCode!=='00'){
                        that.queryMaxClassByGradeAndSubject(that.gradeCode,that.subjectV);
                        that.queryClassByGradeCodeAndSubject(that.gradeCode,that.subjectV);
                    }else{
                        $('#box-row-classes').addClass('hides');
                        $('#classMax-list').html('<option value="00">请选择最大带班数</option>');
                    }
                });

            }
        });
    },
    // 修改教师
    // 删除教师
    delTeacher:function(ids){
        var that = this;
        Common.ajaxFun('/manage/teacher/'+ tnId +'/'+ ids +'/remove.do', 'POST', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                if(res.bizData.result=='SUCCESS'){
                    layer.msg('删除成功');
                    layer.closeAll();
                    that.getTeacherData();
                    $('#checkAll').prop('checked',false);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
    // 模板下载
    // 批量上传
};

var TeacherManagementIns = new TeacherManagement();

$(function () {
    // 点击添加教师
    $('#addTeacher-btn').on('click',function(){


        TeacherManagementIns.addTeacherLayer('添加教师');
    });


    // 点击保存
    $('body').on('click','#save-btn',function(){
        var teacherNameVal = $.trim($('#teacher-name').val());
        if(teacherNameVal==''){
            layer.tips('请输入教师名称', $('#teacher-name'));
            return false;
        }
        if(teacherNameVal.length>10){
            layer.tips('字数限制10个字以内', $('#teacher-name'));
            return false;
        }
        var courseNameListV = $('#course-name-list').val();
        if(courseNameListV == '00'){
            layer.tips('请选择科目', $('#course-name-list'));
            return false;
        }
        var gradeListV = $('#grade-list').children('option:selected').text();
        if(gradeListV == '00'){
            layer.tips('请选择所带年级', $('#grade-list'));
            return false;
        }
        var classMaxListV = $('#classMax-list').val();
        if(classMaxListV == '00'){
            layer.tips('请选择最大带班数', $('#classMax-list'));
            return false;
        }
        var classItemSelLen = $('.form-input-checkbox[type="checkbox"]:checked').length;
        if(classItemSelLen==0){
            layer.tips('请选择所带班级', $('#class-item'));
            return false;
        }

        if(classItemSelLen > parseInt(classMaxListV)){
            layer.tips('所带班级选中数量不能大于选中最大带班数', $('#class-item'));
            return false;
        }


        var classItemSelArr = [];
        $('.form-input-checkbox[type="checkbox"]:checked').each(function(i,v){
            classItemSelArr.push('、' + $(v).attr('className'))
        });

        classItemSelArr = classItemSelArr.join('');
        classItemSelArr = classItemSelArr.substring(1, classItemSelArr.length);



        var datas = {
            "clientInfo": {},
            "style": "",
            "data": {
                "type": 'teacher',
                "tnId": tnId,
                "teantCustomList": [
                    {"key":'teacher_name',"value":teacherNameVal},
                    {"key":'teacher_major_type',"value":courseNameListV},
                    {"key":'teacher_grade',"value":gradeListV},
                    {"key":'teacher_max_take_class',"value":classMaxListV},
                    {"key":'teacher_class',"value":classItemSelArr}
                ]
            }
        };
        var cid = $(this).attr('cid');
        var url = '';
        if(cid){
            datas.data['pri'] = cid;
            url='/manage/teant/custom/data/modify.do';
        }else{
            url='/manage/teant/custom/data/add.do';
        }
        TeacherManagementIns.addSubmit(datas,url)
    });
    // 点击修改
    $('#updateTeacher-btn').on('click',function(){

        var checkboxLen = $('#teacher-manage-list input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }

        var  rowSel = $('#teacher-manage-list input:checked');
        var cid = rowSel.attr('cid'),
            teacher_name = rowSel.attr('teacher_name'),
            teacher_major_type = rowSel.attr('teacher_major_type'),
            gradeCode = rowSel.attr('teacher_grade'),
            teacher_max_take_class = rowSel.attr('teacher_max_take_class'),
            teacher_class = rowSel.attr('teacher_class');

        var teacher_grade = '';
        switch (gradeCode){
            case "高一年级":
                teacher_grade = '1';
                break;
            case "高二年级":
                teacher_grade = '2';
                break;
            case "高三年级":
                teacher_grade = '3';
                break;
            default:
                break;
        }




        TeacherManagementIns.addTeacherLayer('修改教师',cid ,teacher_name,teacher_major_type,teacher_grade,teacher_max_take_class,teacher_class);
    });
    // 点击删除
    $('#delTeacher-btn').on('click',function(){
        var  rowSel = $('#teacher-manage-list input:checked'),
            checkboxLen = rowSel.length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }

        var selItem = [];
        $('#teacher-manage-list').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push($(this).attr('cid'));
            }
        });
        selItem = selItem.join('-');
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            TeacherManagementIns.delTeacher(selItem);
        }, function () {
            layer.closeAll();
        });
    });
    // 点击模板下载
    $('body').on('click','#downloadBtn',function(){
        window.location.href = '/manage/teacher/export/' + tnId + '.do';
    });


    //上传数据类及其原型
    function UploadData () {

    }
    UploadData.prototype = {
        constructor: UploadData,
        init: function () {

        },
        showUploadBox: function (title) {
            var that = this;
            var uploadDataHtml = [];
            uploadDataHtml.push('<div class="upload-box">');
            uploadDataHtml.push('<span id="uploader-demo">');
            uploadDataHtml.push('<span id="fileList" style="display: none;" class="uploader-list"></span>');
            uploadDataHtml.push('<button class="btn btn-info btn-import" id="btn-import">导入教师数据Excel</button>');
            uploadDataHtml.push('</span>');
            uploadDataHtml.push('<a href="javascript: void(0);" id="downloadBtn" class="download-link">请先导出Excel模板，进行填写</a>');
            uploadDataHtml.push('<button class="btn btn-cancel cancel-btn" id="cancel-download-btn">取消</button>');
            uploadDataHtml.push('</div>');
            layer.open({
                type: 1,
                title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
                offset: 'auto',
                area: ['460px', '300px'],
                content: uploadDataHtml.join('')
            });
            upload();
        }
    };

//上传
    $(document).on('click', '#uploadBtn', function () {
        var upload = new UploadData();
        upload.showUploadBox('导入教师数据');
    });

    //取消操作按钮(关闭对话框)
    $(document).on("click", ".cancel-btn", function () {
        layer.closeAll();
    });

    function upload () {
        var $ = jQuery,
            $list = $('#fileList'),

        // Web Uploader实例
            uploader;
        // 初始化Web Uploader
        uploader = WebUploader.create({

            // 自动上传。
            auto: true,

            // swf文件路径
            swf: BASE_URL + '/webuploader-0.1.5 2/Uploader.swf',

            // 文件接收服务端。
            server: rootPath + '/config/upload/teacher/' + tnId + '.do',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#btn-import',

            // 只允许选择文件，可选。
            accept: {
                title: 'excel',
                extensions: 'xlsx,xls',
                mimeTypes: '.xlsx,.xls'
            },
            fileVal: 'inputFile',
            duplicate: new Date()

        });

        // 当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            var $li = $(
                '<div id="' + file.id + '" class="file-item thumbnail">' +
                    //'<img>' +
                '<div class="info">' + file.name + '</div>' +
                '</div>'
            );
            $list.append($li);

        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                $percent = $li.find('.progress span');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<p class="progress"><span></span></p>')
                    .appendTo($li)
                    .find('span');
            }

            $percent.css('width', percentage * 100 + '%');
            //layer.load(1, {shade: [0.3,'#000']});
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file, response) {
            if (TeacherManagementIns != null) {
                TeacherManagementIns.getTeacherData();
            }
            if (response.bizData.result) {
                if (response.bizData.result == 'SUCCESS') {
                    layer.msg('上传成功');
                    setTimeout(function(){
                        layer.closeAll();
                    },1000)
                } else {
                    layer.msg(response.bizData.result);
                }
            } else {
                layer.msg(response.msg);
            }
        });

        // 文件上传失败，现实上传出错。
        uploader.on('uploadError', function (file, response) {
            var $li = $('#' + file.id),
                $error = $li.find('div.error');

            // 避免重复创建
            if (!$error.length) {
                $error = $('<div class="error"></div>').appendTo($li);
            }

            $error.text('上传失败');
            if (response.bizData.result) {
                layer.msg(response.bizData.result);
            } else {
                layer.msg(response.msg);
            }
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').remove();
            //layer.closeAll('loading');
        });
    }
});



