#!/usr/bin/env bash
set -euo pipefail

# Configura regras do NSG para SSH, API e PostgreSQL
# Uso: ./03-configure-nsg.sh

RESOURCE_GROUP="${AZURE_RESOURCE_GROUP:-rg-orbitagro-564099}"
NSG_NAME="${AZURE_NSG_NAME:-nsg-orbitagro-564099}"

echo "==> Configurando NSG: ${NSG_NAME}"

az network nsg rule create \
  --resource-group "${RESOURCE_GROUP}" \
  --nsg-name "${NSG_NAME}" \
  --name AllowSSH \
  --priority 1000 \
  --direction Inbound \
  --access Allow \
  --protocol Tcp \
  --destination-port-ranges 22 \
  --source-address-prefixes '*' \
  --destination-address-prefixes '*' \
  --output none 2>/dev/null || true

az network nsg rule create \
  --resource-group "${RESOURCE_GROUP}" \
  --nsg-name "${NSG_NAME}" \
  --name AllowAPI \
  --priority 1010 \
  --direction Inbound \
  --access Allow \
  --protocol Tcp \
  --destination-port-ranges 8080 \
  --source-address-prefixes '*' \
  --destination-address-prefixes '*' \
  --output none 2>/dev/null || true

az network nsg rule create \
  --resource-group "${RESOURCE_GROUP}" \
  --nsg-name "${NSG_NAME}" \
  --name AllowPostgreSQL \
  --priority 1020 \
  --direction Inbound \
  --access Allow \
  --protocol Tcp \
  --destination-port-ranges 5432 \
  --source-address-prefixes '*' \
  --destination-address-prefixes '*' \
  --output none 2>/dev/null || true

echo "==> Regras NSG configuradas (22, 8080, 5432)."
