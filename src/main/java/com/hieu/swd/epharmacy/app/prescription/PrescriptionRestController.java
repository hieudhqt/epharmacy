package com.hieu.swd.epharmacy.app.prescription;

import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetailsRequest;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetailsService;
import com.hieu.swd.epharmacy.exception.ObjectExistedException;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import com.hieu.swd.epharmacy.notification.PushNotificationRequest;
import com.hieu.swd.epharmacy.notification.PushNotificationService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Prescription API")
@Slf4j
public class PrescriptionRestController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionDetailsService prescriptionDetailsService;

    @Autowired
    public PrescriptionRestController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @Autowired
    private PushNotificationService pushNotificationService;

    private PushNotificationRequest pushNotificationRequest = new PushNotificationRequest()
            .setToken("eF4rfp21SB2zlXqfH3uQax:APA91bGwSXOzCaxGKhJ8Anbrn4uLPuOZMiqtTCBV5OVEKtCzYUHB2PD2NxmXy-R49914ku-uH59oHQLsrNIkoYetBYV7I7EsZsy_V6NMOG3dIAe2SEo4cwsMMsBNKnqNs2R4n2nhc14I");

    @GetMapping("/prescriptions")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get Prescription By Username")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", value = "Get prescription of username", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "Page number start from 1", dataType = "integer", examples = @Example(@ExampleProperty("1")), paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum items in a page", dataType = "integer", examples = @Example(@ExampleProperty("100")), paramType = "query")
    })
    public PageResult findPrescriptionByUsername(
            @NotBlank @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "size", defaultValue = "100") int pageSize) throws Exception {
        return prescriptionService.findPrescriptionByUsername(username, pageNumber, pageSize);
    }

    @GetMapping("/prescriptions/{presId}")
    @ResponseBody
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Get Prescription Details")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "Page number start from 1", dataType = "integer", examples = @Example(@ExampleProperty("1")), paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum items in a page", dataType = "integer", examples = @Example(@ExampleProperty("100")), paramType = "query")
    })
    public PageResult findPrescriptionDetails(@PathVariable String presId,
                                              @RequestParam(name = "page", defaultValue = "1") int pageNumber,
                                              @RequestParam(name = "size", defaultValue = "100") int pageSize) throws Exception {
        return prescriptionDetailsService.findByPresId(presId, pageNumber, pageSize);
    }

    @PostMapping("/prescriptions")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Add new prescription")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void insert(@Valid @RequestBody PrescriptionRequest prescriptionRequest) throws Exception {
        String presId = prescriptionRequest.getId();

        boolean isExisted = prescriptionService.isPrescriptionExisted(presId);
        if (isExisted) {
            throw new ObjectExistedException(presId + " has been already assigned");
        }

        if (prescriptionService.insert(prescriptionRequest)) {
            List<PrescriptionDetailsRequest> prescriptionDetailsRequestList = prescriptionRequest.getPrescriptionDetailsRequestList();
            for (PrescriptionDetailsRequest prescriptionDetailsRequest : prescriptionDetailsRequestList) {
                prescriptionDetailsService.insert(prescriptionDetailsRequest, presId);
            }

            pushNotificationRequest.setTitle("New prescription added successfully")
                    .setMessage("You've added new prescription\nMake sure you follow doctor's instructions exactly\nFor your health");
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
        } else {
            throw new RuntimeException("Add prescription failed");
        }
    }

    @DeleteMapping("/prescriptions/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete Prescription")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void delete(@PathVariable String id) throws Exception {
        boolean isExisted = prescriptionService.isPrescriptionExisted(id);
        if (!isExisted) {
            throw new ObjectNotFoundException("Prescription " + id + " isn't existed");
        }

        boolean isDeleted = prescriptionService.deleteByPresId(id);
        if (!isDeleted) {
            throw new RuntimeException("Error deleting prescription");
        }
        pushNotificationRequest.setTitle("Your prescription has been deleted")
                .setMessage("You've deleted your prescription\nAre you recovering well ?\nHope so");
        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
    }

}
