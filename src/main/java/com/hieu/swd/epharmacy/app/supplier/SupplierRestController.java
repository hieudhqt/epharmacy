package com.hieu.swd.epharmacy.app.supplier;

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
@Api(tags = "Supplier API")
@Slf4j
public class SupplierRestController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    public SupplierRestController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get All Suppliers")
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
        return supplierService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/suppliers/{supplierId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get Supplier By ID")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public SupplierResponse findSupplierById(@PathVariable String supplierId) throws Exception {
        return supplierService.findSupplierById(supplierId);
    }

    @PostMapping("/suppliers")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Add Supplier")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void insert(@Valid @RequestBody SupplierRequest supplierRequest) throws Exception {
        if (supplierService.isSupplierExisted(supplierRequest.getId())) {
            throw new ObjectExistedException(supplierRequest.getName() + " is already taken");
        }

        boolean isAdded = supplierService.add(supplierRequest);

        if (!isAdded) {
            throw new RuntimeException("Error adding supplier");
        }
    }

    @PutMapping("/suppliers")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update Supplier")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void update(@Valid @RequestBody SupplierRequest supplierRequest) throws Exception {
        boolean isExisted = supplierService.isSupplierExisted(supplierRequest.getId());
        if (!isExisted) {
            throw new ObjectNotFoundException("Supplier not found - " + supplierRequest.getId());
        }
        boolean isUpdated = supplierService.update(supplierRequest);
        if (!isUpdated) {
            throw new RuntimeException("Error updating unit");
        }
    }

    @DeleteMapping("/suppliers/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Supplier")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void delete(@PathVariable String id) throws Exception {
        boolean isExisted = supplierService.isSupplierExisted(id);
        if (!isExisted) {
            throw new ObjectNotFoundException("Supplier " + id + " isn't existed");
        }

        boolean isDeleted = supplierService.deleteById(id);
        if (!isDeleted) {
            throw new RuntimeException("Error deleting supplier");
        }
    }
}
