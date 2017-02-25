package agora.humano.seja.com.helloworldthings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    private Gpio ledGpio1;
    private Gpio ledGpio2;
    private Gpio ledGpio3;
    private Gpio ledGpio4;
    private static final String GPIO_PIN_1 = "IO13";
    private static final String GPIO_PIN_2 = "IO12";
    private static final String GPIO_PIN_3 = "IO11";
    private static final String GPIO_PIN_4 = "IO10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        PeripheralManagerService service = new PeripheralManagerService();
        Log.d("GPIOS", "Available GPIO: " + service.getGpioList());
        Log.d("GPIOS", "Available GPIO pwn: " + service.getPwmList());

        try {
            ledGpio1 = service.openGpio(GPIO_PIN_1);
            ledGpio1.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            ledGpio2 = service.openGpio(GPIO_PIN_2);
            ledGpio2.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            ledGpio3 = service.openGpio(GPIO_PIN_3);
            ledGpio3.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            ledGpio4 = service.openGpio(GPIO_PIN_4);
            ledGpio4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
        } catch (IOException e) {
            Log.e("GPIOS", "Error on PeripheralIO API", e);
        }

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("TESTE", "onDataChange...  " + dataSnapshot.getValue());
                int valor = Integer.parseInt(dataSnapshot.getValue().toString());
                //progressBar.setProgress(valor * 20);

                try {
                    ledGpio1.setValue(false);
                    ledGpio2.setValue(false);
                    ledGpio3.setValue(false);
                    ledGpio4.setValue(false);
                    if (valor > 0){
                        ledGpio1.setValue(true);
                    }

                    if (valor > 1){
                        ledGpio2.setValue(true);
                    }

                    if (valor > 2){
                        ledGpio3.setValue(true);
                    }

                    if (valor > 3){
                        ledGpio4.setValue(true);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("TESTE", "erro ... " + databaseError.getMessage());
            }
        };
        MyApplication.myRef.addValueEventListener(postListener);
    }
}
