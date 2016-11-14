/**
 * Created by machengcheng on 16/11/14.
 */

historyCourseAnalysis();
partCourseAnalysisChart();
groupCourseAnalysis();

function historyCourseAnalysis () {
    var historyCourseAnalysisChart = echarts.init(document.getElementById('historyCourseAnalysisChart'));
    historyCourseAnalysisOption = {
        title: {
            show: false,
            text: '部分学生选课分析'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            top: 'bottom',
            itemGap: 20,
            selectedMode: false,
            data: ['上线人数', '物理', '化学', '生物'],
            textStyle: {
                color: '#4A4A4A',
                fontSize: 14
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '12%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: ['2016', '2017', '2018', '2019'],
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
            axisTick: {
                show: false,
                inside: false
            },
            axisLine: {
                lineStyle: {
                    color: '#D8D8D8'
                }
            },
            max: 934
        },
        series: [
            {
                name:'上线人数',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#4A90E2',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:[120, 132, 101, 134]
            },
            {
                name:'物理',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#77ACB2',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:[220, 182, 191, 234]
            },
            {
                name:'化学',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#7ED321',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:[150, 232, 201, 154]
            },
            {
                name:'生物',
                type:'line',
                symbolSize: [9, 9],
                itemStyle: {
                    normal: {
                        color: '#F5A623',
                        borderWidth: 2,
                        borderType: 'solid'
                    }
                },
                data:[820, 932, 901, 934]
            }
        ]
    };
    historyCourseAnalysisChart.setOption(historyCourseAnalysisOption);
}

//部分学生选课情况分析
function partCourseAnalysisChart () {
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
            data: ['通用技术', '政治', '历史', '生物', '化学', '物理', '地理']
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                barMinHeight: 26,
                barCategoryGap: '40%',
                data: [123, 234, 456, 567, 489, 211, 265],
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
function groupCourseAnalysis () {
    var groupCourseAnalysisBar = echarts.init(document.getElementById('groupCourseAnalysisBar'));
    var groupCourseAnalysisOption = {
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            selectedMode: false,
            orient: 'horizontal',
            x: 'right',
            itemGap: 32,
            textStyle: {
                color: '#4A4A4A'
            },
            data:['前365名选课人数', '招生计划']
        },
        toolbox: {
            show: false
        },
        calculable: false,
        xAxis: [
            {
                type: 'category',
                data: ['本科一批', '本科二批', '本科三批', '高职高专'],
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
                min: 0,
                max: 1683,
                axisLine: {
                    lineStyle: {
                        color: '#D8D8D8'
                    }
                },
                splitLine: {
                    show: true
                },
                axisLabel: {
                    formatter: '{value} ml'
                },
                axisTick: {
                    show: false
                }
            }
        ],
        series: [
            {
                name: '前365名选课人数',
                type: 'bar',
                barWidth: 44,
                data: [1466, 912, 1276, 1107],
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
            },
            {
                name: '招生计划',
                type: 'bar',
                barWidth: 44,
                data: [1683, 537, 726, 1027],
                label: {
                    normal: {
                        show: true,
                        position: 'top',
                        formatter: '{c}'
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#F5A623'
                    }
                }
            }
        ]
    };
    groupCourseAnalysisBar.setOption(groupCourseAnalysisOption);
}