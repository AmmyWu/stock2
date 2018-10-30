//Datatables跳转到指定页面
function changePage() {
    var tableId = $(this)[0].id;
    $("#"+tableId+"_wrapper .pagination").append("<span style='margin-left: 10px;'>到第 <input type='number'" + " id='"+tableId+"changePage' class='changePage input-text'" + "> 页</span> <a" + " class='btn btn-default' id='"+tableId+"dataTable-btn' style='text-align:center'>确认</a>");
    var oTable = $("#"+tableId).dataTable();

    $('#'+tableId+'dataTable-btn').click(function(e) {
        if($('#'+tableId+"changePage").val() && $('#'+tableId+"changePage").val() > 0) {
            var redirectpage = $('#'+tableId+"changePage").val() - 1;
        } else {
            var redirectpage = 0;
        }
        oTable.fnPageChange(redirectpage);
    });
}

//用于重定为datatbles的摁钮的位置，使用方法：
// buttons: [
//     {
//         extend: 'excelHtml5',
//         exportOptions: {rows: { selected: true }},
//         text:"选中行导出Excel",
//         container: '#export-excel-selected'
//其中container字段中的元素是在页面中存在的，摁钮将要放置的位置
var org_buildButton = $.fn.DataTable.Buttons.prototype._buildButton;
$.fn.DataTable.Buttons.prototype._buildButton = function(config, collectionButton) {
    var button = org_buildButton.apply(this, arguments);
    $(document).one('init.dt', function(e, settings, json) {
        if (config.container && $(config.container).length) {
            $(button.inserter[0]).detach().appendTo(config.container)
        }
    });
    return button;
};

//解决Tab切换页面datatable宽度自动调整问题
$('body').on('shown.bs.tab', function (e) {
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
});
//解决Tab datatable宽度自动调整问题
$('body').on('shown.bs.modal', function (e) {
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
});
$('body').on('click','button', function (e) {
    $.fn.dataTable.tables( {visible: true, api: true} ).columns.adjust();
});

/*dataTables的行内增加的方法的实现*/
//针对第一个td为checkbox
// tableId table的Id,  data行数据
//data为对象，key值为字段的name，value为type：data{itemNo:input,manualNo:input,finalDestinationCountry:select}
//data如果类型为select默认采用select2并且<select></select>的class为字段对于的key
//如果序列号则hasNum传值true
function  AddRowInline(tableId,rowId,data,hasNum) {
    var row = $('<tr role="row"></tr>');
    if(rowId!=""||rowId!=null){
        row.attr("id",rowId);
    }
    var td='<td class=" text-center"><input type="checkbox" class="checkchild"/>';
    row.append(td);
    if(hasNum){
        td='<td class=" text-center"></td>';
        row.append(td);
    }

    for(var key in data){
        var name = key; var elemType=data[key];
        if(elemType.trim()=="input"){
            td = '<td><'+elemType+' name='+name+' class='+name+ ' //>'+'</td>';
        }
        else if(elemType.trim()=="select"){
            // td = '<td><'+elemType+' name='+name+' class= "select2 select2-hidden-accessible '+name+'" tabindex="-1"' +
            //     ' aria-hidden="true" style="width:100%"><option value=""></option></'+elemType+'></td>';

            td = '<td><'+elemType+' name="'+name+'" class="select2 select2-hidden-accessible" tabindex="-1" aria-hidden="true">'+
                '<option value=""></option> </'+elemType+'></td>';
        }

        row.append(td);
    }
    $('#'+tableId+' tbody').prepend(row);
    $(".select2").select2();
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
}
// rowId 为新增行ID
function getRowData(rowId) {
    var form = "<form id='addRowinlineForm'></form>"
    $("body").append(form);
    $("#"+rowId+" input,#"+rowId+" select").each(function(){
        $("#addRowinlineForm").append($(this));
    });
    var data =  $("#addRowinlineForm").serializeObject();
    return data;
}
