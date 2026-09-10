package revisao.fleetlog.infra;

import revisao.fleetlog.Exceptions.CapacidadeExcedidaException;
import revisao.fleetlog.Exceptions.RecursoNaoEncontradoException;
import revisao.fleetlog.dtos.MenssageErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(CapacidadeExcedidaException.class)
    public ResponseEntity<MenssageErrorDto> handlerCapacidadeExcedida(CapacidadeExcedidaException e){
        MenssageErrorDto erro = new MenssageErrorDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                "Capacidade maxima atingida",
                List.of()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }


    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<MenssageErrorDto> handlerRecursoNaoEncontrado(RecursoNaoEncontradoException e){
        MenssageErrorDto erro = new MenssageErrorDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                e.getMessage(),
                "Recurso nao encontrado",
                List.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MenssageErrorDto> handlerMethodArgumentNotValid(MethodArgumentNotValidException e){

        List<String> erros = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        MenssageErrorDto erro = new MenssageErrorDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validacao nos campos informados",
                "Erro de validacao",
                erros
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }
}
