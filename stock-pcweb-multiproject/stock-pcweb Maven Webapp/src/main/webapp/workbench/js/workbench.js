
var workBench = (function () {
    var loginingEmployee = $.cookie("loginingEmployee");//获取当前的user
    var linkInfo = {
        id: '1',
        title: '我的工作台 ',
        icon: 'fa fa-dashboard',
        url: 'workbench/html/workbench',
        close:'true',
        content:"workbench"
    };
    // initTopic();
    // initMessage();
    var messageTable;
    var topicTable;
    var subtopicTable;
    var subMessageTable;

    //监听工作台点击事件，点击初始化控制台模块页面
    $("body").on("click","li.sideMenu_workbench a",function () {
        // getTopics();
    });
    //监听 消息，任务和公告的tab页面的切换
    $("body").on("click","#workbench-tabs",function (e) {
        // var id = $(e.target).attr("id");
        var hrefName = e.target.href;//通过判断href包含的字段来决定触发的函数
        if(hrefName.indexOf("message")>0){
            initMessage();
        }
        else if(hrefName.indexOf("topic")>0){
            initTopic();
        }
        else {

        }
    });
    $(function () {
        $('.ajax-typeahead').typeahead({
            source: function(query, process) {
                return $.ajax({
                    url: "workbench/js/attenList.json",
                    type: 'get',
                    data: {query: query},
                    dataType: 'json',
                    success: function(json) {
                        var objects = [];
                        mapNameId = {};//用于保存id和name的映射关系；
                        var data = json;
                        $.each(data, function(i, object) {
                            mapNameId[object.text] = object;
                            objects.push(object.text);
                        });
                        return  typeof json == 'undefined' ? false : process(objects);
                    }
                });
            },
            //选中时触发该函数；
            updater: function (item) {
                addMessageConsumer(mapNameId[item].id,item)
                return item;
            }
        });
    });
    /**
     * 初始化联系人列表
     * @constructor
     */
    function initAttenList() {
        $.ajax({
            url: "workbench/js/attenList.json",
            type: 'get',
            dataType: 'json',
            success: function(data) {
                var ul=$('#attenList');
                ul.children().remove();//清空节点
                for(var i=0;i<data.length;i++){
                    var li = $('<li class="attenList-item"></li>');
                    var a = $('<a>'+data[i].text+'</a>');
                    var href = 'javascript:workBench.addMessageConsumer("'+data[i].id+'","'+data[i].text+'");';
                    a.attr('href', href);
                    li.append(a);
                    ul.append(li);
                }
            }
        });
    }

    /**
     * 获取联系人的ID和name渲染到页面
     * @param id 联系人id
     * @param name 联系人名
     */
    function addMessageConsumer(id,name) {
        var Array =  $("#messageConsumerId").val().split(",");
        for(var i=0;i<Array.length;i++){
            if(Array[i]==id){
               return;
            }
        }
        var messageConsumers = $(".message-consumer-select");
        var messageConsumer = $("<div class='messageConsumerItem' id="+id+"><strong>"+name+"</strong><i class='" + "fa" + " fa-close" + " removeConsumer'></i></div>");
        messageConsumers.append(messageConsumer);
        $("#messageConsumerId").val($("#messageConsumerId").val()==""?$("#messageConsumerId").val()+id:$("#messageConsumerId").val()+","+id);
    }
    $('body').on('click','.removeConsumer',function () {
        $(this).parent().remove();
        //处理联系人的ID， 把被删除的人员的ID移除
        var id =$(this).parent().attr("id");
        var Array =  $("#messageConsumerId").val().split(",");
        for(var i=0;i<Array.length;i++){
            if(Array[i]==id){
                Array.splice(i,1);
                break;
            }
        }
        $("#messageConsumerId").val(Array.join(","));
    });

    /**
     * 该方法用于获取和初始化当前用户的公告列表
     *
     */
    function initTopic() {

        // Globaltopics=Topics;
        topicTable =  $("#workbench_topicTable").DataTable( {
            // 动态分页加载数据方式
            bProcessing : true,
            bServerSide : true,
            aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
            searching : false,// 禁用搜索
            lengthChange : true, // 是否启用改变每页显示多少条数据的控件
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength : 20, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            autoWidth: true,
            scrollX: false,
            // scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">rt<"bottom"flip><"clear">',
            language: {
                "url": "js/Chinese.json"
            },
            // data:getMessages("TOPIC",'116'),
            ajax: {
                "type": "POST",
                "url": getContextPath()+'message/getMessages.do',
                // "async":false,
                "data": function (d) {
                    //获取该用户的公告列表
                    var data={};
                    data.type =  "TOPIC";
                    data.consumer = '116',
                    // data.consumer = JSON.parse(loginingEmployee)['user']['userId'];
                    d.keys=JSON.stringify(data);
                }
            },
            select: {
                style:"single",
                info:false
            },
            columns: [
                {
                    className:"topic-id",
                    "data": "messageId",
                    "render": function (data, type, full, meta) {
                        return type === 'display'&&full.readNum<=0?
                         '<img src="workbench/image/new.png" style="width: 15px" alt="图片">':"";
                    },
                    "Sortable": false

                },
                {
                    title: "来源", data:"producerName", className:"topic-producer",
                },
                {
                    title: "主题", data:"title", className:"topic-title"
                    // render:function (data, type, full, meta) {
                    //     return type === 'display'?
                    //         '<span onclick="workBench.viewOnetopic(\''+full.messageId+'\')">' + data + '</span>' :
                    //         data;
                    // }
                },
                { title: "内容",data:"strMessage",className:"topic-message" ,
                    render:function (data, type, full, meta){
                        return type === 'display' && data.length > 100 ?
                            '<span title="' + data + '">' + data + '</span>' :
                            data;
                    }
                },
                { title: "时间",data:"time",className:"topic-time"}
            ],
            columnDefs: []
        } );

    }

    //初始化页面右上角headbar的消息提示
    function initHeaderTopics() {
        var length = 0;
        subtopicTable =  $("#workbench_small_topicTable").DataTable( {
            bProcessing : true,
            bServerSide : true,
            aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
            searching : false,// 禁用搜索
            lengthChange : false, // 是否启用改变每页显示多少条数据的控件
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength : 40, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            autoWidth: true,
            scrollX: false,
            // scrollY:calcDataTableHeight(),
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            info:false,
            // data: tapicData,
            ajax: {
                "type": "POST",
                "url": getContextPath()+'message/getMessages.do',
                "data": function (d) {
                    //获取该用户的公告列表
                    var data={};
                    data.type =  "TOPIC";
                    data.readNum = 0;//获取新消息
                    data.consumer =  "116";
                    // data.consumer = JSON.parse(loginingEmployee)['user']['userId'];
                    d.keys=JSON.stringify(data);
                },
                "dataSrc": function ( data ){
                    length=data.aaData.length;
                    $(".notifications-menu .dropdown-menu li.header span").html(length);
                    $(".notifications-menu .dropdown-toggle span").html(length);
                    return data.aaData;
                }
            },
            language: {
                "url": "js/Chinese.json"
            },
            select: true,
            columns: [
                {
                    className:"topic-id",
                    "data": "messageId",
                    "render": function (tapicData, type, full, meta) {
                        return "";
                    },
                    "Sortable": false

                },
                {
                    title: "主题", data:"title", className:"topic-title"
                }
            ],
            columnDefs: [
                {
                    "targets": [0],
                    "visible": false
                }
            ]
        } );

    }
    function initHeaderMessages() {
        var length = 0;
        subMessageTable =  $("#workbench_small_messageTable").DataTable( {
            // 动态分页加载数据方式
            bProcessing : true,
            bServerSide : true,
            aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
            searching : false,// 禁用搜索
            lengthChange : false, // 是否启用改变每页显示多少条数据的控件
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength : 20, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            autoWidth: true,
            scrollX: false,
            // scrollY:calcDataTableHeight(),
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // data:Topics,
            language: {
                "url": "js/Chinese.json"
            },
            info:false,
            ajax: {
                "type": "POST",
                // "url":"../mock_data/container-type.json",
                "url": getContextPath()+'message/getMessages.do',
                "data": function (d) {
                    var data={};
                    data.type =  "QUEUE";
                    data.consumer ="116";
                    data.readNum = 0;
                    //JSON.parse(loginingEmployee)['user']['userId'];
                    d.keys=JSON.stringify(data);
                },
                "dataSrc":function (data) {
                    //将消息的长度写入页面
                    length=data.aaData.length;
                    $(".messages-menu .dropdown-menu li.header span").html(length);
                    $(".messages-menu .dropdown-toggle span").html(length);
                    return data.aaData;
                }
                // "dataSrc": function ( data ){
                //     initHeaderMessages(data.aaData);//将从后台获取到的数据来更新右上角的message数据
                //     return data.aaData;
                // }
            },
            select: true,
            columns: [
                {
                    className:"topic-id",
                    "data": "",
                    "render": function (data, type, full, meta) {
                        return type === 'display'&&full.readNum<=0?
                            '<img src="workbench/image/new-message.png" style="width: 15px" alt="图片">':"";
                    },
                    "Sortable": false

                },
                {
                    title: "主题", data:"title", className:"topic-title"
                }
            ],
            columnDefs: []
        } );
        //将消息的长度写入页面
        $(".messages-menu .dropdown-menu li.header span").html(length);
        $(".messages-menu .dropdown-toggle span").html(length);
    }
    /**topic/message的行如果被点击了则展示详情页
     * 更新改条topic/message的阅读次数
     **/
    $("body").on('click','#workbench_topicTable tr,#workbench_small_topicTable tr,#workbench_messageTable' +
        ' tr,#workbench_small_messageTable tr',function () {
        $(this).addClass("selected");//选中该行以便获取数据
        var id = $(this).closest("table").attr("id");
        var data;
        if(id=="workbench_topicTable"){
            data=topicTable.rows('.selected').data()[0];
            Changtopic(true);
            viewOnetopic(data);//查看该条数据
        }
        else if(id=="workbench_small_topicTable"){
            data=subtopicTable.rows('.selected').data()[0];
            Changtopic(true);
            viewOnetopic(data);//查看该条数据
        }
        else if(id=="workbench_messageTable"){
            data=messageTable.rows('.selected').data()[0];
            Changmessage();
            viewOnemessage(data);//查看该条数据
        }
        else if(id=="workbench_small_messageTable"){
            data=subMessageTable.rows('.selected').data()[0];
            Changmessage();
            viewOnemessage(data);//查看该条数据
        }
        // data.readNum++;//阅读数+1;
        $.ajax({
            url: getContextPath() + "message/updateReadNum.do",//更新该条公告被读次数
            cache: false,
            dataType: "json",
            data: {
                messageId: data.messageId,
                readNum: data.readNum
            },
            type: "POST",
            data:data,
            success: function (res) {
                if(id.indexOf("topic")>=0) {
                    topicTable.ajax.reload();//更新成功后更新table

                    initHeaderTopics();
                }
                else if(id.indexOf("message")>=0){
                    messageTable.ajax.reload();

                    initHeaderMessages();
                }

            },
            error: function () {
            }
        });
        $(this).removeClass("selected");//取消选中


    });
    /**
     * 将需要查看详情的topic的append到页面上
     * @param topic
     */
    function viewOnetopic(topic) {
        ActiveTab("topics");//激活topics 的tab
        // 循环给表单赋值
        $.each($("#editTopic input"), function (i, input) {
            $(this).val(topic[$(this).attr("name")]);

        });
        var message  = topic.strMessage;
        $('#composeTopic').data("wysihtml5").editor.setValue(message);
        // $('#composeTopic').data("wysihtml5").editor.setValue("test");
        //wysihtml5 的赋值需要调用它特定的方法实现；

    }
    /**
     * 将需要查看详情的message的append到页面上
     * @param topic
     */
    function viewOnemessage(message){
        ActiveTab("messages");//激活topics 的tab
        // 循环给表单赋值
        $.each($("#editMessage input"), function (i, input) {
            $(this).val(message[$(this).attr("name")]);

        });
        var message  = message.strMessage;
        $('#composeMessage').data("wysihtml5").editor.setValue(message);
        // $('#composeTopic').data("wysihtml5").editor.setValue("test");
        //wysihtml5 的赋值需要调用它特定的方法实现；
    }

    /**
     * 根据不同的table名称刷新相应的表格
     * @param TableName table的名称
     * @constructor
     */
   function refreshTopic(TableName) {
        //刷新公告表
        if(TableName.indexOf('topic')>=0){
            topicTable.ajax.reload();
        }
        else if(TableName.indexOf('message')>=0){
            messageTable.ajax.reload();
        }
    }




    function initMessage() {
        messageTable =  $("#workbench_messageTable").DataTable( {
            // 动态分页加载数据方式
            bProcessing : true,
            bServerSide : true,
            aLengthMenu : [ 10, 20, 40, 60 ], // 动态指定分页后每页显示的记录数。
            searching : false,// 禁用搜索
            lengthChange : true, // 是否启用改变每页显示多少条数据的控件
            deferRender : true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength : 20, // 默认每页显示多少条记录
            iDisplayStart : 0,
            ordering : false,// 全局禁用排序
            autoWidth: true,
            scrollX: false,
            // scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">rt<"bottom"flip><"clear">',
            // data:Topics,
            language: {
                "url": "js/Chinese.json"
            },
            ajax: {
                "type": "POST",
                // "url":"../mock_data/container-type.json",
                "url": getContextPath()+'message/getMessages.do',
                "data": function (d) {
                    var data={};
                    data.type =  "QUEUE";
                    data.consumer ="116";
                        //JSON.parse(loginingEmployee)['user']['userId'];
                    d.keys=JSON.stringify(data);
                }
                // "dataSrc": function ( data ){
                //     initHeaderMessages(data.aaData);//将从后台获取到的数据来更新右上角的message数据
                //     return data.aaData;
                // }
            },
            select: {
                // blurable: true,
                style: 'multi',//选中多行
                selector: 'td:first-child input'//选中效果仅对第一列的checkbox有效
                // info: false
            },
            columns: [
                {
                    className:"topic-id",
                    "data": "messageId",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    // "render": function (data, type, full, meta) {
                    //     return type === 'display'&&full.readNum<=0?
                    //         '<img src="workbench/image/new.png" style="width: 15px" alt="图片">':"";
                    // },
                    "Sortable": false

                },
                {
                    className:"topic-id",
                    "data": "",
                    "render": function (data, type, full, meta) {
                        return type === 'display'&&full.readNum<=0?
                            '<img src="workbench/image/new-message.png" style="width: 15px" alt="图片">':"";
                    },
                    "Sortable": false

                },
                {
                    title: "来源", data:"producerName", className:"topic-producer",
                },
                {
                    title: "主题", data:"title", className:"topic-title",
                    render:function (data, type, full, meta) {
                        return type === 'display'?
                            '<span onclick="workBench.viewOnetopic(\''+full.messageId+'\')">' + data + '</span>' :
                            data;
                    }
                },
                { title: "内容",data:"message",className:"topic-message" ,
                    render:function (data, type, full, meta){
                        return type === 'display' && data.length > 100 ?
                            '<span title="' + data + '">' + data + '</span>' :
                            data;
                    }
                },
                { title: "时间",data:"time",className:"topic-time"}
            ],
            columnDefs: []
        } );

        // initHeaderMessages();
    }

    /**
     * 点击页面右上方查看所有公告，跳转到公告页面
     */
    function viewAlltopics() {
        addTabs(linkInfo);
        // $("div.nav-tabs-workbench ul").children('li').removeClass("active");
        // $("li.topics").addClass("active");
        // $("#workbench_topic").addClass("active");
        // getTopics(topicKey);
        ActiveTab("topics");
    }
    function viewAllmessages(){
        addTabs(linkInfo);
        ActiveTab("messages")
    }
    // $('body').keydown(function(e){
    //     if(e.keyCode == 13){
    //         //模拟点击按钮，触发search 事件
    //         if(e.target.className.indexOf("searchTopic")>0){
    //             searchTopic()
    //         }
    //
    //     }
    // });

    //查找公告
    function searchTopic() {
        $(".has-feedback").serializeObject();
        //获取查找的关键字，向后台传值
        alert("待完善")
    }
    //取消编辑公告
    function canceleditTopic() {
        $("#editTopic")[0].reset();
       $('#composeTopic').data("wysihtml5").editor.setValue("");
    }
    //发送编辑完成公告
    function sendeditedTopic() {
        var data =  $("#editTopic").serializeObject();
        var content = $('#composeTopic').data("wysihtml5").editor.getValue();
        //编辑框内的文本调用相应的方法获取
        var message={};
        // message.consumer=data.consumer;
        message.title=data.title;
        message.message=content;
        message.type = "TOPIC";
        message.producer = 116;
        // message.producer = JSON.parse(loginingEmployee)['user']['userId'];
        $.ajax({
            url: getContextPath()+'message/insert.do',//更新该条公告被读次数
            cache: false,
            dataType: "json",
            type: "GET",
            data:message,
            success: function (res) {
                callSuccess("success");


            },
            error: function () {
                callAlert("failed");
            }
        });
    }

    //取消编辑消息
    function canceleditMessage() {
        $("#editMessage")[0].reset();
        $('#composeMessage').data("wysihtml5").editor.setValue("");//编辑框清空
        $(".message-consumer-select").empty();//选中收消息人员清空
    }
    function deleteMessages() {
        var selectedRowData = messageTable.rows('.selected').data();
        if(selectedRowData.length<=0){
            callAlert("没有选择任何消息，请重新选择");
            return;
        }
        var ids=[];
        for(var i=0;i<selectedRowData.length;i++){
            ids.push(selectedRowData[i].messageId);
        }
        $.ajax({
            url: getContextPath()+'message/delete.do',//更新该条公告被读次数
            cache: false,
            dataType: "json",
            type: "GET",
            data:{
                ids:ids.join(",")
            },
            success: function (res) {
                callSuccess(res.message);
                messageTable.ajax.reload();
            },
            error: function (res) {
                callAlert(res.message);
            }
        });
    }

    function sendeditedMessage() {
        var data =  $("#editMessage").serializeObject();

        if(data.consumer == "" || data.consumer == undefined){
            alert("请选择消息收件人！");
            return;
        }

        var content = $('#composeMessage').data("wysihtml5").editor.getValue();
        //编辑框内的文本调用相应的方法获取
        var message={};
        message.consumer=data.consumer;
        message.title=data.title;
        message.message=content;
        message.type = "QUEUE";
        message.producer = 116;// JSON.parse(loginingEmployee)['user']['userId'];
        $.ajax({
            url: getContextPath()+'message/insert.do',//更新该条公告被读次数
            cache: false,
            dataType: "json",
            type: "POST",
            data:message,
            success: function (res) {
                callSuccess("success");
            },
            error: function () {
                callAlert("failed");
            }
        });
    }

    $(".open-small-chat").on('click',function () {
        if($(".open-small-chat i").hasClass("fa-remove")){
            $(".open-small-chat i").removeClass().addClass("fa fa-comments");
            $(".small-chat-box").removeClass("active");
        }
        else{
            $(".open-small-chat i").removeClass().addClass("fa fa-remove");
            $(".small-chat-box").addClass("active");
        }

    });
    function Changtopic(showTopic) {
        //需要展示topic
        if(showTopic){
            $(".box.topic-box").addClass("active");
        }
        if( $(".box.topic-box").hasClass("active")){
            $(".box.topic-box").removeClass("active");
            $(".box.edit-topic-box").addClass("active");
            //显示message的textarea关闭，编辑的开启
            $(".edit-topic").css({"display":"block"});
            $("#editTopic")[0].reset();
            $("#topicAction").html("返回公告板");
            $(".refreshTopic").css({"display":"none"});
            $(".canceleditTopic,.sendeditedTopic").css({"display":"inline-block"});
        }
        else {
            $(".box.topic-box").addClass("active");
            $(".box.edit-topic-box").removeClass("active");
            $("#topicAction").html("发布公告");
            $(".refreshTopic").css({"display":"inline-block"});
            $(".canceleditTopic,.sendeditedTopic").css({"display":"none"});
            topicTable.ajax.reload();//返回公告板时刷新公告板列表

        }

    }
    function Changmessage() {
        if( $(".box.message-box").hasClass("active")){
            $(".box.message-box").removeClass("active");
            $(".box.edit-message-box").addClass("active");
            $(".atten-box").css({"display":"block"});
            $("#messageAction").html("返回消息列表");
            $("#workbench_messages .row .message-operation").removeClass("col-md-12").addClass("col-md-3");
            $("#workbench_messages .row .message-content").removeClass("col-md-12").addClass("col-md-9");
            $(".mailbox-controls").css({"display":"none"});
            $(".serach-message").css({"display":"none"});

            initAttenList();//初始化联系人列表
        }
        else {
            $(".box.message-box").addClass("active");
            $(".box.edit-message-box").removeClass("active");
            $(".atten-box").css({"display":"none"});
            $("#messageAction").html("发送消息");
            $("#workbench_messages .row .message-operation").removeClass("col-md-3").addClass("col-md-12");
            $("#workbench_messages .row .message-content").removeClass("col-md-9").addClass("col-md-12");
            $(".mailbox-controls").css({"display":"inline-block"});
            $(".serach-message").css({"display":"block"});

            //初始化Message列表
            initMessage();
        }


    }

    /**
     * 该方法更具用于激活指定的TAB页面
     * @param tabId 需要激活的tab页的ID
     * @constructor
     */
    function ActiveTab(tabclass) {
        $("."+tabclass).children('a').click();

    }

    return{
        viewAlltopics:viewAlltopics,
        initTopic:initTopic,
        initHeaderTopics:initHeaderTopics,
        initHeaderMessages:initHeaderMessages,
        initMessage:initMessage,
        deleteMessages:deleteMessages,//删除消息记录
        // getTopics:getTopics,
        viewOnetopic:viewOnetopic,
        Changtopic:Changtopic,
        searchTopic:searchTopic,
        canceleditTopic:canceleditTopic,//取消编辑公告
        sendeditedTopic:sendeditedTopic,//发送编辑完成的公告
        canceleditMessage:canceleditMessage,//取消编辑消息
        sendeditedMessage:sendeditedMessage,//发送编辑完成的消息
        Changmessage:Changmessage,
        addMessageConsumer:addMessageConsumer,
        viewAllmessages:viewAllmessages,
        refreshTopic:refreshTopic//刷新公告栏

    }


})();

