package select.music.mapper.author;

import org.mapstruct.*;
import select.music.domain.author.AuthorEntity;
import select.music.dto.author.AuthorRequest;
import select.music.dto.author.AuthorResponse;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorEntity toEntity(AuthorRequest request);

    AuthorResponse toResponse(AuthorEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(AuthorRequest request, @MappingTarget AuthorEntity entity);
}

