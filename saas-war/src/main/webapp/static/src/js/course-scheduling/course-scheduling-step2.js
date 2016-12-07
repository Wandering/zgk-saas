/**
 * Created by machengcheng on 16/12/6.
 */

$(function () {

    $(document).on('change', '#no-course-time', function () {
        var itemVal = parseInt($(this).val());
        switch(itemVal) {
            case 1:
                $('#class-no-array').show();
                $('#teacher-no-array').hide();
                $('#course-no-array').hide();
                break;
            case 2:
                $('#class-no-array').hide();
                $('#teacher-no-array').show();
                $('#course-no-array').hide();
                break;
            case 3:
                $('#class-no-array').hide();
                $('#teacher-no-array').hide();
                $('#course-no-array').show();
                break;
            default:
                break;
        }
    });

    $('.class-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.class-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $('.teacher-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.teacher-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $('.course-list li').click(function(){
        if(!$(this).hasClass('on')){
            $('.course-list li a').removeClass('active').eq($(this).index()).addClass('active');
        }
    });

    $(document).on('click', '.no-assign-table td', function () {
        var curText = $(this).text().trim();
        var rowIndex = $(this).index();
        var columnIndex = $(this).parent().index();
        alert('rowIndex: ' + rowIndex + ', columnIndex: ' + rowIndex);
        if (curText == '') {
            $(this).text('不排课');
        } else {
            $(this).text('');
        }
    });

});