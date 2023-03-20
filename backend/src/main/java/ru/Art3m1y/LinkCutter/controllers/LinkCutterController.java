package ru.Art3m1y.LinkCutter.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.Art3m1y.LinkCutter.dtoes.LinkDto;
import ru.Art3m1y.LinkCutter.mappers.LinkMapper;
import ru.Art3m1y.LinkCutter.models.Link;
import ru.Art3m1y.LinkCutter.services.LinkCutterService;
import ru.Art3m1y.LinkCutter.exceptions.ErrorResponse;
import ru.Art3m1y.LinkCutter.exceptions.LinkException;

import java.net.URI;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@EnableCaching
public class LinkCutterController {
    public final LinkCutterService linkCutterService;

    @PostMapping("/cut")
    public ResponseEntity<String> cutLink(@Valid @RequestBody LinkDto linkDto, BindingResult bindingResult) {
        validateRequestBody(bindingResult);

        Link link = LinkMapper.INSTANCE.fromLinkDtoToLink(linkDto);

        String shortenedLink = linkCutterService.cutLink(link, linkDto.getHoursInExpires());

        return ResponseEntity.ok().body(shortenedLink);
    }

    @GetMapping("/{shortenedURI}")
    public ResponseEntity redirectToFullLink(@PathVariable String shortenedURI) {
        Link link = linkCutterService.getFullLinkByShortenedURISaveInCache(shortenedURI);

        if (link.getExpiredAt().before(new Date())) {
            linkCutterService.deleteLink(shortenedURI);
            link = linkCutterService.getFullLinkByShortenedURIUpdateCache(shortenedURI);
        }

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).location(URI.create(link.getFullLink())).build();
    }


    private void validateRequestBody(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));
            throw new RuntimeException(errors.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(LinkException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
