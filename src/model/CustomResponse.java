
import java.util.List;

public class CustomResponse {
    private List<Search> Search;
    private int currentPage;
    private int viewSize;
    private int pages;
    private int totalResults;

    // Геттеры и сеттеры
    public List<Search> getSearch() { return Search; }
    public void setSearch(List<Search> search) { Search = search; }
    public int getCurrentPage() { return currentPage; }
    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }
    public int getViewSize() { return viewSize; }
    public void setViewSize(int viewSize) { this.viewSize = viewSize; }
    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }
    public int getTotalResults() { return totalResults; }
    public void setTotalResults(int totalResults) { this.totalResults = totalResults; }
}