Ce markdown explique succinctement nos réfléxions et analyses sur la séparation de notre code en composants.

clients : Joueurs	
serveur : MoteurDuJeux, ServeurDeStats
         
-------------------------------
Composant Joueur : Lié aux joueurs contientdra les classes couplées à la classe "Joueurs", les classse de
définitions des stratégies, le plateau du joueur.

Composant Ressource : Garderas les classes des bâtiments et plantations.

Composant Outils : Contiendra les classe "Magasins", "Reserve",...

Composants Serveur : Aura pour le moment le serveur de stats


La séparation de départ consistera tout d'abord à initialiser dans la classe jeux, les joueurs, les
parties, le serverur de stat ainsi que le moteur du jeux. La classe partie qui sert de façade entre
"MoteurDuJeux" et "Joueur" prendra en paramètre les joueurs ainsi que le nombre de parties. MoteurDuJeux aura en paramètre la partie. Pour la communication entre le MoteurDuJeux, la réflexion est toujoursen cours, nous avons eu des idées, mais ne savons pas si l'une d'entre elles sera bonne ou non.
On pensais par exemple à refaire une nouvelle façade entre "MoteurDuJeux" et "ServeurDeStats", mais ensuite nous nous demandons, est-ce vraiment le rôle du moteur du jeux manipuler les stats des joueurs ou la façon de les transmettre au serveur de stat?
Ne faudrait-il pas récupérer de façon séparé (hors moteur du jeux) les stats des joueurs en fin de
partie? Le moteur de jeux ayant juste le rôle de faire respecter les rêgles et de faire dérouler la
partie.