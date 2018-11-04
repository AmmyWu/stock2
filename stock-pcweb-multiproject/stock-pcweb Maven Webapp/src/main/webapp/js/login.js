var LOGININGEMPLOYEE = null;
var flag;
// dom 就绪后设置 nav bar 和 copyright
$(document).ready(function () {


    $("#loginButton").click(function () {

        $("#loginButton").val("登录中...").css('background-color', '#ddd').attr("disabled", "true");
        var success = doLogin();
        if (!success) {
            $("#loginButton").attr("disabled", false);
            $("#loginButton").val("登录").css('background-color', '#7552F5');
        }
    });
    $("#loginButton1").click(function () {

        location.href = "http://47.92.30.74:8099/stockssoWebappBase/cas/authorize.do?clientId=sbHC2Mf40_LOwMn60MXPor72st-31s72xr3MqDIwMTgtMTAtMjMgMTc6MzU6NTgxMw";
    });


    $("#resetPassword").click(function () {

        if (!checkCellPhoneIntput($("#loginCellPhone")))
            return;

        if (isUnExistCellPhone($("#loginCellPhone")))
            return;

        if (!$.string.isNullOrEmpty($(".alert_span").text()))
            return;
        checkStatus();
        if (!flag)
            return;
        if (confirm("重置后的新密码将以短信方式发您手机，请确认你的手机号是否输入正确！"))
            resetPassword();
    });
});


function isLogin() {

    if ($.cookie("loginingCustomer") == undefined || $.cookie("loginingCustomer") == null) {


        $('#btn-toCenter').remove();
        $('#btn-logout').remove();
        return false;
    }
    if ("客商" == LOGININGCUSTOMER.user.type)
        $('#btn-toCenter').remove();

    $('#btn-login-div').remove();
    $('#link-register').remove();

    LOGININGCUSTOMER = JSON.parse($.cookie("loginingCustomer"));

    return true;

}

function doLogin() {
    console.log("login..");
    if (!checkAccountIntput($("#loginCellPhone")) || !checkPasswordInput($("#loginPassword")) ||
        !$.string.isNullOrEmpty($(".alert_span").text()))
        return false;
    checkStatus();
    var type = $("#type").val();
    var test = hex_md5($('#loginPassword').val());
    if (!flag) {
        return false;
    }
    $.ajax({
        type: "POST",
        url: getContextPath() + "j_spring_security_check", //baseURL
        data: {
            account: $("#loginCellPhone").val(),
            password: hex_md5($('#loginPassword').val()),
            type: type
        },
        dataType: "json",

        success: function (returnMsg) {
            if (!returnMsg.status || !returnMsg.menuJSON) {
                loginFailure(returnMsg);
            }
            else {
                loginSuccess(returnMsg);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus + errorThrown);
        }
    });
    return false;
}

function checkAccountIntput(input) {

    $(".alert_span").remove();

    $(".alert_span").remove();

    var reg = /^[0-9]*$/;

    if ($(input).val() == "") {
        $(input)
            .parent()
            .after(
                "<span class='alert_span'><font color='red'>帐号不能为空！</font></span>");
        $(input).focus();
        return false;
    } else if (!reg.test($(input).val())) {
        $(input)
            .parent()
            .after(
                "<span class='alert_span'><font color='red'>账号必须为数字！</font></span>");
        $(input).focus();
        return false;
    }

    return true;
}

function checkPasswordInput(input) {

    $(".alert_span").remove();
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{0,50}$/;
    if ($(input).val() == "" || $(input).val().length < 6) {
        $(input)
            .parent()
            .after(
                "<span class='alert_span'><font color='red'>请输入6位及上密码！</font></span>");
        $(input).focus();
        return false;
    } else if (!reg.test($(input).val())) {
        $(input)
            .parent()
            .after(
                "<span class='alert_span'><font color='red'>不允许出现特殊字符！</font></span>");
        $(input).focus();
        return false;
    }
    return true;

}


function loginSuccess(returnMsg) {

    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,50}$/;
    var password = $("#loginPassword").val();
    if (!reg.test(password)) {
        window.location.href = "alterPassword.html?userId=" + returnMsg.user.userId;
        return;

    }
    if ("员工" == returnMsg.user.type) {
        //主菜单
        localStorage.menu = JSON.stringify(returnMsg.menuJSON);
        // alert(returnMsg);

        localStorage.button = returnMsg.btnResourceIds;
        localStorage.username = returnMsg.employee.employeeName;
        delete returnMsg.menuJSON;
        delete returnMsg.btnResourceIds;
        $.cookie('loginingEmployee', JSON.stringify(returnMsg), {
            path: '/stock'
        }); //expires: 7,
        window.location.href = "index-for-login.html";
        return;
    }
    return false;
}


function callAlertModal(info) {
    $('#alertContent').html(info);
    $('#alertModal').modal('show');
}


function resetPassword() {



    //todo  发送重置密码的短信

    var result = $.util.requestAjaxData(BASE_URL + '/portal/resetPassword.do', {
        'cellPhone': $("#loginCellPhone").val(),
        "type": $('input[name="type"]:checked').val()
    });

    $(".alert_span").remove();
    $("#resetPassword").after("<span class='alert_span'><font color='red'>" + result + "</font></span>");
    return false;

}

function loginFailure(returnMsg) {
    alert("用户名或密码错误！");
    AlterWrongTimes();
    return false;
}

function AlterWrongTimes() {
    $.ajax({
        type: "POST",
        url: getContextPath() + "authentication/alterWrongTimes.do", //baseURL
        data: {
            account: $("#loginCellPhone").val(),
        },
        dataType: "text",
        success: function (returnMsg) {
            if (returnMsg == "lock") {
                alert("错误次数过多！请联系管理员解锁账号！")
            } else {
                alert("还可以输入" + (5 - returnMsg) + "次");
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus + errorThrown);
        }
    });
}

function checkStatus() {

    $.ajax({
        type: "POST",
        url: getContextPath() + "authentication/checkStatus.do",
        data: {
            account: $("#loginCellPhone").val()
        },
        dataType: "text",
        async: false,
        success: function (returnMsg) {
            if (returnMsg == "禁用") {
                alert("账号已冻结！请联系管理员解锁账号！");
                flag = false;
            } else if (returnMsg == "用户不存在") {
                alert("账号不存在！");
                flag = false;
            } else {
                flag = true;
            }

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus + errorThrown);
        }
    });
    return flag;
}

function lockAccount() {
    $.ajax({
        type: "POST",
        url: getContextPath() + "authentication/lockAccount.do", //baseURL
        data: {
            account: $("#loginCellPhone").val()
        },
        dataType: "text",
        success: function (res) {
            alert("错误次数过多！请联系管理员解锁账号！")

        },
        error: function () {
            alert("系统出现故障！");
        }
    });
}