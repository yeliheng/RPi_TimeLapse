int redPin = 9;
int greenPin = 10;
int bluePin = 11;

void setup(){
  Serial.begin(9600); // 9600 bps
  pinMode(redPin, OUTPUT);
  pinMode(greenPin, OUTPUT);
  pinMode(bluePin, OUTPUT);
  flash(0,0,0);
}

void loop(){
    //串口逻辑
    if ( Serial.available())
    {
      if('s' == Serial.read()){
        Serial.println("[Arduino] Light has been flashed!");
           //R:0-255 G:0-255 B:0-255 Flashlight
         flash(255,255,255);  
         delay(10000);
         flash(0,0,0);
      }
     } 
}

/*void flash(int red, int green, int blue){
  analogWrite(redPin,constrain(0,0,255));
  analogWrite(greenPin,constrain(0,0,255));
  analogWrite(bluePin,constrain(0,0,255));
}*/
void flash (unsigned char red, unsigned char green, unsigned char blue)     // the color generating function  
{    
          analogWrite(redPin, 255-red);   
          analogWrite(bluePin, 255-blue); 
          analogWrite(greenPin, 255-green); 
}   
