(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var OperateIdType = function () {



        //创建群
        this.User_CreateGroup_C = 3;

        //进入群
        this.User_Enter_Group_C = 3;

        //获取用户电话
        this.User_Phone_Relative_C = 8;

        //查找群
        this.User_Search_Group_C = 13;

        //查找群信息
        this.User_Search_Group_Number_Info_C = 14;

        //查看群成员和个人信息
        this.User_Data_Info = 16;

        this.User_Save_Personal_Info = 17;

        //发送群消息
        this.User_Send_GroupMessage = 21;
        //接收到新的群消息
        this.User_Receive_GroupMessage = 22;
        this.User_Send_GroupDec = 25;
        //用户登陆
        this.User_Login_C = 1;
        //获取群资料
        this.User_Get_Group_Data_C = 5;
        //退出群
        this.User_Quit_Group_C = 6;
        //保存修改群资料
        this.User_Save_GroupData_C = 7;
        //获取群定位信息
        this.User_Group_Number_Location_C = 8;
        //获取用户电话
        this.User_Phone_Relative_C = 9;
        //申请加入群聊
        this.User_Application_Enter_Group_C = 10;
        //接收加入群聊
        this.User_Accept_Enter_Group_C = 11;
        //拒绝加入群聊
        this.User_Refuse_Enter_Group_C = 12;
        //管理群成员
        this.Manager_GroupUser = 13;
        //查看群成员个人信息
        this.User_Data_Info = 15;
        //查看自己的信息
        this.User_For_Me_C = 16;
        //查看我的资料
        this.Search_Own_Data = 17;
        //保存我的资料
        this.Save_Own_Data = 18;
        //用户注销
        this.User_Offline = 19;
        //解散群
        this.User_Delete_Group_Number_C = 20;


        this.Receive_Notifications = 23;

        this.Refuse_Notification = 97;
        this.Receive_Notification = 96;
        //打开聊天群
        this.Open_ChatObj = 100;
        //剔除用户
        this.DeleteGroupMember = 96;

        //发送接收消息的时间截
        this.User_Send_TimeStamp = 99;


        //发送加入群
        this.User_Application_Enter_Group_C = 10;
    }
    window.ddzj.OperateIdType = new OperateIdType();

})(window);

(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var Tool = function () {
        /*
         * 判断对象是否为空
         */
        this.isNull = function (obj) {
            if (obj === null || obj === undefined) {
                return true;
            }
            return false;
        }


        this.toJson = function (obj) {
            var jsonData = JSON.stringify(obj);

            return jsonData;
        }
    }
    window.ddzj.tool = new Tool();
})(window);

(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var Event = function (operateId, data) {

        /*
         * 操作码
         */
        this.operateId;
        /*
         * 附带数据
         */
        this.data = null;

        this.target = null;


        this.resect = function (operateId, data) {
            this.operateId = operateId;
            this.data = data;
            return this;
        }

        this.resect(operateId, data);
    }

    window.ddzj.Event = Event;


    var EventPoor = function () {

        //事件池
        this.eventPoor = [];


        //出池
        this.getEventOnPoor = function (operateId, data) {


            if (this.eventPoor.length) {
                return this.eventPoor.pop().resect(operateId, data);

            } else {
                return new window.ddzj.Event(operateId, data);
            }
        }

        //入池
        this.toEventOnPoor = function (event) {

            this.eventPoor[this.eventPoor.length] = event;
        }
    }
    window.ddzj.EventPoor = new EventPoor();


    var tool = window.ddzj.tool;
    var EventDispatcher = function () {
        //对应方法
        this.EventDispatchListener = [];
        //对应实体
        this.EventFunction = [];
        var eventPoor = window.ddzj.EventPoor;

        this.addEventDispatchListener = function (operateId, listener, func) {

            var listenerc = this.EventDispatchListener[operateId];
            if (tool.isNull(listenerc)) {
                this.EventDispatchListener[operateId] = [listener];

                var funcc = this.EventFunction[listener];
                if (tool.isNull(funcc)) {
                    this.EventFunction[listener] = func;
                }
            } else {
                var check = -1;

                for (var i = 0; i < listenerc.length; i++) {
                    var l = listenerc[i];
                    if (l === listener) {
                        check = 1;
                        break;
                    }
                }

                if (check < 0) {
                    listenerc.push(listener);
                    var funcc = this.EventFunction[listener];
                    if (tool.isNull(funcc)) {
                        this.EventFunction[listener] = func;
                    }
                }
            }
        }

        this.removeEventDispatchListener = function (operateId, listener) {

            var listenerc = this.EventDispatchListener[operateId];

            if (tool.isNull(listenerc)) {
                return;
            }

            //移除该方法
            for (var i = 0; i < listenerc.length; i++) {

                if (listenerc[i] === listener) {
                    listenerc[i].splice(listener, 1);
                }
            }


            var funcc = this.EventFunction[listener];

            if (tool.isNull(funcc)) {
                return;
            }
            //移除对应方法实体
            var index = this.EventFunction.indexOf(listener);
            if (index > -1) {
                this.EventFunction.splice(index, 1);
            }


        }

        //移除某操作的所有监听
        this.removeEventDispatchListeners = function (operateId) {
            if (operateId && this.EventDispatchListener) {
                delete this.EventDispatchListener[operateId];
            }
        }

        this.removeAllEventDispatchListeners = function () {
            this.mEventListeners = null;
        }

        this.dispatchEvent = function (event) {

            var operateId = event.operateId;

            var listeners = this.EventDispatchListener[operateId];

            if (tool.isNull(listeners)) {
                return;
            }


            event.target = this;

            if (tool.isNull(listeners)) {
                return;
            }

            if (listeners.length) {

                for (var i = 0; i < listeners.length; ++i) {

                    var listener = listeners[i];
                    var numArgs = listener.length;
                    var func = this.EventFunction[listener];
                    if (numArgs === 0) {
                        listener.call(func);
                    } else if (numArgs === 1) {
                        listener.call(func, event);
                    }

                }
            }
        }

        this.dispatchEventWith = function (operateId, data) {
            //封装消息
            var Event = eventPoor.getEventOnPoor(operateId, data);

            //分发消息
            this.dispatchEvent(Event);

            //将消息入库，重复利用
            eventPoor.toEventOnPoor(Event);
        }

    }

    window.ddzj.EventDispatcher = EventDispatcher;
})(window);


(function (window) {
    if (!window.ddzj) window.ddzj = {};

    var webSocketConfig = function () {
        this.OPERATEID = "operateId";

        this.CONNECT = "connected";

        this.CLOSE = "closed";
    }

    window.ddzj.webSocketConfig = new webSocketConfig();


})(window);

(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var webSocketConfig = window.ddzj.webSocketConfig;
    var eventDispatcher = window.ddzj.EventDispatcher;


    var WsClient = function (url) {

        this.webSocket = null;
        this.url = url;
        this.isconnected = false;
        this.useText = true;
        //继承EventDispatcher
        eventDispatcher.apply(this);


        this.connect = function () {
            this.webSocket = new WebSocket(this.url);
            this.onOpenListener(this, this.onOpen);
            this.onMessageListener(this, this.onMessage);
            this.onCloseListener(this, this.onClose);
        }


        this.onOpenListener = function (ws, call) {

            var callFunc = function (event) {
                call.call(ws, event);
            }
            ws.webSocket.onopen = callFunc;
        }


        this.onMessageListener = function (ws, call) {

            var callFunc = function (event) {
                call.call(ws, event);
            }
            ws.webSocket.onmessage = callFunc;
        }


        this.onCloseListener = function (ws, call) {
            var func = function (event) {
                call.call(ws, event);
            }
            ws.webSocket.onclose = func;
        }

        //发送消息
        this.sendMessage = function (data) {
            if (!this.isconnected) {
                return;
            }

            if (this.useText) {
                this.webSocket.send(data);
                return;
            }

            var blobs = new Blob([data]);
            this.webSocket.send(blobs);
        }


        this.onOpen = function (event) {
            this.isconnected = true;
            //分发消息
            this.dispatchEventWith(webSocketConfig.CONNECT);
        }


        this.onMessage = function (event) {
            var data = JSON.parse(event.data);
            var operateId = data.operateId;
            if (operateId === null || operateId === undefined) {
                //没有操作码的直接无视
                return;
            }
            //分发消息
            this.dispatchEventWith(operateId, data);
        }


        this.onClose = function (e) {
            this.isconnected = false;
            //分发消息
            console.log(e);
            this.dispatchEventWith(webSocketConfig.CLOSE);
        }


        this.onError = function (e) {
            console.log("异常关闭");
            console.log(e);
        }


        //主动关闭
        this.toClose = function () {
            this.ws.close();
        }

    };


    window.ddzj.WsClient = WsClient;
})(window);



(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var tool = window.ddzj.tool;
    var operateIdType = window.ddzj.OperateIdType;


    var WsAgent = function () {
        this.wsClinet = null;

        this.init = function (wsClinet) {
            this.wsClinet = wsClinet;
            this.wsClinet.connect();

        }

        //用户登陆
        this.onLoginChat = function (userUuid, userPassword) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Login_C;
            obj.uuid = userUuid;
            obj.password = userPassword;

            var jsondata = JSON.stringify(obj);

            this.wsClinet.sendMessage(jsondata);
        }

        //获取群资料
        this.onGetGroupData = function (groupId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Get_Group_Data_C;
            obj.groupId = groupId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //保存修改群资料
        this.onSaveGroupData = function (groupId, groupName, groupDec) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Save_GroupData_C;
            obj.groupId = groupId;
            obj.groupName = groupName;
            obj.groupDec = groupDec;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //保存修改群资料
        this.onSaveGroupData = function (groupId, groupPortrait, groupName, groupDec) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Save_GroupData_C;
            obj.groupId = groupId;
            obj.groupPortrait = groupPortrait;
            obj.groupName = groupName;
            obj.groupDec = groupDec;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        this.sendMessage = function (obj) {

            var jsondata = tool.toJson(obj);

            this.wsClinet.sendMessage(jsondata);
        }

        //用户推送消息
        this.onUserToSendMessage = function (userId, groupId, content) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Send_GroupMessage;
            obj.userUuid = userId;
            obj.groupUuid = groupId;
            obj.content = content;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //用户发送接收消息时间
        this.sendReceiveMessageTime = function (userId, groupId, timestamp) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Send_TimeStamp;
            obj.userUuid = userId;
            obj.groupUuid = groupId;
            obj.timeStamp = timestamp;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //用户创建群
        this.onSendCreateGroup = function (userId, groupName, groupHobby, groupDec) {
            var obj = new Object();
            obj.operateId = operateIdType.User_CreateGroup_C;
            obj.uuid = userId;
            obj.groupName = groupName;
            obj.groupHobby = groupHobby;
            obj.groupDec = groupDec;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //查看群成员位置信息
        this.onGetGroupUserLocations = function (groupId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Group_Number_Location_C;
            obj.groupId = groupId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //电话联系群成员
        this.onPhoneToGroupUser = function (groupId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Phone_Relative_C;
            obj.group_id = groupId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }


        //用户查找群
        this.onSendSearchGroups = function (searchGroupName) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Search_Group_Number_Info_C;
            obj.groupId = searchGroupName;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //用户发送加入群请求
        this.onUserEnterGroup = function (userId, groupId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Application_Enter_Group_C;
            obj.uuid = userId;
            obj.groupId = groupId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //发送群公告
        this.onSendGroupAnnoun = function (userId, groupId, groupDec) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Send_GroupDec;
            obj.uuid = userId;
            obj.groupId = groupId;
            obj.anoun = groupDec;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //处理通知
        this.onUserHandleNotification = function (sendId, receiveId, groupId, result, noticeId, type) {
            var obj = new Object();
            obj.requestUserUuid = receiveId;
            obj.sendUserUuid = sendId;
            obj.groupUuid = groupId;
            obj.result = result;
            obj.noticeId = noticeId;
            if (type === 0) {
                obj.operateId = operateIdType.User_Accept_Enter_Group_C;
            } else if (type === 1) {
                obj.operateId = operateIdType.User_Refuse_Enter_Group_C;
            }

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //退出群聊
        this.onQuitGroup = function (groupId, userId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Quit_Group_C;
            obj.groupId = groupId;
            obj.userUuid = userId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }


        //管理群成员
        this.onManagerGroupUser = function (groupId, userId, delId) {
            var obj = new Object();
            obj.operateId = operateIdType.Manager_GroupUser;
            obj.groupId = groupId;
            obj.uuid = userId;
            obj.delUuid = delId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //查看群成员个人信息
        this.onSearchGroupUserData = function (userId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Data_Info;
            obj.uuid = userId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //查看个人信息
        this.onSearchOwn = function (userId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_For_Me_C;
            obj.uuid = userId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //我的资料
        this.onSearchOwnData = function (userId) {
            var obj = new Object();
            obj.operateId = operateIdType.Search_Own_Data;
            obj.uuid = userId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //保存我的资料
        this.onSaveOwnData = function (userId, pic, name, qianming, phone, email) {
            var obj = new Object();
            obj.operateId = operateIdType.Save_Own_Data;
            obj.uuid = userId;
            obj.uuidPic = pic;
            obj.userName = name;
            obj.userQianming = qianming;
            obj.userPhone = phone;
            obj.userEmail = email;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //用户注销
        this.onUserOffline = function (userId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Offline;
            obj.uuid = userId;


            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

        //解散群
        this.onDeleteGroup = function (userId, groupId) {
            var obj = new Object();
            obj.operateId = operateIdType.User_Delete_Group_Number_C;
            obj.uuid = userId;
            obj.groupId = groupId;

            var jsonData = tool.toJson(obj);
            this.wsClinet.sendMessage(jsonData);
        }

    }
    window.ddzj.WsAgent = WsAgent;
})(window);


(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var operateIdType = window.ddzj.OperateIdType;
    var evenDispatcher = window.ddzj.EventDispatcher;

    var GroupObj = function () {
        this.group = null;
        this.view = null;
        this.chatList = [];
        evenDispatcher.apply(this);

        this.init = function (group) {
            this.group = group;
            this.createView();
        }

        this.createView = function () {
            var newView = document.createElement("li");
            newView.innerHTML = '<img src="' + this.group.groupPortrait + '" class="groupPortrait" />' +
                '<span class="groupName">' + (this.group.groupName === undefined ? "" : this.group.groupName) + '</span>' +
                '<span class="groupLastMessage">' + ((this.group.lastestGroupUser === undefined || this.group.lastestGroupUser === "" || this.group.lastestGroupUser === "null") ? "" : (this.group.lastestGroupUser + '&nbsp;:&nbsp;')) +
                (this.group.lastestGroupMessage === undefined ? "" : this.group.lastestGroupMessage) + '</span>';
            this.view = $(newView);
            this.addEvenListeners();
        }

        this.getView = function () {
            //添加事件监听
            return this.view;

        }


        this.addEvenListeners = function () {

            this.addOnClickListener(this, this.toClick);
        }

        this.addOnClickListener = function (obj, call) {

            var callFunc = function (event) {
                call.call(obj, event);
            }

            //添加点击事件
            $(this.view).on("click", callFunc);
        }

        this.toClick = function (event) {
            this.dispatchEventWith(operateIdType.Open_ChatObj);
        }

    }
    window.ddzj.GroupObj = GroupObj;

})(window);


(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var operateIdType = window.ddzj.OperateIdType;
    var evenDispatcher = window.ddzj.EventDispatcher;

    var SearchGroup = function () {
        evenDispatcher.apply(this);
        this.group = null;
        this.view = null;


        this.init = function (group) {
            this.group = group;
            this.createView();
        }

        this.createView = function () {
            var newView = document.createElement("li");
            newView.innerHTML = '<img src="img/group5.png" />' + '<span class="searchGroupName">' + this.group.groupName + '</span>' + '<input type="button" value="申请加入群聊" class="searchApplication"/>'

            this.view = $(newView);
        }

        this.getView = function () {
            //添加事件监听
            this.addEvenListeners();
            return this.view;

        }

        this.addEvenListeners = function () {

            this.addOnClickListener(this, this.toClick);
        }

        this.addOnClickListener = function (obj, call) {

            var callFunc = function (event) {
                call.call(obj, event);
            }

            //添加点击事件
            $(this.view).children("input.searchApplication").on("click", callFunc);
        }

        this.toClick = function (event) {
            this.dispatchEventWith(operateIdType.User_Application_Enter_Group_C);
        }
    }
    window.ddzj.SearchGroup = SearchGroup;
})(window);


(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var operateIdType = window.ddzj.OperateIdType;
    var evenDispatcher = window.ddzj.EventDispatcher;

    var Notification = function () {
        this.Notification = null;
        this.view = null;
        evenDispatcher.apply(this);

        this.init = function (notification) {
            this.Notification = notification;
            this.createView();
        }

        this.createView = function () {
            var newView = document.createElement("li");

            if (this.Notification.status == 0) {
                newView.innerHTML = '<img  src="img/p1.jpg"/>' +
                    '<span class="applicationMessage">' + this.Notification.sendUserName + '请求加入' + this.Notification.groupName + '群聊' + '</span>';
                newView.innerHTML += '<input type="button" value="同意" class="agree"/>' + '<input type="button" value="拒绝" class="refuse"/>';
            } else {
                newView.innerHTML = '<img  src="img/p1.jpg"/>' +
                    '<span class="applicationMessage">' + this.Notification.groupName + '</span>';
                newView.innerHTML += '<span class="agree">' + this.Notification.noticeContent + '</span>';
            }
            this.view = $(newView);
        }

        this.getView = function () {
            //添加事件监听
            this.addEvenListeners();
            return this.view;

        }


        this.addEvenListeners = function () {

            this.addOnReceiveClickListener(this, this.toReceiveClick);
            this.addOnRefuseClickListener(this, this.toRefuseClick);
        }

        this.addOnReceiveClickListener = function (obj, call) {

            var callFunc = function (event) {
                call.call(obj, event);
            }

            //添加点击事件
            $(this.view).children("input.agree").on("click", callFunc);
        }

        this.addOnRefuseClickListener = function (obj, call) {

            var callFunc = function (event) {
                call.call(obj, event);
            }

            //添加点击事件
            $(this.view).children("input.refuse").on("click", callFunc);
        }


        this.toReceiveClick = function (event) {
            this.dispatchEventWith(operateIdType.Receive_Notification);
        }

        this.toRefuseClick = function (event) {
            this.dispatchEventWith(operateIdType.Refuse_Notification);
        }
    }
    window.ddzj.Notification = Notification;
})(window);



(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var operateIdType = window.ddzj.OperateIdType;
    var evenDispatcher = window.ddzj.EventDispatcher;

    var GroupMember = function () {
        this.member = null;
        this.view = null;
        evenDispatcher.apply(this);

        this.init = function (member) {
            this.member = member;
            this.createView();
        }

        this.createView = function () {
            var newView = document.createElement("li");
            newView.innerHTML = '<img src="' + this.member.groupUserPortrait + '" />' + '<span>' + this.member.groupUserName + '</span>' + '<input type="button" value="删除" class="deleteNumber"/>';

            this.view = $(newView);
            this.addEvenListeners();
        }

        this.getView = function () {
            //添加事件监听
            return this.view;

        }


        this.addEvenListeners = function () {

            this.addOnDeleteClickListener(this, this.toDeleteClick);
        }

        this.addOnDeleteClickListener = function (obj, call) {

            var callFunc = function (event) {
                call.call(obj, event);
            }

            //添加点击事件
            $(this.view).children("input.deleteNumber").on("click", callFunc);
        }

        this.toDeleteClick = function (event) {
            this.dispatchEventWith(operateIdType.DeleteGroupMember);
        }

    }
    window.ddzj.GroupMember = GroupMember;
})(window);


(function (window) {
    if (!window.ddzj) window.ddzj = {};
    var operateIdTpye = window.ddzj.OperateIdType;
    var wsConfig = window.ddzj.webSocketConfig;
    var tool = window.ddzj.tool;
    var wsAgent = window.ddzj.WsAgent;
    var wsClient = window.ddzj.WsClient;
    var Group = window.ddzj.GroupObj;
    var SearchGroup = window.ddzj.SearchGroup;
    var Notification = window.ddzj.Notification;
    var GroupMember = window.ddzj.GroupMember;
    var TalkMonitor = function () {
        //websocket对象
        this.webSocketClient = null;
        //聊天组
        this.groupMap = [];
        //现在的聊天组
        this.nowChatGroupObj = null;
        //自己的数据
        this.owner = null;
        this.currentFile = null;
        this.webSocketAgent = null;
        //时间截集合
        this.timeStamp = [];
        this.notificationMap = [];
        this.isConnect = false;
        this.searchGroup = [];
        this.groupMember = [];
        this.init = function (url) {
            this.disConnectWebsocket();

            this.webSocketClient = new wsClient(url);
            this.webSocketAgent = new wsAgent();
            this.webSocketAgent.init(this.webSocketClient);

            this.initWebSocketListener();
            this.initViewClickListener();
        }


        this.initWebSocketListener = function () {
            //登陆成功监听
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Login_C, this.onLoginSuccess, this);
            //获取群资料监听
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Get_Group_Data_C, this.onGetGroupData, this);
            //接收群消息
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Receive_GroupMessage, this.onReceiveGroupMessage, this);
            //接收返回的时间截验证
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Send_TimeStamp, this.getReceiveTime, this);
            //接收返回创建群的响应
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_CreateGroup_C, this.onMessageByCreateGroup, this);
            //接收返回搜索群的响应
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Search_Group_Number_Info_C, this.onMessageBySearchGroup, this);
            //用户加入群消息响应
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Application_Enter_Group_C, this.onMessageByEnterGroup, this);
            //用户接收到群通知
            this.webSocketClient.addEventDispatchListener(operateIdTpye.Receive_Notifications, this.onMessageByNotification, this);
            //接受通知响应
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Accept_Enter_Group_C, this.onMessageByReceiveNotification, this);
            //拒绝通知响应
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Refuse_Enter_Group_C, this.onMessageByRefuseNotification, this);
            //响应查看我的资料通知
            this.webSocketClient.addEventDispatchListener(operateIdTpye.Search_Own_Data, this.onMessageByLookOwnData, this);
            //响应保存我的资料
            this.webSocketClient.addEventDispatchListener(operateIdTpye.Save_Own_Data, this.onMessageBySaveOwnData, this);
            //响应群公告
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Send_GroupDec, this.onMessageBySendGroup, this);
            //响应查看群资料
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Get_Group_Data_C, this.onMessageByGetGroupData, this);
            //响应退出群
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Quit_Group_C, this.onMessageByonExitGroup, this);
            //响应保存群资料
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Save_GroupData_C, this.onMessageBySaveGroupData, this);
            //响应删除群成员
            this.webSocketClient.addEventDispatchListener(operateIdTpye.Manager_GroupUser, this.onMessageByDeleteMember, this);
            //响应获取群位置信息
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Group_Number_Location_C, this.onMessageByLocation, this);
            //解散群
            this.webSocketClient.addEventDispatchListener(operateIdTpye.User_Delete_Group_Number_C, this.onMessageByDeleteGroup, this);

            this.webSocketClient.addEventDispatchListener(wsConfig.CONNECT, this.toConnect, this);
            this.webSocketClient.addEventDispatchListener(wsConfig.CLOSE, this.toClose, this);
        }

        this.initViewClickListener = function () {
            this.addSendClickListener(this, this.onSendGroupMessage);
            this.addSendCreateGroupMessageListener(this, this.onSendCreateGroupMessage);
            this.onKeySerachGroupsListener(this, this.onSendSearchGroups);
            this.onLookOwnDataClickListener(this, this.onLookOwnData);
            this.onSaveOwnDataClickListner(this, this.onSaveOwnData);
            this.onOfflineClickListener(this, this.onOffline);
            this.onSendGroupAnnnontionListener(this, this.onSendGroupAnnnontion);
            this.onLookGroupDataClickListener(this, this.onLookGroupData);
            this.onExitGroupClickListener(this, this.onExitGroup);
            this.onSaveGroupDataListener(this, this.onSaveGroupData);
            this.onLocationListener(this, this.onLocation);
            this.checkGroupRoleListener(this, this.checkGroupRole);
            this.onDeleteGroupClickListener(this, this.onDeleteGroup);
            $("body").on("click", "#chatClose", function () {
                $("#chatWindow").attr("style", "display: none;");
            });

            this.onChangeOwnImageClickListener(this, this.onChangeOwnImage);
            this.onChangeGroupImageClickListener(this, this.onChangeGroupImage);
        }

        this.onChangeOwnImageClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", ".changeImage", func);
        }

        this.onChangeGroupImageClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", ".groupReplaceImage", func);
        }

        this.onChangeOwnImage = function () {
            var fileInput = $(document.createElement("input"));
            this.currentFile = fileInput;
            fileInput.attr("type", "file");
            fileInput.css("display", "none");
            fileInput.click();
            fileInput.on("change", function () {
                var currentImage = this.files[0];
                var imgSizeBlock = 1024 * 1024 * 4;

                var imageType = /^image\//;

                if (!imageType.test(currentImage.type)) {
                    alert('请选择图片');
                    return;
                }

                var unUsed = currentImage.size > imgSizeBlock;
                if (unUsed) {
                    alert("图片大于4M无法上传.");
                    return;
                }

                var reader = new FileReader();
                reader.onload = function (e) {
                    $(".changeImage").attr("src", e.target.result);
                }

                reader.readAsDataURL(currentImage);
            });
        }


        this.onChangeGroupImage = function () {
            var fileInput = $(document.createElement("input"));
            this.currentFile = fileInput;
            fileInput.attr("type", "file");
            fileInput.css("display", "none");
            fileInput.click();
            fileInput.on("change", function () {
                var currentImage = this.files[0];
                var imgSizeBlock = 1024 * 1024 * 4;

                var imageType = /^image\//;

                if (!imageType.test(currentImage.type)) {
                    alert('请选择图片');
                    return;
                }

                var unUsed = currentImage.size > imgSizeBlock;
                if (unUsed) {
                    alert("图片大于4M无法上传.");
                    return;
                }

                var reader = new FileReader();
                reader.onload = function (e) {
                    $(".groupReplaceImage").attr("src", e.target.result);
                }

                reader.readAsDataURL(currentImage);
            });
        }


        //连接操作
        this.toConnect = function (event) {
            var user = window.user;
            this.isConnect = true;
            this.webSocketAgent.onLoginChat(user.userName, user.password);
        }

        //服务器挂了啊
        this.toClose = function (event) {
            alert("连接服务器失败了啊");
            this.webSocketClient = null;
            this.isConnect = false;
            window.location = "/login";
        }


        //登陆成功
        this.onLoginSuccess = function (event) {
            var data = event.data.data;
            if (event.data.status == "fail") {
                alert("登陆失败");
                window.location = "/login";
                return;
            }
            //登陆成功后倒计时去除
            setTimeout(function () {
                $(".forwardGround").remove();
            }, 1000);

            //存储自己的数据
            this.owner = data.singal;
            var groups = data.groups;
            $("#IPortrait").attr("src", this.owner.userPortrait);
            $(".iImage").attr("src", this.owner.userPortrait);
            $(".nickName").text(this.owner.userName);
            $(".sign").text(this.owner.userSign);
            //设置头像地址
            //$("#IPortrait").attr("src",this.owner.)
            //隐藏
            this.unView();

            if (groups.length) {
                //设置首先打开的为第一个聊天组


                for (var i = 0; i < groups.length; ++i) {
                    var g = groups[i];
                    var group = new Group();
                    group.init(g);
                    group.addEventDispatchListener(operateIdTpye.Open_ChatObj, this.openChatGroup, this);

                    this.groupMap[g.groupNumber] = group;

                    if (i === 0) {
                        this.nowChatGroupObj = group;
                        this.nowChatGroupObj.getView().children("span.groupLastMessage").text("");
                        this.nowChatGroupObj.getView().addClass("toPick");
                    }

                    $("#groupChat").append(group.getView());
                }
                //发送获取群资料请求
                this.webSocketAgent.onGetGroupData(this.nowChatGroupObj.group.groupNumber);
            }

        }

        //打开聊天群
        this.openChatGroup = function (event) {
            var group = event.target.group;
            //如果打开的是当前的聊天群，直接无视

            if (this.nowChatGroupObj === null || this.nowChatGroupObj === undefined) {
                this.onChangeChatGroup(event);
            }

            if (this.nowChatGroupObj.group.groupNumber === group.groupNumber) {
                this.openView("chat");
                return;
            }
            //切换聊天群
            this.onChangeChatGroup(event);

        }

        this.onChangeChatGroup = function (event) {
            this.nowChatGroupObj = event.target;
            $(".toPick").removeClass("toPick");
            this.nowChatGroupObj.getView().addClass("toPick");
            this.nowChatGroupObj.getView().children("span.groupLastMessage").text("");
            //发送获取群资料请求
            this.webSocketAgent.onGetGroupData(event.target.group.groupNumber);
        }


        //返回打开聊天群的数据
        this.onGetGroupData = function (event) {
            var data = event.data;
            //如果返回的数据不是当前打开的聊天直接无视
            if (this.nowChatGroupObj.group.groupNumber !== data.groupNumber) {
                return;
            }
            $(".chatGroupName").text(data.groupName);
            $(".chatGroupNotification").text(data.groupAnoun);
            $("#groupMessage").empty();
            this.onReviewMessage();
            this.openView("chat");
        }

        //恢复群聊天记录
        this.onReviewMessage = function () {
            var chatList = this.nowChatGroupObj.chatList;

            for (var i = 0; i < chatList.length; i++) {
                var chat = chatList[i];
                var li;
                //返回的消息发送的对象和发送的对象的ID相同则right
                if (chat.messageUserId == window.user.userName) {
                    li = this.getOtherMessageView("right", chat.messageUserName, chat.messageContent, chat.userPortrait);
                } else {
                    li = this.getOtherMessageView("left", chat.messageUserName, chat.messageContent, chat.userPortrait);
                }
                $("#groupMessage").append(li);
                //滚动到底部
                $("#groupMessage").scrollTop(10000000000000000);
            }
        }

        //发送已经接收消息的时间
        this.sendHaveReceiveMessageTime = function () {
            var dates = new Date();
            var times = dates.getTime();

        }

        //添加发送消息监听
        this.addSendClickListener = function (obj, call) {

            var func = function (event) {
                call.call(obj, event);
            }
            $("body").on("click", "#chatBoxTopSend", func);
        }

        //用户主动发消息
        this.onSendGroupMessage = function (event) {

            var content = $("#chatBoxContent").val();
            //空群发消息，无视
            if (!this.nowChatGroupObj) return;
            //没输入任何信息，无视
            if (content === "") return;
            if (this.webSocketClient === null) return;

            var groupId = this.nowChatGroupObj.group.groupNumber;
            var li = this.getOtherMessageView("right", this.owner.userName, content);

            //设置当前发送时间
            //var currentTime = this.onGetCurrentTime();
            //var timeli = this.onGetNotieView(currentTime);
            //$("#groupMessage").append(timeli);

            //$("#groupMessage").append(li);
            this.webSocketAgent.onUserToSendMessage(window.user.userName, groupId, content);
            $("#chatBoxContent").val("");
        }

        //添加创建群点击监听
        this.addSendCreateGroupMessageListener = function (obj, cal) {

            var funCal = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", "#submitCreate", funCal);
        }

        //用户创建群
        this.onSendCreateGroupMessage = function (event) {
            var inputs = $(".formCreateGroup").children().children().children().children().children("input");
            //输入参数不够四个，无视
            if (inputs.length < 4) return;

            var groupName = inputs.eq(0).val();
            var groupHobby = inputs.eq(1).val();
            var groupDec = inputs.eq(2).val();

            if (groupName === "" || groupHobby === "" || groupDec === "") alert("请确认你输入的内容正确！！！");
            if (inputs.eq(3).attr("id") != "submitCreate") return;

            inputs.eq(3).attr("disabled", true);

            this.webSocketAgent.onSendCreateGroup(window.user.userName, groupName, groupHobby, groupDec);
        }

        //发送查找群
        this.onSendSearchGroups = function (event) {
            //$(searchBoxInput).val($(searchBoxInput).val().replace( /[^0-9]/g,''));
            var searchContent = $("#searchBoxInput").val();

            this.webSocketAgent.onSendSearchGroups(searchContent);
        }

        //返回创建群的消息响应
        this.onMessageByCreateGroup = function (event) {
            var data = event.data;
            var groups = data.groups;
            var newGroup = groups[0];
            console.log(newGroup);
            if (newGroup === undefined) return;
            //添加群
            this.addGroupToMonitor(newGroup, "prepend");
            $(".formCreateGroup").children().children().children().children().children("input").eq(3).attr("disabled", false);

        }

        //创建新群
        this.addGroupToMonitor = function (g, type) {
            var group = new Group();
            group.init(g);
            group.addEventDispatchListener(operateIdTpye.Open_ChatObj, this.openChatGroup, this);
            this.groupMap[g.groupNumber] = group;

            if (type === "prepend") {
                $("#groupChat").prepend(group.getView());
            } else if (type === "append") {
                $("#groupChat").append(group.getView());
            }
            group.getView().click();
            return group;
        }


        //添加搜索群按键监听
        this.onKeySerachGroupsListener = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }

            $("body").on("keyup", "#searchBoxInput", func);
//			$("body").on("keyup","#searchBoxInput",function(){
//				    $(this).val($(this).val().replace( /[^0-9]/g,''));
//				});
        }

        //返回查找群的消息响应
        this.onMessageBySearchGroup = function (event) {
            var data = event.data;
            var groups = data.data;
            console.log(data);
            if (groups) {
                $(".searchCountInfo").empty();

                for (var i = 0; i < groups.length; i++) {
                    var group = groups[i];
                    var searchGroup = new SearchGroup(group);
                    if (!this.searchGroup) {
                        this.searchGroup = [];
                    }
                    //添加到搜索群记录
                    if (this.searchGroup[group.groupUuid]) {
                        $(".searchCountInfo").append(this.searchGroup[group.groupUuid].getView());
                    } else {
                        this.searchGroup[group.groupUuid] = searchGroup;
                        searchGroup.init(group);
                        searchGroup.addEventDispatchListener(operateIdTpye.User_Application_Enter_Group_C, this.onEnterGroup, this);
                        $(".searchCountInfo").append(searchGroup.getView());
                    }
                }
                $(".searchCount").children("strong").text(groups.length);
            }

        }

        //用户加入群
        this.onEnterGroup = function (event) {
            var target = event.target;
            var groupUuid = target.group.groupUuid;
            var userUuId = window.user.userName;
            if (!groupUuid || !userUuId) return;
            this.webSocketAgent.onUserEnterGroup(userUuId, groupUuid);
            $(target.getView()).children("input.searchApplication").attr("disabled", true);
            $(target.getView()).children("input.searchApplication").attr("value", "申请中");
        }

        //返回加入群消息响应
        this.onMessageByEnterGroup = function (event) {
            var data = event.data;
            var status = data.status;
            console.log(data);
            if (data === undefined || data == null || data === null) return;
            if (data.status == "fail") {
                if (this.searchGroup[data.groupId]) {

                    this.searchGroup[data.groupId].getView().children("input.searchApplication").attr("value", "已加入");
                }
            }
            alert(data.information);

        }

        //接收到群通知
        this.onMessageByNotification = function (event) {
            var datas = event.data.data;
            if (datas.length > 0) {
                for (var i = 0; i < datas.length; ++i) {
                    var data = datas[i];
                    var notif = new Notification();
                    this.notificationMap[data.noticeId] = notif;
                    notif.init(data);
                    notif.addEventDispatchListener(operateIdTpye.Refuse_Notification, this.onRefuseNotification, this);
                    notif.addEventDispatchListener(operateIdTpye.Receive_Notification, this.onReceiveNotification, this);
                    $("#notificationMessage").prepend(notif.getView());
                }
            }
        }

        //接受通知
        this.onReceiveNotification = function (event) {
            var target = event.target;
            var notif = target.Notification;
            var sendUserUuid = window.user.userName;
            var requsetUserUuid = notif.sendUuid;
            var groupUuid = notif.groupId;
            var noticeId = notif.noticeId;
            this.webSocketAgent.onUserHandleNotification(sendUserUuid, requsetUserUuid, groupUuid, "accept", noticeId, 0);
        }

        //拒绝通知
        this.onRefuseNotification = function (event) {
            var target = event.target;
            var notif = target.Notification;
            var sendUserUuid = window.user.userName;
            var requsetUserUuid = notif.sendUuid;
            var groupUuid = notif.groupId;
            var noticeId = notif.noticeId;
            this.webSocketAgent.onUserHandleNotification(sendUserUuid, requsetUserUuid, groupUuid, "refuse", noticeId, 1);
        }

        //接受通知响应
        this.onMessageByReceiveNotification = function (event) {
            var data = event.data;
            console.log(event.data);
            if (data.status == "accpet" || data.status == "success") {
                var infor = this.notificationMap[data.data.noticeId];
                if (infor) {
                    infor.getView().append('<span class="agree">' + '已同意加入' + '</span>');
                    infor.getView().children("input").remove();
                }
            }

            //表示是用户被接受加入
            if (data.status == "accepted") {
                var groupObj = new Group();
                groupObj.init(data);
                console.log(groupObj);
                var view = groupObj.getView();
                this.groupMap[data.groupNumber] = groupObj;
                groupObj.addEventDispatchListener(operateIdTpye.Open_ChatObj, this.openChatGroup, this);
                $("#groupChat").prepend(view);

            }
        }

        //拒绝通知响应
        this.onMessageByRefuseNotification = function (event) {
            var data = event.data;
            console.log(data);
            if (data.status == "success") {
                var infor = this.notificationMap[data.data.noticeId];
                if (infor) {
                    infor.getView().append('<span class="agree">' + '已拒绝加入' + '</span>');
                    infor.getView().children("input").remove();
                }
            }
        }

        //查看我的资料点击监听
        this.onLookOwnDataClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", "#myData", func);
        }

        //查看我的资料
        this.onLookOwnData = function (event) {
            var userId = window.user.userName;
            if (userId) {
                this.webSocketAgent.onSearchOwnData(userId);
            }

        }

        //响应查看我的资料
        this.onMessageByLookOwnData = function (event) {
            var data = event.data;
            var status = $.trim(data.status);
            //设置头像为空
            this.currentFile = null;

            if (status == "" || status === undefined || status === null || status == "fail") {
                alert("获取我的资料失败");

            } else if (status == "success") {
                var inputs = $("input.dataInfoDatile");
                if (inputs.length === 4) {
                    $(".changeImage").attr("src", data.data.userPortrait);
                    inputs.eq(0).val(data.data.userName);
                    inputs.eq(1).val(data.data.userSign);
                    inputs.eq(2).val(data.data.userPhone);
                    inputs.eq(3).val(data.data.userEmail);
                    this.openView("dataI");

                }
            }

        }

        //保存我的资料点击监听
        this.onSaveOwnDataClickListner = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", "#dataISave", func);
        }

        //保存我的资料
        this.onSaveOwnData = function (event) {
            var inputs = $("input.dataInfoDatile");
            var name = "";
            var sign = "";
            var phone = "";
            var email = "";
            if (inputs.length === 4) {
                name = $.trim(inputs.eq(0).val());
                sign = $.trim(inputs.eq(1).val());
                phone = $.trim(inputs.eq(2).val());
                email = $.trim(inputs.eq(3).val());
            }

            if (email == "") {
                //$("#confirmMsg").html("<font color='red'>邮箱地址不能为空！</font>"); 
                alert("邮箱不能为空!")
                return;
            } else if (!email.match(/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/)) {
                alert("邮箱格式不正确");
                return;
            }

            if (phone == "") {
                alert("手机号码不能为空！");
                return;
            } else if (!phone.match(/^1[34578]\d{9}$/)) {
                alert("手机号码格式不正确！");
                return;
            }

            if (name == "") {
                alert("名称不能为空!");
                return;
            }

            var userId = window.user.userName;

            var agent = this.webSocketAgent;
            if (this.currentFile === undefined || this.currentFile === null) {
                agent.onSaveOwnData(userId, null, name, sign, phone, email);
                $("input#dataISave").attr("disabled", true);
                $("input#dataISave").attr("value", "修改中...");
                return;
            }
            var filee = this.currentFile.context.files[0];
            if (filee === undefined || filee === null) {
                agent.onSaveOwnData(userId, null, name, sign, phone, email);
                $("input#dataISave").attr("disabled", true);
                $("input#dataISave").attr("value", "修改中...");
                return;
            }
            var reader = new FileReader();
            reader.readAsDataURL(filee);
            var binaryString;
            reader.onload = function (event) {
                binaryString = event.target.result;

                agent.onSaveOwnData(userId, binaryString, name, sign, phone, email);
                $("input#dataISave").attr("disabled", true);
                $("input#dataISave").attr("value", "修改中...");
            }

        }


        //响应保存我的资料
        this.onMessageBySaveOwnData = function (event) {
            var status = event.data.status;
            console.log(event.data);
            if (status == "" || status === undefined || status === null || status == "fail") {
                alert("修改失败");

            } else if (status == "success") {
                alert("修改成功");
                var inputs = $("input.dataInfoDatile");
                var name = "";
                var sign = "";
                var phone = "";
                var email = "";
                if (inputs.length === 4) {
                    name = $.trim(inputs.eq(0).val());
                    sign = $.trim(inputs.eq(1).val());
                    $(".nickName").text(name);
                    $(".sign").text(sign);
                }
                var srcContent = $(".changeImage").attr("src");
                $("#IPortrait").attr("src", srcContent);
                $(".iImage").attr("src", srcContent);
            }
            $("input#dataISave").attr("value", "保存修改");
            $("input#dataISave").attr("disabled", false);
        }

        //用户点击退出登陆
        this.onOfflineClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }

            $("body").on("click", ".exit", func);
        }

        //退出登陆
        this.onOffline = function (event) {
            if (confirm("确认退出当前账号？")) {
                console.log(!window.user);
                if (!window.user && !window.user.userName && !window.user.password) return;
                this.webSocketAgent.onUserOffline(window.user.userName);
                window.user = undefined;
                this.TalkMonitor = undefined;
                window.localStorage.clear();
                alert("退出登陆成功");
                window.location = "/login";
            }
        }

        //响应退出登陆
        this.onMessageOffline = function (event) {
            console.log(data);
        }

        //点击发送群公告
        this.onSendGroupAnnnontionListener = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }

            $("body").on("click", "#publicAnoun", func);
        }

        //发送群公告
        this.onSendGroupAnnnontion = function (event) {
            var group = this.nowChatGroupObj.group;
            var groupId = group.groupNumber;
            var groupName = group.groupName;
            var groupDec = $(".groupAnountion").val();
            console.log(groupDec);
            this.webSocketAgent.onSendGroupAnnoun(window.user.userName, groupId, groupDec);

            $("#publicAnoun").attr("value", "发送中...");
            $("#publicAnoun").attr("disabled", true);
        }

        //响应群公告
        this.onMessageBySendGroup = function (event) {
            console.log(event.data);
            $("#publicAnoun").attr("value", "发布");
            if (event.data.status == "success") {
                var content = $(".groupAnountion").val();
                console.log(content);
                $(".chatGroupNotification").text(content);
                $("#publicAnoun").attr("disabled", false);
                alert("发布成功");
            } else {
                alert("发布失败");
            }

        }

        //查看群资料点击
        this.onLookGroupDataClickListener = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }

            $("body").on("click", ".groupData", func);
            $("body").on("click", ".manager", func);
            $("body").on("click", ".groupNumber", func);
        }

        //查看群资料
        this.onLookGroupData = function (event) {
            var groupId = this.nowChatGroupObj.group.groupNumber;
            if (groupId) {
                this.webSocketAgent.onGetGroupData(groupId);
            }

        }

        //响应查看群资料
        this.onMessageByGetGroupData = function (event) {
            //群管理
            $("#groupTile").children("img").attr("src", event.data.groupPortrait);
            $(".groupManagerGroupName").text(event.data.groupName);
            $(".groupManagerGroupId").text(event.data.groupNumber);
            var members = event.data.members;
            if (members.length) {
                this.groupMember = [];
                $(".groupNumberInfo").empty();
                $(".ControllerNumbersList").empty();
                for (var i = 0; i < members.length; i++) {
                    var member = members[i];
                    this.onNewGroupUserInformation(member.groupUserPortrait, member.groupUserName);

                    var groupMember = new GroupMember();
                    groupMember.init(member);
                    var view = groupMember.getView();
                    $(".ControllerNumbersList").append(view);
                    //添加进
                    this.groupMember[member.groupUserUuid] = groupMember;

                    if (member.groupUserUuid == window.user.userName) {
                        view.children("input.deleteNumber").remove();
                        continue;
                    }
                    groupMember.addEventDispatchListener(operateIdTpye.DeleteGroupMember, this.onDeleteGroupMember, this);

                }


            }

            //查看群资料
            $(".groupReplaceImage").attr("src", event.data.groupPortrait);
            $(".groupDataSaveName").attr("value", event.data.groupName);

            //管理群成员

        }

        //创建群资料成员名片
        this.onNewGroupUserInformation = function (groupUserPortrait, groupUserName) {
            var newLi = document.createElement("li");
            newLi.innerHTML = '<img src="' + groupUserPortrait + '" />' + '<span>' + groupUserName + '</span>';
            var newLi = $(newLi);
            newLi.addClass("groupNumberList");
            $(".groupNumberInfo").prepend(newLi);
            return newLi;
        }

        //删除用户点击监听
        this.onDeleteGroupMember = function (event) {
            var target = event.target;
            var groupmember = target.member;
            //groupId,userId,delId
            var groupId = this.nowChatGroupObj.group.groupNumber;
            var userId = window.user.userName;
            var delId = target.member.groupUserUuid;
            if (!groupId || !userId || !delId) {
                alert("生成用户错误");
            }
            this.webSocketAgent.onManagerGroupUser(groupId, userId, delId);
        }

        //响应删除用户
        this.onMessageByDeleteMember = function (event) {
            var data = event.data;
            var userId = data.delUuid;
            console.log(event.data);
            console.log(this.groupMember);
            if (this.groupMember[userId]) {
                alert("删除成功");
                var view = this.groupMember[userId].getView();
                //删除自己
                view.remove();
            }
        }

        //退出群点击事件
        this.onExitGroupClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", ".exitGroup", func);
        }

        //退出群
        this.onExitGroup = function (event) {
            var userId = window.user.userName;
            var groupId = this.nowChatGroupObj.group.groupNumber;

            if (userId && groupId) {
                this.webSocketAgent.onQuitGroup(groupId, userId);
            }
        }

        //响应退出群
        this.onMessageByonExitGroup = function (event) {
            var data = event.data;
            var groupId = data.groupId;
            if (groupId) {
                var currentGroup = this.groupMap[groupId];
                if (currentGroup) {
                    var currentView = currentGroup.getView();
                    currentView.remove();
                    $("#chatClose").click();
                    //移除元素
                    var index = this.groupMap.indexOf(currentGroup);
                    if (index) {
                        this.groupMap.splice(index, 1);
                    }
                }
            }
        }

        //保存群资料点击响应
        this.onSaveGroupDataListener = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", "#groupDataSure", func);
        }

        //定位信息监听
        this.onLocationListener = function (obj, cal) {

            var func = function (event) {
                cal.call(obj, event);
            }

            $("body").on("click", "#chatBoxLocation", func);
            $("body").on("click", "#black", function () {
                $("#chatWindowInfo").attr("style", "display: block;");
                $("#chatWindowMap").attr("style", "display: none;");
            });

        }

        //定位信息
        this.onLocation = function (event) {
            var groupId = this.nowChatGroupObj.group.groupNumber;
            if (groupId) {
                this.webSocketAgent.onGetGroupUserLocations(groupId);
            }
        }


        //定位信息返回
        this.onMessageByLocation = function (event) {

            //	this.openView("chatWindowMap");
            var data = event.data.data;
            //清空用户头像
            $(".mapNumbersInfo").empty();
            if (data.length) {
                var currentMap;
                var points;

                for (var i = 0; i < data.length; ++i) {
                    var currentData = data[i];
                    var userName = currentData.userName;
                    var userPortrait = currentData.userPortrait;
                    var userLocationLatitude = currentData.userLocationLatitude;
                    var userLocationLongitude = currentData.userLocationLongitude;

                    //添加用户头像
                    var currentLi = document.createElement("li");
                    currentLi.innerHTML = '<img src="' + userPortrait + '">';
                    $(".mapNumbersInfo").append(currentLi);

                    if (points === undefined || points === null) points = [];

                    var bitMapPoint = new BMap.Point(userLocationLongitude, userLocationLatitude);
                    //			var bitMapPoint = new BMap.Point(113.037936,23.166192);
                    points.push(bitMapPoint);

                    if (i == 0) {
                        currentMap = new BMap.Map("map");
                        currentMap.enableScrollWheelZoom();
                        currentMap.enableKeyboard();
                        currentMap.enableDragging();
                        currentMap.enableDoubleClickZoom();
                        currentMap.centerAndZoom(new BMap.Point(userLocationLongitude, userLocationLatitude), 15);
                        //				currentMap.centerAndZoom(new BMap.Point(113.037936,23.166192), 15);
                    }
                }

                //坐标转换完之后的回调函数
                translateCallback = function (data) {
                    if (data.status === 0) {
                        for (var i = 0; i < data.points.length; i++) {
                            currentMap.addOverlay(new BMap.Marker(data.points[i]));
                            currentMap.setCenter(data.points[i]);

                            //		var pt = new BMap.Point(113.037936,23.166192);
                            //		var myIcon = new BMap.Icon("http://lbsyun.baidu.com/jsdemo/img/fox.gif", new BMap.Size(200,150));
                            //		var marker2 = new BMap.Marker(data.points[i],{icon:myIcon});  // 创建标注
                            //		currentMap.addOverlay(marker2);

                            var opts = {
                                position: data.points[i],// 指定文本标注所在的地理位置
                                offset: new BMap.Size(-25, 0)
                            }
                            var label = new BMap.Label(userName, opts);  // 创建文本标注对象
                            label.setStyle({
                                color: "red",
                                fontSize: "1px",
                                height: "12px",
                                lineHeight: "12px",
                                fontFamily: "微软雅黑"
                            });
                            currentMap.addOverlay(label);
                            currentMap.setCenter(data.points[i]);
                        }
                    }
                }

                setTimeout(function () {
                    var convertor = new BMap.Convertor();
                    convertor.translate(points, 1, 5, translateCallback);
                    console.log(this);
                }, 1000);
                //打开地图窗口
                this.openView("chatWindowMap");
            }
        }

        //保存群资料
        this.onSaveGroupData = function (event) {
            //var groupImage = $(".groupDataSaveName").val();
            var groupName = $(".groupDataSaveName").val();
            var groupId = this.nowChatGroupObj.group.groupNumber;
            var groupAnoun = $(".chatGroupNotification").text();

            var reader = new FileReader();
            var filee = this.currentFile.context.files[0];
            reader.readAsDataURL(filee);
            var binaryString;
            var agent = this.webSocketAgent;
            reader.onload = function (event) {
                binaryString = event.target.result;

                agent.onSaveGroupData(groupId, binaryString, groupName);
            }
        }

        //响应保存群资料
        this.onMessageBySaveGroupData = function (event) {
            var status = event.data.status;
            console.log(event.data);
            if (status == "success") {
                alert("修改群资料成功");
                var groupName = $(".groupDataSaveName").val();
                var srcContent = $('.groupReplaceImage').attr("src");
                this.nowChatGroupObj.group.groupPortrait = srcContent;
                $(".toPick").children("img").attr("src", srcContent);
                $(".chatGroupName").text(groupName);
                this.nowChatGroupObj.group.groupName = groupName;
                $(".toPick").children("span.groupName").text(groupName);
            }
        }

        //解散群点击  
        this.onDeleteGroupClickListener = function (obj, cal) {
            var func = function (event) {
                cal.call(obj, event);
            }
            $("body").on("click", ".groupDissolution", func);
        }

        //解散群
        this.onDeleteGroup = function (event) {
            var userId = window.user.userName;
            var groupId = this.nowChatGroupObj.group.groupNumber;

            this.webSocketAgent.onDeleteGroup(userId, groupId);
        }

        //解散群消息响应
        this.onMessageByDeleteGroup = function (event) {
            var groupId = event.data.groupId;

            if (groupId) {
                var groupObj = this.groupMap[groupId];
                if (groupObj) {
                    groupObj.getView().remove();
                    var index = this.groupMap.indexOf(groupObj);
                    if (index) {
                        this.groupMap.splice(index, 1);
                    }
                    console.log(this.groupMap);
                    $("#chatClose").click();
                    alert("解散群成功");
                }
            }
        }

        //获取消息View
        this.getOtherMessageView = function (type, userName, userContent, userPortrait) {
            var li = document.createElement("li");

            $(li).addClass(type);

            li.innerHTML = '<div class="chat-sender">' +
                '<div class="nickeNamePortrait"><img src="' + userPortrait + '" /></div>' +
                '<div class="nickName" >' + userName + '</div>' +
                '<div class="chatBackgroung">' +
                '<div class="chatMessageDatile">' +
                '<div class="chat-left_triangle"></div>' +
                '<span>' + userContent + '</span>' +
                '</div>' +
                '</div>' +
                '</div>';
            return li;
        }

        //用户被动接收消息
        this.onReceiveGroupMessage = function (event) {
            var data = event.data.data;
            if (data.length) {
                for (var i = 0; i < data.length; ++i) {
                    var currentData = data[i];
                    var currentDataGroupNumber = currentData.groupUuid;
                    var messages = currentData.messages;
                    var timeStamp;

                    //如果当前显示的组有消息发送过来，直接显示，没有就加入消息列表
                    if (this.nowChatGroupObj.group.groupNumber == currentDataGroupNumber) {
                        for (var l = 0; l < messages.length; ++l) {
                            var m = messages[l];

                            this.nowChatGroupObj.chatList.push(m);

                            var li;
                            //返回的消息发送的对象和发送的对象的ID相同则right
                            if (m.messageUserId == window.user.userName) {
                                li = this.getOtherMessageView("right", m.messageUserName, m.messageContent, m.userPortrait);
                            } else {
                                li = this.getOtherMessageView("left", m.messageUserName, m.messageContent, m.userPortrait);
                            }

                            if (timeStamp === undefined) {
                                timeStamp = m.messageTime;
                            } else if (timeStamp < m.messageTime) {
                                timeStamp = m.messageTime;
                            }
                            $("#groupMessage").append(li);
                            //滚动到底部
                            $("#groupMessage").scrollTop(10000000000000000);
                        }
                        this.timeStamp[currentDataGroupNumber] = timeStamp;
                        this.sendReceiveTime(currentDataGroupNumber, timeStamp);
                    } else {
                        var groupObj = this.groupMap[currentDataGroupNumber];
                        if (groupObj) {
                            var sender = messages[messages.length - 1].messageUserName;
                            var content = messages[messages.length - 1].messagecontent;
                            groupObj.getView().children("span.groupLastMessage").text(sender + ' : ' + content);

                            for (var j = 0; j < messages.length; ++j) {
                                groupObj.chatList.push(messages[j]);

                                if (timeStamp === undefined) {
                                    timeStamp = messages[j].messageTime;
                                } else if (timeStamp < m.messageTime) {
                                    timeStamp = messages[j].messageTime;
                                }
                            }
                            this.timeStamp[currentDataGroupNumber] = timeStamp;
                            this.sendReceiveTime(currentDataGroupNumber, timeStamp);
                        }
                    }
                }
                //this.sendReceiveTime(timeStamps);
            }
        }

        //返回当前接收到群最新消息的时间
        this.sendReceiveTime = function (groupId, timestamp) {
            var userId = window.user.userName;
            if (userId) {
                this.webSocketAgent.sendReceiveMessageTime(userId, groupId, timestamp);
            }
        }

        //回去接收的时间验证
        this.getReceiveTime = function (event) {
            var data = event.data;
            var groupId = data.groupId;

            if (this.timeStamp[groupId]) {
                if (this.timeStamp[groupId] > data.timeStamp) {
                    this.timeStamp[groupId] = data.timeStamp;
                }
            }
        }

        //获取当前时间
        this.onGetCurrentTime = function () {
            var date = new Date();

            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var monthStr;
            var hour = date.getHours();
            var second = date.getSeconds();
            var minutes = date.getMinutes();
            if (month < 10) {
                monthStr = "0" + month;
            } else {
                monthStr = month;
            }
            var dateStr;
            var date = date.getDate();
            if (date < 10) {
                dateStr = "0" + date;
            } else {
                dateStr = date;
            }
            if (minutes < 10) {
                minutes = "0" + minutes;
            }


            var time = year + "年" + monthStr + "月" + dateStr + "日" + hour + ":" + minutes + ":" + second;
            return time;
        }

        //获取通知View
        this.onGetNotieView = function (content) {
            var li = document.createElement("li");
            li.innerHTML = '<div class="chat-notice"><span class="notice">' + content + '</span></div>';

            return $(li);
        }

        //隐藏所有布局
        this.unView = function () {
            $("#chatWindow").attr("style", "display: none;");
            $("#createGroup").attr("style", "display: none;");
            $("#searchGroup").attr("style", "display: none;");
            $("#dataI").attr("style", "display: none;");
            $("#chatWindowMap").attr("style", "display: none;");
        }

        this.openView = function (type) {
            this.unView();
            if (type === "chat") {
                $("#chatWindow").attr("style", "display: block;");
            } else if (type === "createGroup") {
                $("#chatWindow").attr("style", "display: none;");
            } else if (type === "searchGroup") {
                $("#chatWindow").attr("style", "display: none;");
            } else if (type === "dataI") {
                $("#dataI").attr("style", "display: block;");
            } else if (type === "chatWindowMap") {
                $("#chatWindow").attr("style", "display: block;");
                $("#chatWindowInfo").attr("style", "display: none;");
                $("#chatWindowMap").attr("style", "display: block;");
            }
        }

        //下线操作
        this.disConnectWebsocket = function () {

            if (this.webSocketClient !== null && this.webSocketClient !== undefined) {

                this.webSocketClient.toClose();
                this.webSocketClient.removeAllEventDispatchListeners();
                this.webSocketClient = null;
            }
        }

        this.checkGroupRoleListener = function (obj, cal) {

            var func = function () {
                cal.call(obj);
            }

            $("body").on("click", "#chatLIistButton", func);
        }

        this.checkGroupRole = function () {
            var role = this.nowChatGroupObj.group.groupRole;

            if (role == undefined || role == null) return;

            if (role == 0) {
                $(".anoun").css("display", "block");
                $(".groupNumber").css("display", "block");
                $("#groupDataSure").css("display", "inline-block");
                $(".exitGroup").css("display", "none");
                $(".groupDissolution").css("display", "block");
            } else {
                console.log(role);
                $(".anoun").css("display", "none");
                $(".groupNumber").css("display", "none");
                $("#groupDataSure").css("display", "none");
                $(".exitGroup").css("display", "block");
                $(".groupDissolution").css("display", "none");
            }
        }
    }
    window.ddzj.TalkMonitor = TalkMonitor;
})(window);
