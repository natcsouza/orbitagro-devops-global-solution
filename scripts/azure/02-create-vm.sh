#!/usr/bin/env bash
set -euo pipefail

# Cria VM Linux Ubuntu 22.04 com NSG (portas 22, 8080, 5432)
# Uso: ./02-create-vm.sh

RESOURCE_GROUP="${AZURE_RESOURCE_GROUP:-rg-orbitagro-564099}"
LOCATION="${AZURE_LOCATION:-brazilsouth}"
VM_NAME="${AZURE_VM_NAME:-vm-orbitagro-564099}"
VM_SIZE="${AZURE_VM_SIZE:-Standard_B2ats_v2}"
ADMIN_USERNAME="${AZURE_ADMIN_USERNAME:-azureuser}"
SSH_KEY_PATH="${AZURE_SSH_KEY_PATH:-$HOME/.ssh/id_rsa_orbitagro.pub}"
NSG_NAME="${AZURE_NSG_NAME:-nsg-orbitagro-564099}"
VNET_NAME="${AZURE_VNET_NAME:-vnet-orbitagro-564099}"
SUBNET_NAME="${AZURE_SUBNET_NAME:-subnet-orbitagro-564099}"
PUBLIC_IP_NAME="${AZURE_PUBLIC_IP_NAME:-pip-orbitagro-564099}"
NIC_NAME="${AZURE_NIC_NAME:-nic-orbitagro-564099}"

if [ ! -f "${SSH_KEY_PATH/#\~/$HOME}" ]; then
  echo "ERRO: Chave SSH não encontrada em ${SSH_KEY_PATH}"
  echo "Gere com: ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa_orbitagro -C \"orbitagro-azure-564099\""
  exit 1
fi

echo "==> Criando VM: ${VM_NAME}"

az vm create \
  --resource-group "${RESOURCE_GROUP}" \
  --name "${VM_NAME}" \
  --image Ubuntu2204 \
  --size "${VM_SIZE}" \
  --admin-username "${ADMIN_USERNAME}" \
  --authentication-type ssh \
  --ssh-key-values "@${SSH_KEY_PATH/#\~/$HOME}" \
  --public-ip-address "${PUBLIC_IP_NAME}" \
  --nsg "${NSG_NAME}" \
  --vnet-name "${VNET_NAME}" \
  --subnet "${SUBNET_NAME}" \
  --nic "${NIC_NAME}" \
  --location "${LOCATION}" \
  --generate-ssh-keys

echo "==> VM criada com sucesso."
echo "==> IP público:"
az vm show -d \
  --resource-group "${RESOURCE_GROUP}" \
  --name "${VM_NAME}" \
  --query publicIps \
  --output tsv
