import java.util

import com.hframework.datacenter.hamster.worker.UAUtils
import org.junit.Test

class UAUtilsSuite {
  val ua_string = "Mozilla/5.0 (Linux; Android 5.1; HLJ-XM-E2 Build/LMY47D) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/39.0.0.0 Mobile Safari/537.36 wxAndroid/4.9.8"

//  val ua_string = "Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/535.19 (KHTML, like Gecko) Version/4.0 LieBaoFast/2.12.0 Mobile Safari/535.19"
  @Test
  def ua_test(): Unit ={
//    println(ua_string.replaceAll("[^a-zA-Z0-9]+", ""))
    val ua = UAUtils.parse(ua_string)
    println(ua)
  }


}
