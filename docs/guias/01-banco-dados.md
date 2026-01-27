# 🎵 Select Music – Banco de Dados

Este repositório contém a **modelagem e documentação do banco de dados** para um sistema de catálogo musical. O objetivo é gerenciar **artistas, álbuns, músicas, autores e imagens**, mantendo uma estrutura normalizada e flexível.

---

## 📌 Visão Geral

O modelo foi projetado para suportar:

- Relacionamentos **muitos-para-muitos (N:N)** entre álbuns e artistas
- Relacionamentos **muitos-para-muitos (N:N)** entre álbuns e músicas
- Múltiplas imagens por álbum
- Associação de músicas a seus respectivos autores

O banco utiliza **UUIDs** como chave primária e segue boas práticas de auditoria com campos de data.

---

## 🗂️ Estrutura do Banco de Dados

### 🎤 Artist
Armazena informações sobre artistas ou bandas.

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | UUID (PK) | Identificador do artista |
| name | VARCHAR | Nome do artista ou banda |
| type | VARCHAR / ENUM | Tipo (Banda, Solo) |
| foundation | DATE/TIMESTAMP | Data de fundação |
| created_at | TIMESTAMP | Data de criação |
| updated_at | TIMESTAMP | Data de atualização |

---

### 💿 Album
Representa um álbum musical.

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | UUID (PK) | Identificador do álbum |
| name | VARCHAR | Nome do álbum |
| created_at | TIMESTAMP | Data de criação |
| updated_at | TIMESTAMP | Data de atualização |

---

### 🎶 Music
Armazena as músicas cadastradas.

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | UUID (PK) | Identificador da música |
| name | VARCHAR | Nome da música |
| author_id | UUID (FK) | Referência ao autor |
| created_at | TIMESTAMP | Data de criação |
| updated_at | TIMESTAMP | Data de atualização |

---

### ✍️ Author
Representa o autor ou compositor de músicas.

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | UUID (PK) | Identificador do autor |
| name | VARCHAR | Nome do autor |
| created_at | TIMESTAMP | Data de criação |
| updated_at | TIMESTAMP | Data de atualização |

---

## 🔗 Tabelas de Relacionamento

### Album_Artist
Relacionamento **N:N** entre álbuns e artistas.

| Campo | Tipo | Descrição |
|------|------|-----------|
| album_id | UUID (FK) | Referência ao álbum |
| artist_id | UUID (FK) | Referência ao artista |

🔹 Recomenda-se chave primária composta (`album_id`, `artist_id`).

---

### Album_Music
Relacionamento **N:N** entre álbuns e músicas.

| Campo | Tipo | Descrição |
|------|------|-----------|
| album_id | UUID (FK) | Referência ao álbum |
| music_id | UUID (FK) | Referência à música |

---

## 🖼️ Imagens do Álbum

### Album_Image
Permite associar múltiplas imagens a um álbum.

| Campo | Tipo | Descrição |
|------|------|-----------|
| id | UUID (PK) | Identificador da imagem |
| album_id | UUID (FK) | Referência ao álbum |
| image_url | VARCHAR | URL da imagem |
| created_at | TIMESTAMP | Data de criação |

---

## 🔄 Relacionamentos Resumidos

- **Album ↔ Artist**: Muitos-para-muitos
- **Album ↔ Music**: Muitos-para-muitos
- **Album ↔ Image**: Um-para-muitos
- **Music ↔ Author**: Muitos-para-um

---

## 🧱 Tecnologias Sugeridas

- **PostgreSQL 15+**
- **Flyway** (migrations)
- **Spring Boot + JPA/Hibernate**
- UUID como padrão de identificação

---

## ✅ Boas Práticas Adotadas

- Normalização de dados
- Uso de tabelas auxiliares para N:N
- Auditoria com `created_at` e `updated_at`
- Estrutura preparada para crescimento do domínio

---

## 🚀 Possíveis Evoluções

- Inclusão de gêneros musicais
- Ordenação de músicas dentro do álbum
- Soft delete (`deleted_at`)
- Controle de usuários e playlists

---