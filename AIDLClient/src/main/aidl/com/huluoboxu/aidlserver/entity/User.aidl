// User.aidl
//第一类AIDL文件
//这个文件的作用是引入了一个序列化对象 User 供其他的AIDL文件使用
//注意：User.aidl与User.java的包名应当是一样的
package com.huluoboxu.aidlserver.entity;

//注意parcelable是小写
parcelable User;
