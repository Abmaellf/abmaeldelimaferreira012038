package select.music.repository.music;

import org.springframework.data.jpa.repository.JpaRepository;
import select.music.domain.author.AuthorEntity;
import select.music.domain.music.MusicEntity;

import java.util.Optional;
import java.util.UUID;

public interface MusicRepository extends JpaRepository<MusicEntity, UUID> {

    Optional<MusicEntity> findByNameAndAuthorId(String name, UUID authorId);

    boolean existsByNameAndAuthorId(String name, UUID authorId);
}

