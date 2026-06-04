package br.com.orbitagro.orbitagro_api.service;

import br.com.orbitagro.orbitagro_api.entity.Produtor;
import br.com.orbitagro.orbitagro_api.repository.ProdutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutorService {

    private final ProdutorRepository repository;

    public ProdutorService(ProdutorRepository repository) {
        this.repository = repository;
    }

    public List<Produtor> listarTodos() {
        return repository.findAll();
    }

    public Produtor buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Produtor salvar(Produtor produtor) {
        return repository.save(produtor);
    }

    public Produtor atualizar(Long id, Produtor produtorAtualizado) {
        Produtor produtor = repository.findById(id).orElse(null);

        if (produtor == null) {
            return null;
        }

        produtor.setNome(produtorAtualizado.getNome());
        produtor.setEmail(produtorAtualizado.getEmail());
        produtor.setTelefone(produtorAtualizado.getTelefone());

        return repository.save(produtor);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}