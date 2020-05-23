# <H1>PUERTO RICO JEUX DE SOCIETE</H1>

<br/><p align="center"><img src="https://cf.geekdo-images.com/itemrep/img/ERdhn-fY5ScWqU1wdYDzEP9LzvM=/fit-in/246x300/pic158548.jpg" width="250" /></p><br/>

- **[DESCRIPTION](#description)**
- **[LANCEMENT](#lancement)**
- **[RENDU14/02](#rendu14/02)**
- **[RENDU21/02](#rendu21/02)**
- **[RENDU]()**
- **[RENDU]()**
- **[CONTRIBUTEURS](#contributeurs)**


# DESCRIPTION 

****


# LANCEMENT

OUTILS : Java (JDK) - [Maven](https://maven.apache.org/) - Internet 256K

COMPILATION                    : mvn compile<br/>
EXECUTION du programme         : mvn exec:java -Dexec.mainClass=puertoricotr.Jeux<br/>
EXECUTION JUnit/cucumber test  : mvn test

Pour lancer 1 ou n parties, il faut modifier le 3ème paramètre (nbPartie) lors de l'appel du constructeur MoteurDuJeux dans la classe Jeux.


# CONTRIBUTEURS 
 
[DIALLO Elhadj Mamadou Foula](https://github.com/Diallo-ucad)<br/>
[JANIN Rémi](https://github.com/Remi-janin)<br/>
[PAJANY Allan ](https://github.com/Allan06)<br/>
[Michael Porrecchia ](https://github.com/)<br/>

# RENDU14/02

Nous avons identifié plusieures dépendances entre Joueurs et MoteurduJeux qu'il faudra supprimer afin de pouvoir mettre en place une façade :
 -Le tableau de Joueurs classement dans MoteurDuJeux
 -La partie qui est en parametre de plusieures méthodes dans Joueurs
