var tnId = Common.cookie.getCookie('tnId');

Common.ajaxFun('/config/get/step/'+ tnId +'.do', 'GET', {}, function (res) {
    console.log(res)
    if (res.rtnCode == "0000000") {

    }
}, function (res) {
    alert("出错了");
});










$('#seting-process1-btn').on('click', function () {
    var that = $(this);
    var numsArr = [];
    $('.seting-process1-input').each(function (i, v) {
        console.log(i + "=" + $(this).val());
        if ($.trim($(this).val()) != '') {
            numsArr.push("-" + $(this).val());
        }
    });
    numsArr = numsArr.join('');
    numsArr = numsArr.substring(1, numsArr.length);
    //console.log(numsArr.substring(1, numsArr.length));
    if (numsArr == '') {
        layer.tips('至少添加一项!', that);
        return false;
    }
    Common.ajaxFun('/config/class/setting/' + tnId + '/' + numsArr + '.do', 'POST', {
        'tnId': tnId,
        'nums': numsArr
    }, function (res) {
        console.log(res)
        if (res.rtnCode == "0000000") {
            if (res.bizData.result == "SUCCESS") {
                window.location.href="/seting-process2";
            }
            if (res.bizData.result == "FAIL") {
                alert('新增失败,请核对后在提交');
            }
        }
    }, function (res) {
        alert("出错了");
    });
});