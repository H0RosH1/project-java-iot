
#include <LiquidCrystal.h>
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
#include "DHT.h"
#define DHTPIN 7
#define DHTTYPE DHT22
DHT dht(DHTPIN, DHTTYPE);
void setup() {
  lcd.begin(20, 4);
  Serial.begin(9600);
  dht.begin();
  Serial.setTimeout(50);
}

void loop() {
  String text = Serial.readString();
  String line1 = text.substring(0, 16);
  String line2 = text.substring(16, 33);
  String line3 = text.substring(33,49);
  String line4 = text.substring(49, 65);
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  float f = dht.readTemperature(true);
  
  Serial.println(h);
  Serial.println(t);
  Serial.println(f);

  
  if(text.length() > 0) {
    delay(100);
    lcd.setCursor(4, 0);
    lcd.print("                    ");
    lcd.setCursor(1, 1);
    lcd.print("                    ");
    lcd.setCursor(0, 2);
    lcd.print(" Humidity(%)    ");
    lcd.setCursor(15, 2);
    lcd.print( h);
    lcd.setCursor(0, 3);
    lcd.print(" T.=       F.=   ");
    lcd.setCursor(5, 3);
    lcd.print(t);
    lcd.setCursor(15, 3);
    lcd.print(f);
  }
    lcd.setCursor(5, 0);
    lcd.print(line1);
    lcd.setCursor(1, 1);
    lcd.print(line2);
    lcd.setCursor(0, 2);
    lcd.print(line3);
    lcd.setCursor(0, 3);
    lcd.print(line4);
}
