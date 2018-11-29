//定义全局变量
var userManager;
var groupManager;
var userManagerList;
var groupManagerList;
var forbidUser;
var startUser;
var forbidGroup;
var startGroup;
var managerNick;
var managerExit;
var box;
var startFlag=false;
var forbidGroupFlag=false;
var startGroupFlag=false;
function globalVariable() {
    userManager = document.getElementById('userManager');
    groupManager = document.getElementById('groupManager');
    userManagerList = document.getElementById('userManagerList');
    groupManagerList = document.getElementById('groupManagerList');
    forbidUser = document.getElementById('forbidUser');
    startUser = document.getElementById('startUser');
    forbidGroup = document.getElementById('forbidGroup');
    startGroup = document.getElementById('startGroup');
    managerNick=document.getElementById("managerNick");
    managerExit=document.getElementById("managerExit");
    box=document.getElementsByClassName("box")[0];
}

function addListener() {
    console.log(groupManagerList + "哈哈哈");
    var userFlag = false;
    var managerFlag = false;
    var userManagerListLis = userManagerList.getElementsByTagName('li');
    var groupManagerListLis = groupManagerList.getElementsByTagName('li');
    var fb = null;
    userManager.onclick = function () {
        if (!userFlag) {
            userManagerList.style.display = 'block';
            if(fb==null){
                fb = userManagerListLis[0];
                fb.style.background = 'rgba(0,0,0,0.1)';
            }
            userFlag = true;
        } else {
            userManagerList.style.display = 'none';
            userFlag = false;
        }
    }
    groupManager.onclick = function () {
        if (!managerFlag) {
            groupManagerList.style.display = 'block';
            // if (fb != null) {
            //     var name=fb.getAttribute("name");
            //     if(name!="forbidUser"){
            //         fb.style.background = 'none';
            //     }
            // }
            managerFlag = true;
        } else {
            groupManagerList.style.display = 'none';
            managerFlag = false;
        }
    }
    for (var i = 0; i < userManagerListLis.length; i++) {
        userManagerListLis[i].onclick = function () {
            if (fb != null) {
                fb.style.background = 'rgba(0,0,0,0)';
            }
            fb = this;
            this.style.background = 'rgba(0,0,0,0.1)';
            var name = this.getAttribute('name');
            if (name == "forbidUser") {
                forbidUser.style.display = 'block';
                startUser.style.display = 'none';
                forbidGroup.style.display = 'none';
                startGroup.style.display = 'none';
            } else if (name == "startUser") {
                forbidUser.style.display = 'none';
                startUser.style.display = 'block';
                forbidGroup.style.display = 'none';
                startGroup.style.display = 'none';
                if(!startFlag){/*每次显示面板时都从第一页开始加载*/
                    loadStartUser(1);
                    //startFlag=true;
                }
            }
        }
    }
    for (var i = 0; i < groupManagerListLis.length; i++) {
        groupManagerListLis[i].onclick = function () {
            if (fb != null) {
                fb.style.background = 'rgba(0,0,0,0)';
            }
            fb = this;
            this.style.background = 'rgba(0,0,0,0.1)';
            var name = this.getAttribute('name');
            if (name == "forbidGroup") {
                forbidUser.style.display = 'none';
                startUser.style.display = 'none';
                forbidGroup.style.display = 'block';
                startGroup.style.display = 'none';
                if(!forbidGroupFlag){/*每次显示面板时都从第一页开始加载*/
                    loadForbidGroup(1);
                    //forbidGroupFlag=true;
                }
            } else if (name == "startGroup") {
                forbidUser.style.display = 'none';
                startUser.style.display = 'none';
                forbidGroup.style.display = 'none';
                startGroup.style.display = 'block';
                if(!startGroupFlag){/*每次显示面板时都从第一页开始加载*/
                    loadStartGroup(1);
                    //startGroupFlag=true;
                }
            }
        }
    }
    managerExit.onclick=function () {
        console.log(this);
        if(window.confirm("确认退出登录吗？")){
            $(".box").slideToggle(2000,function () {
                $(".box").slideUp("slow",function () {
                    sessionStorage.setItem("theUser", null);
                    location.reload();
                });
            });
        }else{
            console.log("取消退出登录");
        }
    }
}

function verifyLogin() {
    console.log("这是检查登录的：");
    var theUser = sessionStorage.getItem("theUser");
    console.log("这是检查登录的：" + theUser);
    if (theUser == null||theUser=="null") {
        window.location.href = "http://localhost:8080/managerLogin";
    }
    managerNick.innerText=theUser;
}

//执行异步请求函数
function asyncHandle() {
    console.log("行异步请求函数");
    var serch = new searchHandel(sendKeyWordToBack);
}

function searchHandel(handle) {
    console.log("这是异步函数的具体实现");
    var searchText = $("#searchText");
    console.log(searchText);
    var init = function () {//定义一个初始化函数
        console.log("这是初始化函数");
        searchText.bind("keyup", sendKeyWord);
    }
    var sendKeyWord = function (event) {
        var valText = $.trim(searchText.val());
        console.log("这是搜索的内容：" + valText);
    }
}

function sendKeyWordToBack(keyword) {
    console.log("这是发送信息到后台");
}

function addLoadEvent(func) {
    var oldonload = window.onload;
    if (typeof window.onload != 'function') {
        window.onload = func;
    } else {
        window.onload = function () {
            oldonload();
            func();
        }
    }
}

addLoadEvent(globalVariable);
addLoadEvent(addListener);
addLoadEvent(verifyLogin);
//addLoadEvent(asyncHandle);