# <H1>PUERTO RICO JEUX DE SOCIETE</H1>

<br/><p align="center"><img src="https://cf.geekdo-images.com/itemrep/img/ERdhn-fY5ScWqU1wdYDzEP9LzvM=/fit-in/246x300/pic158548.jpg" width="250" /></p><br/>

- **[DESCRIPTION](#description)**
- **[LANCEMENT](#lancement)**
- **[RENDU0311](#rendu0311)**
- **[RENDU2810](#rendu2810)**
- **[RENDU2010](#rendu2010)**
- **[RENDU1210](#rendu1210)**
- **[RENDU0410](#rendu0410)**
- **[RENDU0210](#rendu0210)**
- **[CONTRIBUTEURS](#contributeurs)**


# DESCRIPTION 

**Il s'agit d'un projet de développement du jeux de société "Puerto Rico" où l'on devra modéliser les concepts, le moteur du jeux. Nous devrons ainsi simuler une partie entre 2 ou plusieurs robots et avoir une visualistion textuelle du déroulement de la partie. Ce projet fera le lien entre la conception logicielle et la programmation orientée objet (JAVA) et nous permettra d'appliquer des méthodes de suivi, notamment à l'aide de planification des tâches.**


# LANCEMENT

OUTILS : Java (JDK) - [Maven](https://maven.apache.org/) - Internet 256K

COMPILATION                    : mvn compile<br/>
EXECUTION du programme         : mvn exec:java -Dexec.mainClass=puertoricotr.Jeux<br/>
EXECUTION JUnit/cucumber test  : mvn test

Pour lancer 1 ou n parties, il faut modifier le 3ème paramètre (nbPartie) lors de l'appel du constructeur MoteurDuJeux dans la classe Jeux.

# RENDU0311
- Pour ce dernier rendu, on a implementé un bot intelligent contre le bot aléatoire actuel. Les choix de ce bot intelligent se tournent majoritairement vers le gain de points victoire. Sa stratégie est de faire plus des chargement possible sur le navire. Pour cela, d'abord il fait le choix au premier et choisit le paysan. Ensuite il choisit le mais parce qu'il  ne nécessite pas construire un bâtiment. En choissant par la suite les rôles le maire et le producteur il peut obtenir les tonneaux de maïs. Enfin, quand il choisit le rôle capitaine il peut charger ses tonneux de maïs et gagner des points de victoire.
- On a ajouté l'un des grands bâtiments Residence. À la fin du jeu, si la residence est occupée son propriétaire peut gagner des points de victoire en plus selon le nombre de carrière et plantation de son île.  Si le nombre de case occupée est inférieur ou égal à 9 il marque 4 points. 10 cases occupées rapportent 5 points de victoire, 11 en rapportent 6, et une île dont les 12 cases sont
couvertes par des carrières ou plantations rapporte 7 points.
- On a aussi amélioré le tour entre les joueurs. Au lieu de commencer à chaque tour toujours avec le premier joueur, le tour commence avec le joueur celui qui choisit le rôle pour ce tour.
- On a implementé le privilège du rôle producteur. Donc à la fin de la phase de production, après les actions de tous les joueurs, le joueur qui choisit ce rôle peut choisir un tonneau supplémentaire de son choix, d’une marchandise
qu’il vient de produire.  
- On a fait certaines modifications pour améliorer l'affichage. À la fin du jeu, on affiche les calculs des points de victoire concernant les bâtiments achetés, les nombre de chargement pendant la phase de capitaine et le grand bâtiment residence.
- Créations:
	- Classe BotIntelligent héritant de la classe Joueur
	- Classe Residence héritant de la classe Batiment

	* Ajout d'attribut et de méthodes:
		- Classe BotIntelligent:
			+  public Personnage choixRole(ArrayList<Personnage> roles, int tour) (Permet de faire choisir les rôles paysan, maire, producteur et capitaine dans cet ordre)
			+  public Exploitation choixExploitation(ArrayList<Exploitation> plantations, ArrayList<Exploitation> carrieres, boolean joueursActif, int tour) (Permet de choisir toujours la plantation maïs)<br/>

		- Classe Moteur du Jeu:
			+ public int residenceBonus(int nbExploitation) (Calcul des points victoire grâce à la residence)

# RENDU2810
- Dans ce rendu, on a ajouté le rôle capitaine (le derniere personnage du jeu) et les navires. Grâce au rôle capitaine les joueurs peuvent charger leurs marchandises sur les navires et pour chaque tonneau de marchandise chargé sur le navire ils peuvent gagner un point de victoire. Chaque navire peut transporter des marchandises dans une quantité limitée par le nombre de cases du navire. Si un joueur veut charger son tonneau sur un navire qui est déjà occupé, dans ce cas, la marchandise sur le navire et la marchandise à charger doivent être la même type. Quand un ou plusieurs navire(s) sont complet(s) dans une phase de capitaine, à la fin de cette phase les tonneaux sur ces navire(s) sont transférées à la réserve. Aussi on a considéré le privilège du capitaine donc le joueur qui choisit ce rôle peut avoir un point de victoire en plus après son premier chargement. Dans cette version, le bot fait son choix de marchandise et de navire aléatoirement. 
- On a créé aussi la classe Réserve pour stocker les marchandises déjà vendues dans le magasin ou chargées sur un navire.
- Créations:
	- Classe Capitaine héritant de la classe Personnage
	- Classe Navires
	- Classe Réserve 
	- Classe CapitaineTest
	
	* Ajout d'attribut et de méthodes:
		- Classe Capitaine:
			+ private ArrayList<Navires> navireDisponible(ArrayList<Navires> navires, String nomTonneau) [Déterminer les navires disponible pour le joueur concerné]
			+ public String action(Joueurs[] joueur, int i, ArrayList<Exploitation> plantations, ArrayList<Exploitation> carrieres, ArrayList<Batiment> batiments, Magasin magasin, Banque banque, Reserve reserve, ArrayList<Navires> navires)
		- Classe Navires:
			+ public int chargeTonneau(String nomTonneau, int nbreTonneau) [Chargement des tonneaux sur le navire]
			+ public void tranfertVersReserve(Reserve reserve) [Transférer des tonneaux à la réserve]
			+ public boolean estComplet() [Vérification d'un navire complet]
			+ public int getNbPlaceDisponible() [Le nombre de case disponible sur un navire]
			+ public String getNomRessource() [Le nom de marchandise chargé sur le navire]
			+ public String getNomNavire() [Le nom de navire concerné]
			+ public int getNbRessource() [Le nombre de marchandise chargé sur le navire] 
			+ public String[] getRessource() [Renvoyer le navire concerné]
		- Classe Joueur:
			+  public Navires choixNavire (ArrayList<Navires> navires)
		- Classe Réserve:
			+  public Reserve(int nbMais, int nbIndigo, int nbSucre, int nbTabac, int nbCafe) 
			+  public int getNbMarchandise(String nomMarchandise) [Le nombre d'une marchandise stockée dans la réserve]
			+  public String getAffichage() [Affichage de la réserve]
			+  public void ajouterMarchandise(String nomMarchandise, int nbMarchandise) 
			+  public void prendreMarchandise(String nomMarchandise, int nbMarchandise)
		- Classe CapitaineTest:
			+ 4 cas ont été testés: 
				+ Joueur possède aucun marchandise et il charge rien sur les navires
				+ Joueur possède une marchandise et il charge un tonneau de sa marchandise sur un navire
				+ Joueur possède 4 marchandise et il charge 4 tonneaux de sa marchandise sur un navire avec 4 cases. Ensuite ces 4 tonneaux sont transférées à la réserve.
				+ On a défini un deuxième bot qui bénéficie du privilège de rôle capitaine et il reçoit un point de victoire en plus après son premier chargement.			

- Modifications:	
	- Classe Plantations:
		+ On a supprimé cette classe par héritages et on a changé des instanciations avec passage par constante dans la classe Constantes.

# RENDU2010

- Dans ce rendu, nous avons ajouté le rôle marchand et le magasin. Pendant la phase du producteur, les marchandises sont stockées dans chaque instance des joueurs. Ensuite, la méthode "action" du marchand permet de récupérer les marchandises de chaque joueur et de choisir une marchandise aléatoirement. Si les joueurs ont des marchandises à vendre, la méthode action les place dans le magasin. Nous avons pris en compte la règle pour le magasin qui empêche de vendre le même type de marchandise. Aussi, le joueur qui choisit le rôle marchand peut avoir son privilège et donc il peut recevoir un doublon en plus après avoir vendu son premier tonneau.
- Nous avons implémenté le placement des colons dans un bâtiment ou une exploitation. Durant la phase du maire, le bot choisit de façon aléatoire de placer ou non ses colons. Le placement des colons se fait d'abord sur les plantations s'il y en a et si elles ne sont pas déjà occupées. Nous avons aussi ajouté les privilèges du paysant et du maire ainsi que la prise en charge des bonnus offert par l'hacienda.

- Créations:
	- Classe Marchand héritant de la classe Personnage
	- Classe Magasin 
    - Classe Carriere héritant de la classe Exploitation
    - Classe Hacienda héritant de la classe Batiment
    - Classe MarchandTest

    * Ajout d'attribut et de méthodes:	 
         - Classe Magasin: 
         	+  public void addDansMagasin(String tonneau) [Placer les marchandises dans le magasin]
         	+  public boolean dansMagasin(String tonneau) [Vérifier le type de la marchandise à vendre] 
         	+   public boolean estPlein() [Vérification de la place vide dans le magasin]
         	+   public int getNb_tonneau()  [Le nombre de marchandises dans le magasin]
         
		 - Classe Marchand: 
		 	+  private ArrayList venteDisponible(Joueurs j, Magasin magasin)
		 	+  public String action(Joueurs[] j, int i, ArrayList elements_1, ArrayList elements_2, Magasin magasin)
		 	
		 - Classe Plateau:
            + nb\_colon\_ile/cite
            + nb\_place\_cite (place total dans une cité)
            + Accesseurs

        - Classe Bot:
        	+ public String choixTonneau(ArrayList<String> vente)
            + private String placerColonBatiment/Plantation()
            + public String placerColon()

        - Classe Joueurs:
            + public void subColon()
            + public void addtonneau(String nom\_producteur)
            + public void subtonneau(String nom_production)
            + tonneaux (Hashmap pour stocker les marchandises) 
            + addNb méthodes pour chaque plantation

        - Classe Exploitation:
            + public void addColon()
            + public boolean estOccupe()

        - Classe BotTest:
            + Ajout du test pour la méthode choixBatiment

        - Classe Paysan:
            + public String haciendaBonus(Joueur j, ArrayList elements_1, ArrayList elements_2)
          
		- Classe MarchandTest:
		    + Le joueur possède un tonneau d'indigo et il le vend. Le test vérifie si ce tonneau est vendu (donc le joueurs possède 0 tonneau) et s'il y a un tonneau d'indigo dans le magasin.
            
        

    * Ajout de l'affichage du nombre de colon dans les bâtiments et plantations
    du joueur.

- Modifications:
    * Package:
        + Création de nouveaux packages "exploitations" et déplacemet du package
          "plantations" à l'intérieur. Modification du nom de classe
           "Plantation" -> "Exploitation". "exploitations" contient donc le
           package "plantations" ainsi que la classe Carriere.

    * Classe Joueurs:
        + Utilisation du setter permettant d'augmenter le nombre de place total
          pour les colons dans la cite du joueur dans la méthode
          "addBatiment(Batiment b)".

    - Classe Maire:
        + Utilisation de la méthode "placerColon()"

    - Classe Paysan:
        + Modification de la méthode pour prendre en charge le choix d'une
          carrière et l'utilisation

# RENDU1210
- Dans ce rendu, le rôle de producteur a été ajouté. Ce rôle permet aux joueurs de produire des marchandises (sans prendre en compte les nombres de colon). L'affichage a été modifié et on a ajouté des classes de test. <br/>
- Créations:

	* Classe Producteur:
		+ Permet d'augmenter le nombre de tonneau des marchandises du joueur selon le bâtiment de production lié à la plantation.
	* De nouvelles classes liées aux bâtiments:
        + Raffinerie de sucre, teinturerie d'indigo, brulerie de cafe, sechoir
          à tabac.

	* Classe de test avec Junit pour les classes suivantes:
		+ Batisseur
		+ Paysan
		+ Producteur
		+ Raffinerie_sucre
		+ Teinturerie_indigo
		+ Brulerie_cafe
		+ Sechoir_tabac

- Modifications:
	* Classe Batiment:
		+ Ajout d'un nouvel attribut "nom_plantation" ainsi que d'un accessseur pour la prise en charge future du rôle de producteur. Ce nom fera le lien entre la plantation et le batiment associé pour permettre de produire.

	* Classe de test:
		+ Correction de petites erreurs lors des tests cucumber dans le fichier
          test "RoleStepDefs" et le fichier feature "bot" qui ne permettaient
          pas de réussir les tests (WARNING: SKIPPED)

	* Classe paysan/batisseur:
		+ Maintenant lors des choix de plantations ou de batiments, un retour d'information affiche précisément le batiment ou la plantation choisi.

	* Classe MoteurDuJeux:
		+ Modification de l'affichage:
		+ Ajout de couleurs pour différencier les joueurs dans la méthode affichageScore(Joueur j, int i) [nouvel affichage]
		+ Ajout d'une règle pour déterminer le gagnant. Cette règle prends en compte les nombres de batîment choisi qui permet de produire une marchandise et ensuite donne un point victoire par plantations. A la fin du jeu, les joueurs recoivent un point victoire par chaque doublon restant. 
		+ Affichage le gagnant selon ses points victoires
     	
- Ajout de méthodes:
	* Dans MoteurDuJeux:
		+ public void affichagePlateau(Joueurs j) [Affiche le plateau d'un joueur]
		+ public Joueurs getMax_Pv(Joueurs[] j) [Retourne le joueur avec le plus points de victoires].<br/>

# RENDU0410

- Dans ce rendu, le rôle de batisseur a été ajouté ainsi que la création des batiments. Ce rôle permets aux joueurs de constrirure un bâtiment. Pour ce rendu, ils peuvent choisir seulement le batiment teinturerie d'indigo. 
	* Classe Batiment:
		+ Ajout des attributs nom, prix, nombre de colon ainsi que getter. 
		
	* Classe Batisseur:
		+ Il hèrite de la classe Personnage et utilise une méthode "addBatiment" de la classe Joueur pour ajouter un bâtiment.
		
	* Classe Constante:
		+ Ajout de la nouvelle constante de nom "batîsseur".
		
	* Classe Bot :
		+ Dans la méthode "choixRole", l'ajout du nouveau rôle dans la liste des rôles "sRole".
		
	* Classe Moteur de jeux :
		+ Dans la méthode "initRole", l'ajout de l'intialisation du nouveau rôle maire.<br/>
		+ Ajout de nouveaux feedbacks après le choix des rôles ainsi qu'un compteur pour le nombre de tour (facultatif pour ce rendu)<br/>




# RENDU0210

- Le rôle de maire a été ajouté. Ce rôle permet aux joueurs de recevoir un colon.
- Des modifications ont été apprortés dans les classes suivantes:
	* Bot : Dans la méthode "choixRole", l'ajout du nouveau rôle dans la liste des rôles "sRole".
	* Constante : Ajout de la nouvelle constante de nom "maire".
	* Moteur de jeux : <br/>
		+ Dans la méthode "initRole", l'ajout de l'intialisation du nouveau rôle maire.<br/>
		+ Aeration du code pour une meilleure lisibilité<br/>
		+ Ajout de nouveaux feedbacks après le choix des rôles ainsi qu'un compteur pour le nombre de tour<br/>
		+ Nouvel affichage des scores des joueurs sur une seule ligne. <br/>
		+ Les numeros des bots débutent maintenant au numéro 1.<br/>
		 				     
- Création d'un feature pour le scénario du choix d'un rôle (test Cucumber)
	
Deux nouvelles plantations ont été ajouté, sucre et indigo, mais à cette date
nous n'attribuons que la plantation de maïs aux paysans lorqu'il est choisi.





# CONTRIBUTEURS 

[DIALLO Elhadj Mamadou Foula](https://github.com/Diallo-ucad)<br/>
[JANIN Rémi](https://github.com/Remi-janin)<br/>
[PAJANY Allan ](https://github.com/Allan06)<br/>
[SARIGUZEL Begum](https://github.com/Begummm)
