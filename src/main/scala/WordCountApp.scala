import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.elasticsearch.spark.sql._
import org.elasticsearch.spark._

object WordcountApp {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setAppName("Wordcount").setMaster("local")
    conf.set("es.index.auto.create  ", "true")

    val sc = new SparkContext(conf)
    val spark = SparkSession.builder().config("es.index.auto.create  ", "true").appName("Spark SQL - Exemplo 1").getOrCreate()

    import spark.implicits._


    val cliDF = spark.read.option("header", "true").csv("hdfs://localhost:9000/user/eduardo/isabella_emissoes.csv")

    val totalTickets = cliDF.count().toString
    val jsonTotais = s"""{"total":$totalTickets}"""
    sc.makeRDD(Seq(jsonTotais)).saveJsonToEs("tickets/total")

    val tipoReservas = cliDF.groupBy("TIPO_RESERVA").count().orderBy($"count".desc).as[TotaisReservas]
    tipoReservas.saveToEs("totais_reservas/tickets")

    val reservasFornecedor = cliDF.groupBy("nome_fantasia", "TIPO_RESERVA").count().orderBy($"count".desc).as[ReservaFornecedor]
    reservasFornecedor.saveToEs("reserva_fornecedor/tickets")

    val pagamentoFornecedor = cliDF.groupBy("nome_fantasia", "FORMAPGT").count().orderBy($"count".desc).as[PagamentoFornecedor]
    pagamentoFornecedor.saveToEs("pagamento_fornecedor/tickets")

    val formaPagamento = cliDF.groupBy("FORMAPGT").count().orderBy($"count".desc).as[TotaisPagamento]
    formaPagamento.saveToEs("totais_pagamento/tickets")

    val sistemaFornecedor = cliDF.groupBy("nome_fantasia", "SISTEMA").count().orderBy($"count".desc).as[Tickets]
    sistemaFornecedor.saveToEs("fornecedor/tickets")

  }
}