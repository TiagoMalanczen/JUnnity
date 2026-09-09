package revisao.fleetlog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NovaEntregaDto(
        @NotBlank String descricao,
        @NotNull @Positive  Double pesoKg,
        @NotNull @Positive Double distanciaKm
) {
}
