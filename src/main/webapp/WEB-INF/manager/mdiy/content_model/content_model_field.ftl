<#--自定义内容模型字段-->
<#--field:字段实体-->
<#include "${managerViewPath}/include/macro.ftl"/>
<#macro field type name filedName value defaultValue isnull>
	<#--根据filed实体的类别来显示项目的控件-->
		<#switch type>
			<#case "1">			
				<#if value!="">
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${value}" />
				<#else>
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${defaultValue}"/>
				</#if>
				
				
			<#break>
			<#case "2">
				
				<#if value!="">
					<@ms.textarea name="${filedName}"  width="600" label="${name}"  wrap="Soft" rows="4"  size=""  value="${value}" placeholder="请输入${name}"/>
				<#else>
					<@ms.textarea name="${filedName}"  width="600" label="${name}"  wrap="Soft" rows="4"  size=""  value="${defaultValue}" placeholder="请输入${name}"/>
				</#if>
			<#break>
			<#case "3">
				<@ms.editor name="${filedName}"  width="600" label="${filedName}"content="${value}"  width="688px;" appId="${appId?default(0)}"/>			
				 
			<#break>
			<#case "4">
				<#if value!="">
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${value}"/>
				<#else>
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${defaultValue}"/>
				</#if>
				
				<script>
					//判断用户输入的是否为数字
					$("input[name='${filedName}']").blur(function(){
						if((isNaN($(this).val()))){
							<@ms.notify msg= "${name}只能输入数字" type= "warning" />
							$(this).val("");
						}
					});
				</script>
				
			<#break>
			<#case "5">
				<#if value!="">
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${value}"/>
				<#else>
					<@ms.text name="${filedName}"  width="300"  label="${name}" title="${name}" size="5"  placeholder="请输入${name}"  value="${defaultValue}"/>
				</#if>
				
				<script>
					//判断用户输入的是否为数字
					$("input[name='${filedName}']").blur(function(){
						if((isNaN($(this).val()))){
							<@ms.notify msg= "${name}只能输入数字" type= "warning" />
							$(this).val("");
						}
					});
					
				</script>
				
			<#break>
			<#case "6">
				<#if value!="">
					<@ms.date label="${name}" name="${filedName}" value="${value}" single=true width="300"/>
				<#else>
					<@ms.date label="${name}" name="${filedName}" value="${defaultValue}" single=true width="300"/>
				</#if>
			<#break>
			<#case "7">
					<@ms.formRow label="${name}">
							<@ms.uploadImg path="article" inputName="${filedName}" size="4"   msg="提示：可以上传多张图片"  maxSize="10" imgs="${value?default('')}" />
					</@ms.formRow>
			<#break>
			
			<#case "8">
				<@ms.text name="${filedName}" label="${name}" width="300px;" value="${value}" title="${filedName}" readonly="readonly" />
				<@ms.formRow >
						<@ms.uploadFile path="article"  inputName="${filedName}" size="1"  msg="建议上传5M以下的文件,文件类型支持格式：zip、rar、doc、xls、xlsx、ppt、pptx、docx、txt、pdf " filetype="*.zip;*.rar;*.doc;*.xls;*.xlsx;*.ppt;*.pptx;*.docx;*.txt;*.pdf" maxSize="5" callBack="setFile${filedName}" isRename="false"/>
				</@ms.formRow>
				
				<script>
				 function setFile${filedName}(file){
			   	   	if(file < 0){   
			   	   		}else{  
				   		<@ms.notify msg="上传成功!" type= "success" />  
			   	   		$("input[name='${filedName}']").val(file);    	   
			   	   	}
   				}
	   			</script>
	   			
			<#break>
			
			<#case "9">
				<@ms.formRow label="${name}" width="300">
						<select class="form-control" name="${filedName}"></select>
				</@ms.formRow>
				<script>
						var ${filedName}Select= new Array();
						${filedName}Select ="${defaultValue}".split(",");		
						for(var i = 0;i<${filedName}Select.length;i++){
							var j = i+1;
							if(${filedName}Select[i]!=""){
								$("select[name='${filedName}']").append("<option selected value="+(i+1)+">"+${filedName}Select[i]+"</option>");
							}
						}
						$("select[name='${filedName}']").val("${value}");
				</script>				
			<#break>
			<#case "10">
				<@ms.radio name="${filedName}" label="${name}" list=defaultValue?split(',') value="${value}"/>
				<script>
						var dselect= new Array();
						dselect ="${defaultValue}".split(",");		
						var checkValue="${value}";
						if(checkValue == ''){
							checkValue = dselect[0];
						}
						$(":radio[name='${filedName}'][value='" + checkValue + "']").prop("checked", "checked");
				</script>
			<#break>
			<#case "11">
				<@ms.checkbox name="${filedName}" label="${name}" list=defaultValue?split(',') valueList=value?split(",")/>
			<#break>
			
			<#default>	
		</#switch>
		
</#macro>


<#if listField?has_content>
			<#list listField as listField>
					<#if filedValue?has_content>
							<@field type="${listField.fieldType}" name="${listField.fieldTipsName}" filedName="${listField.fieldFieldName}" value="${filedValue['${listField.fieldFieldName}']?default('')}"  defaultValue="${listField.fieldDefault?default('')}" isnull="${listField.fieldIsNull?default()}"/>
					<#else>
							<@field type="${listField.fieldType}" name="${listField.fieldTipsName}" filedName="${listField.fieldFieldName}" value=""  defaultValue="${listField.fieldDefault?default('')}" isnull="${listField.fieldIsNull?default()}"/>
					</#if>
			</#list>
</#if>
