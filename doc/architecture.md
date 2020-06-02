# ARCHITECTURE DE SPRINGBOOT et REST


## Découpage du projet

Nous avons dans un premier temps séparé notre projet en 2 parties :

Moteur : 

              - Contient toutes les classes du jeu de base sans le serveur de stats (Bâtiments, Plantations, Joueurs...)
              - Classe Jeu, classe principale exécutant l'application SpringBoot du côté moteur du jeu.
Serveur :

        - Classe ScoreController, en charge des routes des service.
        - Classe ScoreService, définie les services pour ajouter et récuperer le nom du vainqueur de chaque partie.
        - Classe WsServeurApplication, excécute l'application SpringBoot du serveur de stats.


## Les routes

Dans notre partie "Serveur" nous avons un contexte "score/v1/" et :
 
 - une route, "/statistiques" qui permet de consulter la liste des vainqueurs des parties éxécutées depuis le démarrage du serveur.
 
 - une route, "/statistiques/save" qui attend un String correspondant au nom du vainqueur de la partie en cours.
 
 - une route, "/statistiques/{i}" pour afficher le vainqueur de la ième partie sauvegardée.


## Déroulement des parties

Nous commençons par démarrer notre serveur, ensuite notre classe "Jeu" lance 10 parties, envoie les noms des gagnants de chaque partie au serveur et les affiches.

## Organisation en composants

Les classes "Jeu" et "WsServeurApplcation" sont configurés en @SpringBootApplication

La classe "ScoreController" est un @RestController

La classe "ScoreService" est un @Component.

