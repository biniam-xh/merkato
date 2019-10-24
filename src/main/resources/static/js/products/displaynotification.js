function connect() {
    alert("In display motifications js 2");


    var socket = new SockJS('/ws');
    var stompClient = Stomp.over(socket);

    alert("In display motifications js 3");


    stompClient.connect({}, function(frame) {
        alert("In display motifications js 4");
        stompClient.subscribe('/user/queue/notify', function(notification) {
            alert("In display motifications js 5");


            notify(JSON.parse(notification.body).content);
            alert("In display motifications js 6");

        });
    });

    return;
}


function notify(message) {
    $('#notifications').append(message + "\n");
    return;
}

$(document).ready(function() {
    alert("In display motifications js 1");
    connect();

});