var tnId = Common.cookie.getCookie('tnId');

Common.flowSteps();

$('#seting-process1-btn').on('click', function () {
    var that = $(this);
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