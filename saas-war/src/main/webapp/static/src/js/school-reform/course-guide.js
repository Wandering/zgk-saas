var countyId = Common.cookie.getCookie('countyId');

function CoursePlan () {
    this.init();
}

CoursePlan.prototype = {
    constructor: CoursePlan,
    init: function () {
        this.getSinbleCourse();
    },
    getSinbleCourse: function () {
        Common.ajaxFun('/selectClassesGuide/getUndergraduateEnrollingNumber.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {

            }
        }, function (res) {
            layer.msg("出错了");
        }, false);
        this.renderSinbleCourseChart();
    },
    renderSinbleCourseChart: function () {
        var subjectCourseBar = echarts.init(document.getElementById('subjectCourseBar'));
        var subjectCourseOption = {
            title: {
                text: '单位：人',
                textStyle: {
                    color: '#4A4A4A',
                    fontSize: 12,
                    fontWeight: 'normal'
                }
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                top: '18%',
                left: '1%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : ['2017届','2018届','2019届','2020届','2021届'],
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
                }
            ],
            yAxis : [
                {
                    type : 'value',
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
                        show: true
                    }
                }
            ],
            color: ['#A25E51', '#F5A623', '#FFB789', '#A2D96A', '#87B3E8', '#91D8E4', '#64A3AD'],
            series : [
                {
                    name:'通用技术',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[89, 92, 40, 175, 36]
                },
                {
                    name:'政治',
                    type:'bar',
                    stack: '科目',
                    data:[89, 92, 93, 34, 36]
                },
                {
                    name:'历史',
                    type:'bar',
                    stack: '科目',
                    data:[26, 27, 28, 29, 80]
                },
                {
                    name:'地理',
                    type:'bar',
                    stack: '科目',
                    data:[40, 41, 42, 43, 44]
                },
                {
                    name:'生物',
                    type:'bar',
                    stack: '科目',
                    data:[39, 39, 39, 40, 39]
                },
                {
                    name:'化学',
                    type:'bar',
                    stack: '科目',
                    data:[20, 20, 20, 20, 20]
                },
                {
                    name:'物理',
                    type:'bar',
                    stack: '科目',
                    data:[50, 23, 20, 25, 30]
                }
            ]
        };
        subjectCourseBar.setOption(subjectCourseOption);
    }
};


$(function () {

    var coursePlan = new CoursePlan();


    var weakSubjectChart1 = echarts.init(document.getElementById('weakSubjectChart1'));
    var weakSubjectChart2 = echarts.init(document.getElementById('weakSubjectChart2'));
    var weakSubjectChart3 = echarts.init(document.getElementById('weakSubjectChart3'));
    var weakSubjectChart4 = echarts.init(document.getElementById('weakSubjectChart4'));
    var weakSubjectChart5 = echarts.init(document.getElementById('weakSubjectChart5'));
    var weakSubjectOption1 = {
        title: {
            text: '2017届弱势学科统计',
            textStyle: {
                color: '#4A4A4A',
                fontSize: 14,
                fontWeight: 'normal'
            },
            padding: [20, 60, 20, 60]
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
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
                center: ['50%', '55%'],
                avoidLabelOverlap: false,
                color: ['#64A3AD', '#91D8E4', '#87B3E7', '#A2D86A', '#FFB789', '#F5A623', '#A25E51'],
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
                //data: batchs
                data:[
                    {value: 253, name: '通用技术'},
                    {value: 357, name: '政治'},
                    {value: 321, name: '历史'},
                    {value: 401, name: '地理'},
                    {value: 890, name: '生物'},
                    {value: 590, name: '化学'},
                    {value: 290, name: '物理'}
                ]
            }
        ]
    };
    weakSubjectChart1.setOption(weakSubjectOption1);
    weakSubjectChart2.setOption(weakSubjectOption1);
    weakSubjectChart3.setOption(weakSubjectOption1);
    weakSubjectChart4.setOption(weakSubjectOption1);
    weakSubjectChart5.setOption(weakSubjectOption1);

});