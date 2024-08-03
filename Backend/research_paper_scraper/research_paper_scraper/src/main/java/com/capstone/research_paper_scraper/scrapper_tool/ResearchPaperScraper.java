package com.capstone.research_paper_scraper.scrapper_tool;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.reflections.Reflections;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ResearchPaperScraper {
        public JSONArray getResearchPaperData(Integer pageNo, String searchQuery) throws Exception {
                Reflections reflections = new Reflections("com.capstone.research_paper_scraper.scrapper_portals");
                Set<Class<? extends ResearchPaperScraperInterface>> classes = reflections
                                .getSubTypesOf(ResearchPaperScraperInterface.class);
                JSONArray result = new JSONArray();
                for (Class<? extends ResearchPaperScraperInterface> clazz : classes) {
                        ResearchPaperScraperInterface scraper = clazz.getDeclaredConstructor().newInstance();
                        JSONObject data = scraper.getPageData(searchQuery, pageNo);
                        result.put(new JSONObject().put("source", scraper.getSource()).put("data", data));
                }
                return result;

        }
}