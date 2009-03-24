package br.com.caelum.calopsita.integration.stories;

import org.junit.Test;

public class CreateAnIterationStory extends DefaultStory {
    @Test
    public void iterationCreation() throws Exception {
        given.thereIsAnUserNamed("David");
        given.thereIsAProjectNamed("Papyrus").ownedBy("David");
        given.iAmLoggedInAs("David");
        
        when.iOpenProjectPageOf("Papyrus");
        when.iAddTheIteration("create and update an incident").withStartDate("04/04/2000").withEndDate("04/05/2000");
        
        then.theIteration("create and update an incident").appearsOnList();
    }
}