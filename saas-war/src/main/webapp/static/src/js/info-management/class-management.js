/**
 * Created by machengcheng on 16/11/8.
 */
var tnId = Common.cookie.getCookie('tnId');
/**
 * 班级类及其原型
 * @constructor
 */
function ClassManagement () {
    this.tnId = tnId;
    this.type = 'class';
    this.column = [];
    this.init();
}
ClassManagement.prototype = {
    constructor: ClassManagement,
    init: function () {
        this.getItem();
    },
    getItem: function () {
        var that = this;
        Common.ajaxFun('/config/get/' + that.type + '/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                if (data.length != 0) {
                    var columnHtml = [];
                    columnHtml.push('<tr>');
                    columnHtml.push('<th class="center"><label><input type="checkbox" /><span class="lbl"></span></label></th>');
                    $.each(data, function (i, k) {
                        columnHtml.push('<th class="center">' + k.name + '</th>');
                        that.column.push({
                            name: k.name,
                            enName: k.enName
                        });
                    });
                    columnHtml.push('</tr>');
                    $("#class-table thead").append(columnHtml.join(''));
                } else {

                }
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    }
};

/**
 * 添加班级类及其原型
 * @param type
 * @constructor
 */
function AddClassManagement () {
    ClassManagement.call(this);
    this.init();
}
AddClassManagement.prototype = {
    constructor: AddClassManagement,
    init: function () {

    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderGradeSelect(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderGradeSelect: function (data) {
        if (data.rtnCode == '0000000') {
            var gradeHtml = [];
            $.each(data.bizData.grades, function (i, k) {
                gradeHtml.push('<option>' + k.grade + '</option>');
            });
            $("#select-grade").append(gradeHtml.join(''));
        }
    },
    addClass: function (title) {
        var that = this;
        $.each(that.column, function (i, k) {
            console.info(k.name + ", " + k.enName);
        });
        var contentHtml = [];
        contentHtml.push('<div class="add-class-box">');
        contentHtml.push('<ul>');
        contentHtml.push('<li><span>选择年级</span><select id="select-grade"><option value="00">选择年级</option></select></li>');
        contentHtml.push('<li><span>入学年份</span><select id="select-year"><option value="00">入学年份</option><option>2016年</option><option>2015年</option></select></li>');
        contentHtml.push('<li><span>班级名称</span><input type="text" id="class-name" /></li>');
        contentHtml.push('<li><span>班级类型</span><input type="text" id="class-type" /></li>');
        contentHtml.push('<li><span>班级科类</span><input type="text" id="class-subject" /></li>');
        contentHtml.push('<li><span>班级编号</span><input type="text" id="class-number" /></li>');
        contentHtml.push('<li><span class="three-word">班主任</span><input type="text" class="class-teache" id="class-teacher" /></li>');
        contentHtml.push('<li><span>班级人数</span><input type="text" id="class-count" /></li>');
        contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
        contentHtml.push('</ul>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '533px'],
            content: contentHtml.join('')
        });
        this.getGrade();
    }
};

/**
 * 更新班级类及其原型
 * @param type
 * @constructor
 */
function UpdateClassManagement () {
    ClassManagement.call(this);
}
UpdateClassManagement.prototype = {
    constructor: UpdateClassManagement,
    init: function () {

    }
};

new ClassManagement();

//创建添加班级对象
var addClassManagement = new AddClassManagement('class');

//添加班级按钮操作
$(document).on("click", "#addRole-btn", function () {
    addClassManagement.addClass('添加班级');
});

//更新班级按钮操作
$(document).on("click", "#updateRole-btn", function () {
    var updateClassManagement = new UpdateClassManagement('class');
});

//确认操作按钮
$(document).on("click", "#add-btn", function () {
    layer.closeAll();
});
//取消操作按钮(关闭对话框)
$(document).on("click", ".cancel-btn", function () {
    layer.closeAll();
});
