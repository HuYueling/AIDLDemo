package com.huluoboxu.aidlserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.huluoboxu.aidlserver.entity.User;
import com.huluoboxu.aidlserver.entity.UserManager;

import java.util.ArrayList;
import java.util.List;

public class AIDLService extends Service {
    public final String TAG = this.getClass().getSimpleName();

    //包含User对象的list
    private List<User> mUsers = new ArrayList<>();

    //由AIDL文件生成的UserManager
    private final UserManager.Stub mUserManager = new UserManager.Stub() {
        @Override
        public List<User> getUsers() throws RemoteException {
            synchronized (this) {
                Log.e(TAG, "invoking getUsers() method , now the list is : " + mUsers.toString());
                if (mUsers != null) {
                    return mUsers;
                }
                return new ArrayList<>();
            }
        }


        @Override
        public void addUser(User user) throws RemoteException {
            synchronized (this) {
                if (mUsers == null) {
                    mUsers = new ArrayList<>();
                }
                if (user == null) {
                    Log.e(TAG, "Book is null in In");
                    user = new User();
                }
                //尝试修改user的参数，主要是为了观察其到客户端的反馈
                user.setAge(23);
                if (!mUsers.contains(user)) {
                    mUsers.add(user);
                }
                //打印mBooks列表，观察客户端传过来的值
                Log.e(TAG, "invoking addUsers() method , now the list is : " + mUsers.toString());
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(getClass().getSimpleName(), "aidl service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(getClass().getSimpleName(), "aidl service onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getClass().getSimpleName(), String.format("on bind,intent = %s", intent.toString()));
        return mUserManager;
    }
}