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
            $('#sub-majored-category').attr('type',$(this).attr('type'));
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
    getMajoredInfoById: function () {
        //Common.ajaxFun('/data/getMajoredInfoById.do', 'GET', {
        //    majoredId:SpecialtyDetail.majoredId,
        //    majorType:SpecialtyDetail.majorType,
        //}, function (res) {
            var res = {"bizData":{"degreeOffered":"文学学士","id":"50101","majorCode":"50101","majorIntroduce":"<strong class=\"cat\">主要实践教学环节</strong> \n<p>包括教学实习、论文写作等，一般安排8周左右。　　</p> \n<strong class=\"cat\">培养目标</strong> \n<p>本专业培养具备文艺理论素养和系统的汉语言文学知识，能在新闻文艺出版部门、高校、科研机构和机关企事业单位从事文学评论、汉语言文学教学与研究工作，以及文化、宣传方面的实际工作的汉语言文学高级专门人才。</p> \n<strong class=\"cat\">专业培养要求</strong> \n<p>本专业学生主要学习汉语和中国文学方面的基本知识，受到有关理论、发展历史、研究现状等方面的系统教育和业务能力的基本训练。</p> \n<strong class=\"cat\">毕业生具备的专业知识与能力</strong> \n<p>1．掌握马克思主义的基本原理和关于语言、文学的基本理论；2．掌握本专业的基础知识以及新闻、历史、哲学、艺术等学科的相关知识；3．具有文学修养和鉴赏能力以及较强的写作能力；4．了解我国关于语言文字和文学艺术的方针、政策和法规；5．了解本学科的前沿成就和发展前景；6．能阅读古典文献，掌握文献检索、资料查询的基本方法，具有一定的科学研究和实际工作能力。</p>","majorName":"汉语言文学","offerCourses":"语言学概论、古代汉语、现代汉语、文学概论、中国古代文学史、中国现当代文学史、马克思主义文论、中国古典文献学、汉语史、史学等。","schoolingDuration":"四年"},"rtnCode":"0000000","ts":1479263013951};
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#detail-content-tpl').html());
                $('#detail-content').html(template(res.bizData));
                console.info('aaaa',template(res.bizData))
            }
        //});
    },
    //获取专业开设院校
    getMajorOpenUniversityList: function () {
        Common.ajaxFun('/data/getMajorOpenUniversityList.do', 'GET', {
            majoredId: SpecialtyDetail.majoredId,
            majorType: SpecialtyDetail.majorType,
            page: "1",
            rows: "10"
        }, function (res) {
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
    //获取专业就业方向
    getJobOrientation: function () {
        Common.ajaxFun('/data/getJobOrientation.do', 'GET', {'majoredId': SpecialtyDetail.majoredId}, function (res) {
            var res = {"bizData": {"employmentRate": "80%", "majorId": 510401}, "rtnCode": "0000000", "ts": 1479262103593};
            if (res.rtnCode == "0000000") {
                console.info(res);
            }
        });
    },
    addEvent: function () {
        var that = this;
        //$(document).on('click', '#sub-majored-category span', function () {
            that.majorType = $('#sub-majored-category').attr('type')
            that.majoredId = $(this).attr('b-id');
            //majorType:1本科、2、专科
            that.getMajoredInfoById()
            that.getMajorOpenUniversityList();
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
        //})
    }
}
SpecialtyDetail.init();