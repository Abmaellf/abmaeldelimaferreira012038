package select.music.service.album.albumimage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import select.music.domain.album.AlbumEntity;
import select.music.domain.album.AlbumImageEntity;
import select.music.repository.album.AlbumImageRepository;
import select.music.repository.album.AlbumRepository;
import select.music.service.storage.StorageService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumImageService {

    private final AlbumRepository albumRepository;
    private final AlbumImageRepository albumImageRepository;
    private final StorageService storageService;

    public List<AlbumImageEntity> uploadImages(
            UUID albumId,
            List<MultipartFile> files
    ) {
        AlbumEntity album = albumRepository.findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException("Álbum não encontrado"));

        List<AlbumImageEntity> images = files.stream()
                .map(file -> {
                    String url = storageService.upload(file, "albums/" + albumId);

                    return AlbumImageEntity.builder()
                            .album(album)
                            .imageUrl(url)
                            .build();
                })
                .toList();

        return albumImageRepository.saveAll(images);
    }
}
