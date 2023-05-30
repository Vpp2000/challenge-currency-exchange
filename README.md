# DOCUMENTACIÓN DEL PROYECTO

- [ASPECTOS GENERALES DEL PROYECTO](#aspectos-generales-del-proyecto)
- [¿COMO INTERPRETÉ LOS REQUERIMIENTOS?](#como-interpreté-los-requerimientos)
- [ESQUEMA DE BASE DE DATOS](#esquema-de-base-de-datos)
- [ESTRUCTURA DE CARPETAS](#estructura-de-carpetas)
- [ARQUITECTURA GENERAL DEL PROYECTO](#arquitectura-general-del-proyecto)
- [ENDPOINTS DE LA API](#endpoints-de-la-api)


## ASPECTOS GENERALES DEL PROYECTO
- Se codificó la API procurando cumplir los requerimientos de la mejor manera posible.
- La aplicación esta dockerizada y hay un archivo **docker-compose** para correrla en un SO linux o en Windows con WSL.
- El proyecto cuenta con github actions para generar la imagen cada que se pushea un cambio a main.
- El proyecto cuanta con un test unitario para validar el calculo correcto del valor de una moneda a otra.

## ¿COMO INTERPRETÉ LOS REQUERIMIENTOS?
- Lo que entendí básicamente es que se me pedía un sistema que permitiese almacenar monedas (o tipos de cambio según tengo entendido), almacenar su valor en el mercado internacional y permitir cálculos de una moneda a otra moneda.
- Investigando encontré que una manera de lograr implementar mi sistema es guardando los valores de todas mis monedas en función de una moneda común (**en mi caso tomé al dólar como base**).
- Finalmente, implementé un sistema que permita guardar las monedas y su valor en función de otra moneda (**este valor no lo valido yo, lo que yo entiendo es que puede ser como una regla de negocio y se espera que los usuarios guarden el valor de la moneda en función al dolar**) y que permite también el calculo del valor de una moneda en otra moneda.


## ESQUEMA DE BASE DE DATOS
![database_schema](https://github.com/Vpp2000/BOA_STAR_Java/assets/48797063/afc64658-373f-4248-a3e4-33b6c569c37d)
- Con respecto a las monedas y sus valores:
    - **currency** maneja la información básica de una moneda y **exchange_rate_last** es una tabla que se enlaza a **currency** y que contiene su último valor en el mercado internacional respecto al dólar.
    - Opté por hacer dos tablas ya que no espero que **currency** sea consultado constantemente mientras que **exchange_rate_last** si sufre bastantes consultas y actualizaciones.
    - **exchange_rate** lo uso para mantener un historial de los cambios del valor de una moneda (no sabía si enlazar esta tabla con con **exchange_rate_last** o **currency** porque si las filas de esas tablas sufren eliminaciones creí que sería apropiado no enlazarlas con mi tabla de historial).

## ESTRUCTURA DE CARPETAS
![folder_structure](https://github.com/Vpp2000/BOA_STAR_Java/assets/48797063/f2ca9b27-69d2-4781-ae3d-065e85f5e15d)
- **api**: esta carpeta contiene los controladores, repositorios, servicios y helpers que dan vida a la API.
- **config**: esta carpeta contiene clases que sirven para configurar nuestra API.
- **entities**: esta carpeta viena a contener las clases que representan las tablas de la base de datos.
- **dtos**: esta carpeta contiene clases de propósito general que nos sirven para empaquetar diversos objetos.
- **exceptions**: esta carpeta nos permite definir excepciones más personalisadas.

## ARQUITECTURA GENERAL DEL PROYECTO
![api_architecture](https://github.com/Vpp2000/BOA_STAR_Java/assets/48797063/2ac2411f-8c7f-4101-a01f-135ad62fcc75)
- Los controladores permiten interceptar y manejar los Http Requests. Usan los servicios para construir sus respuestas.
- Los servicios manejan la lógica de negocio e interactúan con repositorios y helpers.
- Los helpers son clases que se crean para evitar acoplamiento entre servicios. Estos helpers también pueden interactuar con repositorios y manejar operaciones complejas.
- Los repositorios interactúan con la base de datos y son usados por los servicios.


## ENDPOINTS DE LA API
Al desplegar la aplicación en el puerto 8888 se puede acceder a Swagger mediante el URL http://localhost:8888/swagger-ui.html

- **POST /currency/{currencyId}**: permite crear una moneda desde cero con su valor en el mercado, código y nombre.
- **POST /currency/{currencyId}/rate**: permite actualizar el valor de mercado de una moneda.
- **GET /currency**: permite obtener la lista de monedas con su valor en dólares
- **POST /currency/exchange**: permite calcular un monto de una moneda en otra moneda.
