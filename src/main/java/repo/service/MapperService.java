package repo.service;

import org.mapstruct.Mapper;
import repo.dto.department.DepartmentDto;
import repo.dto.user.AppUserResponseDto;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;

@Mapper(componentModel = "spring")
public interface MapperService {

    DepartmentDto mapDepartmentEntityToDto(DepartmentEntity entity);
    AppUserResponseDto mapAppUserEntityToDto(AppUserEntity entity);

}
