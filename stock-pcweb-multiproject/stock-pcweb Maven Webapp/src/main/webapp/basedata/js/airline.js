//@ sourceURL=airline.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var airline = (function(){
 $.validator.setDefaults({
     submitHandler:submitEditAirlineModal

 });


 $(function () {
     //Initialize Select2 Elements，初始化银行下拉框架
     $(".select2").select2();

     initSelect2();

     //解决select2 在弹出框中不能搜索的问题
     $.fn.modal.Constructor.prototype.enforceFocus = function () { };
 });

 function initSelect2(){

     //初始化查询form的区域、国家，航线下拉框  superiorId2为国家
     initSelect2FromRedis("searchAirlineForm","countryId","redisController/listIdNameByName.do?name=basedataCommonSet_2","{}","commonSetId","cnName");

     //初始化编辑form的区域、国家，航线下拉框
     initSelect2FromRedis("editAirlineForm","countryId","redisController/listIdNameByName.do?name=basedataCommonSet_2","{}","commonSetId","cnName");

 }

 $().ready(
     function validateAirlineForm() {
         $("#editAirlineForm").validate({
             rules: {
                 airlineNameCn: {
                     required:true,
                 },
                 iataDesignator: {
                     required:true,
                 }
             },
             messages:
         {

             airlineNameCn: {
                 required:"必填项"
             },
             airlineiataDesignatorCode: {
                 required:"必填项"
             }
         }
     });
     }
 );
    var airline_tb;
    var paral={
        // "airlineId":"港口Id",
        "airlineNameCn":"航空公司中文名称",
        "airlineName":"航空公司英文名称（EN）",
        "iataDesignator":"IATA代码",
        "icaoDesignator":"国际民航代码",
        "countryName":"所在地"
    };

    Init();
    function Init() {

        airline_tb =  $("#airline_table").DataTable( {
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
            scrollX: false,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax : {
                "type" : "POST",
                 // "url": "../mock_data/airline.json",
                "url" : getContextPath()+'basedataAirline/listByPage.do',
                "data": function(d){
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys =  JSON.stringify($('#searchAirlineForm').serializeObject());
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
                    "data": "airlineId",
                    "title":"<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';

                    },
                    "Sortable": false,

                },
                { title: "航空公司中文名称",data:"airlineNameCn" },
                { title: "航空公司英文名称（EN）",data:"airlineName" },
                { title: "IATA代码",data:"iataDesignator" },
                { title: "国际民航代码", data:"icaoDesignator"},
                { title: "所在地",data:"countryName"},
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '': data.amenderName;
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
                    container: '#airline_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#airline_export-excel-current'

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
                    container: '#airline_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#airline_export-print-all'
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
                    container: '#airline_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#airline_export-columnVisibility'
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
    $('body').on('click' , '.airline .checkall' , function(){
        var check = $(this).prop("checked");
        $(".airline .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#airline_table tbody tr").each(function () {
            if ( airline_tb.row( this, { selected: true } ).any() ) {
                airline_tb.row( this ).deselect();
            }
            else {
                airline_tb.row( this ).select();
            }
        });

    });

    //add
    function addAirline() {
        // $("#addAirline").on('click',function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editAirlineForm"), 'insert');

        //设置国家默认值为空
        emptyFormSelect2Value("editAirlineForm", ["countryId"]);

        $('#editAirlineModal').modal('show');//现实模态框
        // })
    }
    //edict item
    function editAirline() {
        // $("#editAirline").click(function () {
        emptyAddForm();
        var selectedRowData = airline_tb.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editAirlineForm input,#editAirlineForm select "), function (i, input) {
            $(this).val(data[$(this).attr("name")]);
        });

        //设置国家默认值为空
        setFormSelect2Value("editAirlineForm", ["countryId"],
            [data["countryId"]]);

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editAirlineModalBody"), 'update');
        $("#editAirlineForm input[name='amendTime']").val(  data['amendTime']);
        $('#editAirlineModal').modal('show');//现实模态框

        // })
    }
    //确定增加或者保存编辑；submitEditAirlineModal
     function submitEditAirlineModal() {
        // if(!validateAirlineForm()){
        //     // alert("validate error!");
        //     return;
        // }
            var data = $("#editAirlineForm").serializeObject();
            console.log(data);
            var saveType = $("#editAirlineForm input[name='saveType']").val();

            // 测试使用
            // airline_tb.row.add(data).draw();//插入一行
            // callSuccess("保存成功");

            $.ajax({
                type: 'POST',
                url: getContextPath() + '/basedataAirline/'+saveType+'.do',
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

                         airline_tb.ajax.reload();
                     }
                     else

                         callAlert(res.message);
                	 
                	
                },
                error: function () {
                    hideMask();
                    callAlert("增加失败");
                }
            });
        $('#editAirlineModal').modal('hide');//现实模态框
        }


    // delete item
    // $("#deleteAirline").click(function () {
    function deleteAirline() {
        var info;
        var selectedRowData = airline_tb.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,"Airline_confirmDelete");

        //确定删除
        $('.Airline_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {
                ids.push(this.airlineId);
            });

            $.ajax({
                url: getContextPath() + 'basedataAirline/delete.do',
                data: {
                    airlineIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);

                        airline_tb.ajax.reload();
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
    //$("#refreshAirline").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        airline_tb.ajax.reload();
    }

    $('#airline_table tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log( airline_tb.rows('.selected').data().length);
    } );


    // click item display detail infomation
    $('#airline_table tbody').on('dblclick', 'tr', function () {
        var  data = airline_tb.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data,paral);
    } );
    // $('#showAirlineDetail').on('click',function () {
    //     var rows_data = airline_tb.rows('.selected').data();
    //     if(rows_data.length<1){
    //         callAlert("请选择一条数据进行查看");
    //         return;
    //     }
    //     for (var i=0;i<rows_data.length;i++){
    //         $("#detail_table").html("");
    //         DisplayDetail(rows_data[i],paral);
    //     }
    //
    // })

 //重置查询条件
 $("#resetSeachAirlineForm").click( function() {


     $("#searchAirlineForm")[0].reset();

     //设置国家和航线默认值为空
     emptyFormSelect2Value("searchAirlineForm",["countryId"]);

 })

    // 清空弹框
    function emptyAddForm() {
        $("#editAirlineForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        $.contextMenu({
            selector: '#airline_table tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addAirline();
                        break;
                    case "Delete"://删除
                        $("#airline_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteAirline();
                        break;
                    case "Edit"://编辑该节点
                        $("#airline_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editAirline();
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
        editAirline: editAirline,
        addAirline:addAirline,
        deleteAirline:deleteAirline,
        doSearch:doSearch
    };

})();
