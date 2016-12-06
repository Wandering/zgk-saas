var tnId = Common.cookie.getCookie('tnId');


//Common.flowSteps();


var initClassBtn = '<div class="initClassBtn"><button class="btn btn-info" id="initBtn">初始化班级</button></div>';
layer.open({
    type: 1,
    title:"请初始化班级",
    skin: 'layui-layer-demo', //样式类名
    closeBtn: 0, //不显示关闭按钮
    anim: 2,
    shadeClose: false, //开启遮罩关闭
    offset: 'auto',
    area: ['400px', '200px'],
    content: initClassBtn
});




function SetingProcess3() {
    this.init();
}
SetingProcess3.prototype = {
    constructor: SetingProcess3,
    init: function () {
        this.getClassList();
        this.tableDrag();
    },
    getClassList: function () {
        Common.ajaxFun('/config/get/class/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                $.each(data, function (i, v) {
                    var classTemplate = [];
                    classTemplate.push('<tr>');
                    classTemplate.push('<td class="center">');
                    classTemplate.push('<label>');
                    classTemplate.push('<input type="checkbox" seldata="' + v.id + '" class="ace" />');
                    classTemplate.push('<span class="lbl"></span>');
                    classTemplate.push('</label>');
                    classTemplate.push('</td>');
                    classTemplate.push('<td class="center index" indexId="' + v.id + '">' + (i + 1) + '</td>');
                    classTemplate.push('<td class="center key-name">' + v.name + '</td>');
                    classTemplate.push('<td class="center"><span deldata="' + v.id + '" class="del-btn">删除</span></td>');
                    classTemplate.push('</tr>');
                    $('#class-template').append(classTemplate.join(''));
                });
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    initClassItem: function () {
        var name = {};
        $('#class-template td.key-name').each(function (i, v) {
            name[$(this).text()] = $(this).text();
        });
        var initData = '';
        Common.ajaxFun('/config/getInit/class.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                initData = res.bizData.configList;
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
        var contentHtml = [];
        contentHtml.push('<div class="add-label">');
        $.each(initData, function (i, v) {
            contentHtml.push('<label>');
            if(v.isRetain!=1){
                if (name[v.chName] == v.chName) {
                    contentHtml.push('<input name="form-field-checkbox"  isRetain="'+ v.isRetain +'"  type="checkbox" checked="checked" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
                } else {
                    contentHtml.push('<input name="form-field-checkbox" isRetain="'+ v.isRetain +'" type="checkbox" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
                }
            }else{
                contentHtml.push('<input name="form-field-checkbox" disabled="disabled" isRetain="'+ v.isRetain +'" checked="checked" type="checkbox" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
            }
            contentHtml.push('<span class="lbl">' + v.chName + '</span>');
            contentHtml.push('</label>');

        });
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info" id="sel-confirm">确定选择</button></div>');
        layer.open({
            type: 1,
            title: '添加班级字段',
            area: ['690px', '220px'], //宽高
            content: contentHtml.join('')
        });
    },
    eventSelConfirm: function () {

        var that = this;
        var ids = [];
        $.each($('.add-label input:checked'), function (i, v) {
            ids.push("-" + $(v).attr('iddata'));
        });
        ids = ids.join('');
        ids = ids.substring(1, ids.length);
        Common.ajaxFun('/config/import/class/' + tnId + '.do', 'POST', {
            'ids': ids
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == "SUCCESS") {
                    $('#class-template').html('');
                    that.getClassList();
                    layer.closeAll();
                } else {
                    layer.msg(res.bizData.result);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    delItem: function (ids) {
        var that = this;
        Common.ajaxFun('/config/tenant/remove/class/' + tnId + '/' + ids + '.do', 'POST', {}, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                $('#class-template').html('');
                that.getClassList();
            } else {
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
                    } else {
                        layer.msg(res.msg);
                    }
                }, function (res) {
                    layer.msg(res.msg);
                });
            };
        $("#class-template").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y"
        }).disableSelection();
    }
};
var SetingProcess3Obj = new SetingProcess3();

// 新增班级字段
$(function () {


    $('body').on('click','#initBtn',function(){
        Common.ajaxFun('/config/retain/class/'+ tnId +'.do', 'GET', {},
            function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    //if (res.bizData.result == 3) {
                    //    layer.msg('请完成该流程再进行下一步!');
                    //}else if(res.bizData.result == 4){
                    //    window.location.href='/seting-process4'
                    //}
                }
            }, function (res) {
                layer.msg(res.msg);
            });
    });







    $('#add-btn').on('click', function () {
        SetingProcess3Obj.initClassItem()
    });

// 添加班级字段确定
    $('body').on('click', '#sel-confirm', function () {
        SetingProcess3Obj.eventSelConfirm();
    });

// 删除表头
    $('body').on('click', '.del-btn', function () {
        var ids = $(this).attr('deldata');
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            SetingProcess3Obj.delItem(ids);
        }, function () {
            layer.closeAll();
        });
    });

    // 批量删除
    $('body').on('click', '#del-batch-btn', function () {
        var checkedLen = $("#class-template :checkbox:checked").size();
        if (checkedLen == "0") {
            layer.tips('至少选择一项', $(this));
            return false;
        }

        var selItem = [];
        $('#class-template').find('input[type="checkbox"]').each(function (i, v) {
            if ($(this).is(':checked') == true) {
                selItem.push('-' + $(this).attr('seldata'));
            }
        });
        selItem = selItem.join('');
        selItem = selItem.substring(1, selItem.length);
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            SetingProcess3Obj.delItem(selItem);
        }, function () {
            layer.closeAll();
        });
    });
    // 导出表头
    $('#export-excel-btn').on('click', function () {
        window.location.href = '/config/export/class/' + tnId + '.do';
    });
    // 下一流程
    $('#seting-process3-btn').on('click', function () {
        Common.ajaxFun('/config/get/step/' + tnId + '.do', 'GET', {},
            function (res) {
                console.log(res)
                if (res.rtnCode == "0000000") {
                    if (res.bizData.result == 3) {
                        layer.msg('请完成该流程再进行下一步!');
                    }else if(res.bizData.result == 4){
                        window.location.href='/seting-process4'
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
        server: rootPath + '/config/upload/class/' + tnId + '.do',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#btn-import',

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
    uploader.on('uploadSuccess', function (file) {
        $('#add-btn,#del-batch-btn').addClass('disabled');
        $('.del-btn').removeClass('del-btn').addClass('disabled');
        $('#' + file.id).addClass('upload-state-done');
        layer.msg('导入成功,请进行下一流程!');

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