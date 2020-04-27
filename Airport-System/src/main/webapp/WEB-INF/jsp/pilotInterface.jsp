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

<c:forEach items="${serviceCatalogue}" var="service">
	<input id="requestGateServiceButton" type="button" style="background-color:#7FDD4C" value="${service.value}" />
</c:forEach>

<div id="requestGateService">
    <input id="requestGateServiceButton" type="button" style="background-color:#7FDD4C" value="GATE" />
</div>

<div id="requestRefuelService">
    <input id="requestRefuelServiceButton" type="button" style="background-color:#7FDD4C" value="REFUEL" />
</div>

<div id="requestTrackService">
    <input id="requestTrackService" type="button" style="background-color:#7FDD4C" value="TRACK" />
</div>


<script>
    connectServicesWebsocket();

    function connectServicesWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/planes/${planeObj}/updates', function (update) {
             console.log("HERE5");
             updateObject = JSON.parse(update.body);
             updateServiceStatus(updateObject);
          });
       });
    }


    document.getElementById("requestGateService").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":"gate"};
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
        
        document.getElementById("requestRefuelService").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":"refuel"};
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
        
        document.getElementById("requestTrackService").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":"Bus"};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requesttrack",
                            success: function(data){
                                console.log(data);
                              },
                            error: function(data){
                                console.log(data);
                            },
                });
        };


	function updateServiceStatus(updateObject){
        let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[1]
        let nameService = service.name;
        console.log("Service Name : "+service.name); console.log("Service ID : "+service.id);
        if(nameService.startsWith("Refuel")) {
        	document.getElementById("requestRefuelServiceButton").style.backgroundColor='#CCFF33';
        	document.getElementById("requestRefuelServiceButton").disabled = true;
        }
        if(nameService.startsWith("Gate")) {
        	document.getElementById("requestGateServiceButton").style.backgroundColor='#CCFF33';
        	document.getElementById("requestGateServiceButton").disabled = true;
        }
        
    }

</script>

</body>
</html>