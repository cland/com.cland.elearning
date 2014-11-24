<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'exam.label', default: 'Exam')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
        <link rel="stylesheet"	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
        <g:javascript library="jquerymin" />
		<g:javascript library="jqueryuilatest" />
    </head>
    <body>

        <z:window style="padding:5px" apply="com.cland.elearning.exam.ListComposer">
            <z:hlayout>
            	<h2 style="padding: 2px 2px 1px 1px;"> Exams </h2>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
               <z:label value="${message(code:'exam.module.name',default:'Module:')}"/>
                <z:textbox id="keywordBoxModule" />
                <z:space/><z:space/>
                <z:label value="${message(code:'exam.subModule.name',default:'Mode of Learning:')}"/>
                <z:textbox id="keywordBoxSubmodule" />
                <z:space/><z:space/>
              	<z:toolbarbutton href="${createLink(action:'jq_export_modules', params:[save: '1'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['All'])}"/>
            </z:hlayout>
            <g:if test="${flash.message}">
                <z:window mode="popup" border="normal">
                    <z:hlayout>
                        <z:image src="/images/skin/information.png"/>
                        <z:div>
                            ${flash.message}
                        </z:div>
                    </z:hlayout>
                </z:window>
            </g:if>
            <z:grid id="grid" emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
                <z:columns sizable="true">
               		<z:column label="${message(code: 'exam.module.label', default: 'Module')}"/>
               		<z:column label="${message(code: 'exam.submodule.label', default: 'Mode of Learning')}"/>
               		<z:column label="${message(code: 'exam.testNumber.label', default: 'Exam No.')}" width="80px"/>               		
               		<z:column label="${message(code: 'exam.maxMark.label', default: 'Max Mark')}" width="70px"/>
               		<z:column label="${message(code: 'exam.weight.label', default: 'Weight')}" width="70px"/>
               		<z:column label="${message(code: 'exam.factor.label', default: 'Factor')}" width="70px"/>                    
                    <z:column label="${message(code: 'exam.factorOperand.label', default: 'Factor Operand')}"  width="70px"/>
                    <z:column label="${message(code: 'exam.status.label', default: 'Status')}"  width="70px"/>

                    <z:column width="120px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="50"/>
        </z:window>
        <script>
        function editExam(exam_id){
  		  	 var $dialog = $('<div><div id="wait" style="font-weight:bold;text-align:center;">Loading...</div></div>')             
                          .load('../exam/dialogedit/' +exam_id)
                          
                          .dialog({
                              autoOpen: false,
                              dialogClass: 'no-close',
                              width:650,
                              beforeClose: function(event,ui){
                              	
                              },
                              buttons:{
                                  "DONE":function(){
                                	  location.reload();
                                      },
                                   "CANCEL":function(){
                                	   $(this).dialog('close')
                                       }
                                 },
                              close: function(event,ui){
                            	  $(this).dialog('destroy').remove()
                            	  //location.reload();
                              },
                              position: {my:"top",at:"top",of:window},
                              title: 'Edit Exam'                         
                          });
                              
                          $dialog.dialog('open');
                          
  		  }
        $(document).ready(function(){
        	
            });
        </script>
    </body>
</html>