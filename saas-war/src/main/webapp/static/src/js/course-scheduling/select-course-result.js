/**
 * @Time:2017-3-1
 * @By:pdeng
 * @Api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=45269617
 * @UI:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=45267317
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    // taskId: 2,
    taskId: Common.cookie.getCookie('taskId'),   //角色   2
    sType: null   //课程类型  0：高考科目  1：校本课程"
}


/**
 * 根据type判断用户是否使用本次数据
 * type包含五种状态，为4代表绑定
 */
if (Common.cookie.getCookie('selectCourseState') == 4) {
    $('#select-modify,.handle-btns').remove();
}


/**
 * 选课任务名称
 * @type {{init: ChooseTaskAbout.init, get: ChooseTaskAbout.get, set: ChooseTaskAbout.set, addEvent: ChooseTaskAbout.addEvent}}
 */
var ChooseTaskAbout = {
    init: function () {
        this.get();
        this.addEvent();
    },
    get: function () {
        Common.ajaxFun('/saas/selectCourse/getSelectCourseSurvey.do', 'GET', {
                'taskId': GLOBAL_CONSTANT.taskId
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    ChooseTaskAbout.unSelectedList = res.bizData.unSelectedList;
                    ChooseTaskAbout.set(res.bizData);
                }
            }, function (res) {
                console.info(res.msg)
            })
    },
    set: function (d) {
        Handlebars.registerHelper('formatTime', function (v) {
            return Common.getFormatTime(v);
        })
        Handlebars.registerHelper('gradeFoo', function (v) {
            return ['', '高一', '高二', '高三'][v];
        })
        var tpl = Handlebars.compile($("#choose-task-about-tpl").html());
        $('#choose-task-about').html(tpl(d));
    },
    addEvent: function () {
        $(document).on('click', '.no-choose-course', function () {
            var tpl = Handlebars.compile($("#no-choose-std-list-tpl").html());
            $('#no-choose-std-list').html(tpl(ChooseTaskAbout.unSelectedList));
            var _thisText = $(this).text();
            layer.open({
                type: 1,
                title: _thisText,
                offset: 'auto',
                area: ['475px', 'auto'],
                content: $('#no-choose-std'),
                cancel: function () {
                    layer.closeAll();
                }
            })
        })
    }
};
ChooseTaskAbout.init();


/**
 * 单科选课结果
 * @type {{init: SingleChooseResult.init, get: SingleChooseResult.get, set: SingleChooseResult.set}}
 */
var SingleChooseResult = {
    init: function () {
        this.get();
    },
    get: function () {
        Common.ajaxFun('/saas/selectCourse/getSingleCourseSituation.do', 'GET', {
                'taskId': GLOBAL_CONSTANT.taskId
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    SingleChooseResult.set(res.bizData);
                }
            }, function (res) {
                console.info(res.msg)
            })
    },
    set: function (d) {
        var tpl = Handlebars.compile($("#sel-single-course-tpl").html());
        $('#sel-single-course').html(tpl(d));
    }
}
SingleChooseResult.init();


/**
 * 组合选课结果
 * @type {{init: AssemblyChooseResult.init, get: AssemblyChooseResult.get, set: AssemblyChooseResult.set}}
 */
var AssemblyChooseResult = {
    init: function () {
        this.get();
    },
    get: function () {
        Common.ajaxFun('/saas/selectCourse/getGroupCourseSituation.do', 'GET', {
                'taskId': GLOBAL_CONSTANT.taskId
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    AssemblyChooseResult.set(res.bizData);
                }
            }, function (res) {
                console.info(res.msg)
            })
    },
    set: function (d) {
        var dataJson = {
            groups: [],
            stuCount: []
        };
        d.forEach(function (v) {
            dataJson.groups.push(v.courseName)
            dataJson.stuCount.push(v.stuCount)
        })
        var assemblyCourseChart = echarts.init(document.getElementById('assembly-course-chart'));
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
            grid: {
                left: '2%',
                right: '8%',
                top: '13%',
                containLabel: true,
                borderWidth: 0,
                backgroundColor: 'transparent'
            },
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
                        fontSize: 12
                    },
                    data: dataJson.groups,
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
                    },
                    axisLabel: {
                        interval: 0,
                        rotate: dataJson.groups.length > 6 ? -20 : 0
                        //formatter:function(val){
                        //    return val.split("").join("\n");
                        //}
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    name: '选课人数',
                    nameTextStyle: {
                        color: '#4A4A4A',
                        fontSize: 12
                    },
                    min: Math.min(dataJson.stuCount),
                    max: Math.max(dataJson.stuCount),
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
                    barWidth: 20,
                    data: dataJson.stuCount, //[1466, 912, 1276, 1107],
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
            ]
        };
        assemblyCourseChart.setOption(groupCourseAnalysisOption);
    }
}
AssemblyChooseResult.init();


/**
 * 查询学生高考课程选课详情
 * "type":"课程类型  0：高考科目  1：校本课程",
 */

var SelCourseTypeDetail = {
    init: function () {
        this.type = 0; //高考课程
        this.page = {
            'offset': 0,
            'rows': 20,
            'count': ''
        }
        this.get();
        this.addEvent();
    },
    get: function () {
        Common.ajaxFun('/saas/selectCourse/getStuCourseDetail.do', 'GET', {
                "taskId": GLOBAL_CONSTANT.taskId,
                "type": this.type,
                "pageNo": this.page.offset,//"当前页 首次为 0 ",
                "pageSize": this.page.rows//"页大小"
            },
            function (res) {
                if (res.rtnCode == "0000000") {
                    SelCourseTypeDetail.page.count = res.bizData.count;
                    SelCourseTypeDetail.set(res.bizData);
                }
            }, function (res) {
                console.info(res.msg)
            })
    },
    //查询课程信息
    getCourseBaseInfo: function () {
        Common.ajaxFun('/saas/selectCourse/getCourseBaseInfo.do', 'GET', {
                "taskId": GLOBAL_CONSTANT.taskId,
                "type": this.type
            },
            function (d) {
                if (d.rtnCode == "0000000") {
                    var arrayFoo = [];
                    var tag = ['', '一', '二', '三']
                    for (var i = 1; i <= $('#header-li').val(); i++) {
                        arrayFoo.push(tag[i]);
                    }
                    var dataJson = {
                        data: d.bizData,
                        subjectLength: arrayFoo   //选修课个数
                    }
                    var tpl = Handlebars.compile($("#modify-choose-layer-tpl").html());
                    $('#modify-choose-layer').html(tpl(dataJson));
                }
            }, function (res) {
                console.info(res.msg)
            }, 'true')
    },
    set: function (d) {
        // if(!$.isEmptyObject){
        //     return false;
        // }
        //动态设置表头
        Handlebars.registerHelper('addOne', function (v) {
            return ['', '一', '二', '三'][v + 1];
        });
        var tpl = Handlebars.compile($("#table-header-tpl").html());
        if(d.list){
            $('#table-header').html(tpl(d.list[0].courses));
        }
        //渲染table
        var tpl = Handlebars.compile($("#table-list-tpl").html());
        $('#table-list').html(tpl(d));
        this.pagination(); //分页
    },

    pagination: function () {
        var that = this;
        $(".pagination").createPage({
            pageCount: Math.ceil(that.page.count / that.page.rows),
            current: Math.ceil(that.page.offset / that.page.rows) + 1,
            backFn: function (p) {
                $(".pagination-bar .current-page").html(p);
                that.page.offset = (p - 1) * that.page.rows;
                that.get();
            }
        });
    },
    modifyItem: function () {
        var that = this;
        var foo = $('#table-list').find('input[type=checkbox]:checked').attr('data-attr');
        if (!foo) {
            layer.msg('请选择一项进行修改');
        }
        this.getCourseBaseInfo();//拉取课程信息
        var $parentDom = $('#modify-choose-layer li');
        $parentDom.eq(0).find('input').val(foo.split('|')[0]);
        $parentDom.eq(1).find('input').val(foo.split('|')[1]);
        $parentDom.eq(2).find('input').val(foo.split('|')[2]);
        var xkSubjectValArr = foo.split('|')[3].split('-').slice(0, 3);
        for (var i = 0; i < xkSubjectValArr.length; i++) {
            $parentDom.eq(i + 3).find('select').val(xkSubjectValArr[i]);
        }
        layer.open({
            type: 1,
            title: '修改选课结果',
            offset: 'auto',
            area: ['auto', 'auto'],
            content: $('#modify-choose-layer'),
            cancel: function () {
                layer.closeAll();
            }
        })

        /**
         * 修改提交
         */
        $(document).on('click', '#save-btn', function () {
            //验证
            var $parentDom = $('#modify-choose-layer li'),
                $stdNum = $parentDom.eq(0).find('input').val(),
                fooSubjectArr = [];
            $parentDom.find('select').each(function () {
                fooSubjectArr.push($(this).val())
            });
            if (fooSubjectArr.length > 1) {
                if (fooSubjectArr[0] == fooSubjectArr[1] ||
                    fooSubjectArr[0] == fooSubjectArr[2] ||
                    fooSubjectArr[1] == fooSubjectArr[2]
                ) {
                    layer.msg('选择科目有重复，无法保存');
                    return false;
                }
            } else {
                if (fooSubjectArr[0] == '00') {
                    layer.msg('选择科目没有选择');
                    return false;
                }
            }
            var data = {
                "clientInfo": {},
                "style": "",
                "data": {
                    "stuNo": $stdNum,
                    "taskId": GLOBAL_CONSTANT.taskId,
                    "type": that.type,
                    "courseIds": fooSubjectArr.join(',')//"所选科目id集合,逗号拼接 eg:1,2,3"
                }
            }
            Common.ajaxFun('/saas/selectCourse/updateStuCourse.do', 'post', JSON.stringify(data), function (d) {
                if (d.rtnCode == "0000000") {
                    layer.closeAll();
                    layer.msg('修改成功');
                    that.get();
                }
            }, function (res) {
                layer.msg(res.msg);
            }, null, true)
        });
    },
    useThisData: function () {
        Common.ajaxFun('/saas/selectCourse/confirmSelectCourse.do', 'get', {
            "taskId": GLOBAL_CONSTANT.taskId,
        }, function (d) {
            if (d.rtnCode == "0000000") {
                layer.msg('本次数据使用成功');
                Common.cookie.setCookie('selectCourseState',4);
                $('#select-modify,.handle-btns').remove();
            }
        }, function (res) {
            layer.msg(res.msg);
        })
    },
    addEvent: function () {
        var that = this;
        //切换高考课程和非高考课程
        $(document).on('change', '[name="type-li"]', function () {
            that.type = $(this).val();
            that.page = {
                'offset': 0,
                'rows': 20,
                'count': ''
            }
            that.get();
        });
        //修改
        $('#select-modify').click(function () {
            that.modifyItem();
        });
        //勾选
        $(document).on('change', '.check-template :checkbox', function () {
            $('.check-template :checkbox').prop('checked', false);
            $(this).prop('checked', true);
        })
        //确实使用本次数据
        $('#confirm-this-data').click(function () {
            that.useThisData();
        })
        $('#reset-task').click(function () {
            window.location.href = "/select-course";
        })
    }
}
SelCourseTypeDetail.init();

























