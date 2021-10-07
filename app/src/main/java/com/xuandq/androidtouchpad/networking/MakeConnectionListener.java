package com.xuandq.androidtouchpad.networking;

public interface MakeConnectionListener {
    void onSuccess(Client client);
    void onError(Exception e);
}
