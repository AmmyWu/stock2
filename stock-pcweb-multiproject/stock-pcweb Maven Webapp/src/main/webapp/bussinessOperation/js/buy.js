$(document).ready(function () {
    $("#buyButton").click(function () {
        alert("调用成功！");
        var stock_id = $("#stock_id").val();
        var stock_name = $("#stock_name").val();
        var stock_count = $("#stock_count").val();
        var stock_price = $("#stock_price").val();
        if(!stock_id&&!stock_name){
            alert("未输入股票信息！");
        }
        else if(stock_id&&stock_name){
            if(!check_stock_valid(stock_id,stock_name)){
                alert("股票代码与名称不匹配！");
            }
        }else if(stock_name){
            stock_id = find_stock_id(stock_name);
        }
        buy_stock(stock_id,stock_count,stock_price);
    });
});
function check_stock_valid(id,name){
    $.ajax({
        type:"POST",
        url:getContextPath()+"/check_stock_valid",
        data: {
            id:id,
            name: name
       },
        dataType:"text",
        success: function (reg) {
            if(reg=="correct"){
                return true;
            }
            return false;
        },
        error: function () {
            alert("there is a server error!");
        }
    });
}
function find_stock_id(stock_name) {

}
function buy_stock(stock_id,stock_count,stock_price) {
    // alert(Number.prototype.isInteger(stock_count));
    $.ajax({
        type : "POST",
        url :  getContextPath()+"/buy_stock",
        data : {
            stock_id : stock_id,
            stock_count : stock_count,
            stock_price : stock_price
        },
        dataType : "json",
        success : function (reg) {
            if(reg.data == "wait"){
                alert("交易")
            }
        },
        error :function () {
            alert()
        }
    });
}
