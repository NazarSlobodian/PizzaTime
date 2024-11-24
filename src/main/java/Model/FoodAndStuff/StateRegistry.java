package Model.FoodAndStuff;

import Model.FoodAndStuff.States.CookableState;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class StateRegistry {
    public static Map<String, Boolean> stateMap = null;

    public static Map<String, Boolean> getStateMap() {
        if (stateMap != null) {
            return new LinkedHashMap<>(stateMap);
        }
        stateMap = new LinkedHashMap<>();
        Reflections reflections = new Reflections("Model.FoodAndStuff.States");
        Set<Class<? extends CookableState>> subclasses = reflections.getSubTypesOf(CookableState.class);
        try {
            for (Class<? extends CookableState> subclass : subclasses) {
                if (Modifier.isAbstract(subclass.getModifiers())) {
                    continue;
                }
                CookableState instance = subclass.getDeclaredConstructor().newInstance();
                if (instance.isBad() || instance.isFinal()) {
                    continue;
                }
                stateMap.put(instance.toString(), true);
            }
        } catch (Exception e) {
            throw new RuntimeException("Oopsie daisy");
        }
        return new LinkedHashMap<>(stateMap);
    }
}
