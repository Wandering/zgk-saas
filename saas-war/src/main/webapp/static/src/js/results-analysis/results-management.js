var tnId = Common.cookie.getCookie('tnId');
var globalParam = {
    $oldExamName:''
}
function ResultsManagementFun() {
    this.init();
}

ResultsManagementFun.prototype = {
    constructor: ResultsManagementFun,
    init: function () {
        this.getGrade();
    },
    count: function () {
    },
    getGrade: function () {
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#grade-template").html());
                $('#grade-body').html(myTemplate(res));
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg(res.msg);
        }, true);
    },
    getResultsList: function (grade) {
        Common.ajaxFun('/scoreAnalyse/listExam', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                var myTemplate = Handlebars.compile($("#results-template").html());
                Handlebars.registerHelper('excel', function (url) {
                    return Common.getPageName(url);
                });
                $('#results-tbody').html(myTemplate(res));
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了");
        });
    },
    uploadResults: function (grade,id, examName, examTime, uploadFilePath) {
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
        if (!id && grade) {
            contentHtml.push('<div class="form-group add-results">');
            contentHtml.push('<label class="col-sm-3 control-label no-padding-right">添加成绩 </label>');
            contentHtml.push('<div class="col-sm-9">');
            contentHtml.push('<span id="uploader-demo">');
            contentHtml.push('<span id="fileList" style="display: none;" class="uploader-list"></span>');
            contentHtml.push('<button class="btn btn-pink" id="btn-import">添加</button>');
            contentHtml.push('</span>');
            contentHtml.push('<p><a target="_blank" href="/scoreAnalyse/downloadModel?tnId='+ tnId +'&grade='+ grade +'">请先导出Excel模板,进行填写</a></p>');
            contentHtml.push('<p>温馨提示:上传与模板不一致的成绩单,系统无法识别</p>');
            contentHtml.push('</div>');
            contentHtml.push('</div>');
        }
        contentHtml.push('<div class="btn-box">');
        if (uploadFilePath) {
            contentHtml.push('<button class="btn btn-info save-btn" dataid="' + id + '" filePath="' + uploadFilePath + '">保存</button>');
        } else {
            contentHtml.push('<button class="btn btn-info save-btn">提交</button>');
        }
        contentHtml.push('<button class="btn btn-primary close-btn">取消</button>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        if (id) {
            layer.open({
                type: 1,
                title: '修改成绩',
                offset: 'auto',
                area: ['380px', '250px'],
                content: contentHtml.join('')
            });

        } else {
            layer.open({
                type: 1,
                title: '上传成绩',
                offset: 'auto',
                area: ['380px', '350px'],
                content: contentHtml.join('')
            });
            uploadFun();
        }
        $('.date-picker').datepicker({autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    },
    detailsList: function (uploadfilepath, id, grade, Pn, rows) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/listExamDetail', 'GET', {
            'examId': id,
            'grade': grade,
            'offset': Pn,
            'rows': rows
        }, function (res) {
            if (res.rtnCode == "0000000") {
                $(".tcdPageCode").attr('count', parseInt(Math.ceil(res.bizData.count / rows)));
                var myTemplate = Handlebars.compile($("body #details-template").html());
                layer.close();
                $('body #details-tbody').html(myTemplate(res));
                $('body #details-download-btn').attr('href', uploadfilepath);
                $('body #details-modify-btn').attr({
                    'grade': grade,
                    'examId': id,
                    'uploadfilepath': uploadfilepath
                });
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了");
        }, 'true');
    },
    getClass:function(grade,className){
        Common.ajaxFun('/scoreAnalyse/getClassesNameByGrade', 'GET', {
            'tnId': tnId,
            'grade': grade
        }, function (res) {
            if(res.rtnCode=="0000000"){
                var selClass = [];
                selClass.push('<option value="">请选择班级</option>');
                $.each(res.bizData,function(i,v){
                    selClass.push('<option value="'+ v +'">'+ v +'</option>');
                });
                $('.sel-class').append(selClass);
                if(className){
                    $('.sel-class option[value="'+ className +'"]').attr('selected','selected');
                }
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },
    detailsModify: function (grade,className, classRank, commonScore, diLiScore, examId, gradeRank, huaXueScore, id, liShiScore, selectCourses, shengWuScore, shuXueScore, studentName, totleScore, wuLiScore, yingYuScore, yuWenScore, zhengZhiScore) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal upload-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="name"> 姓名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="" value="' + studentName + '" placeholder="输入姓名" class="col-xs-10 col-sm-10 name"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="class"> 班级 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<select class="sel-class"></select>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group ">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right"> 主课成绩 </label>');
        contentHtml.push('<div class="col-sm-9 form-inline main-subject">');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-yuwen"> 语文 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + yuWenScore + '" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left subject-yuwen"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-shuxue"> 数学 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + shuXueScore + '" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left subject-shuxue"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-yingyu"> 英语 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + yingYuScore + '" placeholder="" class="col-xs-12 col-sm-12 center no-padding-right no-padding-left subject-yingyu"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group ">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right sel-label"> 选课成绩 </label>');
        contentHtml.push('<div class="col-sm-9 form-inline main-subject">');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-wuli"> 物理 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + wuLiScore + '" placeholder="" class="subject-wuli col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-huaxue"> 化学 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + huaXueScore + '" placeholder="" class="subject-huaxue col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-shengwu"> 生物 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + shengWuScore + '" placeholder="" class="subject-shengwu col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-zhengzhi"> 政治 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + zhengZhiScore + '" placeholder="" class="subject-zhengzhi col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
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
        contentHtml.push('<input type="text" id="" value="' + diLiScore + '" placeholder="" class="subject-dili col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-6 control-label no-padding-right no-padding-left" for="subject-lishi"> 历史 </label>');
        contentHtml.push('<div class="col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + liShiScore + '" placeholder="" class="subject-lishi col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group col-sm-6 no-padding-right no-padding-left">');
        contentHtml.push('<label class="col-sm-5 control-label no-padding-right no-padding-left" for="subject-jishu"> 通用技术 </label>');
        contentHtml.push('<div class="col-sm-3 no-padding-right no-padding-left">');
        contentHtml.push('<input type="text" id="" value="' + commonScore + '" placeholder="" class="subject-jishu col-xs-12 col-sm-12 center no-padding-right no-padding-left sel-course"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="top-class"> 班级排名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="" value="' + classRank + '" placeholder="输入班级排名" class="top-class col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="top-grade"> 年级排名 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="" value="' + gradeRank + '" placeholder="输入年级排名" class="top-grade col-xs-10 col-sm-10"/>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box">');
        contentHtml.push('<button class="btn btn-info details-save-btn" id="' + id + '">提交</button>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '修改',
            offset: 'auto',
            area: ['600px', '450px'],
            content: contentHtml.join(''),
            success: function (layero, index) {
                $('.details-save-btn').attr('closeIndex', index);
                that.getClass(grade,className);
            }
        });
    },
    deleteExam: function (id) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/deleteExam', 'GET', {
            'examId': id
        }, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                var radioV = $('input[name="results-radio"]:checked').val();
                that.getResultsList(radioV);
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了1");
        });
    },
    detailsModifyFun: function (id,grade) {
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getExamDetailById', 'GET', {
            'id': id
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
                that.detailsModify(grade,className, classRank, commonScore, diLiScore, examId, gradeRank, huaXueScore, id, liShiScore, selectCourses, shengWuScore, shuXueScore, studentName, totleScore, wuLiScore, yingYuScore, yuWenScore, zhengZhiScore);
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了");
        });
    },
    deleteExamDetail:function(examDetailId){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/deleteExamDetail', 'GET', {
            "examDetailId": examDetailId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                layer.msg('删除成功!');
                $('input[type="checkbox"][dataid="'+ examDetailId +'"]').parents('tr').remove();
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了");
        });
    },
    // 判断年级下是否有班
    getClassesNameByGrade:function(grade){
        var that = this;
        Common.ajaxFun('/scoreAnalyse/getClassesNameByGrade', 'GET', {
            'tnId':tnId,
            'grade':grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.length==0){
                    layer.msg('无班级信息,请设置班级!');
                }else{
                    that.uploadResults(grade);
                }
            } else {
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    },

};

var ResultsManagementIns = new ResultsManagementFun();

$(function () {
//默认第一个

    //ResultsManagementIns.getResultsList($('input[name="results-radio"]:checked:first').val());
    // 选择年级
    $('#grade-body').find('input[name="results-radio"]').click(function () {
        var radioV = $(this).val();
        $('#uploadResultsBtn').attr('grade',radioV);
        ResultsManagementIns.getResultsList(radioV);
    });
    $('#grade-body').find('input[name="results-radio"]:first').click();


    //上传成绩
    $('body').on('click', '#uploadResultsBtn', function () {
        var grade = $(this).attr('grade');
        ResultsManagementIns.getClassesNameByGrade(grade);

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
        var flg = false;
        Common.ajaxFun('/scoreAnalyse/checkExamName', 'GET', {
            'grade': radioV,
            'examName': examName
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData == true) {
                    flg = true;
                }
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg(res.msg);
        },true);
        if(globalParam.$oldExamName == $('#examName').val()){
            layer.closeAll();
            return false;
        }
        if(flg==true){
            layer.tips('考试名称已经存在,请修改考试名称后再提交!', $('#examName'));
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
                }else{
                    layer.msg(res.msg)
                }
            }, function (res) {
                layer.msg(res.msg);
            });
        } else {
            Common.ajaxFun('/scoreAnalyse/addExam.do', 'GET', {
                'tnId': tnId,
                'examName': examName,
                'examTime': examDate,
                'grade': radioV,
                'uploadFilePath': filePath
            }, function (res) {
                if (res.rtnCode == "0000000") {
                    layer.closeAll();
                    ResultsManagementIns.getResultsList(radioV);
                    layer.msg('添加成功!');
                }else{
                    layer.msg(res.msg)
                }
            }, function (res) {
                layer.msg(res.msg);
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
            layer.tips('修改只能选择一项', $(this));
            return false;
        }

        var resultsChecked = $('#results-tbody input:checked');
        var id = resultsChecked.attr('id');
        var examName = resultsChecked.attr('examName');
        var examTime = resultsChecked.attr('examTime');
        var uploadFilePath = resultsChecked.attr('uploadFilePath');
        var radioV = $('input[name="results-radio"]:checked').val();
        ResultsManagementIns.uploadResults(radioV,id, examName, examTime, uploadFilePath);
        globalParam.$oldExamName = $('#examName').val();
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
                ResultsManagementIns.detailsList(uploadfilepath, examId, grade, 0, 10);
                $(".tcdPageCode").createPage({
                    pageCount: $(".tcdPageCode").attr('count'),
                    current: 1,
                    backFn: function (p) {
                        ResultsManagementIns.detailsList(uploadfilepath, examId, grade, (p - 1) * 10, 10);
                    }
                });
            }
        });
        layer.full(index);
    });

    // 详情修改
    $('body').on('click', '#details-modify-btn', function () {
        var checkboxLen = $('#details-tbody input:checked').length;
        if (checkboxLen == 0) {
            layer.tips('选择一项', $(this));
            return false;
        }
        if (checkboxLen > 1) {
            layer.tips('修改只能选择一项', $(this));
            return false;
        }
        var detailsChecked = $('#details-tbody input:checked');
        var id = detailsChecked.attr('dataid');
        var grade = $(this).attr('grade');
        ResultsManagementIns.detailsModifyFun(id,grade);
    });


    // 详情保存
    $('body').on('click', '.details-save-btn', function () {
        var that = $(this);
        var id = $(this).attr('id'),
            closeIndex = $(this).attr('closeIndex'),
            examId = $('body #details-modify-btn').attr('examId'),
            uploadfilepath = $('body #details-modify-btn').attr('uploadfilepath'),
            grade = $('body #details-modify-btn').attr('grade'),
            name = $.trim($('.name').val()),
            detailSlass = $('.sel-class option:selected').val(),
            subjectShuxue = $.trim($('.subject-shuxue').val()),
            subjectYingyu = $.trim($('.subject-yingyu').val()),
            subjectWuli = $.trim($('.subject-wuli').val()),
            subjectHuaxue = $.trim($('.subject-huaxue').val()),
            subjectShengwu = $.trim($('.subject-shengwu').val()),
            subjectZhengzhi = $.trim($('.subject-zhengzhi').val()),
            subjectDili = $.trim($('.subject-dili').val()),
            subjectLishi = $.trim($('.subject-lishi').val()),
            subjectJishu = $.trim($('.subject-jishu').val()),
            topClass = $.trim($('.top-class').val()),
            topGrade = $.trim($('.top-grade').val()),
            subjectYuwen = $.trim($('.subject-yuwen').val());

        // var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
        var re = /^[0-9]*$/;

        if (name == '') {
            layer.tips('请输入姓名!', $('.name'));
            return false;
        }
        if (detailSlass == '') {
            layer.tips('请选择班级!', $('.sel-class'));
            return false;
        }
        if (subjectYuwen == '') {
            layer.tips('请输入语文成绩!', $('.subject-yuwen'));
            return false;
        }
        if (!re.test(subjectYuwen) || subjectYuwen > 200) {
            layer.tips('请输入正确的数字!', $('.subject-yuwen'));
            return false;
        }
        if (subjectShuxue == '') {
            layer.tips('请输入数学成绩!', $('.subject-shuxue'));
            return false;
        }
        if (!re.test(subjectShuxue) || subjectShuxue > 200) {
            layer.tips('请输入正确的数字!', $('.subject-shuxue'));
            return false;
        }
        if (subjectYingyu == '') {
            layer.tips('请输入英语成绩!', $('.subject-yingyu'));
            return false;
        }
        if (!re.test(subjectYingyu) || subjectYingyu > 200) {
            layer.tips('请输入正确的数字!', $('.subject-yingyu'));
            return false;
        }
        if (grade.indexOf('高一') >= 0) {
            if (subjectWuli == '') {
                layer.tips('请输入物理成绩!', $('.subject-wuli'));
                return false;
            }
            if (!re.test(subjectWuli) || subjectWuli > 200) {
                layer.tips('请输入正确的数字!', $('.subject-wuli'));
                return false;
            }
            if (subjectHuaxue == '') {
                layer.tips('请输入化学成绩!', $('.subject-huaxue'));
                return false;
            }
            if (!re.test(subjectHuaxue) || subjectHuaxue > 200) {
                layer.tips('请输入正确的数字!', $('.subject-huaxue'));
                return false;
            }
            if (subjectShengwu == '') {
                layer.tips('请输入生物成绩!', $('.subject-shengwu'));
                return false;
            }
            if (!re.test(subjectShengwu) || subjectShengwu > 200) {
                layer.tips('请输入正确的数字!', $('.subject-shengwu'));
                return false;
            }
            if (subjectZhengzhi == '') {
                layer.tips('请输入政治成绩!', $('.subject-zhengzhi'));
                return false;
            }
            if (!re.test(subjectZhengzhi) || subjectZhengzhi > 200) {
                layer.tips('请输入正确的数字!', $('.subject-zhengzhi'));
                return false;
            }
            if (subjectDili == '') {
                layer.tips('请输入地理成绩!', $('.subject-dili'));
                return false;
            }
            if (!re.test(subjectDili) || subjectDili > 200) {
                layer.tips('请输入正确的数字!', $('.subject-dili'));
                return false;
            }
            if (subjectLishi == '') {
                layer.tips('请输入历史成绩!', $('.subject-lishi'));
                return false;
            }
            if (!re.test(subjectLishi) || subjectLishi > 200) {
                layer.tips('请输入正确的数字!', $('.subject-lishi'));
                return false;
            }
            if (subjectJishu == '') {
                layer.tips('请输入通用技术成绩!', $('.subject-jishu'));
                return false;
            }
            if (!re.test(subjectJishu) || subjectJishu > 200) {
                layer.tips('请输入正确的数字!', $('.subject-jishu'));
                return false;
            }
            if (topClass == '') {
                layer.tips('请输入班级排名!', $('.top-class'));
                return false;
            }
            // if (!isNaN(topClass)) {
            //     layer.tips('请输入正确的数字!', $('.top-class'));
            //     return false;
            // }
            if (!re.test(topClass) || topClass > 1000) {
                layer.tips('请输入正确的数字!', $('.top-class'));
                return false;
            }
            if (topGrade == '') {
                layer.tips('请输入年级排名!', $('.top-grade'));
                return false;
            }
            // if (!isNaN(topGrade)) {
            //     layer.tips('请输入正确的数字!', $('.top-grade'));
            //     return false;
            // }
            if (!re.test(topGrade) || topGrade > 5000) {
                layer.tips('请输入正确的数字!', $('.top-grade'));
                return false;
            }
        } else {
            var valArr = [];
            $('.sel-course').each(function (i, v) {
                //console.log($(v).val());
                if ($.trim($(v).val()) == '') {
                    valArr.push($(v).val());
                }
            });
            var leng = (7-parseInt(valArr.length));
            if ( leng != 3) {
                layer.tips('选课必须填三项!', that);
                return false;
            }
        }
        Common.ajaxFun('/scoreAnalyse/modifyExamDetail', 'GET', {
            "id": id,
            "examId": examId,
            "className": detailSlass,
            "studentName": name,
            "yuWenScore": subjectYuwen,
            "shuXueScore": subjectShuxue,
            "yingYuScore": subjectYingyu,
            "wuLiScore": subjectWuli,
            "huaXueScore": subjectHuaxue,
            "shengWuScore": subjectShengwu,
            "zhengZhiScore": subjectZhengzhi,
            "diLiScore": subjectDili,
            "liShiScore": subjectLishi,
            "commonScore": subjectJishu,
            "classRank": topClass,
            "gradeRank": topGrade
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                ResultsManagementIns.detailsList(uploadfilepath, examId, grade, 0, 10);
                $(".tcdPageCode").createPage({
                    pageCount: $(".tcdPageCode").attr('count'),
                    current: 1,
                    backFn: function (p) {
                        ResultsManagementIns.detailsList(uploadfilepath, examId, grade, (p - 1) * 10, 10);
                    }
                });
                layer.close(closeIndex);
            }else{
                layer.msg(res.msg)
            }
        }, function (res) {
            layer.msg("出错了");
        });

    });
    // 删除详情列表
    $('body').on('click','#details-close-btn',function(){
        var checkboxLen = $('#details-tbody input[type="checkbox"]:checked').length;
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
            var  examDetailId = $('#details-tbody input[type="checkbox"]:checked').attr('dataid');
            ResultsManagementIns.deleteExamDetail(examDetailId);
        }, function () {
        });
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
        uploader.on('uploadSuccess', function (file, response) {
            console.log(file)
            console.log(response)
            $('#' + file.id).addClass('upload-state-done');
            $('.save-btn').attr('filePath', response.bizData.filePath);
            layer.msg('上传成功!');

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
}


