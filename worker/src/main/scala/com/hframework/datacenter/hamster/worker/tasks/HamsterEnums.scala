package com.hframework.datacenter.hamster.worker.tasks

trait TermStatus
object Success extends TermStatus //成功
object Failed extends TermStatus //失败
object Blocking extends TermStatus //阻塞,如Selector本地没有检测到
object Outstrip extends TermStatus //超前,如后面批次优先处理结束