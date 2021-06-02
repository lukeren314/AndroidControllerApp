package com.example.androidpad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class SocketClient {
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public enum SocketState{
        NONE,
        PENDING,
        CONNECTED,
        DISCONNECTED
    }
    public enum Protocol{
        TCP,
        UDP
    }

    private Protocol protocol;
    private String serverIp;
    private int portNum;

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    private DatagramSocket datagramSocket;
    private InetAddress inetAddress;
    private DatagramSocket datagramInput;
    private InetAddress local;

    HandlerThread sendHandlerThread;
    Handler sendHandler;
    private SocketState socketState;
    private String deviceName;
    private String sendKey;
    private String receiveKey;

    private ControllerView controllerView;
    public SocketClient(){
        this.socketState = SocketState.NONE;
    }
    public void setControllerView(ControllerView controllerView){
        this.controllerView = controllerView;
    }
    public String getDeviceName(){
        return deviceName;
    }
    public void setDeviceName(String deviceName){
        this.deviceName = deviceName;
    }
    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getPortNum() {
        return portNum;
    }

    public void setPortNum(int portNum) {
        this.portNum = portNum;
    }

    public boolean isConnected() {
        return socketState == SocketState.CONNECTED;
    }

    public void setConnected() {
        this.socketState = SocketState.CONNECTED;
        controllerView.setConnected(true);
    }

    public boolean isPending(){
        return socketState == SocketState.PENDING;
    }

    public boolean setPending(){
        return socketState == SocketState.PENDING;
    }
    public boolean isDisconnected() {
        return socketState == SocketState.DISCONNECTED;
    }

    public void setDisconnected() {
        this.socketState = SocketState.DISCONNECTED;
        controllerView.setConnected(false);
    }
    public void setSendKey(String key){
        sendKey = key;
    }
    public void setReceiveKey(String key){
        receiveKey = key;
    }
    public void connect(String serverIp, int portNum){
        setPending();
        setSendKey("CONNECT "+deviceName);
        setReceiveKey("CONNECTED");
        new Thread(new ConnectThread(serverIp, portNum)).start();
        
    }
    public void stop(){
        if(socket.isConnected()){
            sendMessage("DISCONNECT");
        }
        disconnect();
    }
    public void disconnect(){
        setDisconnected();
        try{
            input.close();
            output.close();
            socket.close();
        }catch( IOException e){
            e.printStackTrace();
        }
    }
    public void sendTcp(String message){
        if(socket.isConnected()){
            output.println(message+"#");
            output.flush();
        } else{
            disconnect();
        }
    }
    public void sendUdp(String message){
        try{
            int len = message.length();
            byte[] messageBytes = message.getBytes();
            DatagramPacket sendP = new DatagramPacket(messageBytes, len, inetAddress, portNum);
            datagramSocket.send(sendP);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void sendMessage(String message){
//        new Thread(new SendThread(message)).start();
        Message msg = Message.obtain();
        msg.obj = message;
        msg.setTarget(sendHandler);
        msg.sendToTarget();
    }
    class ConnectThread implements Runnable{
        private String ipAddress;
        private int portNum;
        ConnectThread(String ipAddress, int portNum){
            this.ipAddress = ipAddress;
            this.portNum = portNum;
        }

        @Override
        public void run() {
            switch (protocol){
                case TCP:
                    tcpConnect();
                    break;
                case UDP:
                    udpConnect();
                    break;
                default:
                    tcpConnect();
                    break;
            }
        }
        private void tcpConnect(){
            try{
                socket = new Socket(ipAddress, portNum);
                output = new PrintWriter(socket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                tcpHandshake();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void udpConnect(){
            try{
                datagramSocket = new DatagramSocket();
                inetAddress = InetAddress.getByName(ipAddress);
                local = InetAddress.getLocalHost();
                datagramInput = new DatagramSocket(portNum, local);

                udpHandshake();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        private void tcpHandshake(){
            sendTcp(sendKey);
            try{
                String response = input.readLine();
                if (response.equals(receiveKey)) {
                    endHandshake();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void udpHandshake(){
            sendUdp(sendKey);
            try{
                byte[] messageBytes = new byte[1500];
                DatagramPacket receiveP = new DatagramPacket(messageBytes, messageBytes.length);
                datagramInput.receive(receiveP);

                String response = new String(messageBytes, 0, receiveP.getLength());
                if (response.equals(receiveKey)) {
                    endHandshake();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void endHandshake(){
            Log.d("SOCKET", "Handshake Completed");
            setConnected();
            sendHandlerThread = new HandlerThread("Send");
            sendHandlerThread.start();
            sendHandler = new Handler(sendHandlerThread.getLooper()){
                public void handleMessage(Message msg){
                    String message = (String) msg.obj;
                    switch(protocol){
                        case TCP:
                            sendTcp(message);
                            break;
                        case UDP:
                            sendUdp(message);
                            break;
                    }
                }
            };
            //start a sending thread?
        }
    }
//    class SendThread implements Runnable{
//        public Handler messageHandler;
//        public void run(){
//            while(isConnected()){
//                Looper.prepare();
//                messageHandler = new Handler(){
//                    @Override
//                    public void handleMessage(@NonNull Message msg) {
//                        super.handleMessage(msg);
//                        switch(protocol){
//                            case TCP:
//                                sendTcp(msg.toString());
//                                break;
//                            case UDP:
//                                sendUdp(msg.toString());
//                                break;
//                        }
//                    }
//                };
//            }
//        }
//    }

}
