<table class="results_table">
<g:if test="${results?.size() > 0 }">

	<g:set var="totalMaxMark" value="${results?.sum { it?.maxMark } }"/>
	<g:set var="totalMark" value="${results?.sum { it?.mark } }"/>
	<g:set var="totalPercentMark" value="${(totalMark/totalMaxMark)*100 }"/>
	
	<tr><th colspan="4" style="text-align:center;">${type }</th></tr>
	<tr><td></td><td><b>MAX</b></td><td><b>MARK</b></td><td class="td-last"><b>%</b></td></tr>
	<g:each in="${ results?.sort{it?.testNumber}}" var="result" status="i">
		<tr><td>${result?.testNumber }</td><td>${result?.maxMark }</td><td>${result?.mark }</td><td class="td-last">${String.format( '%.1f',result?.percentMark) }</td></tr>
	</g:each>
	<tr class="total-row"><td></td><td><b>${totalMaxMark }</b></td><td><b>${totalMark }</b></td><td><b>${String.format( '%.1f',totalPercentMark) }</b></td></tr>
</g:if>
<g:else>
	<tr><th colspan="4" style="text-align:center;">${type }</th></tr>
	<tr><td></td><td><b>MAX</b></td><td><b>MARK</b></td><td><b>%</b></td></tr>
	<tr><td colspan="4" style="text-align:center;">No data</td></tr>
</g:else>
</table>