package depth.mvp.thinkerbell.domain.dept_info.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import depth.mvp.thinkerbell.domain.dept_info.dto.DeptContactDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeptContactService {

    private static final List<DeptContactDto> contacts;

    static {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("DeptsContact.json").getInputStream();
            contacts = objectMapper.readValue(inputStream, new TypeReference<List<DeptContactDto>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON 파일을 읽지 못했습니다.", e);
        }
    }

    public List<DeptContactDto> getContacts() {
        return contacts;
    }
}
