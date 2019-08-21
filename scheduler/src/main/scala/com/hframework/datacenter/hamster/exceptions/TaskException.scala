package com.hframework.datacenter.hamster.exceptions

class TaskRunningException(message: String) extends RuntimeException(message){

}

class TaskInitializationException(message: String) extends RuntimeException(message){

}

class TaskAckException(message: String) extends RuntimeException(message){

}