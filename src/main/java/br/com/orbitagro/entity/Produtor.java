package br.com.orbitagro.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "TB_PRODUTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produtor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    @Column(unique = true)
    private String cpf;

    @Embedded
    private Endereco endereco;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @OneToMany(mappedBy = "produtor", cascade = CascadeType.ALL)
    private List<AreaCultivo> areas;

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }
}
