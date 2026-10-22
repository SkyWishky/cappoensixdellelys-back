# Backend - API Tamagotchi (Spring Boot)

Projet realise dans le cadre du TP Note DevOps AP4 2026.
Groupe 6 : Six Theo, Delelys Florian, Cappoen Louis.

API REST developpee avec Java 21 et Spring Boot pour gerer les animaux virtuels (nourriture, jeu, etat de sante) connectee a une base PostgreSQL.

URL en production : https://tpfinal.tp-devops-6.online

## Architecture

- Langage : Java 21
- Framework : Spring Boot
- Base de donnees : PostgreSQL 16
- Securite du conteneur : Execution avec un utilisateur dedie non-root (`appuser`).
- Sonde de sante : Spring Boot Actuator sur `/actuator/health`.

## Endpoints principaux

- `GET /api/pets` : Liste des animaux
- `GET /api/pets/{id}` : Details d'un animal
- `POST /api/pets` : Creation d'un nouvel animal
- `POST /api/pets/{id}/feed` : Nourrir l'animal
- `POST /api/pets/{id}/play` : Jouer avec l'animal
- `GET /actuator/health` : Etat de sante de l'API

## Lancer en local avec Docker Compose

Pour lancer l'application en local (build le backend et telecharge le frontend et la base de donnees depuis le registre) :

```bash
docker compose up -d
```

L'API sera accessible sur http://localhost:8080.

Pour arreter les conteneurs :
```bash
docker compose down
```

## Lancer le conteneur seul

```bash
docker build -t backend-tamagotchi .
docker run -d -p 8080:8080 -e DB_HOST=localhost -e DB_USER=tamagochi -e DB_PASSWORD=tamagochi backend-tamagotchi
```

## Variables d'environnement

- `SERVER_PORT` : Port de l'application (8080).
- `DB_HOST` : Adresse de la base de donnees (database ou localhost).
- `DB_PORT` : Port de la base de donnees (5432).
- `DB_NAME` : Nom de la base (tamagochi).
- `DB_USER` : Utilisateur de la base (tamagochi).
- `DB_PASSWORD` : Mot de passe de la base (tamagochi_secret).
- `PET_DECAY_INTERVAL` : Intervalle de mise a jour de l'etat des animaux (10000 ms).

## Pipeline CI/CD (GitHub Actions)

A chaque push sur `main` et `develop`, le pipeline execute :
1. Tests unitaires et compilation Maven avec Java 21.
2. Verification du Dockerfile avec Hadolint.
3. Scan de securite des dependances avec Trivy.
4. Construction et publication de l'image Docker sur GitHub Container Registry (ghcr.io).
5. Declenchement du webhook de deploiement automatique sur Coolify.

## Liens des depots du projet

- Frontend : https://github.com/SkyWishky/cappoensixdellelys-front
- Backend : https://github.com/SkyWishky/cappoensixdellelys-back
- Database et Infra : https://github.com/SkyWishky/cappoensixdellelys-database
