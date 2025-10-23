package repo.entity.enums;

import org.springframework.core.convert.converter.Converter;

public class DegreeTypeConverter implements Converter<String, DegreeType> {

    @Override
    public DegreeType convert(String source) {
        try {
            return DegreeType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
