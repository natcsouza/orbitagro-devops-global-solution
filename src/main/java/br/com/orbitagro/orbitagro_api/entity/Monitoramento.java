package br.com.orbitagro.orbitagro_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "TB_MONITORAMENTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Monitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Double indiceNdvi;

    @NotNull
    private Double umidadeSolo;

    @NotNull
    private Double temperaturaSolo;

    private String observacao;

    @ManyToOne
    @JoinColumn(name = "area_cultivo_id")
    private AreaCultivo areaCultivo;
}