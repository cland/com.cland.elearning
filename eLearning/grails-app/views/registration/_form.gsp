
<z:rows>
<z:row>
        <z:label value="${message(code:'registration.course.label',default:'Course')}"/>
        <zkui:select name="course.id" width="200px" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${registrationInstance?.course?.id}"  />
    </z:row>
<%--    <z:row>--%>
<%--        <z:label value="${message(code:'registration.learner.label',default:'Learner')}"/>--%>
<%--        <zkui:select name="learner.id" from="${com.cland.elearning.PersonRole.findAllByRole(com.cland.elearning.Role.findByAuthority('LEARNER'))?.sort()?.person}" optionKey="id" value="${registrationInstance?.learner?.id}"  />--%>
<%--    </z:row>--%>

	<z:row>
		
		<z:label value="${message(code:'registration.learner.label',default:'Learner')}"/>
		<z:bandbox name="learnerBandbox" width="200px" id="learnerBandbox"  autodrop="true" readonly="true" onClick="learnerSearch.focus();" value="${registrationInstance?.learner}">
	        <z:bandpopup>
	            <z:vbox>
	                <z:hbox>
	                    <z:label value="Search Learners: (Lastname OR student number)"/>
	                    <z:textbox id="learnerSearch"/>
	                    <z:space />
						<z:toolbarbutton id="clearLearnerListBoxSearch" image="${fam.icon(name: 'drink_empty')}" label="${message(code:'default.button.clear.label',default:"Clear")}"/>
<%--						<z:toolbarbutton id="newLearner" client_onClick="addLearner();return false;" label="${message(code:'default.button.add.label',default:"New Learner")}"/>--%>
	                </z:hbox>
	                <z:listbox id="learnerListBox" width="650px" vflex="min">
	                </z:listbox>
	                <z:paging id="learnerPaging" pageSize="5"/>
	            </z:vbox>
	        </z:bandpopup>
	    </z:bandbox>
	    <z:longbox id="learnerIdBox" name="learner.id" visible="false"  value="${registrationInstance?.learner?.id}" />
	</z:row>

    <z:row>
        <z:label value="${message(code:'registration.regDate.label',default:'Registration Date')}"/>
        <z:datebox name="regDate" value="${registrationInstance?.regDate}"/>
    </z:row>

</z:rows>