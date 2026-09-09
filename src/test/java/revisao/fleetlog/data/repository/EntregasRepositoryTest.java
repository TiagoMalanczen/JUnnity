package revisao.fleetlog.data.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import revisao.fleetlog.data.model.CaminhaoEntity;
import revisao.fleetlog.data.model.EntregasEntity;
import revisao.fleetlog.data.model.VeiculoEntity; // se entrega exigir veiculo

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class EntregasRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntregasRepository entregasRepository;

    @Test
    @DisplayName("Entrega encontrada com sucesso")
    void findByIdSucesso() {
        // 1. Cria e persiste um Veículo primeiro (dependência obrigatória)
        VeiculoEntity veiculo = new CaminhaoEntity(10);
        veiculo.setPlaca("ABC-1234");
        veiculo.setCapacidadeCarga(1000.0);
        veiculo.setNumero(10);

        this.entityManager.persist(veiculo);

        // 2. Cria a Entrega e associa o Veículo já persistido
        EntregasEntity novaEntrega = new EntregasEntity();
        novaEntrega.setDescricao("teste");
        novaEntrega.setDistanciaKm(10.0);
        novaEntrega.setPesoKg(7.0);
        novaEntrega.setCustoFrete(50.0);
        novaEntrega.setVeiculo(veiculo); // <-- ASSOCIAÇÃO OBRIGATÓRIA

        // 3. Agora o persist não viola a constraint NOT NULL
        this.entityManager.persist(novaEntrega);
        this.entityManager.flush(); // Força a sincronização imediata com o H2

        // 4. Executa a busca e valida
        Optional<EntregasEntity> resultado = this.entregasRepository.findById(novaEntrega.getId());

        assertThat(resultado.isPresent()).isTrue();
        assertThat(resultado.get().getDescricao()).isEqualTo("teste");
    }

    @Test
    @DisplayName("Deve retornar Optional vazio quando o registro não existir no banco")
    void findByIdVazio() {
        // ARRANGE: um ID que nunca foi salvo
        Long idInexistente = 99999L;

        // ACT: busca pelo ID inexistente
        Optional<EntregasEntity> resultado = this.entregasRepository.findById(idInexistente);

        // ASSERT: valida que veio vazio e não estourou erro de execução
        assertThat(resultado.isEmpty()).isTrue();
    }
}