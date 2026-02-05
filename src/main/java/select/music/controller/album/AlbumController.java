package select.music.controller.album;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import select.music.domain.artist.ArtistType;
import select.music.dto.album.AlbumRequestDTO;
import select.music.dto.album.AlbumResponseDTO;
import select.music.service.album.AlbumService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Albums", description = "Gerenciamento de álbuns musicais")
@RestController
@RequestMapping("/api/v1/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService service;
    private final PagedResourcesAssembler<AlbumResponseDTO> assembler;


    @Operation(
            summary = "Listar imagens do álbum",
            description = "Retorna URLs assinadas das imagens associadas a um álbum"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Imagens retornadas com sucesso"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @GetMapping("/{albumId}/images")
    public List<String> listAlbumImages(@PathVariable UUID albumId) {
        return service.listPresignedImages(albumId);
    }


    @Operation(
            summary = "Criar álbum",
            description = "Cria um novo álbum"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Álbum criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
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

    @Operation(
            summary = "Buscar álbum por ID",
            description = "Retorna os dados de um álbum específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Álbum encontrado"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AlbumResponseDTO>> getById(@PathVariable UUID id) {
        var album = service.findById(id);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                album,
                linkTo(methodOn(AlbumController.class).getById(id)).withSelfRel(),
                linkTo(methodOn(AlbumController.class).update(id, null)).withRel("update")
        );

        return ResponseEntity.ok(model);
    }

    @Operation(
            summary = "Listar álbuns",
            description = "Lista álbuns com paginação e filtro opcional por artista"
    )
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<AlbumResponseDTO>>> findAll(
            @RequestParam(required = false) UUID artistId,
            @ParameterObject Pageable pageable
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

    @Operation(
            summary = "Filtrar álbuns",
            description = "Filtra álbuns por tipo de artista e data de criação"
    )
    @GetMapping("/filters")
    public ResponseEntity<PagedModel<EntityModel<AlbumResponseDTO>>> findFilters (
            @Parameter(
                    description = "Tipos de artista",
                    schema = @Schema(implementation = ArtistType.class)
            )
            @RequestParam(required = false) Set<ArtistType> artistType,

            @Parameter(description = "Data mínima de criação (YYYY-MM-DD)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate createdAfter,

            @ParameterObject Pageable pageable
    ){
        Page<AlbumResponseDTO> page = service.findAllWithFilters(
                artistType,
                createdAfter,
                pageable
        );

        PagedModel<EntityModel<AlbumResponseDTO>> model = assembler.toModel(
                page,
                album -> EntityModel.of(
                        album,
                        linkTo(methodOn(AlbumController.class)
                                .getById(album.id()))
                                .withSelfRel()
                )
        );
         return ResponseEntity.ok(model);
    }

    @Operation(
            summary = "Atualizar álbum",
            description = "Atualiza os dados de um álbum existente"
    )
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AlbumResponseDTO>> update(
            @Parameter(description = "ID do álbum", required = true)
            @PathVariable UUID id,

            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = AlbumRequestDTO.class))
            )
            AlbumRequestDTO request
    ) {
        var updated = service.update(id, request);

        EntityModel<AlbumResponseDTO> model = EntityModel.of(
                updated,
                linkTo(methodOn(AlbumController.class).getById(id)).withSelfRel()
        );

        return ResponseEntity.ok(model);
    }
}
