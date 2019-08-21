package com.hframework.datacenter.hamster.worker

import java.util

import com.blueconic.browscap.{BrowsCapField, UserAgentService}
import com.hframework.common.util.security.MD5Util

object UAUtils {
  val emptyParseResult = new Array[String](9)
  val parser =
    new UserAgentService().loadParser(util.Arrays.asList(BrowsCapField.BROWSER, BrowsCapField.BROWSER_MAJOR_VERSION,
      BrowsCapField.DEVICE_TYPE, BrowsCapField.DEVICE_BRAND_NAME, BrowsCapField.DEVICE_NAME, BrowsCapField.IS_MOBILE_DEVICE,
      BrowsCapField.PLATFORM, BrowsCapField.PLATFORM_VERSION))
  val deviceNameR = ";\\s?(\\S*?\\s?\\S*?)\\s?(build)?/".r
  val brandR = Map(
    "iphone|ipad|ipod".r -> "Apple",
    "gt-|sm-|sch-".r -> "Samsung",
    "huawei|honor".r -> "Huawei",
    "hm|redmi|mi ".r -> "Xiaomi",
    "oppo".r -> "OPPO",
    "vivo".r -> "vivo",
    "htc".r -> "HTC",
  )
  val brandSplitR = "[a-zA-Z]+".r

  def parseArray(ua:String): Array[String] = try{
    val result = parse(ua)
    if(result.isDefined) {
      Array(result.get.deviceType, result.get.deviceBrandName, result.get.deviceName, result.get.isMobile.toString,
        result.get.browser, result.get.browserVersion, result.get.os, result.get.osVersion, result.get.uaMd5)
    }else {
      emptyParseResult
    }
  }catch {
    case _ => emptyParseResult
  }


  def parse(ua: String): Option[UAData] = {
    val ua_ = ua.toLowerCase
    val capabilities = parser.parse(ua_)
    val isMobile = capabilities.getValue(BrowsCapField.IS_MOBILE_DEVICE).toBoolean
    val (brandName, devName) = if(isMobile && "unknown".equals(capabilities.getValue(BrowsCapField.DEVICE_BRAND_NAME).toLowerCase)) {
      val deviceNameOp = deviceNameR.findFirstMatchIn(ua_)
      if(deviceNameOp.isDefined) {
        val devName = deviceNameOp.get.group(1)
        val brandOp = brandR.find(_._1.findFirstIn(ua_).isDefined)
        val brandName = if(brandOp.isDefined) brandOp.get._2 else {
          brandSplitR.findFirstIn(devName).getOrElse("unknown")
        }
        (brandName, devName)
      }else {
        val brandOp = brandR.find(_._1.findFirstIn(ua_).isDefined)
        if(brandOp.isDefined){
          (brandOp.get._2, "unknown")
        }else ("unknown", "unknown")
      }

    }else if("Desktop".equals(capabilities.getDeviceType)){
      ("pc",capabilities.getValue(BrowsCapField.DEVICE_NAME))
    }else (capabilities.getValue(BrowsCapField.DEVICE_BRAND_NAME),capabilities.getValue(BrowsCapField.DEVICE_NAME))

    val md5Zip = MD5Util.encrypt(ua.replaceAll("[^a-zA-Z0-9]+", ""))
    Some(UAData(capabilities.getDeviceType, brandName.toLowerCase(), devName, isMobile, capabilities.getBrowser, capabilities.getBrowserMajorVersion, capabilities.getPlatform, capabilities.getPlatformVersion, md5Zip))
  }

}

case class UAData(deviceType: String, deviceBrandName: String, deviceName: String, isMobile: Boolean,
                  browser: String, browserVersion: String, os: String, osVersion: String, uaMd5: String)
