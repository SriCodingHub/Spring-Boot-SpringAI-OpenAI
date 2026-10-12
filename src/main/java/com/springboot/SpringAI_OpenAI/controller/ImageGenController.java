package com.springboot.SpringAI_OpenAI.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenController {

//	private ChatClient chatClient;
//	private OpenAiImageModel openAiImageModel;
//
//	public ImageGenController(OpenAiImageModel openAiImageModel, OpenAiChatModel chatModel) {
//		this.openAiImageModel = openAiImageModel;
//		this.chatClient = ChatClient.create(chatModel);
//	}
//
//	@GetMapping("/image/{query}")
//	public String genImage(@PathVariable String query) {
//		// ImagePrompt prompt = new ImagePrompt(query);
//		ImagePrompt prompt = new ImagePrompt(query,
//				OpenAiImageOptions.builder().quality("hd").height(1024).width(1024).style("natural").build());
//		ImageResponse response = openAiImageModel.call(prompt);
//		return response.getResult().getOutput().getUrl();
//	}
//
//	@PostMapping("/image/describe")
//	public String descImage(@RequestParam String query, @RequestParam MultipartFile file) {
//		return chatClient.prompt().user(us -> us.text(query).media(MimeTypeUtils.IMAGE_JPEG, file.getResource())).call()
//				.content();
//	}
}