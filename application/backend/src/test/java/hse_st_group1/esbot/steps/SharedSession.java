package hse_st_group1.esbot.steps;

import org.springframework.stereotype.Component;

import hse_st_group1.esbot.model.Session;
import io.cucumber.spring.ScenarioScope;
import lombok.Getter;
import lombok.Setter;

@Component
@ScenarioScope
@Getter
@Setter
public class SharedSession {
    private Session session;
}
