# 🛰️ OrbitAgro API

API REST desenvolvida para a Global Solution 2026/1.

O OrbitAgro é uma solução de monitoramento agrícola inteligente que integra dados de satélites e sensores IoT para auxiliar produtores rurais na tomada de decisões através do acompanhamento da saúde da lavoura, monitoramento climático e geração de alertas preventivos.

---

# 🏗️ Arquitetura da Solução

A arquitetura abaixo representa a infraestrutura utilizada para execução da aplicação, contemplando Azure, Docker, PostgreSQL, persistência de dados e comunicação entre os componentes.

![](https://github.com/natcsouza/orbitagro-devops-global-solution/blob/main/Modelagem%20de%20Arquitetura%20devops.png?raw=true)

---

# ✅ Requisitos DevOps Atendidos

- Dockerfile Multi-Stage
- Docker Compose
- PostgreSQL 16 Containerizado
- Docker Volume para Persistência
- Rede Docker Bridge
- Usuário Não-Root
- Variáveis de Ambiente
- Swagger/OpenAPI
- Logs de Containers
- Persistência Validada via SQL
- Arquitetura Cloud Documentada

---

# 🚀 How To — Execução da Aplicação

## 1️⃣ Clonar o Projeto

```bash
git clone https://github.com/natcsouza/orbitagro-devops-global-solution.git

cd orbitagro-devops-global-solution
```

---

## 2️⃣ Inicializar os Containers

```bash
docker compose up -d --build
```

---

## 3️⃣ Validar os Containers

```bash
docker compose ps
```

Saída esperada:

```bash
orbitagro-api-564105
orbitagro-db-564105
```

---

## 4️⃣ Validar Logs

```bash
docker logs orbitagro-api-564105

docker logs orbitagro-db-564105
```

---

## 5️⃣ Validar Segurança (Usuário Não-Root)

Acessar o container da API:

```bash
docker container exec -it orbitagro-api-564105 sh
```

Executar:

```bash
pwd

ls -l

whoami
```

Resultado esperado:

```bash
/app

orbitagro
```

---

## 6️⃣ Acessar Swagger

A API disponibiliza documentação através do Swagger OpenAPI.

```text
http://localhost:8080/swagger-ui.html
```

---

## 7️⃣ Validar Persistência no Banco

Acessar o PostgreSQL:

```bash
docker container exec -it orbitagro-db-564105 psql -U orbitagro -d orbitagro
```

Executar:

```sql
SELECT * FROM tb_produtor;
```

As operações de CREATE, UPDATE e DELETE realizadas pela API podem ser validadas diretamente através de consultas SQL no PostgreSQL.

---

# 🗄️ Estrutura de Dados

## TB_PRODUTOR

Cadastro de produtores rurais.

## TB_AREA_CULTIVO

Cadastro das áreas monitoradas.

Relacionamento:

```text
PRODUTOR 1:N AREA_CULTIVO
```

## TB_MONITORAMENTO

Histórico de leituras provenientes dos sensores IoT e índices NDVI.

Relacionamento:

```text
AREA_CULTIVO 1:N MONITORAMENTO
```

## TB_ALERTA

Alertas automáticos gerados pelo sistema.

Relacionamento:

```text
AREA_CULTIVO 1:N ALERTA
```

---

# ⚙️ Stack Tecnológica

| Tecnologia | Versão |
|------------|---------|
| Java | 21 | Linguagem de programação
| Spring Boot | 4.0.6 | Framework da API REST |
| PostgreSQL | 16 | Banco de dados do container |
| Docker | Latest | Containerização da infraestrutura |
| Docker Compose | Latest | Orquestração dos serviços |
| Springdoc OpenAPI | 2.8.9 | Documentação (Swagger UI) |

---

# 🎥 Demonstração da Solução

Vídeo demonstrando:

- Build da aplicação
- Execução via Docker Compose
- Containers em execução
- Validação de usuário não-root
- Swagger/OpenAPI
- Operações CRUD
- Persistência dos dados no PostgreSQL
- Consulta SQL de validação
- Arquitetura da solução

🔗 Link do vídeo:

[INSERIR LINK DO VÍDEO AQUI]

---

# 👥 Equipe

| Integrante | RM |
|------------|------------|
| Natalia Cristina de Souza | 564099 |
| Nickolas Davi | 564105 |
| Samara Vilela | 566133 |
| Otávio Ferreira | 565960 |
| Rodrigo Carvalho Silva | 565162 |

---

# 📚 Disciplina

DevOps Tools & Cloud Computing

Global Solution 2026/1

FIAP
