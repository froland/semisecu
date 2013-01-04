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

