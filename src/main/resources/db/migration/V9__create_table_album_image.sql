CREATE TABLE IF NOT EXISTS album_image (
     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     album_id UUID NOT NULL,
     image_url TEXT NOT NULL,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_album_image_album
         FOREIGN KEY (album_id)
             REFERENCES album(id)
             ON DELETE CASCADE
);
