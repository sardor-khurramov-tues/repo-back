package repo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import repo.constant.ResponseType;
import repo.dto.ResponseDto;
import repo.dto.department.DepartmentDto;
import repo.dto.department.DepartmentSaveDto;
import repo.entity.DepartmentEntity;
import repo.exception.CustomBadRequestException;
import repo.repository.AppUserRepository;
import repo.repository.DepartmentRepository;
import repo.repository.DocumentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final MapperService mapperService;
    private final AppUserRepository appUserRepository;
    private final DocumentRepository documentRepository;

    public ResponseEntity<ResponseDto<DepartmentDto>> create(DepartmentSaveDto dto) {
        DepartmentEntity department = new DepartmentEntity();

        department.setDepType(dto.depType());
        department.setNameUz(dto.nameUz());
        department.setNameEn(dto.nameEn());
        department.setNameRu(dto.nameRu());
        department.setIsBlocked(dto.isBlocked());

        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapDepartmentEntityToDto(savedDepartment)
        ));
    }

    public ResponseEntity<ResponseDto<DepartmentDto>> update(Long id, DepartmentSaveDto dto) {
        DepartmentEntity department = getDepartmentById(id);

        department.setDepType(dto.depType());
        department.setNameUz(dto.nameUz());
        department.setNameEn(dto.nameEn());
        department.setNameRu(dto.nameRu());
        department.setIsBlocked(dto.isBlocked());

        DepartmentEntity savedDepartment = departmentRepository.save(department);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapDepartmentEntityToDto(savedDepartment)
        ));
    }

    public ResponseEntity<ResponseDto<Object>> delete(Long id) {
        DepartmentEntity department = getDepartmentById(id);
        validateDeletion(department);

        departmentRepository.delete(department);
        return ResponseEntity.ok(ResponseDto.getEmptySuccess());
    }

    public ResponseEntity<ResponseDto<List<DepartmentDto>>> getAll() {
        List<DepartmentDto> dtoList = departmentRepository.findAllByOrderById()
                .stream()
                .map(mapperService::mapDepartmentEntityToDto)
                .toList();

        return ResponseEntity.ok(ResponseDto.getSuccess(dtoList));
    }

    public ResponseEntity<ResponseDto<List<DepartmentDto>>> getPublicDepartmentList(Boolean isBlocked) {
        List<DepartmentEntity> departmentList = Objects.isNull(isBlocked) ?
                departmentRepository.findAllByOrderById() :
                departmentRepository.findByIsBlockedOrderById(isBlocked);

        List<DepartmentDto> dtoList = departmentList
                .stream()
                .map(mapperService::mapDepartmentEntityToDto)
                .toList();

        return ResponseEntity.ok(ResponseDto.getSuccess(dtoList));
    }



    public DepartmentEntity getDepartmentById(Long id) {
        try {
            return departmentRepository.findById(id)
                    .orElseThrow();
        } catch (NoSuchElementException e) {
            throw new CustomBadRequestException(ResponseType.NO_DEPARTMENT_WITH_THIS_ID);
        }
    }

    private void validateDeletion(DepartmentEntity department) {
        if (
                appUserRepository.existsByDepartment(department) ||
                        documentRepository.existsByDepartment(department)
        )
            throw new CustomBadRequestException(ResponseType.DEPARTMENT_DELETION_FORBIDDEN);
    }

}
