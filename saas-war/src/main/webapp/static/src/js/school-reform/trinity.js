/**
 * Created by machengcheng on 16/11/14.
 */

enrollUniversityTotal();
planTotalLineChart();

//招生院校总数
function enrollUniversityTotal () {
    var enrollTotalChart = echarts.init(document.getElementById('enrollTotalChart'));
    var enrollPieOption = {
        title: {
            show: true,
            text: "招生院校总数：2345所",
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
            data: ['一本院校', '二本院校', '三本院校', '高职高专'],
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
                data:[
                    {value: 2953, name: '一本院校'},
                    {value: 3657, name: '二本院校'},
                    {value: 6321, name: '三本院校'},
                    {value: 8901, name: '高职高专'}
                ]
            }
        ]
    };
    enrollTotalChart.setOption(enrollPieOption);
}

//招生院校总数
function enrollUniversityTotal () {
    var enrollTotalChart = echarts.init(document.getElementById('enrollTotalChart'));
    var enrollPieOption = {
        title: {
            show: true,
            text: "招生院校总数：2345所",
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
            data: ['一本院校', '二本院校', '三本院校', '高职高专'],
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
                data:[
                    {value: 2953, name: '一本院校'},
                    {value: 3657, name: '二本院校'},
                    {value: 6321, name: '三本院校'},
                    {value: 8901, name: '高职高专'}
                ]
            }
        ]
    };
    enrollTotalChart.setOption(enrollPieOption);
}

//招生计划总数
function planTotalLineChart () {
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
            data: ['高职院校', '三本院校', '二本院校', '一本院校']
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                barMinHeight: 26,
                barCategoryGap: '40%',
                data: [8917, 6989, 5342, 3456],
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