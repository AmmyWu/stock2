//var stock_code;
var stock_code = "600804";
$(document).ready(function () {
    //console.log(stock_code+"js");
    getNewData();
    var tg = self.setInterval("getNewData()", 1000);//N毫秒刷新一次，1000毫秒＝1秒
});
//获取新数据
function getNewData() {
    if (getQueryString('c') != undefined) {
        stock_code = getQueryString('c');
    }
    var type = stock_code[0] == 6 ? 1 : 2;
    var durl = "http://flashquote.stock.hexun.com/Stock_Combo.ASPX?mc=" + type + "_" + stock_code + "&dt=T&t=" + Math.random();
    $.getScript(durl, function (data) {
        //加载脚本并执行
    });
}

//刷新显示
function refreshData(code, type, tip, data) {

    var table = document.getElementById("stockTable");

    $("#stockTable").html("");



    table.insertRow();
    table.rows[0].insertCell(0).innerHTML="股票代码";
    table.rows[0].insertCell(1).innerHTML="今日开盘";
    table.rows[0].insertCell(2).innerHTML="昨日收盘";
    table.rows[0].insertCell(3).innerHTML="最高价";
    table.rows[0].insertCell(4).innerHTML="最低价";
    table.rows[0].insertCell(5).innerHTML="当前价格";
    //var code = 0;
    for (var i = 1; i < 2; i++) {
        table.insertRow();
        table.rows[i].insertCell(0).innerHTML= code;
        for (var j = 0; j < 5; j++) {
            var cell = table.rows[i].insertCell(j+1);
            {
                cell.innerHTML = data[j+1];
            }
        }
        // console.log("for")
    }


}


//getQueryString获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
