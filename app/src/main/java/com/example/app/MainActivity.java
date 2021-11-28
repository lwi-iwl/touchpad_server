package com.example.app;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_ENABLE_BT=1;
    BluetoothAdapter bluetoothAdapter;
    //public static final UUID MY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public static final int MESSAGE_READ=0;
    public static final int MESSAGE_WRITE=1;
    public static final int CONNECTING=2;
    public static final int CONNECTED=3;
    public static final int NO_SOCKET_FOUND=4;
    ConnectedThread connectedThread;
    private OutputStream mmOutStream;
    private boolean isCustomEnter;
    private int status = 0;
    @SuppressLint("HandlerLeak")

    Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg_type) {
            super.handleMessage(msg_type);

            switch (msg_type.what){
                case MESSAGE_READ:

                    byte[] readbuf=(byte[])msg_type.obj;
                    String string_recieved=new String(readbuf);

                    //do some task based on recieved string

                    break;

                case CONNECTED:
                    Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
                    break;

                case CONNECTING:
                    Toast.makeText(getApplicationContext(),"Connecting...",Toast.LENGTH_SHORT).show();
                    break;

                case NO_SOCKET_FOUND:
                    Toast.makeText(getApplicationContext(),"No socket found",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Button keyboard = (Button) findViewById(R.id.keyboardButton);
        keyboard.setOnClickListener(keyboardClick);
        initialize_bluetooth();
        start_accepting_connection();
        Button imageView = (Button) findViewById(R.id.touchpad);
        imageView.setOnTouchListener(myTouchListener);
        ((Button) findViewById(R.id.b1)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b2)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b3)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b4)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b5)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b6)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b7)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b8)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b9)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.b0)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bminus)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bplus)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.back)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.btab)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bq)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bw)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.be)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.br)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bt)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.by)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bu)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bi)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bo)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bp)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bot)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bza)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bbacksl)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bcaps)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.ba)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bs)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bd)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bf)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bg)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bh)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bj)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bk)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bl)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.btz)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bkav)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.benter)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bshift)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bz)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bx)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bc)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bv)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bb)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bn)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bm)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bzap)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.btoch)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bsla)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bprshift)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bctrl)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bwin)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.blalt)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bspace)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bpalt)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bpctrl)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.bpdel)).setOnTouchListener(keyboardTouch);
        ((Button) findViewById(R.id.gamepadt)).setOnTouchListener(myTouchListener);
        ((Button) findViewById(R.id.kup)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.kdown)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.kleft)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.kright)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.kaction)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.kesc)).setOnTouchListener(keyTouch);
        ((Button) findViewById(R.id.ke)).setOnTouchListener(keyTouch);
        isCustomEnter = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mmOutStream!=null)
            try {
                mmOutStream.write("DISCONN".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("EXCEPTION" +e.getMessage());
            }
    }

    View.OnTouchListener keyboardTouch = new View.OnTouchListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    String name = "CD";
                    name = name + (String) ((Button) v).getText();
                    while (name.length()<7)
                        name = name + "8";
                    if ((mmOutStream != null))
                        try {
                            System.out.println(name);
                            mmOutStream.write(name.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("EXCEPTION" + e.getMessage());
                        }
                    break;
                case MotionEvent.ACTION_UP:
                    String nameup = "CU";
                    nameup = nameup + (String) ((Button) v).getText();
                    while (nameup.length()<7)
                        nameup = nameup + "8";
                    if ((mmOutStream != null))
                        try {
                            System.out.println(nameup);
                            mmOutStream.write(nameup.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("EXCEPTION" + e.getMessage());
                        }
                    break;
                default:
                    return false;
            }
            return true;
        }
    };
    View.OnTouchListener keyTouch = new View.OnTouchListener() {
        @SuppressLint("DefaultLocale")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    String name = "KD";
                    name = name + (String) ((Button) v).getText();
                    while (name.length()<7)
                        name = name + "8";
                    if ((mmOutStream != null))
                        try {
                            System.out.println(name);
                            mmOutStream.write(name.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("EXCEPTION" + e.getMessage());
                        }
                    break;
                case MotionEvent.ACTION_UP:
                    String nameup = "KU";
                    nameup = nameup + (String) ((Button) v).getText();
                    while (nameup.length()<7)
                        nameup = nameup + "8";
                    if ((mmOutStream != null))
                        try {
                            System.out.println(nameup);
                            mmOutStream.write(nameup.getBytes(StandardCharsets.UTF_8));
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println("EXCEPTION" + e.getMessage());
                        }
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    View.OnClickListener keyboardClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (status == 0){
                status = 1;
                ((LinearLayout) findViewById(R.id.keyboard)).setVisibility(View.VISIBLE);
                ((LinearLayout) findViewById(R.id.touch)).setVisibility(View.INVISIBLE);
                ((Button) findViewById(R.id.keyboardButton)).setText("Gamepad");
            }
            else if (status == 1) {
                status = 2;
                ((LinearLayout) findViewById(R.id.keyboard)).setVisibility(View.INVISIBLE);
                ((LinearLayout) findViewById(R.id.gamepad)).setVisibility(View.VISIBLE);
                ((Button) findViewById(R.id.keyboardButton)).setText("Touchpad");
            }
            else{
                status = 0;
                ((LinearLayout) findViewById(R.id.gamepad)).setVisibility(View.INVISIBLE);
                ((LinearLayout) findViewById(R.id.touch)).setVisibility(View.VISIBLE);
                ((Button) findViewById(R.id.keyboardButton)).setText("Keyboard");
            }
        }
    };

    float dX, dY;
    float scroll;
    long old, oldMove;
    int quantity = 0;
    View.OnTouchListener myTouchListener = new View.OnTouchListener(){
        @SuppressLint("DefaultLocale")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int pointerIndex = event.getActionIndex();
            System.out.println(pointerIndex);
            Date date = new Date();
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                        dX = event.getRawX();
                        dY = event.getRawY();
                        if (isAClick(old, date.getTime())) {
                            if (mmOutStream != null)
                                try {
                                    System.out.println("LEFTDOWN");
                                    mmOutStream.write("LEFTDOW".getBytes(StandardCharsets.UTF_8));
                                } catch (IOException e) {
                                    System.out.println("EXCEPTION" + e.getMessage());
                                    e.printStackTrace();
                                }
                        }
                    old = date.getTime();
                    break;

                case MotionEvent.ACTION_UP:
                        if (quantity == 0) {
                            if (isAClick(old, date.getTime())) {
                                if (mmOutStream != null)
                                    try {
                                        mmOutStream.write("LEFTCLI".getBytes(StandardCharsets.UTF_8));
                                        System.out.println("LEFTCLICK");
                                    } catch (IOException e) {
                                        System.out.println("EXCEPTION" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                old = date.getTime();
                            } else {
                                if (mmOutStream != null)
                                    try {
                                        mmOutStream.write("LEFTUPP".getBytes(StandardCharsets.UTF_8));
                                        System.out.println("LEFTUP");
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                            }
                        } else {
                            if (isAClick(old, date.getTime())) {
                                if (mmOutStream != null)
                                    try {
                                        mmOutStream.write("RIGHTCL".getBytes(StandardCharsets.UTF_8));
                                        System.out.println("RIGHTCLICK");
                                    } catch (IOException e) {
                                        System.out.println("EXCEPTION" + e.getMessage());
                                        e.printStackTrace();
                                    }
                                old = date.getTime();
                            }
                        }
                        quantity = 0;
                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    scroll = event.getRawY();
                    quantity =2;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    quantity--;
                    break;
                case MotionEvent.ACTION_MOVE:

                    if ((quantity<2)&&(!isAClick(oldMove, date.getTime()))) {
                        float eventX = dX - event.getRawX();
                        float eventY = dY - event.getRawY();
                        System.out.println(eventX);
                        System.out.println(eventY);
                        String commandStr = "M";
                        if (eventX >= 0) {
                            int righti = Math.round((eventX));
                            if (righti > 99)
                                righti = 99;
                            commandStr = commandStr + "L" + String.format("%02d", righti);

                        } else if (eventX < 0) {
                            int lefti = -Math.round((eventX));
                            if (lefti > 99)
                                lefti = 99;
                            commandStr = commandStr + "R" + String.format("%02d", lefti);
                        }

                        if (eventY >= 0) {
                            int upi = Math.round((eventY));
                            if (upi > 99)
                                upi = 99;
                            commandStr = commandStr + "U" + String.format("%02d", upi);
                        } else if (eventY < 0) {
                            int downi = -Math.round((eventY));
                            if (downi > 99)
                                downi = 99;
                            commandStr = commandStr + "D" + String.format("%02d", downi);
                        }

                        System.out.println(commandStr);
                        if (mmOutStream != null)
                            try {
                                mmOutStream.write(commandStr.getBytes(StandardCharsets.UTF_8));
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("EXCEPTION" + e.getMessage());
                            }
                        dX = event.getRawX();
                        dY = event.getRawY();
                    }
                    else{
                            boolean isWasUp = false;
                            oldMove = date.getTime();
                            float eventScroll = scroll - event.getRawY();
                            String commandStr = "SCRL";
                            if (eventScroll >= 0) {
                                int upi = Math.round((eventScroll));
                                if (upi > 99)
                                    upi = 99;
                                if (upi == 99)
                                    isWasUp = true;
                                commandStr = commandStr + "U" + String.format("%02d", upi);
                            } else if (eventScroll < 0) {
                                int downi = -Math.round((eventScroll));
                                if (downi > 99)
                                    downi = 99;
                                if (downi == 99)
                                    isWasUp = true;
                                commandStr = commandStr + "D" + String.format("%02d", downi);
                            }
                            System.out.println(commandStr);
                            if ((mmOutStream != null) && (!isWasUp))
                                try {
                                    mmOutStream.write(commandStr.getBytes(StandardCharsets.UTF_8));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    System.out.println("EXCEPTION" + e.getMessage());
                                }
                            scroll = event.getRawY();
                    }
                    break;

                default:
                    return false;
            }
            return true;
        }
    };
    private boolean isAClick(long old, long now) {
        System.out.println(now-old);
        return !(now-old>150);
    }


    public void start_accepting_connection()
    {
        //call this on button click as suited by you

        AcceptThread acceptThread = new AcceptThread();
        acceptThread.start();
        Toast.makeText(getApplicationContext(),"accepting",Toast.LENGTH_SHORT).show();
    }

    public void initialize_bluetooth()
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(getApplicationContext(),"Your Device doesn't support bluetooth. you can play as Single player",Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

    }


    public class AcceptThread extends Thread
    {
        private final BluetoothServerSocket serverSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("NAME",MY_UUID);
            } catch (IOException e) {
                System.out.println("EXCEPTION" +e.getMessage());
            }
            serverSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned
            while (true) {
                try {
                    socket = serverSocket.accept();
                } catch (IOException e) {
                    System.out.println("EXCEPTION" +e.getMessage());
                    break;
                }

                // If a connection was accepted
                if (socket != null)
                {
                    // Do work to manage the connection (in a separate thread)
                    mHandler.obtainMessage(CONNECTED).sendToTarget();
                    connectedThread=new ConnectedThread(socket);
                    connectedThread.run();
                }
            }
        }
    }



    private class ConnectedThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                System.out.println("EXCEPTION" +e.getMessage());
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
            try {
                mmOutStream.write("Startttttttt".getBytes(StandardCharsets.UTF_8));
                Thread.sleep(1000);
            }
            catch (Exception e) {
                System.out.println("EXCEPTION" +e.getMessage());
            }
        }

        public void run() {
            byte[] buffer = new byte[5];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    if (Arrays.toString(buffer).equals("DISCO"))
                        cancel();

                } catch (IOException e) {
                    System.out.println("EXCEPTION" +e.getMessage());
                    break;
                }
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmInStream.close();
                mmOutStream.close();
                mmSocket.close();
            } catch (IOException e) {
                System.out.println("EXCEPTION" + e.getMessage());
            }
        }
    }
}