package repo.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    STAFF,
    AUTHOR;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
