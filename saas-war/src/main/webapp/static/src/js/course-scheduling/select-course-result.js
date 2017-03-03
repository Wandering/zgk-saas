/**
 * @Time:2017-3-1
 * @By:pdeng
 * @Api:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=45269617
 * @UI:http://wiki.qtonecloud.cn/pages/viewpage.action?pageId=45267317
 */
var GLOBAL_CONSTANT = {
    tnId: Common.cookie.getCookie('tnId'), //租户ID
    taskId: 2,
    // taskId: Common.cookie.getCookie('taskId'),   //角色   2
    sType: null   //课程类型  0：高考科目  1：校本课程"
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
        // Common.ajaxFun('/saas/selectCourse/getSelectCourseSurvey.do', 'GET', {
        //         'taskId': GLOBAL_CONSTANT.taskId
        //     },
        //     function (res) {
        res = {
            "rtnCode": "0000000",
            "msg": "",
            bizData: {
                "name": '选课名称',//"选课名称",
                "grade": '高一',//年级编号 1：高一  2：高二  3：高三",
                "startTime": '2017-02-28 13:00:00',//"开始时间：2017-02-28 13:00:00",
                "endTime": '2017-02-28 13:00:00',//结束时间：2017-02-28 13:00:00",
                "selectedCount": 200,//"已选学生数",
                "unSelectedCount": 50,//"未选学生数",
                "unSelectedList": [{  // 未选学生集合
                    "className": '高一二班',//"班级名称",
                    "stuName": '王小明',//"学生名称",
                    "stuNo": 23232399 //"学生学号"
                }, {  // 未选学生集合
                    "className": '高一班',//"班级名称",
                    "stuName": '李晓飞',//"学生名称",
                    "stuNo": 99232 //"学生学号"
                }]
            }
        }
        if (res.rtnCode == "0000000") {
            ChooseTaskAbout.unSelectedList = res.bizData.unSelectedList;
            ChooseTaskAbout.set(res.bizData);
        }
        // }, function (res) {
        //     console.info(res.msg)
        // })
    },
    set: function (d) {
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
        // Common.ajaxFun('/saas/selectCourse/getSingleCourseSituation.do', 'GET', {
        //         'taskId': GLOBAL_CONSTANT.taskId
        //     },
        //     function (res) {
        res = {
            "rtnCode": "0000000",
            "msg": "",
            bizData: [{
                "courseName": '物理',//"单科组合名称",
                "stuCount": 600//人数"
            }, {
                "courseName": '物理',//"单科组合名称",
                "stuCount": 600//人数"
            }, {
                "courseName": '物理',//"单科组合名称",
                "stuCount": 600//人数"
            }, {
                "courseName": '物理',//"单科组合名称",
                "stuCount": 600//人数"
            }]
        }
        if (res.rtnCode == "0000000") {
            SingleChooseResult.set(res.bizData);
        }
        // }, function (res) {
        //     console.info(res.msg)
        // })
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
        // Common.ajaxFun('/saas/selectCourse/getGroupCourseSituation.do', 'GET', {
        //         'taskId': GLOBAL_CONSTANT.taskId
        //     },
        //     function (res) {
        res = {
            "rtnCode": "0000000",
            "msg": "",
            bizData: [{
                "courseName": '政治-历史-地理',//"单科组合名称",
                "stuCount": 50//人数"
            }, {
                "courseName": '生物-化学-地理',//"单科组合名称",
                "stuCount": 100//人数"
            }, {
                "courseName": '历史-通用技术-化学',//"单科组合名称",
                "stuCount": 23//人数"
            }, {
                "courseName": '历史-通用技术-化学',//"单科组合名称",
                "stuCount": 100//人数"
            }, {
                "courseName": '历史-通用技术-化学',//"单科组合名称",
                "stuCount": 31//人数"
            }, {
                "courseName": '历史-通用技术-化学',//"单科组合名称",
                "stuCount": 13//人数"
            }, {
                "courseName": '历史-生物-政治',//"单科组合名称",
                "stuCount": 41//人数"
            }]
        }
        if (res.rtnCode == "0000000") {
            AssemblyChooseResult.set(res.bizData);
        }
        // }, function (res) {
        //     console.info(res.msg)
        // })
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
        this.get();
        this.addEvent();
    },
    get: function () {
        Common.ajaxFun('/saas/selectCourse/getStuCourseDetail.do', 'GET', {
                "taskId":GLOBAL_CONSTANT.taskId,
                "type":this.type,
                "pageNo": 0,//"当前页 首次为 0 ",
                "pageSize":20//"页大小"
            },
            function (res) {
                res = {
                    "rtnCode":"0000000",
                    "msg":"",
                    bizData:{
                        "list":[{
                            "stuNo":"23",
                            "stuName":"张三",
                            "className":"高二一班",
                            "courses":[{
                                "courseId":"12",
                                "courseName":"美术"
                            },{
                                "courseId":"33",
                                "courseName":"声乐"
                            },{
                                "courseId":"23",
                                "courseName":"体育"
                            }]
                        },{
                            "stuNo":"100",
                            "stuName":"王五",
                            "className":"高二一班",
                            "courses":[{
                                "courseId":"12",
                                "courseName":"美术"
                            },{
                                "courseId":"33",
                                "courseName":"声乐"
                            },{
                                "courseId":"23",
                                "courseName":"体育"
                            }]
                        }],
                        "count":"200"
                    }
                }
        if (res.rtnCode == "0000000") {
            SelCourseTypeDetail.set(res.bizData);
        }
        }, function (res) {
            console.info(res.msg)
        })
    },
    set: function (d) {
        var tpl = Handlebars.compile($("#table-list-tpl").html());
        $('#table-list').html(tpl(d));
    },
    addEvent:function(){
        var that = this;
        $(document).on('change','[name="type-li"]',function(){
            that.type = $(this).val();
            that.get();
        });
    }
}
SelCourseTypeDetail.init();


























