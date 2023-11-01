package com.pweb.email.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailAMQPConfig {

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
			Jackson2JsonMessageConverter messageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter);
		return rabbitTemplate;
	}

	@Bean
	public Queue filaEmails() {
		return QueueBuilder.nonDurable("emails.to-send").build();
	}

	@Bean
	public FanoutExchange fanoutExchange() {
		return ExchangeBuilder.fanoutExchange("emails.ex").build();
	}

	@Bean
	public Binding bindEmails(FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(filaEmails()).to(fanoutExchange());
	}

	@Bean
	public RabbitAdmin criarRabbitAdmin(ConnectionFactory conn) {
		return new RabbitAdmin(conn);
	}

	@Bean
	public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
		return event -> rabbitAdmin.initialize();
	}
}
