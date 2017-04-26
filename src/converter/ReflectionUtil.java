package converter;

import java.lang.reflect.Field;

public class ReflectionUtil {

	public static Object copyAttributesFromTo(Object a, Object b) throws IllegalArgumentException, IllegalAccessException {
		Field[] fieldsFromFirstClass = a.getClass().getDeclaredFields();
		Field[] fieldsFromSecondClass = b.getClass().getDeclaredFields();

		for (Field currentFieldFromTheFirstClass : fieldsFromFirstClass) {
			for (Field currentFieldFromTheSecondClass : fieldsFromSecondClass) {
				String nameOfTheFirstField = currentFieldFromTheFirstClass.getName();
				String nameOfTheSecondField = currentFieldFromTheSecondClass.getName();

				if (nameOfTheFirstField.equals(nameOfTheSecondField)) {
					currentFieldFromTheFirstClass.setAccessible(true);
					currentFieldFromTheSecondClass.setAccessible(true);

					currentFieldFromTheFirstClass.set(a, currentFieldFromTheSecondClass.get(b));
				}
			}
		}

		return a;
	}
}
