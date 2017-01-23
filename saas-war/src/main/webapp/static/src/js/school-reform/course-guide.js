var tnId = Common.cookie.getCookie('tnId');

function CoursePlan () {
    this.years = [];
    this.datas = [];
    this.subjectCourseBar = null;
    this.subjectCourseOption = null;
    this.course = ['通用技术', '政治', '历史', '地理', '生物', '化学', '物理'];  //当前选中的课程
    this.status = {
        '通用技术': true,
        '政治': true,
        '历史': true,
        '地理': true,
        '生物': true,
        '化学': true,
        '物理': true
    };
    this.selectedCount = 7;
    this.init();
}

CoursePlan.prototype = {
    constructor: CoursePlan,
    init: function () {
        this.getSingleCourse();
        $("input[name='single-course'], input[name='single-course-all']").prop("checked", true);
        this.selectCourse();
        this.getEnrollingPlanYears();
    },
    selectCourse: function () {
        var that = this;
        var obj = this;
        for (var i = 0; i < $("input[name='single-course']").length; i++) {
            $("input[name='single-course']")
                .eq(i)
                .unbind("change")
                .bind("change", {data: i, obj: obj}, function (evt) {
                    var _this = evt.data.obj;
                    var _child = $(this);
                    if (_child.prop("checked")) {
                        _this.selectedCount++;
                        if (_this.selectedCount >= $("input[name='single-course']").length) {
                            $("input[name='single-course-all']").prop("checked", true);
                        }
                        that.status[_child.next().find('span').text()] = true;
                    } else {
                        if (_this.selectedCount >= 1) {
                            _this.selectedCount--;
                        }
                        $("input[name='single-course-all']").prop("checked", false);
                        that.status[_child.next().find('span').text()] = false;
                    }
                    _this.subjectCourseOption.legend.selected = that.status;
                    _this.subjectCourseBar.setOption(that.subjectCourseOption);
                });
        }
        $("input[name='single-course-all']")
            .unbind("change")
            .bind("change", {data: i, obj: obj}, function (evt) {
                var _this = evt.data.obj;
                var _child = $(this);
                if (_child.prop("checked")) {
                    _this.selectedCount = $("input[name='single-course']").length;
                    $("input[name='single-course']").prop("checked", true);
                    _child.prop("checked", true);
                    _this.status = {
                        '通用技术': true,
                        '政治': true,
                        '历史': true,
                        '地理': true,
                        '生物': true,
                        '化学': true,
                        '物理': true
                    };
                } else {
                    $("input[name='single-course']").prop("checked", false);
                    _this.selectedCount = 0;
                    _this.status = {
                        '通用技术': false,
                        '政治': false,
                        '历史': false,
                        '地理': false,
                        '生物': false,
                        '化学': false,
                        '物理': false
                    };
                }
                _this.subjectCourseOption.legend.selected = that.status;
                _this.subjectCourseBar.setOption(that.subjectCourseOption);
            });
    },
    getSingleCourse: function () {
        var that = this;
        Common.ajaxFun('/selectClassesGuide/getUndergraduateEnrollingNumber.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                that.datas[0] = {
                    name:'通用技术',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[1] = {
                    name:'政治',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[2] = {
                    name:'历史',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[3] = {
                    name:'地理',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[4] = {
                    name:'生物',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[5] = {
                    name:'化学',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                that.datas[6] = {
                    name:'物理',
                    type:'bar',
                    barWidth: 44,
                    stack: '科目',
                    data:[]
                };
                $.each(data.data, function (i, k) {
                    //alert(i + ', ' + k['通用技术']);
                    that.years.push(i + '届');
                    $.each(k, function (n, m) {
                        switch(n) {
                            case '通用技术':
                                that.datas[0].data.push(m);
                                break;
                            case '政治':
                                that.datas[1].data.push(m);
                                break;
                            case '历史':
                                that.datas[2].data.push(m);
                                break;
                            case '地理':
                                that.datas[3].data.push(m);
                                break;
                            case '生物':
                                that.datas[4].data.push(m);
                                break;
                            case '化学':
                                that.datas[5].data.push(m);
                                break;
                            case '物理':
                                that.datas[6].data.push(m);
                                break;
                        }
                    });
                });
                $('#maxYear').html(data.maxYear);
                var coursePercent = data.coursePercent;
                $('#percent-wuli').html(coursePercent['物理'] * 100 + '%');
                $('#percent-huaxue').html(coursePercent['化学'] * 100 + '%');
                $('#percent-shengwu').html(coursePercent['生物'] * 100 + '%');
                $('#percent-zhengzhi').html(coursePercent['政治'] * 100 + '%');
                $('#percent-lishi').html(coursePercent['历史'] * 100 + '%');
                $('#percent-dili').html(coursePercent['地理'] * 100 + '%');
                $('#percent-jishu').html(coursePercent['通用技术'] * 100 + '%');
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
        this.renderSingleCourseChart(that.years, that.datas);
    },
    renderSingleCourseChart: function (years, datas) {
        var that = this;
        this.subjectCourseBar = echarts.init(document.getElementById('subjectCourseBar'));
        this.subjectCourseOption = {
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
                showContent: true,
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                show: false,
                selectedMode: false,
                selected: that.status,
                data: that.course,
                textStyle: {
                    color: '#4A4A4A',
                    fontSize: 14
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
                    data : years, //['2017届','2018届','2019届','2020届','2021届'],
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
            series: datas.reverse()
        };
        this.subjectCourseBar.setOption(this.subjectCourseOption);
    },
    getEnrollingPlanYears: function () {
        var that = this;
        Common.ajaxFun('/selectClassesGuide/getEnrollingYear.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;

            }
        }, function (res) {
            layer.msg("出错了");
        }, false);
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

/**
 * 删除数组中的元素
 * @param varElement
 * @returns {number}
 */
Array.prototype.delete = function (varElement) {
    var numDeleteIndex = -1;
    for (var i = 0; i < this.length; i++) {
        // 严格比较，即类型与数值必须同时相等。
        if (this[i] === varElement) {
            this.splice(i, 1);
            numDeleteIndex = i;
            break;
        }
    }
    return numDeleteIndex;
};