package repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repo.entity.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    List<DepartmentEntity> findAllByOrderById();
    List<DepartmentEntity> findByIsBlockedOrderById(Boolean isBlocked);

}
