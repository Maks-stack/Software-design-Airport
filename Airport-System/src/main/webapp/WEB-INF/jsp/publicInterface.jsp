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


<h4>Planes and states</h4>
<table class="greyGridTable" style="width: 300px" id="planeStateTable">
    <tr>
        <th>Plane ID</th>
        <th>State</th>
    </tr>
    <c:forEach items="${allPlanes}" var="plane">
        <tr>
            <td>${plane.planeId}</td>
            <td>${plane.state.getDisplayName()}</td>
        </tr>
    </c:forEach>
</table>



<script>


    connectPublicInterfaceWebsocket();
    connectTrackWebsocket();
    connectPublicInterfaceWebsocket();

    function connectTrackWebsocket() {
        var socket = new SockJS('/publicinterface-websocket');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/tracks/updates', function (update) {
                $.ajax({
                    type : "POST",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://"+window.location.hostname+":8080/publicinterface",
                    success: function() {
                        location.reload();
                    }
                });


            });
        });
    }


    function connectPublicInterfaceWebsocket() {
       var socket = new SockJS('/publicinterface-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          console.log('Connected: ' + frame);
          stompClient.subscribe('/publicinterface/updates', function (update) {
              $.ajax({
                  type : "POST",
                  contentType : 'application/json; charset=utf-8',
                  url : "http://"+window.location.hostname+":8080/publicinterface",
                  success: function() {
                      location.reload();
                  }
              });


          });
       });
    }
</script>
</body>
</html>





