package com.bolin.group1.dir1.json.jackSon;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JacksonJsonTraversal {

    public static void traverse(JsonNode node, String parentKey) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String key = parentKey.isEmpty() ? field.getKey() : parentKey + "." + field.getKey();
                traverse(field.getValue(), key);
            }
        } else if (node.isArray()) {
            for (int i = 0; i < node.size(); i++) {
                traverse(node.get(i), parentKey + "[" + i + "]");
            }
        } else {
            System.out.println(parentKey + " : " + node.asText());
        }
    }

    public static void main(String[] args) throws IOException {
        String jsonStr = """
            {
              "user": {
                "id": 1,
                "name": "Tom",
                "address": {
                  "city": "New York",
                  "zipcode": "10001"
                }
              },
              "active": [true,true,false]
            }
        """;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonStr);
        traverse(root, "");
    }
}
