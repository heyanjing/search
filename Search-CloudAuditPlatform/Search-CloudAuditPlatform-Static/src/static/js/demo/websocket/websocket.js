/**
 * stompClient.subscribe('/topic/greetings', function(greeting){
    showGreeting(JSON.parse(greeting.body).content);
    });该方法是接收广播消息。
 stompClient.subscribe('/user/' + userid + '/message',function(greeting){
    alert(JSON.parse(greeting.body).content);
        showGreeting(JSON.parse(greeting.body).content);
    });该方法表示接收一对一消息，其主题是"/user/"+userId+"/message"，不同客户端具有不同的id。如果两个或多个客户端具有相同的id，那么服务器端给该userId发送消息时，这些客户端都可以收到。
 */

$(document).ready(function () {
    // connect();
    //checkoutUserlist();
});

var stompClient = null;

function setConnected(connected) {
    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

//this line.
function connect() {
    var sessionId = $('#sessionId').val();
    var socket = new SockJS(CTX + "/wsendpoint");
    stompClient = Stomp.over(socket);
    // stompClient = webstomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/wstopic/all', function (greeting) {
            console.log("多用户消息==============", greeting);
            console.log(greeting);
            showGreeting(JSON.parse(greeting.body).content);
        });

        stompClient.subscribe('/wsuser/' + sessionId + '/message', function (greeting) {
            console.log("单用户消息==============", greeting);
            showGreeting(JSON.parse(greeting.body).content);
        });
        // HEDEL: 2018/7/4 16:08 无效的内容
        // stompClient.subscribe('/wsuser/message', function (greeting) {
        //     console.log("单用户消息xxx==============", greeting);
        //     showGreeting(JSON.parse(greeting.body).content);
        // });
    });
}

function sendName() {
    var name = document.getElementById('name').value;
    stompClient.send("/wsapp/all", {atytopic: "greetings"}, JSON.stringify({'name': name}));
    console.log("********send*********");
}
function sendName2() {
    var name = document.getElementById('name').value;
    stompClient.send("/wsapp/message", {atytopic: "greetings2"}, JSON.stringify({'name': name}));
    console.log("********send2*********");
}

function connectAny() {
    var socket = new SockJS(CTX + "/hello");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/feed', function (greeting) {
            alert(JSON.parse(greeting.body).content);
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
    var response = document.getElementById('response');
    var p = document.createElement('p');
    p.style.wordWrap = 'break-word';
    p.appendChild(document.createTextNode(message));
    response.appendChild(p);
}