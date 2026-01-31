package select.music.mapper.music;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import select.music.domain.album.AlbumEntity;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    AlbumEntity toEntity(AlbumRequestDTO albumRequestDTO);


    AlbumResponseDTO toResponse(AlbumEntity entity);

    AlbumResponseDTO toPageResponse(AlbumEntity entity);


}
