<%@ page import="com.cland.elearning.AttachmentHolder" %>



<div class="fieldcontain ${hasErrors(bean: attachmentHolderInstance, field: 'person', 'error')} required">
	<label for="person">
		<g:message code="attachmentHolder.person.label" default="Person" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="person" name="person.id" from="${com.cland.elearning.Person.list()}" optionKey="id" required="" value="${attachmentHolderInstance?.person?.id}" class="many-to-one"/>
</div>
<div class="fieldcontain" style="padding:5px;">
<h3>Pictures</h3><br/>
<input type="file" name="pictures"/>
<input type="file" name="pictures"/>

<br/><br/>
<h3>Movies</h3><br/>
<input type="file" name="movies"/>
<input type="file" name="movies"/>
<br/><br/>
<h3>Documents</h3><br/>
<input type="file" name="docs"/>
<input type="file" name="docs"/>
</div>

