package in.ac.iitb.search;

import java.util.ArrayList;
import java.util.List;

public class SearchResult
{
    private List<Artifact> artifacts = new ArrayList<Artifact>();
    private int count = 0;
    
    public SearchResult()
    {
        super();
    }

    public List<SearchResult.Artifact> getArtifacts()
    {
        return artifacts;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }


    public static class Artifact
    {
        private String title = "";
        private String descr = "";
        private String url = "";
        
        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public void setDescr(String descr)
        {
            this.descr = descr;
        }

        public String getDescr()
        {
            return descr;
        }

        public void setUrl(String url)
        {
            this.url = url;
        }

        public String getUrl()
        {
            return url;
        }
    }
}
