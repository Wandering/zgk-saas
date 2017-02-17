/**
 * Created by machengcheng on 16/11/8.
 */
var tnId = Common.cookie.getCookie('tnId');
/**
 * 班级类及其原型
 * @constructor
 */
var GLOBAL_CONSTANT = {
    cType: 'class_adm'   //type:class_adm（行政班）、class_edu（教学班）
}
function ClassManagement() {
    this.tnId = tnId;
    this.type = 'class';
    this.classOffset = 0;
    this.classRows = 30;
    this.classCount = 0;
    this.gradeName = '';
    this.pageCount = 1;
    this.columnArr = [];
}
ClassManagement.prototype = {
    constructor: ClassManagement,
    init: function () {
        this.initTable('class_adm');  //行政班初始化
        this.initTable('class_edu');  //教学班初始化
        this.chargeCheckClass();
        this.getGrade();

        this.getItem(GLOBAL_CONSTANT.cType);
    },
    //判断判断当前用户班级类型是否存在教学班
    chargeCheckClass: function () {
        Common.ajaxFun('/grade/checkClass.do', 'GET', {
            "tnId": tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                res.bizData.result == true ? $('#jx-template-download').removeClass('hide') : $('#jx-template-download').addClass('hide')
            }
        })
    },
    //初始化表头
    initTable: function (classType) {
        var that = this;
        // classType:class_adm（行政班）、class_edu（教学班）
        Common.ajaxFun('/config/retain/'+classType+'/' + tnId + '.do', 'POST', {
            'tnId': tnId
        }, function (res) {
            if(res.rtnCode === '0000000'){

            }
        }, function (res) {
            layer.msg("初始化失败");
        }, true,'true');
    },
    getItem: function () {//获取用户自定义班级表头
        var that = this;
        that.columnArr.splice(0, that.columnArr.length);
        Common.ajaxFun('/config/get/' + GLOBAL_CONSTANT.cType + '/' + tnId + '.do', 'GET', {
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
                            dataValue: k.dataValue,
                            dataUrl: k.dataUrl,
                            checkRule: k.checkRule
                        });
                    });
                    columnHtml.push('</tr>');
                    $("#class-manage-table thead").html(columnHtml.join(''));
                }
            } else {
                layer.msg(res.bizData.result);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    // getClassData: function () {//获取全部班级数据
    //     var that = this;
    //     Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.cType + '/' + tnId + '/getTenantCustomData.do', 'GET', {
    //         'tnId': tnId
    //     }, function (res) {
    //         if (res.rtnCode == "0000000") {
    //             var classDataHtml = [];
    //             var data = res.bizData.result;
    //             $.each(data, function (m, n) {
    //                 var obj = data[m];
    //                 classDataHtml.push('<tr rowid="' + obj['id'] + '">');
    //                 classDataHtml.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td>');
    //                 classDataHtml.push('<th class="center">序号</th>');
    //                 $.each(that.columnArr, function (i, k) {
    //                     var tempObj = that.columnArr[i];
    //                     var tempColumnName = tempObj.enName;
    //                     if (obj[tempColumnName]) {
    //                         classDataHtml.push('<td class="center">' + obj[tempColumnName] + '</td>');
    //                     } else {
    //                         classDataHtml.push('<td class="center">-</td>');
    //                     }
    //                 });
    //                 classDataHtml.push('</tr>');
    //             });
    //             $("#class-manage-table tbody").html(classDataHtml.join(''));
    //         } else {
    //             layer.msg(res.bizData.result);
    //         }
    //     }, function (res) {
    //         layer.msg("出错了");
    //     }, true);
    // },
    loadPage: function (offset, rows) {
        var that = this;
        this.classOffset = offset;
        this.classRows = rows;
        //var index = layer.load(2);
        //layer.load(1, {shade: [0.3,'#000']});
        Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.cType + '/' + tnId + '/getTenantCustomData.do', 'GET', {
            's': that.classOffset,
            'r': that.classRows,
            'g': that.gradeName
        }, function (res) {
            //layer.close(index);
            //layer.closeAll('loading');
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                that.showData(data);
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg("出错了");
        }, false);
    },
    showData: function (result) {
        var that = this;
        this.classCount = result.count;
        this.pageCount = Math.ceil(this.classCount / this.classRows);

        var classDataHtml = [];
        var data = result.result;
        $.each(data, function (m, n) {
            var obj = data[m];
            classDataHtml.push('<tr rowid="' + obj['id'] + '">');
            classDataHtml.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td>');
            classDataHtml.push('<th class="center">' + (m + 1) + '</th>');
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
        this.pagination();
    },
    pagination: function () {
        var that = this;
        $(".pagination").createPage({
            pageCount: Math.ceil(that.classCount / that.classRows),
            current: Math.ceil(that.classOffset / that.classRows) + 1,
            backFn: function (p) {
                $(".pagination-bar .current-page").html(p);
                that.classOffset = (p - 1) * that.classRows;
                that.loadPage(that.classOffset, that.classRows);
            }
        });
    },
    removeClass: function () {//删除某一行班级数据
        var that = this;
        var checkedLen = $("#class-manage-list input[type='checkbox']:checked").size();
        if (checkedLen == "0") {
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
            Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.cType + '/' + tnId + '/' + ids + '/remove.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result = "SUCCESS") {
                        $('#class-change-list').html('');
                        $('#checkAll').prop('checked', false);
                        layer.msg('删除成功', {time: 1000});
                        var classManagement = new ClassManagement();
                        classManagement.init();
                    }
                } else {
                    layer.msg(res.bizData.result);
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            });
        }, function () {
            layer.closeAll();
        });
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.grades;
                var gradeListHtml = [];


                if (data[0].grade) {
                    // 行政班|教学班说明：classType 1或3都为行政班  2教学班
                    var $classTypeToggle = $('#class-type-toggle').find('.tab')
                    if (data[0].classType == 2) {
                        $classTypeToggle.eq(0).removeClass('hide').addClass('active');
                        $classTypeToggle.eq(0).removeClass('hide');
                    } else if (data[0].classType == 3) {
                        $classTypeToggle.eq(0).addClass('hide');
                        $classTypeToggle.eq(1).addClass('hide');
                    } else {
                        $classTypeToggle.eq(0).addClass('hide');
                        $classTypeToggle.eq(1).addClass('hide');
                    }
                }


                $.each(data, function (i, k) {
                    if (i != 0) {
                        gradeListHtml.push('<span class="grade-item">');
                        gradeListHtml.push('<input type="radio" name="high-school" id="senior' + k.id + '" classtype="' + k.classType + '" />');
                        gradeListHtml.push('<label for="senior' + k.id + '">' + k.grade + '</label>');
                        gradeListHtml.push('</span>');
                    } else {
                        gradeListHtml.push('<span class="grade-item">');
                        gradeListHtml.push('<input type="radio" name="high-school" checked="checked" id="senior' + k.id + '"  classtype="' + k.classType + '" />');
                        gradeListHtml.push('<label for="senior' + k.id + '">' + k.grade + '</label>');
                        gradeListHtml.push('</span>');
                    }
                });
                $('#grade-level').html(gradeListHtml.join(''));
                var checkedGrade = $('input[name="high-school"]:checked').next().text();
                that.gradeName = checkedGrade;
                that.loadPage(0, that.classRows);
            } else {
                layer.msg(res.bizData.result);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    }
};

/**
 * 添加班级类及其原型
 * @param type
 * @constructor
 */
function AddClassManagement() {
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
            dataType: k.dataType,
            dataValue: k.dataValue,
            dataUrl: k.dataUrl,
            checkRule: k.checkRule
        });
    });
};
AddClassManagement.prototype.getYear = function () {
    var that = this;
    Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderYearSelect(res);
        } else {
            layer.msg(res.bizData.result);
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
    } else {
        layer.msg(data.bizData.result);
    }
};
AddClassManagement.prototype.getGrade = function () {
    var that = this;
    Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
        'tnId': tnId
    }, function (res) {
        if (res.rtnCode == "0000000") {
            that.renderGradeSelect(res);
        } else {
            layer.msg(res.bizData.result);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
};
AddClassManagement.prototype.renderGradeSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var gradeHtml = [];
        $.each(data.bizData.grades, function (i, k) {
            gradeHtml.push('<option value="' + k.grade + '">' + k.grade + '</option>');
        });
        $("#class_grade").append(gradeHtml.join(''));
        var checkedGrade = $('input[name="high-school"]:checked').next().text();
        $("#class_grade").val(checkedGrade);
        $("#class_grade").css({'cursor': 'not-allowed'});
        $("#class_grade").prop('disabled', true);
    } else {
        layer.msg(data.bizData.result);
    }
};
AddClassManagement.prototype.getType = function (type) {
    var that = this;
    var typeHtml = [];
    var types = null;
    $.each(this.columnArr, function (i, k) {
        if (k.enName == type) {
            types = k.dataValue.split('-');
        }
    });
    if (types != null) {
        $.each(types, function (i, k) {
            typeHtml.push('<option value="' + k + '">' + k + '</option>');
        });
    }
    $('#' + type).append(typeHtml.join(''));
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
    $.each(classManagement.columnArr, function (i, k) {
        if (k.dataType != 'select') {
            if (k.enName != 'class_boss') {
                contentHtml.push('<li><i>*</i><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
            } else {
                contentHtml.push('<li><i>*</i><span style="letter-spacing: 6px;">' + k.name + '</span><input type="text" style="position: relative;left: -6px;" id="' + k.enName + '" /></li>');
            }
        } else {
            contentHtml.push('<li><i>*</i><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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

    $.each(classManagement.columnArr, function (i, k) {
        if (k.dataType == 'select') {
            if (k.dataValue) {
                that.getType(k.enName);
            }
            if (k.dataUrl) {

            }
        }
    });
};

/**
 * 更新班级类及其原型
 * @param type
 * @constructor
 */
function UpdateClassManagement() {
    ClassManagement.call(this);
}
UpdateClassManagement.prototype = {
    constructor: UpdateClassManagement,
    init: function (columnArr) {
        var that = this;
        $.each(columnArr, function (i, k) {
            that.columnArr.push({
                name: k.name,
                enName: k.enName,
                dataType: k.dataType,
                dataValue: k.dataValue,
                dataUrl: k.dataUrl,
                checkRule: k.checkRule
            });
        });
    },
    getYear: function () {
        var that = this;
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderYearSelect(res);
            } else {
                layer.msg(res.bizData.result);
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
        } else {
            layer.msg(data.bizData.result);
        }
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderGradeSelect(res);
            } else {
                layer.msg(res.bizData.result);
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
            $("#class_grade").append(gradeHtml.join(''));
            var checkedGrade = $('input[name="high-school"]:checked').next().text();
            $("#class_grade").val(checkedGrade);
            $("#class_grade").css({'cursor': 'not-allowed'});
            $("#class_grade").prop('disabled', true);
        } else {
            layer.msg(data.bizData.result);
        }
    },
    getType: function (type) {
        var that = this;
        var typeHtml = [];
        var types = null;
        $.each(this.columnArr, function (i, k) {
            if (k.enName == type) {
                types = k.dataValue.split('-');
            }
        });
        if (types != null) {
            $.each(types, function (i, k) {
                typeHtml.push('<option value="' + k + '">' + k + '</option>');
            });
        }
        $('#' + type).append(typeHtml.join(''));
    },
    updateClass: function (title) {//更新某一行班级数据
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="add-class-box">');
        contentHtml.push('<ul>');
        $.each(classManagement.columnArr, function (i, k) {
            if (k.dataType != 'select') {
                if (k.enName != 'class_boss') {
                    contentHtml.push('<li><i>*</i><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
                } else {
                    contentHtml.push('<li><i>*</i><span style="letter-spacing: 6px;">' + k.name + '</span><input type="text" style="position: relative;left: -6px;" id="' + k.enName + '" /></li>');
                }
            } else {
                contentHtml.push('<li><i>*</i><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.name + '</option></select></li>');
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
        that.getYear();
        that.getGrade();
        $.each(classManagement.columnArr, function (i, k) {
            if (k.dataType == 'select') {
                if (k.dataValue) {
                    that.getType(k.enName);
                }
                if (k.dataUrl) {

                }
            }
        });
        var rowid = $(".check-template :checkbox:checked").attr('cid');
        var rowItem = $('#class-manage-list tr[rowid="' + rowid + '"]').find('td');
        $.each(classManagement.columnArr, function (i, k) {
            if (rowItem.eq(i + 1).html() != '-') {
                $('#' + k.enName).val(rowItem.eq(i + 1).html());
            }
        });
    }
};

//上传数据类及其原型
function UploadData() {

}
UploadData.prototype = {
    constructor: UploadData,
    init: function () {

    },
    showUploadBox: function (title,hideOrShow) {
        var that = this;
        var uploadDataHtml = [];
        uploadDataHtml.push('<div class="upload-box">');
        uploadDataHtml.push('<span id="uploader-demo">');

        uploadDataHtml.push('<span id="fileList" style="display: none;" class="uploader-list"></span>');
        uploadDataHtml.push('<button class="btn btn-info btn-import" id="xz-btn-import">导入行政班学生数据Excel</button>');
        uploadDataHtml.push('</span>');

        uploadDataHtml.push('<span id="uploader-demo">');
        uploadDataHtml.push('<span id="fileList" style="display: none;" class="uploader-list"></span>');
        uploadDataHtml.push('<button class="btn btn-info btn-import' + " " + hideOrShow + '" id="jx-btn-import">导入教学班学生数据Excel</button>');
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
        upload('class_adm');
        upload('class_edu');
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

var updateClassManagement = new UpdateClassManagement();
//更新班级按钮操作
$(document).on("click", "#updateRole-btn", function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    if (chknum != '1') {
        layer.tips('修改只能选择一项!', that, {time: 1000});
        return false;
    }
    updateClassManagement = new UpdateClassManagement();
    updateClassManagement.init(classManagement.columnArr);
    updateClassManagement.updateClass('更新班级');
});


//切换班级管理
$(document).on('change', 'input[name="high-school"]', function () {
    $('#checkAll').prop('checked', false);
    var checkedGrade = $('input[name="high-school"]:checked').next().text();
    GLOBAL_CONSTANT.cType = 'class_adm';

    // 行政班|教学班说明：classType 1或3都为行政班  2教学班
    var $classTypeToggle = $('#class-type-toggle').find('.tab');
    $classTypeToggle.eq(1).removeClass('active');

    if ($(this).attr('classType') == 2) {
        $classTypeToggle.eq(0).removeClass('hide').addClass('active');
        $classTypeToggle.eq(1).removeClass('hide');
    } else if ($(this).attr('classType') == 3) {
        $classTypeToggle.eq(0).addClass('hide');
        $classTypeToggle.eq(1).addClass('hide');
    } else {
        $classTypeToggle.eq(0).addClass('hide');
        $classTypeToggle.eq(1).addClass('hide');
    }


    classManagement.gradeName = checkedGrade;
    classManagement.loadPage(0, classManagement.classRows);
});


//确认更新操作按钮
$(document).on("click", "#update-btn", function () {
    var postData = [];
    for (var i = 0; i < updateClassManagement.columnArr.length; i++) {
        var tempObj = addClassManagement.columnArr[i];
        //if (tempObj.dataType == 'text') {
        //    if ($('#' + tempObj.enName).val() == '') {
        //        layer.msg(tempObj.name + '不能为空!', {time: 1000});
        //        $('#' + tempObj.enName).focus();
        //        return;
        //    } else {
        //        postData.push({
        //            "key": tempObj.enName,
        //            "value": $('#' + tempObj.enName).val()
        //        });
        //    }
        //}
        if (tempObj.dataType == 'text') {
            //alert('text: ' + eval(tempObj.checkRule) + ', ' + $('#' + tempObj.enName).val());
            if (tempObj.checkRule) {
                var tempType = eval(tempObj.checkRule);
                var typeResult = tempType.test($('#' + tempObj.enName).val());
                if (typeResult === false) {
                    layer.msg(tempObj.name + '长度为1~12个字符!', {time: 1000});
                    $('#' + tempObj.enName).focus();
                    return;
                }
            }

            postData.push({
                "key": tempObj.enName,
                "value": $('#' + tempObj.enName).val()
            });

            //if ($('#' + tempObj.enName).val() == '') {
            //    layer.msg(tempObj.name + '不能为空!', {time: 1000});
            //    $('#' + tempObj.enName).focus();
            //    return;
            //} else {
            //    postData.push({
            //        "key": tempObj.enName,
            //        "value": $('#' + tempObj.enName).val()
            //    });
            //}
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
            "type": GLOBAL_CONSTANT.cType,
            "tnId": tnId,
            "pri": rowid,//记录的id
            "teantCustomList": postData
        }
    };
    Common.ajaxFun('/manage/teant/custom/data/modify.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            var checkedGrade = $('input[name="high-school"]:checked').next().text();
            classManagement.gradeName = checkedGrade;
            classManagement.loadPage(0, classManagement.classRows);
        } else {
            layer.msg(res.bizData.result);
        }
    }, function (res) {
        layer.msg("出错了");
    }, null, true);
});

//确认添加操作按钮
$(document).on("click", "#add-btn", function () {
    var postData = [];
    for (var i = 0; i < classManagement.columnArr.length; i++) {
        var tempObj = classManagement.columnArr[i];
        if (tempObj.dataType == 'text') {
            //alert('text: ' + eval(tempObj.checkRule) + ', ' + $('#' + tempObj.enName).val());
            if (tempObj.checkRule) {
                var tempType = eval(tempObj.checkRule);
                var typeResult = tempType.test($('#' + tempObj.enName).val());
                if (typeResult === false) {
                    layer.msg(tempObj.name + '长度为1~12个字符!', {time: 1000});
                    $('#' + tempObj.enName).focus();
                    return;
                }
            }

            postData.push({
                "key": tempObj.enName,
                "value": $('#' + tempObj.enName).val()
            });

            //if ($('#' + tempObj.enName).val() == '') {
            //    layer.msg(tempObj.name + '不能为空!', {time: 1000});
            //    $('#' + tempObj.enName).focus();
            //    return;
            //} else {
            //    postData.push({
            //        "key": tempObj.enName,
            //        "value": $('#' + tempObj.enName).val()
            //    });
            //}
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
            "type": GLOBAL_CONSTANT.cType,
            "tnId": tnId,
            "teantCustomList": postData
        }
    };

    Common.ajaxFun('/manage/teant/custom/data/add.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            var checkedGrade = $('input[name="high-school"]:checked').next().text();
            classManagement.gradeName = checkedGrade;
            classManagement.loadPage(0, classManagement.classRows);
        } else {
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg("出错了");
    }, null, true);
});
//取消操作按钮(关闭对话框)
$(document).on("click", ".cancel-btn", function () {
    layer.closeAll();
});

$(document).on('click', '#deleteClassBtn', function () {
    classManagement.removeClass();
});




/**
 * 模板下载
 * 行政班模板|教学班模板
 */
$(document).on('click', '#xz-template-download', function () {
    window.location.href = '/manage/class_adm/export/' + tnId + '.do';
});
$(document).on('click', '#jx-template-download', function () {
    window.location.href = '/manage/class_edu/export/' + tnId + '.do';
});




/**
 * 模板上传
 * 行政班模板|教学班模板
 */
$(document).on('click', '#uploadBtn', function () {
    var hideOrShow = 'hide';
    var upload = new UploadData();
    if ($('#jx-template-download').is(":visible")) {
        hideOrShow = '';
    }
    upload.showUploadBox('导入班级数据',hideOrShow);
});


//
// $(document).on('click', '#xz-btn-import', function () {
//     upload('class_adm');
// });
// $(document).on('click', '#jx-btn-import', function () {
//     upload('class_edu');
// });




//班级设置操作
$(document).on('click', '#class-settings-btn', function () {
    layer.open({
        type: 2,
        title: '班级设置',
        shadeClose: false, //点击页面背景是否关闭窗口
        shade: 0.8,
        area: ['60%', '70%'],
        content: '/class-settings',
        cancel: function () {
            //var classManagement = new ClassManagement();
            //classManagement.init();
            history.go(0);
        }
    });
});

//新增行政班和教学班切换
$(document).on('click', '#class-type-toggle .tab', function () {
    $(this).addClass('active').siblings().removeClass('active');
    GLOBAL_CONSTANT.cType = $(this).attr('type');
    classManagement.getItem();  //拉取table-head
    classManagement.loadPage(classManagement.classOffset, classManagement.classRows);     //拉取table-body
    classManagement.pagination();  //分页
});


function upload(whichBtn) {
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
        server: rootPath + '/config/upload/'+whichBtn+'/' + tnId + '.do',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        // pick: '#btn-import',
        pick: whichBtn == 'class_adm' ? '#xz-btn-import' : '#jx-btn-import',

        // 只允许选择文件，可选。
        accept: {
            title: 'excel',
            extensions: 'xls',
            mimeTypes: 'application/vnd.ms-excel'
        },
        fileVal: 'inputFile',
        duplicate: new Date()

    });

    // 当有文件添加进来的时候
    uploader.on('fileQueued', function (file) {
        //return;
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
    });

    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function (file, response) {
        if (classManagement != null) {
            var checkedGrade = $('input[name="high-school"]:checked').next().text();
            classManagement.gradeName = checkedGrade;
            classManagement.loadPage(0, classManagement.classRows);
        }
        if (response.bizData.result) {
            if (response.bizData.result == 'SUCCESS') {
                layer.msg('上传成功');
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