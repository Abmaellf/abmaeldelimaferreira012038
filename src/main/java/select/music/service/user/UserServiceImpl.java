package select.music.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import select.music.dto.user.UserDetailDTO;
import select.music.dto.user.UserRequestDTO;
import select.music.dto.user.UserResponseDTO;
import select.music.exception.LoginAlreadyExistsException;
import select.music.exception.UserNotFoundException;
import select.music.mapper.user.UserMapper;
import select.music.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {
        repository.findByLogin(request.login())
                .ifPresent(user -> {
                    throw new LoginAlreadyExistsException(request.login());
                });

        var entity = mapper.toEntity(request);
        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
            entity.setPassword(encryptedPassword);
        var saved = repository.save(entity);

        return mapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}

