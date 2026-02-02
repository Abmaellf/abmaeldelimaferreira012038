package select.music.service.user;

import select.music.dto.user.UserRequestDTO;
import select.music.dto.user.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO create(UserRequestDTO request);

    UserResponseDTO getById(UUID id);

    List<UserResponseDTO> findAll();
}