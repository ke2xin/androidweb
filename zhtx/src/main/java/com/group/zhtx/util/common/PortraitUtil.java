package com.group.zhtx.util.common;

import java.io.IOException;

public class PortraitUtil {
    /**
     * 编码
     *
     * @param bstr
     * @return
     */
    public static String encode(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * @param str
     * @return
     */
    public static byte[] decode(String str) {
//            byte []data=null;
//            FileInputStream fis=new FileInputStream(p);
//            data=new byte[fis.available()];
//            fis.read(data);
//            fis.close();
//            System.out.println(encoder.encode(data));
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    public static String strTo16(String s) {//将字符串转换成16进制字符串
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
