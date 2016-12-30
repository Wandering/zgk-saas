/**
 *  专业测评模块
 *  @wiki:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=44452571
 **/

    var itimer = null;
var ProTest = {
    init: function () {
        this.provinceId = Common.cookie.getCookie('provinceId');
        this.fetchProTestList();
        this.addEvent();
    },
    fetchProTestList: function () {
        Common.ajaxFun('/apesk/getApeskResult.do', 'get', {}, function (res) {
            if (res.rtnCode === '0000000') {
                ProTest.renderProTestList(res.bizData);
            }
        });
    },
    // 请求题目
    fetchProTestUrl: function (type, name) {
        Common.ajaxFun('/apesk/queryApeskUrl.do', 'get', {
            'acId': type
        }, function (res) {
            if (res.rtnCode === '0000000') {
                var resultUrl = res.bizData.data;
                layer.full(
                    layer.open({
                        type: 2,
                        title: name,
                        content: resultUrl,
                        area: ['100%', '100%'],
                        maxmin: false,
                        success: function(layero, index){
                            var id = res.bizData.id;
                            itimer = setInterval(function(){
                                clearInterval(itimer);
                                layer.close(index);
                                resultUrl = ProTest.getTestUrl(id);
                                layer.open({
                                    type: 2,
                                    title: "测评结果",
                                    content: resultUrl,
                                    area: ['100%', '100%'],
                                    maxmin: false
                                })
                            },1000);
                        }
                    })
                );


            }
        }, function (res) {

        }, true);
    },
    getTestUrl: function (id) {
        var resUrl = '';
        Common.ajaxFun('/apesk/queryReportUrlById.do', 'get', {
            'id': id
        }, function (res) {
            if (res.rtnCode === '0000000') {
                if (res.bizData.data != false) {
                    resUrl = res.bizData.data;
                    clearInterval(itimer);
                }
            }
        }, function (res) {

        }, true);
        return resUrl;
    },
    renderProTestList: function (data) {
        var tpl = Handlebars.compile($('#assessment-main-tpl').html());
        $('#assessment-main').html(tpl(data.apeskObj));
        //zj 更换测评名称
        if (this.provinceId === '330000') {
            $('.assessment1').eq(0).find('h3').html('高中生专业选择测评');
            $('.assessment1').eq(0).find('p').html('系统为高中生测试者提供了最适合的20种专业以及106种专业的适合度指数排行，通过评估个体与专业选择相关的兴趣、价值观、个性等因素，得出被测试者关于专业发展方向的指导性参数，为高中学填报大学志愿选择合适专业提供科学的参考。');
        }
    },
    //课程兴趣测评接口
    addEvent: function () {
        $(document).on('click', '#assessment-main .btn-assessment', function () {
            $(this).attr('type') != '5' ? ProTest.fetchProTestUrl($(this).attr('type'), $(this).attr('name')) : InterestTaskQuestion.init();
        });
    },

}
ProTest.init();


/**
 * 课程兴趣测评
 * @type {{init: InterestTaskQuestion.init, fetchQuestion: InterestTaskQuestion.fetchQuestion, renderQuestion: InterestTaskQuestion.renderQuestion, refreshDom: InterestTaskQuestion.refreshDom, renderPage: InterestTaskQuestion.renderPage, addEvent: InterestTaskQuestion.addEvent, eventPage: InterestTaskQuestion.eventPage, eventSubmit: InterestTaskQuestion.eventSubmit, postEvaluation: InterestTaskQuestion.postEvaluation}}
 */
var InterestTaskQuestion = {
    init: function () {
        this.curPage = 1;  //当前第几页
        this.nowPage = 10; //每页多少条
        this.totalPage = Math.ceil(this.fetchQuestion().length / this.nowPage);  //总共多少页
        this.renderPage();
        this.renderQuestion();
        this.addEvent();
    },
    //拉取试题
    fetchQuestion: function () {
        return questionData;
    },
    //渲染试题
    renderQuestion: function () {
        $('#course-test-container').removeClass('dh')
        var tpl = Handlebars.compile($('#question-tpl').html());
        $('#question').html(tpl(this.fetchQuestion()));
        layer.open({
            type: 1,
            title: '课程兴趣测评',
            content: $('#course-test-container'),
            area: ['100%', '100%'],
            maxmin: false,
            scrollbar:true
        })
        this.refreshDom();
    },
    //刷新Dom
    refreshDom: function () {
        var foo = this.curPage * this.nowPage;
        for (var i = foo - this.nowPage; i < foo; i++) {
            $('#question .col').eq(i).removeClass('dh');
        }
    },
    //渲染页码
    renderPage: function () {
        var pageArr = [];
        for (var i = 1; i <= this.totalPage; i++) {
            pageArr.push(i);
        }
        var tpl = Handlebars.compile($('#page-tpl').html());
        $('#page').html(tpl(pageArr));
        $('#page li').eq(this.curPage - 1).addClass('active');
    },
    //事件入口
    addEvent: function () {
        this.eventPage();
        this.eventSubmit();
    },
    eventPage: function () {
        var that = this;
        $('#page li').click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            that.curPage = parseInt($(this).text());
            $('#question .col').addClass('dh');
            that.refreshDom();
            $('.layui-layer-content').animate({scrollTop:0},400);
        });
    },
    eventSubmit: function () {
        var that = this;
        //试题答案分值
        var mapMark = {
            'A': 5,
            'B': 4,
            'C': 3,
            'D': 2,
            'E': 1
        };
        //用来存储各个科目每道题的分数
        var eachSubjectAdd = {
                wl: [],
                hx: [],
                sw: [],
                ty: [],
                zz: [],
                ls: [],
                dl: []
            },
        //各科目试题编号
            eachSubjectIndex = {
                wl: [1, 8, 15, 22, 29, 36, 43, 50, 57, 64],
                hx: [2, 9, 16, 23, 30, 37, 44, 51, 58, 65],
                sw: [3, 10, 17, 24, 31, 38, 45, 52, 59, 66],
                ty: [4, 11, 18, 25, 32, 39, 46, 53, 60, 67],
                zz: [5, 12, 19, 26, 33, 40, 47, 54, 61, 68],
                ls: [6, 13, 20, 27, 34, 41, 48, 55, 62, 69],
                dl: [7, 14, 21, 28, 35, 42, 49, 56, 63, 70]
            };
        var _parent = $('#question .question-answer');
        $('.submit-question').click(function () {
            for (var i = 0; i < _parent.length; i++) {
                var foo = _parent.eq(i).find("input[name='option']:checked").val()
                var numIndex = i + 1;
                if (!foo) {
                    layer.alert('评测第 ' + numIndex + '题没有填写，请填写后提交');
                    $('.layui-layer-btn0').css({
                        'background': '#d80c18',
                        'color': '#fff',
                        'outline': 'none',
                        'border': 'none'
                    })
                    return false;
                }
                if ($.inArray(numIndex, eachSubjectIndex.wl) > -1) {
                    eachSubjectAdd.wl.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.hx) > -1) {
                    eachSubjectAdd.hx.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.sw) > -1) {
                    eachSubjectAdd.sw.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.ty) > -1) {
                    eachSubjectAdd.ty.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.zz) > -1) {
                    eachSubjectAdd.zz.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.ls) > -1) {
                    eachSubjectAdd.ls.push(mapMark[foo])
                } else if ($.inArray(numIndex, eachSubjectIndex.dl) > -1) {
                    eachSubjectAdd.dl.push(mapMark[foo])
                }
            }
            //各个科目试题求和
            var eachSubjectSum = {
                wl: 0,
                hx: 0,
                sw: 0,
                ty: 0,
                zz: 0,
                ls: 0,
                dl: 0
            }
            for (var j in eachSubjectAdd.wl) {
                eachSubjectSum.wl += eachSubjectAdd.wl[j];
            }
            for (var j in eachSubjectAdd.hx) {
                eachSubjectSum.hx += eachSubjectAdd.hx[j];
            }
            for (var j in eachSubjectAdd.sw) {
                eachSubjectSum.sw += eachSubjectAdd.sw[j];
            }
            for (var j in eachSubjectAdd.ty) {
                eachSubjectSum.ty += eachSubjectAdd.ty[j];
            }
            for (var j in eachSubjectAdd.zz) {
                eachSubjectSum.zz += eachSubjectAdd.zz[j];
            }
            for (var j in eachSubjectAdd.ls) {
                eachSubjectSum.ls += eachSubjectAdd.ls[j];
            }
            for (var j in eachSubjectAdd.dl) {
                eachSubjectSum.dl += eachSubjectAdd.dl[j];
            }
            that.analyzeResult(eachSubjectSum);
        });
    },
    analyzeResult:function(data){
        ResultEvaluation.assemblyChartData(data);
    }
}





/**
 * 生成图标结果
 * @type {{init: ResultEvaluation.init, fetch: ResultEvaluation.fetch, assemblyChartData: ResultEvaluation.assemblyChartData, render: ResultEvaluation.render}}
 */
var ResultEvaluation = {
    assemblyChartData: function (data) {
        var assemblyData = {
            subjectLi: [],
            subjectLiScores: [],
            mainData: {}
        };
        var subjectListMap = {
            dl: '地理',
            hx: '化学',
            ls: '历史',
            sw: '生物',
            wl: '物理',
            zz: '政治',
            ty: '通用技术'
        }
        for (var i in data) {
            if (subjectListMap[i]) {
                assemblyData.subjectLi.push(subjectListMap[i]);
                assemblyData.subjectLiScores.push(data[i]);
                assemblyData.mainData = data;
            }
        }
        delete assemblyData.mainData.cdate;
        delete assemblyData.mainData.userId;
        delete assemblyData.mainData.id;
        this.render(assemblyData);
    },
    render: function (data) {
        var drawChart = function(){

            var myBarChart = echarts.init(document.getElementById('myBarChart'));
            var barOption = {
                title: {
                    show: true,
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
                        nameTextStyle: {
                            color: '#4A4A4A',
                            fontSize: 14
                        },
                        data: data.subjectLi, //科目
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
                        nameTextStyle: {
                            color: '#4A4A4A',
                            fontSize: 14
                        },
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
                        name: '分数',
                        type: 'bar',
                        barWidth: 44,
                        data: data.subjectLiScores,  //各科分数
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
            myBarChart.setOption(barOption);
        }
        //弹层展示最终结果
        layer.closeAll();
        layer.open({
            type: 1,
            title: '课程兴趣测评分析结果',
            content: $('#analyze-result'),
            area: ['100%', '100%'],
            maxmin: false,
            success:function(){
                drawChart();
            },
        })
        //测评提示
        var foo = (data.subjectLiScores).sort(function (a, b) {
            return a - b;
        });
        var Obj = {
            key: [],
            value: []
        };

        for (var i in data.mainData) {
            Obj.key.push(i);
            Obj.value.push(data.mainData[i]);
        }
        sortObj(Obj);
        function sortObj(Obj) {
            for (var i = 0; i < Obj.value.length - 1; i++) {
                for (var j = 0; j < Obj.value.length - 1 - i; j++) {
                    if (Obj.value[j] > Obj.value[j + 1]) {
                        var temp = Obj.value[j + 1];
                        Obj.value[j + 1] = Obj.value[j];
                        Obj.value[j] = temp;
                        var temKey = Obj.key[j + 1];
                        Obj.key[j + 1] = Obj.key[j];
                        Obj.key[j] = temKey;
                    }
                }
            }
        }
        data.mapOneToOne = {};
        for (var i in Obj.key) {
            data.mapOneToOne[Obj.key[i]] = Obj.value[i];

        }
        var evaluationTip = '';
        var subjectListMap = {
            dl: '地理',
            hx: '化学',
            ls: '历史',
            sw: '生物',
            wl: '物理',
            zz: '政治',
            ty: '通用技术'
        }
        var interstateSubject = subjectListMap[Obj.key[Obj.key.length - 1]] + '、' + subjectListMap[Obj.key[Obj.key.length - 2]] + '、' + subjectListMap[Obj.key[Obj.key.length - 3]];
        if (foo[foo.length - 1] - foo[0] < 10) {
            evaluationTip = '综上所述，你最感兴趣的3科是' + interstateSubject + ',由于您的各科兴趣分值相差不大，建议在选课时，多多参考平时的各科考试成绩进行决策~';
        } else {
            evaluationTip = '综上所述，你感兴趣的3科是' + interstateSubject + ',但' + subjectListMap[Obj.key[0]] + '（兴趣得分最低的一科）科目缺乏学习热情，可能会导致偏科比较严重，建议多多关注' + subjectListMap[Obj.key[0]] + '（兴趣得分最低的一科）学科~均衡发展';
        }
        $('.advise-tip').html(evaluationTip);
    }
}

