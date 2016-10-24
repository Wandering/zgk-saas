

$('#seting-process2-btn').on('click', function () {

    var reg = new RegExp("^[0-9]*$");
    var obj = $("#form-grade1");
    if(!/^[0-9]*$/.test(obj.value)){
        alert("请输入数字!");
    }
});