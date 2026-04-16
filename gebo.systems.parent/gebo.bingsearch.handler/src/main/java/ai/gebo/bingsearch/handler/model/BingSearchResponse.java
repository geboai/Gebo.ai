package ai.gebo.bingsearch.handler.model;

import lombok.Data;

@Data
public class BingSearchResponse {
	String _type = null;
	BingWebPages webPages = new BingWebPages();
	BingNews news = new BingNews();
}