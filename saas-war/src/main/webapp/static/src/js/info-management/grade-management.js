var tnId = Common.cookie.getCookie('tnId');

function GradeManagement() {
    this.init();
}
GradeManagement.prototype = {
    constructor: GradeManagement,
    init: function () {
        this.getGrade();
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/grade/getGrade.do', 'GET', {}, function (res1) {
            if (res1.rtnCode == "0000000") {
                //获取班级类型
                Common.ajaxFun('/grade/getGradeTypeDict.do', 'GET', {
                    'tnId': tnId
                }, function (res2) {
                    if (res2.rtnCode == "0000000") {
                        that.renderList(res1, res2);
                    }
                }, function (res) {
                    layer.msg("出错了");
                }, true);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderList: function (data1, data2) {
        if (data1.rtnCode == "0000000") {
            var gradeArr = [], yearsTpl = [], gradeTypeDict =[];
            yearsTpl.push('<select><option value="00">请选择入学年份</option>')
            for (var i = (new Date()).getFullYear(); i > (new Date()).getFullYear() - 4; i--) {
                yearsTpl.push('<option value="' + i + '">' + i + '年</option>');
            }
            yearsTpl.push('</select>');
            yearsTpl.join('')
            $.each(data1.bizData, function (i, v) {
                //存在的班级类型
                gradeTypeDict = [];
                $.each(data2.bizData, function (j, k) {
                    gradeTypeDict.push('<input type="radio" id="xzb-' + i + '-' + j + '" name="typeDict' + i + '" value="' + k.dictId + '">');
                    gradeTypeDict.push('<label for="xzb-' + i + '-' + j + '" >' + k.name + '</label>');
                });
                gradeArr.push('<tr id="grade-col-' + i + '">');
                gradeArr.push('<td class="center index" indexid="' + v.id + '">' + (i + 1) + '</td>');
                gradeArr.push('<td class="center"><span class="gradeNameItem" gradeId="' + v.id + '" gradeCode="' + v.gradeCode + '">' + v.grade + '</span></td>');
                gradeArr.push('<td class="center"><span class="gradeNameItem">' + yearsTpl + '</span></td>');
                gradeArr.push('<td class="center"><span class="gradeNameItem type-dict">' + gradeTypeDict.join("") + '</span></td>');
                gradeArr.push('</tr>');
            });
            $('#grade-list').append(gradeArr.join(''));
            $('#warm-tip').html('温馨提示：各年级入学年份、存在的班级类型一旦填写后将不能修改，请谨慎填写');

            //第二次来赋值
            $.each(data1.bizData, function (m, n) {
                if (n.year) {
                    $("#grade-col-" + m).find('td').eq(2).find('.gradeNameItem').find('select').val(n.year);  //赋值年份
                    //班级类型classType  1、行政吧|2、行政班+教学班|3、文科班+理科班
                    $("#grade-col-" + m).find('td').eq(3).find('.gradeNameItem').find('input:radio[value="' + n.classType + '"]').attr('checked', true);
                    $('#grade-manage-btn').attr('disabled',true);
                }
            });
        }
    },
    addGrade: function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal grade-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-4 control-label no-padding-right" for="grade-name"> 年级名称 </label>');
        contentHtml.push('<div class="col-sm-8">');
        contentHtml.push('<input type="text" id="grade-name" placeholder="输入年级名称" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn" >保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: title,
            offset: 'auto',
            area: ['362px', '185px'],
            content: contentHtml.join('')
        });
    },
    deleteGrade: function () {
        var that = this;
        var checkedLen = $("#grade-list :checkbox:checked").size();
        if (checkedLen == "0") {
            layer.tips('至少选择一项', $('.del-btn'));
            return false;
        }
        var selItem = [];
        $('#grade-list').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push('-' + $(this).attr('gradeid'));
            }
        });
        selItem = selItem.join('');
        selItem = selItem.substring(1, selItem.length);
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            Common.ajaxFun('/manage/grade/delete/' + selItem + '.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result = "SUCCESS") {
                        layer.closeAll();
                        $('#grade-list').html('');
                        that.getGrade();
                        $('#checkAll').prop('checked', false);
                    }
                }
            }, function (res) {
                alert("出错了");
            });
        }, function () {
            layer.closeAll();
        });
    },
    saveGradeManage: function (data) {
        Common.ajaxFun('/grade/insertGrade.do', 'POST', {'gradesStr': JSON.stringify(data)}, function (res) {
            if (res.rtnCode == "0000000") {
                res.bizData == 'true';
                $('#grade-manage-btn').attr('disabled', 'disabled');
                layer.tips('保存成功', $('#grade-manage-btn'));
            }
        }, function (res) {
            alert("出错了");
        });
    }
};
var GradeManagementIns = new GradeManagement();
$('#add-btn').on('click', function () {
    GradeManagementIns.addGrade('添加年级');
});

//保存年级管理
$('#grade-manage-btn').click(function () {
    var data = [], gradeCode, gradeId, dictId, yearVal;
    var gradeArr = ['高一年','高二年级','高三年级'];
    // gradeCode | 年级id | dictId | 年份val
    for (var i = 0; i < $('#grade-list tr').length; i++) {
        gradeCode = $("#grade-col-" + i).find('td').eq(1).find('.gradeNameItem').attr('gradecode');  //gradeCode
        gradeId = $("#grade-col-" + i).find('td').eq(1).find('.gradeNameItem').attr('gradeId');  //年级id
        dictId = $("#grade-col-" + i).find('td').eq(3).find('.gradeNameItem').find('input[name="typeDict' + i + '"]:checked').val();  //dictId
        yearVal = $("#grade-col-" + i).find('td').eq(2).find('select').val();  //年份val
        if(yearVal == 00){
            layer.msg(gradeArr[i]+'入学年份没有选择');
            return false;
        }
        if(!dictId){
            layer.msg(gradeArr[i]+'存在的班级类型没有选择');
            return false;
        }else{
            data.push(gradeCode + "|" + gradeId + "|" + dictId + "|" + yearVal)
        }
    }

    GradeManagementIns.saveGradeManage(data);
});

$('body').on('click', '.save-btn', function () {
    var gradeName = $.trim($('#grade-name').val());
    var gradeid = $(this).attr('gradeid');
    if (gradeName == "") {
        layer.tips('请填写年级名称!', $('#grade-name'));
        return false;
    }
    if (gradeName.length > 12) {
        layer.tips('年级名称最多熟入为12个字符!', $('#grade-name'));
        return false;
    }

    for (var i = 0; i < $('.gradeNameItem').length; i++) {
        var tempGradeName = $('.gradeNameItem').eq(i).text().trim();
        if (tempGradeName == gradeName) {
            layer.tips('该年级名称已经存在!', $('#grade-name'));
            return false;
        }
    }

    if (!gradeid) {
        Common.ajaxFun('/manage/grade/add/' + tnId + '.do', 'POST', {
            gradeName: gradeName
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == 'SUCCESS') {
                    $('#grade-list').html('');
                    GradeManagementIns.getGrade();
                    layer.closeAll();
                }
            }
        }, function (res) {
            alert("出错了");
        });
    } else {
        Common.ajaxFun('/manage/grade/modify/' + tnId + '/' + gradeid + '.do', 'POST', {
            gradeName: gradeName
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == 'SUCCESS') {
                    $('#grade-list').html('');
                    GradeManagementIns.getGrade();
                    layer.closeAll();
                }
            }
        }, function (res) {
            alert("出错了");
        });
    }

});
$('#modify-btn').on('click', function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    var checkV = $(".check-template :checkbox:checked").attr('gradeid');
    var gradename = $(".check-template :checkbox:checked").attr('gradename');
    if (chknum != '1') {
        layer.tips('修改只能选择一项!', that);
        return false;
    }
    GradeManagementIns.addGrade('修改年级');
    $('.save-btn').attr('gradeid', checkV);
    $('#grade-name').val(gradename);
});


$('body').on('click', '.close-btn', function () {
    layer.closeAll();
});

$('body').on('click', '.del-btn', function () {
    GradeManagementIns.deleteGrade();
});
