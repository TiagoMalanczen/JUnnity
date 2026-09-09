package revisao.fleetlog.controllers;

import revisao.fleetlog.dtos.CadastroCaminhaoDto;
import revisao.fleetlog.dtos.CadastroMotoDto;
import revisao.fleetlog.dtos.NovaEntregaDto;
import revisao.fleetlog.dtos.RespostaEntrega;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import revisao.fleetlog.services.VeiculoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/veiculos")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/caminhao")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCaminhao(@RequestBody @Validated CadastroCaminhaoDto cadastroCaminhaoDto){
        veiculoService.cadastrarCaminhao(cadastroCaminhaoDto);
    }

    @PostMapping("/moto")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarMoto(@RequestBody @Validated CadastroMotoDto cadastroMotoDto){
        veiculoService.cadastrarMoto(cadastroMotoDto);
    }

    @PostMapping("/{id}/entregas")
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarNovaEntrega(@PathVariable @Validated Long id,
                                     @Validated @RequestBody NovaEntregaDto NovaEntregaDto){
        veiculoService.alocarEntrega(id, NovaEntregaDto);
    }

    @GetMapping("/{id}/resumo")
    @ResponseStatus(HttpStatus.OK)
    public RespostaEntrega mostrarResumo(@PathVariable Long id){
        return veiculoService.obterResumoVeiculo(id);
    }
}
