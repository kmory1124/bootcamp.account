# bootcamp.account
microservices account

microservicio para administrar los productos del banco
permite crear varios tipos de cuenta y cada una de estas tiene restrincciones priopias.

link postman: se adicionaron nuevos endpoint

http://localhost:8090/Account/ListAll (lista todas las cuentas)

http://localhost:8090/Account/ListByAccount/2234567890 (lista por numero de cuenta)

http://localhost:8090/Account/countAccount/47304033 (muestra cantidad de cuentas x cliente)

http://localhost:8090/Account/RegisterSaving (registra cuenta ahorro valida x1 por cliente persona, no para empresas)

http://localhost:8090/Account/registerAccountCurrent/ (registra cuenta corriente valida x1 por cliente tipo persona varias x empresa)

http://localhost:8090/Account/registerAccountPermanentDeadlines (registra cuenta plazo fijo valida x1 por cliente, no para empresa)

http://localhost:8090/Account/registerCreditPersonal (registra credito personal)

http://localhost:8090/Account/registerCreditCompany (registra credito empresarial)

http://localhost:8090/Account/registerCreditCard (registra tarjeta credito)

http://localhost:8090/Account/getByDocument/47304033 (muestra cuentas por documento identidad)

http://localhost:8090/Account/register (registra cuentas sin validar tipo cliente y cantidades, por si el front lo necesita)

http://localhost:8090/Account/haveCard/47304033 (valida si el cliente tiene cuentas, requerido para tarea sesion 2)

http://localhost:8090/Account/haveAccountType/47304032/saving (valida si el cliente tiene algun tipo de cuenta, requerido para tarea sesion 2, necesario para las validaciones del front)

http://localhost:8090/Account/updateAmount/2234567890/0 (actualiza montos para las cuentas y creditos)

...se tienen mas servicios en el codigo pero aun no estan terminados por falta de tiempo.


porcentaje de avance: 90%


formato json para registrar cuentas:

{
"numberAccount": "2234567890",
"typeAccount": "saving",
"documentNumber": "47304033",
"description": "cuenta de ahorros",
"limitMoviment": 5,
"comision": 0,
"amount": 1000,
"credit": 0,
"dateCreate": "19/03/2023",
"dateModify": "19/03/2023",
"datePay": "19/03/2023",
"numberCard": "",
"typeClient": "P"
}