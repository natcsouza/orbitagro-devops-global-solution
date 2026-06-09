#!/usr/bin/env bash
set -euo pipefail

# Provisiona toda a infraestrutura Azure para o OrbitAgro
# Uso: ./provision-all.sh

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "========================================"
echo "  OrbitAgro - Provisionamento Azure"
echo "========================================"

if ! command -v az &> /dev/null; then
  echo "ERRO: Azure CLI não encontrado. Instale: https://aka.ms/installazurecli"
  exit 1
fi

if ! az account show &> /dev/null; then
  echo "ERRO: Faça login com: az login"
  exit 1
fi

bash "${SCRIPT_DIR}/01-create-resource-group.sh"
bash "${SCRIPT_DIR}/02-create-vm.sh"
bash "${SCRIPT_DIR}/03-configure-nsg.sh"
bash "${SCRIPT_DIR}/04-open-ports.sh"

echo ""
echo "==> Provisionamento concluído!"
echo "==> Próximo passo: execute o deploy na VM com scripts/deploy/deploy.sh"
