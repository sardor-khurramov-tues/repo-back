package repo.service;

import org.mapstruct.Mapper;
import repo.dto.department.DepartmentDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.DocumentListDto;
import repo.dto.user.AppUserResponseDto;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;
import repo.entity.DocumentEntity;

@Mapper(componentModel = "spring")
public interface MapperService {

    DepartmentDto mapDepartmentEntityToDto(DepartmentEntity entity);
    AppUserResponseDto mapAppUserEntityToDto(AppUserEntity entity);
    DocumentDto mapDocumentEntityToDto(DocumentEntity entity);
    DocumentListDto.DocumentDto mapDocumentEntityToListDto(DocumentEntity entity);

}
