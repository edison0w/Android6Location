package com.example.edison.android6location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity
        implements LocationListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    LocationManager mLocationManager;
    TextView latitud, longitud;
    private static final int REQUEST_CODE_LOCATION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializamos los componentes o vistas
        latitud = (TextView) findViewById(R.id.txtLatitud);
        longitud = (TextView) findViewById(R.id.txtLongitud);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
    public void onclick(View view) {
        // Preguntamos si tenemos otorgado el permiso
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)

            // si tenemos otorgado el permiso continuamos con la petición
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this, null);
        else
            // Si no tenemos los permisos, se abrira  un cuadro de dialogo para la petición del mismo
            ActivityCompat.requestPermissions(this,                             // contexto
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},     // array de string con los permisos
                    REQUEST_CODE_LOCATION);                                     // código de la solicitud
    }
    // Despues de permitir o rechazar el permiso venimos a este metodo con el respuesta
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Si el codigo de solicitud es igual al que le envie
        if (requestCode == REQUEST_CODE_LOCATION) {
            // Si el array de persmisos enviados es > 0 y permiso enviado fue otorgado
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                // Si fue permitido continuar con el flujo
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this, null);
            else
                // Si no fue concenido le indicamos al usuario con un mensaje Permiso denegado
                Toast.makeText(getApplicationContext(), "Permiso denegado", Toast.LENGTH_LONG).show();
        }else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationManager != null)
            mLocationManager.removeUpdates(this);
    }
    @Override
    public void onLocationChanged(Location location) {
        latitud.setText(String.format("%s %f", "latitud", location.getLatitude()));
        longitud.setText(String.format("%s %f", "longitud", location.getLongitude()));
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
    }
}
