<!DOCTYPE html>
<html lang="zh">
 <head>
<#include "${managerViewPath}/include/macro.ftl"/>
<#include "${managerViewPath}/include/meta.ftl"/>
</head>
<body>	
<@ms.content>
<@ms.contentBody>
	<@ms.contentNav title="自定义页面">
		<@ms.panelNavBtnGroup>
			<@ms.panelNavBtnAdd/>
		</@ms.panelNavBtnGroup>
	</@ms.contentNav >
	<@ms.contentPanel>
		<@ms.table head=['名称','模版路径','访问路径',"<th class='text-center'>操作</th>"]>
			<#if list?has_content>
				<#list list as item>
					<tr>
			   			<td style="width:30%">${item.modelTemplateTitle?default("")}</td>
						<td style="width:30%">${item.modelTemplatePath?default("")}</td>
						<td style="width:30%">${item.modelTemplateKey?default("")}</td>
						<td class="text-center">
							<a class="btn btn-xs red tooltips del-btn" data-toggle="tooltip" data-id="${item.modelTemplateId?c?default(0)}"  data-original-title="删除">
								<i class="glyphicon glyphicon-trash"></i>
							</a>
							<a class="btn btn-xs red tooltips editModel" data-toggle="tooltip" data-id="${item.modelTemplateId?c?default(0)}"  data-original-title="编辑" >
								<i class="glyphicon glyphicon-pencil"></i>
							</a>
						</td>
			   		</tr>
				</#list>
			<#else>
			<tr>
	       	<td colspan="4" class="text-center">
	          		<@ms.nodata content="暂无自定义页面！"/>
				</td>
	       </tr>
			</#if>
		</@ms.table>
		
		 <!--添加模块-->    
		<@ms.modal id="addEditModel" title="添加模块">
			<@ms.modalBody>
				<@ms.form isvalidation=true name="addEditForm"  action="" method="post"  >
					<@ms.text name="modelTemplateTitle" label="标题" labelStyle="width:25%" width="250" title="标题" placeholder="请输入标题" value="" validation={"maxlength":"20","required":"true", "data-bv-notempty-message":"标题不能为空","data-bv-stringlength-message":"标题在20个字符以内!"}/>
					<@ms.text name="modelTemplateKey"  label="访问路径" labelStyle="width:25%" width="250" title="访问路径" placeholder="请输入访问路径" value="" validation={"maxlength":"100","required":"true", "data-bv-notempty-message":"访问路径不能为空","data-bv-stringlength-message":"访问路径在100个字符以内!"}/>
					<@ms.formRow label="选择模板" labelStyle="width:25%" width="300" >			    	
					 	<select class="form-control template templateSelect" name="modelTemplatePath"></select>
					</@ms.formRow>
				</@ms.form>
			</@ms.modalBody>
			<@ms.modalButton>
				<@ms.button  value=""  id="addEditBtn"/>
			</@ms.modalButton>
		</@ms.modal>		
		<@ms.modal id="delete" title="删除提示!">
			<@ms.modalBody>
			  		确认删除？
			</@ms.modalBody>
			<@ms.modalButton>
				<@ms.button value="确认" id="rightDelete" class="btn btn-danger" value="删除"/>
			</@ms.modalButton>
		</@ms.modal>	
	<!--删除的模态框结束-->
	</@ms.contentPanel>
</@ms.contentBody>
</@ms.content>        

<script>

var tmplModelId;
var postUrl;
$(function(){
	//加载选择模块列表
	$(".templateSelect").request({url:base+"${baseManager}/template/queryTemplateFileForColumn.do",type:"json",method:"post",func:function(msg) {
		if(msg.length != 0 && ($(".template").html() == "" || $(".template").html() == "")){
   			for(var i=0; i<msg.length; i++){
	   			$(".templateSelect").append($("<option>").val(msg[i]).text(msg[i]));
	   		}
   		} else {
   			$(".templateSelect").append("<option>暂无文件</option>");
   		}
   		//使用select2插件
		$(".templateSelect").select2({width: "220px"});
	}});
		
	
	//添加模块
	$("#addButton").on("click",function(){
		postUrl= base+"${baseManager}/mdiy/modeltemplate/save.do";
		$("#addEditBtn").text("保存");
		$("#addEditModelTitle").text("添加模块");
		$("#addEditForm")[0].reset();
		$(".addEditModel").modal();
	});
	
	//编辑模块
	$(".editModel").on("click",function(){
		tmplModelId=$(this).attr("data-id");	//获取点击的id
		var actionUrl=base+"${baseManager}/mdiy/modeltemplate/"+tmplModelId+"/edit.do";	//编辑请求地址
		$(".editModel").request({url:actionUrl,type:"json",method:"post",func:function(data) {
			var model=eval("("+data.resultData+")");
			// 给表单赋值
			if(model!=null){
				$("input[name='modelTemplateTitle']").val(model.modelTemplateTitle);
				$("input[name='modelTemplateKey']").val(model.modelTemplateKey); 
				$(".templateSelect").find("option[value='"+model.modelTemplatePath+"']").attr("selected",true);
				$("#select2-chosen-2").text($(".templateSelect").find("option[value='"+model.modelTemplatePath+"']").text());
				$(".addEditModel").modal();
				postUrl=base+"${baseManager}/mdiy/modeltemplate/"+tmplModelId+"/update.do",
				$("#addEditBtn").text("更新");
				$("#addEditModelTitle").text("编辑模块");
			}	
		}});
		
		
	});
	
	//删除模块
	$(".del-btn").on("click",function(){
		tmplModelId=$(this).attr("data-id");
		$(".delete").modal();
	});
	
	//确认删除
	$("#rightDelete").on("click",function(){
		var actionUrl=base+"${baseManager}/mdiy/modeltemplate/"+tmplModelId+"/delete.do";
		$(this).text("删除中");
		$(this).attr("disabled",true);
		$("#rightDelete").request({url:actionUrl,type:"json",method:"post",func:function(data) {
			<@ms.notify msg= "删除成功!" type= "success" />
			location.reload();
		}});
	});
	
	//保存或更新
	$("#addEditBtn").on("click",function(){
		var vobj = $("#addEditForm").data('bootstrapValidator').validate();
		if(vobj.isValid()){
			$("#addEditForm").attr("action",postUrl);
			$("#addEditForm").postForm("#addEditForm",{func:function(msg){
				if (msg.result) {
		     		if($("#addEditBtn").text()=="保存"){
		     			<@ms.notify msg= "保存成功!" type= "success" />
		     		}else{
		     			<@ms.notify msg= "更新成功!" type= "success" />
		     		}
		    		location.reload();
		    	}else{
		    		alert(msg);
		    	}
			}});
		}
	});
});


</script>


</body>
</html>
