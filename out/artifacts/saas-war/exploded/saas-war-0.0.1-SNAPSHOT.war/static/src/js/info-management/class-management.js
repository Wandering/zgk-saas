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
    this.columnArr = [];
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
                        that.columnArr.push({
                            name: k.name,
                            enName: k.enName
                        });
                    });
                    columnHtml.push('</tr>');
                    $("#class-table thead").html(columnHtml.join(''));
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
}
AddClassManagement.prototype = new ClassManagement();
AddClassManagement.prototype.constructor = AddClassManagement;
AddClassManagement.prototype.init = function (columnArr) {
    var that = this;
    $.each(columnArr, function (i, k) {
        that.columnArr.push({
            name: k.name,
            enName: k.enName
        });
    });
};
AddClassManagement.prototype.getGrade = function () {
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
};
AddClassManagement.prototype.renderGradeSelect = function (data) {
    if (data.rtnCode == '0000000') {
        var gradeHtml = [];
        $.each(data.bizData.grades, function (i, k) {
            gradeHtml.push('<option>' + k.grade + '</option>');
        });
        $("#select-grade").append(gradeHtml.join(''));
    }
};
AddClassManagement.prototype.addClass = function (title) {
    var that = this;
    var contentHtml = [];
    //contentHtml.push('<div class="add-class-box">');
    //contentHtml.push('<ul>');
    //contentHtml.push('<li><span>选择年级</span><select id="select-grade"><option value="00">选择年级</option></select></li>');
    //contentHtml.push('<li><span>入学年份</span><select id="select-year"><option value="00">入学年份</option><option>2016年</option><option>2015年</option></select></li>');
    //contentHtml.push('<li><span>班级名称</span><input type="text" id="class-name" /></li>');
    //contentHtml.push('<li><span>班级类型</span><input type="text" id="class-type" /></li>');
    //contentHtml.push('<li><span>班级科类</span><input type="text" id="class-subject" /></li>');
    //contentHtml.push('<li><span>班级编号</span><input type="text" id="class-number" /></li>');
    //contentHtml.push('<li><span class="three-word">班主任</span><input type="text" class="class-teache" id="class-teacher" /></li>');
    //contentHtml.push('<li><span>班级人数</span><input type="text" id="class-count" /></li>');
    //contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
    //contentHtml.push('</ul>');
    //contentHtml.push('</div>');
    contentHtml.push('<div class="add-class-box">');
    contentHtml.push('<ul>');
    $.each(that.columnArr, function (i, k) {
        if (k.enName != 'class_grade' && k.enName != 'class_in_year') {
            if (k.enName != 'class_boss') {
                contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
            } else {
                contentHtml.push('<li><span>' + k.name + '</span><input type="text" id="' + k.enName + '" /></li>');
            }
        } else {
            contentHtml.push('<li><span>' + k.name + '</span><select id="' + k.enName + '"><option value="00">' + k.enName + '</option></select></li>');
            if (k.enName == 'class_grade') {
                that.getGrade();
            }
        }
    });
    contentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
    contentHtml.push('</ul>');
    contentHtml.push('</div>');
    layer.open({
        type: 1,
        title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
        offset: 'auto',
        area: ['362px', 'auto'],
        content: contentHtml.join('')
    });
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

var classManagement = new ClassManagement();
classManagement.init();

//创建添加班级对象
var addClassManagement = new AddClassManagement();
addClassManagement.init(classManagement.columnArr);

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
    var postData = [];
    //postData.push('[');
    //$.each(addClassManagement.columnArr, function (i, k) {
    //    if (i != addClassManagement.columnArr.length - 1) {
    //        postData.push('{' + '"' + k.enName + '"' + ': ' + '"' + $('#' + k.enName).val().trim() + '"' + '},');
    //    } else {
    //        postData.push('{' + '"' + k.enName + '"' + ': ' + '"' + $('#' + k.enName).val().trim() + '"' + '}');
    //    }
    //
    //});
    //postData.push(']');
    $.each(addClassManagement.columnArr, function (i, k) {
        postData.push({"id":i,"key":k.enName,"value":$('#' + k.enName).val().trim()});
    });
    console.info(JSON.stringify({teantCustomList:postData}));
    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "teantCustomList": postData
        }
    };
    //' + addClassManagement.type + '/' + tnId + '
    Common.ajaxFun('/manage/teant/custom/add.do', 'POST',JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});
//取消操作按钮(关闭对话框)
$(document).on("click", ".cancel-btn", function () {
    layer.closeAll();
});

//班级设置操作
$(document).on('click', '#class-settings-btn', function () {
    layer.open({
        type: 2,
        title: '班级设置',
        shadeClose: true,
        shade: 0.8,
        area: ['60%', '70%'],
        content: '/class-settings',
        cancel: function () {
            classManagement.init();
        }
    });
});