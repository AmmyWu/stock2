//@ sourceURL=role.js
var Stock = (function () {
    var account, stock;

    InitStock();
    InitAccount();

    function InitAccount() {
        account = $("#account").DataTable({
            //fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            //fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            //动态分页加载数据方式
            bProcessing: true,
            bServerSide: true,
            bLengthChange:false,
            bFilter:false,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            //searching: false,// 禁用搜索
            //lengthChange: true, // 是否启用改变每页显示多少条数据的控件
            deferRender: true,// 延迟渲染
            //stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            //iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            //serverSide: true,
            //autoWidth: true,
            // scrollX: true,
            //colReorder: true,//列位置的拖动
            scrollY: calcDataTableHeight(),
            destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            ajax: {
                "type": "POST",
                "url": getContextPath() + 'stockAccount/getByPage.do',
            },
            dom: '<"top">Brt<"bottom"flip><"clear">',
            columns: [
                { title: "总资产", data: "totalAsset" },
                { title: "股票总资产", data: "stockAsset" },
                { title: "资金余额", data: "remainingFund" },
                { title: "可用资金", data: "availableFund" },
                { title: "可取资金", data: "advisableFund" },
                { title: "冻结资金", data: "freezeFund" }
            ],
            // columnDefs: [
            //     {
            //         orderable: false,
            //         targets: 0
            //     },
            //     {
            //         "render": function (data, type, full, meta) {
            //             if (type === 'display')
            //                 return type === 'display' && data.length > 30 ?
            //                     '<span title="' + data + '">' + data + '</span>' : data;
            //             else if (type === 'copy') {
            //                 var api = new $.fn.dataTable.Api(meta.settings);
            //                 data = $(api.column(meta.col).header()).text() + ": " + data + "  ";
            //             }
            //             return data;
            //         },
            //         targets: [1, 2]
            //     }
            // ],
        });
    }

    function InitStock() {
        stock = $("#stock").DataTable({
            //fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            //fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            //动态分页加载数据方式
            bProcessing: true,
            bServerSide: true,
            bLengthChange:false,
            bFilter:false,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            searching: false,// 禁用搜索
            //lengthChange: true, // 是否启用改变每页显示多少条数据的控件
            /*
             * sort : "position",
             * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
             */
            deferRender: true,// 延迟渲染
            //stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            //iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            //serverSide: true,
            //autoWidth: true,
            // scrollX: true,
            //colReorder: true,//列位置的拖动
            scrollY: calcDataTableHeight(),
            destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
            //			    ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                "url": getContextPath() + 'stockExisting/getByPage.do',
            },
            dom: '<"top">Brt<"bottom"flip><"clear">',
            columns: [
                { title: "证券代码", data: "stockCode" },
                { title: "证券名称", data: "stockName" },
                { title: "买入价格", data: "costPrice" },
                { title: "持有量", data: "stockAvailableSellNum" },
            ],
            // columnDefs: [
            //     {
            //         orderable: false,
            //         targets: 0
            //     },
            //     {
            //         "render": function (data, type, full, meta) {
            //             if (type === 'display')
            //                 return type === 'display' && data.length > 30 ?
            //                     '<span title="' + data + '">' + data + '</span>' : data;
            //             else if (type === 'copy') {
            //                 var api = new $.fn.dataTable.Api(meta.settings);
            //                 data = $(api.column(meta.col).header()).text() + ": " + data + "  ";
            //             }
            //             return data;
            //         },
            //         targets: [1, 2]
            //     }
            // ],
        });
    }
})();

