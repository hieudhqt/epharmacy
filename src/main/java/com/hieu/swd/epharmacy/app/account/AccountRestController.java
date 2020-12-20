package com.hieu.swd.epharmacy.app.account;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.hieu.swd.epharmacy.app.PageResult;
import com.hieu.swd.epharmacy.app.account.request.AccountGeneralRequest;
import com.hieu.swd.epharmacy.app.account.request.GoogleLoginRequest;
import com.hieu.swd.epharmacy.app.account.request.LoginRequest;
import com.hieu.swd.epharmacy.app.account.response.AccountGeneralResponse;
import com.hieu.swd.epharmacy.app.account.response.LoginResponse;
import com.hieu.swd.epharmacy.app.prescription.PrescriptionService;
import com.hieu.swd.epharmacy.app.prescriptiondetails.PrescriptionDetailsService;
import com.hieu.swd.epharmacy.exception.ObjectExistedException;
import com.hieu.swd.epharmacy.exception.ObjectNotFoundException;
import com.hieu.swd.epharmacy.notification.PushNotificationRequest;
import com.hieu.swd.epharmacy.notification.PushNotificationService;
import com.hieu.swd.epharmacy.security.auth.CustomUserDetails;
import com.hieu.swd.epharmacy.security.jwt.JwtProvider;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1")
@Api(tags = "Account API")
@Slf4j
public class AccountRestController {

    @Value("${swd.oauthClientId}")
    private String oauthClientId;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PushNotificationService pushNotificationService;

    private PushNotificationRequest pushNotificationRequest = new PushNotificationRequest()
            .setToken("eF4rfp21SB2zlXqfH3uQax:APA91bGwSXOzCaxGKhJ8Anbrn4uLPuOZMiqtTCBV5OVEKtCzYUHB2PD2NxmXy-R49914ku-uH59oHQLsrNIkoYetBYV7I7EsZsy_V6NMOG3dIAe2SEo4cwsMMsBNKnqNs2R4n2nhc14I");

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    @ApiOperation(value = "Log In")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        Authentication authentication = authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        final String jwt = jwtProvider.generateToken(userDetails);

        return new LoginResponse(userDetails.getUsername(), jwt);
    }

    @PostMapping(value = "/tokenlogin")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Google Token Log In")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<?> tokenLogIn(@Valid @RequestBody GoogleLoginRequest googleLoginRequest) throws GeneralSecurityException, IOException {
        String idToken = googleLoginRequest.getIdToken();
        final NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
        final JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(oauthClientId))
                .build();

        final GoogleIdToken googleIdToken = verifier.verify(idToken);

        if (googleIdToken == null) {
            return new ResponseEntity<>("ID Token is invalid or expired", HttpStatus.UNAUTHORIZED);
        }

        final GoogleIdToken.Payload payload = googleIdToken.getPayload();
        final Boolean isEmailVerified = payload.getEmailVerified();

        if (isEmailVerified) {
            String email = payload.getEmail();
            String username = email.split("@")[0];

            try {
                if (!accountService.isEmailExisted(email)) {
                    String name = (String) payload.get("name");

                    boolean isAdded = accountService.insertGoogleAccount(new AccountGeneralRequest()
                            .setUsername(username)
                            .setPassword(null)
                            .setName(name)
                            .setEmail(email)
                            .setBirthDate(null)
                            .setGender(null)
                            .setAddress(null));

                    if (isAdded) {
                        pushNotificationRequest.setTitle("Register with Google account successfully")
                                .setMessage("Your account with email " + email + " has been created successfully\nYou are now able to use our app");
                        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
                    }
                }
            } catch (Exception ex) {
                log.error("Add new Google Account failed", ex);
                return new ResponseEntity<>("Can't add this Google Account", HttpStatus.BAD_REQUEST);
            }

            final CustomUserDetails userDetails = new CustomUserDetails(new Account().setUsername(username));
            final String jwt = jwtProvider.generateToken(userDetails);

            return ResponseEntity.ok(new LoginResponse(username, jwt));

        } else {
            return new ResponseEntity<>("Email isn't verified", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Sign Up")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Conflict"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void registerUser(@Valid @RequestBody AccountGeneralRequest signUpRequest) throws Exception {
        if (accountService.isUsernameExisted(signUpRequest.getUsername())) {
            throw new ObjectExistedException(signUpRequest.getUsername() + " is already taken");
        }

        if (accountService.isEmailExisted(signUpRequest.getEmail())) {
            throw new ObjectExistedException(signUpRequest.getEmail() + " has been used");
        }

        boolean isAdded = accountService.insert(signUpRequest);

        if (!isAdded) {
            throw new RuntimeException("Registered process failed");
        }

        pushNotificationRequest.setTitle("Register successfully")
                .setMessage("You are now able to use our app");
        pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
    }

    @GetMapping("/accounts")
    @ResponseBody
    @ApiOperation(value = "Get All User")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "page", value = "Page number start from 1", dataType = "integer", examples = @Example(@ExampleProperty("1")), paramType = "query"),
            @ApiImplicitParam(name = "size", value = "Maximum items in a page", dataType = "integer", examples = @Example(@ExampleProperty("100")), paramType = "query")
    })
    public PageResult findAll(@RequestParam(name = "page", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "size", defaultValue = "100") int pageSize) throws Exception {
        return accountService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/accounts/{username}")
    @ResponseBody
    @ApiOperation(value = "Find User By Username")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public AccountGeneralResponse findByUsername(@PathVariable String username) throws Exception {
        AccountGeneralResponse accountResponse = accountService.findByUsername(username);
        return accountResponse;
    }

    @PutMapping("/accounts")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update User")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void updateUser(@Valid @RequestBody AccountGeneralRequest updateRequest) throws Exception {
        boolean isUsernameExisted = accountService.isUsernameExisted(updateRequest.getUsername());
        if (!isUsernameExisted) {
            throw new ObjectNotFoundException("Username is not found - " + updateRequest.getUsername());
        }
        boolean isUpdated = accountService.update(updateRequest);
        if (!isUpdated) {
            throw new RuntimeException("Error updating user");
        }
    }

    @DeleteMapping("/accounts/{username}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete User")
    @ApiResponses(value = {@ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public void deleteUser(@PathVariable String username) throws Exception {
        boolean isUsernameExisted = accountService.isUsernameExisted(username);
        if (!isUsernameExisted) {
            throw new ObjectNotFoundException("Username is not found - " + username);
        }
        boolean isDeleted = accountService.deleteByUsername(username);
        if (!isDeleted) {
            throw new RuntimeException("Error deleting user");
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (DisabledException ex) {
            throw new Exception("USER_DISABLED", ex);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username/password", ex);
        }
        return authentication;
    }
}
