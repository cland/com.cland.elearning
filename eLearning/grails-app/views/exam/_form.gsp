
<z:rows>

    <z:row>
        <z:label value="${message(code:'exam.factorOperand.label',default:'Factor Operand')}"/>
        <zkui:select name="factorOperand" from="${examInstance.constraints.factorOperand.inList}" value="${examInstance?.factorOperand}" valueMessagePrefix="exam.factorOperand"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'exam.factor.label',default:'Factor')}"/>
        <z:textbox name="factor" value="1" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'exam.maxMark.label',default:'Max Mark')}"/>
        <z:textbox name="maxMark" value="${fieldValue(bean: examInstance, field: 'maxMark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'exam.submodule.label',default:'Submodule')}"/>
        <zkui:select name="submodule.id" from="${com.cland.elearning.SubModule.list()}" optionKey="id" value="${examInstance?.submodule?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'exam.testNumber.label',default:'Test Number')}"/>
        <z:textbox name="testNumber" value="${fieldValue(bean: examInstance, field: 'testNumber')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'exam.weight.label',default:'Weight')}"/>
        <z:textbox name="weight" value="${fieldValue(bean: examInstance, field: 'weight')}" />
    </z:row>

</z:rows>