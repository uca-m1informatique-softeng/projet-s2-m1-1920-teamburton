#language: fr

Fonctionnalité: Choisir un Batiment


  Contexte:

    Etant donné le joueur du nom de "Joueur 1"
    Et la liste de batiments de productions "Raffinerie de sucre", "Brulerie de cafe", "Sechoir a tabac", "Teinturerie d'indigo", "Petite raffinerie de sucre" et "Petite teinturerie d'indigo"

  Scénario: Choix d'un bâtiment
     Quand le "Joueur 1" choisi le role "Batisseur"
     Alors le "Joueur 1" choisi un batiment parmi la liste de batiment
     Et le batiment choisi est ajoute a son plateau
     Et le batiment choisi est retire de la liste

