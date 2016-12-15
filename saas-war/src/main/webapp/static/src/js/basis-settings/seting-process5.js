var tnId = Common.cookie.getCookie('tnId');

Common.flowSteps();


function SetingProcess5() {
    this.init();
}
SetingProcess5.prototype = {
    constructor: SetingProcess5,
    init: function () {
        this.getTeacherList();
        this.tableDrag();
        var trLen = $('#teacher-template').find('tr').length;
        console.log(trLen);
        if(trLen==0){
            this.initTeacher();
        }
    },
    initTeacher:function(){
        var initTeacherBtn = '<div class="initClassBtn"><button class="btn btn-info" id="initBtn">初始化教师</button></div>';
        layer.open({
            type: 1,
            title: "请初始化教师",
            skin: 'layui-layer-demo', //样式类名
            closeBtn: 0, //不显示关闭按钮
            anim: 2,
            shadeClose: false, //开启遮罩关闭
            offset: 'auto',
            area: ['400px', '200px'],
            content: initTeacherBtn
        });
    },
    getTeacherList: function () {
        Common.ajaxFun('/config/get/teacher/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                $('#teacher-template').html('');
                var data = res.bizData.configList;
                $.each(data, function (i, v) {
                    var TeacherTemplate = [];
                    TeacherTemplate.push('<tr class="isRetain'+ v.isRetain +'">');
                    TeacherTemplate.push('<td class="center">');
                    if(v.isRetain==1){""}else {
                        TeacherTemplate.push('<label>');
                        TeacherTemplate.push('<input type="checkbox" seldata="' + v.id + '" class="ace" />');
                        TeacherTemplate.push('<span class="lbl"></span>');
                        TeacherTemplate.push('</label>');
                    }
                    TeacherTemplate.push('</td>');
                    TeacherTemplate.push('<td class="center index">' + (i + 1) + '</td>');
                    TeacherTemplate.push('<td class="center key-name">' + v.name + '</td>');
                    if(v.isRetain==1){
                        TeacherTemplate.push('<td class="center"></td>');
                    }else{
                        TeacherTemplate.push('<td class="center"><span deldata="' + v.id + '" class="del-btn">删除</span></td>');
                    }
                    TeacherTemplate.push('</tr>');
                    $('#teacher-template').append(TeacherTemplate.join(''));
                });
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
    },
    initClassItem: function () {
        var name = {};
        $('#teacher-template td.key-name').each(function (i, v) {
            name[$(this).text()] = $(this).text();
        });
        var initData = '';
        Common.ajaxFun('/config/getInit/teacher.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                initData = res.bizData.configList;
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        var contentHtml = [];
        contentHtml.push('<div class="add-label">');
        $.each(initData, function (i, v) {
            contentHtml.push('<label>');
            if (v.isRetain != 1) {
                if (name[v.chName] == v.chName) {
                    contentHtml.push('<input name="form-field-checkbox" isRetain="' + v.isRetain + '" type="checkbox" checked="checked" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
                } else {
                    contentHtml.push('<input name="form-field-checkbox" isRetain="' + v.isRetain + '" type="checkbox" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
                }
            } else {
                contentHtml.push('<input name="form-field-checkbox" isRetain="' + v.isRetain + '" disabled="disabled" type="checkbox" checked="checked" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
            }

            contentHtml.push('<span class="lbl">' + v.chName + '</span>');
            contentHtml.push('</label>');
        });
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info" id="sel-confirm">确定选择</button></div>');
        layer.open({
            type: 1,
            title: '添加教师字段',
            area: ['690px', '220px'], //宽高
            content: contentHtml.join('')
        });
    },
    eventSelConfirm: function () {
        $('#teacher-template').html('');
        var that = this;
        var ids = [];
        $.each($('.add-label input:checked'), function (i, v) {
            ids.push("-" + $(v).attr('iddata'));
        });
        ids = ids.join('');
        ids = ids.substring(1, ids.length);
        console.log(ids)
        Common.ajaxFun('/config/import/teacher/' + tnId + '.do', 'POST', {
            'ids': ids
        }, function (res) {

            console.log(res)
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == "SUCCESS") {
                    $('#teacher-template').html('');
                    that.getTeacherList();
                    layer.closeAll();
                }
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    delItem: function (ids) {
        var that = this;
        Common.ajaxFun('/config/tenant/remove/teacher/'+ tnId + '/' + ids + '.do', 'POST', {}, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                $('#teacher-template').html('');
                that.getTeacherList();
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
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
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 1);
                });
            };
        $("#teacher-template").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y",
            items: "tr:not(.isRetain1)"
        }).disableSelection();
    }
};
var SetingProcess5Obj = new SetingProcess5();

// 新增班级字段
$(function () {


    $('body').on('click', '#initBtn', function () {
        Common.ajaxFun('/config/retain/teacher/' + tnId + '.do', 'POST', {},
            function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result == true) {
                        layer.msg('教师初始化成功!');
                        layer.closeAll();
                        SetingProcess5Obj.getTeacherList();
                    } else {
                        layer.msg('教师初始化失败!');
                        SetingProcess5Obj.initTeacher();
                    }
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    });


    $('#add-btn').on('click', function () {
        SetingProcess5Obj.initClassItem()
    });


    // 全选
    $('#checkAll').on('click', function () {
        if ($(this).is(':checked')) {
            $('#teacher-template :checkbox').prop('checked', true);
        } else {
            $('#teacher-template :checkbox').prop('checked', false);
        }
    });
    $("#teacher-template").on('click', ':checkbox', function () {
        allchk();
    });
    function allchk() {
        var chknum = $("#teacher-template :checkbox").size();//选项总个数
        var chk = 0;
        $("#teacher-template").find(':checkbox').each(function () {
            if ($(this).prop("checked") == true) {
                chk++;
            }
        });
        if (chknum == chk) {//全选
            $("#checkAll").prop("checked", true);
        } else {//不全选
            $("#checkAll").prop("checked", false);
        }
    }


// 添加班级字段确定
    $('body').on('click', '#sel-confirm', function () {
        var that = $(this);
        var addLabelSelLen = $('.add-label input:checked').length;
        if (addLabelSelLen == "0") {
            layer.tips('至少选择一项', that);
        } else {
            SetingProcess5Obj.eventSelConfirm();
        }

    });

// 删除表头
    $('body').on('click', '.del-btn', function () {
        var ids = $(this).attr('deldata');
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            SetingProcess5Obj.delItem(ids);
        }, function () {
            layer.closeAll();
        });
    });

    // 批量删除
    $('body').on('click', '#del-batch-btn', function () {
        var checkedLen = $("#teacher-template :checkbox:checked").size();
        if(checkedLen=="0"){
            layer.tips('至少选择一项', $(this));
            return false;
        }

        var selItem = [];
        $('#teacher-template').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push('-' + $(this).attr('seldata'));
            }
        });
        selItem = selItem.join('');
        selItem = selItem.substring(1, selItem.length);
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            SetingProcess5Obj.delItem(selItem);
        }, function () {
            layer.closeAll();
        });
    });
    // 导出表头
    $('#export-excel-btn').on('click', function () {
        window.location.href = '/config/export/teacher/' + tnId + '.do';
    });
    // 下一流程
    $('#seting-process5-btn').on('click',function(){
        Common.ajaxFun('/config/get/step/' + tnId + '.do', 'GET', {},
            function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result == 5) {
                        layer.msg('请完成该流程再开始SAAS!');
                    }else if(res.bizData.result == 0){
                        window.location.href='/course-guide'
                    }
                }
            }, function (res) {
                layer.msg('出错了');
            });
    })
});


jQuery(function () {
    var $ = jQuery,
        $list = $('#fileList'),
    // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,

    // 缩略图大小
        thumbnailWidth = 100 * ratio,
        thumbnailHeight = 100 * ratio,

    // Web Uploader实例
        uploader;

    // 初始化Web Uploader
    uploader = WebUploader.create({

        // 自动上传。
        auto: true,

        // swf文件路径
        swf: BASE_URL + '/js/Uploader.swf',

        // 文件接收服务端。
        server: rootPath + '/config/upload/teacher/' + tnId + '.do',

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
        )
        //    $img = $li.find('img');
        //
        $list.append($li);

        // 创建缩略图
        //uploader.makeThumb( file, function( error, src ) {
        //    if ( error ) {
        //        $img.replaceWith('<span>不能预览</span>');
        //        return;
        //    }
        //
        //    $img.attr( 'src', src );
        //}, thumbnailWidth, thumbnailHeight );
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
    uploader.on('uploadSuccess', function (file,response) {
        $('#add-btn,#del-batch-btn').addClass('disabled');
        $('.del-btn').removeClass('del-btn').addClass('disabled');
        $('#' + file.id).addClass('upload-state-done');
        layer.msg('导入成功,请开始SAAS!');
        if (!response.bizData.result) {
            layer.msg(response.msg);
            return false;
        }
        if (response.bizData.result != 'SUCCESS') {
            layer.msg(response.bizData.result);
            return false;
        }

    });

    // 文件上传失败，现实上传出错。
    uploader.on('uploadError', function (file) {
        var $li = $('#' + file.id),
            $error = $li.find('div.error');

        // 避免重复创建
        if (!$error.length) {
            $error = $('<div class="error"></div>').appendTo($li);
        }

        $error.text('上传失败');
    });

    // 完成上传完了，成功或者失败，先删除进度条。
    uploader.on('uploadComplete', function (file) {
        $('#' + file.id).find('.progress').remove();
    });
});