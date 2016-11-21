/*
 * @module:学生管理模块
 * @author:pdeng
 * @time:2016-11-9
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436387
 * */

/**
 * 全局常量
 * @type {{tnId: *, typ: string}}
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    type: 'student',   //角色
}
//function proInherit(obj){
//    function F () {}
//    F.prototype = obj;
//    return new F();
//}

/**
 * 学生管理初始化
 * @type {{init: App.init, tableHeader: App.tableHeader, tableBody: App.tableBody}}
 */
var App = {
    init: function () {
        this.tableData = [];
        this.renderTableHeader();
    },
    renderTableHeader: function () {
        Common.ajaxFun('/config/get/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                if (data.length != 0) {
                    var tpl = [];
                    App.tableData = [] //制空
                    tpl.push('<tr>');
                    tpl.push('<th class="center"><label><input type="checkbox" id="check-all" class="ace" /><span class="lbl"></span></label></th>');
                    $.each(data, function (i, k) {
                        tpl.push('<th class="center">' + k.name + '</th>');
                        App.tableData.push({
                            name: k.name,
                            enName: k.enName,
                            dataType: k.dataType,
                            dataValue: k.dataValue,
                            checkRule: k.checkRule,
                        });
                    });
                    tpl.push('</tr>');
                    $("#student-table thead").html(tpl.join(''));
                    App.renderTableBody(data);
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderTableBody: function (data) {
        var that = this
        Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
                var dataJson = res.bizData.result;
                $.each(dataJson, function (m, n) {
                    var obj = dataJson[m];
                    tpl.push('<tr>');
                    tpl.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td>');
                    $.each(App.tableData, function (i, k) {
                        var enName = App.tableData[i].enName;
                        if (obj[enName]) {
                            tpl.push('<td class="center">' + obj[enName] + '</td>');
                        } else {
                            tpl.push('<td class="center">-</td>');
                        }
                    });
                    tpl.push('</tr>');
                });
                $("#student-table tbody").html(tpl.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    }
}
App.init();


/**
 * 添加学生模块
 * @type {{init: AddStd.init, fetchGrade: AddStd.fetchGrade, fetchEntranceYear: AddStd.fetchEntranceYear, fetchBelongClass: AddStd.fetchBelongClass, renderElement: AddStd.renderElement, bindEvents: AddStd.bindEvents, removeStd: AddStd.removeStd, addStdVerify: AddStd.addStdVerify, addStdEvent: AddStd.addStdEvent}}
 */
var AddStd = {
    init: function () {
        this.bindEvents();
        this.addStdData = {
            renderEleData: [],
            gradeData: '',
            yearData: '',
            classData: ''
        }
        $(document).on('click','#close-btn',function(){
            window.location.reload();
        })
    },
    //所属年级
    fetchGrade: function () {
        Common.ajaxFun('/config/grade/get/' + GLOBAL_CONSTANT.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = '';
                $.each(res.bizData.grades, function (i, v) {
                    tpl += v.grade + '-'
                })
                tpl = tpl.substr(0, tpl.length - 1);
                AddStd.addStdData.gradeData = tpl;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    //入学年份
    fetchEntranceYear: function () {
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = '';
                for (var i in res.bizData.result) {
                    tpl += res.bizData.result[i] + '-'
                }
                tpl = tpl.substr(0, tpl.length - 1);
                AddStd.addStdData.yearData = tpl;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    //所属班级
    fetchBelongClass: function () {
        Common.ajaxFun('/manage/class/' + GLOBAL_CONSTANT.tnId + '/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = '';
                $.each(res.bizData.result, function (i, v) {
                    tpl += v.class_name + '-'
                })
                tpl = tpl.substr(0, tpl.length - 1);
                AddStd.addStdData.classData = tpl;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    //渲染元素
    renderElement: function () {
        var eleData = {}, templateEle = '';
        $.each(App.tableData, function (i, v) {
            if (!v.dataValue) {
                switch (v.enName) {
                    case 'student_grade':  //所在年级
                        AddStd.fetchGrade();
                        v.dataValue = AddStd.addStdData.gradeData;
                        break;
                    case 'student_class_in_year':  //入学年份
                        AddStd.fetchEntranceYear();
                        v.dataValue = AddStd.addStdData.yearData;
                        break;
                    case 'student_class':      //所在班级
                        AddStd.fetchBelongClass();
                        v.dataValue = AddStd.addStdData.classData;
                        break;
                }
            }
            eleData = {
                name: v.name,
                enName: v.enName,
                dataType: v.dataType,
                dataValue: v.dataValue,
                checkRule: v.checkRule,
                checkRule: v.checkRule,
            }
            AddStd.addStdData.renderEleData.push(eleData);
        })
        /**
         * 渲染Radio
         * @param v
         * @returns {string}
         */
        var renderRadio = function (v) {
            var radioLen = (v.dataValue).split('-'),
                radioTpl = '';
            for (var i = 0; i < radioLen.length; i++) {
                if (i == 0) {
                    radioTpl += '<label><input name="form-field-radio" type="radio" class="ace" value="' + radioLen[i] + '" checked="checked">' +
                        '<span class="lbl">' + radioLen[i] + '</span></label>'
                } else {
                    radioTpl += '<label><input name="form-field-radio" type="radio" class="ace" value="' + radioLen[i] + '">' +
                        '<span class="lbl">' + radioLen[i] + '</span></label>'
                }

            }
            return '<li><span class="f20">' + v.name + '</span>' +
                '<div id="' + v.enName + '" class="sex-type f70">' + radioTpl +
                '</label>' +
                '</div></li>'
        }
        /**
         * 渲染CheckBox
         * @param v
         * @returns {string}
         */
        var renderCheckBox = function (v) {
            var checkBoxLen = (v.dataValue).split('-'),
                checkBoxTpl = '';
            for (var i = 0; i < checkBoxLen.length; i++) {
                if (i == 0) {
                    checkBoxTpl += '<label>' +
                        '<input type="checkbox" class="ace" value="' + checkBoxLen[i] + '" checked="checked" name="ck">' +
                        '<span class="lbl">' + checkBoxLen[i] + '</span>' +
                        '</label>';
                } else {
                    checkBoxTpl += '<label>' +
                        '<input type="checkbox" class="ace" value="' + checkBoxLen[i] + '" name="ck">' +
                        '<span class="lbl">' + checkBoxLen[i] + '</span>' +
                        '</label>';
                }

            }
            return '<li><span class="f20">' + v.name + '</span><div id="' + v.enName + '" class="subject-list f70">' + checkBoxTpl + '</div></li>'
        }
        /**
         * 渲染Select
         * @param v
         * @returns {string}
         */
        var renderSelect = function (v) {
            var selectLen = (v.dataValue).split('-'),
                selectTpl = '';
            for (var i = 0; i < selectLen.length; i++) {
                selectTpl += '<option>' + selectLen[i] + '</option>'
            }
            return '<li><span>' + v.name + '</span><select id="' + v.enName + '">' + selectTpl + '</select></li>'
        }
        /**
         * 渲染Text
         * @param v
         * @returns {string}
         */
        var renderText = function (v) {
            return '<li><span>' + v.name + '</span><input type="text" placeholder="请输入' + v.name + '" id="' + v.enName + '" checkRule="' + v.checkRule + '" class="input-common-w"/></li>'
        }
        $.each(AddStd.addStdData.renderEleData, function (i, v) {
            switch (v.dataType) {
                case 'radio':
                    templateEle += renderRadio(v);
                    break;
                case 'checkbox':
                    templateEle += renderCheckBox(v);
                    break;
                case 'select':
                    templateEle += renderSelect(v);
                    break;
                case 'text':
                    templateEle += renderText(v);
                    break;
            }
        })
        templateEle += '<div class="opt-btn-box">' +
            '<button class="btn btn-info save-btn" id="add-btn">确认添加</button>' +
            '<button class="btn btn-primary close-btn" id="close-btn">取消</button>' +
            '</div>';
        AddStd.addStdData.renderEleData = [] //制空
        $('#student-add-box').html(templateEle);
    },
    bindEvents: function () {
        var that = this;
        //添加|更新  弹框
        $(document).on('click', '#student-add,#student-modify', function () {
            var type = $(this).attr('type');
            that.renderElement()
            if(type === 'add'){   //添加
                layer.open({
                    type: 1,
                    title: '添加学生',
                    offset: 'auto',
                    area: ['425px', 'auto'],
                    content: $('#student-add-layer').html(),
                    cancel: function () {
                        layer.closeAll();
                        window.location.reload();
                    }
                })
            }else{    //更新
                var checkboxN = $("#student-table :checkbox:checked").size();
                if (checkboxN != '1') {
                    layer.tips('修改只能选择一项!', $('#student-modify'), {time: 1000});
                    return false;
                }
                $('#add-btn').html('确认修改');
                layer.open({
                    type: 1,
                    title: '修改学生',
                    offset: 'auto',
                    area: ['425px', 'auto'],
                    content: $('#student-add-layer').html(),
                    cancel: function () {
                        layer.closeAll();
                        window.location.reload();
                    }
                })
            }
            $('#student-add-layer').remove();
        })
        //确认添加
        $(document).on('click', '#add-btn', function () {
            that.addStdEvent(that.addStdVerify());
        })
        //删除
        $(document).on('click', '#student-remove', function () {
            AddStd.removeStd();
        });
        //更新
        $(document).on("click", "#add-btn", function () {

            //that.updateStd(checkboxN);
            //updateClassManagement = new UpdateClassManagement();
            //updateClassManagement.init(classManagement.columnArr);
            //updateClassManagement.updateClass('更新班级');
        })
    },
    //更新
    updateStd:function(num){

    },
    //删除一行记录
    removeStd: function () {
        var that = this;
        var checkedLen = $("#student-table input[type='checkbox']:checked").size();
        if (checkedLen == "0") {
            layer.tips('至少选择一项', $('#student-remove'));
            return false;
        }
        var selItem = [];
        $('#student-table').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push($(this).attr('cid'));
            }
        });
        selItem = selItem.join('-');
        var ids = selItem;
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '/' + ids + '/remove.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result = "SUCCESS") {
                        $('#class-change-list').html('');
                        $('#check-all').prop('checked', false);
                        layer.msg('删除成功', {time: 1000});
                        App.renderTableHeader();
                    }
                }
            }, function (res) {
                layer.msg("出错了", {time: 1000});
            });
        }, function () {
            layer.closeAll();
        });
    },
    addStdVerify: function () {
        var postData = [];
        (function () {
            var lock = 0;
            $.each(App.tableData, function (i, v) {
                if (v.dataType === "text") {
                    if ($('#' + v.enName).val() == '') {
                        layer.msg(v.name + '不能为空', {time: 1000});
                        $('#' + v.enName).focus();
                        lock = 1;
                        return false
                    }
                    //if (!($('#' + v.enName).val()).match(v.checkRule)) {
                    //    layer.msg(v.name + '输入不合法', {time: 1000});
                    //    $('#' + v.enName).focus();
                    //    lock = 1;
                    //    return false
                    //}
                }
            })
            return false
        })()
        $.each(App.tableData, function (i, v) {
            if (v.dataType === "text" || v.dataType === "select") {
                postData.push({
                    "key": v.enName,
                    "value": $.trim($('#' + v.enName).val())
                });
            } else if (v.dataType === "radio") {
                postData.push({
                    "key": v.enName,
                    "value": $('#' + v.enName).find('[name="form-field-radio"]:checked').val()
                });
            } else if (v.dataType === "checkbox") {
                postData.push({
                    "key": v.enName,
                    "value": $('#' + v.enName).find('[name="ck"]:checked').val()
                });
            }
        })
        console.info(postData);
        return {
            "clientInfo": {},
            "style": "",
            "data": {
                "type": GLOBAL_CONSTANT.type,
                "tnId": GLOBAL_CONSTANT.tnId,
                "teantCustomList": postData
            }
        };
    },
    addStdEvent: function (data) {
        Common.ajaxFun('/manage/teant/custom/data/add.do', 'POST', JSON.stringify(data), function (res) {
            if (res.rtnCode == "0000000" && res.bizData.result === 'SUCCESS') {
                layer.closeAll();
                window.location.reload() //layer-bug 待解决
                //App.renderTableHeader();
            }
        }, function (res) {
            layer.msg("出错了");
        }, null, true);
    }
}
AddStd.init();



























//修改
var StudentModify = {}

//删除
var StudentRemove = {}

//模板上传
var StudentTemplateDown = {}

//模板上传
var StudentUpload = {}


/*
 * 学生设置
 * */
var StudentSetting = {
    init: function () {
        this.eventStudentSetting();
        this.removeTableHeaderField();//删除设置的表头
    },
    eventStudentSetting: function () {
        var that = this;
        $(document).on('click', '#student-setting', function () {
            that.fetchTableHeader();
            that.eventAdd();
            that.saveAddTableHeaderField();
            layer.full(
                layer.open({
                    type: 1,
                    content: $("#sub-student-setting").html(),
                    area: ['100%', '100%'],
                    maxmin: false
                })
            )
        });
    },
    eventAdd: function () {
        var that = this;
        $(document).on('click', '#sub-student-add', function () {
            that.generateTableHeader();
            layer.open({
                type: 1,
                title: '选择添加字段',
                content: $('#sub-choose-field'),
                area: ['550px', '200px'],
                cancel: function () {
                    $('#field').html('');
                }
            })
        });
    },
    fetchTableHeader: function () {
        Common.ajaxFun('/config/get/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentSetting.renderHeader(res)
                StudentSetting.fetchTableHeaderData = res;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderHeader: function (res) {
        var dataJson = res.bizData.configList;
        var template = Handlebars.compile($('#sub-student-table-tpl').html());
        $('#sub-student-table').html(template(dataJson));
    },
    /*
     * 添加表头字段
     * (获取所有字段名称)
     * */
    generateTableHeader: function () {
        Common.ajaxFun('/config/getInit/' + GLOBAL_CONSTANT.type + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
                $('#field').html('');
                $.each(res.bizData.configList, function (i, v) {
                    tpl.push('<label for="li-' + v.id + '">');
                    var isCheck = false;
                    $.each(StudentSetting.fetchTableHeaderData.bizData.configList, function (j, k) {
                        if (v.id == k.configKey) {
                            tpl.push('<input type="checkbox" class="ace" id="li-' + v.id + '" checked="checked">');
                            isCheck = true;
                            return false;
                        }
                    })
                    if (!isCheck) {
                        tpl.push('<input type="checkbox" class="ace" id="li-' + v.id + '">');
                    }
                    tpl.push('<span class="lbl">' + v.chName + '</span></label>');
                })
                $('#field').append(tpl.join(''));
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    /*
     * 保存已选择的添加字段
     * */
    saveAddTableHeaderField: function () {
        var that = this;
        $(document).on('click', '#btn-choose', function () {
            var tplArr = [];
            var $fidldLen = $('#field').find('[type="checkbox"]').length;
            for (var i = 0; i < $fidldLen; i++) {
                if ($('#field').find('[type="checkbox"]').eq(i).prop('checked') == true) {
                    tplArr.push($('#field').find('[type="checkbox"]').eq(i).attr('id').split('-')[1])
                }
            }
            Common.ajaxFun('/manage/import/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do', 'POST', {
                ids: tplArr.join('-')
            }, function (res) {
                if (res.rtnCode == "0000000") {
                    that.fetchTableHeader();
                    layer.closeAll();
                    layer.full(
                        layer.open({
                            type: 1,
                            content: $("#sub-student-setting").html(),
                            area: ['100%', '100%'],
                            maxmin: false,
                            cancel: function () {
                                $('#field').html(''); //清空
                                window.location.reload();
                            }
                        })
                    )
                }
            }, function (res) {
                layer.msg("出错了");
            }, true);
        })
    },
    /*
     *
     * 删除设置的表头字段
     * */
    removeTableHeaderField: function () {
        var that = this;
        var idsData = null;
        $(document).on('click', '.student-setting-remove-head', function () {
            idsData = $(this).attr('data-id');
            removeField(idsData)
        })
        $(document).on('click', '#sub-student-remove', function () {
            idsData = $(this).attr('data-id');
            removeField(idsData)
        })
        var removeField = function (idsData) {
            Common.ajaxFun('/manage/tenant/remove/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '/' + idsData + '.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    that.fetchTableHeader();
                    that.eventAdd();
                    that.saveAddTableHeaderField();
                    layer.closeAll();
                    layer.full(
                        layer.open({
                            type: 1,
                            content: $("#sub-student-setting").html(),
                            area: ['100%', '100%'],
                            maxmin: false,
                            cancel: function () {
                                StudentManage.studentTable();
                            }
                        })
                    )
                }
            }, function (res) {
                layer.msg("出错了");
            }, true);
        }
    },
    tableDrag: function () {
        // 表格排序
        var fixHelperModified = function (e, tr) {
                var $originals = tr.children();
                var $helper = tr.clone();
                $helper.children().each(function (index) {
                    $(this).width($originals.eq(index).width())
                });
                return $helper;
            },
            updateIndex = function (e, ui) {
                var ids = [];
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 1);
                    ids.push("-" + $(this).attr('indexId'));
                });
                ids = ids.join('');
                ids = ids.substring(1, ids.length);
                Common.ajaxFun('/config/sort/class/' + ids + '.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result == "SUCCESS") {
                            layer.msg('排序成功');
                        } else {
                            layer.msg(res.bizData.result);
                        }
                    }
                }, function (res) {
                    alert("出错了");
                });
            };
        $("#class-template").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y"
        }).disableSelection();
    }


}
StudentSetting.init();



