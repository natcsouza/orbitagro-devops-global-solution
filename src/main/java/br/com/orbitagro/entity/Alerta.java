package br.com.orbitagro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_ALERTA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAlerta tipoAlerta;

    private String observacao;

    @Column(nullable = false)
    private LocalDateTime dataAlerta;

    @Enumerated(EnumType.STRING)
    private StatusAlerta statusAlerta;

    @ManyToOne
    @JoinColumn(name = "area_cultivo_id", nullable = false)
    private AreaCultivo areaCultivo;

    @PrePersist
    public void prePersist() {
        this.dataAlerta = LocalDateTime.now();
        this.statusAlerta = StatusAlerta.ATIVO;
    }

    public enum TipoAlerta {
        ESTRESSE_HIDRICO,
        EXCESSO_CHUVA,
        TEMPERATURA_ALTA,
        NDVI_BAIXO,
        SECA
    }

    public enum StatusAlerta {
        ATIVO,
        RESOLVIDO,
        IGNORADO
    }
}
