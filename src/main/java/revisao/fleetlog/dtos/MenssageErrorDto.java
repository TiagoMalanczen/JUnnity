package revisao.fleetlog.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record MenssageErrorDto(
        LocalDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        List<String> validacoes
) {}
