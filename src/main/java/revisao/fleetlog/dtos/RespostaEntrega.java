package revisao.fleetlog.dtos;

import revisao.fleetlog.data.model.EntregasEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record RespostaEntrega (
        Long id,
        String placa,
        Double capacidadeCarga,
        Double custoFrete,
        List<EntregasEntity> entregas,
        Double distanciaKm

){

}
