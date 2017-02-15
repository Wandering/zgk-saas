/*
 * @module:学生管理模块
 * @author:pdeng
 * @time:2016-11-9
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436387
 * */
//layer.load(1, {shade: [0.3,'#000']});
/**
 * 全局常量
 * @type {{tnId: *, typ: string}}
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    type: 'student',   //角色
    cType: 1   //classType默认都为1-行政班管理
}


/**
 * 学生管理初始化
 * @type {{init: App.init, tableHeader: App.tableHeader, tableBody: App.tableBody}}
 */
var App = {
    init: function () {
        this.tableData = [];
        this.checkGradeName = '';
        this.page = {
            'offset': 0,
            'rows': 30,
            'count': ''
        }
        this.fetchGrade();
        this.renderTableHeader();
        this.addEvent();
    },
    renderTableHeader: function () {
        Common.ajaxFun('/student/getStuExcelHeader.do', 'GET', {
            'type':GLOBAL_CONSTANT.cType,
            'tnId':GLOBAL_CONSTANT.tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length == 0) {
                    App.layerAlertInit();
                    return false;
                }
                if (data.length !== 0) {
                    var tpl = [];
                    App.tableData = [] //制空
                    tpl.push('<tr>');
                    tpl.push('<th class="center"><label><input type="checkbox" id="stdCheckAll" class="ace" /><span class="lbl"></span></label></th><th>序号</th>');
                    $.each(data, function (i, k) {
                        tpl.push('<th class="center">' + k.name + '</th>');
                        App.tableData.push({
                            name: k.name,
                            enName: k.enName,
                            dataType: k.dataType,
                            dataValue: k.dataValue,
                            checkRule: k.checkRule,
                            isRetain: k.isRetain,
                            id: k.id,
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
        // 行政班|教学班说明：classType 1或3都为行政班  2教学班
        Common.ajaxFun('/student/getStuInfo.do', 'GET', {
            'star': App.page.offset,
            'row': App.page.rows,
            'grade': App.checkGradeName,
            'type': GLOBAL_CONSTANT.cType,
            'tnId': GLOBAL_CONSTANT.tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
                var dataJson = res.bizData.result;
                App.page.count = res.bizData.count;
                $.each(dataJson, function (m, n) {
                    var obj = dataJson[m];
                    tpl.push('<tr>');
                    tpl.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td><td>' + parseInt(m + 1) + '</td>');
                    $.each(App.tableData, function (i, k) {
                        var enName = App.tableData[i].enName;
                        var dType = App.tableData[i].dataType;
                        if (obj[enName]) {
                            tpl.push('<td class="center" iName="' + enName + '" dataType="' + dType + '" pid="' + n.id + '">' + obj[enName] + '</td>');
                        } else {
                            tpl.push('<td class="center">-</td>');
                        }
                    });
                    tpl.push('</tr>');
                });
                $("#student-table tbody").html(tpl.join(''));
                that.pagination();
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    layerAlertInit: function () {
        var that = this;
        layer.open({
            type: 1,
            closeBtn: 0,
            title: '请初始化学生',
            shade: [0.8, '#000'],
            shadeClose: false,
            scrollbar: false,
            content: '<div class="init-page-layer"><button class="btn btn-info" id="init-btn">初始化学生数据</button></div>'
        });
        $(document).on('click', '#init-btn', function () {
            Common.ajaxFun('/config/retain/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do', 'POST', {},
                function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result == true) {
                            layer.msg('学生初始化成功');
                            layer.closeAll();
                            that.renderTableHeader();
                        } else {
                            layer.msg('学生初始化失败');
                        }
                    }
                }, function (res) {
                    layer.msg(res.msg);
                });
        })
    },
    //动态渲染所属年级
    fetchGrade: function () {
        Common.ajaxFun('/config/grade/get/' + GLOBAL_CONSTANT.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var dataJson = res.bizData.grades;
                //初始页面table-header渲染
                var template = Handlebars.compile($('#grade-list-tpl').html());
                $('#grade-list').html(template(dataJson));
                if (dataJson[0].grade) {

                    // 行政班|教学班说明：classType 1或3都为行政班  2教学班
                    var $classTypeToggle = $('#class-type-toggle').find('.tab')
                    if (dataJson[0].classType == 2) {
                        $classTypeToggle.eq(0).removeClass('hide').addClass('active');
                        $classTypeToggle.eq(0).removeClass('hide');
                    } else if(dataJson[0].classType == 3){
                        $classTypeToggle.eq(0).addClass('hide');
                        $classTypeToggle.eq(1).addClass('hide');
                    }else{
                        $classTypeToggle.eq(0).addClass('active');
                        $classTypeToggle.eq(1).addClass('hide');
                    }
                    App.checkGradeName = dataJson[0].grade;

                }
            }
            setTimeout(function () {
                layer.closeAll('loading');
            }, 500);
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    loadPage: function () {
        var that = this;
        //layer.load(1, {shade: [0.3,'#000']});
        Common.ajaxFun('/student/getStuInfo.do', 'GET', {
            'star': App.page.offset,
            'row': App.page.rows,
            'grade': App.checkGradeName,
            'type': GLOBAL_CONSTANT.cType,
            'tnId': GLOBAL_CONSTANT.tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
                var dataJson = res.bizData.result;
                App.page.count = res.bizData.count;
                $.each(dataJson, function (m, n) {
                    var obj = dataJson[m];
                    tpl.push('<tr>');
                    tpl.push('<td class="center"><label><input type="checkbox" cid="' + obj['id'] + '" class="ace" /><span class="lbl"></span></label></td><th>' + parseInt(m + 1) + '</th>');
                    $.each(App.tableData, function (i, k) {
                        var enName = App.tableData[i].enName;
                        var dType = App.tableData[i].dataType;
                        if (obj[enName]) {
                            tpl.push('<td class="center" iName="' + enName + '" dataType="' + dType + '" pid="' + n.id + '">' + obj[enName] + '</td>');
                        } else {
                            tpl.push('<td class="center">-</td>');
                        }
                    });
                    tpl.push('</tr>');
                });
                $("#student-table tbody").html(tpl.join(''));
                that.pagination();
            }
            //layer.closeAll('loading');
        }, function (res) {
            layer.msg("出错了");
        }, false);
    },
    pagination: function () {
        var that = this;
        $(".pagination").createPage({
            pageCount: Math.ceil(App.page.count / App.page.rows),
            current: Math.ceil(App.page.offset / App.page.rows) + 1,
            backFn: function (p) {
                $(".pagination-bar .current-page").html(p);
                App.page.offset = (p - 1) * App.page.rows;
                App.loadPage();
            }
        });
    },
    addEvent: function () {
        var targetDom = $('#grade-list input');
        $(document).on('click', '.grade-list input[name="grade-li"]', function () {
            App.page = {
                'offset': 0,
                'rows': 30,
                'count': ''
            }
            var $checkedInputDom = $('input[name="grade-li"]:checked');
            App.checkGradeName = $checkedInputDom.next().text();
            $('#class-type-toggle').find('.tab').eq(1).removeClass('active');


            // 行政班|教学班说明：classType 1或3都为行政班  2教学班
            var $classTypeToggle = $('#class-type-toggle').find('.tab')
            if ($checkedInputDom.attr('classType') == 2) {
                $classTypeToggle.eq(0).removeClass('hide').addClass('active');
                $classTypeToggle.eq(1).removeClass('hide');
            } else if($checkedInputDom.attr('classType') == 3) {
                $classTypeToggle.eq(0).addClass('hide');
                $classTypeToggle.eq(1).addClass('hide');
            }else{
                $classTypeToggle.eq(0).addClass('active');
                $classTypeToggle.eq(1).addClass('hide');
            }


            GLOBAL_CONSTANT.cType = 1;
            App.renderTableHeader();
            App.loadPage();
            App.pagination();
            $('#student-table').find('#stdCheckAll').prop('checked', false);
        })
        //新增行政班和教学班切换
        $(document).on('click', '#class-type-toggle .tab', function () {
            GLOBAL_CONSTANT.cType = $(this).attr('type');
            App.renderTableHeader();
            App.loadPage();
            App.pagination();
            $(this).addClass('active').siblings().removeClass('active');
        });
    }
}
App.init();


/**
 * 添加学生模块
 * @type {{init: CRUDStd.init, fetchGrade: CRUDStd.fetchGrade, fetchEntranceYear: CRUDStd.fetchEntranceYear, fetchBelongClass: CRUDStd.fetchBelongClass, renderElement: CRUDStd.renderElement, bindEvents: CRUDStd.bindEvents, addStd: CRUDStd.addStd, updateStd: CRUDStd.updateStd, removeStd: CRUDStd.removeStd, CRUDStdVerify: CRUDStd.CRUDStdVerify, addStdEvent: CRUDStd.addStdEvent, updateStdEvent: CRUDStd.updateStdEvent}}
 */
var CRUDStd = {
    init: function () {
        this.bindEvents();
        this.CRUDStdData = {
            renderEleData: [],
            gradeData: '',
            yearData: '',
            classData: ''
        };
        // this.fetchGrade(); //拉取渲染grade 12.7
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
                CRUDStd.CRUDStdData.yearData = tpl;
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
                CRUDStd.CRUDStdData.classData = tpl;
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
                        // CRUDStd.fetchGrade(); 初始化调用了12.7
                        // console.info('CRUDStd.CRUDStdData.gradeData', CRUDStd.CRUDStdData.gradeData);
                        v.dataValue = App.checkGradeName;
                        break;
                    case 'student_class_in_year':  //入学年份
                        CRUDStd.fetchEntranceYear();
                        v.dataValue = CRUDStd.CRUDStdData.yearData;
                        break;
                    case 'student_class':      //所在班级
                        CRUDStd.fetchBelongClass();
                        v.dataValue = CRUDStd.CRUDStdData.classData;
                        break;
                }
            }
            eleData = {
                name: v.name,
                enName: v.enName,
                dataType: v.dataType,
                dataValue: v.dataValue,
                checkRule: v.checkRule,
                isRetain: v.isRetain,
            }
            CRUDStd.CRUDStdData.renderEleData.push(eleData);
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
            var foo = '';
            v.isRetain == 1 ? foo = '<b class="red-icon">*</b>' + v.name : foo = v.name
            return '<li><span class="f20">' + foo + '</span>' +
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
            var foo = '';
            v.isRetain == 1 ? foo = '<b class="red-icon">*</b>' + v.name : foo = v.name
            for (var i = 0; i < checkBoxLen.length; i++) {
                //if (i == 0) {
                //checkBoxTpl += '<label>' +
                //    '<input type="checkbox" class="ace" value="' + checkBoxLen[i] + '" checked="checked" name="ck">' +
                //    '<span class="lbl">' + checkBoxLen[i] + '</span>' +
                //    '</label>';
                //} else {
                checkBoxTpl += '<label>' +
                    '<input type="checkbox" class="ace" value="' + checkBoxLen[i] + '" name="ck">' +
                    '<span class="lbl">' + checkBoxLen[i] + '</span>' +
                    '</label>';
                //}

            }
            return '<li><span class="f20">' + foo + '</span><div id="' + v.enName + '" class="subject-list f70">' + checkBoxTpl + '</div></li>'
        }
        /**
         * 渲染Select
         * @param v
         * @returns {string}
         */
        var renderSelect = function (v) {

            var foo = '';
            v.isRetain == 1 ? foo = '<b class="red-icon">*</b>' + v.name : foo = v.name
            if (v.enName == "student_grade") {
                return '<li><span>' + foo + '</span><select id="' + v.enName + '" readonly disabled style="cursor: not-allowed;background-color: #eee;"><option>' + App.checkGradeName + '</option></select></li>'
            } else {
                var selectLen = (v.dataValue).split('-'),
                    selectTpl = '';
                if (v.enName == 'student_check_major1'
                    || v.enName == 'student_check_major2'
                    || v.enName == 'student_check_major3') {
                    selectTpl = '<option>请选择科目</option>'
                }
                for (var i = 0; i < selectLen.length; i++) {
                    selectTpl += '<option>' + selectLen[i] + '</option>'
                }
                return '<li><span>' + foo + '</span><select id="' + v.enName + '">' + selectTpl + '</select></li>'
            }
        }
        /**
         * 渲染Text
         * @param v
         * @returns {string}
         */
        var renderText = function (v) {
            // var foo = '';
            // v.isRetain == 1 ? foo = '<b class="red-icon">*</b>' + v.name : foo = v.name

            // var foo = v.name;
            // if (v.enName == 'student_no' || v.enName == 'student_name') {
            //     foo = '<b class="red-icon">*</b>' + v.name
            // }


            var foo = '';
            v.isRetain == 1 ? foo = '<b class="red-icon">*</b>' + v.name : foo = v.name
            return '<li><span>' + foo + '</span><input type="text" placeholder="请输入' + v.name + '" id="' + v.enName + '" checkRule="' + v.checkRule + '" class="input-common-w"/></li>'
        }
        $.each(CRUDStd.CRUDStdData.renderEleData, function (i, v) {
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
        CRUDStd.CRUDStdData.renderEleData = [] //制空
        $('#student-add-box').html(templateEle);
    },
    bindEvents: function () {
        var that = this;
        //添加|更新  弹框
        $(document).on('click', '#student-add,#student-modify', function () {
            var type = $(this).attr('type');
            that.renderElement();
            if (type === 'add') {
                CRUDStd.addStd();//添加
            } else {
                CRUDStd.updateStd();//更新 update
            }
        })
        //确认添加
        $(document).on('click', '#add-btn', function () {
            if (that.CRUDStdVerify('add')) {
                that.addStdEvent(that.CRUDStdVerify('add'));
                // window.location.reload();
            }
        })
        //删除
        $(document).on('click', '#student-remove', function () {
            CRUDStd.removeStd();
        });
        //更新
        $(document).on("click", "#update-btn", function () {
            if (that.CRUDStdVerify('update')) {
                that.updateStdEvent(that.CRUDStdVerify('update'));
            }
        })
        //获取修改前数据 [组装修改前的数据]
        $(document).on('click', '#student-table td input[type="checkbox"]', function () {
            var rowData = [];
            var parent = $(this).parent().parent().parent();
            var rowLen = parent.find('td').length
            for (var i = 1; i < rowLen; i++) {
                var childData = {};
                childData.iName = parent.find('td').eq(i).attr('iName');
                childData.pid = parent.find('td').eq(i).attr('pid');
                childData.dataType = parent.find('td').eq(i).attr('dataType');
                childData.value = parent.find('td').eq(i).text();
                rowData.push(childData);
            }
            parent.attr('rowData', JSON.stringify(rowData));
        })
    },
    //添加
    addStd: function () {
        layer.open({
            type: 1,
            title: '添加学生',
            offset: 'auto',
            area: ['475px', 'auto'],
            content: $('#student-add-layer'),
            cancel: function () {
                layer.closeAll();
            }
        })
    },
    //更新
    updateStd: function () {
        var checkboxN = $("#student-table .check-template :checkbox:checked").size();
        if (checkboxN != '1') {
            layer.tips('修改只能选择一项', $('#student-modify'), {time: 1000});
            return false;
        }
        //赋值
        var parent = $('#student-table td input[type="checkbox"]:checked').parent().parent().parent();
        CRUDStd.pid = $('#student-table td input[type="checkbox"]:checked').parents('tr').find('td').eq(2).attr('pid');
        var rowData = JSON.parse(parent.attr('rowdata'));
        $('.save-btn').html('确认修改').attr('id', 'update-btn');
        layer.open({
            type: 1,
            title: '修改学生',
            offset: 'auto',
            area: ['475px', 'auto'],
            content: $('#student-add-layer'),
            cancel: function () {
                layer.closeAll();
            },
            success: function (html) {
                $.each(rowData, function (i, v) {
                    if (v.dataType === 'radio') {
                        for (var j = 0; j < $('#' + v.iName).find("[name='form-field-radio']").length; j++) {
                            if ($('#' + v.iName).find("[name='form-field-radio']").eq(j).val() == v.value) {
                                $('#' + v.iName).find("[name='form-field-radio']").eq(j).attr('checked', 'checked')
                            }
                        }
                    }
                    if (v.dataType === 'checkbox') {
                        for (var j = 0; j < $('#' + v.iName).find("[name='ck']").length; j++) {
                            var foo = $('#' + v.iName).find("[name='ck']").eq(j).val();
                            if ($.inArray(foo, v.value.split('-')) >= 0) {
                                $('#' + v.iName).find("[name='ck']").eq(j).attr('checked', true)
                            }
                        }
                    }
                    $('#' + v.iName).val(v.value);
                });
            }
        });
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
            Common.ajaxFun('/student/removeStuInfo.do', 'POST',{
                "tnId":GLOBAL_CONSTANT.tnId,
                "ids":ids
            }, function (res) {
            // Common.ajaxFun('/manage/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '/' + ids + '/remove.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result = "SUCCESS") {
                        $('#class-change-list').html('');
                        $('#student-table').find('#stdCheckAll').prop('checked', false);
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
    CRUDStdVerify: function (type) {
        var postData = [];
        var lock = 0;
        $.each(App.tableData, function (i, v) {
            if (v.dataType === "text") {
                // if(v.enName != 'student_check_major_class1'
                //     || v.enName != 'student_check_major_class2'
                //     || v.enName != 'student_check_major_class3'){
                //     return false;
                // }
                // if (v.enName == 'student_check_major_class1') {
                //     var a = $('#student_check_major1').val()
                //     var b = $('#student_check_major_class1').val()
                //     // alert(a);
                //     // alert($.trim(b));
                //     if (a == '请选择科目' && $.trim(b) != '' || a != '请选择科目' && $.trim(b) == '') {
                //         layer.msg('选考科目不能填写不完整');
                //         return false;
                //     }
                // }
                // if (v.enName == 'student_check_major_class2') {
                //     var a = $('#student_check_major2').val()
                //     var b = $('#student_check_major_class2').val()
                //     console.info('a', a);
                // }
                // if (v.enName == 'student_check_major_class3') {
                //     var a = $('#student_check_major3').val()
                //     var b = $('#student_check_major_class3').val()
                //     console.info('a', a);
                // }
                // if (v.enName == 'student_check_major_class1' ||
                //     v.enName == 'student_check_major_class2' ||
                //     v.enName == 'student_check_major_class3') {
                //
                // }
                if ($('#' + v.enName).val() == '') {
                    if (v.enName == 'student_check_major_class1' ||
                        v.enName == 'student_check_major_class2' ||
                        v.enName == 'student_check_major_class3') {
                        return false;
                    }
                    layer.msg(v.name + '不能为空', {time: 1000});
                    $('#' + v.enName).focus();
                    lock = 1;
                    return false;
                }
                var reg = eval(v.checkRule),
                    regV = $('#' + v.enName).val();
                if (!reg.test(regV)) {
                    layer.msg(v.name + '输入不合法', {time: 1000});
                    $('#' + v.enName).focus();
                    lock = 1;
                    return false;
                }
            }
            if (v.dataType === "checkbox") {
                if ($('#' + v.enName).find('[name="ck"]:checked').length == 0) {
                    layer.msg('请至少选择一门所教科目');
                    return false;
                }
            }
        })
        if (lock == 1) {
            return false;
        }
        $.each(App.tableData, function (i, v) {
            if (v.dataType === "text" || v.dataType === "select") {
                if (v.enName == 'student_check_major1' ||
                    v.enName == 'student_check_major2' ||
                    v.enName == 'student_check_major3') {
                    var foo = $.trim($('#' + v.enName).val());
                    postData.push({
                        "key": v.enName,
                        "value": foo == '请选择科目' ? '' : foo
                    });
                } else {
                    postData.push({
                        "key": v.enName,
                        "value": $.trim($('#' + v.enName).val())
                    });
                }

            } else if (v.dataType === "radio") {
                postData.push({
                    "key": v.enName,
                    "value": $('#' + v.enName).find('[name="form-field-radio"]:checked').val()
                });
            } else if (v.dataType === "checkbox") {
                var subjects = [];
                $($('#' + v.enName).find('[name="ck"]')).each(function () {
                    if ($(this).prop('checked')) {
                        subjects.push($(this).val());
                    }
                });
                var values = subjects.join('-');
                postData.push({
                    "key": v.enName,
                    "value": values
                });
            }
        })
        //选考科目3个非必填|入填一个则所有都必填
        if(GLOBAL_CONSTANT.tnId == 0){
            if (postData[3].value != '') {
                if (postData[3].value == postData[5].value || postData[5].value == postData[7].value || postData[7].value == postData[3].value) {
                    layer.msg('选考科目1,2,3不能相同');
                    return false;
                }
            }
        }
        if (type == 'add') {
            return {
                "clientInfo": {},
                "style": "",
                "data": {
                    "type": GLOBAL_CONSTANT.cType,
                    "tnId": GLOBAL_CONSTANT.tnId,
                    "teantCustomList": postData
                }
            };
        } else {
            return {
                "clientInfo": {},
                "style": "",
                "data": {
                    "type": GLOBAL_CONSTANT.cType,
                    "tnId": GLOBAL_CONSTANT.tnId,
                    "pri": CRUDStd.pid,
                    "teantCustomList": postData
                }
            };
        }
    },
    addStdEvent: function (data) {
        Common.ajaxFun('/student/addStuInfo.do', 'POST', JSON.stringify(data), function (res) {
            if (res.rtnCode == "0000000" && res.bizData.result === 'SUCCESS') {
                layer.closeAll();
                layer.msg('添加成功');
                App.renderTableHeader();
            }
        }, function (res) {
            layer.msg("出错了");
        }, null, true);
    },
    updateStdEvent: function (data) {
        Common.ajaxFun('/student/updateStuInfo.do', 'POST', JSON.stringify(data), function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                App.renderTableHeader();
            }
        }, function (res) {
            layer.msg("出错了");
        }, null, true);
    }
}
CRUDStd.init();


/**
 * 模板上传|下载
 * 模板下载细分：行政班模块下载和教学班模板下载
 * @type {{init: TplHandler.init, tplDownload: TplHandler.tplDownload, tplUpload: TplHandler.tplUpload}}
 */
var TplHandler = {
    init: function () {
        this.tplDownload();
        this.tplUpload();
    },
    tplDownload: function () {
        //行政模板下载type = 1
        $(document).on('click', '#xz-template-download', function () {
            window.location.href = '/student/downloadStuMould.do?type=' + 1 + '&tnId=' + GLOBAL_CONSTANT.tnId;
        });
        //教学模板下载type = 0
        $(document).on('click', '#jx-template-download', function () {
            window.location.href = '/student/downloadStuMould.do?type=' + 0 + '&tnId=' + GLOBAL_CONSTANT.tnId;
        });
    },
    tplUpload: function () {
        //上传
        $(document).on('click', '#student-upload', function () {
            var tpl = [];
            tpl.push('<div class="upload-box">');
            tpl.push('<span id="uploader-demo">');
            tpl.push('<span id="fileList" class="uploader-list dh"></span>');
            tpl.push('<button class="btn btn-info btn-import" id="btn-import">导入学生数据Excel</button>');
            tpl.push('</span>');
            tpl.push('<a href="javascript: void(0);" id="student-template-download" class="download-link">请先导出Excel模板，进行填写</a>');
            tpl.push('<button class="btn btn-cancel cancel-btn" id="cancel-download-btn">取消</button>');
            tpl.push('</div>');
            layer.open({
                type: 1,
                title: '导入学生数据',
                offset: 'auto',
                area: ['460px', '280px'],
                content: tpl.join('')
            });
            upload();
        });
        var upload = function () {
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
                server: rootPath + '/config/upload/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do',
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
            });
            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            uploader.on('uploadSuccess', function (file, response) {
                console.info('file', file)
                console.info('response', response)
                if (response.msg) {
                    layer.msg(response.msg);
                    return false;
                }
                if (!response.bizData.result) {
                    layer.msg(response.msg);
                    return false;
                }
                if (response.bizData.result != 'SUCCESS') {
                    layer.msg(response.bizData.result);
                    return false;
                }
                layer.closeAll();
                if (App != null) {
                    App.renderTableHeader();
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
            });
            // 完成上传完了，成功或者失败，先删除进度条。
            uploader.on('uploadComplete', function (file, response) {
                $('#' + file.id).find('.progress').remove();
            });

            // uploader.on("uploadAccept", function (file, data) {
            //     layer.msg(data.bizData.result)
            // });

        }
    }
}
TplHandler.init();


/**
 * 学生设置
 * @type {{init: StdSet.init, eventStdSet: StdSet.eventStdSet, eventAdd: StdSet.eventAdd, fetchTableHeader: StdSet.fetchTableHeader, renderHeader: StdSet.renderHeader, generateTableHeader: StdSet.generateTableHeader, saveAddTableHeaderField: StdSet.saveAddTableHeaderField, removeTableHeaderField: StdSet.removeTableHeaderField, tableDrag: StdSet.tableDrag}}
 */
var StdSet = {
    init: function () {
        this.eventStdSet();
        this.removeTableHeaderField();//删除设置的表头
    },
    eventStdSet: function () {
        var that = this;
        $(document).on('click', '#student-setting', function () {
            that.fetchTableHeader();
            that.eventAdd();
            that.saveAddTableHeaderField();
            // layer.full(
            layer.open({
                type: 1,
                content: $("#sub-student-setting"),
                area: ['800px', '600px'],
                // area: ['100%', '100%'],
                maxmin: false,
                scrollbar: false,
                cancel: function () {
                    window.location.reload();
                }
            })
            // )
        });
    },
    eventAdd: function () {
        var that = this;
        $(document).on('click', '#sub-student-add', function () {
            that.generateTableHeader();
            layer.closeAll()
            layer.open({
                type: 1,
                title: '选择添加字段',
                content: $('#sub-choose-field'),
                area: ['668px', '230px'],
                cancel: function () {
                    window.location.reload();
                }
            })
        });
    },
    fetchTableHeader: function () {
        var that = this;
        Common.ajaxFun('/config/get/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StdSet.renderHeader(res)
                that.tableDrag();
                StdSet.fetchTableHeaderData = res;

            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderHeader: function (res) {
        var that = this;
        var dataJson = res.bizData.configList;
        var template = Handlebars.compile($('#sub-student-table-tpl').html());
        $('#sub-student-table').html(template(dataJson));
    },
    /**
     * 添加表头字段
     * (获取所有字段名称)
     */
    generateTableHeader: function () {
        Common.ajaxFun('/config/getInit/' + GLOBAL_CONSTANT.type + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
                $('#field').html('');
                $.each(res.bizData.configList, function (i, v) {
                    tpl.push('<label for="li-' + v.id + '">');
                    var isCheck = false;
                    $.each(StdSet.fetchTableHeaderData.bizData.configList, function (j, k) {
                        if (v.id == k.configKey) {
                            if (v.isRetain == 1) {
                                tpl.push('<input type="checkbox" class="ace" id="li-' + v.id + '" checked="checked" disabled>');
                            } else {
                                tpl.push('<input type="checkbox" class="ace" id="li-' + v.id + '" checked="checked">');
                            }
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
    /**
     * 保存已选择的添加字段
     */
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
                    // layer.full(
                    layer.open({
                        type: 1,
                        content: $("#sub-student-setting"),
                        // area: ['100%', '100%'],
                        area: ['800px', '600px'],
                        maxmin: false,
                        cancel: function () {
                            $('#field').html(''); //清空
                            window.location.reload();
                        }
                    })
                    // )
                }
            }, function (res) {
                layer.msg("出错了");
            }, true);
        })
    },
    /**
     * 删除设置的表头字段
     */
    removeTableHeaderField: function () {
        var that = this;
        var idsData = null;
        $(document).on('click', '.student-setting-remove-head', function () {
            idsData = $(this).attr('data-id');
            removeField(idsData)
        })
        $(document).on('click', '#sub-student-remove', function () {
            var selItem = [];
            $('#sub-student-table').find('input[type="checkbox"]').each(function (i, v) {
                if ($(this).is(':checked') == true) {
                    selItem.push($(this).attr('cid'));
                }
            });
            selItem = selItem.join('-');
            removeField(selItem)
            $('#setting-student-table').find('#stdCheckAll').prop('checked', false);
        })
        var removeField = function (idsData) {
            Common.ajaxFun('/manage/tenant/remove/' + GLOBAL_CONSTANT.type + '/' + GLOBAL_CONSTANT.tnId + '/' + idsData + '.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    that.fetchTableHeader();
                    that.eventAdd();
                    that.saveAddTableHeaderField();
                }
            }, function (res) {
                layer.msg("出错了");
            }, true);
        }
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
                ids = ids.join('-');
                Common.ajaxFun('/config/sort/' + GLOBAL_CONSTANT.type + '/' + ids + '.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result == "SUCCESS") {
                            layer.msg('排序成功', {time: 1000});
                        } else {
                            layer.msg(res.bizData.result);
                        }
                    }
                }, function (res) {
                    layer.msg("出错了", {time: 1000});
                }, true, null);
            };
        $("#sub-student-table").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y",
            items: "tr:not(.isRetain1)"
        }).disableSelection();
    }

}
StdSet.init();


/*
 * 全选
 * */
var checkAllFun = function (tableParent) {
    $(document).on('click', tableParent + ' ' + '#stdCheckAll', function () {
        if ($(this).is(':checked')) {
            $(tableParent).find('.check-template :checkbox').prop('checked', true);
        } else {
            $(tableParent).find('.check-template :checkbox').prop('checked', false);
        }
    });
    $(tableParent + ' ' + ".check-template").on('click', ':checkbox', function () {
        var chknum = $(tableParent).find(".check-template :checkbox").size();//选项总个数
        var chk = 0;
        $(tableParent).find(".check-template :checkbox").each(function () {
            if ($(this).prop("checked") == true) {
                chk++;
            }
        });
        if (chknum == chk) {//全选
            $(tableParent + ' ' + '#stdCheckAll').prop("checked", true);
        } else {//不全选
            $(tableParent + ' ' + '#stdCheckAll').prop("checked", false);
        }
    });
}
checkAllFun('#student-table')
checkAllFun('#setting-student-table')

$(document).on('click', '#cancel-download-btn', function () {
    layer.closeAll();
});

