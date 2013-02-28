# Remarques slides

Les numéros de slides sont relatifs au document websecurity-notes.pdf taggé « slides académiques ».

## Remarques globales
* Mieux identifier les exemples par une typographie propre ou alors les mettre sur leur propre slide (éventuellement avec un « code » pour bien montrer que ce sont des slides).
* Il ne faut pas hésiter à répéter certaines informations déjà présentées afin de tisser des liens entre les différentes parties (« enseigner, c'est répéter »). Ce genre de lien est très efficace dans un exposé car le cerveau humain se « réveille » d'un coup en faisant le lien et en se disant « ah oui, c'est vrai, on vient de parler de ça ».
* Attention à la cohérence typographique : certaines phrases (notamment dans les bullet points) sont terminées par un point et d'autres pas.
* Attention à l'orthographe, notamment l'accord du verbe à la 3ème personne (-s).

## Remarques localisées

* slide 7
  * Quel est le rapport entre le titre et le contenu ?
  * Financial and fiscal peuvent être regroupés, par contre, j'ajouterais l'aspect Professionel
* slide 9
  * assurer la cohérence « false sentiment of security » vs « false sense of security »
  * mieux identifier les digressions telle que celle sur HTTPS
* slide 10
  * On peut également dire oralement quelque chose du genre : ou bien faisons-le payer sur un site de rencontre pour glâner des informations et finalement lui voler son identité.
* slide 16
  * ajouter les alternatives : 1password, lastpass, pwdhash
* slide 30
  * ajouter une dmz dans le graphique
  * faire un schéma un peu plus sexy
* slide 34
  * ne pas utiliser d'abbréviations non introduites préalablement
* slide 51 (note)
  * attention : la session n'est pas stockée chez le client mais sur l'applicaiton server. Seul _l'identifiant_ de session peut être stocké sous forme de cookie.
* slide 56
  * “appropriate salt” : plus qu'approprié, il s'agit ici d'avoir un salt différent pour chaque password pour contrer les attaques par rainbow table.
* slide 68
  * “avoid redirects and forwards” : ces 2 opérations sont à la base de toute navigation dans un site web dynamique. Il faut préciser.
* slide 82
  * concernant les captcha, il existe des sites qui présentent le captcha dans un autre contexte (par exemple, un autre site) et qui utilisent la réponse fournie par l'utilisateur pour attaque le site cible de manière automatique).
* slide 86
  * la précision « someone / something » alourdit inutilement le propos. Autant dire à un moment donné que ce qui s'applique aux personnes dans ce cadre-ci est aussi valable pour les systèmes.
* slide 96-97
  * « When the browser does require a CA certificate it just asks the CA for it » Qu'es-ce que cela veut dire ? Que le browser est capable de générer un certificat racine de confiance sur simple demande au Comité d'Administration ? ;-) Je pense qu'il y a confusion, d'autant que l'enregistrement du certificat CA dans un browser est soit faite lors de l'installation du browser soit spar opération manuelle de l'utilisateur afin justement de garantir la confiance en ce certificat.
  * On parle ici de CA et d'autentification 2-way alors que l'utilisation 1-way (autentification d'un site web), bien plus répandue, n'est pas expliquée en détail.
  * Je préciserais également qu'étant donné que les algorithmes à clé assymétrique sont des algorithmes lents, ils sont pricipalement utilisés au sein de protocoles pour permettre la négotiation d'une clé secrète qui est ensuite utilisée avec un algorithme symétrique durant une durée définie pour le reste de la communication (nouvel exemple de compromis sécurité / utilisabilité).
* slide 99
  * Il existe aussi des systèmes d'autentification biométrique (voix, empreinte digitale ou palmaire, scan rétinien).
* slide 101
  * Ceci devrait être expliqué avant SSL
  * Avant de parler des algorithmes symétriques et assymétriques, je ferais un rappel sur les 2 opérations courantes de cryptographie, c'est-à-dire la signature et le cryptage. Ces 2 opérations sont à la base des différents protocoles. Les éléments de cette explication sont déjà présents un peu partout dans les slides mais mériteraient d'être rassemblés.
* slide 105
  * COTS: abbréviation inutile. Utilisez plutôt une phrase pour décrire le concept.
* slide 109
  * Pourquoi y-a-t'il un risque d'être lié à un seul provider ? Il est justement courant de voir plusieurs systèmes concurrents disponibles sur la même page de login.
* slide 111
  * Réinsister sur les dangers du social engineering par rapport à ce point.
* slide 113
  * « life » --> « lifecycle »
  * Je ne vois pas bien le lien entre le propos et l'image.
* slide 114
  * « as soon as possible » --> « from the beginning »
  * Comme introduction, je citerais la définition de l'architecture selon Martin Fowler : « The highest-level breakdown of a system into its parts; the decisions that are hard to change; there are multiple architectures in a system; what is architecturally significant can change over a system's lifetime; and, in the end, architecture boils down to whatever the important stuff is. » Patterns of Enterprise Application Arcitecture, Martin Fowler. Cette citation introduit le premier bullet point.
  * Comme question importante, je rajouterais « Existe-t-il d'autres moyens d'accéder aux information et quels sont les règles d'accès de ces autres systèmes ? ».
* slide 118
  * Ajouter la sensibilisation des responsables business au compromis sécurité / facilité d'utilisation.
* slide 122
  * Le « etc » n'apporte rien.
* slide 124
  * La différence entre SSL et TLS est-elle vraiment que TLS utilise un « hello » sujet à des failles de sécurité ? C'est comme ça que je comprends la phrase.
