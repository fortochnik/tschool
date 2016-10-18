/**
 * Created by mipan on 16.10.2016.
 */

jQuery(document).ready(function () {

    /*
    update basket on the ui
    */
    $('.remove-product').on('click', function (e) {
        $(this).parentsUntil("div").hide('slow', function(){$(this).remove();})
    });

    /*$('.refresh-product').on('click', function (e) {

        price = $(this).parent().children(".product-price").text().replace(/[A-Za-z$-]/g, "");

        number = $(this).parent().children(".selectnumber" ).val();
        $(this).parent().children(".totul-product-price").replaceWith("<span class='totul-product-price'>" +price*number +"</span>");

    });*/



    /*
     form validation
     */
    $('.userinfo-form input[type="text"], .password-form input[type="password"]').on('focus', function () {
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