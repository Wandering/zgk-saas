/*
 * @module:数据查询-职业信息
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */

var ProfessionalInfo = {
    init: function () {
        this.getProfessionCategory('');
        this.getProfessionalList();
        this.addEvent();
    },
    getProfessionCategory: function (pid) {
        Common.ajaxFun('/data/getProfessionCategory.do', 'GET', {'pid': pid}, function (res) {
            if (res.rtnCode == "0000000") {
                if (pid == '') {
                    var template = Handlebars.compile($('#profession-category-tpl').html());
                    $('#profession-category').html(template(res.bizData));
                    $('#detail-title').html('<li class="active">全部</li>')
                } else {
                    Handlebars.registerHelper('firstActive', function (data, options) {
                        console.info(data);
                        if (options.data.index == 0) {
                            return '<li class="active" pid="' + data.id + '">' + data.Type + '</li>'
                        } else {
                            return '<li pid="' + data.id + '">' + data.Type + '</li>'
                        }
                    })
                    var template = Handlebars.compile($('#detail-title-tpl').html());
                    $('#detail-title').html(template(res.bizData));
                }
            }
        });
    },
    getProfessionalList: function (pId, sId) {
        Common.ajaxFun('/data/getProfessionalList.do', 'GET', {
            'queryparam': "", //搜索内容
            'professionTypeId': pId, //大类型ID
            'professionSubTypeId': sId, //子类型ID
            'page': "1", //当前页数
            'rows': "10", //每页行数
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#tab-detail-content-tpl').html());
                $('#tab-detail-content').html(template(res.bizData));
            }
        });
    },
    addEvent: function () {
        var that = this;
        var parentPid = null;
        $(document).on('click', '#profession-category span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('.professional-detail .sub-title').text($(this).text());
            parentPid = $(this).attr('pid');
            that.getProfessionCategory(parentPid);
            that.getProfessionalList(parentPid, $('#detail-title li:first').attr('pid'));
        });
        $(document).on('click', '#detail-title li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var subId = $(this).attr('pid');
            that.getProfessionalList(parentPid, subId);
        });
    }
}
ProfessionalInfo.init();


