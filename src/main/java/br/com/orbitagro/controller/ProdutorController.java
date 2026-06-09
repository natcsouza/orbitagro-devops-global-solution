package br.com.orbitagro.controller;

import br.com.orbitagro.dto.ProdutorDTO;
import br.com.orbitagro.service.ProdutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtores")
@RequiredArgsConstructor
@Tag(name = "Produtores", description = "Gerenciamento de produtores rurais")
@SecurityRequirement(name = "Bearer")
public class ProdutorController {

    private final ProdutorService service;

    @GetMapping
    @Operation(summary = "Listar todos os produtores")
    public ResponseEntity<List<EntityModel<ProdutorDTO.Response>>> findAll() {
        List<EntityModel<ProdutorDTO.Response>> list = service.findAll().stream()
                .map(p -> EntityModel.of(p,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutorController.class).findById(p.id())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutorController.class).findAll()).withRel("produtores")))
                .toList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar produtor por ID")
    public ResponseEntity<EntityModel<ProdutorDTO.Response>> findById(@PathVariable Long id) {
        ProdutorDTO.Response response = service.findById(id);
        EntityModel<ProdutorDTO.Response> model = EntityModel.of(response,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutorController.class).findById(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProdutorController.class).findAll()).withRel("produtores"));
        return ResponseEntity.ok(model);
    }

    @PostMapping
    @Operation(summary = "Criar produtor")
    public ResponseEntity<ProdutorDTO.Response> create(@Valid @RequestBody ProdutorDTO.Request dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produtor")
    public ResponseEntity<ProdutorDTO.Response> update(@PathVariable Long id, @Valid @RequestBody ProdutorDTO.Request dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produtor")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
