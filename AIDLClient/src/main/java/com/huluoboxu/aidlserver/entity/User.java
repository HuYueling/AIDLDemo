package com.huluoboxu.aidlserver.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @Auther: admin HuYueling
 * @E-mail: 1228205445@qq.com
 * @Date: 2020/10/19 12:02:02
 * <p/>
 * @Description:
 * @Version:
 */
public class User implements Parcelable {
    private String username;
    private int age;

    public User() {

    }

    protected User(Parcel in) {
        username = in.readString();
        age = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeInt(age);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username:'" + username + '\'' +
                ", age:" + age +
                '}';
    }
}