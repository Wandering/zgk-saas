/**
 * Created by machengcheng on 16/12/6.
 */
$(function () {

    $('.rule-tab-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.rule-tab-list li a').removeClass('active').eq($(this).index()).addClass('active');
            $('.base-rule-content').stop(true,true).hide().eq($(this).index()).show();
        }
    });

});