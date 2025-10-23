package repo.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.constant.Endpoint;
import repo.dto.ResponseDto;
import repo.dto.department.DepartmentDto;
import repo.dto.department.DepartmentSaveDto;
import repo.service.DepartmentService;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(Endpoint.ADMIN + Endpoint.DEPARTMENT)
public class AdminDepartmentController {

    private final DepartmentService departmentService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DepartmentDto>> create(
            @RequestBody @Valid DepartmentSaveDto dto
    ) {
        return departmentService.create(dto);
    }

    @PutMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<DepartmentDto>> update(
            @PathVariable @Valid Long id,
            @RequestBody @Valid DepartmentSaveDto dto
    ) {
        return departmentService.update(id, dto);
    }

    @DeleteMapping(path = Endpoint.ID_PATH_VARIABLE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<Object>> delete(
            @PathVariable @Valid Long id
    ) {
        return departmentService.delete(id);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDto<List<DepartmentDto>>> getAll() {
        return departmentService.getAll();
    }

}
