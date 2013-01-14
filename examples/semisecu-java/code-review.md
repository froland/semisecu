# Code-review 2013-01-04 #

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
  * Mettre Identifiable dans le package dao.base introduit une dépendance circulaire (appelée *tangle* dans ce cas-ci) avec le package domain. Dans la mesure du possible, il faut éviter ce genre de chose car cela diminue la modularité du code.
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
  * Pourquoi *username* est-il sauvegardé en DB ?
* Hotel
  * *hotelName* ne devrait-il pas être unique ?
  * *address* est un drôle de nom pour un champs qui ne contient pas country et city. Quid du code postal par exemple ? L'idéal est de créer une classe *Address* et de l'ajouter comme champ embedded dans le mapping Hibernate.
* User
  * *enabled* doit être de type *boolean*.
  * Remplacer *ROLE_USER* et *ROLE_ADMIN* par une enum.
  * *roles* ne doit pas être une *List* mais un *Set*. Ça simplifiera *setAdmin()*.
* service.dto
  * Je n'aime pas la copie automatique de propriétés par réflection. On a toujours pleins d'ennuis avec ces brols et le comportement de l'application n'est plus validé lors de la compilation.
  * Ces DTO ont l'air de travailler avec la couche de présentation :
    1. ils sont donc au mauvais endroit;
    2. ce ne sont plus vraiment des DTO puisqu'il y a de la logique de validation à l'intérieur.
  * Il y a donc lieu de refactorer profondément cela ainsi que les services et les contrôleurs qui dépendent largement de ce choix.
* AdminServiceImpl
  * dumpToWriter()
    * Pourquoi catcher SQLException si on utilise un DAO Spring qui n'est pas censé retourner autre chose que des DataAccessException ?
    * L'exécution du code peut-elle vraiment poursuivre après ces erreurs ? Si non, il faut relancer une exception. Logger ne suffit pas.


# Code-review 2013-01-14 #

* Remarques générales
  * La plupart des écrans actuels peuvent aficher plus de 80 caractères par ligne.
  * Attention à utiliser une indentation correcte et ne pas se laisser abuser par les réglages par défaut d'Eclipse.
  * Il faut éviter les noms de variable sans signification.
  * Dans les tests, il faut tester le comportement et pas l'implémentation. Cela doit également se voir dans le choix des noms de méthode.
  * La Javadoc est presque absent partout. Et là où elle est présente, elle n'apporte aucune info (@author).

* DumperTest
  * .toArray(new String[0]) est inefficace. Demander explications orales.
* UserDaoTest
  * Quel est le sens du test sur les rôles dans .checkEquals ?
* HotelTest
  * .testStars()
    * Il y a plusieurs tests dans la même methode. --> bad practice
    * Variable inutile : Integer s = 3
* AdminServiceTest
  * .dumpCalled utilise une construction complexe pour ne pas tester grand chose au final. Quelle est l'intention ?
  * initService: variable inutile
    <code>
      AdminServiceImpl adminService = new AdminServiceImpl();
      this.adminService = adminService;
    </code>
* HotelServiceTest
  * .listAllItems: Il est inutile de mettre à jour l'id de l'hôtel par réflexion.
  * .testApprove: ne pas utiliser de mock pour Hotel. Il ne faut utiliser un mock que lorsqu'il est difficile d'utiliser directement l'objet souhaité (par exemple, un DAO). Il est plus intéressant de tester l'état du système après l'appel de la méthode (<code>hotel.isApproved()</code>) que de vérifier l'implémentation de la méthode (<code>mock.verify()</code>).
  * .deleteComment est à réécrire pour les raisons expliquées ci-avant.
* AdminControllerTest
  * <code>controller.setAdminService(adminService = Mockito.mock(AdminService.class))</code> est une construction à éviter car elle fait 2 choses en même temps.
* HotelControllerTest
  * .createTestHotel: inutile d'utiliser une variable juste avant le return.
* UserControllerTest
  * .postUpdateUser: fausse constante <code>Integer id = 1</code>.
  * .getUserServiceUpdatedUser: On fait joujou ? C'est la grosse Bertha anti-tsétsé ?
  * .updateWithForm: Ça sert à quoi <code>Mockito.mock(UserForm.class)</code> ?
* HotelDaoJpa
  * .findApprovedHotels ne doit pas utiliser la représentation interne 0 au lieu de false dans le JPQL.
* SimpleJPA
  * Très mauvaise idée d'utiliser @Transactional dans un DAO. A fortiori dans tous.
* Dumper
  * listTables: Ne jamais utiliser e.printStackTrace. 1° Ça imprime dans la console du serveur sans timestamp, ce qui est inutilisable. 2° C'est un appel synchrone.
  * La gestion des exceptions n'est pas très élégante.
  * La gestion de la fermeture des ressources JDBC est dupliquée.
  * Pourquoi ne pas utiliser un JDBCTemplate partout ?
* Address
  * Non-respect de la convention de nommage pour ZIPCode (--> zipCode).
  * Pour un value object, il est préférable de chéer un objet immuable.
  * Attention, cet objet n'est pas sérializable ! Or il est embarqué par la classe Hotel qui, elle, est sérializable.
* Hotel
  * Quel est le problème avec la liste des commentaires ?
  * Quelle est l'utilité de setCountry et setCity ? NPE si address est null.
  * Quel est l'utilité de l'interface Noted ?
  * Méthode equals() illisible. Utilisez commons-lang ou guava pour gagner du temps.
  * Méthode hashcode() non cohérente avec equals() == problème en perspective, surtout avec les collections.
* HotelListItem
  * Ressemble plus à un DTO qu'à un objet du domaine. --> Déplacez-le dans service ou dao selon l'usage.
* HotelServiceImpl
  * getById(): Mieux vaut solutionner le problème à la source qu'utiliser des bidouilles pareilles.
  * itemize(): Quel est l'intérêt de cette méthode ? Pourquoi utiliser un DTO ici ?
  * listManagedHotels(String name) --> listManagedHotels(String userName)
  * addComment(): Si on regarde bien tout ce qui est fait ici, on se rend compte que, dans le DAO, on catch une exception pour retourner null. Et dans le service on teste null pour retourner une exception. N'est-ce pas un peu «overkill» ?
  * deleteComment(): Le code fait une première requête DB pour récupérer l'Hotel puis une requête par commentaire (puisque liste en lazy loading), tout ça pour supprimer un commentaire dont on a déjà l'id. Ne serait-il pas plus simple de faire un « delete from Comment comment where comment.id = :id » dans un DAO ? Ça me parait moins gourmand en appels DB (et accessoirement en mémoire pour l'instanciation des classes qui ne sont même pas utilisées).
  * setHotelImage(): Même remarque que pour deleteComment() pour la gestion des exceptions.
* UserServiceImpl
  * enableUser() et disableUser() : Pourquoi retourner le User ?
* SameValueValidator
  * isValid() : Évitez catch(Exception e), soyez plus précis sous peine de ne pas pouvoir faire facilement la différence entre une erreur d'entrée utilisateur et une erreur de programmation.
* AdminController
  * Dans export(), Le MIME-TYPE correct pour un fichier csv est «text/csv» et non «application/octet-stream».
  * Dans viewAdmin(), peut-on vraiment continuer après les exceptions ?