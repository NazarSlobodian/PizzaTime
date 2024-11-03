package Model.Utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventFiringContext {

    private final PropertyChangeSupport support;
    boolean eventFireAllowed;

    public EventFiringContext() {
        support = new PropertyChangeSupport(this);
        eventFireAllowed = false;
    }
    public void allowEventFiring() {
        eventFireAllowed = true;
    }
    public void forbidEventFiring() {
        eventFireAllowed = false;
    }
    public boolean canFireEvent() {
        return eventFireAllowed;
    }
    public void firePropertyChange(String name, Object oldValue, Object newValue) {
        if (canFireEvent()) {
            support.firePropertyChange(name, oldValue, newValue);
        }
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
