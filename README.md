El programa se corre en MessagingServer.java. Los parametros se definen en `resources/parameters.txt`.

#### Descripcion de `parameters.txt`
Cada linea es parametro numerico diferente.  
Linea 0: numero de clientes (threads)  
Linea 1: numero de mensajes que produce cada cliente (igual para todos)  
Linea 2: numero de filtros de spam (threads)  
Linea 3: numero de servidores (threads)  
Linea 4: capacidad maxima buzon de entrada  
Linea 5: capacidad maxima buzon de entrega  

#### Traducciones
Aclaracion de nombres con respecto al documento.  
Buzon de entrada = IncomingQueue  
Buzon de entrega = DeliveryQueue

