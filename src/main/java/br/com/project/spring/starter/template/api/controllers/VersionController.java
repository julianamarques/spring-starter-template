package br.com.project.spring.starter.template.api.controllers;

import br.com.project.spring.starter.template.api.dtos.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/version")
public class VersionController {
    @Value("${application.version}")
    private String version;

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE)
    public Response<String> getVersion() {
        return new Response<>(version);
    }
}
