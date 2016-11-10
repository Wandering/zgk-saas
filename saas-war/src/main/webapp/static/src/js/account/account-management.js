var tnId = Common.cookie.getCookie('tnId');

function AccountManagementFun() {
    this.init();
}

AccountManagementFun.prototype = {
    constructor: AccountManagementFun,
    init: function () {
        this.getAccountList();
    },
    getAccountList: function () {
        Common.ajaxFun('/account/queryUserBaseInfo/null/'+ tnId +'.do', 'GET', {}, function (res) {
            var myTemplate = Handlebars.compile($("#account-template").html());
            Handlebars.registerHelper('FormatTime', function (num) {
                return Common.getFormatTime(num);
            });
            $('#account-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    },
    addAccount: function () {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal account-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account"> 登录账号 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="account" placeholder="输入登录账号" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-name"> 人员名称 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="account-name" placeholder="输入人员名称" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-phone"> 联系方式 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="text" id="account-phone" placeholder="输入联系方式" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-content"> 选择角色 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<select class="sel-role col-xs-10 col-sm-10" id="sel-role"></select>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-content"> 状态 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<label><input name="start-radio" type="radio" value="1" class="ace" /><span class="lbl"> 启用</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        contentHtml.push('<label><input name="start-radio" type="radio" value="2" class="ace" /><span class="lbl"> 禁用</span></label>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        //Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");
        layer.open({
            type: 1,
            title: '添加账号',
            offset: 'auto',
            area: ['362px', '380px'],
            content: contentHtml.join('')
        });
        that.queryRolesByTnId();
    },
    createUser: function () {

    },
    queryRolesByTnId: function () {
        var option=[];
        option.push('<option value="00">请选择角色</option>')
        Common.ajaxFun(' /role/queryRolesByTnId/' + tnId + '.do', 'GET', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                $.each(res.bizData,function(i,v){
                    option.push('<option value="'+ v.id +'" roleDesc="'+ v.roleDesc +'" tnId="'+ v.tnId +'">'+ v.roleName +'</option>');
                });
                $('#sel-role').append(option);
            }
        }, function (res) {
            alert("出错了");
        });
    }
};

var AccountManagementIns = new AccountManagementFun();
$('#addAccount-btn').on('click', function () {
    AccountManagementIns.addAccount();
});


// 保存
$('body').on('click', '.save-btn', function () {
    var account = $.trim($('#account').val());
    var accountName = $.trim($('#account-name').val());
    var accountPhone = $.trim($('#account-phone').val());
    var selRole = $('#sel-role').val();
    var startRadio = $('input[name="start-radio"]:checked').val();
    if (account == "") {
        layer.tips('请填写登录账号!', $('#account'));
        return false;
    }
    if (accountName == "") {
        layer.tips('请填写人员名称!', $('#account-name'));
        return false;
    }
    if (accountPhone == "") {
        layer.tips('请填写联系方式!', $('#account-phone'));
        return false;
    }
    if (selRole == "00") {
        layer.tips('请选择角色!', $('#sel-role'));
        return false;
    }
    if (startRadio == undefined) {
        layer.tips('请选择状态!', $('.save-btn'));
        return false;
    }

    var datas = {
        "clientInfo":{},
        "style":"",
        "data":{
            "tnId":tnId,
            "userName":accountName,
            "userAccount":account,
            "telephone":accountPhone,
            "roleId":selRole
        }
    };
    console.log(datas)
    Common.ajaxFun('/account/createUser.do', 'POST', JSON.stringify(datas), function (res) {
        console.log(res)
        if (res.rtnCode == "0000000") {

        }
    }, function (res) {
        alert("出错了");
    },null,true);
});


$('body').on('click', '.close-btn', function () {
    layer.closeAll();
});


