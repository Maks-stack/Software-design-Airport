<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>
<body>

<div class="container">

<h1 align="center">Pilot view</h1>
<hr>
<div id="planeState"> ${planeState} </div>
<div id="PlaneInformation" class = "widget">
<h4>Plane Information</h4>
	<c:if test="${not empty planeId}">
	    <p id="planeid"><b>PlaneID:</b> ${planeId}</p>
		<p id="planeStateVisible"><b>Current state of the plane:</b> ${planeState}</p>
	</c:if>
	<c:if test="${empty planeId}">
		<p> ERROR. Unable to get information about the plane.</p>
	</c:if>
	
</div>

<hr>
<div id="mock" class = "widget">
<h4>&#128027;Mock&#128027;</h4>

</div>


<hr>

<div id="status" class = "widget">
<h4>Status</h4>
    <input id="inAir" type="button" value="In the air" onClick="changeState('InAir');" disabled="disabled"/>
    <input id="landed" type="button" value="Landed" onClick="changeState('Landed');" />
    <input id="atTerminal" type="button" value="At terminal" onClick="changeState('AtTerminal');" disabled="disabled"/>
</div>

<hr>

<div id="CatalogOfServices" class = "widget">
<h4>Catalog of services</h4>
<c:forEach items="${serviceCatalogue}" var="service">
	<div id="${service.key}">
		<input id="${service.key}" type="button" value="${service.value}" onClick="processService('${service.key}','${service.value}');" />
	</div>
</c:forEach>
</div>

<hr>

<div id="requestTrackService" class = "widget">
    <input id="requestTrackService" type="button" style="background-color:#7FDD4C" value="TRACK" />
</div>

<br>
<hr>

<div id="requestLanding" class = "widget">
    <input id="requestLanding" type="button" value="Request Landing Track"  />
</div>

</div>
<script>
    connectServicesWebsocket();

    function connectServicesWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/planes/${planeId}/updates', function (update) {
             
             updateObject = JSON.parse(update.body);
             updateServiceStatus(updateObject);
          });
       });
    }


    function processService(serviceKey, serviceValue) {
        let planeId = "${planeId}";
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
           document.getElementById("requestLanding").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestlanding",
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
    
    function changeState (state) {
    	let planeId = "${planeId}";
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
        console.log("STATE :"+state);
        if(state === "InAir") {
        	console.log("LA 1");
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');"  />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
		    
		    let html2 = 'Landed';
		    document.getElementById("planeState").innerHTML = html2;
		    
        }
        if(state === "Landed") {
        	console.log("LA 2");
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" />';
		    document.getElementById("status").innerHTML = html;
		    
		    let html2 = 'AtTerminal';
		    document.getElementById("planeState").innerHTML = html2;
        }
        if(state === "AtTerminal") {
        	console.log("LA 3");
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
		    
		    let html2 = 'InAir';
		    document.getElementById("planeState").innerHTML = html2;
        }
        
        
    }

</script>

</body>
</html>