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
       <table class="greyGridTable" style="width: 300px" id="requestsTable">
           <tr>
               <th>Plane ID</th>
               <th>Available tracks</th>
               <th></th>
           </tr>
           <c:forEach items="${newTrackRequests}" var="request">
                   <tr id="${request.plane.id}">
                       <td>${request.plane.id}</td>
                       <td ALIGN="center">
                          <select>
                              <c:forEach items="${availableTracks}" var="track">
                                   <option>${track.trackID}</option>
                              </c:forEach>
                          </select>
                       </td>
                       <td><input onclick="assignTrack(this);" type="button" value="Assign track" />
                   </tr>
       </c:forEach>
       </table>
</div>

<div id="TrackPanel">
    <h2>Tracks list</h2>
    <div id="TracksList" class="TracksWidget">
            <table id="TracksTable" class="greyGridTable" style="width: 300px">
                <tr>
                    <th>Track ID</th>
                    <th>Track available</th>
                    <th>Assigned Plane</th>
                    <th></th>
                </tr>
                <c:forEach items="${allTracks}" var="track">
                    <tr id="${track.trackID}">
                        <td value=${track.trackID}>${track.trackID}</td>
                        <td>${track.state.state}</td>
                        <td>${track.assignedPlane.id}</td>
                        <td><input onclick="unassignTrack(this)" type="button" ${ track.state.state eq "assigned"? '' : 'disabled="disabled"'} value="Unassign track" /></td>
                    </tr>
                </c:forEach>
            </table>
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
             console.log("track update", update);

             updateTrackStatus(JSON.parse(update.body))
          });
        });
    };

    connectRequestWebsocket();
    function connectRequestWebsocket() {
       var socket = new SockJS('/track-websocket');
       stompClient = Stomp.over(socket);
       stompClient.connect({}, function (frame) {
          //console.log('Connected: ' + frame);
          stompClient.subscribe('/tracks/requests', function (update) {
            console.log("request update", update);
            updateRequestList(JSON.parse(update.body))
          });
        });
    };

    function updateTrackStatus(update){
        $('tr').each(function(){
            if($(this).attr('id') == update.trackID){
                var planeId = update.assignedPlane? update.assignedPlane.id : "";
                var isDisabled = planeId? "" : "disabled";
                $(this).html("<td>"+update.trackID+"</td><td>"+update.state.state+"</td><td>"+ planeId +"</td><td><input onclick='unassignTrack(this)' " + isDisabled + " type='button' value='Unassign track' /></td>");
            }
        })
    }

    function updateRequestList(update){
        $('#requestsTable > tbody').children().slice(1).each(function(index,element) {
            $(element).remove();
        })
        update.forEach(function (request) {
            $('#requestsTable').append(addRequestRow(request));
        })

    }

    var addRequestRow = function(request){
    	var output =
    	'<tr id="' + request.plane.id + '">' +
    	'<td>'+ request.plane.id +'</td>' +
    	'<td><select>'+ addOptions(request.availableTracks) + '</select></td>' +
        '<td><input onclick="assignTrack(this);" type="button" value="Assign track" />' +
        '</tr>'

        return output;
    }

    var addOptions = function(availableTracks){
        var options = "";

    	for (num in availableTracks) {
    	    options += "<option>" + availableTracks[num].trackID + "</option>"
    	}

    	return options;
    }

    function assignTrack(param){
       var planeId = $(param).parent().siblings(":first").text();
       var trackId = $(param).parent().closest('tr').find('td:nth-child(2)').children(":first").children("option:selected").val();
       $.ajax({
                  type : "PATCH",
                  contentType : 'application/json; charset=utf-8',
                  url : "http://localhost:8080/assigntrack/" + trackId + "?plane_id=" + planeId,
                  statusCode: {
                      500: function(xhr) {
                        console.log(xhr);
                        alert ("Track not available");
                      }
                    }
              });
    };

    function unassignTrack(param) {
         var trackId = $(param).parent().siblings(":first").text();

         $.ajax({
                    type : "PATCH",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/unassigntrack/" + trackId,
                    statusCode: {
                        500: function(xhr) {
                          console.log(xhr);
                          alert ("Track not assigned");
                        }
                      }
                });
    };

    document.getElementById("mockTrackRequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/mocktrackrequest"
            });
    };
</script>

</body>

</html>