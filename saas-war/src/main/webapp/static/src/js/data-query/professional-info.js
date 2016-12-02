/*
 * @module:数据查询-职业信息
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */

/**
 * 职业信息
 * @type {{init: ProfessionalInfo.init, getProfessionCategory: ProfessionalInfo.getProfessionCategory, getProfessionalList: ProfessionalInfo.getProfessionalList, addEvent: ProfessionalInfo.addEvent}}
 */
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
        var that = this;
        Common.ajaxFun('/data/getProfessionCategory.do', 'GET', {'pid': pid}, function (res) {
            if (res.rtnCode == "0000000") {
                if (pid == '') {
                    var template = Handlebars.compile($('#profession-category-tpl').html());
                    $('#profession-category').html(template(res.bizData));
                    $('#detail-title').html('<li class="active" pid="">全部</li>')
                } else {
                    Handlebars.registerHelper('firstActive', function (data, options) {
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
        }, function () {
        }, 'true');
    },
    getProfessionalList: function (type) {
        var that = this;
        Common.ajaxFun('/data/getProfessionalList.do', 'GET', this.params, function (res) {
            if (res.rtnCode == "0000000") {
                type ? that.renderSearchList(res.bizData) : that.renderProfessionalList(res.bizData)
            }
        });
    },
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
    renderProfessionalList: function (dataJson) {
        var that = this;
        if (dataJson.rows.length == 0) {
            $('#tab-detail-content').html('<div style="text-align: center">暂无数据 ~</div>');
            $('#professional-load-more').hide();
            return false;
        }
        //总记录数 - 每页条数*第几页数 > 每页条数 [ 展示加载更多 ]
        if (dataJson.records - that.params.rows * (that.params.page - 1) > parseInt(that.params.rows)) {
            $('#professional-load-more').show();
        } else {
            $('#professional-load-more').hide();
        }
        var template = Handlebars.compile($('#tab-detail-content-tpl').html());
        if (that.params.page == '1') {
            $('#tab-detail-content').html(template(dataJson));
        } else {
            $('#tab-detail-content').append(template(dataJson));
        }
        $('#professional-loading-img').hide();
    },
    addEvent: function () {
        var that = this;
        var parentPid = '';
        $(document).on('click', '#profession-category span', function () {
            $(this).addClass('active').siblings().removeClass('active');
            $('.professional-detail .sub-title').text($(this).text());
            that.getProfessionCategory($(this).attr('pid'));
            that.params = {
                'queryparam': "", //搜索内容
                'rows': "10", //每页行数
                'page': '1',
                'professionTypeId': $(this).attr('pid'),
                'professionSubTypeId': $('#detail-title li:first').attr('pid')
            }
            parentPid = $(this).attr('pid');
            $('#professional-load-more').attr('page-no', 1);
            that.getProfessionalList();
        });
        //搜索oninput
        $('.search-input').unbind('input propertychange').bind('input propertychange', function () {
            that.params = {
                'queryparam': $(this).val(), //搜索内容
                'rows': "5", //备选方案五个
                'page': '1',
                'professionTypeId': $(this).attr('pid'),
                'professionSubTypeId': $('#detail-title li:first').attr('pid')
            }
            that.getProfessionalList('search');
        });
        //选择单个职业
        $(document).on('click', '#detail-title li', function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.params = {
                'queryparam': "", //搜索内容
                'rows': "10", //每页行数
                'page': '1',
                'professionTypeId': parentPid,
                'professionSubTypeId': $(this).attr('pid')
            }
            that.getProfessionalList();
            $('#professional-load-more').attr('page-no', 1);
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


/**
 * 职业详情模块
 * @type {{init: ProfessionalDetail.init, getProfessionalInfo: ProfessionalDetail.getProfessionalInfo, addEvent: ProfessionalDetail.addEvent}}
 */
var ProfessionalDetail = {
    init: function () {
        this.addEvent();
    },
    ////职业分类
    //getProfessionCategory:function(pId){
    //    Common.ajaxFun('/data/getProfessionCategory.do', 'GET', {'pid':pId}, function (res) {
    //        if (res.rtnCode == "0000000") {
    //            var dataJson = res.bizData;
    //            var template = Handlebars.compile($('#detail-content-tpl').html());
    //            $('#detail-content').html(template(dataJson));
    //            console.info(template(dataJson));
    //        }
    //    },function(){},'true');
    //},
    //根据id获取职业详情
    getProfessionalInfo: function (sId) {
        Common.ajaxFun('/data/getProfessionalInfo.do', 'GET', {'id': sId}, function (res) {
            if (res.rtnCode == "0000000") {
                var dataJson = res.bizData;
                var template = Handlebars.compile($('#detail-content-tpl').html());
                $('#detail-content').html(template(dataJson));
            }
        }, function () {
        }, 'true');
    },
    addEvent: function () {
        var that = this;
        var layerHtmlFn = function(id){
            that.getProfessionalInfo(id);
            layer.full(
                layer.open({
                    type: 1,
                    title: '职业信息详情',
                    content: $('#professional-detail'),
                    area: ['100%', '100%'],
                    maxmin: false
                })
            )
        }
        $(document).on('click', '#tab-detail-content a', function () {
            var pid = $(this).attr('pid');
            layerHtmlFn(pid)
        })
        //搜索按钮
        $(document).on('click', '.search-btn', function () {
            if($(this).attr('pid')){
                var pid = $(this).attr('pid');
                layerHtmlFn(pid)
            }else{
                layer.msg('没有搜索到');
            }
        })

    }
}
ProfessionalDetail.init();






