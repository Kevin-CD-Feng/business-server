package com.xtxk.cn.utils.ip;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class PingUtil {

    /**
     * 检测 ip 和 端口 是否能连接
     * @param host
     * @param port
     * @param timeOut 多少毫秒超时
     * @return
     */
    public static boolean connect(String host, int port, int timeOut){
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port),timeOut);
            //boolean res = socket.isConnected();//通过现有方法查看连通状态
        } catch (IOException e) {
            //当连不通时，直接抛异常，异常捕获即可
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(socket!=null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        String ipAddress = "127.0.0.1";
        //System.out.println(ping(ipAddress, 5, 3000));

        String host = "172.16.14.200";
        int port = 9910;

        System.out.println(connect(host, port, 3000));
    }
}
