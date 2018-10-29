package com.group.zhtx.webSocket;

import com.google.gson.Gson;
import com.group.zhtx.message.IMessage;
import com.group.zhtx.message.MessageFactory;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class WebSocketUtil {

    private static Logger logger = LoggerFactory.getLogger(WebSocketUtil.class);

    public static WebSocket decodeJosn(ByteBuffer buffer){


        try {
            Charset charset = Charset.forName("utf-8");
            CharsetDecoder decoder = charset.newDecoder();
            CharBuffer charBuffer = decoder.decode(buffer);

            return decodeJson(charBuffer);
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static WebSocket decodeJson(CharBuffer buffer){

        String stringResult = buffer.toString();

        if(!validOperateIdIsExit(stringResult)){
            return new WebSocket(null,null);
        }

        JSONObject jsonObject = JSONObject.fromObject(stringResult);
        int operateId = jsonObject.getInt("operateId");
        IMessage IMessage = MessageFactory.newMessageByOperateCode(operateId,stringResult);

        WebSocket webSocket = new WebSocket(operateId, IMessage);
        return webSocket;
    }

    public static WebSocket decodeJson(String text){

        //String stringResult = buffer.toString();
        if(!validOperateIdIsExit(text)){
            return new WebSocket(null,null);
        }

        JSONObject jsonObject = JSONObject.fromObject(text);
        int operateId = jsonObject.getInt("operateId");
        IMessage IMessage = MessageFactory.newMessageByOperateCode(operateId,text);

        WebSocket webSocket = new WebSocket(operateId, IMessage);
        return webSocket;

    }

    public static String encodeJson(WebSocket webSocket){
        String s=new Gson().toJson(webSocket.getIMessage());
        System.out.println(s);
        return s;
    }

    public static boolean validOperateIdIsExit(String text){
        int operateIdOfIndex = text.indexOf("operateId");
        if(operateIdOfIndex < 0){
            return false;
        }
        return true;
    }
}
