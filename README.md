# Projet_JDBC
Creer des classes java pour faire des requetes a un base de donné avec JDBC

les requetes JDBC sont répartie en deux type:
- **Select**
  - Select 
- **Update**
  - Tout les autres type de requêtes (Update, Alter, Insert, Create, Drop )

Librairie Utiliser:
````xml
    <!-- Modifier le pom pour mettre votre type de base de donnée (par défault mariadb est dedans) -->

    <!-- Pour ne pas faire les getter et setter-->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.34</version>
      <scope>provided</scope>
    </dependency>
````

1. AfficheSelectRequest:
permet d'afficher le retour de la base de donnée dans l'attribut a lui (resultat)

2. MappingSelectRequest
permet de mapper un retour de la base de donné

## Ajout futur:
- Classe pour faire les type de requetes
