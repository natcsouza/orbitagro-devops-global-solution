# OrbitAgro — Monitoramento Inteligente do Agronegócio

> Plataforma REST que conecta **satélites ao agronegócio**: calcula **NDVI** (saúde da vegetação), integra sensores **IoT** (umidade e temperatura do solo) e gera **alertas inteligentes** para produtores rurais e cooperativas. ODS 2, 9 e 13.

**Global Solution 2026/1 · FIAP · DevOps Tools & Cloud Computing**

| Integrante | RM |
|------------|-----|
| **Natalia Cristina de Souza** *(representante DevOps)* | **564099** |
| Nickolas Davi | 564105 |
| Samara Vilela | 566133 |
| Otávio Ferreira | 565960 |
| Rodrigo Carvalho Silva | 565162 |

**Repositório:** https://github.com/natcsouza/orbitagro-devops-global-solution

---

## Descrição da Solução

Pequenos e médios produtores muitas vezes não têm acesso a monitoramento contínuo e acessível da lavoura. O OrbitAgro centraliza dados de campo e satélite em uma API na nuvem:

- Cadastra **produtores** e **áreas de cultivo** com geolocalização
- Registra **monitoramentos** (NDVI, umidade, temperatura do solo)
- Gera **alertas** automáticos por risco agrícola
- Persiste tudo em **PostgreSQL 16** com rastreabilidade
- Executa na **VM Azure** com **Docker Compose** (API Java + banco integrados)

---

## Arquitetura Macro

![](Modelagem%20de%20Arquitetura%20devops.png)

Diagrama de infraestrutura na nuvem: Azure (VM AlmaLinux 10, NSG, IP público) → Docker Compose → API + PostgreSQL, volume `pgdata` e rede `orbitagro-network`.

**Tabelas relacionadas:** `tb_produtor` 1:N `tb_area_cultivo` 1:N `tb_monitoramento` · `tb_alerta`

---

## Pré-requisitos

- Conta [Azure for Students](https://portal.azure.com)
- [Azure CLI](https://learn.microsoft.com/cli/azure/install-azure-cli) instalado (`az login`)
- **Chave SSH** gerada no seu PC (veja seção abaixo — **não confundir com o IP da VM**)
- Git Bash, WSL ou terminal Linux na VM

### Chave SSH — o que é e onde fica

A chave SSH **não tem o IP no nome**. Ela fica no **seu computador**, em:

| Arquivo | Função |
|---------|--------|
| `~/.ssh/id_rsa_orbitagro` | Chave **privada** (secreta — não compartilhar) |
| `~/.ssh/id_rsa_orbitagro.pub` | Chave **pública** (enviada para a Azure na criação da VM) |

No **Windows**, o caminho equivalente é:

`C:\Users\<SEU_USUARIO>\.ssh\id_rsa_orbitagro`

Gerar a chave (PowerShell):

```powershell
ssh-keygen -t rsa -b 4096 -f $env:USERPROFILE\.ssh\id_rsa_orbitagro -C "orbitagro-azure-564099"
```

Git Bash / Linux:

```bash
ssh-keygen -t rsa -b 4096 -f ~/.ssh/id_rsa_orbitagro -C "orbitagro-azure-564099"
```

Conectar na VM (substitua pelo **IP público** exibido ao criar a VM):

```bash
ssh -i ~/.ssh/id_rsa_orbitagro azureuser@20.151.105.176
```

> **Login na VM:** usuário `azureuser` (não root). **`sudo`** na VM para instalar Docker é permitido. A penalidade de root é no **container da API** — `whoami` deve retornar `orbitagro`.

---

## Atenção — Evitar penalidades

| Penalidade | Perda | O que fazer |
|------------|-------|-------------|
| Solução em **localhost** | **Nota zero** | Rodar tudo na **VM Azure**. `curl` com **IP público** no vídeo. |
| Sem SELECT por operação | −2,0 cada | `SELECT` após **CREATE, READ, UPDATE e DELETE** |
| App com usuário **root** | −1,0 | `whoami` no container da API → `orbitagro` |
| Sem How To no GitHub | −1,5 | Este README |
| Sem volume nomeado | −1,0 | `pgdata` no compose |
| Sem variável de ambiente (por container) | −0,5 cada | Env vars no compose |
| Sem porta exposta (por container) | −0,5 cada | 8080 e 5432 |
| Container sem RM | −0,5 cada | `564099` no nome |
| Sem WORKDIR | −0,5 | `/app` no Dockerfile |
| Sem modo background | −0,5 cada | Flag `-d` no `docker compose up` |
| Sem logs dos containers | −1,0 cada | `docker logs` nos dois |
| Sem `docker exec` (pwd/ls/whoami) | −1,0 cada | Nos dois containers |
| CRUD só em 1 tabela | −2,0 | Produtor + Área + JOIN |
| Sem descrição da solução | −1,0 | Seção inicial deste README |
| Diagrama estilo TOGAF/fluxograma | **Nota zero** | Usar o PNG de arquitetura cloud |

> **Modo background (`-d`)** = containers em segundo plano (`docker compose up -d --build`). Isso **não substitui** a VM Azure: os containers rodam **dentro da VM na nuvem**, não no seu PC.

---

## How To — Do clone ao ambiente em nuvem

Ordem sugerida para o **vídeo demonstrativo** (terminal + curl, sem Swagger).

### ETAPA 1 — Clonar o repositório (PC ou VM)

```bash
git clone https://github.com/natcsouza/orbitagro-devops-global-solution.git
cd orbitagro-devops-global-solution
```

### ETAPA 2 — Criar a VM AlmaLinux no Azure

No PC: `az login`, gere a chave SSH (seção acima) e execute:

```bash
cd scripts/azure
chmod +x criar-vm-azure.sh
./criar-vm-azure.sh
```

O script cria:

- Resource Group `rg-orbitagro-564099`
- VM `vm-orbitagro-564099` — **AlmaLinux 10 (2ª geração)**, **Standard_B2ats_v2**, região **canadacentral**
- Portas **22**, **8080** e **5432** abertas no NSG

Anote o **IP público** exibido ao final (exemplo desta entrega: `20.151.105.176`).

Variáveis opcionais (`.env.example`):

```bash
export AZURE_SSH_KEY_PATH=~/.ssh/id_rsa_orbitagro.pub
export AZURE_VM_SIZE=Standard_B2ats_v2
export AZURE_LOCATION=canadacentral
```

### ETAPA 3 — Conectar na VM via SSH

```bash
ssh -i ~/.ssh/id_rsa_orbitagro azureuser@<IP_PUBLICO_DA_VM>
hostname -I
cat /etc/os-release | head -2
```

### ETAPA 4 — Instalar Docker na VM (primeira vez)

```bash
git clone https://github.com/natcsouza/orbitagro-devops-global-solution.git
cd orbitagro-devops-global-solution
chmod +x scripts/deploy/install-docker-almalinux.sh
sudo ./scripts/deploy/install-docker-almalinux.sh
```

Reconectar para aplicar o grupo `docker`:

```bash
exit
ssh -i ~/.ssh/id_rsa_orbitagro azureuser@<IP_PUBLICO_DA_VM>
cd orbitagro-devops-global-solution
```

### ETAPA 5 — Subir os containers em background

```bash
cp .env.example .env
docker compose up -d --build
```

Aguarde na primeira vez (Maven compila a API). O `-d` executa em **segundo plano**.

```bash
docker compose ps
docker ps
```

Esperado: `orbitagro-api-564099` e `orbitagro-db-564099` **Up**, portas **8080** e **5432**.

### ETAPA 6 — Validar API na nuvem (IP público)

```bash
curl -s http://<IP_PUBLICO_DA_VM>:8080/produtores
```

Resposta esperada: `[]` ou JSON com produtores.

---

## Evidências obrigatórias

### Logs dos dois containers

```bash
docker logs orbitagro-api-564099 --tail 30
docker logs orbitagro-db-564099 --tail 30
```

### Acesso ao container da API (usuário não-root)

```bash
docker container exec -it orbitagro-api-564099 sh
whoami    # orbitagro
pwd       # /app
ls -l     # app.jar
exit
```

### Acesso ao container do banco

```bash
docker container exec -u postgres -it orbitagro-db-564099 bash
whoami
ls -l /var/lib/postgresql/data | head
exit
```

### Infraestrutura Docker

```bash
docker images
docker network ls
docker volume ls
```

---

## CRUD completo via curl + SELECT no banco

Definir a API **dentro da VM**:

```bash
API="http://localhost:8080"
```

> Cada operação **deve** ser seguida do `SELECT` correspondente (evita −2,0 pts por operação).

### CREATE — Produtor

```bash
curl -s -X POST "$API/produtores" \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@fazenda.com","telefone":"66999999999"}'

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"
```

### CREATE — Área de Cultivo (2ª tabela)

```bash
curl -s -X POST "$API/areas" \
  -H "Content-Type: application/json" \
  -d '{"nomeArea":"Talhão Norte","cultura":"Soja","latitude":-12.97,"longitude":-56.10,"produtorId":1}'

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_area_cultivo;"
```

### READ — Listar e buscar

```bash
curl -s "$API/produtores"
curl -s "$API/produtores/1"

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT id, nome, email FROM tb_produtor WHERE id = 1;"
```

### UPDATE — Atualizar produtor

```bash
curl -s -X PUT "$API/produtores/1" \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva Atualizado","email":"joao.novo@fazenda.com","telefone":"66988887777"}'

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT id, nome, email FROM tb_produtor WHERE id = 1;"
```

### DELETE — Remover produtor de teste

```bash
curl -s -X POST "$API/produtores" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria Teste","email":"maria@teste.com","telefone":"66977776666"}'

curl -s -X DELETE "$API/produtores/2"

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"
```

### JOIN — Relacionamento entre tabelas

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT p.nome, a.nome_area, a.cultura FROM tb_produtor p JOIN tb_area_cultivo a ON a.produtor_id = p.id;"
```

---

## Teste de persistência (volume pgdata)

Sair e reconectar na VM:

```bash
exit
ssh -i ~/.ssh/id_rsa_orbitagro azureuser@<IP_PUBLICO_DA_VM>
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_area_cultivo;"
```

Os dados devem permanecer após reconexão — volume nomeado `pgdata`.

---

## Checklist de requisitos DevOps

| Requisito | Status |
|-----------|--------|
| Dockerfile multi-stage personalizado | ✅ |
| Usuário não-root na API: `orbitagro` | ✅ |
| WORKDIR `/app` | ✅ |
| Variáveis de ambiente (API + DB) | ✅ |
| Porta 8080 exposta (API) | ✅ |
| Container API com RM **564099** | ✅ |
| CRUD + 2 tabelas + JOIN | ✅ |
| SELECT após cada operação CRUD | ✅ |
| Volume nomeado `pgdata` | ✅ |
| Porta 5432 exposta (PostgreSQL) | ✅ |
| Container DB com RM **564099** | ✅ |
| Rede `orbitagro-network` | ✅ |
| Modo background (`-d`) | ✅ |
| Logs + `docker exec` nos 2 containers | ✅ |
| Execução em nuvem — VM Azure + IP público | ✅ |
| How To no GitHub | ✅ |

---

## Stack

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 3.2.5 |
| PostgreSQL | 16 |
| Docker + Compose | Latest |
| Azure VM | AlmaLinux 10 (2ª geração) · Standard_B2ats_v2 |

---

## Vídeo demonstrativo

Link: https://youtu.be/fuKUpc1hQKw

---

*OrbitAgro · Global Solution 2026/1 · FIAP · VM AlmaLinux 10 (2ª geração) · RM 564099*
