# Run a Lightweight Webhook Listener on Your Server

This example shows how to create a minimal Flask-based webhook listener that receives Docker Hub webhooks and triggers a Docker Swarm service update when a new image is pushed:

1. create a small webhook handler:

    ```bash
    mkdir docker-webhook-listener && cd docker-webhook-listener
    python3 -m venv venv && source venv/bin/activate
    pip install Flask
    ```

2. create a Python script **app.py:**

    ```python
    from flask import Flask, request
    import os
    
    app = Flask(__name__)
    
    @app.route('/webhook', methods=['POST'])
    def webhook():
        # Pull new image and update service
        os.system("docker service update --image yourdockerhubuser/yourimage:latest your_service_name")
        return "OK", 200
    
    if __name__ == '__main__':
        app.run(host='0.0.0.0', port=8080)
    ```

   **we're using the `:latest` tag for simplicity, but in a real-world setup, it's better to use explicit version tags to ensure predictable deployments and avoid ambiguity.**

3. start the webhook listener by running the Flask app:

    ```bash
    FLASK_APP=app.py flask run --host=0.0.0.0 --port=8080
    ```
