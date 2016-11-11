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
            Handlebars.registerHelper('statusV', function (v) {
                var statusStr='';
                if(v=='0'){
                    statusStr = "启用";
                }else if(v=='1'){
                    statusStr = "禁用";
                }
                return statusStr;
            });
            $('#account-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    },
    addAccount: function (userAccount,userName,telephone,roleId,userId) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal account-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account"> 登录账号 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if(userAccount){
            contentHtml.push('<input type="text" value="'+ userAccount +'" id="account" placeholder="输入登录账号" class="col-xs-10 col-sm-10" />');
        }else{
            contentHtml.push('<input type="text" id="account" placeholder="输入登录账号" class="col-xs-10 col-sm-10" />');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-name"> 人员名称 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if(userName){
            contentHtml.push('<input type="text" id="account-name" value="'+ userName +'" placeholder="输入人员名称" class="col-xs-10 col-sm-10" />');
        }else{
            contentHtml.push('<input type="text" id="account-name" placeholder="输入人员名称" class="col-xs-10 col-sm-10" />');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="account-phone"> 联系方式 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if(telephone){
            contentHtml.push('<input type="text" id="account-phone" value="'+ telephone +'" placeholder="输入联系方式" class="col-xs-10 col-sm-10" />');
        }else{
            contentHtml.push('<input type="text" id="account-phone" placeholder="输入联系方式" class="col-xs-10 col-sm-10" />');
        }
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
        contentHtml.push('<label><input name="start-radio" type="radio" value="0" class="ace" /><span class="lbl"> 启用</span></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
        contentHtml.push('<label><input name="start-radio" type="radio" value="1" class="ace" /><span class="lbl"> 禁用</span></label>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        if(userId){
            contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn" userId="'+ userId +'">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        }else{
            contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        }
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '添加账号',
            offset: 'auto',
            area: ['362px', '380px'],
            content: contentHtml.join('')
        });
        that.queryRolesByTnId(roleId);
    },
    createUser: function () {

    },
    queryRolesByTnId: function (roleId) {
        var option=[];
        option.push('<option value="00">请选择角色</option>')
        Common.ajaxFun(' /role/queryRolesByTnId/' + tnId + '.do', 'GET', {}, function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                $.each(res.bizData,function(i,v){
                    option.push('<option value="'+ v.id +'" roleDesc="'+ v.roleDesc +'" tnId="'+ v.tnId +'">'+ v.roleName +'</option>');
                });
                $('#sel-role').append(option);
                if(roleId){
                    $('#sel-role option[value="'+ roleId +'"]').attr('selected','selected');
                }
            }
        }, function (res) {
            alert("出错了");
        });
    },
    resetPass:function(userId){
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal account-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="reset-pass"> 重设密码 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="password" id="reset-pass" placeholder="输入密码" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="confirm-pass"> 确认密码 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<input type="password" id="confirm-pass" placeholder="输入确认密码" class="col-xs-10 col-sm-10" />');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-pass-btn" userId="'+ userId +'">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        layer.open({
            type: 1,
            title: '添加账号',
            offset: 'auto',
            area: ['362px', '380px'],
            content: contentHtml.join('')
        });
    },
    deleteAccount:function(userId){
        var that = this;
        Common.ajaxFun('/account/deleteUser/'+ userId +'.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                that.getAccountList();
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
    var regMobile = /^1[3|4|5|6|7|8|9][0-9]{1}[0-9]{8}$/;
    var mobileResult = regMobile.test(accountPhone);
    if (mobileResult === false) {
        layer.tips('手机号有误,请重新输入!', $('#account-phone'));
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

    var updateId = $(this).attr('userId');
    var datas = {
        "clientInfo":{},
        "style":"",
        "data":{
            "tnId":tnId,
            "userName":accountName,
            "userAccount":account,
            "telephone":accountPhone,
            "roleId":selRole,
            "status":startRadio
        }
    };
    if(updateId){
        datas['data']['userId']= updateId;
        Common.ajaxFun('/account/updateUser.do', 'POST', JSON.stringify(datas), function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('修改成功!');
                AccountManagementIns.getAccountList();
            }
        }, function (res) {
            alert("出错了");
        },null,true);
    }else{
        Common.ajaxFun('/account/createUser.do', 'POST', JSON.stringify(datas), function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('添加成功!');
                AccountManagementIns.getAccountList();
            }
        }, function (res) {
            alert("出错了");
        },null,true);
    }

});


$('body').on('click', '.close-btn', function () {
    layer.closeAll();
});

// 修改
$('#modify-btn').on('click', function () {
    var checkboxLen = $('#account-tbody input:checked').length;
    if (checkboxLen == 0) {
        layer.tips('选择一项', $(this));
        return false;
    }
    if (checkboxLen > 1) {
        layer.tips('只能选择一项', $(this));
        return false;
    }
    var accountChecked = $('#account-tbody input:checked');
    var role = accountChecked.attr('role');
    var roleId = accountChecked.attr('roleId');
    var userAccount = accountChecked.attr('userAccount');
    var userName = accountChecked.attr('userName');
    var telephone = accountChecked.attr('telephone');
    var status = accountChecked.attr('status');
    var userId = accountChecked.attr('userId');
    AccountManagementIns.addAccount(userAccount,userName,telephone,roleId,userId);
    $('input[type="radio"][value="'+ status +'"]').attr('checked','checked');
});

// 重设密码
$('#resetPass-btn').on('click',function(){
    var checkboxLen = $('#account-tbody input:checked').length;
    if (checkboxLen == 0) {
        layer.tips('选择一项', $(this));
        return false;
    }
    if (checkboxLen > 1) {
        layer.tips('只能选择一项', $(this));
        return false;
    }
    var accountChecked = $('#account-tbody input:checked');
    var userId = accountChecked.attr('userId');
    AccountManagementIns.resetPass(userId);
});

// 重设密码保存
$('body').on('click','.save-pass-btn',function(){
    var userId = $(this).attr('userId');
    var resetPassV = $.trim($('#reset-pass').val());
    var confirmPassV = $.trim($('#confirm-pass').val());
    if (resetPassV == "") {
        layer.tips('请输入重设密码!', $(this));
        return false;
    }
    if (confirmPassV.length < 6) {
        layer.tips('重设密码最少6位!', $(this));
        return false;
    }
    if (confirmPassV == "") {
        layer.tips('请输入确认密码!', $(this));
        return false;
    }
    if (confirmPassV != resetPassV) {
        layer.tips('输入密码不一致!', $(this));
        return false;
    }
    Common.ajaxFun('/account/updatePwd/'+ userId +'/'+ confirmPassV +'.do', 'GET', {}, function (res) {
        if (res.rtnCode == "0000000") {
            layer.closeAll();
            layer.msg('重设成功!');
            AccountManagementIns.getAccountList();
        }
    }, function (res) {
        alert("出错了");
    },null,true);
});

// 删除
$('#close-btn').on('click', function () {
    var checkboxLen = $('#account-tbody input:checked').length;
    if (checkboxLen == 0) {
        layer.tips('至少选择一项', $(this));
        return false;
    }
    if (checkboxLen > 1) {
        layer.tips('删除只能选择一项', $(this));
        return false;
    }
    layer.confirm('确定删除?', {
        btn: ['确定', '关闭'] //按钮
    }, function () {
        var userId = $('#account-tbody input:checked').attr('userId');
        AccountManagementIns.deleteAccount(userId);
    }, function () {
        layer.closeAll();
    });
});



