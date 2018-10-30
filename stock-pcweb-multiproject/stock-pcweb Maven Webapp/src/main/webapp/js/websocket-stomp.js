//@ sourceURL=websocketStomp.js

var websocketStomp = (function () {
    var stompClient = null;

    //activeMQ 的配置
    // var url =  'ws://localhost:61614';

    var url =  'ws://test.bandlive.cn:61614';
    var userName = 'admin';
    var password = 'admin';
    var topicDestination = '/topic/stock';
    var loginingEmployee = $.cookie("loginingEmployee");

    var userId = 116;//确实的系统用户

    if(loginingEmployee == undefined || loginingEmployee == null)
      ;
    else
        userId = JSON.parse(loginingEmployee).user.userId;//当前登陆的用户ID


    var queueDestination = '/queue/stock/'+ userId;


    // connectActiveMQ();

    // function setConnected(connected) {
    //     document.getElementById('connect').disabled = connected;
    //     document.getElementById('disconnect').disabled = !connected;
    //     document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    //     document.getElementById('response').innerHTML = '';
    // }

    function connectActiveMQ() {

        //链接到spring配置的websocket broker
        // var socket = new SockJS('/stockWebappBase/stomp');
        // stompClient = Stomp.over(socket);

        stompClient = Stomp.client(url);

        stompClient.connect(userName,password, function(frame) {
            // setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe(topicDestination, function(message){
                workBench.initHeaderTopics();
                alert("topic aa" +message.body);
            });

            stompClient.subscribe(queueDestination, function(message){
                // showGreeting(greeting.body);
                workBench.initHeaderMessages();
                alert("queue bb" +message.body);
            });
        });
    }

    function disconnectActiveMQ() {
        if (stompClient != null) {
            stompClient.disconnect();
            // setConnected(false);
            console.log("Disconnected");
        }
    }

    function sendMessage(destination,message) {
        // var name = document.getElementById('name').value;
        //         stompClient.send("/app/hello", {}, name);
        stompClient.send(destination, {}, message);

    }

    // function showGreeting(message) {
    //     var response = document.getElementById('response');
    //     var p = document.createElement('p');
    //     p.style.wordWrap = 'break-word';
    //     p.appendChild(document.createTextNode(message));
    //     response.appendChild(p);
    // }

    return{
        connection:connectActiveMQ,
        disConnection:disconnectActiveMQ,
        sendMessage:sendMessage
    }
})();

