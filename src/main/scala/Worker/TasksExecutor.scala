package Akka_with_Crawler.src.main.scala.Worker

import scala.sys.process._


class TasksExecutor {

  val Path = "src/main/scala/Akka_with_Crawler/src/main/python"

  def generateAPIbyPython(date: String, symbol: String): String = {
    val runCmd = s"python $Path/multi-lan_stock-crawler_py-ver.py --date $date --listed-company $symbol"
    runCmd.!!
  }


  def runCode(pythonFileArguments: String): String = {
    val runningCmd = s"python $Path/multi-lan_stock-crawler_py-ver.py --api-params $pythonFileArguments"
    println("[INFO] Running Python Code Command Line: \n" + runningCmd)
    println("[DEBUG] running command line result: " + runningCmd.!!)
    runningCmd.!!
  }

}
