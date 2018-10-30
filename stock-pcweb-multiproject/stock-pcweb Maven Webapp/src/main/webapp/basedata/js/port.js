//@ sourceURL=port.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var Port = (function(){
 $.validator.setDefaults({
     submitHandler:submitEditPortModal

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
     initSelect2FromRedis("searchPortForm","countryCommonSetId","redisController/listIdNameByName.do?name=basedataCommonSet_2","{}","commonSetId","cnName");
     initSelect2FromRedis("searchPortForm","areaCommonSetId","redisController/listIdNameByName.do?name=basedataCommonSet_1","{}","commonSetId","cnName");
     initSelect2FromRedis("searchPortForm","serviceLineId","redisController/listIdNameByName.do?name=basedataServiceLine","{}","serviceLineId","serviceLineName");

     //初始化编辑form的区域、国家，航线下拉框
     initSelect2FromRedis("editPortForm","countryCommonSetId","redisController/listIdNameByName.do?name=basedataCommonSet_2","{}","commonSetId","cnName");
     initSelect2FromRedis("editPortForm","areaCommonSetId","redisController/listIdNameByName.do?name=basedataCommonSet_1","{}","commonSetId","cnName");
     initSelect2FromRedis("editPortForm","serviceLineId","redisController/listIdNameByName.do?name=basedataServiceLine","{}","serviceLineId","serviceLineName");

 }

 $().ready(
     function validatePortForm() {
         $("#editPortForm").validate({
             rules: {
                 portType: {
                     required:true,
                     maxlength:5
                 },
                 portCode: {
                     required:true,
                     maxlength:5
                 },
                 port3code: {
                     maxlength:20
                 },
                 areaCommonSetId: {
                     maxlength:11,
                     digits:true
                 },
                 serviceLineId: {
                     maxlength:11,
                     digits:true
                 },
                 countryCommonSetId: {
                     maxlength:11,
                     digits:true
                 },
                 zipcode: {
                     maxlength:20
                 },
                 //basePort: {
                 //    maxlength:1,
                 //    digits:true
                 //},
                 longitude: {
                     maxlength:10
                 },
                 latitude: {
                     maxlength:10
                 },
                 areaCode: {
                     maxlength:3
                 },
                 useSystemFlag: {
                     maxlength:1,
                     digits:true
                 }
             },
             messages:
                 {
                     timezone: {
                         maxlength:"最多输入20个字符"
                     },
                     portType: {
                         maxlength:"最多输入5个字符"
                     },
                     portCode: {
                         maxlength:"最多输入5个字符"
                     },
                     port3code: {
                         maxlength:"最多输入20个字符"
                     },
                     areaCommonSetId: {
                         maxlength:"最多输入11个字符",
                         digits:"请输入数字"
                     },
                     serviceLineId: {
                         maxlength:"最多输入11个字符",
                         digits:"请输入数字"
                     },
                     countryCommonSetId: {
                         maxlength:"最多输入11个字符",
                         digits:"请输入数字"
                     },
                     zipcode: {
                         maxlength:"最多输入20个字符"
                     },
                     //basePort: {
                     //    maxlength:"最多输入1个字符",
                     //    digits:"请输入数字"
                     //},
                     longitude: {
                         maxlength:"最多输入10个字符"
                     },
                     latitude: {
                         maxlength:"最多输入10个字符"
                     },
                     areaCode: {
                         maxlength:"最多输入3个字符"
                     },
                     useSystemFlag: {
                         maxlength:"最多输入1个字符",
                         digits:"请输入数字"
                     }
                 }
         });
     }
 );
    var port_table;
    var paral={
        // "portId":"港口Id",
        "portType":"港口类型",
        "portCode":"港口代码",
        "portName":"港口名称（EN）",
        "portNameCn":"港口名称（CN）",
        "port3code":"港口3代码",
        // "areaCommonSetId":"区域ID",
        // "serviceLineId":"航线ID",
        // "countryCommonSetId":"国家ID",
        "areaCommonSetId":"区域",
        "serviceLineId":"航线",
        "countryCommonSetId":"国家",

        //"zipcode":"邮政编码",
        //"stateName":"地区名称",
        "basePort":"基本港",
        "timezone":"时区",
        "longitude":"经度",
        "latitude":"纬度",
        //"areaCode":"地区代码",
        //"useSystemFlag":"useSystemFlag",
        "description":"描述"
    };

    Init();
    function Init() {

        port_table =  $("#port_table").DataTable( {
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
            scrollX: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax : {
                "type" : "POST",
                 // "url": "../mock_data/port.json",
                "url" : getContextPath()+'basedataPort/listByPage.do',
                "data": function(d){
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys =  JSON.stringify($('#searchPortForm').serializeObject());
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
                    "data": "portId",
                    "title":"<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';

                    },
                    "Sortable": false,

                },
                // { title: "港口", data:"portId"},
                { title: "港口代码",data:"portCode" },
                { title: "港口名称（EN）",data:"portName" },
                { title: "港口名称（CN）",data:"portNameCn" },
                { title: "港口类型", data:"portType"},
                { title: "港口3代码",data:"port3code"},
                // { title: "区域ID",data:"areaCommonSetId" },
                // { title: "航线ID",data:"serviceLineId" },
                // { title: "国家ID",data:"countryCommonSetId" },
                { title: "区域",data:"areaName" },
                { title: "航线",data:"serviceLineName" },
                { title: "国家",data:"countryName" },
                //{ title: "邮政编码",data:"zipcode" },
                //{ title: "地区名称",data:"stateName" },
                { title: "基本港",data:"basePort" },
                { title: "时区",data:"timezone" },
                { title: "经度",data:"longitude" },
                { title: "纬度",data:"latitude" },
                //{ title: "地区代码",data:"areaCode" },
                //{ title: "useSystemFlag",data:"useSystemFlag" },
                { title: "描述",data:"description" },
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
                        else if  (type === 'display')
                        return type === 'display' && data.length > 30 ?
                        '<span title="'+data+'">'+data+'</span>' : data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                },
                    targets: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15]
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
                    container: '#Port_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#Port_export-excel-current'

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
                    container: '#Port_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#Port_export-print-all'
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
                    container: '#Port_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#Port_export-columnVisibility'
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
    $('body').on('click' , '.port .checkall' , function(){
        var check = $(this).prop("checked");
        $(".port .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#port_table tbody tr").each(function () {
            if ( port_table.row( this, { selected: true } ).any() ) {
                port_table.row( this ).deselect();
            }
            else {
                port_table.row( this ).select();
            }
        });
    });

    //add
    function addPort() {
        // $("#addPort").on('click',function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortForm"), 'insert');

        //设置国家和航线默认值为空
        emptyFormSelect2Value("editPortForm", ["countryCommonSetId", "serviceLineId", "areaCommonSetId"]);

        $('#editPortModal').modal('show');//现实模态框
        // })
    }
    //edict item
    function editPort() {
        // $("#editPort").click(function () {
        emptyAddForm();
        var selectedRowData = port_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editPortForm input,#editPortForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置国家和航线默认值为空
        setFormSelect2Value("editPortForm", ["countryCommonSetId", "serviceLineId", "areaCommonSetId","timezone"],
            [data["countryCommonSetId"], data["serviceLineId"], data["areaCommonSetId"], data["timezone"]]);

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortModalBody"), 'update');
        $("#editPortForm input[name='amendTime']").val(  data['amendTime']);

        $('#editPortModal').modal('show');//现实模态框

        // })
    }
    //确定增加或者保存编辑；submitEditPortModal
     function submitEditPortModal() {
        // if(!validatePortForm()){
        //     // alert("validate error!");
        //     return;
        // }
            var data = $("#editPortForm").serializeObject();
            var saveType = $("#editPortForm input[name='saveType']").val();

            // 测试使用
            // port_table.row.add(data).draw();//插入一行
            // callSuccess("保存成功");

            $.ajax({
                type: 'POST',
                url: getContextPath() + '/basedataPort/'+saveType+'.do',
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

                         port_table.ajax.reload();
                     }
                     else

                         callAlert(res.message);
                	 
                	
                },
                error: function () {
                    hideMask();
                    callAlert("增加失败");
                }
            });
        $('#editPortModal').modal('hide');//现实模态框
        }


    // delete item
    // $("#deletePort").click(function () {
    function deletePort() {
        var info;
        var selectedRowData = port_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,"Port_confirmDelete");

        //确定删除
        $('.Port_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.portId);
            });

            $.ajax({
                url: getContextPath() + 'basedataPort/delete.do',
                data: {
                    portIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);

                        port_table.ajax.reload();
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
    //$("#refreshPort").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        port_table.ajax.reload();
    }

    $('#port_table tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log( port_table.rows('.selected').data().length);
    } );


    // click item display detail infomation
    $('#port_table tbody').on('dblclick', 'tr', function () {
        var  data = port_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data,paral);
    } );
    $('#showPortDetail').on('click',function () {
        var rows_data = port_table.rows('.selected').data();
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
 $("#resetSeachPortForm").click( function() {


     $("#searchPortForm")[0].reset();

     //设置国家和航线默认值为空
     emptyFormSelect2Value("searchPortForm",["countryCommonSetId","serviceLineId","areaCommonSetId"]);

 })

    // 清空弹框
    function emptyAddForm() {
        $("#editPortForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#port_table tbody tr',
            callback: function (key, options) {
                //var row_data = port_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addPort();
                        break;
                    case "Delete"://删除
                        $("#port_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deletePort();
                        break;
                    case "Edit"://编辑该节点
                        $("#port_table tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editPort();
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
        editPort: editPort,
        addPort:addPort,
        deletePort:deletePort,
        doSearch:doSearch
    };

})();
