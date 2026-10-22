# Gitflow du projet

Ce projet utilise une organisation en branches Gitflow :

- `main` : Branche de production, correspond a la version stable deployee sur Coolify.
- `develop` : Branche de developpement principal et d'integration.
- `feature/*` : Branches temporaires pour developper de nouvelles fonctionnalites avant fusion sur `develop`.
- `hotfix/*` : Branches pour corriger un bug critique directement depuis `main`.

## 📌 Architecture des Branches

```text
  main ──────●────────────────────────● (v1.0.0 / Production)
             ▲                        ▲
             │                        │ Merge release
  release ───┼──────────●─────────────┘
             │          ▲
             │          │ Cut release
  develop ───●──●───────●──●──────────► (CI ; publication dev-latest, sans déploiement)
                ▲       ▲  ▲
     feature ───┘       │  │
                        └──┘
```

### 1. Branches Principales (Long-lived)

| Branche | Rôle | Environnement de déploiement | Protection |
|---|---|---|---|
| **`main`** | Code stable de production | Coolify Production Backend | Protection à configurer par un administrateur |
| **`develop`** | Intégration continue | CI et publication `dev-latest` ; aucun déploiement automatique | Protection à configurer par un administrateur |

### 2. Branches Temporaires (Short-lived)

- **`feature/<nom-fonctionnalite>`** :
  - Créée depuis : `develop`
  - Mergée dans : `develop`
  - Exemple : `feature/pet-energy-statistic`, `feature/leaderboard-api`
- **`release/vX.Y.Z`** :
  - Créée depuis : `develop`
  - Mergée dans : `main` ET `develop`
  - Taggée sur `main` avec le tag de version semver `vX.Y.Z`.
- **`hotfix/<nom-correctif>`** :
  - Créée depuis : `main`
  - Mergée dans : `main` ET `develop`
  - Permet de corriger un incident critique en production.

---

## 🛡️ Règles de Validation CI/CD

Avant tout merge sur `develop` ou `main`, le pipeline GitHub Actions valide obligatoirement :
1. ✅ Exécution des tests unitaires Maven (`mvn test`).
2. ✅ Hadolint sur le `Dockerfile` multi-stage.
3. ✅ Audit de vulnérabilités Trivy.
4. ✅ Publication sur GHCR (`ghcr.io/skywishky/cappoensixdellelys-back`).

Le workflow déclenche Coolify uniquement après un push sur `main`. Les protections GitHub des branches n’étaient pas configurées/vérifiables lors de cet audit ; leur activation nécessite un compte administrateur.
