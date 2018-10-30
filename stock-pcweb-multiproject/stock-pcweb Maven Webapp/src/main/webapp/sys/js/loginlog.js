//@ sourceURL=log.js
(function() {
    var loginlog_table;

    $(function () {

        Init();//加载表格
        //InitAddTable();
        function Init() {
            loginlog_table = $("#loginlog_table").DataTable({
                // data: dataSet,
                // bPaginate: true, //翻页功能
                // bLengthChange: true, //改变每页显示数据数量
                // paging: true,
                // lengthChange: false,
                // searching: true,
                // ordering: true,
                // info: true,
                // autoWidth: true,
                // select:true,
                // destroy: true,
                // scrollX: true,
                // ajax: "sys/mock_data/log.txt",

                // 动态分页加载数据方式
                fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
                bStateSave: false, // 在第三页刷新页面，会自动到第一页
                iDisplayLength: 20, // 默认每页显示多少条记录
                iDisplayStart: 0,
                ordering: false,// 全局禁用排序
                serverSide: true,
                colReorder: true,//列位置的拖动
                // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
                ajax: {
                    "type": "POST",
                    "url": getContextPath() + 'loginlog/list.do',
                    "data": function (d) {
//					alert(JSON.stringify($('#searchForm').serializeObject()));

                        d.keys = JSON.stringify($('#searchLogForm').serializeObject());
                    }

                },
                dom:'<"top">rt<"bottom"flip><"clear">',
                language: {
                    "url": "js/Chinese.json",
                    select: {
                        rows: "%d 行被选中"
                    },
                    buttons: {
                        copyTitle: '复制到剪切板',
                        // copyKeys: 'Appuyez sur <i>ctrl</i> ou <i>\u2318</i> + <i>C</i> pour copier les données du tableau à votre presse-papiers. <br><br>Pour annuler, cliquez sur ce message ou appuyez sur Echap.',
                        copySuccess: {
                            _: '将%d 行复制到剪切板',
                            1: '将1行复制到剪切板'
                        }
                    }
                },
                columns: [
                    {
                        "Class": "text-center",
                        "data": "loginLogId",
                        "title": "<input type='checkbox' class='checkall' />",
                        "render": function (data, type, full, meta) {
                            return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';

                        },
                        "Sortable": false,

                    },
                    {title: "日志编号", data: "loginLogId"},
                    {title: "用户名称", data: "username"},
                    {title: "用户账号", data: "authenticationAccount"},
                    {title: "访问IP", data: "loginIp"},
                    {title: "操作类型", data: "type"},
                    {title: "操作时间", data: "loginTime"}
                ],
                columnDefs: [
                    {
                        orderable: false,
                        targets: 0
                    },
                    {
                        "render": function (data, type, full, meta) {
                            if (data == undefined || data == "")
                                return "";
                            else if  (type === 'display')
                                return type === 'display' && data.length > 30 ?
                                    '<span title="' + data + '">' + data + '</span>' : data;
                            else if  (type === 'copy') {
                                var api = new $.fn.dataTable.Api(meta.settings);
                                data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                            }
                            return data;
                        },
                        targets: [1, 2, 3, 4, 5]
                    }
                ],
                buttons: [
                    {
                        extend: 'excelHtml5'
                        // text:"导出Excel"
                    },
                    {
                        extend: 'copyHtml5',
                        text: '拷贝选中行',
                        header: false,
                        exportOptions: {
                            modifier: {
                                selected: true
                            },
                            orthogonal: 'copy'
                        }
                    },
                    {
                        extend: 'print',
                        text: '打印全部'
                    },
                    {
                        extend: 'print',
                        text: '打印选中行',
                        exportOptions: {
                            modifier: {
                                selected: true
                            }
                        }
                    }
                ],
                select: {
                    // blurable: true,
                    style: 'multi',//选中多行
                    // info: false
                }

            });

        }
    });
    $('body').on('click', '.container-type .checkall', function () {
        var check = $(this).prop("checked");
        $(".loginlog .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#loginlog_table tbody tr").each(function () {
            if ( loginlog_table.row( this, { selected: true } ).any() ) {
                loginlog_table.row( this ).deselect();
            }
            else {
                loginlog_table.row( this ).select();
            }
        });

    });
    $('#loginlog_table tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        // console.log(table.rows('.selected').data().length);
    });

    function doSearch(){
        loginlog_table.ajax.reload();
    }



    $(function () {
        //Date picker
        $('.date').datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });
    });



//重置查询条件
    $("#resetSeachLogForm").click(function () {
        $("#searchLogForm")[0].reset();
    });

// click item display detail infomation
// $('#loginlog_table tbody').on('dblclick', 'tr', function () {
//     var data = loginlog_table.rows($(this)).data()[0];
//     $("#detail_table").html("");
//     DisplayDetail(data, paral);
// });
// $('#showLogDetail').on('click', function () {
//     var rows_data = bank_table.rows('.selected').data();
//     if (rows_data.length < 1) {
//         callAlert("请选择一条数据进行查看");
//         return;
//     }
//     for (var i = 0; i < rows_data.length; i++) {
//         $("#detail_table").html("");
//         DisplayDetail(rows_data[i], paral);
//     }
//
// });
    return{
        doSearch:doSearch
    };
})();