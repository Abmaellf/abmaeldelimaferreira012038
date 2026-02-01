package select.music.repository.album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import select.music.domain.album.AlbumEntity;
import select.music.domain.artist.ArtistType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<AlbumEntity, UUID> {
    @Query("""
        SELECT DISTINCT a
        FROM AlbumEntity a
        JOIN a.artists ar
        WHERE
            (:artistTypes IS NULL OR ar.type IN :artistTypes)
        AND
          a.createdAt >= COALESCE(:createdAfter, a.createdAt)
    """)
    Page<AlbumEntity> findAllWithFilters(
            @Param("artistTypes") Set<ArtistType> artistTypes,
            @Param("createdAfter") LocalDateTime createdAfter,
            Pageable pageable
    );
    @Query("""
        SELECT DISTINCT a
        FROM AlbumEntity a
        JOIN a.artists ar
        WHERE ar.id = :artistId
    """)
    Page<AlbumEntity> findAllByArtistId(
            @Param("artistId") UUID artistId,
            Pageable pageable
    );
}
