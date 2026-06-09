#!/usr/bin/env bash
set -euo pipefail

# Instala Docker e Docker Compose na VM Linux Ubuntu
# Uso: sudo ./install-docker.sh

if [ "$(id -u)" -ne 0 ]; then
  echo "ERRO: Execute como root ou com sudo."
  exit 1
fi

echo "==> Atualizando pacotes..."
apt-get update -y
apt-get upgrade -y

echo "==> Instalando dependências..."
apt-get install -y \
  ca-certificates \
  curl \
  gnupg \
  lsb-release \
  git

echo "==> Adicionando repositório Docker..."
install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
chmod a+r /etc/apt/keyrings/docker.asc

echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(. /etc/os-release && echo "${VERSION_CODENAME}") stable" \
  > /etc/apt/sources.list.d/docker.list

apt-get update -y

echo "==> Instalando Docker Engine e Compose Plugin..."
apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

echo "==> Habilitando Docker no boot..."
systemctl enable docker
systemctl start docker

if [ -n "${SUDO_USER:-}" ]; then
  usermod -aG docker "${SUDO_USER}"
  echo "==> Usuário ${SUDO_USER} adicionado ao grupo docker."
fi

echo "==> Docker instalado:"
docker --version
docker compose version
