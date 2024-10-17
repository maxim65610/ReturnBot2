package io.proj3ct.ReturnBot1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageAndKeyboardLogic {
    RetrieveData retrieveData = new RetrieveData();
    private  Map<Long, Integer> id_testABI = new HashMap<>();
    private List<String> list = new ArrayList<>();
    public List<String> worksWithTestAPI(String messageText, Long userId, Map<Long, String> userStatesforTest, String data) {
        String currentState = userStatesforTest.get(userId);
        List<String> data_BD = new ArrayList<>();
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
                data_BD = arrayBDforTestABI(id_testABI.get(userId));
            }
            else if(data.equals("200"))
            {
                data_BD =arrayBDforTestABI(id_testABI.get(userId));
            }
            else if(data.equals("300")){
                data_BD = arrayBDforTestABI(id_testABI.get(userId));
            }
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_2");
        }
        else if("awaiting_testABI_2".equals(currentState)){
            System.out.println("1414");
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 1);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_3");

        }
        else if("awaiting_testABI_3".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 2);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_4");
        }
        else if("awaiting_testABI_4".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 3);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_5");
        }
        else if("awaiting_testABI_5".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 4);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_6");
        }
        else if("awaiting_testABI_6".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 5);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_7");
        }
        else if("awaiting_testABI_7".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 6);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_8");
        }
        else if("awaiting_testABI_8".equals(currentState)) {
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 7);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_9");
        }
        else if("awaiting_testABI_9".equals(currentState)){
            data_BD = arrayBDforTestABI(id_testABI.get(userId) + 8);
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_10");
        }
        else if("awaiting_testABI_10".equals(currentState)){
            System.out.println(list);
            userStatesforTest.remove(userId);
        }



        return data_BD;
    }
    public List<String> arrayBDforTestABI(int id_testABI){
        List<String> data_BD = new ArrayList<>();
        data_BD.add(retrieveData.getDataById(id_testABI, "question"));
        data_BD.add(retrieveData.getDataById(id_testABI, "answer1"));
        data_BD.add(retrieveData.getDataById(id_testABI, "answer2"));
        data_BD.add(retrieveData.getDataById(id_testABI, "answer3"));
        data_BD.add(retrieveData.getDataById(id_testABI, "cash1"));
        data_BD.add(retrieveData.getDataById(id_testABI, "cash2"));
        return data_BD;
    }
}
