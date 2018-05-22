<!DOCTYPE html>
<html lang="en">
<head>
<#include "/manager/include/meta.ftl"/> 
</head>
<style>
	a:hover,a:visited{color:#000}
	A:visited,a:hover{color:#000}
</style>
<body>
	<!-- bootstarp 布局容器 开始 -->	
	<div class="container-fluid link-style">
		<!--顶部   开始-->
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-title bottomLine">
					用户管理
					<small>用户列表</small>
				</h3>
			</div>
		</div>
		<!--顶部  结束-->
		<hr>	
		<!--搜索框 结束-->
			
		<hr>	
		<!--内容 部分 开始-->
		<div class="row margin20">
			<!--列表 开始-->
			<table class="table table-bordered">
				<!--表格栏目属性 开始-->
		        <thead>
		        	<tr>
		        		<td colspan="12" class="text-left">
	                     	<i class="glyphicon glyphicon-pushpin"></i>
	                     	<a href="">栏目名</a>&nbsp;>&nbsp;<a href=""><strong>用户管理</strong></a>
		        		</td>
		        	</tr>
			        <tr>
			        	<th class="col-md-1 text-center">编号</th>
			        	<th class="col-md-1 text-center">姓名</th>
			            <th class="col-md-1 text-center">学校</th>
			            <th class="col-md-2 text-center">电话</th>
			            <th class="col-md-2 text-center">所在系</th>
			            <th class="col-md-1 text-center">宿舍</th>
			            <th class="col-md-1 text-center">用户状态</th>
			            <th class="col-md-2 text-center">操作</th>
			        </tr>
		        </thead>
		        <!--表格栏目属性 结束-->
		        
		        <!--表格内容  开始-->
		        <tbody>
		        	<#if listPeopleStudent?has_content>
		        		<#list listPeopleStudent as peopleStudent>
				            <tr>
				            	<td class="text-center">${peopleStudent.peopleId?c?default(0)}</td>
					            <td class="text-center">${peopleStudent.peopleUserRealName?default("暂无")}</td>
					            <td class="text-center">${peopleStudent.peopleStudentSchool?default("暂无")}</td>
					            <td class="text-center">${peopleStudent.peoplePhone?default("暂无")}</td>
					             <td class="text-center">${peopleStudent.peopleStudentDepartment?default("暂无")}</td>
					            <td class="text-center">${peopleStudent.peopleStudentRoom?default("暂无")}</td>
					            <td class="text-center">
				                    <span class="switch switch-mini" data-id="${peopleStudent.peopleId}" data-status="${peopleStudent.peopleState}" >
				                    		<input type="checkbox" name="peopleState" data-size="mini" data-on-text="已审" data-id="${peopleStudent.peopleId}" data-status="${peopleStudent.peopleState}"  data-off-text="未审"/>
				                    </span>
					            <td class="text-center operate">
					            
	                    			
				            		<a class="btn btn-primary btn-xs tooltips " style="color:#fff" data-toggle="tooltip"  href="${managerPath}/people/student/${peopleStudent.peopleId?c?default(0)}/edit.do"  data-original-title="用户信息详情">
				                     	 <i class="glyphicon glyphicon-pencil"></i> 编辑
				                    </a>	
								</td>
					        </tr>
				        </#list>
			        <#else>
			        	<tr>
				            <td colspan="12" class="text-center">
				            	<p class="alert alert-info" role="alert" style="margin:0; cursor:pointer;">
				            		您好，暂无用户数据!
							  	</p>
							</td>
				        </tr>
			 		</#if>
		        </tbody>
		        <!--表格内容  结束-->
			</table>
		</div>
		<!--分页样式 开始-->
		<@showPage page=page/>
		<!-- 用户详信信息载体开始 -->
		<div id="peopleStudentInfo" style="display:none;">
				<div class="row">
						 <div class="col-md-6">
						 	<div class="form-group">
							    	<label>姓名:</label>
							    	<span class="realName"></span>
							 </div>
							 <div class="form-group">
							    	<label>昵称:</label>
							    	<span class="nickName"></span>
							 </div>
							 <div class="form-group">
							    	<label>性别:</label>
							    	<span class="sex"></span>
							 </div>
							 <div class="form-group">
							    	<label>手机:</label>
							    	<span class="phone"></span>
							 </div>
							 <div class="form-group">
							    	<label>邮箱:</label>
							    	<span class="mail"></span>
							 </div>	
							 <div class="form-group">
							    	<label>入学年份:</label>
							    	<span class="peopleStudentIndate"></span>
							 </div>	
							  <div class="form-group">
							    	<label>身份证号码:</label>
							    	<span class="peopleUserCard"></span>
							 </div>		 
						 </div>
						 
						 <div class="col-md-6">
						 	
							 <div class="form-group">
							    	<label>学校:</label>
							    	<span class="peopleStudentSchool"></span>
							 </div>
							<div class="form-group">
							    	<label>学生学号:</label>
							    	<span class="peopleStudentNo"></span>
							 </div>
							 <div class="form-group">
							    	<label>所在院系:</label>
							    	<span class="peopleStudentDepartment"></span>
							 </div>
							 <div class="form-group">
							    	<label>所在专业:</label>
							    	<span class="peopleStudentSpecialty"></span>
							 </div>
							 <div class="form-group">
							    	<label>所在班级:</label>
							    	<span class="peopleStudentClass"></span>
							 </div>
							 <div class="form-group">
							    	<label>所在宿舍:</label>
							    	<span class="peopleStudentRoom"></span>
							 </div>
							 <div class="form-group">
						 		<label>学校所在地:</label>
						 		<span class="peopleStudentCityID"></span>
						 	</div>	
						 </div>
					</div>	
		</div>
		<!-- 用户详细信息载体结束 -->		
		<!--编辑和新增字段模态框-->
	<@modalDialog modalName="peopleInfoModel"/>
	</div>
	<!-- bootstarp 布局容器 结束 -->
	
	<script>
		$(function(){
			//进行数据的筛选
			$(".submit").click(function() {
				$("#searchForm").submit();
			});
			//导出数据
			$(".exportExcel").on("click",function(){
				$("#searchForm").attr("action", base+"${baseManager}/people/student/exprotStudentsExcel.do").submit();
				$("#searchForm").attr("action", base+"${baseManager}/people/student/list.do");
			});
			//清除条件
			$(".reset").click(function() {
				$("#peopleState").prepend("<option value=''>全部</option>");
				$("#stagingOrderEntityShopId").val("").trigger("change");
				$("#searchForm :input").val("");
				$("#orderStatus").val("-4").trigger("change");
			});	
			
			
			
			 var actionUrl ="${managerPath}/basic/"+$("#peopleStudentCityID").find("option:selected").val()+"/query.do?categoryTitle="+encodeURIComponent($(this).find("option:selected").text());
				$("#peopleStudentCityID").request({url:actionUrl,func:function(data){
						$("#peopleStudentSchool").html("");
						$("#peopleStudentSchool").append('<option value="">全部</option>')	
						for(var i=0; i<data.length;i++){
								$("#peopleStudentSchool").append("<option value='"+data[i].basicTitle.trim()+"'>"+data[i].basicTitle+"</option>")
						}
						<#if peopleStudentSchool?exists>
							$("#peopleStudentSchool").find("option[value=${peopleStudentSchool}]").attr("selected","selected");
						</#if>
					}
				})
				
			//当切换城市时，
			$("#peopleStudentCityID").change(function(){
			   var actionUrl ="${managerPath}/basic/"+$(this).find("option:selected").val()+"/query.do?categoryTitle="+encodeURIComponent($(this).find("option:selected").text());
				$(this).request({url:actionUrl,func:function(data){
						$("#peopleStudentSchool").html("");
						$("#peopleStudentSchool").append('<option value="">全部</option>')	
						for(var i=0; i<data.length;i++){
								$("#peopleStudentSchool").append("<option value='"+data[i].basicTitle+"'>"+data[i].basicTitle+"</option>")
						}
					}
				})
			});
			//查看用户详细信息
			$(".zoom").click(function(){
				var peopleId = $(this).attr("data-id");
				$.ajax({
					type:"POST",
					url:"${managerPath}/people/student/"+peopleId+"/query.do",
					data:"peopleId="+peopleId,
					success:function(msg){
						if($.parseJSON(msg).result == true){
							var peopleUserInfo = $.parseJSON($.parseJSON(msg).resultData);
							$("#peopleStudentInfo .nickName").html(isBlank(peopleUserInfo.peopleUserNickName));
							$("#peopleStudentInfo .phone").html(isBlank(peopleUserInfo.peoplePhone));
							$("#peopleStudentInfo .mail").html(isBlank(peopleUserInfo.peopleMail));
							$("#peopleStudentInfo .realName").html(isBlank(peopleUserInfo.peopleUserRealName));
							$("#peopleStudentInfo .peopleStudentIndate").html(isBlank(peopleUserInfo.peopleStudentIndate)+"年");
							$("#peopleStudentInfo .peopleStudentSchool").html(isBlank(peopleUserInfo.peopleStudentSchool));
							$("#peopleStudentInfo .peopleStudentDepartment").html(isBlank(peopleUserInfo.peopleStudentDepartment));
							$("#peopleStudentInfo .peopleStudentSpecialty").html(isBlank(peopleUserInfo.peopleStudentSpecialty));
							$("#peopleStudentInfo .peopleStudentClass").html(isBlank(peopleUserInfo.peopleStudentClass));
							$("#peopleStudentInfo .peopleStudentNo").html(isBlank(peopleUserInfo.peopleStudentNo));
							$("#peopleStudentInfo .peopleStudentRoom").html(isBlank(peopleUserInfo.peopleStudentRoom));
							$("#peopleStudentInfo .peopleUserCard").html(isBlank(peopleUserInfo.peopleUserCard));
							//判断用户性别
							var sex = peopleUserInfo.peopleUserSex;
							if(sex == 1){
								$("#peopleStudentInfo .sex").html("男");
							}else if(sex == 2){
								$("#peopleStudentInfo .sex").html("女");
							}else{
								$("#peopleStudentInfo .sex").html("未知");
							}
							//获取城市信息
				    	 	if(peopleUserInfo.peopleStudentCityID > 0){
				          	 		$.ajax({
				              			url:"${managerPath}/category/"+peopleUserInfo.peopleStudentCityID+"/query.do",
				              			type:"POST",
				              			dataType:"JSON",
				              			success:function(msg){
				                  			var city = "";
				                  			for(var i=0;i<msg.length;i++){
				                      			city += msg[i].categoryTitle;
				                   			};
				                   			$("#peopleStudentInfo .peopleStudentCityID").html(isBlank(city));
				              	 		}
				           			});
				     	 		}
				     	 		openpeopleInfoModel($("#peopleStudentInfo"),"用户详细信息");
							}else{
							$("#peopleInfoModel .modal-body").html('<p class="alert alert-info" role="alert" style="margin:0; cursor:pointer;">该用户未还未填写详细信息!<p>');
						}
					},
				});
				$("#peopleInfoModel").modal();
			});
			
			
			
			/×××××××××××××××更新用户状态B××××××××××××××××/
			//初始化Switch按钮
			$("input[name='peopleState']").bootstrapSwitch();
			//遍历所有的学生状态
			$("input[name=peopleState]").each(function() {
					var status = $(this).attr("data-status");//当前学生状态
					if (status=="1") {
						$(this).bootstrapSwitch('state', true);
					} else if(status=="0") {
						$(this).bootstrapSwitch('state', false);
					}
				});
			});
			//点击事件,切换学生的状态
			$(".switch-mini").click(function() {
				    //用户ID
					var peopleId = $(this).attr("data-id");
					//用户当前状态
					var peopleState = $(this).attr("data-status");
					//更新状态begin
					$.ajax({
						type:"post",
						url:"${managerPath}/people/updateState.do",
						data:"peopleId="+peopleId+"&peopleState="+peopleState+"&t="+new Date(),
						success:function(msg){
						},
					});			
					//更新状态end				
			});	
		   /×××××××××××××××更新用户状态E××××××××××××××××/
		
		/**
		 * 时间转换
		 * date:需要转换的时间
		 */
		function changeDateTime(date){
			if(date == null || date == ""){
				return "暂无";
			}
			var now = new Date(date)
			var year=now.getFullYear();   
			var month=now.getMonth()+1;   
			var date=now.getDate();   
			var hour=now.getHours();   
			var minute=now.getMinutes();   
			var second=now.getSeconds();   
			return year+"-"+month+"-"+date+"   "+hour+":"+minute+":"+second;   
		}
		
		/**
		 * 判断参数是否为空
		 * str 需要判断的值
		 * return 不为空返回原值,为空返回"暂无" 
		 */
		function isBlank(str){
			if(str == null || str == ""){
				return "暂无";
			}else{
				return str;
			}
		}
	</script>	
</body>
</html>	