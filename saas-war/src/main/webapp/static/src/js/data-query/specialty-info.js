/*
 * @module:数据查询-专业信息
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 * */

/**
 * 数据查询-专业信息
 * @type {{init: SpecialtyInfo.init, getMajoredCategory: SpecialtyInfo.getMajoredCategory, getCategoryMajoredList: SpecialtyInfo.getCategoryMajoredList, addEvent: SpecialtyInfo.addEvent}}
 */
var SpecialtyInfo = {
    init: function () {
        this.getMajoredCategory('2'); //默认专科
        this.addEvent();
    },
    getMajoredCategory: function (type) {
        var that = this;
        Common.ajaxFun('/data/getMajoredCategory.do', 'GET', {'type': type}, function (res) {
            if (res.rtnCode == "0000000") {
                Handlebars.registerHelper('firstActive', function (data, options) {
                    if (options.data.index == 0) {
                        that.getCategoryMajoredList(data.id);
                        return '<span class="active" categoryId="' + data.id + '" parentId="' + data.parentId + '">' + data.name + '</span>'
                    } else {
                        return '<span categoryId="' + data.id + '" parentId="' + data.parentId + '">' + data.name + '</span>'
                    }
                })
                var template = Handlebars.compile($('#majored-category-tpl').html());
                $('#majored-category').html(template(res.bizData));
            }
        }, function () {
        }, 'true');
    },
    getCategoryMajoredList: function (cId) {
        Common.ajaxFun('/data/getCategoryMajoredList.do', 'GET', {'categoryId': cId}, function (res) {
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#sub-majored-category-tpl').html());
                $('#sub-majored-category').html(template(res.bizData));
            }
        });
    },
    addEvent: function () {
        var that = this;
        $(document).on('click', '.tab-li li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('#sub-majored-category').attr('type', $(this).attr('type'));
            that.getMajoredCategory($(this).attr('type'));
        });
        $(document).on('click', '#majored-category span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('#sub-title').html($(this).text());
            var cId = $(this).attr('categoryid');
            that.getCategoryMajoredList(cId);
        });
    }
}
SpecialtyInfo.init();


/**
 * 专业信息详情模块
 * @type {{init: SpecialtyDetail.init, getMajoredInfoByCode: SpecialtyDetail.getMajoredInfoByCode, getMajorOpenUniversityList: SpecialtyDetail.getMajorOpenUniversityList, renderMajorOpenUniversityList: SpecialtyDetail.renderMajorOpenUniversityList, getJobOrientation: SpecialtyDetail.getJobOrientation, addEvent: SpecialtyDetail.addEvent}}
 */

var SpecialtyDetail = {
    init: function () {
        this.addEvent();
    },
    //获取专业详情
    getMajoredInfoByCode: function () {
        Common.ajaxFun('/data/getMajoredInfoByCode.do', 'GET', {
            //majoredCode:'010103K'
            majoredCode: SpecialtyDetail.majoredId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                if ($.isEmptyObject(res.bizData)) {
                    $('#detail-content').html('抱歉,暂无数据');
                    return false;
                }
                var template = Handlebars.compile($('#detail-content-tpl').html());
                $('#detail-content').html(template(res.bizData));
                if (res.bizData.fmRatio) {
                    var malePercent = (res.bizData.fmRatio).split('-')[0];
                    var femalePercent = (res.bizData.fmRatio).split('-')[1];

                    $(".sex-bar .male-bar").css({
                        'width': '-webkit-calc(100% - ' + femalePercent + ' - 2px)',
                        'width': '-moz-calc(100% - ' + femalePercent + ' - 2px)',
                        'width': 'calc(100% - ' + femalePercent + ' - 2px)'
                    }).html(malePercent);
                    $(".sex-bar .female-bar").css({
                        'width': '-webkit-calc(100% - ' + malePercent + ' - 2px)',
                        'width': '-moz-calc(100% - ' + malePercent + ' - 2px)',
                        'width': 'calc(100% - ' + malePercent + ' - 2px)'
                    }).html(femalePercent);
                    $('.male-bar').text(femalePercent);
                    $('.female-bar').text(malePercent);
                }
            }
        }, function () {
        }, 'true');
    },
    //获取专业开设院校
    getMajorOpenUniversityList: function () {
        var that = this;
        Common.ajaxFun('/data/getMajorOpenUniversityList.do', 'GET', that.paramsData, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.universityList.length == 0){
                    $('#open-school').html('暂无数据');
                    $('#table-loading-img').hide();
                    return false;
                }
                that.renderMajorOpenUniversityList(res.bizData);
                if ((res.bizData.count - parseInt(that.paramsData.page)) > parseInt(that.paramsData.rows)) {
                    $('#specialty-load-more').removeClass('dh'); //显示加载更多
                }
                $('#specialty-load-more').show();
                $('#table-loading-img').hide();
            }
        });
    },
    renderMajorOpenUniversityList: function (data) {
        var that = this;
        var tpl = '';
        var propertyListTpl = '';
        $.each(data.universityList, function (i, v) {
            if (!v.photo_url.match('http')) {
                v.photo_url = 'http://123.59.12.77:8080/' + v.photo_url;
            }
            if (v.rank) {
                v.rank = '<i class="icon-flags"></i>全国排名：<span class="national-rank">' + v.rank + '</span>'
            } else {
                v.rank = '';
            }
            if (v.majorRank) {
                v.majorRank = '专业排名：<span class="national-rank">' + v.majorRank + '</span>'
            } else {
                v.majorRank = '';
            }
            $.each(v.propertys, function (j, k) {
                switch (k) {
                    case '985':
                        propertyListTpl += '<span class="type-985">985</span>';
                        break;
                    case '211':
                        propertyListTpl += '<span class="type-211">211</span>';
                        break;
                    case '985高校':
                        propertyListTpl += '<span class="type-985">985</span>';
                        break;
                    case '211工程':
                        propertyListTpl += '<span class="type-211">211</span>';
                        break;
                    case '有研究生院':
                        propertyListTpl += '<span class="type-yan">研</span>';
                        break;
                    case '含国防生':
                        propertyListTpl += '<span class="type-guo">国</span>';
                        break;
                    case '卓越计划':
                        propertyListTpl += '<span class="type-zhuo">卓</span>';
                        break;
                    case '自主招生':
                        propertyListTpl += '<span class="type-zi">自</span>';
                        break;
                }
            })
            tpl += '' + '<li class="school-list">' +
                '<img src="' + v.photo_url + '" class="school-logo">' +
                '<div class="top">' +
                '<span class="school-name" sid="' + v.id + '">' + v.name + '</span>' + v.rank + v.majorRank +
                '</div>' +
                '<div class="middle">' +
                '<div id="property">' + propertyListTpl +
                '</div>' +
                '</div>' +
                '<div class="bottom">' +
                '<span class="province">' + v.province + '</span>' +
                '<b>隶属：</b><span class="belong">' + v.subjection + '</span>' +
                '<b>院校类型：</b><span class="school-type">' + v.typeName + '</span>' +
                '<a target="_blank" href="' + v.url + '" class="enter-site">' +
                '<span>进入网站</span>' +
                '</a>' +
                '</div>' +
                '</li>';
            propertyListTpl = '';
        })
        $('#open-school').append(tpl);
    },
    //获取专业就业方向
    getJobOrientation: function () {
        Common.ajaxFun('/data/getJobOrientation.do', 'GET', {'majoredId': SpecialtyDetail.majoredId}, function (res) {
            if (res.rtnCode == "0000000") {
                if (res.bizData.employmentRate) {
                    $('#employmentRate').html((res.bizData.employmentRate) * 100 + '%');
                } else {
                    $('#employmentRate').html('暂无');
                }
            }
        });
    },
    addEvent: function () {
        var that = this;
        $(document).on('click', '#sub-majored-category span', function () {
            that.majorType = $('#sub-majored-category').attr('type')
            that.majoredId = $(this).attr('b-id');
            //majorType:1本科、2、专科
            that.getMajoredInfoByCode()
            that.getJobOrientation();

            layer.full(
                layer.open({
                    type: 1,
                    title: '专业信息详情',
                    content: $('#specialty-detail'),
                    area: ['100%', '100%'],
                    maxmin: false,
                })
            )
            that.paramsData = {
                majoredId: SpecialtyDetail.majoredId,
                majorType: SpecialtyDetail.majorType,
                page: "1",
                rows: "10"
            }
            that.getMajorOpenUniversityList();
            that.getJobOrientation();
        });
        $(document).on('click', '.tab-detail-title .detail-tab', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = parseInt($(this).index());
            $('.sub-content').eq(index).removeClass('dh').siblings().addClass('dh');
        });
        //开设院校加载更多
        $(document).on('click', '#specialty-load-more', function () {
            $('#table-loading-img').show();
            $('#specialty-load-more').hide();
            var nowPage = parseInt($(this).attr('page-no'));
            $(this).attr('page-no', nowPage + 1);
            that.paramsData.page = nowPage + 1;
            that.getMajorOpenUniversityList();
        });

    }
}
SpecialtyDetail.init();
