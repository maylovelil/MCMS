<@ms.html5>
	 <@ms.nav title="用户管理" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="peopleUserForm" isvalidation=true>
    		<@ms.hidden name="puPeopleId" value="${(peopleUserEntity.puPeopleId)?default('')}"/>
			<@ms.text label="用户昵称" name="puNickname" value="${(peopleUserEntity.puNickname)?default('')}"  width="240px;" placeholder="请输入用户昵称" validation={"data-bv-stringlength":"true","maxlength":"50","data-bv-stringlength-message":"用户昵称长度不能超过五十个字符长度!"}/>
			<@ms.text name="peopleName" label="用户名" placeholder="请输入用户名"  title="" size="5" width="240"  value="${(peopleUserEntity.peopleName)?default('')}" validation={"required":"true","data-bv-stringlength":"true","data-bv-regexp":"true","data-bv-regexp-regexp":'^[^[!@#$%^&*()_+-/~?！@#￥%…&*（）——+—？》《：“‘’]+$',"data-bv-stringLength-min":"3" ,"data-bv-stringlength-max":"30","data-bv-regexp-message":"用户名长度不能超过30个字符且不能包含特殊字符","data-bv-stringLength-message":"用户名长度为3到30个字符", "data-bv-notempty-message":"必填项目"}/>
			<@ms.password name="peoplePassword" label="密码"   title="" size="5" width="240" placeholder="请输入密码" validation={"required":"true","data-bv-stringlength":"true","data-bv-stringlength-max":"20","data-bv-stringLength-min":"6" ,"data-bv-stringlength-message":"密码长度为6-20个字符","data-bv-regexp":"true","data-bv-regexp-regexp":'^[A-Za-z0-9_]+$',"data-bv-regexp-message":"密码只能由英文字母，数字，下划线组成!", "data-bv-notempty-message":"必填项目"}/>
			<@ms.text name="peoplePhone" label="手机号" placeholder="请输入手机号" title="" size="5" width="240" value="${(peopleUserEntity.peoplePhone)?default('')}" validation={"maxlength":"18","data-bv-stringlength":"true","data-bv-stringlength-max":"18","data-bv-stringlength-message":"手机号码长度不能超过18个字符","data-bv-regexp":"true", "data-bv-regexp-regexp":'^[1][1-8][0-9]{9}',"data-bv-regexp-message":"手机号码格式错误"}/>
			<@ms.text name="peopleMail" label="邮箱" placeholder="请输入邮箱" title="" size="5" width="240" value="${(peopleUserEntity.peopleMail)?default('')}" validation={"data-bv-stringlength":"true","data-bv-stringlength-max":"50","data-bv-stringlength-message":"邮箱长度不能超过50个字符","data-bv-regexp":"true", "data-bv-regexp-regexp":'^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$',"data-bv-regexp-message":"邮箱格式错误"}/>
			<#assign peopleSexs=[{"id":"1","name":"男"},{"id":"2","name":"女"}]>
			<@ms.radio name="puSex" label="性别"  list=peopleSexs listKey="id" listValue="name" value="${(peopleUserEntity.puSex)?default('1')}"/>
			<@ms.text label="姓名" name="puRealName" value="${(peopleUserEntity.puRealName)?default('')}"  width="240px;" placeholder="请输入用户真实名称" validation={"data-bv-stringlength":"true","maxlength":"50","data-bv-stringlength-message":"用户真实名称长度不能超过五十个字符长度!"}/>
			<@ms.formRow label="头像" width="400">
					<@ms.uploadImg path="people" inputName="puIcon" size="1" filetype="" msg="提示:头像缩略图,支持jpg格式"  maxSize="2" imgs="${(peopleUserEntity.puIcon)?default('')}"  />
			</@ms.formRow>
			<@ms.text label="用户身份证" name="puCard" value="${(peopleUserEntity.puCard)?default('')}"  width="240px;" placeholder="请输入身份证" validation={"data-bv-stringlength":"true","data-bv-stringLength-min":"18" ,"data-bv-stringlength-max":"18","data-bv-regexp":"true","data-bv-regexp-regexp":'^[X0-9]+$',"data-bv-stringLength-message":"身份证长度错误","data-bv-regexp-message":"身份证输入不合法"}/>
			<@ms.textarea name="puAddress"  label="用户地址"   rows="4"  placeholder="请输入用户地址" width="500" value="${(peopleUserEntity.puAddress)?default('')}" validation={"data-bv-stringlength":"true","data-bv-stringlength-max":"60","data-bv-stringlength-message":"地址不能超过60个字符"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/people/peopleUser/save.do";
	if($("input[name = 'puPeopleId']").val() > 0){
		url = "${managerPath}/people/peopleUser/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#peopleUserForm").data("bootstrapValidator").validate();
			var isValid = $("#peopleUserForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'peopleUserForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.peopleId > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/people/peopleUser/index.do";
				}
				else{
					alert(status.resultMsg);
					$(".btn-success").text(btnWord);
					$(".btn-success").prop("disabled",false);
				}
			}
		})
	}	
</script>
