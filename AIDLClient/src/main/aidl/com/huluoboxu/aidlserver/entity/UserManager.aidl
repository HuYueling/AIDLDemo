// UserManager.aidl
//第二类AIDL文件
//作用是定义方法接口
package com.huluoboxu.aidlserver.entity;
//导入所需要使用的非默认支持数据类型的包
import com.huluoboxu.aidlserver.entity.User;

interface UserManager {

    //所有的返回值前都不需要加任何东西，不管是什么数据类型
    List<User> getUsers();

    //传参时除了Java基本类型以及String，CharSequence之外的类型
    //都需要在前面加上定向tag，具体加什么量需而定
    //AIDL中的定向 tag 表示了在跨进程通信中数据的流向，
    //其中 in 表示数据只能由客户端流向服务端，
    //out 表示数据只能由服务端流向客户端，
    //而 inout 则表示数据可在服务端与客户端之间双向流通。
    //其中，数据流向是针对在客户端中的那个传入方法的对象而言的
    void addUser(in User user);
}
