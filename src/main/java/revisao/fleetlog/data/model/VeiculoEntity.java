package revisao.fleetlog.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "veiculos")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class VeiculoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String placa;

    @NotNull
    private Integer numero;

    @NotNull
    private Double capacidadeCarga;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL)
    private List<EntregasEntity> entregas = new ArrayList<>();

    public abstract Double calcularFrete(Double distanciaKm);
}
