package alex.valker91.test_learning.service.impl;

import alex.valker91.test_learning.dao.TicketDao;
import alex.valker91.test_learning.dao.UserDao;
import alex.valker91.test_learning.model.Event;
import alex.valker91.test_learning.model.Ticket;
import alex.valker91.test_learning.model.User;
import alex.valker91.test_learning.service.TicketService;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketServiceImpl implements TicketService {

    private TicketDao ticketDao;
    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    public TicketServiceImpl() {}

    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        Ticket booked = ticketDao.bookTicket(userId, eventId, place, category);
        log.info("bookTicket: id={}, userId={}, eventId={}, place={}, category={}", booked.getId(), userId, eventId, place, category);
        return booked;
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        List<Ticket> page = ticketDao.getBookedTickets(user, pageSize, pageNum);
        log.debug("getBookedTickets(user): userId={}, pageSize={}, pageNum={}, returned={}", user.getId(), pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        List<Ticket> page = ticketDao.getBookedTickets(event, pageSize, pageNum);
        log.debug("getBookedTickets(event): eventId={}, pageSize={}, pageNum={}, returned={}", event.getId(), pageSize, pageNum, page.size());
        return page;
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        boolean result = ticketDao.cancelTicket(ticketId);
        log.info("cancelTicket: id={}, result={}", ticketId, result);
        return result;
    }
}
