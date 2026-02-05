package select.music.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import select.music.domain.author.AuthorEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {

    Optional<AuthorEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}

