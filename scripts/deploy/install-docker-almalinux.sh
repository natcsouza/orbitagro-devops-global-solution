#!/usr/bin/env bash
set -euo pipefail

# Instala Docker e Docker Compose na VM AlmaLinux (dnf)
# Uso: sudo ./install-docker-almalinux.sh

if [ "$(id -u)" -ne 0 ]; then
  echo "ERRO: Execute como root ou com sudo."
  exit 1
fi

echo "==> Atualizando pacotes..."
dnf update -y

echo "==> Instalando dependências..."
dnf install -y dnf-plugins-core curl git

echo "==> Adicionando repositório Docker..."
dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

echo "==> Instalando Docker Engine e Compose Plugin..."
dnf install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

echo "==> Habilitando Docker..."
systemctl enable docker
systemctl start docker

if [ -n "${SUDO_USER:-}" ]; then
  usermod -aG docker "${SUDO_USER}"
  echo "==> Usuário ${SUDO_USER} adicionado ao grupo docker."
fi

echo "==> Docker instalado:"
docker --version
docker compose version
