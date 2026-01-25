CREATE TABLE IF NOT EXISTS  album_artist (
     album_id UUID NOT NULL,
     artist_id UUID NOT NULL,

     CONSTRAINT pk_album_image_artist
         PRIMARY KEY (album_id, artist_id),

     CONSTRAINT fk_album_artist_album
     FOREIGN KEY (album_id)
     REFERENCES album(id),

    CONSTRAINT fk_album_artist_artist
    FOREIGN KEY (artist_id)
    REFERENCES artist(id)
);
