package cucumber.runner;

import cucumber.api.event.Event;
import cucumber.api.event.TestCaseEvent;
import cucumber.api.event.TestCaseFinished;

import java.util.ArrayList;
import java.util.List;

public class TestCaseEventBus extends AbstractEventBus {

    private final SynchronizedEventBus parent;
    private List<Event> queue = new ArrayList<Event>();

    public TestCaseEventBus(final SynchronizedEventBus parent) {
        this.parent = parent;
    }

    @Override
    public Long getTime() {
        return parent.getTime();
    }

    @Override
    public void send(final Event event) {
        super.send(event);
        if (event instanceof TestCaseEvent) {
            queue(event);
        }
    }

    private void queue(final Event event) {
        queue.add(event);
        if (event instanceof TestCaseFinished) {
            parent.sendAll(queue);
            queue.clear();
        }
    }

}