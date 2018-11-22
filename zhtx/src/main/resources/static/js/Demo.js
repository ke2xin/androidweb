//index
$(document).ready(function(){

		this.init = function(){
			this.onLoginClickListener(this,this.onLogin);
		}
		
		this.onLoginClickListener = function(obj,cal){
			
			var func = function(event){
				cal.call(obj,cal);
			}
			$(".loginButton").on("click",func);
		}
		
		this.onLogin = function(event){
			var inputs = $("input.loginRowRight");
			if(inputs.length === 2){
				var userName = inputs.eq(0).val();
				var userPassword = inputs.eq(1).val();
				
				sessionStorage.setItem('userName',userName);
				sessionStorage.setItem('userPassword',userPassword);
				window.location = "/index";
			}
		}

		$(".loginLeftText").click(function(){
			window.location = "/register";
		});
		$(".loginRightText").click(function(){
			window.location = "/updataPassword";
		});
	window.onload = this.init();

});

//register
$(document).ready(function(){
    var inputs = $(".RegisterRowRight");
    $(".RegisterButton").click(function(event){
		if(inputs.length === 5){

			var uuid = inputs.eq(0).val();
			if(!uuid.match(/^[a-zA-Z0-9_]{0,}$/)){
				alert("用户名不能含有中文");
				return;
			}

			var password = inputs.eq(1).val();
			var repassword = inputs.eq(2).val();

			//匹配中文字符
			if(!password.match(/^[a-zA-Z0-9_]{0,}$/)){
				alert("密码异常，请重新输入");
				return;
			}

			if( password == "" || repassword == "" || password !== repassword){
				alert("密码不一致,请检查");
				return;
			}

			var email = inputs.eq(3).val();
			if(email == "" || !email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)){
				alert("邮箱格式不正确");
				return;
			}

			var phone = inputs.eq(4).val();
			if(!phone.match(/^1[34578]\d{9}$/)){
				alert("手机号码格式不正确！");
				return;
			}
		}
        var data = $(".register").serializeJson();
        $.ajax({
            type:"post",
            url:"/register/post",
            async:true,
            data:data,
            contentType:"application/json",
            success:function(){
                alert("注册成功");
            },
            error:function(){
                alert("异常错误");
            }
        });
    });

    $.fn.serializeJson = function()
    {
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return JSON.stringify(o);
    };

    $(".RegisterLeftText").click(function(){
        window.location = "/login";
    });
    $(".RegisterRightText").click(function(){
        window.location = "/updataPassword";
    });
});