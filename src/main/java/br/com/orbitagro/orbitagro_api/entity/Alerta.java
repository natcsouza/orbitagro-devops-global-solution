package br.com.orbitagro.orbitagro_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "TB_ALERTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String tipoAlerta;

    @NotBlank
    private String mensagem;

    private String status;

    @ManyToOne
    @JoinColumn(name = "area_cultivo_id")
    private AreaCultivo areaCultivo;
}