/**
 * Created by mipan on 15.10.2016.
 */

jQuery(document).ready(function () {

    /*
     Login form validation
     */
    $('.userinfo-form input[type="text"], .userinfo-form textarea').on('focus', function () {
        $(this).removeClass('input-error');
    });

    //$('.login-form').on('submit', function(e) {
    $('button.update').on('click', function (e) {

        var bool = true;

        $('#not-identical').remove();

        //$(this).find('input[type="text"], input[type="password"], textarea').each(function(){
        $('.userinfo-form').find('input[type="text"], textarea').each(function () {
            $attr = $(this).attr('class');
            if (!$attr.includes('date-empty')) {
                if ($(this).val() == "") {
                    //e.preventDefault();
                    $(this).addClass('input-error');
                    bool = false;
                }
                else {
                    $(this).removeClass('input-error');

                }
                ;
            }
        });

        $('.password-block').find('#old-pass').each(function () {


            if (/*$('li > a[href$="ABC"]') && */ $('#old-pass').val() == "" && ($('#new-pass').val() != "" || $('#new-pass2').val() != "" )) {
                $('#old-pass').addClass('input-error');
                bool = false;
            }
            /* if( $(this).val() == "" ) {
             //e.preventDefault();
             $(this).addClass('input-error');
             bool = false;
             }
             else {
             $(this).removeClass('input-error');
             }*/
        });

        if ($('#new-pass').val() != $('#new-pass2').val()) {
            $('.div-new-password').after('<p id="not-identical" style="color: red">The new passwords are not identical</p>');
            bool = false;
        }

        if (bool) {
            document.getElementById("tab").submit();
            //document.getElementById("tab2").submit();
        }
    });


});
