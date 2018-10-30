//删除上传的文件
// function deleteUploadFile(businessTable,businessId,fileName)
function deleteUploadFile(attachmentId,fileName)
{



    //alert("businessTable="+businessTable +"businessId="+businessId);
    //todo 删除已保存到数据库中的文件
	
    $.ajax({
        url: getContextPath()+'/sysAttachment/delete.do',
        data:{
            attachmentIds:attachmentId

        },
        success: function(){

            //删除文件
            $("."+fileName).remove();
            //删除input标签
            // $("."+fileName).eq(1).next().remove();
            $("#uploadedFile input[name='attachmentId_"+attachmentId+"']").remove();
            $("#uploadedFile input[name='attachmentId_"+attachmentId+"']").next().remove();
            $("#uploadedFile2 input[name='attachmentId_"+attachmentId+"']").remove();
            $("#uploadedFile2 input[name='attachmentId_"+attachmentId+"']").nextAll("br").eq(0).remove();
            // $("#uploadedFile input[name=attachmentId_"+attachmentId+"]").next("br").remove();
        },
        error:function(){

            callAlert("删除失败！");
        }


    });
}
//上传附件
function uploadFile(formId,selectedFile,businessTable,businessId,fileType,showUploadedFileLabelId,modelName){

    $Form = $("#" + formId);

    //根据上传的文件路径取得文件名称
    str=$('#'+selectedFile).val();
    if(str==''){
        callAlert('请先选择要上传的文件');
    }else{
        var arr=str.split('\\');//注split可以用字符或字符串分割
        var fileName=arr[arr.length-1];//这就是要取得的文件名称
//		document.getElementById("documentNames").value=filename;
//		document.getElementById("fileName1").value=filename;
        fileName = encodeURI(encodeURI(fileName));
        var fileType =  encodeURI(encodeURI(fileType));

        $Form.attr("action",getContextPath()+'/uploader/uploadFile.do?businessTable='+businessTable+"&businessId="+businessId+"&fileName="+fileName+"&fileType="+fileType+"&showUploadedFileLabelId="+showUploadedFileLabelId+"&modelName="+modelName);
        $Form.submit();

//		document.forms.uploadForm1.action=getContextPath()+'/uploader/uploadFile.do?businessTable='+businessTable+"&businessId="+businessId;
//		document.forms.uploadForm1.submit();
    }
}

// //销售上传附件
// function SaleUploadFile(formId,selectedFile,businessTable,customerId,fileType,showUploadedFileLabelId,modelName){
//
//     $Form = $("#" + formId);
//
//     //根据上传的文件路径取得文件名称
//     str=$('#'+selectedFile).val();
//     if(str==''){
//         callAlert('请先选择要上传的文件');
//
//
//     }else{
//         var arr=str.split('\\');//注split可以用字符或字符串分割
//         var fileName=arr[arr.length-1];//这就是要取得的文件名称
// //		document.getElementById("documentNames").value=filename;
// //		document.getElementById("fileName1").value=filename;
//         fileName = encodeURI(encodeURI(fileName));
//         var fileType =  encodeURI(encodeURI(fileType));
//
//         $Form.attr("action",getContextPath()+'/saleFile/uploader.do?businessTable='+businessTable+"&customerId="+customerId+"&fileName="+fileName+"&fileType="+fileType+"&showUploadedFileLabelId="+showUploadedFileLabelId+"&modelName="+modelName);
//         $Form.submit(
//             customerAttachment.InitCustomerAttachment()
//         )
//
// //		document.forms.uploadForm1.action=getContextPath()+'/uploader/uploadFile.do?businessTable='+businessTable+"&businessId="+businessId;
// //		document.forms.uploadForm1.submit();
//     }
// }