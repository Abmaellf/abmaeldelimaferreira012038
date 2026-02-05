package select.music.controller.author;


import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.dto.author.AuthorRequest;
import select.music.dto.author.AuthorResponse;
import select.music.service.author.AuthorService;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService service;

    @PostMapping
    public ResponseEntity<EntityModel<AuthorResponse>> create(
            @RequestBody AuthorRequest request
    ) {
        var created = service.create(request);

        var model = EntityModel.of(
                created,
                linkTo(methodOn(AuthorController.class).getById(created.id())).withSelfRel()
        );

        return ResponseEntity.created(
                linkTo(methodOn(AuthorController.class).getById(created.id())).toUri()
        ).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AuthorResponse>> update(
            @PathVariable UUID id,
            @RequestBody AuthorRequest request
    ) {
        var updated = service.update(id, request);

        var model = EntityModel.of(
                updated,
                linkTo(methodOn(AuthorController.class).getById(id)).withSelfRel()
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AuthorResponse>> getById(
            @PathVariable UUID id
    ) {
        var author = service.getById(id);

        var model = EntityModel.of(
                author,
                linkTo(methodOn(AuthorController.class).getById(id)).withSelfRel()
        );
        return ResponseEntity.ok(model);
    }
}

