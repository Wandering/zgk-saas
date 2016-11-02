/**
 * Created by machengcheng on 16/11/1.
 */

$("#addGrade-btn").on("click", function () {
    var contentHtml = [];
    contentHtml.push('<form class="form-horizontal" role="form">');
    contentHtml.push('<div class="form-group">');
    contentHtml.push('<label class="col-sm-5 control-label no-padding-right" for="role-name">*添加年级名称</label>');
    contentHtml.push('<input type="text" id="role-name" placeholder="输入年级名称" class="col-xs-10 col-sm-5" />');
    contentHtml.push('</div>');
    contentHtml.push('</form>');

    layer.open({
        type: 1,
        title:'<span style="font-size: 14px;color: #CB171D;">添加年级</span>',
        offset: 'auto',
        area: ['362px','185px'],
        shadeClose: true, //点击遮罩关闭
        content: contentHtml.join('')
    });
});