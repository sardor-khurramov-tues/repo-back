package repo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repo.dto.ResponseDto;
import repo.dto.document.DocumentDto;
import repo.dto.document.dissertation.DocDissertationSubmitDto;
import repo.dto.document.dissertation.DocDissertationUpdateDto;
import repo.entity.AppUserEntity;
import repo.entity.DepartmentEntity;
import repo.entity.DocContributorEntity;
import repo.entity.DocumentEntity;
import repo.entity.enums.DocRole;
import repo.entity.enums.DocType;
import repo.repository.DocContributorRepository;
import repo.repository.DocumentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocDissertationService {

    private final DocumentService documentService;
    private final AppUserService appUserService;
    private final DocumentRepository documentRepository;
    private final DocContributorRepository docContributorRepository;
    private final DepartmentService departmentService;
    private final MapperService mapperService;

    @Transactional
    public ResponseEntity<ResponseDto<Long>> create(DocDissertationSubmitDto dto) {
        DepartmentEntity department = departmentService.getDepartmentById(dto.departmentId());

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        documentService.validateCreateDocument(submitter);

        DocumentEntity document = new DocumentEntity();
        document.setCreatedAt(LocalDateTime.now());

        String docKey = documentService.generateDocKey();
        document.setDocKey(docKey);

        document.setDocType(DocType.DISSERTATION);
        document.setTitle(dto.title().trim());
        document.setRor(dto.ror());
        document.setDegree(dto.degreeType());
        document.setIsbn(dto.isbn());
        document.setDocAbstract(dto.docAbstract().trim());
        document.setIsPublished(false);
        document.setSubmitter(submitter);
        document.setDepartment(department);

        DocumentEntity savedDocument = documentRepository.save(document);

        DocContributorEntity primaryAuthor = documentService.createContributorFromSubmitter(document, submitter, DocRole.AUTHOR);
        docContributorRepository.save(primaryAuthor);

        List<DocContributorEntity> contributorlist = documentService.createContributorlist(dto.docContributorList(), submitter.getId(), savedDocument);
        docContributorRepository.saveAll(contributorlist);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                savedDocument.getId()
        ));
    }

    @Transactional
    public ResponseEntity<ResponseDto<DocumentDto>> update(Long id, DocDissertationUpdateDto dto) {
        DocumentEntity document = documentService.getDocumentById(id);
        documentService.validateDocIsNotPublished(document);

        AppUserEntity submitter = appUserService.getAuthenticatedUser();
        documentService.validateDocSubmitter(document, submitter);

        document.setTitle(dto.title().trim());
        document.setRor(dto.ror());
        document.setDegree(dto.degreeType());
        document.setIsbn(dto.isbn());
        document.setDocAbstract(dto.docAbstract().trim());

        DocumentEntity savedDocument = documentRepository.save(document);

        return ResponseEntity.ok(ResponseDto.getSuccess(
                mapperService.mapDocumentEntityToDto(savedDocument)
        ));
    }

}
