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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"</head>
<body>
<h1>Service manager</h1>

<div id="planeMocker">
    <p>This button will create a new plane object and create a random service request</p>
    <input id="mockplanerequest" type="button" value="Mock Plane Request" />
</div>

<div id="serviceAssigner">
    <p>This button will change random service state and hopefully send update through websocket</p>
    <input id="mockassignservice" type="button" value="Assign random service" />
</div>

<div id="newRequestsWidget">
       <h2>New requests</h2>
       <c:if test="${not empty newServiceRequests}">
               <table class="greyGridTable" style="width: 300px">
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
                                     <select>
                                            <c:forEach items="${serviceGroup.value}" var="service">
                                               <option value=${service.id}>${service.name}</option>
                                            </c:forEach>
                                     </select>
                                </c:if>
                           </c:forEach>
                           </td>
                           <td headers="NR_Button"><div class="button-newRequest"><button> Click Me </button></div>
                       </tr>
                   </c:forEach>
               </table>
       </c:if>
</div>

<div id="overwiewOfServices">
       <h2>Overview of the available services</h2>
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

<div id="activeServices">
  <h2>Active Services</h2>

  <div>
        <table id="activeServicesTable" class="greyGridTable" >
                <tr>
                    <th>Plane ID</th>
                    <th>Service ID</th>
                    <th>Service available</th>
                </tr>
                <tr id="noServicesWarning">
                    <td>
                        <h2>No ActiveServices</h2>
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
                        <td><button class="cancelServiceButton" id="cancelService${service.id}">Cancel &#9888;</button></td>
                        </tr>
                    </c:if>
                     </c:forEach>
                </c:forEach>
        </table>
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
        '<td><button class="cancelServiceButton" id="cancelService' + update.id + '">Cancel &#9888;</button></td>' +
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

    connectServicesWebsocket();
    function connectServicesWebsocket() {
       var socket = new SockJS('/services-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/services/updates', function (update) {
            updateObject = JSON.parse(update.body);
            updateServiceStatus(updateObject)
            updateOverview(updateObject)
            //console.log(updateObject)
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

</script>
</body>
</html>