package select.music.controller.artist;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.dto.artist.ArtistRequest;
import select.music.dto.artist.ArtistResponse;
import select.music.service.artist.ArtistService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("api/v1/artist")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<List<ArtistResponse>> findAll() {

        return ResponseEntity.ok(artistService.findAll());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ArtistResponse>> create(@RequestBody ArtistRequest artistRequest) {

        var created = artistService.create(artistRequest);

        EntityModel<ArtistResponse> model = EntityModel.of(
                created,
                linkTo(methodOn(ArtistController.class).getById(created.id())).withSelfRel(),
                linkTo(methodOn(ArtistController.class).getByName(created.name())).withRel("by-name")
        );

        return ResponseEntity
                .created(URI.create("/artist/" + created.id()))
                .body(model);
    }



    @PutMapping
    @RequestMapping("/{id}")
    public ResponseEntity<EntityModel<ArtistResponse>> update(
            @PathVariable UUID id,
            @RequestBody ArtistRequest artistRequest) {
        var updated = artistService.update(id, artistRequest);

        EntityModel<ArtistResponse> model = EntityModel.of(
                updated,
                linkTo(methodOn(ArtistController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(ArtistController.class).getByName(updated.name())).withRel("by-name")
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ArtistResponse>> getById(@PathVariable UUID id) {
        var artist = artistService.findById(id);

        EntityModel<ArtistResponse> model = EntityModel.of(
                artist,
                linkTo(methodOn(ArtistController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(ArtistController.class).getByName(artist.name())).withRel("by-name")
        );
        return ResponseEntity.ok(model);
    }

    @GetMapping("/search")
    public ResponseEntity<EntityModel<ArtistResponse>> getByName(
            @RequestParam String name) {

        var artist = artistService.findByName(name);

        EntityModel<ArtistResponse> model = EntityModel.of(
                artist,
                linkTo(methodOn(ArtistController.class).getById(artist.id())).withSelfRel(),
                linkTo(methodOn(ArtistController.class).getByName(name)).withRel("by-name")

        );
        return ResponseEntity.ok(model);
    }
}
