INSERT INTO album (id,name)
values
    ('4e680196-04db-44e5-8dfa-797163ed30ef','Harakiri'),
    ('cee9d0e7-1055-45af-af04-9f4cf015900d','The Rising Tied'),
    ('329be917-4cf6-407d-8879-8d89fc1db9c0','Bem Sertanejo'),
    ('974f311f-79a6-44dd-bb32-ca6fc0ac6a71','Use Your Illusion I')
;

INSERT INTO public.album_artist (album_id, artist_id)
SELECT
    v.album_id,
    a.id AS artist_id
FROM (
         VALUES
             ('4e680196-04db-44e5-8dfa-797163ed30ef'::uuid, 'Serj Tankian'),
             ('cee9d0e7-1055-45af-af04-9f4cf015900d'::uuid, 'Mike Shinoda'),
             ('329be917-4cf6-407d-8879-8d89fc1db9c0'::uuid, 'Michel Teló'),
             ('974f311f-79a6-44dd-bb32-ca6fc0ac6a71'::uuid, 'Guns N’ Roses')
     ) AS v(album_id, artist_name)
         JOIN public.artist a
              ON a.name = v.artist_name
    ON CONFLICT DO NOTHING;