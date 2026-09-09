package revisao.fleetlog.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_moto")
public class MotoEntity extends VeiculoEntity{

    @NotNull
    private Integer cilindradas;

    @Override
    public Double calcularFrete(Double distanciaKm) {
        return 1.5*distanciaKm;
    }
}
