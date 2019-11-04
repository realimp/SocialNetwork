package ru.skillbox.socialnetwork.api.converters;

import ru.skillbox.socialnetwork.api.responses.NotificationTypeCode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NotificationTypeConverter implements AttributeConverter<NotificationTypeCode,Integer> {
    @Override
    public Integer convertToDatabaseColumn(NotificationTypeCode nameCode) {
        if (nameCode == null) {
            return null;
        }
        return nameCode.getCode();
    }

    @Override
    public NotificationTypeCode convertToEntityAttribute(Integer code) {
        if ( code == null) {
            return null;
        }

        return Stream.of(NotificationTypeCode.values())
                .filter((NotificationTypeCode c) -> {
                    final boolean equals = c.getCode().equals(code);
                    return equals;
                })
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
