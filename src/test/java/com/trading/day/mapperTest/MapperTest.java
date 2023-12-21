package com.trading.day.mapperTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class MapperTest {

//    ModelMapper modelMapper;

    @Test
    void task_one() throws JsonProcessingException {
        ModelMapper modelMapper = new ModelMapper();
        System.out.println("task_one");

        // make Json
        testDTO dto = new testDTO();
        dto.setName("John Doe");
        dto.setAge("30");
        dto.setAddress("1234 Park St");

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        System.out.println(json);

        // fail - modelMapper 는 Object - Object 매핑만 가능하다.
        testDTO testDTO = modelMapper.map(json, testDTO.class);
        System.out.println(testDTO.toString());
    }

}

class testDTO {
    private String name;
    private String age;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "testDTO{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
