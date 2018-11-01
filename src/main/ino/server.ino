#include <stdio.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>

const char* ssid     = "rabarbar";
const char* password = "xxx";
const char* STATE_ON = "on";
const char* STATE_OFF = "off";

byte relON[] = {0xA0, 0x01, 0x01, 0xA2};  //Hex command to send to serial for open relay
byte relOFF[] = {0xA0, 0x01, 0x00, 0xA1}; //Hex command to send to serial for close relay

ESP8266WebServer http_rest_server(80);

String header;

String state = STATE_OFF;
const int output0 = 0;

void setup() {
    Serial.begin(9600);
    pinMode(output0, OUTPUT);
    offRelay();
    connectToWifi();
    config_rest_server_routing();
    http_rest_server.begin();
}

void onRelay() {
    Serial.write(relON, sizeof(relON));
    state = STATE_ON;
    digitalWrite(output0, HIGH);
}

void offRelay() {
    Serial.write(relOFF, sizeof(relOFF));
    state = STATE_OFF;
    digitalWrite(output0, LOW);
}

void connectToWifi() {
    IPAddress ip(192, 168, 1, 20);
    IPAddress gateway(192, 168, 1, 1);
    IPAddress subnet(255, 255, 255, 0);
    WiFi.config(ip, gateway, subnet);
    WiFi.begin(ssid, password);
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
}

void config_rest_server_routing() {
    http_rest_server.on("/", HTTP_GET, []() {
        http_rest_server.send(200, "text/html",
                              "Welcome to the ESP8266 REST Web Server");
    });
    http_rest_server.on("/states/current", HTTP_GET, get_current_state);
    http_rest_server.on("/states", HTTP_POST, add_state);
}

void get_current_state() {
    StaticJsonBuffer<200> jsonBuffer;
    JsonObject& jsonObj = jsonBuffer.createObject();
    char JSONmessageBuffer[200];


    jsonObj["state"] = state;
    jsonObj.prettyPrintTo(JSONmessageBuffer, sizeof(JSONmessageBuffer));
    http_rest_server.send(200, "application/json", JSONmessageBuffer);
}

void add_state() {
    StaticJsonBuffer<500> jsonBuffer;
    String post_body = http_rest_server.arg("plain");
    JsonObject& jsonBody = jsonBuffer.parseObject(http_rest_server.arg("plain"));

    if (!jsonBody.success()) {
        http_rest_server.sendHeader("Debug", "1");
        http_rest_server.send(400);
    } else {
        if (http_rest_server.method() == HTTP_POST) {
            if (jsonBody["state"] == STATE_ON) {
                onRelay();
                http_rest_server.send(200);
            } else if (jsonBody["state"] == STATE_OFF) {
                offRelay();
                http_rest_server.send(200);
            } else {
                http_rest_server.sendHeader("Debug", "2:" + post_body);
                http_rest_server.send(400);
            }
        } else {
            http_rest_server.send(404);
        }
    }
}

void loop() {
    http_rest_server.handleClient();
}
