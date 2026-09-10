package revisao.fleetlog.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import revisao.fleetlog.data.model.CaminhaoEntity;
import revisao.fleetlog.data.repository.EntregasRepository;
import revisao.fleetlog.data.repository.VeiculoRepository;
import revisao.fleetlog.dtos.CadastroCaminhaoDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private EntregasRepository entregasRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @Captor
    private ArgumentCaptor<CaminhaoEntity> caminhaoCaptor;

    @Test
    @DisplayName("Deve cadastrar caminhão com sucesso quando a placa não existir")
    void deveCadastrarCaminhaoComSucesso() {
        CadastroCaminhaoDto dto = new CadastroCaminhaoDto("ABC1D23", 101, 15000.0, 4);

        when(veiculoRepository.existsByPlaca(dto.placa())).thenReturn(false);

        veiculoService.cadastrarCaminhao(dto);

        verify(veiculoRepository, times(1)).save(caminhaoCaptor.capture());
        CaminhaoEntity caminhaoSalvo = caminhaoCaptor.getValue();

        assertEquals("ABC1D23", caminhaoSalvo.getPlaca());
        assertEquals(101, caminhaoSalvo.getNumero());
        assertEquals(15000.0, caminhaoSalvo.getCapacidadeCarga());
        assertEquals(4, caminhaoSalvo.getQuantidadeEixos());
    }

    @Test
    @DisplayName("Deve lançar RuntimeException e não salvar quando a placa já existir")
    void deveLancarExcecaoQuandoPlacaJaExistir() {
        CadastroCaminhaoDto dto = new CadastroCaminhaoDto("ABC1D23", 101, 15000.0, 4);

        when(veiculoRepository.existsByPlaca(dto.placa())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                veiculoService.cadastrarCaminhao(dto)
        );

        assertEquals("Placa já registrada no sistema", exception.getMessage());

        verify(veiculoRepository, never()).save(any());
    }
}