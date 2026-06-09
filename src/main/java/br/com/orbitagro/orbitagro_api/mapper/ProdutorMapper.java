package br.com.orbitagro.orbitagro_api.mapper;

import br.com.orbitagro.orbitagro_api.dto.request.ProdutorRequestDTO;
import br.com.orbitagro.orbitagro_api.dto.response.ProdutorResponseDTO;
import br.com.orbitagro.orbitagro_api.entity.Produtor;
import org.springframework.stereotype.Component;

@Component
public class ProdutorMapper {

    public Produtor toEntity(ProdutorRequestDTO dto) {
        Produtor produtor = new Produtor();
        produtor.setNome(dto.getNome());
        produtor.setEmail(dto.getEmail());
        produtor.setTelefone(dto.getTelefone());
        return produtor;
    }

    public void updateEntity(Produtor produtor, ProdutorRequestDTO dto) {
        produtor.setNome(dto.getNome());
        produtor.setEmail(dto.getEmail());
        produtor.setTelefone(dto.getTelefone());
    }

    public ProdutorResponseDTO toResponse(Produtor produtor) {
        return ProdutorResponseDTO.builder()
                .id(produtor.getId())
                .nome(produtor.getNome())
                .email(produtor.getEmail())
                .telefone(produtor.getTelefone())
                .build();
    }
}
