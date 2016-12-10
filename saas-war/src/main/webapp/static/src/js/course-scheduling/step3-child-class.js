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
    getClassRoomTable:function(){
        var res = {
            "thechDate": "星期一|星期二|星期三|星期四|星期五",
            "thechTime": "430",
            "week": [
                [
                    "数学(金小江)",
                    "物理B1",
                    "物理A1",
                    "音乐(李颖)",
                    "外语(朱晓琳)",
                    "主题体育活动",
                    ""
                ],
                [
                    "数学(金小江)",
                    "物理B1",
                    "物理A1",
                    "音乐(李颖)",
                    "外语(朱晓琳)",
                    "主题体育活动",
                    ""
                ],
                [
                    "数学(金小江)",
                    "物理B1",
                    "物理A1",
                    "音乐(李颖)",
                    "外语(朱晓琳)",
                    "主题体育活动",
                    ""
                ],
                [
                    "数学(金小江)",
                    "物理B1",
                    "物理A1",
                    "音乐(李颖)",
                    "外语(朱晓琳)",
                    "主题体育活动",
                    ""
                ],
                [
                    "数学(金小江)",
                    "物理B1",
                    "物理A1",
                    "音乐(李颖)",
                    "外语(朱晓琳)",
                    "主题体育活动",
                    ""
                ]
            ]
        };

        var weekList = res.thechDate.split('|');

        console.log(weekList);
        var theadTemplate = Handlebars.compile($("#grade-thead-list-template").html());
        $('#grade-thead-list').html(theadTemplate(weekList));




        var classOrder = res.thechTime.split('');
        var classOrderCount = parseInt(classOrder[0]) + parseInt(classOrder[1]) + parseInt(classOrder[2]);
        var tbodyTemplate = Handlebars.compile($("#grade-tbody-list-template").html());
        Handlebars.registerHelper("addOne", function (index, options) {
            console.log(index)
            return parseInt(index) + 1;
        });
        $('#grade-tbody-list').html(tbodyTemplate(res.week));


        //var trTemplate = Handlebars.compile($("#grade-tr-list-template").html());
        //$('#grade-tr-list').html(trTemplate(classOrderCount));
        //
        //console.log(classOrderCount)









    }

};

var ClassRoomTableIns = new ClassRoomTable();

$(function () {
    // 选择教室
    // 导出


});


