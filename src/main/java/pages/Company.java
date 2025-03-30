package pages;

import org.openqa.selenium.WebElement;

public class Company {
    private WebElement name;
    private WebElement pageViews;
    private WebElement pageFollowers;
    private WebElement activeJobsCount;
    private WebElement historyJobsCount;

    public Company(WebElement name, WebElement pageViews, WebElement pageFollowers, WebElement activeJobsCount, WebElement historyJobsCount) {
        this.name = name;
        this.pageViews = pageViews;
        this.pageFollowers = pageFollowers;
        this.activeJobsCount = activeJobsCount;
        this.historyJobsCount = historyJobsCount;
    }

    public WebElement getName() {
        return name;
    }

    public void setName(WebElement name) {
        this.name = name;
    }

    public WebElement getPageViews() {
        return pageViews;
    }

    public void setPageViews(WebElement pageViews) {
        this.pageViews = pageViews;
    }

    public WebElement getPageFollowers() {
        return pageFollowers;
    }

    public void setPageFollowers(WebElement pageFollowers) {
        this.pageFollowers = pageFollowers;
    }

    public WebElement getActiveJobsCount() {
        return activeJobsCount;
    }

    public void setActiveJobsCount(WebElement activeJobsCount) {
        this.activeJobsCount = activeJobsCount;
    }

    public WebElement getHistoryJobsCount() {
        return historyJobsCount;
    }

    public void setHistoryJobsCount(WebElement historyJobsCount) {
        this.historyJobsCount = historyJobsCount;
    }
}
