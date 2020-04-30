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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"></head>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>


<body>


<div id="planeStateList" class = widget>
    <h4>Plane State List</h4>
    <table class="greyGridTable" style="width: 300px" id="planeStateTable">
        <tr>
            <th>Plane</th>
            <th>State</th>
        </tr>
        <c:forEach items="${planes}" var="planes">
            <c:set var = "changeState" value = "InAir"/>
            <c:forEach items="${planes.value}" var="state">
                <c:if test="${planes.proceedToNextState}">
                    <c:set var = "changeState" value = "${changeState.proceedToNextState}"/>
                </c:if>
            </c:forEach>
            <tr>
                <td>${planes.key.value}</td>
                <td id="${planes.key.key}">${proceedToNextState}</td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="trackStateList" class = widget>
    <h4>Plane State List</h4>
    <table class="greyGridTable" style="width: 300px" id="trackStateTable">
        <tr>
            <th>Track</th>
            <th>State</th>
        </tr>

        <c:forEach items="${allTracks}" var="tracks">
            <c:set var = "changeState" value = "available"/>
                <c:forEach items="${tracks.value}" var="service">
                    <c:if test="${tracks.available}">
                        <c:set var = "changeState" value = "${changeState.proceedToNextState}"/>
                    </c:if>
                </c:forEach>
                <tr>
                    <td>${tracks.key.value}</td>
                    <td id="${tracks.key.key}">${proceedToNextState}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>





</body>
</html>>
<head>
<script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
</head>
<body>
<c:if test="${not empty planeObj}">
    <p id="planeid">${planeObj}</p>
</c:if>

<div id="requestGateService">
    <input id="requestGateService" type="button" value="Request gate service" />
</div>

<script>
    connectPublicInterfaceWebsocket();

    function connectPublicInterfaceWebsocket() {
       var socket = new SockJS('/public-interface');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/public-interface/${planeObj}/updates', function (update) {
             console.log(JSON.parse(update.body))
          });
       });
    }


    document.getElementById("requestGateService").onclick = function () {
        let planeId = document.getElementById('planeid').innerHTML;
        let data = {"planeId": planeId, "service":"Bus"};
         $.ajax({
                            type : "POST",
                            data: JSON.stringify(data),
                            contentType : 'application/json; charset=utf-8',
                            url : "http://localhost:8080/plane/requestservice",
                            success: function(data){
                                console.log(data);
                              },
                            error: function(data){
                                console.log(data);
                            },
                });
        };



</script>

</body>
</html>