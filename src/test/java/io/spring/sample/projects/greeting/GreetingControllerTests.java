package io.spring.sample.projects.greeting;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.boot.test.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(GreetingController.class)
class GreetingControllerTests {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void shouldGreetWithSpecificName() {
		this.graphQlTester.query("""
				{
				  greeting(name: "Brian")
				}
				""")
				.execute().path("greeting").entity(String.class).isEqualTo("Hello, Brian!");
	}

	@Test
	void shouldGreetWithDefaultName() {
		this.graphQlTester.query("""
				{
				  greeting
				}
				""")
				.execute().path("greeting").entity(String.class).isEqualTo("Hello, Spring!");
	}
}