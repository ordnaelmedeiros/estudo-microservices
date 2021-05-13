mvn -f videos clean verify sonar:sonar \
  -Dsonar.projectKey=ms-videos \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=a919102c8248864704809cb21c153e786df47b9f