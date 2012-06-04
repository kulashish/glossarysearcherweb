<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Search Results</title>
</head>
<body>
	<f:view>
		<h:outputText
			value="You searched for #{searchInputBean.ontoNode} : #{searchInputBean.seedList}"
			style="font-weight:bold" />
		<br />
		<h:dataTable rows="100"
			value="#{searchInputBean.searchResult.artifacts}" var="resultEntry">
			<h:column>
				<h:outputLink value="#{resultEntry.url}">
					<h:outputText value="#{resultEntry.title}" />
				</h:outputLink>
				
			</h:column>
		</h:dataTable>
	</f:view>
</body>
</html>