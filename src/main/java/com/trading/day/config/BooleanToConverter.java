package com.trading.day.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/************
 * @info : Boolean Converter
 * @name : BooleanToConverter
 * @date : 2023/01/04 1:58 AM
 * @author : SeokJun Kang(swings134@gmail.com)
 * @version : 1.0.0
 * @Description :
 * - Entity <-> DB 사이의 data를 1,0 -> Y,N 으로 변환하는 컨버터 클래스
 * - 사용 할 Entity 필드 위에 @Convert(converter = BooleanToConverter.class) 작성.
 ************/
@Converter
public class BooleanToConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null && attribute) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
