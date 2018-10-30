//@ sourceURL=CommonUsedWords.js
// $(document).ready(function() {
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
// var CommonUsedWords_Validator;
var commonUsedWords = (function() {
$(function () {
    //Initialize Select2 Elements，初始化银行下拉框架
    $(".select2").select2();

    // //初始化查询form的港口下拉列表
    initSelect2FromDB("editCommonUsedWordsForm","vesselParameterId","basedataVesselParameter/listByKeys.do","{}","vesselParameterId","vesselParameterName");

    //初始化编辑form的港口下拉列表
    // initSelect2("editWharfForm","portId","basedataPort/listByKeys.do","{}","portId","portName");

    //解决select2 在弹出框中不能搜索的问题
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
});



$.validator.setDefaults({
    submitHandler:submitEditCommonUsedWordsModal
});

$().ready(
    function validateCommonUsedWordsForm() {
        $("#editCommonUsedWordsForm").validate({
            rules: {
                codeType: {
                    maxlength:80
                },
                codeWidth: {
                    maxlength:11,
                    digits: true
                },
                codeName: {
                    maxlength:80,
                },
                relatedType: {
                    maxlength:80,
                },
                description: {
                    maxlength:200
                }
            },
            messages:
                {
                    codeType: {
                        maxlength:"最多输入80个字符"
                    },
                    codeWidth: {
                        maxlength:"最多输入11位数字"
                    },
                    codeName: {
                        maxlength:"最多输入80个字符"
                    },
                    relatedType: {
                        maxlength:"最多输入80个字符"
                    },
                    description: {
                        maxlength:"最多输入200个字符"
                    }
                }
        });
    }
);
console.log("commonUsedWords");
var commonUsedWords_table;
var paral = {
    // "commonUsedWordsId": "银行ID",
    "codeType": "代码类型",
    "codeWidth": "编码宽度",
    "codeName": "编码名称",
    "relatedType": "相关类型",
    "description": "编码描述"
};
InitCommonUsedWords();
function InitCommonUsedWords() {
    commonUsedWords_table = $("#commonUsedWordsTable").DataTable({
        fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
        fnDrawCallback:changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
        iDisplayLength: 10, // 默认每页显示多少条记录
        iDisplayStart: 0,
        ordering: false,// 全局禁用排序
        serverSide: true,
        scrollCollapse:true,
        scrollX: true,
        autoWidth: true,
        colReorder: true,//列位置的拖动
        destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
        dom:'<"top">rt<"bottom"flip><"clear">',
        // buttons: [ 'colvis' ],
        // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
// 			 ajax: "../mock_data/user.txt",
        ajax: {
            "type": "POST",
            // url: '../mock_data/CommonUsedWords.json',

            "url": getContextPath() + 'basedataCommonUsedWords/listByPage.do',
            "data": function (d) {
                // alert( JSON.stringify($('#port-group-port_search-form').serializeObject()));
                d.keys = JSON.stringify($('#commonUsedWordsSearchForm').serializeObject());
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
                "data": "commonUsedWordsId",
                "title": "<input type='checkbox' class='checkall' />",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                },
                "bSortable": false,

            },

            {title: "代码类型", data: "codeType"},
            {title: "编码宽度", data: "codeWidth"},
            {title: "编码名称", data: "codeName"},
            {title: "相关类型", data: "relatedType"},
            {title: "编码描述", data: "description"},
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



// select/not select all
$('body').on('click', '.commonUsedWords .checkall', function () {
    var check = $(this).prop("checked");
    $(".commonUsedWords .checkchild").prop("checked", check);
    //通过调用datatables的select事件来触发选中
    $("#commonUsedWordsTable tbody tr").each(function () {
        if ( commonUsedWords_table.row( this, { selected: true } ).any() ) {
            commonUsedWords_table.row( this ).deselect();
        }
        else {
            commonUsedWords_table.row( this ).select();
        }
    });

});


//add
    function addCommonUsedWords() {
// $("#addCommonUsedWords").on('click', function () {

        $('#editCommonUsedWordsForm')[0].reset();


        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editCommonUsedWordsModalBody"), 'insert');

        $('#editCommonUsedWordsModal').modal('show');//现实模态框
    }
// })


//edict item
    function editCommonUsedWords() {
// $("#editCommonUsedWords").click(function () {
        emptyAddForm();
        var selectedRowData = commonUsedWords_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editCommonUsedWordsForm input,#editCommonUsedWordsForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editCommonUsedWordsModalBody"), 'update');
        $("#editCommonUsedWordsForm input[name='amendTime']").val(  data['amendTime']);
        $('#editCommonUsedWordsModal').modal('show');//现实模态框

//
    }

  // 弹出框提交按钮
function submitEditCommonUsedWordsModal() {
    // if(!validateCommonUsedWordsForm()){
    //     alert("validate error!");
    //     return;
    // }
    var data = $("#editCommonUsedWordsForm").serializeObject();

     var saveType = $("#editCommonUsedWordsForm input[name='saveType']").val();

    $.ajax({
            url: getContextPath() + 'basedataCommonUsedWords/' + saveType + '.do',
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

                    commonUsedWords_table.ajax.reload();
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
    $('#editCommonUsedWordsModal').modal('hide');//现实模态框
    }


// delete item
    function deleteCommonUsedWords() {
// $("#deleteCommonUsedWords").click(function () {
        var ids = [];
        var info;
        var selectedRowData = commonUsedWords_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'CommonUsedWords_confirmDelete');
        $('.CommonUsedWords_confirmDelete').click(function () {
            $.each(selectedRowData, function () {

                ids.push(this.commonUsedWordsId);
            });

            $.ajax({
                url: getContextPath() + 'basedataCommonUsedWords/delete.do',
                data: {
                    commonUsedWordsIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        commonUsedWords_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    hideMask();

                    callAlert("删除失败！")
                }
            });
        });

// });
    }

// 清空弹框
function emptyAddForm() {
    $("#editCommonUsedWordsForm")[0].reset();
    $("label.error").remove();//清除提示语句
};

//refesh CommonUsedWords_table
// $("#refreshCommonUsedWords").click(function () {
function doSearch() {
    // CommonUsedWords_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
    // $("#editCommonUsedWordsForm")[0].reset();
    commonUsedWords_table.ajax.reload();
}

$('#commonUsedWordsTable tbody').on('click', 'tr', function () {
    // $(".selected").not(this).removeClass("selected");
    $(this).toggleClass("selected");
    var check = $(this).hasClass("selected")
    $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
    console.log(commonUsedWords_table.rows('.selected').data().length);
});


// click item display detail infomation
$('#commonUsedWordsTable tbody').on('dblclick', 'tr', function () {
    var data = commonUsedWords_table.rows($(this)).data()[0];
    $("#detail_table").html("");
    DisplayDetail(data, paral);
});
$('#showCommonUsedWordsDetail').on('click', function () {
    var rows_data = commonUsedWords_table.rows('.selected').data();
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
            selector: '#commonUsedWordsTable tbody tr',
            callback: function (key, options) {
                //var row_data = commonUsedWords_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据 ()
                        addCommonUsedWords();
                        break;
                    case "Delete"://删除该节点
                        $("#commonUsedWordsTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteCommonUsedWords();
                        break;
                    case "Edit"://编辑该节点
                        $("#commonUsedWordsTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editCommonUsedWords();
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
        editCommonUsedWords: editCommonUsedWords,
        addCommonUsedWords:addCommonUsedWords,
        deleteCommonUsedWords:deleteCommonUsedWords,
        doSearch:doSearch
    };
    
})();
// //jTable为jquery.dataTables表格对象
// //colNum为操作列的序号 为整形数字
//  jTable = CommonUsedWords_table;
// function hidColumn(jTable, colNum) {
//     var column = jTable.column(colNum);
//     column.visible( ! column.visible() );
// }
// $('a.toggle-vis').on( 'click', function (e) {
//     e.preventDefault();
//
//     // Get the column API object
//     var column = CommonUsedWords_table.column( $(this).attr('data-column') );
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
//     var column = CommonUsedWords_table.column( $(this).attr('data-column') );
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
