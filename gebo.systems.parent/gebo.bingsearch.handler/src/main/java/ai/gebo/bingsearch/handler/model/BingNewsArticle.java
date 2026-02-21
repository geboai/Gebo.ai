package ai.gebo.bingsearch.handler.model;

import lombok.Data;

@Data
public class BingNewsArticle {
	String name;
	String url;
	String description;
	String datePublished;
	// provider, image, category, etc.
}