package com.mailpilot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class EmailGeneratorService {

        private final WebClient webClient;

        @Value("${gemini.api.url}")
        private String geminiApiUrl;
        @Value("${gemini.api.key}")
        private String geminiApiKey;

        public EmailGeneratorService(WebClient.Builder webClientBuilder) {
                this.webClient = webClientBuilder.build();
        }

        public String generateEmailReply(EmailRequest emailRequest) {
                // prompt which will be going to gemini api

                String prompt = buildPrompt(emailRequest);

                Map<String, Object> requestBody = Map.of(
                        "contents", new Object[]{
                                Map.of("parts", new Object[]{
                                        Map.of("text", prompt)
                                })
                        }
                );
                String response = webClient.post()
                        .uri(geminiApiUrl + geminiApiKey)
                        .header("Content-type", "application/json")
                        .bodyValue(requestBody)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                return extractResponseContent(response);
        }

        private String extractResponseContent(String response) {
                try {
                        ObjectMapper mapper = new ObjectMapper();
                        JsonNode rootNode = mapper.readTree(response);

                        return rootNode.path("candidates")
                                .get(0)
                                .path("content")
                                .path("parts")
                                .get(0)
                                .path("text")
                                .asText();
                } catch (Exception e) {
                        return "Sorry, something went wrong while generating the reply. Please try again.";
                }
        }

        private String buildPrompt(EmailRequest emailRequest) {
                StringBuilder prompt = new StringBuilder();
                prompt.append("You are an AI assistant that generates professional email replies. Generate a professional reply for the following email without generating the subject! Keep it natural and human!");


                if (emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
                        prompt.append("Use a ").append(emailRequest.getTone()).append(" tone.");
                }
                prompt.append("\nOriginal email: \n").append(emailRequest.getEmailContent());
                return prompt.toString();
        }
}
