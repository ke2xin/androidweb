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
