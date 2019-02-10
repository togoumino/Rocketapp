package com.example.sanji.aninterface.socketManage;

import com.example.sanji.aninterface.ConfigBasic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import io.reactivex.Observable;
import io.reactivex.Observer;


public class ObservableSocket extends Observable<String> {
    private BufferedReader br;
    @Override
    public void subscribeActual(Observer<? super String> observer) {
        try {
            Socket socket = new Socket(ConfigBasic.IP_ADDRESS, ConfigBasic.SOCKET_PORT);
            this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                try {
                    String data = this.br.readLine();
                    if (data != null) {
                        observer.onNext(data);
                    }
                } catch (IOException e) {
                    observer.onError(e);
                }
            }
        } catch (IOException e) {
            observer.onError(e);
        }
    }
}
