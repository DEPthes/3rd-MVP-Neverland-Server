package depth.mvp.thinkerbell.domain.dept_info.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import depth.mvp.thinkerbell.domain.dept_info.dto.DeptURLDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DeptURLService {

    private static final List<DeptURLDto> url;

    static {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("DeptURL.json").getInputStream();
            url = objectMapper.readValue(inputStream, new TypeReference<List<DeptURLDto>>() {});
        } catch (IOException e) {
            throw new RuntimeException("JSON 파일을 읽지 못했습니다.", e);
        }
    }

    public List<DeptURLDto> getAllDeptURL() {
        return url;
    }
}
