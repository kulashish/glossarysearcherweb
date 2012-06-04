<!doctype html public "-//w3c//dtd html 4.0 transitional//en">

<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<!-- RichFaces tag library declaration -->

<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j"%>

<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>



<html>

<head>

<title>Glossary Searcher</title>
<style type="text/css">
.colStyle1 { width: 560px;
vertical-align: top;
}
.colStyle2 { width: 560px;
height: 100%;
}
.colStyle3 { width: 160px;
background: blue;
}
.textbox { width: 500px; height: 50px; font-size: 18px; }
.valign td {
    vertical-align: top;
}

</style>
</head>

<body>


	<f:view>
		<h:panelGrid columns="2" columnClasses="colStyle1,colStyle2" >
		
		<h:form>
		<div style="height: 500px; width: 560px; overflow: scroll;">
			<rich:tree nodeSelectListener="#{searchInputBean.processSelection}" ajaxSubmitSelection="true" value="#{academicOntology.ontoNodes}" var="node" ajaxKeys="#{null}" switchType="client">
				<rich:treeNode>
					<h:outputText value="#{node}" />
				</rich:treeNode>
			</rich:tree>
			</div>
		</h:form>
		
		<h:form>
		<h:panelGrid cellpadding="10" columns="1">
			<h:outputText value="Enter the seed list" style="font-size: 20px;" />
    		<h:inputText value="#{searchInputBean.seedList}" styleClass="textbox" style="font-size: 20px;"/>
    		<h:commandButton value="Search" action="#{searchInputBean.search}" style="font-size: 20px; horizontal-align: center;"/>
    	</h:panelGrid>
    	  </h:form>
		</h:panelGrid>
	</f:view>

</body>

</html>
