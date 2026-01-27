CREATE TABLE IF NOT EXISTS album_music (

    album_id UUID NOT NULL,
    music_id UUID NOT NULL,

    CONSTRAINT fk_album_music
    PRIMARY KEY (album_id, music_id),

    CONSTRAINT fk_album_music_album
    FOREIGN KEY (album_id)
    REFERENCES album(id),

    CONSTRAINT fk_album_music_music
    FOREIGN KEY (music_id)
    REFERENCES music(id)
);
