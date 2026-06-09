package br.com.orbitagro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "TB_AREA_CULTIVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaCultivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeArea;

    @Column(nullable = false)
    private String cultura;

    private Double latitude;
    private Double longitude;
    private Double hectares;

    @ManyToOne
    @JoinColumn(name = "produtor_id", nullable = false)
    private Produtor produtor;

    @OneToMany(mappedBy = "areaCultivo", cascade = CascadeType.ALL)
    private List<Monitoramento> monitoramentos;

    @OneToMany(mappedBy = "areaCultivo", cascade = CascadeType.ALL)
    private List<Alerta> alertas;
}
