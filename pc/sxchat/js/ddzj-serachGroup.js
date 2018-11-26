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
