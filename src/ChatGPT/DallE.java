package ChatGPT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DallE {
    public static String getImage(String textualDescription) throws Exception {
        String apiKey = "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";
        String apiUrl = "https://api.openai.com/v1/images/generations";
        //String textualDescription = "astronaut flying on mars";

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString("{\"prompt\": \"" + textualDescription + "\"}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        //int statusCode = response.statusCode();
        String responseBody = response.body();

        //System.out.println("Status Code: " + statusCode);
        System.out.println("Response Body: " + responseBody);

        // Extract image URL from the actual API response
        String imageUrl = extractImageUrl(responseBody);
        System.out.println("Generated Image URL: " + imageUrl);
        return imageUrl;
    }

    public static String extractImageUrl(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        // Check if the top-level node is not null
        if (jsonNode != null && jsonNode.has("data")) {
            // Navigate into the "data" array
            JsonNode dataArray = jsonNode.get("data");

            // Check if the "data" array is not null and not empty
            if (dataArray != null && dataArray.isArray() && dataArray.size() > 0) {
                // Get the first element of the "data" array
                JsonNode firstDataElement = dataArray.get(0);

                // Replace "url" with the actual field name in the API response
                JsonNode imageUrlNode = firstDataElement.get("url");

                // Check if the field is not null and is a text node
                if (imageUrlNode != null && imageUrlNode.isTextual()) {
                    return imageUrlNode.asText();
                } else {
                    throw new RuntimeException("Image URL not found or not a valid text node in the API response");
                }
            } else {
                throw new RuntimeException("No data or empty data array in the API response");
            }
        } else {
            throw new RuntimeException("Invalid JSON response: Top-level node is null or missing 'data'");
        }
    }



}
