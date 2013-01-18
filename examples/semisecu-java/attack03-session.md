Java: Broken session management
===============================

Scenario summary
----------------
Using XSS the attacker get a valid session ID from an other user and
then start posting comments with the others user login.

Code change
-----------

None

Scenario description
--------------------

Setup WebScarab: start-it, check the use complete interface in the
tools check-box and setup firefox to use localhost:8008 as a proxy.

Login with an attacker user post a comment on an hotel. In WebScarab
search the POST request on comment, right-click it and click use as a
fuzz template.

Now in the fuzz template tab change the hotel ID in the URL, the
session ID, the note and the comment to suit your need (note: after
editing a field you have to click on an other one for the change to be
taken into account). Click start and it's done...

Geekier alternative: open a *Firebug* javascript console and run:
	var expire= new Date();
	expire.setTime(expire.getTime()+3600000);
	document.cookie="JSESSIONID=74743D39171A9AC78187451031D47912;expires="+expire.toGMTString();
with a valid JSESSIONID.


Preventing the attack
---------------------

It's not possible to prevent the attacker to impersonate somebody if
he can get credendentials. However you can mitigate the impact of the
usage of a stolen session ID by limiting the number of session per
user (e.g. in Spring:
http://javarevisited.blogspot.be/2012/03/spring-security-example-tutorial-how-to.html
), setup a timeout on session, put an accessible logout button on
every page and ask for re-authentification before doing something important.
