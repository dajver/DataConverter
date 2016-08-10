package com.backendlessstringconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.backendless.BackendlessUser;
import com.backendlessstringconverter.api.LoginRequestListener;
import com.backendlessstringconverter.api.UpdateUserRequestListener;
import com.backendlessstringconverter.api.UsersRequestListener;
import com.backendlessstringconverter.api.models.Users;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements UsersRequestListener.OnUsersFetchedListener,
        UpdateUserRequestListener.OnUpdateUserListener, FacebookCallback<LoginResult>, LoginRequestListener.OnUserLoggedInListener {

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);
    }

    public void loginClick(View v) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday", "user_photos"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        LoginRequestListener.with(this)
                .setCallbackManager(callbackManager)
                .login()
                .setOnUserLoggedInListener(this);
    }

    @Override
    public void onCancel() { }

    @Override
    public void onError(FacebookException error) {
        error.printStackTrace();
    }

    @Override
    public void successAuth(BackendlessUser response) {
        UsersRequestListener.with(this)
                .getUsers()
                .setOnUsersFetchedListener(this);
    }

    public static BigInteger[] getViewedUsers(Users user) {
        Pattern p = Pattern.compile("\\[\"([^)\"]+)");
        if(!TextUtils.isEmpty(user.getViewedUsers())) {
            Matcher m = p.matcher(user.getViewedUsers());
            if (m.matches()) {
                String[] viewed = m.group(1).split(",");
                BigInteger[] viewedList = new BigInteger[viewed.length];
                for(int i = 0; i < viewed.length; i++) {
                    viewedList[i] = BigInteger.valueOf(Long.valueOf(viewed[i]));
                }
                return viewedList;
            }
        }
        return new BigInteger[0];
    }

    @Override
    public void onFetchedUsers(List<Users> users) {
        UpdateUserRequestListener.with(this)
                .setObjectId(users.get(0).getObjectId())
                .setViewedUsers(getViewedUsers(users.get(1)).toString())
                .updateUser()
                .setUpdateUserRequestListener(this);
    }

    @Override
    public void onUpdatedUser(BackendlessUser user) {
        Log.e("aaaa", "finished " + user.getProperty("viewedUsers"));
    }
}
