package com.ogoma.hr_core.controllers;

import com.ogoma.hr_core.boundaries.access_control.entities.User;
import com.ogoma.hr_core.models.ResponseModel;
import com.ogoma.hr_core.models.requests.ForgotPasswordRequest;
import com.ogoma.hr_core.models.requests.PasswordResetRequest;
import com.ogoma.hr_core.models.requests.UserRegistrationModel;
import com.ogoma.hr_core.boundaries.access_control.services.UserService;
import com.ogoma.hr_core.utils.ErrorConverter;
import com.ogoma.hr_core.validators.ForgotPasswordRequestValidator;
import com.ogoma.hr_core.validators.PasswordResetRequestValidator;
import com.ogoma.hr_core.validators.UserRegistrationModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Map;


@Controller
public class AuthController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;
    private final UserRegistrationModelValidator userRegistrationModelValidator;

    private final ForgotPasswordRequestValidator forgotPasswordRequestValidator;

    private final PasswordResetRequestValidator passwordResetRequestValidator;

    public AuthController(UserService userService, UserRegistrationModelValidator userRegistrationModelValidator, ForgotPasswordRequestValidator forgotPasswordRequestValidator, PasswordResetRequestValidator passwordResetRequestValidator) {
        this.userService = userService;
        this.userRegistrationModelValidator = userRegistrationModelValidator;
        this.forgotPasswordRequestValidator = forgotPasswordRequestValidator;
        this.passwordResetRequestValidator = passwordResetRequestValidator;
    }
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "forward:/dashboard";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationModel userRegistrationModel,
                                          BindingResult bindingResult) throws MessagingException {
        userRegistrationModelValidator.validate(userRegistrationModel, bindingResult);
        if (!bindingResult.hasErrors()) {
            User user = this.userService.register(userRegistrationModel);
            logger.debug("user successfully registered,user id %s", user.getId());
            return ResponseEntity.ok("Registration successful,a verification email has been sent to your email, please verify to complete registration");
        }
        Map<String, ArrayList<String>> errors = ErrorConverter.convert(bindingResult);
        return ResponseEntity.badRequest().body(errors);
    }

    @RequestMapping(value = "confirm-registration/{userId}/{token}", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(
            @PathVariable("userId") Long userId,
            @PathVariable("token") String token,
            ModelMap model
    ) {
        model.addAttribute("msg", "Account verification successful");
        model.addAttribute("state", "success");
        return new ModelAndView("redirect:/login", model);
    }

    @RequestMapping(value = "forgot-password", method = RequestMethod.POST)
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest,
                                            BindingResult bindingResult) {
        forgotPasswordRequestValidator.validate(forgotPasswordRequest, bindingResult);
        if (!bindingResult.hasErrors()) {
            this.userService.handleForgotPasswordRequest(forgotPasswordRequest);
            return ResponseEntity.ok("A new password reset link has been sent to your email");
        }
        Map<String, ArrayList<String>> errors = ErrorConverter.convert(bindingResult);
        return ResponseEntity.badRequest().body(errors);
    }

    @RequestMapping(value = "password-reset/{userId}/{token}", method = RequestMethod.GET)
    public String resetPassword(
            @PathVariable("userId") Long userId,
            @PathVariable("token") String token,
            Model model
    ) {
        model.addAttribute("token", token);
        model.addAttribute("userId", userId);
        return "auth/password_reset";
    }

    @RequestMapping(value = "password-reset", method = RequestMethod.POST)
    public ResponseEntity<?> handlePasswordReset(
            @RequestBody PasswordResetRequest passwordResetRequest,
            BindingResult bindingResult
    ) {
        passwordResetRequestValidator.validate(passwordResetRequest, bindingResult);
        if (!bindingResult.hasErrors()) {
            ResponseModel responseModel = userService.resetUserPassword(passwordResetRequest);
            return ResponseEntity.ok(responseModel);
        }
        Map<String, ArrayList<String>> errors = ErrorConverter.convert(bindingResult);
        return ResponseEntity.badRequest().body(errors);
    }
}
