package revisao.fleetlog.data.repository;

import revisao.fleetlog.data.model.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

    Optional<VeiculoEntity> findByPlaca(String placa);
    boolean existsByPlaca(String placa);
}
