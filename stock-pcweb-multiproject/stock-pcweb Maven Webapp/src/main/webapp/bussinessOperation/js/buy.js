var stock_key = -1 ;
$(document).ready(function () {
    init();
    $("#buyButton").click(function () {
        // alert("调用成功！");
        var stock_id = $("#stock_id").val();
        var stock_name = $("#stock_name").val();
        var stock_count = $("#stock_count").val();
        var stock_price = $("#stock_price").val();
        if(!stock_count||!stock_price){
            alert("输入信息不完全！");
            return;
        }
        if(!stock_id&&!stock_name){
            alert("未输入股票信息！");
        }
        else if(stock_id&&stock_name){
            stock_key = check_stock_valid(stock_id,stock_name);
        }else if(stock_name){
            stock_key = find_key_by_name(stock_name);
        }else{
            stock_key = find_key_by_id(stock_id);
        }

        if(stock_key==-1){
            alert("查询不到此股票！交易失败！")
        }else {
            // alert("准备开始购买股票~");
            buy_stock(stock_key,stock_count,stock_price);
        }
    });
});
function check_stock_valid(id,name){
    var res;
    $.ajax({
        type:"POST",
        url:getContextPath()+"stock/check_stock_valid",
        data: {
            id:id,
            name: name
       },
        // dataType:"number",
        async:false,
        success: function (reg) {
            res =  reg;
        },
        error: function () {
            alert("there is a server error!");
        }
    });
    return res;
}
function find_key_by_name(stock_name) {
    var res;
    $.ajax({
        type:"POST",
        url:getContextPath()+"stock/find_key_by_name",
        data:{
            stock_name:stock_name
        },
        async:false,
        // dataType:"number",
        success : function (reg) {
            res = reg;
        },
        error: function () {
            alert("there is a server error!");
        }
    });
    return res;
}
function find_key_by_id(stock_id) {
    var res;
    $.ajax({
        type:"POST",
        url:getContextPath()+"stock/find_key_by_id",
        data:{
            code:stock_id
        },
        async:false,
        // dataType:"number",
        success : function (reg) {
            res = reg;
        },
        error: function () {
            alert("there is a server error!");
        }
    });
    return res;
}
function buy_stock(stock_key,stock_count,stock_price) {
    var loginingEmployee = $.cookie("loginingEmployee");//获取当前的user
    alert("prepare ajax...");
    $.ajax({
        type : "POST",
        url :  getContextPath()+"buyerEntrustPrice/buy_stock",
        data : {
            stock_id : stock_key,
            stock_count : stock_count,
            stock_price : stock_price,
            user_id : JSON.parse(loginingEmployee)['user']['userId']
        },
        async:false,
        dataType : "text",
        success : function (reg) {
            if(reg == "wait"){
                alert("交易挂单成功");
            }else{
                alert("交易成功");
                init();
            }
        },
        error :function () {
            alert("there is a server error!");
        }
    });
}
function init() {
    var my_stock = $("#myStockAccount").DataTable({
        //fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
        fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
        // 动态分页加载数据方式
        bProcessing: true,
        bServerSide: true,
        aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
        searching: false,// 禁用搜索
        lengthChange: true, // 是否启用改变每页显示多少条数据的控件
        /*
         * sort : "position",
         * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
         */
        deferRender: true,// 延迟渲染
        stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
        iDisplayLength: 20, // 默认每页显示多少条记录
        iDisplayStart: 0,
        ordering: false,// 全局禁用排序
        serverSide: true,
        autoWidth: true,
        // scrollX: true,
        colReorder: true,//列位置的拖动
        scrollY: calcDataTableHeight(),
        destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
        // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
        //			    ajax: "../mock_data/user.txt",
        ajax: {
            "type": "POST",
            "url": getContextPath() + 'stockExisting/getByPage.do',
        },
        // dom: '<"top">Brt<"bottom"><"clear">',//flip
        // dom: '<"top">Brt<"bottom"flip><"clear">',
        columns: [
            { title: "证券代码", data: "stockCode" },
            { title: "证券名称", data: "stockName" },
            { title: "买入价格", data: "costPrice" },
            { title: "持有数量", data: "stockAvailableSellNum" },
            { title: "股票id", data: "stockId" ,bVisible: false}
        ],//
        columnDefs: [
            {
                orderable: false,
                targets: 0
            },
            {
                "render": function (data, type, full, meta) {
                    if (type === 'display')
                        return type === 'display' && data.length > 30 ?
                            '<span title="' + data + '">' + data + '</span>' : data;
                    else if (type === 'copy') {
                        var api = new $.fn.dataTable.Api(meta.settings);
                        data = $(api.column(meta.col).header()).text() + ": " + data + "  ";
                    }
                    return data;
                },
                targets: [1, 2]
            }
        ],
    });
}
