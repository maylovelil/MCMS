<@ms.html5>
    <@ms.nav title="${modelTitle}编辑">
		<@ms.saveButton id="saveFormButton" postForm="categoryForm"/>
    </@ms.nav>
    <@ms.panel>
   			<@ms.form isvalidation=true name="categoryForm"   action="${managerPath}/category/${autoCURD}.do"  redirect="${managerPath}/category/list.do?modelId=${modelId}&modelTitle=${modelTitle}&categoryLevel=${category.categoryLevel}">
				    		<input type="hidden" name="categoryId" id="categoryId"  value="${category.categoryId}"/>
				    		<@ms.text name="categoryTitle" width="300" label="${Session.model_title_session?default('栏目')}名称" 
				    			title="${Session.model_title_session?default('栏目')}名称" placeholder="${Session.model_title_session?default('栏目')}名称" 
				    		 	value="${category.categoryTitle?default('')}" id="" validation={"data-bv-stringlength":"true","required":"true",
				    		 	"data-bv-notempty-message":"必填项目", 
				    		 	"data-bv-regexp":"true","data-bv-regexp-regexp":'^[^[!@#$%^&*()_+-/~?！@#￥%…&*（）——+—？》《：“‘’ ]+$',
				    		 	"data-bv-stringlength-max":"20","data-bv-regexp-message":"${Session.model_title_session?default('栏目')}名称不能包含特殊字符",
				    		 	"data-bv-stringLength-message":"长度不能超过20个字符"}
							/>
				    		<@ms.textarea id="description"  name="categoryDescription" label="描述:"  title="栏目描述" placeholder="类别描述" maxlength="150" value="${category.categoryDescription?default('')}"/>
				    		
				    		<@ms.formRow label="缩略图">
								<@ms.uploadImg path="category" inputName="categorySmallImg" size="15" filetype="" msg=""  maxSize="1" imgs="${category.categorySmallImg?default('')}" />
				    		</@ms.formRow>
				    		<#if category.categoryLevel gt 0>
					    		<@ms.formRow label="关联父分类" width="300">
					            	<@ms.inputTree  
					            		treeId="inputTree" 
					            		json="${listCategory?default('')}" 
					            		jsonId="categoryId" 
					            		jsonPid="categoryCategoryId" 
					            		jsonName="categoryTitle"
					            		name="categoryCategoryId"
					            		rootName="顶级节点"
					            		required=false
					            		text="${modelName?default('请选择关联节点')}"
					            		value="${category.categoryCategoryId?default('0')}"
					            		onclick="isSelf"
					            		parent=true
					            	/>				    		
					    		</@ms.formRow>
				    		</#if>
	    	</@ms.form>	
    </@ms.panel>
</@ms.html5>
<script> 
	function isSelf(event,treeId,treeNode) {
		if (treeNode.categoryId==${category.categoryId}) {
			<@ms.notify msg="不能选择当前节点！" />
			return false;
		}
		
		//检查编辑的时候是否将父节点移动到子节点下面
		var nodes = zTreeObjinputTree.getNodesByParam("categoryId", treeNode.categoryId, zTreeObjinputTree.getNodeByParam("categoryId", ${category.categoryId}, null));
		if (nodes.length > 0) {
			<@ms.notify msg="不支持父节点移动到子节点！" />
			return false;
		}
		return true;
	}
</script>