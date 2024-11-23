package io.proj3ct.ReturnBot1.keybords;

import io.proj3ct.ReturnBot1.datebase.DatabaseConnection;
import io.proj3ct.ReturnBot1.datebase.DatebaseTables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс для извлечения информации о факультетах из базы данных.
 * Этот класс предоставляет методы для получения информации о факультетах
 * по заданному идентификатору, подсчета количества факультетов в институте
 * и извлечения идентификаторов и названий факультетов.
 */
public class DepartmentsInfo {
    private DatabaseConnection databaseConnection = new DatabaseConnection();
    private DatebaseTables datebaseTables = new DatebaseTables(databaseConnection);
    private static final Logger logger = Logger.getLogger(DepartmentsInfo.class.getName());
    /**
     * Конструктор с заданными зависимостями.
     */
    public DepartmentsInfo(DatabaseConnection databaseConnection,DatebaseTables datebaseTables ) {
        this.databaseConnection =databaseConnection;
        this.datebaseTables = datebaseTables;
    }
    /**
     * Конструктор по умолчанию.
     */
    public DepartmentsInfo() {
    }
    /**
     * Извлекает информацию о факультете по заданному идентификатору.
     *
     * @param data Идентификатор факультета (например, строка с кодом факультета).
     * @return Строка с информацией о факультете или null, если факультет не найден.
     */
    public String extract(String data) {
        datebaseTables.createDepartsInfoTableQuery();
        String extractInfoFromDepartsInfo = "SELECT * FROM DepartsInfo WHERE id_depart = ?";

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(extractInfoFromDepartsInfo)) {
            stmt.setString(1, data);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("info");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка извлечения данных:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return null;
    }
    /**
     * Подсчитывает количество факультетов в заданном институте.
     *
     * @param data Идентификатор института, для которого необходимо подсчитать количество факультетов.
     * @return Количество факультетов, принадлежащих указанному институту.
     */
    public Integer countOfInst(String data) {
        String query = "SELECT * FROM DepartsInfo";
        int count = 0;

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String[] departInfo = new String[2];
                departInfo[0] = String.valueOf(rs.getInt("id_depart"));
                departInfo[1] = rs.getString("inst");
                if (departInfo[1].equals(data)) {
                    count++;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return count;
    }
    /**
     * Получает массив идентификаторов факультетов для заданного института.
     *
     * @param data Идентификатор института, для которого нужно получить идентификаторы факультетов.
     * @return Массив идентификаторов факультетов в обратном порядке.
     */
    public String[] getIdDeparts(String data) {
        String query = "SELECT id_depart FROM DepartsInfo WHERE inst = ?";
        List<String> idDepartList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, data); // Устанавливаем значение параметра

            ResultSet rs = stmt.executeQuery();

            // Обрабатываем результат запроса
            while (rs.next()) {
                idDepartList.add(String.valueOf(rs.getInt("id_depart")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка получения id: " + e.getMessage(), e);
            throw new RuntimeException(e);

        }

        // Преобразуем список в массив
        String[] idDepartArray = idDepartList.toArray(new String[0]);

        // Переворачиваем массив
        return reverseArray(idDepartArray);
    }
    /**
     * Получает массив названий факультетов для заданного института.
     *
     * @param data Идентификатор института, для которого нужно получить названия факультетов.
     * @return Массив названий факультетов в обратном порядке.
     */
    public String[] getNames(String data) {
        String query = "SELECT name FROM DepartsInfo WHERE inst = ?";
        List<String> nameList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, data); // Устанавливаем значение параметра

            ResultSet rs = stmt.executeQuery();

            // Обрабатываем результат запроса
            while (rs.next()) {
                nameList.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка получения имен: " + e.getMessage(), e);
            throw new RuntimeException(e);

        }

        // Преобразуем список в массив
        String[] nameArray = nameList.toArray(new String[0]);

        // Переворачиваем массив
        return reverseArray(nameArray);
    }
    /**
     * Получает все имена и их идентификаторы (id_depart) из таблицы DepartsInfo.
     *
     * @return массив строк, где каждая строка содержит имя и соответствующий идентификатор в формате "name (id_depart)".
     */
    public String[] getAllNamesWithId() {
        String query = "SELECT name, id_depart FROM DepartsInfo";
        List<String> resultList = new ArrayList<>();

        try (Connection conn = databaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            // Обрабатываем результат запроса
            while (rs.next()) {
                String name = rs.getString("name");
                int idDepart = rs.getInt("id_depart");
                resultList.add(idDepart + " - " + name);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка: " + e.getMessage(), e);
            throw new RuntimeException(e);

        }

        // Преобразуем список в массив
        String[] resultArray = resultList.toArray(new String[0]);

        // Переворачиваем массив
        return sortDepartmentsById(resultArray);
    }
    /**
     * Сортирует массив строк по числовым значениям, которые находятся в начале каждой строки.
     *
     * @param departments массив строк, содержащих пары "id - название"
     * @return отсортированный массив строк по числовым значениям (id)
     */
    public static String[] sortDepartmentsById(String[] departments) {
        // Сортировка с использованием компаратора, который извлекает числовое значение из каждой строки
        Arrays.sort(departments, Comparator.comparingInt(department -> Integer.parseInt(department.split(" - ")[0])));

        return departments;
    }
    /**
     * Переворачивает массив строк.
     * @param array Исходный массив строк, который нужно перевернуть.
     * @return Новый массив строк в обратном порядке.
     */
    private String[] reverseArray(String[] array) {
        String[] reversed = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            reversed[i] = array[array.length - 1 - i];
        }
        return reversed;
    }
}
