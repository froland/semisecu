Java: Insecure Direct Object References
=======================================

Scenario summary
----------------

When users create hotels, these are not approved and do not show in the
results of a search. However, these can still be accessed by directly typing
their URL.

Scenario description
--------------------

1. Create some hotel
2. Copy the URL of the hotel
3. Log out
4. Paste the copied URL

Vulnerable code
---------------

See permission checking in controllers.
Whether a hotel has been approved is never checked when gaining access.

Preventing the attack
---------------------

The permissions have to be checked before working on data.
This can be done, for example, as in `ÙserController.viewUser(..)` method.
