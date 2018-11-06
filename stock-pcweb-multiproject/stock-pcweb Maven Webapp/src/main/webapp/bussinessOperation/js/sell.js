//@ sourceURL=role.js
var Stock = (function () {
    var my_stock;
    var maxNum;
    var stockId;
    InitMyStock();//加载表格

    // $().ready(
    //     function validateEmployeeForm() {
    //         $("#sellStockForm").validate({
    //             rules: {
    //                 entrustNum:{
    //                     required:true,
    //                     range:[1,maxNum/100]
    //                 },
    //                 entrustPrice:{
    //                     required:true,
    //                     min:0
    //                 },
    //             },
    //             messages: {
    //                 entrustNum:{
    //                     required:"委托数量必须填写",
    //                     range:"数量必须介于"+1+"-"+maxNum/100+"之间"
    //                 },
    //                 entrustPrice:{
    //                     required:"委托价格必须填写",
    //                     min:"价格不得小于0"
    //                 },
    //             }
    //         });
    //     }
    // );
    function InitMyStock() {
        my_stock = $("#myStock").DataTable({
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
            dom: '<"top">Brt<"bottom"flip><"clear">',
            columns: [
                { title: "证券代码", data: "stockCode" },
                { title: "证券名称", data: "stockName" },
                { title: "买入价格", data: "costPrice" },
                { title: "可卖数量", data: "stockAvailableSellNum" },
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

    //选中一行
    $('#myStock tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            my_stock.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
        var td = $(this).find("td");//this指向了当前点击的行，通过find我们获得了该行所有的td对象
        //题中说到某个td，为了演示所以我们假设是要获得第3个td的数据
        stock_code = td.eq(0).html();//通过eq可以得到具体的某个td对象，从而得到相应的数据
        maxNum = td.eq(3).html();
        
    });

    function sell() {
        emptyAddForm();

        var selectedRowData = my_stock.rows('.selected').data();

        if (selectedRowData.length < 1) {
            callAlert("请选择一个股票卖出！")
            return;
        }
        if (selectedRowData.length > 1) {
            callAlert("每次只允许选择一个股票！")
            return;
        }

        var data = selectedRowData[0];
        stockId=data['stockId']
        console.log("stockId:"+stockId)
        // var sdata=JSON.stringify(data);
        // console.log("data:"+sdata);
        // 循环给表单赋值
        $.each($("#editRoleModal input "), function (i, input) {
            $(this).val(data[$(this).attr("name")]);
        });

        setDefaultValue($("#sellStockForm"), 'insert');
        $("#sellStockForm input[name='amendTime']").val(data['amendTime']);

        $('#editRoleModal').modal('show');
    }

    //确定增加或者保存编辑；
    $("#saveRole").click(function () {
        var saveType = $("#sellStockForm input[name='saveType']").val();
        var entrustNum = $("#sellStockForm input[name='entrustNum']").val();
        var entrustPrice = $("#sellStockForm input[name='entrustPrice']").val();
        
        var price={};
        price.entrustPrice=entrustPrice;
        price.stockId=stockId.toString();
        //price.sellerEntrustPriceId="";
        console.log(price);
        var priceQueue={};
        priceQueue.entrustNum=entrustNum;
        console.log(priceQueue);
        $.ajax({
            url: getContextPath() + 'sellerEntrustPrice/sell.do',
            type: 'POST',
            data:
            {
                //stockExisting: JSON.stringify($('#sellStockForm').serializeObject()),
                price: JSON.stringify(price),
                priceQueue: JSON.stringify(priceQueue),
                //entrustPrice:JSON.stringify(entrustPrice)
            },
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0)
                    callSuccess(res.message);

                my_stock.ajax.reload();

            },
            error: function () {
                hideMask();
                callAlert("修改失败!");
            }
        });

    })


    // 清空弹框
    function emptyAddForm() {
        $("#sellStockForm")[0].reset();
    };

    return {
        sell: sell
    };
})();

