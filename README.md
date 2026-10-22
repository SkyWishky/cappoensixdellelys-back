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

Copiez `.env.example` vers `.env`, remplacez `POSTGRES_PASSWORD` par un secret local, puis démarrez la stack :

```bash
cp .env.example .env
docker compose up -d
```

L'application est disponible sur [http://localhost:4200](http://localhost:4200); l'API reste interne au réseau Compose et est accessible via le proxy frontend. Pour arrêter les conteneurs :

```bash
docker compose down
```

## Configuration

| Variable | Description | Exemple |
|---|---|---|
| `SERVER_PORT` | Port d'écoute de l'API | `8080` |
| `DB_HOST` | Hôte PostgreSQL | `database` |
| `DB_PORT` | Port PostgreSQL | `5432` |
| `DB_NAME` | Nom de la base | `tamagochi` |
| `DB_USER` | Utilisateur PostgreSQL | `tamagochi` |
| `DB_PASSWORD` | Secret PostgreSQL requis | Secret local unique |
| `PET_DECAY_INTERVAL` | Intervalle de dégradation en millisecondes | `10000` |

## Pipeline CI/CD

Le workflow `.github/workflows/ci-cd.yml` exécute les tests Maven, Hadolint et Trivy, puis publie l'image GHCR. `main` publie `latest` et déclenche le webhook Coolify; `develop` publie `dev-latest` sans déployer la production.

## Gitflow

Consultez [GITFLOW.md](GITFLOW.md). La protection des branches GitHub doit être configurée par un administrateur.

## Exécution manuelle du conteneur

Pour connecter l'API à une base PostgreSQL disponible sur l'hôte, exportez d'abord `DB_PASSWORD`, puis lancez :

```bash
docker build -t cappoensixdellelys-back .
docker run -d --name tamagotchi-back \
  -p 127.0.0.1:8080:8080 \
  --add-host=host.docker.internal:host-gateway \
  -e DB_HOST=host.docker.internal -e DB_PORT=5432 \
  -e DB_NAME=tamagochi -e DB_USER=tamagochi \
  -e DB_PASSWORD="${DB_PASSWORD:?Export DB_PASSWORD first}" \
  cappoensixdellelys-back
```

## Dépôts du projet

- Frontend : https://github.com/SkyWishky/cappoensixdellelys-front
- Backend : https://github.com/SkyWishky/cappoensixdellelys-back
- Base de données et infrastructure : https://github.com/SkyWishky/cappoensixdellelys-database
