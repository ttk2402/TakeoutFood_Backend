package com.kientran.shipping_service.imgur;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ImgurService {

    private final String clientId = "6cb5947e1c85407";

    private final WebClient webClient;

    public ImgurService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.imgur.com/3/upload").build();
    }

    public String uploadImage(MultipartFile file) {
        try {
            ImgurResponse response = webClient.post()
                    .header(HttpHeaders.AUTHORIZATION, "Client-ID " + clientId)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData("image", file.getResource()))
                    .retrieve()
                    .bodyToMono(ImgurResponse.class)
                    .block();
            return response != null && response.getData() != null ? response.getData().getLink() : "Error: Link not found";
        } catch (WebClientResponseException e) {
            return "Error uploading image: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}