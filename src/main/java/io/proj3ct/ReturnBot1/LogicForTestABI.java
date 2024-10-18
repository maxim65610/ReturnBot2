package io.proj3ct.ReturnBot1;

import java.util.*;
/**
 * Класс LogicForTestABI реализует логику обработки теста ABI для пользователей.
 * Он управляет состояниями пользователей, выбирает данные для теста и определяет подходящий факультет на основе выборов.
 */
public class LogicForTestABI {
    /**
     * Экземпляр класса RetrieveData, используемый для получения данных из базы данных.
     */
    RetrieveData retrieveData = new RetrieveData();

    /**
     * Карта, связывающая идентификаторы пользователей с идентификаторами тестов ABI.
     * Ключ: Идентификатор пользователя, Значение: Идентификатор строки в БД.
     */
    private Map<Long, Integer> id_testABI = new HashMap<>();

    /**
     * Карта, хранящая выборы пользователей во время теста ABI.
     * Ключ: Идентификатор пользователя, Значение: Список выбранных ответов.
     */
    private Map<Long, List<String>> choiceABI = new HashMap<>();

    /**
     * Карта, хранящая результаты теста ABI для каждого пользователя.
     * Ключ: Идентификатор пользователя, Значение: Название факультета, связанного с результатами пользователя.
     */
    private Map<Long, String> resultsTestAbi = new HashMap<>();


    /**
     * Обрабатывает входящие сообщения от пользователей и управляет состоянием теста.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param userStatesforTest состояние теста для каждого пользователя
     * @param data данные, полученные с getCallbackQuery().getData()
     * @return список данных, полученных из базы данных для текущего состояния теста
     */
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
            choiceABI.putIfAbsent(userId, new ArrayList<>());
            choiceABI.get(userId).add(data);

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
            resultsTestAbi.put(userId, gettingresult(userId, choiceABI));
            userStatesforTest.remove(userId);
            userStatesforTest.put(userId, "awaiting_testABI_11");
        }

        return data_BD;
    }
    /**
     * Получает данные для теста по заданному идентификатору.
     *
     * @param id_testABI идентификатор строки в базе данных
     * @return список данных, включая вопрос,ответы и значения, для формирования ответа
     */
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

    /**
     * Получает название факультета из базы данных по заданному идентификатору.
     *
     * @param id_getfaculty идентификатор факультета
     * @return название факультета
     */
    public String getnamefacultyfromBD(int id_getfaculty){
        return retrieveData.getDataById(id_getfaculty, "cash3");
    }

    /**
     * Определяет, какой факультет наиболее подходит пользователю на основе его выборов.
     *
     * @param userID идентификатор пользователя
     * @param choiceABI выборы пользователя
     * @return название подходящего факультета
     */
    public String gettingresult(Long userID, Map<Long, List<String>> choiceABI){
        List<String> listofABIechoice= choiceABI.get(userID);
        int sizeListofABIechoice = listofABIechoice.size();
        List<String> listofABIechoiceWithoutGap= new ArrayList<>();
        for(int i =0 ;i < sizeListofABIechoice; i++){
            listofABIechoiceWithoutGap.add(listofABIechoice.get(i).trim());
        }
        Map<Integer, Integer> countChoiceABI = new HashMap<>();
        for(int i = 1000; i < 1013; i++) {
            for (int j = 0; j < sizeListofABIechoice; j++) {

                if (listofABIechoiceWithoutGap.get(j).equals(String.valueOf(i))) {
                    countChoiceABI.put(i, countChoiceABI.getOrDefault(i, 0) + 1);
                }

            }
        }

        int maxcountchoiceABI = 0;
        int idforchoiceABI = 0;
        for (Map.Entry<Integer, Integer> entry : countChoiceABI.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            if(value > maxcountchoiceABI){
                idforchoiceABI = key;
                maxcountchoiceABI = value;
            }
        }
        return getnamefacultyfromBD(idforchoiceABI);

    }
    /**
     * Возвращает результат теста для заданного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return строка с результатом теста
     */
    public String getResult(long chatID){
        return "Вам больше всего подходит факультет: " + resultsTestAbi.get(chatID);
    }

    /**
     * Сетер для поля resultsTestAbi
     * @param results входящий results
     */
    public void setResultsTestAbi(Map<Long, String> results) {
        this.resultsTestAbi = results;
    }
}
