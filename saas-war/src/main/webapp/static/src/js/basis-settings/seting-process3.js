var tnId = Common.cookie.getCookie('tnId');

function SetingProcess3() {
    this.init();
}
SetingProcess3.prototype = {
    constructor: SetingProcess3,
    init: function () {
        this.getClassList();
    },
    getClassList: function () {
        Common.ajaxFun('/config/get/class/' + tnId + '.do', 'GET', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                var data = res.bizData.configList;
                $.each(data, function (i, v) {
                    var classTemplate = [];
                    classTemplate.push('<tr>');
                    classTemplate.push('<td class="center">');
                    classTemplate.push('<label seldata="' + v.id + '">');
                    classTemplate.push('<input type="checkbox" class="ace" />');
                    classTemplate.push('<span class="lbl"></span>');
                    classTemplate.push('</label>');
                    classTemplate.push('</td>');
                    classTemplate.push('<td class="center">' + (i + 1) + '</td>');
                    classTemplate.push('<td class="center">' + v.name + '</td>');
                    classTemplate.push('<td class="center"><a href="javascript:;" deldata="' + v.id + '">删除</a></td>');
                    classTemplate.push('</tr>');
                    $('#class-template').append(classTemplate.join(''));
                });
            }
        }, function (res) {
            alert("出错了");
        });
    },
    initClassItem: function () {
        var initData = '';
        Common.ajaxFun('/config/getInit/class.do', 'GET', {}, function (res) {
            //console.log(res)
            if (res.rtnCode == "0000000") {
                initData = res.bizData.configList;
            }
        }, function (res) {
            alert("出错了");
        }, true);
        //console.log(initData)
        var contentHtml = [];
        contentHtml.push('<div class="add-label">');
        $.each(initData, function (i, v) {
            contentHtml.push('<label>');
            contentHtml.push('<input name="form-field-checkbox" type="checkbox" iddata="' + v.id + '" class="ace ' + v.class_in_year + '" />');
            contentHtml.push('<span class="lbl">' + v.chName + '</span>');
            contentHtml.push('</label>');
        });
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info" id="sel-confirm">确定选择</button></div>');
        layer.open({
            type: 1,
            title: '添加班级字段',
            area: ['690px', '220px'], //宽高
            content: contentHtml.join('')
        });
    },
    eventSelConfirm:function(){
        var ids = [];
        $.each($('.add-label input:checked'), function (i, v) {
            //console.log(i)
            //console.log($(v).attr('iddata'))
            ids.push("-" + $(v).attr('iddata'));
        });
        ids = ids.join('');
        ids = ids.substring(1, ids.length);
        console.log(ids);
        Common.ajaxFun('/config/import/class/' + tnId + '.do', 'POST', {
            'ids': ids
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
            }
        }, function (res) {
            alert("出错了");
        });
    }
};
var SetingProcess3Obj = new SetingProcess3();

// 新增班级字段

$('#add-btn').on('click', function () {
    SetingProcess3Obj.initClassItem()
});
// 添加班级字段确定
$('body').on('click', '#sel-confirm', function () {
    SetingProcess3Obj.eventSelConfirm()
});





//var tnId = Common.cookie.getCookie('tnId');
//// 默认拉取列表 & 刷新
//Common.ajaxFun('/config/get/class/'+ tnId +'.do', 'GET', {
//    'type' : 'class',
//    'tnId': tnId
//}, function (res) {
//    console.log(res)
//    if(res.rtnCode=="0000000"){
//        var data = res.bizData.configList;
//        $.each(data,function(i,v){
//            var classTemplate = [];
//            classTemplate.push('<tr>');
//            classTemplate.push('<td class="center">');
//            classTemplate.push('<label seldata="'+ v.id +'">');
//            classTemplate.push('<input type="checkbox" class="ace" />');
//            classTemplate.push('<span class="lbl"></span>');
//            classTemplate.push('</label>');
//            classTemplate.push('</td>');
//            classTemplate.push('<td class="center">'+ (i+1) +'</td>');
//            classTemplate.push('<td class="center">'+ v.name +'</td>');
//            classTemplate.push('<td class="center"><a href="javascript:;" deldata="'+ v.id +'">删除</a></td>');
//            classTemplate.push('</tr>');
//            $('#class-template').append(classTemplate.join(''));
//        });
//    }
//}, function (res) {
//    alert("出错了");
//});
//
//
//// 新增班级字段
//
//$('#add-btn').on('click',function(){
//    var initData = '';
//    Common.ajaxFun('/config/getInit/class.do', 'GET', {
//        'type' : 'class'
//    }, function (res) {
//        //console.log(res)
//        if(res.rtnCode=="0000000"){
//            initData = res.bizData.configList;
//        }
//    }, function (res) {
//        alert("出错了");
//    }, true);
//    //console.log(initData)
//    var contentHtml = [];
//    contentHtml.push('<div class="add-label">');
//    $.each(initData,function(i,v){
//        //console.log(i)
//        //console.log(v)
//        contentHtml.push('<label>');
//        contentHtml.push('<input name="form-field-checkbox" type="checkbox" iddata="'+ v.id +'" class="ace '+ v.class_in_year +'" />');
//        contentHtml.push('<span class="lbl">'+ v.chName +'</span>');
//        contentHtml.push('</label>');
//    });
//    contentHtml.push('</div>');
//    contentHtml.push('<div class="btn-box"><button class="btn btn-info" id="sel-confirm">确定选择</button></div>');
//    layer.open({
//        type: 1,
//        title:'添加班级字段',
//        area: ['690px', '220px'], //宽高
//        content: contentHtml.join('')
//    });
//});
//
//
//
//$('body').on('click','#sel-confirm',function(){
//    var ids = [];
//    $.each($('.add-label input:checked'),function(i,v){
//        //console.log(i)
//        //console.log($(v).attr('iddata'))
//        ids.push("-" + $(v).attr('iddata'));
//    });
//    ids = ids.join('');
//    ids = ids.substring(1, ids.length);
//    Common.ajaxFun('/config/import/class/'+ tnId +'.do', 'POST', {
//        'type':'class',
//        'ids' : ids
//    }, function (res) {
//        console.log(res)
//        if(res.rtnCode=="0000000"){
//        }
//    }, function (res) {
//        alert("出错了");
//    });
//});
//
//// 生成租户自选表头
//
//
//$('#seting-process3-btn').on('click', function () {
//
//});


