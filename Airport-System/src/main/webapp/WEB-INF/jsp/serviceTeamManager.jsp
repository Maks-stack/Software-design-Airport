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
<div id="AddServices" class = "widget">
	<h4>Create services</h4>		
	<table class="greyGridTable" style="width: 500px" id="overviewTable">
	       	    <tr>
	                   <th>Name</th>
	                   <th>Service</th>
	                   <th>Create</th>
				</tr>
				<tr>
				<td><input id="InputName" type="text"></td>
				<td>
					<select class = "browser-default">
						<c:forEach items="${allServices}" var="request">
							<option value="${request.key.key}">${request.key.key}</option>
						</c:forEach>
					</select>
				</td>
				<td><button id="AddTeam" class="waves-effect waves-light btn-small">Add New Service Team</button></td>
				</tr>
	</table>
</div>

<hr>
<div id="DeleteServices" class = "widget">
	<h4>Delete services</h4>
	       	    <table class="greyGridTable" style="width: 500px" id="overviewTable">
	       	    <tr>
	                   <th>Service</th>
	                   <th>Select</th>
	                   <th>Remove</th>
	            </tr>
	               <c:forEach items="${allServices}" var="request">
	                       <tr>
	                       <td>${request.key.key}</td>
	                       <td>
	                           <c:forEach items="${allServices}" var="serviceGroup">
	                                <c:if test="${fn:toLowerCase(serviceGroup.key.key).startsWith(fn:toLowerCase(request.key.key))}">
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
	                       <td><button id="RemoveTeam" class="waves-effect waves-light btn-small">Remove Team</button></td>
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


    


    $("body").on( "click", "#AddTeam", function(){
    	
		Name = document.getElementById("InputName").value;
		
    	if( /^([a-zA-Z]){3,6}([0-9]){0,2}$/.test(Name) ){
    		$.ajax({
                type: "POST",
                url:  "http://"+window.location.hostname+":8080/addserviceteam",
                data: {
                    serviceSelected : $(this).closest('tr').find('td:eq(1) :selected').text(),
                    serviceName : document.getElementById("InputName").value
                  },
                statusCode: {
                    409: function(xhr) {
                        alert(xhr.responseJSON.error.message);
                    },
                  	500: function(xhr) {
                      alert(xhr.responseJSON.error.message);
                	}
                },
                success :
                	function(data){

                }  
            })
    	}else{
    		alert("Incorrect name for a service");
    	}
    	
		
	});
	
	$("body").on( "click", "#RemoveTeam", function(){ 
		
		$.ajax({
            type: "POST",
            url:  "http://"+window.location.hostname+":8080/removeserviceteam",
            data: {
                serviceSelected : $(this).closest('tr').find('td:eq(0)').text(),
                teamSelected : $(this).closest('tr').find('td:eq(1) :selected').text()
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

	});
	

	
</script>
</body>
</html>