package repo.entity.enums;

import org.springframework.core.convert.converter.Converter;

public class DocRoleConverter implements Converter<String, DocRole> {

    @Override
    public DocRole convert(String source) {
        try {
            return DocRole.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
