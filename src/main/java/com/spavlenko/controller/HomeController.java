package com.spavlenko.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spavlenko.controller.mapper.UserMapper;
import com.spavlenko.controller.request.UserRequest;
import com.spavlenko.domain.User;
import com.spavlenko.dto.ExchangeRateDto;
import com.spavlenko.dto.UserDto;
import com.spavlenko.exception.ConstraintsViolationException;
import com.spavlenko.exception.EntityNotFoundException;
import com.spavlenko.service.SecurityService;
import com.spavlenko.service.UserService;
import com.spavlenko.service.UserValidator;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String home() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = { "/currency-converter" }, method = RequestMethod.GET)
    public String currencyConverter(Model model, HttpServletRequest request)
            throws ClientProtocolException, IOException, EntityNotFoundException {

        User user = securityService.getAuthenticatedUser();
        Long curentUserId = user.getId();

        UriComponents url = ServletUriComponentsBuilder.fromServletMapping(request).path("/v1/rates/")
                .path(curentUserId.toString()).build();

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", request.getSession().getId());
        cookie.setDomain(url.getHost());
        cookieStore.addCookie(cookie);

        HttpContext httpContext = new BasicHttpContext();
        httpContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(new HttpGet(url.toString()), httpContext);

        ObjectMapper mapper = new ObjectMapper();
        List<ExchangeRateDto> exchangeRateList = mapper.readValue(response.getEntity().getContent(), List.class);

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
            BindingResult bindingResult) throws ConstraintsViolationException, EntityNotFoundException {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.create(userMapper.toUser(user));
        securityService.login(user.getUserName(), user.getPassword());

        return "redirect:/currency-converter";
    }

}
