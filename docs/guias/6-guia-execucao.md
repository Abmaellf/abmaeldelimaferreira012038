# Guia de Execução da API – Fluxo Completo

Este documento descreve **o passo a passo para executar e testar a aplicação**, seguindo o fluxo correto de dependências entre os recursos:

1. Criar usuário
2. Autenticar (login)
3. Criar artista
4. Criar música
5. Criar álbum

Os exemplos abaixo assumem que a aplicação já está **rodando localmente** e que você está utilizando **Bruno ou Postman** para executar as requisições HTTP.

---

## 1️⃣ Criar Usuário

O primeiro passo é criar um usuário que será utilizado para autenticação.

### Endpoint
```
POST /users
```

### Exemplo de Request Body
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

### Resultado Esperado
- Usuário criado com sucesso
- Retorno HTTP **201 Created**

> 🔹 Guarde o **email** e a **senha**, pois eles serão usados no login.

---

## 2️⃣ Autenticar (Login)

Após criar o usuário, é necessário autenticar para obter o **JWT Token**, que será usado nos próximos endpoints protegidos.

### Endpoint
```
POST /auth/login
```

### Exemplo de Request Body
```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

### Resultado Esperado
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### Próximo Passo
- Copie o valor do **token**
- Utilize-o no header das próximas requisições:

```
Authorization: Bearer SEU_TOKEN_AQUI
```

---

## 3️⃣ Criar Artista

Com o usuário autenticado, agora é possível criar um artista.

### Endpoint
```
POST /artists
```

### Headers
```
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json
```

### Exemplo de Request Body
```json
{
  "name": "Linkin Park"
}
```

### Resultado Esperado
```json
{
  "id": "uuid-do-artista",
  "name": "Linkin Park",
  "createdAt": "2026-02-05T10:00:00"
}
```

> 🔹 Guarde o **id do artista**, ele será usado na criação da música e do álbum.

---

## 4️⃣ Criar Música

Agora, crie uma música associada a um artista existente.

### Endpoint
```
POST /musics
```

### Headers
```
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json
```

### Exemplo de Request Body
```json
{
  "name": "Numb",
  "duration": 185,
  "artistId": "uuid-do-artista"
}
```

### Resultado Esperado
```json
{
  "id": "uuid-da-musica",
  "name": "Numb",
  "duration": 185,
  "artist": {
    "id": "uuid-do-artista",
    "name": "Linkin Park"
  }
}
```

> 🔹 Guarde o **id da música**, ele será utilizado no álbum.

---

## 5️⃣ Criar Álbum

Por fim, crie um álbum associando artistas e músicas já cadastradas.

### Endpoint
```
POST /albums
```

### Headers
```
Authorization: Bearer SEU_TOKEN_AQUI
Content-Type: application/json
```

### Exemplo de Request Body
```json
{
  "name": "Meteora",
  "artistIds": [
    "uuid-do-artista"
  ],
  "musicIds": [
    "uuid-da-musica"
  ]
}
```

### Resultado Esperado
```json
{
  "id": "uuid-do-album",
  "name": "Meteora",
  "artists": [
    {
      "id": "uuid-do-artista",
      "name": "Linkin Park"
    }
  ],
  "musics": [
    {
      "id": "uuid-da-musica",
      "name": "Numb"
    }
  ],
  "createdAt": "2026-02-05T10:30:00"
}
```

---

## ✅ Fluxo Resumido

1. **POST /users** → cria usuário
2. **POST /auth/login** → gera token JWT
3. **POST /artists** → cria artista
4. **POST /musics** → cria música
5. **POST /albums** → cria álbum

---

## 🧪 Dica para Bruno / Postman

- Crie uma variável de ambiente chamada `TOKEN`
- No login, salve automaticamente o token
- Use `Authorization: Bearer {{TOKEN}}` nos próximos endpoints

Se quiser, posso:
- Ajustar o documento para **README.md**
- Adaptar para **Swagger / OpenAPI**
- Criar um **collection pronta do Bruno ou Postman**
