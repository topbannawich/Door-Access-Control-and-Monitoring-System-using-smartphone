# Door-Access-Control-and-Monitoring-System-using-smartphone
## Hardware
- Arduino MEGA 2560 With WiFi Built-in ESP8266
- RFID Card Reader/Detector Module Kit (RC522)
- 1602 LCD with backlight
- I2C Matrix 4x4 Keypad
- Breadboard 400 holes
- Micro SD Card Module (Catalex)
- Arduino Relay 5V 30A Power Relay
- Battery 18650
- 18650 Battery Box for 3*18065
- 3 Series 20A 12.6V BMS PCB Protection Board with Automatic Recovery for 18650 Li-ion Lithium Battery
- Button module
- Magnetic Door Switch
- Active Buzzer DC 1.5V-12V Buzzer
- Resistor 1K
- Solenoid door lock 12v
- Adapter 12V
- Voltage Meter Sensor

## Requirements
- Android Studio version 3.3.2(https://developer.android.com/studio/install)
- Arduino Ide version 1.8.9 (https://www.arduino.cc/en/Guide/Windows)
------------------------------------------------------------------------------------------------
## Firmware
- Mega.ino (\src\Arduino\Mega)ใช้สำหรับควบคุมอุปกรณ์ทุกตัวที่เชื่องต่อเข้ากับArduino mega
- ESP8266.ino (src\Arduino\ESP8266) ใช้สำหรับเชื่อมต่ออินเทอร์เน็ตผ่านไวไฟ เพื่อรับส่งข้อมูลจาก Firebase Realtime Database
- โฟลเดอร์libraries (\src\Arduino\libraries)เป็นโฟลเดอร์ที่รวม libraries ที่ใช้ในArduino
- โฟลเดอร์Final402 เป็น โปรเจกต์โฟลเดอร์แอปพลิเคชันของแอนดรอยด์
- โฟลเดอร์ Firebase Realtime Database เป็นไฟล์json export จาก database ที่ใช้ในโครงงาน ให้เห็นรูปแบบในการสร้างฐานข้อมูลว่าใช้ตัวแปรอะไรบ้าง นำไปimport ใช้ต่อได้
------------------------------------------------------------------------------------------------
## Installation
- โฟลเดอร์ Mega ใช้arduino ide เลือกBoard เป็น arduino mega 2560 เลือก port และอัพโหลดขึ้นไปที่บอร์ดarduino mega โหมด USB ATmega2560
- โฟลเดอร์ ESP8266 ใช้arduino ide เลือกBoard เป็นGeneric ESP 8266 Module เลือก port และอัพโหลดขึ้นไปที่บอร์ดarduino mega โหมด USBESP8266 (Update firmware or sketch)
- โฟลเดอร์ libraries Extract file ไว้ที่โฟลเดอร์ที่เก็บโปรเจกต arduino ในคอมพิวเตอร์ (C:\Users\PCname\Documents\Arduino) 
- โฟลเดอร์ Final402 เป็นแอปพลิเคชันแอนดรอยด์ ใช้android studio open project เลือก โฟลเดอร์Final402 หรือ ต้องการไฟล์.apk ไปติดตั้ง ให้นำไฟล์ app-release.apk จาก \Final402\app\release ไปไว้ในหน่วยความจำบนมือถือและกดติดตั้ง
- โฟลเดอร์ Firebase Realtime Database กด import ไฟล์ smartlock-5a81e-export.json บนเว็บ Firebase
------------------------------------------------------------------------------------------------
## Android Application
- src\Final402\app\src\main\java\com\example\passagon\final402
- หน้าหลักของแอปพลิเคชันเป็นไฟล์ MainActivity.java หลังจากที่เข้าสู่ระบบ จาก loginActivity.java
- การแบ่งแยกไฟล์java code แบ่งตามหน้าจอ และฟังก์ชัน เช่น การดูประวัติ(history.java), เพิ่มผู้ใช้(addActivity.java), ลบผู้ใช้(deleteActivity.java) เป็นต้น
- codejavaAdapter  เป็นการทำ Listview แสดงข้อมูล (http://www.akexorcist.com/2012/09/android-code-custom-list-view.html)
------------------------------------------------------------------------------------------------
## Config
- ESP-8266.ino ตรงส่วน FireBase_HOST และ FireBase_Auth นำค่าที่ได้รับจากเว็บของFirebase มาใส่ , WiFi.begin("username","password") ส่วนนี้ตั้งชื่อและรหัสผ่านของwifi ที่ต้องการให้ Arduino เชื่อมต่อ
- กรณีที่ต้องการแก้ไขไปใช้ Firebase Realtime Database ของตนเอง ต้อง Add Firebase to your Android project ในโปรเจคแอนดรอยด์ใหม่ (https://firebase.google.com/docs/android/setup) หรือ (https://medium.com/@pechpijitthapudom/%E0%B8%81%E0%B8%B2%E0%B8%A3%E0%B8%95%E0%B8%B4%E0%B8%94%E0%B8%95%E0%B8%B1%E0%B9%89%E0%B8%87-firebase-to-android-project-f27888a541a6)
