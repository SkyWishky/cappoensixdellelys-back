# ☕ Backend Service - Tamagotchi REST API (Spring Boot 21)

[![CI/CD Pipeline](https://github.com/SkyWishky/cappoensixdellelys-back/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/SkyWishky/cappoensixdellelys-back/actions/workflows/ci-cd.yml)
[![Docker Image](https://img.shields.io/badge/Docker-GHCR-blue?logo=docker)](https://github.com/SkyWishky/cappoensixdellelys-back/pkgs/container/cappoensixdellelys-back)
[![Branching](https://img.shields.io/badge/Git-Gitflow-orange?logo=git)](GITFLOW.md)
[![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk)](https://openjdk.org)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1.1-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-4169E1?logo=postgresql)](https://www.postgresql.org)

API REST backend développée sous **Java 21** et **Spring Boot 4.1.1** pour la gestion de l'état des animaux virtuels (Tamagotchi), orchestrée avec une base de données PostgreSQL relationnelle et un service planifié d'évolution temporelle de la faim et du bonheur des familiers.

Projet réalisé dans le cadre du **TP Noté DevOps AP4 (2026)** - **Groupe 6 : Cappoen / Six / Dellelys**.

---

## 🏗️ Architecture & Fonctionnalités

- **Runtime & Framework** : Java 21 (Eclipse Temurin) avec Spring Boot 4.1.1.
- **Persistance des Données** : Spring Data JPA, Hibernate, pilote JDBC PostgreSQL (`ddl-auto: validate`).
- **Moteur de Simulation** : Service planifié Spring (`@Scheduled`) appliquant la dégradation périodique (`PET_DECAY_INTERVAL`, par défaut toutes les 10 secondes) des statistiques des animaux (faim, bonheur, vie).
- **Observabilité & Santé** : Spring Boot Actuator exposé sur `/actuator/health` et `/actuator/info` pour les probes de santé Docker et Coolify.
- **Conteneurisation Multi-stage** :
  - Étape 1 (`build`) : Maven 3.9.6 + JDK 21 Alpine pour compiler et packager l'archive jar.
  - Étape 2 (`runtime`) : JRE 21 Alpine allégé, utilisateur non-root `appuser:appgroup` (sécurité renforcée contre l'élévation de privilèges).
  - Directive native `HEALTHCHECK` vérifiant la disponibilité de l'application via `wget --spider http://localhost:8080/actuator/health`.

---

## 📡 Endpoints de l'API REST

| Méthode | Route | Description |
|---|---|---|
| `GET` | `/api/pets` | Liste de tous les Tamagotchis et leur état |
| `GET` | `/api/pets/{id}` | Détail d'un animal spécifique |
| `POST` | `/api/pets` | Création d'un nouveau Tamagotchi |
| `POST` | `/api/pets/{id}/feed` | Nourrir le Tamagotchi (augmente la satiété) |
| `POST` | `/api/pets/{id}/play` | Jouer avec le Tamagotchi (augmente le bonheur) |
| `GET` | `/actuator/health` | Sonde de santé applicative (Liveness/Readiness) |

---

## 🚀 Pipeline CI/CD (GitHub Actions)

Workflow automatisé défini dans `.github/workflows/ci-cd.yml` :

1. **☕ Maven Test & Build** : Exécution des tests unitaires et d'intégration avec Java 21 Temurin.
2. **🐳 Linter Docker** : Vérification des bonnes pratiques avec Hadolint.
3. **🛡️ Audit Sécurité Trivy** : Scan des dépendances logicielles et détection de vulnérabilités connues (CVE).
4. **📦 Build & Push GHCR** : Publication des images conteneur sur GitHub Container Registry :
   - `ghcr.io/skywishky/cappoensixdellelys-back:latest` (branche `main`)
   - `ghcr.io/skywishky/cappoensixdellelys-back:dev-latest` (branche `develop`)
   - `ghcr.io/skywishky/cappoensixdellelys-back:vX.Y.Z` (tags de version)
5. **🔄 Continuous Deployment** : Déclenchement du webhook Coolify uniquement après un push sur `main`.

---

## 🌿 Stratégie Gitflow

Consultez le document dédié : **[GITFLOW.md](GITFLOW.md)**.

- **`main`** : Code de production stabilisé.
- **`develop`** : Branche principale d'intégration continue.
- **`feature/*`**, **`release/*`**, **`hotfix/*`** : Branches de travail isolées.

---

## ⚙️ Configuration & Variables d'Environnement

| Variable | Description | Exemple |
|---|---|---|
| `SERVER_PORT` | Port d'écoute de l'API | `8080` |
| `DB_HOST` | Hôte du serveur PostgreSQL | `database` ou `localhost` |
| `DB_PORT` | Port du serveur PostgreSQL | `5432` |
| `DB_NAME` | Nom de la base de données | `tamagochi` |
| `DB_USER` | Utilisateur de connexion à la base | `tamagochi` |
| `DB_PASSWORD` | Mot de passe de la base | `replace-with-a-unique-local-password` |
| `PET_DECAY_INTERVAL` | Intervalle de dégradation en millisecondes | `10000` |

---

## 🐳 Lancement Manuel (Docker)

```bash
# Build de l'image
docker build -t cappoensixdellelys-back .

# Exécution avec liaison vers une base PostgreSQL existante
# Exportez un mot de passe unique dans DB_PASSWORD avant de lancer cette commande.
docker run -d \
  --name tamagotchi-back \
  -p 8080:8080 \
  -e DB_HOST=host.docker.internal \
  -e DB_PORT=5432 \
  -e DB_NAME=tamagochi \
  -e DB_USER=tamagochi \
  -e DB_PASSWORD="${DB_PASSWORD:?Export DB_PASSWORD first}" \
  cappoensixdellelys-back
```

Vérification de l'état de l'API :
```bash
curl http://localhost:8080/actuator/health
# Réponse attendue : {"status":"UP"}
```

---

## 📦 Dépôts du Projet

- 🌐 **Frontend** : [https://github.com/SkyWishky/cappoensixdellelys-front](https://github.com/SkyWishky/cappoensixdellelys-front)
- ☕ **Backend** : [https://github.com/SkyWishky/cappoensixdellelys-back](https://github.com/SkyWishky/cappoensixdellelys-back)
- 🗄️ **Database & Infra** : [https://github.com/SkyWishky/cappoensixdellelys-database](https://github.com/SkyWishky/cappoensixdellelys-database)
- 🌍 **Déploiement Production Coolify** : [https://tpfinal.tp-devops-6.online](https://tpfinal.tp-devops-6.online)
