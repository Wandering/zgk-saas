/**
 * Created by machengcheng on 16/11/18.
 */
var tnId = Common.cookie.getCookie('tnId');

/**
 * 教师类及其原型
 * @constructor
 */
function TeacherManagement () {
    this.tnId = tnId;
    this.type = 'teacher';
    this.columnArr = [];
}
TeacherManagement.prototype = {
    constructor: TeacherManagement,
    init: function () {
        this.getItem();
    },
    getItem: function () {//获取全部用户自定义教师表头
        var that = this;
        Common.ajaxFun('/config/get/' + that.type + '/' + tnId + '.do', 'GET', {
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
                        columnHtml.push('<th class="center">' + k.name + '</th>');
                        that.columnArr.push({
                            name: k.name,
                            enName: k.enName,
                            dataType: k.dataType,
                            dataValue: k.dataValue
                        });
                    });
                    columnHtml.push('</tr>');
                    $("#teacher-manage-table thead").html(columnHtml.join(''));
                    that.getTeacherData();
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getTeacherData: function () {//获取全部用户自定义教师数据
        var that = this;
        layer.load(1, {shade: [0.3,'#000']});
        Common.ajaxFun('/manage/' + that.type + '/' + tnId + '/getTenantCustomData.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var teacherDataHtml = [];
                var data = res.bizData.result;
                $.each(data, function (m, n) {
                    var obj = data[m];
                    teacherDataHtml.push('<tr rowid="' + obj['id'] + '">');
                    teacherDataHtml.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td>');
                    teacherDataHtml.push('<th class="center">' + (m + 1) + '</th>');
                    $.each(that.columnArr, function (i, k) {
                        var tempObj = that.columnArr[i];
                        var tempColumnName = tempObj.enName;
                        if (obj[tempColumnName]) {
                            if (tempColumnName != 'teacher_major_type') {
                                teacherDataHtml.push('<td class="center">' + obj[tempColumnName] + '</td>');
                            } else {
                                var subjects = obj[tempColumnName].split('-');
                                teacherDataHtml.push('<td class="center">' + subjects.join('、') + '</td>');
                            }
                        } else {
                            teacherDataHtml.push('<td class="center">-</td>');
                        }
                    });
                    teacherDataHtml.push('</tr>');
                });
                $("#teacher-manage-table tbody").html(teacherDataHtml.join(''));
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 500);
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    removeTeacher: function () {//删除某一行教师数据
        var that = this;
        var checkedLen = $("#teacher-manage-list input[type='checkbox']:checked").size();
        if(checkedLen == "0"){
            layer.tips('至少选择一项', $('#deleteTeacherBtn'), {time: 1000});
            return false;
        }
        var selItem = [];
        $('#teacher-manage-list').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push($(this).attr('cid'));
            }
        });
        selItem = selItem.join('-');
        var ids = selItem;
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            Common.ajaxFun('/manage/' + that.type + '/' + tnId + '/' + ids + '/remove.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if(res.bizData.result="SUCCESS"){
                        $('#teacher-change-list').html('');
                        $('#checkAll').prop('checked', false);
                        layer.msg('删除成功', {time: 1000});
                        var teacherManagement = new TeacherManagement();
                        teacherManagement.init();
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            });
        }, function () {
            layer.closeAll();
        });
    }
};

//实例化教师管理类, 并调用初始化方法
var teacherManagement = new TeacherManagement();
teacherManagement.init();

function AddTeacherManagement () {
    TeacherManagement.call(this);
}
AddTeacherManagement.prototype = new TeacherManagement();
AddTeacherManagement.prototype.constructor = AddTeacherManagement;
AddTeacherManagement.prototype.init = function (columnArr) {
    var that = this;
    $.each(columnArr, function (i, k) {
        that.columnArr.push({
            name: k.name,
            enName: k.enName,
            dataType: k.dataType,
            dataValue: k.dataValue
        });
    });
};
AddTeacherManagement.prototype.getYear = function () {
    var that = this;
    Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderYearSelect(res);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
};
AddTeacherManagement.prototype.renderYearSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var yearHtml = [];
        $.each(data.bizData.result, function (i, k) {
            yearHtml.push('<option value="' + k + '">' + k + '</option>');
        });
        $("#teacher_in_school").append(yearHtml.join(''));
    }
};
AddTeacherManagement.prototype.getGrade = function () {
    var that = this;
    Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
        'tnId': tnId
    }, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderGradeSelect(res);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
};
AddTeacherManagement.prototype.renderGradeSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var gradeHtml = [];
        $.each(data.bizData.grades, function (i, k) {
            gradeHtml.push('<option value="' + k.grade + '">' + k.grade + '</option>');
        });
        $("#teacher_grade").append(gradeHtml.join(''));
    }
};
AddTeacherManagement.prototype.getClass = function () {
    var that = this;
    Common.ajaxFun('/manage/class/' + tnId + '/getTenantCustomData.do', 'GET', {
        'tnId': tnId
    }, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderClassSelect(res);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
};
AddTeacherManagement.prototype.renderClassSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var classHtml = [];
        $.each(data.bizData.result, function (i, k) {
            classHtml.push('<option value="' + k.class_name + '">' + k.class_name + '</option>');
        });
        $("#teacher_class").append(classHtml.join(''));
    }
};
AddTeacherManagement.prototype.addTeacher = function (title) {
    var that = this;
    var contentHtml = [];
    contentHtml.push('<div class="add-class-box">');
    contentHtml.push('<ul>');
    $.each(that.columnArr, function (i, k) {
        if (k.dataType == 'text') {
            if (k.enName != 'teacher_sex' && k.enName != 'teacher_age' && k.enName != 'teacher_position' && k.enName != 'teacher_title' && k.enName != 'teacher_experience') {
                contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
            } else {
                contentHtml.push('<li><span style="letter-spacing: 24.0px;">' + k.name + '</span><input type="text" style="position: relative;left: -23px;" id="' + k.enName + '" /></li>');
            }
        } else if (k.dataType == 'select') {
            contentHtml.push('<li>');
            contentHtml.push('<span>' + k.name + '</span>');
            contentHtml.push('<select id="' + k.enName + '">');
            contentHtml.push('<option value="00">' + k.name + '</option>');
            if (k.dataValue) {
                var subjects = k.dataValue.split('-');
                $.each(subjects, function (n, m) {
                    contentHtml.push('<option value="' + m + '">' + m + '</option>');
                });
            }
            contentHtml.push('</select>');
            contentHtml.push('</li>');
        } else if (k.dataType == 'radio') {
            var values = (k.dataValue + '').split('-');
            var enName = k.enName;
            contentHtml.push('<li><span style="letter-spacing: 24.0px;">' + k.name + '</span>');
            $.each(values, function (i, k) {
                if (i == 0) {
                    contentHtml.push('<input type="radio" name="' + enName + '" id="' + enName + i + '" checked="checked" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label>');
                } else {
                    contentHtml.push('<input type="radio" name="' + enName + '" id="' + enName + i + '" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label>');
                }
            });
            contentHtml.push('</li>');
        } else if (k.dataType == 'checkbox') {
            var values = (k.dataValue + '').split('-');
            var enName = k.enName;
            contentHtml.push('<li style="margin-bottom: 56px;"><span>' + k.name + '</span><ul id="teacher_major_type">');
            $.each(values, function (i, k) {
                contentHtml.push('<li><input type="checkbox" name="' + enName + '" id="' + enName + i + '" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label></li>');
            });
            contentHtml.push('</ul></li>');
        }
    });
    contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
    contentHtml.push('</ul>');
    contentHtml.push('</div>');
    layer.open({
        type: 1,
        title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
        offset: 'auto',
        area: ['362px', '80%'],
        content: contentHtml.join('')
    });
    this.getGrade();
    this.getYear();
    this.getClass();
};

//创建添加教师对象
var addTeacherManagement = new AddTeacherManagement();
addTeacherManagement.init(teacherManagement.columnArr);

//添加教师按钮操作
$(document).on("click", "#addRole-btn", function () {
    addTeacherManagement.addTeacher('添加教师');
});

function UpdateTeacherManagement () {
    TeacherManagement.apply(this);
}
UpdateTeacherManagement.prototype = {
    constructor: UpdateTeacherManagement,
    init: function (columnArr) {
        var that = this;
        $.each(columnArr, function (i, k) {
            that.columnArr.push({
                name: k.name,
                enName: k.enName,
                dataType: k.dataType,
                dataValue: k.dataValue
            });
        });
    },
    getYear: function () {
        var that = this;
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderYearSelect(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderYearSelect: function (data) {
        if (data.rtnCode == '0000000') {
            var yearHtml = [];
            $.each(data.bizData.result, function (i, k) {
                yearHtml.push('<option value="' + k + '">' + k + '</option>');
            });
            $("#teacher_in_school").append(yearHtml.join(''));
        }
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderGradeSelect(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderGradeSelect: function (data) {
        if (data.rtnCode == '0000000') {
            var gradeHtml = [];
            $.each(data.bizData.grades, function (i, k) {
                gradeHtml.push('<option value="' + k.grade + '">' + k.grade + '</option>');
            });
            $("#teacher_grade").append(gradeHtml.join(''));
        }
    },
    getClass: function () {
        var that = this;
        Common.ajaxFun('/manage/class/' + tnId + '/getTenantCustomData.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderClassSelect(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderClassSelect: function (data) {
        if (data.rtnCode == '0000000') {
            var classHtml = [];
            $.each(data.bizData.result, function (i, k) {
                classHtml.push('<option value="' + k.class_name + '">' + k.class_name + '</option>');
            });
            $("#teacher_class").append(classHtml.join(''));
        }
    },
    updateTeacher: function (title) {//更新某一行教师数据
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="add-class-box">');
        contentHtml.push('<ul>');
        $.each(that.columnArr, function (i, k) {
            if (k.dataType == 'text') {
                if (k.enName != 'teacher_sex' && k.enName != 'teacher_age' && k.enName != 'teacher_position' && k.enName != 'teacher_title' && k.enName != 'teacher_experience') {
                    contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
                } else {
                    contentHtml.push('<li><span style="letter-spacing: 24.0px;">' + k.name + '</span><input type="text" style="position: relative;left: -23px;" id="' + k.enName + '" /></li>');
                }
            } else if (k.dataType == 'select') {
                //contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
                contentHtml.push('<li>');
                contentHtml.push('<span>' + k.name + '</span>');
                contentHtml.push('<select id="' + k.enName + '">');
                contentHtml.push('<option value="00">' + k.name + '</option>');
                if (k.dataValue) {
                    var subjects = k.dataValue.split('-');
                    $.each(subjects, function (n, m) {
                        contentHtml.push('<option value="' + m + '">' + m + '</option>');
                    });
                }
                contentHtml.push('</select>');
                contentHtml.push('</li>');
            } else if (k.dataType == 'radio') {
                var values = (k.dataValue + '').split('-');
                var enName = k.enName;
                contentHtml.push('<li><span style="letter-spacing: 24.0px;">' + k.name + '</span>');
                $.each(values, function (i, k) {
                    if (i == 0) {
                        contentHtml.push('<input type="radio" name="' + enName + '" id="' + enName + i + '" checked="checked" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label>');
                    } else {
                        contentHtml.push('<input type="radio" name="' + enName + '" id="' + enName + i + '" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label>');
                    }
                });
                contentHtml.push('</li>');
            } else if (k.dataType == 'checkbox') {
                var values = (k.dataValue + '').split('-');
                var enName = k.enName;
                contentHtml.push('<li style="margin-bottom: 56px;"><span>' + k.name + '</span><ul id="teacher_major_type">');
                $.each(values, function (i, k) {
                    contentHtml.push('<li><input type="checkbox" name="' + enName + '" id="' + enName + i + '" value="' + k + '" /><label for="' + enName + i + '">' + k + '</label></li>');
                });
                contentHtml.push('</ul></li>');
            }
        });
        contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="update-btn">确认修改</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
        contentHtml.push('</ul>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '80%'],
            content: contentHtml.join('')
        });
        this.getGrade();
        this.getYear();
        this.getClass();
        var rowid = $(".check-template :checkbox:checked").attr('cid');
        var rowItem = $('#teacher-manage-list tr[rowid="' + rowid + '"]').find('td');
        $.each(that.columnArr, function (i, k) {
            if (k.dataType == 'text') {
                if (rowItem.eq(i + 1).html() != '-') {
                    $('#' + k.enName).val(rowItem.eq(i + 1).html());
                }
            }
            if (k.dataType == 'radio') {
                var value = rowItem.eq(i + 1).html();
                $("input[name='" + k.enName + "'][value=" + value + "]").attr("checked",true);
            }
            if (k.dataType == 'checkbox') {
                var subjects = rowItem.eq(i + 1).html().split('、');
                var enName = k.enName;
                $.each(subjects, function (i, k) {
                    $("input[name='" + enName + "'][value=" + k + "]").attr("checked",true);
                });
            }
            if (k.dataType == 'select') {
                var value = rowItem.eq(i + 1).html();
                if (value != '-') {
                    $('#' + k.enName).val(rowItem.eq(i + 1).html());
                } else {
                    $('#' + k.enName).val('00');
                }
            }
        });
    }
};

var updateTeacherManagement = new UpdateTeacherManagement();
//更新班级按钮操作
$(document).on("click", "#updateRole-btn", function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    if(chknum!='1'){
        layer.tips('修改只能选择一项!', that, {time: 1000});
        return false;
    }
    updateTeacherManagement = new UpdateTeacherManagement();
    updateTeacherManagement.init(teacherManagement.columnArr);
    updateTeacherManagement.updateTeacher('更新教师');
});
//确认更新操作按钮
$(document).on("click", "#update-btn", function () {
    var postData = [];
    for (var i = 0; i < updateTeacherManagement.columnArr.length; i++) {
        var tempObj = updateTeacherManagement.columnArr[i];
        if (tempObj.dataType == 'text') {
            if ($('#' + tempObj.enName).val() == '') {
                layer.msg(tempObj.name + '不能为空!', {time: 1000});
                $('#' + tempObj.enName).focus();
                return;
            } else {
                postData.push({
                    "key": tempObj.enName,
                    "value": $('#' + tempObj.enName).val()
                });
            }
        }
        if (tempObj.dataType == 'radio') {
            postData.push({
                "key": tempObj.enName,
                "value": $("input[name='" + tempObj.enName + "']:checked").val()
            });
        }
        if (tempObj.dataType == 'checkbox') {
            var subjects = [];
            $("input[name='" + tempObj.enName + "']").each(function () {
                if ($(this).prop('checked')) {
                    subjects.push($(this).val());
                }
            });
            if (subjects.length === 0) {
                layer.msg('请至少选择一门所教科目!', {time: 1000});
                return;
            }
            var values = subjects.join('-');
            postData.push({
                "key": tempObj.enName,
                "value": values
            });
        }
        if (tempObj.dataType == 'select') {
            if ($('#' + tempObj.enName).val() == '00') {
                layer.msg('请选择' + tempObj.name + '!', {time: 1000});
                $('#' + tempObj.enName).focus();
                return;
            } else {
                postData.push({
                    "key": tempObj.enName,
                    "value": $('#' + tempObj.enName).val()
                });
            }
        }
    }
    var rowid = $(".check-template :checkbox:checked").attr('cid');
    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "type": updateTeacherManagement.type,
            "tnId": tnId,
            "pri": rowid,//记录的id
            "teantCustomList": postData
        }
    };
    Common.ajaxFun('/manage/teant/custom/data/modify.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            teacherManagement.getTeacherData();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});

//确认添加操作按钮
$(document).on("click", "#add-btn", function () {
    var postData = [];
    console.info(addTeacherManagement.columnArr);
    for (var i = 0; i < addTeacherManagement.columnArr.length; i++) {
        var tempObj = addTeacherManagement.columnArr[i];
        if (tempObj.dataType == 'text') {
            if ($('#' + tempObj.enName).val() == '') {
                layer.msg(tempObj.name + '不能为空!', {time: 1000});
                $('#' + tempObj.enName).focus();
                return;
            } else {
                postData.push({
                    "key": tempObj.enName,
                    "value": $('#' + tempObj.enName).val()
                });
            }
        }
        if (tempObj.dataType == 'radio') {
            postData.push({
                "key": tempObj.enName,
                "value": $("input[name='" + tempObj.enName + "']:checked").val()
            });
        }
        if (tempObj.dataType == 'checkbox') {
            var subjects = [];
            $("input[name='" + tempObj.enName + "']").each(function () {
                if ($(this).prop('checked')) {
                    subjects.push($(this).val());
                }
            });
            if (subjects.length === 0) {
                layer.msg('请至少选择一门所教科目!', {time: 1000});
                return;
            }
            var values = subjects.join('-');
            postData.push({
                "key": tempObj.enName,
                "value": values
            });
        }
        if (tempObj.dataType == 'select') {
            if ($('#' + tempObj.enName).val() == '00') {
                layer.msg('请选择' + tempObj.name + '!', {time: 1000});
                $('#' + tempObj.enName).focus();
                return;
            } else {
                postData.push({
                    "key": tempObj.enName,
                    "value": $('#' + tempObj.enName).val()
                });
            }
        }
    }
    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "type": addTeacherManagement.type,
            "tnId": tnId,
            "teantCustomList": postData
        }
    };
    Common.ajaxFun('/manage/teant/custom/data/add.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            teacherManagement.getTeacherData();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});

//取消操作按钮(关闭对话框)
$(document).on("click", ".cancel-btn", function () {
    layer.closeAll();
});

$(document).on('click', '#deleteTeacherBtn', function () {
    teacherManagement.removeTeacher();
});

//模板下载
$(document).on('click', '#downloadBtn', function () {
    window.location.href = '/manage/' + teacherManagement.type + '/export/' + tnId + '.do';
});

//教师设置操作
$(document).on('click', '#teacher-settings-btn', function () {
    layer.open({
        type: 2,
        title: '教师设置',
        shadeClose: false, //点击页面背景是否关闭窗口
        shade: 0.8,
        area: ['60%', '70%'],
        content: '/teacher-settings',
        cancel: function () {
            var teacherManagement = new TeacherManagement();
            teacherManagement.init();
        }
    });
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
        layer.load(1, {shade: [0.3,'#000']});
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, response) {
        layer.closeAll();
        layer.msg('上传成功!');
        if (teacherManagement != null) {
            teacherManagement.getTeacherData();
        }
        if (response.bizData.result == 'SUCCESS') {
            //layer.msg(response.bizData.result);
            layer.msg('上传成功');
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
        setTimeout(function () {
            layer.closeAll('loading');
        }, 500);
    });
}