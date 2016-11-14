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
        this.getYear();
        this.getBatchByYearAndArea(new Date().getFullYear(),'2013');
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
        Common.ajaxFun('/data/getYears.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
    //根据年份和区域获取批次
    getBatchByYearAndArea: function (year,provinceId) {
        Common.ajaxFun('/data/getBatchByYearAndArea.do', 'GET', {
            year: year,
            provinceId: provinceId,
        }, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
}
SchoolAdmission.init();

