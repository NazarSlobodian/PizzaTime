package Model.Utils;

import java.beans.PropertyChangeListener;

public abstract class ObservableModel {
    protected EventFiringContext eventContext = new EventFiringContext(this);
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        eventContext.addPropertyChangeListener(listener);
    }
    public void setNotifications(boolean setting) {
        eventContext.setEventFiring(setting);
    }
}
