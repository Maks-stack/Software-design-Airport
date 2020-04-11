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
    <h1>Track manager</h1>

<div id="requestMocker">
    <p>This button will create a new plane object</p>
    <input id="mockTrackRequest" type="button" value="Mock Track Request" />
</div>

<div id="newRequestsWidget">
       <h2>Incoming requests</h2>
       <c:if test="${not empty newTrackRequests}">
               <table class="greyGridTable" style="width: 300px">
                   <tr>
                       <th>Plane ID</th>
                       <th>Track requested</th>
                       <th>Available tracks</th>
                   </tr>
                   <c:forEach items="${newTrackRequests}" var="request">
                           <tr>
                               <td>${request.plane.planeId}</td>
                               <td>${request.trackRequested.type}</td>
                               <td ALIGN="center">
                                  <select onchange="assignTrack(value);">
                                      <c:forEach items="${availableTracks}" var="track">
                                           <option value=${track.trackID}>Assign track ${track.trackID}</option>
                                      </c:forEach>
                                  </select>
                               </td>
                           </tr>
               </c:forEach>
               </table>
       </c:if>
</div>

<div id="TrackPanel">
    <div id="TracksList" class="TracksWidget">
        <c:if test="${not empty allTracks}">
            <table id="allTracks" class="greyGridTable" style="width: 300px">
                <tr>
                    <th>Track ID</th>
                    <th>Track type</th>
                    <th>Track available</th>
                </tr>
                <c:forEach items="${allTracks}" var="track">
                    <tr id="${track.trackID}">
                        <td>${track.trackID}</td>
                        <td>${track.type.type}</td>
                        <td>${track.state.state}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>

<script>
    connectTrackWebsocket();
    function connectTrackWebsocket() {
       var socket = new SockJS('/track-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/tracks/updates', function (update) {
             updateTrackStatus(JSON.parse(update.body))
          });
       });
    };

    function updateTrackStatus(update){
            $('tr').each(function(){
                if($(this).attr('id') == update.trackID){
                    $(this).html("<td>"+update.trackID+"</td><td>"+update.type.type+"</td><td>"+update.state.state+"</td>");
                }
            })
    }

    function assignTrack($i) {
         $.ajax({
                    type : "PATCH",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/assigntrack/" + $i,
                    complete: function(data) {
                            window.location.reload();
                        },
                    statusCode: {
                        409: function(xhr) {
                          console.log(xhr);
                          alert ("Track not available");
                        }
                      }
                });
    };

    document.getElementById("mockTrackRequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/mocktrackrequest",
                    complete: function(data) {
                            window.location.reload();
                        }
            });
    };
</script>

</body>

</html>