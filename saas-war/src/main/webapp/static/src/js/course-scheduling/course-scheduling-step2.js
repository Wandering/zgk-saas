/**
 * Created by machengcheng on 16/12/6.
 */

$(function () {

    $('.rule-item-tab li').click(function(){
        if(!$(this).hasClass('on')){
            $('.rule-item-tab li a').removeClass('active').eq($(this).index()).addClass('active');
            $('.rule-content').stop(true,true).hide().eq($(this).index()).show();
        }
    });

});