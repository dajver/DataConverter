package com.backendlessstringconverter.api;

import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendlessstringconverter.components.ProgressDialogHelper;

/**
 * Created by lfqdt on 19.07.2016.
 */
public class UpdateUserRequestListener implements AsyncCallback<BackendlessUser> {

    private static UpdateUserRequestListener instance;
    private OnUpdateUserListener onUpdateUserListener;
    private Context context;

    private String viewedUsers;
    private String objectId;

    public static UpdateUserRequestListener with(Context context) {
        try {
            instance = UpdateUserRequestListener.class.newInstance();
            instance.context = context;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public UpdateUserRequestListener updateUser() {
        ProgressDialogHelper.showProgress(context);
        BackendlessUser user = new BackendlessUser();
        user.setProperty( "objectId", objectId );
        user.setProperty("viewedUsers", viewedUsers);
        Backendless.UserService.update(user, this);
        return instance;
    }

    @Override
    public void handleResponse(BackendlessUser response) {
        ProgressDialogHelper.dismissProgress();
        Backendless.UserService.setCurrentUser(response);
        onUpdateUserListener.onUpdatedUser(response);
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        ProgressDialogHelper.dismissProgress();
        Log.e("updateUser", fault.toString());
    }

    public UpdateUserRequestListener setViewedUsers(String viewedUsers) {
        this.viewedUsers = viewedUsers;
        return instance;
    }

    public UpdateUserRequestListener setObjectId(String objectId) {
        this.objectId = objectId;
        return instance;
    }

    public UpdateUserRequestListener setUpdateUserRequestListener(OnUpdateUserListener onUpdateUserListener) {
        this.onUpdateUserListener = onUpdateUserListener;
        return instance;
    }

    public interface OnUpdateUserListener {
        void onUpdatedUser(BackendlessUser user);
    }
}
