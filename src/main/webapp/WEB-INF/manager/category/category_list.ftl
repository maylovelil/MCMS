<@ms.html5>
    <@ms.nav title="${Session.model_title_session?default('')}管理">
		<@ms.buttonGroup>
			<@ms.addButton url="${managerPath}/category/add.do?${params}"/>
		</@ms.buttonGroup>
    </@ms.nav>
    <@ms.panel>
		<@ms.table head=['编号,90','标题','操作,100'] id="tableConterent">
			<#if categoryJson?has_content && categoryJson!="[]">
	        	<@ms.treeTable treeId="peopleAddressTree"   tbodyId="tableConterent" json="${categoryJson?default('')}" jsonName="categoryTitle" jsonId="categoryId" jsonPid="categoryCategoryId"/>
	      	<#else>
             	<tr>
		            <td colspan="3" class="text-center">
		            	<@ms.nodata/>
					</td>
	          	</tr>                          
        	</#if>
		</@ms.table>
		
		<script id="afterpeopleAddressTree" type="text/x-jquery-tmpl">
			<td>
				<#if categoryLevel?if_exists?has_content && categoryLevel?number gt 0 > 
				<a class="btn btn-xs red tooltips addpeopleAddressTree" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="添加" >
                 	<i class="glyphicon glyphicon-plus"></i>
                </a>	
                </#if>
				<a class="btn btn-xs red tooltips editclumnTree" onclick="editclumnTree(this)" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="编辑">
             		<i class="glyphicon glyphicon-pencil"></i>
            	</a>
				<a class="btn btn-xs red tooltips deleteclumnTree" onclick="deleteclumnTree(this)" data-toggle="tooltip" data-id="{{= categoryId}}" data-original-title="删除" >
             		<i class="glyphicon glyphicon-trash"></i>
            	</a>
			</td>
		</script>
    </@ms.panel>
</@ms.html5>	        
   		<!--删除-->    
		<@ms.modal modalName="delete" title="删除类别">
			  <@ms.modalBody>
			  		确定要删除所选的类别吗?
		     </@ms.modalBody>
			 <@ms.modalButton>
		 		<@ms.button class="btn btn-danger rightDelete" value="确定"/>
		 	</@ms.modalButton>
		</@ms.modal>
<script>
var categoryId="";
$(function(){

	$("body").delegate("#addButton,.addpeopleAddressTree", "click", function(){    
	
		var categoryCategoryId = $(this).attr("data-id");
		if(categoryCategoryId==undefined){
			location.href = base+"${baseManager}/category/add.do?${params}";
			return;
		}
		
		location.href = base+"${baseManager}/category/add.do?${params}&categoryCategoryId="+categoryCategoryId;
	});

	
	
	//确认删除
	$(".rightDelete").on("click",function(){
	
		$(this).request({url: base+"${baseManager}/category/"+categoryId+"/delete.do",type:"json",method:"post",func:function(msg) {
			var columnJson = $.parseJSON(msg.resultMsg);
			if(columnJson==false){
				<@ms.notify msg= "请先删除子栏目" type= "warning" />
				$(".delete").modal("hide");
			}else{
				<@ms.notify msg= "删除成功" type= "success" />
				location.reload();
			}
		}});
	});
});


function editclumnTree(obj){
	var categoryId = $(obj).attr("data-id");
	location.href = base+"${baseManager}/category/edit.do?categoryId="+categoryId+"&${params}";
}

function deleteclumnTree(obj){
	categoryId = $(obj).attr("data-id");
	$(".delete").modal();
}
</script>
</body>

</html>













