#!/usr/bin/env bash
set -euo pipefail

# Atualiza a aplicação OrbitAgro na VM (git pull + rebuild)
# Uso: ./update.sh

APP_DIR="${APP_DIR:-/opt/orbitagro}"
BRANCH="${BRANCH:-main}"

echo "==> Atualizando OrbitAgro em ${APP_DIR}..."

if [ ! -d "${APP_DIR}/.git" ]; then
  echo "ERRO: Repositório não encontrado. Execute deploy.sh primeiro."
  exit 1
fi

cd "${APP_DIR}"

git fetch origin
git checkout "${BRANCH}"
git pull origin "${BRANCH}"

docker compose down
docker compose up -d --build

echo "==> Atualização concluída."
docker compose ps
