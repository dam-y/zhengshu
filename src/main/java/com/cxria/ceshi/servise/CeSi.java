package com.cxria.ceshi.servise;

import com.cxria.ceshi.util.HttpXml4Client;
import net.sf.json.JSONObject;
import org.apache.http.entity.StringEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CeSi {

    public static void main(String[] args) {
            JSONObject user = new JSONObject();
            /*User user=new User();
            user.setId("1");
            user.setName("小王");*/
            user.put("name", "小王");
            user.put("id","25");
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            String serverUrl = "http://localhost:5555/hello";
            StringEntity stringEntity = new StringEntity(user.toString(), "UTF-8");
            String post = HttpXml4Client.post(serverUrl, stringEntity, headers);
            System.out.println(post);
            System.out.println("你好");
    }
}
