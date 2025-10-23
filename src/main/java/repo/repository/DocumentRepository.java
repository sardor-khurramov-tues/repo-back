package repo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;
import repo.entity.DocumentEntity;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    boolean existsBySubmitter(AppUserEntity submitter);

    boolean existsByDepartment(DepartmentEntity department);

    List<DocumentEntity> findBySubmitterAndIsPublished(AppUserEntity submitter, boolean isPublished);

    @Query(
            nativeQuery = true,
            value = """
                    select count(*) from document d
                    where d.submitter = ?2 and
                    (
                        lower(d.title) like ?1 or
                        lower(d.subtitle) like ?1
                    )
                    """
    )
    Long countDocBySearchKeyAndSubmitterId(String searchKey, Long submitterId);

    @Query(
            nativeQuery = true,
            value = """
                    select * from document d
                    where d.submitter = ?2 and
                    (
                        lower(d.title) like ?1 or
                        lower(d.subtitle) like ?1
                    )
                    order by d.created_at desc
                    limit ?3
                    offset ?4
                    """
    )
    List<DocumentEntity> findDocBySearchKeyAndSubmitterId(String searchKey, Long submitterId, long limit, long offset);

    @Query(
            nativeQuery = true,
            value = """
                    select count(*) from document d
                    where d.department = ?2 and
                    d.is_published = ?3 and
                    (
                        lower(d.title) like ?1 or
                        lower(d.subtitle) like ?1
                    )
                    """
    )
    Long countDocBySearchKeyAndDepartmentIdAndIsPublished(
            String searchKey, Long departmentId, boolean isPublished
    );

    @Query(
            nativeQuery = true,
            value = """
                    select * from document d
                    where d.department = ?2 and
                    d.is_published = ?3 and
                    (
                        lower(d.title) like ?1 or
                        lower(d.subtitle) like ?1
                    )
                    order by d.created_at desc
                    limit ?4
                    offset ?5
                    """
    )
    List<DocumentEntity> findDocBySearchKeyAndDepartmentIdAndIsPublished(
            String searchKey, Long departmentId, boolean isPublished, long limit, long offset
    );

}
