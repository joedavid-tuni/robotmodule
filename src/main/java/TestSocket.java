import java.net.*;
import java.io.*;
import java.util.stream.Collectors;

public class TestSocket {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    private String ip;
    private Integer port;

    public TestSocket(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    public void startConnection() throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
//        String resp = in.lines().collect(Collectors.joining());
        String resp = org.apache.commons.io.IOUtils.toString(in);
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {

        TestSocket sock = new TestSocket("127.0.0.1", 30002);
        sock.startConnection();

        String motion = "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)";
        String resp = sock.sendMessage(motion);

        System.out.println(resp);


    }
}
