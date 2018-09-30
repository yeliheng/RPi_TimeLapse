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
public class ArduinoSerial {
    private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;
    public ArduinoSerial(SerialPort serialPort) throws IOException {
        this.serialPort = serialPort;
        this.in = serialPort.getInputStream();
        this.out = serialPort.getOutputStream();
    }
    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write() throws InterruptedException {
        try {
            Thread.sleep(2000);
            System.out.println("Connected!");
            OutputStreamWriter writer = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("s");
            bw.flush();
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close() throws IOException {
        in.close();
        out.close();
    }
}