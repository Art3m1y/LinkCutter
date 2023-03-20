package ru.Art3m1y.LinkCutter.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.Art3m1y.LinkCutter.models.Link;
import ru.Art3m1y.LinkCutter.repositories.LinkRepo;
import ru.Art3m1y.LinkCutter.exceptions.LinkException;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "links")
public class LinkCutterService {
    private final LinkRepo linkRepo;
    private final ConverterService converterService;

    @Value("${default-url}")
    private String defaultUrl;

    @Transactional
    public String cutLink(Link link, int hoursInExpires) {
        link.setExpiredAt(Date.from(ZonedDateTime.now().plusHours(hoursInExpires).toInstant()));

        linkRepo.save(link);

        String shortenedURI = converterService.encodeId(link.getId());

        return defaultUrl + shortenedURI;
    }

    @Transactional(noRollbackFor = LinkException.class)
    @Cacheable(key = "#shortenedURI")
    public Link getFullLinkByShortenedURISaveInCache(String shortenedURI) {
        return getFullLinkShortenedURI(shortenedURI);
    }

    @Transactional(noRollbackFor = LinkException.class)
    @CachePut(key = "#shortenedURI")
    public Link getFullLinkByShortenedURIUpdateCache(String shortenedURI) {
        return getFullLinkShortenedURI(shortenedURI);
    }

    private Link getFullLinkShortenedURI(String shortenedURI) {
        long id = converterService.decodeShortenedURI(shortenedURI);

        Link link = linkRepo.findById(id).orElseThrow(() -> new LinkException("Не существует такой сокращенной ссылки"));

        if (link.getExpiredAt().before(new Date())) {
            linkRepo.delete(link);
            throw new LinkException("Время действия сокращенной ссылки истекло");
        }

        return link;
    }

    @CacheEvict(key = "#shortenedURI")
    public void deleteLink(String shortenedURI) {}
}
