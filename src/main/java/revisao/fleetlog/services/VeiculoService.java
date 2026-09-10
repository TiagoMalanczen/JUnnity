package revisao.fleetlog.services;

import revisao.fleetlog.Exceptions.CapacidadeExcedidaException;
import revisao.fleetlog.Exceptions.RecursoNaoEncontradoException;
import revisao.fleetlog.data.model.CaminhaoEntity;
import revisao.fleetlog.data.model.EntregasEntity;
import revisao.fleetlog.data.model.MotoEntity;
import revisao.fleetlog.data.model.VeiculoEntity;
import revisao.fleetlog.data.repository.EntregasRepository;
import revisao.fleetlog.data.repository.VeiculoRepository;
import revisao.fleetlog.dtos.CadastroCaminhaoDto;
import revisao.fleetlog.dtos.CadastroMotoDto;
import revisao.fleetlog.dtos.NovaEntregaDto;
import revisao.fleetlog.dtos.RespostaEntrega;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final EntregasRepository entregasRepository;

    public void cadastrarCaminhao(CadastroCaminhaoDto cadastroCaminhaoDto){
        if (veiculoRepository.existsByPlaca(cadastroCaminhaoDto.placa())) {
            throw new RuntimeException("Placa já registrada no sistema");
        }

        veiculoRepository.save(CaminhaoEntity.builder()
                        .placa(cadastroCaminhaoDto.placa())
                        .numero(cadastroCaminhaoDto.numero())
                        .capacidadeCarga(cadastroCaminhaoDto.capacidadeCarga())
                        .quantidadeEixos(cadastroCaminhaoDto.quantidadeEixos())
                .build());
    }

    public void cadastrarMoto(CadastroMotoDto cadastroMotoDto){
        if (veiculoRepository.existsByPlaca(cadastroMotoDto.placa())) {
            throw new RuntimeException("Placa já registrada no sistema");
        }

        veiculoRepository.save(MotoEntity.builder()
                .placa(cadastroMotoDto.placa())
                .numero(cadastroMotoDto.numero())
                .capacidadeCarga(cadastroMotoDto.capacidadeCarga())
                .cilindradas(cadastroMotoDto.cilindradas())
                .build());
    }

    public void alocarEntrega(Long idVeiculo, NovaEntregaDto novaEntregaDto){
        VeiculoEntity veiculo = veiculoRepository.findById(idVeiculo)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo nao encontrado"));

        Double pesoAtual = 0.0;

        for (EntregasEntity entregasEntity: veiculo.getEntregas()){
            pesoAtual += entregasEntity.getPesoKg();
        }

        if(pesoAtual + novaEntregaDto.pesoKg() > veiculo.getCapacidadeCarga()){
            throw new CapacidadeExcedidaException("Carga excedida");
        }

        Double custo = veiculo.calcularFrete(novaEntregaDto.distanciaKm());

        entregasRepository.save(EntregasEntity.builder()
                        .descricao(novaEntregaDto.descricao())
                        .pesoKg(novaEntregaDto.pesoKg())
                        .distanciaKm(novaEntregaDto.distanciaKm())
                        .custoFrete(custo)
                        .veiculo(veiculo)
                .build());

    }

    public RespostaEntrega obterResumoVeiculo(Long veiculoId){
        VeiculoEntity veiculo = veiculoRepository.findById(veiculoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Veiculo nao encontrado"));

        Double custoTotal = 0.0;
        for (EntregasEntity entrega : veiculo.getEntregas()) {
            custoTotal += entrega.getCustoFrete();
        }

        return RespostaEntrega.builder()
                .id(veiculo.getId())
                .placa(veiculo.getPlaca())
                .capacidadeCarga(veiculo.getCapacidadeCarga())
                .custoFrete(custoTotal)
                .entregas(veiculo.getEntregas())
                .build();
    }
}
