# evaluation

Proyecto de evaluacion de Nisum

## Execution

***El proyecto esta desarrollado con VM version 11***

Para comenzar la aplicacion ejecute:

```
mvnw spring-boot:run
```

## Testing

Para ejecutar los Test de la aplicacion, ejecute:


```
mvnw test
```

## Postman

En el proyecto se provee una colección ***nisum.postman_collection.json*** exportada de postman, la cual se recomienda importar en el postman para probar la aplicacion.

Para probar las funcionalidades desde postman deben seguir el siguiente orden:

1. Ejecutar el endpoint ***http://localhost:8080/api/auth/register***. La petición debe contener un objeto JSON como el que se muestra, los valores de los campos pueden variar:

``` json 
{
    "name": "Jorge Luis Arango Labrada",
    "email": "jarango71@gmail.com",
    "password": "Arango2411",
    "phones": [
        {
            "number": "99 523 0554",
            "citycode": "1",
            "contrycode": "593"
        }
    ]
}
```
La respuesta esperada es como el siguiente JSON:

``` json 
{
    "token": "788af4fb-792a-444d-bbf3-8061726f84c8",
    "id": "5fea69d2-3328-4113-944a-c6f57b9e4bac",
    "created": "2022-07-21T16:42:34.7750159",
    "modified": "2022-07-21T16:42:34.7750159",
    "last_login": null,
    "isactive": false
}
```
2. El siguiente paso seria activar la cuenta, para esto ejecutar en endpoint ***http://localhost:8080/api/auth/activate/account***, el cual necesita como dato de entrada el ***token*** que retorno la llamada anterior. Un ejemplo de la peticion que se necesita se muestra en el json a continuacion:
``` json 
{
    "email": "jarango71@gmail.com",
    "token": "788af4fb-792a-444d-bbf3-8061726f84c8"
}
```
3. El proximo paso es autenticarse con el usuario y la contraseña ***http://localhost:8080/api/auth/login***, si no se activado la cuenta no se podra autentificar, por lo que no podra consumir ningun recurso. El json de la peticion es el siguiente:
``` json 
{
    "email": "jarango71@gmail.com",
    "password": "Arango2411"
}
```
4. Este paso consumir servicios que requieran autorizacion y autenticación. Para esto se expone un endpoint que permite cambiar el nombre del usuario en su perfil, a travez del endpoint ***http://localhost:8080/api/auth/user/profile***, se le debe pasar el header: ***Authorization: Bearer token que retorno el login*** 
``` json 
{
    "name": "Jorge Arango Labrada"
}
```



  

