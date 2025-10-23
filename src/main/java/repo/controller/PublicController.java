package repo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.department.DepartmentDto;
import repo.service.DepartmentService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.PUBLIC)
public class PublicController {

    private final DepartmentService departmentService;

    @GetMapping(path = Endpoint.DEPARTMENT, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<List<DepartmentDto>>> getPublicDepartmentList(
            @RequestParam(required = false) @Valid Boolean isBlocked
    ) {
        return departmentService.getPublicDepartmentList(isBlocked);
    }

}
