15-04-2024 | 19:23
Status: #Process 
Tags: [JavaEE](../JavaEE.md) 

---
# Parte A

### Ejercicio 1
"*1. Baje el código del proyecto de prueba 03a_JakartaAuthorization, importe el mismo a Eclipse y ejecute el comando mvn package wildfly:dev para provisionar el servidor y lanzar el mismo*"

Se descarga el código del repositorio https://github.com/gabrielaramburu/TallerJakartaEE
Y se deployá en WildFly con:
```Bash
mvn package wildfly:dev
```

Tras esto se hace una sencilla prueba ejecutando el endpoint que no precisa ningún usuario o credencial, localizado en *MensajeComunApi.java*

Aquí se evidencia su funcionamiento:
![](_attachments/Pasted%20image%2020240415195715.png)

---

### Ejercicio 2
*2) Configurar usuarios y sus respectivos grupos es el servidor WildFly. 
Utilizar el script localizado en /target/server/bin/ llamado add-user.sh  
Agregar usr1 al grupo empleado, usr2 al grupo empleado y usr3 al grupo gerente.
- a) analice el contenido del archivo application-roles.properties
- b) analice el contenido del archivo application-users.properties (ambos localizados en /server/standalone/configuration


Se accede a: */TallerJakartaEE/03a_JakartaEE-Authorization/target/server/bin/*
en donde se ejecuta el .sh para poder añadir los usuarios.
~~Nota para mi mismo: contraseña usr1 y 2: papa
usr3: zapallo~~


Revisando la configuración que se encuentra en: /target/server/standalone/configuration
podemos ver los usuarios que hemos creado en el archivo *application-users.properties*
![](_attachments/Pasted%20image%2020240416181157.png)
Cuyas contraseñas que hemos usado han sido encriptados a base64

además de eso, en el archivo *application-roles.properties*
podemos observar que usuarios tienen que roles:
![](_attachments/Pasted%20image%2020240417160520.png)

---
### Ejercicio 3
*"Invocar utilizando culr (sin envío de credenciales) el endpoint gerente/enviarMensaje?mensaje=hola 
Analice la respuesta"*

Si intentamos tirar un curl sin creedenciales al endpoint de *MensajeGerenteApi* nos tirá el siguiente error:
![](_attachments/Pasted%20image%2020240417161420.png)
Como se puede ver, nos indica que es un acceso inautorizado, con el error 401.
Lo mismo sucede si intentamos llamar al endpoint con las creedenciales incorrectas

---
### Ejercicio 4
*"Invocar utilizando el browser el mismo endpoint del punto 2. Ingrese las credenciales y observe el resultado mostrado."*

Si intentamos acceder al mismo endpoint desde nuestro navegador, ocurre lo siguiente
![](_attachments/Pasted%20image%2020240417162349.png)



El navegador nos pide las credenciales para poder llevar a cabo la petición, si le pasamos las credenciales correctas nos recibe con esto:
![](_attachments/Pasted%20image%2020240417162439.png)

---
### Ejercicio 5
*"1. Invoque al mismo endpoint utilizando nuevamente curl pero esta vez seteando credenciales:*

*a. utilice las credenciales del usuario usr1. Analice el resultado*
*b. utilice la credenciales del usuario usr2. Analice el resultado"*

Intentar hacer la petición utilizando tanto el usr1 como el usr2, nos devuelve el mismo resultado:
![](_attachments/Pasted%20image%2020240417162956.png)
Acá podemos ver que el error cambió, ahora nos muestra el status 403 prohibido, esto es debido a que estámos intentando conectarnos con un usuario que no es parte del rol que tiene permiso a utilizar esa petición.


Ahora, habiendo creado el usuario de gerente correctamente, si llamamos al endpoint pasandole las credenciales del usr3, podremos observar su correcto funcionamiento:
![](_attachments/Pasted%20image%2020240417161825.png)

---
### Ejercicio 6
*"Intente acceder utilizando culr al endpoint /gerente/enviarMensajeSeguro utilizando un usuario que pertenezca al grupo gerente. Analice el resultado"*

Al intentar conectarnos al endpoint que precisa una conexión segura, nos topamos con el siguiente error:
![](_attachments/Pasted%20image%2020240417164203.png)

En este caso particular se debe a que no me encuentro en la misma carpeta que el certificado SSL que se precisa usar, pero si me muevo a la carpeta que contiene dicho certificado nos muestra lo siguiente:
![](_attachments/Pasted%20image%2020240417164405.png)

Este error ocurre debido a que el certificado *"certificadoPrueba.pem"* no fue creado con la computadora que corre el curl, por lo que falla y no nos permite acceder al endpoint.

---
### Ejercicio 7
*"1. Generar certificado con llave pública del servidor, utilizando la herramienta openssl. Si la misma no está disponible instale la misma desde la consola.

a. observe certificado con : openssl s_client -showcerts -connect localhost:8443
b. genere archivo de certificados .pen con
```Bash
openssl s_client -showcerts -connect localhost:8443 </dev/null | sed -n -e '/-.BEGIN/,/-.END/ p' > certificadoPrueba.pem"*
```

Utilizando el comando provisto por el profe, nos generamos un certificado SSL para nuestro servidor:
![](_attachments/Pasted%20image%2020240417164835.png)

Cabe mencionar que el certificado es auto firmado, no hay ninguna entidad que nos lo valide, esto suele generar muchos problemas dependiendo de a que servidor nos estémos conectando, pero para este caso será más que suficiente.

---
### Ejercicio 8
*"Invoque nuevamente al endpoint del punto 6 utilizando el certificado generado. Obtenga la configuración del curl desde el ejemplo que aparece en el código del proyecto. Analice la respuesta."*

Tras crear nuestro propio certificado, me propongo a lanzar el mismo curl que antes:
![](_attachments/Pasted%20image%2020240417165407.png)

Ahora si funciona correctamente el endpoint, aunque cabe destacar que el propio servidor se percató que es un certificado auto firmado, y que no deberíamos usarlos fuera del ambiente de testing.

---

# Parte B

### Ejercicio 1
*"Implemente un nuevo método en la API que exponga una funcionalidad solo para los usuarios con el rol empleados. Exponga la misma de forma cifrada (https)"*

Se implementa un nuevo archivo con el endpoint para los empleados llamado *MensajeEmpleadoApi.java* en donde se encuentra el siguiente código:
![](_attachments/Pasted%20image%2020240417185835.png)

Por si solo el código simplemente expone una funcionalidad más, para que solo los empleados puedan usarla hay que configurar el web.xml

dentro del mismo añadí las siguientes líneas:
![](_attachments/Pasted%20image%2020240417190931.png)
Con el web.xml ya configurado, me dispuse a tirar CURLs como loco


Aquí el resultado de tirar un curl sin ser empleado ni usar SSL:
![](_attachments/Pasted%20image%2020240417191147.png)


también conseguimos el mismo resultado si tiramos un curl con credenciales apropiadas pero sin SSL:
![](_attachments/Pasted%20image%2020240417191911.png)


Luego tenemos que pasa si tiramos al URL correcto pero sin credenciales:
![](_attachments/Pasted%20image%2020240417192002.png)


Y por ultimo, si lanzamos correctamente el URL y además estamos usando credenciales de empleado:
![](_attachments/Pasted%20image%2020240417192121.png)



# Parte C
*"Convertir la API realizada en la tarea del ejercicio 2 (API REST) para que la misma funcione de forma segura. (http)

*a)El endpoint agregar y borrar empleado solo podrá ser ejecutado por usuarios con rol recursosHumanos.

*b)Listar las tareas relacionadas a cada empleado deberá de ser ejecutados por usuarios con rol gerente.*

*c)Crear los usuarios y sus respectivos roles para poder probar los mismos.*
*Realizar las pruebas necesarias con curl.*

*d)Realizar los cambios necesarios para poder acceder a los endpoint utilizando una conexión segura. (https)"*

Se insertan 2 usuarios nuevos para empezar a testear las funcionalidades, se crea al 

- usuario: **SAPO-PEPE** 
- con contraseña: **pepe** 
- con rol: **recursosHumanos**

- usuario: **VACA-LOLA**
- contraseña: **vacaloka**
- con rol: **gerente**
![](_attachments/Pasted%20image%2020240417214008.png)

Además, se genera un nuevo certificado SSL utilizando el siguiente código:
```Bash
openssl s_client -showcerts -connect localhost:8443 </dev/null | sed -n -e '/-.BEGIN/,/-.END/ p' > certificadoSSL.pem
```

Se modifica nuevamente el web.xml para dejar configurado las autorizaciones y roles necesarios para los endpoints:
![](_attachments/Pasted%20image%2020240417213001.png)



también se dejan los nuevos curls actualizados en los endpoint correspondientes.
Con esto dicho ~~o escrito~~ procedo a probar los endpoint:


listar tareas:
![](_attachments/Pasted%20image%2020240417215011.png)


listar tareas con credenciales equivocadas:
![](_attachments/Pasted%20image%2020240417215217.png)


añadir empleado:
![](_attachments/Pasted%20image%2020240417215824.png)

añadir empleado con credenciales equivocadas:
![](_attachments/Pasted%20image%2020240417220032.png)


borrar empleado:
![](_attachments/Pasted%20image%2020240417220426.png)


borrar empleado con credenciales:
![](_attachments/Pasted%20image%2020240417220615.png)
# Referencias