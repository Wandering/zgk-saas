var tnId = Common.cookie.getCookie('tnId');
var provinceId = Common.cookie.getCookie('provinceId');

function CoursePlan() {
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
    this.rankData = [];
    this.init();
}

CoursePlan.prototype = {
    constructor: CoursePlan,
    init: function () {
        this.getSingleCourse();
        $("input[name='single-course'], input[name='single-course-all']").prop("checked", true);
        this.selectCourse();
        this.getEnrollingPlanYears();
        $('#senior1').prop('checked', true);
        var grade = $('input[name="senior-radio"]:checked').next().text();
        this.getTeacherConfiguration(tnId, grade);
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
                if (res.bizData.length > 0) {
                    that.datas[0] = {
                        name: '通用技术',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[1] = {
                        name: '政治',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[2] = {
                        name: '历史',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[3] = {
                        name: '地理',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[4] = {
                        name: '生物',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[5] = {
                        name: '化学',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    that.datas[6] = {
                        name: '物理',
                        type: 'bar',
                        barWidth: 44,
                        stack: '科目',
                        data: []
                    };
                    var yearsFoo = [];
                    $.each(data.data, function (i, k) {
                        that.years.push(i + '届');
                        yearsFoo.push(Number(i));
                        $.each(k, function (n, m) {
                            switch (n) {
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
                    for (var i = 0; i < that.years.length; i++) {
                        var tempYear = that.years[i].substr(0, 4);
                        if (data.undergraduateEnrollingNumberList.length != 0) {
                            $.each(data.undergraduateEnrollingNumberList, function (n, m) {
                                var tempInnerYear = m.year;
                                var tempNumber = m.number;
                                if (tempInnerYear == tempYear) {
                                    that.years[i] = tempInnerYear + "届(录取人数" + tempNumber + ')';
                                }
                            });
                        }
                    }
                    $('#maxYear').html(data.maxYear);
                    var coursePercent = data.coursePercent;
                    if (coursePercent) {
                        $('#percent-wuli').html(Number(coursePercent['物理'] * 100).toFixed(0) + '%');
                        $('#percent-huaxue').html(Number(coursePercent['化学'] * 100).toFixed(0) + '%');
                        $('#percent-shengwu').html(Number(coursePercent['生物'] * 100).toFixed(0) + '%');
                        $('#percent-zhengzhi').html(Number(coursePercent['政治'] * 100).toFixed(0) + '%');
                        $('#percent-lishi').html(Number(coursePercent['历史'] * 100).toFixed(0) + '%');
                        $('#percent-dili').html(Number(coursePercent['地理'] * 100).toFixed(0) + '%');
                        $('#percent-jishu').html(Number(coursePercent['通用技术'] * 100).toFixed(0) + '%');
                    } else {
                        $('.course-bar-analyse, .course-bar-analyse-results').hide();
                    }

                    that.renderSingleCourseChart(that.years, that.datas);

                    $('.subject-chk').css({'display': 'inline-block'});

                    if (provinceId != '330000') {
                        that.course.delete('通用技术');
                        that.selectedCount = 6;
                        that.status = {
                            '通用技术': false,
                            '政治': true,
                            '历史': true,
                            '地理': true,
                            '生物': true,
                            '化学': true,
                            '物理': true
                        };
                        that.subjectCourseOption.legend.selected = that.status;
                        that.subjectCourseBar.setOption(that.subjectCourseOption);
                        $('#jishu-show').attr('name', 'single-course-none');
                        $('#jishu-show').parent().hide();
                        $('#percent-jishu').parent().hide();
                    }
                }else{
                    $('#history-single-course').hide();
                    $('.no-data-tips-single').show();
                }

            } else {
                $('#history-single-course').hide();
                $('.no-data-tips-single').show();
            }
        }, function (res) {
            layer.msg("出错了");
        }, false);   //备注
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
            tooltip: {
                trigger: 'axis',
                showContent: true,
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
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
            xAxis: [
                {
                    type: 'category',
                    data: years, //['2017届','2018届','2019届','2020届','2021届'],
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
            yAxis: [
                {
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
                var template = Handlebars.compile($("#enrolling-plan-radio-group-data-template").html());
                $('#enrolling-plan-radio-group').html(template(data));
                $('#enrolling-plan-radio-group li').eq(0).find('input[type="radio"]').prop('checked', true);

                var year = $('input[name="plan-radio"]:checked').attr('data-year');
                that.getEnrollingPlanData(year);

                $('input[name="plan-radio"]').on('change', function () {
                    var year = $('input[name="plan-radio"]:checked').next().text();
                    that.getEnrollingPlanData(year);
                });
            }
        }, function (res) {
            layer.msg("出错了");
        }, false);
    },
    getEnrollingPlanData: function (year) {
        var that = this;
        Common.ajaxFun('/selectClassesGuide/selectMajorTopCount.do', 'GET', {
            year: year
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                if (data.length != 0) {
                    that.rankData.splice(0, that.rankData.length);
                    var dataHtml = [];
                    var tempYear = year;
                    that.rankData[tempYear] = new Array();
                    $.each(data, function (i, k) {
                        if (i <= 4) {
                            var tempName = k.name.split(' ').join('+');
                            dataHtml.push('<tr>');
                            if (i != 0 && i != 1 && i != 2) {
                                dataHtml.push('<td>' + (i + 1) + '</td>');
                            } else if (i == 0) {
                                dataHtml.push('<td><i class="rank-top1"></i>Top' + (i + 1) + '</td>');
                            } else if (i == 1) {
                                dataHtml.push('<td><i class="rank-top2"></i>Top' + (i + 1) + '</td>');
                            } else if (i == 2) {
                                dataHtml.push('<td><i class="rank-top3"></i>Top' + (i + 1) + '</td>');
                            }

                            dataHtml.push('<td>' + tempName + '</td>');
                            dataHtml.push('<td>' + k.schoolNumber + '个学校' + k.majorNumber + '个专业可选</td>');
                            dataHtml.push('</tr>');
                        }

                        that.rankData[tempYear].push({
                            "majorNumber": k.majorNumber,
                            "name": k.name,
                            "schoolNumber": k.schoolNumber
                        });
                    });
                    $('#select-course-enrolling-plan-list').html(dataHtml.join(''));
                }else{
                    $('#admission-plan').hide();
                    $('.no-data-tips-results').show();
                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, false);  //备注
    },
    showRankBox: function (datas) {
        console.info(datas);
        var moreRankHtml = [];
        moreRankHtml.push('<div class="teacher-config-box">');
        moreRankHtml.push('<table>');
        moreRankHtml.push('<thead>');
        moreRankHtml.push('<tr>');
        moreRankHtml.push('<th>排名</th>');
        moreRankHtml.push('<th>组合</th>');
        moreRankHtml.push('<th>专业选择</th>');
        moreRankHtml.push('</tr>');
        moreRankHtml.push('</thead>');
        moreRankHtml.push('<tbody>');
        $.each(datas, function (i, k) {
            moreRankHtml.push('<tr>');
            if (i != 0 && i != 1 && i != 2) {
                moreRankHtml.push('<td>' + (i + 1) + '</td>');
            } else if (i == 0) {
                moreRankHtml.push('<td><i class="rank-top1"></i>Top' + (i + 1) + '</td>');
            } else if (i == 1) {
                moreRankHtml.push('<td><i class="rank-top2"></i>Top' + (i + 1) + '</td>');
            } else if (i == 2) {
                moreRankHtml.push('<td><i class="rank-top3"></i>Top' + (i + 1) + '</td>');
            }
            moreRankHtml.push('<td>' + k.name + '</td>');
            moreRankHtml.push('<td>' + k.schoolNumber + '个学校' + k.majorNumber + '个专业可选</td>');
            moreRankHtml.push('</tr>');
        });
        moreRankHtml.push('</tbody>');
        moreRankHtml.push('</table>');
        moreRankHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">更多排名</span>',
            offset: 'auto',
            scrollbar: false,
            area: ['639.8px', '553px'],
            content: moreRankHtml.join('')
        });
    },
    getTeacherConfiguration: function (tnId, grade) {
        var that = this;
        Common.ajaxFun('/selectClassesGuide/queryTeacherBytnIdAndGrade.do', 'GET', {
            tnId: tnId,
            grade: grade
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData;
                var tempTeachers = {};
                if (data.length === 0) {
                    var grade = $('input[name="senior-radio"]:checked').next().text();
                    $('.no-data-tips .current-grade').html(grade);
                    $('.teacher-config-table').hide();
                    $('.no-data-tips').show();
                    return;
                }

                $('.no-data-tips').hide();
                $('.teacher-config-table').show();

                var template = Handlebars.compile($("#teacher-config-list-data-template").html());
                $.each(data, function (i, k) {
                    k.index = i + 1;
                    var tempIndex = k.index;
                    tempTeachers[tempIndex] = new Array();
                    $.each(k.teachers, function (n, m) {
                        tempTeachers[tempIndex].push({
                            "className": m.className,
                            "courseName": m.courseName,
                            "grade": m.grade,
                            "maxClass": m.maxClass,
                            "teacherId": m.teacherId,
                            "teacherName": m.teacherName
                        });
                    });
                });
                $('#teacher-config-list').html(template(data));

                $('.config-btn').unbind('click').bind('click', function () {
                    var dataId = $(this).attr('data-id');
                    var datas = tempTeachers[dataId];
                    that.showTeacherConfigBox(datas);
                });
            } else {
                var grade = $('input[name="senior-radio"]:checked').next().text();
                $('.no-data-tips .current-grade').html(grade);
                $('.teacher-config-table').hide();
                $('.no-data-tips').show();
                return;
            }
        }, function (res) {
            layer.msg("出错了");
        }, false);
    },
    showTeacherConfigBox: function (datas) {
        console.info(datas);
        var teacherSubjectHtml = [];
        teacherSubjectHtml.push('<div class="teacher-config-box">');
        teacherSubjectHtml.push('<table>');
        teacherSubjectHtml.push('<thead>');
        teacherSubjectHtml.push('<tr>');
        teacherSubjectHtml.push('<th>老师名称</th>');
        teacherSubjectHtml.push('<th>所教科目</th>');
        teacherSubjectHtml.push('<th>所教年级</th>');
        teacherSubjectHtml.push('<th>所带班级</th>');
        teacherSubjectHtml.push('</tr>');
        teacherSubjectHtml.push('</thead>');
        teacherSubjectHtml.push('<tbody>');
        $.each(datas, function (i, k) {
            teacherSubjectHtml.push('<tr>');
            teacherSubjectHtml.push('<td>' + k.teacherName + '</td>');
            teacherSubjectHtml.push('<td>' + k.courseName + '</td>');
            teacherSubjectHtml.push('<td>' + k.grade + '</td>');
            teacherSubjectHtml.push('<td>' + k.className + '</td>');
            teacherSubjectHtml.push('</tr>');
        });
        teacherSubjectHtml.push('</tbody>');
        teacherSubjectHtml.push('</table>');
        teacherSubjectHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">师资科目</span>',
            offset: 'auto',
            scrollbar: false,
            area: ['512px', '259px'],
            content: teacherSubjectHtml.join('')
        });
    }
};


$(function () {

    var coursePlan = new CoursePlan();

    $('.more-rank-btn').on('click', function () {
        var year = $('input[name="plan-radio"]:checked').next().text();
        coursePlan.showRankBox(coursePlan.rankData[year]);
    });

    $('input[name="senior-radio"]').on('change', function () {
        var grade = $('input[name="senior-radio"]:checked').next().text();
        coursePlan.getTeacherConfiguration(tnId, grade);
    });

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
        tooltip: {
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
                radius: [0, '70%'],
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
                data: [
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


Array.prototype.delete = function (delIndex) {
    var temArray = [];
    for (var i = 0; i < this.length; i++) {
        if (i != delIndex) {
            temArray.push(this[i]);
        }
    }
    return temArray;
}
