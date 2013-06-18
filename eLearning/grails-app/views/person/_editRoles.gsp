 
    <z:row>
		<z:label value="${message(code:'person.role.label',default:'Roles')}" />   
		<z:vlayout>
		<g:each var="auth" in="${com.cland.elearning.Role.list()}">
			<g:set var="isRoleChecked" value="false" />
			<g:if test="${params.action=='edit'}">
    		<g:each var="sel" in="${roleMap.authority }">    		
        		 <g:if test="${auth?.authority==sel}">           		  
        		 	<g:set var="isRoleChecked" value="true" />
        		 </g:if>        		      		
    		</g:each>
    		</g:if>
    		
    		 <z:checkbox name="role_${auth.authority}" id="${auth.id}" checked="${isRoleChecked}" label="${auth.authority}"/>
    		 
		</g:each>
		</z:vlayout>
</z:row>		
	
	
    
   