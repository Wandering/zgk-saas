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
            contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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
            contentHtml.push('<li style="margin-bottom: 26px;"><span>' + k.name + '</span><ul id="teacher_major_type">');
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
        area: ['362px', 'auto'],
        content: contentHtml.join('')
    });
    this.getGrade();
    this.getYear();
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
                contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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
                contentHtml.push('<li style="margin-bottom: 26px;"><span>' + k.name + '</span><ul id="teacher_major_type">');
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
            area: ['362px', 'auto'],
            content: contentHtml.join('')
        });
        this.getGrade();
        this.getYear();
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
    window.location.href = '/config/export/' + teacherManagement.type + '/' + tnId + '.do';
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