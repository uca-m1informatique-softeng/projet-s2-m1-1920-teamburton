#language: fr

Fonctionnalité: Choisir une Plantation


  Contexte:

    Etant donné un autre joueur du nom de "Joueur 1"
    Et la liste de plantation "Cafe", "Sucre", "Indigo", "Mais" et "Tabac"

  Scénario: Choix d'une plantation
    Quand le "Joueur 1" choisi un role "Paysan"
    Alors le "Joueur 1" choisi une plantation parmi la liste de plantation
    Et la plantation choisie est ajoutee a son plateau
    Et la plantation choisie est retiree de la liste