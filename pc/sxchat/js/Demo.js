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
				window.location = "index.html";
			}
		}		
	window.onload = this.init();
});