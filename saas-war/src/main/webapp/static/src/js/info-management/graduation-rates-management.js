/**
 * Created by machengcheng on 16/11/18.
 */

var tnId = Common.cookie.getCookie('tnId');

function NumberManagement () {

}
NumberManagement.prototype = {
    constructor: NumberManagement,
    init: function () {

    },
    getYear: function () {
        var that = this;
        Common.ajaxFun('/config/get/school/year.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                that.renderYearSelect(res);
            }
        }, function (res) {
            layer.msg("出错了");
        }, true);
    },
    renderYearSelect: function (data) {
        if (data.rtnCode == '0000000') {
            var yearHtml = [];
            $.each(data.bizData.result, function (i, k) {
                yearHtml.push('<option value="' + k + '">' + k + '</option>');
            });
            $("#rate-year").append(yearHtml.join(''));
        }
    },
    addYearData: function (title) {
        var that = this;
        var yearContentHtml = [];
        yearContentHtml.push('<div class="add-class-box add-year-box">');
        yearContentHtml.push('<ul>');
        yearContentHtml.push('<li><span class="year-title">选择年份</span><select id="rate-year"><option value="00">选择年份</option></select></li>');
        yearContentHtml.push('<li><span>高三考生数量</span><input type="text" class="rate-input" id="senior-three" /></li>');
        yearContentHtml.push('<li><span>一本上线人数</span><input type="text" class="rate-input" id="batch-first" /></li>');
        yearContentHtml.push('<li><span>二本上线人数</span><input type="text" class="rate-input" id="batch-second" /></li>');
        yearContentHtml.push('<li><span>三本上线人数</span><input type="text" class="rate-input" id="batch-third" /></li>');
        yearContentHtml.push('<li><span>高职上线人数</span><input type="text" class="rate-input" id="batch-fourth" /></li>');
        yearContentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
        yearContentHtml.push('</ul>');
        yearContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '442px'],
            content: yearContentHtml.join('')
        });
        that.getYear();
    }
};

var numberManagement = new NumberManagement();

$(document).on('click', '#addRole-btn', function () {
    numberManagement.addYearData('添加年份');
});

$(document).on('click', '#add-btn', function () {//新增升学率
    var year = $('#rate-year').val();
    if (year == '00') {
        layer.msg('请选择年份!', {time: 1000});
        $('#rate-year').focus();
        return;
    }
    for (var i = 0; i < $('.add-year-box input[type="text"]').length; i++) {
        var node = $('.add-year-box input[type="text"]').eq(i);
        if (node.val().trim() == '') {
            layer.msg(node.prev().text() + '不能为空!', {time: 1000});
            node.focus();
            return;
        }
    }
    var stu3numbers = $('#senior-three').val().trim();
    var batch1enrolls = $('#batch-first').val().trim();
    var batch2enrolls = $('#batch-second').val().trim();
    var batch3enrolls = $('#batch-third').val().trim();
    var batch4enrolls = $('#batch-fourth').val().trim();
    Common.ajaxFun('/manage/enrollingRatio/add.do', 'POST', {
        'tnId': tnId,//租户ID
        'stu3numbers': stu3numbers,//高三考生数量
        'batch1enrolls': batch1enrolls,//一本上线人数
        'batch2enrolls': batch2enrolls,//二本上线人数
        'batch3enrolls': batch3enrolls,//三本上线人数
        'batch4enrolls': batch4enrolls,//高职上线人数
        'year': year
    }, function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});