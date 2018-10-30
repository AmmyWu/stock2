//@ sourceURL=SerialNumber.js
// $(document).ready(function() {
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
var serialNumber = (function() {

    $.validator.setDefaults({
        submitHandler: submitEditSerialNumberModal
    });

    $(function () {
        //初始化组织架构树
        InitOrganizationZtree2();
    });

    $().ready(
        function validateSerialNumberForm() {
            $("#editSerialNumberForm").validate({
                rules: {
                    useLogo: {
                        maxlength: 11,
                        required:true
                    },
                    // officeCodeId: {
                    //     maxlength: 11,
                    //     digits: true
                    // },
                    organizationStructureId: {
                        maxlength: 11,
                        required:true,
                        digits: true
                    },
                    serialNumberLength: {
                        maxlength: 11,
                        digits: true
                    },
                    serialNumberStart: {
                        maxlength: 11,
                        digits: true
                    },
                    serialNumberLatest: {
                        maxlength: 11,
                        digits: true
                    },
                    ruleText: {
                        maxlength: 200,
                    }

                },
                messages: {
                    useLogo: {
                        maxlength: "最多输入11位数字"
                    },
                    // officeCodeId: {
                    //     maxlength: "最多输入11位数字"
                    // },
                    organizationStructureId: {
                        maxlength: "最多输入11位数字"
                    },
                    serialNumberLength: {
                        maxlength: "最多输入11位数字"
                    },
                    serialNumberStart: {
                        maxlength: "最多输入11位数字"
                    },
                    serialNumberLatest: {
                        maxlength: "最多输入11位数字"
                    },
                    ruleText: {
                        maxlength: "最多输入200个字符"
                    }

                }
            });
        }
    );

    var serialNumber_table;
    var paral = {
        // "serialNumberId": "银行ID",
        "useLogo": "用途标识",
        // "officeCodeId": "办事处代码",
        "organizationStructureName": "组织机构",
        "serialNumberLength": "序列号长度",
        "serialNumberStart": "序列号起始值",
        "serialNumberLatest": "最新的序列号",
        "ruleText": "规则文本"
    };
    InitSerialNumber();
    function InitSerialNumber() {
        serialNumber_table = $("#serialNumberTable").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
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
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            serverSide: true,
            scrollX: true,
            autoWidth: true,
            colReorder: true,//列位置的拖动
            scrollY:calcDataTableHeight(),
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // buttons: [ 'colvis' ],
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
// 			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // url: '../mock_data/SerialNumber.json',

                "url": getContextPath() + 'basedataSerialNumber/listByPage.do',
                "data": function (d) {
                    // alert( JSON.stringify($('#port-group-port_search-form').serializeObject()));
                    d.keys = JSON.stringify($('#serialNumberSearchForm').serializeObject());
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
                    "sClass": "text-center",
                    "data": "serialNumberId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,
                },

            // { title: "银行ID", data:"serialNumberId"},
            {title: "用途标识", data: "useLogo"},
            // {title: "办事处代码", data: "officeCodeId"},
            {title: "组织架构", data: "organizationStructureName"},
            {title: "序列号长度", data: "serialNumberLength"},
            {title: "序列号起始值", data: "serialNumberStart"},
            {title: "最新的序列号", data: "serialNumberLatest"},
            {title: "规则文本", data: "ruleText"},
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.amenderName;
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
                        else

                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' :
                                data;
                    },
                    targets: [1, 2, 3, 4, 5, 6, 7, 8]
                }
            ],
            buttons: [
                {
                    extend: 'excelHtml5',
                    exportOptions: {
                        rows: { selected: true },
                        columns: ':visible'
                    },
                    text:"选中行导出Excel",
                    container: '#serialNumber_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#serialNumber_export-excel-current'

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
                    container: '#serialNumber_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#serialNumber_export-print-all'
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
                    container: '#serialNumber_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#serialNumber_export-columnVisibility'
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
    $('body').on('click', '.serialNumber .checkall', function () {
        var check = $(this).prop("checked");
        $(".serialNumber .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#serialNumberTable tbody tr").each(function () {
            if ( serialNumber_table.row( this, { selected: true } ).any() ) {
                serialNumber_table.row( this ).deselect();
            }
            else {
                serialNumber_table.row( this ).select();
            }
        });
    });


//add
//     $("#addSerialNumber").on('click', function () {
  function addSerialNumber() {
        emptyAddForm();
        InitOrganizationZtree2(); // 初始化资源树

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editSerialNumberModalBody"), 'insert');

        $('#editSerialNumberModal').modal('show');//现实模态框

    }


//edict item
    function editSerialNumber() {
    // $("#editSerialNumber").click(function () {
        emptyAddForm();
        InitOrganizationZtree2(); // 初始化资源树
        var selectedRowData = serialNumber_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];
        //
        // //"departmentName": "海盟集团 || 贸易子公司 || 贸易代理部"，作处理截取最后一部分；
        var organizationStructureNameAttr = data.organizationStructureName.split("||");
        data.organizationStructureName = organizationStructureNameAttr[organizationStructureNameAttr.length - 1].trim();

        // 循环给表单赋值
        $.each($("#editSerialNumberForm input,#editSerialNumberForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });


        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editSerialNumberModalBody"), 'update');
        $("#editSerialNumberForm input[name='amendTime']").val(  data['amendTime']);

        $("#editSerialNumberForm input[name='useLogo']").attr({ readonly: 'true' });
        $("#editSerialNumberForm input[name='serialNumberStart']").attr({ readonly: 'true' });
        $("#editSerialNumberForm input[name='serialNumberLength']").attr({ readonly: 'true' });
        $("#editSerialNumberForm input[name='serialNumberStart']").attr({ readonly: 'true' });

        $('#editSerialNumberModal').modal('show');//现实模态框

    }

    function isExistUseLogo(useLogo){

      var  isExist = 'true';

        $.ajax({
            url: getContextPath() + 'basedataSerialNumber/isExistUseLogo.do',
            type: 'POST',
            data: {
                useLogo:useLogo
            },
            async: false,
            success: function (res) {
                isExist =  res;
            },
            error: function () {
                hideMask();
                alert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });

        return isExist;
    }

    // 弹出框提交按钮
    function submitEditSerialNumberModal() {
        // if(!validateSerialNumberForm()){
        //     alert("validate error!");
        //     return;
        // }
        var data = $("#editSerialNumberForm").serializeObject();

        var saveType = $("#editSerialNumberForm input[name='saveType']").val();

        //判断用途标识是否已存在
        if(saveType == 'insert' && isExistUseLogo(data.useLogo) == 'true'){

            callAlert("用途标识已存在！")
            return ;
        }

        $.ajax({
            url: getContextPath() + 'basedataSerialNumber/' + saveType + '.do',
            type: 'POST',
            data: data,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    callSuccess(res.message);

                    serialNumber_table.ajax.reload();
                }
                else

                    callAlert(res.message);

            },
            error: function () {
                hideMask();
                alert("保存失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
        $('#editSerialNumberModal').modal('hide');//现实模态框
    }


// delete item
//     $("#deleteSerialNumber").click(function () {
    function deleteSerialNumber() {
        var info;
        var selectedRowData = serialNumber_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'SerialNumber_confirmDelete');
        $('.SerialNumber_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.serialNumberId);
            });

            $.ajax({
                url: getContextPath() + 'basedataSerialNumber/delete.do',
                data: {
                    serialNumberIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        serialNumber_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });

    }

// 清空弹框
    function emptyAddForm() {
        $("#editSerialNumberForm")[0].reset();
        $("#editSerialNumberForm input[name='useLogo']").removeAttr("readonly"); //去除readonly属性
        $("#editSerialNumberForm input[name='serialNumberStart']").removeAttr("readonly"); //去除readonly属性
        $("#editSerialNumberForm input[name='serialNumberLength']").removeAttr("readonly"); //去除readonly属性
        $("#editSerialNumberForm input[name='serialNumberStart']").removeAttr("readonly"); //去除readonly属性

        $("label.error").remove();//清除提示语句
    };

//refesh SerialNumber_table
//     $("#refreshSerialNumber").click(function () {
    function doSearch() {
        // SerialNumber_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        // $("#editSerialNumberForm")[0].reset();
        serialNumber_table.ajax.reload();
    }

    $('#serialNumberTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(serialNumber_table.rows('.selected').data().length);
    });


// click item display detail infomation
    $('#serialNumberTable tbody').on('dblclick', 'tr', function () {
        var data = serialNumber_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showSerialNumberDetail').on('click', function () {
        var rows_data = serialNumber_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    });
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#serialNumberTable tbody tr',
            callback: function (key, options) {
                //var row_data = serialNumber_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addSerialNumber();
                        break;
                    case "Delete"://删除该节点
                        $("#serialNumberTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteSerialNumber();
                        break;
                    case "Edit"://编辑该节点
                        $("#serialNumberTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editSerialNumber();
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
        editSerialNumber: editSerialNumber,
        addSerialNumber:addSerialNumber,
        deleteSerialNumber:deleteSerialNumber,
        doSearch:doSearch
    };
})();




function InitOrganizationZtree2(){
    $.ajax({
        // url : "../mock_data/organizational-structure.json",
        url: getContextPath() + 'organization/listAll.do',
        type : 'POST',
        data : {},
        dataType:'json',
        async : false,
        success : function(data) {
            //初始化form弹框的资源树
            InitOrganizationStructurezTree("serialNumberOrganizationalStructureTreeResouce_search",data);
            InitOrganizationStructurezTree("serialNumberOrganizationalStructureTreeResouce",data);
        },
        error : function() {
            callAlert("获取数据失败!");
            // $.messager.alert('系统提示','申请失败,请重试！','warning');
        }
    });
};

// //jTable为jquery.dataTables表格对象
// //colNum为操作列的序号 为整形数字
//  jTable = SerialNumber_table;
// function hidColumn(jTable, colNum) {
//     var column = jTable.column(colNum);
//     column.visible( ! column.visible() );
// }
// $('a.toggle-vis').on( 'click', function (e) {
//     e.preventDefault();
//
//     // Get the column API object
//     var column = SerialNumber_table.column( $(this).attr('data-column') );
//
//     // Toggle the visibility
//     column.visible( ! column.visible() );
// } );
// //通过jquery 查找到相应每个li  并注册其点击事件
// //        $("#" + tableDivId + " .dropdown-menu").find("li").click(function (e) {
// $(".dropdown-menu").on('click','li',function (e) {
//     //由于列的隐藏与显示是由checkbox的true、false来控制的。所以点击时首先需要获取每行checkbox目前的选择状态
//     // var val = $(this).find(".hideColCheck").prop("checked");
//     // var val = $(this).find("input").prop("checked");
//     // if (val == true) {
//     //     //表示隐藏操作
//     //     hidColumn(jTable, $(this).attr('data-column'));
//     // } else {
//     //     //表示显示操作
//     //     hidColumn(jTable, $(this).attr('data-column'));
//     // }
//     e.stopPropagation();
//     // Get the column API object
//     var column = SerialNumber_table.column( $(this).attr('data-column') );
//
//     // Toggle the visibility
//     column.visible( ! column.visible() );
//     // hidColumn(jTable, $(this).attr('data-column'));
//     // //列操作后 需要将checkbox的值进行切换
//     $(this).find("input").prop("checked",! column.visible());
//     //阻止事件冒泡 目的是不使下拉列表隐藏，如果不加这句话就会每点击一行，下拉列表就隐藏咯
//
// })
// //        $("#" + tableDivId + " .dropdown-menu").find(".hideColCheck").click(function (e) {
// // $(".dropdown-menu").click(function (e) {
// //
// //     e.stopPropagation();
// //     var val = $(this).prop("checked");
// //     if (val == true) {
// //         //表示隐藏操作
// //         hidColumn(jTable, $(this).attr('data-column'));
// //     } else {
// //         //表示显示操作
// //         hidColumn(jTable, $(this).attr('data-column'));
// //     }
// // })
