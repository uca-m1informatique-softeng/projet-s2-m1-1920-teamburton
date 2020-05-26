
# <H1>PUERTO RICO JEUX DE SOCIETE</H1>

<br/><p align="center"><img src="https://cf.geekdo-images.com/itemrep/img/ERdhn-fY5ScWqU1wdYDzEP9LzvM=/fit-in/246x300/pic158548.jpg" width="250" /></p><br/>
# <H1>Démonstration</H1>
- *Fonctionnement et utilisation de Docker*
- *Plan de builds dans Travis*
- *Images d'exécutions*

## Fonctionnement et utilisation de Docker
  *Notre conteneur Docker contient le serveur de stat dont le port 2480 est mappé au port 2480 de la machine exécutant le projet afin d'éviter un conflit d'utilisation de port avec le moteur de jeu qui communique par le port 8080 de la machine exécutant le projet.*

## Plan de build dans Travis
  *1_ Définition du langage et JDK utilisé*</br>
  *2_ Build des deux projets contenant les applications springboot*</br>
  *3_ Exécution des tests du moteur de jeu*</br>
  *4_ Construction de l'image Docker "servveur" contenant l'application SpringBoot du serveur de stat*</br>
  *5_ Exécution de l'image Docker et connexion du port 8080 de la machine physique au port 2480 de la VM*</br>
  *6_ Exécution de l'application SpringBoot "Moteur" qui est notre moteur de jeu (celui-ci exécute 10 parties et requiert le service de sauvegarde du serveur de stat à la fin de chacune pour enregistrer le vainqueur de chaque partie)*</br>

## Images d'exécutions
<br/><p align="center"><img src="C:\Users\Kinli\Desktop\Statisitques-Chrome.png" /></p><br/>
<br/><p align="center"><img src="Build-Travis.png" width="250" /></p><br/>
