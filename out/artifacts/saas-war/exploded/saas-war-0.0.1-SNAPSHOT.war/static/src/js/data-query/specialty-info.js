/*
 * @module:数据查询-专业信息
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 * */


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
                        return '<span class="active" categoryId="'+data.id+'" parentId="' + data.parentId + '">' + data.name + '</span>'
                    } else {
                        return '<span categoryId="'+data.id+'" parentId="' + data.parentId + '">' + data.name + '</span>'
                    }
                })
                var template = Handlebars.compile($('#majored-category-tpl').html());
                $('#majored-category').html(template(res.bizData));
            }
        },function(){},'true');
    },
    getCategoryMajoredList: function (cId) {
        Common.ajaxFun('/data/getCategoryMajoredList.do', 'GET', {'categoryId':cId}, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
                var template = Handlebars.compile($('#sub-majored-category-tpl').html());
                $('#sub-majored-category').html(template(res.bizData));
            }
        });
    },
    addEvent: function () {
        var that = this;
        $(document).on('click', '.tab-li li', function () {
            $(this).addClass('active').siblings().removeClass('active');
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



/*
 * ==================================================================
 * ============================专业信息详情模块============================
 * ==================================================================
 * */
var SpecialtyDetail = {
    init:function(){
        this.addEvent();
    },
    addEvent: function () {
        var that = this;
        $(document).on('click','#sub-majored-category span',function(){
            layer.full(
                layer.open({
                    type: 1,
                    title:'专业信息详情',
                    content: $('#specialty-detail').html(),
                    area: ['100%','100%'],
                    maxmin: false
                })
            )
        })
    }
}
SpecialtyDetail.init();