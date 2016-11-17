/**
 * Created by machengcheng on 16/11/8.
 */
var tnId = Common.cookie.getCookie('tnId');
/**
 * 班级类及其原型
 * @constructor
 */
function ClassManagement () {
    this.tnId = tnId;
    this.type = 'class';
    this.columnArr = [];
}
ClassManagement.prototype = {
    constructor: ClassManagement,
    init: function () {
        this.getItem();
    },
    getItem: function () {//获取用户自定义班级表头
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
                            dataType: k.dataType
                        });
                    });
                    columnHtml.push('</tr>');
                    $("#class-manage-table thead").html(columnHtml.join(''));
                    that.getClassData();
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getClassData: function () {//获取全部班级数据
        var that = this;
        Common.ajaxFun('/manage/' + that.type + '/' + tnId + '/getTenantCustomData.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var classDataHtml = [];
                var data = res.bizData.result;
                $.each(data, function (m, n) {
                    var obj = data[m];
                    classDataHtml.push('<tr>');
                    classDataHtml.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td>');
                    $.each(that.columnArr, function (i, k) {
                        var tempObj = that.columnArr[i];
                        var tempColumnName = tempObj.enName;
                        if (obj[tempColumnName]) {
                            classDataHtml.push('<td class="center">' + obj[tempColumnName] + '</td>');
                        } else {
                            classDataHtml.push('<td class="center">-</td>');
                        }
                    });
                    classDataHtml.push('</tr>');
                });
                $("#class-manage-table tbody").html(classDataHtml.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    removeClass: function () {//删除某一行班级数据
        var that = this;
        var checkedLen = $("#class-manage-list input[type='checkbox']:checked").size();
        if(checkedLen == "0"){
            layer.tips('至少选择一项', $('#deleteClassBtn'));
            return false;
        }
        var selItem = [];
        $('#class-manage-list').find('input[type="checkbox"]').each(function (i, v) {
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
                        $('#class-change-list').html('');
                        $('#checkAll').prop('checked', false);
                        layer.msg('删除成功', {time: 1000});
                        var classManagement = new ClassManagement();
                        classManagement.init();
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

/**
 * 添加班级类及其原型
 * @param type
 * @constructor
 */
function AddClassManagement () {
    ClassManagement.call(this);
}
AddClassManagement.prototype = new ClassManagement();
AddClassManagement.prototype.constructor = AddClassManagement;
AddClassManagement.prototype.init = function (columnArr) {
    var that = this;
    $.each(columnArr, function (i, k) {
        that.columnArr.push({
            name: k.name,
            enName: k.enName,
            dataType: k.dataType
        });
    });
};
AddClassManagement.prototype.getYear = function () {
    var that = this;
    Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderYearSelect(res);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
};
AddClassManagement.prototype.renderYearSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var yearHtml = [];
        $.each(data.bizData.result, function (i, k) {
            yearHtml.push('<option value="' + k + '">' + k + '</option>');
        });
        $("#class_in_year").append(yearHtml.join(''));
    }
};
AddClassManagement.prototype.getGrade = function () {
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
AddClassManagement.prototype.renderGradeSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var gradeHtml = [];
        $.each(data.bizData.grades, function (i, k) {
            gradeHtml.push('<option value="' + k.id + '">' + k.grade + '</option>');
        });
        $("#class_grade").append(gradeHtml.join(''));
    }
};
AddClassManagement.prototype.addClass = function (title) {
    var that = this;
    var contentHtml = [];
    //contentHtml.push('<div class="add-class-box">');
    //contentHtml.push('<ul>');
    //contentHtml.push('<li><span>选择年级</span><select id="select-grade"><option value="00">选择年级</option></select></li>');
    //contentHtml.push('<li><span>入学年份</span><select id="select-year"><option value="00">入学年份</option><option>2016年</option><option>2015年</option></select></li>');
    //contentHtml.push('<li><span>班级名称</span><input type="text" id="class-name" /></li>');
    //contentHtml.push('<li><span>班级类型</span><input type="text" id="class-type" /></li>');
    //contentHtml.push('<li><span>班级科类</span><input type="text" id="class-subject" /></li>');
    //contentHtml.push('<li><span>班级编号</span><input type="text" id="class-number" /></li>');
    //contentHtml.push('<li><span class="three-word">班主任</span><input type="text" class="class-teache" id="class-teacher" /></li>');
    //contentHtml.push('<li><span>班级人数</span><input type="text" id="class-count" /></li>');
    //contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
    //contentHtml.push('</ul>');
    //contentHtml.push('</div>');
    contentHtml.push('<div class="add-class-box">');
    contentHtml.push('<ul>');
    $.each(that.columnArr, function (i, k) {
        if (k.dataType != 'select') {
            if (k.enName != 'class_boss') {
                contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
            } else {
                contentHtml.push('<li><span style="letter-spacing: 6px;">' + k.name + '</span><input type="text" style="position: relative;left: -6px;" id="' + k.enName + '" /></li>');
            }
        } else {
            contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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
    that.getYear();
    that.getGrade();
};

/**
 * 更新班级类及其原型
 * @param type
 * @constructor
 */
function UpdateClassManagement () {
    ClassManagement.call(this);
}
UpdateClassManagement.prototype = {
    constructor: UpdateClassManagement,
    init: function () {

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
            $("#class_in_year").append(yearHtml.join(''));
        }
    },
    updateClass: function (title) {//更新某一行班级数据
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="add-class-box">');
        contentHtml.push('<ul>');
        $.each(that.columnArr, function (i, k) {
            if (k.dataType != 'select') {
                if (k.enName != 'class_boss') {
                    contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
                } else {
                    contentHtml.push('<li><span style="letter-spacing: 6px;">' + k.name + '</span><input type="text" style="position: relative;left: -6px;" id="' + k.enName + '" /></li>');
                }
            } else {
                contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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
        that.getYear();
        //that.getGrade();
    }
};

var classManagement = new ClassManagement();
classManagement.init();

//创建添加班级对象
var addClassManagement = new AddClassManagement();
addClassManagement.init(classManagement.columnArr);

//添加班级按钮操作
$(document).on("click", "#addRole-btn", function () {
    addClassManagement.addClass('添加班级');
});

//更新班级按钮操作
$(document).on("click", "#updateRole-btn", function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    if(chknum!='1'){
        layer.tips('修改只能选择一项!', that);
        return false;
    }
    var updateClassManagement = new UpdateClassManagement();
    updateClassManagement.updateClass('更新班级');
});

//确认操作按钮
$(document).on("click", "#add-btn", function () {
    var postData = [];
    for (var i = 0; i < addClassManagement.columnArr.length; i++) {
        var tempObj = addClassManagement.columnArr[i];
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
            "type": addClassManagement.type,
            "tnId": tnId,
            "teantCustomList": postData
        }
    };
    Common.ajaxFun('/manage/teant/custom/data/add.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            classManagement.getClassData();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});
//取消操作按钮(关闭对话框)
$(document).on("click", ".cancel-btn", function () {
    layer.closeAll();
});

$(document).on('click', '#deleteClassBtn', function () {
    classManagement.removeClass();
});

//班级设置操作
$(document).on('click', '#class-settings-btn', function () {
    layer.open({
        type: 2,
        title: '班级设置',
        shadeClose: true,
        shade: 0.8,
        area: ['60%', '70%'],
        content: '/class-settings',
        cancel: function () {
            var classManagement = new ClassManagement();
            classManagement.init();
        }
    });
});