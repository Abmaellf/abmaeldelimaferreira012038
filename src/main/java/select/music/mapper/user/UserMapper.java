package select.music.mapper.user;

import org.mapstruct.Mapper;
import select.music.domain.user.UserEntity;
import select.music.dto.user.UserRequestDTO;
import select.music.dto.user.UserResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(UserRequestDTO request);

    UserResponseDTO toResponse(UserEntity entity);

    List<UserResponseDTO> toListResponse(List<UserEntity> entitys);

}