package paradoxinfinite.wol_cn;



import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText iptext = findViewById(R.id.mIPAddress);
                EditText mactext = findViewById(R.id.mMACAddress);
                String mac = mactext.getText().toString();
                String broadcastIP = iptext.getText().toString();
                wakeup(broadcastIP, mac);
            }
        });
    }

    private byte[] getMacBytes(String mac) throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = mac.split("(\\:|\\-)");
        if (hex.length != 6) {
            Toast toast=Toast.makeText(getApplicationContext(),"Invalid MAC address.",Toast.LENGTH_SHORT);
            toast.show();
            throw new IllegalArgumentException("Invalid MAC address.");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        }
        catch (NumberFormatException e) {
            Toast toast=Toast.makeText(getApplicationContext(),"Invalid hex in MAC address.",Toast.LENGTH_SHORT);
            toast.show();
            throw new IllegalArgumentException("Invalid hex digit in MAC address.");
        }
        return bytes;
    }

    public void wakeup(String broadcastIP, String mac) {
        if (mac == null) {
            return;
        }

        try {
            byte[] macBytes = getMacBytes(mac);
            byte[] bytes = new byte[6 + 16 * macBytes.length];
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) 0xff;
            }
            for (int i = 6; i < bytes.length; i += macBytes.length) {
                System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
            }
            InetAddress address = InetAddress.getByName(broadcastIP);
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, 5555);
            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
            Toast toast=Toast.makeText(getApplicationContext(),"Magic Packet Sent!",Toast.LENGTH_SHORT);
            toast.show();
        }
        catch (Exception e) {
            Toast toast=Toast.makeText(getApplicationContext(),"Magic Packet Failed! "+e,Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}

