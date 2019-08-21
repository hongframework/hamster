import java.util.concurrent.TimeUnit

import com.hframework.common.util.DateUtils
import com.hframework.utils.scala.Logging
import com.hframework.datacenter.hamster.worker.Times
import org.junit.Test
import org.quartz._
import org.quartz.impl.StdSchedulerFactory

class QuartzSuite {

  @Test
  def testInterval: Unit ={
    val scheduler = StdSchedulerFactory.getDefaultScheduler
    val jobDetail = JobBuilder.newJob(classOf[DemoJob]).withIdentity("j1", "g1").build()
    val trigger = TriggerBuilder.newTrigger().withIdentity("t1", "g1").withSchedule(
      SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever()).build()
    scheduler.scheduleJob(jobDetail, trigger)
    scheduler.start()
    println("----------------------1---------------")
    TimeUnit.SECONDS.sleep(10)
    println("----------------------2---------------")
    scheduler.shutdown()
  }

  @Test
  def testCron: Unit ={
    val scheduler = StdSchedulerFactory.getDefaultScheduler
    scheduler.start()
    val trigger = TriggerBuilder.newTrigger().withIdentity("t1", "g1").withSchedule(
      CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build()
    val jobDetail1 = JobBuilder.newJob(classOf[DemoJob]).withIdentity("j1", "g1").build()
    scheduler.scheduleJob(jobDetail1, trigger)
    scheduler.start()
    val trigger2 = TriggerBuilder.newTrigger().withIdentity("t2", "g1").withSchedule(
      CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).build()
    val jobDetail2 = JobBuilder.newJob(classOf[Demo2Job]).withIdentity("j2", "g1").build()
    scheduler.scheduleJob(jobDetail2, trigger2)
    scheduler.start()
    println("----------------------1---------------")
    TimeUnit.SECONDS.sleep(10)
    println("----------------------2---------------")
    scheduler.deleteJob(jobDetail1.getKey)
    TimeUnit.SECONDS.sleep(10)
    println("----------------------3---------------")
    scheduler.shutdown()
  }


  @Test
  def test_Times: Unit ={
    println(DateUtils.getDateYYYYMMDDHHMMSS(Times.getBeginTime("-1s").getTime))
    println(DateUtils.getDateYYYYMMDDHHMMSS(Times.getEndTime("-1s").getTime))

  }
}

class DemoJob extends Job with Logging{
  override def execute(jobExecutionContext: JobExecutionContext): Unit = {
    Thread.sleep(2000L)
    logger.info("111111111")
  }
}

class Demo2Job extends Job with Logging{
  override def execute(jobExecutionContext: JobExecutionContext): Unit = {
    Thread.sleep(1000L)
    logger.info("111111111")
  }
}