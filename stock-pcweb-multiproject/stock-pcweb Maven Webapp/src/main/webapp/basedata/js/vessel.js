//@ sourceURL=Vessel.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var Vessel = (function(){
 $.validator.setDefaults({
     submitHandler:submitEditVesselModal

 });

$(function () {
    //Initialize Select2 Elements，初始化银行下拉框架
    $(".select2").select2();

    // //初始化查询form的港口下拉列表
    initSelect2FromRedis("searchVesselForm", "vesselParameterId", "redisController/listIdNameByName.do?name=basedataVesselParameter", "{}", "vesselParameterId", "vesselParameterName");

    //初始化编辑form的港口下拉列表
    initSelect2FromRedis("editVesselForm", "vesselParameterId", "redisController/listIdNameByName.do?name=basedataVesselParameter", "{}", "vesselParameterId", "vesselParameterName");

    // initSelect2FromDB("editWharfForm","portId","basedataPort/listByKeys.do","{}","portId","portName");

    //解决select2 在弹出框中不能搜索的问题
    $.fn.modal.Constructor.prototype.enforceFocus = function () { };
});

 $().ready(
     function validateVesselForm() {
         $("#editVesselForm").validate({
             rules: {
                 // vesselParameterId: {
                 //     //required:true,
                 //     maxlength:11,
                 //     digits:true
                 // },
                 vesselCode: {
                     maxlength:10
                 },
                 vesselName: {
                     maxlength:50
                 },
                 vesselNameCn: {
                     maxlength:50
                 }
             },
             messages:
                 {
                     // vesselParameterId: {
                     //     maxlength:"最多输入11个字符",
                     //     digits:"请输入数字"
                     // },
                     vesselCode: {
                         maxlength:"最多输入10个字符"
                     },
                     vesselName: {
                         maxlength:"最多输入50个字符"
                     },
                     vesselNameCn: {
                         maxlength:"最多输入50个字符"
                     }
                 }
         });
     }
 );
    var Vessel_table;
    var paral={
        "vesselCode":"船舶代码",
        "vesselNameCn":"船名(中文)",
        "vesselName":"船名(英文)",
        "vesselParameterId":"船公司"
    };

    Init();
    function Init() {

        Vessel_table =  $("#Vessel_table").DataTable( {
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback:changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength : 20, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            serverSide: true,
            autoWidth: true,
            destroy: true,
            // scrollX: true,
            scrollY: calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax : {
                "type" : "POST",
                 // "url": "../mock_data/Vessel.json",
                "url" : getContextPath()+'basedataVessel/listByPage.do',
                "data": function(d){
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys =  JSON.stringify($('#searchVesselForm').serializeObject());
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
                    "Class": "text-center",
                    "data": "vesselId",
                    "title":"<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';

                    },
                    "Sortable": false,

                },
                // { title: "港口", data:"vesselId"},
                { title: "船舶代码",data:"vesselCode" },
                { title: "船名(中文)",data:"vesselNameCn" },
                { title: "船名(英文)",data:"vesselName" },
                { title: "船公司", data:"vesselParameterId"},
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
                    targets: [1,2,3,4,5,6]
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
                    container: '#Vessel_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#Vessel_export-excel-current'

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
                    container: '#Vessel_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#Vessel_export-print-all'
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
                    container: '#Vessel_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#Vessel_export-columnVisibility'
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
    $('body').on('click' , '.Vessel .checkall' , function(){
        var check = $(this).prop("checked");
        $(".Vessel .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#Vessel_table tbody tr").each(function () {
            if ( Vessel_table.row( this, { selected: true } ).any() ) {
                Vessel_table.row( this ).deselect();
            }
            else {
                Vessel_table.row( this ).select();
            }
        });

    });

    //add
    function addVessel() {
    // $("#addVessel").on('click',function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editVesselForm"), 'insert');

        $('#editVesselModal').modal('show');//现实模态框
        // })
    }
    //edict item
    function editVessel() {
        // $("#editVessel").click(function () {
        emptyAddForm();
        var selectedRowData = Vessel_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editVesselForm input,#editVesselForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editVesselModalBody"), 'update');
        $("#editVesselForm input[name='amendTime']").val(  data['amendTime']);

        //设置港口的值
        setSelect2Value("editVesselForm", "vesselParameterId", data["vesselParameterId"]);

        $('#editVesselModal').modal('show');//现实模态框

        // })
    }
    //确定增加或者保存编辑；submitEditVesselModal
     function submitEditVesselModal() {
        // if(!validateVesselForm()){
        //     // alert("validate error!");
        //     return;
        // }
            var data = $("#editVesselForm").serializeObject();
            var saveType = $("#editVesselForm input[name='saveType']").val();

            // 测试使用
            // Vessel_table.row.add(data).draw();//插入一行
            // callSuccess("保存成功");

            $.ajax({
                type: 'POST',
                url: getContextPath() + '/basedataVessel/'+saveType+'.do',
                data: data,
                cache: false,
                dataType: "json",
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (res) {
                    hideMask();
                	
                	 if(res.code ==0){
                         callSuccess(res.message);

                         Vessel_table.ajax.reload();
                     }
                     else

                         callAlert(res.message);
                	 
                	
                },
                error: function () {
                    hideMask();
                    callAlert("增加失败");
                }
            });
        $('#editVesselModal').modal('hide');//现实模态框
        }


    // delete item
    function deleteVessel() {
    // $("#deleteVessel").click(function () {
        var info;
        var selectedRowData = Vessel_table.rows('.selected').data();
        if(selectedRowData.length<1){
            info="请选择需要删除的数据！";
            callAlert(info);
            return;
        }

        info="确定要删除"+selectedRowData.length+"条数据吗?";
        callAlertModal(info,'Vessel_confirmDelete');


        //确定删除
        $('.Vessel_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.vesselId);
            });

            $.ajax({
                url: getContextPath() + 'basedataVessel/delete.do',
                data: {
                    vesselIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if(rsp.code ==0){
                        callSuccess(rsp.message);

                        Vessel_table.ajax.reload();
                    }
                    else
                        callAlert(rsp.message);
                },
                error: function (res) {
                    hideMask();
                    alert(res.code);
                    callAlert("修改失败！")
                }
            });
        });

    // });
    }

    //refesh table
   // $("#refreshVessel").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        Vessel_table.ajax.reload();
    }

    $('#Vessel_table tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log( Vessel_table.rows('.selected').data().length);
    } );


    // click item display detail infomation
    $('#Vessel_table tbody').on('dblclick', 'tr', function () {
        var  data = Vessel_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data,paral);
    } );
    $('#showVesselDetail').on('click',function () {
        var rows_data = Vessel_table.rows('.selected').data();
        if(rows_data.length<1){
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i=0;i<rows_data.length;i++){
            $("#detail_table").html("");
            DisplayDetail(rows_data[i],paral);
        }

    })




 //重置查询条件
 $("#resetSeachVesselForm").click( function() {


     $("#searchVesselForm")[0].reset();


     //设置船公司默认值为空
     emptySelect2Value("searchVesselForm","vesselParameterId");
 })


    // 清空弹框
    function emptyAddForm() {
        $("#editVesselForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#Vessel_table tbody tr',
            callback: function (key, options) {
                //var row_data = Vessel_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addVessel();
                        break;
                    case "Delete"://删除该节点
                        $("#Vessel_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteVessel();
                        break;
                    case "Edit"://编辑该节点
                        $("#Vessel_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editVessel();
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
        editVessel: editVessel,
        addVessel:addVessel,
        deleteVessel:deleteVessel,
        doSearch:doSearch
    };

})();
