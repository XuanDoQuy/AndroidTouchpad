package com.xuandq.androidtouchpad.networking;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.xuandq.androidtouchpad.model.AsyncTaskResult;
import com.xuandq.androidtouchpad.model.KeyboardData;
import com.xuandq.androidtouchpad.model.MouseData;
import com.xuandq.androidtouchpad.model.PowerpointData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    private static final String TAG = "xClient";
    private Socket socket;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private static Client instance;

    private Client(Socket socket, DataOutputStream outputStream, DataInputStream inputStream) {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    public static void makeConnection(final String host, final int port, final MakeConnectionListener listener) {
        Log.d(TAG, "doInBackground: 0 ");
        AsyncTask taskMakeConnection = new AsyncTask<Object, Object, AsyncTaskResult<Client>>() {

            @Override
            protected AsyncTaskResult<Client> doInBackground(Object... objects) {
                try {
                    Log.d(TAG, "doInBackground: 1 ");
                    Socket s = new Socket(host, port);
                    Log.d(TAG, "doInBackground: 2");
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());
                    Log.d(TAG, "doInBackground: 3");
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    Log.d(TAG, "doInBackground: 4");
                    return new AsyncTaskResult<>(new Client(s, out, in));
                } catch (Exception e) {
                    e.printStackTrace();
                    return new AsyncTaskResult<>(e);
                }
            }

            @Override
            protected void onPostExecute(AsyncTaskResult<Client> result) {
                super.onPostExecute(result);

                if (result.getError() != null) {
                    Exception e = result.getError();
                    listener.onError(e);
                } else {
                    if (instance == null) {
                        instance = result.getResult();
                        listener.onSuccess(result.getResult());
                    }
                }

            }
        };
        if (instance == null) {
            taskMakeConnection.execute();
        }else {
            closeConnection();
            taskMakeConnection.execute();
        }

    }


    public static void makeUdpConnection(final String host, final int port, final MakeConnectionListener listener){
        AsyncTask taskMakeConnection = new AsyncTask<Object, Object, AsyncTaskResult<Client>>(){

            @Override
            protected AsyncTaskResult<Client> doInBackground(Object... objects) {
                try {
                    DatagramSocket socket = new DatagramSocket();
                    DatagramPacket packet =  null;
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(AsyncTaskResult<Client> clientAsyncTaskResult) {
                super.onPostExecute(clientAsyncTaskResult);

            }
        };
    }

    public static Client getInstance() {
        return instance;
    }

    public Socket getSocket() {
        return socket;
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.inputStream.close();
                instance.outputStream.close();
                instance.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendDataToServer(final Object object){
        @SuppressLint("StaticFieldLeak")

        AsyncTask taskSendData = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    if (object instanceof MouseData){
                        MouseData data = (MouseData) object;
                        outputStream.writeUTF("mouse");
                        outputStream.writeInt(data.getX());
                        outputStream.writeInt(data.getY());
                        outputStream.writeInt(data.getWheeel());
                        outputStream.writeBoolean(data.isLeftClick());
                        outputStream.writeBoolean(data.isRightClick());
                    }

                    if (object instanceof KeyboardData){
                        KeyboardData data = (KeyboardData) object;
                        outputStream.writeUTF("keyboard");
                        outputStream.writeUTF(data.getInput());
                        outputStream.writeBoolean(data.isText());
                        outputStream.writeUTF(data.getKeyCode());
                    }

                    if (object instanceof PowerpointData){
                        PowerpointData data = (PowerpointData) object;
                        outputStream.writeUTF("powerpoint");
                        outputStream.writeInt(data.getState());
                        outputStream.writeInt(data.getX());
                        outputStream.writeInt(data.getY());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        taskSendData.execute(object);
    }

    public void sendMode(final String mode){
        @SuppressLint("StaticFieldLeak")
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    outputStream.writeUTF(mode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
    }

    public Bitmap readCaptureScreen(){

        Bitmap bitmap = null;
        try {
            int size = inputStream.readInt();
            byte[] data = new byte[size];
            int index = 0;
            while (index < size)
            {
                int bytesRead = inputStream.read(data, index, size - index);
                if (bytesRead < 0)
                {
                    break;
                }
                index += bytesRead;
            }

            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Log.d(TAG, "readCaptureScreen: " + bitmap.getByteCount());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

}
