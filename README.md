# Application Projet Android/IOT - Yann Alle & Bastien Dupuch

Pour cuase de difficultés techniques sous Windows (l'éditeur visuel Android avait des problèmes d'affichage), l'application a été créée sour Linux avec le SDK Java 1.8 avec l'API 29 d'Android en target version et l'API 21 en min version.
Il se peut donc qu'il soit difficile de lancer un build de l'application, nous avons donc fourni un APK à la racine du repo et une vidéo de démonstration de l'application avec notre objet connecté (une serrure de porte avec des bagdes NFC) :

https://youtu.be/i3vVKb2VDxg

(Nous avons oublié de mentionner dans la vidéo que les champs des formulaires de connexion/enregistrement ne peuvent pas être vides (erreur / alerte affichée au niveau des champs si champ vide) + Erreur affichée si mauvais identifiants au login).

Fonctions implémentées dans l'application :
- Login utilisateur
- Enregistrement utilisateur (+ liste déroulante des pays)
- Affichage de la liste des serrures associées à l'utilisateur
- Déverouillage avec mot de passe des serrures possédées par l'utilisateur (via le clic sur l'une des serrures de la liste)
- Ajout d'une serrure (existante en base de données) à l'utilisateur via adresse MAC + mot de passe
- Affichage de la liste des logs des serrures
