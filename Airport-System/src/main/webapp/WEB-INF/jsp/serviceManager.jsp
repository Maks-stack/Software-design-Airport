<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="css/serviceManager.css" rel="stylesheet">
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>
<body>
    <h1>Service manager</h1>

<div id="planeMocker">
    <p>This button will create a new plane object and create a random service request</p>
    <input id="mockplanerequest" type="button" value="Mock Plane Request" />
</div>

<div id="serviceAssigner">
    <p>This button will change random service state and hopefully send update through websocket</p>
    <input id="assignservice" type="button" value="Assign random service" />
</div>

<div id="ServicesPanel">
    <div id="GateServices" class="ServicesWidget">
    <h2>Gate Services (populated via Spring model in jsp)</h2>
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

<div id="newRequestsWidget">
       <h2>New requests</h2>
       <c:if test="${not empty newServiceRequests}">
               <table class="greyGridTable" style="width: 300px">
                   <tr>
                       <th>Plane ID</th>
                       <th>Service requested</th>
                       <th>Available services</th>
                   </tr>
                   <c:forEach items="${newServiceRequests}" var="request">
                           <tr>
                               <td>${request.plane.planeId}</td>
                               <td>${request.serviceRequested}</td>
                               <td ALIGN="center">
                                  <select>
                                      <c:forEach items="${refuelServices}" var="service">
                                           <option value=${service.name}>${service.name}</option>
                                      </c:forEach>

                                      </select>

                               </TD>
                           </tr>
               </c:forEach>
               </table>
               </c:if>
</div>

<script>
    connectServicesWebsocket();
    function connectServicesWebsocket() {
       var socket = new SockJS('/services-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/services/updates', function (update) {
             updateServiceStatus(JSON.parse(update.body))
          });
       });
    }

    function updateServiceStatus(update){
            $('tr').each(function(){
                if($(this).attr('id') == update.name){
                    $(this).html("<td>"+update.name+"</td><td>"+update.available+"</td>");
                }
            })
    }

    document.getElementById("assignservice").onclick = function () {
     $.ajax({
                        type : "GET",
                        contentType : 'application/json; charset=utf-8',
                        url : "http://localhost:8080/assignservice"
            });
    };


    document.getElementById("mockplanerequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/mockplanerequest"
            });
    };
</script>

</body>

</html>