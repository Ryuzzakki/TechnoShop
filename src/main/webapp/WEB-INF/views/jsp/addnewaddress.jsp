<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Add address</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
<c:if test="${ sessionScope.user == null }">
    <c:redirect url="login"></c:redirect>
</c:if>
<jsp:include page="header.jsp"></jsp:include>
<br>
<div class="container">
    <h1>Enter new Address:</h1>

    <c:if test="${ requestScope.invalidAddress == true }">
        <h2>Invalid address! Try Again!</h2>
    </c:if>

    <div id="locationField">
        <input id="autocomplete" placeholder="Enter your address" onFocus="geolocate()" type="text"></input>
    </div>
    <form action="addressprofile" method="post">
        <br><input placeholder="Street Number" id="street_number" name="streetNumber"></input>
        <br><input placeholder="Street" id="route" name="street"></input>
        <br><input placeholder="City" id="locality" name="city"></input>
        <br><input placeholder="State" name="state" id="administrative_area_level_1" type="hidden"></input>
        <br><input placeholder="Postal Code" name="postalCode" id="postal_code" type="hidden"></input>
        <input placeholder="Country" class="field" id="country" name="country" type="hidden"></input>
        <input type="submit" class="btn" name="login_submit" value="Submit"/>
    </form>

    <script>
        var placeSearch, autocomplete;
        var componentForm = {
            street_number: 'short_name',
            route: 'long_name',
            locality: 'long_name',
            administrative_area_level_1: 'short_name',
            country: 'long_name',
            postal_code: 'short_name'
        };

        function initAutocomplete() {
            // Create the autocomplete object, restricting the search to geographical
            // location types.
            autocomplete = new google.maps.places.Autocomplete(
                /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
                {types: ['geocode']});

            // When the user selects an address from the dropdown, populate the address
            // fields in the form.
            autocomplete.addListener('place_changed', fillInAddress);
        }

        function fillInAddress() {
            // Get the place details from the autocomplete object.
            var place = autocomplete.getPlace();

            for (var component in componentForm) {
                document.getElementById(component).value = '';
                document.getElementById(component).disabled = false;
            }

            // Get each component of the address from the place details
            // and fill the corresponding field on the form.
            for (var i = 0; i < place.address_components.length; i++) {
                var addressType = place.address_components[i].types[0];
                if (componentForm[addressType]) {
                    var val = place.address_components[i][componentForm[addressType]];
                    document.getElementById(addressType).value = val;
                }
            }
        }

        // Bias the autocomplete object to the user's geographical location,
        // as supplied by the browser's 'navigator.geolocation' object.
        function geolocate() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    var geolocation = {
                        lat: position.coords.latitude,
                        lng: position.coords.longitude
                    };
                    var circle = new google.maps.Circle({
                        center: geolocation,
                        radius: position.coords.accuracy
                    });
                    autocomplete.setBounds(circle.getBounds());
                });
            }
        }
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDkHN_gdiuaWXmHeLB8Fpe_pBc840VRgIk&libraries=places&callback=initAutocomplete"
            async defer></script>

</div>
</body>
</html>