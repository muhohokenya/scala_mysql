package com.muhoho

import org.apache.poi.ss.usermodel.{DataFormatter, Row, WorkbookFactory}

import java.io.File
import java.sql.{Connection, DriverManager, PreparedStatement}
import scala.collection.convert.ImplicitConversions.`iterable AsScalaIterable`
object HelloWorld {

  // connect to the database named "mysql" on port 8889 of localhost
  private val url = "jdbc:mysql://localhost:3306/spring_app"
  private val driver = "com.mysql.cj.jdbc.Driver"
  private val username = "root"
  private val password = "secret"
  private var connection: Connection = _

  private def connect(): Unit = {
    try {
      Class.forName(driver)
      connection = DriverManager.getConnection(url, username, password)
      val statement = connection.createStatement()
      statement.executeQuery(" INSERT INTO spring_app.excel_data (country, product, unit_sold) VALUES ('Kenya', 'Tea', 1990);")
    } catch {
      case e: Exception => e.printStackTrace()
    }
    connection.close()
  }

  private def readExcel(): Unit = {
    val f = new File("Example.xlsx")
    val workbook = WorkbookFactory.create(f)
    val sheet = workbook.getSheetAt(0)

    Class.forName(driver)
    connection = DriverManager.getConnection(url, username, password)
    val sqlQuery: String = "INSERT INTO spring_app.excel_data (country, product, unit_sold) VALUES (?,?,?)"
    val preparedStatement: PreparedStatement = connection.prepareStatement(sqlQuery)

    connection.setAutoCommit(false)

    /*Start of Loop*/
    sheet.zipWithIndex.foreach { case (row, index) =>
      val formatter = new DataFormatter()
      val countryCol = Option(row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL))
      val productCol = Option(row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL))
      val unitSoldCol = Option(row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL))

      val result = for {
        country <- countryCol
        prod <- productCol
        unitSold <- unitSoldCol
      } yield {
        /*Start*/
        try {
          val countryVal = formatter.formatCellValue(country)
          val prodVal = formatter.formatCellValue(prod)
          val unitSoldVal = formatter.formatCellValue(unitSold)

          preparedStatement.setString(1,countryVal)
          preparedStatement.setString(2,prodVal)
          preparedStatement.setDouble(3,unitSoldVal.toDouble)
          preparedStatement.addBatch()
          println(unitSoldVal.toDouble)
        } catch {
          case e: Exception => e.printStackTrace()
        }
        /*End*/
      }
    }
    /*end of Loop*/
    connection.commit()
    val result = preparedStatement.executeBatch()
    connection.close()

  }
  def main(args:Array["string"]): Unit = {
    readExcel()
  }
}
