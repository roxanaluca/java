import requests
import json

url = 'http://localhost:8081/lab1-2024-1.0-SNAPSHOT/home'
data = {'numVertices': 10, 'numEdges': 5}

response = requests.post(url, data = json.dumps(data))

if response.status_code == 200:
    print(response.text)
else:
    print(f"Error: {response.status_code}")