package com.hieu.swd.epharmacy.app.unit;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.exception.ObjectExistedException;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Unit API")
@Slf4j
public class UnitRestController {

    @Autowired
    private UnitService unitService;

    @Autowired
    public UnitRestController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping("/units")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get All Units")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error") })
    @ApiImplicitParams( value = {
            @ApiImplicitParam(name = "page", value = "Page number start from 1", dataType = "integer", examples = @Example(@ExampleProperty("1")), paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum items in a page", dataType = "integer", examples = @Example(@ExampleProperty("100")), paramType = "query")
    })
    public PageResult findAll(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "size", defaultValue = "100") int pageSize) throws Exception {
        return unitService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/units/{unitId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get Unit By ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public UnitResponse findUnitById(@PathVariable String unitId) throws Exception {
        return unitService.findUnitById(unitId);
    }

    @PostMapping("/units")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Add Unit")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void insert(@Valid @RequestBody UnitRequest unitRequest) throws Exception {
        if (unitService.isUnitExisted(unitRequest.getId())) {
            throw new ObjectExistedException(unitRequest.getName() + " is already taken");
        }

        boolean isAdded = unitService.add(unitRequest);

        if (!isAdded) {
            throw new RuntimeException("Error adding unit");
        }
    }

    @PutMapping("/units")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Unit")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void update(@Valid @RequestBody UnitRequest unitRequest) throws Exception {
        boolean isExisted = unitService.isUnitExisted(unitRequest.getId());
        if (!isExisted) {
            throw new ObjectNotFoundException("Unit not found - " + unitRequest.getId());
        }
        boolean isUpdated = unitService.update(unitRequest);
        if (!isUpdated) {
            throw new RuntimeException("Error updating unit");
        }
    }

    @DeleteMapping("/units/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Unit")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void delete(@PathVariable String id) throws Exception {
        boolean isExisted = unitService.isUnitExisted(id);
        if (!isExisted) {
            throw new ObjectNotFoundException("Unit " + id + " isn't existed");
        }

        boolean isDeleted = unitService.deleteById(id);
        if (!isDeleted) {
            throw new RuntimeException("Error deleting unit");
        }
    }

}
