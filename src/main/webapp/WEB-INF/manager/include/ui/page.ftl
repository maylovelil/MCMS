<#--后台的UI界面通用区域定义-->
<#macro html5 width="100%" style="">
<!DOCTYPE html>
<html lang="en">
	<head>
		<#include "${managerViewPath}/include/macro.ftl"/>
		<#include "${managerViewPath}/include/meta.ftl"/>
	</head>
	<body>
		<div class="ms-content">
				<div class="ms-content-body" style="width:${width};${style}">
		   			<#nested/>
		   			<div class='notifications ms-notifications top-right'></div>
		   		</div>
		</div>
		
	</body>
</html>
</#macro>
<#macro nav title="板块名称" back=false style="">
    <div class="ms-content-body-title" style="${style}">
        <strong>${title}</strong>
        <#nested/>
        <#if back>
        	<@ms.backButton/>
        </#if>
    </div>
</#macro>

<#--面板-->
<#macro panel style="">
	    <div class="ms-content-body-panel" style="${style}"> 
	   		<#nested/>
	   		
	    </div>
</#macro>

<#--面板导航-->
<#macro panelNav empty=false>
    <div class="ms-content-body-panel-nav"  <#if empty>style="  padding: 0;"</#if>>
        <#nested/>
    </div>
</#macro>

<#--提示-->
<#macro notify msg="提示信息" type="warning">
			    		 $('.ms-notifications').offset({top:43}).notify({
			    		    type:'${type}',
						    message: { text:'${msg}' }
						 }).show();	
</#macro>