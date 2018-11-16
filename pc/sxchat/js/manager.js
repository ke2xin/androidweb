function globalVeriable(){
	var userManager=document.getElementById('userManager');
	var groupManager=document.getElementById('groupManager');
	var userManagerList=document.getElementById('userManagerList');
	var groupManagerList=document.getElementById('groupManagerList');
	var forbidUser=document.getElementById('forbidUser');
	var startUser=document.getElementById('startUser');
	var forbidGroup=document.getElementById('forbidGroup');
	var startGroup=document.getElementById('startGroup');
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
	var userManagerListLis=userManagerList.getElementsByTagName('li');
	var groupManagerListLis=groupManagerList.getElementsByTagName('li');
	for(var i=0;i<userManagerListLis.length;i++){
		userManagerListLis[i].onclick=function(){
			var name=this.getAttribute('name');
			if(name=="forbidUser"){
				forbidUser.style.display='block';
				startUser.style.display='none';
				forbidGroup.style.display='none';
				startGroup.style.display='none';
			}else if(name=="startUser"){
				forbidUser.style.display='none';
				startUser.style.display='block';
				forbidGroup.style.display='none';
				startGroup.style.display='none';
			}
		}
	}
	for(var i=0;i<groupManagerListLis.length;i++){
		groupManagerListLis[i].onclick=function(){
			var name=this.getAttribute('name');
			if(name=="forbidGroup"){
				forbidUser.style.display='none';
				startUser.style.display='none';
				forbidGroup.style.display='block';
				startGroup.style.display='none';
			}else if(name=="startGroup"){
				forbidUser.style.display='none';
				startUser.style.display='none';
				forbidGroup.style.display='none';
				startGroup.style.display='block';
			}
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
