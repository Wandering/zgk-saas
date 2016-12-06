/**
 * Created by pdeng on 2016/12/5.
 */


$('#role-scheduling-tab .role-tab li').click(function () {
    $(this).addClass('active').siblings().removeClass('active');
    var index = $(this).index();
    $('.bottom-page').eq(index).removeClass('dh').siblings().addClass('dh');
});
