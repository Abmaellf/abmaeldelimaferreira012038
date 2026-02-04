package select.music.repository.album;

import org.springframework.data.jpa.repository.JpaRepository;
import select.music.domain.album.AlbumImageEntity;

import java.util.List;
import java.util.UUID;

public interface AlbumImageRepository extends JpaRepository<AlbumImageEntity, UUID> {

    List<AlbumImageEntity> findByAlbumId(UUID albumId);
}

