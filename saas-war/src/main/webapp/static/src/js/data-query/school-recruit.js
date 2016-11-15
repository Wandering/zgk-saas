/*
 * @module:数据查询-院校招生计划 | 院校招生计划详情
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */
var SchoolRecruit = {
    init: function () {
        this.areaId = Common.cookie.getCookie('countyId');
        this.getProvince();
        this.getYear('');
        this.getRemoteDataDictList();
        this.getBatchByYearAndArea('', '');

        this.params = {
            year: "",
            areaId: "",
            property: "",
            batch: "",
            type: "2",
            page: $('#recruit-load-more').attr('page-no'),
            rows: "10"
        };
        this.eventListen();
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
                //Handlebars.registerHelper('firstActive', function (data, options) {
                //    if (options.data.index == 0) {
                //初始化渲染批次
                //SchoolRecruit.getBatchByYearAndArea(res.bizData[0], '-1');
                //        return '<span class="active">' + data + '</span>'
                //    } else {
                //        return '<span>' + data + '</span>'
                //    }
                //})
                var template = Handlebars.compile($('#year-list-tpl').html());
                $('#year-list').html(template(res.bizData))
            }
        });
    },
    //根据年份和区域获取批次
    getBatchByYearAndArea: function (year, provinceId) {
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
                that.getCollegeUniversitiesEnrollment(that.params);
            }
        });
    },
    //院校录取数据
    getCollegeUniversitiesEnrollment: function (data) {
        var that = this;
        $('#table-loading-img').show();
        Common.ajaxFun('/data/getGkAdmissionLineList.do', 'GET', data, function (res) {
            if (res.rtnCode == "0000000") {
                var dataJson = res.bizData;
                //总记录数 - 每页条数*第几页数 > 每页条数 [ 展示加载更多 ]
                if (dataJson.records - that.params.rows * (that.params.page - 1) > that.params.rows) {
                    $('#recruit-load-more').show();
                }
                var template = Handlebars.compile($('#school-admission-plan-tpl').html());
                $('#school-admission-plan').append(template(dataJson))
                $('#table-loading-img').hide();
            } else {
                layer.msg(res.msg);
                $('.layui-layer-msg').css('left', '56%');
            }
        });
    },
    eventListen: function () {
        //院校所属地
        var that = this;
        $(document).on('click', '#province-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            that.params = {
                'year': '',
                'property': '',
                'batch': '',
                'areaId': $(this).attr('provinceid'),
            }

            $(this).addClass('active').siblings().removeClass('active');
            that.getYear(that.params.areaId);
            that.getCollegeUniversitiesEnrollment(that.params);
        })

        //招生年份
        $(document).on('click', '#year-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.year = $(this).text();
            that.getBatchByYearAndArea(that.params.year, that.params.areaId);
            that.getCollegeUniversitiesEnrollment(that.params);
        })

        //录取批次
        $(document).on('click', '#batch-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.batch = $(this).attr('dictid');
            that.getCollegeUniversitiesEnrollment(that.params);
        })

        //院校特征
        $(document).on('click', '#feature-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.property = $(this).attr('dictid')
            that.getCollegeUniversitiesEnrollment(that.params);
        })

        //文理切换
        $(document).on('click', '.tab-detail-title li', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            $(this).attr('page-no', 1);
            $('#recruit-load-more').hide();
            $('#school-admission-plan').html('');
            that.params.type = $(this).attr('type')
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        //加载更多
        $(document).on('click', '#recruit-load-more', function () {
            var nowPage = parseInt($(this).attr('page-no'));
            $(this).attr('page-no', nowPage + 1).hide();
            that.params.page = nowPage + 1;
            that.getCollegeUniversitiesEnrollment();
        })
    }
}
SchoolRecruit.init();


/*
 * =========================================================
 * 数据查询 - 院校招生详情
 * =========================================================
 * */
var SchoolRecruitDetail = {
    init: function () {

    }
}
SchoolRecruitDetail.init();





