<@ms.html5>
	<@ms.nav title="自定义模型表管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.text label="模型名称" name="cmTipsName" value=""  width="240px;" placeholder="请输入模型名称" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"模型名称长度不能超过五十个字符长度!"}/>
		<@ms.searchFormButton>
			 <@ms.queryButton onclick="search()"/> 
		</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.buttonGroup>
					<@shiro.hasPermission name="mdiy:content:save"><@ms.addButton id="addContentModelBtn"/></@shiro.hasPermission>
					<@shiro.hasPermission name="mdiy:content:del"><@ms.delButton id="delContentModelBtn"/></@shiro.hasPermission>
				</@ms.buttonGroup>
			</@ms.panelNav>
		</div>
		<table id="contentModelList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	<!--添加模块-->    
	<@ms.modal id="addEditModel" title="添加自定义模型">
		<@ms.modalBody>
			<@ms.form isvalidation=true name="addEditForm"  action="" method="post"  >
				<@ms.text name="cmTipsName" id="cmTipsName" label="名称" labelStyle="width:25%" width="250" title="表名提示文字" placeholder="请输入模型名称" value="" validation={"maxlength":"50","required":"true", "data-bv-notempty-message":"表名提示文字不能为空","data-bv-stringlength-message":"表名提示文字在50个字符以内!"}/>
				<@ms.text name="cmTableName"  label="表名" labelStyle="width:25%" width="250" title="表单名称" placeholder="请输入表名（仅英文字符）" value="" validation={"maxlength":"50","data-bv-regexp":"true","required":"true", "data-bv-regexp-regexp":"^[a-zA-Z]+$","data-bv-regexp-message":"表名只能为英文字符!","data-bv-notempty-message":"表单名称不能为空","data-bv-stringlength-message":"表单名称在50个字符以内!"}/>
			</@ms.form>
		</@ms.modalBody>
		<@ms.modalButton>
			<@ms.button  value=""  id="addEditBtn"/>
		</@ms.modalButton>
	</@ms.modal>
	<!--删除模块--> 		
	<@ms.modal  modalName="delContentModel" title="自定义模型删除" >
		<@ms.modalBody>删除选中模型
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteContentModelBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	var postUrl;
	$(function(){
		$("#contentModelList").bootstrapTable({
			url:"${managerPath}/mdiy/contentModel/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    		{
				        	field: 'cmTipsName',
				        	title: '模型名称',
				        	formatter:function(value,row,index) {
				        		<@shiro.hasPermission name="mdiy:content:update">	        
						        	return "<a style='cursor:pointer;text-decoration:none;' onclick='editModel("+row.cmId+")'>" + value + "</a>";
					    		</@shiro.hasPermission> 
					    		<@shiro.lacksPermission name="mdiy:content:update">
					    			return value;
					    		</@shiro.lacksPermission> 
				        		
				        	}
				    	},{
				        	field: 'cmTableName',
				        	title: '模型表名',
				        	formatter:function(value,row,index) {
				        		<@shiro.hasPermission name="mdiy:content:update">	        
						        	var url = "${managerPath}/mdiy/contentModel/contentModelField/"+row.cmId+"/list.do";
				        			return "<a href=" +url+ " target='_self'>" + value + "</a>";
					    		</@shiro.hasPermission> 
					    		<@shiro.lacksPermission name="mdiy:content:update">
					    			return value;
					    		</@shiro.lacksPermission> 
				        		
				        	}
				    	}]
	    })
	})
	//点击名称更新
	function editModel(id){
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/contentModel/get.do?cmId="+id,
			async: false,
			dataType: "json",
			contentType: "application/json",
			success:function(data) {
				if(data!=null){
					$("#cmTipsName").val(data.cmTipsName);
					$("input[name='cmTableName']").val(data.cmTableName); 
					$(".addEditModel").modal();
					postUrl="${managerPath}/mdiy/contentModel/update.do?cmId="+id,
					$("#addEditBtn").text("更新");
					$("#addEditModelTitle").text("编辑自定义模型");
				}	
			}
		})
		$("input[name='cmTableName']").attr("disabled",true);
	}
	//增加按钮
	$("#addContentModelBtn").click(function(){
		$("input[name='cmTableName']").attr("disabled",false);
		postUrl = "${managerPath}/mdiy/contentModel/save.do";
		$("#addEditBtn").text("保存");
		$("#addEditModelTitle").text("添加自定义模型");
		$("#addEditForm")[0].reset();
		$(".addEditModel").modal();
	})
	//删除按钮
	$("#delContentModelBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#contentModelList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delContentModel").modal();
		}
	})
	
	$("#deleteContentModelBtn").click(function(){
		var rows = $("#contentModelList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/contentModel/delete.do",
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
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#contentModelList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#contentModelList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
	//保存或更新
	$("#addEditBtn").on("click",function(){
		// 验证自定义模型的表名是否相同
		var cmTableName= $("input[name='cmTableName']").val();
		if(cmTableName!=""){
			var URL="${managerPath}/mdiy/contentModel/"+cmTableName+"/checkcmTableNameExist.do"
			$(this).request({url:URL,method:"post",type:"json",func:function(msg) {
				if(msg){
				     <@ms.notify msg= "表名已存在，请重新输入" type= "warning" />
				     //$("input[name='cmTableName']").val("");
				} else{
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
					    	}else{
					    		$('.ms-notifications').offset({top:43}).notify({
					            type:'danger',
					            message: { text:msg.resultMsg }
					            }).show();
					    	}
					    	location.reload();
						}});
					}
				}
			}});
		}
		
	});
</script>