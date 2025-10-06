package repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @SequenceGenerator(name = "department_id_generator", sequenceName = "department_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "department_id_generator")
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

}
