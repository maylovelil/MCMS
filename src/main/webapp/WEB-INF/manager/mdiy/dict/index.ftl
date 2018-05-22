<@ms.html5>
	<@ms.nav title="字典表管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.text label="类型" name="dictType" value=""  width="240px;" placeholder="请输入类型" />
		<@ms.searchFormButton>
			 <@ms.queryButton onclick="search()"/> 
		</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNav>
				<@ms.panelNavBtnGroup>
					<@shiro.hasPermission name="mdiy:dict:save"><@ms.panelNavBtnAdd title="" id="addDictBtn"/></@shiro.hasPermission> 
					<@shiro.hasPermission name="mdiy:dict:del"><@ms.panelNavBtnDel title="" id="delDictBtn"/></@shiro.hasPermission> 
				</@ms.panelNavBtnGroup>
			</@ms.panelNav>
		</div>
		<table id="dictList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delDict" title="数据删除" >
		<@ms.modalBody>是否删除所选中的数据
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认删除？"  id="deleteDictBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#dictList").bootstrapTable({
			url:"${managerPath}/mdiy/dict/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
	    				{
				        	field: 'dictValue',
				        	title: '数据值',
				        	align: 'center'
				    	},{
				        	field: 'dictLabel',
				        	title: '标签名',
				        	formatter:function(value,row,index) {
				        		<@shiro.hasPermission name="mdiy:dict:update">	        
						        	var url = "${managerPath}/mdiy/dict/form.do?dictId="+row.dictId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
					    		</@shiro.hasPermission> 
					    		<@shiro.lacksPermission name="mdiy:dict:update">
					    			return value;
					    		</@shiro.lacksPermission> 
				        	}
				    	},{
				        	field: 'dictSort',
				        	title: '排序'
				    	},{
				        	field: 'dictType',
				        	title: '类型'
				    	},{
				        	field: 'dictDescription',
				        	title: '描述'
				    	},{
				        	field: 'dictRemarks',
				        	title: '备注信息'
				    	}]
	    })
	})
	//增加按钮
	$("#addDictBtn").click(function(){
		location.href ="${managerPath}/mdiy/dict/form.do"; 
	})
	//删除按钮
	$("#delDictBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#dictList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delDict").modal();
		}
	})
	
	$("#deleteDictBtn").click(function(){
		var rows = $("#dictList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mdiy/dict/delete.do",
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
        var params = $('#dictList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#dictList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>