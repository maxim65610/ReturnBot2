package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAndKeyboardLogic {
    RetrieveData retrieveData = new RetrieveData();
    private  Map<Long, Integer> id_testABI = new HashMap<>();
    List<String> list = new ArrayList<>();
    public String[] worksWithTestAPI(String messageText, Long userId, Map<Long, String> userStatesforTest, String data) {
        String currentState = userStatesforTest.get(userId);
        String[] valuesBD = new String[6];
        if(data.equals("100")){
            id_testABI.put(userId, 101);
        }
        else if(data.equals("200")){
            id_testABI.put(userId, 201);
        }
        else if(data.equals("300")){
            id_testABI.put(userId, 301);
        }
        else if(!data.equals("-")){
            list.add(data);
        }


        if(messageText.equals("/test") && userStatesforTest.isEmpty()){
            userStatesforTest.put(userId, "awaiting_testABI_1");
        }
        else if("awaiting_testABI_1".equals(currentState)){
            if(data.equals("100")){
                valuesBD = arrayBDforTestABI(id_testABI.get(userId));
            }
            else if(data.equals("200"))
            {
                valuesBD =arrayBDforTestABI(id_testABI.get(userId));
            }
            else if(data.equals("300")){
                valuesBD = arrayBDforTestABI(id_testABI.get(userId));
            }
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_2");
        }
        else if("awaiting_testABI_2".equals(currentState)){
            System.out.println("1414");
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 1);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_3");

        }
        else if("awaiting_testABI_3".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 2);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_4");
        }
        else if("awaiting_testABI_4".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 3);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_5");
        }
        else if("awaiting_testABI_5".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 4);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_6");
        }
        else if("awaiting_testABI_6".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 5);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_7");
        }
        else if("awaiting_testABI_7".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 6);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_8");
        }
        else if("awaiting_testABI_8".equals(currentState)) {
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 7);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_9");
        }
        else if("awaiting_testABI_9".equals(currentState)){
            valuesBD = arrayBDforTestABI(id_testABI.get(userId) + 8);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_10");
        }
        else if("awaiting_testABI_10".equals(currentState)){
            System.out.println(list);
        }



        return valuesBD;
    }
    public String[] arrayBDforTestABI(int id_testABI){
        String[] valuesBD = new String[6];
        valuesBD[0] = retrieveData.getDataById(id_testABI, "question");
        valuesBD[1] = retrieveData.getDataById(id_testABI, "answer1");
        valuesBD[2] = retrieveData.getDataById(id_testABI, "answer2");
        valuesBD[3] = retrieveData.getDataById(id_testABI, "answer3");
        valuesBD[4] = retrieveData.getDataById(id_testABI, "cash1");
        valuesBD[5] = retrieveData.getDataById(id_testABI, "cash2");
        return valuesBD;
    }
}
