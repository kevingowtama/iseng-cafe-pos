package iseng.cafe.pos.controllers.jdbc;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import iseng.cafe.pos.entities.SnackCategory;
import iseng.cafe.pos.exceptions.EntityNotFoundException;
import iseng.cafe.pos.models.ResponseMessage;
import iseng.cafe.pos.models.snackCategory.SnackCategoryRequest;
import iseng.cafe.pos.services.jdbc.SnackCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/snack-categories")
public class SnackCategoryController {
    @Autowired
    private SnackCategoryService snackCategoryService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "500", description = "Internal server error."),
            @ApiResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ResponseMessage.class))))
    })

    @PostMapping
    public ResponseMessage<SnackCategory> save(@RequestBody @Valid SnackCategoryRequest model){
        SnackCategory snackCategory = new SnackCategory();

        snackCategory.setName(model.getName());

        SnackCategory data = snackCategoryService.save(snackCategory);
        return ResponseMessage.success("New data has been added", data);
    }

    @PutMapping("/{id}")
    public ResponseMessage<SnackCategory> update(@RequestBody @Valid SnackCategoryRequest model, @PathVariable Integer id){
        SnackCategory snackCategory = snackCategoryService.findById(id);

        if(snackCategory.getId() == null){
            throw new EntityNotFoundException();
        }
        snackCategory.setName(model.getName());

        SnackCategory data = snackCategoryService.save(snackCategory);
        return ResponseMessage.success("Data has been updated", data);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage<Boolean> remove(@PathVariable Integer id){
        Boolean data = snackCategoryService.remove(id);

        return ResponseMessage.success("Data has been deleted", data);
    }

    @GetMapping("/{id}")
    public ResponseMessage<SnackCategory> findById(@PathVariable @Valid Integer id){
        SnackCategory data = snackCategoryService.findById(id);

        return ResponseMessage.success("Get data success", data);
    }

    @GetMapping
    public ResponseMessage<List<SnackCategory>> findAll(){
        List<SnackCategory> data = snackCategoryService.findAll();

        return ResponseMessage.success("Get list of data success", data);
    }
}
