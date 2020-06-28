#include <Firebase.h>
#include <FirebaseArduino.h>
#include <FirebaseCloudMessaging.h>
#include <FirebaseError.h>
#include <FirebaseHttpClient.h>
#include <FirebaseObject.h>

#include <ESP8266WebServer.h>
#include <ESP8266WebServerSecure.h>
#include <ESP8266WebServerSecureAxTLS.h>
#include <ESP8266WebServerSecureBearSSL.h>
#include <time.h>



#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>

//FirebaseData firebaseData;
ESP8266WebServer server(80);
MDNSResponder mdns;
int timezone = 7 * 3600; //ตั้งค่า TimeZone ตามเวลาประเทศไทย
int dst = 0; //กำหนดค่า Date Swing Time
#define FireBase_HOST "smartlock-5a81e.firebaseio.com"
#define FireBase_AUTH "VL5djv1fynDTLgHuSrhIhsCndmBK6kll4WxQW6fm"
String webPage = "";
String card;
int led_pin = 7;
int count = 0;
String inString;

void setup()
{

  Serial.begin(115200);
  Serial1.begin(115200);






  // Serial.println();


 // WiFi.begin("Bankscript", "passagon12");
  WiFi.begin("Kaewmarble", "kaew17482"); //เชื่อมต่อ WIFI
  //Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) // รอจนกว่าจะเชื่อมต่อ WiFi ได้
  {
    delay(500);
     Serial.print(".");
  }

  // Serial.println();

   Serial.print("Connected, IP address: ");
     Firebase.setInt("connection", 1);
  Serial.println(WiFi.localIP());

  Firebase.begin(FireBase_HOST, FireBase_AUTH); //เชื่อมต่อ Firebase

 
  configTime(timezone, dst, "pool.ntp.org", "time.nist.gov");     //ดึงเวลาจาก Server
  // Serial.println("\nWaiting for time");
  while (!time(nullptr)) {
    //  Serial.print(".");
    delay(1000);
  }
  // Serial.println("");


}


void loop() {
  


  
  if (Firebase.getInt("connection") == 0){  //ยืนยันว่าเชื่อมต่อ WiFi อยู่
         Firebase.setInt("connection", 1);
    
    }
  
  if (Firebase.getInt("status") == 1) //รอรับค่าเมื่อผู้ใช้สั่งเปิดประตูจาก App
  {


    Serial.println("[ON]"); 
    Firebase.setInt("status", 0);
    
    savehistory("Smart Lock App");
    inString = "";

  }
  if (Firebase.getInt("register") == 1) { //รอคำสั่งสมัครสมาชิก
    delay(500);
    Serial.println("[register]");
    delay(500);
    Firebase.setInt("register", 2);

    
   

  }
  

  if (Serial.available()) {
    char inChar = Serial.read();
    // Вывод прочитанных данных в порт Serial
    Serial.write(inChar);
    // Поиск команды в полученных данных (команда должна быть в квадратных скобках)
    inString += inChar;
    if (inChar == ']') {
      if (inString.indexOf("[keypad]") > 0) {


        // Serial.println("am here");
        Firebase.setInt("test", 587);

        savehistory("Key Pad");
        inString = "";
      }

      else if (inString.indexOf("[taxcard]") > 0) {
        
        inString = "";
        Firebase.setInt("test", 989);
        //savehistory("Key Card");
        savehistoryCard("Key Card");

      }

      
      else if (inString.indexOf("[succ]") > 0) {
        
        inString = "";
       Firebase.setInt("register", 0);
       
      }
      inString = "";

    }
    else if(inChar == '('){
    
      card = inString.substring(1,inString.length()-1);
       inString = "";
      }
  

  }



}

void savehistory(String type) { //บันทึกข้อมูลลง firebasse

  String user  = Firebase.getString("tmp");
  String namee = Firebase.getString("User/" + user + "/name");

  String surname = Firebase.getString("User/" + user + "/surname");

  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& valueObject = jsonBuffer.createObject();
  valueObject["name"] = namee;
  valueObject["surname"] = surname;
  valueObject["note"] = type;
  valueObject["picture"] = "null";
  valueObject["type"] = "pass " + type;
  time_t now = time(nullptr);
  struct tm* p_tm = localtime(&now);
  String ans;
  int xx = p_tm->tm_hour;
  ans = ans + xx;
  xx = p_tm->tm_min;
  ans = ans + ":" + xx;
  xx = p_tm->tm_sec;
  ans = ans + ":" + xx;
  xx = p_tm->tm_mday;
  ans = ans + ":" + xx;
  xx = p_tm->tm_mon + 1;
  ans = ans + ":" + xx;
  xx = p_tm->tm_year + 1900;
  ans = ans + ":" + xx;
  valueObject["date"] = ans;
  Firebase.push("history", valueObject);


}
void savehistoryCard(String type) {//บันทึกข้อมูลลง firebasse

 // String user  = Firebase.getString("tmp");
  String namee = "Key Card";
 card="";
  String surname ="";
  StaticJsonBuffer<200> jsonBuffer;
  JsonObject& valueObject = jsonBuffer.createObject();
  valueObject["name"] = namee;
  valueObject["surname"] = surname;
  valueObject["note"] = type;
  valueObject["picture"] = "null";
  valueObject["type"] = "pass " + type;
  time_t now = time(nullptr);
  struct tm* p_tm = localtime(&now);
  String ans;
  int xx = p_tm->tm_hour;
  ans = ans + xx;
  xx = p_tm->tm_min;
  ans = ans + ":" + xx;
  xx = p_tm->tm_sec;
  ans = ans + ":" + xx;
  xx = p_tm->tm_mday;
  ans = ans + ":" + xx;
  xx = p_tm->tm_mon + 1;
  ans = ans + ":" + xx;
  xx = p_tm->tm_year + 1900;
  ans = ans + ":" + xx;
  valueObject["date"] = ans;
  Firebase.push("history", valueObject);


}






