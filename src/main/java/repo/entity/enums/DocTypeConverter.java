package repo.entity.enums;

import org.springframework.core.convert.converter.Converter;

public class DocTypeConverter implements Converter<String, DocType> {

    @Override
    public DocType convert(String source) {
        try {
            return DocType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
