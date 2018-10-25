# ESP8266 Web server

Code that serves simple rest api on ESP8266 digital circuit


## Rest API

### Fetching current state
```
GET /states/current
```
Sample response
```
{
    "state": "off"
}
```

### Adding new state change request
```
POST /states
```
with body
```
{
	"state":"on"
}
```
