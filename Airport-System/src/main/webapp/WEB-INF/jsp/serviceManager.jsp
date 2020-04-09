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
    <h2>Gate Services</h2>
    <c:if test="${not empty gateServices}">
    <table class="greyGridTable" style="width: 300px">
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
        <h2>Refuel Services</h2>
        <c:if test="${not empty refuelServices}">
        <table class="greyGridTable" style="width: 300px">
            <tr>
                <th>Service ID</th>
                <th>Service available</th>
            </tr>
            <c:forEach items="${refuelServices}" var="service">
                    <tr>
                        <td>${service.name}</td>
                        <td>${service.available}</td>
                    </tr>
        </c:forEach>
        </table>
        </c:if>
        </div>
</div>

<script>

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