function globalVeriable(){
	var userManager=document.getElementById('userManager');
	var groupManager=document.getElementById('groupManager');
	var userManagerList=document.getElementById('userManagerList');
	var groupManagerList=document.getElementById('groupManagerList');
}
function addListener(){
	console.log(groupManagerList);
	var userFlag=false;
	var managerFlag=false;
	userManager.onclick=function(){
		if(!userFlag){
			userManagerList.style.display='block';
			userFlag=true;
		}else{
			userManagerList.style.display='none';
			userFlag=false;
		}
	}
	groupManager.onclick=function(){
		if(!managerFlag){
			groupManagerList.style.display='block';
			managerFlag=true;
		}else{
			groupManagerList.style.display='none';
			managerFlag=false;
		}
	}
}
function addLoadEvent(func){
	var oldonload=window.onload; 
	if(typeof window.onload!='function'){
		window.onload=func; 
	}else{
		window.onload=function(){
			oldonload();
			func();
		}
	}
}
addLoadEvent(globalVeriable);
addLoadEvent(addListener);
