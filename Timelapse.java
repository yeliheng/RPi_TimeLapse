import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;

public class Timelapse {
	static Process process = null;
	static int i = 0;
	static int second = 10;//拍照间隔
	static String time = null;
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		showinfo();
		 new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {	
					try {
					 System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");//系统端口号
			            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
			            CommPortIdentifier portId = null;
			            while (portList.hasMoreElements()) {
			                CommPortIdentifier tmpPortId = (CommPortIdentifier) portList.nextElement();
			                System.out.println(tmpPortId.getName());
			                // 如果端口类型是串口则判断名称
			                if (tmpPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
			                    if (tmpPortId.getName().startsWith("/dev")) {
			                        portId = tmpPortId;
			                    }
			                }
			            }
			            if (portId == null) {
			                System.out.println("not detected");
			            }
			            SerialPort serialPort = (SerialPort) portId.open("ArduinoSerial", 1000);// 打开串口的超时时间为1000ms
			            // 设置串口速率为9600，数据位8位，停止位1们，奇偶校验无
			            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			            ArduinoSerial testObj = new ArduinoSerial(serialPort);
						testObj.write();
						takephoto();
			            //testObj.read();
			            testObj.close();
			            serialPort.close();
			        } catch (Exception e) {
			            e.printStackTrace();
			        }	
					takephoto();
					try {
						Thread.sleep(second * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			
		}).start();
		
	}
	
	public static void takephoto() {
        SimpleDateFormat df = new SimpleDateFormat("[MM-dd HH:mm:ss]");//设置日期格式
        time = df.format(new Date());// new Date()为获取当前系统时间
		i += 1;
		String cmd1 = "raspistill -rot 180 -w 1280 -h 720 -q 100 -o /home/timelapse/tl" + i + ".jpg";
		try {
			//process = Runtime.getRuntime().exec(cmd1);//执行闪光灯命令
			//System.out.println(time + " Light has been flashed!");
			System.out.println(time + " " + i + " photos has been token!");
			process = Runtime.getRuntime().exec(cmd1);//执行拍照命令
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    

	}
	public static void showinfo() {
		System.out.println("A simple Time-lapse program for Raspberry Pi.\n"
				+ "More detail: https://github.com/yeliheng/RPi_TimeLapse/");
		System.out.println("  ____         __     __  _ _ _                      \r\n" + 
				" |  _ \\        \\ \\   / / | (_) |                     \r\n" + 
				" | |_) |_   _   \\ \\_/ /__| |_| |__   ___ _ __   __ _ \r\n" + 
				" |  _ <| | | |   \\   / _ \\ | | '_ \\ / _ \\ '_ \\ / _` |\r\n" + 
				" | |_) | |_| |    | |  __/ | | | | |  __/ | | | (_| |\r\n" + 
				" |____/ \\__, |    |_|\\___|_|_|_| |_|\\___|_| |_|\\__, |\r\n" + 
				"         __/ |                                  __/ |\r\n" + 
				"        |___/                                  |___/ \r\n" + 
				"");
}
}