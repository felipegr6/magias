package br.com.fgr.magias.model;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

public class ControlCamera {

    public static final String LUMUS = "lumus";
    public static final String NOX = "nox";

    private boolean flashOn = false;

    private Camera cam;
    private Camera.Parameters param;

    private Context context;

    public ControlCamera(Context context) {
        this.context = context;
    }

    public void openCamera() {

        cam = Camera.open();
        param = cam.getParameters();

    }

    public void closeCamera() {

        cam.stopPreview();
        cam.release();

    }

    public void lightOn() {

        String mode = Camera.Parameters.FLASH_MODE_TORCH;

        flashOn = true;
        control(mode);

    }

    public void lightOff() {

        String mode = Camera.Parameters.FLASH_MODE_OFF;

        flashOn = false;
        control(mode);

    }

    private void control(String mode) {

        param.setFlashMode(mode);
        cam.setParameters(param);
        cam.startPreview();

    }

    public boolean isFlashOn() {
        return this.flashOn;
    }

    public boolean verifyCamera() {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}