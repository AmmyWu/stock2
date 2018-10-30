//@ sourceURL=charge.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var Charge = (function(){
 $.validator.setDefaults({
     submitHandler:submitEditChargeModal
 });
 $().ready(
     function ValidateChargeForm() {
         $("#editChargeForm").validate({
             rules:
                 {
                     code: {
                         required:true,
                         maxlength: 20
                     },
                     rateBase: {
                         maxlength: 10
                     },
                     cnName: {
                         required:true
                     },
                     enName: {
                         required:true
                     },
                     ediCode: {
                         maxlength:50
                     },
                     otherEdiCode: {
                         maxlength:50
                     },
                     description: {
                         maxlength:200
                     }

                 },
             messages:
                 {
                     code: {
                         required:"这是必填字段",
                         maxlength: "请不要超过限制的20个字符数"
                     },
                     rateBase: {
                         maxlength: "请不要超过限制的10个字符数"
                     },
                     cnName: {
                         required:"这是必填字段"
                     },
                     enName: {
                         required:"这是必填字段"
                     },
                     ediCode: {
                         maxlength:"请不要超过限制的50个字符数"
                     },
                     otherEdiCode: {
                         maxlength:"请不要超过限制的50个字符数"
                     },
                     description: {
                         maxlength:"请不要超过限制的200个字符数"
                     }
                 }

         });
     }
 );

    var charge_table;
    var paral={
        "chargeId":"费用ID",
        "superiorId":"SUPERIOR_ID",
        "code":"费用代码",
        "cnName":"费用名称（CN）",
        "enName":"费用名称（EN）",
        "rateBase":"基准利率",
        "ediCode":"EDI代码",
        "otherEdiCode":"其他EDI代码",
        "description":"描述"
    };
    Init();
    function Init() {
        charge_table =  $("#chargeTable").DataTable( {
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback:changePage,//重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            // 动态分页加载数据方式
            bProcessing : true,
            bServerSide : true,
            aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
            searching : false,// 禁用搜索
            lengthChange : true, // 是否启用改变每页显示多少条数据的控件
            /*
             * sort : "position",
             * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
             */
            ordering : false,// 全局禁用排序
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength :20, // 默认每页显示多少条记录
            iDisplayStart : 0,
            // ordering : false,// 全局禁用排序
            serverSide: true,
            autoWidth: true,
            // scrollX: true,
            colReorder: true,//列位置的拖动
            scrollY: calcDataTableHeight(),
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
			 // ajax: "../mock_data/user.txt",

            ajax : {
                "type" : "POST",
                // "url" : "../mock_data/charge.json",
                "url" : getContextPath()+'basedataCharge/listByPage.do',
                "data": function(d){
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys =  JSON.stringify($('#searchChargeForm').serializeObject());
                }
//
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
                    "sClass": "text-center",
                    "data": "chargeId",
                    "title":"<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return  '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                // { title: "费用ID", data:"chargeId"},
                // { title: "SUPERIOR_ID",data:"superiorId" },
                { title: "费用代码",data:"code" },
                { title: "费用名称（CN）",data:"cnName" },
                { title: "费用名称（EN）",data:"enName"},
                { title: "基准利率",data:"rateBase" },
                { title: "EDI代码",data:"ediCode" },
                { title: "其他EDI代码",data:"otherEdiCode" },
                { title: "描述",data:"description" },
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '': data.creatorName;
                    },
                    // "bSortable": false,
                },

                { title: "最新修改时间",data:"amendTime"}

            ],
            columnDefs: [
                {
                    orderable: false,
                    targets: 0 },
                {
                    "render": function ( data, type, full, meta ) {
                        if($.string.isNullOrEmpty(data))
                            return "";
                        else if(type === 'display')
                        return type === 'display' && data.length > 30 ?
                            '<span title="'+data+'">'+data+'</span>' : data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                    },
                    targets: [1,2,3,4,5,6,7,8,9]
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
                    container: '#Charge_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#Charge_export-excel-current'

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
                    container: '#Charge_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#Charge_export-print-all'
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
                    container: '#Charge_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#Charge_export-columnVisibility'
                }
            ],
            select: {
                // blurable: true,
                style: 'multi',//选中多行
                // info: false
            }
        } );
    }

// select/not select all
    $('body').on('click' , '.charge .checkall' , function(){
        var check = $(this).prop("checked");
        $(".charge .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#chargeTable tbody tr").each(function () {
            if ( charge_table.row( this, { selected: true } ).any() ) {
                charge_table.row( this ).deselect();
            }
            else {
                charge_table.row( this ).select();
            }
        });
        // if(check){
        //     $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
        // }
        // else {
        //     $("tr").removeClass("selected");
        // }

    });


    //add
    function addCharge() {
    // $("#addCharge").on('click',function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editChargeModalBody"), 'insert');

        $('#editChargeModal').modal('show');//现实模态框
    }
    // })
    //edict item
    function editCharge() {
        // $("#editCharge").click(function () {
        emptyAddForm();
        var selectedRowData = charge_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editChargeForm input,#editChargeForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editChargeModalBody"), 'update');
        $("#editChargeForm input[name='amendTime']").val(  data['amendTime']);
        $('#editChargeModal').modal('show');//现实模态框
    }
    // })
    //确定增加或者保存编辑；
        function submitEditChargeModal() {
            var data = $("#editChargeForm").serializeObject();
            var saveType = $("#editChargeForm input[name='saveType']").val();

            // 测试使用
        // charge_table.row.add(data).draw();//插入一行
        //     callSuccess("保存成功");

            $.ajax({
                url: getContextPath() + '/basedataCharge/'+saveType+'.do',
                data: data,
                cache: false,
                type:'POST',
                dataType: "json",
                beforeSend: function () {
                    showMask()
                },
                success: function (res) {
                    hideMask()
                    if(res.code ==0){
                        callSuccess(res.message);

                        charge_table .ajax.reload();
                    }
                    else

                        callAlert(res.message);

                },
                error: function () {
                    hideMask();
                    callAlert("增加失败");
                }
            });

            $('#editChargeModal').modal('hide');//现实模态框

        }

    // delete item
    function deleteCharge() {
        // $("#deleteCharge").click(function () {
        var info;
        var selectedRowData = charge_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'Charge_confirmDelete');
        $('.Charge_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.chargeId);
            });

            $.ajax({
                url: getContextPath() + 'basedataCharge/delete.do',
                data: {
                    chargeIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                     hideMask();//隐藏遮罩层
                    charge_table.ajax.reload();
                    if (rsp.code == 0)
                        callSuccess(rsp.message);
                },
                error: function () {
                    hideMask();//隐藏遮罩层
                    callAlert("删除失败！")
                }
            });

        });
        // });
    }

    //refesh charge_table
    function doSearch() {
    // $("#refreshCharge").click(function () {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        charge_table.ajax.reload();
    }

    $('#chargeTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log( charge_table.rows('.selected').data().length);
    } );


    // click item display detail infomation
    $('#chargeTable tbody').on('dblclick', 'tr', function () {
        var  data = charge_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data,paral);
    } );
    $('#showChargeDetail').on('click',function () {
        var rows_data = charge_table.rows('.selected').data();
        if(rows_data.length<1){
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i=0;i<rows_data.length;i++){
            $("#detail_table").html("");
            DisplayDetail(rows_data[i],paral);
        }

    })


    // 清空弹框
    function emptyAddForm() {
        $("#editChargeForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };

 //重置查询条件
 $("#resetChargeForm").click( function() {


     $("#searchChargeForm")[0].reset();

 });

 function doSearch(){
     charge_table.ajax.reload();
 }
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#chargeTable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addCharge();
                        break;
                    case "Delete"://删除
                        $("#chargeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteCharge();
                        break;
                    case "Edit"://编辑该节点
                        $("#chargeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editCharge();
                        break;
                    default:
                        options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);;//取消选择selected
                        null;
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
        editCharge: editCharge,
        addCharge:addCharge,
        deleteCharge:deleteCharge,
        doSearch:doSearch
    };
})();

