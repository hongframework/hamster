package com.hframework.datacenter.hamster.zookeeper

import java.text.MessageFormat

import org.apache.commons.lang.StringUtils

object ZKPathUtils {
  private val ROOT = "/hamster"
  private val CONFIG_ROOT = s"${ROOT}/config"

  private val JOBS_CONFIG_ROOT = s"${CONFIG_ROOT}/jobs"
  private val JOB_CONFIG = s"${JOBS_CONFIG_ROOT}/%s/%s"
  private val JOB_NODE_CONFIG = s"${JOB_CONFIG}/%s"


  private val JOB_GROUPS_CONFIG_ROOT = s"${CONFIG_ROOT}/jgroups"
  private val JOB_GROUP_CONFIG = s"${JOB_GROUPS_CONFIG_ROOT}/%s"
  private val JOB_GROUP_JOBS_CONFIG = s"${JOB_GROUP_CONFIG}/%s"

  private val DEPLOYMENT_CONFIG_ROOT = s"${CONFIG_ROOT}/deploys"
  private val DEPLOYMENT_CONFIG = s"${DEPLOYMENT_CONFIG_ROOT}/%s"
  private val DEPLOYMENT_JOBS_CONFIG = s"${DEPLOYMENT_CONFIG}/%s"

  private val WORKERS_ROOT = s"${ROOT}/workers"
  private val WORKERS_CONFIG_ROOT = s"${WORKERS_ROOT}/all"
  private val CONFIG_WORKER = s"${WORKERS_CONFIG_ROOT}/%s"

  private val WORKERS_ALIVE_ROOT = s"${WORKERS_ROOT}/alive"
  private val ALIVE_WORKER = s"${WORKERS_ALIVE_ROOT}/%s"

  private val PROCESS_ROOT = s"${ROOT}/process"
  private val PROCESS_DEPLOY_ROOT = s"${PROCESS_ROOT}/%s"
  private val JOB_GROUPS_PROCESS_ROOT = s"${PROCESS_DEPLOY_ROOT}/jgroups"
  private val JOB_GROUP_PROCESS= s"${JOB_GROUPS_PROCESS_ROOT}/%s"

  private val JOB_PROCESS_ROOT = s"${PROCESS_DEPLOY_ROOT}/jobs/%s/%s"
  private val TASK_PROCESS_ROOT= s"${JOB_PROCESS_ROOT}/%s"
  private val JOB_PROCESS= s"${JOB_PROCESS_ROOT}/%s"
  private val TASK_PROCESS= s"${TASK_PROCESS_ROOT}/%s"

  private val QUARTZ_ROOT = s"${ROOT}/destinations"
  private val QUARTZ_DEPLOY_ROOT = s"${QUARTZ_ROOT}/%s"
  private val QUARTZ_SELECTOR_ROOT = s"${QUARTZ_DEPLOY_ROOT}/%s"
  private val QUARTZ_CURSOR_ROOT= s"${QUARTZ_SELECTOR_ROOT}/cursor"
  private val QUARTZ_VCURSOR_ROOT = s"${QUARTZ_SELECTOR_ROOT}/v_cursor"

  private val JOB_PROCESS_REDIS_PATH = "hamster-%s-%s-%s-%s"
  private val JOBG_PROCESS_REDIS_PATH = "hamster-%s-%s-%s"

  private val CANAL_ROOT = s"/otter/canal/destinations"

  def getRootPath: String = ROOT

  def getConfigRootPath: String = CONFIG_ROOT
  def getJobsConfigRootPath: String = JOBS_CONFIG_ROOT
  def getJobGroupsConfigRootPath: String = JOB_GROUPS_CONFIG_ROOT
  def getDeploymentConfigRootPath: String = DEPLOYMENT_CONFIG_ROOT

  def getWorkerRootPath: String = WORKERS_ROOT
  def getConfigWorkerRootPath: String = WORKERS_CONFIG_ROOT
  def getAliveWorkerRootPath: String = WORKERS_ALIVE_ROOT

  def getProcessRootPath: String = PROCESS_ROOT
  def getJobGroupsProcessRootPath: String = JOB_GROUPS_PROCESS_ROOT

  def getJobConfigPath(jobTemplate: String, jobCode: String): String = JOB_CONFIG.format(jobTemplate, jobCode)
  def getJobNodeConfigPath(jobTemplate: String, jobCode: String, nodeCode: String): String = JOB_NODE_CONFIG.format(jobTemplate,jobCode, nodeCode)

  def getJobGroupConfigPath(groupCode: String): String = JOB_GROUP_CONFIG.format(groupCode)
  def getJobGroupJobConfigPath(groupCode: String, jobCode: String): String = JOB_GROUP_JOBS_CONFIG.format(groupCode, jobCode)

  def getDeploymentConfigPath(deployCode: String): String = DEPLOYMENT_CONFIG.format(deployCode)
  def getDeploymentJobConfigPath(deployCode: String, jobCode: String): String = DEPLOYMENT_JOBS_CONFIG.format(deployCode, jobCode)

  def getConfigWorkerPath(workerId : String): String = CONFIG_WORKER.format(workerId)
  def getAliveWorkerPath(workerId : String): String = ALIVE_WORKER.format(workerId)

  def getJobGroupProcessPath(deployCode: String, groupCode: String): String = JOB_GROUP_PROCESS.format(deployCode, groupCode)

  def getJobProcessRootPath(deployCode: String, jobTemplate: String, jobCode: String): String = JOB_PROCESS_ROOT.format(deployCode, jobTemplate, jobCode)
  def getTaskProcessRootPath(deployCode: String, jobTemplate: String, jobCode: String, taskCode: String): String = TASK_PROCESS_ROOT.format(deployCode, jobTemplate, jobCode, taskCode)

  def getQuartzRootPath: String = QUARTZ_ROOT
  def getQuartzDeployPath(deployCode: String): String = QUARTZ_DEPLOY_ROOT.format(deployCode)
  def getQuartzSelectorPath(deployCode: String, selectorCode: String): String = QUARTZ_SELECTOR_ROOT.format(deployCode, selectorCode)
  def getQuartzCursorPath(deployCode: String, selectorCode: String): String = QUARTZ_CURSOR_ROOT.format(deployCode, selectorCode)
  def getQuartzVCursorPath(deployCode: String, selectorCode: String): String = QUARTZ_VCURSOR_ROOT.format(deployCode, selectorCode)


//  def getJobProcessPath(jobTemplate: String, jobCode: String, processId: Long): String =
//    JOB_PROCESS.format(jobTemplate, jobCode, processId.toString.reverse.padTo(10, '0').reverse)
  def getTaskProcessPath(deployCode: String, jobTemplate: String, jobCode: String, taskCode: String, processId: Long): String =
    TASK_PROCESS.format(deployCode: String, jobTemplate, jobCode, taskCode, processId.toString.reverse.padTo(10, '0').reverse)


  def getJobProcessRedisPath(deployCode: String, jobKey: String, jobTask: String, processId: Long): String =
    JOB_PROCESS_REDIS_PATH.format(deployCode,jobKey, processId.toString.reverse.padTo(10, '0').reverse, jobTask)
  def getJobGroupProcessRedisPath(deployCode: String, selectKey: String, processId: Long): String =
    JOBG_PROCESS_REDIS_PATH.format(deployCode, selectKey, processId.toString.reverse.padTo(10, '0').reverse)

  def getCanalRootPath: String = CANAL_ROOT
}
