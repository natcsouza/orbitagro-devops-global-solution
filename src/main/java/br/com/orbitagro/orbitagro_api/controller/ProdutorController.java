package br.com.orbitagro.orbitagro_api.controller;

import br.com.orbitagro.orbitagro_api.entity.Produtor;
import br.com.orbitagro.orbitagro_api.service.ProdutorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtores")
public class ProdutorController {

    private final ProdutorService service;

    public ProdutorController(ProdutorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Produtor> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produtor> buscarPorId(@PathVariable Long id) {
        Produtor produtor = service.buscarPorId(id);

        if (produtor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produtor);
    }

    @PostMapping
    public ResponseEntity<Produtor> cadastrar(@RequestBody @Valid Produtor produtor) {
        Produtor novoProdutor = service.salvar(produtor);
        return ResponseEntity.status(201).body(novoProdutor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produtor> atualizar(@PathVariable Long id, @RequestBody @Valid Produtor produtor) {
        Produtor produtorAtualizado = service.atualizar(id, produtor);

        if (produtorAtualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(produtorAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = service.deletar(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}