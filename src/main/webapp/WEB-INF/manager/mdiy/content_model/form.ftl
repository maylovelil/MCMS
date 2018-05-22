<@ms.html5>
	 <@ms.nav title="自定义模型表编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="contentModelForm" isvalidation=true>
    		<@ms.hidden name="cmId" value="${contentModelEntity.cmId?default('')}"/>
    			<@ms.text label="表单提示文字" name="cmTipsName" value="${contentModelEntity.cmTipsName?default('')}"  width="240px;" placeholder="请输入表单提示文字" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"表单提示文字长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="表单名称" name="cmTableName" value="${contentModelEntity.cmTableName?default('')}"  width="240px;" placeholder="请输入表单名称" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"表单名称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/mdiy/contentModel/save.do";
	if($("input[name = 'cmId']").val() > 0){
		url = "${managerPath}/mdiy/contentModel/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#contentModelForm").data("bootstrapValidator").validate();
			var isValid = $("#contentModelForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'contentModelForm']").serialize(),
			url:url,
			success: function(status) {
				if(status != null) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mdiy/contentModel/index.do";
				}
				else{
					<@ms.notify msg= "保存或更新失败！" type= "danger" />
					location.href= "${managerPath}/mdiy/contentModel/index.do";
				}
			}
		})
	}	
</script>
