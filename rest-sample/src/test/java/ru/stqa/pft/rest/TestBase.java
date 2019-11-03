package ru.stqa.pft.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.testng.SkipException;

import java.io.IOException;
import java.util.Set;

public class TestBase {
  private String issueStatus;


  public boolean isIssueOpened(int issueId) throws IOException {
    String status = issueStatus(issueId);
    if (!status.equals("Closed")) {
      return true;
    }
    return false;
  }

  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpened(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId + " was not fixed");
    }
  }

  public Executor getExecutor() {
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  //узнаем статус таска по ID
  public String issueStatus(int id) throws IOException {
    String json = getExecutor().execute(Request.Get("http://bugify.stqa.ru/api/issues/" + id + ".json"))
            .returnContent().asString();

    JsonElement parsed = new JsonParser().parse(json);
    JsonElement issues = parsed.getAsJsonObject().get("issues");
    Set<Issue> issue = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    issueStatus = issue.iterator().next().getState_name();

    return issueStatus;
  }
}