/**
 * Created by machengcheng on 16/11/11.
 */
function UniversityDetail () {

}
UniversityDetail.prototype = {
    constructor: UniversityDetail,
    init: function () {

    },
    showBox: function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="search-condition">');
        contentHtml.push('<input type="text" class="university-name" placeholder="请输入院校名称" />');
        contentHtml.push('<select id="batch-select">');
        contentHtml.push('<option value="1">本科一批</option>');
        contentHtml.push('<option value="8">高职高专</option>');
        contentHtml.push('</select>');
        contentHtml.push('<button type="button" class="red-btn">搜索</button>');
        contentHtml.push('</div>');
        contentHtml.push('<table class="results-table" cellpadding="0" cellspacing="0">');
        contentHtml.push('<thead><tr>');
        contentHtml.push('<th width="56px">排名</th>');
        contentHtml.push('<th>院校名称</th>');
        contentHtml.push('<th>专业名称</th>');
        contentHtml.push('<th width="62px">招生批次(2016年)</th>');
        contentHtml.push('<th width="62px">招生计划(2016年)</th>');
        contentHtml.push('<th>必修要求</th>');
        contentHtml.push('</tr></thead>');
        contentHtml.push('<tbody>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>1</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>2</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>3</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>4</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>5</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>6</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>7</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>8</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>9</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('<tr>');
        contentHtml.push('<td>10</td>');
        contentHtml.push('<td>北京大学（985）</td>');
        contentHtml.push('<td>法学、国际金融</td>');
        contentHtml.push('<td>本科一批</td>');
        contentHtml.push('<td>23人</td>');
        contentHtml.push('<td>物理</td>');
        contentHtml.push('</tr>');
        contentHtml.push('</tbody>');
        contentHtml.push('</table>');
        contentHtml.push('<div class="pagination-bar"><div class="pagination"></div></div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['543px', '490px'],
            content: contentHtml.join('')
        });
    }
};

$(document).on('click', '#university-detail', function () {
    var universityDetail = new UniversityDetail();
    universityDetail.showBox('院校详情');
});

batchAnalysis();
majorTypeAnalysis();
enrollUniversityTotal();
planTotalLineChart();

//按批次分析
function batchAnalysis () {
    var subjectBarChart = echarts.init(document.getElementById('subjectLineChart'));
    var subjectBarOption = {
        title: {
            text: '按批次分析'
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
    subjectBarChart.setOption(subjectBarOption);
}

function majorTypeAnalysis () {
    var subjectPieChart = echarts.init(document.getElementById('subjectPieChart'));
    var subjectPieOption = {
        title : {
            text: '按专业类别分析',
            x:'left'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            width: '65',
            itemWidth: 12,
            itemHeight: 12,
            orient: 'horizontal',
            selectedMode: false,
            x: 'left',
            left: 150,
            bottom: '2%',
            data: ['哲学', '经济学', '法学', '教育学', '文学', '历史学', '理学', '工学', '农学'], //'医学', '管理学', '艺术学'],
            textStyle: {
                color: '#4A4A4A',
                fontSize: 12
            }
        },
        series : [
            {
                name: '计划人数',
                type: 'pie',
                selectedOffset: 0,
                legendHoverLink: false,
                hoverAnimation: false,
                radius : [0, '60%'],
                center: ['18%', '50%'],
                color: ['#D0021B', '#F5A623', '#F8E71C', '#8B572A', '#7ED321', '#417505', '#BD10E0', '#9013FE', '#4A90E2', '#50E3C2', '#B8E986', '#EA6264'],
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
                    {value: 345, name: '哲学'},
                    {value: 310, name: '经济学'},
                    {value: 234, name: '法学'},
                    {value: 135, name: '教育学'},
                    {value: 148, name: '文学'},
                    {value: 35, name: '历史学'},
                    {value: 85, name: '理学'},
                    {value: 305, name: '工学'},
                    {value: 65, name: '农学'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
    subjectPieChart.setOption(subjectPieOption);
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