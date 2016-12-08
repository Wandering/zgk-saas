var tnId = Common.cookie.getCookie('tnId');

//Common.flowSteps();

$('#seting-process1-btn').on('click', function () {
    var that = $(this);
    var numsArr = [];
    //$('.seting-process1-input').each(function (i, v) {
    //    //console.log(i + "=" + $.trim($(this).val()).length);
    //    if($.trim($(this).val()).length > 12){
    //        layer.tips('第'+ (i+1) +'项字数限制在12字以内!', that);
    //        return false;
    //    }
    //    if ($.trim($(this).val()) !="") {
    //        numsArr.push("-" + $(this).val());
    //    }
    //});
    //
    //numsArr = numsArr.join('');
    //numsArr = numsArr.substring(1, numsArr.length);
    //console.log(numsArr);
    //if (numsArr == '') {
    //    layer.tips('至少添加一项!', that);
    //    return false;
    //}
    var numsArr = "高一年级-高二年级-高三年级";
    Common.ajaxFun('/config/class/setting/' + tnId + '/' + numsArr + '.do', 'POST', {}, function (res) {
        if (res.rtnCode == "0000000") {
            if (res.bizData.result == "SUCCESS") {
                window.location.href="/seting-process2";
            }
            if (res.bizData.result == "FAIL") {
                alert('新增失败,请核对后在提交');
            }
        }else{
            layer.msg(res.msg);
        }
    }, function (res) {
        layer.msg(res.msg);
    });
});