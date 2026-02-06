
### PROCESSO SELETIVO CONJUNTO Nº 001/2026/SEPLAG e demais Órgãos - Engenheiro da Computação- Sênior
### PROJETO PRÁTICO - IMPLEMENTAÇÃO BACK END JAVA SÊNIOR
#### Número da inscrição: 16330
#### Perfil: ENGENHEIRO DA COMPUTAÇÃO - SÊNIOR
#### Candidato: 
    Abmael de Lima Ferreira
 #### Documentação
    RG: 3142129-6
#####
    cpf: 012.038.553-89
<!-- https://github.com/Ileriayo/markdown-badges -->
# Music

[![Project Status: Active](https://img.shields.io/badge/Project%20Status-Active-brightgreen.svg)]()
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)]()
[![Java](https://img.shields.io/badge/Java-21-orange.svg)]()
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-green.svg)]()
[![Docker](https://img.shields.io/badge/Docker-Compose-blue.svg)]()

O **Music** é uma API REST que disponibiliza dados sobre **Artistas**  e  **Álbuns**.

---

## 📋 Sobre o Projeto

O **Music** é uma plataforma segura, pesquisas bem elaboradas; **Imagens**: permite efetuar upload de imagens;
Bem documentado
---

## 📚 Documentação Técnica

Para detalhes profundos sobre como o sistema foi construído, consulte nossos guias especializados:

- [🗄️ Modelagem de Banco de Dados](docs/guias/01-banco-dados.md)
- [🗄️ Analise de requisitos](docs/guias/05-analise-requisitos.md)
- [🗄️ Guia de execução da api](docs/guias/06-guia-execucao.md)


---

## 🚀 Como Executar

### Passo a Passo Rápido
# 🚀 Executando o Projeto Localmente

Este guia descreve o passo a passo para executar o projeto em ambiente local utilizando **Docker** e **Docker Compose**.

---
## 📌 Pré-requisitos

Certifique-se de ter instalado em sua máquina:

- Git
- Docker
- Docker Compose

Verifique as instalações com:

```bash
git --version
docker --version
docker compose version
```

---

## 📥 1. Clonar o repositório

```bash
git clone https://github.com/Abmaellf/abmaeldelimaferreira012038.git
cd abmaeldelimaferreira012038
```



---

## ⚙️ 2. Configurar as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto:

```bash
cp .env.example .env
```

Ou crie manualmente:

```bash
touch .env
```

### Exemplo de `.env`

```env
# Banco de dados
DB_USER_MUSIC=
DB_PASSWORD_MUSIC=
DB_NAME_MUSIC=
DB_HOST_MUSIC=
DB_PORT_MUSIC=
DB_DDL_AUTO_MUSIC=
DB_DRIVER_MUSIC=

SPRING_DATASOURCE_URL_MUSIC=
SPRING_DATASOURCE_USERNAME_MUSIC=
SHOW_SQL_MUSIC=

APP_PORT_MUSIC=

JWT_SECRET_MUSIC=
JWT_EXPIRATION_MUSIC=300000
JWT_REFRESH_EXPIRATION_MUSIC=500000
COOKIES_SECURE_MUSIC=
SAME_SITE_MUSIC=None

SEED_PASSWORD_ADMIN_MUSIC=

CORS_ALLOWED_ORIGINS=localhost:3000

MINIO_HOST_URL=http://localhost:9000
MINIO_ROOT_USER=minioadmin
MINIO_ROOT_PASSWORD=minioadmin
MINIO_BUCKET=album-images
```

🔐 **Importante:**  
O arquivo `.env` não deve ser versionado. Certifique-se de que ele esteja listado no `.gitignore`.

---

## 🐳 3. Subir os containers com Docker Compose

Execute o comando abaixo na raiz do projeto:

```bash
docker compose up --build
```

Este comando irá:
- Construir as imagens
- Criar os containers
- Inicializar a aplicação e o banco de dados

---

## ✅ 4. Acessar a aplicação

Após a inicialização, a aplicação estará disponível em:

- **API:**  
  http://localhost:8080

- **Swagger / OpenAPI:**  
  http://localhost:8080/swagger-ui.html

---

## 🛑 5. Parar a aplicação

Para encerrar os containers:

```bash
docker compose down
```

---

## ♻️ (Opcional) Rebuild completo

Caso precise recriar os containers e volumes do zero:

```bash
docker compose down -v
docker compose up --build
```

---

## 📄 Observações finais

- As migrations do banco são executadas automaticamente pelo **Flyway**
- Logs podem ser acompanhados diretamente no terminal
- Para produção, recomenda-se o uso de variáveis de ambiente seguras

---











## 👨‍💻 Autor ABMAEL DE LIMA FERREIRA


Este projeto foi desenvolvido com dedicação por **Abmael de Lima Ferreira**.

- 🌐 **Portfolio/Links:** [ ]()
- 🐙 **GitHub:** [ https://github.com/Abmaellf ](https://github.com/Abmaellf)
- 💼 **LinkedIn:** [in/abmaelferreira](https://www.linkedin.com/in/abmaelferreira/)

###  👨 Metodologia de desenvolvimento
    Tarefas do projeto criadas via ClickUp
[https://app.clickup.com/9013176627/v/b/li/901324721568](https://app.clickup.com/9013176627/v/b/li/901324721568) 

        
---

d