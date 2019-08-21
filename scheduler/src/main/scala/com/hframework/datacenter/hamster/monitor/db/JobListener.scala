package com.hframework.datacenter.hamster.monitor.db

import com.hframework.common.monitor.Node
import com.hframework.datacenter.hamster.monitor.db.JobRegistry._
import com.hframework.hamster.cfg.domain.model.{CfgDeployment, CfgDeploymentDetail, CfgNode}

import scala.collection.JavaConverters._
import scala.collection.mutable
trait JobListener {

  def processJobChanges(changeJobs: Map[String, JobInfo], workers: mutable.Buffer[WorkerMeta],
                        aboutDeployments: mutable.Buffer[DeploymentND],
                        aboutDeploymentDetails: mutable.Buffer[DeployJobNodeMeta] ) : Unit = {
    processJobChanges(changeJobs.asJava, workers.asJava, aboutDeployments.asJava, aboutDeploymentDetails.asJava)
  }
  def processJobChanges(changeJobs: java.util.Map[String, JobInfo], aboutWorkers: java.util.List[WorkerMeta]
                        , aboutDeployments: java.util.List[Node[CfgDeployment]]
                        , aboutDeploymentDetails: java.util.List[DeployJobNodeMeta]) = {}

  def processJobGroupChanges(changeJobs: Map[String, JobGroup]) = {}


}
