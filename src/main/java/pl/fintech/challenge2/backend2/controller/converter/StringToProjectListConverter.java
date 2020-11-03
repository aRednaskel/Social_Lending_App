package pl.fintech.challenge2.backend2.controller.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.fintech.challenge2.backend2.domain.user.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class StringToProjectListConverter
		implements Converter<String, List<Role>> {

	@Override
	public List<Role> convert(String source) {
		List<Role> list = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
        try {
            Role[] array = mapper.readValue(source, Role[].class);
            list.addAll(Arrays.asList(array));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return list;
	}
}