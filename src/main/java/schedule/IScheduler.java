package schedule;

import org.quartz.SchedulerException;

public abstract class IScheduler {
    abstract void dailyChange() throws SchedulerException;
}
