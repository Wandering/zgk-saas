/*
 * @module:数据查询-院校录取数据
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */


var SchoolAdmission = {
    init: function () {
        this.areaId = Common.cookie.getCookie('countyId');
        this.getProvince();
        this.getYear('');
        this.getRemoteDataDictList();
        this.params = {
            year: "",
            areaId: "",
            property: "",
            batch: "",
            type: "2",
            page: $('#admission-load-more').attr('page-no'),
            rows: "10"
        };
        this.getCollegeUniversitiesEnrollment(this.params);
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
        Common.ajaxFun('/data/getYears.do', 'GET', {'schoolProId':provinceId}, function (res) {
            if (res.rtnCode == "0000000") {
                var str = '';
                if (res.bizData.length == 0) {
                    $('#year-list').html('<span>暂无</span>');
                    return false;
                }
                Handlebars.registerHelper('firstActive', function (data, options) {
                    if (options.data.index == 0) {
                        //初始化渲染批次
                        SchoolAdmission.getBatchByYearAndArea(data, '-1');
                        return '<span class="active">' + data + '</span>'
                    } else {
                        return '<span>' + data + '</span>'
                    }
                })
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
                if (dataJson.records - that.params.rows * (that.params.page-1) > that.params.rows) {
                    $('#admission-load-more').show();
                }
                var template = Handlebars.compile($('#school-admission-plan-tpl').html());
                $('#school-admission-plan').append(template(dataJson))
                $('#table-loading-img').hide();
            } else {
                layer.msg(res.msg);
                $('.layui-layer-msg').css('left','56%');
            }
        });
    },
    eventListen: function () {
        //院校所属地
        var that = this;
        $(document).on('click', '#province-list span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.params.areaId = $(this).attr('provinceid');
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        //招生年份
        $(document).on('click', '#year-list span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.params.year = $(this).text();
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        //录取批次
        $(document).on('click', '#batch-list span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.params.batch = $(this).attr('dictid');
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        //录取批次
        $(document).on('click', '#feature-list span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.params.property = $(this).attr('dictid')
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        //文理切换
        $(document).on('click', '.tab-detail-title li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $(this).attr('page-no', 1);
            $('#admission-load-more').hide();
            $('#school-admission-plan').html('');
            that.params.type = $(this).attr('type')
            that.getCollegeUniversitiesEnrollment(that.params);
        })
        $(document).on('click', '#ladmission-load-more', function () {
            var nowPage = parseInt($(this).attr('page-no'));
            $(this).attr('page-no', nowPage + 1).hide();
            that.params.page = nowPage + 1;
            that.getCollegeUniversitiesEnrollment();
        })
    }
}
SchoolAdmission.init();
