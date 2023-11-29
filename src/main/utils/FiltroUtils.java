/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.utils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 *
 * @author micha
 */
public class FiltroUtils {
    public static <T> HashMap<String, Object> convertFiltrosToHashMap(T object) {
        HashMap<String, Object> result = new HashMap<>();

        if (object != null) {
            Class<?> clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);

                    // Verifica se o valor não é nulo ou, no caso de booleanos, se é true
                    if (value != null && !(value instanceof Boolean) || (Boolean) value) {
                        result.put(field.getName(), value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // Trate a exceção conforme necessário
                }
            }
        }

        return result;
    }
}
