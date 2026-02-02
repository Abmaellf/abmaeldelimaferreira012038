package select.music.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.userdetails.UserDetails;
import select.music.domain.user.UserEntity;
import select.music.dto.user.UserDetailDTO;
import select.music.dto.user.UserRequestDTO;
import select.music.dto.user.UserResponseDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDTO request);

    UserResponseDTO toResponse(UserEntity entity);

}

