package agora.humano.seja.com.helloworldthings;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ricardoogliari on 2/17/17.
 */

public class MyApplication extends Application {

    public static DatabaseReference myRef;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("valor");
    }
}
