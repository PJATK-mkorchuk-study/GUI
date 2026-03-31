package task05.registry;

import java.util.HashMap;
import java.util.Map;

public class WarehouseRegistry {
    private final Map<Class<?>, Object> modules = new HashMap<>();

    public <T> void put(Class<T> type, T instance) {
        modules.put(type, instance);

        System.out.println("Module registered: " + type.getSimpleName());
    }

    public <T> T get(Class<T> type) {
        Object value = modules.get(type);

        if (value == null) {
            throw new IllegalArgumentException("No module registered for type: " + type.getSimpleName());
        }


        return type.cast(value);
    }

    public boolean contains(Class<?> type) {
        return modules.containsKey(type);
    }

    public int size() {
        return modules.size();
    }
}
