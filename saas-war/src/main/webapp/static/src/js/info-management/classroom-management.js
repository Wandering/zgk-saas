var tnId = Common.cookie.getCookie('tnId');

function ClassRoomManagement() {
    this.init();
}

ClassRoomManagement.prototype = {
    constructor: ClassRoomManagement,
    init: function () {
        this.getClassRoom();
        this.tableDrag();
    },
    getClassRoom:function () {
        var that = this;
        layer.load(1, {shade: [0.3,'#000']});
        Common.ajaxFun('/config/classRoom/get/'+ tnId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 500);
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderList: function (data) {
        if (data.rtnCode == "0000000") {
            var classRoomHtml = [];
            $.each(data.bizData.classRoom, function (i, v) {
                classRoomHtml.push('<tr rowid="' + v.id + '">');
                classRoomHtml.push('<td class="center">');
                classRoomHtml.push('<label>');
                classRoomHtml.push('<input type="checkbox" gradename="'+ v.grade +'" crid = "'+ v.id +'" class="ace" />');
                classRoomHtml.push('<span class="lbl"></span>');
                classRoomHtml.push('</label>');
                classRoomHtml.push('</td>');
                classRoomHtml.push('<td class="center index" indexid="' + v.id + '">'+ (i+1) +'</td>');
                classRoomHtml.push('<td class="center" name="grade" gradeid="' + v.gradeId + '">'+ v.grade +'</td>');
                classRoomHtml.push('<td class="center">'+ v.executiveNumber +'</td>');
                classRoomHtml.push('<td class="center">'+ v.dayNumber +'</td>');
                classRoomHtml.push('</tr>');
            });
            $('#classroom-table tbody').html(classRoomHtml.join(''));
        }
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var grade = [];
                grade.push('<option value="00">请选择年级</option>')
                $.each(res.bizData.grades,function(i,v){
                    grade.push('<option value="'+ v.id +'">'+ v.grade +'</option>');
                });
                $('#grade-list').append(grade.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    addClassRoom:function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal grade-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="classroom-num">行政教室数量: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="executiveNumber" placeholder="输入行政教室数量" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="classroom-num">走班教室数量: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="dayNumber" placeholder="输入走班教室数量" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="grade-name">选择年级: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<select id="grade-list"></select>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn" id="save-classroom-btn">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: title,
            offset: 'auto',
            area: ['362px', '280px'],
            content: contentHtml.join('')
        });
        that.getGrade();
    },
    updateClassRoom:function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal grade-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="classroom-num">行政教室数量: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="executiveNumber" placeholder="输入行政教室数量" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="classroom-num">走班教室数量: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="dayNumber" placeholder="输入走班教室数量" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="grade-name">选择年级: </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<select id="grade-list"></select>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info" id="update-classroom-btn">确认修改</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: title,
            offset: 'auto',
            area: ['362px', '280px'],
            content: contentHtml.join('')
        });
        that.getGrade();
        var rowid = $(".check-template :checkbox:checked").attr('crid');
        var rowItem = $('#classroom-manage-list tr[rowid="' + rowid + '"]').find('td');
        $('#executiveNumber').val(rowItem.eq(3).text());
        $('#dayNumber').val(rowItem.eq(4).text());
        $('#grade-list').val(rowItem.eq(2).attr('gradeid'));
    },
    deleteClassRoom: function () {//删除某一行教室数据
        var that = this;
        var checkedLen = $("#classroom-manage-list input[type='checkbox']:checked").size();
        if(checkedLen == "0"){
            layer.tips('至少选择一项', $('#deleteRole-Btn'), {time: 1000});
            return false;
        }
        var selItem = [];
        $('#classroom-manage-list').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push($(this).attr('crid'));
            }
        });
        selItem = selItem.join('-');
        var ids = selItem;
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            Common.ajaxFun('/manage/classRoom/delete/' + ids + '.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if(res.bizData.result="SUCCESS"){
                        $('#classroom-change-list').html('');
                        $('#checkAll').prop('checked', false);
                        layer.msg('删除成功', {time: 1000});
                        var classRoomManagement = new ClassRoomManagement();
                        classRoomManagement.init();
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            });
        }, function () {
            layer.closeAll();
        });
    },
    tableDrag: function () {
        var that = this;
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
                    ids.push($(this).attr('indexid'));
                    console.info($(this));
                });
                ids = ids.join('-');///manage/sort/classRoom/{tnId}/{ids}.do
                Common.ajaxFun('/manage/sort/classRoom/' + tnId + '/'+ ids +'.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result) {
                            layer.msg('排序成功', {time: 1000});
                        }else{
                            layer.msg(res.bizData.result);
                        }
                    }
                }, function (res) {
                    layer.msg("出错了", {time: 1000});
                }, true, null);
            };
        $("#classroom-manage-list").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y"
        }).disableSelection();
    }
};

var classRoomManagement = new ClassRoomManagement();

$('#addRole-btn').on('click',function(){
    classRoomManagement.addClassRoom('添加教室');
});

$(document).on('click','.save-btn',function () {
    var executiveNumber = $.trim($('#executiveNumber').val());
    var dayNumber = $.trim($('#dayNumber').val());
    var gradeV = $('#grade-list').val(),
        gradeName = $('#grade-list option[value=' + gradeV + ']').text(),
        classRoomReg = /^[0-9]*[1-9][0-9]*$/;
    if (executiveNumber == '' || !classRoomReg.test(executiveNumber)) {
        layer.tips('请填写行政教室数量!', $('#executiveNumber'));
        return false;
    }
    if (dayNumber == '' || !classRoomReg.test(dayNumber)) {
        layer.tips('请填写走班教室数量!', $('#dayNumber'));
        return false;
    }
    if (gradeV == '00') {
        layer.tips('请选择年级!', $('#grade-list'));
        return false;
    }

    for (var i = 0; i < $('td[name="grade"]').length; i++) {
        var tempGrade = $('td[name="grade"]').eq(i).text().trim();
        if (tempGrade == gradeName) {
            layer.msg('该年级已经存在!');
            return;
        }
    }

    Common.ajaxFun('/manage/classRoom/add/'+ tnId +'/' + gradeV + '.do', 'POST',{
        crNum: executiveNumber,
        dayNum: dayNumber
    }, function (res) {
        if (res.rtnCode == "0000000") {
            $('#grade-list').html('');
            classRoomManagement.getClassRoom();
            layer.closeAll();
        } else if (res.rtnCode == '1000001') {
            layer.closeAll();
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg("出错了");
    });

});

$('#updateRole-btn').on('click',function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    if (chknum != '1') {
        layer.tips('修改只能选择一项!', that);
        return false;
    }
    classRoomManagement.updateClassRoom('修改教室');
});
$(document).on('click', '#update-classroom-btn', function () {
    var executiveNumber = $.trim($('#executiveNumber').val());
    var dayNumber = $.trim($('#dayNumber').val());
    var gradeV = $('#grade-list').val(),
        classRoomReg = /^[0-9]*[1-9][0-9]*$/;
    if (executiveNumber == '' || !classRoomReg.test(executiveNumber)) {
        layer.tips('请填写行政教室数量!', $('#executiveNumber'));
        return false;
    }
    if (dayNumber == '' || !classRoomReg.test(dayNumber)) {
        layer.tips('请填写走班教室数量!', $('#dayNumber'));
        return false;
    }
    if (gradeV == '00') {
        layer.tips('请选择年级!', $('#grade-list'));
        return false;
    }

    var rowid = $(".check-template :checkbox:checked").attr('crid');
    Common.ajaxFun('/manage/classRoom/modify/' + rowid + '.do?gid=' + gradeV, 'POST',{
        num: executiveNumber,
        dayNum: dayNumber
    }, function (res) {
        if (res.rtnCode == "0000000") {
            $('#grade-list').html('');
            classRoomManagement.getClassRoom();
            layer.closeAll();
        } else if (res.rtnCode == '1000001') {
            layer.closeAll();
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg("出错了");
    });
});

//$(document).on('click', '#deleteRole-Btn', function () {
//    classRoomManagement.deleteClassRoom();
//});

$(document).on('click','.close-btn',function(){
    layer.closeAll();
});