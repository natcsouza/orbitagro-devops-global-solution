package br.com.orbitagro.orbitagro_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "TB_AREA_CULTIVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AreaCultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da área é obrigatório")
    private String nomeArea;

    @NotBlank(message = "Cultura é obrigatória")
    private String cultura;

    @NotNull(message = "Latitude é obrigatória")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "produtor_id")
    private Produtor produtor;
}