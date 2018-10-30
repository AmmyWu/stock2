//@ sourceURL=bank-account.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    // 标题行
var bankAccount = (function(){
 $.validator.setDefaults({
     submitHandler:submitEditBankaccountModal
 });



 $(function () {
     //Initialize Select2 Elements，初始化银行下拉框架
     $(".select2").select2();

     //初始搜索的银行下拉框
     initSelect2FromRedis("searchBankAccountForm","bankId","/redisController/listIdNameByName.do?name=basedataBank","{}","bankId","bankName");

     // var  searchBankIdSelect2 =   $("#searchBankAccountForm  select[name='bankId']");
     // initBankIdSelect2(searchBankIdSelect2);

     //初始化编辑的银行下拉框
     initSelect2FromRedis("editBankaccountForm","bankId","/redisController/listIdNameByName.do?name=basedataBank","{}","bankId","bankName");

     // var  eidtBankIdSelect2 =   $("#editBankaccountForm  select[name='bankId']");
     // initBankIdSelect2(eidtBankIdSelect2);


     //解决select2 在弹出框中不能搜索的问题
     $.fn.modal.Constructor.prototype.enforceFocus = function () { };
 });

 // function initBankIdSelect2(bankIdSelect2) {
 //
 //     $.ajax({
 //         type: 'POST',
 //         url: getContextPath() + '/redisController/listByName.do?name=basedataBank',
 //         // url: '../mock_data/bank_list.json',
 //         cache: false,
 //         dataType: "json",
 //         async: false,
 //         success: function (res) {
 //
 //             //id，text转换
 //             var data = $.map(res, function (obj) {
 //                 obj.text = obj.text || obj.bankName; // replace name with the property used for the text
 //                 obj.id = obj.id || obj.bankId; // replace pk with your identifier
 //                 return obj;
 //             });
 //             // alert(res);
 //             $(bankIdSelect2).select2({
 //
 //                 data: data
 //             });
 //
 //         },
 //         error: function () {
 //             callAlert("初始化失败!");
 //         }
 //     });
 // }

 $().ready(
     function validateBankAccountForm() {
         $("#editBankaccountForm").validate({
             rules: {
                 abbrev: {
                     required: true,
                     maxlength:20
                 },
                 officeId: {
                     required: true,
                     digits: true,
                     maxlength:11
                 },
                 bacntType: {
                     required: true,
                     maxlength:1
                 },
                 depositType: {
                     required: true,
                     maxlength:1
                 },
                 curCode: {
                     maxlength:3
                 },
                 utility: {
                     maxlength:10
                 },
                 curid: {
                     digits:true,
                     maxlength:11
                 }
             },
             messages:
                 {
                     abbrev: {
                         maxlength:"最多输入20个字符"

                     },
                     officeId: {
                         digits:"请输入数字",
                         maxlength:"最多输入11位数字"
                     },
                     bacntType: {
                         maxlength:"最多输入1个字符"
                     },
                     depositType: {
                         maxlength:"最多输入1个字符"
                     },
                     curCode: {
                         maxlength:"最多输入3个字符"
                     },
                     utility: {
                         maxlength:"最多输入10个字符"
                     },
                     curid: {
                         maxlength:"最多输入11位数字",
                         digits:"请输入数字"
                     }
                 }
         });
     }
 );
    var bankacnt_table;
    var paral={
        // "bacntId":"银行账号ID",
        "bankId":"银行ID",
        "officeId":"办公室ID",
        "abbrev":"缩写",
        "bacntType":"账号类型",
        "bacntName":"银行帐号名称",
        "depositType":"储蓄类型",
        "accounts":"账目",
        "curid":"curId",
        "curCode":"cur代码",
        "initDate":"初始日期",
        "amtInit":"amt_init",
        "amt":"amt",
        "utility":"功能",
        "more":"更多",
        "coaCode":"COA代码",
        "description":"描述",

    };

    Init();
    function Init() {
        bankacnt_table  =  $("#bankaccountTable").DataTable( {
            fnDrawCallback:changePage,//重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
            bStateSave : false, // 在第三页刷新页面，会自动到第一页
            iDisplayLength : 10, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            serverSide: true,
            autoWidth: true,
            scrollX: true,
            scrollY: "400px",
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
			 // ajax: "../mock_data/user.txt",
            ajax : {
                "type" : "POST",
                // "url":"../mock_data/bank-account.json",
                "url" : getContextPath()+'basedataBankacnt/listByPage.do',
                "data": function(d){
                    // alert( JSON.stringify($('#searchBankAccountForm ').serializeObject()));
                    d.keys =  JSON.stringify($('#searchBankAccountForm ').serializeObject());
                }
            },


            language: {
                "url": "js/Chinese.json"
            },
            columns: [
                {
                    "sClass": "text-center",
                    "data": "bankacntId",
                    "title":"<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,
                },
                // { title: "银行账号ID", data:"bankacntId"},
                // { title: "银行ID",data:"bankId" },
                { title: "银行名称",data:"bankName" },
                { title: "办公室ID",data:"officeId" },
                { title: "缩写",data:"abbrev" },
                { title: "账号类型", data:"bacntType"},
                { title: "银行帐号名称",data:"bacntName" },
                { title: "储蓄类型",data:"depositType" },
                { title: "账目",data:"accounts" },
                { title: "curId",data:"curid"},
                { title: "cur代码",data:"curCode" },
                { title: "初始日期",data:"initDate" },
                { title: "amt_init",data:"amtInit" },
                { title: "amt",data:"amt" },
                { title: "功能",data:"utility" },
                { title: "更多",data:"more" },
                { title: "COA代码",data:"coaCode" },
                { title: "描述",data:"description" },
                {
                    title: "最新修改人",
                    "data": "baseModel",
                    "render": function (data, type, full, meta) {
                        // console.log(data)
                        return (data == null || data == undefined || data == '') ? '': data.creatorName;
                    },
                    "bSortable": false,
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
                        else
                        return type === 'display' && data.length > 30 ?
                            '<span title="'+data+'">'+data+'</span>' :
                            data;
                    },
                    targets: [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17]
                }
            ],
        } );
    }
 // select/not select all
 $('body').on('click' , '.bank-account .checkall' , function(){
     var check = $(this).prop("checked");
     $(".bank-account .checkchild").prop("checked", check);
     if(check){
         $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
     }
     else {
         $("tr").removeClass("selected");
     }

 });
 //重置查询条件
 $("#bank-account_reset").click( function() {

     // $("#searchBankAccountForm  select[name='bankId']").val("").html("所有");
     $("#searchBankAccountForm  select[name='bankId']").select2('val',['']);

     $("#searchBankAccountForm")[0].reset();
 })

 //add
 $("#addBankaccount").on('click',function () {
     emptyAddForm();

     //设置默认值amender ,amenderName,amendTime,saveType
     setDefaultValue( $("#editBankaccountModalBody"),'insert');


     $('#editBankaccountModal').modal('show');//现实模态框
 })
 //edict item
 $("#editBankaccount").click(function () {
     emptyAddForm();
     var selectedRowData = bankacnt_table.rows('.selected').data();
     if(selectedRowData.length!=1){
         callAlert("请选择一条记录进行编辑！")
         return;
     }
     var data = selectedRowData[0];

     // 循环给表单赋值
     $.each($("#editBankaccountForm input,#editBankaccountForm select "),function(i, input) {

         $(this).val(data[$(this).attr("name")]);

     });

    //设置银行默认值
     $("#editBankaccountForm  select[name='bankId']").select2('val',[data['bankId']]);


     //设置默认值amender ,amenderName,amendTime,saveType
     setDefaultValue($("#editBankaccountModalBody"), 'update');
     $("#editBankaccountForm input[name='amendTime']").val(  data['amendTime']);
     $('#editBankaccountModal').modal('show');//现实模态框

 })
 //确定增加或者保存编辑；
 // $("#editBankaccountModalSubmit").click( function() {
     // if(!validateBankAccountForm()){
     //     alert("validate error!");
     //     return;
     // }
 function submitEditBankaccountModal() {

        var data = $("#editBankaccountForm").serializeObject();
        var saveType = $("#editBankaccountForm input[name='saveType']").val();

     // 测试使用
     //     bankacnt_table.row.add(data).draw();//插入一行
     //     callSuccess("保存成功");

         $.ajax({
             type:'POST',
             url: getContextPath() + '/basedataBankacnt/'+ saveType+'.do',
             data:data,
             cache: false,
             dataType: "json",
             success: function (res) {

                 if(res.code ==0){
                     callSuccess(res.message);

                     bankacnt_table .ajax.reload();
                 }
                 else

                     callAlert(res.message);

             },
             error: function () {
                 callAlert("增加失败");
             }
         });
     $('#editBankaccountModal').modal('hide');//现实模态框
 }


 // delete item
 $("#deleteBankaccount").click(function () {
     var ids = [];
     var info;
     var selectedRowData = bankacnt_table .rows('.selected').data();
     if(selectedRowData.length<1){
         info = "请选择需要删除的数据！";
         callAlert(info);
         return;
     };
     info="确定要删除"+selectedRowData.length+"条数据吗?";
     callAlertModal(info);
     $('#alertModal_Submit').click(function () {
         $.each(selectedRowData, function () {

             ids.push(this.bankacntId);
         });

         $.ajax({
             type:'POST',
             url: getContextPath() + 'basedataBankacnt/delete.do',
             data: {
                 bankacntIds: ids.join(',')
             },
             dataType: 'json',
             success: function (rsp) {

                 if(rsp.code == 0){
                     callSuccess(rsp.message);
                     bankacnt_table .ajax.reload();
                 }else
                     callAlert(rsp.message)


             },
             error: function () {
                 callAlert("删除失败！")
             }
         });
     });

 });


 //refesh bankacnt_table
 function doSearch() {
     // bankacnt_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
     bankacnt_table.ajax.reload();
 }


 $('#bankaccountTable tbody').on('click', 'tr', function () {
     // $(".selected").not(this).removeClass("selected");
     $(this).toggleClass("selected");
     var check = $(this).hasClass("selected")
     $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
     console.log( bankacnt_table.rows('.selected').data().length);
 } );


 // click item display detail infomation
 $('#bankaccountTable tbody').on('dblclick', 'tr', function () {
     var  data = bankacnt_table.rows($(this)).data()[0];
     $("#detail_bankacnt_table").html("");
     DisplayDetail(data,paral);
 } );
 $('#showBankaccountDetail').on('click',function () {
     var rows_data = bankacnt_table.rows('.selected').data();
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
     $("#editBankaccountForm")[0].reset();
     $("label.error").remove();//清除提示语句

 };
return{
    doSearch:doSearch
}
 })();

