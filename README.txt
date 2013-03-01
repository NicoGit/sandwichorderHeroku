//-----------------------------------------------------------------------------------------
						UTILISATION L'APPLICATION WEB COTE UTILISATEUR
//-----------------------------------------------------------------------------------------


A l'ouverture l'application propose une page de connexion pour laquelle on a besoin d'un identifiant et d'un mdp fort EMN.
Pour se connecter en administrateur il faut utiliser : identifiant = admin ; mdp = admin. 

Sur la page d'accueil de la WebApp les utilisateurs peuvent ajouter des sandwichs au panier grâce à un catalogue défilant. Lors de l'ajout au panier, l'utilisateur peut choisir la quantité et la date.
Pour confirmer un panier et l'ajouter à la base de données, l'utilisateur doit cliquer sur le bouton "Confirmer la commande" disponible dans le panier. Une fois confirmée, cette commande n'est plus modifiable par l'utilisateur (seulement par l'admin).

L'utilisateur dispose d'une page commande où il peut visualiser ses commandes du jour et les commandes futurs.

Une session peut être déconnéctée à tout moment en allant dans l'onglet 'Mon compte'."Deconnexion".

//-----------------------------------------------------------------------------------------
						UTILISATION L'APPLICATION WEB COTE ADMINISTRATEUR
//-----------------------------------------------------------------------------------------

Un admin peut accéder à la partie administration à tout moment par le lien administration présent dans le pied de page.
Dans les deux pages du panneau d'administration, l'admin peut :

#Page "Les sandiwchs"
- ajouter ou modifier ou supprimer un ingrédient.
- ajouter ou modifier ou supprimer un sandwich. 
- pour chaque sandwich de la liste, l'admin peut modifier les caractéristiques (nom, description, prix et la disponibilité).
	L'admin peut également ajouter un ingrédient présent dans la liste sur la droite de la page au sandwich.
	ATTENTION : un prix doit être écrit avec un "." et non une virgule pour séparer les centimes des entiers. 
- pour dire que le sandwich n'est plus vendu, il faut cliquer sur "modifier" du sandwich en question, puis décocher "disponible actuellement"

#Page "Les commandes"

- l'admin peut visualiser les commandes du jour, les modifier et/ou les supprimer.
- Il peut également visualiser les commandes futures.
- Un graphe présente les ventes de la semaine passée par jour et par sandwich.


NB : Nous vous conseillons d'utiliser le navigateur google chrome qui propose sa propre interface pour les balise HTML5 <input type="date" />
	 Exépté Opéra les autres navigateurs vous demanderons de saisir un texte. Vous devrez alors saisir la date sous la forme : 2012-11-15 pour le 15 novembre 2012.
	 

