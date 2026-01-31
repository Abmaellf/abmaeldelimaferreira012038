package select.music.controller.album;

import lombok.RequiredArgsConstructor;
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

    // CREATE — 201 Created
    @PostMapping
    public ResponseEntity<EntityModel<AlbumResponseDTO>> create(@RequestBody AlbumRequestDTO request) {
        var created = service.create(request);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                created,
                linkTo(methodOn(AlbumController.class).getById(created.id())).withSelfRel(),
                linkTo(methodOn(AlbumController.class).findAll(Pageable.unpaged())).withRel("find-all")
        );

        return ResponseEntity
                .created(linkTo(methodOn(AlbumController.class).getById(created.id())).toUri())
                .body(model);
    }

    // GET BY ID — 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlbumResponseDTO>> getById(@PathVariable UUID id) {
        var album = service.findById(id);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                album,
                linkTo(methodOn(AlbumController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(AlbumController.class).findAll(Pageable.unpaged())).withRel("albums"),
                linkTo(methodOn(AlbumController.class).update(id, null)).withRel("update"),
                linkTo(methodOn(AlbumController.class).delete(id)).withRel("delete")
        );

        return ResponseEntity.ok(model);
    }

    // GET ALL (PAGINADO) — 200 OK
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AlbumResponseDTO>>> findAll(
            Pageable pageable
    ) {
        var page = service.findAll(pageable);

        PagedModel<EntityModel<AlbumResponseDTO>> model = assembler.toModel(
                page,
                album -> EntityModel.of(
                        album,
                        linkTo(methodOn(AlbumController.class).getById(album.id())).withSelfRel()
                )
        );

        return ResponseEntity.ok(model);
    }

    // UPDATE — 200 OK (RFC 7231 §6.3.2)
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

    // DELETE — 204 No Content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
