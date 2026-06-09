#!/usr/bin/env bash
set -euo pipefail

# Deploy da aplicação OrbitAgro na VM Linux
# Uso: ./deploy.sh [URL_DO_REPOSITORIO]

REPO_URL="${1:-https://github.com/SEU_USUARIO/orbitagro-api.git}"
APP_DIR="${APP_DIR:-/opt/orbitagro}"
BRANCH="${BRANCH:-main}"

echo "========================================"
echo "  OrbitAgro - Deploy na VM Linux"
echo "========================================"

if ! command -v docker &> /dev/null; then
  echo "ERRO: Docker não encontrado. Execute: sudo ./install-docker.sh"
  exit 1
fi

if ! docker compose version &> /dev/null; then
  echo "ERRO: Docker Compose plugin não encontrado."
  exit 1
fi

echo "==> Preparando diretório ${APP_DIR}..."
sudo mkdir -p "${APP_DIR}"

if [ -d "${APP_DIR}/.git" ]; then
  echo "==> Atualizando repositório existente..."
  cd "${APP_DIR}"
  git fetch origin
  git checkout "${BRANCH}"
  git pull origin "${BRANCH}"
else
  echo "==> Clonando repositório..."
  sudo git clone "${REPO_URL}" "${APP_DIR}"
  cd "${APP_DIR}"
  git checkout "${BRANCH}"
fi

if [ ! -f .env ]; then
  echo "==> Criando .env a partir do .env.example..."
  cp .env.example .env
  echo "ATENÇÃO: Revise o arquivo .env antes de usar em produção."
fi

echo "==> Parando containers anteriores..."
docker compose down --remove-orphans 2>/dev/null || true

echo "==> Construindo e iniciando containers..."
docker compose up -d --build

echo "==> Aguardando API ficar disponível..."
sleep 15

echo "==> Status dos containers:"
docker compose ps

API_PORT="${SERVER_PORT:-8080}"
echo ""
echo "==> Deploy concluído!"
echo "==> API:      http://$(hostname -I | awk '{print $1}'):${API_PORT}"
echo "==> Swagger:  http://$(hostname -I | awk '{print $1}'):${API_PORT}/swagger-ui.html"
