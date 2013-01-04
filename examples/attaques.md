Groupes d'attaques
==================

Injection
---------
(1) Injection

Vol de sessions/données
-----------------------
(3) Broken Session Management
(9) Insufficient Transport Layer Protection

Configuration du serveur
------------------------
(6) Security Misconfiguration
(7) Insecure Cryptographic Storage
(8) Failure to Restrict Access

Manipulation des paramètres et URLs
-----------------------------------
(4) Insecure Direct Object References
(10) Unvalidated Redirects and Forwards

Scripting
---------
(2) XSS
(5) CSRF

Outils
======

- Web proxy: WebScarab
  <https://www.owasp.org/index.php/Webscarab>
- Ajouter des session/request listeners dans le projet Java

Exemple d'attaques pour le projet Java
======================================

Vol d'une session
-----------------
Utiliser le XSS pour injecter un script qui va envoyer les données (cookies)
d'un utilisateur vers un autre site web.

Créer un autre site pour CSRF
-----------------------------
Faire un autre site web pour faire des attaques du type CSRF.

- Le site doit etre parametrable
- Images ou iframes (+scripts) pour lancer les modifications en arriere-plan
- A la base le site web peut simplement contenir des formulaires avec des champs
  hidden qui seront envoyés sur le site attaqué

Les attaques possibles sont: approuver automatiquement un hotel, poster une
serie de commentaires, rendre un compte disponible ou bloqué, etc.

Changer les hotels
------------------
Il faut seulement vérifier si un utilisateur est "manager" avant d'accepter les
modifications sur les hotels (il ne faut plus vérifier si l'hotel en question
lui appartient). De plus, il faut utiliser l'id du hotel envoyé par
l'utilisateur à la place de l'extraire de l'URL pour pouvoir modifier un autre
hotel.

Lister les hotels (et trouver les mots de passe)
------------------------------------------------
Injection JPQL: trouver tous les hotels dont le manager possede le meme mot de
passe qu'un user donné.
Les mots de passe sont encodés en MD5, mais cela n'a aucun effet sur cette
query. Une fois l'attaque réussie, on a une liste d'hotels d'autres utilisateurs
et on connait leur mot de passe.
On peut adapter le site pour afficher les stacktraces lorsque l'injection n'est
pas syntaxiquement correcte.

Dump de la DB
-------------
Injection SQL possible dans le nom de la table.
En plus, il est possible d'ajouter des colonnes non-visibles dans la query
(l'exemple du mot de passe du utilisateur).
