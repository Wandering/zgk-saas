/*
 * @module:数据查询-院校招生计划 | 院校招生计划详情
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */
/**
 * SchoolRecruit 数据查询-院校招生计划 | 院校招生计划详情
 * @type {{init: SchoolRecruit.init, getProvince: SchoolRecruit.getProvince, getYear: SchoolRecruit.getYear, getBatch: SchoolRecruit.getBatch, getRemoteDataDictList: SchoolRecruit.getRemoteDataDictList, fetchSchoolEnroll: SchoolRecruit.fetchSchoolEnroll, renderSchoolEnroll: SchoolRecruit.renderSchoolEnroll, eventListen: SchoolRecruit.eventListen}}
 */
var SchoolRecruit = {
    init: function () {
        this.areaId = Common.cookie.getCookie('countyId');
        //院校录取数据[参数数据]
        this.params = {
            year: "",   //年份
            areaId: "", //省份code
            property: "", //院校特征
            batch: "",   //批次
            type: "2",  //1.文史 2.理工
            page: '1',  //第几页
            rows: "10"  //每页条数
        };
        this.eventListen();
        this.getProvince();
        this.getYear('');
        this.getBatch('', '');
        this.getRemoteDataDictList();
    },

    //获取省份
    getProvince: function () {
        Common.ajaxFun('/data/getProvinceList.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#province-list-tpl').html());
                $('#province-list').html(template(res.bizData))
            }
        });
    },

    //根据省份ID获取录取年份
    getYear: function (provinceId) {
        Common.ajaxFun('/data/getYears.do', 'GET', {
            schoolProId: provinceId,
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var str = '';
                if (res.bizData.length == 0) {
                    $('#year-list').html('<span>暂无</span>');
                    return false;
                }
                var template = Handlebars.compile($('#year-list-tpl').html());
                $('#year-list').html(template(res.bizData))
            }
        });
    },


    //根据年份和区域获取批次
    getBatch: function (year, provinceId) {
        Common.ajaxFun('/data/getBatchByYearAndArea.do', 'GET', {
            year: year,
            provinceId: provinceId,
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.length == 0) {
                    $('#batch-list').html('<span>暂无</span>');
                    return false;
                }
                var template = Handlebars.compile($('#batch-list-tpl').html());
                $('#batch-list').html(template(res.bizData))
                $('.common-select').show();
            }
        });
    },


    //院校特征
    getRemoteDataDictList: function () {
        var that = this;
        Common.ajaxFun('/data/getRemoteDataDictList.do', 'GET', {
            type: 'FEATURE',
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.length == 0) {
                    $('#feature-list').html('<span>暂无</span>');
                    return false;
                }
                var template = Handlebars.compile($('#feature-list-tpl').html());
                $('#feature-list').html(template(res.bizData))
                that.fetchSchoolEnroll(that.params);
            }
        }, function () {
        }, 'true');
    },

    //拉取录取数据
    fetchSchoolEnroll: function (data, more) {
        var that = this;
        $('#table-loading-img').show();
        console.log('data', JSON.stringify(data));
        Common.ajaxFun('/data/getGkAdmissionLineList.do', 'GET', data, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderSchoolEnroll(res.bizData, more);
            } else {
                layer.msg(res.msg);
                $('.layui-layer-msg').css('left', '56%');
            }
        });
    },
    //渲染录取数据
    renderSchoolEnroll: function (dataJson, more) {
        var that = this;
        $('#table-loading-img').hide();
        var template = Handlebars.compile($('#school-admission-plan-tpl').html());
        more == 'more' ? $('#school-admission-plan').append(template(dataJson)) : $('#school-admission-plan').html(template(dataJson))
        //总记录数 - 每页条数*第几页数 > 每页条数 [ 展示加载更多 ]
        if (dataJson.records - that.params.rows * (that.params.page - 1) > that.params.rows) {
            $('#recruit-load-more').show().attr('page-no', parseInt(that.params.page));
            $('#no-data').remove();
        } else if (dataJson.records == 0) {
            $('.admission-detail .table-list').after('<p id="no-data" style="text-align: center;margin-top: 40px;">暂无数据</p>');
        } else {
            $('#recruit-load-more').hide();
        }
    },


    //添加事件
    eventListen: function () {
        //院校所属地
        var that = this;
        $(document).on('click', '#province-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            $('#no-data').remove();

            $(this).addClass('active').siblings().removeClass('active');


            that.params.areaId = $(this).attr('provinceid');
            that.params.year = "";
            that.params.batch = "";
            that.params.type = "2";
            that.params.page = "1";
            that.params.rows = "10";


            that.getYear(that.params.areaId);
            that.getBatch(that.params.year, that.params.areaId);
            that.fetchSchoolEnroll(that.params);
        })

        //招生年份
        $(document).on('click', '#year-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            ('#no-data').remove();

            that.params.year = $(this).attr('yearId');
            that.params.batch = '';
            that.params.page = "1";
            that.params.rows = "10";

            that.getBatch(that.params.year, that.params.areaId);
            that.fetchSchoolEnroll(that.params);
        })

        //录取批次
        $(document).on('click', '#batch-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            $('#no-data').remove();

            that.params.batch = $(this).attr('dictid');
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })

        //院校特征
        $(document).on('click', '#feature-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            $('#no-data').remove();

            that.params.property = $(this).attr('dictid')
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })

        //文理切换
        $(document).on('click', '.tab-detail-title li', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            $('#no-data').remove();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.type = $(this).attr('type')
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })
        //加载更多
        $(document).on('click', '#recruit-load-more', function () {
            that.params.page = parseInt(that.params.page) + 1
            $(this).attr({
                'disabled': false,
                'page-no': that.params.page
            }).hide();
            that.fetchSchoolEnroll(that.params, 'more')
        })
    }
}
SchoolRecruit.init();


/**
 * 数据查询 - 院校招生详情  majorType//科类 1：文科，2：理科
 * @type {{init: SchoolRecruitDetail.init, getSchoolBaseInfo: SchoolRecruitDetail.getSchoolBaseInfo, getMpConditions: SchoolRecruitDetail.getMpConditions, getUniversityMajorEnrollingPlanList: SchoolRecruitDetail.getUniversityMajorEnrollingPlanList, addEvent: SchoolRecruitDetail.addEvent}}
 */
var SchoolRecruitDetail = {
    init: function () {
        this.eventListen();
    },
    //拉取院校基础信息
    getSchoolBaseInfo: function (uId) {
        Common.ajaxFun('/data/getRemoteUniversityById.do', 'GET', {'universityId': uId}, function (res) {
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#school-detail-top-tpl').html());
                $('#school-detail-top').html(template(res.bizData))
            }
        }, function () {
        }, 'true');
    },
    //拉取年份,文理科
    getMpConditions: function (uId) {
        var that = this;
        Common.ajaxFun('/data/getMpConditions.do', 'GET', {'universityId': uId}, function (res) {
            if (res.rtnCode == "0000000") {
                var tplYear = '',
                    tplSubject = '',
                    foo = '',
                    yearArr = [];
                that.yearAndTypeBoj = res.bizData;
                if ($.isEmptyObject(that.yearAndTypeBoj)) {
                    $('.select-condition').hide();
                    return false;
                }
                $.each(res.bizData, function (i, v) {
                    tplYear += '<option value="' + i + '">' + i + '年</option>';
                    yearArr.push(i);  //[2013,2014,2015]
                    if (yearArr.sort()[0] == i) {
                        for (var j = 0; j < res.bizData[yearArr.sort()[0]].length; j++) {
                            foo = res.bizData[yearArr.sort()[0]][j];
                            if (res.bizData[yearArr.sort()[0]][j] == 1) {
                                tplSubject += '<option value="' + foo + '">文科</option>';
                            } else {
                                tplSubject += '<option value="' + foo + '">理科</option>';
                            }
                        }
                    }
                })
                $('#plan-year').html(tplYear);
                $('#plan-subject').html(tplSubject);
            }
        }, function () {
        }, 'true');
    },
    //拉取院校招生计划
    getUniversityMajorEnrollingPlanList: function (data) {
        $('#school-detail-loading-img').removeClass('dh');  //loading - show
        Common.ajaxFun('/data/getUniversityMajorEnrollingPlanList.do', 'GET', data, function (res) {
            if (res.rtnCode == "0000000") {
                $('#school-detail-loading-img').addClass('dh');  //loading - hide
                if (res.bizData.length > parseInt(data.rows)) {
                    $('#school-detail-load-more').removeClass('dh');  //more - show
                } else {
                    $('#school-detail-load-more').addClass('dh');  //more - hide
                }
                var template = Handlebars.compile($('#school-detail-bottom-tpl').html());
                $('#school-detail-bottom').html(template(res.bizData))
            }
        }, function () {
        }, 'true');
    },
    eventListen: function () {
        var that = this;
        $(document).on('click', '#school-admission-plan a', function () {
            that.getSchoolBaseInfo($(this).attr('type'));
            that.getMpConditions($(this).attr('type'));
            //初始化params
            that.params = {
                universityId: $(this).attr('type'),
                year: $('#plan-year').val(),
                majorType: $('#plan-subject').val(),
                batch: "",
                page: "1",
                rows: "50"
            }
            that.getUniversityMajorEnrollingPlanList(that.params);
            layer.full(
                layer.open({
                    type: 1,
                    title: '专业信息详情',
                    content: $('#school-detail-info'),
                    area: ['100%', '100%'],
                    maxmin: false
                })
            )
        });
        $(document).on('change', '#plan-year', function () {
            var checkYear = $(this).val();
            var values = that.yearAndTypeBoj[parseInt(checkYear)],
                tplSubject = '',
                foo = '';
            for (var i = 0; i < values.length; i++) {
                values[i] == 1 ? foo = '文科' : foo = '理科'
                tplSubject += '<option value="' + (i + 1) + '">' + foo + '</option>';
            }
            $('#plan-subject').html(tplSubject);

            that.params.year = checkYear;
            that.params.majorType = $('#plan-subject').val();
            that.params.batch = "";
            that.params.page = "1";
            that.params.rows = "50";

            that.getUniversityMajorEnrollingPlanList(that.params);
        })
        $(document).on('change', '#plan-subject', function () {
            var checkBatch = $(this).val();
            that.params.year = $('#plan-year').val();
            that.params.majorType = checkBatch;
            that.params.batch = "";
            that.params.page = "1";
            that.params.rows = "50";

            that.getUniversityMajorEnrollingPlanList(that.params);
        })
    }
}
SchoolRecruitDetail.init();





