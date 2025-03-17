from locust import HttpUser, task, between
from random import randint

class RateLimiterUser(HttpUser):
    wait_time = between(.5, 1.5)

    @task
    def hit_endpoint(self):
        fake_ip = f"192.168.0.{randint(1,100)}"
        self.client.get(
            "/actuator",
            headers = {"X-Forwarded-For": fake_ip}
        )
        