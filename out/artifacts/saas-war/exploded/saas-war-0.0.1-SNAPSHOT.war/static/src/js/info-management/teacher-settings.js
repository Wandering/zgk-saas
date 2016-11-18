/**
 * Created by machengcheng on 16/11/18.
 */

var tnId = Common.cookie.getCookie('tnId');

function TeacherSettings () {
    this.type = 'teacher';
    this.ids = [];
    this.init();
}
TeacherSettings.prototype = {
    constructor: TeacherSettings,
    init: function () {
        this.getTeacher();
    },
    getTeacher: function () {
        var that = this;
        Common.ajaxFun('/config/get/' + that.type + '/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                that.renderTeacherTable(data);
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, true);
    },
    renderTeacherTable: function (result) {
        var that = this;
        var classHtml = [];
        $.each(result, function (i, k) {
            that.ids.push(k.configKey);
            classHtml.push('<tr>');
            classHtml.push('<td class="center"><label><input type="checkbox" columnid="' + k.id + '" class="ace" /><span class="lbl"></span></label></td>');
            classHtml.push('<td class="center index" indexid="' + k.id + '">' + k.configKey + '</td>');
            classHtml.push('<td class="center">' + k.name + '</td>');
            classHtml.push('<td class="center"><a href="javascript: void(0);" id="' + k.id + '" class="remove-link remove-column">移除</a></td>');
            classHtml.push('</tr>');
        });
        $('#teacher-table tbody').html(classHtml.join(''));
    },
    removeColumn: function (isBatch, ids) {//isBatch是否是批量删除(false: 单个删除; true: 批量删除, 包括单个删除)
        var that = this;
        if (isBatch) {
            var checkedLen = $("#column-change-list input[type='checkbox']:checked").size();
            if(checkedLen == "0"){
                layer.tips('至少选择一项', $('#delColumn-btn'));
                return false;
            }
            var selItem = [];
            $('#column-change-list').find('input[type="checkbox"]').each(function (i, v) {
                if ($(this).is(':checked') == true) {
                    selItem.push($(this).attr('columnid'));
                }
            });
            selItem = selItem.join('-');
            var ids = selItem;
            layer.confirm('确定删除?', {
                btn: ['确定', '关闭'] //按钮
            }, function () {
                Common.ajaxFun('/manage/tenant/remove/' + that.type + '/' + tnId + '/' + ids + '.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if(res.bizData.result="SUCCESS"){
                            $('#column-change-list').html('');
                            that.getTeacher();
                            $('#checkAll').prop('checked', false);
                            layer.msg('删除成功', {time: 1000});
                            var teacherManagement = new parent.TeacherManagement();
                            teacherManagement.init();
                        }
                    }
                }, function (res) {
                    layer.msg("出错了", {time: 1000});
                });
            }, function () {
                layer.closeAll();
            });
        } else {
            //删除租户表头
            Common.ajaxFun('/manage/tenant/remove/' + that.type + '/' + tnId + '/' + ids +'.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result == "SUCCESS") {
                        layer.msg('删除成功', {time: 1000});
                        var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                        var teacherManagement = new parent.TeacherManagement();
                        teacherManagement.init();
                        parent.layer.close(index);
                    }else{
                        layer.msg(res.bizData.result);
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            }, true, null);
        }
    }
};

var teacherSettings = new TeacherSettings();

var chooseWindowBox = null;
$(document).on('click', '#addColumn-btn', function () {
    var columnHtml = [];
    columnHtml.push('<div class="teacher-settings-box">');
    columnHtml.push('<ul id="column-list">');
    columnHtml.push('</ul>');
    columnHtml.push('<div class="opt-bottom">');
    columnHtml.push('<a href="javascript: void(0);" class="opt-btn choose-btn">确定选择</a><a href="javascript: void(0);" class="opt-btn cancel-btn">取消</a>');
    columnHtml.push('</div>');
    columnHtml.push('</div>');
    chooseWindowBox = layer.open({
        type: 1,
        title: '选择添加字段',
        offset: 'auto',
        area: ['689px', '197px'],
        content: columnHtml.join('')
    });
    $('.class-column').prop('checked', true);
    Common.ajaxFun('/config/getInit/' + teacherSettings.type + '.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            if (res.bizData.configList.length != 0) {
                var template = Handlebars.compile($('#column-list-data-template').html());
                var data = res.bizData.configList;
                $('#column-list').html(template(data));
                $('#column-list li').each(function () {
                    var tempCheck = $(this).find('.class-column');
                    var tempId = $(this).find('.class-column').attr('columnid');
                    $.each(teacherSettings.ids, function (i, k) {
                        if (k == tempId) {
                            tempCheck.prop('checked', true);
                        }
                    });
                });
            }
        }
    }, function (res) {
        layer.msg("出错了", {time: 1000});
    }, true);
});

function ColumnSettings () {
    this.type = 'teacher';
    this.idCount = 0;
    this.ids = [];
    this.init();
}

ColumnSettings.prototype = {
    constructor: ColumnSettings,
    init: function () {
        this.idCount = 0;
        this.ids.splice(0, this.ids.length);
        this.initEvent();
        this.checkEvent();
    },
    initEvent: function () {
        var that = this;
        $('.class-column').each(function (i) {
            if ($(this).prop("checked")) {
                that.idCount++;
                that.ids.push($(this).attr('columnid'));
            }
        });
    },
    checkEvent: function () {
        var that = this;
        $('.class-column').each(function (i) {
            $(this).on('change', function () {
                if ($(this).prop("checked")) {
                    that.idCount++;
                    that.ids.push($(this).attr('columnid'));
                } else {
                    that.idCount--;
                    that.ids.delete($(this).attr('columnid'));
                }
            });
        });
    },
    changeColumnEvent: function () {
        var that = this;
        Common.ajaxFun('/manage/import/' + that.type + '/' + tnId + '.do?ids=' + that.ids.join('-'), 'POST', {}, function (res) {
            if (res.rtnCode == "0000000") {
                chooseWindowBox != null ? layer.close(chooseWindowBox) : layer.msg('窗口已关闭', {time: 1000});
                layer.msg(res.bizData.result, {time: 1000});
                if (teacherSettings != null) {
                    teacherSettings.getTeacher();
                }
            }
        }, function (res) {
            layer.msg("出错了!", {time: 1000});
        });
    }
};

$(document).on('click', '.remove-column', function () {
    var ids = $(this).attr('id');
    teacherSettings.removeColumn(false, ids);
});
$(document).on('click', '#delColumn-btn', function () {
    teacherSettings.removeColumn(true, '');
});

//(选择添加字段对话框内的)确定选择按钮
$(document).on('click', '.teacher-settings-box .choose-btn', function () {
    var columnSettings = new ColumnSettings();
    if (columnSettings.idCount != 0) {
        columnSettings.changeColumnEvent();
    } else {
        layer.msg('至少选择一个字段!', {time: 1000});
    }
});
//(选择添加字段对话框内的)取消按钮
$(document).on('click', '.teacher-settings-box .cancel-btn', function () {
    chooseWindowBox != null ? layer.close(chooseWindowBox) : layer.msg('窗口已关闭', {time: 1000});
    //classSettings.getClass();
});

Array.prototype.delete = function(varElement) {
    var numDeleteIndex = -1;
    for (var i = 0; i < this.length; i++) {
        //严格比较，即类型与数值必须同时相等。
        if (this[i] === varElement)
        {
            this.splice(i, 1);
            numDeleteIndex = i;
            break;
        }
    }
    return numDeleteIndex;
}