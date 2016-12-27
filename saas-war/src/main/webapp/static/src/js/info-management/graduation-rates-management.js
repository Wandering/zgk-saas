/**
 * Created by machengcheng on 16/11/18.
 */

var tnId = Common.cookie.getCookie('tnId');

function NumberManagement () {

}
NumberManagement.prototype = {
    constructor: NumberManagement,
    init: function () {
        this.getNumber();
        this.tableDrag();
    },
    getNumber: function () {
        var that = this;
        Common.ajaxFun('/manage/get/enrollingRatio/' + tnId + '.do', 'GET', {}, function (res) {
            layer.load(1, {shade: [0.3,'#000']});
            if (res.rtnCode == "0000000") {
                var ratioHtml = [];
                var data = res.bizData.result;
                $.each(data, function (i, k) {
                    ratioHtml.push('<tr rowid="' + k.id + '">');
                    ratioHtml.push('<td class="center"><label><input type="checkbox" rid="' + k.id + '" class="ace" /><span class="lbl"></span></label></td>');
                    ratioHtml.push('<td class="center index" indexid="' + k.id + '">' + (i + 1) + '</td>');
                    if (k.year) {
                        ratioHtml.push('<td class="center" name="year">' + k.year + '</td>');
                    } else {
                        ratioHtml.push('<td class="center" name="year">-</td>');
                    }
                    ratioHtml.push('<td class="center">' + k.stu3numbers + '</td>');
                    ratioHtml.push('<td class="center">' + k.batch1enrolls + '</td>');
                    ratioHtml.push('<td class="center">' + k.batch2enrolls + '</td>');
                    //ratioHtml.push('<td class="center">' + k.batch3enrolls + '</td>');
                    ratioHtml.push('<td class="center">' + k.batch4enrolls + '</td>');
                });
                $('#ratio-manage-list').html(ratioHtml.join(''));
            }
            setTimeout(function () {
                layer.closeAll();
            }, 500);
        }, function (res) {
            layer.msg("出错了");
        }, true);
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
        //yearContentHtml.push('<li><span>三本上线人数</span><input type="text" class="rate-input" id="batch-third" /></li>');
        yearContentHtml.push('<li><span>高职上线人数</span><input type="text" class="rate-input" id="batch-fourth" /></li>');
        yearContentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="add-btn">确认添加</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
        yearContentHtml.push('</ul>');
        yearContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '390px'],
            content: yearContentHtml.join('')
        });
        that.getYear();
    },
    updateYearData: function (title) {
        var that = this;
        var yearContentHtml = [];
        yearContentHtml.push('<div class="add-class-box add-year-box">');
        yearContentHtml.push('<ul id="update-ratio-list">');
        yearContentHtml.push('<li><span class="year-title">选择年份</span><select id="rate-year"><option value="00">选择年份</option></select></li>');
        yearContentHtml.push('<li><span>高三考生数量</span><input type="text" class="rate-input" id="senior-three" /></li>');
        yearContentHtml.push('<li><span>一本上线人数</span><input type="text" class="rate-input" id="batch-first" /></li>');
        yearContentHtml.push('<li><span>二本上线人数</span><input type="text" class="rate-input" id="batch-second" /></li>');
        //yearContentHtml.push('<li><span>三本上线人数</span><input type="text" class="rate-input" id="batch-third" /></li>');
        yearContentHtml.push('<li><span>高职上线人数</span><input type="text" class="rate-input" id="batch-fourth" /></li>');
        yearContentHtml.push('<li><div class="opt-btn-box"><button class="btn btn-red" id="update-btn">确认修改</button><button class="btn btn-cancel cancel-btn">取消</button></div></li>');
        yearContentHtml.push('</ul>');
        yearContentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '<span style="color: #CB171D;font-size: 14px;">' + title + "</span>",
            offset: 'auto',
            area: ['362px', '390px'],
            content: yearContentHtml.join('')
        });
        that.getYear();
        var rowid = $(".check-template :checkbox:checked").attr('rid');
        var rowItem = $('#ratio-manage-list tr[rowid="' + rowid + '"]').find('td');
        if (rowItem.eq(2).html() != '-') {
            $('#rate-year').val(rowItem.eq(2).html());
        } else {
            $('#rate-year').val('00');
        }

        for (var i = 0; i < rowItem.length - 3; i++) {
            (function (k) {
                var tempVal = rowItem.eq(k+3).html();
                if (tempVal != '-') {
                    $('#update-ratio-list input[type="text"]').eq(k).val(tempVal);
                } else {
                    $('#update-ratio-list input[type="text"]').eq(k).val('');
                }
            })(i);
        }
    },
    tableDrag: function () {
        var that = this;
        //表格排序
        var fixHelperModified = function (e, tr) {
                var $originals = tr.children();
                var $helper = tr.clone();
                $helper.children().each(function (index) {
                    $(this).width($originals.eq(index).width());
                });
                return $helper;
            },
            updateIndex = function (e, ui) {
                var ids = [];
                $('td.index', ui.item.parent()).each(function (i) {
                    $(this).html(i + 1);
                    ids.push($(this).attr('indexid'));
                    console.info($(this));
                });
                ids = ids.join('-');///manage/sort/enrollingRatio/{tnId}/{ids}.do
                Common.ajaxFun('/manage/sort/enrollingRatio/' + tnId + '/'+ ids +'.do', 'POST', {}, function (res) {
                    if (res.rtnCode == "0000000") {
                        if (res.bizData.result) {
                            layer.msg('排序成功', {time: 1000});
                        }else{
                            layer.msg(res.bizData.result);
                        }
                    }
                }, function (res) {
                    layer.msg("出错了", {time: 1000});
                }, true, null);
            };
        $("#ratio-manage-list").sortable({
            helper: fixHelperModified,
            stop: updateIndex,
            axis: "y"
        }).disableSelection();
    }
};

var numberManagement = new NumberManagement();
numberManagement.init();

$(document).on('click', '#addRole-btn', function () {
    numberManagement.addYearData('添加年份');
});

$(document).on('click', '#add-btn', function () {//新增升学率
    var year = $('#rate-year').val(),
        yearName = $('#rate-year option[value=' + year + ']').text();
    if (year == '00') {
        layer.msg('请选择年份!', {time: 1000});
        $('#rate-year').focus();
        return;
    }

    for (var i = 0; i < $('td[name="year"]').length; i++) {
        var tempYear = $('td[name="year"]').eq(i).text().trim();
        if (tempYear == yearName) {
            layer.msg('该年份已经存在!');
            return;
        }
    }

    for (var i = 0; i < $('.add-year-box input[type="text"]').length; i++) {
        var node = $('.add-year-box input[type="text"]').eq(i);
        if (node.val().trim() == '') {
            layer.msg(node.prev().text() + '不能为空!', {time: 1000});
            node.focus();
            return;
        }
    }

    var stu3numbers = parseInt($('#senior-three').val().trim());
    var batch1enrolls = parseInt($('#batch-first').val().trim());
    var batch2enrolls = parseInt($('#batch-second').val().trim());
    //var batch3enrolls = parseInt($('#batch-third').val().trim());
    var batch4enrolls = parseInt($('#batch-fourth').val().trim());
    var rowCount = $('#ratio-manage-list').find('tr').length;
    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "EnrollingRatioObj": {
                "tnId": tnId,
                "year": year,
                "ratioOrder": rowCount + 1,
                "stu3numbers": stu3numbers,
                "batch1enrolls": batch1enrolls,
                "batch2enrolls": batch2enrolls,
                "batch3enrolls": 0,
                "batch4enrolls": batch4enrolls
            }
        }
    };
    Common.ajaxFun('/manage/enrollingRatio/add.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            numberManagement.getNumber();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});

$(document).on('click', '#updateRole-btn', function () {
    var that = $(this);
    var chknum = $(".check-template :checkbox:checked").size();
    if(chknum!='1'){
        layer.tips('修改只能选择一项!', that, {time: 1000});
        return false;
    }
    numberManagement = new NumberManagement();
    numberManagement.updateYearData('更新升学率');
});
$(document).on('click', '#update-btn', function () {
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
    var rowid = $(".check-template :checkbox:checked").attr('rid');
    var stu3numbers = parseInt($('#senior-three').val().trim());
    var batch1enrolls = parseInt($('#batch-first').val().trim());
    var batch2enrolls = parseInt($('#batch-second').val().trim());
    //var batch3enrolls = parseInt($('#batch-third').val().trim());
    var batch4enrolls = parseInt($('#batch-fourth').val().trim());
    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "EnrollingRatioObj": {
                "id": rowid,
                "tnId": tnId,
                "year": year,
                "stu3numbers": stu3numbers,
                "batch1enrolls": batch1enrolls,
                "batch2enrolls": batch2enrolls,
                "batch3enrolls": 0,
                "batch4enrolls": batch4enrolls
            }
        }
    };
    Common.ajaxFun('/manage/enrollingRatio/modify.do', 'POST', JSON.stringify(datas), function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            numberManagement.getNumber();
        }
    }, function (res) {
        layer.msg("出错了");
    }, null,true);
});

$(document).on('click', '.cancel-btn', function () {
    layer.closeAll();
});