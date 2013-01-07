# Code-review 2013-03-04 #

* Dans les tests unitaires JUnit4, il est inutile de préfixer les méthodes de test par *test*. Au contraire, cela crée un bruit génant lors de la lecture.

* DaoTestBase
  * Pourquoi est-ce que la connection DB est fermée dans la méthode @Before ? Pourquoi pas dans un @After ?
  * Pourquoi faire un dropAll() plutôt que de prendre une nouvelle DB puisqu'elle est en mémoire ?
  * À quoi sert la méthode initializeLiquibase(ApplicationContext ctx) ?
* SimpleDaoTestBase
  * testDAO() ne teste pas l'intégration avec la DB car le test est transactionnel. Vérifier le comportement de SpringJUnit4Runner et utiliser flush() sur l'entity manager après save() et delete() pour valider le comportement de la DB.
  * checkEquals() pourrait par la même occasion valider les méthodes equals() et hashCode() des entités.
* HotelDaoTest
  * Quel est l'usage des méthodes et champs protected (hormis les overrides, bien sûr) ?
  * Dans testFindByName(), je n'aime pas l'assertion selon laquelle une méthode findXXX() renvoie *null* quand rien ne match. Je préfère que mes méthodes findXXX() renvoient une collection vide (ce qui est communément admis et donc attendu comme comportement par la majorité des développeurs expérimentés).
  * Dans testFindSearchQuery(), on teste plusieurs cas dans la même méthode. Il serait plus élégant de la découper.
  * Dans testComputedNote(), la syntaxe h.addComment(null).setNote(xxx) ne me semble pas naturelle. Qu'est censé retourner addComment(null) ? Instinctivement, je dirais, par ordre décroissant de préférence, void, Hotel ou l'argument passé en paramètre.
  * L'utilisation d'un *double* comme type pour la note me laisse perplexe. Est-ce vraiment nécessaire d'avoir une telle précision ?
  * La présence d'une méthode computeNote() sur HotelDao me parait inappropriée. H.getAverageNote() doit pouvoir calculer la moyenne en real-time sans mettre un super calculateur sur les genoux, non ?
* UserDaoTest
  * testFindByName(), même commentaire que dans HotelDaoTest.
  * Dans testDuplicateUserName(), ne faut-il pas appeler la méthode flush() sur l'entity manager ?

---

* HotelTest
  * setAverageNote() ne devrait pas exister. Il s'agit d'un résultat de calcul.
  * Pour l'usage qui en est fait ici, je renommerais addComment en createComment avec tous les éléments obligatoires en paramètres.
* UserTest
  * testCreate() teste trop de choses en même temps.
  * User.setEnabled() devrait être changé en enable() et disable() qui sont plus explicites.

* ServiceTestBase et ControllerTestBase
  * Il n'y a pas de raison de faire intervenir Spring ou la DB dans les tests sur les services ou la couche de présentation. Je ferai le code review quand ils seront modifiés.

---

* Habituellement, le nom des tables en DB est au singulier.
* Attention aux conventions de nommage et surtout à la cohérence : certaines colonnes dans la DB ont des noms non séparés (USERID, hotelname) et d'autres ont des noms séparés par des underscores (USER_NAME).
* Identifiable
  * Quel est la raison du paramètre de type ? Y-a-t-il une raison de ne pas utiliser Long ou Integer ?
  * Mettre Identifiable dans le package dao.base introduit une dépendance circulaire (appelée *tangle* dans ce cas-ci) avec le package domain. Dans la mesure du possible, il faut éviter ce genre de chose cbr cela diminue la modularité du code.
* SimpleJPA
  * Dans la méthode save(), je ne vois pas l'intérêt d'utiliser flush. Au contraire, cela neutralise les optimisations d'Hibernate en cas d'opérations multiples.
  * Toujours dans la méthode save(), je ne vois pas l'intérêt du merge. Je préfère que le code soit organisé de tel manière à ne pas en avoir besoin et éviter ainsi les problèmes de modifications concurrentes.
* HotelDaoJpa
  * Supprimer la méthode computeNote.
  * Mauvaise pratique : findTopNotedHotels() ne doit pas calculer la note moyenne en DB. Au besoin, il faut créer une propriété read-only ou mémoriser le résultat du calcul dans la DB. Le risque ici est d'avoir des arrondis différents en DB et en Java. C'est un problème fréquent, notamment dans la facturation et les arrondis de somme et de calcul de TVA.
* IdentifiableEntity
  * L'implémentation de equals() et hashcode() est mauvaise. La rendre *final* est une **hérésie** !
  * L'implémentation de toString n'est pas terrible. Si on indique l'id, est-il nécessaire de garder le hashcode également ? Il est aussi préférable d'utiliser un StringBuilder pour la concaténation plutôt que de se reposer sur le remplacement automatique des *+* par le compilateur (le compilateur de Sun utilise un StringBuffer, moins performant que le StringBuilder).
  * Dans equalsId(), mieux vaut utiliser le paramètre de type pour l'id plutôt qu'Object.
* Dumper
  * Dumper doit être annoté comme @Repository et non comme @Component.
* Comment
  * L'utilisation de la clé composite CommentID est inutilement complexe.
  * La relation entre *Hotel* et *Comment* devrait être unidirectionelle et uniquement navigable de *Hotel* vers *Comment*.
  * Quel est l'intérêt du champs *sequence* ?
  * *deleted* devrait être de type boolean.
  * Pourquoi *username* est-il sauvegalsé en DB ?
* Hotel
  * *hotelName* ne devrait-il pas être unique ?
  * *address* est un drôle de nom pour un chapms qui ne contient pas country et city. Quid du code postal par exemple ? L'idéal est de créer une classe *Address* et de l'ajouter comme champ embedded dans le mapping Hibernate.
* User
  * *enabled* doit être de type *boolean*.
  * Remplacer *ROLE_USER* et *ROLE_ADMIN* par une enum.
  * *roles* ne doit pas être une *List* mais un *Set*. Ça simplifiera *setAdmim()*.
* service.dto
  * J'aime pas la copie automatique de propriétés par réflection. On a toujours pleins d'ennuis avec ces brols et le comportement de l'application n'est plus validé lors de la compilation.
  * Ces DTO ont l'air de travailler avec la couche de présentation :
    1. ils sont donc au mauvais endroit;
    2. ce ne sont plus vraiment des DTO puisqu'il y a de la logique de validation à l'intérieur.
  * Il y a donc lieu de refactorer profondément cela ainsi que les services et les contrôleurs qui dépendent largement de ce choix.
* AdminServiceImpl
  * dumpToWriter()
    * Pourquoi catcher SQLException si on utilise un DAO Spring qui n'est pas censé retourner autre chose que des DataAccessException ?
    * L'exécution du code peut-elle vraiment poursuivre après ces erreurs ? Si non, il faut relancer une exception. Logger ne suffit pas.
