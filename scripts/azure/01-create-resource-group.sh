#!/usr/bin/env bash
set -euo pipefail

# Cria o Resource Group na Azure
# Uso: ./01-create-resource-group.sh

RESOURCE_GROUP="${AZURE_RESOURCE_GROUP:-rg-orbitagro-564105}"
LOCATION="${AZURE_LOCATION:-brazilsouth}"

echo "==> Criando Resource Group: ${RESOURCE_GROUP} em ${LOCATION}"

az group create \
  --name "${RESOURCE_GROUP}" \
  --location "${LOCATION}"

echo "==> Resource Group criado com sucesso."
