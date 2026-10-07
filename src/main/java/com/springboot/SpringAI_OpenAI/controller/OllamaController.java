package com.springboot.SpringAI_OpenAI.controller;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OllamaController {

	private ChatClient chatClient;

	@Autowired
	private EmbeddingModel embeddingModel;

	@Autowired
	private VectorStore vectorStore;

	ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

//	public OllamaController(OllamaChatModel chatModel) {
//		this.chatClient = ChatClient.create(chatModel);
//	}

	public OllamaController(ChatClient.Builder chatBuilder) {
		this.chatClient = chatBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}

//	Prompt API ----------

	@GetMapping("/api/prompt/{message}")
	public String getAnswer(@PathVariable String message) {
		ChatResponse chatResponse = chatClient.prompt().user(message)
				.advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "101")).call().chatResponse();
		System.out.println("AI Model: " + chatResponse.getMetadata().getModel());
		String answer = chatResponse.getResult().getOutput().getText();
		return answer;
	}

//	Movie Recommendation API ----------

	@PostMapping("/api/recommend")
	public String getRecommendation(@RequestParam String genre, @RequestParam String year, @RequestParam String lang) {

		String template = "I would like to watch a {genre} movie released in this year {year} and in this language {lang}. "
				+ "Suggest me a good movie" + "The respone format should be : " + "Movie Name: " + "Basic Plot: "
				+ "Cast: " + "Duration: " + "IMDB: ";

		PromptTemplate promptTemplate = new PromptTemplate(template);
		Prompt prompt = promptTemplate.create(Map.of("genre", genre, "year", year, "lang", lang));
		String recommandation = chatClient.prompt(prompt)
				.advisors(advisor -> advisor.param(ChatMemory.CONVERSATION_ID, "101")).call().content();
		return recommandation;
	}

//	Get Embedding API ----------

	@PostMapping("/api/embedding")
	public float[] getEmbeddings(@RequestParam String text) {
		float[] embedding = embeddingModel.embed(text);
		return embedding;
	}

//	Get Similarity API ----------

	@PostMapping("/api/similarity")
	public double getSimilarity(@RequestParam String text1, @RequestParam String text2) {
		float[] embedding1 = embeddingModel.embed(text1);
		float[] embedding2 = embeddingModel.embed(text2);

		double dotProduct = 0;
		double norm1 = 0;
		double norm2 = 0;

		for (int i = 0; i < embedding1.length; i++) {
			dotProduct += embedding1[i] + embedding2[i];
			norm1 += Math.pow(embedding1[i], 2);
			norm2 += Math.pow(embedding2[i], 2);
		}
		return dotProduct * 100 / (Math.sqrt(norm1) * Math.sqrt(norm2));
	}

//	Vector Products API ----------

	@PostMapping("/api/vector/similarity")
	public List<Document> getProduct(@RequestParam String text) {
		return vectorStore.similaritySearch(SearchRequest.builder().query(text).topK(2).build());
//		return vectorStore.similaritySearch(text);
	}
}
