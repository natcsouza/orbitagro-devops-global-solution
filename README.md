# OrbitAgro API

API REST — **FIAP Global Solution 2026/1** | Disciplina: **DevOps Tools & Cloud Computing**

O OrbitAgro conecta **satélites ao agronegócio**: calcula o **NDVI** (saúde da vegetação), integra sensores **IoT** (umidade e temperatura do solo) e gera **alertas inteligentes** para produtores rurais e cooperativas. ODS 2, 9 e 13.

**Representante da equipe:** Natalia Cristina de Souza — RM **564099** (presente no nome dos containers).

---

## Arquitetura da Solução

![](Modelagem%20de%20Arquitetura%20devops.png)

Diagrama macro da infraestrutura: VM Azure Linux → Docker Compose → API Java + PostgreSQL 16, mesma rede, volume persistente.

---

## Requisitos DevOps Atendidos

| Requisito | Como atendemos |
|-----------|----------------|
| 2 containers Docker integrados | `orbitagro-api-564099` + `orbitagro-db-564099` |
| Imagem personalizada da API | `Dockerfile` multi-stage (Maven + JRE 21) |
| Usuário não-root na API | `USER orbitagro` no Dockerfile |
| Diretório de trabalho | `WORKDIR /app` |
| Variáveis de ambiente | `.env` + `docker-compose.yml` (API e DB) |
| Portas expostas | 8080 (API) e 5432 (PostgreSQL) |
| RM no nome dos containers | `564099` |
| Volume nomeado | `pgdata` |
| Mesma rede Docker | `orbitagro-network` |
| Modo background | `docker compose up -d` |
| CRUD + 2+ tabelas | Produtor (CRUD) + Área de Cultivo (relacionamento) |
| SELECT após cada operação | Documentado no passo 10 |
| Execução em nuvem | VM Azure (não localhost) |

---

## Atenção — Evitar penalidades

| Penalidade | Perda | O que fazer |
|------------|-------|-------------|
| Solução em **localhost** | **Nota zero** | Rodar tudo na **VM Azure**. `curl` com IP público no vídeo. |
| Sem SELECT por operação | −2,0 cada | `SELECT` após **CREATE, READ, UPDATE e DELETE** |
| App com usuário **root** | −1,0 | `whoami` no container da API deve retornar `orbitagro` |
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

> **Modo background (`-d`)** significa que os containers rodam em segundo plano no terminal — comando `docker compose up -d --build`. Isso **não substitui** a VM Azure: os containers devem rodar **dentro da VM na nuvem**, não no seu PC.

> **Precisa criar VM antes?** **Sim.** Sem VM Azure com IP público a entrega recebe **nota zero**. Crie a VM no passo 1 (pode ser antes de gravar o vídeo).

---

## How To — Tutorial Completo

Ordem exata do vídeo demonstrativo: da VM Azure até as evidências no terminal.

### 1. Criar a VM Azure — AlmaLinux (terminal, estilo aula)

**No seu PC (Windows):** instale o Azure CLI, faça login, gere chave SSH e execute o script.

```powershell
winget install -e --id Microsoft.AzureCLI
```

Feche e abra o terminal. Depois:

```powershell
az login
```

```powershell
ssh-keygen -t rsa -b 4096
```

(Aperte Enter nas perguntas para aceitar o padrão.)

No **Git Bash** ou **WSL**, dentro da pasta do projeto:

```bash
cd scripts/azure
chmod +x criar-vm-azure.sh
./criar-vm-azure.sh
```

Anote o **IP público** exibido ao final. SO: **AlmaLinux 9**. Portas: 22, 8080, 5432.

---

### 2. Conectar na VM

```bash
ssh azureuser@<IP_PUBLICO_DA_VM>
```

Confirmar que está na nuvem:

```bash
hostname -I
```

---

### 3. Clonar o Repositório

```bash
git clone https://github.com/natcsouza/orbitagro-devops-global-solution.git
cd orbitagro-devops-global-solution
```

---

### 4. Instalar Docker na VM AlmaLinux (primeira vez)

```bash
chmod +x scripts/deploy/install-docker-almalinux.sh
sudo ./scripts/deploy/install-docker-almalinux.sh
```

Reconectar para aplicar o grupo `docker`:

```bash
exit
ssh azureuser@<IP_PUBLICO_DA_VM>
cd orbitagro-devops-global-solution
```

---

### 5. Subir os Containers em Background

```bash
docker compose up -d --build
```

O `-d` executa em **segundo plano** (modo background).

---

### 6. Validar os Containers

```bash
docker compose ps
```

Esperado: `orbitagro-api-564099` e `orbitagro-db-564099` com status **Up**, portas **8080** e **5432**.

```bash
docker ps
```

---

### 7. Validar API na Nuvem (não localhost)

```bash
curl -s http://<IP_PUBLICO_DA_VM>:8080/produtores
```

Resposta esperada: `[]` ou JSON com produtores.

---

### 8. Exibir Logs dos Dois Containers

```bash
docker logs orbitagro-api-564099
```

```bash
docker logs orbitagro-db-564099
```

---

### 9. Inspecionar Container da API (usuário não-root)

```bash
docker container exec -it orbitagro-api-564099 sh
```

```bash
whoami
pwd
ls -l
exit
```

Resultado esperado: `orbitagro` | `/app` | `app.jar`

---

### 10. Inspecionar Container do Banco

```bash
docker container exec -u postgres -it orbitagro-db-564099 bash
```

```bash
whoami
ls -l /var/lib/postgresql/data
exit
```

Resultado esperado: `postgres` | arquivos do volume `pgdata`

---

### 11. CRUD via curl + SELECT no Banco

Definir a API (dentro da VM):

```bash
API="http://localhost:8080"
```

> Cada operação abaixo **deve** ser seguida do `SELECT` correspondente no container do banco.

#### CREATE — Produtor

```bash
curl -s -X POST "$API/produtores" \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","email":"joao@fazenda.com","telefone":"66999999999"}'
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"
```

#### CREATE — Área de Cultivo (2ª tabela)

```bash
curl -s -X POST "$API/areas" \
  -H "Content-Type: application/json" \
  -d '{"nomeArea":"Talhão Norte","cultura":"Soja","latitude":-12.97,"longitude":-56.10,"produtorId":1}'
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_area_cultivo;"
```

#### READ — Listar e buscar

```bash
curl -s "$API/produtores"
curl -s "$API/produtores/1"
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT id, nome, email FROM tb_produtor WHERE id = 1;"
```

#### UPDATE — Atualizar produtor

```bash
curl -s -X PUT "$API/produtores/1" \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva Atualizado","email":"joao.novo@fazenda.com","telefone":"66988887777"}'
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT id, nome, email FROM tb_produtor WHERE id = 1;"
```

#### DELETE — Remover produtor de teste

```bash
curl -s -X POST "$API/produtores" \
  -H "Content-Type: application/json" \
  -d '{"nome":"Maria Teste","email":"maria@teste.com","telefone":"66977776666"}'
```

```bash
curl -s -X DELETE "$API/produtores/2"
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"
```

#### JOIN — Relacionamento entre tabelas

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT p.nome, a.nome_area, a.cultura FROM tb_produtor p JOIN tb_area_cultivo a ON a.produtor_id = p.id;"
```

---

### 12. Infraestrutura Docker

```bash
docker images
docker network ls
docker volume ls
```

---

### 13. Teste de Persistência

Sair e reconectar na VM:

```bash
exit
ssh azureuser@<IP_PUBLICO_DA_VM>
```

```bash
docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_produtor;"

docker container exec -it orbitagro-db-564099 psql -U orbitagro -d orbitagro \
  -c "SELECT * FROM tb_area_cultivo;"
```

Dados devem permanecer no volume `pgdata`.

---

## Estrutura de Dados

```text
TB_PRODUTOR  1:N  TB_AREA_CULTIVO  1:N  TB_MONITORAMENTO
                              └── 1:N  TB_ALERTA
```

---

## Stack

| Tecnologia | Versão |
|------------|--------|
| Java | 21 |
| Spring Boot | 4.0.6 |
| PostgreSQL | 16 |
| Docker + Compose | Latest |
| Azure VM | AlmaLinux 9 |

---

## Vídeo Demonstrativo

Link: [INSERIR LINK DO YOUTUBE AQUI]

---

## Equipe

| Integrante | RM |
|------------|-----|
| Natalia Cristina de Souza (representante) | 564099 |
| Nickolas Davi | 564105 |
| Samara Vilela | 566133 |
| Otávio Ferreira | 565960 |
| Rodrigo Carvalho Silva | 565162 |
