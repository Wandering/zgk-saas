/**
 * Created by machengcheng on 16/11/9.
 */
var tnId = Common.cookie.getCookie('tnId');

function ClassSettings () {
    this.type = 'class';
    this.ids = [];
    this.init();
}
ClassSettings.prototype = {
    constructor: ClassSettings,
    init: function () {
        this.getClass();
        this.tableDrag();
    },
    getClass: function () {
        var that = this;
        Common.ajaxFun('/config/get/' + that.type + '/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                that.renderClassTable(data);
            }
        }, function (res) {
            layer.msg("出错了", {time: 1000});
        }, true);
    },
    renderClassTable: function (result) {
        var that = this;
        var classHtml = [];
        $.each(result, function (i, k) {
            that.ids.push(k.configKey);
            classHtml.push('<tr>');
            classHtml.push('<td class="center"><label><input type="checkbox" columnid="' + k.id + '" class="ace" /><span class="lbl"></span></label></td>');
            classHtml.push('<td class="center index" indexid="' + k.configOrder + '">' + k.configKey + '</td>');
            classHtml.push('<td class="center">' + k.name + '</td>');
            classHtml.push('<td class="center"><a href="javascript: void(0);" id="' + k.id + '" class="remove-link remove-column">移除</a></td>');
            classHtml.push('</tr>');
        });
        $('#class-table tbody').html(classHtml.join(''));
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
                            that.getClass();
                            $('#checkAll').prop('checked', false);
                            layer.msg('删除成功', {time: 1000});
                            var classManagement = new parent.ClassManagement();
                            classManagement.init();
                            //parent.layer.close(index);
                        }
                    }
                }, function (res) {
                    alert("出错了");
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
                        var classManagement = new parent.ClassManagement();
                        classManagement.init();
                        parent.layer.close(index);
                    }else{
                        layer.msg(res.bizData.result);
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            }, true, null);
        }
    },
    tableDrag: function () {
        //表格排序
        var fixHelperModified = function (e, tr) {
                var $originals = tr.children();
                var $helper = tr.clone();
                $helper.children().each(function (index) {
                    $(this).width($originals.eq(index).width());
                });
                return $helper;
            },
            updateIndex = function (e, ui) {
                var ids = [];
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 1);
                    ids.push("-" + $(this).attr('indexid'));
                });
                ids = ids.join('');
                ids = ids.substring(1, ids.length);
                Common.ajaxFun('/config/sort/class/'+ ids +'.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result == "SUCCESS") {
                            layer.msg('排序成功', {time: 1000});
                        }else{
                            layer.msg(res.bizData.result);
                        }
                    }
                }, function (res) {
                    layer.msg("出错了", {time: 1000});
                }, true, null);
            };
        $("#class-table tbody").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y"
        }).disableSelection();
    }
};

var classSettings = new ClassSettings();

var chooseWindowBox = null;
$(document).on('click', '#addColumn-btn', function () {
    var columnHtml = [];
    columnHtml.push('<div class="class-settings-box">');
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
    Common.ajaxFun('/config/getInit/' + classSettings.type + '.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            if (res.bizData.configList.length != 0) {
                var template = Handlebars.compile($('#column-list-data-template').html());
                var data = res.bizData.configList;
                $('#column-list').html(template(data));
                $('#column-list li').each(function () {
                    var tempCheck = $(this).find('.class-column');
                    var tempId = $(this).find('.class-column').attr('columnid');
                    $.each(classSettings.ids, function (i, k) {
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
    this.type = 'class';
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
        alert('hahahehe' + ', idCount: ' + that.idCount + ', ids: ' + that.ids.join('-'));
        Common.ajaxFun('/manage/import/' + that.type + '/' + tnId + '.do?ids=' + that.ids.join('-'), 'POST', {}, function (res) {
            if (res.rtnCode == "0000000") {
                chooseWindowBox != null ? layer.close(chooseWindowBox) : layer.msg('窗口已关闭', {time: 1000});
                layer.msg(res.bizData.result, {time: 1000});
                if (classSettings != null) {
                    classSettings.getClass();
                }
            }
        }, function (res) {
            layer.msg("出错了!", {time: 1000});
        });
    }
};

$(document).on('click', '.remove-column', function () {
    var ids = $(this).attr('id');
    classSettings.removeColumn(false, ids);
});
$(document).on('click', '#delColumn-btn', function () {
    classSettings.removeColumn(true, '');
});

//(选择添加字段对话框内的)确定选择按钮
$(document).on('click', '.class-settings-box .choose-btn', function () {
    var columnSettings = new ColumnSettings();
    if (columnSettings.idCount != 0) {
        columnSettings.changeColumnEvent();
    } else {
        layer.msg('至少选择一个字段!', {time: 1000});
    }
});

//(选择添加字段对话框内的)取消按钮
$(document).on('click', '.class-settings-box .cancel-btn', function () {
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