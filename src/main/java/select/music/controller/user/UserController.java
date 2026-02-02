package select.music.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.dto.user.UserRequestDTO;
import select.music.dto.user.UserResponseDTO;
import select.music.service.user.UserService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/create")
    public ResponseEntity<EntityModel<UserResponseDTO>> create(
            @RequestBody UserRequestDTO request
    ) {
        var created = service.create(request);

        EntityModel<UserResponseDTO> model = EntityModel.of(
                created,
                linkTo(methodOn(UserController.class)
                        .getById(created.id()))
                        .withSelfRel()
        );

        return ResponseEntity
                .created(URI.create("/api/v1/users/" + created.id()))
                .body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserResponseDTO>> getById(
            @PathVariable UUID id
    ) {
        var user = service.getById(id);

        EntityModel<UserResponseDTO> model = EntityModel.of(
                user,
                linkTo(methodOn(UserController.class)
                        .getById(id))
                        .withSelfRel()
        );

        return ResponseEntity.ok(model);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }
}