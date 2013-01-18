Security seminar - Java example
===============================

Launching the example
---------------------

	cd hotel
	# launch in a tomcat instance
	mvn -Pdev tomcat:run

The server will be accessible from <http://localhost:8080/owasphotel>.

Launching evil website
----------------------

	cd evil-website
	mvn tomcat:run

The servier will be accessible from <http://localhost:8081/evilhotel>.

