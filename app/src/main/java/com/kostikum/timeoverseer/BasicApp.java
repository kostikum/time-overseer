package com.kostikum.timeoverseer;

import android.app.Application;

public class BasicApp extends Application {

    public ProcessRepository getRepository() {
        return ProcessRepository.getInstance(this);
    }
}
