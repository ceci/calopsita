package br.com.caelum.calopsita.integration.stories;

import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.joda.time.LocalDate;
import org.junit.Test;

import br.com.caelum.calopsita.integration.HtmlUnitFactory;
import br.com.caelum.calopsita.integration.stories.common.DefaultStory;
import br.com.caelum.calopsita.model.Card;
import br.com.caelum.calopsita.model.Iteration;
import br.com.caelum.calopsita.model.Project;
import br.com.caelum.calopsita.model.User;
import br.com.caelum.seleniumdsl.Browser;
import br.com.caelum.seleniumdsl.ContentTag;
import br.com.caelum.seleniumdsl.Form;
import br.com.caelum.seleniumdsl.Page;

/**
 * <b>In order to</b> follow the iteration's progress <br>
 * <b>As a</b> client or developer <br>
 * <b>I want</b> to flag a card as done. <br>
 * 
 * @author lucascs
 * 
 */
public class MarkAsDoneStory extends DefaultStory {

	@Test
	public void flagCardAsDone() {
		String login = "kung";
		User user = new User();
		user.setLogin(login);
		user.setEmail(login + "@caelum.com.br");
		user.setName(login);
		user.setPassword(login);
		session.saveOrUpdate(user);

		Project project = new Project();
		project.setId(1L);
		project.setDescription("VRaptor 3");
		project.setName("Vraptor 3");
		project.setOwner(user);
		session.save(project);

		Iteration iteration = new Iteration();
		iteration.setGoal("make it work");
		iteration.setProject(project);
		iteration.setStartDate(new LocalDate().minusDays(1));
		session.save(iteration);

		Card card = new Card();
		card.setName("support Vraptor 2");
		card.setProject(project);
		card.setIteration(iteration);
		card.setDescription("some stuff should be backward compatible");
		session.save(card);
		session.flush();

		Browser browser = new HtmlUnitFactory().getBrowser();

		browser.open("/calopsita");
		Page currentPage = browser.currentPage();
		Form form = currentPage.form("form");
		form.field("user.login").type("kung");
		form.field("user.password").type("kung");
		form.submit();

		browser.open("/calopsita/projects/");
		browser.currentPage().clickLink("Vraptor 3");
		browser.currentPage().dragAndDrop("support Vraptor 2", "done_cards");
		browser.currentPage().waitUntil("$('#done_cards .card').length > 0", 5000);

		assertThat(browser.currentPage().div("done_cards"), containsText("support Vraptor 2"));
	}

	@Factory
	public static Matcher<ContentTag> containsText(final String string) {
		return new TypeSafeMatcher<ContentTag>() {
			private String innerHTML;

			@Override
			public boolean matchesSafely(ContentTag item) {
				innerHTML = item.innerHTML();
				return innerHTML.contains(string);
			}

			public void describeTo(Description description) {
				description.appendText("a div containing '").appendText(string).appendText("'");
			}
		};
	}

}
