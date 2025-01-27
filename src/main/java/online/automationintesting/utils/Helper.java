package online.automationintesting.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Helper {

  /**
   * Generates a list of x random distinct values from 0 to y-1
   * @param x Number of values to generate
   * @param y Maximum value (exclusive)
   * @return List of x random distinct values
   */
  public static Integer[] getRandomDistinctValues(int x, int y) {
        // Generate a list of values from 0 to y-1
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < y; i++) {
            values.add(i);
        }

        // Shuffle the list to randomize the order
        Collections.shuffle(values, new Random());

        // Return the first x elements
        return values.subList(0, x).toArray(Integer[]::new);
    }

 
  /**
   * Sets the value of a field in an object based on the field name
   * @param obj Object to set the field value in
   * @param fieldName Name of the field to set
   * @param value Value to set the field to
   * @throws Exception
   */
  public static void setField(Object obj, String fieldName, Object value) throws Exception {
    Field field = obj.getClass().getDeclaredField(fieldName);
    field.setAccessible(true); // Bypass private access
    field.set(obj, value);
  }
  
}
