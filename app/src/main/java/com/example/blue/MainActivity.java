package com.example.blue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.RemoteException;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AtomicFile;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.estimote.coresdk.common.internal.utils.L;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;


import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static java.util.Collections.asLifoQueue;
import static java.util.Collections.reverseOrder;

/*Created by
    NAGA VINEEL GOLLA
*/


public class MainActivity extends AppCompatActivity {

    private BeaconManager beaconManager;
    LocationManager locationManager;
    String response;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    private BeaconRegion region;
    ListView listView;
    ArrayList <products> list = new ArrayList<>();

    beacons beacons2 = new beacons();
    beacons beacons3 = new beacons();
    beacons previous = new beacons();
    beacons beacons4 = new beacons();


    int ratio;
    float ratiolinear;
    float Distance1,Distance2,Distance3;

    int flag = 1;
    List <String> words = new ArrayList<>() ;
    Map<String,Float> treemap = new HashMap <>();
    int occurrences1,occurrences2,occurrences3;

    int b2,b3,b4;

    Map<String,Integer> h1 = new HashMap<>();




    private static final UUID ESTIMOTE_PROXIMITY_UUID = UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D");
    private static final Region ALL_ESTIMOTE_BEACONS = new BeaconRegion("ranged region",ESTIMOTE_PROXIMITY_UUID,null,null);





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestLocationPermission();

        h1.put("beacons2",0);
        h1.put("beacons3",0);
        h1.put("beacons4",0);


        beaconManager = new BeaconManager(getApplicationContext());

        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {

            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List <Beacon> beacons) {
                System.out.println("discovered");
                System.out.println(beacons.toString());
                if (beacons.size() != 0 && flag<=3) {

                    for(int i =0; i< beacons.size();i++){
                        if(beacons.get(i).getMajor() == 41072 && beacons.get(i).getMinor()== 44931){
                            ratio = beacons.get(i).getMeasuredPower() - beacons.get(i).getRssi();
                            ratiolinear = (float) Math.pow((double) 10, (double) ratio /20);
                            Distance1 = (float) Math.sqrt((double) ratiolinear);
                            beacons2.setMajor(beacons.get(i).getMajor());
                            beacons2.setMinor(beacons.get(i).getMinor());
                            beacons2.setDistance(Distance1);
                            Log.d("dist2", String.valueOf(Distance1));
                            treemap.put("beacons2",beacons2.getDistance());

                        }
                        else if(beacons.get(i).getMajor() == 47152 && beacons.get(i).getMinor()== 61548){
                            ratio = beacons.get(i).getMeasuredPower() - beacons.get(i).getRssi();
                            ratiolinear = (float) Math.pow((double) 10, (double) ratio /20);
                            Distance2 = (float) Math.sqrt((double) ratiolinear);
                            beacons3.setMajor(beacons.get(i).getMajor());
                            beacons3.setMinor(beacons.get(i).getMinor());
                            beacons3.setDistance(Distance2);
                            Log.d("dist3", String.valueOf(Distance2));
                            treemap.put("beacons3",beacons3.getDistance());

                        }
                        else if(beacons.get(i).getMajor() == 15326 && beacons.get(i).getMinor()== 56751){
                            ratio = beacons.get(i).getMeasuredPower() - beacons.get(i).getRssi();
                            ratiolinear = (float) Math.pow((double) 10, (double) ratio /20);
                            Distance3 = (float) Math.sqrt((double) ratiolinear);
                            beacons4.setMajor(beacons.get(i).getMajor());
                            beacons4.setMinor(beacons.get(i).getMinor());
                            beacons4.setDistance(Distance3);
                            Log.d("dist4", String.valueOf(Distance3));
                            treemap.put("beacons4",beacons4.getDistance());
                        }
                    }
                    List<Map.Entry <String,Float>> list = new LinkedList <Map.Entry <String, Float>>(treemap.entrySet());
                    Collections.sort(list, new Comparator <Map.Entry <String, Float>>() {
                        @Override
                        public int compare(Map.Entry <String, Float> o1, Map.Entry <String, Float> o2) {
                            return o1.getValue().compareTo(o2.getValue());
                        }
                    });
                    Log.d("pou",list.toString());

                    if(list.size() == 3) {

                        if (h1.containsKey(list.get(0).getKey())) {
                            h1.put(list.get(0).getKey(), h1.get(list.get(0).getKey()) + 5);
                        }

                        if (h1.containsKey(list.get(1).getKey())) {
                            h1.put(list.get(1).getKey(), h1.get(list.get(1).getKey()) + 4);
                        }

                        if (h1.containsKey(list.get(2).getKey())) {
                            h1.put(list.get(2).getKey(), h1.get(list.get(2).getKey()) + 3);
                        }
                    }
                    else if(list.size() == 2){
                        if (h1.containsKey(list.get(0).getKey())) {
                            h1.put(list.get(0).getKey(), h1.get(list.get(0).getKey()) + 5);
                        }

                        if (h1.containsKey(list.get(1).getKey())) {
                            h1.put(list.get(1).getKey(), h1.get(list.get(1).getKey()) + 4);
                        }
                    }
                    else{
                        if (h1.containsKey(list.get(0).getKey())) {
                            h1.put(list.get(0).getKey(), h1.get(list.get(0).getKey()) + 5);
                        }
                    }



                    flag = flag + 1;

                    if(flag == 4){

                        flag = 1;

                        List<Map.Entry<String,Integer>> list2 = new LinkedList <>(h1.entrySet());
                        Collections.sort(list2, new Comparator <Map.Entry <String, Integer>>() {
                            @Override
                            public int compare(Map.Entry <String, Integer> o1, Map.Entry <String, Integer> o2) {
                                return o2.getValue().compareTo(o1.getValue());
                            }
                        });

                        h1.put("beacons2",0);
                        h1.put("beacons3",0);
                        h1.put("beacons4",0);

                        Log.d("end",list2.toString());


                        if(((LinkedList<Map.Entry<String, Integer>>) list2).getFirst().getKey() == "beacons2"){
                            if(previous != beacons2) {
                                previous = beacons2;
                                getdata(beacons2.major, beacons2.minor);
                            }
                        }
                        else if(((LinkedList<Map.Entry<String, Integer>>) list2).getFirst().getKey() == "beacons3"){
                            if(previous != beacons3) {
                                previous = beacons3;
                                getdata(beacons3.major, beacons3.minor);
                            }
                        }
                        else if(((LinkedList<Map.Entry<String, Integer>>) list2).getFirst().getKey() == "beacons4"){
                            if(previous != beacons4) {
                                previous = beacons4;
                                getdata(beacons4.major, beacons4.minor);
                            }
                        }


                    }


                }
                else{
                    System.out.println("came into no data");
                    getdata(0,0);
                }

            }

        });

        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion region, List <Beacon> beacons) {
                Log.d("here12","here12");
                showNotification(
                        "Detected",
                        "beacon found");
                Toast.makeText(getApplicationContext(),"beacon found",Toast.LENGTH_LONG).show();
            }
            @Override
            public void onExitedRegion(BeaconRegion region) {
                // could add an "exit" notification too if you want (-:
                Log.d("exit","exit");
                Toast.makeText(getApplicationContext(),"beacon lost",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, notification);
    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                Log.d("here", "startRanging");
                beaconManager.startRanging((BeaconRegion) ALL_ESTIMOTE_BEACONS);
            }
        });

    }






    public String readJSON() {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = getAssets().open("discount.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }
        return json;
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);


    }



    @AfterPermissionGranted(REQUEST_LOCATION_PERMISSION)
    public void requestLocationPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION};
        if(EasyPermissions.hasPermissions(this, perms)) {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
        else {
            EasyPermissions.requestPermissions(this, "Please grant the location permission", REQUEST_LOCATION_PERMISSION, perms);
        }
    }

    private void getdata(Integer major,Integer minor){
        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        String url = "";
        if (major == 0 && minor == 0){
            url = "https://z1cbskq9w2.execute-api.us-east-2.amazonaws.com/default/beacon?major="+""+"&minor="+"";
        }
        else {
            url="https://z1cbskq9w2.execute-api.us-east-2.amazonaws.com/default/beacon?major="+major+"&minor="+minor;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ListView l = findViewById(R.id.listview);
                try {
                    JSONArray j = new JSONArray(response);
                    ArrayList<products> b = new ArrayList<>();

                    for (int i = 0; i < j.length();i++){
                        JSONArray a = (JSONArray) j.get(i);
                        System.out.println(a.get(0));
                        b.add(new products(a.getString(0), a.getDouble(2), a.getString(1),a.getInt(3)));
                    }
                    list_Adapter adapter = new list_Adapter(MainActivity.this, b);
                    l.setAdapter(adapter);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Payment_unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);

    }
}