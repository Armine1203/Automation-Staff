package pages;

import org.openqa.selenium.WebElement;

public class Company {
    private String name;
    private Integer pageViews;
    private Integer pageFollowers;
    private Integer activeJobsCount;
    private Integer historyJobsCount;

    public Company(String name, Integer pageViews, Integer pageFollowers, Integer activeJobsCount, Integer historyJobsCount) {
        this.name = name;
        this.pageViews = pageViews;
        this.pageFollowers = pageFollowers;
        this.activeJobsCount = activeJobsCount;
        this.historyJobsCount = historyJobsCount;
    }

    public String getName() {
        return name;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public Integer getPageFollowers() {
        return pageFollowers;
    }

    public Integer getActiveJobsCount() {
        return activeJobsCount;
    }

    public Integer getHistoryJobsCount() {
        return historyJobsCount;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", pageViews=" + pageViews +
                ", pageFollowers=" + pageFollowers +
                ", activeJobsCount=" + activeJobsCount +
                ", historyJobsCount=" + historyJobsCount +
                '}';
    }
}

