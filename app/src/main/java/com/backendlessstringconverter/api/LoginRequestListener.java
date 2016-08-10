package com.backendlessstringconverter.api;

import android.app.Activity;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendlessstringconverter.components.ProgressDialogHelper;
import com.facebook.CallbackManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lfqdt on 10.08.2016.
 */
public class LoginRequestListener implements AsyncCallback<BackendlessUser> {

    private static LoginRequestListener instance;
    private static Activity context;
    private CallbackManager callbackManager;
    private OnUserLoggedInListener onUserLoggedInListener;

    public static LoginRequestListener with(Activity context) {
        try {
            instance = LoginRequestListener.class.newInstance();
            instance.context = context;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public LoginRequestListener login() {
        Map<String, String> facebookFieldsMappings = new HashMap<>();
        facebookFieldsMappings.put("email", "email");
        facebookFieldsMappings.put("first_name", "firstName");
        facebookFieldsMappings.put("last_name", "lastName");
        facebookFieldsMappings.put("gender", "gender");
        facebookFieldsMappings.put("name", "name");
        facebookFieldsMappings.put("picture", "avatar");
        facebookFieldsMappings.put("birthday", "birthday");

        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");
        permissions.add("user_birthday");
        permissions.add("user_photos");

        Backendless.UserService.loginWithFacebookSdk(context, facebookFieldsMappings, permissions, callbackManager, this);
        ProgressDialogHelper.showProgress(context);
        return instance;
    }

    public LoginRequestListener setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
        return instance;
    }

    @Override
    public void handleResponse(BackendlessUser response) {
        ProgressDialogHelper.dismissProgress();
        Backendless.UserService.setCurrentUser(response);
        onUserLoggedInListener.successAuth(response);
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        ProgressDialogHelper.dismissProgress();
        Log.e("login", fault.toString());
    }

    public void setOnUserLoggedInListener(OnUserLoggedInListener onUserLoggedInListener) {
        this.onUserLoggedInListener = onUserLoggedInListener;
    }

    public interface OnUserLoggedInListener {
        void successAuth(BackendlessUser response);
    }
}
