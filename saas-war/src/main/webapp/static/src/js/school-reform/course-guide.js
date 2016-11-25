/**
 * Created by machengcheng on 16/11/11.
 */

function SubjectAnalysis () {
    this.init();
    this.subjectChangeEvent();
}
SubjectAnalysis.prototype = {
    constructor: SubjectAnalysis,
    init: function () {
        $('input[name="subject-analysis"]').eq(0).prop('checked', true);
        var subject = $('input[name="subject-analysis"]').eq(0).next().text(),
            subjectTitle = subject;
        this.getUniversityAndMajorNumber(subject, subjectTitle);
        this.getPlanEnrollingByProperty(subject);
        this.getAnalysisBatch(subject);
        this.getAnalysisDiscipline(subject);
        $('#university-detail').attr('subject', subject);
    },
    getUniversityAndMajorNumber: function (subject, subjectTitle) {
        Common.ajaxFun('/selectClassesGuide/getUniversityAndMajorNumber.do', 'GET', {
            'subject': subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.universityAndMajorNumber;
                if (subjectTitle != '不限学科') {
                    $('.require-num').html(data.universityNumber + '所院校的' + data.majorNumber + '个专业对' + subjectTitle + '有要求');
                } else {
                    $('.require-num').html(data.universityNumber + '所院校的' + data.majorNumber + '个专业选课要求为不限学科');
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getPlanEnrollingByProperty: function (subject) {
        Common.ajaxFun('/selectClassesGuide/getPlanEnrollingByProperty.do', 'GET', {
            'subject': subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                $('.plan-to-enroll').empty();
                $.each(data, function (i, k) {
                    $('.plan-to-enroll').append('<li>' + i + '招生计划人数：' + k + '人</li>');
                });
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    getAnalysisBatch: function (subject) {
        Common.ajaxFun('/selectClassesGuide/getAnalysisBatch.do', 'GET', {
            'subject': subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.analysisBatch;
                var analysisBatchData = {
                    batchs: [],
                    values: []
                }
                $.each(data, function (i, k) {
                    analysisBatchData.batchs.push(k.batchName);
                    analysisBatchData.values.push(k.number);
                });
                batchAnalysis(analysisBatchData.batchs, analysisBatchData.values);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    sortNumber: function (a,b) {
        return a - b;
    },
    getAnalysisDiscipline: function (subject) {
        var that = this;
        Common.ajaxFun('/selectClassesGuide/getAnalysisDiscipline.do', 'GET', {
            'subject': subject
        }, function (res) {
            if (res.rtnCode == "0000000") {
                for (var i = 1; i < 7; i++) {
                    $('.major-type-top thead tr th').eq(i).html('-');
                    $('.major-type-top tbody tr td').eq(i).html('-');
                    $('.major-type-bottom thead tr th').eq(i).html('-');
                    $('.major-type-bottom tbody tr td').eq(i).html('-');
                }
                var data = res.bizData.analysisDiscipline;
                var types = {
                    type: [],
                    datas: [],
                    data: []
                };
                var tempStr = '';
                $.each(data, function (i, k) {
                    types.type.push(k.disciplineName);
                    types.datas.push({
                        value: k.number,
                        name: k.disciplineName
                    });
                    types.data.push(k.number);
                    if (i <= 5) {
                        $('.major-type-top thead tr th').eq(i+1).html(k.disciplineName);
                        $('.major-type-top tbody tr td').eq(i+1).html(k.number);
                    } else {
                        var n = i % 6;
                        $('.major-type-bottom thead tr th').eq(n+1).html(k.disciplineName);
                        $('.major-type-bottom tbody tr td').eq(n+1).html(k.number);
                    }
                });

                for(var i = 0; i < types.data.length - 1; i++)
                {
                    for(var j = 0; j < types.data.length - 1 - i; j++)
                    {
                        if(parseInt(types.data[j]) > parseInt(types.data[j+1]))
                        {
                            var tmp_number = types.data[j];
                            types.data[j] = types.data[j+1];
                            types.data[j+1] = tmp_number;

                            var tmp_name = types.type[j];
                            types.type[j] = types.type[j+1];
                            types.type[j+1] = tmp_name;
                        }
                    }
                }

                majorTypeAnalysis(types.type.slice(types.type.length - 3, types.type.length).reverse(), types.datas);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    subjectChangeEvent: function () {
        var that = this;
        $(document).on('change', 'input[name="subject-analysis"]', function () {
            var subject = $(this).next().text(),
                subjectTitle = subject;
            if (subject == '不限学科') {
                subject = '';
            }
            that.getUniversityAndMajorNumber(subject, subjectTitle);
            that.getPlanEnrollingByProperty(subject);
            that.getAnalysisBatch(subject);
            that.getAnalysisDiscipline(subject);
            $('#university-detail').attr('subject', subject);
        });
    }
};

function UniversityDetail () {
    this.universityOffset = 0;
    this.universityRows = 10;
    this.universityCount = 0;
    this.subject = '';//科目名称,subject=物理
    this.universityName = '';//学校名称，支持模糊查询
    this.pageCount = 1;
}
UniversityDetail.prototype = {
    constructor: UniversityDetail,
    showBox: function (title) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="search-condition">');
        contentHtml.push('<input type="text" id="search-keywords" uid="" class="university-name" placeholder="请输入院校名称" />');
        //contentHtml.push('<select id="batch-select">');
        //contentHtml.push('<option value="1">本科一批</option>');
        //contentHtml.push('<option value="8">高职高专</option>');
        //contentHtml.push('</select>');
        contentHtml.push('<ul id="results-list">');
        //contentHtml.push('<li>南京理工大学</li>');
        //contentHtml.push('<li>南京大学</li>');
        //contentHtml.push('<li>南京航空航天大学</li>');
        //contentHtml.push('<li>东南大学</li>');
        //contentHtml.push('<li>南京农业大学</li>');
        contentHtml.push('</ul>');
        contentHtml.push('<button type="button" id="search-btn" class="red-btn">搜索</button>');
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
        contentHtml.push('<tbody id="university-detail-data-list">');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>1</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>2</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>3</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>4</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>5</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>6</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>7</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>8</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>9</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
        //contentHtml.push('<tr>');
        //contentHtml.push('<td>10</td>');
        //contentHtml.push('<td>北京大学（985）</td>');
        //contentHtml.push('<td>法学、国际金融</td>');
        //contentHtml.push('<td>本科一批</td>');
        //contentHtml.push('<td>23人</td>');
        //contentHtml.push('<td>物理</td>');
        //contentHtml.push('</tr>');
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
    },
    loadPage: function (offset, rows) {
        var that = this;
        this.universityOffset = offset;
        this.universityRows = rows;
        var subject = this.subject;
        var universityName = this.universityName;
        var batch = this.batch;
        Common.ajaxFun('/selectClassesGuide/getMajorByUniversityNameAndBatch.do', 'GET', {
            'subject': subject,
            'universityName': universityName,
            'offset': offset,
            'rows': rows
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
        var that = this;
        this.universityCount = result.count;
        this.pageCount = Math.ceil(this.universityCount / this.universityRows);
        var template = Handlebars.compile($("#university-detail-data-template").html());
        $.each(result.majorByUniversityNameAndBatch, function (i, k) {
            if (k.rank === "" || k.rank === undefined || k.rank === null || k.rank === "0") {
                k.rank = "-";
            }
            if (k.selSubject === "" || k.selSubject === undefined || k.selSubject === null || k.selSubject === "0") {
                k.selSubject = "-";
            } else {
                k.selSubject = k.selSubject.split(' ').join('、');
            }
        });
        $('#university-detail-data-list').html(template(result.majorByUniversityNameAndBatch));
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
                that.loadPage(that.universityOffset, that.universityRows);
            }
        });
    }
};

$(function () {
    new SubjectAnalysis();

    var universityDetail = null;
    $(document).on('click', '#university-detail', function () {
        var subjectName = $(this).attr('subject');
        if (subjectName != '00') {
            universityDetail = new UniversityDetail();
            universityDetail.subject = subjectName;
            universityDetail.showBox('院校详情');
            universityDetail.loadPage(0, universityDetail.universityRows);
        }
    });

    $(document).on('click', '#search-btn', function () {
        if (universityDetail != null) {
            universityDetail.universityName = $('#search-keywords').val().trim();
            universityDetail.loadPage(0, universityDetail.universityRows);
        }
    });

    getLatestYearData();

    historyEnrollingData();

    /**
     * 院校文本框添加事件监听
     */
    $(document).on('click', "#search-keywords", startSearchUniversity)
        .on('keyup', "#search-keywords", startSearchUniversity);
        //.on('click', "#search-keywords", startSearchUniversity)
        //.on('mouseover', "#search-keywords", startSearchUniversity)
        //.on('focus', "#search-keywords", startSearchUniversity);
        //.on('blur', "#search-keywords", startSearchUniversity);
    $(document).on("click", function (e) {
        $("#results-list").hide();
        e.stopPropagation();
    });
});

/**
 * 模糊查询动态接口
 */
function startSearchUniversity () {
    var keywords = $("#search-keywords").val().trim();
    if (keywords != "") {
        Common.ajaxFun('/selectClassesGuide/getUniversityName.do', 'GET', {
            'universityName': keywords,
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.majorByUniversityNameAndBatch.length != 0) {
                    var uArr = [];
                    $.each(data.majorByUniversityNameAndBatch, function (i, k) {
                        uArr.push({
                            id: k.universityId,
                            name: k.universityName
                        });
                    });
                    uArr.splice(5, uArr.length);
                    var template = Handlebars.compile($("#university-results-item-data-template").html());
                    $('#results-list').html(template(uArr));
                    $('#results-list li').on("click", function () {
                        $("#search-keywords").val($(this).html());
                        $("#search-keywords").attr("uid", $(this).attr("uid"));
                        $("#results-list").hide();
                    });
                    $('#results-list li').on("mouseover", function () {
                        $('#results-list li').removeClass('active');
                        $(this).addClass('active');
                        $("#search-keywords").val($(this).html());
                        $("#search-keywords").attr("uid", $(this).attr("uid"));
                    });

                    $("#results-list").show();
                } else {
                    $("#results-list").hide();
                    $("#search-keywords").attr("uid", "");
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    } else {
        $(".course-info .m-list").hide();
        $("#search-keywords").attr("uid", "");
    }
}

//2016年招生数据
function getLatestYearData () {
    Common.ajaxFun('/selectClassesGuide/getEnrollingNumberByBatch.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData.enrollingNumberByBatch;
            var majorCount = data.majorCount;
            var enrollingCount = data.enrollingCount;
            var datas = {
                batchs: [],
                batchNames: [],
                numbers: []
            };
            $.each(data.enrollingNumberByBatch, function (i, k) {
                datas.batchs.push({
                    value: k.majorNumber,
                    name: k.batchName
                });
                datas.batchNames.push(k.batchName);
                datas.numbers.push(k.planEnrollingNumber);
            });
            enrollUniversityTotal(majorCount, datas.batchs, datas.batchNames);
            planTotalLineChart(enrollingCount, datas.batchNames, datas.numbers);
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
}

//按批次分析
function batchAnalysis (batchs, values) {
    console.info('======');
    console.info(batchs);
    console.info(values);
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
            data: batchs //['高职院校', '三本院校', '二本院校', '一本院校']
        },
        series: [
            {
                name: '人数',
                type: 'bar',
                barMinHeight: 26,
                barCategoryGap: '40%',
                data: values, //[8917, 6989, 5342, 3456],
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

//按专业类别分析
function majorTypeAnalysis (type, datas) {
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
            data: type, //['哲学', '经济学', '法学', '教育学', '文学', '历史学', '理学', '工学', '农学'], //'医学', '管理学', '艺术学'],
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
                data: datas,
                //data:[
                //    {value: 345, name: '哲学'},
                //    {value: 310, name: '经济学'},
                //    {value: 234, name: '法学'},
                //    {value: 135, name: '教育学'},
                //    {value: 148, name: '文学'},
                //    {value: 35, name: '历史学'},
                //    {value: 85, name: '理学'},
                //    {value: 305, name: '工学'},
                //    {value: 65, name: '农学'}
                //],
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

//选课指导 2016年招生数据--招生专业总数
function enrollUniversityTotal (majorCount, batchs, batchNames) {
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

//选课指导 2016年招生数据--招生计划总数
function planTotalLineChart (enrollingCount, batchNames, numbers) {
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
                data: numbers, //[8917, 6989, 5342, 3456],
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

//选课指导历年招生数据
function historyEnrollingData () {
    Common.ajaxFun('/selectClassesGuide/getEnrollingNumberTable.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            var data = res.bizData.enrollingNumberTable;
            var datas = {
                years: [],
                batchNames: [],
                universityNumbers: [],
                planEnrollingNumbers: []
            };
            $.each(data, function (i, k) {
                datas.years.push(k.year);
            });
            var years = datas.years.deleteRepeat();
            var historyHtml = [];
            $.each(years, function (i, k) {
                var tempYear = years[i];
                historyHtml.push('<tr>');
                historyHtml.push('<td colspan="2">' + tempYear + '</td>');
                var tempBatch = [
                    {
                        universityNumber: '-',
                        planEnrollingNumber: '-'
                    },
                    {
                        universityNumber: '-',
                        planEnrollingNumber: '-'
                    },
                    {
                        universityNumber: '-',
                        planEnrollingNumber: '-'
                    }
                ];
                $.each(data, function (n, m) {
                    if (m.year == tempYear) {
                        if (m.batchName == '一批本科') {
                            tempBatch[0].universityNumber = m.universityNumber;
                            tempBatch[0].planEnrollingNumber = m.planEnrollingNumber;
                        }
                        if (m.batchName == '二批本科') {
                            tempBatch[1].universityNumber = m.universityNumber;
                            tempBatch[1].planEnrollingNumber = m.planEnrollingNumber;
                        }
                        if (m.batchName == '高职（专科）') {
                            tempBatch[2].universityNumber = m.universityNumber;
                            tempBatch[2].planEnrollingNumber = m.planEnrollingNumber;
                        }
                    }
                });
                $.each(tempBatch, function (i, k) {
                    historyHtml.push('<td>' + k.universityNumber + '</td>');
                    historyHtml.push('<td>' + k.planEnrollingNumber + '</td>');
                });
                historyHtml.push('</tr>');
            });
            $('#history-enroll-list').html(historyHtml.join(''));
        }
    }, function (res) {
        layer.msg("出错了");
    }, true);
}

Array.prototype.deleteRepeat = function(){
    var res = [];
    var json = {};
    for(var i = 0; i < this.length; i++){
        if(!json[this[i]]){
            res.push(this[i]);
            json[this[i]] = 1;
        }
    }
    return res;
};
