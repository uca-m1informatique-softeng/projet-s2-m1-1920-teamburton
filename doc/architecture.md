# ARCHITECTURE DE SPRINGBOOT et REST


## Découpage du projet

Nous avons dans un premier temps séparé notre projet en 2 parties :
Moteur du jeux : 

              - Contient toutes les classes du jeu de base sans le serveur de stat (Bâtiments, Plantations, Joueurs...)
              - Classe Jeu, classe principale exécutant l'application Spring du côté moteur.
Serveur :

        - Classe ScoreController, en charge des routes de communication.
        - Classe ScoreService, définie les services pour ajouter et récuperer le nom du vainqueur de chaque partie.
        - Classe WsServeurApplication, excécute l'application Spring du serveur.


## Les routes

Dans notre partie "Serveur" nous avons une route, "score/v1/statistiques" qui permet de recevoir du moteur et de renvoyer les noms des joueurs vainqueurs.


## Déroulement des parties

Nous commençons par démarrer notre serveur, ensuite notre classe "Jeu" lance 10 parties, envoie les noms des gagnants au serveur et les affiches.

## Organisation en composants

Les classes "Jeu" et "WsServeurApplcation" sont configurés en @SpringBootApplication

La classe "ScoreController" est un @RestController

La classe "ScoreService" est un @Component.



