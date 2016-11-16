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


/*
 * ==================================================================
 * ============================专业信息详情模块============================
 * ==================================================================
 * */
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
                var template = Handlebars.compile($('#detail-content-tpl').html());
                $('#detail-content').html(template(res.bizData));
                if(res.bizData.fmRatio){
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
        Common.ajaxFun('/data/getMajorOpenUniversityList.do', 'GET', {
            majoredId: SpecialtyDetail.majoredId,
            majorType: SpecialtyDetail.majorType,
            page: "1",
            rows: "10"
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderMajorOpenUniversityList(res.bizData);
            }
        });
    },
    renderMajorOpenUniversityList:function(data){
        var that = this;
        var tpl = '';
        $.each(data.universityList,function(i,v){
            var rank = '',
                majorRank = '';
            if(v.rank){
                rank = '<i class="icon-flags"></i>全国排名：<span class="national-rank">'+ v.rank+'</span>'
            }
            if(v.majorRank){
                rank = '专业排名：<span class="national-rank">'+ v.majorRank+'</span>'
            }
            tpl += '' + '<li class="school-list">' +
                '<img src="http://123.59.12.77:8080/'+ v.photo_url+'"' +
                'class="school-logo">' +
                '<div class="top">' +
                '<span class="school-name" sid="'+ v.id+'">'+ v.name+'</span>'+rank+majorRank
                '</div>' +
                '<div class="middle">' +
                '<div id="property">' +
                '<span class="type-985">985</span> <span class="type-211">211</span>' +
                '<span class="type-yan">研</span> <span class="type-guo">国</span>' +
                '<span class="type-zi">自</span>' +
                '</div>' +
                '</div>' +
                '<div class="bottom">' +
                '<span class="province">'+ v.province+'</span>' +
                '<b>隶属：</b><span class="belong">'+ v.subjection+'</span>' +
                '<b>院校类型：</b><span class="school-type">'+ v.typeName+'</span>' +
                '<a target="_blank" href="'+ v.url+'" class="enter-site">' +
                '<span>进入网站</span>' +
                '</a>' +
                '</div>' +
                '</li>';
        })
        $('#open-school').append(tpl);
    },
    //获取专业就业方向
    getJobOrientation: function () {
        Common.ajaxFun('/data/getJobOrientation.do', 'GET', {'majoredId': SpecialtyDetail.majoredId}, function (res) {
            if (res.rtnCode == "0000000") {
                if(res.bizData.employmentRate){
                    $('#employmentRate').html(res.bizData.employmentRate)
                }else{
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
                    content: $('#specialty-detail').html(),
                    area: ['100%', '100%'],
                    maxmin: false
                })
            )
            $('#detail-content').remove(); //万年坑 ==========  一定要清楚
        });
        $(document).on('click', '.tab-detail-title .detail-tab', function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = parseInt($(this).index());
            $('.sub-content').eq(index).removeClass('dh').siblings().addClass('dh');
            if(index === 1){
                that.getMajorOpenUniversityList();
            }else if(index === 2){
                that.getJobOrientation();
            }
        });

    }
}
SpecialtyDetail.init();
