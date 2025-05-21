package com.bolin.com.fasterxml.jackson.databind;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import one.util.streamex.StreamEx;
import java.util.ArrayList;
import java.util.List;

public class ReadJsonStringS1 {
    /**
     *  参考 ：
     *  		offerSyncBean.setCategory(StreamEx.of(jsonNode.get("categories").iterator())
     * 			.map(o -> o.get("category_name").asText())
     * 			.joining(","));
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String json = "{ \"events\": [ " +
            "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
            "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
            " ] }";

    private static final String json_error1 = "{ \"events222\": [ " +
            "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
            "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
            " ] }";

    private static JsonNode trueRootNode;
    private static JsonNode errorJsonNode;

    private static List<JsonNode> rootNodeList=new ArrayList<>();

    static {
        try {
            trueRootNode = mapper.readTree(json);
            errorJsonNode = mapper.readTree(json_error1);
            rootNodeList.add(trueRootNode);
            rootNodeList.add(errorJsonNode);
        } catch (Exception e) {
            // 推荐：打印或记录日志，避免吞异常
            e.printStackTrace();
        }
    }


    public ReadJsonStringS1() throws JsonProcessingException {
    }

    public   static void readFirstEventName() throws Exception {
        String json = "{ \"events\": [ " +
                "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
                "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
                " ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode eventsNode = rootNode.get("events");

        if (eventsNode != null && eventsNode.isArray() && eventsNode.size() > 0) {
            JsonNode firstEvent = eventsNode.get(0);
            JsonNode eventNameNode = firstEvent.get("event_name");

            if (eventNameNode != null) {
                String eventName = eventNameNode.asText();
                System.out.println("第一个 event_name: " + eventName);
            }
        }
    }

    public   static void readFirstEventName_2() throws Exception {
      rootNodeList.stream().forEach(rootNode->{
//          StreamEx.of(rootNode.path("events").iterator()).findFirst().map(o -> o.path("event_name")).ifPresent(firstEventName-> System.out.println("firstEventName: "+firstEventName));
          StreamEx.of(rootNode.path("events").iterator()).findFirst().map(o -> o.path("event_name")).ifPresentOrElse(firstEventName-> System.out.println("firstEventName: "+firstEventName),()-> System.out.println("没有找到对应的值啊"));
      });





    }

    /**
     * 		offerSyncBean.setCategory(StreamEx.of(jsonNode.get("categories").iterator())
     * 			.map(o -> o.get("category_name").asText())
     * 			.joining(","));
     * @throws Exception
     */
    public   static void readEventNameJoinList() throws Exception {
        String json = "{ \"events\": [ " +
                "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
                "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
                " ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode eventsNode = rootNode.get("events");

        if (eventsNode != null && eventsNode.isArray() && eventsNode.size() > 0) {
            String eventNamesString = StreamEx.of(eventsNode.iterator()).map(o -> o.get("event_name")).joining(",");
            System.out.println(" event_namesString: " + eventNamesString);
        }
    }

    public   static void readEventNameArrayList() throws Exception {
        String json = "{ \"events\": [ " +
                "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
                "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
                " ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode eventsNode = rootNode.get("events");

        List<String> eventNames = StreamEx.of(trueRootNode.path("events").iterator())
                .map(o -> o.path("event_name").asText(null))
                .nonNull()
                .toList();
        String jsonArrayStr = mapper.writeValueAsString(eventNames);  // ["event1", "event2"]
        System.out.println(jsonArrayStr);
    }



    public   static void readEventNameAndPayOutJoinList() throws Exception {
        String json = "{ \"events\": [ " +
                "{ \"e_id\": \"1\", \"event_name\": \"InitialEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPA\", \"payout\": \"2.00000\", \"conversion_point\": \"\" }," +
                "{ \"e_id\": \"2\", \"event_name\": \"SecondEvent\", \"e_tkn\": \"\", \"description\": \"\", \"payout_type\": \"CPC\", \"payout\": \"1.50000\", \"conversion_point\": \"\" }" +
                " ] }";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode eventsNode = rootNode.get("events");

        if (eventsNode != null && eventsNode.isArray() && eventsNode.size() > 0) {
            String eventNamesString = StreamEx.of(eventsNode.iterator()).map(o -> "envent_name:"+o.get("event_name")+","+"payout:"+o.get("payout")).joining(";");
            System.out.println(" event_namesString: " + eventNamesString);
        }
    }

    public   static void readEventNameAndPayOutJoinList_2() throws Exception {


        String eventNameJoin = StreamEx.of(trueRootNode.path("events").iterator()).map(o -> "envent_name:"+o.get("event_name")+","+"payout:"+o.get("payout")).joining(";");
        String errorEventNameJoin = StreamEx.of(errorJsonNode.path("events").iterator()).map(o -> "envent_name:"+o.get("event_name")+","+"payout:"+o.get("payout")).joining(";");
    }

    public   static void readEventNameAndPayOutJoinList_3() throws Exception {


        String eventNameJoin = StreamEx.of(trueRootNode.path("events").iterator()).map(o -> "envent_name:"+o.get("event_name")+","+"payout:"+o.path("payout")).joining(";");
        String errorEventNameJoin = StreamEx.of(errorJsonNode.path("events").iterator()).map(o -> "envent_name:"+o.get("event_name")+","+"payout:"+o.path("payout")).joining(";");
    }

    public static void main(String[] args) throws Exception {
//         readFirstEventName();
//        readEventNameJoinList();
//        readEventNameAndPayOutJoinList_2();
//        readFirstEventName_2();
        readEventNameArrayList();
    }
}
