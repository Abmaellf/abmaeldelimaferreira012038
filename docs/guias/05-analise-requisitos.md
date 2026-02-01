# 📄 Documento de Análise de Requisitos

## 1. Visão Geral

Este documento descreve a **análise de requisitos funcionais e não funcionais** do sistema **Catálogo Musical**, baseado na modelagem de banco de dados fornecida e nas regras de negócio estabelecidas.

O sistema tem como objetivo gerenciar **artistas, álbuns, músicas, autores e imagens de capa**, expondo funcionalidades via **API REST**, com foco em **escalabilidade, segurança e boas práticas de arquitetura**.

---

## 2. Escopo do Sistema

O sistema deverá permitir:

* Cadastro e consulta de álbuns, artistas, músicas e autores
* Relacionamentos N:N entre álbuns e artistas, álbuns e músicas
* Upload e recuperação de imagens de capa de álbuns
* Exposição de APIs versionadas, seguras e documentadas
* Execução em ambiente conteinerizado

Fora do escopo inicial:

* Interface gráfica (frontend)
* Recomendação musical ou streaming

---

## 3. Stakeholders

* **Usuário consumidor da API** (frontend / integradores)
* **Equipe de desenvolvimento backend**
* **Equipe de DevOps / Infraestrutura**

---

## 4. Requisitos Funcionais (RF)

### RF01 – Gerenciamento de Artistas

* O sistema deve permitir **cadastrar, atualizar e consultar artistas**.
* Deve ser possível consultar artistas pelo **nome**, com **ordenação alfabética ascendente ou descendente**.
* O artista pode ser do tipo **solo ou banda**.

---

### RF02 – Gerenciamento de Álbuns

* O sistema deve permitir **cadastrar, atualizar e consultar álbuns**.
* Um álbum deve estar associado a **um ou mais artistas**.
* Um álbum pode conter **uma ou mais músicas**.
* As consultas de álbuns devem suportar **paginação**.

---

### RF03 – Consulta Parametrizada de Álbuns

* O sistema deve permitir consultar:

    * Álbuns por artista
    * Álbuns de **bandas**, **cantores solo** ou ambos
* Os filtros devem ser combináveis via parâmetros de consulta.

---

### RF04 – Gerenciamento de Músicas e Autores

* O sistema deve permitir cadastrar e consultar músicas.
* Cada música deve estar associada a **um autor**.
* Uma música pode pertencer a **vários álbuns**.

---

### RF05 – Upload de Imagens de Capa

* O sistema deve permitir o **upload de uma ou mais imagens de capa** para um álbum.
* As imagens devem ser armazenadas em um serviço compatível com **S3 (MinIO)**.
* Cada álbum pode possuir **múltiplas imagens**.

---

### RF06 – Recuperação de Imagens

* O sistema deve disponibilizar **links pré-assinados** para acesso às imagens.
* Os links devem possuir **expiração de 30 minutos**.

---

### RF07 – Versionamento da API

* Todos os endpoints devem ser versionados.
* Exemplo:

  ```
  /api/v1/albums
  /api/v1/artists
  ```

---

### RF08 – Migração e Carga Inicial do Banco

* O sistema deve utilizar **Flyway** para:

    * Criação das tabelas
    * Versionamento do schema
    * Carga inicial de dados (seed)

---

### RF09 – Documentação da API

* O sistema deve disponibilizar documentação interativa via **OpenAPI/Swagger**.
* A documentação deve conter:

    * Endpoints
    * Parâmetros
    * Códigos de resposta
    * Exemplos

---

### RF10 – Notificação em Tempo Real (Sênior)

* O sistema deve notificar clientes via **WebSocket** sempre que um novo álbum for cadastrado.

---

### RF11 – Health Checks (Sênior)

* O sistema deve expor endpoints de:

    * **Liveness**
    * **Readiness**

---

### RF12 – Rate Limit (Sênior)

* O sistema deve limitar o uso da API em **até 10 requisições por minuto por usuário**.

---

## 5. Requisitos Não Funcionais (RNF)

### RNF01 – Arquitetura

* O sistema deve ser desenvolvido utilizando:

    * Java 21
    * Spring Boot 3.5.x
    * JPA/Hibernate
    * PostgreSQL 15+

---

### RNF02 – Segurança

* O acesso aos endpoints deve ser bloqueado para **domínios externos não autorizados** (CORS).
* O sistema deve implementar **autenticação via JWT**.
* O token JWT deve:

    * Expirar a cada **5 minutos**
    * Permitir **renovação (refresh token)**

---

### RNF03 – Persistência de Imagens

* O armazenamento de imagens deve utilizar **MinIO**.
* O sistema não deve armazenar arquivos binários no banco de dados.

---

### RNF04 – Desempenho

* As consultas devem ser paginadas para evitar grandes volumes de dados.
* Índices devem ser utilizados para campos de busca frequente (ex: nome do artista).

---

### RNF05 – Escalabilidade

* A aplicação deve ser **stateless**, permitindo múltiplas instâncias.
* Tokens JWT não devem depender de sessão em memória.

---

### RNF06 – Observabilidade

* Logs devem ser claros e padronizados.
* Health checks devem permitir monitoramento por orquestradores.

---

### RNF07 – Testabilidade

* O sistema deve possuir **testes unitários** cobrindo regras de negócio críticas.

---

### RNF08 – Conteinerização

* A aplicação deve ser empacotada em **imagem Docker**.
* O ambiente deve ser executável via **docker-compose**, contendo:

    * API
    * Banco de Dados PostgreSQL
    * MinIO

---

## 6. Requisitos de Entrega

* Projeto versionado em **repositório GitHub**
* Commits pequenos e semânticos
* README.md contendo:

    * Descrição do projeto
    * Tecnologias utilizadas
    * Como executar a aplicação
    * Como executar testes
    * Dados de inscrição e vaga

---

## 7. Critérios de Qualidade Esperados

* Código legível e bem organizado
* Separação clara de camadas (Controller, Service, Repository)
* Uso correto de DTOs
* Boas práticas REST
* Escalabilidade e extensibilidade

---

## 8. Considerações Finais

Este documento serve como base para **desenvolvimento, validação e avaliação técnica**, garantindo alinhamento entre regras de negócio, arquitetura e expectativas de entrega.
