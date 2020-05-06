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


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</head>


<body>
<div class="container">
	<h4>Service Team Manager</h4>
	
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

<div id="overwiewOfServices" class = "widget">
	       	    <table class="greyGridTable" style="width: 300px" id="overviewTable">
	       	    <tr>
	                   <th>Service</th>
	            </tr>
	               <c:forEach items="${allServices}" var="serviceGroup">
	                       <tr>
	                       <td>${serviceGroup.key.value}</td>
	                       <td><a class="btn-floating btn-small waves-effect waves-light red" onClick="RemoveTemporalName('${serviceGroup.key.value}')">-</a>
	                       <a class="btn-floating btn-small waves-effect waves-light red" onClick="AddTemporalName('${serviceGroup.key.value}')">+</a></td>    
	                       </tr>
	               </c:forEach>
	           </table>
	</div>
<hr>

<script>
	$( document ).ready(function() {
		
	});
	
	
    connectServicesWebsocket();
    function connectServicesWebsocket() {
       var socket = new SockJS('/services-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          stompClient.subscribe('/services/updates', function (update) {
            updateObject = JSON.parse(update.body);
            updateOverview(updateObject)
            console.log(updateObject)
          });          
       });
    }
    
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
	

	function AddTemporalName(ServiceGroup){
		$.ajax({
            type: "POST",
            url:  "http://"+window.location.hostname+":8080/addserviceteam",
            data: {
                serviceSelected : ServiceGroup
              },
            statusCode: {
                409: function(xhr) {
                    alert(xhr.responseJSON.error.message);
                }
            },
            success :
            	function(data){

            }  
        })
	}
	function RemoveTemporalName(ServiceGroup){
		$.ajax({
            type: "POST",
            url:  "http://"+window.location.hostname+":8080/removeserviceteam",
            data: {
                serviceSelected : ServiceGroup
              },
            statusCode: {
                409: function(xhr) {
                    alert(xhr.responseJSON.error.message);
                }
            },
            success :
            	function(data){

            }  
        })
	}	
	
	
</script>
</body>
</html>