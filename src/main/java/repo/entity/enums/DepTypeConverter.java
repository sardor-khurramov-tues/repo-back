package repo.entity.enums;

import org.springframework.core.convert.converter.Converter;

public class DepTypeConverter implements Converter<String, DepType> {

    @Override
    public DepType convert(String source) {
        try {
            return DepType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
