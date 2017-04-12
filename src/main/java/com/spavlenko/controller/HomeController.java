package com.spavlenko.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spavlenko.dto.ExchangeRateDto;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = { "/currency-converter" }, method = RequestMethod.GET)
    public String currencyConverter(Model model, HttpServletRequest request)
            throws ClientProtocolException, IOException {
        // TODO get real user id
        Long curentUserId = 2L;
        
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
    public String login(Model model, String error, String logout) {
        // TODO to implement
        if (error != null)
            model.addAttribute("error", "Your username or password are invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

}
