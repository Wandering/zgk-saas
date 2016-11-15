/*
 * @module:数据查询-职业信息
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */

var ProfessionalInfo = {
    init: function () {
        this.params = {
            'queryparam': "", //搜索内容
            'professionTypeId': '', //大类型ID
            'professionSubTypeId': '', //子类型ID
            'page': "1", //当前页数
            'rows': "10", //每页行数
        }
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
    getProfessionalList: function () {
        var that = this;
        Common.ajaxFun('/data/getProfessionalList.do', 'GET',this.params, function (res) {
            if (res.rtnCode == "0000000") {
                var dataJson = res.bizData;
                //总记录数 - 每页条数*第几页数 > 每页条数 [ 展示加载更多 ]
                if (dataJson.records - that.params.rows * (that.params.page-1) > parseInt(that.params.rows)) {
                    $('#professional-load-more').show();
                }
                var template = Handlebars.compile($('#tab-detail-content-tpl').html());
                if(that.params.page == '1'){
                    $('#tab-detail-content').html(template(dataJson));
                }else{
                    $('#tab-detail-content').append(template(dataJson));
                }
                $('#professional-loading-img').hide();
            }
        });
    },
    addEvent: function () {
        var that = this;
        var parentPid = '';
        $(document).on('click', '#profession-category span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('.professional-detail .sub-title').text($(this).text());
            that.params.professionTypeId = $(this).attr('pid');
            that.getProfessionCategory(that.params.professionTypeId);
            that.params.professionSubTypeId = $('#detail-title li:first').attr('pid');
            that.getProfessionalList();
        });
        $(document).on('click', '#detail-title li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var subId = $(this).attr('pid');
            that.params.professionSubTypeId = $(this).attr('pid');
            that.params.page = 1;
            that.getProfessionalList();
        });
        //加载更多
        $(document).on('click', '#professional-load-more', function () {
            var nowPage = parseInt($(this).attr('page-no'));
            $(this).attr('page-no', nowPage + 1).hide();
            that.params.page = nowPage + 1;
            that.getProfessionalList();
        })
    }
}
ProfessionalInfo.init();


