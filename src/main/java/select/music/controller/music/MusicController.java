package select.music.controller.music;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.dto.music.MusicRequest;
import select.music.dto.music.MusicResponse;
import select.music.service.music.MusicEntityService;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v1/musics")
@RequiredArgsConstructor
public class MusicController {

    private final MusicEntityService service;

    @PostMapping
    public ResponseEntity<EntityModel<MusicResponse>> create(
            @RequestBody @Valid MusicRequest request
    ) {
        var created = service.create(request);

        EntityModel<MusicResponse> model = EntityModel.of(
                created,
                linkTo(methodOn(MusicController.class).getById(created.id())).withSelfRel(),
                linkTo(methodOn(MusicController.class).create(request)).withRel("create")
        );

        return ResponseEntity.created(
                linkTo(methodOn(MusicController.class).getById(created.id())).toUri()
        ).body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<MusicResponse>> getById(@PathVariable UUID id) {
        var response = service.getById(id);

        EntityModel<MusicResponse> model = EntityModel.of(
                response,
                linkTo(methodOn(MusicController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(MusicController.class).update(id, null)).withRel("update")
        );

        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<MusicResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid MusicRequest request
    ) {
        var updated = service.update(id, request);

        EntityModel<MusicResponse> model = EntityModel.of(
                updated,
                linkTo(methodOn(MusicController.class).getById(id)).withSelfRel()
        );

        return ResponseEntity.ok(model);
    }
}
