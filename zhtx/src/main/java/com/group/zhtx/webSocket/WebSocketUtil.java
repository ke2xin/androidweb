package com.group.zhtx.webSocket;

import com.google.gson.Gson;
import com.group.zhtx.message.Message;
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

        Message message = MessageFactory.newMessageByOperateCode(operateId,stringResult);
        WebSocket webSocket = new WebSocket(operateId, message);

        return webSocket;
    }

    public static String encodeJson(WebSocket webSocket){
        return new Gson().toJson(webSocket.getMessage());
    }
}
