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

<style>
input {
   border: rigde;
	  
	  padding: 12px 22px;
	  text-align: center;
	  text-decoration: none;
	  display: inline-block;
	  font-size: 12px;
}
input:hover
{
        box-shadow:
                1px 1px #ffffff,
                2px 2px #ffffff,
                3px 3px #ffffff;
        -webkit-transform: translateX(-3px);
        transform: translateX(-3px);    
}
</style>

<div class="container">

<h1 align="center">Pilot view&#128027;</h1>
<hr>
<div id="PlaneInformation" class = "widget">
<h4>Plane Information</h4>
	<c:if test="${not empty planeId}">
	    <p id="planeidVisible"><b>PlaneID:</b> <span id="planeid"> ${planeId}</span></p>
		<p id="planeStateVisible"><b>Current state of the plane:</b> <span id="planeState"> ${planeState} </span> </p>
	</c:if>
	<c:if test="${empty planeId}">
		<p> ERROR. Unable to get information about the plane.</p>
	</c:if>
	
</div> <!-- div plane info -->



<hr>

<div id="mock" class = "widget" style="display:none;">
<h4>Plane status</h4>
	<p>
		<input id="ChangeStatusButton" type="button" class="waves-effect waves-light btn-large" value="AwaitingTrackForLanding" />
	</p>
<br>
<hr>
</div> <!-- div plane mock -->


<div id="requestlanding2" class = "widget">
<h4>Request track</h4>
	<p style="display:none;" id="trackAffected"> Assigned track : <span id="trackAffectedID">X</span> </p>
    <div id="innerrequestLanding" class = "widget">
        <input id="requestLanding" type="button" class="waves-effect waves-light btn-small" value="Request Landing Track" onclick="requestLandingFunc()"  />
    </div>

    <div id="requestTakeOff" style="display:none;">
        <input id="requestTakeOff" type="button" class="waves-effect waves-light btn-small" value="Request Take Off Track"  onclick="requestTakeoffFunc()" onClick="hideElements()"/>
    </div>
    <br>
    <hr>
</div>



<div id="status" class = "widget">
<h4>Status</h4>
    <input id="inAir" type="button" value="In the air" onClick="changeState('InAir');" disabled="disabled"/>
    <input id="landed" type="button" value="Landed" onClick="changeState('Landed');" disabled="disabled"/>
    <input id="atTerminal" type="button" value="At terminal" onClick="changeState('AtTerminal');" disabled="disabled"/>
<hr>
</div>

<div id="CatalogOfGates" class = "widget" style="display:none;">
<h4>Request gate</h4>
<div class="row" id="gateServices">
<c:forEach items="${serviceCatalogue}" var="service">
	<c:if test="${service.key eq 'bus'}">
		<div id="${service.key}" class"col s4">
			<input id="${service.key}" type="button" class="waves-effect waves-light btn-small" value="REQUEST A GATE" onClick="processService('${service.key}','${service.value}');" />
		</div>
	</c:if>
</c:forEach>
</div>
<div id="valueOfGateAffected" style="display:none;">
	<p> Assigned gate: <span id="gateAffected"> </span> </p>
</div>
<hr>
</div>

<div id="CatalogOfServices" class = "widget" style="display:none;">
<h4>Catalog of services</h4>
<div class="row">
<c:forEach items="${serviceCatalogue}" var="service">
	<c:if test="${service.key != 'bus'}">
		<div id="${service.key}" class"col s4">
			<input id="${service.key}" type="button" class="waves-effect waves-light btn-small" value="${service.value}" onClick="processService('${service.key}','${service.value}');" />
		</div>
	</c:if>
</c:forEach>
</div>
<hr>
</div>

</div> <!-- div container -->


<script>
    connectPlaneWebsocket();
    //connectTrackWebsocket();
    var currentState = $("#planeState").text();

    
    function connectPlaneWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          stompClient.subscribe('/planes/${planeId}/updates', function (update) {
            
             updateObject = JSON.parse(update.body);

             if (Array.isArray(updateObject)){
                 if(updateObject[1] === "assigned") {
                    updateServiceStatus(updateObject);
                 }
                 if(updateObject[1] === "nextState") {
                    changeValueState(updateObject);
                 }
                 if(updateObject[1] === "cancel") {
                    cancelService(updateObject);
                 }
                 if(updateObject[1] === "completed") {
                    serviceCompleted(updateObject);
                 }
             } else {
                  console.log(updateObject);
                  if (updateObject.assignedTrackId != 'null'){
                        document.getElementById("trackAffectedID").innerHTML = updateObject.assignedTrackId;
                        if(updateObject.state.stateName == "Landing") {
                            document.getElementById("planeState").innerHTML = "Landing";
                            let html = '<h4>Status</h4>'+
                            '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
                            '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');"  />'+
                            '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
                            document.getElementById("status").innerHTML = html;
                            $("#trackAffected").show();
                            $("#requestLanding").hide();
                            $("#requestTakeOff").hide();
                        } else if(updateObject.state.stateName == "TakingOff") {
                            document.getElementById("planeState").innerHTML = "TakingOff";
                            let html = '<h4>Status</h4>'+
                            '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');"/>'+
                            '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled"/>'+
                            '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
                            document.getElementById("status").innerHTML = html;
                            $("#trackAffected").show();
                            $("#requestLanding").hide();
                            $("#requestTakeOff").hide();
                        }
                  }
             }
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
         			'<input id="'+serviceKey+'" type="button" class="waves-effect waves-light btn-small" value="'+serviceValue+'" disabled="disabled" onClick="processService("'+serviceKey+'","'+serviceValue+'");"/>'+	
     				'</div>';
		 document.getElementById(serviceKey).innerHTML = html;
        }
    
    
        document.getElementById("requestTrack").onclick = function () {
        
	        let planeId = "${planeId}";
	        let data = {"planeId": planeId, "service":"Bus"}; //Bus?
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
            changeState('AwaitingTrack');
        };
        
       function requestLandingFunc() {
        let planeId = "${planeId}";
        let data = {"planeId": planeId};
        $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                           
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestlanding?plane="+planeId,
                            success: function(res){
                                if(res["response"] == "Sent") 
                                {
                                 changeState('AwaitingTrackForLanding');
                                 let html = '<h4>Status</h4>'+
								    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
								    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled"/>'+
								    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
						    	document.getElementById("status").innerHTML = html;
				                $("#trackAffected").show();
				                $("#requestLanding").hide(); 
				                $("#requestTakeOff").hide();
                                	
                                }
                                else {
                                    alert("Cannot request landing track!");
                                }
                                
                              },
                            error: function(res){
                                console.log("res: " + res);
                            },
                });
                
                
                
        }
        
        function requestTakeoffFunc() {
        document.getElementById("trackAffectedID").innerHTML = "X";
        let planeId = "${planeId}";
        let data = {"plane": planeId};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                           
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestTakeOff?plane="+planeId,
                            success: function(res){
                                if(res["response"] == "Sent") // TO-DO
                                {
	                                 changeState('AwaitingTrackForTakeOff');
	                                 $("#trackAffected").show();
	                                 $("#requestTakeOff").hide();
	                                 $("#CatalogOfServices").hide(); 
             						 $("#CatalogOfGates").hide();
             						 let html = '<h4>Status</h4>'+
										    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
										    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
										    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
                                	document.getElementById("status").innerHTML = html;
                                }
                                else {
                                    alert("Cannot request take off track!");
 
                                }
                                
                              },
                            error: function(res){
                                console.log("res: " + res);
                            },
                });

        };
        
	function updateServiceStatus(updateObject){
        let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[2];
        let nameService = service.name;
        if(nameService.startsWith("Refuel")) {
        
        	let html = '<div id="refuel">'+
         			'<input id="refuel" type="button" value="Refuel service" disabled="disabled" style="background-color:#FF7F50;border-radius:5px 5px;" onClick="processService("refuel","Refuel service");"/>'+	
     				'</div>';
		 	document.getElementById("refuel").innerHTML = html;
		 
        }
        if(nameService.startsWith("Bus")) {
		 	let nameOfService = service.id;
		 	document.getElementById("gateAffected").innerHTML = nameOfService;
		 	$("#valueOfGateAffected").show();
		 	$("#gateServices").hide();
		 	
		 	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" />';
		    document.getElementById("status").innerHTML = html;
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
        
        if(state === "InAir") {
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
		    $("#requestLanding").show();
		    $("#requestTakeOff").hide();
		    $("#trackAffected").hide();
		    
		    
        }
        if(state === "Landed") {
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled" />';
		    document.getElementById("status").innerHTML = html;
		    $("#CatalogOfGates").show();
		    
        }
        if(state === "AtTerminal") {
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
		    $("#CatalogOfServices").show();
		    $("#trackAffected").hide();
            $("#requestTakeOff").show();
        }
    }

    function changeValueState(updateObject) {
    	let plane = updateObject[0]
    	let state = updateObject[2]
    	document.getElementById("planeState").innerHTML = state;
    }
    
    function hideElements() {
    	$("#CatalogOfServices").hide();
    	$("#CatalogOfGates").hide();
    }
    
    function cancelService(updateObject) {
    	let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[2]
        let nameService = service.name;

		if(nameService.startsWith("Refuel")) {
			alert("The refuel service has been canceled. You can launch it again.");
			let html = '<input id="refuel" type="button" class="waves-effect waves-light btn-small" value="Refuel service" onClick="processService(\'refuel\',\'Refuel service\');" />';
			document.getElementById("refuel").innerHTML = html; 
			//$("#gateServices").show(); $("#valueOfGateAffected").hide(); 

		}
    }
    
    function serviceCompleted(updateObject) {
		let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[2]
        let nameService = service.name;

		if(nameService.startsWith("Refuel")) {
			let html = '<input id="refuel" type="button" style="background-color:#32CD32;border-radius:5px 5px;" disabled="disabled" value="Refuel service" onClick="processService(\'refuel\',\'Refuel service\');" />';
			document.getElementById("refuel").innerHTML = html; 
		}
    }
    
</script>

</body>
</html>