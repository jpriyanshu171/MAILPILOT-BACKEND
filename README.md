# MAILPILOT-BACKEND

Spring Boot backend service for AI-assisted email reply generation. Provides a REST API to generate professional, casual, friendly, or concise email responses using AI.

## Key Features & Benefits

- **AI-Powered Reply Generation:** Leverages AI to generate diverse email replies based on user-defined styles.
- **Multiple Style Options:** Supports generating replies in professional, casual, friendly, and concise tones.
- **REST API:**  Provides a well-defined REST API for easy integration with frontend applications or other services.
- **Scalable Architecture:** Built with Spring Boot, ensuring scalability and maintainability.
- **Dockerized Deployment:**  Easy deployment via Docker containerization.

## Prerequisites & Dependencies

- **Java Development Kit (JDK) 21:** Required for compiling and running the application.
- **Maven:**  Used for dependency management and building the project.
- **Docker (Optional):** Required for containerized deployment.
- **An active OpenAI API key (or equivalent):**  For AI email generation functionality (details in Configuration Options).

## Installation & Setup Instructions

1. **Clone the repository:**

   ```bash
   git clone https://github.com/jpriyanshu171/MAILPILOT-BACKEND.git
   cd MAILPILOT-BACKEND
   ```

2. **Build the project using Maven:**

   ```bash
   ./mvnw clean install
   ```

3. **Configure API Key and other properties:**  Refer to the Configuration Options section.

4. **Run the application:**

   ```bash
   ./mvnw spring-boot:run
   ```

   Alternatively, you can run the packaged JAR file:

   ```bash
   java -jar target/mailpilot-0.0.1-SNAPSHOT.jar
   ```

5. **Docker (Optional):**

   - Build the Docker image:

     ```bash
     docker build -t mailpilot-backend .
     ```

   - Run the Docker container:

     ```bash
     docker run -p 8080:8080 -e OPENAI_API_KEY=<YOUR_OPENAI_API_KEY> mailpilot-backend
     ```

## Usage Examples & API Documentation

The backend provides a REST API endpoint for generating email replies.

**Endpoint:** `POST /generateEmail`

**Request Body (JSON):**

```json
{
  "prompt": "Reply to this email: 'Hi, thanks for your interest in our product...'",
  "style": "professional"
}
```

**Request Body Parameters:**

- `prompt`: The email content to generate a reply for (String).
- `style`: The desired tone for the generated reply ("professional", "casual", "friendly", "concise"). (String)

**Response (JSON):**

```json
{
  "reply": "Dear [Recipient Name],\n\nThank you for your interest in our product. We appreciate your inquiry and are happy to provide you with more information...\n\nSincerely,\n[Your Name]"
}
```

**Example using `curl`:**

```bash
curl -X POST \
  http://localhost:8080/generateEmail \
  -H 'Content-Type: application/json' \
  -d '{
    "prompt": "Reply to this email: \'Hi, thanks for your interest in our product...\'",
    "style": "professional"
  }'
```

## Configuration Options

The application can be configured using the `application.properties` file located in `src/main/resources/`.

**Important properties:**

- `openai.api.key`: Your OpenAI API key (or the key for your AI service). This is crucial for the AI-powered reply generation.  It can also be set using an environment variable `OPENAI_API_KEY` (preferred for security).

- `server.port`:  The port the application runs on (default: 8080).

**Example `application.properties`:**

```properties
server.port=8080
# Note:  Do NOT store your API key directly in this file for production. Use environment variables instead.
# openai.api.key=YOUR_OPENAI_API_KEY
```

Using Environment Variables (recommended for sensitive data like API Keys):

```bash
export OPENAI_API_KEY="YOUR_OPENAI_API_KEY"
```

## Contributing Guidelines

We welcome contributions to improve the project! Please follow these guidelines:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Implement your changes and write tests.
4.  Ensure all tests pass.
5.  Submit a pull request with a clear description of your changes.

## License Information

This project does not currently have a specified license. All rights are reserved by the owner.

## Acknowledgments

- This project leverages the Spring Boot framework for its backend architecture.
- The AI-powered email reply generation is made possible by Gemini API.
