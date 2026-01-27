INSERT INTO public.album_music (album_id, music_id)
SELECT
    v.album_id,
    a.id AS music_id
FROM (
         VALUES
             ('4e680196-04db-44e5-8dfa-797163ed30ef'::uuid, 'Black Blooms'),
             ('4e680196-04db-44e5-8dfa-797163ed30ef'::uuid, 'The Rough Dog'),

             ('cee9d0e7-1055-45af-af04-9f4cf015900d'::uuid, 'Post Traumatic'),
             ('cee9d0e7-1055-45af-af04-9f4cf015900d'::uuid, 'Post Traumatic EP'),
             ('cee9d0e7-1055-45af-af04-9f4cf015900d'::uuid, 'Where’d You Go'),

             ('329be917-4cf6-407d-8879-8d89fc1db9c0'::uuid, 'Bem Sertanejo - O Show (Ao Vivo)'),
             ('329be917-4cf6-407d-8879-8d89fc1db9c0'::uuid, 'Bem Sertanejo - (1ª Temporada) - EP'),


             ('974f311f-79a6-44dd-bb32-ca6fc0ac6a71'::uuid, 'Use Your Illusion II'),
             ('974f311f-79a6-44dd-bb32-ca6fc0ac6a71'::uuid, 'Greatest Hits')
     ) AS v(album_id, music_name)
         JOIN public.music a  ON a.name = v.music_name
    ON CONFLICT DO NOTHING;