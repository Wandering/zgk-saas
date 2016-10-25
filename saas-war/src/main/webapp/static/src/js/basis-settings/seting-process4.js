var tnId = Common.cookie.getCookie('tnId');
$('#seting-process4-btn').on('click', function () {
    var that = $(this);
    var gradeAllNumV = $.trim($('#grade-all-num').val());
    var oneBatchNumV = $.trim($('#one-batch-num').val());
    var twoBatchNumV = $.trim($('#two-batch-num').val());
    var threeBatchNumV = $.trim($('#three-batch-num').val());
    var fourBatchNumV = $.trim($('#four-batch-num').val());
    var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
    if (!re.test(gradeAllNumV) || gradeAllNumV=='') {
        layer.tips('请输入正确的数字!', '#grade-all-num');
        return false;
    }

    if (!re.test(oneBatchNumV) || oneBatchNumV=='') {
        layer.tips('请输入正确的数字!', '#one-batch-num');
        return false;
    }
    if (!re.test(twoBatchNumV) || twoBatchNumV=='') {
        layer.tips('请输入正确的数字!', '#two-batch-num');
        return false;
    }
    if (!re.test(threeBatchNumV) || threeBatchNumV=='') {
        layer.tips('请输入正确的数字!', '#three-batch-num');
        return false;
    }
    if (!re.test(fourBatchNumV) || fourBatchNumV=='') {
        layer.tips('请输入正确的数字!', '#four-batch-num');
        return false;
    }



    var nums = gradeAllNumV + '-' + oneBatchNumV + '-' + twoBatchNumV + '-' + threeBatchNumV + '-' + fourBatchNumV;
    Common.ajaxFun('/config/classRoom/setting/'+ tnId +'/'+ nums +'.do', 'GET', {
        'tnId' : tnId,
        'nums': nums
    }, function (res) {
        console.log(res)
        if(res.rtnCode=="0000000"){
            if(res.bizData.result=="SUCCESS"){
                window.location.href="/seting-process5";
            }
            if(res.bizData.result=="FAIL"){
                layer.tips('请核对后在提交!', that);
            }
        }
    }, function (res) {
        alert("出错了");
    }, 'true');







});