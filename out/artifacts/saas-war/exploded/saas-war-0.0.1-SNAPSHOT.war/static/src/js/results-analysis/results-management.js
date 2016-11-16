var tnId = Common.cookie.getCookie('tnId');

function ResultsManagementFun() {
    this.init();
}

ResultsManagementFun.prototype = {
    constructor: ResultsManagementFun,
    init: function () {
    },
    count: function () {
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
    uploadResults: function (id, examName, examTime, uploadFilePath) {
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal upload-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="examName"> 考试名称 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if (examName) {
            contentHtml.push('<input type="text" id="examName" value="' + examName + '" placeholder="输入考试名称" class="col-xs-10 col-sm-10"/>');
        } else {
            contentHtml.push('<input type="text" id="examName" placeholder="输入考试名称" class="col-xs-10 col-sm-10"/>');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-name">考试时间 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if (examTime) {
            contentHtml.push('<input class="col-xs-10 col-sm-10 date-picker" value="' + examTime + '" readonly="readonly" id="exam-date" type="text" placeholder="选择考试时间" data-date-format="yyyy-mm-dd"/>');
        } else {
            contentHtml.push('<input class="col-xs-10 col-sm-10 date-picker" readonly="readonly" id="exam-date" type="text" placeholder="选择考试时间" data-date-format="yyyy-mm-dd"/>');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        if(!id){
            contentHtml.push('<div class="form-group add-results">');
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
        }
        contentHtml.push('<div class="btn-box">');
        if (uploadFilePath) {
            contentHtml.push('<button class="btn btn-info save-btn" dataid="' + id + '" filePath="' + uploadFilePath + '">保存</button>');
        } else {
            contentHtml.push('<button class="btn btn-info save-btn">保存</button>');
        }
        contentHtml.push('<button class="btn btn-primary close-btn">取消</button>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        if(id){
            layer.open({
                type: 1,
                title: '上传成绩',
                offset: 'auto',
                area: ['362px', '230px'],
                content: contentHtml.join('')
            });

        }else{
            layer.open({
                type: 1,
                title: '上传成绩',
                offset: 'auto',
                area: ['362px', '350px'],
                content: contentHtml.join('')
            });
            uploadFun();
        }
        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    },
    detailsList: function (uploadfilepath,id, grade, Pn, rows) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/listExamDetail', 'GET', {
            'examId': id,
            'grade': grade,
            'offset': Pn,
            'rows': rows
        }, function (res) {
            $(".tcdPageCode").attr('count', parseInt(Math.ceil(res.bizData.count / rows)));
            var myTemplate = Handlebars.compile($("body #details-template").html());
            $('body #details-tbody').html(myTemplate(res));
            $('body #details-download-btn').attr('href',uploadfilepath);

        }, function (res) {
            alert("出错了");
        }, 'true');
    },
    detailsModify: function (className,classRank,commonScore,diLiScore,examId,gradeRank,huaXueScore,id,liShiScore,selectCourses,shengWuScore,shuXueScore,studentName,totleScore,wuLiScore,yingYuScore,yuWenScore,zhengZhiScore) {
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal upload-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="name"> 姓名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="name" value="'+ studentName +'" placeholder="输入姓名" class="col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="class"> 班级 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="班级" value="'+ className +'" placeholder="输入班级" class="col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group ">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right"> 主课成绩 </label>');
        contentHtml.push('<div class="col-sm-9 form-inline main-subject">');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-yuwen"> 语文 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-yuwen" value="'+ yuWenScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-shuxue"> 数学 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-shuxue" value="'+ shuXueScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-yingyu"> 英语 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-yingyu" value="'+ yingYuScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group ">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right"> 选课成绩 </label>');
        contentHtml.push('<div class="col-sm-9 form-inline main-subject">');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-wuli"> 物理 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-wuli" value="'+ wuLiScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-huaxue"> 化学 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-huaxue" value="'+ huaXueScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-shengwu"> 生物 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-shengwu" value="'+ shengWuScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-zhengzhi"> 政治 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-zhengzhi" value="'+ zhengZhiScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group ">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right"> </label>');
        contentHtml.push('<div class="col-sm-9 form-inline main-subject">');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-dili"> 地理 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-dili" value="'+ diLiScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-lishi"> 历史 </label>');
                contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-lishi" value="'+ liShiScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
            contentHtml.push('<div class="form-group col-sm-6 no-padding-right no-padding-left">');
                contentHtml.push('<label class="col-sm-5 control-label no-padding-right no-padding-left" for="subject-jishu"> 通用技术 </label>');
                contentHtml.push('<div class="col-sm-3 no-padding-right no-padding-left">');
                contentHtml.push('<input type="text" id="subject-jishu" value="'+ commonScore +'" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left"/>');
                contentHtml.push('</div>');
            contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="top-class"> 班级排名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="top-class" value="'+ classRank +'" placeholder="输入班级排名" class="col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="top-grade"> 年级排名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="top-grade" value="'+ gradeRank +'" placeholder="输入年级排名" class="col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box">');
        contentHtml.push('<button class="btn btn-info save-btn">提交</button>');
        contentHtml.push('<button class="btn btn-primary close-btn">取消</button>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '修改',
            offset: 'auto',
            area: ['600px', '450px'],
            content: contentHtml.join('')
        });
    },
    deleteExam: function (id) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/deleteExam', 'GET', {
            'examId':id
        }, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                var radioV = $('input[name="results-radio"]:checked').val();
                that.getResultsList(radioV);
            }
        }, function (res) {
            alert("出错了1");
        });
    },
    detailsModifyFun:function(id){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getExamDetailById', 'GET', {
            'id':id
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var className = data.className,
                    classRank = data.classRank,
                    commonScore = data.commonScore,
                    diLiScore = data.diLiScore,
                    examId = data.examId,
                    gradeRank = data.gradeRank,
                    huaXueScore = data.huaXueScore,
                    id = data.id,
                    liShiScore = data.liShiScore,
                    selectCourses = data.selectCourses,
                    shengWuScore = data.shengWuScore,
                    shuXueScore = data.shuXueScore,
                    studentName = data.studentName,
                    totleScore = data.totleScore,
                    wuLiScore = data.wuLiScore,
                    yingYuScore = data.yingYuScore,
                    yuWenScore = data.yuWenScore,
                    zhengZhiScore = data.zhengZhiScore;
                that.detailsModify(className,classRank,commonScore,diLiScore,examId,gradeRank,huaXueScore,id,liShiScore,selectCourses,shengWuScore,shuXueScore,studentName,totleScore,wuLiScore,yingYuScore,yuWenScore,zhengZhiScore);
            }
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
        if (filePath == '' || filePath == undefined) {
            layer.tips('请添加成绩!', $('#btn-import'));
            return false;
        }
        if (dataid) {
            Common.ajaxFun('/scoreAnalyse/modifyExam', 'GET', {
                'id': dataid,
                'examName': examName,
                'examTime': examDate,
                'grade': radioV
            }, function (res) {
                if (res.rtnCode == "0000000") {
                    layer.closeAll();
                    ResultsManagementIns.getResultsList(radioV);
                    layer.msg('修改成功!');
                }
            }, function (res) {
                alert("出错了");
            });
        } else {
            Common.ajaxFun('/scoreAnalyse/addExam.do', 'GET', {
                'examName': examName,
                'examTime': examDate,
                'grade': radioV,
                'uploadFilePath': filePath
            }, function (res) {
                if (res.rtnCode == "0000000") {
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
        ResultsManagementIns.uploadResults(id, examName, examTime, uploadFilePath);
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
            var id = $('#results-tbody input:checked').attr('id');
            ResultsManagementIns.deleteExam(id);
        }, function () {
            layer.closeAll();
        });
    });


    // 查看详情
    $('body').on('click', '.look-details', function () {
        var examId = $(this).attr('urlId');
        var grade = $(this).attr('grade');
        var uploadfilepath = $(this).attr('uploadfilepath');
        var index = layer.open({
            title: '成绩明细',
            type: 1,
            content: $('#details-main').html(),
            area: ['100%', '100%'],
            maxmin: false,
            success: function (layero, index) {
                //$('#details-main').remove();
                ResultsManagementIns.detailsList(uploadfilepath,examId, grade, 0, 10);
                $(".tcdPageCode").createPage({
                    pageCount: $(".tcdPageCode").attr('count'),
                    current: 1,
                    backFn: function (p) {
                        ResultsManagementIns.detailsList(uploadfilepath,examId, grade, (p - 1) * 10, 10);
                    }
                });
            }
        });
        layer.full(index);
    });

    $('body').on('click', '#details-modify-btn', function () {
        var checkboxLen = $('#details-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('删除只能选择一项', $(this));
            return false;
        }
        var detailsChecked = $('#details-tbody input:checked');
        var id = detailsChecked.attr('dataid');
        ResultsManagementIns.detailsModifyFun(id);
    });


});


function uploadFun() {
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
        uploader.on('uploadSuccess', function (file, response) {
            $('#' + file.id).addClass('upload-state-done');
            $('.save-btn').attr('filePath', response.bizData.filePath);
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


