<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="usuario.css" type="text/css" />

<title>Pagina inicial</title>
</head>
<body>
			<% String usuario = ""; %>

    <div id="all">
        <h1>Bienvenido ususario <%= usuario %></h1>

	<%@ page import= "Tickects/GestionApp.java" %>
		
			<div class="grid">
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Registrar incidencia</h2>
    				 <form action="">
    				 	Descripccion: <input type="text" name="descripcion">
    				 	Prioridad: <input type="number" name="prioridad">
    				 </form>
      				 <%gestionApp.insertaIncidencia(request.getParameter("descripcion"), request.getParameter("prioridad"), gestionApp.buscaUsuario(usuario), gestionApp.buscaUsuario(usuario).getId()); %>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Consultar incidencias abiertas</h2>
      				 <% gestionApp.buscaUsuario(usuario); %>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Consultar incidencias cerradas</h2>
      				 <% gestionApp.buscaIncidenciasCerradas(); %>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Mostrar mi perfil</h2>
      				 <% gestionApp.buscaUsuario(usuario) %>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Cambiar clave</h2>
    				 <input type=password placeholder="nueva contraseņa" name="nuevaClave">
      				 <% gestionApp.buscaUsuario(usuario).setClave(request.getParameter("nuevaClave"));
                     gestionApp.actualizarClave(gestionApp.buscaUsuario(usuario), (request.getParameter("nuevaClave") ); %>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Guardar cambios (@deprecated)</h2>
      				 <button><a href="#">Ir</a></button>
   				 </div>
        	
        	
        	
   				 <div class="grid-item">
    				 <h2 class="grid-item-title">Cerrar sesion</h2>
      				 <button><a href="/index.jsp">Ir</a></button>
   				 </div>
        	</div>
		


</body>
</html>
