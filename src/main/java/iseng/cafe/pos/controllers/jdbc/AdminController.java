package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.Admin;
import iseng.cafe.pos.entities.AdminType;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.admin.AdminRequest;
import iseng.cafe.pos.services.jdbc.AdminService;
import iseng.cafe.pos.services.jdbc.AdminTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/jdbc/admins")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminTypeService adminTypeService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<Admin> save(@RequestBody @Valid AdminRequest model){
        Admin admin = new Admin();

        admin.setName(model.getName());
        admin.setEmail(model.getEmail());
        admin.setUsername(model.getUsername());
        admin.setPassword(model.getPassword());

        AdminType adminType = adminTypeService.findById(model.getAdminTypeId());
        admin.setAdminType(adminType);

        Admin data = adminService.save(admin);
        return ResponseMessage.success("New data has been added", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<Admin> findById(@PathVariable @Valid Integer id){
        Admin data = adminService.findById(id);

        return ResponseMessage.success(data);
    }

    @GetMapping
    public ResponseMessage<List<Admin>> findAll(){
        List<Admin> data = adminService.findAll();

        return ResponseMessage.success(data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<Admin> update(@RequestBody @Valid AdminRequest model, @PathVariable Integer id){
        Admin admin = adminService.findById(id);
        System.out.println(admin.getId());
        if(admin.getId() == null){
            throw new EntityNotFoundException();
        }
        admin.setName(model.getName());
        admin.setEmail(model.getEmail());
        admin.setUsername(model.getUsername());
        admin.setPassword(model.getPassword());

        AdminType adminType = adminTypeService.findById(model.getAdminTypeId());
        admin.setAdminType(adminType);

        Admin data = adminService.save(admin);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = adminService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }
}
