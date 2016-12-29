var tnId = Common.cookie.getCookie('tnId');

function RoleManagementFun() {
    this.init();
}

RoleManagementFun.prototype = {
    constructor: RoleManagementFun,
    init: function () {
        this.getAllRole();
    },
    getAllRole: function () {
        Common.ajaxFun('/role/queryRolesByTnId/' + tnId + '.do', 'GET', {}, function (res) {
            var myTemplate = Handlebars.compile($("#role-template").html());
            Handlebars.registerHelper('FormatTime', function (num) {
                return Common.getFormatTime(num);
            });
            $('#role-tbody').html(myTemplate(res));
        }, function (res) {
            alert("出错了");
        });
    },
    renderTree: function (roleId) {
        Array.prototype.remove = function (val) {
            var index = this.indexOf(val);
            if (index > -1) {
                this.splice(index, 1);
            }
        };
        var meunId = [];

        function zTreeOnCheck(event, treeId, treeNode) {
            //console.log(event)
            //console.log(treeId)
            //console.log(treeNode)
            if (treeNode.checked == true) {
                meunId.push(treeNode.meunId)
                if (treeNode.children) {
                    $.each(treeNode.children, function (i, v) {
                        meunId.push(v.meunId)
                    });
                }
            } else {
                meunId.remove(treeNode.meunId)
                if (treeNode.children) {
                    $.each(treeNode.children, function (i, v) {
                        meunId.remove(v.meunId)
                    });
                }
            }
            console.log(meunId)
            $('.save-btn').attr('id-data', meunId);
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
            view: {
                showIcon: false
            },
            callback: {
                onCheck: zTreeOnCheck
            }
        };
        var meunIdArr = [];
        var sonMeunsIdArr = [];
        if (roleId) {
            Common.ajaxFun('/role/getMeunsByRoleId/' + roleId + '.do', 'GET', {}, function (res) {
                console.log(res)
                $.each(res.bizData, function (i, v) {
                    meunIdArr.push(v.meunId);
                    meunId.push(v.meunId);
                    if (v.sonMeuns.length > 0) {
                        $.each(v.sonMeuns, function (k, n) {
                            sonMeunsIdArr.push(n.meunId);
                            meunId.push(n.meunId);
                        });
                    }
                })
            }, function (res) {
                alert("出错了");
            }, true, true);
            $('.save-btn').attr({
                'id-data': meunId,
                'updateId':roleId
            });
        }
        var zNodes = [];
        Common.ajaxFun('/role/getMeunsByRoleId/-1.do', 'GET', {}, function (res) {
            $.each(res.bizData, function (i, v) {
                var zNodesObj = {};
                zNodesObj['id'] = parseInt(i + 1);
                zNodesObj['pId'] = 0;
                zNodesObj['name'] = v.meunName;
                zNodesObj['meunId'] = v.meunId;
                for (var g in meunIdArr) {
                    if (v.meunId == meunIdArr[g]) {
                        zNodesObj['checked'] = 'true';
                    }
                }
                zNodes.push(zNodesObj);
                if (v.sonMeuns.length > 0) {
                    $.each(v.sonMeuns, function (k, n) {
                        var zNodesSonMenuObj = {}
                        zNodesSonMenuObj['id'] = parseInt((i + 1) + "" + (k + 1));
                        zNodesSonMenuObj['pId'] = parseInt(i + 1);
                        zNodesSonMenuObj['name'] = n.meunName;
                        zNodesSonMenuObj['meunId'] = n.meunId;
                        for (var f in sonMeunsIdArr) {
                            if (n.meunId == sonMeunsIdArr[f]) {
                                zNodesSonMenuObj['checked'] = 'true';
                            }
                        }
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
    addRole: function (roleId, roleName, roleDesc) {
        var that = this;
        var contentHtml = [];
        contentHtml.push('<div class="form-horizontal role-layer">');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-name"> 角色名称 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if (roleName) {
            contentHtml.push('<input type="text" id="role-name" value="' + roleName + '" placeholder="输入角色名称" class="col-xs-10 col-sm-5" />');
        } else {
            contentHtml.push('<input type="text" id="role-name" placeholder="输入角色名称" class="col-xs-10 col-sm-5" />');
        }
        contentHtml.push('</div>');
        contentHtml.push('</div>');
        contentHtml.push('<div class="form-group">');
        contentHtml.push('<label class="col-sm-3 control-label no-padding-right" for="role-content"> 角色内容 </label>');
        contentHtml.push('<div class="col-sm-9">');
        if (roleDesc) {
            contentHtml.push('<input type="text" value="' + roleDesc + '" id="role-content" placeholder="输入角色内容" class="col-xs-10 col-sm-5" />');
        } else {
            contentHtml.push('<input type="text" id="role-content" placeholder="输入角色内容" class="col-xs-10 col-sm-5" />');
        }
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
        if(roleId) {
            layer.open({
                type: 1,
                title: '修改角色',
                offset: 'auto',
                area: ['362px', '420px'],
                content: contentHtml.join('')
            });
        }else{
            layer.open({
                type: 1,
                title: '添加角色',
                offset: 'auto',
                area: ['362px', '420px'],
                content: contentHtml.join('')
            });
        }
        that.renderTree(roleId);
    },
    deleteRole: function (roleId) {
        var that = this;
        Common.ajaxFun('/role/deleteRole/' + roleId + '/' + tnId + '.do', 'GET', {}, function (res) {
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                layer.msg('删除成功!');
                that.getAllRole();
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        });
    }
};

var RoleManagementIns = new RoleManagementFun();

$('#addRole-btn').on('click', function () {
    RoleManagementIns.addRole();
});
// 保存
$('body').on('click', '.save-btn', function () {
    var roleName = $.trim($('#role-name').val());
    var roleContent = $.trim($('#role-content').val());
    var idData = $(this).attr('id-data');
    if (roleName == "") {
        layer.tips('输入角色名称!', $('#role-name'));
        return false;
    }
    if (roleContent == "") {
        layer.tips('输入角色内容!', $('#role-content'));
        return false;
    }
    if (idData == "" || !idData) {
        layer.tips('请选择角色权限!', $(this));
        return false;
    }
    var updateId = $(this).attr('updateId');

    var datas = {
        "clientInfo": {},
        "style": "",
        "data": {
            "roleName": roleName,
            "roleDesc": roleContent,
            "tnId": tnId,
            "meunIds": idData
        }
    };
    if(updateId){
        datas['data']['roleId']= updateId;
        Common.ajaxFun('/role/updateRole.do', 'POST', JSON.stringify(datas), function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                RoleManagementIns.getAllRole();
                layer.msg('修改成功!');
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, null, true);
    }else{
        console.log(JSON.stringify(datas))
        Common.ajaxFun('/role/createRole.do', 'POST', JSON.stringify(datas), function (res) {
            console.log(res)
            if (res.rtnCode == "0000000") {
                layer.closeAll();
                RoleManagementIns.getAllRole();
                layer.msg('添加成功!');
            }else{
                layer.msg(res.msg);
            }
        }, function (res) {
            layer.msg(res.msg);
        }, null, true);
    }

});

// 删除
$('#close-btn').on('click', function () {
    var checkboxLen = $('#role-tbody input:checked').length;
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
        var roleId = $('#role-tbody input:checked').attr('roleid');
        RoleManagementIns.deleteRole(roleId);
    }, function () {
        layer.closeAll();
    });
});

// 修改
$('#modify-btn').on('click', function () {

    var checkboxLen = $('#role-tbody input:checked').length;
    if (checkboxLen == 0) {
        layer.tips('至少选择一项', $(this));
        return false;
    }
    if (checkboxLen > 1) {
        layer.tips('修改只能选择一项', $(this));
        return false;
    }
    var roleChecked = $('#role-tbody input:checked');
    var roleId = roleChecked.attr('roleid');
    var roleName = roleChecked.attr('roleName');
    var roleDesc = roleChecked.attr('roleDesc');
    RoleManagementIns.addRole(roleId, roleName, roleDesc);
});