//@ sourceURL=ServiceLine.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var serviceLine = (function() {
    var port_tb;
    $.validator.setDefaults({
        submitHandler: submitEditServiceLineModal


    });

    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        // //初始化编辑form的港口下拉列表
        initSelect2FromRedis("editServiceLinePortModal", "portId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portId", "portName");

        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
    });


    $("#editServiceLinePortForm select[name='portId']").on("select2:select", function (e) {

        var portNameAndCode = $.string.split($("#editServiceLinePortForm select[name='portId']").select2("data")[0].text, '|');

        $("#editServiceLinePortForm input[name='portName']").val(portNameAndCode[0]);

        $("#editServiceLinePortForm input[name='portCode']").val(portNameAndCode[1]);


        var portId = $("#editServiceLinePortForm select[name='portId']").val();

        // //清空表单
        // $("#editServiceLinePortModal")[0].reset();
        // //初始化码头
        // emptySelect2Value("editServiceLinePortModal","wharfId");

        initSelect2FromDB("editServiceLinePortModal", "wharfId", "basedataWharf/listByKeys.do", "{'portId':" + portId + "}", "wharfId", "wharfCode");

    });

    $("#editServiceLinePortForm select[name='wharfId']").on("select2:select", function (e) {

        // var portNameAndCode =    $.string.split( $("#editServiceLinePortModal select[name='portId']").select2("data")[0].text,'|');


        $("#editServiceLinePortForm input[name='wharfCode']").val($("#editServiceLinePortModal select[name='wharfId']").select2("data")[0].text);


    });


    $().ready(
        function validateServiceLineForm() {
            $("#editServiceLineForm").validate({
                rules: {
                    serviceLineCode: {
                        //required:true,
                        maxlength: 5
                    },
                    serviceLineName: {
                        maxlength: 30
                    },
                    serviceLineNameCn: {
                        maxlength: 30
                    }
                },
                messages: {
                    serviceLineCode: {
                        maxlength: "最多输入5个字符"
                    },
                    serviceLineName: {
                        maxlength: "最多输入30个字符"
                    },
                    serviceLineNameCn: {
                        maxlength: "最多输入30个字符"
                    }
                }
            });
        }
    );
    var ServiceLine_table;
    var paral = {
        "serviceLineCode": "代码",
        "serviceLineNameCn": "中文名称",
        "serviceLineName": "英文名称",
        "isBulkSuit": "散货适用",
        "isContainerSuit": "集装箱适用"
    };

    Init();
    function Init() {

        ServiceLine_table = $("#ServiceLine_table").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
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
            //autoWidth: true,
            destroy: true,
            //scrollX: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url": "../mock_data/ServiceLine.json",
                "url": getContextPath() + 'basedataServiceLine/listByPage.do',
                "data": function (d) {
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys = JSON.stringify($('#searchServiceLineForm').serializeObject());
                }

            },
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
                    "class": "details-control",
                    "orderable": false,
                    "data": null,
                    "defaultContent": ""
                },
                {
                    "Class": "text-center",
                    "data": "serviceLineId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';

                    },
                    "Sortable": false

                },
                // { title: "港口", data:"ServiceLineId"},
                {title: "代码", data: "serviceLineCode"},
                {title: "中文名称", data: "serviceLineNameCn"},
                {title: "英文名称", data: "serviceLineName"},
                {
                    title: "散货适用",
                    data: "isBulkSuit",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "适用" : "不适应";
                    }
                },
                {
                    title: "集装箱适用", data: "isContainerSuit",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "适用" : "不适应";
                    }
                },
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.creatorName;
                    },
                    // "bSortable": false,
                },

                {title: "最新修改时间", data: "amendTime"}


            ],
            columnDefs: [
                {
                    orderable: false,
                    targets: 0
                },
                {
                    "render": function (data, type, full, meta) {
                        if ($.string.isNullOrEmpty(data))
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
                    targets: [1, 2, 3, 4, 5, 6, 7]
                }
            ],
            buttons: [
                {
                    extend: 'excelHtml5',
                    exportOptions: {
                        columns: ':visible',
                        rows: { selected: true }
                        },
                    text:"选中行导出Excel",
                    container: '#serviceLine_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#serviceLine_export-excel-current'

                },
                {
                    extend: 'copyHtml5',
                    text: '拷贝选中行',
                    header: false,
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            selected: true
                        },
                        orthogonal: 'copy'
                    },
                    container: '#serviceLine_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#serviceLine_export-print-all'
                },
                {
                    extend: 'print',
                    text: '打印选中行',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            selected: true
                        }
                    },
                    container: '#serviceLine_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#serviceLine_export-columnVisibility'
                }
            ],
            select: {
                // blurable: true,
                style: 'multi',//选中多行
                // info: false
            }

        });

    }

    // select/not select all
    $('body').on('click', '.ServiceLine .checkall', function () {
        var check = $(this).prop("checked");
        $(".ServiceLine .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#ServiceLine_table tbody tr").each(function () {
            if ( ServiceLine_table.row( this, { selected: true } ).any() ) {
                ServiceLine_table.row( this ).deselect();
            }
            else {
                ServiceLine_table.row( this ).select();
            }
        });

    });


// Array to track the ids of the details displayed rows
    var detailRows = [];

    $('#ServiceLine_table tbody').on('click', 'tr td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = ServiceLine_table.row(tr);

        var idx = $.inArray(tr.attr('id'), detailRows);

        if (row.child.isShown()) {
            tr.removeClass('details');
            row.child.hide();

            // Remove from the 'open' array
            detailRows.splice(idx, 1);
        }
        else {
            tr.addClass('details');
            row.child(format(row.data())).show();

            // Add to the 'open' array
            if (idx === -1) {
                detailRows.push(tr.attr('id'));
            }
        }
    });

// On each draw, loop over the `detailRows` array and show any child rows

    ServiceLine_table.on('draw', function () {
        $.each(detailRows, function (i, id) {
            $('#' + id + ' td.details-control').trigger('click');
        });
    });

    function format(d) {

        var table = $("<table id='port_tb' class='table table-bordered  table-condensed'></table>");

        init_port_tb(table, d.serviceLineId);


        return table;
        // 'Full name:  aaaa<br>'+
        //     'Salary: bbbb <br>'+
        //     'The child row can contain any data you wish, including links, images, inner tables etc.';
    }


//edict item
    $("#addPortToServiceLine").click(function () {

        var selectedRowData = ServiceLine_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行挂港操作！")
            return;
        }
        var data = selectedRowData[0];

        $("#editServiceLinePortForm input[name='serviceLineId']").val(data['serviceLineId']);

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editServiceLinePortModal"), 'insert');

        $('#editServiceLinePortModal').modal('show');//现实模态框

    })


//确定增加或者保存编辑；
    function submitEditServiceLinePortForm() {

        var data = $("#editServiceLinePortForm").serializeObject();
        var saveType = $("#editServiceLinePortForm input[name='saveType']").val();


        // 测试使用
        // ServiceLine_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + '/basedataServiceLinePort/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    callSuccess(res.message);

                    //刷新新增的港口
                    port_tb.ajax.reload();

                }
                else

                    callAlert(res.message);

            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        })


        //清空港口代码
        $("#editServiceLinePortForm input[name='portCode']").val("");

        //清空表单
        emptySelect2Value("editServiceLinePortForm", "portId");
        emptySelect2Value("editServiceLinePortForm", "wharfId");
        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editServiceLinePortModal"), 'insert');


        // $('#editServiceLineModal').modal('hide');//现实模态框
    }


//add
    function addServiceLine() {
        // $("#addServiceLine").on('click', function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editServiceLineForm"), 'insert');

        $('#editServiceLineModal').modal('show');//现实模态框
        // })
    }
    //edict item
    function editServiceLine() {
        // $("#editServiceLine").click(function () {
        emptyAddForm();
        var selectedRowData = ServiceLine_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editServiceLineForm input,#editServiceLineForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        $.each($("#editServiceLineForm input[type=checkbox]"), function (i, input) {


            $(this).prop("checked", data[$(this).attr("name")] == 1 ? true : false);
        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editServiceLineModalBody"), 'update');
        $("#editServiceLineForm input[name='amendTime']").val(  data['amendTime']);

        $('#editServiceLineModal').modal('show');//现实模态框

        // })
    }


    //确定增加或者保存编辑；submitEditServiceLineModal
    function submitEditServiceLineModal() {
        // if(!validateServiceLineForm()){
        //     // alert("validate error!");
        //     return;
        // }
        var data = $("#editServiceLineForm").serializeObject();
        var saveType = $("#editServiceLineForm input[name='saveType']").val();

        data.isBulkSuit == undefined ? data.isBulkSuit = 0 : data.isBulkSuit = 1;
        data.isContainerSuit == undefined ? data.isContainerSuit = 0 : data.isContainerSuit = 1;


        // 测试使用
        // ServiceLine_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + '/basedataServiceLine/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    callSuccess(res.message);

                    ServiceLine_table.ajax.reload();
                }
                else

                    callAlert(res.message);


            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
        $('#editServiceLineModal').modal('hide');//现实模态框
    }


    // delete item
    // $("#deleteServiceLine").click(function () {
    function deleteServiceLine() {
        var info;
        var selectedRowData = ServiceLine_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,"ServiceLine_confirmDelete");

        //确定删除
        $('.ServiceLine_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.serviceLineId);
            });

            $.ajax({
                url: getContextPath() + 'basedataServiceLine/delete.do',
                data: {
                    serviceLineIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);

                        ServiceLine_table.ajax.reload();
                    }
                    else

                        callAlert(rsp.message);


                },
                error: function (res) {
                    alert(res.code);
                    callAlert("修改失败！")
                }
            });
        });

        // });
    }


    //refesh table
    // $("#refreshServiceLine").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        ServiceLine_table.ajax.reload();
    }

    $('#ServiceLine_table tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(ServiceLine_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#ServiceLine_table tbody').on('dblclick', 'tr', function () {
        var data = ServiceLine_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showServiceLineDetail').on('click', function () {
        var rows_data = ServiceLine_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    })

    //重置查询条件
    $("#resetSeachServiceLineForm").click(function () {


        $("#searchServiceLineForm")[0].reset();

    })


    // 清空弹框
    function emptyAddForm() {
        $("#editServiceLineForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };


//根据选择的用户，初始化角色datatable
    function init_port_tb(table, serviceLineId) {
        port_tb = $(table).DataTable(
            {

                // 动态分页加载数据方式
                bPaginate: false, // 翻页功能
                bProcessing: true,
                bServerSide: true,
                searching: false,// 禁用搜索
                // lengthChange: false, // 是否启用改变每页显示多少条数据的控件
                /*
                 * sort : "position",
                 * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
                 */
                ordering: false,
                bInfo: false,
                deferRender: true,// 延迟渲染
                // iDisplayLength : 3, //默认每页显示多少条记录
                // iDisplayStart : 0,
//						ordering : false,// 全局禁用排序
                // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
                // ajax: "../mock_data/user_role.txt",
                ajax: {
                    "type": "POST",
                    "url": getContextPath() + 'basedataServiceLinePort/listByKeys.do',
                    dataType: 'json',
                    data: {
                        keys: '{"serviceLineId":' + serviceLineId + '}',
                        length: 100,
                        start: 0

                    }
                },
                language: {
                    "url": "js/Chinese.json"
                },
                columns: [
                    // {
                    //     "sClass": "text-center",
                    //     "data": "roleId",
                    //     "title": "<input type='checkbox' class='checkall' />",
                    //     "render": function (data, type, full, meta) {
                    //
                    //         return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    //     },
                    //     "Sortable": false
                    //
                    // },
                    {
                        title: "港序",
                        "data": null,
                        "render": function (data, type, full, meta) {
                            return meta.row + 1 + meta.settings._iDisplayStart;
                        }
                    },
                    {
                        title: "港口代码",
                        data: "portCode"
                    }, {
                        title: "港口名称",
                        data: "portName"
                    },
                    {
                        title: "码头代码",
                        data: "wharfCode"
                    }, {
                        title: "操作",
                        data: "serviceLinePortId",
                        "render": function (data, type, full, meta) {

                            if (data == undefined)
                                return "";
                            else
                                return "<button onclick='deleteServiceLintPort(" + data + ")'>删除</button> "
                        }
                    }],
                columnDefs: [{
                    orderable: false,
                    targets: [0]
                }]
            });

        $(table).on(
            'click',
            'tr',
            function (e) {
                e.stopPropagation();//阻止子元素触发父元素的事件的方法

                // $(this).toggleClass('selected');
                // $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);
                ;
            });
    }

    function deleteServiceLintPort(serviceLinePortId) {

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataServiceLinePort/delete.do',
            data: {
                serviceLinePortIds: serviceLinePortId
            },
            dataType: 'json',
            success: function (rsp) {

                if (rsp.code == 0) {
                    callSuccess(rsp.message);

                    // ServiceLinePort_table
                    port_tb.ajax.reload();
                }
                else

                    callAlert(rsp.message);

            },
            error: function (res) {
                alert(res.code);
                callAlert("修改失败！")
            }
        });
    }
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#ServiceLine_table tbody tr',
            callback: function (key, options) {
                //var row_data = ServiceLine_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addServiceLine();
                        break;
                    case "Delete"://删除该节点
                        $("#ServiceLine_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteServiceLine();
                        break;
                    case "Edit"://编辑该节点
                        $("#ServiceLine_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editServiceLine();
                        break;
                    default:
                        options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);;//取消选择selected
                }
            },
            items: {
                "Edit": {name: "修改", icon: "edit"},
                // "cut": {name: "Cut", icon: "cut"},
                // copy: {name: "Copy", icon: "copy"},
                // "paste": {name: "Paste", icon: "paste"},
                "Delete": {name: "删除", icon: "delete"},
                "Add": {name: "新增", icon: "add"},
                "sep1": "---------",
                "quit": {
                    name: "取消操作", icon: function () {
                        return 'context-menu-icon context-menu-icon-quit';
                    }
                }
            }
        });
    };
    return {
        // 将供页面该方法调用
        editServiceLine: editServiceLine,
        addServiceLine:addServiceLine,
        deleteServiceLine:deleteServiceLine,
        doSearch:doSearch
    };

})();
