var tnId = Common.cookie.getCookie('tnId');

function ResultsManagementFun() {
    this.init();
}

ResultsManagementFun.prototype = {
    constructor: ResultsManagementFun,
    init: function () {
    },
    getResultsList: function (grade) {
        Common.ajaxFun('/scoreAnalyse/listExam', 'GET', {
            'grade': grade
        }, function (res) {
            console.log(res)
            var myTemplate = Handlebars.compile($("#results-template").html());
            Handlebars.registerHelper('excel', function (url) {
                return Common.getPageName(url);
            });
            $('#results-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    },
    uploadResults: function (id,examName,examTime,uploadFilePath) {
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal upload-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="examName"> 考试名称 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if(examName){
            contentHtml.push('<input type="text" id="examName" value="'+ examName +'" placeholder="输入考试名称" class="col-xs-10 col-sm-10"/>');
        }else{
            contentHtml.push('<input type="text" id="examName" placeholder="输入考试名称" class="col-xs-10 col-sm-10"/>');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-name">考试时间 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if(examTime){
            contentHtml.push('<input class="col-xs-10 col-sm-10 date-picker" value="'+ examTime +'" readonly="readonly" id="exam-date" type="text" placeholder="选择考试时间" data-date-format="yyyy-mm-dd"/>');
        }else{
            contentHtml.push('<input class="col-xs-10 col-sm-10 date-picker" readonly="readonly" id="exam-date" type="text" placeholder="选择考试时间" data-date-format="yyyy-mm-dd"/>');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right">添加成绩 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<span id="uploader-demo">');
        contentHtml.push('<span id="fileList" style="display: none;" class="uploader-list"></span>');
        contentHtml.push('<button class="btn btn-pink" id="btn-import">添加</button>');
        contentHtml.push('</span>');
        contentHtml.push('<p><a target="_blank" href="/scoreAnalyse/downloadModel">请先导出Excel模板,进行填写</a></p>');
        contentHtml.push('<p>温馨提示:上传与模板不一致的成绩单,系统无法识别</p>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box">');
        if(uploadFilePath){
            contentHtml.push('<button class="btn btn-info save-btn" dataid="'+ id +'" filePath="'+ uploadFilePath +'">保存</button>');
        }else{
            contentHtml.push('<button class="btn btn-info save-btn">保存</button>');
        }
        contentHtml.push('<button class="btn btn-primary close-btn">取消</button>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '上传成绩',
            offset: 'auto',
            area: ['362px', '350px'],
            content: contentHtml.join('')
        });
        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
        uploadFun();
    },
    detailsList:function(id,grade,curr){
        Common.ajaxFun('/scoreAnalyse/listExamDetail', 'GET', {
            'examId':id,
            'grade': grade,
            'offset':curr || 1,
            'rows':10
        }, function (res) {
            console.log(res)
            var myTemplate = Handlebars.compile($("body #details-template").html());
            $('body #details-tbody').html(myTemplate(res));


            //显示分页
            laypage({
                cont: 'details-page', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                pages: res.pages, //通过后台拿到的总页数
                curr: curr || 1, //当前页
                jump: function(obj, first){ //触发分页后的回调
                    if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                    }
                }
            });





        }, function (res) {
            alert("出错了");
        });


    }
};

var ResultsManagementIns = new ResultsManagementFun();

$(function () {
    // 选择年级
    $('input[name="results-radio"]').change(function () {
        var radioV = $('input[name="results-radio"]:checked').val();
        ResultsManagementIns.getResultsList(radioV);
    });

    // 默认高一年级
    $('input[name="results-radio"][value="1"]').attr('checked', true);
    ResultsManagementIns.getResultsList(1);

    //上传成绩
    $('body').on('click', '#uploadResultsBtn', function () {
        ResultsManagementIns.uploadResults();
    });

    // 保存
    $('body').on('click', '.save-btn', function () {
        var radioV = $('input[name="results-radio"]:checked').val();
        var examName = $.trim($('#examName').val());
        var examDate = $.trim($('#exam-date').val());
        var dataid = $(this).attr('dataid');
        var filePath = $(this).attr('filePath');
        if (examName == "") {
            layer.tips('请输入考试名称!', $('#examName'));
            return false;
        }
        if (examDate == "") {
            layer.tips('请选择考试时间!', $('#exam-date'));
            return false;
        }
        if(filePath=='' || filePath == undefined){
            layer.tips('请添加成绩!', $('#btn-import'));
            return false;
        }
        if(dataid){
            Common.ajaxFun('/scoreAnalyse/getExamDetailById', 'GET', {
                'id':dataid
            }, function (res) {
                if(res.rtnCode=="0000000"){
                    layer.closeAll();
                    ResultsManagementIns.getResultsList(radioV);
                    layer.msg('修改成功!');
                }
            }, function (res) {
                alert("出错了");
            });
        }else{
            Common.ajaxFun('/scoreAnalyse/addExam.do', 'GET', {
                'examName':examName,
                'examTime':examDate,
                'grade':radioV,
                'uploadFilePath':filePath
            }, function (res) {
                if(res.rtnCode=="0000000"){
                    layer.closeAll();
                    ResultsManagementIns.getResultsList(radioV);
                    layer.msg('添加成功!');
                }
            }, function (res) {
                alert("出错了");
            });
        }


    });

    // 修改
    $('#modify-btn').on('click', function () {
        var checkboxLen = $('#results-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }
        var resultsChecked = $('#results-tbody input:checked');
        var id = resultsChecked.attr('id');
        var examName = resultsChecked.attr('examName');
        var examTime = resultsChecked.attr('examTime');
        var uploadFilePath = resultsChecked.attr('uploadFilePath');
        ResultsManagementIns.uploadResults(id,examName,examTime,uploadFilePath);
    });

    // 删除
    $('#close-btn').on('click', function () {
        var checkboxLen = $('#results-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('至少选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }
        layer.confirm('确定删除?', {
            btn: ['确定', '关闭'] //按钮
        }, function () {
            var roleId = $('#results-tbody input:checked').attr('roleid');
            RoleManagementIns.deleteRole(roleId);
        }, function () {
            layer.closeAll();
        });
    });


    // 查看详情
    $('body').on('click','.look-details',function(){
        var examId = $(this).attr('urlId');
        var grade = $(this).attr('grade');
        var index = layer.open({
            title:'成绩明细',
            type: 1,
            content: $('#details-main').html(),
            area: ['100%','100%'],
            maxmin: false
        });
        layer.full(index);
        ResultsManagementIns.detailsList(examId,grade);
    })
});


function uploadFun(){
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
            server: rootPath + '/scoreAnalyse/uploadData.do',

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
        uploader.on('uploadSuccess', function (file,response) {
            $('#' + file.id).addClass('upload-state-done');
            $('.save-btn').attr('filePath',response.bizData.filePath);
            layer.msg('上传成功!');

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
}


