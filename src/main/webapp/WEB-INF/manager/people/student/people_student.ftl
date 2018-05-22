<!DOCTYPE html>
<html lang="en">
<head>
<#include "/manager/include/meta.ftl"/> 
</head>

<body>
	<@ms.content>
  			<@ms.contentBody >
				<@ms.contentNav title="学生管理">
					<@ms.savebutton id="updateStudent" value="更新学生资料"/> 
					<@ms.contentNavBack/>
				</@ms.contentNav>
				<@ms.contentPanel>
			  		<@ms.form  isvalidation=true name="peopleStudentForm" action="${managerPath}/staging/people/student/update.do">
			  			<input type="hidden" class="form-control" name="peopleStudentPeopleId" value="${peopleStudent.peopleStudentPeopleId?default(0)}"/>
						<input type="hidden" class="form-control" name="peopleUserPeopleId" value="${peopleStudent.peopleStudentPeopleId?default(0)}"/>
						<input type="hidden" class="form-control" name="peopleId" value="${peopleStudent.peopleStudentPeopleId?default(0)}"/>
						<input type="hidden" class="form-control" name="stagingPeopleCreditLimitPeopleId" value="0"/>
			    		<@ms.text name="peopleUserRealName" label="姓名" title="" value="" size="2" style="width:17%;"/>
			    		<@ms.text name="peopleUserCard" label="身份证" title="" size="5" style="width:17%;"/>
			    		<@ms.text name="peoplePhone" label="手机号" title="" size="5" style="width:28%;"   placeholder="请填写手机号" />
			    		<@ms.text name="peopleStudentSchool" label="大学" title="" size="5" style="width:28%;"   placeholder="请填写所在大学" />
			    		<#assign status=[{"id":"1","name":"本科"},{"id":"2","name":"专科"},{"id":"3","name":"硕士研究生"},{"id":"3","name":"博士研究生"}] >
						<@ms.select label="入学年份"  list="status" name="peopleStudentEducation" id="peopleStudentEducation" listKey="id" listValue="name" value="" style="width:21%;" />
			    		<@ms.text name="peopleStudentIndate" label="入学年份" title="" size="5" style="width:28%;"   placeholder="请填写入学年份"/>
			    		<@ms.text name="peopleStudentDepartment" label="院系" title="" size="5" style="width:28%;"   placeholder="请填写院系信息"/>
			    		<@ms.text name="peopleStudentSpecialty" label="专业" title="" size="5" style="width:28%;"   placeholder="请填写专业信息" />
			    		<@ms.text name="peopleStudentClass" label="班级" title="" size="5" style="width:28%;"   placeholder="请填写班级信息"/>
			    		<@ms.text name="peopleStudentNo" label="学号" title="" size="5" style="width:28%;"   placeholder="请填写学号"/>
			    		<@ms.text name="peopleStudentRoom" label="宿舍号" title="" size="5" style="width:28%;"   placeholder="请填写宿舍号"/>
			    	</@ms.form>
				</@ms.contentPanel>	
			</@ms.contentBody>          
	</@ms.content>
	<!-- bootstarp 布局容器 结束 -->
	<script>
		$(function() {
			<#if peopleStudent?has_content>
				$("input[name=peopleStudentSchool]").val("${peopleStudent.peopleStudentSchool?default('')}");
				$("input[name=peopleStudentDepartment]").val("${peopleStudent.peopleStudentDepartment?default('')}");
				$("input[name=peopleStudentSpecialty]").val("${peopleStudent.peopleStudentSpecialty?default('')}");
				$("input[name=peopleStudentClass]").val("${peopleStudent.peopleStudentClass?default('')}");
				$("input[name=peopleStudentNo]").val("${peopleStudent.peopleStudentNo?default('')}");
				$("input[name=peopleStudentRoom]").val("${peopleStudent.peopleStudentRoom?default('')}");
				$("input[name=peopleUserRealName]").val("${peopleStudent.peopleUserRealName?default('')}");
				$("input[name=peopleStudentIndate]").val("${peopleStudent.peopleStudentIndate?default('')}");
				$("input[name=peopleStudentRoom]").val("${peopleStudent.peopleStudentRoom?default('')}");
				$("input[name=peopleStudentPeopleId]").val("${peopleStudent.peopleStudentPeopleId?default('')}");
				$("input[name=peopleUserCard]").val(${peopleStudent.peopleUserCard?default()});
				$("input[name=peopleStudentPeopleId]").val("${peopleStudent.peopleStudentPeopleId?default('')}");
				$("input[name=peopleId]").val("${peopleStudent.peopleId?default(0)}");
				$("input[name=peoplePhone]").val("${peopleStudent.peoplePhone?default(0)}");
				var peopleStudentEducation="${peopleStudent.peopleStudentEducation?default('')}"
				$("select[name=peopleStudentEducation]").find("option[value="+peopleStudentEducation+"]").attr("selected",true);
			</#if>
			$("#updateStudent").click(function() {
				$(this).postForm("#peopleStudentForm",{func:function(msg) {
					if(msg.result){
							alert("更新成功");
							location.href=msg.resultMsg;
						}else{
							alert(msg.resultMsg);
						}
				}});
			});
		})
	</script>
</body>
</html>	