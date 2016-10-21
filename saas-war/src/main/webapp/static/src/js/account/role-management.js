$('#addRole-btn').on('click',function(){
    var contentHtml = [];
    contentHtml.push('<form class="form-horizontal" role="form">');
    contentHtml.push('<div class="form-group">');
    contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-name"> 角色名称 </label>');
    contentHtml.push('<div class="col-sm-9">');
    contentHtml.push('<input type="text" id="role-name" placeholder="输入角色名称" class="col-xs-10 col-sm-5" />');
    contentHtml.push('</div>');
    contentHtml.push('</div>');
    contentHtml.push('<div class="form-group">');
    contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-content"> 角色内容 </label>');
    contentHtml.push('<div class="col-sm-9">');
    contentHtml.push('<input type="text" id="role-content" placeholder="输入角色名称" class="col-xs-10 col-sm-5" />');
    contentHtml.push('</div>');
    contentHtml.push('</div>');
    contentHtml.push('</form>');
    Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");
    $('#addRole').modal();
});



Common.ajaxFun('/getMeunsByRoleId/1.do','GET',{
    'roleId':'1'
},function (res) {
    console.log(res)

}, function (res) {
    alert("出错了");
}, 'true');