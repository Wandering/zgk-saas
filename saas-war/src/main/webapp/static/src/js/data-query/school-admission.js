/*
 * @module:数据查询-院校录取数据
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */


var SchoolAds = {
    init: function () {
        this.params = {
            year: "",   //年份
            areaId: "", //省份code
            property: "", //院校特征
            batch: "",   //批次
            type: "2",  //1.文史 2.理工
            page: '1',  //第几页
            rows: "10"  //每页条数
        }
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
            $('#admission-load-more').show().attr('page-no', parseInt(that.params.page));
            $('#no-data').remove();
        } else if (dataJson.records == 0) {
            $('.admission-detail .table-list').after('<p id="no-data" style="text-align: center;margin-top: 40px;">暂无数据</p>');
        } else {
            $('#admission-load-more').hide();
        }
    },


    //渲染搜索数据
    renderSearchList: function (data) {
        var dataJson = data.rows;
        $(document).on('click', function (e) {
            $('#search-list').html('').hide();
            e.stopPropagation();
        });
        if (dataJson.length == '') {
            $('#search-list').html('<span>暂无数据</span>')
            return false;
        }
        var tpl = '';
        $.each(dataJson, function (i, v) {
            if (v.professionName.length > 12) {
                v.professionName = v.professionName.substr(0, 12) + '...';
            }
            tpl += '<li pid="' + v.id + '">' + v.professionName + '</li>'
        })
        $('#search-list').html(tpl).show();
        $(document).on('click', '#search-list li', function () {
            $('.search-input').val($(this).text());
            $('.search-btn').attr('pid', $(this).attr('pid'));
            $('#search-list').html('').hide();
        })
    },


    eventListen: function () {
        var that = this;

        //院校所属地
        var that = this;
        $(document).on('click', '#province-list span', function () {
            $('#school-admission-plan').html('');
            $('#admission-load-more').hide();
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
            $('#admission-load-more').hide();
            $('#no-data').remove();
            $(this).addClass('active').siblings().removeClass('active');

            that.params.year = $(this).attr('yearId');
            that.params.batch = '';
            that.params.page = "1";
            that.params.rows = "10";

            that.getBatch(that.params.year, that.params.areaId);
            that.fetchSchoolEnroll(that.params);
        })


        //录取批次
        $(document).on('click', '#batch-list span', function () {
            $('#admission-load-more').hide();
            $('#no-data').remove();
            $(this).addClass('active').siblings().removeClass('active');
            that.params.batch = $(this).attr('dictid');
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })


        //院校特征
        $(document).on('click', '#feature-list span', function () {
            $('#admission-load-more').hide();
            $('#no-data').remove();
            $(this).addClass('active').siblings().removeClass('active');
            that.params.property = $(this).attr('dictid')
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })


        // 文理切换
        $(document).on('click', '.tab-detail-title li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('#admission-load-more').hide();
            $('#no-data').remove();
            $('#school-admission-plan').html('');
            that.params.type = $(this).attr('type')
            that.params.page = "1";
            that.params.rows = "10";
            that.fetchSchoolEnroll(that.params);
        })


        //加载更多
        $(document).on('click', '#admission-load-more', function () {
            that.params.page = parseInt(that.params.page) + 1
            $(this).attr({
                'disabled': false,
                'page-no': that.params.page
            }).hide();
            that.fetchSchoolEnroll(that.params, 'more')
        })

        //搜索
        $('.search-input').unbind('input propertychange').bind('input propertychange', function () {

        });

    },
}
SchoolAds.init();
