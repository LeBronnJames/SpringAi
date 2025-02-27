/*
 * Copyright 2024-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wangchangyang.ai.services;

import java.time.LocalDate;

import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * * @author wangcy
 */
@Service
public class CustomerSupportAssistant {

	private final ChatClient chatClient;

	public CustomerSupportAssistant(ChatClient.Builder modelBuilder, VectorStore vectorStore, ChatMemory chatMemory) {

		// @formatter:off
		this.chatClient = modelBuilder
				.defaultSystem("""
						您是“慧退税”平台的客户聊天智能助手。请以友好、乐于助人且愉快的方式来回复。
						您正在通过在线聊天系统与客户互动。
						您能够支持已有报关单信息查询、报关单出口发票号补填、报关单信息删除、报关单数据下载等操作，其余功能将在后续版本中添加，如果用户问的问题不支持请告知详情。
						在发起关报关单信息查询、报关单信息删除操作之前，您必须始终从用户处获取以下信息：报关单号。
						在发起报关单出口发票号补填操作之前，您必须始终从用户处获取以下信息：报关单号、出口发票号。
						在发起报关单数据下载操作之前，您必须始终从用户处获取以下信息：出口日期范围。
						在询问用户之前，请检查消息历史记录以获取报关单号、出口发票号、出口日期范围等信息，尽量避免重复询问给用户造成困扰。
						在您使用慧退税智能助手各项功能之前，您必须确保条款允许这样做。
						使用提供的功能完成报关单信息查询、报关单出口发票号补填和报关单信息删除。
						如果需要，您可以调用相应函数辅助完成。
						请讲中文。
						今天的日期是 {current_date}.
					""")
				.defaultAdvisors(
						new PromptChatMemoryAdvisor(chatMemory), // Chat Memory
						// new VectorStoreChatMemoryAdvisor(vectorStore)),
					
						new QuestionAnswerAdvisor(vectorStore, SearchRequest.builder().topK(4).similarityThresholdAll().build()), // RAG
						// new QuestionAnswerAdvisor(vectorStore, SearchRequest.defaults()
						// 	.withFilterExpression("'documentType' == 'terms-of-service' && region in ['EU', 'US']")),
						
						new LoggingAdvisor())
						
				.defaultFunctions("getBgdDetails", "updateBgd", "deleteBgd", "downloadBgd") // FUNCTION CALLING

				.build();
		// @formatter:on
	}

	public Flux<String> chat(String chatId, String userMessageContent) {

		return this.chatClient.prompt()
			.system(s -> s.param("current_date", LocalDate.now().toString()))
			.user(userMessageContent)
			.advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId).param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
			.stream()
			.content();
	}

}