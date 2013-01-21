Java: Broken Session Management
===============================

Linked attack
-------------

- XSS

Scenario summary
----------------

Using XSS the attacker gets a valid session ID from another user and
then starts posting comments with the other user's login.

Code change
-----------

None

Scenario description
--------------------

Setup *WebScarab*: start-it, in the menu *Tools*, check the "Use full-featured
interface" (restart if needed) and setup *FireFox* to use `localhost:8008` as
a proxy.

In *WebScarab* search a `POST` request to reuse, right-click it and use it as
a fuzz template. You can also use directly the fuzzer to build manually a
request to send.

Now in the fuzz template tab change the hotel ID in the URL, the
session ID, the note and the comment to suit your needs (note: after
editing a field you have to click on an other one for the change to be
taken into account). Click start and it's done...

A simpler alternative: open a *FireBug* console and run the code below after
replacing the value of `session` with the value captured using XSS.
It will replace your session with one that you have defined.

	var session = "74743D39171A9AC78187451031D47912"; // the session ID
	var expire = new Date();
	expire.setTime(expire.getTime()+3600000);
	document.cookie = "JSESSIONID=" + escape(session)
		+ ";expires=" + expire.toGMTString();


Preventing the attack
---------------------

It's not possible to prevent the attacker to impersonate somebody if
he can get credentials. However you can mitigate the impact of the
usage of a stolen session ID by limiting the number of sessions per
user (e.g. in Spring:
<http://javarevisited.blogspot.be/2012/03/spring-security-example-tutorial-how-to.html>
), setting a timeout on session, putting an accessible logout button on
every page and asking for re-authentication before doing something important.
