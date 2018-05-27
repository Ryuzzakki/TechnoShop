<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>Shops</title>
</head>

<body>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDkHN_gdiuaWXmHeLB8Fpe_pBc840VRgIk&callback=map"
		type="text/javascript"></script>
<jsp:include page="header.jsp"></jsp:include>

	<c:if test="${ sessionScope.user == null }">
		<c:redirect url="login"></c:redirect>
	</c:if>

<div class="container">
	  <div id="map" class="border" style="width: 100%; height: 300px;"></div>
  <script type="text/javascript">
    var locations = [
      ['Musagenica', 42.661723, 23.360285, 4],
      ['Studentski grad', 42.653884, 23.345965, 5],
      ['Geo Milev', 42.680135, 23.356985, 3],
      ['Lozenec', 42.674448, 23.309441, 2],
      ['Center', 42.697180, 23.316997, 1]
    ];

    var map = new google.maps.Map(document.getElementById('map'), {
      zoom: 12,
      center: new google.maps.LatLng(42.661723, 23.360285),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var infowindow = new google.maps.InfoWindow();

    var marker, i;

    for (i = 0; i < locations.length; i++) {  
      marker = new google.maps.Marker({
        position: new google.maps.LatLng(locations[i][1], locations[i][2]),
        map: map
      });

      google.maps.event.addListener(marker, 'click', (function(marker, i) {
        return function() {
          infowindow.setContent(locations[i][0]);
          infowindow.open(map, marker);
        }
      })(marker, i));
    }
  </script>
  <div>
	<table class="table">
		<h3>Shops:</h3>
		<thead>
				<th>Name</th>
				<th>Location</th>
				<th>Action</th>
		</thead>
		<tbody>
			<c:forEach items="${applicationScope.restaurants}" var="restaurant">
				<tr>
				<td><c:out value="${ restaurant.name }"></c:out></td>
				<td><c:out value="${ restaurant.location } "></c:out></td>
				<td><form action="pickFromRestaurant" method="post">
						<input type="hidden" name="restaurantId" value="${ restaurant.id }" />
						<input type="submit" class="btn" name="address_submit" value="Take from" />
					</form></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	</div>

</div>
</body>
</html>