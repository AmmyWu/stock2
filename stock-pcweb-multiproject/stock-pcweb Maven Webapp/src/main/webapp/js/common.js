//获取当前webcontext路径
function getContextPath(){
    return '/stockWebappBase/';////salesys-pc-web
    // return 'http://test.bandlive.cn/stockWebappBase/';
   // return '/stock-webapp-base/';
}
function getShipContextPath(){
    return '/stockWebappShip/';
}

//批处理，设置表单为只读
function setReadonlyForInputs(inputObjects){

    $.each(inputObjects,function(i,inputObject){

       $(inputObject).attr("readonly",true);
    });

}
//新增修改时设置默认值
function setDefaultValue(formObject,saveType){
	//todo  form cookie
	$(formObject).find(" input[name='amender']").val("8");

	$(formObject).find(" input[name='saveType']").val(saveType);


    $(formObject).find(" input[type='submit']").val("确定");



    if(saveType=='insert'){
        $(formObject).find(" input[name='amendTime']").val( $.date.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        $(formObject).find(" input[name='amenderName']").val();  //todo  from cookie

        $(formObject).prev('.modal-header').find('.modal-title').html("增加");
    }
    else {
        $(formObject).prev('.modal-header').find('.modal-title').html("修改");
       // $('#addModalLabel').html("修改");
    }
}

// 警告弹框
function callAlert(info) {
    $(".alert-danger-content").html(info);

    setTimeout(function() {
        $(".alert-danger").css("display", "none");
    }, 2000);
    var top = $(document).scrollTop()+30;
    $(".alert-danger").css("top", top+"px");
    $(".alert-danger").css("display", "block");
}
//成功弹框
function callSuccess(info) {
    $(".alert-success-content").html(info);
    setTimeout(function() {
        $(".alert-success").css("display", "none");
    }, 2000);
    var top = $(document).scrollTop()+30;
    $(".alert-success").css("top", top+"px");
    $(".alert-success").css("display", "block");
}

function callAlertModal(info,className) {
    $('#alert_modal_body').html(info);
    $('#alertModal').modal('show');
    $('#alertModal_Submit').removeClass().addClass(className+" btn btn-danger");
}

//object sort according to a property
function compare(property){
    return function(a,b){
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
    }
}


//点击表格某一条，现实弹框
//传入参数是self:被选中的
function DisplayDetail(data,paral) {
    console.log("come in DisplayDetail");
    var table = $("<table id='detail_table'></table>")
    var count=0;
    var tr;
    for(var key in paral){
        if(count==0){
            tr="<tr><td>"+paral[key]+"</td><td>"+data[key]+"</td>";
            count=1;
        }
        else {
            tr +="<td>"+paral[key]+"</td><td>"+data[key]+"</td></tr>";
            table.append(tr);
            count=0;
        }
        // console.log(index);
    }
    if(count==1){
        tr +="</tr>";
        table.append(tr);
    }
    // else {
    //     $("#detail_table").append(tr);
    // }
    $("#myModal .modal-body").empty();
    $("#myModal .modal-body").append(table);
    $('#myModal').modal('show');//现实模态框

}
//显示遮罩层
function showMask(){
    $("#operationMask").modal('show');
}
//隐藏遮罩层
function hideMask(){
    $("#operationMask").modal('hide');
}


//把数据中的data转为datatime，因为后台需要处理统一的格式
function changeDatetoDatetime(data) {

    for(var key in data){
        if (key == "declarationDate"||key == "importDate"||key == "receivedDate"||key == "clearanceDate"||key == "returnDate"||key == "releaseDate"||key =="createTime") {
           {
               if(data[key]!=""&&data[key]!=null&&data[key]!=undefined)
                data[key] = $.date.format(new Date(data[key]),"yyyy-MM-dd HH:mm:ss");
            }
        }
    }
    return data;
}
