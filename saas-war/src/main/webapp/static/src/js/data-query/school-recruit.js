/*
 * @module:数据查询-院校招生计划 | 院校招生计划详情
 * @author:pdeng
 * @time:2016-11-14
 * @api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44447916
 *
 * */
var SchoolRecruit = {
    init: function () {
        this.areaId = Common.cookie.getCookie('countyId');
        //院校录取数据[参数数据]
        this.params = {
            year: "",
            areaId: "",
            property: "",
            batch: "",
            type: "2",
            page: '1',
            rows: "10"
        };
        this.getProvince();
        this.getYear('');
        this.getRemoteDataDictList();
        this.getBatchByYearAndArea('', '');
        this.addEvent();
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


                that.getCollegeUniversitiesEnrollment();
            }
        }, function () {
        }, 'true');
    },
    //院校录取数据
    getCollegeUniversitiesEnrollment: function () {
        var that = this;
        $('#table-loading-img').show();
        //Common.ajaxFun('/data/getGkAdmissionLineList.do', 'GET', this.params, function (res) {
        res = {
            "bizData": {
                "conditions": {},
                "page": 1,
                "pagesize": 10,
                "records": 150837,
                "rows": [{
                    "averageScore": 660,
                    "batchname": "本科一批",
                    "enrollingNumber": 149,
                    "highestScore": 702,
                    "id": 1,
                    "lowestScore": 618,
                    "name": "北京大学",
                    "property": "211工程,985高校,有研究生院,自主招生,含国防生",
                    "propertys": {"16": "含国防生", "2": "211工程", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 630,
                    "batchname": "本科一批",
                    "enrollingNumber": 102,
                    "highestScore": 662,
                    "id": 2,
                    "lowestScore": 598,
                    "name": "中国人民大学",
                    "property": "211工程,985高校,有研究生院,自主招生,含国防生",
                    "propertys": {"16": "含国防生", "2": "211工程", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 660,
                    "batchname": "本科一批",
                    "enrollingNumber": 209,
                    "highestScore": 712,
                    "id": 3,
                    "lowestScore": 608,
                    "name": "清华大学",
                    "property": "211工程,985高校,有研究生院,自主招生,含国防生,卓越计划",
                    "propertys": {"16": "含国防生", "2": "211工程", "32": "卓越计划", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 596,
                    "batchname": "本科一批",
                    "enrollingNumber": 205,
                    "highestScore": 652,
                    "id": 4,
                    "lowestScore": 540,
                    "name": "北京交通大学",
                    "property": "211工程,有研究生院,自主招生,含国防生,卓越计划",
                    "propertys": {"16": "含国防生", "2": "211工程", "32": "卓越计划", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 537,
                    "batchname": "本科一批",
                    "enrollingNumber": 1765,
                    "highestScore": 654,
                    "id": 5,
                    "lowestScore": 420,
                    "name": "北京工业大学",
                    "property": "211工程,自主招生,卓越计划",
                    "propertys": {"2": "211工程", "32": "卓越计划", "64": "自主招生"},
                    "subjection": "北京市",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 624,
                    "batchname": "本科一批",
                    "enrollingNumber": 261,
                    "highestScore": 662,
                    "id": 6,
                    "lowestScore": 586,
                    "name": "北京航空航天大学",
                    "property": "211工程,985高校,有研究生院,自主招生,含国防生,卓越计划",
                    "propertys": {"16": "含国防生", "2": "211工程", "32": "卓越计划", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "工业和信息化部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 611,
                    "batchname": "本科一批",
                    "enrollingNumber": 239,
                    "highestScore": 653,
                    "id": 7,
                    "lowestScore": 569,
                    "name": "北京理工大学",
                    "property": "211工程,985高校,有研究生院,自主招生,含国防生,卓越计划",
                    "propertys": {"16": "含国防生", "2": "211工程", "32": "卓越计划", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "工业和信息化部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 586,
                    "batchname": "本科一批",
                    "enrollingNumber": 249,
                    "highestScore": 652,
                    "id": 8,
                    "lowestScore": 520,
                    "name": "北京科技大学",
                    "property": "211工程,有研究生院,自主招生,含国防生,卓越计划",
                    "propertys": {"16": "含国防生", "2": "211工程", "32": "卓越计划", "64": "自主招生", "8": "有研究生院"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 499,
                    "batchname": "本科一批",
                    "enrollingNumber": 1144,
                    "highestScore": 600,
                    "id": 9,
                    "lowestScore": 398,
                    "name": "北方工业大学",
                    "property": "卓越计划",
                    "propertys": {"32": "卓越计划"},
                    "subjection": "北京市",
                    "typename": "理工",
                    "year": 2012
                }, {
                    "averageScore": 564,
                    "batchname": "本科一批",
                    "enrollingNumber": 159,
                    "highestScore": 634,
                    "id": 10,
                    "lowestScore": 494,
                    "name": "北京化工大学",
                    "property": "211工程,自主招生,卓越计划",
                    "propertys": {"2": "211工程", "32": "卓越计划", "64": "自主招生"},
                    "subjection": "教育部",
                    "typename": "理工",
                    "year": 2012
                }],
                "total": 0
            }, "rtnCode": "0000000", "ts": 1479310313309
        };
        if (res.rtnCode == "0000000") {
            var dataJson = res.bizData;
            //总记录数 - 每页条数*第几页数 > 每页条数 [ 展示加载更多 ]
            if (res.bizData.rows.length == 0) {
                $('#recruit-load-more').html('<span>暂无</span>');
                return false;
            } else {
                $('#recruit-load-more').html('<span>加载更多</span>');
            }
            if (dataJson.records - that.params.rows * (that.params.page - 1) > that.params.rows) {
                $('#recruit-load-more').show();
            }
            var template = Handlebars.compile($('#school-admission-plan-tpl').html());
            $('#school-admission-plan').append(template(dataJson))
            $('#table-loading-img').hide();
        } else {
            layer.msg(res.msg);
            $('.layui-layer-msg').css('left', '56%');
        }
        //});
    },
    addEvent: function () {
        //院校所属地
        var that = this;
        $(document).on('click', '#province-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            that.params = {
                'year': '',
                'property': '',
                'batch': '',
                'areaId': $(this).attr('provinceid'),
            }

            $(this).addClass('active').siblings().removeClass('active');
            that.getYear(that.params.areaId);
            that.getBatchByYearAndArea('', that.params.areaId);
            that.getCollegeUniversitiesEnrollment();
        })

        //招生年份
        $(document).on('click', '#year-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.year = $(this).text();
            that.getBatchByYearAndArea(that.params.year, that.params.areaId);
            that.getCollegeUniversitiesEnrollment();
        })

        //录取批次
        $(document).on('click', '#batch-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();
            $(this).addClass('active').siblings().removeClass('active');
            that.params.batch = $(this).attr('dictid');
            that.getCollegeUniversitiesEnrollment();
        })

        //院校特征
        $(document).on('click', '#feature-list span', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            that.params.property = $(this).attr('dictid')
            that.getCollegeUniversitiesEnrollment();
        })

        //文理切换
        $(document).on('click', '.tab-detail-title li', function () {
            $('#school-admission-plan').html('');
            $('#recruit-load-more').hide();

            $(this).addClass('active').siblings().removeClass('active');
            $(this).attr('page-no', 1);
            $('#recruit-load-more').hide();
            $('#school-admission-plan').html('');

            that.params.type = $(this).attr('type');
            that.params.page = '1';
            $('#recruit-load-more').attr('page-no', '1');
            that.getCollegeUniversitiesEnrollment();
        })
        //加载更多
        $(document).on('click', '#recruit-load-more', function () {
            var nowPage = parseInt($(this).attr('page-no'));
            $(this).attr('page-no', nowPage + 1).hide();
            that.params.page = nowPage + 1;
            that.getCollegeUniversitiesEnrollment();
        })
    }
}
SchoolRecruit.init();


/*
 * =========================================================
 * 数据查询 - 院校招生详情  majorType//科类 1：文科，2：理科
 * =========================================================
 * */
var SchoolRecruitDetail = {
    init: function () {
        this.addEvent();
    },
    //拉取院校基础信息
    getRemoteUniversityById: function (uId) {
        Common.ajaxFun('/data/getRemoteUniversityById.do', 'GET', {'universityId': uId}, function (res) {
            if (res.rtnCode == "0000000") {
                var template = Handlebars.compile($('#school-detail-top-tpl').html());
                $('#school-detail-top').html(template(res.bizData))
            }
        }, function () {
        }, 'true');
    },
    //拉取年份,文理科
    getMpConditions: function (uId) {
        Common.ajaxFun('/data/getMpConditions.do', 'GET', {'universityId': uId}, function (res) {
            if (res.rtnCode == "0000000") {
                var tplYear = '',
                    tplSubject = '',
                    foo = '';
                $.each(res.bizData,function(i,v){
                    v == '1' ? foo = '文科' : foo = '理科';
                    tplYear += '<option value="'+i+'">'+i+'年</option>';
                    tplSubject += '<option value="'+v+'">'+foo+'</option>';
                    foo = '';
                })
                console.info('tplYear',tplYear)
                console.info('tplSubject',tplSubject)
                $('#plan-year').html(tplYear);
                $('#plan-subject').html(tplSubject);
            }
        }, function () {
        }, 'true');
    },
    //拉取院校招生计划
    getUniversityMajorEnrollingPlanList: function (data) {
        $('#school-detail-loading-img').removeClass('dh');  //loading - show
        Common.ajaxFun('/data/getUniversityMajorEnrollingPlanList.do', 'GET', data, function (res) {
            if (res.rtnCode == "0000000") {
                $('#school-detail-loading-img').addClass('dh');  //loading - hide
                if (res.bizData.length > parseInt(data.rows)) {
                    $('#school-detail-load-more').removeClass('dh');  //more - show
                }else{
                    $('#school-detail-load-more').addClass('dh');  //more - hide
                }
                var template = Handlebars.compile($('#school-detail-bottom-tpl').html());
                $('#school-detail-bottom').html(template(res.bizData))
            }
        }, function () {
        }, 'true');
    },
    addEvent: function () {
        var that = this;
        $(document).on('click', '#school-admission-plan a', function () {
            that.getRemoteUniversityById($(this).attr('type'));
            //初始化params
            that.params = {
                universityId: $(this).attr('type'),
                year: "",
                majorType: "",
                batch: "",
                page: "1",
                rows: "30"
            }
            that.getUniversityMajorEnrollingPlanList(that.params);
            that.getMpConditions($(this).attr('type'));
            layer.full(
                layer.open({
                    type: 1,
                    title: '专业信息详情',
                    content: $('#school-detail-info').html(),
                    area: ['100%', '100%'],
                    maxmin: false
                })
            )
        })
    }
}
SchoolRecruitDetail.init();





