/**
 * Created by machengcheng on 16/11/14.
 */

$(function () {
    var trinityData = new TrinityData();
    trinityData.loadPage(0, trinityData.universityRows);
});

function TrinityData () {
    this.universityOffset = 0;
    this.universityRows = 10;
    this.universityCount = 0;
    this.pageCount = 1;
    this.init();
}
TrinityData.prototype = {
    constructor: TrinityData,
    init: function () {
        this.getEnrollTotalData();
    },
    getEnrollTotalData: function () {
        Common.ajaxFun('/selectClassesGuide/getEnrollingNumberByBatch.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.enrollingNumberByBatch;
                var datas = {
                    batchs: [],
                    batchNames: [],
                    planEnrollingNumbers: []
                };
                $.each(data, function (i, k) {
                    datas.batchNames.push(k.batchName);
                    datas.batchs.push({
                        value: k.majorNumber,
                        name: k.batchName
                    });
                    datas.planEnrollingNumbers.push(k.planEnrollingNumber);
                });
                enrollUniversityMajorTotal(datas.batchNames, datas.batchs);
                planTotalLineChart(datas.batchNames, datas.planEnrollingNumbers);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    loadPage: function (offset, rows) {
        var that = this;
        this.universityOffset = offset;
        this.universityRows = rows;
        Common.ajaxFun('/trineController/getEnrollingInfo.do', 'GET', {
            'offset': that.universityOffset,
            'rows': that.universityRows
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                that.showData(data);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    showData: function (result) {
        this.universityCount = result.count;
        this.pageCount = Math.ceil(this.universityCount / this.universityRows);

        var template = Handlebars.compile($("#enroll-university-list-data-template").html());
        $.each(result.enrollingInfo, function (i, k) {
            if (k.rank === "" || k.rank === undefined || k.rank === null || k.rank === "0") {
                k.rank = "-";
            }
            if (k.planEnrollingNumber === "" || k.planEnrollingNumber === undefined || k.planEnrollingNumber === null || k.planEnrollingNumber === "0") {
                k.planEnrollingNumber = "-";
            }
        });
        $('#enroll-university-list').html(template(result.enrollingInfo));
        this.pagination();
    },
    pagination: function () {
        var that = this;
        $(".pagination").createPage({
            pageCount: Math.ceil(that.universityCount / that.universityRows),
            current: Math.ceil(that.universityOffset / that.universityRows) + 1,
            backFn: function (p) {
                $(".pagination-bar .current-page").html(p);
                that.universityOffset = (p - 1) * that.universityRows;
                that.loadPage(that.universityOffset, that.universityRows, false);
            }
        });
    }
};

function Page() {
    this.majorCount = 0;
    this.pageCount = 1;
    var that = this;
    this.loadPage = function (offset, rows) {
        majorOffset = offset;
        majorRows = rows;
        var subjects = util.cookie.getCookieValue("selectedMajorBySubjects").split(',');
        var queryType = parseInt($("#keywords-university-major").attr("category"));
        var queryId = parseInt($("#keywords-university-major").attr("uid"));
        var provinceCode = condition.provinceCode;
        var batch = condition.batch;
        var type = condition.type;
        util.ajaxFun(util.INTERFACE_URL.getMajorList, 'GET', {
            'subject1': subjects[0],
            'subject2': subjects[1],
            'subject3': subjects[2],
            'queryType': queryType, //类型，1为院校，2为专业, 可选
            'queryId': queryId, //查询院校id或专业code
            'provinceCode': provinceCode, //学校所在地，可选
            'batch': batch, //批次，可选
            'type': type, //类型，可选
            'offset': offset, //起始条数，可选，默认为0
            'rows': rows //查询条数，可选，默认为10
        }, function (res) {
            if (res.rtnCode == '0000000') {
                var data = res.bizData;
                that.showData(data);
            }
        });
    };
    this.showData = function (result) {
        that.majorCount = result.count;
        that.pageCount = Math.ceil(that.majorCount / majorRows);
        $(".pagination-bar .page-count").html(that.pageCount);
        $(".pagination-bar .record-count").html(result.count);
        var dictBatchType = {
            "0": "零批次",
            "1": "一批本科",
            "10": "普通批次",
            "11": "本科一批A",
            "111": "本科一批A1",
            "12": "本科一批B",
            "2": "二批本科",
            "21": "本科二批A",
            "22": "本科二批B",
            "23": "本科二批C",
            "4": "三批本科",
            "41": "本科三批A",
            "411": "本科三批A1",
            "412": "本科三批A2",
            "42": "本科三批B",
            "8": "高职（专科）",
            "81": "专科一批",
            "82": "专科二批",
            "83": "专科A类",
            "84": "专科B类"
        };
        handlebars.registerHelper('dictBatchType', function (data) {
            return dictBatchType[parseInt(data)];
        });
        var template = handlebars.compile($("#major-detail-data-template").html());
        $.each(result.majorList, function (i, k) {
            if (!k.selectSubject) {
                k.selectSubject = '不限';
            } else {
                k.selectSubject = k.selectSubject.split(' ').join('、');
            }
        });
        $('#major-detail-data-list').html(template(result.majorList));
        $.each(result.majorList, function (i, k) {
            switch (parseInt(k.isFavorite)) {
                case 0:
                    $('#major-detail-table .collect-icon').eq(i).css({
                        //'background': 'url(../img/icon_collect.png) no-repeat'
                        'background': 'url(' + iconCollect + ') no-repeat'
                    });
                    break;
                case 1:
                    $('#major-detail-table .collect-icon').eq(i).css({
                        //'background': 'url(../img/icon_collected.png) no-repeat'
                        'background': 'url(' + iconCollected + ') no-repeat'
                    });
                    break;
                default:
                    break;
            }
        });
        that.pagination();
    };
    this.pagination = function () {
        $(".pagination").createPage({
            pageCount: Math.ceil(that.majorCount / majorRows),
            current: Math.ceil(majorOffset / majorRows) + 1,
            backFn: function (p) {
                $(".pagination-bar .current-page").html(p);
                majorOffset = (p - 1) * majorRows;
                that.loadPage(majorOffset, majorRows, false);
            }
        });
    }
}

//招生院校专业总数
function enrollUniversityMajorTotal (batchNames, batchs) {
    var enrollTotalChart = echarts.init(document.getElementById('enrollTotalChart'));
    var enrollPieOption = {
        title: {
            show: true,
            text: "招生院校专业总数：2345个专业",
            x: 'left',
            top: 'top',
            bottom: -30,
            textStyle: {
                color: '#4A4A4A',
                fontWeight: 'normal',
                fontSize: 14
            }
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            selectedMode: false,
            orient: 'vertical',
            x: 'center',
            top: 'middle',
            left: 250,
            itemWidth: 12,
            itemHeight: 12,
            itemGap: 22,
            data: batchNames, //['一批本科', '二批本科', '三批本科', '高职高专'],
            textStyle: {
                color: ['#4A4A4A', '#4A4A4A', '#4A4A4A', '#4A4A4A'],
                fontSize: 14
            }
        },
        calculable: true,
        series: [
            {
                name: '',
                type: 'pie',
                selectedOffset: 0,
                legendHoverLink: false,
                hoverAnimation: false,
                radius : [0, '70%'],
                center: ['23%', '50%'],
                avoidLabelOverlap: false,
                color: ['#A98FCB', '#EF8B87', '#C0DD7D', '#65A1DD'],
                label: {
                    normal: {
                        show: false,
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: batchs
                //data:[
                //    {value: 2953, name: '一批本科'},
                //    {value: 3657, name: '二批本科'},
                //    {value: 6321, name: '三批本科'},
                //    {value: 8901, name: '高职高专'}
                //]
            }
        ]
    };
    enrollTotalChart.setOption(enrollPieOption);
}

//招生计划总数
function planTotalLineChart (batchNames, planEnrollingNumbers) {
    var planTotalLineChart = echarts.init(document.getElementById('planTotalLineChart'));
    var planTotalLineOption = {
        title: {
            text: '招生计划总数：12345人',
            textStyle: {
                color: '#4A4A4A',
                fontWeight: 'normal',
                fontSize: 14
            }
        },
        color: ['#4992CC'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '2%',
            right: '8%',
            bottom: '1%',
            top: '13%',
            containLabel: true,
            borderWidth: 0,
            backgroundColor: 'transparent'
        },
        xAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            axisTick: {
                show: false
            },
            axisLabel: {
                show: true
            },
            axisLine: {
                lineStyle: {
                    color: '#D8D8D8'
                }
            },
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'category',
            axisTick: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#D8D8D8'
                }
            },
            splitLine: {
                show: false
            },
            data: batchNames //['高职高专', '三批本科', '二批本科', '一批本科']
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                barMinHeight: 26,
                barCategoryGap: '40%',
                data: planEnrollingNumbers, //[8917, 6989, 5342, 3456],
                label: {
                    normal: {
                        show: true,
                        position: 'insideRight',
                        formatter: '{c}人',
                        textStyle: {
                            fontSize: 14
                        }
                    }
                }
            }
        ]
    };
    planTotalLineChart.setOption(planTotalLineOption);
}