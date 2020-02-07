#language: fr
Fonctionnalité: Choisir un rôle

  Contexte:
    Etant donné un joueur du nom de "Joueur 1"
    Et une liste de rôle "Chercheur d'or", "Paysan" et "Maire"

  Scénario: Pas de choix par défaut
    Quand "Joueur 1" choisi le personnage "Chercheur d'or"
    Alors le "Joueur 1" gagne 1 doublon
    Et le role "Chercheur d'or" est supprimer de la liste
