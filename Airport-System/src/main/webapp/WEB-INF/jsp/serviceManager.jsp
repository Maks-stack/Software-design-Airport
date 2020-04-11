<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<div id="tabs">
  <ul>
    <li><a href="#tabs-1">Gate Services</a></li>
    <li><a href="#tabs-2">Refuel Services</a></li>
    <li><a href="#tabs-3">Catering Services</a></li>
  </ul>
  <div id="tabs-1">
        <div id="ServicesPanel">
            <div id="GateServices" class="ServicesWidget">
            <c:if test="${not empty gateServices}">
                <table id="gateServices" class="greyGridTable" style="width: 300px">
                    <tr>
                        <th>Service ID</th>
                        <th>Service available</th>
                    </tr>
                    <c:forEach items="${gateServices}" var="service">
                        <tr id="${service.name}">
                            <td>${service.name}</td>
                            <td>${service.available}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
            </div>
        </div>
  </div>
  <div id="tabs-2">
    <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
  </div>
  <div id="tabs-3">
    <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
    <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
  </div>
</div>





<div id="newRequestsWidget">
       <h2>New requests</h2>
       <c:if test="${not empty newServiceRequests}">
               <table class="greyGridTable" style="width: 300px">
                   <tr>
                       <th id="NR_PlaneID">Plane ID</th>
                       <th id="NR_ServiceRequested">Service requested</th>
                       <th id="NR_AvailableServices">Available services</th>
                       <th id="NR_Button">Test</th>
                   </tr>
                   <c:forEach items="${newServiceRequests}" var="request">
                           <tr>
                               <td headers="NR_PlaneID">${request.plane.planeId}</td>
                               <td headers="NR_ServiceRequested">${request.serviceRequested}</td>
                               <td ALIGN="center" headers="NR_AvailableServices">
                                  <select>
                                      <c:choose>
                                          <c:when test="${request.serviceRequested=='Gate'}">
                                              <c:forEach items="${gateServices}" var="service">
                                                   <option value=${service.name}>${service.name}</option>
                                              </c:forEach>
                                          </c:when>
                                          <c:when test="${request.serviceRequested=='Refuel'}">
                                              <c:forEach items="${refuelServices}" var="service">
                                                   <option value=${service.name}>${service.name}</option>
                                              </c:forEach>
                                          </c:when>
                                       </c:choose>
                                   </select>

                               </TD>
                               <td headers="NR_Button"><div class="button-newRequest"><button> Click Me </button></div>
                           </tr>
               </c:forEach>
               </table>
               </c:if>
</div>

<div id="overwiewOfServices">
       <h2>Overview of the available services</h2>
       	   <c:set var="nbGate" scope="session" value="0"/>
       	   <c:set var="nbRefuel" scope="session" value="0"/>
           <table class="greyGridTable" style="width: 300px">
               <tr>
                   <th>Service</th>
                   <th>Number of available teams</th>
               </tr>
                <c:forEach items="${gateServices}" var="service">
                	<c:if test="${service.available}">
                    	<c:set var="nbGate" value="${nbGate + 1}"/>
            		</c:if>	
                </c:forEach>
                <c:forEach items="${refuelServices}" var="service">
                    <c:if test="${service.available}">
                        <c:set var="nbRefuel" value="${nbRefuel + 1}"/>
                    </c:if>
                </c:forEach>
                
               <tr>
                   <th>Gate</th>
                   <th id="gate">${nbGate}</th>
               </tr>
               <tr>
                   <th>Refuel</th>
                   <th id="refuel">${nbRefuel}</th>
               </tr>
           </table>
</div>

<div id="activeServices">
  <h2>Active Services</h2>

  <div>
  	<c:if test="${not empty gateServices}">
                <table id="activeServicesTable" class="greyGridTable" style="width: 300px">
	                    <tr>
	                        <th>Service ID</th>
	                        <th>Service available</th>
	                    </tr>
	                    <!-- 
	                    <c:forEach items="${gateServices}" var="service">
		                     <c:if test ="${ not service.available}">
		                        <tr id="${service.name}">
		                            <td>${service.name}</td>
		                            <td>${service.available}</td>
		                        </tr>
	                    	</c:if>	
	                    </c:forEach>
	                    <c:forEach items="${refuelServices}" var="service">
		                    <c:if test="${ not service.available}">
		                        <tr id="${service.name}">
		                            <td>${service.name}</td>
		                            <td>${service.available}</td>
		                        </tr>
	                        </c:if>
	                    </c:forEach>
	                     -->
                </table>
    </c:if>
    
  
 </div>

<script>


	var serviceRow = function(update){
		
		var output = 
		'<tr id="' + update.name + '">' +
        '<td>' + update.name + '</td> ' +
        '<td>'+ update.available + '</td> ' +
        '<td><button id="cancelService' + update.name + '">Cancel</button></td>'
        '</tr>' 
		
		return output
	}

      $( function() {
        $( "#tabs" ).tabs();
      } );

    connectServicesWebsocket();
    function connectServicesWebsocket() {
       var socket = new SockJS('/services-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/services/updates', function (update) {
             updateServiceStatus(JSON.parse(update.body))
            console.log(JSON.parse(update.body))	 
            
          });
       });
    }

    function updateServiceStatus(update){
    	
    		console.log("UPDATE!!!!")
    		console.log(update)
    		
    		/*
            $('tr').each(function(){
                if($(this).attr('id') == update.name){
                    $(this).html("<td>"+update.name+"</td><td>"+update.available+"</td>");
                }
            })
            */
            
           if(update.available === false){
	            $('#activeServicesTable').append(serviceRow(update))
            	
	            document.getElementById("cancelService"+update.name).onclick  = function(){
           			
       				console.log("#cancelService"+update.name +" clicked!!!!!!!!!")            			
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
           				data : { serviceId: update.name },
               		});
           		}
    		}
    		if(update.available){
    			document.getElementById(update.name).remove()
    		}
    }

    document.getElementById("mockassignservice").onclick = function () {
     $.ajax({
                        type : "GET",
                        contentType : 'application/json; charset=utf-8',
                        url : "http://localhost:8080/mockassignservice",
                        statusCode: {
                            409: function(xhr) {
                              console.log(xhr);
                              alert ("Service not available");
                            }
                          }
            });
            
        <c:set var="nbGates" scope="session" value="0"/>
   	   	<c:set var="nbRefuels" scope="session" value="0"/>
   	   	
   	   	<c:forEach items="${gateServices}" var="service">
        	<c:if test="${service.available}">
            	<c:set var="nbGates" value="${nbGates + 1}"/>
    		</c:if>	
        </c:forEach>
        <c:forEach items="${refuelServices}" var="service">
            <c:if test="${service.available}">
                <c:set var="nbRefuels" value="${nbRefuels + 1}"/>
            </c:if>
        </c:forEach>
        
        document.getElementById("gate").innerHTML = ${nbGates};
        document.getElementById("refuel").innerHTML = ${nbRefuels};
    };
    

    $("body").on( "click", ".button-newRequest", function(){

        $.ajax({
            type: "POST",
            url:  "http://localhost:8080/assignservice",
            data: {
                PlaneID : $(this).closest('tr').find('td:eq(0)').html(),
                ServiceRequested : $(this).closest('tr').find('td:eq(1)').html(),
                ServiceSelected : $(this).closest('tr').find('td:eq(2) :selected').text()
              },
            statusCode: {
                409: function(xhr) {
                console.log(xhr);
                alert ("Service not available");
                }
            }
        }).done(function(data){
             $( "#newRequestsWidget" ).load(window.location.href + " #newRequestsWidget" );
             $( "#activeServicesWidget" ).load(window.location.href + " #activeServicesWidget" );
        });

    });
    document.getElementById("mockplanerequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/mockplanerequest"
            });
            $( "#newRequestsWidget" ).load(window.location.href + " #newRequestsWidget" );
    };
</script>

</body>

</html>