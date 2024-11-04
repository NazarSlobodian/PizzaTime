package Model.Utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventFiringContext {

    private final PropertyChangeSupport support;
    boolean eventFireAllowed;

    public EventFiringContext(Object source) {
        support = new PropertyChangeSupport(source);
        eventFireAllowed = true;
    }
    public void setEventFiring(boolean setting) {
        eventFireAllowed = setting;
    }
    public boolean canFireEvent() {
        return eventFireAllowed;
    }
    public void firePropertyChange(String name, Object oldValue, Object newValue) {
        if (canFireEvent()) {
            support.firePropertyChange(name, oldValue, newValue);
        }
    }
    public void forceFirePropertyChange(String name, Object oldValue, Object newValue) {
        support.firePropertyChange(name, oldValue, newValue);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
