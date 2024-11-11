package io.proj3ct.ReturnBot1;

import java.util.*;

/**
 * LogicForTestABI реализует логику обработки теста для абитуриентов (ABI).
 * Он управляет состояниями пользователей, выбирает данные для теста
 * и определяет подходящий факультет на основе выборов пользователя.
 */
public class LogicForTestABI {
    private final RetrieveData retrieveData = new RetrieveData();
    private final TextForMessage textForMessage = new TextForMessage();
    private Map<Long, Integer> idTestABI = new HashMap<>();
    private Map<Long, List<String>> choiceABI = new HashMap<>();
    private Map<Long, String> resultsTestAbi = new HashMap<>();
    private Map<Long, String> userStatesForTest = new HashMap<>();
    /**
     * Получает текущее состояние теста для указанного пользователя.
     * @param chatID идентификатор пользователя
     * @return состояние пользователя, или "0", если состояние отсутствует
     */
    public String getUserStatesForTest(Long chatID) {
        return userStatesForTest.getOrDefault(chatID, "0");
    }
    /**
     * Очищает состояние указанного пользователя.
     * @param chatID идентификатор пользователя
     */
    public void removeUserStatesForTest(Long chatID) {
        userStatesForTest.remove(chatID);
    }

    /**
     * Получает название факультета из базы данных по заданному идентификатору.
     * @param id_getfaculty идентификатор факультета
     * @return название факультета
     */
    private String getNameFacultyFromBD(int id_getfaculty) {
        return retrieveData.getDataById(id_getfaculty, "cash3");
    }
    /**
     * Вызывает worksWithTestABI
     */
    public List<String> getDataBd(String messageText, Long userId, String data) {
        return worksWithTestABI(messageText, userId, data);
    }
    /**
     * Возвращает результат теста для указанного идентификатора чата.
     * @param chatID идентификатор чата
     * @return строка с результатом теста
     */
    public String getResult(long chatID) {
        return textForMessage.handleMessage("resultTestABI") + resultsTestAbi.get(chatID);
    }
    /**
     * Получает данные для теста по заданному идентификатору.
     * @param id_testABI идентификатор строки в базе данных
     * @return список данных, включая вопрос, ответы и связанные значения
     */
    private List<String> arrayBdForTestABI(int id_testABI) {
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
     * @param userID идентификатор пользователя
     * @param choiceABI выборы пользователя
     * @return название подходящего факультета
     */
    private String gettingResult(Long userID, Map<Long, List<String>> choiceABI) {
        List<String> listofABIechoice = choiceABI.get(userID);
        List<String> listofABIechoiceWithoutGap = new ArrayList<>();
        for (String s : listofABIechoice) {
            listofABIechoiceWithoutGap.add(s.trim());
        }
        Map<Integer, Integer> countChoiceABI = new HashMap<>();
        final int idFromChoice = 1000; //в базе данных начиная с этого id лежат названия факультетов
        final int idEndChoice = 1013; // в базе данных это после последний id, по которому лежит навание факультета
        //  то есть этот цикл пробегается по всем id , по которым лежат названия факультетов
        for (int i = idFromChoice; i < idEndChoice; i++) {
            for (String choice : listofABIechoiceWithoutGap) {
                if (choice.equals(String.valueOf(i))) {
                    countChoiceABI.merge(i, 1, Integer::sum);
                }
            }
        }
        int maxcountchoiceABI = 0;
        int idforchoiceABI = 0;

        for (Map.Entry<Integer, Integer> entry : countChoiceABI.entrySet()) {
            if (entry.getValue() > maxcountchoiceABI) {
                idforchoiceABI = entry.getKey();
                maxcountchoiceABI = entry.getValue();
            }
        }
        return getNameFacultyFromBD(idforchoiceABI);
    }
    /**
     * Обрабатывает входящие сообщения от пользователей и управляет состоянием теста.
     * @param messageText текст сообщения от пользователя
     * @param userId идентификатор пользователя
     * @param data данные, полученные с getCallbackQuery().getData()
     * @return список данных, полученных из базы данных для текущего состояния теста
     */
    private List<String> worksWithTestABI(String messageText, Long userId, String data) {
        String currentState = userStatesForTest.get(userId);
        List<String> dataBD = new ArrayList<>();

        // Устанавливаем идентификатор теста в зависимости от выбора пользователя
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
        // Обрабатываем переходы состояний теста
        if (messageText.equals("/testAbit") && (!userStatesForTest.containsKey(userId))) {
            userStatesForTest.put(userId, "awaiting_testABI_1");
        } else if ("awaiting_testABI_1".equals(currentState)) {
            dataBD = arrayBdForTestABI(idTestABI.get(userId));
            userStatesForTest.remove(userId);
            userStatesForTest.put(userId, "awaiting_testABI_2");
        } else if (currentState != null && currentState.startsWith("awaiting_testABI")) {
            int stepForAwaiting_testABI = Integer.parseInt(currentState.split("_")[2]);
            if (stepForAwaiting_testABI == 10) {
                resultsTestAbi.put(userId, gettingResult(userId, choiceABI));
                userStatesForTest.remove(userId);
                dataBD.add(textForMessage.handleMessage("userPassedTest"));
                userStatesForTest.put(userId, "awaiting_testABI_11");
            } else {
                dataBD = arrayBdForTestABI(idTestABI.get(userId) + stepForAwaiting_testABI - 1);
                userStatesForTest.remove(userId);
                userStatesForTest.put(userId, "awaiting_testABI_" + (stepForAwaiting_testABI + 1));
            }
        }
        return dataBD;
    }
}
