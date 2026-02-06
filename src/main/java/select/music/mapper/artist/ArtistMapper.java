package select.music.mapper.artist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import select.music.domain.artist.ArtistEntity;
import select.music.dto.artist.ArtistRequest;
import select.music.dto.artist.ArtistResponse;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArtistMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "type", source = "type")
    ArtistEntity toEntity(ArtistRequest request);

    ArtistResponse toResponse(ArtistEntity entity);

    List<ArtistResponse> toResponseList(List<ArtistEntity> entity);

    void updateEntity(@MappingTarget ArtistEntity entity, ArtistRequest request);
}
