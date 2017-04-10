package com.spavlenko.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import com.spavlenko.domain.User;
import com.spavlenko.dto.ExchangeRateDto;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class HomeController {

    private RestTemplate template = new RestTemplate();

    @RequestMapping("/")
    public String home() {
        return "redirect:/currency-converter";
    }

    @RequestMapping(value = { "/currency-converter" }, method = RequestMethod.GET)
    public String currencyConverter(Model model, HttpServletRequest request) {
        User currentUser = null; // TODO
        Long curentUserId = 1L;
        UriComponents url = ServletUriComponentsBuilder.fromServletMapping(request).path("/v1/rates/")
                .path(curentUserId.toString()).build();
        List<ExchangeRateDto> exchangeRateList = template.getForObject(url.toString(), List.class);

        // TODO to implement
        return "currency-converter";
    }

}
