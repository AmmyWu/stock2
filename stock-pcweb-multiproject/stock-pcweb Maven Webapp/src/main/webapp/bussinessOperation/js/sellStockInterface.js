//var stock_code;
var stock_code = "600804";
$(document).ready(function () {
    //console.log(stock_code+"js");
    getNewData();
    var tg = self.setInterval("getNewData()", 2000);//N毫秒刷新一次，1000毫秒＝1秒
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

    var table = document.getElementById("marketStock");
    // var row = table.rows.length;
    // var col = table.rows[0].cells.length;
    $("#marketStock").html("");
    // for(var i=0;i<row;i++){
    //     for(var j=0;j<col;j++){
    //         table.rows[i].deleteCell(j);
    //     }
    // }
    var code = 24;
    var title = "卖";
    var titleRow = 5;

    table.insertRow();
    table.rows[0].insertCell(0).innerHTML=""+stock_code;

    for (var i = 1; i < 11; i++) {
        table.insertRow();
        for (var j = 0; j < 3; j++) {
            var cell = table.rows[i].insertCell(j);
            if (j == 0) {
                cell.innerHTML = title + "" + titleRow;
                titleRow--;
            } else {
                cell.innerHTML = data[code];
                code++;
            }

        }
        if (titleRow == 0) {
            title = "买"
            titleRow = 5;
        }
        if (i < 4) {
            code -= 4;
        }
        else if (i == 4) {
            code -= 12;
        }
        // else {
        //     code += 1;
        // }
    }
    //$("#result").html(result);
}
//getQueryString获取url参数
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
