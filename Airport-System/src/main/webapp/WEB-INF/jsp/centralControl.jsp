<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html lang="en">
<head>
<script src="/webjars/jquery/3.1.1-1/jquery.min.js"></script>
<script src="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
<link href="css/styling.css" rel="stylesheet">
<script src="/webjars/sockjs-client/1.0.2/sockjs.js"></script>
<script src="/webjars/stomp-websocket/2.3.3/stomp.min.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>

</head>

<body>

<div class="grid-container">
  <div class="MainPanel">
	<button class="waves-effect waves-light btn-small" onclick="window.open('/servicemanager');">Service Manager</button>

	<button class="waves-effect waves-light btn-small">Pilot Interface</button>

	<button class="waves-effect waves-light btn-small">Track Manager</button>
 
  	<div class="SidebarLeft"></div>
  	<div class="SidebarRight"></div>
</div>

</div>
</script>
</body>
</html>
