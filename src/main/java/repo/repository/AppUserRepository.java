package repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;
import repo.entity.enums.UserRole;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserEntity, Long> {

    Optional<AppUserEntity> findByUsername(String email);

    boolean existsByUsername(String username);

    boolean existsByHemisId(String hemisId);

    boolean existsByHemisIdAndIdNot(String hemisId, Long id);

    Optional<AppUserEntity> findByIdAndIsBlockedFalse(Long id);

    @Query(
            nativeQuery = true,
            value = """
                    select count(*) from app_user u
                    where (
                        lower(u.username) like ?1 or
                        lower(u.hemis_id) like ?1 or
                        lower(u.first_name) like ?1 or
                        lower(u.last_name) like ?1 or
                        lower(u.middle_name) like ?1
                    )
                    """
    )
    Long countBySearchKey(String searchKey);

    @Query(
            nativeQuery = true,
            value = """
                    select * from app_user u
                    where (
                        lower(u.username) like ?1 or
                        lower(u.hemis_id) like ?1 or
                        lower(u.first_name) like ?1 or
                        lower(u.last_name) like ?1 or
                        lower(u.middle_name) like ?1
                    )
                    order by u.id desc
                    limit ?2
                    offset ?3
                    """
    )
    List<AppUserEntity> findBySearchKey(String searchKey, long limit, long offset);

    @Query(
            nativeQuery = true,
            value = """
                    select count(*) from app_user u
                    where u.user_role = ?2 and
                    (
                        lower(u.username) like ?1 or
                        lower(u.hemis_id) like ?1 or
                        lower(u.first_name) like ?1 or
                        lower(u.last_name) like ?1 or
                        lower(u.middle_name) like ?1
                    )
                    """
    )
    Long countBySearchKeyAndUserRole(String searchKey, String userRole);

    @Query(
            nativeQuery = true,
            value = """
                    select * from app_user u
                    where u.user_role = ?2 and
                    (
                        lower(u.username) like ?1 or
                        lower(u.hemis_id) like ?1 or
                        lower(u.first_name) like ?1 or
                        lower(u.last_name) like ?1 or
                        lower(u.middle_name) like ?1
                    )
                    order by u.id desc
                    limit ?3
                    offset ?4
                    """
    )
    List<AppUserEntity> findBySearchKeyAndUserRole(String searchKey, String userRole, long limit, long offset);

    boolean existsByDepartment(DepartmentEntity department);

    Optional<AppUserEntity> findByIdAndUserRole(Long id, UserRole userRole);

}
