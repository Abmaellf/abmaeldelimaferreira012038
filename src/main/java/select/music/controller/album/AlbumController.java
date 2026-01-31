package select.music.controller.album;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.service.album.AlbumService;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService service;
    private final PagedResourcesAssembler<AlbumResponseDTO> assembler;

    @PostMapping
    public ResponseEntity<EntityModel<AlbumResponseDTO>> create(@RequestBody AlbumRequestDTO request) {
        var created = service.create(request);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                created,
                linkTo(methodOn(AlbumController.class).getById(created.id())).withSelfRel()
        );

        return ResponseEntity
                .created(linkTo(methodOn(AlbumController.class).getById(created.id())).toUri())
                .body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlbumResponseDTO>> getById(@PathVariable UUID id) {
        var album = service.findById(id);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                album,
                linkTo(methodOn(AlbumController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(AlbumController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(AlbumController.class).delete(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AlbumResponseDTO>>> findAll(
            @RequestParam(required = false) UUID artistId,
            Pageable pageable
    ) {
        Page<AlbumResponseDTO> page = (artistId == null)
                ? service.findAll(pageable)
                : service.findAllByArtist(artistId, pageable);

        PagedModel<EntityModel<AlbumResponseDTO>> model = assembler.toModel(
                page,
                album -> EntityModel.of(
                        album,
                        linkTo(methodOn(AlbumController.class).getById(album.id())).withSelfRel()
                )
        );

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AlbumResponseDTO>> update(
            @PathVariable UUID id,
            @RequestBody AlbumRequestDTO request
    ) {
        var updated = service.update(id, request);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                updated,
                linkTo(methodOn(AlbumController.class).getById(id)).withSelfRel()
        );

        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
