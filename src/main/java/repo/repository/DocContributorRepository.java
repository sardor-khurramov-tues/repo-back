package repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repo.entity.AppUserEntity;
import repo.entity.DocContributorEntity;

@Repository
public interface DocContributorRepository extends JpaRepository<DocContributorEntity, Long> {

    boolean existsByAppUser(AppUserEntity contributor);

}
