package puertoricotr;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefsIntegrationTest extends SpringIntegrationTest {

    @When("^the client calls /statistiques/save$")
    public void the_client_issues_POST_winner(){
        executePost("http://localhost:8080/score/v1/statistiques/save", "BotTest1");
    }

    @Then("^the client receives status code of (\\d+)$")
    public void the_client_receives_status_code_of(int statusCode) {
        assertThat("status code is correct : ", statusCode, is(statusCode));
    }

    @And("^the server saves the winner's name (.+)$")
    public void the_server_saves_the_winners_name(String botName){
        addWinners(botName);
        assertThat("size of the winners list : ", arrayBody.size(), is(1));
        assertThat("the name added in the winners list : ", arrayBody.get(0), is(botName));
    }

    @Given("^The list of winners has 2 winners")
    public void The_list_of_winners_has_2_winners() throws Throwable {
        executePost("http://localhost:8080/score/v1/statistiques/save", "BotTest1");
        addWinners("BotTest1");
    }

    @When("^the client calls /statistiques$")
    public void the_client_issues_Get_winners_list_array() throws Throwable {
        executeGetArray("http://localhost:8080/score/v1/statistiques/");
    }

    @And("^the client receives the winners list containing 2 winners")
    public void the_client_receives_the_winners_list_containing_2_winners() throws Throwable {
        executePost("http://localhost:8080/score/v1/statistiques/save", "BotTest2");
        addWinners("BotTest2");
        assertThat("size of the winners list : ", arrayBody.size(), is(2));
    }

    @And("^the client receives the name of the two winners recorded by the server")
    public void the_client_receives_the_name_of_the_two_winners_recorded_by_the_server() throws Throwable {
        assertThat("the name added in the winners list : ", arrayBody.get(0), is("BotTest1"));
        assertThat("the name added in the winners list : ", arrayBody.get(1), is("BotTest2"));
    }

    @Given("^the fitst winner recorded by the server is (.+)$")
    public void the_fitst_winner_recorded_by_the_server_is(String botName){
        executePost("http://localhost:8080/score/v1/statistiques/save", botName);
        addWinners("BotTest1");
        assertThat("the fitst winner recorded by the server is : ", arrayBody.get(0), is(botName));
    }

    @When("^the client calls /statistiques/1$")
    public void the_client_issues_Get_winners_list(){
        executeGet("http://localhost:8080/score/v1/statistiques/1");
    }

    @And("^the client receives the name of the first winner recorded by the server (.+)$")
    public void the_client_receives_the_name_of_the_first_winner_recorded_by_the_server(String botName) {
        assertThat("the name added in the winners list : ", arrayBody.get(0), is(botName));
    }
}
