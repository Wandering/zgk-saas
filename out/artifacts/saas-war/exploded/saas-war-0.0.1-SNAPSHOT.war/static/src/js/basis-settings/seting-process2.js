var tnId = Common.cookie.getCookie('tnId');

//Common.flowSteps();






function SetingProcess2() {
    this.init();
}
SetingProcess2.prototype = {
    constructor: SetingProcess2,
    init: function () {
        this.getGrade();
    },
    getGrade: function () {
        var that = this;
        Common.ajaxFun('/config/grade/get/' + tnId + '.do', 'GET', {
            'tnId': tnId
        }, function (res) {
            //console.log(res)
            if (res.rtnCode == "0000000") {
                that.renderList(res);
            }
        }, function (res) {
            alert("出错了");
        }, true);
    },
    renderList: function (data) {
        console.log(data);
        if (data.rtnCode == "0000000") {
            var gradeArr = [];
            $.each(data.bizData.grades, function (i, v) {
                gradeArr.push('<div class="form-group">');
                gradeArr.push('<label class="col-sm-3 control-label no-padding-right" for="form-grade1">');
                gradeArr.push( v.grade +'教室数量 </label>');
                gradeArr.push('<div class="col-sm-9">');
                gradeArr.push('<input type="text" gradeId="'+ v.id +'" id="form-grade'+ (i+1) +'" placeholder="0--100以内" class="col-xs-10 col-xs-10 col-sm-10 grade-item"/>');
                gradeArr.push('</div>');
                gradeArr.push('</div>');
            });
            $('#grade-group').append(gradeArr.join(''));
        }
    },
    eventClick:function(){
        var nums=[];
        $('body').find('.grade-item').each(function(i,v){
            var formGradeId = $('#form-grade'+(i+1)).attr('gradeid');
            var formGradeV = $.trim($('#form-grade'+(i+1)).val());
            var re = /^[0-9]+.?[0-9]*$/; //判断字符串是否为数字 //判断正整数 /^[1-9]+[0-9]*]*$/
            if (!re.test(formGradeV) || formGradeV > 100) {
                layer.tips('请输入正确的数字!', '#form-grade'+(i+1));
                return false;
            }
            nums.push("-"+formGradeId+":"+formGradeV);
        });
        nums = nums.join('');
        nums = nums.substring(1, nums.length);
        console.log(nums);
        Common.ajaxFun('/config/classRoom/setting/' + tnId + '/' + nums + '.do', 'POST', {
            'tnId': tnId,
            'nums': nums
        }, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                if (res.bizData.result == "SUCCESS") {
                    window.location.href = "/seting-process3";
                }
                if (res.bizData.result == "FAIL") {
                    alert('新增失败,请核对后在提交');
                }
            }
        }, function (res) {
            alert("出错了");
        }, 'true');

    }
};
var SetingProcess2Obj = new SetingProcess2();
$('#seting-process2-btn').on('click', function () {
    SetingProcess2Obj.eventClick();
});
