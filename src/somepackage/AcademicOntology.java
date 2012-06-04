package somepackage;

import java.io.File;
import java.util.Set;

import org.richfaces.model.TreeNodeImpl;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.ClassExpressionType;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class AcademicOntology {

	private static final String ACAD_ONTO_OWL = "/home/ashish/iitb-intranet/ontology/Academic-Ontology.owl";
	private TreeNodeImpl<String> root;
	private TreeNodeImpl<String> ontoNodes;
	private OWLOntologyManager manager;
	private OWLOntology acadOnto;

	public AcademicOntology() throws BeanException {
		root = new TreeNodeImpl<String>();
		ontoNodes = new TreeNodeImpl<String>();
		manager = OWLManager.createOWLOntologyManager();
		File ontoFile = new File(ACAD_ONTO_OWL);
		try {
			acadOnto = manager.loadOntologyFromOntologyDocument(ontoFile);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			throw new BeanException(e);
		}
		OWLDataFactory factory = manager.getOWLDataFactory();
		OWLClass thingClass = factory.getOWLThing();
		root.setData(className(thingClass.toStringID()));
		ontoNodes.addChild(0, root);
		subclasses(thingClass, root, null);
	}

	private String className(String stringID) {
		int last = stringID.indexOf('>');
		if (last == -1)
			last = stringID.length();
		return stringID.substring(stringID.indexOf('#') + 1, last);
	}

	private void subclasses(OWLClassExpression parentClass,
			TreeNodeImpl<String> parentNode,
			Set<OWLClassExpression> inheritedAnonymous) {
		int iChildCount = 0;
		Set<OWLClassExpression> subClasses = null;
		Set<OWLClassExpression> superClasses = parentClass.asOWLClass()
				.getSuperClasses(acadOnto);
		if (null != inheritedAnonymous)
			superClasses.addAll(inheritedAnonymous);
		if (superClasses.size() > 1) {
			TreeNodeImpl<String> propertiesNode = new TreeNodeImpl<String>();
			propertiesNode.setData("Properties");
			parentNode.addChild(iChildCount++, propertiesNode);

			for (OWLClassExpression expr : superClasses) {
				if (expr.getClassExpressionType().equals(
						ClassExpressionType.OWL_CLASS))
					continue;
				TreeNodeImpl<String> node = new TreeNodeImpl<String>();
				node.setData(className(expr.toString()));
				propertiesNode.addChild(iChildCount++, node);
			}
		}
		if (parentClass.getClassExpressionType().equals(
				ClassExpressionType.OWL_CLASS))
			subClasses = parentClass.asOWLClass().getSubClasses(acadOnto);
		if (null != subClasses && subClasses.size() > 0) {
			TreeNodeImpl<String> subClassNode = new TreeNodeImpl<String>();
			subClassNode.setData("Sub Classes");
			parentNode.addChild(iChildCount++, subClassNode);
			for (OWLClassExpression expr : subClasses) {
				TreeNodeImpl<String> node = new TreeNodeImpl<String>();
				node.setData(className(expr.toString()));
				subClassNode.addChild(iChildCount++, node);
				subclasses(expr, node, superClasses.size() > 1 ? superClasses
						: null);
			}
		}
	}

	public TreeNodeImpl<String> getOntoNodes() {
		return ontoNodes;
	}

}
