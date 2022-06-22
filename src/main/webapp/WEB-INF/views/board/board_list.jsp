<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	a{
		text-decoration:none;
	}
	table{
		border-collapse:collapse;
	}
</style>
</head>
<!-- boardVO
	private int idx,readhit,ref,step,depth;
	private String name,subject,content,pwd,ip,regdate;
	 -->
<body>
	<c:if test="${list eq null}">
		<script>
			location.replace("board_list.do?page=1");
		</script>
	</c:if>
	<table border="1" width="700">
		<tr>
			<td colspan="5"><img src="resources/img/title_04.gif"></td>
		</tr>
		<tr>
			<th>번호</th>
			<th width="350">제목</th>
			<th width="120">작성자</th>
			<th width="100">작성일</th>
			<th width="60">조회수</th>
		<tr>
		<c:forEach var="vo" items="${list}">
			<tr>
				<td align="center">
				<c:out value="${vo.idx}"></c:out>
				</td>
				<td>
				<c:if test="${vo.del_info ne -1}">
					<c:forEach begin="1" end="${vo.depth}">&nbsp;</c:forEach>
					<!-- 댓글기호달기 -->
					<c:if test="${vo.depth ne 0}">ㄴ</c:if>
					<a href="view.do?idx=${vo.idx}&page=${param.page}">
					<font color="black">${vo.subject}</font>
					</a>
				</c:if>
				<c:if test="${vo.del_info eq -1}">
					<c:forEach begin="1" end="${vo.depth}">&nbsp;</c:forEach>
					<!-- 댓글기호달기 -->
					<c:if test="${vo.depth ne 0}">ㄴ</c:if>
						<font color="gray">${vo.subject}</font>
				</c:if>
				</td>
				<td>
				<c:out value="${vo.name}"></c:out>
				</td>
				<c:if test="${vo.del_info ne -1 }">
					<td align="center">
					<c:out value="${fn:split(vo.regdate, ' ')[0]}"></c:out>
					</td>
				</c:if>
				<c:if test="${vo.del_info eq -1 }">
					<td align="center">
					unknown
					</td>
				</c:if>
				<td align="center">
				<c:out value="${vo.readhit}"></c:out>
				</td>
			</tr>
		</c:forEach>
		<tr>
			
			<td colspan="5" align="center">
				${pageMenu}	
			</td>
		</tr>
		<tr>
			<td colspan="5" align="right">
				<img src="resources/img/btn_reg.gif" onclick="location.href='insert_form.do'"
				style="cursor:pointer;">
			</td>
		<tr>
		
	</table>
	
</body>
</html>











