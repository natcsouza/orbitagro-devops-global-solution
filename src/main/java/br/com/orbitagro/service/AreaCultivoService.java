package br.com.orbitagro.service;

import br.com.orbitagro.dto.AreaCultivoDTO;
import br.com.orbitagro.entity.*;
import br.com.orbitagro.exception.ResourceNotFoundException;
import br.com.orbitagro.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaCultivoService {

    private final AreaCultivoRepository repository;
    private final ProdutorRepository produtorRepository;

    public List<AreaCultivoDTO.Response> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AreaCultivoDTO.Response findById(Long id) {
        return toResponse(findOrThrow(id));
    }

    public List<AreaCultivoDTO.Response> findByProdutor(Long produtorId) {
        return repository.findByProdutorId(produtorId).stream().map(this::toResponse).toList();
    }

    public AreaCultivoDTO.Response create(AreaCultivoDTO.Request dto) {
        Produtor produtor = produtorRepository.findById(dto.produtorId())
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado: " + dto.produtorId()));

        AreaCultivo area = AreaCultivo.builder()
                .nomeArea(dto.nomeArea())
                .cultura(dto.cultura())
                .latitude(dto.latitude())
                .longitude(dto.longitude())
                .hectares(dto.hectares())
                .produtor(produtor)
                .build();
        return toResponse(repository.save(area));
    }

    public AreaCultivoDTO.Response update(Long id, AreaCultivoDTO.Request dto) {
        AreaCultivo area = findOrThrow(id);
        area.setNomeArea(dto.nomeArea());
        area.setCultura(dto.cultura());
        area.setLatitude(dto.latitude());
        area.setLongitude(dto.longitude());
        area.setHectares(dto.hectares());
        return toResponse(repository.save(area));
    }

    public void delete(Long id) {
        repository.delete(findOrThrow(id));
    }

    private AreaCultivo findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Área de cultivo não encontrada: " + id));
    }

    private AreaCultivoDTO.Response toResponse(AreaCultivo a) {
        return new AreaCultivoDTO.Response(
                a.getId(), a.getNomeArea(), a.getCultura(),
                a.getLatitude(), a.getLongitude(), a.getHectares(),
                a.getProdutor().getId(), a.getProdutor().getNome()
        );
    }
}
