
(function ($) {
    "use strict";

    var text = $(".txt2[name='login']").css("display","none")
     /*==================================================================
    [ Focus input ]*/
    $('.input100').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() != "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })
  
  
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');
    var check = false;
    $('.validate-form').on('submit',function(){
        
        console.log("submit")
        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }
        
        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });
    $("#forgetPass").click(function(){
        alert("忘记密码");
       
    })

    $("#register").click(function(){
    
        $('.login100-form-title').text("注册");
        $('.login100-form-btn').text("注册");
        $('.txt2').hide();
        $(".txt2[name='login']").css("display","")

    })
    $(".txt2[name='login']").click(function(){
        $('.login100-form-title').text("登录");
        $('.login100-form-btn').text("登录");
        $('.txt2').show();
        $(".txt2[name='login']").css("display","none")
    })

    $('.login100-form-btn').click(function(){

        
        console.log($('.login100-form-btn').text().trim())
        if($('.login100-form-btn').text().trim()=="登 录"){
            console.log("登录\n用户名：" + $(".input100[name='username']").val()+"\n密码："+$(".input100[name='pass']").val());
        } else{
            console.log("注册\n用户名：" + $(".input100[name='username']").val()+"\n密码："+$(".input100[name='pass']").val());
        }
    })
    

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else {
            if($(input).val().trim() == ''){
                return false;
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    
     
})(jQuery);