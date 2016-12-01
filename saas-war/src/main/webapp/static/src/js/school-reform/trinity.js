/**
 * Created by machengcheng on 16/11/14.
 */

$(function () {
    var trinityData = new TrinityData();
    trinityData.loadPage(0, trinityData.universityRows);
});

function TrinityData () {
    this.universityOffset = 0;
    this.universityRows = 20;
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
        Common.ajaxFun('/trineController/getEnrollingNumberByBatch.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var majorCount = data.majorCount,
                    enrollingCount = data.enrollingCount;
                var datas = {
                    batchs: [],
                    batchNames: [],
                    planEnrollingNumbers: []
                };
                $.each(data.enrollingNumberByBatch, function (i, k) {
                    datas.batchNames.push(k.batchName);
                    datas.batchs.push({
                        value: k.majorNumber,
                        name: k.batchName
                    });
                    datas.planEnrollingNumbers.push(k.planEnrollingNumber);
                });
                enrollUniversityMajorTotal(majorCount, datas.batchNames, datas.batchs);
                planTotalLineChart(enrollingCount, datas.batchNames, datas.planEnrollingNumbers);
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

//招生院校专业总数
function enrollUniversityMajorTotal (majorCount, batchNames, batchs) {
    var enrollTotalChart = echarts.init(document.getElementById('enrollTotalChart'));
    var enrollPieOption = {
        title: {
            show: true,
            text: '招生院校专业总数：' + majorCount + '个专业',
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
                        show: true,
                        position: 'inner',
                        formatter: "{d}%"
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
function planTotalLineChart (enrollingCount, batchNames, planEnrollingNumbers) {
    var planTotalLineChart = echarts.init(document.getElementById('planTotalLineChart'));
    var planTotalLineOption = {
        title: {
            text: '招生计划总数：' + enrollingCount + '人',
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
                        //position: 'insideRight',
                        position: 'right',
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