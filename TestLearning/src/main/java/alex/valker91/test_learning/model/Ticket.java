package alex.valker91.test_learning.model;

import alex.valker91.test_learning.model.impl.TicketImpl;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by maksym_govorischev.
 */
@JsonDeserialize(as = TicketImpl.class)
public interface Ticket {
    public enum Category {STANDARD, PREMIUM, BAR}

    /**
     * Ticket Id. UNIQUE.
     * @return Ticket Id.
     */
    long getId();
    void setId(long id);
    long getEventId();
    void setEventId(long eventId);
    long getUserId();
    void setUserId(long userId);
    Category getCategory();
    void setCategory(Category category);
    int getPlace();
    void setPlace(int place);
}
