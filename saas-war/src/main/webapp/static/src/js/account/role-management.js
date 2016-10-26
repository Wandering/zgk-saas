var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    view:{
        showIcon:false
    }
};

var zNodes = [];

Common.ajaxFun('/role/getMeunsByRoleId/-1.do', 'GET', {}, function (res) {
    console.log(res)
    $.each(res.bizData,function(i,v){
        zNodes.push()
    })


}, function (res) {
    alert("出错了");
}, 'true');
//
//var zNodes = [
//    {id: 1, pId: 0, name: "随意勾选 1", open: true},
//    {id: 11, pId: 1, name: "随意勾选 1-1"},
//    {id: 12, pId: 1, name: "随意勾选 1-2"},
//    {id: 2, pId: 0, name: "随意勾选 2", open: true},
//    {id: 21, pId: 2, name: "随意勾选 2-1"},
//    {id: 22, pId: 2, name: "随意勾选 2-2"},
//    {id: 23, pId: 2, name: "随意勾选 2-3"}
//];


$.fn.zTree.init($("#treeDemo"), setting, zNodes);


function RoleManagementFun() {
    this.init();
}

RoleManagementFun.prototype = {
    constructor: RoleManagementFun,
    init: function () {
        this.getAllPermissions();
    },
    getAllPermissions: function () {

    },
    renderTree: function () {

    }
};

var RoleManagementIns = new RoleManagementFun();

$('#addRole-btn').on('click', function () {

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
    //Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");

    layer.open({
        type: 1,
        title: '添加角色',
        offset: 'auto',
        area: ['362px', '500px'],
        shadeClose: true, //点击遮罩关闭
        content: contentHtml.join('')
    });
});


//
