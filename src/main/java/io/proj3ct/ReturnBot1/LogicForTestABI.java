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
    private RetrieveData retrieveData = new RetrieveData();

    /**
     * Карта, связывающая идентификаторы пользователей с идентификаторами тестов ABI.
     * Ключ: Идентификатор пользователя, Значение: Идентификатор строки в БД.
     */
    private Map<Long, Integer> idTestABI = new HashMap<>();

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

    private Map<Long, String> userStatesForTest = new HashMap<>();

    public TextForCommonMessage textForCommonMessage = new TextForCommonMessage();

    /**
     * Геттер для @param userStatesForTest
     * Если userStatesForTest не пустой,
     * возвращает его значение по ID.
     */
    public String getUserStatesForTest(Long chatID){
        if (userStatesForTest.isEmpty()) {
            return ("0");
        }
        else {
            return (userStatesForTest.get(chatID));
        }
    }
    /**
     * Очищает состояния пользователей.
     * Этот метод удаляет все состояния из карты userStatesForTest.
     */
    public void removeUserStatesForTest(Long chatID) {
        userStatesForTest.remove(chatID);
    }
    /**
     * Устанавливает результат теста для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     */
    public void setTestResult(Long userId, String result) {

        resultsTestAbi.put(userId, result);
    }
    /**
     * Получает результат теста для указанного идентификатора чата.
     *
     * @param chatID идентификатор чата
     * @return строка с результатом теста
     */
    public String getTestResult(Long chatID) {
        return resultsTestAbi.get(chatID);
    }
    /**
     * Получает название факультета из базы данных по заданному идентификатору.
     *
     * @param id_getfaculty идентификатор факультета
     * @return название факультета
     */
    private String getNameFacultyFromBD(int id_getfaculty){
        return retrieveData.getDataById(id_getfaculty, "cash3");
    }
    /**
     * Получает данные для теста по заданному идентификатору.
     *
     * @param id_testABI идентификатор строки в базе данных
     * @return список данных, включая вопрос,ответы и значения, для формирования ответа
     */
    private List<String> arrayBDforTestABI(int id_testABI){
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
     * Определяет, какой факультет наиболее подходит пользователю на основе его выборов.
     *
     * @param userID идентификатор пользователя
     * @param choiceABI выборы пользователя
     * @return название подходящего факультета
     */
    public String gettingResult(Long userID, Map<Long, List<String>> choiceABI){
        List<String> listofABIechoice= choiceABI.get(userID);


        int sizeListofABIechoice = listofABIechoice.size();
        List<String> listofABIechoiceWithoutGap= new ArrayList<>();
        for (String s : listofABIechoice) {
            listofABIechoiceWithoutGap.add(s.trim());
        }
        Map<Integer, Integer> countChoiceABI = new HashMap<>();
        final int idFromChoice = 1000;
        for (int i = idFromChoice; i < 1013; i++) {
            for (int j = 0; j < sizeListofABIechoice; j++) {
                if (listofABIechoiceWithoutGap.get(j).equals(String.valueOf(i))) {
                    countChoiceABI.merge(i, 1, Integer::sum);
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
        return getNameFacultyFromBD(idforchoiceABI);
    }
    /**
     * Обрабатывает входящие сообщения от пользователей и управляет состоянием теста.
     *
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param data данные, полученные с getCallbackQuery().getData()
     * @return список данных, полученных из базы данных для текущего состояния теста
     */
    public List<String> worksWithTestAPI(String messageText, Long userId,String data) {
        String currentState = userStatesForTest.get(userId);
        List<String> data_BD = new ArrayList<>();
        if (data.equals("100")) {
            idTestABI.put(userId, 101);
        } else if (data.equals("200")) {
            idTestABI.put(userId, 201);
        } else if (data.equals("300")) {
            idTestABI.put(userId, 301);
        } else if (!data.equals("-")) {
            choiceABI.putIfAbsent(userId, new ArrayList<>());
            choiceABI.get(userId).add(data);
        }
        if (messageText.equals("/testAbit") && userStatesForTest.isEmpty()) {
            userStatesForTest.put(userId, "awaiting_testABI_1");
        } else if ("awaiting_testABI_1".equals(currentState)) {
            if (data.equals("100")) {
                data_BD = arrayBDforTestABI(idTestABI.get(userId));
            } else if (data.equals("200")) {
                data_BD = arrayBDforTestABI(idTestABI.get(userId));
            } else if (data.equals("300")) {
                data_BD = arrayBDforTestABI(idTestABI.get(userId));
            }
            userStatesForTest.remove(userId);
            userStatesForTest.put(userId, "awaiting_testABI_2");
        } else if (currentState != null && currentState.startsWith("awaiting_testABI")) {
            int stepForAwaiting_testABI = Integer.parseInt(currentState.split("_")[2]);
            if (stepForAwaiting_testABI == 10) {
                resultsTestAbi.put(userId, gettingResult(userId, choiceABI));
                userStatesForTest.remove(userId);
                userStatesForTest.put(userId, "awaiting_testABI_11");
            } else {
                data_BD = arrayBDforTestABI(idTestABI.get(userId) + stepForAwaiting_testABI - 1);
                userStatesForTest.remove(userId);
                userStatesForTest.put(userId, "awaiting_testABI_" + (stepForAwaiting_testABI + 1));
            }
        }
        return data_BD;
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
}