var tnId = Common.cookie.getCookie('tnId');
$('#seting-process2-btn').on('click', function () {
    var that = $(this);
    var formGrade1V = $.trim($('#form-grade1').val());
    var formGrade2V = $.trim($('#form-grade2').val());
    var formGrade3V = $.trim($('#form-grade3').val());
    var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
    if (!re.test(formGrade1V) || formGrade1V > 100) {
        layer.tips('请输入正确的数字!', '#form-grade1');
        return false;
    }

    if (!re.test(formGrade2V) || formGrade2V > 100) {
        layer.tips('请输入正确的数字!', '#form-grade2');
        return false;
    }

    if (!re.test(formGrade3V) || formGrade3V > 100) {
        layer.tips('请输入正确的数字!', '#form-grade3');
        return false;
    }

    var nums = formGrade1V + '-' + formGrade2V + '-' + formGrade3V
    Common.ajaxFun('/config/classRoom/setting/'+ tnId +'/'+ nums +'.do', 'POST', {
        'tnId' : tnId,
        'nums': nums
    }, function (res) {
        console.log(res)
        if(res.rtnCode=="0000000"){
            if(res.bizData.result=="SUCCESS"){
                window.location.href="/seting-process3";
            }
            if(res.bizData.result=="FAIL"){
                alert('新增失败,请核对后在提交');
            }
        }
    }, function (res) {
        alert("出错了");
    }, 'true');







});