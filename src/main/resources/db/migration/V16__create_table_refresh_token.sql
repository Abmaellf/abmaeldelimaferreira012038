-- Criação da tabela refresh_tokens
CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    token VARCHAR(255) NOT NULL UNIQUE,

    user_id UUID NOT NULL,

    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_refresh_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE
);

-- Índice para melhorar performance em buscas por token
CREATE INDEX idx_refresh_tokens_token
    ON refresh_tokens (token);

-- Índice para limpeza de tokens expirados
CREATE INDEX idx_refresh_tokens_expires_at
    ON refresh_tokens (expires_at);
