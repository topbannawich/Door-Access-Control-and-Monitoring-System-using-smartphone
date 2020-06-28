#include <Wire.h> 
#include <LiquidCrystal_I2C.h>
#include <i2c_keypad.h>
#include <SPI.h>
#include <RFID.h>
#include <SD.h>
#include <SoftwareSerial.h>
#define SD_CS 4

#define SS_PIN 53
#define RST_PIN 49
const int switchPin = 6;
int relay_pin=7;
String str;
LiquidCrystal_I2C lcd(0x27, 16, 2);
String x="";
String star="";
String pass = "123";
String ca= "135231992122";

String   inString;
String tmp;
String cardtmp;
int door=0;
I2CKEYPAD key;
RFID rfid(SS_PIN, RST_PIN); 
File myFile;
 int serNum0;
    int serNum1;
    int serNum2;
    int serNum3;
    int serNum4;

 String line;
  String cardArray[3];

void setup() {
  // put your setup code here, to run once:
 Serial3.begin(115200);
  Serial.begin(115200);
   Serial1.begin(115200);
 
  SPI.begin(); 
  rfid.init();
  if(!SD.begin(SD_CS)) {  //เชื่อมต่อกับ sd card
    // If the SD can't be started, loop forever
    Serial.println("SD failed or not present!");
    //while(1);
  }else{
    Serial.println("SD correct");
    }
     
      digitalWrite(SD_CS,LOW);
     myFile = SD.open("CARD.txt");
  if (myFile) { 
  

    // read from the file until there's nothing else in it:
    while (myFile.available()) {
     line= myFile.readString();
    }
    // close the file:
    myFile.close();
  } else {
    // if the file didn't open, print an error:
    Serial.println("error opening");
  }
    digitalWrite(SD_CS,HIGH);
    Serial.println(line);
    
    
    
  pinMode(relay_pin,OUTPUT); 
    pinMode(switchPin, INPUT);
    digitalWrite(switchPin, HIGH); 
    lcd.begin();
  // Turn on the blacklight and print a message.
  lcd.backlight();
  lcd.print("Door close");
if(door==1)
  {
     digitalWrite(relay_pin,LOW);
     door=0;
    }

key.begin(0x20, 100); // void begin(char addr = 0x20, long interval = 200) ;
  key.on(PRESS, [](char key) { // void on(KEYPAD_EVENT event, KeypadEventCallback callback) ;
  tmp = key;
  if(tmp=="D") //รับค่า password เมื่อแกดปุ่ม D
  {
     
     if(x==pass){
    digitalWrite(relay_pin,HIGH);
    star = "";
   // Serial.println("OPEN!!");  
     Serial3.println("[keypad]");
    str="";
    x="";
    lcd.clear();
     lcd.print("Door Open");
   
   
     
   }
   else{ //password ผิด
    x="";
    tmp="";
    lcd.clear();
     lcd.print("PASSAWORD WRONG!!");
     star="";
      delay(5000);
      lcd.clear();
lcd.print("Door close");
    }
  }else{
    x=x+key;
    lcd.setCursor(0, 1);
   star = star+"*";
    lcd.print("Enter Pass:"+star);     
   
  }
   // Serial.print("PRESS: ");
    Serial.println(x);
  });
  
}

void loop() {
     
 
  // put your main code here, to run repeatedly:
  String card;
   if (rfid.isCard()) { //รอรับ keycard
        if (rfid.readCardSerial()) {
            
                /* With a new cardnumber, show it. */
                Serial.println(" ");
                Serial.println("Card found");
                serNum0 = rfid.serNum[0];
                serNum1 = rfid.serNum[1];
                serNum2 = rfid.serNum[2];
                serNum3 = rfid.serNum[3];
                serNum4 = rfid.serNum[4];
               
                //Serial.println(" ");
                Serial.println("Cardnumber:");
                Serial.print("Dec: ");
   Serial.print(rfid.serNum[0],DEC);
                Serial.print(", ");
    Serial.print(rfid.serNum[1],DEC);
                Serial.print(", ");
    Serial.print(rfid.serNum[2],DEC);
                Serial.print(", ");
    Serial.print(rfid.serNum[3],DEC);
                Serial.print(", ");
    Serial.print(rfid.serNum[4],DEC);
                Serial.println(" ");
                        
                Serial.print("Hex: ");
    Serial.print(rfid.serNum[0],HEX);
    card = card+rfid.serNum[0];
      card = card+rfid.serNum[1];
        card = card+rfid.serNum[2];
          card = card+rfid.serNum[3];
            card = card+rfid.serNum[4];
    
                Serial.print(", ");
    Serial.print(rfid.serNum[1],HEX);
                Serial.print(", ");
    Serial.print(rfid.serNum[2],HEX);
                Serial.print(", ");
    Serial.print(rfid.serNum[3],HEX);
                Serial.print(", ");
    Serial.print(rfid.serNum[4],HEX);
                Serial.println(" ");

               Serial.println(card);
              cardtmp=card;
              
              if(card == ca)
              {
                 Serial3.println("("+card+")");
                 Serial3.println("[taxcard]");
                 digitalWrite(relay_pin,HIGH);
                 //Serial.println("OPEN!!");  
                
                  card = "";
                 lcd.clear();
                 lcd.print("Door Open");
               
    
               }
                delay(5000);
               
          }
    }
    
    rfid.halt();
   
   
   key.scand(); //รอรับค่าจาก keypad
 
  if(digitalRead(switchPin) == LOW){

   digitalWrite(relay_pin,LOW);
     lcd.clear();
     lcd.print("Door Close");
  }
 

   
  
 
 
 
 
   
  }
  void serialEvent3() { //รอรับค่าจาก esp8266
  while (Serial3.available()) {
    // Чтение данных из порта Serial3
    char inChar = Serial3.read();
    // Вывод прочитанных данных в порт Serial
   // Serial.write(inChar);
    // Поиск команды в полученных данных (команда должна быть в квадратных скобках)
    inString += inChar;
    if (inChar == ']') {
     
      if (inString.indexOf("[ON]")>0) {
        inString="";
        digitalWrite(relay_pin, HIGH);
        delay(5000);
        digitalWrite(relay_pin, LOW);
        
        
        
      }
      else if (inString.indexOf("[register]")>0) {
        //String cardx;
       // cardx = "("+cardtmp+")";
        // Serial3.println(cardx);
         Serial.println(inString);
        
        inString="";
         lcd.clear();
       lcd.print("CARD NEW ");
       
       
   while(true){
   if (rfid.isCard()) {
        if (rfid.readCardSerial()) {
            
             String card;
         cardtmp="";
                serNum0 = rfid.serNum[0];
                serNum1 = rfid.serNum[1];
                serNum2 = rfid.serNum[2];
                serNum3 = rfid.serNum[3];
                serNum4 = rfid.serNum[4];
              
                //Serial.println(" ");
         card = card + "(" ;    
    card = card+rfid.serNum[0];
      card = card+rfid.serNum[1];
        card = card+rfid.serNum[2];
          card = card+rfid.serNum[3];
            card = card+rfid.serNum[4];
    
               card = card + ")";
                 cardtmp = card.substring(1,card.length()-1);
               Serial3.println(card);   
                
                  myFile=SD.open("CARD.txt", FILE_WRITE);
                   digitalWrite(SD_CS,LOW);
                 if (myFile) {
    Serial.println("File opened ok");
    myFile.print(cardtmp+",");
     Serial3.println("[succ]");
      myFile.close();
    digitalWrite(SD_CS,HIGH);
    }else{
       Serial.println("Not open file");
      }
    
               }
                delay(1000); 
                  break; 
          }
    }
    lcd.clear();
       lcd.print("done");
       delay(5000); 
        lcd.clear();
      lcd.print("Door close");
    
      }
      }
      
     // inString = "";
    }
  }
  







