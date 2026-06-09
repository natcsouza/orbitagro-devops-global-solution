#!/usr/bin/env bash
set -euo pipefail

# Abre portas no firewall da VM via Azure CLI (extensão run-command)
# Uso: ./04-open-ports.sh

RESOURCE_GROUP="${AZURE_RESOURCE_GROUP:-rg-orbitagro-564099}"
VM_NAME="${AZURE_VM_NAME:-vm-orbitagro-564099}"

echo "==> Abrindo portas no firewall da VM ${VM_NAME}"

az vm run-command invoke \
  --resource-group "${RESOURCE_GROUP}" \
  --name "${VM_NAME}" \
  --command-id RunShellScript \
  --scripts "
    sudo ufw allow 22/tcp
    sudo ufw allow 8080/tcp
    sudo ufw allow 5432/tcp
    sudo ufw --force enable
    sudo ufw status
  "

echo "==> Portas abertas na VM."
