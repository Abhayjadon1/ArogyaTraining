package com.ispl.ekalarogya.training.app_interfaces;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by sonu on 24/10/17.
 */

public interface RetroAPICallback {

    public void onResponse(Call<?> call, Response<?> response, int requestCode, Object request);

    public void onFailure(Call<?> call, Throwable t, int requestCode, Object request);

    public void onNoNetwork(int requestCode);
}
