package com.springboot.SpringAI_OpenAI.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

//	private OpenAiChatModel chatModel;
//
//	public OpenAIController(OpenAiChatModel chatModel) {
//		this.chatModel = chatModel;
//	}
//
//	@GetMapping("/api/{message}")
//	public String getReponse(@PathVariable String message) {
//		String response = chatModel.call(message);
//		return "Hello World " + response;
//	}

//	private ChatClient chatClient;
//
//	public OpenAIController(OpenAiChatModel chatModel) {
//		this.chatClient = ChatClient.create(chatModel);
//	}

//	@GetMapping("/api/{message}")
//	public String getAnswer(@PathVariable String message) {
//		String answer = chatClient.prompt(message).call().content();
//		return answer;
//	}

	private ChatClient chatClient;
	ChatMemory chatMemory = MessageWindowChatMemory.builder().build();

	public OpenAIController(ChatClient.Builder chatBuilder) {
		this.chatClient = chatBuilder.defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build()).build();
	}

	@GetMapping("/api/{message}")
	public String getAnswer(@PathVariable String message) {
		ChatResponse chatResponse = chatClient.prompt(message).call().chatResponse();
		System.out.println("AI Model: " + chatResponse.getMetadata().getModel());
		String answer = chatResponse.getResult().getOutput().getText();
		return answer;
	}
}
