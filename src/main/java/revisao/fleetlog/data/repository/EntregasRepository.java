package revisao.fleetlog.data.repository;

import revisao.fleetlog.data.model.EntregasEntity;
import revisao.fleetlog.data.model.VeiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EntregasRepository extends JpaRepository<EntregasEntity, Long> {

    Optional<EntregasEntity> findById(Long id);
}
