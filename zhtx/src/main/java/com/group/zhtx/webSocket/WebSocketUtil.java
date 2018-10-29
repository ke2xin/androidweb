package com.group.zhtx.webSocket;

import com.google.gson.Gson;
import com.group.zhtx.message.IMessage;
import com.group.zhtx.message.MessageFactory;
import net.sf.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class WebSocketUtil {

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

        JSONObject jsonObject = JSONObject.fromObject(stringResult);
        int operateId = jsonObject.getInt("operateId");
        System.out.println("我在这里"+jsonObject+"\t"+stringResult+"operateId="+operateId);
        IMessage IMessage = MessageFactory.newMessageByOperateCode(operateId,stringResult);
        System.out.println("IMessage="+IMessage);
        WebSocket webSocket = new WebSocket(operateId, IMessage);

        return webSocket;
    }

    public static String encodeJson(WebSocket webSocket){
        String s=new Gson().toJson(webSocket.getIMessage());
        System.out.println(s);
        return s;
    }
}
