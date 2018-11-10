var SellHistory = (function () {
    var sell_history_table;

    Init();
    function Init() {
        sell_history_table = $("#sellHistoryTable").DataTable({
            bProcessing: true,
            bServerSide: true,
            bLengthChange: false,
            bFilter: false,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            searching: false,// 禁用搜索
            deferRender: true,// 延迟渲染
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            scrollY: calcDataTableHeight(),
            destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            ajax: {
                "type": "POST",
                // "url": "../mock_data/employee.json",
                "url": getContextPath() + 'sellerHistoryEntrustRecord/getByPage.do',
                "data": function (d) {
                    d.keys = JSON.stringify($('#searchSellHistoryForm').serializeObject());
                }
            },
            columns: [
                { title: "股票代码", data: "stockCode" },
                { title: "股票名称", data: "stockName" },
                { title: "委托日期", data: "entrustDate" },
                { title: "委托价格", data: "entrustPrice" },
                { title: "委托数量", data: "entrustNum" },
                { title: "成交价格", data: "dealPrice" },
                { title: "成交数量", data: "dealNum" },
                { title: "成交日期", data: "dealDate" },
            ],
        });
    }

    function doSearch() {
        sell_history_table.ajax.reload();
    }

    $("#resetSearchSellHistoryForm").click(function () {
        $("#searchSellHistoryForm")[0].reset();
        //Init();
        sell_history_table.ajax.reload();
    })
    return {
        doSearch: doSearch,
    };
})();

