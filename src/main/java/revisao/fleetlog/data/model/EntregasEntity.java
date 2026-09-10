package revisao.fleetlog.data.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tb_entregas")

public class EntregasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descricao;

    @NotNull
    private Double pesoKg;

    @NotNull
    private Double distanciaKm;

    @NotNull
    private Double custoFrete;

    @ManyToOne
    @JoinColumn(name = "veiculo_id", nullable = false)
    @JsonIgnore
    private VeiculoEntity veiculo;

}
