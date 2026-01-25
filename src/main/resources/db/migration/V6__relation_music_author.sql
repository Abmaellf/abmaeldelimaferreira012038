
-- Adicionei o campo
ALTER TABLE music ADD column author_id UUID;

-- atualizei o campo author_id, com o id do author que tem o mesmo nome na da tabela author
UPDATE music m
SET author_id = a.id
    FROM author a
WHERE m.author = a.name;

--definir o campo com o relacionamento
ALTER TABLE music ALTER column author_id SET NOT NULL;


-- Definit o relacionamento entre music e author
ALTER TABLE music
    ADD CONSTRAINT fk_music_author
        FOREIGN KEY (author_id)
            REFERENCES author(id);

-- Verificar se na tabela music tem index e remover
DROP INDEX IF EXISTS idx_music_name_author;

-- Remover a coluna antiga
ALTER TABLE music
DROP COLUMN author;

-- Criar um novo index com o fk
CREATE INDEX idx_music_name_author_id ON music(name, author_id);