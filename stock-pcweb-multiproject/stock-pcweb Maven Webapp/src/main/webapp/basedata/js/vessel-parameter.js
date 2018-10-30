//@ sourceURL=vessel-parameter.js
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
var vesselParameter = (function(){
$.validator.setDefaults({
    submitHandler:submitEditVesselParameterModal
});
$().ready(
    function validateVesselParameterForm() {
        Commonset_Validator= $("#editVesselParameterForm").validate({
            rules:
            {
                vesselParameterCode: {
                    required: true,
                    maxlength: 10
                },
                vesselParameterId: {
                    maxlength: 11,
                    digits:true
                },
                unit: {
                    maxlength: 10
                },
                vesselParameterName: {
                    maxlength: 50
                },
                vesselParameterNameCn: {
                    maxlength: 50
                },
                field: {
                    maxlength: 50
                },
                description: {
                    maxlength: 200
                }
            },
            messages: {
                vesselParameterCode: {
                    required: "这是必填字段",
                    maxlength: "请不要超过限制的10个字符数"
                },
                vesselParameterId: {
                    maxlength: "请不要超过限制的11个字符数",
                    digits:"请请输入数字"
                },
                unit: {
                    maxlength: "请不要超过限制的10个字符数"
                },
                vesselParameterName: {
                    maxlength: "请不要超过限制的50个字符数"
                },
                vesselParameterNameCn: {
                    maxlength: "请不要超过限制的50个字符数"
                },
                field: {
                    maxlength: "请不要超过限制的50个字符数"
                },
                description: {
                    maxlength: "请不要超过限制的200个字符数"
                }
            }
        });
        //return Commonset_Validator.form();
    }
);




var vesselParameterTable;
var paral={
    // "vesselParameterId":"船公司ID",
    "vesselParameterCode":"船公司代码",
    "vesselParameterName":" 船公司名称（EN）",
    "vesselParameterNameCn":"船公司名称（CN）",
    //"unit":"单位",
    "field":"网址",
    "description":"描述"
};

Init();
function Init() {
    vesselParameterTable =  $("#vesselParameterTable").DataTable( {
        // ajax: "../mock_data/objects_shipping-company.txt",

        // data: dataSet,
        fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
        fnDrawCallback:changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
        bProcessing : true,
        bServerSide : true,
        aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
        searching : false,// 禁用搜索
        lengthChange : true, // 是否启用改变每页显示多少条数据的控件
        /*
         * sort : "position",
         * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
         */
        deferRender : true,// 延迟渲染
        stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
        iDisplayLength : 20, // 默认每页显示多少条记录
        iDisplayStart : 0,
        ordering : false,// 全局禁用排序
        serverSide: true,
        autoWidth: true,
        // scrollX: true,
        colReorder: true,//列位置的拖动
        scrollY: calcDataTableHeight(),
        destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
        dom:'<"top">Brt<"bottom"flip><"clear">',
        // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
// 			 ajax: "../mock_data/user.txt",
        ajax : {
            "type" : "POST",
            // "url": "../mock_data/vessel-parameter.json",
            "url" : getContextPath()+'basedataVesselParameter/listByPage.do',
            "data": function(d){
// 					alert( JSON.stringify(value, replacer, space)ringify($('#port-group_search-table').serializeObject()));
                d.keys =  JSON.stringify($('#vesselParameterSearchForm').serializeObject());
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
                "data": "vesselParameterId",
                "title":"<input type='checkbox' class='checkall' />",
                "render": function (data, type, full, meta) {
                    return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                },
                "bSortable": false,

            },
            // { title: "船公司ID", data:"vesselParameterId"},
            { title: "船公司代码",data:"vesselParameterCode" },
            { title: "船公司名称（EN）",data:"vesselParameterName" },
            { title: "船公司名称（CN）",data:"vesselParameterNameCn" },
            //{ title: "单位",data:"unit"},
            { title: "网址",data:"field" },
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
                    else if  (type === 'display')
                        return type === 'display' && data.length > 30 ?
                            '<span title="'+data+'">'+data+'</span>' : data;
                    else if  (type === 'copy') {
                        var api = new $.fn.dataTable.Api(meta.settings);
                        data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                    }
                    return data;
                },
                targets: [1,2,3,4,5,6,7]
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
                container: '#vesselParameter_export-excel-selected'
            },
            {
                extend: 'excelHtml5',
                text:"当前页导出Excel",
                container: '#vesselParameter_export-excel-current'

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
                container: '#vesselParameter_export-copy'
            },
            {
                extend: 'print',
                text: '打印全部',
                container: '#vesselParameter_export-print-all'
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
                container: '#vesselParameter_export-print-selected'
            },
            {
                extend: 'colvis',
                text: '自定义列表头',
                container: '#vesselParameter_export-columnVisibility'
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
$('body').on('click' , '.shipping-company .checkall' , function(){
    var check = $(this).prop("checked");
    $(".shipping-company .checkchild").prop("checked", check);
    //通过调用datatables的select事件来触发选中
    $("#vesselParameterTable tbody tr").each(function () {
        if ( vesselParameterTable.row( this, { selected: true } ).any() ) {
            vesselParameterTable.row( this ).deselect();
        }
        else {
            vesselParameterTable.row( this ).select();
        }
    });

});

//add
// $("#addVesselParameter").on('click',function () {
function addVesselParameter() {

    emptyAddForm();

    //设置默认值amender ,amenderName,amendTime,saveType
    setDefaultValue($("#editVesselParameterModalBody"), 'insert');

    $('#editVesselParameterModal').modal('show');//现实模态框
}
// })
//edict item
    function editVesselParameter() {
// $("#editVesselParameter").click(function () {
    emptyAddForm();
    var selectedRowData = vesselParameterTable.rows('.selected').data();
    if(selectedRowData.length!=1){
        callAlert("请选择一条记录进行编辑！")
        return;
    }
    var data = selectedRowData[0];

    // 循环给表单赋值
    $.each($("#editVesselParameterForm input,#editVesselParameterForm select "),function(i, input) {

        $(this).val(data[$(this).attr("name")]);

    });

    //设置默认值amender ,amenderName,amendTime,saveType
    setDefaultValue( $("#editVesselParameterModalBody"),'update');

    $("#editVesselParameterForm input[name='amendTime']").val(  data['amendTime']);

    $('#editVesselParameterModal').modal('show');//现实模态框

// });
}
//确定增加或者保存编辑；
function submitEditVesselParameterModal() {
    //$("#editVesselParameterModalSubmit").click(function () {
        var data = $("#editVesselParameterForm").serializeObject();
        var saveType = $("#editVesselParameterForm input[name='saveType']").val();

        // 测试使用
        // vesselParameterTable.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataVesselParameter/' + saveType + '.do',
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

                    vesselParameterTable.ajax.reload();
                }
                else

                    callAlert(res.message);


            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
    $('#editVesselParameterModal').modal('hide');//现实模态
    //})
}
// delete item
function deleteVesselParameter() {
// $("#deleteVesselParameter").click(function () {
    var info;
    var selectedRowData = vesselParameterTable.rows('.selected').data();
    if (selectedRowData.length < 1) {
        info = "请选择要删除的数据";
        callAlert(info);
        return;
    }
    info = "确定要删除" + selectedRowData.length + "数据吗?";
    callAlertModal(info,'VesselParameter_confirmDelete');
    //确定删除
    $('.VesselParameter_confirmDelete').unbind('click').click(function () {
        var ids = [];
        $.each(selectedRowData, function () {

            ids.push(this.vesselParameterId);
        });

        $.ajax({
            url: getContextPath() + 'basedataVesselParameter/delete.do',
            data: {
                vesselParameterIds: ids.join(',')
            },
            dataType: 'json',
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (rsp) {
                hideMask();

                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    vesselParameterTable.ajax.reload();
                } else
                    callAlert(rsp.message)


            },
            error: function () {
                hideMask();
                callAlert("删除失败！")
            }
        });
    })
// })
}


//refesh table
//$("#refreshVesselParameter").click(function () {
    function doSearch() {
    // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
    vesselParameterTable.ajax.reload();
}

$('#vesselParameterTable tbody').on('click', 'tr', function () {
    // $(".selected").not(this).removeClass("selected");
    $(this).toggleClass("selected");
    var check = $(this).hasClass("selected")
    $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
    console.log( vesselParameterTable.rows('.selected').data().length);
} );


// click item display detail infomation
$('#vesselParameterTable tbody').on('dblclick', 'tr', function () {
    var  data = vesselParameterTable.rows($(this)).data()[0];
    $("#detail_table").html("");
    DisplayDetail(data,paral);
} );
$('#showVesselParameterDetail').on('click',function () {
    var rows_data = vesselParameterTable.rows('.selected').data();
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
    $("#editVesselParameterForm")[0].reset();
    $("label.error").remove();//清除提示语句
};
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#vesselParameterTable tbody tr',
            callback: function (key, options) {
                //var row_data = vesselParameterTable.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addVesselParameter();
                        break;
                    case "Delete"://删除该节点
                        $("#vesselParameterTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteVesselParameter();
                        break;
                    case "Edit"://编辑该节点
                        $("#vesselParameterTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editVesselParameter();
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
        editVesselParameter: editVesselParameter,
        addVesselParameter:addVesselParameter,
        deleteVesselParameter:deleteVesselParameter,
        doSearch:doSearch
    };
})();

