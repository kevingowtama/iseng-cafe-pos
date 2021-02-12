package iseng.cafe.pos.controllers;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.entities.Customer;
import iseng.cafe.pos.models.PagedList;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.admin.AdminRequest;
import iseng.cafe.pos.models.admin.AdminResponse;
import iseng.cafe.pos.models.admin.AdminSearch;
import iseng.cafe.pos.services.AdminService;
import iseng.cafe.pos.services.AdminTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admins")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminTypeService adminTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<AdminResponse> add(
            @RequestBody @Valid AdminRequest model){
        Admin entity = modelMapper.map(model, Admin.class);

        AdminType adminType = adminTypeService.findById(model.getAdminTypeId());
        entity.setAdminType(adminType);

        entity = adminService.save(entity);

        AdminResponse data = modelMapper.map(entity, AdminResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<AdminResponse> edit(
            @PathVariable Integer id,
            @RequestBody AdminRequest model){

        Admin entity = adminService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }

        AdminType adminType = adminTypeService.findById(model.getAdminTypeId());
        entity.setAdminType(adminType);

        modelMapper.map(model, entity);
        entity = adminService.save(entity);

        AdminResponse data = modelMapper.map(entity, AdminResponse.class);
        return ResponseMessage.success(data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<AdminResponse> removeById(@PathVariable Integer id){

        Admin entity = adminService.removeById(id);

        if(entity == null){
            throw new EntityNotFoundException();
        }
        AdminResponse data = modelMapper.map(entity, AdminResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<AdminResponse> findById(@PathVariable Integer id){
        Admin entity = adminService.findById(id);
        if(entity == null){
            throw new EntityNotFoundException();
        }
        AdminResponse data = modelMapper.map(entity, AdminResponse.class);
        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<PagedList<AdminResponse>> findAll(
            @Valid AdminSearch model
    ){
        Admin search = modelMapper.map(model, Admin.class);

        Page<Admin> entityPage = adminService.findAll(search,
                model.getPage(), model.getSize(), model.getSort());

        List<Admin> entities = entityPage.toList();

        List<AdminResponse> models = entities.stream()
                .map(e -> modelMapper.map(e, AdminResponse.class))
                .collect(Collectors.toList());

        PagedList<AdminResponse> data = new PagedList(models,
                entityPage.getNumber(),
                entityPage.getSize(),
                entityPage.getTotalElements());

        return ResponseMessage.success(data);
    }

}
