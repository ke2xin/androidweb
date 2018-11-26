$(function () {
   addLoadData(1);
   function oSearchSuggest(searchFunc) {
       console.log("正在查询oSearchSuggest");
       var input=$("#searchText");
       var suggestWrap=$("#gov_search_suggest");
       var key="";
       var init=function () {
           input.bind('keyup',sendKeyWord);
           input.bind('blur',function () {
               setTimeout(hideSuggest,100);
           })
       }
       var hideSuggest=function () {
            suggestWrap.hide();
       }
       //发送请求，根据关键字到后台
       var sendKeyWord=function (event) {
            var valText=$.trim(input.val());
            console.log("valText="+valText);
           console.log("key="+key);
            if(valText==''||valText==key){//这里有个好处吧，就是重复输入同样的关键字，不会重复发送到后台
                return;
            }
            searchFunc(valText);
            key=valText;
       }
       //请求返回后，执行数据展示
       console.log(this);
       this.dataDisplay=function(data){
            if(data.length<=0){
                suggestWrap.hide();
            }
            //网搜索框下拉建议显示栏添加条目并显示
           var li;
           var tmpFrag=document.createDocumentFragment();
           suggestWrap.find('ul').html('');
           for(var i=0;i<data.length;i++){
               li=document.createElement('li');
               li.innerHTML=data[i];
               tmpFrag.appendChild(li);
           }
           suggestWrap.find('ul').append(tmpFrag);
           suggestWrap.show();
           //为下拉选项绑定鼠标事件
           suggestWrap.find('li').hover(function () {
               suggestWrap.find('li').removeClass('hover');
               $(this).addClass('hover');
           },function () {
               $(this).removeClass('hover');
           }).bind('click',function () {
               console.log("给li添加点击事件");
               console.log(this);
               $(this).find('span').remove();
               input.val(this.innerHTML)
           });
       }
       init();
   }
   //实例化输入提示的js，参数为进行查询操作时要调用的函数名
   var searchSuggest=new oSearchSuggest(sendKeyWordToBack);
   //这是一个模拟函数，实现向后台发送ajax查询请求，并返回一个查询结果数据，传递给前台的js，再由前台js来展示数据，
    //参数为一个字符串，是搜素输入框的内容
   function sendKeyWordToBack(keyword) {
       console.log(keyword);
       var aData=[];
       $.ajax({
           url:"http://localhost:8080/searchByWord?key="+keyword,
           type:"GET",
           dataType:"json",
           contentType:"application/json",
           success:function (data) {
               console.log(data);
               for(var i=0;i<data.users.data.length;i++){
                   aData.push('<span class="num_right">'+data.users.data[i].userName+'</span>'+data.users.data[i].userId);
               }
               //将返回的数据传递个实现搜索输入框的输入提示类
               searchSuggest.dataDisplay(aData);
           },
           error:function (err) {
               console.log("异步查询出错了");
               console.log(err);
           }
       })

   }
})
//定义全局变量，保存总页数，当前页
var talPage;
var pageIndex;
function createPage(obj,commonPage,name) {
    console.log("生成页码");
    //获取分页数
    talPage=obj.totalPage;
    console.log("分页数："+obj.totalPage);
    //获取当前页数
    pageIndex=obj.currentPage;
    var $li_0=$("<li>"+"总共："+obj.totalCount+"条，共："+obj.totalPage+"页，每页：5条"+"</li>");
    //console.log($li_0);
    var getPage=commonPage;
    // getPage.append($li_0);
    if(talPage==1||pageIndex==1){
        var li_1=$("<li></li>").text("第一页")
        li_1.attr({
            "class":"pageItemDisable bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        var li_2=$("<li></li>").text("上一页");
        li_2.attr({
            "class":"pageItemDisable bt4",
            "onclick":"pageClick(this)",
            "name":name
        })
        getPage.append(li_1);
        getPage.append(li_2);
    }else{
        var li_1=$("<li></li>").text("第一页")
        li_1.attr({
            "class":"pageItem bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        var li_2=$("<li></li>").text("上一页");
        li_2.attr({
            "class":"pageItem bt4",
            "onclick":"pageClick(this)",
            "name":name
        })
        getPage.append(li_1);
        getPage.append(li_2);
    }
    if(talPage<=5){
        for(var i=0;i<talPage;i++){
            if(i+1==pageIndex){//循环数和当前页相等时，为当前页样式
                var li=$("<li></li>").text(i+1);
                li.attr({
                    "class":"pageItemActive",
                    "onclick":"pageClick(this)",
                    "name":name
                });
                getPage.append(li);
            }else{
                var li=$("<li></li>").text(i+1);
                li.attr({
                    "class":"pageItem",
                    "onclick":"pageClick(this)",
                    "name":name
                });
                getPage.append(li);
            }
        }
    }else if(talPage>5){
        if(pageIndex<=3){
            for(var i=0;i<5;i++){
                if(i+1==pageIndex){
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItemActive",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li);
                }else{
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItem",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li)
                }
            }
        }else if(pageIndex>talPage-5){
            for(var i=talPage-5;i<talPage;i++){
                if(i+1==pageIndex){//循环数和当前页相等时，为当前页样式
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItemActive",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li);
                }else{
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItem",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li)
                }
            }
        }else{//当前页为中间页时
            for(var i=pageIndex-3;i<pageIndex+2;i++){
                if(i+1==pageIndex){
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItemActive",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li);
                }else{
                    var li=$("<li></li>").text(i+1);
                    li.attr({
                        "class":"pageItem",
                        "onclick":"pageClick(this)",
                        "name":name
                    });
                    getPage.append(li);
                }
            }
        }
    }
    if(pageIndex==talPage){//当前页为最大页时，下一个和页尾不可操作
        var li_3=$("<li></li>").text("下一页");
        li_3.attr({
            "class":"pageItemDisable bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        getPage.append(li_3);
        var li_4=$("<li></li>").text("尾页");
        li_4.attr({
            "class":"pageItemDisable bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        getPage.append(li_4);
    }else{
        var li_3=$("<li></li>").text("下一页");
        li_3.attr({
            "class":"pageItem bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        getPage.append(li_3);
        var li_4=$("<li></li>").text("尾页");
        li_4.attr({
            "class":"pageItem bt4",
            "onclick":"pageClick(this)",
            "name":name
        });
        getPage.append(li_4);
    }
    if(0==talPage){
        $(".bt4").removeClass("pageItem");
        $(".bt4").addClass("pageItemDisable");
    }
    for(var i=0;i<obj.objectList.length;i++){
        console.log(obj.objectList[i].userName);
    }
}
//分页的按钮的点击事件
function pageClick(obj) {
    console.log(obj);
    // var url="http://localhost:8080/getData";
    var text=obj.innerText;//点击标签的值
    var name=obj.getAttribute("name");
    //如果为不可操作的直接返回false
    console.log($(obj).attr("class"));
    console.log($(obj).attr("class").indexOf("pageItemDisable"));
    if($(obj).attr("class").indexOf("pageItemDisable")>0){
        return false;
    }
    if("第一页"==text){
        if(name=="addLoadData"){
            addLoadData(1);
        }else if(name=="loadStartUser"){
            loadStartUser(1);
        }else if(name=="loadForbidGroup"){
            loadForbidGroup(1);
        }else if(name=="loadStartGroup"){
            loadStartGroup(1);
        }
    }else if("上一页"==text){
        if(pageIndex<=1){
            if(name=="addLoadData"){
                addLoadData(1);
            }else if(name=="loadStartUser"){
                loadStartUser(1);
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(1);
            }else if(name=="loadStartGroup"){
                loadStartGroup(1);
            }
        }else{
            var p=pageIndex-1;
            if(name=="addLoadData"){
                addLoadData(p);
            }else if(name=="loadStartUser"){
                loadStartUser(p);
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(p);
            }else if(name=="loadStartGroup"){
                loadStartGroup(p);
            }
        }
    }else if("下一页"==text){
        if(pageIndex==talPage){
            if(name=="addLoadData"){
                addLoadData(pageIndex);
            }else if(name=="loadStartUser"){
                loadStartUser(pageIndex);
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(pageIndex);
            }else if(name=="loadStartGroup"){
                loadStartGroup(pageIndex);
            }
        }else{
            var p=pageIndex+1;
            if(name=="addLoadData"){
                addLoadData(p);
            }else if(name=="loadStartUser"){
                loadStartUser(p)
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(p);
            }else if(name=="loadStartGroup"){
                loadStartGroup(p);
            }
        }
    }else if("尾页"==text){
        if(pageIndex==talPage){
            if(name=="addLoadData"){
                addLoadData(pageIndex);
            }else if(name=="loadStartUser"){
                loadStartUser(pageIndex)
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(pageIndex);
            }else if(name=="loadStartGroup"){
                loadStartGroup(pageIndex);
            }
        }else {
            var p=talPage;
            if(name=="addLoadData"){
                addLoadData(p);
            }else if(name=="loadStartUser"){
                loadStartUser(p)
            }else if(name=="loadForbidGroup"){
                loadForbidGroup(p);
            }else if(name=="loadStartGroup"){
                loadStartGroup(p);
            }
        }
    }else{
        //点击页数时
        if(name=="addLoadData"){
            addLoadData(text);
        }else if(name=="loadStartUser"){
            loadStartUser(text)
        }else if(name=="loadForbidGroup"){
            loadForbidGroup(text);
        }else if(name=="loadStartGroup"){
            loadStartGroup(text);
        }
    }
}

//响应分页并添加数据
function addLoadData(page) {
    $.ajax({
        url: "http://localhost:8080/getData?pageIndex="+page+"&status=1",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("用户所有的数据：");
            console.log(data.users.data.length);
            var s = JSON.stringify(data);
            console.log(data.page);
            $("#forbidUserUl").empty();//清除forbidUserUl里面的子元素
            for (var i = 0; i < data.users.data.length; i++) {
                console.log(data.users.data[i].userName);
                var elm = "<li> " +
                    "<img src='" + data.users.data[i].url + "'/>" +
                    "<span class='userName'>" + data.users.data[i].userName + "</span>" +
                    "<span class='userId'>" + data.users.data[i].userId + "</span>" +
                    "<input type='button' value='禁用该用户' class='forbidButton' name='" + data.users.data[i].userId + "'/> " +
                    "</li>";
                $("#forbidUserUl").append(elm);
            }
            $("input.forbidButton").on('click', function () {
                console.log(this.getAttribute("name"));
                if(window.confirm("确定"+this.value)){
                    forbidAndStart(0,this,this.value,"user");//第一个参数表示禁用用户的操作码，第二个是点击的对象，第三个是点击对象的值，操作类型，字符串user表示用户管理
                }
            });
            $("#getPage").empty();
            createPage(data.page,$("#getPage"),"addLoadData");
        },
        error: function (err) {
            console.log("获取用户所有的数据的错误是的函数：");
            console.log(err);
        }
    })
}
function loadStartUser(page) {
    console.log("开启用户");
    $.ajax({
        url: "http://localhost:8080/getData?pageIndex="+page+"&status=0",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("用户所有的数据：");
            console.log(data.users.data.length);
            var s = JSON.stringify(data);
            console.log(data.page);
            $("#startUserUl").empty();//清除forbidUserUl里面的子元素
            for (var i = 0; i < data.users.data.length; i++) {
                console.log(data.users.data[i].userName);
                var elm = "<li> " +
                    "<img src='" + data.users.data[i].url + "'/>" +
                    "<span class='userName'>" + data.users.data[i].userName + "</span>" +
                    "<span class='userId'>" + data.users.data[i].userId + "</span>" +
                    "<input type='button' value='开启该用户' class='startButton' name='" + data.users.data[i].userId + "'/> " +
                    "</li>";
                $("#startUserUl").append(elm);
            }
            $("input.startButton").on('click', function () {
                console.log(this.getAttribute("name"));
                if(window.confirm("确定"+this.value)){
                    forbidAndStart(1,this,this.value,"user");
                }
            });
            $("#getStartUser").empty();
            createPage(data.page,$("#getStartUser"),"loadStartUser");
        },
        error: function (err) {
            console.log("获取用户所有的数据的错误是的函数：");
            console.log(err);
        }
    })
}
function loadForbidGroup(page) {
    console.log("禁用群");
    $.ajax({
        url: "http://localhost:8080/getGroupData?pageIndex="+page+"&status=1",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("所有群的数据：");
            console.log(data.groups.data.length);
            var s = JSON.stringify(data);
            console.log(data.page);
            console.log(data.groups);
            $("#forbidGroupUl").empty();//清除forbidUserUl里面的子元素
            if(data.groups.data.length==0){
                var elm=$("<li>" +
                    "<img src='http://localhost:8080/img/nothing.jpg'/>" +
                    "</li>");
                elm.find('img').css({width:"630px",height:"450px"});
                elm.css("border","none");
                $("#forbidGroupUl").css("overflow","hidden");
                $("#forbidGroupUl").append(elm);
            }else{
                for (var i = 0; i < data.groups.data.length; i++) {
                    console.log(data.groups.data[i].groupName);
                    var elm = "<li> " +
                        "<img src='" + data.groups.data[i].url + "'/>" +
                        "<span class='userName'>" + data.groups.data[i].groupName + "</span>" +
                        "<span class='userId'>" + data.groups.data[i].groupId + "</span>" +
                        "<input type='button' value='禁用该群' class='forbidButton' name='" + data.groups.data[i].groupId + "'/> " +
                        "</li>";
                    $("#forbidGroupUl").append(elm);
                }
                $("input.forbidButton").on('click', function () {
                    console.log(this.getAttribute("name"));
                    if(window.confirm("确定"+this.value)){
                        forbidAndStart(0,this,this.value,"group");
                    }
                });
                $("#getForbidGroup").empty();
                createPage(data.page,$("#getForbidGroup"),"loadForbidGroup");
            }
        },
        error: function (err) {
            console.log("获取用户所有的数据的错误是的函数：");
            console.log(err);
        }
    })
}
function loadStartGroup(page) {
    console.log("开启群");
    $.ajax({
        url: "http://localhost:8080/getGroupData?pageIndex="+page+"&status=0",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            console.log("获取所有已经禁用群的数据：");
            console.log(data.groups.data.length);
            var s = JSON.stringify(data);
            console.log(data.page);
            console.log(data.groups);
            $("#startGroupUl").empty();//清除forbidUserUl里面的子元素
            if(data.groups.data.length==0){
                var elm=$("<li style=''>" +
                    "<img src='http://localhost:8080/img/nothing.jpg'/>"+
                    "</li>");
                elm.find('img').css({width:"630px",height:"450px"});
                elm.css("border","none");
                $("#startGroupUl").css("overflow","hidden");
                $("#startGroupUl").append(elm);
            }else{
                for (var i = 0; i < data.groups.data.length; i++) {
                    console.log(data.groups.data[i].groupName);
                    var elm = "<li> " +
                        "<img src='" + data.groups.data[i].url + "'/>" +
                        "<span class='userName'>" + data.groups.data[i].groupName + "</span>" +
                        "<span class='userId'>" + data.groups.data[i].groupId + "</span>" +
                        "<input type='button' value='开启该群' class='startButton' name='" + data.groups.data[i].groupId + "'/> " +
                        "</li>";
                    $("#startGroupUl").append(elm);
                }
                $("input.startButton").on('click', function () {
                    console.log(this.getAttribute("name"));
                    if(window.confirm("确定"+this.value)){
                        forbidAndStart(1,this,this.value,"group");
                    }
                });
                $("#getStartGroup").empty();
                createPage(data.page,$("#getStartGroup"),"loadStartGroup");
            }
        },
        error: function (err) {
            console.log("获取用户所有的数据的错误是的函数：");
            console.log(err);
        }
    })
}
function forbidAndStart(result,obj,str,type) {//禁用与开启函数，第一个参数是开启码，或者禁用码，第二个参数是当前对象，第三个参数是当前对象的值，用来提示，第四个参数是操作类型，是用用户管理还是群管理
    var o=new Object();
    o.result=result;
    o.type=type;
    o.uuid=obj.getAttribute("name");
    $.ajax({
        url:"http://localhost:8080/update",
        type:"POST",
        dataType:"json",
        contentType: "application/json",
        data:JSON.stringify(o),
        success:function (data) {
            console.log(data);
            console.log(data.status);
            if(data.code==1){
                console.log("成功"+str);
                $(obj).parent().remove();
            }else{
                console.log("失败"+str);
                alert(str+"失败");
            }
        },
        error:function (err) {
            console.log(str+"出错");
            console.log(err);
            alert(str+"失败");
        }
    })
}