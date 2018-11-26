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
            $.ajax({
                type: "post",
                url: "http://172.17.145.127:8080/adminLogin",
                async: false,
                dataType: "json",
                contentType: "application",
                data: JSON.stringify(obj),
                xhrFields: {
                    withCredentials: true // 设置运行跨域操作  
                },
                success: function (data) {
                    console.log(data);
                }
            });
        }
    });
    $(".loginName").blur(function () {
        var name = loginName.val();
        var tips = loginName.next("span");
        tips.text("用户名不能为空")
        console.log(tips.text());
        console.log("用户名失去焦点");
    });
    $(".loginPassword").blur(function () {
        var possword = loginPassword.val();
        var tips = loginPassword.next("span");
        tips.text("密码不能为空")
        console.log(tips.text());
        console.log("密码失去焦点");
    });
})
