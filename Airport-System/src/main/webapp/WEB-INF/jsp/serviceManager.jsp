<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
<script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
<script src="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<link href="css/serviceManager.css" rel="stylesheet">
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</head>


<body>
<div class="container">
	<h4>Service manager</h4>
	<hr>
	<div id="planeMocker" class = "widget">
	    <p>This button will create a new plane object and create a random service request</p>
	  	<button id="mockplanerequest" class="waves-effect waves-light btn-small">Mock Plane Request</button>	    
	</div>	
	<div id="serviceAssigner" class = "widget">
	    <p>This button will change random service state and hopefully send update through websocket</p>
	    <button id="mockassignservice" class="waves-effect waves-light btn-small">Assign random service</button>
	</div>
	<hr>
	<div id="newRequestsWidget" class = "widget">
	       <h4>New requests</h4>
	       
               <table id ="serviceRequestTable" class="greyGridTable">
	               <c:if test="${not empty newServiceRequests}">
	                   <tr>
	                       <th id="NR_ServiceRequestID">Request ID</th>
	                       <th id="NR_PlaneID">Plane ID</th>
	                       <th id="NR_ServiceRequested">Service requested</th>
	                       <th id="NR_AvailableServices">Available services</th>
	                       <th id="NR_Button">Test</th>
	                   </tr>
	                   <c:forEach items="${newServiceRequests}" var="request">
	                       <tr>
	                           <td headers="NR_ServiceRequestID">${request.id}</td>
	                           <td headers="NR_PlaneID">${request.plane.planeId}</td>
	                           <td headers="NR_ServiceRequested">${request.serviceRequested}</td>
	                           <td ALIGN="center" headers="NR_AvailableServices">
	                           <c:forEach items="${allServices}" var="serviceGroup">
	                                <c:if test="${serviceGroup.key.key eq fn:toLowerCase(request.serviceRequested)}">
	                                     <select class = "browser-default">
	                                            <c:forEach items="${serviceGroup.value}" var="service">
		                                            <c:if test="${ service.available }">
		                                               <option value="${service.id}">${service.name}</option>
	                                               </c:if>
	                                            </c:forEach>
	                                     </select>
                                     </c:if>	                                
	                           </c:forEach>
	                           </td>
	                           <td headers="NR_Button"><div><button class="button-newRequest waves-effect waves-light btn-small">Assign</button></div>
	                       </tr>
	                   </c:forEach>
	               </c:if>
               </table>
	       
	</div>
	<hr>
	<div id="overwiewOfServices" class = "widget">
	       <h4>Overview of the available services</h4>
	       <div id="overviewContainer">
	           <table class="greyGridTable" style="width: 300px" id="overviewTable">
	               <tr>
	                   <th>Service</th>
	                   <th>Number of available teams</th>
	               </tr>
	
	               <c:forEach items="${allServices}" var="serviceGroup">
	               <c:set var = "countAvailable" value = "0"/>
	                   <c:forEach items="${serviceGroup.value}" var="service">
	                        <c:if test="${service.available}">
	                            <c:set var = "countAvailable" value = "${countAvailable +1}"/>
	                        </c:if>
	                   </c:forEach>
	                   <tr>
	                       <td>${serviceGroup.key.value}</td>
	                       <td id="${serviceGroup.key.key}">${countAvailable}</td>
	                   </tr>
	               </c:forEach>
	           </table>
	       </div>
	</div>
	<hr>
	<div id="activeServices" class = "widget">
	  <h4>Active Services</h4>
	
	  <div>
	        <table id="activeServicesTable" class="greyGridTable" >
	                <tr>
	                    <th>Plane ID</th>
	                    <th>Service ID</th>
	                    <th>Service available</th>
	                </tr>
	                <tr id="noServicesWarning">
	                    <td>
	                        <h4>No ActiveServices</h4>
	                    </td>
	                </tr>
	
	                <c:forEach items="${allServices}" var="serviceGroup">
	                    <c:forEach items="${serviceGroup.value}" var="service">
	                     <c:if test ="${ not service.available}">
	                        <tr id="${service.id}">
	                        <td> Plane ID: ${service.planeId} </td>
	                        <td>${service.name}</td>
	                        <td>${service.available}</td>
	                        <td>timeStarted:${service.timeStarted}</td>
	                        <td id="counter${service.id}">time left: ${service.duration}</td>
	                        <td style="width:50px">
	                            <div class="progressBar">
	                                <div id="progressIndicator${service.id}" data-timeStarted="${service.timeStarted}" data-duration="${service.duration}" class="progressBarIndicator"></div>
	                            </div>
	                        </td>
	                        <td><button class="cancelServiceButton waves-effect waves-light btn-small red" id="cancelService${service.id}">Cancel &#9888;</button></td>
	                        </tr>
	                    </c:if>
	                     </c:forEach>
	                </c:forEach>
	        </table>
	 </div>
</div>
<script>
	$( document ).ready(function() {
		var tableBody = $("#activeServicesTable > tbody")
		console.log(tableBody.children().length)
		if(tableBody.children().length > 1){
			tableBody.children().each(function(){
				var serviceID = $(this).attr("id")
				var button = document.getElementById("cancelService"+serviceID)
				if(button){
					button.onclick  = function(){
	           			$.ajax({
	                           type : "GET",
	                           contentType : 'application/json; charset=utf-8',
	                           url : "http://localhost:8080/cancelService",
	                           statusCode: {
	                               409: function(xhr) {
	                                 console.log(xhr);
	                                 alert ("Service not available");
	                               }
	                             },
	           				data : { serviceId: serviceID},
	               		});
	           		}
					
				
					var startingTime = new Date((document.getElementById("progressIndicator"+serviceID).getAttribute('data-timestarted'))).getTime()/1000
					var duration = parseInt(document.getElementById("progressIndicator"+serviceID).getAttribute('data-duration'))/1000 
					var timePassed = (new Date().getTime() / 1000) - startingTime
					var timeLeft = Math.round(duration - timePassed)
					var progressIndicatorWidth = 1
		        	var updateCounter = setInterval(function() {
			        	var element =  document.getElementById("counter"+serviceID)
			        	if (element == null)
			        	{
			        		clearInterval(updateCounter);
			        		return
			        	}
			    		timeLeft--
			    		progressIndicatorWidth = 100 - (timeLeft / duration)*100
					   	var difference = (startingTime - Date.now())/1000;
					   	
					   	if(document.getElementById("counter"+serviceID)){
					   		document.getElementById("counter"+serviceID).innerHTML = ("time left: " + timeLeft + " sec")
					   	}
					   	
					   	document.getElementById("progressIndicator" + serviceID).style.width = progressIndicatorWidth + "%"
				 	}, 1000)
				}
			})
			
		}
		
	});
	var serviceRow = function(update){
		
		var startingTime = parseInt(update.duration)/1000 
		var timeLeft = startingTime
		
		var output = 
		'<tr id="' + update.id + '">' +
		'<td> Plane ID: ' + update.planeId +' </td>' +
        '<td>' + update.name + '</td> ' +
        '<td>'+ update.available + '</td> ' +
        '<td>timeStarted:' + update.timeStarted
        + '</td>' +
        '<td id="counter'+update.id + '">time left: ' + timeLeft + ' sec</td>' +
        '<td style="width:50px">' +
        '	<div class="progressBar">' +
        ' 		<div id="progressIndicator' + update.id + '" class="progressBarIndicator"></div>' +
       	'	</div>'+
        '</td>'+
        '<td><button class="cancelServiceButton waves-effect waves-light btn-small red" id="cancelService' + update.id + '">Cancel &#9888;</button></td>' +
        '</tr>'
        
        var progressIndicatorWidth = 1
        
        var updateCounter = setInterval(function() {
        	
        	var element =  document.getElementById("counter"+update.id)
        	if (element == null)
        	{
        		clearInterval(updateCounter);
        		return
        	}
        	
    		timeLeft--
    		progressIndicatorWidth = 100 - (timeLeft / startingTime)*100
		   	// var difference = (dateCreated - Date.now())/1000;
		   	
		   	if(document.getElementById("counter"+update.id)){
		   		document.getElementById("counter"+update.id).innerHTML = ("time left: " + timeLeft + " sec")
		   	}
		   	document.getElementById("progressIndicator" + update.id).style.width = progressIndicatorWidth + "%"
		 }, 1000)
        	
		return output
	}
	
	var serviceRequestRow = function(update){

		var requestId = update[0].id
		var planeId = update[0].plane.planeId
		var serviceName = update[0].serviceRequested
		
		var output =
		'<tr>' +
        '<td headers="NR_ServiceRequestID">' + requestId + '</td>' +
        '<td headers="NR_PlaneID">' + planeId + '</td>' +
        '<td headers="NR_ServiceRequested">' + serviceName + '</td>' +
        '<td ALIGN="center" headers="NR_AvailableServices">' +
	    '    <select id="'+serviceName+'"class="browser-default planeRequestSelector">' +      
        '    	<option value=1>Test</option>' +
	    '   </select>' +
        '</td>' +
        '<td headers="NR_Button">' +
        '	<div>' +
        '		<button class="button-newRequest waves-effect waves-light btn-small">Assign</button> ' +
        '	</div>' +
       	'</td>' +
    	'</tr>'
    	
    	return output
		
	}

    connectServicesWebsocket();
    function connectServicesWebsocket() {
       var socket = new SockJS('/services-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          stompClient.subscribe('/services/updates', function (update) {
            updateObject = JSON.parse(update.body);
            updateServiceStatus(updateObject)
            updateOverview(updateObject)
            console.log(updateObject)
            updateServiceRequests(updateObject)   
          });          
       });
    }

    function updateServiceStatus(updateObject){
        let update = updateObject[0] // Kind of hack, single updated service is first element in updateObject
           if(update.available === false){
	            $('#activeServicesTable').append(serviceRow(update))
                document.getElementById("cancelService"+update.id).onclick  = function(){
                    $.ajax({
                           type : "GET",
                           contentType : 'application/json; charset=utf-8',
                           url : "http://"+window.location.hostname+":8080/cancelService",
                           statusCode: {
                               409: function(xhr) {
                                alert(xhr.responseJSON.error.message);
                               }
                             },
                        data : { serviceId: update.id },
                    });
                }
    	   }
           else if(update.available || update.cancelled){
    			$('#activeServicesTable > tbody').children().each(function(index,element){
	    				if($(element).attr("id") === update.id){
	    					$(element).remove()
	    				}	
    		    })
    		}
           
           if($('#activeServicesTable > tbody').children().length > 1) {
        	   $("#noServicesWarning").hide()
           }else{
        	   $("#noServicesWarning").show()
           }
    }

    document.getElementById("mockassignservice").onclick = function () {
     $.ajax({
        type : "GET",
        contentType : 'application/json; charset=utf-8',
        url : "http://"+window.location.hostname+":8080/mockassignservice",
        statusCode: {
            409: function(xhr) {
              alert(xhr.responseJSON.error.message);
            }
          }
     });
    };

    $("body").on( "click", ".button-newRequest", function(){    	
    	console.log("UTPUT")
    	console.log($(this))
    	var element = $(this)
    	console.log($(this).closest('tr').find('td:eq(0)').html())
    	console.log($(this).closest('tr').find('td:eq(3) :selected').val())    	
        $.ajax({
            type: "POST",
            url:  "http://"+window.location.hostname+":8080/assignservice",
            data: {
                requestId: $(this).closest('tr').find('td:eq(0)').html(),
                serviceSelected : $(this).closest('tr').find('td:eq(3) :selected').val()
              },
            statusCode: {
                409: function(xhr) {
                    alert(xhr.responseJSON.error.message);
                }
            },
            success :
            	function(data){
            		console.log("remove myself")
            		console.log(element)
            		console.log(element.closest('tr'))
            		element.closest('tr').remove()
            }  
        })
    });

    document.getElementById("mockplanerequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://"+window.location.hostname+":8080/mockplanerequest"
            });
    };

    function updateOverview(updateObject){
          html = "<table class='greyGridTable' style='width: 300px' id='overviewTable'>" +
                  "<tr>" +
                        "<th>Service</th>" +
                        "<th>Number of available teams</th>" +
                  "</tr>";
          let services = updateObject[1] //List of services is second element in return array
          for (serviceType in services) {
                let countAvailable = 0;
                for (var i = 0; i < services[serviceType].length; i++) {
                    if(services[serviceType][i].available){
                        countAvailable++;
                    }
                }
          let serviceName = serviceType.split("=")[1] // this is a bit of a hack which I did not resolve. We should fix the backend to get nicer object, so stuff like this is not required
          html+= "<tr>"+
                    "<td>" +serviceName+ "</td>" +
                    "<td>" +countAvailable+"</td>"+
                 "</tr>";
          }
          document.getElementById("overviewContainer").innerHTML = html;
    }
    
    function updateServiceRequests(update){
		
    	if($('#serviceRequestTable').children().length > 0) {
	 		updateServiceRequestSelectors(update)	
	 	}
    	
    	if(update[0].type === "undefined" || typeof update[0].type === 'undefined' ) 
   			return null
   			
	 	$('#serviceRequestTable').append(serviceRequestRow(update))
	 	
 		updateServiceRequestSelectors(update) 
    	    	    	
    }
    function updateServiceRequestSelectors(update){
    	
    	$('.planeRequestSelector').empty()   	 
   	 	console.log(update)    	 
   	 	
   	 	$('.planeRequestSelector').each(function(){   
   	 		var htmlId = $(this).attr('id')
   	 		
   	 		var services = update[1] //List of services is second element in return array
          	for (serviceType in services) {
          		var serviceName = serviceType.split("=")[1] 
          		console.log(serviceName)
          		console.log(htmlId)
          		if(serviceName.includes(htmlId)){
          			for(service in services[serviceType]) {
          				if(services[serviceType][service].available) {
	       	 				$(this).append('<option value='+ services[serviceType][service].id +'>' + services[serviceType][service].name + '</option>')
          				}
	       	 		}
          		}	
          	}
   	 	})     	
    }

</script>
</body>
</html>