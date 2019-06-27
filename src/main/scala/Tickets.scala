

case class Tickets(nome_fantasia: String,
                   sistema: String,
                   count: String)


case class TotaisReservas(tipo_reserva: String, count: String)

case class ReservaFornecedor(nome_fantasia: String,
                             tipo_reserva: String,
                             count: String)

case class PagamentoFornecedor(nome_fantasia: String,
                               formapgt: String,
                               count: String)

case class TotaisPagamento(formapgt: String,
                               count: String)

