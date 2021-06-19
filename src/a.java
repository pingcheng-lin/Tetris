import java.net.*;
import java.io.*;

class a{
    public static String[] allPatternPool = new String[10];
    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(1234);
        while(true)
        {
            Socket s = ss.accept();
            DataOutputStream o = new DataOutputStream(s.getOutputStream());
            o.writeUTF("fuckyou");
            o.flush();
            o.write(1);
            o.flush();
            o.write(2);
            o.flush();
            DataInputStream i = new DataInputStream(s.getInputStream());
            String x = i.readUTF();
            System.out.println(x);
        }
    }

}