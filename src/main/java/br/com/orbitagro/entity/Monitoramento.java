package br.com.orbitagro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MONITORAMENTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double indiceNdvi;

    private Double ndviAnterior;
    private Double umidadeSolo;
    private Double temperaturaSolo;

    @Column(nullable = false)
    private LocalDateTime dataLeitura;

    @ManyToOne
    @JoinColumn(name = "area_cultivo_id", nullable = false)
    private AreaCultivo areaCultivo;

    @PrePersist
    public void prePersist() {
        this.dataLeitura = LocalDateTime.now();
    }
}
