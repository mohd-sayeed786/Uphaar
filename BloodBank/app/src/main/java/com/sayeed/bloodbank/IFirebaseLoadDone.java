package com.sayeed.bloodbank;

import java.util.List;

public interface IFirebaseLoadDone {
    void onFirebaseLoadUserNameDone(List<String> firstEmail);
    void onFirebaseLoadFailed(String message);
}
