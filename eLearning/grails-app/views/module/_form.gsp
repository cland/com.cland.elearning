
<z:rows>
	<z:row>
		<z:label value="${message(code:'module.name.label',default:'Name')}" />
		<z:textbox name="name" value="${moduleInstance?.name}" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'module.description.label',default:'Description')}" />
		<z:textbox rows="4" name="description"
			value="${moduleInstance?.description}" />
	</z:row>
	<z:row>
		<z:label value="${message(code:'module.duration.label',default:'Duration')}" />
		<z:hbox>
			<z:textbox name="duration" value="${moduleInstance?.duration }"/>
			<z:label value="${message(code:'module.durationunit.label',default:'Duration Unit')}" />
	    	<zkui:select name="durationUnit" from="['hours','days','weeks','months','years']" 
	    	value="${moduleInstance?.durationUnit}" valueMessagePrefix="" noSelection="['months': 'months']" />
		</z:hbox>	
		
	</z:row>
</z:rows>