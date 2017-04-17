package com.spavlenko.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.spavlenko.controller.mapper.UserMapper;
import com.spavlenko.controller.request.UserRequest;
import com.spavlenko.domain.User;
import com.spavlenko.dto.ExchangeRateDto;
import com.spavlenko.dto.UserDto;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.exception.EntityNotFoundException;
import com.spavlenko.service.SecurityService;
import com.spavlenko.service.UserValidator;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserMapper userMapper;

    private final static RestTemplate REST_TEMPLATE = new RestTemplate();

    @RequestMapping("/")
    public String home() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = { "/currency-converter" }, method = RequestMethod.GET)
    public String currencyConverter(Model model, HttpServletRequest request) throws EntityNotFoundException {
        User authenticatedUser = securityService.getAuthenticatedUser();
        Long curentUserId = Optional.ofNullable(authenticatedUser).map(User::getId).orElse(0L);

        UriComponents url = ServletUriComponentsBuilder.fromServletMapping(request).path("/v1/rates/")
                .path(curentUserId.toString()).build();
        HttpEntity<Void> requestEntity = buildHttpEntityWithSession(request);

        ResponseEntity<List> response = REST_TEMPLATE.exchange(url.toString(), HttpMethod.GET, requestEntity,
                List.class);
        List<ExchangeRateDto> exchangeRateList = response.getBody();

        model.addAttribute("recentExchanges", exchangeRateList);
        return "currency-converter";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout) {
        Optional.ofNullable(error)
                .ifPresent(t -> model.addAttribute("error", "Your username or password are invalid."));
        Optional.ofNullable(logout)
                .ifPresent(t -> model.addAttribute("message", "You have been logged out successfully."));

        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("user", new UserRequest());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("user") UserRequest user, @RequestParam String passwordConfirm,
            BindingResult bindingResult, HttpServletRequest request)
            throws ConstraintsViolationException, EntityNotFoundException {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        UriComponents url = ServletUriComponentsBuilder.fromServletMapping(request).path("/v1/users").build();

        REST_TEMPLATE.postForObject(url.toString(), userMapper.toUserDto(user), UserDto.class);
        securityService.login(user.getUserName(), user.getPassword());

        return "redirect:/currency-converter";
    }

    private HttpEntity<Void> buildHttpEntityWithSession(HttpServletRequest request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", "JSESSIONID=" + request.getSession().getId());
        HttpEntity<Void> requestEntity = new HttpEntity<>(null, requestHeaders);
        return requestEntity;
    }

}
