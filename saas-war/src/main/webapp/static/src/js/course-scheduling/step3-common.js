/**
 * 地址Hash处理
 */

var HashHandle = {
    init: function () {
        this.hashArr = ['#class', '#teacher', '#student', '#all'];
        this.addEvent();
    },
    addEvent: function () {
        $('#role-scheduling-tab .role-tab li').click(function () {
            $(this).addClass('active').siblings().removeClass('active');
            var index = $(this).index();
            $('.bottom-page').eq(index).removeClass('dh').siblings().addClass('dh');
            window.location.hash = HashHandle.hashArr[index];
        });
    }
}
HashHandle.init();