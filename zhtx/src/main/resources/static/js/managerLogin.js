$(document).ready(function () {
    var loginName = $(".loginName");
    var loginPassword = $(".loginPassword");
    $("#managerLogin").click(function () {//管理员登录
        var name = loginName.val();
        var possword = loginPassword.val();
        console.log("登录名：" + name);
        console.log("密码：" + possword);
        if (name != "" && possword != "") {
            var obj = new Object();
            obj.loginName = name;
            obj.loginPassword = possword;
            console.log(JSON.stringify(obj))
            var loginTips = loginName.next("span");
            var passwordTips = loginPassword.next("span");
            $.ajax({
                type: "post",
                url: "http://localhost:8080/adminLogin",
                async: false,
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                data: JSON.stringify(obj),
                success: function (data) {
                    console.log(data);
                    var json = JSON.stringify(data);
                    console.log(json);
                    var jsonObj = JSON.parse(json);
                    console.log(jsonObj.theUser);
                    sessionStorage.setItem("theUser", jsonObj.theUser);
                    if (jsonObj.code == 0) {
                        loginTips.text(jsonObj.result);
                    } else if (jsonObj.code == 2) {
                        passwordTips.text(jsonObj.result);
                    } else if (jsonObj.code == 1) {
                        loginTips.text(jsonObj.result);
                        passwordTips.text(jsonObj.result);
                        window.location.href = "http://localhost:8080/manager"
                    }
                },
                error: function (err, textStatus, errorThrown) {
                    console.log("出错了1：" + err)
                    console.log("出错了2：" + textStatus)
                    console.log("出错了3：" + errorThrown)
                }
            });
        }
    });
    $(".loginName").blur(function () {
        var name = loginName.val();
        var tips = loginName.next("span");
        if (name == null) {
            tips.text("用户名不能为空")
        }
        console.log(tips.text());
        console.log("用户名失去焦点");
    });
    $(".loginPassword").blur(function () {
        var possword = loginPassword.val();
        var tips = loginPassword.next("span");
        if (possword == null) {
            tips.text("密码不能为空")
        }
        console.log(tips.text());
        console.log("密码失去焦点");
    });
})