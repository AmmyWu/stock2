//解决select2 在弹出框中不能搜索的问题
$.fn.modal.Constructor.prototype.enforceFocus = function () { };

//根据请求的url，keyes，idFieldName,textFieldName等属性,初始指定form下的select2对象
function initSelect2FromRedis(formId,select2Name,url,keys,idFieldName,textFieldName) {

    //置空,删除所有赋值的option，保留 请选择/select的option
    $("#"+formId+"  select[name='"+select2Name+"'] option[value !='']").remove();

    $.ajax({
        type: 'POST',
        url: getContextPath() + url,
        // url: '../mock_data/bank_list.json',
        // url:url,
        cache: false,
        data:{
          keys:keys
        },
        dataType: "json",
        // async: false,
        success: function (res) {


            //以json数据返回的处理方式，[{1,"aaa"},{2,"bbb"},{...}]
            var data = $.map(res, function (obj) {
                for (var key in obj){
                    obj.id = key;// replace name with the property used for the text
                    obj.text = obj[key]; // replace pk with your identifier
                    return obj;
                }
            });

            //以json方式直接返回的处理方式，{1:"aaa",2:"bbb"}
            // var data =[];
            //
            // for(var key in res){
            //     var nodeData ={};
            //     nodeData['id'] = key;
            //     nodeData['text']  = res[key];
            //
            //     data.push(nodeData);
            // }


            //已带字段名的json返回的处理方式，[{"idFieldName":"1","textFieldName":"aaa"},{"idFieldName":"2","textFieldName":"bbbb"},...]id，text转换
            // var data = $.map(res, function (obj) {
            //     obj.text = obj.text || obj[textFieldName]; // replace name with the property used for the text
            //     obj.id = obj.id || obj[idFieldName]; // replace pk with your identifier
            //     return obj;
            // });

            $("#"+formId+"  select[name='"+select2Name+"']").select2({

                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}

// 切换码头
function initSelect2FromRedis3(formId,select2Name,url,keys,idFieldName,textFieldName) {

    //置空,删除所有赋值的option，保留 请选择/select的option
    $("#"+formId+"  select[name='"+select2Name+"'] option[value !='']").remove();

    $.ajax({
        type: 'POST',
        url: getContextPath() + url,
        // url: '../mock_data/bank_list.json',
        // url:url,
        cache: false,
        data:{
            keys:keys
        },
        dataType: "json",
        // async: false,
        success: function (res) {
            var length= res.length;
            var data=[];
            for(var i=0;i<length;i++){
                 data.push({id:i,text:res[i]})
            }
            $("#"+formId+"  select[name='"+select2Name+"']").select2({
                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}


// 等级设置
function initSelect2FromRedis4(formId,select2Name,url,keys,idFieldName,textFieldName) {

    //置空,删除所有赋值的option，保留 请选择/select的option
    $("#"+formId+"  select[name='"+select2Name+"'] option[value !='']").remove();

    $.ajax({
        type: 'POST',
        url: getContextPath() + url,
        // url: '../mock_data/bank_list.json',
        // url:url,
        cache: false,
        data:{
            keys:keys
        },
        dataType: "json",
        // async: false,
        success: function (res) {
            var length= res.length;
            var data=[];
            var codeName=[];
            for(var i=0;i<length;i++){
                data.push({id:res[i].rankId,text:res[i].rankCode})
            }
            $("#"+formId+"  select[name='"+select2Name+"']").select2({
                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}


//根据请求的url，keyes，idFieldName,textFieldName等属性,初始指定form下的select2对象
function initSelect2FromRedis2(formId,select2Name,url,keys,idFieldName,textFieldName) {

    //置空,删除所有赋值的option，保留 请选择/select的option
    $("#"+formId+"  select[name='"+select2Name+"'] option[value !='']").remove();

    $.ajax({
        type: 'POST',
        url: getContextPath() + url,
        // url: '../mock_data/bank_list.json',
        // url:url,
        cache: false,
        data:{
            keys:keys
        },
        dataType: "json",
        // async: false,
        success: function (res) {


            //以json数据返回的处理方式，[{1,"aaa"},{2,"bbb"},{...}]
            var data = $.map(res, function (obj) {
                for (var key in obj){
                    obj.id = key;// replace name with the property used for the text
                    obj.text = obj[key]; // replace pk with your identifier
                    return obj[idFieldName];
                }
            });

            $("#"+formId+"  select[name='"+select2Name+"']").select2({
                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}




//根据请求的url，keyes，idFieldName,textFieldName等属性,初始指定form下的select2对象
function initSelect2FromDB(formId,select2Name,url,keys,idFieldName,textFieldName) {

    //置空,删除所有赋值的option，保留 请选择/select的option
    $("#"+formId+"  select[name='"+select2Name+"'] option[value !='']").remove();


    $.ajax({
        type: 'POST',
        url: getContextPath() + url,
        // url: '../mock_data/bank_list.json',
        // url:url,
        cache: false,
        data:{
            keys:keys
        },
        dataType: "json",
        // async: false,
        success: function (res) {


            //id，text转换
            var data = $.map(res, function (obj) {
                obj.text = obj.text || obj[textFieldName]; // replace name with the property used for the text
                obj.id = obj.id || obj[idFieldName]; // replace pk with your identifier
                return obj;
            });

            $("#"+formId+"  select[name='"+select2Name+"']").select2({

                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}


//给指定表单的select2对象赋值,select2Names,select2Values为长度一样的数组
function setFormSelect2Value(formId,select2Names,select2Values){
    $.each( select2Names,function(i, selec2Name) {
        $("#"+formId +" select[name='"+selec2Name+"'").select2('val',[select2Values[i]]);

    });
}
//给指定表单的select2对象赋值,select2Names为数组，select2Values为key值包含select2Names为数组值的对象
// function setFormSelect2ValueforObj(formId,select2Names,select2Values){
//     $.each( select2Names,function(i, selec2Name) {
//         $("#"+formId +" select[name='"+selec2Name+"'").select2('val',[select2Values[selec2Name]]);
//
//     });
// }

//给指定表单的select2对象赋值,select2Names为数组，select2Values为key值包含select2Names为数组值的对象
function setFormSelect2ValueforObj(formId,select2Names,select2Values){
    // alert();
    $.each( select2Names,function(i, selec2Name) {
        //
        var array = select2Values[selec2Name].toString().split(",");
        // alert(array);

        $("#"+formId +" select[name='"+selec2Name+"'").select2('val',[array]);

    });
}


//给指定表单的select2对象赋值
function setSelect2Value(formId,select2Name,select2Value){
    $("#"+formId+"  select[name='"+select2Name+"']").select2('val',[select2Value]);
}

//清空表单中多个的select2值，select2Names为数组
function emptyFormSelect2Value(formId,select2Names){

    $.each( select2Names,function(i, selec2Name) {
         $("#"+formId +" select[name='"+selec2Name+"'").select2('val',['']);

    });
}

//给指定表单的select2对象付空值
function emptySelect2Value(formId,select2Name){

    $("#"+formId+"  select[name='"+select2Name+"']").select2('val',['']);
}


function initCommonSetIdSelect2(formId,select2Name,superiorId){

    var  commonSetIdSelect2 =   $("#"+formId+"  select[name='"+select2Name+"']");
    // initCommonSetIdSelect2(countryCommonSetIdSelect2,1);//superiorId 1为国家

    $.ajax({
        type: 'POST',
        url: getContextPath() + '/basedataCommonSet/listByKeys.do',
        // url: '../mock_data/bank_list.json',
        cache: false,
        data:{
            keys:'{"superiorId":'+superiorId+'}'
        },
        dataType: "json",
        async: false,
        success: function (res) {

            //id，text转换
            var data = $.map(res, function (obj) {
                obj.text = obj.text || obj.cnName; // replace name with the property used for the text
                obj.id = obj.id || obj.commonSetId; // replace pk with your identifier
                return obj;
            });
            // alert(res);
            $(commonSetIdSelect2).select2({

                data: data
            });

        },
        error: function () {
            callAlert("初始化失败!");
        }
    });
}
