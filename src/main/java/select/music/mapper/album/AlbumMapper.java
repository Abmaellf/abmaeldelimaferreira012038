package select.music.mapper.album;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import select.music.domain.album.AlbumEntity;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.mapper.artist.ArtistMapper;
import select.music.mapper.music.MusicMapper;

@Mapper(componentModel = "spring", uses = { ArtistMapper.class, MusicMapper.class })
public interface AlbumMapper {

//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    AlbumEntity toEntity(AlbumRequestDTO albumRequestDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "artists", ignore = true)
    @Mapping(target = "musics", ignore = true)
    AlbumEntity toEntity(AlbumRequestDTO request);


    AlbumResponseDTO toResponse(AlbumEntity entity);

    AlbumResponseDTO toPageResponse(AlbumEntity entity);
}
