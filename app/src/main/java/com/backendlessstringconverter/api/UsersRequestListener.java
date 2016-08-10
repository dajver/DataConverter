package com.backendlessstringconverter.api;

import android.content.Context;
import android.util.Log;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendlessstringconverter.api.models.Users;
import com.backendlessstringconverter.components.ProgressDialogHelper;

import java.util.List;

/**
 * Created by lfqdt on 19.07.2016.
 */
public class UsersRequestListener implements AsyncCallback<BackendlessCollection<Users>> {

    private static final int PAGE_SIZE = 100;

    private static UsersRequestListener instance;
    private Context context;
    private OnUsersFetchedListener onUsersFetchedListener;

    public static UsersRequestListener with(Context context) {
        try {
            instance = UsersRequestListener.class.newInstance();
            instance.context = context;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public UsersRequestListener getUsers() {
        ProgressDialogHelper.showProgress(context);
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setPageSize(PAGE_SIZE);
        Backendless.Persistence.of(Users.class).find(dataQuery, this);
        return instance;
    }

    @Override
    public void handleResponse(BackendlessCollection<Users> response) {
        ProgressDialogHelper.dismissProgress();
        onUsersFetchedListener.onFetchedUsers(response.getData());
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        ProgressDialogHelper.dismissProgress();
        Log.e("getUsers", fault.toString());
    }

    public UsersRequestListener setOnUsersFetchedListener(OnUsersFetchedListener onUsersFetchedListener) {
        this.onUsersFetchedListener = onUsersFetchedListener;
        return instance;
    }

    public interface OnUsersFetchedListener {
        void onFetchedUsers(List<Users> users);
    }
}
