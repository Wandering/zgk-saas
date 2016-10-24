

$('#seting-process1-btn').on('click', function () {
    var setingProcess11 = $('#seting-process1-1').val(),
        setingProcess12 = $('#seting-process1-2').val(),
        setingProcess13 = $('#seting-process1-3').val(),
        setingProcess14 = $('#seting-process1-4').val(),
        setingProcess15 = $('#seting-process1-5').val(),
        setingProcess16 = $('#seting-process1-6').val();
    var tnId = Common.cookie.getCookie('tnId');

    var nums = '';
    var numsArr = [];
    $('.seting-process1-input').each(function(i,v){

            console.log(i + "=" + $(this).val());
            //console.log($(this).val());
        if($.trim($(this).val())!=''){
            numsArr.push("-" + $(this).val());
        }
    });
    numsArr = numsArr.join('');
    console.log(numsArr.substring(1, numsArr.length));

    Common.ajaxFun('/config/class/setting/' + tnId + '/'+ numsArr +'.do', 'POST', {
        'tnId' : tnId,
        'nums': numsArr
    }, function (res) {
        console.log(res)
        if(res.rtnCode=="0000000"){

        }
    }, function (res) {
        alert("出错了");
    }, 'true');
});