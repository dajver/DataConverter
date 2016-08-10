package com.backendlessstringconverter.api.models;

import com.backendless.BackendlessUser;

/**
 * Created by lfqdt on 10.08.2016.
 */
public class Users extends BackendlessUser {

    private String viewedUsers;
    private String objectId;

    public String getViewedUsers() {
        return viewedUsers;
    }

    public void setViewedUsers(String viewedUsers) {
        this.viewedUsers = viewedUsers;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
