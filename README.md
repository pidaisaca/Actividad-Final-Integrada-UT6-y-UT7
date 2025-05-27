# Actividad-Final-Integrada-UT6-y-UT7
API REST en Java + Spring Boot que permite gestionar Usuarios y sus Notas de forma persistente, usando JPA.

Entidades necesarias:

          Relación
 - Usuario -------> Muchas Notas
 - Nota ----------> Un único usuario


Endpoints a hacer:

 - UsuarioController (/api/v1/usuarios)  
   GET /usuarios  
   GET /usuarios/{id}  
   POST /usuarios  
   PUT /usuarios/{id}  
   DELETE /usuarios/{id}  
   
 - UsuarioController (/api/v2)  
   POST /sign-in → recibe email y password, y guarda el hash (como en la Act.Ev – UT5)  
 
 - NotaController (/api/v1/notas)  
   GET /notas  
   GET /notas?usuarioId={id}&order={asc|desc}  
   GET /notas/{id}  
   POST /notas?usuarioId={id}  
   PUT /notas/{id}  
   DELETE /notas/{id}  
