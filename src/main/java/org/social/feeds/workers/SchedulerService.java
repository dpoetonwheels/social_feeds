/**
 * 
 */
package org.social.feeds.workers;

import org.apache.log4j.Logger;
import org.social.feeds.workers.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author devang.desai
 *
 */
@Service
public class SchedulerService {
	protected static Logger logger = Logger.getLogger("service");

	@Autowired
	@Qualifier("twitterWorker")
	private Worker twitterWorker;
	
	@Autowired
	@Qualifier("youtubeWorker")
	private Worker youtubeWorker;
	
	/**
	 * You can opt for cron expression or fixedRate or fixedDelay
	 * <p>
	 * See Spring Framework 3 Reference:
	 * Chapter 25.5 Annotation Support for Scheduling and Asynchronous Execution
	 */
	//@Scheduled(fixedDelay=5000)
	//@Scheduled(fixedRate=5000)
	@Scheduled(cron="*/5 * * * * ?")
	public void doSchedule() {
		logger.debug("Start schedule");
		
		for (int i = 0; i < 1; i++) {
			logger.debug("Delegate to worker " + i);
			twitterWorker.updateDB();
			youtubeWorker.updateDB();
        }
		
		logger.debug("End schedule");
	}
	
}
