package com.droidrocks.demos.helloui.authentication;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by hollisinman on 12/31/17.
 */

// Singleton for package access to banned and allowed
class AppAccess {

    private HashMap<String, String> allowed;
    private HashSet<String> banned;

    private static AppAccess instance = new AppAccess();

    protected static AppAccess getInstance() {
        return instance;
    }

    private AppAccess() {
        allowed = new HashMap<>();
        banned = new HashSet<>();

        allowed.put("hollis@droid.rocks", "123");
        allowed.put("kevin.walker@droid.rocks", "123");
        allowed.put("vyom.nautiyal@droid.rocks", "123");

        banned.add("inman.hollis@gmail.com");
    }

    protected HashMap<String,String> getAllowed() {
        return allowed;
    }

    protected void setAllowed(HashMap<String, String> allowed) {
        this.allowed = allowed;
    }

    protected HashSet<String> getBanned() {
        return banned;
    }

    protected void setBanned(HashSet<String> banned) {
        this.banned = banned;
    }
}
