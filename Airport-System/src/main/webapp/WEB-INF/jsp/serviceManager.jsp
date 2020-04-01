<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="css/serviceManager.css" rel="stylesheet">
</head>
<body>
    <h1>Service manager</h1>

<div id="planeMocker">
    <p>This button will create a new plane object and create a random service request</p>
    <input id="mockplanerequest" type="button" value="Mock Plane Request" onclick="mockplanerequest();" />
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
                <tr>
                    <td>${service.name}</td>
                    <td>${service.available}</td>
                </tr>
    </c:forEach>
    </table>
    </c:if>
    </div>

    <div id="RefuelServices" class="ServicesWidget">
        <h2>Refuel Services (populated via javascript)</h2>
        <table id="refuelServices" class="greyGridTable" style="width: 300px">
            <tr>
                <th>Service ID</th>
                <th>Service available</th>
            </tr>
        </table>
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
    getRefuelServices()
    window.setInterval(function(){
        getRefuelServices();
    }, 3000);

    function getRefuelServices(){
        $.ajax({
            type : "GET",
                contentType : 'application/json; charset=utf-8',
                url : "http://localhost:8080/getrefuelservices",
                success : function(services) {
                        console.log(services);
                        let t = document.getElementById("refuelServices");
                        let tableBody = t.getElementsByTagName('tbody')[0];
                        let tableRows = t.getElementsByTagName("tr");
                        services.forEach(function (item, index) {
                            let append = 1;

                            console.log(item, index);
                            for (var i = 0, row; row = t.rows[i]; i++) {
                                if(row.cells[0].innerHTML == item[0]){
                                    append = 0;
                                }
                            }
                            if (append){
                                var newRow = tableBody.insertRow(tableBody.rows.length);
                                newRow.innerHTML = "<tr><td>"+item[0]+"</td><td>"+item[1]+"</td></tr>";
                            }
                        });
                    },
                error: function(e){
                    console.log("ERROR: ", e);
                },
                done : function(e) {
                    console.log("DONE");
                }
        });
    }

    document.getElementById("mockplanerequest").onclick = function () {
        $.ajax({
                    type : "GET",
                    contentType : 'application/json; charset=utf-8',
                    url : "http://localhost:8080/mockplanerequest.html",
                    success : function(result) {
                        console.log("SUCCESS");
                    },
                    error: function(e){
                        console.log("ERROR: ", e);
                    },
                    done : function(e) {
                        console.log("DONE");
                    }
            });
    };
</script>

</body>

</html>