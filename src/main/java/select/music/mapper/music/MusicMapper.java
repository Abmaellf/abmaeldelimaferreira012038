package select.music.mapper.music;
import org.mapstruct.*;
import select.music.domain.music.MusicEntity;
import select.music.dto.music.MusicRequest;
import select.music.dto.music.MusicResponse;

@Mapper(componentModel = "spring")
public interface MusicMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "album", ignore = true)
    MusicEntity toEntity(MusicRequest request);

    @Mapping(source = "author.id", target = "authorId")
    MusicResponse toResponse(MusicEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "author", ignore = true)
    void updateEntityFromRequest(MusicRequest request, @MappingTarget MusicEntity entity);
}

