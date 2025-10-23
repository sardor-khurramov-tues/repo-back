package repo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import repo.entity.enums.UserRole;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_user")
public class AppUserEntity implements UserDetails {

    @Id
    @SequenceGenerator(name = "app_user_id_generator", sequenceName = "app_user_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "app_user_id_generator")
    private Long id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "hemis_id")
    private String hemisId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    private String orcid;
    private String ror;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_blocked")
    private Boolean isBlocked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department")
    private DepartmentEntity department;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(getUserRole());
    }

    @Override
    public String getUsername() {
        return username;
    }

}
