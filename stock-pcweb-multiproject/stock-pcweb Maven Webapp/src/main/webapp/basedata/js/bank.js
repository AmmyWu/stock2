//@ sourceURL=bank.js
// $(document).ready(function() {
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
// var Bank_Validator;
var Bank = (function(){
$.validator.setDefaults({
    submitHandler:submitEditBankModal
});

$().ready(
    function validateBankForm() {
        $("#editBankForm").validate({
            rules: {
                abbrev: {
                    maxlength:20
                },
                xchgDays: {
                    maxlength:9,
                    digits: true
                },
                amender: {
                    maxlength:11
                }
            },
            messages:
                {
                    abbrev: {
                        maxlength:"最多输入20个字符"
                    },
                    xchgDays: {
                        maxlength:"最多输入9位数字"
                    },
                    amender: {
                        maxlength:"最多输入11个字符"
                    }
                }
        });
    }
);
console.log("bank");
var bank_table;
var paral = {
    // "bankId": "银行ID",
    "abbrev": "银行名称缩写",
    "bankName": "银行名称",
    "addr": "银行地址",
    "xchgDays": "xchgDays",
    "bankNameNative": "bankNameNative",
    "description": "描述"
};
InitBank();
function InitBank() {
    bank_table = $("#bankTable").DataTable({
        // data: dataSet,
            bPaginate: true, //翻页功能
            bLengthChange: true, //改变每页显示数据数量
            paging: true,
            lengthChange: false,
            searching: true,
            ordering: true,
            info: true,
            autoWidth: true,
            select:true,

            scrollX: true,
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
           ajax:{
                url: 'basedata/mock_data/bank.json'
               //"url":"../mock_data/objects_public.txt",
                 //"url":"/stockWebappBase/basedataBank/listByPage.do?rows=10&page=1",
//                "data":{}
                },
        // dom:"Bfrtip",
        // fnRowCallback: function(nRow) {
        //     alert("fnRowCallback");
        //     $(nRow).on('mousedown', function (e) {
        //         if (e.button == 2) {
        //             console.log('Right mouse button!');
        //             return false;
        //         }
        //         return true;
        //     })
        // },
        colReorder: true,//列位置的拖动
        rowReorder: {
            selector: 'tr'
        },
        // fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
        // fnDrawCallback:changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
//         bProcessing: true,
//         bServerSide: true,
//         aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
//         searching: false,// 禁用搜索
//         lengthChange: true, // 是否启用改变每页显示多少条数据的控件
//         /*
//          * sort : "position",
//          * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
//          */
//         deferRender: true,// 延迟渲染
//         bStateSave: false, // 在第三页刷新页面，会自动到第一页
//         iDisplayLength: 10, // 默认每页显示多少条记录
//         iDisplayStart: 0,
//         ordering: false,// 全局禁用排序
//         serverSide: true,
//         scrollCollapse:true,
//         scrollX: true,
//         autoWidth: true,
//         scrollY: "360px",
//         colReorder: true,//列位置的拖动
//         // buttons: [ 'colvis' ],
//         // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
// // 			 ajax: "../mock_data/user.txt",
//         ajax: {
//             "type": "POST",
//             // url: '../mock_data/bank.json',
//
//             "url": getContextPath() + 'basedataBank/listByPage.do',
//             "data": function (d) {
//                 // alert( JSON.stringify($('#port-group-port_search-form').serializeObject()));
//                 d.keys = JSON.stringify($('#bankSearchForm').serializeObject());
//             }
//         },
        language: {
            //"url": "../../js/Chinese.json"
            "url": "js/Chinese.json"
        },
        columns: [
            {
                "sClass": "text-center",
                "data": "bankId",
                "title": "<input type='checkbox' class='checkall' />",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                },
                "bSortable": false,

            },
            // { title: "银行ID", data:"bankId"},
            {title: "银行名称缩写", data: "abbrev",},
            {title: "银行名称", data: "bankName"},
            {title: "银行地址", data: "addr"},
            {title: "xchgDays", data: "xchgDays"},
            {title: "bankNameNative", data: "bankNameNative"},
            {title: "描述", data: "description"},
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
                    else

                        return type === 'display' && data.length > 30 ?
                            '<span title="' + data + '">' + data + '</span>' :
                            data;
                },
                targets: [1, 2, 3, 4, 5, 6, 7]
            }
        ],
    });
    // new $.fn.dataTable.Buttons( bank_table, {
    //     buttons: [
    //         {
    //             extend: 'excel',
    //             text:"excel"
    //             // className: 'btn btn-info'
    //             // columns: ':gt(0)'
    //         }
    //     ]
    // } );
}



// select/not select all
$('body').on('click', '.bank .checkall', function () {
    var check = $(this).prop("checked");
    $(".bank .checkchild").prop("checked", check);
    if (check) {
        $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
    }
    else {
        $("tr").removeClass("selected");
    }

});


//add
// $("#addBank").on('click', function () {
function addBank() {
    $('#editBankForm')[0].reset();


    //设置默认值amender ,amenderName,amendTime,saveType
    setDefaultValue($("#editBankModalBody"), 'insert');

    $('#editBankModal').modal('show');//现实模态框
}

// })


//edict item
// $("#editBank").click(function () {
    function editBank() {


        emptyAddForm();
        var selectedRowData = bank_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editBankForm input,#editBankForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });


        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editBankModalBody"), 'update');
        $("#editBankForm input[name='amendTime']").val(  data['amendTime']);
        $('#editBankModal').modal('show');//现实模态框

// });
    }
  // 弹出框提交按钮
function submitEditBankModal() {
    // if(!validateBankForm()){
    //     alert("validate error!");
    //     return;
    // }
    var data = $("#editBankForm").serializeObject();

     var saveType = $("#editBankForm input[name='saveType']").val();

    $.ajax({
            url: getContextPath() + 'basedataBank/' + saveType + '.do',
            type: 'POST',
            data:data,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();
                if(res.code ==0){
                    callSuccess(res.message);

                    bank_table.ajax.reload();
                }
                else

                    callAlert(res.message);

            },
            error: function () {
                hideMask();
                alert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
    $('#editBankModal').modal('hide');//现实模态框
    }


// delete item
//$("#deleteBank").click(function () {
    function deleteBank() {


        var ids = [];
        var info;
        var selectedRowData = bank_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'Bank_confirmDelete');
        $('.Bank_confirmDelete').click(function () {
            $.each(selectedRowData, function () {

                ids.push(this.bankId);
            });

            $.ajax({
                url: getContextPath() + 'basedataBank/delete.do',
                data: {
                    bankIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        bank_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    callAlert("删除失败！")
                }
            });
        });
    }
// });

// 清空弹框
function emptyAddForm() {
    $("#editBankForm")[0].reset();
    $("label.error").remove();//清除提示语句
};

//refesh bank_table
// $("#refreshBank").click(function () {
//     // bank_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
//     // $("#editBankForm")[0].reset();
//     bank_table.ajax.reload();
// })

$('#bankTable tbody').on('click', 'tr', function () {
    // $(".selected").not(this).removeClass("selected");
    $(this).toggleClass("selected");
    var check = $(this).hasClass("selected")
    $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
    console.log(bank_table.rows('.selected').data().length);
});


// click item display detail infomation
$('#bankTable tbody').on('dblclick', 'tr', function () {
    var data = bank_table.rows($(this)).data()[0];
    $("#detail_table").html("");
    DisplayDetail(data, paral);
});
$('#showBankDetail').on('click', function () {
    var rows_data = bank_table.rows('.selected').data();
    if (rows_data.length < 1) {
        callAlert("请选择一条数据进行查看");
        return;
    }
    for (var i = 0; i < rows_data.length; i++) {
        $("#detail_table").html("");
        DisplayDetail(rows_data[i], paral);
    }

});
function doSearch(){

    bank_table.ajax.reload();

}
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#bankTable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                options.$trigger.click();//选中该行selected
                switch (key) {
                    case "Add"://增加一条数据
                        addBank();
                        break;
                    case "Delete"://删除该节点
                        $("#bankTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteBank();
                        break;
                    case "Edit"://编辑该节点
                        $("#bankTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editBank();
                        break;
                    default:
                        options.$trigger.removeClass("selected");//取消选择selected
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
        editBank: editBank,
        addBank:addBank,
        deleteBank:deleteBank,
        doSearch:doSearch
    };
})();
// //jTable为jquery.dataTables表格对象
// //colNum为操作列的序号 为整形数字
//  jTable = bank_table;
// function hidColumn(jTable, colNum) {
//     var column = jTable.column(colNum);
//     column.visible( ! column.visible() );
// }
// $('a.toggle-vis').on( 'click', function (e) {
//     e.preventDefault();
//
//     // Get the column API object
//     var column = bank_table.column( $(this).attr('data-column') );
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
//     var column = bank_table.column( $(this).attr('data-column') );
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
