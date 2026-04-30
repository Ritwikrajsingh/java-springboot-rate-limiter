# java-springboot-rate-limiter

Implementation of **Fixed Window** rate-limiting algorithm using Redis in a Spring Boot filter. It identifies clients by IP address, stores request counts with a 1-minute TTL, increments on each request, and blocks requests with HTTP 429 once the limit (e.g., 5 requests/minute) is exceeded.
