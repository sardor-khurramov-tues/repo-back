package repo.entity.enums;

import org.springframework.core.convert.converter.Converter;

public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(String source) {
        try {
            return UserRole.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
