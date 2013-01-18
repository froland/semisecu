Attack groups
=============

Injection
---------
(1) Injection

Session and data stealing
-------------------------
(3) Broken Session Management
(9) Insufficient Transport Layer Protection

Server configuration
--------------------
(6) Security Misconfiguration
(7) Insecure Cryptographic Storage
(8) Failure to Restrict Access

URL and parameter manipulation
------------------------------
(4) Insecure Direct Object References
(10) Unvalidated Redirects and Forwards

Scripting
---------
(2) XSS
(5) CSRF

Tools
=====

- Web proxy: WebScarab
  <https://www.owasp.org/index.php/Webscarab>
- Add session/request listeners to the Java project

Example of attacks for the project
==================================

Session stealing
----------------
Use XSS to inject a script that will send data (cookies) of a user to another
web site.

Create another site for the CSRF attack
---------------------------------------
Build another web site to perform CSRF attacks.

- Images or iframes (+scripts) to post forms in background
- The web site could contain hidden input fields that are sent to the attacked
  web site

Possible attacks are: approve automatically a hotel, post a series of
comments, enable or disable a hotel, etc.

Change the hotel
----------------
The attacked web site should only verify whether the user is a *manager*
before accepting modifications on the hotel (we can drop the verification of
whether the hotel is managed by that user). Moreover, we must use the sent ID
of the hotel to update instead of extracting it from the URL.

List hotels (and find passwords)
--------------------------------
JPQL injection: find all hotels that have a manager that has the same password
as a given user.
The passwords are hashed using MD5, however, without a salt this has no effect
on that query. When that attack succeeds, we have a list of managed hotels and
we know the password of their managers.
The web site could show the executed stack trace when the request is not
syntactically correct.

Database dump
-------------
Possible SQL injection in the name of the table.
