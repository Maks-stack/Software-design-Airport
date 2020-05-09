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
	<p style="display:none;" id="trackAffected"> Affected track : <span id="trackAffectedID">X</span> </p>
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
	<p> Affected gate: <span id="gateAffected"> </span> </p>
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


    connectServicesWebsocket();
    connectTrackWebsocket();
    function connectTrackWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          console.log('Connected: ' + frame);
          stompClient.subscribe('/tracks/updates', function (update) {
             
             updateObject = JSON.parse(update.body);
             console.log("Umer TEST:"+updateObject.trackID)
             if(updateObject != null) {
             	document.getElementById("trackAffectedID").innerHTML = updateObject.trackID;
             	
		        console.log("Track id : "+updateObject.trackID);
		        
		        let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');"  />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
             } else {
             	
             }
             
          });
       });
    }
    
    function connectServicesWebsocket() {
       var socket = new SockJS('/planes-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/planes/${planeId}/updates', function (update) {
            
             updateObject = JSON.parse(update.body);
             if(updateObject[1] === "assigned") {
             	updateServiceStatus(updateObject); 
             } 
             if(updateObject[1] === "nextState") {
             	changeValueState(updateObject);
             }
             if(updateObject[1] === "cancel") {
             	cancelService(updateObject); 
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
        //alert(data);
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
                changeState('AwaitingTrackForLanding');
                let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    	document.getElementById("status").innerHTML = html;
                $("#trackAffected").show();
                $("#requestLanding").hide(); 
                $("#requestTakeOff").hide();
        }
        
        function requestTakeoffFunc() {
        document.getElementById("trackAffectedID").innerHTML = "X";
        let planeId = "${planeId}";
        let data = {"planeId": planeId};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestTakeOff",
                            success: function(res){
                                console.log("umer1: "+res);
                                //if(sent) // TO-DO
                                //{
                                	changeState('AwaitingTrackForTakeOff');
						            let html = '<h4>Status</h4>'+
										    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');"/>'+
										    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
										    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
								    document.getElementById("status").innerHTML = html;
                                //}
                                //else {
                                  // display error
                               // }
                                
                              },
                            error: function(res){
                                console.log("umer: " + res);
                            },
                });
            
		    
		    $("#trackAffected").show();
            $("#requestTakeOff").hide();
        };
        
	function updateServiceStatus(updateObject){
        let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[2]; console.log("Je suis dedans!");
        let nameService = service.name; 
        console.log("Service Name : "+service.name); console.log("Service ID : "+service.id);
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
        console.log("STATE :"+state);
        
        if(state === "InAir") {
        	console.log("LA 1");
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled"/>';
		    document.getElementById("status").innerHTML = html;
		    $("#requestLanding").show();
		    $("#requestTakeOff").hide();
		    
		    
        }
        if(state === "Landed") {
        	console.log("LA 2");
        	let html = '<h4>Status</h4>'+
				    '<input id="inAir" type="button" value="In the air" onClick="changeState(\'InAir\');" disabled="disabled"/>'+
				    '<input id="landed" type="button" value="Landed" onClick="changeState(\'Landed\');" disabled="disabled" />'+
				    '<input id="atTerminal" type="button" value="At terminal" onClick="changeState(\'AtTerminal\');" disabled="disabled" />';
		    document.getElementById("status").innerHTML = html;
		    $("#CatalogOfGates").show();
		    
        }
        if(state === "AtTerminal") {
        	console.log("LA 3");
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
    
    
    document.getElementById("ChangeStatusButton").onclick = function () {
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
        console.log("Je change");
    	VisualizationBased_Status(document.getElementById('ChangeStatusButton').value);
    	
    };
    function VisualizationBased_Status(status){
    	console.log("Dans Vizu:"+status);
    	switch(status) {
    	  case "InAir":
    		document.getElementById('ChangeStatusButton').value = "AwaitingTrackForLanding";
    		
      	    break;
    	  case "AwaitingTrackForLanding":
    	    document.getElementById('ChangeStatusButton').value = "Landing";
    	    
    		  break;
    	  case "Landing":
      	    document.getElementById('ChangeStatusButton').value = "Landed";
    		  break;  
      	  case "Landed":
        	document.getElementById('ChangeStatusButton').value = "AwaitingGateAssigment";
            
            break;
      	  case "AwaitingGateAssigment":
          	document.getElementById('ChangeStatusButton').value = "AtTerminal";
      		  
  		    break;  
      	  case "AtTerminal":
            document.getElementById('ChangeStatusButton').value = "AwaitingTrackForTakeOff";
      		$("#CatalogOfServices").show();
    		
        	break;
      	  case "AwaitingTrackForTakeOff":
            document.getElementById('ChangeStatusButton').value = "TakingOff";
            $("#CatalogOfServices").hide();
            
  		    break;
      	  case "TakingOff":
            document.getElementById('ChangeStatusButton').value = "InAir";
            
  		    break; 
    	  default:
    	  	console.log(status);
    	} 
    }
    
    function changeValueState(updateObject) {
    	let plane = updateObject[0]
    	let state = updateObject[2]
    	console.log("Mon etat:"+state)
    	document.getElementById("planeState").innerHTML = state;
    }
    
    function hideElements() {
    	$("#CatalogOfServices").hide();
    	$("#CatalogOfGates").hide();
    }
    
    function cancelService(updateObject) {
    	let plane = updateObject[0] // Kind of hack, single updated service is first element in updateObject
        let service = updateObject[2]
        let nameService = service.name; console.log("Dans cancelService:"+service.name);

		if(nameService.startsWith("Refuel")) {
			alert("The refuel service has been canceled. You can launch it again");
			let html = '<input id="refuel" type="button" class="waves-effect waves-light btn-small" value="REFUEL" onClick="processService(\'refuel\',\'Refuel service\');" />';
			document.getElementById("refuel").innerHTML = html; 
			//$("#gateServices").show(); $("#valueOfGateAffected").hide(); 
			
			
		}
    	
 
    }
    
</script>

</body>
</html>