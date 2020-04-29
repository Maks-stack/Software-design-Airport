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

<div id="status">
    <input id="inAir" type="button" value="In the air" onClick="changeState();"/>
    <input id="landed" type="button" value="Landed" onClick="changeState();" />
    <input id="atTerminal" type="button" value="At terminal" onClick="changeState();"/>
</div>

<c:forEach items="${serviceCatalogue}" var="service">
	<div id="${service.key}">
		<input id="${service.key}" type="button" value="${service.value}" onClick="processService('${service.key}','${service.value}');" />
	</div>
</c:forEach>

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


    function processService(serviceKey, serviceValue) {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":serviceKey};
        console.log(serviceValue);
         $.ajax({
                    type : "POST",
                    data: JSON.stringify(data),
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/plane/requestservice",
                    success: function(data){
                        console.log("s");
                      },
                    error: function(data){
                        console.log("e");
                    },
                });
         
         let html = '<div id="serviceKey">'+
         			'<input id="'+serviceKey+'" type="button" value="'+serviceValue+'" disabled="disabled" style="background-color:#F4661B" onClick="processService("'+serviceKey+'","'+serviceValue+'");"/>'+	
     				'</div>';
		 document.getElementById(serviceKey).innerHTML = html;
        }
        
        
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
        
        	let html = '<div id="refuel">'+
         			'<input id="refuel" type="button" value="Refuel service" disabled="disabled" style="background-color:#7FDD4C" onClick="processService("refuel","Refuel service");"/>'+	
     				'</div>';
		 	document.getElementById("refuel").innerHTML = html;
		 
        }
        if(nameService.startsWith("Bus")) {
        
        	let html = '<div id="bus">'+
         			'<input id="bus" type="button" value="Bus service" disabled="disabled" style="background-color:#7FDD4C" onClick="processService("bus","Bus service");"/>'+	
     				'</div>';
		 	document.getElementById("bus").innerHTML = html;
		 
        }
        
    }
    
    function changeState () {
    	let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId};
    	$.ajax({
            type : "POST",
            data: JSON.stringify(data),
            contentType : 'application/json; charset=utf-8',
            url : "http://localhost:8080/plane/changeState",
            success: function(data){
                console.log(data);
              },
            error: function(data){
                console.log(data);
            },
        });
    }

</script>

</body>
</html>