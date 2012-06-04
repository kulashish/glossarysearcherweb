package in.ac.iitb.search;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchFiles {
	public SearchFiles() {
		super();
	}

	public static void main(String[] args) {
		SearchFiles search = new SearchFiles();
		SearchResult result = null;
		try {
			result = search.query("bairi");
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result.getCount());
	}

	public SearchResult query(String queryString) throws CorruptIndexException,
			IOException, ParseException {
		 String index = "/home/ashish/csecrawl/indexes/part-00000";
//		String index = "/home/biadmin/csecrawl/index";
		String field = "content";
		int hitsPerPage = 100;

		SearchResult searchResult = new SearchResult();

		if (queryString.equalsIgnoreCase("test-me")) {
			SearchResult.Artifact artifact = new SearchResult.Artifact();
			artifact.setTitle("Test 1");
			artifact.setUrl("http://www.iitb.ac.in");
			searchResult.getArtifacts().add(artifact);

			artifact = new SearchResult.Artifact();
			artifact.setTitle("Test 2");
			artifact.setUrl("http://www.iitb.ac.in");
			searchResult.getArtifacts().add(artifact);

			searchResult.setCount(40);

			return searchResult;
		}

		IndexReader reader = IndexReader
				.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_31);
		QueryParser parser = new QueryParser(Version.LUCENE_31, field, analyzer);
		Query query = parser.parse(queryString);

		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;

		searchResult.setCount(results.totalHits);

		int end = Math.min(results.totalHits, hitsPerPage);
		for (int i = 0; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			SearchResult.Artifact artifact = prepareArtifact(doc);
			searchResult.getArtifacts().add(artifact);
		}

		searcher.close();
		reader.close();

		return searchResult;
	}

	private SearchResult.Artifact prepareArtifact(Document doc)
			throws UnsupportedEncodingException {
		SearchResult.Artifact artifact = new SearchResult.Artifact();
		List<Fieldable> fields = doc.getFields();
		String[] values = null;
		for(Fieldable field:fields)	{
			values = doc.getValues(field.name());
			System.out.print(field.name()+": ");
			for(String value:values)
				System.out.print(value+",");
			System.out.println();
		}
		System.out.println();
		// System.out.println(doc.getFieldable("url").stringValue());
		String url = doc.getFieldable("url").stringValue();
		String title = doc.getFieldable("title").stringValue();
		artifact.setUrl(doc.getFieldable("url").stringValue());
		// Prepare title
		artifact.setTitle(!title.equalsIgnoreCase("") ? title : url);

		return artifact;
	}
}
