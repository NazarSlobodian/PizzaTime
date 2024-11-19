package Model.Utils;

import java.beans.PropertyChangeListener;

public interface Observable {
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public void setNotifications(boolean setting);
}
