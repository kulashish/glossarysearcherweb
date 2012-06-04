package somepackage;

import in.ac.iitb.search.SearchFiles;
import in.ac.iitb.search.SearchResult;

import java.io.IOException;

import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.richfaces.component.html.HtmlTree;
import org.richfaces.event.NodeSelectedEvent;

public class SearchInputBean {
	private String ontoNode;
	private String seedList;
	private SearchResult searchResult = null;

	public SearchResult getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(SearchResult searchResult) {
		this.searchResult = searchResult;
	}

	public String getOntoNode() {
		return ontoNode;
	}

	public void setOntoNode(String ontoNode) {
		this.ontoNode = ontoNode;
	}

	public String getSeedList() {
		return seedList;
	}

	public void setSeedList(String seedList) {
		this.seedList = seedList;
		// System.out.println(seedList);
	}

	public void processSelection(NodeSelectedEvent event) {
		HtmlTree tree = (HtmlTree) event.getComponent();
		ontoNode = (String) tree.getRowData();
		// System.out.println(ontoNode);
	}

	public String search() {
		String result = "success";
		SearchFiles search = new SearchFiles();
		try {
			searchResult = search.query(seedList);
		} catch (CorruptIndexException e) {
			result = "failure";
		} catch (IOException e) {
			result = "failure";
		} catch (ParseException e) {
			result = "failure";
		}
		return result;
	}
}
