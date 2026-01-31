package select.music.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import select.music.domain.album.AlbumEntity;

import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, UUID> {
    Page<AlbumEntity> findAll(Pageable pageable);
}
