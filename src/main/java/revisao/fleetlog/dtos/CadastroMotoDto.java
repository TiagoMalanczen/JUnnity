package revisao.fleetlog.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroMotoDto(
        @NotBlank  String placa,
        @NotNull Integer numero,
        @NotNull @Positive Double capacidadeCarga,
        @NotNull @Positive Integer cilindradas
) {}
