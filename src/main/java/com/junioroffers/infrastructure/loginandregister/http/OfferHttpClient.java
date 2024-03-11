package com.junioroffers.infrastructure.loginandregister.http;

import com.junioroffers.domain.offer.OfferFetchable;
import com.junioroffers.domain.offer.dto.JobOfferResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@AllArgsConstructor
@Log4j2
public class OfferHttpClient implements OfferFetchable {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<JobOfferResponse> fetchOffers() {
        log.info("Started fetching offers using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            String urlForService = getUrlForService();
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<JobOfferResponse>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            final List<JobOfferResponse> body = response.getBody();
            if (body == null) {
                log.error("Response body was null");
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            log.info("Success Response Body Returned: " + body);
            return body;
        } catch (ResourceAccessException exception) {
            log.error("Error while fetching offers using http client: " + exception.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUrlForService() {
        return uri + ":" + port + "/offers";
    }
}
