var tnId = Common.cookie.getCookie('tnId');

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
        Array.prototype.remove = function(val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
        var meunId=[];
        function zTreeOnCheck(event, treeId, treeNode) {
            //console.log(event)
            //console.log(treeId)
            //console.log(treeNode)
            if(treeNode.checked==true){
                meunId.push(treeNode.meunId)
                if(treeNode.children){
                    $.each(treeNode.children,function(i,v){
                        meunId.push(v.meunId)
                    });
                }
            }else{
                meunId.remove(treeNode.meunId)
                if(treeNode.children){
                    $.each(treeNode.children,function(i,v){
                        meunId.remove(v.meunId)
                    });
                }
            }
            console.log(meunId)
            $('.save-btn').attr('id-data',meunId);
        };
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
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };

        var zNodes = [];
        Common.ajaxFun('/role/getMeunsByRoleId/-1.do', 'GET', {}, function (res) {
            $.each(res.bizData,function(i,v){
                var zNodesObj = {};
                zNodesObj['id']=parseInt(i+1);
                zNodesObj['pId']=0;
                zNodesObj['name']= v.meunName;
                zNodesObj['meunId']= v.meunId;
                zNodes.push(zNodesObj);
                if(v.sonMeuns.length>0){
                    $.each(v.sonMeuns,function(k,n){
                        var zNodesSonMenuObj = {}
                        zNodesSonMenuObj['id']=parseInt((i+1)+ "" +(k+1));
                        zNodesSonMenuObj['pId']=parseInt(i+1);
                        zNodesSonMenuObj['name']= n.meunName;
                        zNodesSonMenuObj['meunId']= n.meunId;
                        zNodes.push(zNodesSonMenuObj);
                    });
                }
            })
        }, function (res) {
            alert("出错了");
        }, 'true');
        //console.log(JSON.stringify(zNodes))
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
    },
    addRole:function(){
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal role-layer">');
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
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-name"> 设置权限 </label>');
        contentHtml.push('<div class="col-sm-9">');
        contentHtml.push('<ul id="treeDemo" class="ztree"></ul>');
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="btn-box"><button class="btn btn-info save-btn">保存</button><button class="btn btn-primary close-btn">取消</button></div>');
        contentHtml.push('</div>');
        //Common.modal("addRole","添加角色",contentHtml.join(''),"内容","");
        layer.open({
            type: 1,
            title: '添加角色',
            offset: 'auto',
            area: ['362px', '420px'],
            content: contentHtml.join('')
        });
        that.renderTree();
        console.log(that.renderTree())
    }
};

var RoleManagementIns = new RoleManagementFun();

$('#addRole-btn').on('click', function () {
    RoleManagementIns.addRole();
});
// 保存
$('body').on('click','.save-btn',function () {
    var roleName = $.trim($('#role-name').val());
    var roleContent = $.trim($('#role-content').val());
    var idData = $(this).attr('id-data');
    if(roleName==""){
        layer.tips('请填写角色名称!', $('#role-name'));
        return false;
    }
    if(roleContent==""){
        layer.tips('请填写角色内容!', $('#role-content'));
        return false;
    }
    if(idData=="" || !idData){
        layer.tips('请选择角色权限!', $(this));
        return false;
    }
    var datas = {
        "clientInfo":{},
        "style":"",
        "data":{
            "roleName":roleName,
            "roleDesc":roleContent,
            "tnId":tnId,
            "meunIds":idData
        }
    };
    console.log(JSON.stringify(datas))
    Common.ajaxFun('/role/createRole.do', 'POST',JSON.stringify(datas), function (res) {
        console.log(res)
        if (res.rtnCode == "0000000") {

        }
    }, function (res) {
        alert("出错了");
    },null,true);



});


$('body').on('click','.close-btn',function(){
        layer.closeAll();
});

//
