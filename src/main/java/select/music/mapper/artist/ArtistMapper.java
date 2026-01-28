package select.music.mapper.artist;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import select.music.domain.artist.ArtistEntity;
import select.music.dto.artist.ArtistRequest;
import select.music.dto.artist.ArtistResponse;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    ArtistEntity toEntity(ArtistRequest request);
    ArtistResponse toResponse(ArtistEntity entity);

    void updateEntity(@MappingTarget ArtistEntity entity, ArtistRequest request);
}
