/*
 * @module:学生管理模块
 * @author:pdeng
 * @time:2016-11-9
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44436387
 *
 * */



//添加学生
var StudentAdd = {
    init: function () {
        this.tnId = Common.cookie.getCookie('tnId');
        this.addEvent();
    },
    addEvent: function () {
        var that = this;
        $(document).on('click', '#student-add', function () {
            that.fetchGrade();  //拉取年级
            that.fetchEntranceYear(); //拉取入学年份
            that.fetchBelongClass(); //拉取所在班级
            layer.open({
                type: 1,
                title: '添加学生',
                offset: 'auto',
                area: ['425px', '530px'],
                content: $('#student-add-layer').html(),
                cancel:function(){
                    layer.closeAll();
                }
            })
        })
    },
    fetchGrade: function () {
        Common.ajaxFun('/config/grade/get/' + this.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderGrade(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    fetchEntranceYear: function () {
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderEntranceYear(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    fetchBelongClass: function () {
        Common.ajaxFun('/manage/class/' + this.tnId + '/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                StudentAdd.renderBelongClass(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderGrade: function (res) {
        var tpl = '<option value="00">选择年级</option>';
        $.each(res.bizData.grades, function (i, k) {
            tpl += '<option>' + k.grade + '</option>';
        });
        $('#select-grade').html(tpl)
    },
    renderEntranceYear: function (res) {
        var tpl = '<option value="00">入学年份</option>';
        for (var i in res.bizData.result) {
            tpl += '<option>' + res.bizData.result[i] + '</option>';
        }
        $('#select-year').html(tpl)
    },
    renderBelongClass: function (res) {
        var dataJson = res.bizData.result;
        var tpl = '<option value="00">所在班级</option>';
        for (var i in dataJson) {
            tpl += '<option class_name="' + dataJson[i].class_name + '" class_no="' + dataJson[i].id + '" class_id="' + dataJson[i].id + '">' + dataJson[i].class_type + '</option>';
        }
        $('#now-class').html(tpl)
    }
}

//修改
var StudentModify = {}

//删除
var StudentRemove = {}

//模板上传
var StudentTemplateDown = {}

//模板上传
var StudentUpload = {}


/*
 * 学生管理
 * */
var StudentManage = {
    init: function () {
        this.tnId = Common.cookie.getCookie('tnId');
        StudentAdd.init();
        this.studentTable();
    },
    studentTable: function () {
        var studentTableData = {};
        Common.ajaxFun('/config/get/student/' + this.tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                studentTableData.header = res.bizData.configList;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
        Common.ajaxFun('/manage/student/' + this.tnId + '/getTenantCustomData.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
                //studentTableData.body = res.bizData.configList;
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
        //渲染学生管理header
        var template = Handlebars.compile($('#student-table-tpl').html());
        $('#student-table').html(template(studentTableData));
    }
}

StudentManage.init();


/*
 * 学生设置
 * */
var StudentSetting = {
    init: function () {
        this.tnId = Common.cookie.getCookie('tnId');
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
                    area: ['100%','100%'],
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
                cancel:function(){
                    $('#field').html('');
                }
            })
        });
    },
    fetchTableHeader: function () {
        Common.ajaxFun('/config/get/student/' + this.tnId + '.do', 'GET', {}, function (res) {
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
        Common.ajaxFun('/config/getInit/student.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var tpl = [];
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
                if($('#field').find('[type="checkbox"]').eq(i).prop('checked') == true){
                    tplArr.push($('#field').find('[type="checkbox"]').eq(i).attr('id').split('-')[1])
                }
            }
            Common.ajaxFun('/manage/import/student/' + StudentSetting.tnId + '.do', 'POST', {
                ids: tplArr.join('-')
            }, function (res) {
                if (res.rtnCode == "0000000") {
                    that.fetchTableHeader();
                    layer.closeAll();
                    $('#field').html(''); // 清空
                    layer.full(
                        layer.open({
                            type: 1,
                            content: $("#sub-student-setting").html(),
                            area: ['100%','100%'],
                            maxmin: false,
                            cancel:function(){
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
    removeTableHeaderField:function(){
        var that = this;
        $(document).on('click','.student-setting-remove-head',function(){
            var idsData = $(this).attr('data-id');
            removeField(idsData)
        })
        var removeField = function(idsData){
            Common.ajaxFun('/manage/tenant/remove/student/'+StudentSetting.tnId+'/'+idsData+'.do', 'POST', {}, function (res) {
                if (res.rtnCode == "0000000") {
                    that.fetchTableHeader();
                    that.eventAdd();
                    that.saveAddTableHeaderField();
                    layer.closeAll();
                    layer.full(
                        layer.open({
                            type: 1,
                            content: $("#sub-student-setting").html(),
                            area: ['100%','100%'],
                            maxmin: false,
                            cancel:function(){
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
                Common.ajaxFun('/config/sort/class/'+ ids +'.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result == "SUCCESS") {
                            layer.msg('排序成功');
                        }else{
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



