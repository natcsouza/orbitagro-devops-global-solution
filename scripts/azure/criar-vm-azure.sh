#!/usr/bin/env bash
set -euo pipefail

# OrbitAgro — Cria VM AlmaLinux na Azure (estilo GS DevOps)
# Uso: chmod +x criar-vm-azure.sh && ./criar-vm-azure.sh
#
# Pré-requisitos:
#   - Azure CLI instalado (az --version)
#   - az login realizado
#   - Chave SSH em ~/.ssh/id_rsa.pub

RESOURCE_GROUP="${AZURE_RESOURCE_GROUP:-rg-orbitagro-564099}"
LOCATION="${AZURE_LOCATION:-brazilsouth}"
VM_NAME="${AZURE_VM_NAME:-vm-orbitagro-564099}"
VM_SIZE="${AZURE_VM_SIZE:-Standard_B2s}"
ADMIN_USERNAME="${AZURE_ADMIN_USERNAME:-azureuser}"
SSH_KEY_PATH="${AZURE_SSH_KEY_PATH:-$HOME/.ssh/id_rsa.pub}"

echo "========================================"
echo "  OrbitAgro — Criar VM AlmaLinux Azure"
echo "========================================"

if ! command -v az &> /dev/null; then
  echo "ERRO: Azure CLI não encontrado."
  echo "Windows: winget install -e --id Microsoft.AzureCLI"
  echo "Depois feche e abra o terminal."
  exit 1
fi

if ! az account show &> /dev/null; then
  echo "ERRO: Faça login primeiro: az login"
  exit 1
fi

if [ ! -f "${SSH_KEY_PATH/#\~/$HOME}" ]; then
  echo "ERRO: Chave SSH não encontrada em ${SSH_KEY_PATH}"
  echo "Gere com: ssh-keygen -t rsa -b 4096"
  exit 1
fi

echo "==> [1/4] Criando Resource Group..."
az group create --name "${RESOURCE_GROUP}" --location "${LOCATION}" --output none

echo "==> [2/4] Criando VM AlmaLinux 9 (pode demorar alguns minutos)..."
az vm create \
  --resource-group "${RESOURCE_GROUP}" \
  --name "${VM_NAME}" \
  --image almalinux:9-gen2 \
  --size "${VM_SIZE}" \
  --admin-username "${ADMIN_USERNAME}" \
  --authentication-type ssh \
  --ssh-key-values "@${SSH_KEY_PATH/#\~/$HOME}" \
  --public-ip-sku Standard \
  --location "${LOCATION}" \
  --output none

echo "==> [3/4] Abrindo portas 22, 8080 e 5432 no NSG..."
NSG_NAME=$(az network nsg list --resource-group "${RESOURCE_GROUP}" --query "[0].name" -o tsv)

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
  --output none 2>/dev/null || true

echo "==> [4/4] Obtendo IP público..."
PUBLIC_IP=$(az vm show -d \
  --resource-group "${RESOURCE_GROUP}" \
  --name "${VM_NAME}" \
  --query publicIps \
  --output tsv)

echo ""
echo "========================================"
echo "  VM CRIADA COM SUCESSO!"
echo "========================================"
echo "  Nome:  ${VM_NAME}"
echo "  SO:    AlmaLinux 9"
echo "  IP:    ${PUBLIC_IP}"
echo ""
echo "  Conectar:"
echo "  ssh ${ADMIN_USERNAME}@${PUBLIC_IP}"
echo "========================================"
