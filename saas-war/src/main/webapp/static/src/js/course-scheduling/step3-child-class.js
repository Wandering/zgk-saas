var tnId = Common.cookie.getCookie('tnId');
function ClassRoomTable() {
    this.init();
}
ClassRoomTable.prototype = {
    constructor: ClassRoomTable,
    init: function () {
        this.getClassRoomTable();
    },
    // 拉取课表

    getClassRoomTable: function () {
        Common.ajaxFun('/scheduleTask/teacher/course/result.do', 'GET', {
            "param":""
        }, function (result) {
            console.log(result)
            if (result.rtnCode == "0000000") {
                var res =  result.bizData.result;
                Handlebars.registerHelper("addOne", function (index) {
                    return parseInt(index) + 1;
                });
                Handlebars.registerHelper("createN", function (res) {
                    var str = '';
                    for(var i=1;i<=res;i++){
                        str += '<p class="tbody-item">'+i+'</p>'
                    }
                    return str;
                });
                var weekList = res.teachDate.split('|');
                var theadTemplate = Handlebars.compile($("#grade-thead-list-template").html());
                $('#grade-thead-list').html(theadTemplate(weekList));
                var tbodyTemplate = Handlebars.compile($("#grade-tbody-list-template").html());
                $('#grade-tbody-list').html(tbodyTemplate(res.week));
            } else {
                layer.msg(result.msg);
            }
        }, function (result) {
            layer.msg(result.msg);
        }, true);
        //var res = {
        //    "thechDate": "星期一|星期二|星期三|星期四|星期五",
        //    "thechTime": "430",
        //    "week": [
        //        [
        //            "数学(金小江1)",
        //            "物理B1",
        //            "物理A1",
        //            "音乐(李颖)",
        //            "外语(朱晓琳)",
        //            "主题体育活动",
        //            "23"
        //        ],
        //        [
        //            "数学(金小江2)",
        //            "物理B1",
        //            "物理A1",
        //            "音乐(李颖)",
        //            "外语(朱晓琳)",
        //            "主题体育活动",
        //            ""
        //        ],
        //        [
        //            "数学(金小江3)",
        //            "物理B1",
        //            "物理A1",
        //            "音乐(李颖)",
        //            "外语(朱晓琳)",
        //            "主题体育活动",
        //            ""
        //        ],
        //        [
        //            "数学(金小江4)",
        //            "物理B1",
        //            "物理A1",
        //            "音乐(李颖)",
        //            "外语(朱晓琳)",
        //            "主题体育活动",
        //            ""
        //        ],
        //        [
        //            "数学(金小江5)",
        //            "物理B1",
        //            "物理A1",
        //            "音乐(李颖)",
        //            "外语(朱晓琳)",
        //            "主题体育活动",
        //            "23"
        //        ]
        //    ]
        //};

    }

};

var ClassRoomTableIns = new ClassRoomTable();

$(function () {
    // 选择教室
    // 导出


});


