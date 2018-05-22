<@ms.html5>
	<@ms.nav title="自定义模型"></@ms.nav>
	<@ms.panel>

			<@ms.form  name="contentModelForm" isvalidation=true action="${managerPath}/cms/contentModel/${autoCURD}.do">
				<@ms.hidden name="cmId" value="${contentModel.cmId?default(0)}"  />
				<!--模型名称-->
				<@ms.text label="模型名称" name="cmTipsName" value="${contentModel.cmTipsName}" title="名称" width="200" maxlength="15" placeholder="请输入模型名称"  validation={"required":"true", "data-bv-notempty-message":"名称不能为空","data-bv-stringlength":"true","data-bv-stringlength-max":"10","data-bv-stringlength-min":"1","data-bv-stringlength-message":"长度介于1-15个字符"} />
				<!--模型表名-->
				<#if contentModel.cmTableName?has_content>
				<@ms.formRow label="模型表名">
					${contentModel.cmTableName}
				</@ms.formRow>
				<#else>
					<@ms.text label="模型表名" name="cmTableName" value="${contentModel.cmTableName}"  title="名称" width="200" maxlength="15" help="注：表单名称一旦保存不能再更改" placeholder="表名只能为英文字符或下划线或数字"  validation={"required":"true", "data-bv-notempty-message":"名称不能为空","data-bv-stringlength":"true","data-bv-stringlength-max":"10","data-bv-stringlength-min":"1","data-bv-stringlength-message":"长度介于1-15个字符"}/>
				</#if>
				<@ms.formRow>
					<@ms.saveButton id="saveContentModel" value="保存模型" postForm="contentModelForm" />
				</@ms.formRow>
    		</@ms.form>
			
			<@ms.table head=['字段提示文字','字段名称',"<th style='width:30%' class='text-center'>字段类型</th>","<th class='text-center'>操作</th>"]>
				<#if contentModel?has_content>
					<hr>
					<div class="form-group">
						<label class="col-md-2 control-label col-xs-2 "><h4><strong>字段信息</strong></h4></label>
					</div>
					<!--新增按钮-->
					<@ms.panelNav>
						<@ms.panelNavBtnGroup>
							<@ms.panelNavBtnAdd  id="addField"/>
						</@ms.panelNavBtnGroup>
					</@ms.panelNav>
	    			
						<#if searchList?has_content>
	           				<tr>
					            <td class="commentId" style="width: 10%"></td>
					            <td style="width: 30%"></td>
					            <td style="width: 30%"></td>
					            <td class="text-center" style="width: 30%"></td>
					            <td class="text-center operate" style="width: 10%">
				                    <a class="btn btn-xs tooltips deleteImg" data-toggle="tooltip" data-id="" data-original-title="删除">
				                        <i class="glyphicon glyphicon-trash"></i>
				                    </a>
				                     <a class="btn btn-xs red tooltips  updateSearch" data-toggle="tooltip"  data-original-title="编辑" data-id="">
				                     	<i class="glyphicon glyphicon-pencil"></i>
				                    </a>
								</td>
					        </tr>
		           		<#else>	
		           			<tr>
					            <td colspan="5" class="text-center">
					            	<@ms.nodata/>
								</td>
				          	</tr>
		           		</#if>
							
				</#if>	
			</@ms.table>	

	</@ms.panel>
</@ms.html5>

<!--删除的模态框开始-->
<@ms.modal modalName="deleteModal" title="删除字段">
	 <@ms.modalBody>
		确定删除该字段吗？
     </@ms.modalBody>
     <@ms.modalButton>
		<@ms.button class="btn btn-danger" id="deleteButtonField" value="删除"/>  
	 </@ms.modalButton>
</@ms.modal>
<!--删除的模态框结束-->
<!--编辑和新增字段模态框开始-->
<@ms.modal modalName="openModal" title="" style="min-width:555px;">
	<@ms.modalBody>
		<@ms.form isvalidation=true class="fieldForm" name="fieldForm" action="">
			<@ms.row>
				<!--字段提示文字-->
				<@ms.col size="4" style="text-align: right;">字段提示文字</@ms.col>
				<@ms.col size="7">
					<@ms.text title="名称"  size="3"  value="" placeholder="请输入提示文字" name="fieldTipsName" validation={"required":"true", "data-bv-notempty-message":"不能为空","data-bv-stringlength":"true","data-bv-stringlength-max":"100","data-bv-stringlength-min":"1","data-bv-stringlength-message":"长度介于1-100个字符"} />
				</@ms.col>					
				<!--字段名称-->
				<@ms.col size="4" style="text-align: right;">字段名称</@ms.col>
				<@ms.col size="7">
					<@ms.text title="名称" size="3" value=""  placeholder="请输入字段名称" name="fieldFieldName"  validation={"required":"true", "data-bv-notempty-message":"不能为空","data-bv-stringlength":"true","data-bv-stringlength-max":"100","data-bv-stringlength-min":"1","data-bv-stringlength-message":"长度介于1-100个字符","data-bv-regexp":"true","data-bv-regexp-regexp":'^[A-Za-z0-9]+$',"data-bv-regexp-message":"字段名只能为字符!"}  />
				</@ms.col>				
				<!--数据类型-->
				<@ms.hidden name="fieldCmid" value="${contentModel.cmId?default(0)}"  />
				<@ms.col size="4" style="text-align: right;">数据类型</@ms.col>
				<@ms.col size="7">
					<div styel="display:none;" id="hideFieldId"></div>
					<div  id="fieldTypeInfo"></div>
				</@ms.col>
				<@ms.col size="12" style="height: 8px;"></@ms.col>
				<!--是否是必填字段-->
				<@ms.col size="4" style="text-align: right;">是否是必填字段</@ms.col>
				<@ms.col size="7">
					<div class='fieldRadio'>
						<input value='0' name="fieldIsNull" type="radio"/>必填
					</div>
					<div class='fieldRadio'>
						<input value='1' type="radio" name="fieldIsNull"/>可选
					</div>
				</@ms.col>
				<@ms.col size="12" style="height: 8px;"></@ms.col>
				<!--是否是搜索-->
				<@ms.col size="4" style="text-align: right;">是否是搜索</@ms.col>
				<@ms.col size="7">
					<div class='fieldRadio'>
						<input value='1' name="fieldIsSearch" type="radio"/>是
					</div>
					<div class='fieldRadio'>
						<input value='0' type="radio" name="fieldIsSearch"/>否
					</div>
				</@ms.col>
				<@ms.col size="12" style="height: 8px;"></@ms.col>
				<!--字段默认值-->
				<@ms.col size="4" style="text-align: right;">字段默认值</@ms.col>
				<@ms.col size="7">
					<@ms.textarea name="fieldDefault"   wrap="Soft" rows="5"  size=""  value=""  placeholder="下拉框,多选框等存在多个默认值的必须使用英文逗号隔开"/>
				</@ms.col>			
			</@ms.row>	
		</@ms.form>
     </@ms.modalBody>
     <@ms.modalButton>   		
		<@ms.button class="btn btn-primary" id="saveOrUpdate" value="保存"/>  
	 </@ms.modalButton>
</@ms.modal>
<!--编辑和新增字段模态框结束-->	
	<script type="text/javascript">
		//自定义表单验证绑定函数
	/*	function bindValidate(obj){
			bootstrapValidator = obj.bootstrapValidator({
				feedbackIcons: {
	                valid: 'glyphicon glyphicon-ok',
	                invalid: 'glyphicon glyphicon-remove',
	                validating: 'glyphicon glyphicon-refresh'	                
	            },
		       	fields: {
		            fieldTipsName: {
		                validators: {
		                    notEmpty: { message: '字段提示文字不能为空'},
		                    stringLength: {min: 1,max: 30,message: '字段提示文字长度介于1-30个字符'}
		                }
		            },
		            fieldFieldName: {
		                validators: {
		                    notEmpty: {message: '字段名称不能为空'},
		                    stringLength: {min: 1,max: 13,message: '字段长度介于1-13个字符'},
		                    regexp: {regexp: /^[a-zA-Z0-9]+$/,message: '字段名只能由英文字母，数字组成'}
		                }
		            },
		        }
		     });
		}
*/	
	
	
		//字段列表
		function queryFieldList(){
			$.ajax({
			   type: "post",
			   dataType: "json",
			   url:  base+"${baseManager}/cms/field/list.do",
			   data: "cmId=" + ${contentModel.cmId?default(0)},
			   success: function(msg){
			   		if(msg.fieldList.length != 0){
			    		$("tbody").html("");
			    		//获取字段列表信息
			    		for(var i=0; i<msg.fieldList.length; i++){	    					
	    					var fieldTypeC =  msg.fieldType[msg.fieldList[i].fieldType];
	    					$("tbody").append("<tr class='fieldList'><td style='width:30%'>"+msg.fieldList[i].fieldTipsName+"</td>"+
							            "<td style='width:30%'>"+msg.fieldList[i].fieldFieldName+"</td>"+
							 			"<td class='text-center'>"+fieldTypeC+"</td>"+
							            "<td class='text-center'>"+
					                    "<a class='btn btn-xs tooltips delete' data-toggle='tooltip' data-id='"+msg.fieldList[i].fieldId+"' data-original-title='删除'>"+
					                    "<i class='glyphicon glyphicon-trash'></i></a>"+
					                    "<a class='btn btn-xs tooltips edit' data-toggle='tooltip' data-id='"+msg.fieldList[i].fieldId+"' data-original-title='编辑'>"+
				                     	"<i class='glyphicon glyphicon-pencil'></i></a></td></tr>");
			    		}
			    	}
			    	//动态获取字段属性			    	
			    	if(msg.fieldType != null){
			    		for(var k=1; k<=msg.fieldNum; k++){
			    			$("#fieldTypeInfo").append("<div class='fieldRadio'><input type='radio' class='text-center' name='fieldType' value='"+k+"'>"+msg.fieldType[k]+"</div>");
			    		}
			    	}
			   }
			});
		}
		//删除字段
		function deleteField(fieldId){
			var URL="${managerPath}/cms/field/"+fieldId+"/delete.do";
			$("#deleteButtonField").text("删除中");
			$("#deleteButtonField").attr("disabled",true);
			$(this).request({url:URL,method:"post",type:"json",func:function(msg) {
				if(msg != 0) {
			    	$(".closeModal").click();
					<@ms.notify msg= "删除字段成功" type= "success" />
		    		if($("tbody tr").length==0 && msg != 1){
		    			location.href = base+"${baseManager}/cms/contentModel/add.do";
					}else{
						location.href = base+"${baseManager}/cms/contentModel/${contentModel.cmId?default(0)}/edit.do";
					}
		    	} else {
					<@ms.notify msg= "删除字段失败" type= "danger" />
			    	$(".closeModal").click();
		    	}
			}});
		}
		
		// 点击新增字段时弹出新增字段弹出框
		function saveField(){
			$("#saveOrUpdate").html("保存");
			$(".fieldForm input:text").val("");
			$("input:radio[value='1']").attr("checked", true);
			var cmTableName=$("input[name='cmTableName']").val();
			$(".fieldForm").attr("action","${managerPath}/cms/field/"+cmTableName+"/save.do");
			$("#openModalTitle").text("新增字段");
		}
		
		// 用于判断编辑时用户是否改变了字段名称的值
		var oldFielName =$("input[name='fieldFieldName']").val();
		$(function(){			
			//更新自定义模型函数
			function updateContentModel(){
				//对自定义模型的表单进行验证
				var vobj = $(".contentModelFrom").data('bootstrapValidator').validate();
				if(vobj.isValid()){
					var URL="${managerPath}/cms/contentModel/update.do"
					var contentModel = $(".contentModelFrom").serialize();
					$(".updateContentModel").text("更新中");
					$(".updateContentModel").attr("disabled",true);
					$(this).request({url:URL,method:"post",type:"json",data:contentModel,func:function(msg) {
						if(msg.result){
							<@ms.notify msg= "更新成功" type= "success" />
						}else {
							<@ms.notify msg= "更新失败" type= "danger" />							
						}
						$(".updateContentModel").text("更新模型");
						$(".updateContentModel").attr("disabled",false);
					}});
				}
			}
			//点击更新表单按钮
		 	$(".updateContentModel").on("click",function(){
				updateContentModel();
		 	});
			//字段列表查询
			//点击新增字段按钮,弹出新增字段的模态框
			$("#addField").on("click",function(){
				$(".openModal").modal();//打开模态框
				saveField();
			});
			
			//关闭模态框
			$("#closeSaveUpdate").click(function(){
				$(".openModal").modal("hide");
			});
			
			//点击删除，弹出删除提示框
			$("tbody").delegate(".delete","click",function(){
				var fieldId = $(this).attr("data-id");
				$(".deleteModal").modal();//打开删除的模态框
				$("#deleteButtonField").click(function(){
					deleteField(fieldId);
				});
			});
			
			//前端判断同一个表单中是否存在相同的字段名
			$("input[name='fieldFieldName']").blur(function(){
				var fieldFieldName = $(this).val();
				var fieldCmId = $("input[name='fieldCmid']").val();
				if(oldFielName!=$("input[name='fieldFieldName']").val() && $("input[name='fieldFieldName']").val()!=""){
					var URL="${managerPath}/cms/field/"+fieldFieldName+"/checkFieldNameExist.do?fieldCmId="+fieldCmId;
					$(this).request({url:URL,method:"post",type:"json",data:fieldCmId,func:function(msg) {
						if(msg){
				     		<@ms.notify msg= "字段名已存在，请再次输入" type= "warning" />
				     		$("input[name='fieldFieldName']").val("");
				     	} 
					}});
				}
			});
			
				
			//点击模态框保存按钮时进行ajax请求保存字段信息		
			$(".openModal").delegate("#saveOrUpdate", "click", function(){
				var fieldType = $("input[name='fieldType']:checked").val();
				var flag = true;
				// 当用户选择的是数字类型时,默认值只能为数字
				if(fieldType=="6"||fieldType =="7"){
					if((isNaN($("textarea[name='fieldDefault']").val()))){
						$($("textarea[name='fieldDefault']")).val("");
						flag = false;
						<@ms.notify msg= "字段类型为数字类型,默认值只能为数字" type= "warning" />
					}
				}
				//获取按钮文字
				var btnText = $("#saveOrUpdate").html();
				//表单验证
				var obj = $(".fieldForm").data('bootstrapValidator').validate();
				if(obj.isValid() && flag){
					$.ajax({
						type: "post",
						url:$(".fieldForm").attr("action"),
						dataType:"json",
						data: $(".fieldForm").serialize(),
						beforeSend:function(){
							$("#saveOrUpdate").html(btnText+"中");
							$("#saveOrUpdate").attr('disabled',true);
						},
						success: function(msg){
							var fieldCmid = $("input[name='fieldCmid']").val();
							if(msg.result){
								$('.ms-notifications').offset({top:43}).notify({
					    			type:'success',
					    			message: { text:btnText+"成功" }
					 			}).show();	
								location.href="${managerPath}/cms/contentModel/"+fieldCmid+"/edit.do";					
							}else{
								$('.ms-notifications').offset({top:43}).notify({
					    			type:'danger',
					    			message: { text:msg.resultMsg }
					 			}).show();
								$("#saveOrUpdate").html(btnText);
								$("#saveOrUpdate").attr('disabled',false);
							}
						}
					});
				}
			});
			
			// 点击编辑按钮弹出更新模态框
			$("tbody").delegate(".edit","click",function(){
				$(".openModal").modal();//打开模态框
				var fieleId = $(this).attr("data-id");
				$("#hideFieldId").html("<input name='fieldId'type='hidden'/>");
				$("input[name='fieldId']").val(fieleId);
				$("#saveOrUpdate").html("更新");
				$(".fieldForm").attr("action","${managerPath}/cms/field/update.do");			
				var URL="${managerPath}/cms/field/"+fieleId+"/edit.do"
				$(this).request({url:URL,method:"post",type:"json",func:function(msg) {
					//加载模态框默认值
					$("input[name='fieldTipsName']").val(msg.field.fieldTipsName);
			     	$("input[name='fieldFieldName']").val(msg.field.fieldFieldName);
			     	$("textarea[name='fieldDefault']").val(msg.field.fieldDefault);
			     	var fieldType = msg.field.fieldType; 	
			     	oldFielName = msg.field.fieldFieldName;
			     	$("[name='fieldType'][value="+fieldType+"]").attr("checked", true);
			     	$("[name='fieldIsSearch'][value="+msg.field.fieldIsSearch+"]").attr("checked", true);
			     	$("[name='fieldIsNull'][value="+msg.field.fieldIsNull+"]").attr("checked", true);
			     	
				}});			
				$("#openModalTitle").text("更新字段");
			});		
			queryFieldList();
			//新增自定义模型
			function saveContentModel(){
				//对自定义模型的表单进行验证
				var vobj = $(".contentModelFrom").data('bootstrapValidator').validate();
				if(vobj.isValid()){
					$.ajax({
						type: "post",
						url:"${managerPath}/cms/contentModel/save.do",
						dataType:"json",
						data: $(".contentModelFrom").serialize(),
						beforeSend:function() {
			   				$(".saveContentModel").html("保存中");
			   				$(".saveContentModel").attr("disabled","disabled");
			  			 },
						success: function(msg){
							if(msg.result){
								$("input[name='cmTableName']").attr("readonly","readonly");
								$("input[name='fieldCmid']").val(msg.resultMsg);
								<@ms.notify msg= "保存成功" type= "success" />
								location.href="${managerPath}/cms/contentModel/"+msg.resultMsg+"/edit.do";
							}else{
								$('.ms-notifications').offset({top:43}).notify({
					    			type:'danger',
					    			message: { text:msg.resultMsg }
					 			}).show();	
								$(".saveContentModel").html("保存模型");	
							}
							$(".saveContentModel").removeAttr("disabled");
						}
					});
				}
			}
		
			//点击保存自定义模型表单
			$(".saveContentModel").bind("click",function(){
				saveContentModel();
			});
			
			// 前端验证自定义模型的表名是否相同
			$("input[name='cmTableName']").blur(function(){
				var cmTableName= $(this).val();
				if(cmTableName!=""){
					var URL="${managerPath}/cms/contentModel/"+cmTableName+"/checkcmTableNameExist.do"
					$(this).request({url:URL,method:"post",type:"json",func:function(msg) {
						if(msg){
						     <@ms.notify msg= "表名已存在，请重新输入" type= "warning" />
						     $("input[name='cmTableName']").val("");
						} 
					}});
				}
			});
		});
	</script>
