package com.group.zhtx.webSocket;

import com.google.gson.*;
import com.group.zhtx.message.IMessage;
import com.group.zhtx.message.MessageFactory;
import com.group.zhtx.model.Message;
import com.group.zhtx.util.common.WebSocketOperateUtil;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class WebSocketUtil {

    private static Logger logger = LoggerFactory.getLogger(WebSocketUtil.class);

    public static WebSocket decodeJosn(ByteBuffer buffer) {


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

    public static WebSocket decodeJson(CharBuffer buffer) {

        String stringResult = buffer.toString();

        if (!validOperateIdIsExit(stringResult)) {
            return new WebSocket(null, null);
        }

        JSONObject jsonObject = JSONObject.fromObject(stringResult);
        int operateId = jsonObject.getInt("operateId");
        IMessage IMessage = MessageFactory.newMessageByOperateCode(operateId, stringResult);
        System.out.println("客户端发来的参数：" + stringResult);
        WebSocket webSocket = new WebSocket(operateId, IMessage);
        return webSocket;
    }


    public static WebSocket decodeJson(String text) {

        //String stringResult = buffer.toString();
        if (!validOperateIdIsExit(text)) {
            return new WebSocket(null, null);
        }

        JSONObject jsonObject = JSONObject.fromObject(text);
        int operateId = jsonObject.getInt("operateId");
        IMessage IMessage = MessageFactory.newMessageByOperateCode(operateId, text);

        WebSocket webSocket = new WebSocket(operateId, IMessage);
        return webSocket;

    }

    public static String encodeJson(WebSocket webSocket) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Message.class, new JsonSerializer<Message>() {
            @Override
            public JsonElement serialize(Message src, Type typeOfSrc,
                                         JsonSerializationContext context) {
                JsonObject o = new JsonObject();
                o.addProperty("messageGroupId", src.getGroup().getUuid());
                if (src.getUser().getPortrait() == null || src.getUser().getPortrait().equals("")) {
                    o.addProperty("userPortrait", WebSocketOperateUtil.Portrait_Image);
                } else {
                    o.addProperty("userPortrait", WebSocketOperateUtil.Portrait_Url + src.getUser().getPortrait() + WebSocketOperateUtil.Portrait_Suffix);
                }
                o.addProperty("messageUserName", src.getUser().getName());
                o.addProperty("messageUserId", src.getUser().getUuid());
                o.addProperty("messageContent", src.getContent());
                o.addProperty("messageTime", src.getSendTime().getTime());
                return o;
            }
        }).create();
        String s = gson.toJson(webSocket.getIMessage());
        System.out.println("这是我编码返回给客户端的信息：" + s);
        return s;
    }

    public static boolean validOperateIdIsExit(String text) {
        int operateIdOfIndex = text.indexOf("operateId");
        if (operateIdOfIndex < 0) {
            return false;
        }
        return true;
    }
}
