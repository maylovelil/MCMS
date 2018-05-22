<@ms.html5>
	<@ms.nav title="自定义搜索表管理"></@ms.nav>
	<style>
		.select2-container .select2-container--default {  
		 	height: 34px;  
		} 
		.select2-container .select2-selection--single{
			font: inherit;
			border: 1px solid #ccc;
		    display: block;
		    height: 34px;
		    padding: 0px 3px;
    		font-size: 14px;
    		color: rgb(85, 85, 85);
		}
	</style>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@shiro.hasPermission name="mdiy:search:save"><@ms.addButton openModal="searchModal"/></@shiro.hasPermission>
					<@shiro.hasPermission name="mdiy:search:del"><@ms.delButton id="delSearchBtn"/></@shiro.hasPermission>
				</@ms.buttonGroup>
				<@ms.button id="setUp" value="高级设置"/>
			</@ms.panelNav>
		</div>
		<table id="searchList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delSearch" title="搜索数据删除" >
		<@ms.modalBody>删除选中记录
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteSearchBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>
<@ms.modal modalName="searchModal" title="搜索设置">
	 <@ms.modalBody>
	 	<@ms.form isvalidation=true name="searchForm" action="${managerPath}/mdiy/search/save.do" redirect="${managerPath}/mdiy/search/index.do">
				<@ms.hidden name="searchId" value="0"/>
				<@ms.text label="搜索名称"  id="searchName" title="搜索名称" labelStyle="width:25%" width="250"  placeholder="请输入搜索名称" name="searchName" 
					 validation={"minlength":"1","maxlength":"10","required":"true","data-bv-notempty-message":"必填项目", "data-bv-stringlength-message":"长度在1到10个字符以内!"} />
				<!--搜索结果模板-->
				<@ms.select id="searchTemplets"  name="searchTemplets" label="结果模版"/>	
				<@ms.select  name="searchType" labelStyle="width:25%" width="250" label="所属模块" list=searchType  value="" listKey="key" listValue="Value"/>			 	
		</@ms.form>
     </@ms.modalBody>
     <@ms.modalButton>
		<@ms.saveButton id= "saveOrUpdate"/>  
	 </@ms.modalButton>
</@ms.modal>
		
<!--=================模态框部分结束=================-->
<script>
	$(function(){
		//加载选择模块列表
		$("#searchTemplets").request({url:base+"${baseManager}/template/queryTemplateFileForColumn.do",type:"json",method:"post",func:function(msg) {
			if(msg.length != 0 && ($("#searchTemplets").val() == null)){
	   			for(var i=0; i<msg.length; i++){
		   			$("#searchTemplets").append($("<option>").val(msg[i]).text(msg[i]));
		   		}
	   		} else {
	   			$("#searchTemplets").append("<option>暂无文件</option>");
	   		}
	   		//使用select2插件
	 		$("#searchTemplets").select2({width: "220px"});
		}});
		$("#searchList").bootstrapTable({
			url:"${managerPath}/mdiy/search/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
		    	{
		        	field: 'searchId',
		        	title: '编号',
		        	width:'10',
		        	align: 'center'
		    	},{
		        	field: 'searchName',
		        	title: '搜索名称',
		        	width:'20',
		        	formatter:function(value,row,index) {
		        		<@shiro.hasPermission name="mdiy:search:update">	        
						        	return "<a onclick='updateSearch("+row.searchId+")' style='cursor:pointer;text-decoration:none;' >" + value + "</a>";
			    		</@shiro.hasPermission> 
			    		<@shiro.lacksPermission name="mdiy:search:update">
			    			return value;
			    		</@shiro.lacksPermission> 
		        	}
		    	},{
		        	field: 'searchTemplets',
		        	title: '搜索结果模版',
		        	width:'50'
		    	},{
		        	field: 'searchType',
		        	title: '搜索类型',
		        	width:'255',
		        	align: 'center',
		        	formatter:function(value,row,index) {
		        		if(value == "mmall"){
		        			return "商品";
		        		}else{
		        			return "文章";
		        		}
		        	}
		    	}]
	    })
	})
	
	//增加按钮
	$("#setUp").click(function(){
		var rows = $("#searchList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length < 1){
			<@ms.notify msg="请选择需要设置的记录" type="warning"/>
		}else if(rows.length > 1){
			<@ms.notify msg="只能选中一条数据" type="warning"/>
		}else{
			location.href ="${managerPath}/mdiy/search/"+rows[0].searchId+"/searchCode.do"; 
		}
		
	})
	//删除按钮
	$("#delSearchBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#searchList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delSearch").modal();
		}
	})
	
	//保存或更新
	$("#saveOrUpdate").click(function(){
	    $("#searchForm").data("bootstrapValidator").validate();
			var isValid = $("#searchForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		$(this).text("正在保存...");
		$(this).attr("disabled","true");
		var searchEntity = $('#searchForm').serialize();
		var url = $('#searchForm').attr("action");
		$.ajax({
			type: "post",
			url:url,
			data: searchEntity,
			dataType:"json",
			success:function(data){
				if(data.searchId > 0){
					<@ms.notify msg= "保存或更新成功" type= "success" />
				}else {
					$('.ms-notifications').offset({top:43}).notify({
					            type:'danger',
					            message: { text:data.resultMsg }
					            }).show();
				}
				location.reload();
			}
		});
	})
	
	//删除搜索记录
	$("#deleteSearchBtn").click(function(){
		var rows = $("#searchList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/search/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "danger" />
				}
				location.reload();
			}
		})
	});
	
	//表单赋值
	function updateSearch(searchId){
		$(this).request({url:"${managerPath}/mdiy/search/form.do?searchId="+searchId,func:function(search) {
			if (search.searchId > 0) {
				$("#searchForm").attr("action","${managerPath}/mdiy/search/update.do");
				$("#searchForm input[name='searchId']").val(search.searchId);
				$("#searchForm input[name='searchName']").val(search.searchName);
				$("#searchTemplets").select2({width: "220px"}).val(search.searchTemplets).trigger("change");
				$("#searchForm select[name='searchType']").val(search.searchType);
				$("#searchModal").modal();
			}					
		}});
	}
</script>