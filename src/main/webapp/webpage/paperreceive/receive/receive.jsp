<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="ani"/>
<%@include file="/webpage/include/treeview.jsp" %>
<%@ include file="/webpage/include/bootstraptable.jsp"%>
 <script type="text/javascript">
	 $(document).ready(function() {
		$('#submitBtn').click(function(){
		    var tqm = $('#tqm').val();
		    var fileName = "/paper/userfiles/1/程序附件//papermanage/paper/2018/9/BB.docx";
		    console.log(tqm);
		    if(tqm.trim() == ""){
		        layer.alert("提取码不能为空!");
            }
            else {
		        jp.get("${ctx}/receive/receive/getOrder?accessCode="+tqm,function (data) {
                    console.log(data);
                    if(data.success){
                        top.layer.open({
                            type: 2,
                            area: ['800px','500px'],
                            title: "提取论文",
                            auto: true,
                            name: 'friend',
                            content:'${ctx}/receive/receive/getPaperBytqm?tqm='+tqm,
                            yse: function (index,layero) {

                            },
                            cancel: function(index){
                            }
                        });
                    }else {
                        layer.alert(data.msg);
                    }
                });
            }
		});

	 });
 </script>
<title>接收论文</title>
</head>
<body>
<div class="wrapper wrapper-content">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        <a class="panelButton" href="${ctx}/receive/receive"><i class="ti-angle-left"></i> 返回</a>
                    </h3>
                </div>
                <div class="panel-body">
                    <div class="row">
        			<div class="col-sm-12 col-md-12 animated fadeInRight">
                    <form:form id="inputForm" modelAttribute="receive" action="${ctx}/receive/receive/save" method="post" class="form-horizontal" onsubmit="return false">
                        <form:hidden path="id"/>
                        <sys:message content="${message}"/>
                        <div class="form-group">
                            <label style="font-size: 20px" class="col-sm-1 control-label"><font color="red">*</font>提取码：</label>
                            <div class="col-sm-10">
                                <input id="tqm" name="tqm" htmlEscape="false" size="40" class="required input-lg" type="text"/>
                            </div>
                        </div>
                        </div>
					<div class="col-lg-1"></div>
			        <div class="col-lg-1">
			             <div class="form-group text-center">
			                 <div>
			                     <button id="submitBtn"  class="btn btn-primary btn-block btn-lg btn-parsley" data-loading-text="正在提交...">提取</button>
			                 </div>
			             </div>
			        </div>
			      </form:form>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>