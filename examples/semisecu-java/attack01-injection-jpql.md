Java: Injection (JPQL)
======================

Scenario summary
----------------

We can inject JPQL into the hotel search field.
This allows us to find hotels based on conditions we want.
Two examples will be shown: querying unapproved hotels and hotels that have a
manager that has the same password as another user.

Code change
-----------

The method `HotelDaoJpa.findSearchQuery(..)` has been changed to allow code
injection. We are now using a simple concatenation.
For the second example, the search was made case-sensitive to be allowed to
inject class names that are camel-cased.

Scenario description
--------------------

This scenario can be executed as an anonymous user. Just enter the query in
the search field.
You can monitor results using *FireBug*. When the query is invalid, a 500
response will be sent with the stack trace and the generated query (see the
example below).

	Request processing failed; nested exception is
	java.lang.IllegalArgumentException: org.hibernate.QueryException:
	expecting ''', found '&lt;EOF&gt;' [select h from
	com.hermes.owasphotel.domain.Hotel h where h.approved is true and h.name
	like ''test%' order by h.name]

Run the following queries:

> (when copying, remove spaces at the edges of the query)

1. Find the hotels that are not approved.

   ' or h.approved is false and h.name like '

2. Find hotels that are managed by users that have the same password that
   the user *2*.
   For that query to work, the search must be case-sensitive as the class
   names begin with an upper-case letter.

   %' and h.manager in (select u from com.hermes.owasphotel.domain.User u,
   com.hermes.owasphotel.domain.User f where u.password = f.password and
   f.id = 2) and '%' = '


Preventing the attack
---------------------

Use parameter placeholders in the query instead of concatenating the
arguments. The correct code is placed as a comment in the source file.

