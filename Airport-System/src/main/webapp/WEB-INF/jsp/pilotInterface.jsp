<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>


<body>
<c:if test="${not empty planeObj}">
    <p id="planeid">${planeObj}</p>
</c:if>

<div id="requestGateService">
    <input id="requestGateService" type="button" value="Request gate service" />
</div>

<script>
    connectServicesWebsocket();

    function connectServicesWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/planes/${planeObj}/updates', function (update) {
             console.log(JSON.parse(update.body))
          });
       });
    }


    document.getElementById("requestGateService").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":"Bus"};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestservice",
                            success: function(data){
                                console.log(data);
                              },
                            error: function(data){
                                console.log(data);
                            },
                });
        };



</script>

</body>
</html>