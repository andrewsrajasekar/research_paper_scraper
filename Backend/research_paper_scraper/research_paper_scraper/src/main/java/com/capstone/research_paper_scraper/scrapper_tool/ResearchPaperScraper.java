package com.capstone.research_paper_scraper.scrapper_tool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public final class ResearchPaperScraper {
        public static void main(String[] args) throws IOException {
                WebDriverManager.chromedriver().setup();
                WebDriver driver = new ChromeDriver();
                HashMap<String, HashMap<String, String>> titleVsData = new HashMap<>();

                // Navigate to the starting page
                driver.get("https://ieeexplore.ieee.org/search/searchresult.jsp?newsearch=true&queryText=Robotics");
                Boolean isPageDataAvail = true;
                // Your scraping logic with pagination
                while (isPageDataAvail) {
                        // Scrape the content on the current page
                        List<WebElement> pageContent = driver
                                        .findElements(By.xpath("//xpl-results-list/div[@class='List-results-items']"));

                        for (WebElement element : pageContent) {
                                // Get the HTML content of the element
                                String htmlContent = element.getAttribute("outerHTML");

                                // Parse the HTML content with Jsoup
                                Document doc = Jsoup.parse(htmlContent);

                                // Now you can use Jsoup to extract data from the parsed HTML
                                Elements links = doc.select("a[class='fw-bold']");
                                for (Element link : links) {
                                        StringBuilder title = new StringBuilder();
                                        HashMap<String, String> paperData = new HashMap<>();
                                        for (Node data : link.childNodes()) {
                                                if (data instanceof TextNode) {
                                                        title.append(((TextNode) data).text());
                                                } else if (data instanceof Element) {
                                                        title.append(((TextNode) data.childNode(0)).text());
                                                }
                                        }
                                        paperData.put("paper_link", "ieeexplore.ieee.org" + link.attr("href"));
                                        paperData.put("title", title.toString());
                                        titleVsData.put(title.toString(), paperData);
                                }
                        }
                        isPageDataAvail = false;

                        // Process pageContent as needed

                        // Navigate to the next page
                        // WebElement nextPageButton =
                        // driver.findElement(By.xpath("//your/next/page/button"));
                        // if (nextPageButton.isEnabled()) {
                        // nextPageButton.click();
                        // } else {
                        // // Break the loop if there is no next page
                        // break;
                        // }
                }

                // Close the browser when done
                driver.quit();
                System.out.println(titleVsData);
        }
}