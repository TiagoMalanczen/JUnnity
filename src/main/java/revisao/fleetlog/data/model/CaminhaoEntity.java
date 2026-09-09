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
@Table(name = "tb_caminhao")

public class CaminhaoEntity extends VeiculoEntity{

    @NotNull
    private Integer quantidadeEixos;


    @Override
    public Double calcularFrete(Double distanciaKm) {
        return (3.0*distanciaKm) + (10.0 * quantidadeEixos);
    }
}
