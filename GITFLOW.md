# 🌿 Stratégie de Branches Gitflow - Backend

Ce dépôt respecte la méthodologie standard **Gitflow** pour assurer un cycle de livraison fiable, traçable et sécurisé.

---

## 📌 Architecture des Branches

```text
  main ──────●────────────────────────● (v1.0.0 / Production)
             ▲                        ▲
             │                        │ Merge release
  release ───┼──────────●─────────────┘
             │          ▲
             │          │ Cut release
  develop ───●──●───────●──●──────────► (Intégration continue / Staging)
                ▲       ▲  ▲
     feature ───┘       │  │
                        └──┘
```

### 1. Branches Principales (Long-lived)

| Branche | Rôle | Environnement de déploiement | Protection |
|---|---|---|---|
| **`main`** | Code stable de production | Coolify Production Backend | Protégée (PR requise, checks CI validés) |
| **`develop`** | Intégration continue | Coolify Staging Backend | Protégée (Merge via PR uniquement) |

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
