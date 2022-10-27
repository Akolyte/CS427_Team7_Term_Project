package edu.uiuc.cs427app;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
public class AuthService extends Service{
    private Authenticator mAuthenticator;

    // Service for adding a custom type account, as instructed on Account Manager document
    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
