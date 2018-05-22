<#--
4.5.5开始废弃
<input type="checkbox"/>
-->
<#macro checkboxlist
	list listKey="" listValue="" valueList=[]
	label=""     colon=":" hasColon="true"
	id="" name="" class="" style="" size="" title="" disabled=false
	validation="" direction=true
	width=""
	labelStyle=""
	help=""
	>
<div class="form-group ms-form-group">	
	<#include "control.ftl"/><#rt/>
	<div class="col-sm-10" <#if width!=""> style="${width}px"</#if><#rt/>>
		<#if list?is_sequence>
			<#if listKey!="" && listValue!="">
				<#list list as item>
					<#local rkey=item[listKey]>
					<#local rvalue=item[listValue]>
					<#local index=item_index>
					<#local hasNext=item_has_next>
					<#include "checkboxlist-item.ftl"><#t/>
				</#list>
			<#else>
				<#list list as item>
					<#local rkey=item>
					<#local rvalue=item>
					<#local index=item_index>
					<#local hasNext=item_has_next>
					<#include "checkboxlist-item.ftl"><#t/>
				</#list>
			</#if>
		<#else>
			<#list list?keys as key>
				<#local rkey=key/>
				<#local rvalue=list[key]/>
				<#local index=key_index>
				<#local hasNext=key_has_next>
				<#include "checkboxlist-item.ftl"><#t/>
			</#list>
		</#if>
	</div>
</div>	
</#macro>