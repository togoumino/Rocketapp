package com.example.sanji.aninterface.socketManage;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Thread;
import android.util.*;

import com.example.sanji.aninterface.ObjectToUpdateInterface;
import com.example.sanji.aninterface.rest.Client;

public class SocketModule extends Thread {
    private Socket socket;
    private BufferedReader br;
    private String adresseIP;
    private int port;
    private ObjectToUpdateInterface updated;

    public SocketModule(String adresseIP, int port, ObjectToUpdateInterface updated) {
        this.updated = updated;
        this.adresseIP = adresseIP;
        this.port = port;
    }

    public void run() {
        try {
            this.socket = new Socket(this.adresseIP, this.port);
            this.br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            String actuData;
            try {
                actuData = this.br.readLine();
                if (actuData != null) {
                    this.updated.update(actuData);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
