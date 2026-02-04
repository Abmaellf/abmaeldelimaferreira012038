package select.music.controller.album.albumimage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import select.music.domain.album.AlbumImageEntity;
import select.music.service.album.albumimage.AlbumImageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumImageController {

    private final AlbumImageService albumImageService;

    @PostMapping(
            value = "/{albumId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<List<String>> uploadImages(
            @PathVariable UUID albumId,
            @RequestParam("files") List<MultipartFile> files
    ) {

        var images = albumImageService.uploadImages(albumId, files);

        var urls = images.stream()
                .map(AlbumImageEntity::getImageUrl)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(urls);
    }
}

