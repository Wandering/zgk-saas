/**
 * Created by machengcheng on 16/11/14.
 */
var tnId = Common.cookie.getCookie('tnId');

$(function () {

    var chkLength = $("input[name='course-analysis']").length;
    var courseAnalysis = new CourseAnalysis(chkLength, 'course-analysis', null);

    //();

    var gradeName = $('input[name="senior-analysis"]:checked').next().text();
    getAnalysisGroup(tnId, gradeName);
    selectTypeAnalysis(tnId, gradeName);
    getNumberByYear(tnId);
    $(document).on('change', 'input[name="senior-analysis"]', function () {
        var gradeName = $(this).next().text();
        getAnalysisGroup(tnId, gradeName);
        selectTypeAnalysis(tnId, gradeName);
    });

});

function CourseAnalysis (chkLength, chkName, courseFun) {
    this.selectCount = 0; //当前选中的课程数目,初始化0
    this.course = [];  //当前选中的课程
    this.init(chkLength, chkName, courseFun);
}
CourseAnalysis.prototype = {
    constructor: CourseAnalysis,
    init: function (chkLength, chkName, courseFun) {
        $("input[name='course-analysis']").prop("checked", false);
        this.selectCourse(chkLength, chkName, courseFun);
    },
    selectCourse: function (chkLength, chkName, courseFun) {
        var obj = this;
        for (var i = 0; i < chkLength; i++) {
            $("input[name=" + chkName + "]")
                .eq(i)
                .unbind("change")
                .bind("change", {data: i, obj: obj}, function (evt) {
                    var _that = $(this);
                    var _this = evt.data.obj;
                    var _child = $(this);
                    if (_child.prop("checked")) {
                        _this.selectCount++;
                        if (_this.selectCount <= 3) {
                            _this.course.push(_child.next().html());
                            if (_this.selectCount == 3) {
                                if (courseFun != null) {
                                    courseFun(_this.course);
                                }
                            }
                        } else {
                            _this.selectCount--;
                            _child.prop("checked", false);
                            layer.tips('您最多能选3门课程哦!', _that, {
                                tips: '2'
                            });
                        }
                    } else {
                        _this.selectCount--;
                        _this.course.delete(_child.next().html());
                    }
                });
        }
    }
};

//往年选课分析
function historyCourseAnalysis (years, subjects) {
    console.info('years: ' + years.length);
    console.info(subjects);
    var historyCourseAnalysisChart = echarts.init(document.getElementById('historyCourseAnalysisChart'));
    var historyCourseAnalysisOption = {
        title: {
            show: false,
            text: '往年选课分析'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            top: 'bottom',
            itemGap: 20,
            selectedMode: true,
            data: ['物理', '化学', '生物', '地理', '历史', '政治', '通用技术'],
            textStyle: {
                color: '#4A4A4A',
                fontSize: 14
            }
        },
        grid: {
            left: '2%',
            right: '8%',
            top: '13%',
            containLabel: true,
            borderWidth: 0,
            backgroundColor: 'transparent'
        },
        xAxis: {
            type: 'category',
            name: '高考年份',
            nameTextStyle: {
                color: '#4A4A4A',
                fontSize: 14
            },
            data: years, //['2016', '2017', '2018', '2019'],
            boundaryGap: [0, 0.01],
            axisTick: {
                show: true,
                inside: true
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
            type: 'value',
            name: '选课人数',
            nameTextStyle: {
                color: '#4A4A4A',
                fontSize: 14
            },
            axisTick: {
                show: false,
                inside: false
            },
            axisLine: {
                lineStyle: {
                    color: '#D8D8D8'
                }
            }
            //max: 934
        },
        series: [
            {
                name: '物理',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#4A90E2',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:subjects['物理']
            },
            {
                name: '化学',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#77ACB2',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:subjects['化学']
            },
            {
                name: '生物',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#7ED321',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:subjects['生物']
            },
            {
                name: '政治',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#F5A623',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:subjects['政治']
            },
            {
                name: '历史',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#C0DD7D',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:subjects['历史']
            },
            {
                name: '地理',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#EA6264',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data: subjects['地理']
            },
            {
                name: '通用技术',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#A98FCB',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data: subjects['通用技术'],
            }
        ]
    };
    historyCourseAnalysisChart.setOption(historyCourseAnalysisOption);
}

//往年选课分析
function getNumberByYear (tnId) {
    Common.ajaxFun('/selectClassesGuide/getNumberByYear.do', 'GET', {
        'tnId': tnId
    }, function (res) {
        if (res.rtnCode == "0000000") {
            //var res = {
            //    "bizData":{
            //        "data":{
            //            "2015":{
            //                "化学":3,
            //                "历史":2,
            //                "地理":2,
            //                "政治":2,
            //                "物理":3,
            //                "生物":2,
            //                "通用技术":4
            //            },
            //            "2018":{
            //                "化学":1,
            //                "地理":1,
            //                "物理":1,
            //                "生物":1,
            //                "通用技术":1
            //            }
            //        }
            //    },
            //    "rtnCode":"0000000",
            //        "ts":1479967678079
            //};
            var data = res.bizData;
            var historyData = {
                year: [],
                subjects: {
                    '物理': [],
                    '化学': [],
                    '生物': [],
                    '政治': [],
                    '历史': [],
                    '地理': [],
                    '通用技术': []
                }
            };
            $.each(data.data, function (i, k) {
                historyData.year.push(i);
                $.each(k, function (m, n) {
                    console.info('i: ' + i + ', m: ' + m + ', n: ' + n);
                    historyData.subjects[m].push(n);
                });
            });
            historyCourseAnalysis(historyData.year, historyData.subjects);
            console.info(historyData.year);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
}

//单科选课情况分析
function partCourseAnalysisChart (subjects, numbers) {
    var partCourseAnalysisChart = echarts.init(document.getElementById('partCourseAnalysisChart'));
    var partCourseAnalysisOption = {
        title: {
            show: false,
            text: '选课分析',
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
            data: subjects //['通用技术', '政治', '历史', '生物', '化学', '物理', '地理']
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                barMinHeight: 26,
                barCategoryGap: '40%',
                data: numbers, //[123, 234, 456, 567, 489, 211, 265],
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
    partCourseAnalysisChart.setOption(partCourseAnalysisOption);
}

//组合招生计划
function groupCourseAnalysis (groups, stuNumbers) {
    var groupCourseAnalysisBar = echarts.init(document.getElementById('groupCourseAnalysisBar'));
    var groupCourseAnalysisOption = {
        title: {
            show: true,
            text: '组合选课情况统计',
            x: 'center',
            top: 'top',
            textStyle: {
                color: '#4A4A4A',
                fontWeight: 'normal',
                fontSize: 14
            }
        },
        tooltip: {
            trigger: 'axis'
        },
        //legend: {
        //    selectedMode: false,
        //    orient: 'horizontal',
        //    x: 'right',
        //    itemGap: 32,
        //    textStyle: {
        //        color: '#4A4A4A'
        //    },
        //    data:['前365名选课人数']
        //},
        toolbox: {
            show: false
        },
        calculable: false,
        xAxis: [
            {
                type: 'category',
                name: '选课组合',
                nameTextStyle: {
                    color: '#4A4A4A',
                    fontSize: 14
                },
                data: groups, //['本科一批', '本科二批', '本科三批', '高职高专'],
                axisLine: {
                    lineStyle: {
                        color: '#D8D8D8'
                    }
                },
                splitLine: {
                    show: false
                },
                axisTick: {
                    show: false
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '选课人数',
                nameTextStyle: {
                    color: '#4A4A4A',
                    fontSize: 14
                },
                min: Math.min(stuNumbers),
                max: Math.max(stuNumbers),
                axisLine: {
                    lineStyle: {
                        color: '#D8D8D8'
                    }
                },
                splitLine: {
                    show: true
                },
                axisLabel: {
                    formatter: '{value}'
                },
                axisTick: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '选课人数',
                type: 'bar',
                barWidth: 44,
                data: stuNumbers, //[1466, 912, 1276, 1107],
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        formatter: '{c}'
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#108EE9'
                    }
                }
            }
            //},
            //{
            //    name: '招生计划',
            //    type: 'bar',
            //    barWidth: 44,
            //    data: [1683, 537, 726, 1027],
            //    label: {
            //        normal: {
            //            show: true,
            //            position: 'top',
            //            formatter: '{c}'
            //        }
            //    },
            //    itemStyle: {
            //        normal: {
            //            color: '#F5A623'
            //        }
            //    }
            //}
        ]
    };
    groupCourseAnalysisBar.setOption(groupCourseAnalysisOption);
}

//单科选课情况分析
function selectTypeAnalysis (tnId, studentGrade) {
    Common.ajaxFun('/selectClassesGuide/selectTypeAnalysis.do', 'GET', {
        'tnId': tnId,
        'studentGrade': studentGrade
    }, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            $('.stu-count').html(data.count);
            if (data.top != 0) {
                $('.reach-line').html('预计' + data.top + '人上线').css({'width': (data.top / data.count) * 100 + '%'});
            } else {
                $('.reach-line').hide();//.html('预计' + data.top + '人上线').css({'width': '0%'});
                $('.ratio-detail').hide();
            }
            var datas = {
                subjects: [],
                numbers: []
            };
            $.each(data.oneTypeMap, function (i, k) {
                datas.subjects.push(i);
                datas.numbers.push(k);
            });
            var subjectHtml = [];
            if (!isEmptyObject(data.limitTypeMap)) {
                $.each(data.limitTypeMap, function (i, k) {
                    subjectHtml.push('<span class="item">' + i + '（' + k + '人）</span>');
                });
            } else {
                subjectHtml.push('<span class="item">物理（0人）</span>');
                subjectHtml.push('<span class="item">化学（0人）</span>');
                subjectHtml.push('<span class="item">生物（0人）</span>');
                subjectHtml.push('<span class="item">政治（0人）</span>');
                subjectHtml.push('<span class="item">地理（0人）</span>');
                subjectHtml.push('<span class="item">历史（0人）</span>');
                subjectHtml.push('<span class="item">通用技术（0人）</span>');
            }
            $('.single-course-info').html(subjectHtml.join(''));
            partCourseAnalysisChart(datas.subjects, datas.numbers);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
}

//组合选课情况分析
function getAnalysisGroup (tnId, grade) {
    Common.ajaxFun('/selectClassesGuide/getAnalysisGroup.do', 'GET', {
        'tnId': tnId,
        'grade': grade //两个值: 高二或高三
    }, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData;
            var datas = {
                groups: [],
                stuNumbers: []
            };
            $.each(data, function (i, k) {
                datas.groups.push(k.group);
                datas.stuNumbers.push(k.stuNumber);
            });
            groupCourseAnalysis(datas.groups, datas.stuNumbers);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
}

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

//判断对象是否为空
function isEmptyObject (e) {
    var t;
    for (t in e) {
        return !1;
    }
    return !0;
}